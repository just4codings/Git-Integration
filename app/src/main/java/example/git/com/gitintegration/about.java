package example.git.com.gitintegration;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.alorma.github.sdk.bean.dto.response.User;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;


import com.ocpsoft.pretty.time.PrettyTime;

public class about extends AppCompatActivity {
    TextView date,follower,following,profiletxt;
    ImageView pro_imag;
    sharedprefen shared;
    Gson gson;
    User userdetails;
    Commonclass common;
    Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aboutscreen);

        toolBar= (Toolbar) findViewById(R.id.toolBar);
        assert getSupportActionBar() != null;
        setSupportActionBar(toolBar);
        date= (TextView) findViewById(R.id.date);
        follower= (TextView) findViewById(R.id.folowercount);
        following= (TextView) findViewById(R.id.folowingcount);
        profiletxt= (TextView) findViewById(R.id.txtprofilename);
        pro_imag= (ImageView) findViewById(R.id.profileImg);
        shared=new sharedprefen(this);
        gson=new Gson();
        common=new Commonclass(this);


        Set_layout();


    }

    private void Set_layout() {
        String userd=shared.getvalue(sharedprefen.USER_DE);
        System.out.println("++"+userd);
        userdetails=gson.fromJson(userd,User.class);

        //set image
        if(userdetails.avatar_url!=null){
            Glide.with(this).load(userdetails.avatar_url).fitCenter().into(pro_imag);


        }
        //set followers
        if(!Commonclass.isEmptyString(String.valueOf(userdetails.followers))){
            follower.setText(String.valueOf(userdetails.followers));
        }
        if(!Commonclass.isEmptyString(String.valueOf(userdetails.following))){
            following.setText(String.valueOf(userdetails.following));
        }
        if(userdetails.created_at!=null){
            PrettyTime prettyTime = new PrettyTime();

            date.setText(prettyTime.format(userdetails.created_at));

        }
        if(!Commonclass.isEmptyString(userdetails.login)){
            profiletxt.setText(userdetails.login);
        }




    }
}
