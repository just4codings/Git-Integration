package example.git.com.gitintegration.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alorma.github.sdk.bean.dto.response.Issue;
import com.alorma.github.sdk.bean.info.IssueInfo;
import com.alorma.github.sdk.bean.info.RepoInfo;
import com.alorma.github.sdk.exception.ApiException;
import com.alorma.github.sdk.services.issues.GetIssuesClient;
import com.alorma.gitskarios.core.Pair;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.List;

import example.git.com.gitintegration.Adapters.IssueAdapter;
import example.git.com.gitintegration.Adapters.ReposAdapter;
import example.git.com.gitintegration.Commonclass;
import example.git.com.gitintegration.MainActivity;
import example.git.com.gitintegration.R;
import example.git.com.gitintegration.sharedprefen;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;


public class issues extends Fragment {
    View view;
    RecyclerView Reclycer_issue;
    Context context;
    String Reponame=null;
    private static final String ARG_PARAM1 = "param1";
    Gson gson;
    IssueAdapter issueapater;
    sharedprefen shared;
    RelativeLayout R_loading_issue;
    TextView noissueTxt;
    Commonclass common;

    public issues() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static issues newInstance(String Reponame) {
        issues fragment = new issues();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, Reponame);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context contxt) {
        super.onAttach(context);
        context=contxt;
      //  setUserVisibleHint(true);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
       // MainActivity.VisibleFragment="issues";
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gson=new Gson();
        common=new Commonclass(getActivity());
        shared=new sharedprefen(getActivity());
        if (getArguments() != null) {
            Reponame = getArguments().getString(ARG_PARAM1);

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_issues, container, false);
        Reclycer_issue= (RecyclerView) view.findViewById(R.id.Reclycer_issue);
        R_loading_issue= (RelativeLayout) view.findViewById(R.id.R_Loading);
        noissueTxt=(TextView)view.findViewById(R.id.noissueTxt);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Reclycer_issue.setHasFixedSize(true);
        Reclycer_issue.setLayoutManager(new LinearLayoutManager(getActivity()));
        if(Reponame!=null){
            Getissues();
        }


    }



    // TODO: Rename method, update argument and hook method into UI event




    @Override
    public void onDetach() {
        super.onDetach();
    }


    private void Getissues() {
        R_loading_issue.setVisibility(View.VISIBLE);
        RepoInfo repoInfo = new RepoInfo();
        repoInfo.owner = shared.getvalue(sharedprefen.USERNAME);
        repoInfo.branch = "master";
        repoInfo.name=Reponame;


        IssueInfo issueInfo = new IssueInfo();
        issueInfo.repoInfo = repoInfo;
        HashMap<String, String> bb = new HashMap<>();
        bb.put("all", "assigned");


        new GetIssuesClient(issueInfo, bb).observable().observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new Action1<Pair<List<Issue>, Integer>>() {
                    @Override
                    public void call(Pair<List<Issue>, Integer> listIntegerPair) {
                        R_loading_issue.setVisibility(View.GONE);
                        if (listIntegerPair != null) {
                            List<Issue> repos = listIntegerPair.first;
                            if(repos!=null&&repos.size()>0) {
                                String issues = gson.toJson(repos);
                                System.out.println("+issue+" + issues);
                                issueapater = new IssueAdapter(repos, getActivity(), "dy");
                                Reclycer_issue.setAdapter(issueapater);
                            }else {
                                R_loading_issue.setVisibility(View.GONE);
                                noissueTxt.setVisibility(View.VISIBLE);

                                Toast.makeText(getActivity(),"No issue's presented",Toast.LENGTH_SHORT).show();
                            }


                        }

                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        if (throwable instanceof ApiException) {
                            System.out.println("APIEXcpetion+" + throwable.toString());
                        } else {
                            System.out.println("Issues+" + throwable.toString());
                        }

                       // Getcommits();
                    }
                });

        /**
         * This interface must be implemented by activities that contain this
         * fragment to allow an interaction in this fragment to be communicated
         * to the activity and potentially other fragments contained in that
         * activity.
         * <p>
         * See the Android Training lesson <a href=
         * "http://developer.android.com/training/basics/fragments/communicating.html"
         * >Communicating with Other Fragments</a> for more information.
         */

}
}
