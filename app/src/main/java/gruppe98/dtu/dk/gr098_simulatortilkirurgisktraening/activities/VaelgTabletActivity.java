package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pGroup;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.apache.commons.lang3.SerializationUtils;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.R;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.adapters.PeerAdapter;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.adapters.SendScenarieRecyclerViewAdapter;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.adapters.ShowLogsRecyclerViewAdapter;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.adapters.VaelgTabletsRecyleViewAdapter;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.dal.DataHaandtering;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.dal.LogEntry;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.dal.OutcomeOptions;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.dal.Scenario;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.interfaces.IRecycleViewAdapterListener;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.interfaces.IWifiListener;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.objects.WifiP2P;

public class VaelgTabletActivity extends AppCompatActivity implements View.OnClickListener, IWifiListener, IRecycleViewAdapterListener {

    private static final int MY_PERMISSIONS_REQUEST = 0;
    int ButtonState = 0;
    Button btnFunktion;
    RecyclerView RV;
    VaelgTabletsRecyleViewAdapter rvaTablets;
    PeerAdapter rvaPeers;
    ShowLogsRecyclerViewAdapter rvaLogs;
    SendScenarieRecyclerViewAdapter rvaScenarier;

    String ReciverAddress;
    WifiP2P WP;
    ArrayList<LogEntry> DeviceLogs;
    ArrayList<Scenario> Brugsscenarier;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaelg_tablet);
        checkPermissions();

    }

    @Override
    protected void onStop() {
        super.onStop();
        WP.disableDiscovery();
        WP.unregisterReciever();
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

    private void init() {
        btnFunktion = findViewById(R.id.btnFunction);
        btnFunktion.setOnClickListener(this);

        //Test data
        DeviceLogs = new ArrayList<>();
        LogEntry test = new LogEntry();
        test.setComment("");
        test.setCompleted(System.currentTimeMillis());
        test.setStart(System.currentTimeMillis());
        test.setLoggedScenario(DataHaandtering.getInstance().hentScenarie("TestScenarie"));
        test.setOutcome(OutcomeOptions.SUCCESS);
        LogEntry test2 = new LogEntry();
        test2.setComment("");
        test2.setCompleted(System.currentTimeMillis());
        test2.setStart(System.currentTimeMillis());
        test2.setLoggedScenario(DataHaandtering.getInstance().hentScenarie("TestScenarie"));
        test2.setOutcome(OutcomeOptions.SUCCESS);
        DeviceLogs.add(test);
        DeviceLogs.add(test2);

        //Adapters
        rvaTablets = new VaelgTabletsRecyleViewAdapter(new ArrayList<WifiP2pDevice>(),this);
        rvaPeers = new PeerAdapter(this,new ArrayList<WifiP2pDevice>());
        rvaLogs = new ShowLogsRecyclerViewAdapter(DeviceLogs,this);
        rvaScenarier = new SendScenarieRecyclerViewAdapter(DataHaandtering.getInstance().hentAlleScenarier(),this);

        //Recycleview
        RV = findViewById(R.id.rvIndhold);
        RV.setHasFixedSize(true);
        RV.setLayoutManager(new LinearLayoutManager(this));

        WP = new WifiP2P(this);
        WP.enableDiscovery();
        WP.registerReciever(this);

        ChangeToMainView();
    }

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

    @Override
    public void onClick(View v) {
       switch (ButtonState) {
           case 0:
               ChangeToConnectTablet();
               break;
           case 1:
               //Should never end here / Button visibility is GONE
               break;
           case 2:
               break;
           case 3:
               break;
       }
    }

    private void ChangeToConnectTablet() {
        ButtonState = 1;
        btnFunktion.setVisibility(View.GONE);
        WP.enableDiscovery();
        RV.setAdapter(rvaPeers);
    }

    @Override
    public void WifiEnabled(boolean b) {
        if(!b)
            Toast.makeText(this,"Make sure wifi is enabled",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void P2PEnabled(boolean b) {
        if(!b)
            Toast.makeText(this,"P2P couldn't initialize",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void DiscoveryEnabled(boolean b) {
        /*if(b)
            Toast.makeText(this,"Started Discovery",Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this,"Discovery Disabled",Toast.LENGTH_SHORT).show();*/
    }

    @Override
    public void ChangesInPeersAvailable(List<WifiP2pDevice> listWPD) {
        rvaPeers.updateAdapterData(listWPD);
    }

    @Override
    public void PeerChosen(WifiP2pDevice WPD) {
        WP.connectToDevice(WPD);
        ChangeToMainView();
    }

    @Override
    public void DeviceConnected(boolean isGroupOwner, String groupOwnerAddress) { }

    @Override
    public void DeviceDisconnected() { }

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

    private void ChangeToMainView(){
        WP.getConnectedDevices();
        ButtonState = 0;
        btnFunktion.setVisibility(View.VISIBLE);
        WP.disableDiscovery();
        RV.setAdapter(rvaTablets);
    }

    @Override
    public void VaelgBrugsscenarie(String Id) {
        ButtonState = 2;
        btnFunktion.setVisibility(View.GONE);
        rvaScenarier.updateData(DataHaandtering.getInstance().hentAlleScenarier());
        RV.setAdapter(rvaScenarier);
        ReciverAddress = Id;
    }

    @Override
    public void SeLog(String Id) {
        ButtonState = 3;
        btnFunktion.setVisibility(View.GONE);
        rvaLogs.updateData(DeviceLogs);
        RV.setAdapter(rvaLogs);
    }

    @Override
    public void SletTablet(String Id) {
        //TODO disconnect fra device
    }

    @Override
    public void sendBrugsscenarie(Scenario brugsscencarie) {
        /*
        byte[] BB = ReciverAddress.getBytes();//ByteBuffer.allocate(1024)
                //.put(ReciverAddress.getBytes())
                //.put(brugsscencarie.toByteArray())
                //.array();
*/
        WP.sendMessage(SerializationUtils.serialize(brugsscencarie));
    }

    @Override
    public void onBackPressed() {
        if(ButtonState == 0)
            finish();
        else
            ChangeToMainView();
    }
}
