package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.net.wifi.p2p.WifiP2pDevice;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.apache.commons.lang3.SerializationUtils;

import java.net.InetAddress;
import java.util.List;

import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.R;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.application.ApplicationSingleton;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.fragments.InsufflatorFragment;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.fragments.VisningAfventerFragment;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.interfaces.IWifiListener;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.objects.BroadcastSendReceiveThread;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.objects.CommunicationObject;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.objects.LoopMediaPlayer;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.objects.Scenario;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.objects.WifiP2P;

public class InsufflatorVisningActivity extends AppCompatActivity implements IWifiListener {

    private static final int MY_PERMISSIONS_REQUEST = 1;
    private String deviceName;
    private LoopMediaPlayer noiseSound;
    private Handler threadHandler;

    /////////////////////////////////////////
    //// Activity overrides /////////////////
    /////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insufflator_visning);

        ApplicationSingleton.getInstance().activeScenario = new Scenario();
        threadHandler = createThreadHandler();
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        checkPermissions();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(ApplicationSingleton.getInstance().WifiP2P != null) {
            ApplicationSingleton.getInstance().WifiP2P.registerReceiver(this);
            ApplicationSingleton.getInstance().WifiP2P.enableDiscovery();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(ApplicationSingleton.getInstance().WifiP2P != null) {
            ApplicationSingleton.getInstance().WifiP2P.unRegisterReceiver();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if(requestCode == MY_PERMISSIONS_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if(ApplicationSingleton.getInstance().WifiP2P == null) {
                    ApplicationSingleton.getInstance().WifiP2P = new WifiP2P(this, false);
                } else {
                    ApplicationSingleton.getInstance().WifiP2P.registerReceiver(this);
                }
                ApplicationSingleton.getInstance().WifiP2P.enableDiscovery();
            } else {
                Toast.makeText(this,"Please restart app, and accept permissions",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure?");
        builder.setMessage("Do you want to leave?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                ApplicationSingleton.getInstance().WifiP2P.close();
                ApplicationSingleton.getInstance().WifiP2P = null;
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //ApplicationSingleton.getInstance().WifiP2P.unRegisterReceiver();
        if (noiseSound != null) {
            noiseSound.release();
            noiseSound = null;
        }
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

    private android.os.Handler createThreadHandler() {
        return new android.os.Handler(new android.os.Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                switch(msg.what) {
                    case ApplicationSingleton.MESSAGE_READ:
                        byte[] readBuffer = (byte[]) msg.obj;
                        CommunicationObject CO = SerializationUtils.deserialize(readBuffer);
                        if(CO.getRecipientMacAddress().toUpperCase().equals(ApplicationSingleton.getInstance().WifiP2P.getMyMacAddress().toUpperCase())) {
                            CO.setScenario(null);
                            String recipient = CO.getSenderMacAddress();
                            CO.setSenderMacAddress(CO.getRecipientMacAddress());
                            CO.setRecipientMacAddress(recipient);
                            ApplicationSingleton.getInstance().broadcastThread.write(SerializationUtils.serialize(CO));
                            changeToInsufflatorFragment(readBuffer);
                        }
                        break;
                }
                return true;
            }
        });
    }

    private void changeToInsufflatorFragment(byte[] msg) {
        Fragment fragment = new InsufflatorFragment();
        Bundle args = new Bundle();
        args.putBoolean("erInstruktor", false);
        args.putByteArray("scenarieByteArray", msg);
        fragment.setArguments(args);

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();


        // Start støj ved første brugsscenarie
        if (noiseSound == null) {
            noiseSound = LoopMediaPlayer.create(this, R.raw.noise);
        }
    }


    /////////////////////////////////////////
    //// WifiP2P overrides //////////////////
    /////////////////////////////////////////

    @Override
    public void ChangesInPeersAvailable(List<WifiP2pDevice> listWPD) { }

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
    public void SetDeviceName(String name) {
        if (deviceName == null) {
            deviceName = name;
            Fragment fragment = new VisningAfventerFragment();
            Bundle args = new Bundle();
            args.putString("deviceName", name);
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .commit();
        }
    }

    @Override
    public void OnGroupCreated(boolean b) {

    }
}
