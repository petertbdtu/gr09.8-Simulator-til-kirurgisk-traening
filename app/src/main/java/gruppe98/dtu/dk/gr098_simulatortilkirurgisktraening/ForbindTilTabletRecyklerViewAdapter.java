/*package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class ForbindTilTabletRecyklerViewAdapter extends RecyclerView.Adapter<ForbindTilTabletRecyklerViewAdapter.ViewHolder> {


    private ArrayList<String> listForbindTablet = new ArrayList<>();
    private Context context;

    public ForbindTilTabletRecyklerViewAdapter(ArrayList<String> listForbindTablet, Context context){
        this.listForbindTablet = listForbindTablet;
        this.context = context;
    }

    @Override
    public VaelgScenarieRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forbind_tablet_liste, parent, false);
       ViewHolder holder = new ViewHolder(view);
       return holder;

    @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            viewHolder.textView2.setText(listForbindTablet.get(i));
        }

}
*/