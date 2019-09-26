package com.demo.okhttp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.demo.okhttp.model.LoginModel;
import com.demo.okhttp.retrofit.BaseObserver;
import com.demo.okhttp.retrofit.BaseResultEntity;
import com.demo.okhttp.retrofit.RetrofitManager;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity {

    public String userName="c_melo", password="11111111";
    private TextView textView;
    private Button btLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

    }

    private void initView() {

        textView = findViewById(R.id.tv_message);
        btLogin = findViewById(R.id.bt_login);
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                login();

            }
        });
    }

    private void login() {

        RetrofitManager retrofitManager = RetrofitManager.getInstance();
        Observable<ResponseBody> responseBodyObservable = retrofitManager.getDataMethord()
                .login(userName, password, "ZH");
        retrofitManager.getObservable(responseBodyObservable, LoginModel.class)


                .subscribe(new BaseObserver<LoginModel>(this, "登录中",true) {

            @Override
            public void onHandleSuccess(LoginModel loginModel) {
                super.onHandleSuccess(loginModel);

                textView.setText(loginModel.toString());

            }

            @Override
            public void onHandleError(int code, String message) {
                super.onHandleError(code, message);
                switch (code) {
                    case 125:
                        Toast.makeText(MainActivity.this, "用户名错误", Toast.LENGTH_SHORT).show();
                        break;
                    case 130:
                        Toast.makeText(MainActivity.this, "密码错误", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();

                        break;
                }

            }
        });
    }

    private void getDate() {


        RetrofitManager retrofitManager = RetrofitManager.getInstance();
        Observable<ResponseBody> responseBodyObservable = retrofitManager.getDataMethord().signOut();
        retrofitManager.getObservable(responseBodyObservable).subscribe(new BaseObserver(this, "登录中", true) {

            @Override
            public void onHandleSuccess(BaseResultEntity baseResultEntity) {
                super.onHandleSuccess(baseResultEntity);

            }

            @Override
            public void onHandleError(int code, String message) {
                super.onHandleError(code, message);
               Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
