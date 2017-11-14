package com.slatrack.slatrack;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SQLiteDB extends AppCompatActivity {


    List<String> checkPt = new ArrayList<String>();
    List<String> IMEI_Number1 = new ArrayList<String>();
    List<String> IMEI_Number2 = new ArrayList<String>();
    List<String> Maintenance = new ArrayList<String>();
    List<String> Checking = new ArrayList<String>();
    List<byte []> ByteArray = new ArrayList<byte []>();

    String CheckPoint;
    String IMEI_Number_1;
    String IMEI_Number_2;
    String checking;
    String maintenance;
    byte [] byteArray;

    private static final String DBase = "SLATrack";
    public static final int version = 1;
    // public static final int new_version = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_db);

        Intent intent = getIntent();
        CheckPoint = intent.getStringExtra("Checkpoint");
        IMEI_Number_1 = intent.getStringExtra("IMEI_Num1");
        IMEI_Number_2 = intent.getStringExtra("IMEI_Num2");
        maintenance = intent.getStringExtra("maintenance");
        checking = intent.getStringExtra("checking");
        byteArray = intent.getByteArrayExtra("byteArray");
        String res = CheckPoint + " " + IMEI_Number_1 + " " + IMEI_Number_2;
        System.out.println(CheckPoint + IMEI_Number_1 + " " + IMEI_Number_2 + " " + maintenance + " " + checking);
        Toast.makeText(SQLiteDB.this, res, Toast.LENGTH_LONG).show();
        DataBaseSQLite dataBaseSQLite = new DataBaseSQLite(SQLiteDB.this, DBase, null, version);
        Boolean flag = true;
        if (CheckPoint.length() == 0) {
            flag = false;
        }
        System.out.println(flag);
        if (flag) {
            System.out.println("hi1");
            SQLiteDatabase sqLiteDatabase = dataBaseSQLite.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("CheckPoint", CheckPoint);
            values.put("IMEI_Number1", IMEI_Number_1);
            values.put("IMEI_Number2", IMEI_Number_2);
            values.put("maintenance", maintenance);
            values.put("checking", checking);
            values.put("byteArray",byteArray);
            System.out.println("hi2");
            sqLiteDatabase.insert("SQLData", null, values);
            Toast.makeText(SQLiteDB.this, "DATA Inserted successfully..!!", Toast.LENGTH_SHORT).show();
        }
        System.out.println("hi3");
        SQLiteDatabase sqLiteDatabase1 = dataBaseSQLite.getReadableDatabase();
        String projection[] = {"CheckPoint", "IMEI_Number1", "IMEI_Number2", "maintenance", "checking","byteArray"};
        Cursor c = sqLiteDatabase1.query("SQLData", projection, null, null, null, null, null);
        System.out.println("hi4");
        c.moveToFirst();

        do {
            System.out.println("hi5");
            checkPt.add(c.getString(0));
            IMEI_Number1.add(c.getString(1));
            IMEI_Number2.add(c.getString(2));
            Maintenance.add(c.getString(3));
            Checking.add(c.getString(4));
            System.out.println("hi6");

        } while (c.moveToNext());

        for (int i = 0; i < checkPt.size(); i++) {
            System.out.println("hi1" + i);
            System.out.println(checkPt.get(i) + IMEI_Number1.get(i) + IMEI_Number2.get(i) + Maintenance.get(i) + Checking.get(i));
        }
        SQLiteVariables sqLiteVariables = new SQLiteVariables(checkPt, IMEI_Number1, IMEI_Number2);
//        sqLiteVariables.setCheckPoint(checkPt);
//        sqLiteVariables.setIMEI_Number1(IMEI_Number1);
//        sqLiteVariables.setIMEI_Number2(IMEI_Number2);


        if (flag) {
            System.out.println("hi7");
            Intent i = new Intent(SQLiteDB.this, MainActivity.class);
            i.putExtra("CheckPoint", (ArrayList<String>) checkPt);
            i.putExtra("IMEI_Number1", (ArrayList<String>) IMEI_Number1);
            i.putExtra("IMEI_Number2", (ArrayList<String>) IMEI_Number2);
            startActivity(i);
        }else{
            System.out.println("hi8");
            Intent i = new Intent(SQLiteDB.this, ConnectServer.class);
            i.putExtra("CheckPoint", (ArrayList<String>) checkPt);
            i.putExtra("IMEI_Number1", (ArrayList<String>) IMEI_Number1);
            i.putExtra("IMEI_Number2", (ArrayList<String>) IMEI_Number2);
            i.putExtra("Maintenance", (ArrayList<String>) Maintenance);
            i.putExtra("Checking", (ArrayList<String>) Checking);
            i.putExtra("ByteArray" ,(ArrayList<byte []>) ByteArray);
//            DataBaseSQLite dataBaseSQLite1 = null;
//            dataBaseSQLite1.onUpgrade(sqLiteDatabase1,1,2);
            startActivity(i);
        }

    }

    public class DataBaseSQLite extends SQLiteOpenHelper{
//        public static final int version1 = 1;
//        public static final int new_version1 = 2;

        SQLiteDatabase sqLiteDatabase;
        public DataBaseSQLite(SQLiteDB sqLiteDB, String DBase, Object o, int version) {
            super(sqLiteDB, DBase, (SQLiteDatabase.CursorFactory) o, version);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("create table SQLData(CheckPoint text,IMEI_Number1 text,IMEI_Number2 text,maintenance text,checking text,byteArray BLOB);");
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int version1, int new_version1) {
            sqLiteDatabase.execSQL("DROP TABLE IF EXISTS SQLData");
            onCreate(sqLiteDatabase);
        }

    }

}

