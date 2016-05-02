package example.git.com.gitintegration;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alorma.github.sdk.bean.dto.response.Commit;
import com.alorma.github.sdk.bean.dto.response.Issue;
import com.alorma.github.sdk.bean.dto.response.Repo;
import com.alorma.github.sdk.bean.dto.response.User;
import com.alorma.github.sdk.bean.info.CommitInfo;
import com.alorma.github.sdk.bean.info.IssueInfo;
import com.alorma.github.sdk.bean.info.RepoInfo;
import com.alorma.github.sdk.exception.ApiException;
import com.alorma.github.sdk.services.commit.ListCommitsClient;
import com.alorma.github.sdk.services.issues.GetIssuesClient;
import com.alorma.github.sdk.services.repos.GithubReposClient;
import com.alorma.github.sdk.services.repos.UserReposClient;
import com.alorma.gitskarios.core.Pair;
import com.alorma.gitskarios.core.client.TokenProvider;
import com.google.gson.Gson;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;
import example.git.com.gitintegration.Adapters.ReposAdapter;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    TokenProvider tokenProvider;
    String token;
    GithubReposClient client;
   List<Repo> repos = new ArrayList<>();
    Toolbar toolBar;
    LinearLayout toollinear;
    RecyclerView Reclycer;
    User userdetails;
    Gson gson;
    ReposAdapter adpater;
    sharedprefen shared;
    Commonclass common;
    RelativeLayout RLoading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        common=new Commonclass(this);
          Reclycer= (RecyclerView) findViewById(R.id.Reclycer);
        toolBar= (Toolbar) findViewById(R.id.toolBar);
        gson=new Gson();
        shared=new sharedprefen(this);
        assert getSupportActionBar() != null;
        setSupportActionBar(toolBar);
        RLoading= (RelativeLayout) findViewById(R.id.R_Loading);
        toolBar.setTitleTextColor(ContextCompat.getColor(this, R.color.darkgray));
        Reclycer.setHasFixedSize(true);
        Reclycer.setLayoutManager(new LinearLayoutManager(this));



           GetReposdetails();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.about:
                startActivity(new Intent(this,about.class));
                break;
            case R.id.logout:
                shared.clearpref();
                startActivity(new Intent(this,Login.class));
                finish();
                break;



        }
        return true;


    }

    private void GetReposdetails() {
        RLoading.setVisibility(View.VISIBLE);
        String userd=shared.getvalue(sharedprefen.USER_DE);
        System.out.println("++"+userd);
        userdetails=gson.fromJson(userd,User.class);

        System.out.println("++Mainactivity"+userdetails.login);
        client = new UserReposClient(userdetails.login, "full_name");


        client.observable().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.newThread())
                .subscribe(new Action1<Pair<List<Repo>, Integer>>() {
                    @Override
                    public void call(Pair<List<Repo>, Integer> listIntegerPair) {
                        RLoading.setVisibility(View.GONE);
                        System.out.println("+++++" + listIntegerPair.hashCode());

                        repos = listIntegerPair.first;
                        if(repos!=null&&repos.size()>0) {
                           // git_singleton.getInstance().setReposDetails(repos);
                             adpater = new ReposAdapter(repos, MainActivity.this,"dm");
                            Reclycer.setAdapter(adpater);
                        }


                    }
                });
    }






}




