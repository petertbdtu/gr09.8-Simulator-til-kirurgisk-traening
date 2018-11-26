package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening;

import android.app.Fragment;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.dal.DataHaandtering;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.dal.Scenario;

public class VaelgScenarieActivity extends AppCompatActivity implements View.OnClickListener {

    Button scenarieKnap;
    int scenarieKnapTilstand;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaelg_scenarie);

        scenarieKnap = findViewById(R.id.scenarieKnap);
        scenarieKnap.setOnClickListener(this);

        Fragment fragment = new ScenarieListFragment();
        getFragmentManager().beginTransaction()
                .add(R.id.fragmentScenarieContainer, fragment)
                .commit();
        scenarieKnapTilstand = 0;
    }

    @Override
    public void onClick(View view) {
        if (scenarieKnapTilstand == 0) {
            // TODO opret scenarie i InsufflatorSimApp som kan indlæses af insufflator fragmentet
            skiftTilInsufflator();
        }
        else {
            // TODO Gem ændringer i InsufflatorSimAPp
            skiftTilScenarieListe();
        }
    }

    private void skiftTilScenarieListe() {
        Fragment fragment = new ScenarieListFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.fragmentScenarieContainer, fragment)
                .commit();

        scenarieKnap.setText(getString(R.string.vaelg_scenarie_knap_nyt_scenarie));
        scenarieKnapTilstand = 0;
    }

    private void skiftTilInsufflator() {
        Bundle args = new Bundle();
        args.putBoolean("erInstruktor", true);
        Fragment fragment = new InsufflatorFragment();
        fragment.setArguments(args);
        getFragmentManager().beginTransaction()
                .replace(R.id.fragmentScenarieContainer, fragment)
                .commit();

        scenarieKnap.setText("gem scenarie");
        scenarieKnapTilstand = 1;
    }
}
