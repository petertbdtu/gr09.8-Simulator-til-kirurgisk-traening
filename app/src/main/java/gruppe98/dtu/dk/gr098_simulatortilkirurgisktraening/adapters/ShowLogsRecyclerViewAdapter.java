package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.adapters;

import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.R;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.activities.ShowLogsActivity;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.dal.LogEntry;

public class ShowLogsRecyclerViewAdapter extends RecyclerView.Adapter<ShowLogsRecyclerViewAdapter.ViewHolder> {

    private ArrayList<LogEntry> listLogElementer;
    private ShowLogsActivity context;

    public ShowLogsRecyclerViewAdapter(ArrayList<LogEntry> listLogElementer, ShowLogsActivity context) {
        this.listLogElementer = listLogElementer;
        this.context = context;
    }

    @NonNull
    @Override
    public ShowLogsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.log_entry_layout, parent, false);
        return new ShowLogsRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LogEntry logElement = listLogElementer.get(position);

       // Calendar c = Calendar.getInstance();
        //c.setTimeInMillis(logElement.getStart());
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        holder.startTid.setText(format1.format(logElement.getStart()));
        holder.scenarie_navn.setText(logElement.getLoggedScenario().getName());

       // c.setTimeInMillis(logElement.getCompleted());
        holder.slutTid.setText(format1.format(logElement.getStart()));
        holder.resultat.setText(logElement.getOutcome().name());


        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Åben dialog til redigering af comment.
            }

        });

        holder.visScenarie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //vis scenarie i fragmentcontainer, går tilbage til liste ved click

            }
        });
    }

    @Override
    public int getItemCount() {
        return listLogElementer.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView scenarie_navn;
        TextView resultat;
        TextView startTid;
        TextView slutTid;
        Button comment;
        Button visScenarie;
        ConstraintLayout liste_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            //TextViews
            scenarie_navn = itemView.findViewById(R.id.scenarioName);
            resultat = itemView.findViewById(R.id.outcome);
            startTid = itemView.findViewById(R.id.scenarioStartTime);
            slutTid = itemView.findViewById(R.id.scenarioCompletionTime);


            //Buttons
            comment = itemView.findViewById(R.id.comment);
            visScenarie = itemView.findViewById(R.id.showScenarioButton);

            //Layout
            liste_layout = itemView.findViewById(R.id.logListElement);


        }
    }
}