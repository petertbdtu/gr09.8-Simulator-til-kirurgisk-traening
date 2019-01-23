package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.fragments;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import org.apache.commons.lang3.SerializationUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;

import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.R;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.activities.VaelgScenarieActivity;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.application.ApplicationSingleton;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.objects.CommunicationObject;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.objects.Scenario;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.views.LEDView;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.views.MeterView;

public class InsufflatorFragment extends Fragment implements View.OnClickListener {

    View view;
    boolean erInstruktor;
    VALGT_ELEMENT ve = VALGT_ELEMENT.trykAktuel;

    MeterView gasforsyningMeter, trykMeter, flowrateMeter;
    LEDView overtrykLed, tubeblokeretLed;
    ConstraintLayout overtrykCL, tubeblokeretCL;
    TextView trykDisplay, trykMaalDisplay, flowrateDisplay, flowrateMaalDisplay, volumenDisplay;

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
            aktivtScenarie = ApplicationSingleton.getInstance().activeScenario;

            invokeAnimations();
        }
        else
        {
            byte[] aktivtScenarieByteArray = this.getArguments().getByteArray("scenarieByteArray");
            CommunicationObject CO = SerializationUtils.deserialize(aktivtScenarieByteArray);
            aktivtScenarie = CO.getScenario();
        }

        loadScenarie(aktivtScenarie);

        return view;
    }

    private void initialiserViews() {
        // LED'er
        overtrykLed = view.findViewById(R.id.overtrykLed);
        tubeblokeretLed = view.findViewById(R.id.tubeblokeretLed);
        overtrykCL = view.findViewById(R.id.clOvertryk);
        tubeblokeretCL = view.findViewById(R.id.clTube);

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
    }

    public void loadScenarie(Scenario sc) {

        overtrykLed.setErTaendt(sc.isOverPressureLED());
        tubeblokeretLed.setErTaendt(sc.isTubeBlockedLED());

        gasforsyningMeter.setAktuelVaerdi(sc.getGasSupply());

        DecimalFormat df = new DecimalFormat("0.0");
        flowrateMaalDisplay.setText(df.format(sc.getTargetFlowRate()/10f));
        flowrateDisplay.setText(df.format(sc.getActualFlowRate()/10f));
        flowrateMeter.setAktuelVaerdi(sc.getActualFlowRate());
        flowrateMeter.setForventetVaerdi(sc.getTargetFlowRate());

        trykMaalDisplay.setText(String.format("%02d", sc.getTargetPressure()));
        trykDisplay.setText(String.format("%02d", sc.getActualPressure()));
        trykMeter.setAktuelVaerdi(sc.getActualPressure());
        trykMeter.setForventetVaerdi(sc.getTargetPressure());

        volumenDisplay.setText(String.format("%02d", sc.getVolume()));
    }

    public void bindInstruktorKnapper() {
        overtrykCL.setOnClickListener(this);
        tubeblokeretCL.setOnClickListener(this);
        gasforsyningMeter.setOnClickListener(this);
        trykMaalDisplay.setOnClickListener(this);
        trykDisplay.setOnClickListener(this);
        flowrateMaalDisplay.setOnClickListener(this);
        flowrateDisplay.setOnClickListener(this);
        volumenDisplay.setOnClickListener(this);
    }

    private enum VALGT_ELEMENT{
        gasForsyning,
        trykTarget, trykAktuel,
        flowrateTarget, flowrateAktuel,
        volumen
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.clOvertryk)
        {
            ApplicationSingleton.getInstance().activeScenario.setOverPressureLED(!ApplicationSingleton.getInstance().activeScenario.isOverPressureLED());
            loadScenarie(ApplicationSingleton.getInstance().activeScenario);
            return;
        }
        if (v.getId() == R.id.clTube) {
            ApplicationSingleton.getInstance().activeScenario.setTubeBlockedLED(!ApplicationSingleton.getInstance().activeScenario.isTubeBlockedLED());
            loadScenarie(ApplicationSingleton.getInstance().activeScenario);
            return;
        }

        switch (v.getId()) {
            case R.id.gasforsyningMeter:
                ve = VALGT_ELEMENT.gasForsyning;
                break;
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
        }
        showNumberPickDialog();
    }

    private void showNumberPickDialog(){
        LinearLayout LL = new LinearLayout(getActivity());
        LL.setOrientation(LinearLayout.HORIZONTAL);

        final NumberPicker numPick1 = new NumberPicker(getActivity());
        numPick1.setMaxValue(9);
        numPick1.setMinValue(0);
        numPick1.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        final NumberPicker numPick2 = new NumberPicker(getActivity());
        numPick2.setMaxValue(9);
        numPick2.setMinValue(0);
        numPick2.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(50, 50);
        params.gravity = Gravity.CENTER;

        LinearLayout.LayoutParams numPicerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        numPicerParams.weight = 1;

        LinearLayout.LayoutParams qPicerParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        qPicerParams.weight = 1;

        LL.setLayoutParams(params);
        LL.addView(numPick1,numPicerParams);
        LL.addView(numPick2,qPicerParams);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Indtast værdi");


        builder.setView(LL);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int tmp = (numPick1.getValue()*10) + numPick2.getValue();

                switch (ve) {
                    case gasForsyning:
                        ApplicationSingleton.getInstance().activeScenario.setGasSupply(tmp);
                        break;
                    case trykTarget:
                        ApplicationSingleton.getInstance().activeScenario.setTargetPressure(tmp);
                        break;
                    case trykAktuel:
                        ApplicationSingleton.getInstance().activeScenario.setActualPressure(tmp);
                        break;
                    case flowrateTarget:
                        ApplicationSingleton.getInstance().activeScenario.setTargetFlowRate(tmp);
                        break;
                    case flowrateAktuel:
                        ApplicationSingleton.getInstance().activeScenario.setActualFlowRate(tmp);
                        break;
                    case volumen:
                        ApplicationSingleton.getInstance().activeScenario.setVolume(tmp);
                        break;
                }
                loadScenarie(ApplicationSingleton.getInstance().activeScenario);
                dialogInterface.dismiss();
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
    private void invokeAnimations(){

        new ValueAnimator();
        final ValueAnimator anim = ValueAnimator.ofFloat(0.6f,1f);
       anim.setDuration(500);
       anim.setRepeatMode(ValueAnimator.REVERSE);
       anim.setRepeatCount(1);
       anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
           @Override
           public void onAnimationUpdate(ValueAnimator valueAnimator) {
               System.out.println("DEBUG: "+valueAnimator.getAnimatedValue());
               trykDisplay.setAlpha((Float) valueAnimator.getAnimatedValue());
               overtrykLed.setAlpha((Float) valueAnimator.getAnimatedValue());
               tubeblokeretLed.setAlpha((Float) valueAnimator.getAnimatedValue());
               trykMaalDisplay.setAlpha((Float) valueAnimator.getAnimatedValue());
               flowrateDisplay.setAlpha((Float) valueAnimator.getAnimatedValue());
               flowrateMaalDisplay.setAlpha((Float) valueAnimator.getAnimatedValue());
               volumenDisplay.setAlpha((Float) valueAnimator.getAnimatedValue());
               gasforsyningMeter.setAlpha((Float) valueAnimator.getAnimatedValue());
               if(!(getActivity() instanceof VaelgScenarieActivity)) {

                   anim.removeAllUpdateListeners();
                   anim.removeAllListeners();
               }

           }
       });
       anim.addListener(new Animator.AnimatorListener() {
           @Override
           public void onAnimationStart(Animator animator) {

           }

           @Override
           public void onAnimationEnd(Animator animator) {
               anim.start();
           }

           @Override
           public void onAnimationCancel(Animator animator) {

           }

           @Override
           public void onAnimationRepeat(Animator animator) {

           }
       });

        anim.start();
    }
}
