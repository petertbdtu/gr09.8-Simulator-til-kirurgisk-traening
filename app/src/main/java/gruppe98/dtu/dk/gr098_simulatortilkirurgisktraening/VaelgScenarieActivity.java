package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;

public class VaelgScenarieActivity extends AppCompatActivity implements View.OnClickListener {

    Button opretScenarie;
    Button redigerScenarie;
    Button sletScenarie;
    LinearLayoutManager manageScenarier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaelg_scenarie);

        opretScenarie = findViewById(R.id.nyt_scenarie);
        opretScenarie.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(this, OpretScenarie.class));
    }
}
