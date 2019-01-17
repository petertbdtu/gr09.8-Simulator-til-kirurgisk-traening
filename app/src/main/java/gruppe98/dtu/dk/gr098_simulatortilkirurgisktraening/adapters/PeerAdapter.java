package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.adapters;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.net.wifi.p2p.WifiP2pDevice;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.R;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.interfaces.IRecycleViewAdapterListener;

public class PeerAdapter extends RecyclerView.Adapter {

    List<WifiP2pDevice> listWPD;
    Context context;

    public PeerAdapter(Context context, List<WifiP2pDevice> listWPD){
        this.context = context;
        this.listWPD = listWPD;
    }

    public void updateAdapterData(List<WifiP2pDevice> listWPD) {
        this.listWPD = listWPD;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        return new PeerViewHolder(inflater.inflate(R.layout.adapter_peer,null));
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {
        PeerViewHolder PVH = (PeerViewHolder) viewHolder;
        PVH.ivChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((IRecycleViewAdapterListener)context).PeerChosen(listWPD.get(viewHolder.getAdapterPosition()));
            }
        });
        PVH.tvName.setText(listWPD.get(i).deviceName);
        switch (listWPD.get(i).status) {
            case WifiP2pDevice.CONNECTED:
                PVH.tvAddress.setText("CONNECTED");
                break;
            case WifiP2pDevice.INVITED:
                PVH.tvAddress.setText("INVITED");
                break;
            case WifiP2pDevice.FAILED:
                PVH.tvAddress.setText("FAILED");
                break;
            case WifiP2pDevice.AVAILABLE:
                PVH.tvAddress.setText("AVAILABLE");
                break;
            case WifiP2pDevice.UNAVAILABLE:
                PVH.tvAddress.setText("UNAVAILABLE");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return listWPD.size();
    }

    private class PeerViewHolder extends RecyclerView.ViewHolder {

        ImageView ivChoose;
        TextView tvName;
        TextView tvAddress;

        public PeerViewHolder(View itemView) {
            super(itemView);
            ivChoose = itemView.findViewById(R.id.ivChoose);
            tvName = itemView.findViewById(R.id.tvName);
            tvAddress = itemView.findViewById(R.id.tvAddress);
        }
    }
}
