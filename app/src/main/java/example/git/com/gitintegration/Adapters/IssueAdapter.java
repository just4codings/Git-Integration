package example.git.com.gitintegration.Adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alorma.github.sdk.bean.dto.response.Commit;
import com.alorma.github.sdk.bean.dto.response.Issue;
import com.ocpsoft.pretty.time.PrettyTime;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormatter;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;



import example.git.com.gitintegration.Commonclass;
import example.git.com.gitintegration.MainActivity;
import example.git.com.gitintegration.R;

/**
 * Created by Rajasudhakar on 01/05/16.
 */
public class IssueAdapter extends RecyclerView
        .Adapter<RecyclerView.ViewHolder> {
    Commonclass common;
    List<Issue> issue;

    View itemLayout;
    Activity Activi;
    LayoutInflater layoutInflater;
    public IssueAdapter(List<Issue> repos, Activity contt, String dy) {
        Activi=contt;
        common=new Commonclass(Activi);
        issue=repos;
        layoutInflater = (LayoutInflater) Activi.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        String mode=null;

            itemLayout =  LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.repos_item, parent, false);
            mode="MainActivity";

        return new ReposAdapter.MyViewHolder(itemLayout, viewType,mode,Activi);

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder hold, int position) {
        if (hold instanceof ReposAdapter.MyViewHolder) {
            ReposAdapter.MyViewHolder holder = (ReposAdapter.MyViewHolder) hold;
            if (!common.isEmptyString(issue.get(position).title)) {
                    holder.reponame.setText(issue.get(position).title);
                } else {
                    holder.reponame.setVisibility(View.GONE);
                }
                if (!common.isEmptyString(issue.get(position).body)) {
                    holder.descript.setText(issue.get(position).body);
                } else {
                    holder.descript.setVisibility(View.GONE);
                }
                if (issue.get(position).created_at != null) {
                    String createat = common.method(issue.get(position).created_at);
                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ENGLISH);
                    Date date = null;
                    try {
                        date = format.parse(createat);
                        PrettyTime prettyTime = new PrettyTime();
                        System.out.println("prettyTime" + prettyTime.format(date));
                        holder.updatedate.setText("opened " + prettyTime.format(date) + " by " + issue.get(position).user.login);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }else {
                    holder.updatedate.setVisibility(View.GONE);
                }
            }
        }


    @Override
    public int getItemCount() {

        return issue.size();
    }
}
