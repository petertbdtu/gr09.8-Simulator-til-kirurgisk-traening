package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.R;

public class VisningAfventerFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_visning_afventer, container, false);
        Bundle args = getArguments();

        TextView deviceNavn = v.findViewById(R.id.deviceNavnTextView);
        deviceNavn.setText(args.getString("deviceNavn"));

        return v;
    }
}
