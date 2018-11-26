package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class VisningAfventerFragment extends Fragment implements View.OnClickListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_visning_afventer, container, false);

        view.findViewById(R.id.skipButton).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        // TODO onClick skal slettes når bluetooth er implementeret i InsufflatorVisningActivity.java
        Fragment fragment;
        fragment = new InsufflatorFragment();
        Bundle args = new Bundle();
        args.putBoolean("erInstruktor", true);
        fragment.setArguments(args);

        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(R.id.fragmentContainer, fragment)
                .commit();
    }
}
