package example.git.com.gitintegration;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class splash_screen extends AppCompatActivity {
   sharedprefen shared;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(android.R.style.Theme_Holo_Light_NoActionBar);
        setContentView(R.layout.activity_splash_screen);
        shared=new sharedprefen(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
        new Thread(new Runnable() {
            public void run() {
                Boolean valid=(shared.getvalue(sharedprefen.USERNAME)==null)?true:false;

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(valid)
                startActivity(new Intent(splash_screen.this,Login.class));
                else
                    startActivity(new Intent(splash_screen.this,MainActivity.class));

                finish();
            }
        }).start();

    }
}
