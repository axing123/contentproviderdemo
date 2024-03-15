package com.example.democontenta.cp;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MserviceContentProvider extends ContentProvider {
    private static final String TAG = "MserviceContentProvider";
    private static final String AUTHORITY = "com.demoa.mservicecontentprovider";
    private static final String PATH = "service_definition";
    private static final String DATA_JSON_PATH = "service_definition.json";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + PATH);

    @Override
    public boolean onCreate() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(AUTHORITY, "/"+PATH, 1);
        // TODO: Implement this to initialize your content provider on startup.
        return true;
    }

    public MserviceContentProvider() {

    }

    @Nullable
    @Override
    public ParcelFileDescriptor openFile(@NonNull Uri uri, @NonNull String mode) throws FileNotFoundException {
        if (PATH.equals(uri.getLastPathSegment())) {
            File file = new File(getContext().getFilesDir(), PATH);
            return ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY);
        }
        throw new FileNotFoundException("File not found: " + uri);

    }

    @Nullable
    @Override
    public AssetFileDescriptor openAssetFile(@NonNull Uri uri, @NonNull String mode) throws FileNotFoundException {
        Log.d(TAG, "openAssetFile: "+uri.getLastPathSegment());
        AssetFileDescriptor assetFileDescriptor=null;
        if (uri.equals(CONTENT_URI)) {
            Log.d(TAG, "openAssetFile: 打开文件");
            try {
                // 打开要共享的 asset 文件
                AssetManager assetManager = getContext().getAssets();
                assetFileDescriptor = assetManager.openFd("service_definition.json");
                return assetFileDescriptor;
            } catch (IOException e) {
                Log.d(TAG, "openAssetFile: "+e.toString());
                throw new FileNotFoundException("Unable to open asset file: " + "service_definition.json");
            }
        }
        return assetFileDescriptor;

    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {

        throw new UnsupportedOperationException("Not yet implemented");
}


    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // TODO: Implement this to handle requests for the MIME type of the data
        // at the given URI.
        return "application/json";
       // throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        // TODO: Implement this to handle requests to insert a new row.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        // TODO: Implement this to handle requests to update one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}