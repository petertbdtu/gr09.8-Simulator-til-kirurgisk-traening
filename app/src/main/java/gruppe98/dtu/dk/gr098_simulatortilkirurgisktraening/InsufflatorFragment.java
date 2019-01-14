package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.dal.Scenario;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.views.LEDView;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.views.MeterView;

public class InsufflatorFragment extends Fragment implements View.OnClickListener {

    View view;
    boolean erInstruktor;
    VALGT_ELEMENT ve = VALGT_ELEMENT.trykAktuel;

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
        flowrateMeter.setAktuelVaerdi(sc.getActualFlowRate());

        trykMaalDisplay.setText(Integer.toString(sc.getTargetPressure()));
        trykDisplay.setText(Integer.toString(sc.getActualPressure()));
        trykMeter.setAktuelVaerdi(sc.getActualPressure());

        volumenDisplay.setText(Integer.toString(sc.getVolume()));
        volumenMeter.setAktuelVaerdi(sc.getVolume());
    }

    public void bindInstruktorKnapper() {

        trykMaalDisplay.setOnClickListener(this);
        trykDisplay.setOnClickListener(this);
        flowrateMaalDisplay.setOnClickListener(this);
        flowrateDisplay.setOnClickListener(this);
        volumenDisplay.setOnClickListener(this);

        startKnap.setOnClickListener(this);
        stopKnap.setOnClickListener(this);
        incTrykKnap.setOnClickListener(this);
        decTrykKnap.setOnClickListener(this);
        incFlowrateKnap.setOnClickListener(this);
        decFlowrateKnap.setOnClickListener(this);
        resetVolumenKnap.setOnClickListener(this);
    }

    private enum VALGT_ELEMENT{
        trykTarget, trykAktuel,
        flowrateTarget, flowrateAktuel,
        volumen
    }

    @Override
    public void onClick(View v) {
        Scenario as = InsufflatorSimApp.aktivtScenarie;
        switch (v.getId())
        {
            case R.id.flowrateMaalDisplay:
                ve = VALGT_ELEMENT.flowrateTarget;
                break;
            case R.id.flowrateDisplay:
                ve = VALGT_ELEMENT.flowrateAktuel;
                break;
            case R.id.trykMaalDisplay:
                ve = VALGT_ELEMENT.trykTarget;
                break;
            case R.id.trykDisplay:
                ve = VALGT_ELEMENT.trykAktuel;
                break;
            case R.id.volumenDisplay:
                ve = VALGT_ELEMENT.volumen;
                break;
            case R.id.incTrykKnap:
            case R.id.incFlowrateKnap:
                switch (ve) {
                    case trykTarget:
                        as.setTargetPressure(as.getTargetPressure()+1);
                        break;
                    case trykAktuel:
                        as.setActualPressure(as.getActualPressure()+1);
                        break;
                    case flowrateTarget:
                        as.setTargetFlowRate(as.getTargetFlowRate()+1);
                        break;
                    case flowrateAktuel:
                        as.setActualFlowRate(as.getActualFlowRate()+1);
                        break;
                    case volumen:
                        as.setVolume(as.getVolume()+1);
                        break;
                }
                break;
            case R.id.decTrykKnap:
            case R.id.decFlowrateKnap:
                switch (ve) {
                    case trykTarget:
                        as.setTargetPressure(as.getTargetPressure()-1);
                        break;
                    case trykAktuel:
                        as.setActualPressure(as.getActualPressure()-1);
                        break;
                    case flowrateTarget:
                        as.setTargetFlowRate(as.getTargetFlowRate()-1);
                        break;
                    case flowrateAktuel:
                        as.setActualFlowRate(as.getActualFlowRate()-1);
                        break;
                    case volumen:
                        as.setVolume(as.getVolume()-1);
                        break;
                }
                break;
        }
        InsufflatorSimApp.aktivtScenarie = as;
        loadScenarie(as);
    }
}
