package com.example.contentprovider2;
import static com.example.contentprovider2.ContractProvider.*;
import static com.example.contentprovider2.ContractProvider.Chapitre.*;
import static com.example.contentprovider2.ContractProvider.Person.*;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        insertChapitre();
        insertPerson();
        displayChapitre();
        displayPerson();
    }

    void insertChapitre() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_CHAPITRE_NAME, "Chapitre 2");
        contentValues.put(COL_CHAPITRE_DESCRIPTION, "Chapitre 2 Description");

        getContentResolver().insert(URI_CHAPITRE, contentValues);

    }

    void insertPerson() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_PERSON_NAME, "Person 2");
        contentValues.put(COL_PERSON_SURNAME, "Person2 Description");

        getContentResolver().insert(URI_PERSON, contentValues);

    }

    @SuppressLint("Range")
    private void displayChapitre() {
        // Chapitre
        Cursor cursor = getContentResolver().query(URI_CHAPITRE,
                /* new String[] {ContractProvider.COL_CHAPITRE_NAME}*/ null,
                null,
                null,
                null);
        if (cursor.moveToFirst()) {
            String result = null;
            do {
                result = new StringBuilder()
                        .append(COL_CHAPITRE_ID + " : ")
                        .append(cursor.getString(cursor.getColumnIndex(COL_CHAPITRE_ID)) + " ")
                        .append(COL_CHAPITRE_NAME + " : ")
                        .append(cursor.getString(cursor.getColumnIndex(COL_CHAPITRE_NAME)) + " ")
                        .append(COL_CHAPITRE_DESCRIPTION + " : ")
                        .append(cursor.getString(cursor.getColumnIndex(COL_CHAPITRE_DESCRIPTION)) + " ")
                        .toString();
                Log.d("App", result);
            } while (cursor.moveToNext());

            cursor.close();
        }
    }

    @SuppressLint("Range")
    private void displayPerson() {
        // Chapitre
        Cursor cursor = getContentResolver().query(URI_PERSON,
                null,
                null,
                null,
                null);
        if (cursor.moveToFirst()) {
            String result = null;
            do {
                result = new StringBuilder()
                        .append(COL_PERSON_ID + " : ")
                        .append(cursor.getString(cursor.getColumnIndex(COL_PERSON_ID)) + " ")
                        .append(COL_PERSON_NAME + " : ")
                        .append(cursor.getString(cursor.getColumnIndex(COL_PERSON_NAME)) + " ")
                        .append(COL_PERSON_SURNAME + " : ")
                        .append(cursor.getString(cursor.getColumnIndex(COL_PERSON_SURNAME)) + " ")
                        .toString();
                Log.d("App", result);
            } while (cursor.moveToNext());

            cursor.close();
        }
    }

}