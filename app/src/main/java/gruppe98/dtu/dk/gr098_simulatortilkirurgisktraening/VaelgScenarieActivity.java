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

    private ArrayList liste_scenarie_navne = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaelg_scenarie);

        opretScenarie = findViewById(R.id.nyt_scenarie);
        opretScenarie.setOnClickListener(this);

        indlaesScenarier();

    }

    private void indlaesScenarier() {
        liste_scenarie_navne.add("placeholder 1");
        liste_scenarie_navne.add("placeholder 2");
        liste_scenarie_navne.add("placeholder 3");
        liste_scenarie_navne.add("placeholder 1");
        liste_scenarie_navne.add("placeholder 2");
        liste_scenarie_navne.add("placeholder 3");
        liste_scenarie_navne.add("placeholder 1");
        liste_scenarie_navne.add("placeholder 2");
        liste_scenarie_navne.add("placeholder 3");
        liste_scenarie_navne.add("placeholder 1");
        liste_scenarie_navne.add("placeholder 2");
        liste_scenarie_navne.add("placeholder 3");
        liste_scenarie_navne.add("placeholder 1");
        liste_scenarie_navne.add("placeholder 2");
        liste_scenarie_navne.add("placeholder 3");
        liste_scenarie_navne.add("placeholder 1");
        liste_scenarie_navne.add("placeholder 2");
        liste_scenarie_navne.add("placeholder 3");
        liste_scenarie_navne.add("placeholder 1");
        liste_scenarie_navne.add("placeholder 2");
        liste_scenarie_navne.add("placeholder 3");
        liste_scenarie_navne.add("placeholder 1");
        liste_scenarie_navne.add("placeholder 2");
        liste_scenarie_navne.add("placeholder 3");
        liste_scenarie_navne.add("placeholder 1");
        liste_scenarie_navne.add("placeholder 2");
        liste_scenarie_navne.add("placeholder 3");
        liste_scenarie_navne.add("placeholder 1");
        liste_scenarie_navne.add("placeholder 2");
        liste_scenarie_navne.add("placeholder 3");



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
}
