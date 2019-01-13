package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.dal.DataHaandtering;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.dal.LogEntry;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.dal.OutcomeOptions;

public class ShowLogsListFragment extends Fragment {

    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_vaelg_scenarie_liste, container, false);

        Bundle bundle = getArguments();
        System.out.println("DEBUG: bundle has "+bundle.getInt("currentDay"));
        indlaesLogs((bundle.getInt("currentDay")));

        return view;
    }

// TODO find ud af hvad der skal ske her.
//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        indlaesScenarier();
//    }

    private void indlaesLogs(int currentDay) {
        ArrayList<LogEntry> tempLogArrayList = new ArrayList<>();
        LogEntry test = new LogEntry();
        test.setComment("");
        test.setCompleted(1);
        test.setStart(1);
        test.setLoggedScenario(DataHaandtering.getInstance().hentScenarie("TestScenarie"));
        test.setOutcome(OutcomeOptions.SUCCESS);
        tempLogArrayList.add(test);
        /*
        for(LogEntry logElement:DataHaandtering.getInstance().hentAlleLogs()) {

            if(currentDay==logElement.getStart()) {
                tempLogArrayList.add(logElement);
            }
        }*/
        System.out.println("DEBUG: array har size() "+tempLogArrayList.size());

        aktiverRecyclerView(tempLogArrayList);
    }

    private void aktiverRecyclerView(ArrayList<LogEntry> logElementer) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        ShowLogsRecyclerViewAdapter adapter = new ShowLogsRecyclerViewAdapter(logElementer, (ShowLogsActivity) getActivity());

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        System.out.println("DEBUG: crashed?");
    }
}

