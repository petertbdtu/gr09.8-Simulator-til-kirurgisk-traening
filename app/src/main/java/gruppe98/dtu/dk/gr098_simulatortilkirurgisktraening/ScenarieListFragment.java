package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.dal.DataHaandtering;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.dal.Scenario;

public class ScenarieListFragment extends Fragment implements View.OnClickListener {

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_vaelg_scenarie_liste, container, false);

        indlaesScenarier();

        return view;
    }

// TODO find ud af hvad der skal ske her.
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        indlaesScenarier();
//    }

    private void indlaesScenarier() {
        ArrayList<String> liste_scenarie_navne = new ArrayList<>();
        for(Scenario scenarie:DataHaandtering.getInstance().hentAlleScenarier()) {
            liste_scenarie_navne.add(scenarie.getName());
        }

        aktiverRecyclerView(liste_scenarie_navne);
    }

    @Override
    public void onClick(View v) {
    }

    private void aktiverRecyclerView(ArrayList<String> navne) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        VaelgScenarieRecyclerViewAdapter adapter = new VaelgScenarieRecyclerViewAdapter(navne, getActivity());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }
}
