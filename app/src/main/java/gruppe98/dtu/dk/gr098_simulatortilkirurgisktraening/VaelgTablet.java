package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class VaelgTablet extends AppCompatActivity implements View.OnClickListener {

    Button tabletAktiviteter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaelg_tablet);

        tabletAktiviteter = findViewById(R.id.button4);
        tabletAktiviteter.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent tabletAktiviteter = new Intent(this, TabletAktiviteter.class);
        startActivity(tabletAktiviteter);
    }
}
