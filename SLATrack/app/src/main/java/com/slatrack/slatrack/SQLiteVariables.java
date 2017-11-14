package com.slatrack.slatrack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class SQLiteVariables extends AppCompatActivity {


    List<String> CheckPoint = new ArrayList<String>();
    List<String> IMEI_Number1 = new ArrayList<String>();
    List<String> IMEI_Number2 = new ArrayList<String>();

    SQLiteVariables(List<String> CheckPoint,List<String> IMEI_Nunmber1,List<String> IMEI_Number2){
        this.CheckPoint = CheckPoint;
        this.IMEI_Number1 = IMEI_Nunmber1;
        this.IMEI_Number2 = IMEI_Number2;

    }

    public List<String> getCheckPoint() {
        return CheckPoint;
    }

    public void setCheckPoint(List<String> checkPoint) {
        CheckPoint = checkPoint;
    }

    public List<String> getIMEI_Number1() {
        return IMEI_Number1;
    }

    public void setIMEI_Number1(List<String> IMEI_Number1) {
        this.IMEI_Number1 = IMEI_Number1;
    }

    public List<String> getIMEI_Number2() {
        return IMEI_Number2;
    }

    public void setIMEI_Number2(List<String> IMEI_Number2) {
        this.IMEI_Number2 = IMEI_Number2;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_variables);
    }
}



