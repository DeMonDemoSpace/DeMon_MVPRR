package com.demon.demon_mvprr.mvpc.model;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.demon.baseframe.model.DialogHandler;
import com.demon.baseui.dialog.ImageProgressDialog;
import com.demon.baseutil.ToastUtil;
import com.demon.baseutil.des.DESUtil;
import com.wisefly.nurtouch.data.Constant;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class IObserver implements Observer<String>, DialogHandler.CancelListener {
    private static final String TAG = "BaseObserver";
    private OnNextListener listener;
    private DialogHandler mDialogHandler;
    private Disposable d;
    private Context mContext;
    private boolean isShow = true;
    private int what = 0;

    public IObserver(Context context, OnNextListener listener) {
        mContext = context;
        this.listener = listener;
        ImageProgressDialog dialog = new ImageProgressDialog(context);
        mDialogHandler = new DialogHandler(context, dialog, this);
    }

    public IObserver(Context context, OnNextListener listener, boolean isShow) {
        mContext = context;
        this.listener = listener;
        this.isShow = isShow;
        ImageProgressDialog dialog = new ImageProgressDialog(context);
        mDialogHandler = new DialogHandler(context, dialog, this);
    }

    public IObserver(Context context, OnNextListener listener, boolean isShow, int what) {
        mContext = context;
        this.listener = listener;
        this.isShow = isShow;
        this.what = what;
        ImageProgressDialog dialog = new ImageProgressDialog(context);
        mDialogHandler = new DialogHandler(context, dialog, this);
    }

    private void showProgressDialog() {
        if (mDialogHandler != null && isShow) {
            mDialogHandler.obtainMessage(DialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog() {
        if (mDialogHandler != null && isShow) {
            mDialogHandler.obtainMessage(DialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mDialogHandler = null;
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.d = d;
        showProgressDialog();
    }

    @Override
    public void onNext(String s) {
        String result = dealResult(s);
        if (!TextUtils.isEmpty(result)) {
            listener.onNext(result);
        }
    }

    @Override
    public void onError(Throwable e) {
        if (!TextUtils.isEmpty(e.getMessage())) {
            if (e.getMessage().startsWith("Failed")) {
                ToastUtil.showToast(mContext, "设备网络未连接！！！");
            }

            if (e.getMessage().startsWith("failed")) {
                ToastUtil.showToast(mContext, "服务器未响应！！！");
            }
        }
        listener.onError();
        dismissProgressDialog();
    }

    @Override
    public void onComplete() {
        dismissProgressDialog();
    }

    @Override
    public void onCancelProgress() {
        //如果处于订阅状态，则取消订阅
        if (!d.isDisposed()) {
            d.dispose();
        }
    }

    /**
     * 针对Api返回数据格式进行统一处理
     *
     * @param result
     */
    public String dealResult(String result) {
        String json = DESUtil.decoding(parseXML(result));
        try {
            JSONObject jsonObject = new JSONObject(json);
            if (jsonObject.has(Constant.SUCCESS)) {
                String success = jsonObject.getString(Constant.SUCCESS);
                JSONObject successJson = new JSONObject(success);
                // 判断Json字符串是否包含response字段
                if (successJson.has(Constant.RESPONSE)) {
                    if (successJson.get(Constant.RESPONSE) instanceof JSONObject) {//response是JsonObject
                        JSONObject response = successJson.getJSONObject(Constant.RESPONSE);
                        Log.i(TAG, "JSONObject:" + response.toString());
                        return response.toString();
                    } else if (successJson.get(Constant.RESPONSE) instanceof JSONArray) {//response是JsonArray
                        JSONArray response = successJson.getJSONArray(Constant.RESPONSE);
                        Log.i(TAG, "JSONArray:" + response.toString());
                        return response.toString();
                    } else {
                        Log.i(TAG, "ResponseString: " + successJson.getString(Constant.RESPONSE));
                        return successJson.getString(Constant.RESPONSE);
                    }
                } else {
                    Log.i(TAG, "NO Response:" + success);
                    return successJson.getString(Constant.SUCCESS_MESSAGE);
                }
            } else {
                Log.i(TAG, "ErrorResult:" + json);
                int errorCode = jsonObject.getJSONObject(Constant.ERROR).getInt(Constant.ERROR_MESSAGE_CODE);
                String errorMessage = jsonObject.getJSONObject(Constant.ERROR).getString(Constant.ERROR_MESSAGE);
                if (errorCode == 1 && errorMessage.equals("未将对象引用设置到对象的实例。")) {
                    return null;
                }
                ToastUtil.showThreadToast(mContext, errorMessage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 将接口返回的字符串去掉XML头
     *
     * @param datas 源字符串
     * @return 结果字符串（已去掉了XML头）
     */
    public String parseXML(String datas) {
        XmlPullParserFactory factory;
        String result = "";
        try {
            factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(new StringReader(datas));
            int eventType = parser.getEventType();

            while (eventType != XmlPullParser.END_DOCUMENT) {
                String nodeName = parser.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if ("string".equals(nodeName)) {
                            result = parser.nextText();
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        break;
                    default:
                        break;
                }
                eventType = parser.next();
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public interface OnNextListener {
        void onNext(String result);

        void onError();
    }
}

