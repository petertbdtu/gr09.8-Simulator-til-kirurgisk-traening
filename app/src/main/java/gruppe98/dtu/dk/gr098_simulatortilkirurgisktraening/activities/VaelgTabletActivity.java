package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.activities;

import android.app.ProgressDialog;
import android.net.wifi.p2p.WifiP2pDevice;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.apache.commons.lang3.SerializationUtils;

import java.net.InetAddress;
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
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.objects.BroadcastSendReceiveThread;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.objects.CommunicationObject;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.objects.LogEntry;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.objects.Scenario;

public class VaelgTabletActivity extends AppCompatActivity implements View.OnClickListener, IWifiListener, IRecycleViewAdapterListener {


    private SendScenarioAdapter rvaScenarier;
    private PickTabletAdapter rvaTablets;
    private ShowLogsAdapter rvaLogs;
    private PeerAdapter rvaPeers;
    private ImageView btnFunktion;
    private TextView tvTitel;
    private RecyclerView RV;

    private String receiverAddress;
    private int ButtonState = 0;

    private ProgressDialog dlg;
    private String targetMacAddress;
    private boolean msgResponseRecieved;
    private Handler threadHandler;

    /////////////////////////////////////////
    //// Activity overrides /////////////////
    /////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaelg_tablet);

        //Initialize Adapters
        rvaTablets = new PickTabletAdapter(ApplicationSingleton.getInstance().getDevices(),this);
        rvaPeers = new PeerAdapter(this,new ArrayList<WifiP2pDevice>());
        rvaLogs = new ShowLogsAdapter(new ArrayList<LogEntry>(),this);
        rvaScenarier = new SendScenarioAdapter(ApplicationSingleton.getInstance().hentAlleScenarier(),this);

        //Initialize Recycleview
        RV = findViewById(R.id.rvIndhold);
        RV.setHasFixedSize(true);
        RV.setLayoutManager(new LinearLayoutManager(this));

        //Initialize btn & titel
        tvTitel = findViewById(R.id.tvVaelgTabletTitel);
        btnFunktion = findViewById(R.id.btnFunction);
        btnFunktion.setOnClickListener(this);

        threadHandler = createThreadHandler();
        ApplicationSingleton.getInstance().WifiP2P.registerReceiver(this);
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
    protected void onPause() {
        super.onPause();
        if(ApplicationSingleton.getInstance().WifiP2P != null)
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
        if(ButtonState == 0)
            ChangeToConnectTablet();
    }

    /////////////////////////////////////////
    //// Activity methods ///////////////////
    /////////////////////////////////////////

    private void ChangeToMainView(){
        ButtonState = 0;
        btnFunktion.setVisibility(View.VISIBLE);
        tvTitel.setText("Forbundne enheder");
        ApplicationSingleton.getInstance().WifiP2P.enableDiscovery();
        RV.setAdapter(rvaTablets);
    }

    private void ChangeToConnectTablet() {
        ButtonState = 1;
        btnFunktion.setVisibility(View.GONE);
        tvTitel.setText("Forbind til enhed");
        ApplicationSingleton.getInstance().WifiP2P.enableDiscovery();
        RV.setAdapter(rvaPeers);
    }

    private void ChangeToSendBrugsscenarie() {
        ButtonState = 2;
        btnFunktion.setVisibility(View.GONE);
        tvTitel.setText("Send scenarie");
        rvaScenarier.updateData(ApplicationSingleton.getInstance().hentAlleScenarier());
        RV.setAdapter(rvaScenarier);
    }

    private void ChangeToSeeLogs(String id) {
        ButtonState = 3;
        btnFunktion.setVisibility(View.GONE);
        tvTitel.setText("Logs");
        System.out.println("KIGHER | ChangeToLogs | " + ApplicationSingleton.getInstance().hentLogs(id));
        rvaLogs.updateData(ApplicationSingleton.getInstance().hentLogs(id));
        RV.setAdapter(rvaLogs);
    }

    private void waitForResponse(final byte[] bMsgToSend) {
        if(!msgResponseRecieved) {
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    if(!msgResponseRecieved) {
                        ApplicationSingleton.getInstance().broadcastThread.write(bMsgToSend);
                        waitForResponse(bMsgToSend);
                    }
                }
            }, 200);
        } else {
            dlg.dismiss();
        }
    }

    private android.os.Handler createThreadHandler() {
        return new android.os.Handler(new android.os.Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch(msg.what) {
                    case ApplicationSingleton.MESSAGE_READ:
                        byte[] readBuffer = (byte[]) msg.obj;
                        CommunicationObject CO = SerializationUtils.deserialize(readBuffer);
                        if(CO.getSenderMacAddress().equals(receiverAddress) && CO.getRecipientMacAddress().equals(ApplicationSingleton.getInstance().WifiP2P.getMyMacAddress())) {
                            msgResponseRecieved = true;
                        }
                        break;
                }
                return true;
            }
        });
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
    public void PeerChosen(final WifiP2pDevice WPD, final boolean suppressdlg) {
        ApplicationSingleton.getInstance().WifiP2P.connectToDevice(WPD);
        targetMacAddress = WPD.deviceAddress;
        if(!suppressdlg) {
            dlg = new ProgressDialog(this);
            dlg.setCancelable(false);
            dlg.setMessage("Waiting for response...");
            dlg.show();
        }

        new Handler().postDelayed(new Runnable() {
            public void run() {
                if(!suppressdlg) {
                    dlg.dismiss();
                }
                if(!ApplicationSingleton.getInstance().connectedDevices.contains(WPD.deviceAddress)) {
                    ApplicationSingleton.getInstance().WifiP2P.cancelConnection(WPD);
                }
            }
        }, 15000);

    }

    @Override
    public void sendBrugsscenarie(String scenarioName, Scenario scencario) {
        System.out.println("KIGHER | SendScenario1 | " + ApplicationSingleton.getInstance().hentLogs(receiverAddress));
        msgResponseRecieved = false;
        CommunicationObject CO = new CommunicationObject(receiverAddress,ApplicationSingleton.getInstance().WifiP2P.getMyMacAddress(),scencario);
        ApplicationSingleton.getInstance().addLog(receiverAddress,scenarioName);
        System.out.println("KIGHER | SendScenario1 | " + ApplicationSingleton.getInstance().hentLogs(receiverAddress));
        final byte[] bMsgToSend = SerializationUtils.serialize(CO);

        ApplicationSingleton.getInstance().broadcastThread.write(bMsgToSend);
        waitForResponse(bMsgToSend);

        dlg = new ProgressDialog(this);
        dlg.setCancelable(false);
        dlg.setMessage("Waiting for response...");
        dlg.show();

        new Handler().postDelayed(new Runnable() {
            public void run() {
                msgResponseRecieved = true;
                dlg.dismiss();
            }
        }, 3000);
    }

    @Override
    public void fjernBrugsscenarie(String scenarieNavn) { }

    @Override
    public void redigerScenarie(String scenarieNavn) { }

    @Override
    public void deleteDevice(String deviceAddress) { }

    /////////////////////////////////////////
    //// WifiP2P overrides //////////////////
    /////////////////////////////////////////

    @Override
    public void ChangesInPeersAvailable(List<WifiP2pDevice> listWPD) {
        //Update list of connected peers / devices
        for(String str : ApplicationSingleton.getInstance().connectedDevices){
            boolean found = false;
            for(WifiP2pDevice wpd : listWPD){
                if(str.equals(wpd.deviceAddress)){
                    found = true;
                    if(!(wpd.status == WifiP2pDevice.CONNECTED)){
                        ApplicationSingleton.getInstance().connectedDevices.remove(str);
                    }
                }
            }
            if(!found){
                ApplicationSingleton.getInstance().connectedDevices.remove(str);
            }
        }

        //Update peer list and connect to known devices
        rvaPeers.updateAdapterData(listWPD);
        for(WifiP2pDevice wpd : listWPD){
            switch (wpd.status) {

                //Save device connection status and add to persistent list of known devices
                case WifiP2pDevice.CONNECTED:
                    if(!ApplicationSingleton.getInstance().connectedDevices.contains(wpd.deviceAddress)) {
                        ApplicationSingleton.getInstance().connectedDevices.add(wpd.deviceAddress);
                    }
                    if(wpd.deviceAddress.equals(targetMacAddress)) {
                        targetMacAddress = null;
                        if(dlg != null)
                            dlg.dismiss();
                        ApplicationSingleton.getInstance().addDevice(wpd.deviceAddress, wpd.deviceName);
                        rvaTablets.updateData(ApplicationSingleton.getInstance().getDevices());
                        ChangeToMainView();
                    }
                    break;

                //Connect to device if known
                case WifiP2pDevice.AVAILABLE:
                    if(ApplicationSingleton.getInstance().getDevices().containsKey(wpd.deviceAddress)){
                        PeerChosen(wpd, true);
                    }
                    break;
            }
        }

        //Update tablet LEDs with possible new connected devices
        rvaTablets.updateLEDs(ApplicationSingleton.getInstance().connectedDevices);
    }

    @Override
    public void groupFormed(InetAddress broadcastAddress) {
        if(ApplicationSingleton.getInstance().broadcastThread == null) {
            ApplicationSingleton.getInstance().broadcastThread = new BroadcastSendReceiveThread(threadHandler, broadcastAddress);
            ApplicationSingleton.getInstance().broadcastThread.start();
        } else {
            ApplicationSingleton.getInstance().broadcastThread.updateBroadcastAddress(broadcastAddress);
        }
    }

    @Override
    public void SetDeviceName(String name) { }

    @Override
    public void OnGroupCreated(boolean b) {

    }
}
