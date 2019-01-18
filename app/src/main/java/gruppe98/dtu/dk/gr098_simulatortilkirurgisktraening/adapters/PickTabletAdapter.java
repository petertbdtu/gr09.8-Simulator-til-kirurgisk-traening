package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.R;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.interfaces.IRecycleViewAdapterListener;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.views.LEDView;

public class PickTabletAdapter extends RecyclerView.Adapter<PickTabletAdapter.ViewHolder> {
    private static final String TAG = "tabletRecyclerViewAdapter";

    private HashMap<String,String> mapDevices;
    private HashMap<String, Boolean> mapLEDStatus;
    private ArrayList<String> listAddresses;
    private Context context;


    public PickTabletAdapter(HashMap<String, String> mapDevices, Context context) {
        this.mapDevices = mapDevices;
        this.context = context;
        this.listAddresses = new ArrayList<>(mapDevices.keySet());
        this.mapLEDStatus = new HashMap<>();

        for(String str : listAddresses) {
            mapLEDStatus.put(str,false);
        }
    }

    public void updateData(HashMap<String, String> mapDevices) {
        this.mapDevices = mapDevices;
        this.listAddresses = new ArrayList<>(mapDevices.keySet());
        for(String str : listAddresses) {
            if(!mapLEDStatus.containsKey(str))
                mapLEDStatus.put(str,false);
        }
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

        viewHolder.tablet_liste_element_LED.setErTaendt(mapLEDStatus.get(listAddresses.get(position)));

        viewHolder.tablet_liste_element_ID.setText(mapDevices.get(listAddresses.get(position)));
        viewHolder.tablet_liste_element_valgBrugs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((IRecycleViewAdapterListener)context).VaelgBrugsscenarie(listAddresses.get(viewHolder.getAdapterPosition()));
            }
        });
        viewHolder.tablet_liste_element_selog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((IRecycleViewAdapterListener)context).SeLog(listAddresses.get(viewHolder.getAdapterPosition()));
            }
        });
        viewHolder.tablet_liste_element_slet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((IRecycleViewAdapterListener)context).deleteDevice(listAddresses.get(viewHolder.getAdapterPosition()));

                listAddresses.remove(viewHolder.getAdapterPosition());
                mapDevices.remove(listAddresses.get(viewHolder.getAdapterPosition()));

                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listAddresses.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        LEDView tablet_liste_element_LED;
        TextView tablet_liste_element_ID;
        Button tablet_liste_element_valgBrugs;
        Button tablet_liste_element_selog;
        Button tablet_liste_element_slet;

        public ViewHolder(View itemView) {
            super(itemView);

            Paint colour = new Paint();
            colour.setARGB(255, 0, 255, 0);

            tablet_liste_element_LED = itemView.findViewById(R.id.tablet_liste_element_LED);
            tablet_liste_element_LED.setTaendtFarve(colour);
            tablet_liste_element_ID = itemView.findViewById(R.id.tablet_liste_element_ID);
            tablet_liste_element_valgBrugs = itemView.findViewById(R.id.tablet_liste_element_valgBrugs);
            tablet_liste_element_selog = itemView.findViewById(R.id.tablet_liste_element_selog);
            tablet_liste_element_slet = itemView.findViewById(R.id.tablet_liste_element_slet);

        }
    }

    public void updateLEDs(List<WifiP2pDevice> list){

        for(String str : listAddresses) {
            mapLEDStatus.put(str,false);
        }

        for(WifiP2pDevice wif : list){
            if(mapLEDStatus.containsKey(wif.deviceAddress)){
                mapLEDStatus.put(wif.deviceAddress,wif.status == WifiP2pDevice.CONNECTED);
            }
        }

        notifyDataSetChanged();
    }
}

