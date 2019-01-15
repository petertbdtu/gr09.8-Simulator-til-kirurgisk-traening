package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.R;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.adapters.ShowLogsAdapter;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.application.ApplicationSingleton;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.dummy.ShowLogsActivity;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.objects.LogEntry;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.objects.OutcomeOptions;

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
        test.setCompleted(System.currentTimeMillis());
        test.setStart(System.currentTimeMillis());
        test.setLoggedScenario(ApplicationSingleton.getInstance().hentScenarie("TestScenarie"));
        test.setOutcome(OutcomeOptions.SUCCESS);
        LogEntry test2 = new LogEntry();
        test2.setComment("");
        test2.setCompleted(System.currentTimeMillis());
        test2.setStart(System.currentTimeMillis());
        test2.setLoggedScenario(ApplicationSingleton.getInstance().hentScenarie("TestScenarie"));
        test2.setOutcome(OutcomeOptions.SUCCESS);
        tempLogArrayList.add(test);
        tempLogArrayList.add(test2);
        tempLogArrayList.add(makeDummy());
        tempLogArrayList.add(makeDummy());
        tempLogArrayList.add(makeDummy());
        tempLogArrayList.add(makeDummy());
        tempLogArrayList.add(makeDummy());
        tempLogArrayList.add(makeDummy());
        tempLogArrayList.add(makeDummy());
        tempLogArrayList.add(makeDummy());
        tempLogArrayList.add(makeDummy());
        tempLogArrayList.add(makeDummy());

        for(LogEntry logElement:ApplicationSingleton.getInstance().hentAlleLogs()) {

            if(true) {
                tempLogArrayList.add(logElement);
            }
        }
        System.out.println("DEBUG: array har size() "+tempLogArrayList.size());

        aktiverRecyclerView(tempLogArrayList);
    }

    private void aktiverRecyclerView(ArrayList<LogEntry> logElementer) {
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);
        ShowLogsAdapter adapter = new ShowLogsAdapter(logElementer, (ShowLogsActivity) getActivity());

        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        System.out.println("DEBUG: crashed?");
    }

    private LogEntry makeDummy() {
        LogEntry test = new LogEntry();
        test.setComment("");
        test.setCompleted(System.currentTimeMillis());
        test.setStart(System.currentTimeMillis());
        test.setLoggedScenario(ApplicationSingleton.getInstance().hentScenarie("TestScenarie"));
        test.setOutcome(OutcomeOptions.SUCCESS);
        return test;
    }
}

