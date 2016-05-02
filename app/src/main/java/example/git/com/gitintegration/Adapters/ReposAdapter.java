package example.git.com.gitintegration.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alorma.github.sdk.bean.dto.response.Issue;
import com.alorma.github.sdk.bean.dto.response.Repo;



import com.ocpsoft.pretty.time.PrettyTime;

import java.util.List;

import example.git.com.gitintegration.Commonclass;
import example.git.com.gitintegration.MainActivity;
import example.git.com.gitintegration.R;
import example.git.com.gitintegration.Repos_details;

/**
 * Created by nnadmin on 30/4/16.
 */
public class ReposAdapter extends RecyclerView
.Adapter<RecyclerView.ViewHolder>  {
    List<Repo> repo;
    List<Issue> issues;
    Activity activity;
    Commonclass common;
    View itemLayout;

    LayoutInflater layoutInflater;


    public ReposAdapter(List<Repo> repos, Activity Activi, String dm) {
        common=new Commonclass(Activi);
        repo=repos;
        activity=Activi;
        layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


    }

    public ReposAdapter(List<Issue> issue, Activity context) {
        issues=issue;
        activity=context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        String mode=null;
        if(activity.getClass().getName().toString().contains("example.git.com.gitintegration.MainActivity")){
            itemLayout =  LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.repos_item, parent, false);
            mode="MainActivity";
        }

        return new MyViewHolder(itemLayout, viewType,mode,activity);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder hold, int position) {
        if (hold instanceof MyViewHolder) {
            MyViewHolder holder = (MyViewHolder) hold;
            if(!common.isEmptyString(repo.get(position).name)){
                holder.reponame.setText(repo.get(position).name);
            }else {
                holder.reponame.setVisibility(View.GONE);
            }

            if(!common.isEmptyString(repo.get(position).description)){
                holder.descript.setText(repo.get(position).description);
            }else {
                holder.descript.setVisibility(View.GONE);
            }
            if(repo.get(position).updated_at!=null){
                PrettyTime prettyTime=new PrettyTime();
                System.out.println("prettyTime"+prettyTime.format(repo.get(position).updated_at));
                holder.updatedate.setText("updated "+prettyTime.format(repo.get(position).updated_at));
            }
            holder.cardview.setTag(repo.get(position).name);
           holder.cardview.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   if(!Commonclass.isEmptyString((String)v.getTag())) {
                       activity.startActivity(new Intent(activity, Repos_details.class).putExtra("Reponame", (String) v.getTag()));
                   }else {
                       Toast.makeText(activity,"Sorry!,Couldn't get details without Repo Name",Toast.LENGTH_SHORT).show();
                   }
               }
           });


        }


    }

    @Override
    public int getItemCount() {
        int size=0;
        if(activity.getClass().getName().toString().contains("example.git.com.gitintegration.MainActivity")){
            size=repo.size();
        }
        return size;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView reponame,descript,updatedate;
        CardView cardview;

        public MyViewHolder(View itemLayout, int viewType, String p2, Activity p3) {
            super(itemLayout);
            if(p2.equals("MainActivity")){
                reponame= (TextView) itemLayout.findViewById(R.id.reponame);
                descript= (TextView) itemLayout.findViewById(R.id.Description);
                updatedate= (TextView) itemLayout.findViewById(R.id.updatedate);
                cardview= (CardView) itemLayout.findViewById(R.id.card_view);
            }
        }
    }
}
