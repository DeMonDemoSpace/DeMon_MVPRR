package com.demon.mvprr.model;

import android.app.ProgressDialog;
import android.content.Context;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author DeMon
 * @date 2019/7/19
 * @email 757454343@qq.com
 * @description 封装Observer过程
 */
public abstract class BaseObserver<T> implements Observer<T>, DialogHandler.CancelListener {
    private static final String TAG = "BaseObserver";
    private DialogHandler mDialogHandler;
    private Disposable d;
    private Context mContext;
    private boolean isShow = true;
    private ProgressDialog dialog;

    public BaseObserver(Context context) {
        mContext = context;
    }

    public BaseObserver(Context context, boolean isShow) {
        mContext = context;
        this.isShow = isShow;
    }


    @Override
    public void onSubscribe(Disposable d) {
        this.d = d;
        showProgressDialog();
    }

    @Override
    public void onNext(T t) {
        onNextResult(t);
    }

    @Override
    public void onError(Throwable e) {
        onErrorResult(e);
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

    protected abstract void onNextResult(T t);

    protected abstract void onErrorResult(Throwable e);


    public ProgressDialog initDialog(Context context) {
        return new ProgressDialog(context);
    }

    private void showProgressDialog() {
        if (dialog == null) {
            dialog = initDialog(mContext);
        }
        if (mDialogHandler == null) {
            mDialogHandler = new DialogHandler(mContext, dialog, this);
        }

        if (isShow) {
            mDialogHandler.obtainMessage(DialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog() {
        if (mDialogHandler != null && isShow) {
            mDialogHandler.obtainMessage(DialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mDialogHandler = null;
        }
    }


}
