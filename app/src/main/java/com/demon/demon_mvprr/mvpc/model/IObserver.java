package com.demon.demon_mvprr.mvpc.model;

import android.app.ProgressDialog;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.demon.mvprr.model.DialogHandler;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class IObserver implements Observer<String>, DialogHandler.CancelListener {
    private static final String TAG = "BaseObserver";
    private OnNextListener listener;
    private DialogHandler mDialogHandler;
    private Disposable d;
    private Context mContext;
    private boolean isShow = true;

    public IObserver(Context context, OnNextListener listener) {
        mContext = context;
        this.listener = listener;
        ProgressDialog dialog = new ProgressDialog(mContext);
        mDialogHandler = new DialogHandler(mContext, dialog, this);
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
        if (!TextUtils.isEmpty(s)) {
            listener.onNext(s);
        }
    }

    @Override
    public void onError(Throwable e) {
        if (!TextUtils.isEmpty(e.getMessage())) {
            if (e.getMessage().startsWith("Failed")) {
                Log.i(TAG, "onError:设备网络未连接！！！");
            }

            if (e.getMessage().startsWith("failed")) {
                Log.i(TAG, "服务器未响应！！！");
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


    public interface OnNextListener {
        void onNext(String result);

        void onError();
    }
}

