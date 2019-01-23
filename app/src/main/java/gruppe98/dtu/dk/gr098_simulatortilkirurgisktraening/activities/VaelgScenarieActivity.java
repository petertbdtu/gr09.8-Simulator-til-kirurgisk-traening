package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.activities;

import android.content.DialogInterface;
import android.media.MediaScannerConnection;
import android.net.wifi.p2p.WifiP2pDevice;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.R;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.application.ApplicationSingleton;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.fragments.InsufflatorFragment;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.fragments.ScenarieListFragment;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.interfaces.IRecycleViewAdapterListener;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.objects.Scenario;

public class VaelgScenarieActivity extends AppCompatActivity implements View.OnClickListener, IRecycleViewAdapterListener {

    ImageView scenarieKnap;
    TextView overskrift;
    int scenarieKnapTilstand;
    String scenarieNavn;

    /////////////////////////////////////////
    //// Activity overrides /////////////////
    /////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaelg_scenarie);

        overskrift = findViewById(R.id.tvVaelgTabletTitel);
        scenarieKnap = findViewById(R.id.scenarieKnap);
        scenarieKnap.setOnClickListener(this);

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentScenarieContainer, new ScenarieListFragment())
                    .commit();
        }

        scenarieKnapTilstand = 0;
    }

    /////////////////////////////////////////
    //// OnClick override ///////////////////
    /////////////////////////////////////////

    @Override
    public void onClick(View view) {
        if (scenarieKnapTilstand == 0) {
            ApplicationSingleton.getInstance().activeScenario = new Scenario();
            dialogVaelgScenarieNavn();
        }
        else {
            ApplicationSingleton.getInstance().createScenario(ApplicationSingleton.getInstance().activeScenario, scenarieNavn);
            MediaScannerConnection.scanFile(this, new String[] {Environment.getExternalStorageDirectory().getAbsolutePath()}, null, null);
            skiftTilScenarieListe();
        }
    }

    /////////////////////////////////////////
    //// Activity methods ///////////////////
    /////////////////////////////////////////

    private void dialogVaelgScenarieNavn() {
        //Prepare view
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);

        //Create dialog and show
        new AlertDialog.Builder(this)
            .setTitle("Angiv navn på brugsscenarie")
            .setView(input)
            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // TODO skal være mindst et bogstav langt.
                // TODO tjek at navn ikke er brugt.
                Scenario tmpScenarie = new Scenario();
                scenarieNavn = input.getText().toString();
                ApplicationSingleton.getInstance().activeScenario = tmpScenarie;
                skiftTilInsufflator();
            }})
            .setNegativeButton("Annuller", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }})
            .show();
    }

    private void skiftTilScenarieListe() {
        overskrift.setText(getString(R.string.vaelg_scenarie_overskrift));
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentScenarieContainer, new ScenarieListFragment())
                .commit();
        scenarieKnap.setImageDrawable(getResources().getDrawable(R.drawable.plus));
        scenarieKnapTilstand = 0;
    }

    private void skiftTilInsufflator() {
        overskrift.setText(scenarieNavn);
        Bundle args = new Bundle();
        args.putBoolean("erInstruktor", true);
        Fragment fragment = new InsufflatorFragment();
        fragment.setArguments(args);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentScenarieContainer, fragment)
                .commit();

        scenarieKnap.setImageDrawable(getResources().getDrawable(R.drawable.save));
        scenarieKnapTilstand = 1;
    }

    /////////////////////////////////////////
    //// Adapter overrides //////////////////
    /////////////////////////////////////////

    @Override
    public void VaelgBrugsscenarie(String Id) { }

    @Override
    public void SeLog(String Id) { }

    @Override
    public void PeerChosen(WifiP2pDevice WPD, boolean suppressdlg) { }

    @Override
    public void sendBrugsscenarie(Scenario brugsscencarie) { }

    @Override
    public void fjernBrugsscenarie(String scenarieNavn) {
        String filePath = ApplicationSingleton.getInstance().fjernScenarie(scenarieNavn);
        MediaScannerConnection.scanFile(this, new String[]{filePath}, null, null);
    }

    @Override
    public void redigerScenarie(String scenarieNavn) {
        ApplicationSingleton.getInstance().activeScenario = ApplicationSingleton.getInstance().hentScenarie(scenarieNavn);
        this.scenarieNavn = scenarieNavn;
        skiftTilInsufflator();
    }

    @Override
    public void deleteDevice(String deviceAddress) {

    }
}
