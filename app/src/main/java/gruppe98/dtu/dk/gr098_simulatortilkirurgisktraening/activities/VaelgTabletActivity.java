package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.activities;

import android.app.ProgressDialog;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pGroup;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.lang3.SerializationUtils;

import java.util.ArrayList;
import java.util.List;

import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.R;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.adapters.PeerAdapter;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.adapters.PickTabletAdapter;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.adapters.SendScenarioAdapter;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.adapters.ShowLogsAdapter;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.application.ApplicationSingleton;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.interfaces.IRecycleViewAdapterListener;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.interfaces.IWifiListener;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.objects.CommunicationObject;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.objects.LogEntry;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.objects.Scenario;

public class VaelgTabletActivity extends AppCompatActivity implements View.OnClickListener, IWifiListener, IRecycleViewAdapterListener {


    private SendScenarioAdapter rvaScenarier;
    private PickTabletAdapter rvaTablets;
    private ShowLogsAdapter rvaLogs;
    private PeerAdapter rvaPeers;
    private Button btnFunktion;
    private TextView tvTitel;
    private RecyclerView RV;



    private ArrayList<LogEntry> DeviceLogs;
    private String receiverAddress;
    private int ButtonState = 0;

    private ProgressDialog dlg;
    private String targetMacAddress;

    /////////////////////////////////////////
    //// Activity overrides /////////////////
    /////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaelg_tablet);

        //Initialize Adapters
        rvaTablets = new PickTabletAdapter(ApplicationSingleton.getInstance().getKnownDevices(),this);
        rvaPeers = new PeerAdapter(this,new ArrayList<WifiP2pDevice>());
        rvaLogs = new ShowLogsAdapter(DeviceLogs,this);
        rvaScenarier = new SendScenarioAdapter(ApplicationSingleton.getInstance().hentAlleScenarier(),this);

        //Initialize Recycleview
        RV = findViewById(R.id.rvIndhold);
        RV.setHasFixedSize(true);
        RV.setLayoutManager(new LinearLayoutManager(this));

        //Initialize btn & titel
        tvTitel = findViewById(R.id.tvVaelgTabletTitel);
        btnFunktion = findViewById(R.id.btnFunction);
        btnFunktion.setOnClickListener(this);

        //Set start adapter
        ChangeToMainView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(ApplicationSingleton.getInstance().WifiP2P != null)
            ApplicationSingleton.getInstance().WifiP2P.registerReceiver(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        ApplicationSingleton.getInstance().WifiP2P.unRegisterReceiver();
    }

    @Override
    public void onBackPressed() {
        if(ButtonState == 0)
            finish();
        else
            ChangeToMainView();
    }

    /////////////////////////////////////////
    //// OnClick override ///////////////////
    /////////////////////////////////////////

    @Override
    public void onClick(View v) {
        switch (ButtonState) {
            case 0 :
                ChangeToConnectTablet();
                break;
            case 1 :
                // something
                break;
            case 2 :
                // Something
                break;
            case 3 :
                // Something
                break;
        }
    }

    /////////////////////////////////////////
    //// Activity methods ///////////////////
    /////////////////////////////////////////

    private void ChangeToMainView(){
        ButtonState = 0;
        btnFunktion.setVisibility(View.VISIBLE);
        tvTitel.setText("Forbundne visninger");
        ApplicationSingleton.getInstance().WifiP2P.getConnectedDevices();
        ApplicationSingleton.getInstance().WifiP2P.disableDiscovery();
        RV.setAdapter(rvaTablets);
    }

    private void ChangeToConnectTablet() {
        ButtonState = 1;
        btnFunktion.setVisibility(View.GONE);
        tvTitel.setText("Forbind til visning");
        ApplicationSingleton.getInstance().WifiP2P.enableDiscovery();
        RV.setAdapter(rvaPeers);
    }

    private void ChangeToSendBrugsscenarie() {
        ButtonState = 2;
        btnFunktion.setVisibility(View.GONE);
        tvTitel.setText("Send brugsscenarie");
        rvaScenarier.updateData(ApplicationSingleton.getInstance().hentAlleScenarier());
        RV.setAdapter(rvaScenarier);
    }

    private void ChangeToSeeLogs(String id) {
        ButtonState = 3;
        btnFunktion.setVisibility(View.GONE);
        tvTitel.setText("Logs");
        rvaLogs.updateData(DeviceLogs);
        RV.setAdapter(rvaLogs);
    }

    /////////////////////////////////////////
    //// Adapter overrides //////////////////
    /////////////////////////////////////////

    @Override
    public void VaelgBrugsscenarie(String Id) {
        receiverAddress = Id;
        ChangeToSendBrugsscenarie();
    }

    @Override
    public void SeLog(String Id) {
        ChangeToSeeLogs(Id);
    }

    @Override
    public void PeerChosen(final WifiP2pDevice WPD) {
        ApplicationSingleton.getInstance().WifiP2P.connectToDevice(WPD);
        targetMacAddress = WPD.deviceAddress;
        dlg = new ProgressDialog(this);
        dlg.setCancelable(false);
        dlg.setMessage("Waiting for response...");
        dlg.show();

      new Handler().postDelayed(new Runnable() {
        public void run() {
          dlg.dismiss();
          ApplicationSingleton.getInstance().WifiP2P.cancelConnection(WPD);
        }
      }, 10000);

        //ChangeToMainView();
    }

    @Override
    public void sendBrugsscenarie(Scenario brugsscencarie) {
        CommunicationObject CO = new CommunicationObject(receiverAddress,ApplicationSingleton.getInstance().WifiP2P.getMyMacAddress(),brugsscencarie);
        byte[] tmp = SerializationUtils.serialize(CO);
        ApplicationSingleton.getInstance().WifiP2P.sendMessage(tmp);
    }

    @Override
    public void redigerScenarie(String scenarieNavn) { }

    @Override
    public void deleteDevice(String deviceAddress) { }

    /////////////////////////////////////////
    //// WifiP2P overrides //////////////////
    /////////////////////////////////////////

    @Override
    public void DiscoveryEnabled(boolean b) { }

    @Override
    public void ChangesInPeersAvailable(List<WifiP2pDevice> listWPD) {
      rvaPeers.updateAdapterData(listWPD);
      for(WifiP2pDevice wpd : listWPD){
        if(wpd.deviceAddress.equals(targetMacAddress)){
          switch (wpd.status) {
            case WifiP2pDevice.CONNECTED:
              targetMacAddress = null;
              dlg.dismiss();
              ApplicationSingleton.getInstance().addKnownDevice(wpd.deviceAddress,wpd.deviceName);
              rvaTablets.updateData(ApplicationSingleton.getInstance().getKnownDevices());
              ChangeToMainView();
              break;
            case WifiP2pDevice.FAILED:
              dlg.dismiss();
              Toast.makeText(this, "Failed to connect", Toast.LENGTH_SHORT).show();
              break;
            case WifiP2pDevice.UNAVAILABLE:
              dlg.dismiss();
              Toast.makeText(this, "Failed to connect", Toast.LENGTH_SHORT).show();
              break;
          }
        }
      }
    }

    @Override
    public void DeviceConnected() { }

    @Override
    public void DeviceDisconnected() { ApplicationSingleton.getInstance().WifiP2P.getConnectedDevices(); }

    @Override
    public void MessageReceived(byte[] msg) { }

    @Override
    public void GroupInfoUpdate(WifiP2pGroup WPG) {
        if(WPG != null) {
            WPG.getClientList();
            ArrayList<WifiP2pDevice> tmpList = new ArrayList<>(WPG.getClientList());
            rvaTablets.updateLEDs(tmpList);
        }
    }

    @Override
    public void SetDeviceName(String name) { }

    @Override
    public void OnGroupCreated(boolean b) { }
}
