package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.dal.DataHaandtering;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.dal.Scenario;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.views.LEDView;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.views.MeterView;

public class InsufflatorFragment extends Fragment implements View.OnClickListener {

    View view;
    boolean erInstruktor;

    MeterView gasforsyningMeter, trykMeter, flowrateMeter, volumenMeter;
    LEDView overtrykLed, tubeblokeretLed;
    TextView trykDisplay, trykMaalDisplay, flowrateDisplay, flowrateMaalDisplay, volumenDisplay;

    ImageView startKnap, stopKnap, incTrykKnap, decTrykKnap, incFlowrateKnap, decFlowrateKnap, resetVolumenKnap;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_insufflator, container, false);
        erInstruktor = this.getArguments().getBoolean("erInstruktor");

        initialiserViews();

        if(erInstruktor)
        {
            bindInstruktorKnapper();
        }
        else
        {

        }

        loadScenarie(InsufflatorSimApp.aktivtScenarie);

        return view;
    }

    private void initialiserViews() {
        // LED'er
        overtrykLed = view.findViewById(R.id.overtrykLed);
        tubeblokeretLed = view.findViewById(R.id.tubeblokeretLed);

        // Gasforsyning
        gasforsyningMeter = view.findViewById(R.id.gasforsyningMeter);

        // Tryk
        trykDisplay = view.findViewById(R.id.trykDisplay);
        trykMaalDisplay = view.findViewById(R.id.trykMaalDisplay);
        trykMeter = view.findViewById(R.id.trykMeter);

        // Flowrate
        flowrateDisplay = view.findViewById(R.id.flowrateDisplay);
        flowrateMaalDisplay = view.findViewById(R.id.flowrateMaalDisplay);
        flowrateMeter = view.findViewById(R.id.flowrateMeter);

        // Volumen
        volumenDisplay = view.findViewById(R.id.volumenDisplay);
        volumenMeter = view.findViewById(R.id.volumenMeter);

        // OBS AT DE INDTIL VIDERE ER IMAGEVIEWS
        startKnap = view.findViewById(R.id.startKnap);
        stopKnap = view.findViewById(R.id.stopKnap);
        incTrykKnap = view.findViewById(R.id.incTrykKnap);
        decTrykKnap = view.findViewById(R.id.decTrykKnap);
        incFlowrateKnap = view.findViewById(R.id.incFlowrateKnap);
        decFlowrateKnap = view.findViewById(R.id.decFlowrateKnap);
        resetVolumenKnap = view.findViewById(R.id.resetVolumenKnap);
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
    }

    public void bindInstruktorKnapper() {
        startKnap.setOnClickListener(this);
        stopKnap.setOnClickListener(this);
        incTrykKnap.setOnClickListener(this);
        decTrykKnap.setOnClickListener(this);
        incFlowrateKnap.setOnClickListener(this);
        decFlowrateKnap.setOnClickListener(this);
        resetVolumenKnap.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO opsæt instruktør funktionalitet.
        switch (v.getId())
        {
            case R.id.incTrykKnap:
                break;
            case R.id.decTrykKnap:
                break;
            case R.id.incFlowrateKnap:
                break;
            case R.id.decFlowrateKnap:
                break;
            case R.id.resetVolumenKnap:
                break;
        }
    }
}
