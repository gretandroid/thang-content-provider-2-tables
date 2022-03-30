package com.example.contentprovider2;

import android.net.Uri;

public class PersonContentProvider {
    public static final Uri URI = Uri.parse("content://com.example.contentprovider2.PersonContentProvider");
    public static final String TABLE_NAME = ContractProvider.TABLE_PERSON;
}
