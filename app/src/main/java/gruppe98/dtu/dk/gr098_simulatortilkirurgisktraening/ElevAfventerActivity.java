package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ElevAfventerActivity extends AppCompatActivity implements View.OnClickListener {

    Button skip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elev_afventer);

        skip = findViewById(R.id.skipButton);
        skip.setOnClickListener(this);

        //setup bluetooth her.
        //kør BluetoothWrapper.start() for at sætte den i listen mode
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(this, ElevInsufflatorActivity.class));
    }
}
