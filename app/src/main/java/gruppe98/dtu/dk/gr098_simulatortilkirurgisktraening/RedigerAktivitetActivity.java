package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class RedigerAktivitetActivity extends AppCompatActivity {

    TextView tekst;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rediger_aktivitet);

        tekst = findViewById(R.id.textView2);


        tekst.setText(getIntent().getStringExtra("scenarieNavn"));

    }
}
