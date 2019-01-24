package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.net.wifi.p2p.WifiP2pDevice;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.net.InetAddress;
import java.util.List;

import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.R;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.application.ApplicationSingleton;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.interfaces.IWifiListener;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.objects.WifiP2P;


public class VaelgOpgaveActivity extends AppCompatActivity implements View.OnClickListener, IWifiListener {

  ConstraintLayout clBrugssc;
  ConstraintLayout clForbindTablet;

  private static final int MY_PERMISSIONS_REQUEST = 0;

  private ProgressDialog dlg;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_vaelg_opgave);

    clBrugssc = findViewById(R.id.clBrugsscenairer);
    clBrugssc.setOnClickListener(this);

    clForbindTablet = findViewById(R.id.clTablet);
    clForbindTablet.setOnClickListener(this);



    dlg = new ProgressDialog(this);
    dlg.setCancelable(false);
    dlg.setMessage("Initialising P2P...");
    dlg.show();

    new Handler().postDelayed(new Runnable() {public void run() { dlg.dismiss(); }}, 5000);

    checkPermissions();
  }


  @Override
  public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
    if(requestCode == MY_PERMISSIONS_REQUEST) {
      if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
        init();
        dlg.dismiss();
        MediaScannerConnection.scanFile(this, new String[] {Environment.getExternalStorageDirectory().getAbsolutePath()}, null, null);
      } else {
        Toast.makeText(this,"Please restart app, and accept permissions",Toast.LENGTH_SHORT).show();
      }
    }
  }


  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.clBrugsscenairer:
        Intent BrugssecenairerVisning = new Intent(getApplicationContext(), VaelgScenarieActivity.class);
        startActivity(BrugssecenairerVisning);
        break;
      case R.id.clTablet:
        Intent ForbinTTabletVisning = new Intent(getApplicationContext(), VaelgTabletActivity.class);
        startActivity(ForbinTTabletVisning);
        break;
      default:
        break;
    }
  }


  private void init() {
    //Initialize WifiP2P
    if(ApplicationSingleton.getInstance().WifiP2P == null) {
      ApplicationSingleton.getInstance().WifiP2P = new WifiP2P(this, true);
      ApplicationSingleton.getInstance().WifiP2P.enableDiscovery();
    }
  }


  private void checkPermissions() {
    String[] permissions = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION
            , Manifest.permission.ACCESS_WIFI_STATE
            , Manifest.permission.CHANGE_WIFI_STATE
            , Manifest.permission.INTERNET
            , Manifest.permission.ACCESS_NETWORK_STATE
            , Manifest.permission.CHANGE_NETWORK_STATE
            , Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    ActivityCompat.requestPermissions(this, permissions, MY_PERMISSIONS_REQUEST);
  }

  @Override
  public void ChangesInPeersAvailable(List<WifiP2pDevice> listWPD) {

  }

  @Override
  public void groupFormed(InetAddress broadcastAddress) {

  }

  @Override
  public void SetDeviceName(String name) {

  }

  @Override
  public void OnGroupCreated(boolean b) {
    if(b){
      dlg.dismiss();
    } else {
      dlg.dismiss();
      finish();
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
}
