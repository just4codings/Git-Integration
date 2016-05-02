package example.git.com.gitintegration;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.TabLayout;



import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;


import example.git.com.gitintegration.Fragments.issues;

public class Repos_details extends AppCompatActivity {


    TabLayout tabLayout;
     ViewPager pager;
    int Numboftabs = 3;
    FragmentManager manger;
    FragmentTransaction transaction;
    FrameLayout container;
    String Reponame=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repos_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar_repo_details);
        setSupportActionBar(toolbar);
        tabLayout= (TabLayout) findViewById(R.id.tab_layout);
        container= (FrameLayout) findViewById(R.id.container);
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.tabissueTxt)));


        tabLayout.setTabTextColors(getResources().getColor(R.color.tabunselect), getResources().getColor(R.color.black));

        Reponame=getIntent().getExtras().getString("Reponame");
       manger=getFragmentManager();

        transaction = manger.beginTransaction();

       transaction.replace(R.id.container, issues.newInstance(Reponame)).commit();
        // adding functionality to tab and viewpager to manage each other when a page is changed or when a tab is selected
       // pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:

                        transaction = manger.beginTransaction();

                        transaction.replace(R.id.container, issues.newInstance(Reponame)).commit();



                        break;

                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

}
