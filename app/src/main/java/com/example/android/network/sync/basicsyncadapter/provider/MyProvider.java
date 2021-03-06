package com.example.android.network.sync.basicsyncadapter.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public class MyProvider extends ContentProvider {

    private static final String PROVIDER_NAME = "com.example.android.network.sync.basicsyncadapter";
    private static final Uri CONTENT_URI = Uri.parse("content://" + PROVIDER_NAME );
    private static final int INTEGERS = 1;
    private static final int INTEGER_ID = 2;
    private static final UriMatcher uriMatcher = getUriMatcher();
    private static UriMatcher getUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER_NAME, "integers", INTEGERS);
        uriMatcher.addURI(PROVIDER_NAME, "integers/#", INTEGER_ID);
        return uriMatcher;
    }
    private MyDB db = null;

    public MyProvider() {
    }

    @Override
    public boolean onCreate() {
        Context context = getContext();
        db = new MyDB(context);
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        String id = null;
        if(uriMatcher.match(uri) == INTEGER_ID) {
            //Query is for one single integer. Get the ID from the URI.
            id = uri.getPathSegments().get(1);
        }
        return db.getIntegers(id, projection, selection, selectionArgs, sortOrder);
    }

    @Override
    public String getType(Uri uri) {
        return "integers";
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        try {
            long id = db.addNewInteger(values);
            Uri returnUri = ContentUris.withAppendedId(CONTENT_URI, id);
            return returnUri;
        } catch(Exception e) {
            return null;
        }
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        String id = null;
        if(uriMatcher.match(uri) == INTEGER_ID) {
            //Delete is for one single integer. Get the ID from the URI.
            id = uri.getPathSegments().get(1);
        }

        return db.deleteIntegers(id);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        String id = null;
        if(uriMatcher.match(uri) == INTEGER_ID) {
            //Update is for one single integer. Get the ID from the URI.
            id = uri.getPathSegments().get(1);
        }

        return db.updateIntegers(id, values);
    }
}

