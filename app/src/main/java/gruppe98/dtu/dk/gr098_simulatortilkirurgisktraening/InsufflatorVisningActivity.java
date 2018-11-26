package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening;

import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;

public class InsufflatorVisningActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insufflator_visning);

        if (savedInstanceState == null) {
            Fragment fragment = new VisningAfventerFragment();
            getFragmentManager().beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        }

    }
}
