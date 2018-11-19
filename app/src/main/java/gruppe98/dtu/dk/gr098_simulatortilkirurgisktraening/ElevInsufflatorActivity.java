package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.views.LEDView;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.views.MeterView;

public class ElevInsufflatorActivity extends AppCompatActivity {

    MeterView gasforsyningMeter, trykMeter, flowrateMeter, volumenMeter;
    LEDView overtrykLed, tubeblokeretLed;
    TextView trykDisplay, flowrateDisplay, volumenDisplay;
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
        trykMeter = findViewById(R.id.trykMeter);
        incTrykKnap = findViewById(R.id.incTrykKnap);
        decTrykKnap = findViewById(R.id.decTrykKnap);

        // Flowrate
        flowrateDisplay = findViewById(R.id.flowrateDisplay);
        flowrateMeter = findViewById(R.id.flowrateMeter);
        incFlowrateKnap = findViewById(R.id.incFlowrateKnap);
        decFlowrateKnap = findViewById(R.id.decFlowrateKnap);

        // Volumen
        volumenDisplay = findViewById(R.id.volumenDisplay);
        volumenMeter = findViewById(R.id.volumenMeter);
        resetVolumenKnap = findViewById(R.id.resetVolumenKnap);



        Paint bggasfor = new Paint();
        bggasfor.setARGB(255, 0, 0, 0);
        Paint fggasfor = new Paint();
        fggasfor.setARGB(255, 0, 255, 0);
        gasforsyningMeter.setFilledColor(fggasfor);
        gasforsyningMeter.setEmptyColor(bggasfor);

        gasforsyningMeter.setValue(80);
        trykMeter.setValue(25);
        flowrateMeter.setValue(100/3);
        volumenMeter.setValue(100);

        overtrykLed.setColor(fggasfor);


    }
}
