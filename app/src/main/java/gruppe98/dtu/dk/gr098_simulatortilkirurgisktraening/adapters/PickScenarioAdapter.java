package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.adapters;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.R;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.application.ApplicationSingleton;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.interfaces.IRecycleViewAdapterListener;

public class PickScenarioAdapter extends RecyclerView.Adapter<PickScenarioAdapter.ViewHolder> {

        private ArrayList<String> list_scenarie_navne;
        private Context context;

    public PickScenarioAdapter(Context context){
        this.context = context;
        hentScenarier();
    }

    private void hentScenarier() {
        list_scenarie_navne = new ArrayList<>(ApplicationSingleton.getInstance().hentAlleScenarier().keySet());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = LayoutInflater.from(context).inflate(R.layout.adapter_vaelg_scenarie, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
        viewHolder.scenarie_navn.setText(list_scenarie_navne.get(i));
        viewHolder.rediger.setOnClickListener(
            new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((IRecycleViewAdapterListener)context).redigerScenarie(list_scenarie_navne.get(viewHolder.getAdapterPosition()));
                }
            }
        );
        viewHolder.slet.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String path = ApplicationSingleton.getInstance().fjernScenarie(list_scenarie_navne.get(viewHolder.getAdapterPosition()));
                        MediaScannerConnection.scanFile(context, new String[]{path}, null, null);
                        hentScenarier();
                        notifyDataSetChanged();
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return list_scenarie_navne.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView scenarie_navn;
        ImageView rediger;
        ImageView  slet;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            scenarie_navn = itemView.findViewById(R.id.scenarie_navn);
            rediger = itemView.findViewById(R.id.knap_rediger_scenarie);
            slet = itemView.findViewById(R.id.knap_slet_scenarie);


        }
    }
}
