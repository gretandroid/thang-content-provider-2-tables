package com.example.contentprovider2;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

// Le content provider permet de MAJ le contenu de la base de donné
// SQLite tout en maintenant à jour un système d'URI permettant d'acces aux resources
// de la base même à partir d'autre application
public class BaseContentProvider extends ContentProvider {

    // from AndroidManifest.provider.android:authorities
//    public static final Uri CONTENTURI =  Uri.parse("content://com.example.contentprovider2.BaseContentProvider");

    // on declare le helper pour ouvrir une session à chaque lecture/écriture
    private DatabaseSQliteHelper dbHelper;

    @Override
    public boolean onCreate() {
        dbHelper = new DatabaseSQliteHelper(getContext(),
                ContractProvider.DB_NAME,
                null, DatabaseSQliteHelper.VERSION);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if (uri.equals(PersonContentProvider.URI)) {
            return query(uri, projection, selection, selectionArgs, sortOrder, db, PersonContentProvider.TABLE_NAME, ContractProvider.COL_PERSON_ID);
        }
        if (uri.equals(ChapitreContentProvider.URI)) {
            return query(uri, projection, selection, selectionArgs, sortOrder, db, ChapitreContentProvider.TABLE_NAME, ContractProvider.COL_CHAPITRE_ID);
        }

        return null;
    }

    private Cursor query(@NonNull Uri uri,
                         String [] projection,
                         @Nullable String selection,
                         String [] selectionArgs,
                         @Nullable String sortOrder,
                         SQLiteDatabase db,
                         String tableName,
                         String idColumnName) {
        long id = getId(uri);
        if (id < 0) {
            return db.query(tableName,
                    projection,
                    selection,
                    selectionArgs, null, null, sortOrder);
        }
        else {
            return db.query(tableName,
                    projection,
                    idColumnName  + " = " + id ,
                    selectionArgs, null, null, sortOrder);
        }
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        // Creation ou MAJ de la BDD
        try (SQLiteDatabase db = dbHelper.getWritableDatabase()) {
            if (uri.equals(PersonContentProvider.URI)) {
                return insert(uri, values, db, PersonContentProvider.TABLE_NAME);
            }
            if (uri.equals(ChapitreContentProvider.URI)) {
                return insert(uri, values, db, ChapitreContentProvider.TABLE_NAME);
            }
            return uri;
        }
    }

    private Uri insert(@NonNull Uri uri, @Nullable ContentValues values, SQLiteDatabase db, String tableName) {
        long id;
        id = db.insertOrThrow(tableName, null, values);

        if (id == -1) {
            throw new RuntimeException("Fail insert into " + tableName);
        }
        else {
            Log.d("App", "uri=" + uri.toString() + "/" + id);
            return ContentUris.withAppendedId(uri, id);
        }
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    /**
     * Recuperer a dernier partie URI (content://com.example.contentprovider2.ContentProvider/#)
     */
    private long getId(Uri uri) {
        String  lastPathSegment = uri.getLastPathSegment();
        if (lastPathSegment != null) {
            return Long.parseLong(lastPathSegment);
        }

        return -1;
    }
}
