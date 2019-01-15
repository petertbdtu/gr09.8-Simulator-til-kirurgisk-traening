package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.adapters;

import android.content.Context;
import android.net.wifi.p2p.WifiP2pDevice;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.R;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.interfaces.IRecycleViewAdapterListener;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.views.LEDView;

public class PickTabletAdapter extends RecyclerView.Adapter<PickTabletAdapter.ViewHolder> {
    private static final String TAG = "tabletRecyclerViewAdapter";

    private ArrayList<WifiP2pDevice> listDevices;
    private Context context;


    public PickTabletAdapter(ArrayList<WifiP2pDevice> listDevices, Context context) {
        this.listDevices = listDevices;
        this.context = context;
    }

    public void updateData(ArrayList<WifiP2pDevice> listDevices) {
        this.listDevices = listDevices;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_vaelg_tablet, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PickTabletAdapter.ViewHolder viewHolder, int position) {


        viewHolder.tablet_liste_element_ID.setText(listDevices.get(position).deviceName);
        viewHolder.tablet_liste_element_valgBrugs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((IRecycleViewAdapterListener)context).VaelgBrugsscenarie(listDevices.get(viewHolder.getAdapterPosition()).deviceAddress);
            }
        });
        viewHolder.tablet_liste_element_selog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((IRecycleViewAdapterListener)context).SeLog(listDevices.get(viewHolder.getAdapterPosition()).deviceAddress);
            }
        });
        viewHolder.tablet_liste_element_slet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listDevices.remove(viewHolder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listDevices.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        LEDView tablet_liste_element_LED;
        TextView tablet_liste_element_ID;
        Button tablet_liste_element_valgBrugs;
        Button tablet_liste_element_selog;
        Button tablet_liste_element_slet;

        public ViewHolder(View itemView) {
            super(itemView);
            tablet_liste_element_LED = itemView.findViewById(R.id.tablet_liste_element_LED);
            tablet_liste_element_ID = itemView.findViewById(R.id.tablet_liste_element_ID);
            tablet_liste_element_valgBrugs = itemView.findViewById(R.id.tablet_liste_element_valgBrugs);
            tablet_liste_element_selog = itemView.findViewById(R.id.tablet_liste_element_selog);
            tablet_liste_element_slet = itemView.findViewById(R.id.tablet_liste_element_slet);

        }
    }
}

