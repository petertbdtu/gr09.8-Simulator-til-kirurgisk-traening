package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.R;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.interfaces.IRecycleViewAdapterListener;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.objects.Scenario;

public class SendScenarioAdapter extends RecyclerView.Adapter<SendScenarioAdapter.ViewHolder> {

    private List<Scenario> sendScenarier;
    private List<String> sendScenarierNavne;
    private Context context;

    public SendScenarioAdapter(Map<String, Scenario> send_brugsscenarie, Context context) {
        sendScenarier = new ArrayList<Scenario>(send_brugsscenarie.values());
        sendScenarierNavne = new ArrayList<>(send_brugsscenarie.keySet());
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.adapter_send_scenarie, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder)holder;
        vh.addBrugs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((IRecycleViewAdapterListener)context).sendBrugsscenarie(sendScenarierNavne.get(holder.getAdapterPosition()),sendScenarier.get(holder.getAdapterPosition()));
            }
        });
        vh.navnSenarie.setText(sendScenarierNavne.get(position));
    }

    @Override
    public int getItemCount() {
        return sendScenarier.size();
    }

    public void updateData(Map<String, Scenario> brugsscenarier) {
        this.sendScenarierNavne = new ArrayList<>(brugsscenarier.keySet());
        this.sendScenarier = new ArrayList<>(brugsscenarier.values());
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView addBrugs;
        TextView navnSenarie;

        public ViewHolder(View itemView) {
            super(itemView);
            addBrugs = itemView.findViewById(R.id.addBrugs);
            navnSenarie = itemView.findViewById(R.id.navnSenarie);
        }
    }



    }

