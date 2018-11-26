package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Set;

import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.dal.DataHaandtering;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.dal.Scenario;

public class VaelgTablet extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaelg_tablet);

        ArrayList<String> tmpNameList = new ArrayList<>();
        ArrayList<String> tmpAddrList = new ArrayList<>();

        Set<BluetoothDevice> btSet = BluetoothAdapter.getDefaultAdapter().getBondedDevices();

        for(BluetoothDevice device : btSet){
            tmpNameList.add(device.getName());
            tmpAddrList.add(device.getAddress());
        }

        aktiverRecyclerView(tmpNameList, tmpAddrList);
    }

    @Override
    public void onClick(View v) {
       Intent tabletAktiviteter = new Intent(this, TabletAktiviteter.class);
       startActivity(tabletAktiviteter);
    }

    private void aktiverRecyclerView(ArrayList<String> name, ArrayList<String> addr) {
        RecyclerView recyclerView = findViewById(R.id.m_list_tabletselect);
        VaelgTabletRecyclerViewAdapter adapter = new VaelgTabletRecyclerViewAdapter(name, addr, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}
