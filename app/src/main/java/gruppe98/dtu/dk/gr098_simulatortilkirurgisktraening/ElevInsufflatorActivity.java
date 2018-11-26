package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.dal.DataHaandtering;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.dal.Scenario;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.views.LEDView;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.views.MeterView;

public class ElevInsufflatorActivity extends AppCompatActivity implements View.OnClickListener {

    MeterView gasforsyningMeter, trykMeter, flowrateMeter, volumenMeter;
    LEDView overtrykLed, tubeblokeretLed;
    TextView trykDisplay, trykMaalDisplay, flowrateDisplay, flowrateMaalDisplay, volumenDisplay;
    ImageView startKnap, stopKnap, incTrykKnap, decTrykKnap, incFlowrateKnap, decFlowrateKnap, resetVolumenKnap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elev_insufflator);

        // LED'er
        overtrykLed = findViewById(R.id.overtrykLed);
        tubeblokeretLed = findViewById(R.id.tubeblokeretLed);

        // Gasforsyning
        gasforsyningMeter = findViewById(R.id.gasforsyningMeter);

        // Tryk
        trykDisplay = findViewById(R.id.trykDisplay);
        trykMaalDisplay = findViewById(R.id.trykMaalDisplay);
        trykMeter = findViewById(R.id.trykMeter);

        // Flowrate
        flowrateDisplay = findViewById(R.id.flowrateDisplay);
        flowrateMaalDisplay = findViewById(R.id.flowrateMaalDisplay);
        flowrateMeter = findViewById(R.id.flowrateMeter);

        // Volumen
        volumenDisplay = findViewById(R.id.volumenDisplay);
        volumenMeter = findViewById(R.id.volumenMeter);

        // Elevens knapper (Ikke nødvendigvis relevant for instruktør)
        // OBS AT DE INDTIL VIDERE ER IMAGEVIEWS
        startKnap = findViewById(R.id.startKnap);
        stopKnap = findViewById(R.id.stopKnap);
        incTrykKnap = findViewById(R.id.incTrykKnap);
        decTrykKnap = findViewById(R.id.decTrykKnap);
        incFlowrateKnap = findViewById(R.id.incFlowrateKnap);
        decFlowrateKnap = findViewById(R.id.decFlowrateKnap);
        resetVolumenKnap = findViewById(R.id.resetVolumenKnap);

        resetVolumenKnap.setOnClickListener(this);

//        gasforsyningMeter.setVaerdi(80);
//        trykMeter.setVaerdi(25);
//        flowrateMeter.setVaerdi(100/3);
//        volumenMeter.setVaerdi(100);

//        overtrykLed.taend();

        Scenario sc = DataHaandtering.getInstance().hentScenarie("TestScenarie");
        Log.d("pt", "onCreate: "+sc.getName());
        loadScenarie(sc);

    }

    @Override
    public void onClick(View v) {

    }

    public void loadScenarie(Scenario sc) {

        flowrateMaalDisplay.setText(Integer.toString(sc.getTargetFlowRate()));
        flowrateDisplay.setText(Integer.toString(sc.getActualFlowRate()));
        flowrateMeter.setVaerdi(sc.getActualFlowRate());

        trykMaalDisplay.setText(Integer.toString(sc.getTargetPressure()));
        trykDisplay.setText(Integer.toString(sc.getActualPressure()));
        trykMeter.setVaerdi(sc.getActualPressure());

        volumenDisplay.setText(Integer.toString(sc.getVolume()));
        volumenMeter.setVaerdi(sc.getVolume());

        // sc.getName();
    }
}
