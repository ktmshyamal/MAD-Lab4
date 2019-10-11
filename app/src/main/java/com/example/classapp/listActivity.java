package com.example.classapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import Database.DBHelper;

public class listActivity extends AppCompatActivity {

    private ListView listView;

    private DBHelper dbHelper;

    private ArrayList<String> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        listView = findViewById(R.id.listView);

        dbHelper = new DBHelper(this);
        arrayList = new ArrayList<>();

        viewData();


    }
    public void viewData(){
        Cursor cursor=dbHelper.readAllInfo();

        if(cursor.getCount() == 0){
            Toast.makeText(this,"NOTHING TO SHOW",Toast.LENGTH_SHORT).show();
        }else {
            if (cursor.moveToFirst()) {

                arrayList.add(cursor.getString(1));

                while (cursor.moveToNext()) {
                    arrayList.add(cursor.getString(1));
                }
            }

            ArrayAdapter arrayAdapter = new ArrayAdapter(this,R.layout.support_simple_spinner_dropdown_item,arrayList);
            listView.setAdapter(arrayAdapter);
        }
        cursor.close();
    }


}
