package com.example.user.ex11;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NfcF;
import android.os.Bundle;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URLEncoder;

public class MainActivity extends Activity {

    NfcAdapter mNfcAdapter; // NFC ?대뙌??
    PendingIntent mPendingIntent; // ?섏떊諛쏆? ?곗씠?곌? ??λ맂 ?명뀗??
    IntentFilter[] mIntentFilters; // ?명뀗???꾪꽣
    String[][] mNFCTechLists;
    Tag mytag;

    boolean write = false;
    String url= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //market://details id=com.everytime.v2


        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);

        Intent intent = new Intent(this, getClass());
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        mPendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        IntentFilter iFilter = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        try {
            iFilter.addDataType("text/plain");
            mIntentFilters = new IntentFilter[] { iFilter };
        } catch (Exception e) {
            e.printStackTrace();
        }
        mNFCTechLists = new String[][] { new String[] { NfcF.class.getName() } };


        String name = URLEncoder.encode("김현우");
                String tel = "010-5635-1845";
        String address = URLEncoder.encode("경기도");
                String memo = URLEncoder.encode("<h1>간사합니다</h1>");


        new HttpTask(new HttpTask.OnCompletionListener() {
            public void onComplete(String result)  {

                System.out.println(result);

                JSONObject root = null;
                try {
                    root = new JSONObject(result);
                    url = root.getString("url");
                    System.out.println("url->" + url);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).execute("http://i2max-ml.xyz:8080/missing.jsp?name=" +  name  + "&tel="  + tel + "&address=" + address + "&memo=" + memo );
    }


    public void onResume() {
        super.onResume();
        // ?깆씠 ?ㅽ뻾?좊븣 NFC ?대뙌?곕? ?쒖꽦???쒕떎
        if( mNfcAdapter != null )
            mNfcAdapter.enableForegroundDispatch(this, mPendingIntent, mIntentFilters, mNFCTechLists);

        // NFC ?쒓렇 ?ㅼ틪?쇰줈 ?깆씠 ?먮룞 ?ㅽ뻾?섏뿀?꾨븣
        if( NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction()) )
            // ?명뀗?몄뿉 ?ы븿???뺣낫瑜?遺꾩꽍?댁꽌 ?붾㈃???쒖떆
            onNewIntent(getIntent());
    }

    public void onPause() {
        super.onPause();
        // ?깆씠 醫낅즺?좊븣 NFC ?대뙌?곕? 鍮꾪솢?깊솕 ?쒕떎
        if( mNfcAdapter != null )
            mNfcAdapter.disableForegroundDispatch(this);
    }

    public void onNewIntent(Intent intent) {

        mytag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

        if ( write) {
            try {
                Uri uri =Uri.parse(url);
                NdefRecord recordNFC =NdefRecord.createUri(uri);
                if (recordNFC == null) System.out.println("error");

                NdefRecord[] records = {recordNFC};
                NdefMessage message = new NdefMessage(records);
                Ndef ndef = Ndef.get(mytag);
                ndef.connect();
                ndef.writeNdefMessage(message);
                ndef.close();
                write  = false;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (FormatException e) {
                e.printStackTrace();
            }
        }
    }

    public void btnWriteClick(View v) {
        write = true;
    }
}
