package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.activities;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.R;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.fragments.VisningAfventerFragment;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.application.InsufflatorSimApp;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.dal.Scenario;

public class InsufflatorVisningActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insufflator_visning);

        InsufflatorSimApp.aktivtScenarie = new Scenario();

        Fragment fragment = new VisningAfventerFragment();
        getFragmentManager().beginTransaction()
                .add(R.id.fragmentContainer, fragment)
                .commit();

        // TODO Vent på bluetooth asynkront
        // TODO sæt aktivt scenarie når bluetooth svarer

    }
}
