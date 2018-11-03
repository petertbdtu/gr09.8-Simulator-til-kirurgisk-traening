package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class VaelgScenarieRecyclerViewAdapter extends RecyclerView.Adapter<VaelgScenarieRecyclerViewAdapter.ViewHolder> {

    private ArrayList<String> list_scenarie_navne = new ArrayList<>();
    private Context context;

    public VaelgScenarieRecyclerViewAdapter(ArrayList<String> list_scenarie_navne, Context context){
        this.list_scenarie_navne = list_scenarie_navne;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scenarie_liste_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.scenarie_navn.setText(list_scenarie_navne.get(i));

        viewHolder.rediger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent redigerAktivitet = new Intent(context, redigerAktivitetActivity.class);
                //redigerAktivitet.putExtra("position", i);
                redigerAktivitet.putExtra("scenarieID", i);

                context.startActivity(redigerAktivitet);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list_scenarie_navne.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView scenarie_navn;
        Button rediger;
        Button slet;
        ConstraintLayout liste_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            scenarie_navn = itemView.findViewById(R.id.scenarie_navn);
            rediger = itemView.findViewById(R.id.knap_rediger_scenarie);
            slet = itemView.findViewById(R.id.knap_slet_scenarie);
            liste_layout = itemView.findViewById(R.id.listeElement);


        }
    } {

    }
}
