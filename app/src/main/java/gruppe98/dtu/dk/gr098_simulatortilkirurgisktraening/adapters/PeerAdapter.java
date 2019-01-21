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
        View rootView = LayoutInflater.from(context).inflate(R.layout.adapter_peer, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        rootView.setLayoutParams(lp);
        return new PeerViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {
        PeerViewHolder PVH = (PeerViewHolder) viewHolder;
        PVH.btnForbind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((IRecycleViewAdapterListener)context).PeerChosen(listWPD.get(viewHolder.getAdapterPosition()), false);
            }
        });
        PVH.tvName.setText(listWPD.get(i).deviceName);
    }

    @Override
    public int getItemCount() {
        return listWPD.size();
    }

    private class PeerViewHolder extends RecyclerView.ViewHolder {

        Button btnForbind;
        TextView tvName;

        public PeerViewHolder(View itemView) {
            super(itemView);
            btnForbind = itemView.findViewById(R.id.btnForbind);
            tvName = itemView.findViewById(R.id.tvName);
        }
    }
}
