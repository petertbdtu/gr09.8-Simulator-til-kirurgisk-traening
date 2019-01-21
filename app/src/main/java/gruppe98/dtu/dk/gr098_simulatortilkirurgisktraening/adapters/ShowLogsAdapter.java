package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.R;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.objects.LogEntry;

public class ShowLogsAdapter extends RecyclerView.Adapter<ShowLogsAdapter.ViewHolder> {

    private ArrayList<LogEntry> listLogElementer;
    private Context context;

    public ShowLogsAdapter(ArrayList<LogEntry> listLogElementer, Context context) {
        this.listLogElementer = listLogElementer;
        this.context = context;
    }

    public void updateData(ArrayList<LogEntry> listLogElementer) {
        this.listLogElementer = listLogElementer;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.adapter_log_entry, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        holder.startTid.setText(format1.format(listLogElementer.get(position).getStart()));
        holder.scenarie_navn.setText(listLogElementer.get(position).getScenarioNavn());
    }

    @Override
    public int getItemCount() {
        return listLogElementer.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView scenarie_navn;
        TextView startTid;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            scenarie_navn = itemView.findViewById(R.id.scenarioName);
            startTid = itemView.findViewById(R.id.scenarioStartTime);
        }
    }
}
