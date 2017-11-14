package com.example.user.ex8;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.tech.NfcF;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

    EditText etxtInputMessage, etxtTag;
    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;
    IntentFilter intentFilters[];
    String[][] nfcTagLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etxtInputMessage = (EditText)findViewById(R.id.etxtInputMessage);
        etxtTag = (EditText)findViewById(R.id.etxtTag);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        Log.d("ex8", "nfc adapter: " + nfcAdapter);

        Intent intent = new Intent(this, getClass());
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        IntentFilter intentFilter = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        try {
            intentFilter.addDataType("*/*");
            intentFilters = new IntentFilter[] {
                    intentFilter
            };
        }
        catch (Exception err) {
            Log.d("ex8", "Error: " + err.getMessage());
        }

        nfcTagLists = new String[][] {
                new String[] {
                        NfcF.class.getName()
                }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("ex8", "onresume");

        if(nfcAdapter != null) {
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilters, nfcTagLists);
        }
        if(NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
            onNewIntent(getIntent());
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d("ex8", "on new intent");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("ex8", "onpause");
    }

    public void btnOnWriteClick(View view ){

    }
}
