package com.example.classapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Database.DBHelper;

public class MainActivity extends AppCompatActivity {

    DBHelper dbHelper;

    EditText edit_name,edit_password;

    Button btnSelectAll,btnAdd,btnSignIn,btnDelete,btnUpdate;

    private String un,pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DBHelper(this);

        edit_name = (EditText) findViewById(R.id.editText_name);
        edit_password = (EditText)findViewById(R.id.editText_password);

        btnSelectAll = (Button)findViewById(R.id.buttonselectall);
        btnAdd = (Button)findViewById(R.id.buttonadd);
        btnSignIn = (Button)findViewById(R.id.buttonsignin);
        btnDelete = (Button)findViewById(R.id.buttondelete);
        btnUpdate = (Button)findViewById(R.id.buttonupdate);

        addData();
        viewAll();
        updateData();
        deleteData();
        signInData();


    }

    public void addData(){
        btnAdd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean isInserted = dbHelper.addInfo(edit_name.getText().toString(),
                                edit_password.getText().toString());
                    if (isInserted == true)
                        Toast.makeText(MainActivity.this,"Data Inserted..!",Toast.LENGTH_LONG).show();
                    else
                        Toast.makeText(MainActivity.this,"Data not Inserted..!",Toast.LENGTH_LONG).show();
                    }
                }
        );

    }

    public void signInData(){
        btnSignIn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        un = edit_name.getText().toString().trim();
                        pw = edit_password.getText().toString().trim();

                        boolean valid = dbHelper.validate(un,pw);

                        if (valid) {
                            clearData();

                            Toast.makeText(MainActivity.this,"Signing in Successfully..!",Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(MainActivity.this, homeActivity.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(MainActivity.this,"Invalid Username or Password..!",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    public void viewAll(){
        btnSelectAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this,listActivity.class);
                        startActivity(intent);

                    }
                }
        );
    }

    public void updateData(){
        btnUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        un = edit_name.getText().toString().trim();
                        pw = edit_password.getText().toString().trim();

                        boolean updt = dbHelper.updateInfo(un,pw);

                        if (updt){
                            Toast.makeText(MainActivity.this,"Update Successfully..!",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(MainActivity.this,"Update is Not Successful..!",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    public void deleteData(){
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        un = edit_name.getText().toString().trim();

                        boolean del = dbHelper.deleteInfo(un);

                        if (del){
                            Toast.makeText(MainActivity.this,"Deleted Successfully..!",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(MainActivity.this,"Delete is not Successful..!",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    public void clearData(){
        edit_name.setText("");
        edit_password.setText("");
    }

}
