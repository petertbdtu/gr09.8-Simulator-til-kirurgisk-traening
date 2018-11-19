package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening;

import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.views.LEDView;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.views.MeterView;

public class ElevInsufflatorActivity extends AppCompatActivity {

    MeterView gasforsyningMeter, trykMeter, flowrateMeter, volumenMeter;
    LEDView overtrykLed, tubeblokeretLed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elev_insufflator);

        overtrykLed = findViewById(R.id.overtrykLed);
        tubeblokeretLed = findViewById(R.id.tubeblokeretLed);

        gasforsyningMeter = findViewById(R.id.gasforsyningMeter);
        trykMeter = findViewById(R.id.trykMeter);
        flowrateMeter = findViewById(R.id.flowrateMeter);
        volumenMeter = findViewById(R.id.volumenMeter);


        Paint bggasfor = new Paint();
        bggasfor.setARGB(255, 0, 0, 0);
        Paint fggasfor = new Paint();
        fggasfor.setARGB(255, 0, 255, 0);
        gasforsyningMeter.setFilledColor(fggasfor);
        gasforsyningMeter.setEmptyColor(bggasfor);

        gasforsyningMeter.setValue(80);
        trykMeter.setValue(25);
        flowrateMeter.setValue(1/3*100);
        volumenMeter.setValue(100);

        overtrykLed.setColor(fggasfor);


    }
}
