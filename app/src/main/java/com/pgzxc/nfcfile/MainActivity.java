package com.pgzxc.nfcfile;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.FileOutputStream;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity implements NfcAdapter.CreateBeamUrisCallback {
    private NfcAdapter mNfcAdapter;
    private PendingIntent mPendingIntent;
    private final String targetFilename = "/sdcard/temp_icon.png";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNfcAdapter = mNfcAdapter.getDefaultAdapter(this);
        mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
                getClass()), 0);

        try {
            InputStream is = getResources().getAssets().open("icon.png");
            FileOutputStream fos = new FileOutputStream(targetFilename);
            byte[] buffer = new byte[10000];
            int n = is.read(buffer);
            fos.write(buffer, 0, n);
            fos.close();
            is.close();
        } catch (Exception e) {

        }
        mNfcAdapter.setBeamPushUrisCallback(this, this);

    }

    @Override
    public Uri[] createBeamUris(NfcEvent event) {
        Uri[] uris = new Uri[1];
        Uri uri = Uri.parse("file://" + targetFilename);
        uris[0] = uri;
        return uris;
    }

}
