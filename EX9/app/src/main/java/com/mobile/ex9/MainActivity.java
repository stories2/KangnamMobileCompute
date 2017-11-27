package com.mobile.ex9;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NfcF;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.Arrays;

import static android.content.ContentValues.TAG;

public class MainActivity extends Activity {

    NfcAdapter nfcAdapter;
    PendingIntent pendingIntent;
    IntentFilter[] intentFilters;
    String[][] nfcTechList;
    Tag myTag;
    TextView txtNfc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtNfc = (TextView)findViewById(R.id.txtNfc);

        nfcAdapter = NfcAdapter.getDefaultAdapter(this);

        if(nfcAdapter == null) {
            Log.d(TAG, "NFC adapter is null");
            return;
        }
        else {
            Log.d(TAG, "You can use NFC adapter");
        }

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
            Log.e(TAG, "init intent filter is something wrong");
        }

        nfcTechList = new String[][]{
                new String[] {
                        NfcF.class.getName()
                }
        };
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.d(TAG, "intent action: " + getIntent().getAction());

        if(nfcAdapter != null) {
            nfcAdapter.enableForegroundDispatch(this, pendingIntent, intentFilters, nfcTechList);
        }
        if(NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
            Log.d(TAG, "on resume action data: " + getIntent().getAction());
            onNewIntent(getIntent());
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if(txtNfc != null) {
            txtNfc.setText("");
        }

        Log.d(TAG, "on new intent accepted");

        myTag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

        Log.d(TAG, "myTag: " + myTag.toString());

        Parcelable[] messages = intent.getParcelableArrayExtra(
                NfcAdapter.EXTRA_NDEF_MESSAGES
        );

        if(messages == null) {
            Log.d(TAG, "parcelable message is null");
            return;
        }
        Log.d(TAG, "parcelable message length: " + messages.length);
        int i;
        for(i = 0; i < messages.length; i += 1) {
            ParseNfcMessage((NdefMessage)messages[i]);
        }
    }

    public void ParseNfcMessage(NdefMessage message) {
        NdefRecord[] ndefRecords = message.getRecords();
        int i;
        for(i = 0; i < ndefRecords.length; i += 1) {
            String payloadString = "";
            NdefRecord ndefRecord = ndefRecords[i];
            byte[] payload = ndefRecord.getPayload();
            if(Arrays.equals(ndefRecord.getType(), NdefRecord.RTD_TEXT)) {
                payloadString = DecodePayloadText(payload);
                payloadString = "TEXT: " + payloadString;
            }
            else if(Arrays.equals(ndefRecord.getType(), NdefRecord.RTD_URI)) {
                payloadString = new String(payload, 0, payload.length);
                payloadString = "URL: " + payloadString;
            }
            Log.d(TAG, "parsed payload data: " + payloadString);
            if(txtNfc != null) {
                txtNfc.append(payloadString);
            }
        }
    }

    public String DecodePayloadText(byte[] payload) {
        String resultText = "";
        String textEncoding = ((payload[0] & 0200) == 0) ? "UTF-8" : "UTF-16";
        int textCodeLength = payload[0] & 0077;
        try {
            resultText = new String(payload, textCodeLength + 1, payload.length - textCodeLength - 1, textEncoding);
            Log.d(TAG, "decoded text: " + resultText);
        }
        catch (Exception err) {
            Log.d(TAG, "Error in decode payload text: " + err.getMessage());
        }
        return resultText;
    }

    public NdefRecord CreateNewRecord(String text) {
        try {
            String lang = "en";
            byte[] textBytes = text.getBytes();
            byte[] langBytes = lang.getBytes("US-ASCII");
            int langLength = langBytes.length;
            int textLength = textBytes.length;

            byte[] payload = new byte[1 + langLength + textLength];
            payload[0] = (byte)langLength;

            System.arraycopy(langBytes, 0, payload, 1             , langLength);
            System.arraycopy(textBytes, 0, payload, 1 + langLength, textLength);

            NdefRecord ndefRecord = new NdefRecord(NdefRecord.TNF_WELL_KNOWN, NdefRecord.RTD_TEXT, new byte[0], payload);
            return ndefRecord;
        }
        catch (Exception err) {
            Log.d(TAG, "Error in create new record " + err.getMessage());
        }
        return null;
    }

    public void btnOnWriteNewRecord(View view) {
        try {
            NdefRecord[] ndefRecords = {
                    CreateNewRecord("Hello world")
            };
            NdefMessage message = new NdefMessage(ndefRecords);
            Ndef ndef = Ndef.get(myTag);
            ndef.connect();
            ndef.writeNdefMessage(message);
            ndef.close();
        }
        catch (Exception err) {
            Log.d(TAG, "Error in write new record " + err.getMessage());
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(nfcAdapter != null) {
            nfcAdapter.disableForegroundDispatch(this);
        }
    }
}
