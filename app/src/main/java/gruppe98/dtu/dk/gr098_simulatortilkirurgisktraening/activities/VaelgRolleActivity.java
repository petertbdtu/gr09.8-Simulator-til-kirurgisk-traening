package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pGroup;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.R;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.activities.InsufflatorVisningActivity;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.activities.VaelgOpgaveActivity;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.application.ApplicationSingleton;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.interfaces.IWifiListener;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.objects.WifiP2P;

public class VaelgRolleActivity extends AppCompatActivity implements View.OnClickListener {

    ProgressDialog dlg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaelg_rolle);

        View clTemp = findViewById(R.id.clInsufflator);
        clTemp.setOnClickListener(this);

        clTemp = findViewById(R.id.clInstruktor);
        clTemp.setOnClickListener(this);

        closeConnections();
    }

    private void closeConnections() {
        dlg = new ProgressDialog(this);
        dlg.setCancelable(false);
        dlg.setMessage("Rebooting WiFi...");
        dlg.show();

        final WifiManager wf = (WifiManager) this.getApplicationContext().getSystemService(this.WIFI_SERVICE);

        wf.setWifiEnabled(false);

        new Handler().postDelayed(new Runnable() {public void run() {
            wf.setWifiEnabled(true);
            dlg.dismiss();
        }}, 5000);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clInsufflator:
                Intent intentInsufflatorVisning = new Intent(getApplicationContext(), InsufflatorVisningActivity.class);
                startActivity(intentInsufflatorVisning);
                break;
            case R.id.clInstruktor:
                Intent intentInstruktorVisning = new Intent(getApplicationContext(), VaelgOpgaveActivity.class);
                startActivity(intentInstruktorVisning);
                break;
            default:
                break;
        }

    }
}
