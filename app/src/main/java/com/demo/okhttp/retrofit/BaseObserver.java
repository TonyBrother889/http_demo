package com.demo.okhttp.retrofit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;


import com.demo.okhttp.LoginActivity;
import com.demo.okhttp.R;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONException;

import java.lang.ref.WeakReference;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;

public abstract class BaseObserver<T> implements Observer<BaseResultEntity> {

    private Disposable mDisposable;
    private final int SUCCESS_CODE = 800;
    private String mTitle;
    private boolean show;
    private boolean isCancelDialog = true;
    private KProgressHUD dialog;
    private Activity activity;
    private static final int NO_LOGIN = 100;
    private static final int SERVER_ERROR = -1;
    private WeakReference<Activity> activityWeakReference;

    @Override
    public void onSubscribe(Disposable d) {
        mDisposable = d;
        if (show) {
            showProgressDialog();
        }
    }

    /**
     * @param activity 上下文
     * @param title    dialog上面的标题
     * @param show     加载数据是否显示dialog
     */

    public BaseObserver(Activity activity, String title, boolean show) {
        super();
        this.activity = activity;
        activityWeakReference = new WeakReference(activity);
        this.mTitle = title;
        this.show = show;
        initProgressDialog(mTitle);
    }

    /**
     * @param activity       上下文
     * @param title          dialog上面的标题
     * @param show           加载数据是否显示dialog
     * @param isCancelDialog 是否能够按返回键取消dialog
     */

    public BaseObserver(Activity activity, String title, boolean show, boolean isCancelDialog) {
        super();
        this.activity = activity;
        activityWeakReference = new WeakReference(activity);
        this.mTitle = title;
        this.show = show;
        this.isCancelDialog = isCancelDialog;
        initProgressDialog(mTitle);
    }


    public Activity getActivity() {

        return isEmptyActivity() ? activityWeakReference.get() : null;
    }

    /**
     * 存在activity
     */
    protected boolean isEmptyActivity() {

        return activityWeakReference != null && activityWeakReference.get() != null;
    }

    /**
     * 清空activity
     */
    public void cleanActivity() {
        if (isEmptyActivity()) {
            activityWeakReference.clear();
            activityWeakReference = null;
        }
    }

    /**
     * 初始化加载框
     */
    private void initProgressDialog(String title) {
        Activity activity = getActivity();
        if (dialog == null && activity != null) {
            dialog = KProgressHUD.create(activity);
            dialog.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel(title)
                    .setCancellable(false)
                    .setAnimationSpeed(2)
                    .setDimAmount(0.5f);
        }
    }

    /**
     * 显示加载框
     */
    private void showProgressDialog() {
        Context context = getActivity();
        if (dialog == null || context == null) return;
        if (!dialog.isShowing()) {
            dialog.show();
        }
    }

    /**
     * 隐藏
     */
    private void dismissProgressDialog() {
        Context context = getActivity();
        if (dialog != null && dialog.isShowing() && context != null) {
            try {
                dialog.dismiss();
            } catch (Exception e) {
            }
            onCancelProgress();
        }
    }


    @Override
    public void onNext(BaseResultEntity value) {
        /***服务器有返回值 将全局变量设置为true**/
        if (value.getCode() == SUCCESS_CODE) {
            T val = (T) value.getData();
            onHandleSuccess(val);
            onHandleSuccess(value);
        } else {
            onHandleError(value.getCode(), value.getMsg());
        }
    }


    @Override
    public void onError(Throwable e) {

        if (e instanceof SocketTimeoutException) {
            Toast.makeText(getActivity(), getActivity().getString(R.string.connectTimeOut) + "-1", Toast.LENGTH_SHORT);
        } else if (e instanceof ConnectException) {
            Toast.makeText(getActivity(), getActivity().getString(R.string.serverBreak) + "-2", Toast.LENGTH_SHORT);
        } else if (e instanceof HttpException) {
            Toast.makeText(getActivity(), getActivity().getString(R.string.serverBreak) + "-3", Toast.LENGTH_SHORT);
        } else if (e instanceof UnknownHostException) {
            Toast.makeText(getActivity(), getActivity().getString(R.string.serverBreak) + "-2", Toast.LENGTH_SHORT);
        } else if (e instanceof JSONException) {
            Toast.makeText(getActivity(), getActivity().getString(R.string.dataError) + "-4", Toast.LENGTH_SHORT);
        } else {
            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT);
        }
        dismissProgressDialog();
    }

    @Override
    public void onComplete() {
        if (show) {
            dismissProgressDialog();
        }
        cleanActivity();
    }

    /**
     * 取消ProgressDialog的时候，取消对observable的订阅，同时也取消了http请求
     */
    public void onCancelProgress() {
        if (!mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    protected void onHandleSuccess(BaseResultEntity baseResultEntity) {

    }

    protected void onHandleSuccess(T t) {

    }

    protected void onHandleError(int code, String message) {
        switch (code) {
            /**未登录*/
            case NO_LOGIN:
                Intent intent = new Intent(activity, LoginActivity.class);
                activity.startActivity(intent);
                break;
            case SERVER_ERROR:
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
                break;
            default:
                Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT);
                break;
        }
    }
}
