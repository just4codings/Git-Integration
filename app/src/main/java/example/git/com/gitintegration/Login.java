package example.git.com.gitintegration;

import
        android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.alorma.github.sdk.bean.dto.response.User;
import com.alorma.github.sdk.services.user.GetAuthUserClient;
import com.alorma.github.sdk.services.user.TwoFactorAuthException;
import com.alorma.github.sdk.services.user.UnauthorizedException;
import com.google.gson.Gson;


import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class Login extends AppCompatActivity implements View.OnClickListener {


    EditText etusername,pwd;
    Button btnguest;

    Gson gson;
    sharedprefen shared;
    public String Tag="LoginScreen";
    FrameLayout progressBarHolder;
    AlphaAnimation inAnimation;
    AlphaAnimation outAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //ButterKnife.bind(this);
        shared=new sharedprefen(Login.this);
        etusername= (EditText) findViewById(R.id.etusername);
        progressBarHolder = (FrameLayout) findViewById(R.id.progressBarHolder);
        pwd= (EditText) findViewById(R.id.pwd);
        btnguest= (Button) findViewById(R.id.btnguest);
        btnguest.setOnClickListener(this);
        gson=new Gson();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnguest:
                if(Commonclass.isEmptyString(etusername.getText().toString())){
                    etusername.setError(getResources().getString(R.string.ed_login_user));
                } else if(Commonclass.isEmptyString(pwd.getText().toString())){
                    pwd.setError(getResources().getString(R.string.ed_login_pass));
                }else {
                    inAnimation = new AlphaAnimation(0f, 1f);
                    inAnimation.setDuration(200);
                    progressBarHolder.setAnimation(inAnimation);
                    progressBarHolder.setVisibility(View.VISIBLE);
                    new GetAuthUserClient(etusername.getText().toString(),pwd.getText().toString()).observable()
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.newThread())
                            .subscribe(new Action1<User>() {
                                @Override
                                public void call(User user) {

                                    shared.save(sharedprefen.USERNAME,etusername.getText().toString());
                                    String userde=gson.toJson(user);
                                    shared.save(sharedprefen.USER_DE,userde);
                                    Log.e(Tag, user.toString());
                                    git_singleton.getInstance().setUserdetails(user);
                                    hideLoading();
                                    startActivity(new Intent(Login.this, MainActivity.class));
                                    finish();

                                }
                            }, new Action1<Throwable>() {
                                @Override
                                public void call(Throwable throwable) {
                                    if (throwable instanceof UnauthorizedException) {
                                       Toast.makeText(Login.this, "incorrect credentials", Toast.LENGTH_SHORT).show();
                                    } else if (throwable instanceof TwoFactorAuthException) {

                                    } else {
                                        Log.e(Tag, throwable.getMessage());
                                    }
                                    hideLoading();
                                }
                            });
                }
                break;
        }
    }
    public void hideLoading(){

        outAnimation = new AlphaAnimation(1f, 0f);
        outAnimation.setDuration(200);
        progressBarHolder.setAnimation(outAnimation);
        progressBarHolder.setVisibility(View.GONE);
    }
}
