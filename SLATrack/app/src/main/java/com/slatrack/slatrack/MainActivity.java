package com.slatrack.slatrack;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity{
    List<String> checkPt = new ArrayList<String>();
    List<String> IMEI_Number1 = new ArrayList<String>();
    List<String> IMEI_Number2 = new ArrayList<String>();
    int a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button b1,b2;

        b1 = (Button)findViewById(R.id.button1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,QRScanner.class);
                startActivity(intent);
                a = 1;
            }
        });


//            Intent intent1 = getIntent();
//            checkPt = intent1.getStringArrayListExtra("CheckPoint");
//            IMEI_Number1 = intent1.getStringArrayListExtra("IMEI_Number1");
//            IMEI_Number2 = intent1.getStringArrayListExtra("IMEI_Number2");
//            Intent intent = new Intent(MainActivity.this, Features.class);
//            startActivity(intent);


//        if(CheckPoint == null) {
//            Intent intent = getIntent();
//            CheckPoint = intent.getStringExtra("Checkpoint");
//            IMEI_Number[0] = intent.getStringExtra("IMEI_Num1");
//            IMEI_Number[1] = intent.getStringExtra("IMEI_Num2");
//            String res = CheckPoint + " " + IMEI_Number[0] + " " + IMEI_Number[1];
//            Toast.makeText(MainActivity.this, res, Toast.LENGTH_LONG).show();
//        }
        b1 = (Button)findViewById(R.id.button4);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SQLiteDB.class);
//                intent.putExtra("Checkpoint", (ArrayList<String>)checkPt);
//                intent.putExtra("IMEI_Num1",(ArrayList<String>)IMEI_Number1);
//                intent.putExtra("IMEI_Num2", (ArrayList<String>)IMEI_Number2);
                intent.putExtra("Checkpoint", "");
                intent.putExtra("IMEI_Num1","");
                intent.putExtra("IMEI_Num2", "");
                startActivity(intent);
            }
        });

    }
}
