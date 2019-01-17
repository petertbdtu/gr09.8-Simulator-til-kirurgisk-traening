package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pGroup;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
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
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.objects.OutcomeOptions;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.objects.Scenario;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.objects.WifiP2P;

public class VaelgTabletActivity extends AppCompatActivity implements View.OnClickListener, IWifiListener, IRecycleViewAdapterListener {

    private static final int MY_PERMISSIONS_REQUEST = 0;

    private SendScenarioAdapter rvaScenarier;
    private PickTabletAdapter rvaTablets;
    private ShowLogsAdapter rvaLogs;
    private PeerAdapter rvaPeers;
    private Button btnFunktion;
    private RecyclerView RV;

    private ArrayList<LogEntry> DeviceLogs;
    private String receiverAddress;
    private int ButtonState = 0;
    private WifiP2P wp;

    /////////////////////////////////////////
    //// Activity overrides /////////////////
    /////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaelg_tablet);
        checkPermissions();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(wp != null)
            wp.registerReceiver(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if(requestCode == MY_PERMISSIONS_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                init();
            } else {
                Toast.makeText(this,"Please restart app, and accept permissions",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        ApplicationSingleton.getInstance().WifiP2P = wp;
        wp.unRegisterReceiver();
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
        if(ButtonState == 0)
            ChangeToConnectTablet();
    }

    /////////////////////////////////////////
    //// Activity methods ///////////////////
    /////////////////////////////////////////

    private void checkPermissions() {
        String[] permissions = new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION
                , Manifest.permission.ACCESS_WIFI_STATE
                , Manifest.permission.CHANGE_WIFI_STATE
                , Manifest.permission.INTERNET
                , Manifest.permission.ACCESS_NETWORK_STATE
                , Manifest.permission.CHANGE_NETWORK_STATE
        };
        ActivityCompat.requestPermissions(this, permissions, MY_PERMISSIONS_REQUEST);
    }

    private void init() {
        //Initialize button
        btnFunktion = findViewById(R.id.btnFunction);
        btnFunktion.setOnClickListener(this);

        //Test data
        DeviceLogs = new ArrayList<>();
        LogEntry test = new LogEntry();
        test.setComment("");
        test.setCompleted(System.currentTimeMillis());
        test.setStart(System.currentTimeMillis());
        test.setScenarioNavn("TestScenarie");
        test.setOutcome(OutcomeOptions.SUCCESS);
        LogEntry test2 = new LogEntry();
        test2.setComment("");
        test2.setCompleted(System.currentTimeMillis());
        test2.setStart(System.currentTimeMillis());
        test2.setScenarioNavn("TestScenarie");
        test2.setOutcome(OutcomeOptions.SUCCESS);
        DeviceLogs.add(test);
        DeviceLogs.add(test2);

        //Initialize Adapters
        rvaTablets = new PickTabletAdapter(new ArrayList<WifiP2pDevice>(),this);
        rvaPeers = new PeerAdapter(this,new ArrayList<WifiP2pDevice>());
        rvaLogs = new ShowLogsAdapter(DeviceLogs,this);
        rvaScenarier = new SendScenarioAdapter(ApplicationSingleton.getInstance().hentAlleScenarier(),this);

        //Initialize Recycleview
        RV = findViewById(R.id.rvIndhold);
        RV.setHasFixedSize(true);
        RV.setLayoutManager(new LinearLayoutManager(this));

        //Initialize WifiP2P
        if(ApplicationSingleton.getInstance().WifiP2P == null) {
            wp = new WifiP2P(this, true);
            ApplicationSingleton.getInstance().WifiP2P = wp;
        } else {
            wp = ApplicationSingleton.getInstance().WifiP2P;
            wp.registerReceiver(this);
        }
        //Set start adapter
        ChangeToMainView();
    }

    private void ChangeToMainView(){
        wp.getConnectedDevices();
        ButtonState = 0;
        btnFunktion.setVisibility(View.VISIBLE);
        wp.disableDiscovery();
        RV.setAdapter(rvaTablets);
    }

    private void ChangeToConnectTablet() {
        ButtonState = 1;
        btnFunktion.setVisibility(View.GONE);
        wp.enableDiscovery();
        RV.setAdapter(rvaPeers);
    }

    /////////////////////////////////////////
    //// Adapter overrides //////////////////
    /////////////////////////////////////////

    @Override
    public void VaelgBrugsscenarie(String Id) {
        ButtonState = 2;
        btnFunktion.setVisibility(View.GONE);
        rvaScenarier.updateData(ApplicationSingleton.getInstance().hentAlleScenarier());
        RV.setAdapter(rvaScenarier);
        receiverAddress = Id;
    }

    @Override
    public void SeLog(String Id) {
        ButtonState = 3;
        btnFunktion.setVisibility(View.GONE);
        rvaLogs.updateData(DeviceLogs);
        RV.setAdapter(rvaLogs);
    }

    @Override
    public void PeerChosen(WifiP2pDevice WPD) {
        wp.connectToDevice(WPD);
        ChangeToMainView();
    }

    @Override
    public void sendBrugsscenarie(Scenario brugsscencarie) {
        CommunicationObject CO = new CommunicationObject(receiverAddress,wp.getMyMacAddress(),brugsscencarie);
        byte[] tmp = SerializationUtils.serialize(CO);
        wp.sendMessage(tmp);
    }

    @Override
    public void redigerScenarie(String scenarieNavn) { }

    /////////////////////////////////////////
    //// WifiP2P overrides //////////////////
    /////////////////////////////////////////

    @Override
    public void DiscoveryEnabled(boolean b) { }

    @Override
    public void ChangesInPeersAvailable(List<WifiP2pDevice> listWPD) { rvaPeers.updateAdapterData(listWPD); }

    @Override
    public void DeviceConnected() { }

    @Override
    public void DeviceDisconnected() { wp.getConnectedDevices(); }

    @Override
    public void MessageReceived(byte[] msg) { }

    @Override
    public void GroupInfoUpdate(WifiP2pGroup WPG) {
        if(WPG != null) {
            WPG.getClientList();
            ArrayList<WifiP2pDevice> tmpList = new ArrayList<>(WPG.getClientList());
            rvaTablets.updateData(tmpList);
        }
    }
}
