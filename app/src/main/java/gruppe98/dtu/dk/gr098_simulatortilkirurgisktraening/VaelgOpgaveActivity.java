package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class VaelgOpgaveActivity extends AppCompatActivity implements View.OnClickListener {

    ConstraintLayout clBrugssc;
    ConstraintLayout clForbindTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaelg_opgave);

        clBrugssc = findViewById(R.id.clBrugsscenairer);
        clBrugssc.setOnClickListener(this);

        clForbindTablet = findViewById(R.id.clForbinTTablet);
        clForbindTablet.setOnClickListener(this);

        InsufflatorSimApp.scenarieHaandtering.indlaesScenarier();
    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clBrugsscenairer:
                Intent BrugssecenairerVisning = new Intent(getApplicationContext(), VaelgScenarieActivity.class);
                startActivity(BrugssecenairerVisning);
                break;
            case R.id.clForbinTTablet:
                Intent ForbinTTabletVisning = new Intent(getApplicationContext(), VaelgTablet.class);
                startActivity(ForbinTTabletVisning);
                break;
            default:
                break;
        }
    }
}
