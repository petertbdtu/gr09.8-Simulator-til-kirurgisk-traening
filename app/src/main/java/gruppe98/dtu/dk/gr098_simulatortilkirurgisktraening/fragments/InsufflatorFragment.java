package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.fragments;

import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.application.InsufflatorSimApp;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.R;
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

    Scenario aktivtScenarie;

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
            aktivtScenarie = InsufflatorSimApp.aktivtScenarie;
        }
        else
        {
            byte[] aktivtScenarieByteArray = this.getArguments().getByteArray("scenarieByteArray");
            aktivtScenarie = new Scenario(aktivtScenarieByteArray);
        }

        loadScenarie(aktivtScenarie);

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
        flowrateMeter.setForventetVaerdi(sc.getTargetFlowRate());

        trykMaalDisplay.setText(Integer.toString(sc.getTargetPressure()));
        trykDisplay.setText(Integer.toString(sc.getActualPressure()));
        trykMeter.setAktuelVaerdi(sc.getActualPressure());
        trykMeter.setForventetVaerdi(sc.getTargetPressure());

        volumenDisplay.setText(Integer.toString(sc.getVolume()));
        volumenMeter.setAktuelVaerdi(sc.getVolume());
    }

    public void bindInstruktorKnapper() {

        trykMaalDisplay.setOnClickListener(this);
        trykDisplay.setOnClickListener(this);
        flowrateMaalDisplay.setOnClickListener(this);
        flowrateDisplay.setOnClickListener(this);
        volumenDisplay.setOnClickListener(this);

//        startKnap.setOnClickListener(this);
//        stopKnap.setOnClickListener(this);
//        incTrykKnap.setOnClickListener(this);
//        decTrykKnap.setOnClickListener(this);
//        incFlowrateKnap.setOnClickListener(this);
//        decFlowrateKnap.setOnClickListener(this);
        // resetVolumenKnap kan godt bruges da volumen displayet ikke bør have noget med scenariet at gøre.
//        resetVolumenKnap.setOnClickListener(this);
    }

    private enum VALGT_ELEMENT{
        trykTarget, trykAktuel,
        flowrateTarget, flowrateAktuel,
        volumen
    }

    @Override
    public void onClick(View v) {
        Scenario as = InsufflatorSimApp.aktivtScenarie;
        switch (v.getId()) {
            case R.id.flowrateMaalDisplay:
                ve = VALGT_ELEMENT.flowrateTarget;
                showNumberPickDialog();
                break;
            case R.id.flowrateDisplay:
                ve = VALGT_ELEMENT.flowrateAktuel;
                showNumberPickDialog();
                break;
            case R.id.trykMaalDisplay:
                ve = VALGT_ELEMENT.trykTarget;
                showNumberPickDialog();
                break;
            case R.id.trykDisplay:
                ve = VALGT_ELEMENT.trykAktuel;
                showNumberPickDialog();
                break;
            case R.id.volumenDisplay:
                ve = VALGT_ELEMENT.volumen;
                showNumberPickDialog();
                break;
        }
        InsufflatorSimApp.aktivtScenarie = as;
        loadScenarie(as);
    }

    private void showNumberPickDialog(){
        /*
        final NumberPicker numberPicker = new NumberPicker(getActivity());
        numberPicker.setMaxValue(99);
        numberPicker.setMinValue(0);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Tal");
        builder.setMessage("Vælg værdi :");
        builder.setView(numberPicker);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //dialogHost.onPositiveButton(numberPicker.getValue());
                Scenario as = InsufflatorSimApp.aktivtScenarie;
                switch (ve) {
                    case trykTarget:
                        as.setTargetPressure(numberPicker.getValue());
                        break;
                    case trykAktuel:
                        as.setActualPressure(numberPicker.getValue());
                        break;
                    case flowrateTarget:
                        as.setTargetFlowRate(numberPicker.getValue());
                        break;
                    case flowrateAktuel:
                        as.setActualFlowRate(numberPicker.getValue());
                        break;
                    case volumen:
                        as.setVolume(numberPicker.getValue());
                        break;
                }

                loadScenarie(InsufflatorSimApp.aktivtScenarie);
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("ANNULLER", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //dialogHost.onCancelButton();
                dialog.dismiss();
            }
        });

        builder.show();
        */

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Indtast værdi");

        final EditText input = new EditText(getActivity());
        input.setInputType(InputType.TYPE_CLASS_NUMBER);

        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(input.getText().toString().length() != 0) {
                    int tmp = Integer.parseInt(input.getText().toString());

                        Scenario as = InsufflatorSimApp.aktivtScenarie;
                        switch (ve) {
                            case trykTarget:
                                as.setTargetPressure(tmp);
                                break;
                            case trykAktuel:
                                as.setActualPressure(tmp);
                                break;
                            case flowrateTarget:
                                as.setTargetFlowRate(tmp);
                                break;
                            case flowrateAktuel:
                                as.setActualFlowRate(tmp);
                                break;
                            case volumen:
                                as.setVolume(tmp);
                                break;
                        }

                        loadScenarie(InsufflatorSimApp.aktivtScenarie);
                        dialogInterface.dismiss();
                }
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

}
