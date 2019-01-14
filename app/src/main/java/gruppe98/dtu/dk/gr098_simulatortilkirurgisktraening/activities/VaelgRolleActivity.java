package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.R;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.activities.InsufflatorVisningActivity;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.activities.VaelgOpgaveActivity;

public class VaelgRolleActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaelg_rolle);

        View clTemp = findViewById(R.id.clInsufflator);
        clTemp.setOnClickListener(this);

        clTemp = findViewById(R.id.clInstruktor);
        clTemp.setOnClickListener(this);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clInsufflator:
                Intent intentInsufflatorVisning = new Intent(getApplicationContext(), InsufflatorVisningActivity.class);
                startActivity(intentInsufflatorVisning);
                break;
            case R.id.clInstruktor:
                Intent intentInstruktorVisning = new Intent(getApplicationContext(), VaelgOpgaveActivity.class);
                startActivity(intentInstruktorVisning);
                break;
            default:
                break;
        }

    }
}