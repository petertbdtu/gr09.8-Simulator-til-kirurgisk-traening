package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.constraint.ConstraintLayout;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.R;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.interfaces.IRecycleViewAdapterListener;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.views.LEDView;

public class VaelgTabletsRecyleViewAdapter extends RecyclerView.Adapter<VaelgTabletsRecyleViewAdapter.ViewHolder> {
    private static final String TAG = "tabletRecyclerViewAdapter";

    private ArrayList<String> listTabletId;
    private Context context;


    public VaelgTabletsRecyleViewAdapter(ArrayList<String> list_tablet_ID, Context context) {
        this.listTabletId = list_tablet_ID;
        this.context = context;
    }

    public void updateData(ArrayList<String> listTabletId) {
        this.listTabletId = listTabletId;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_vaelg_tablets_liste_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final VaelgTabletsRecyleViewAdapter.ViewHolder viewHolder, int position) {

//        Glide.with(iContext)
//                .asBitmap()
//                .load(hs.get(position).billede)
//                .into(holder.enhedBilleder);
        viewHolder.tablet_liste_element_ID.setText(listTabletId.get(position));
        //viewHolder.tablet_liste_element_LED.setText(list_tablet_ID.get(position));
      viewHolder.tablet_liste_element_valgBrugs.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              ((IRecycleViewAdapterListener)context).VaelgBrugsscenarie(listTabletId.get(viewHolder.getAdapterPosition()));
          }
      });
        viewHolder.tablet_liste_element_selog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((IRecycleViewAdapterListener)context).SeLog(listTabletId.get(viewHolder.getAdapterPosition()));
            }
        });
        viewHolder.tablet_liste_element_slet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((IRecycleViewAdapterListener)context).SletTablet(listTabletId.get(viewHolder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return listTabletId.size();
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

