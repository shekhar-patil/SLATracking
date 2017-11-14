package com.slatrack.slatrack;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class ConnectServer extends AppCompatActivity {

    List<String> CheckPoint = new ArrayList<String>();
    List<String> IMEI_Number1 = new ArrayList<String>();
    List<String> IMEI_Number2 = new ArrayList<String>();
    List<String> Maintenance = new ArrayList<String>();
    List<String> Checking = new ArrayList<String>();
    //    byte[] ByteArray = new byte[0];
//    byte [] byteArray;
    String checkpoint,IMEI_Num1,IMEI_Num2,maintenance,checking;
    SQLiteDB sqLiteDB = new SQLiteDB();
    SQLiteDatabase sqLiteDatabase;
    String o;
    SQLiteDB.DataBaseSQLite dataBaseSQLite = sqLiteDB.new DataBaseSQLite(sqLiteDB,"SLATrack",o,5);
    int k;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_server);
        System.out.println("hei1");
        Intent intent = getIntent();
        CheckPoint = intent.getStringArrayListExtra("CheckPoint");
        IMEI_Number1 = intent.getStringArrayListExtra("IMEI_Number1");
        IMEI_Number2 = intent.getStringArrayListExtra("IMEI_Number2");
        Maintenance = intent.getStringArrayListExtra("Maintenance");
        Checking = intent.getStringArrayListExtra("Checking");
        //ByteArray = intent.getByteArrayExtra("ByteArray");

        for (int i=0;i<CheckPoint.size();i++){
            System.out.println(CheckPoint.get(i));
            System.out.println(IMEI_Number1.get(i));
            System.out.println(IMEI_Number2.get(i));
        }

        //dataBaseSQLite.onUpgrade(sqLiteDatabase,1,2);

        k = CheckPoint.size();
        System.out.println(k);
        for (int i=0; i<CheckPoint.size(); i++){
            checkpoint = CheckPoint.get(i);
            IMEI_Num1 = IMEI_Number1.get(i);
            IMEI_Num2 = IMEI_Number2.get(i);
            maintenance = Maintenance.get(i);
            checking = Checking.get(i);
            System.out.println(checkpoint+IMEI_Num1+IMEI_Num2+maintenance+checking);
            InsertOnServer insertOnServer = new InsertOnServer(this);
            insertOnServer.execute(checkpoint,IMEI_Num1,IMEI_Num2,maintenance,checking);
            k--;
            System.out.println(k);

        }
        Intent intent1 = new Intent(ConnectServer.this,MainActivity.class);
        startActivity(intent1);

    }


    public class InsertOnServer extends AsyncTask<String, Void, String> {
        private Context context;

        public InsertOnServer(Context context) {
            this.context = context;
        }
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(String... arg0) {
            String CheckPoint = arg0[0];
            String IMEI_Number1 = arg0[1];
            String IMEI_Number2 = arg0[2];
            String maintenance = arg0[3];
            String checking = arg0[4];
            String link;
            String data;
            BufferedReader bufferedReader;
            String result;
            try {

                data = "?Check_Point=" + URLEncoder.encode(CheckPoint, "UTF-8");
                data += "&IMEI_Number_1=" + URLEncoder.encode(IMEI_Number1, "UTF-8");
                data += "&IMEI_Number_2=" + URLEncoder.encode(IMEI_Number2, "UTF-8");
                data += "&maintenance=" + URLEncoder.encode(maintenance, "UTF-8");
                data += "&checking=" + URLEncoder.encode(checking, "UTF-8");
                link = "http://192.168.43.78/Signup.php" + data;

                URL url = new URL(link);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                result = bufferedReader.readLine();
                return result;

            } catch (Exception e) {

                return new String("Exception: " + e.getMessage());

            }
        }
        @Override
        protected void onPostExecute(String result) {
            String jsonStr = result;
            if (jsonStr != null) {
                Toast.makeText(context,result,Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);
                    String query_result = jsonObj.getString("query_result");

                    if (query_result.equals("SUCCESS")) {

                        Toast.makeText(context, "Data inserted successfully. Signup successful.", Toast.LENGTH_SHORT).show();

                    } else if (query_result.equals("FAILURE")) {

                        Toast.makeText(context, "Data could not be inserted. Signup failed.", Toast.LENGTH_SHORT).show();

                    } else {

                        Toast.makeText(context, "Couldn't connect to remote database.", Toast.LENGTH_SHORT).show();

                    }

                } catch (JSONException e) {

                    e.printStackTrace();
                    Toast.makeText(context, "Error parsing JSON data.", Toast.LENGTH_SHORT).show();
                }

            } else {

                Toast.makeText(context, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
