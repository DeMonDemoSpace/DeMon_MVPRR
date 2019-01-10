package com.demon.mvprr.model;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;


/**
 * Created by DeMon on 2017/9/6.
 */
public class DialogHandler extends Handler {
    public static final int SHOW_PROGRESS_DIALOG = 1;
    public static final int DISMISS_PROGRESS_DIALOG = 2;
    private ProgressDialog pd;
    private Context context;
    private CancelListener mCancelListener;

    public DialogHandler(Context context, CancelListener mCancelListener) {
        super();
        this.context = context;
        this.mCancelListener = mCancelListener;
    }

    public DialogHandler(Context context, ProgressDialog dialog, CancelListener mCancelListener) {
        super();
        this.context = context;
        this.mCancelListener = mCancelListener;
        this.pd = dialog;
    }

    private void initProgressDialog() {
        if (pd == null) {
            pd = new ProgressDialog(context);
            pd.setMessage("数据加载中...");
            pd.setCancelable(true);
        }
        pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                mCancelListener.onCancelProgress();
            }
        });
        if (!pd.isShowing() && context != null) {
            if (context instanceof Activity) {
                if (!((Activity) context).isFinishing() && !((Activity) context).isDestroyed()) {
                    pd.show();
                }
            } else {
                pd.show();
            }

        }
    }

    private void dismissProgressDialog() {
        if (pd != null) {
            pd.dismiss();
            pd = null;
        }
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_PROGRESS_DIALOG:
                initProgressDialog();
                break;
            case DISMISS_PROGRESS_DIALOG:
                dismissProgressDialog();
                break;
        }
    }


    public interface CancelListener {
        void onCancelProgress();
    }
}
