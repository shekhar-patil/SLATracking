package com.slatrack.slatrack;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.io.ByteArrayOutputStream;

public class Feedback extends AppCompatActivity {
    private RadioGroup rg1;
    private RadioGroup rg2;
    Button b1;

    String CheckPoint;
    String IMEI_Number_1;
    String IMEI_Number_2;
    String maintenance;
    String checking;
    byte [] byteArray;
    private RadioButton r1;
    private RadioButton r2;
    private static final int CAMERA_REQUEST = 1888;
    public ImageView imageView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Intent intent = getIntent();
        CheckPoint = intent.getStringExtra("Checkpoint");
        IMEI_Number_1 = intent.getStringExtra("IMEI_Num1");
        IMEI_Number_2 = intent.getStringExtra("IMEI_Num2");
        System.out.println(CheckPoint+"   "+IMEI_Number_1+"   "+IMEI_Number_2);
        rg1 = (RadioGroup)findViewById(R.id.maintenance);
        rg2 = (RadioGroup)findViewById(R.id.Checking);


        b1 = (Button)findViewById(R.id.Submit);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = rg1.getCheckedRadioButtonId();
                r1 = (RadioButton)rg1.findViewById(i);
                maintenance = r1.getText().toString();
                int j = rg2.getCheckedRadioButtonId();
                r2 = (RadioButton)rg2.findViewById(j);
                checking = r2.getText().toString();

                System.out.println("\nmaintenance :"+maintenance+"\nchecking :"+checking+"");

                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);



            }
        });

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
            byteArray = byteArrayOutputStream.toByteArray();
            Intent intent = new Intent(Feedback.this,SQLiteDB.class);
            intent.putExtra("Checkpoint",CheckPoint);
            intent.putExtra("IMEI_Num1",IMEI_Number_1);
            intent.putExtra("IMEI_Num2",IMEI_Number_2);
            intent.putExtra("maintenance",maintenance);
            intent.putExtra("checking",checking);
            //intent.putExtra("byteArray",byteArray);
            startActivity(intent);

        }
    }
}
