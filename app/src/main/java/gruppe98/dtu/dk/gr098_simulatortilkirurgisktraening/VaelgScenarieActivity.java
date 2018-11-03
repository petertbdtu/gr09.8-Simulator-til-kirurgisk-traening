package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class VaelgScenarieActivity extends AppCompatActivity implements View.OnClickListener {

    Button opretScenarie;
    Button redigerScenarie;
    Button sletScenarie;

    private static ArrayList<String> liste_scenarie_navne = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaelg_scenarie);

        opretScenarie = findViewById(R.id.nyt_scenarie);
        opretScenarie.setOnClickListener(this);

        indlaesScenarier();

    }

    private void indlaesScenarier() {
        liste_scenarie_navne.add("ForhøjetVolumen");
        liste_scenarie_navne.add("placeholder2");
        liste_scenarie_navne.add("placeholder3");
        liste_scenarie_navne.add("placeholder 4");
        liste_scenarie_navne.add("placeholder 5");
        liste_scenarie_navne.add("placeholder 6");
        liste_scenarie_navne.add("placeholder 7");
        liste_scenarie_navne.add("placeholder 8");
        liste_scenarie_navne.add("placeholder 9");
        liste_scenarie_navne.add("placeholder 10");
        liste_scenarie_navne.add("placeholder 11");
        liste_scenarie_navne.add("placeholder 12");
        liste_scenarie_navne.add("placeholder 13");
        liste_scenarie_navne.add("placeholder 14");
        liste_scenarie_navne.add("placeholder 15");
        liste_scenarie_navne.add("placeholder 16");
        liste_scenarie_navne.add("placeholder 17");
        liste_scenarie_navne.add("placeholder 18");
        liste_scenarie_navne.add("placeholder 19");
        liste_scenarie_navne.add("placeholder 20");
        liste_scenarie_navne.add("placeholder 21");
        liste_scenarie_navne.add("placeholder 22");
        liste_scenarie_navne.add("placeholder 23");
        liste_scenarie_navne.add("placeholder 24");
        liste_scenarie_navne.add("placeholder 25");
        liste_scenarie_navne.add("placeholder 26");
        liste_scenarie_navne.add("placeholder 27");
        liste_scenarie_navne.add("placeholder 28");
        liste_scenarie_navne.add("placeholder 29");
        liste_scenarie_navne.add("placeholder 30");



        aktiverRecyclerView();
    }

    @Override
    public void onClick(View v) {
        startActivity(new Intent(this, OpretScenarie.class));
    }

    private void aktiverRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        VaelgScenarieRecyclerViewAdapter adapter = new VaelgScenarieRecyclerViewAdapter(liste_scenarie_navne, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    public static String getScenarieNavn(int ID) {
        return liste_scenarie_navne.get(ID);
    }
}
