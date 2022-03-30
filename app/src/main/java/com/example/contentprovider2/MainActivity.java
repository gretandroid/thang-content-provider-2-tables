package com.example.contentprovider2;

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
        contentValues.put(ContractProvider.COL_CHAPITRE_NAME, "Chapitre 2");
        contentValues.put(ContractProvider.COL_CHAPITRE_DESCRIPTION, "Chapitre 2 Description");

        getContentResolver().insert(ChapitreContentProvider.URI, contentValues);

    }

    void insertPerson() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ContractProvider.COL_PERSON_NAME, "Person 2");
        contentValues.put(ContractProvider.COL_PERSON_SURNAME, "Person2 Description");

        getContentResolver().insert(PersonContentProvider.URI, contentValues);

    }

    @SuppressLint("Range")
    private void displayChapitre() {
        // Chapitre
        Cursor cursor = getContentResolver().query(ChapitreContentProvider.URI,
                /* new String[] {ContractProvider.COL_CHAPITRE_NAME}*/ null,
                null,
                null,
                null);
        if (cursor.moveToFirst()) {
            String result = null;
            do {
                result = new StringBuilder()
                        .append(ContractProvider.COL_CHAPITRE_ID + " : ")
                        .append(cursor.getString(cursor.getColumnIndex(ContractProvider.COL_CHAPITRE_ID)) + " ")
                        .append(ContractProvider.COL_CHAPITRE_NAME + " : ")
                        .append(cursor.getString(cursor.getColumnIndex(ContractProvider.COL_CHAPITRE_NAME)) + " ")
                        .append(ContractProvider.COL_CHAPITRE_DESCRIPTION + " : ")
                        .append(cursor.getString(cursor.getColumnIndex(ContractProvider.COL_CHAPITRE_DESCRIPTION)) + " ")
                        .toString();
                Log.d("App", result);
            } while (cursor.moveToNext());

            cursor.close();
        }
    }

    @SuppressLint("Range")
    private void displayPerson() {
        // Chapitre
        Cursor cursor = getContentResolver().query(PersonContentProvider.URI,
                null,
                null,
                null,
                null);
        if (cursor.moveToFirst()) {
            String result = null;
            do {
                result = new StringBuilder()
                        .append(ContractProvider.COL_PERSON_ID + " : ")
                        .append(cursor.getString(cursor.getColumnIndex(ContractProvider.COL_PERSON_ID)) + " ")
                        .append(ContractProvider.COL_PERSON_NAME + " : ")
                        .append(cursor.getString(cursor.getColumnIndex(ContractProvider.COL_PERSON_NAME)) + " ")
                        .append(ContractProvider.COL_PERSON_SURNAME + " : ")
                        .append(cursor.getString(cursor.getColumnIndex(ContractProvider.COL_PERSON_SURNAME)) + " ")
                        .toString();
                Log.d("App", result);
            } while (cursor.moveToNext());

            cursor.close();
        }
    }

}