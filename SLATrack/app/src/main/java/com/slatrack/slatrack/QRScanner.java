package com.slatrack.slatrack;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRScanner extends Activity implements ZXingScannerView.ResultHandler {
    private ZXingScannerView mScannerView;
    Button b;
    String[] IMEI_Number = new String[2];
    String checkPoint;
    TelephonyManager telephonyManager;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscanner);
        telephonyManager = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();

        IMEI_Number[0] = telephonyManager.getDeviceId(1);
        IMEI_Number[1] = telephonyManager.getDeviceId(2);

    }



    //For scanning purpose-


    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    //To handle scanning result and IMEI number and display it-

    @Override
    public void handleResult(Result result) {
        Log.v("handleResult", result.getText());
        checkPoint = result.getText().toString();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan Result and IMEI number");
        builder.setMessage(checkPoint + "\n" + IMEI_Number[0].toString() + "\n" + IMEI_Number[1].toString());


        builder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {


                Intent intent = new Intent(QRScanner.this,Feedback.class);
                intent.putExtra("Checkpoint",checkPoint);
                intent.putExtra("IMEI_Num1",IMEI_Number[0]);
                intent.putExtra("IMEI_Num2",IMEI_Number[1]);
                startActivity(intent);
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }
}