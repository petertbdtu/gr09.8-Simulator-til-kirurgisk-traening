package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.adapters;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.R;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.interfaces.IRecycleViewAdapterListener;

import static gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.R.id.navnSenarie;

public class SendScenarieRecyclerViewAdapter extends RecyclerView.Adapter<SendScenarieRecyclerViewAdapter.ViewHolder> {

    private ArrayList<String> sendScenarier;
    private Context context;

    public SendScenarieRecyclerViewAdapter(ArrayList<String> send_brugsscenarie, Context context) {
        this.sendScenarier = send_brugsscenarie;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        return new ViewHolder(inflater.inflate(R.layout.send_brugsscenarie, parent));
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        ViewHolder vh = (ViewHolder)holder;
        vh.addBrugs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((IRecycleViewAdapterListener)context).sendBrugsscenarie(sendScenarier.get(holder.getAdapterPosition()));
            }
        });
        vh.navnSenarie.setText(sendScenarier.get(position));
    }

    @Override
    public int getItemCount() {
        return sendScenarier.size();
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

