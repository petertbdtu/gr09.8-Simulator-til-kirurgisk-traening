package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.dal.DataAccessLayer;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.dal.Scenario;

public class TestDataLag extends AppCompatActivity {

    String fileName = "data.data";
    DataAccessLayer<Scenario> dataAccessLayer;
    List<Scenario> lScenarier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_data_lag);

        lScenarier = dataAccessLayer.loadData(fileName);
        for(Scenario s : lScenarier) {
            System.out.println(s.getName());
        }

        Scenario s1 = new Scenario();
        s1.setName("s1");

        lScenarier.add(s1);
        dataAccessLayer.saveData(lScenarier,fileName);

        lScenarier = dataAccessLayer.loadData(fileName);
        for(Scenario s : lScenarier) {
            System.out.println(s.getName());
        }
    }


}
