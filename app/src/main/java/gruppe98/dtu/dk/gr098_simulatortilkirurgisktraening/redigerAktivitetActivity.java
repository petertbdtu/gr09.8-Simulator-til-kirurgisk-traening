package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class redigerAktivitetActivity extends AppCompatActivity {

    TextView tekst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rediger_aktivitet);

        tekst = findViewById(R.id.textView2);

        int scenarieID = getIntent().getIntExtra("scenarieID", 0);


        String testString = VaelgScenarieActivity.getScenarieNavn(scenarieID);

        tekst.setText(testString);

    }
}
