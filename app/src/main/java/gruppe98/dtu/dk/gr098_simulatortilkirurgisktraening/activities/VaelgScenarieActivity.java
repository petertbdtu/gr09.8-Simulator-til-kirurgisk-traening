package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.activities;

import android.app.Fragment;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.R;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.application.InsufflatorSimApp;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.dal.DataHaandtering;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.dal.Scenario;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.fragments.InsufflatorFragment;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.fragments.ScenarieListFragment;

public class VaelgScenarieActivity extends AppCompatActivity implements View.OnClickListener {

    Button scenarieKnap;
    int scenarieKnapTilstand;
    TextView overskrift;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaelg_scenarie);

        overskrift = findViewById(R.id.tvOverskrift);

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
            InsufflatorSimApp.aktivtScenarie = new Scenario();

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Angiv navn på brugsscenarie");

            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);

            builder.setView(input);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // TODO skal være mindst et bogstav langt.
                    // TODO tjek at navn ikke er brugt.
                    InsufflatorSimApp.aktivtScenarie.setName(input.getText().toString());
                    skiftTilInsufflator();
                }
            });
            builder.setNegativeButton("Annuller", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                }
            });
            builder.show();
        }
        else {
            DataHaandtering.getInstance().opretScenarie(InsufflatorSimApp.aktivtScenarie);
            skiftTilScenarieListe();
        }
    }

    private void skiftTilScenarieListe() {
        overskrift.setText(getString(R.string.vaelg_scenarie_overskrift));
        Fragment fragment = new ScenarieListFragment();
        getFragmentManager().beginTransaction()
                .replace(R.id.fragmentScenarieContainer, fragment)
                .commit();
        scenarieKnap.setText(getString(R.string.vaelg_scenarie_knap_nyt_scenarie));
        scenarieKnapTilstand = 0;
    }

    private void skiftTilInsufflator() {
        overskrift.setText(InsufflatorSimApp.aktivtScenarie.getName());
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

    public void skiftTilRedigerScenarie(String scenarieNavn) {
        InsufflatorSimApp.aktivtScenarie = DataHaandtering.getInstance().hentScenarie(scenarieNavn);
        skiftTilInsufflator();
    }

    public void sletScenarie(String s) {
        DataHaandtering.getInstance().fjernScenarie(s);
        skiftTilScenarieListe();
    }
}
