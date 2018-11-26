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

public class VaelgTabletRecyclerViewAdapter extends RecyclerView.Adapter<VaelgTabletRecyclerViewAdapter.ViewHolder> {

    private ArrayList<String> m_list_tablet_names;
    private ArrayList<String> m_list_tablet_addr;
    private Context context;

    public VaelgTabletRecyclerViewAdapter(ArrayList<String> namelist,ArrayList<String> addrlist, Context context){
        m_list_tablet_names = namelist;
        m_list_tablet_addr = addrlist;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_tablet_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.m_tabletname.setText("Placeholder");

        viewHolder.m_tabletconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Placeholder
            }
        });
    }

    @Override
    public int getItemCount() {
        return m_list_tablet_names.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView m_tabletname;
        Button m_tabletactivities;
        Button m_tabletdelete;
        Button m_tabletconnect;
        ConstraintLayout m_listlayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            m_tabletname = itemView.findViewById(R.id.tabletNavn);
            m_tabletconnect = itemView.findViewById(R.id.m_button_connect);
            m_tabletactivities = itemView.findViewById(R.id.m_button_activity);
            m_tabletdelete = itemView.findViewById(R.id.m_button_delete);
            m_listlayout = itemView.findViewById(R.id.m_list_tabletselect);


        }
    }
}
