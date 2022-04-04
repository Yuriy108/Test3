package com.example.test2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText name;
    EditText email;
    Button btAdd,btRaed,btClear;
    DbHelper dbHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name=findViewById(R.id.editTextTextPersonName);
        email=findViewById(R.id.editTextTextEmailAddress);
        btAdd=findViewById(R.id.Add);
        btRaed=findViewById(R.id.buttonRead);
        btClear=findViewById(R.id.buttonClear);
        btAdd.setOnClickListener(this);
        btRaed.setOnClickListener(this);
        btClear.setOnClickListener(this);
        dbHelper = new DbHelper(this);

    }

    @Override
    public void onClick(View v) {
        String cname=name.getText().toString();
        String cemail=email.getText().toString();

        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        switch (v.getId()){
            case R.id.Add:
                contentValues.put(DbHelper.KEY_NAME, cname);
                contentValues.put(DbHelper.KEY_MAIL, cemail);

                database.insert(DbHelper.TABLE_CONTACTS, null, contentValues);
                break;
            case R.id.buttonRead:
                Cursor cursor = database.query(DbHelper.TABLE_CONTACTS, null, null, null, null, null, null);

                if (cursor.moveToFirst()) {
                    int idIndex = cursor.getColumnIndex(DbHelper.KEY_ID);
                    int nameIndex = cursor.getColumnIndex(DbHelper.KEY_NAME);
                    int emailIndex = cursor.getColumnIndex(DbHelper.KEY_MAIL);
                    do {
                        Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
                                ", name = " + cursor.getString(nameIndex) +
                                ", email = " + cursor.getString(emailIndex));
                    } while (cursor.moveToNext());
                } else
                    Log.d("mLog","0 rows");

                cursor.close();
                break;
            case R.id.buttonClear:
                database.delete(DbHelper.TABLE_CONTACTS, null, null);
                break;
        }
        dbHelper.close();
    }
}