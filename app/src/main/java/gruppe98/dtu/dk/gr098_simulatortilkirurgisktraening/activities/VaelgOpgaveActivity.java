package gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaScannerConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.R;
import gruppe98.dtu.dk.gr098_simulatortilkirurgisktraening.application.ApplicationSingleton;


public class VaelgOpgaveActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int EXTERNAL_STORAGE_PERMISSION_CODE = 42;
    ConstraintLayout clBrugssc;
    ConstraintLayout clForbindTablet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaelg_opgave);

        clBrugssc = findViewById(R.id.clBrugsscenairer);
        clBrugssc.setOnClickListener(this);

        clForbindTablet = findViewById(R.id.clTablet);
        clForbindTablet.setOnClickListener(this);

        initExternalStorage();
    }

    private void initExternalStorage() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_CODE);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if(requestCode == EXTERNAL_STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Loaded scenarios", Toast.LENGTH_SHORT).show();
                MediaScannerConnection.scanFile(this, new String[] {Environment.getExternalStorageDirectory().getAbsolutePath()}, null, null);
                ApplicationSingleton.getInstance().initExternalStorage();
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
}
