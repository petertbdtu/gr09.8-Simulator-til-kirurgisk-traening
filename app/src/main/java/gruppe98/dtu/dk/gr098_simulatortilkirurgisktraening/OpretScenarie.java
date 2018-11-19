package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.dal.Scenario;

public class OpretScenarie extends AppCompatActivity {

    Button saveScenario;
    TextView flowRate;
    TextView scenarioName;
    TextView infoText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opret_scenarie);

        flowRate = findViewById(R.id.flowrateDisplay);
        flowRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flowRate.setText(""+99);
            }
        });

        scenarioName = findViewById(R.id.new_scenario_name);
        scenarioName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                scenarioName.setText("Chosen scenario name");
            }
        });

        saveScenario = findViewById(R.id.saveScenario);
        saveScenario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Scenario newScenario = new Scenario();
                newScenario.setName(scenarioName.getText().toString());
                newScenario.setActualFlowRate(Integer.parseInt(flowRate.getText().toString()));
                //TODO
                //check for duplicates
                Applikation.scenarieHaandtering.opretScenarie(newScenario);
                updateScenarios();
            }
        });

        infoText = findViewById(R.id.new_scenario_info);
    }

    private void updateScenarios() {
        infoText.setText("Scenario updated");

        finish();
    }
}
