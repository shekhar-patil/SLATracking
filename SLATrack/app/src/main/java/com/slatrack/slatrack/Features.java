package com.slatrack.slatrack;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Features extends AppCompatActivity {
    Button selfie;
    ImageView imgTakePic;
    private static final int CAM_Request = 1313;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_features);


        selfie = (Button) findViewById(R.id.selfie);
        imgTakePic = (ImageView) findViewById(R.id.selfie);
        selfie.setOnClickListener(new btnTakePhotoClicker());
        Intent intent = new Intent(Features.this,MainActivity.class);

        startActivity(intent);

}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAM_Request){
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imgTakePic.setImageBitmap(bitmap);
        }
    }

    public class btnTakePhotoClicker implements  Button.OnClickListener{

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent,CAM_Request);
        }
    }


}
