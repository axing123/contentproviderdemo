package com.example.democontenta.cp;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.democontenta.model.ServiceDefinitionItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ServiceContentProvider extends ContentProvider {
    private static final String TAG = "ServiceContentProvider";
    private static final String AUTHORITY = "com.example.democontenta";
    private static final String FILE_PATH = "/json_files/";
    private static final String PATH = "service_definition";
    private static final String ASSET_JSON_FILE = "service_definition.json";
    private static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + FILE_PATH);

    private static final Uri ASSET_CONTENT_URI = Uri.parse("content://" + AUTHORITY +"/"+ PATH);

    private static final int JSON_FILE = 1;
    private static final int ASEET_JSON_FILE = 2;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, "json_files/*", JSON_FILE);
        uriMatcher.addURI(AUTHORITY, "", ASEET_JSON_FILE);
    }

    @Override
    public boolean onCreate() {
      //  initData();
        return true;
    }

    public void initData()  {
        ContentResolver contentResolver=getContext().getContentResolver();

        String json = null;
        try {
            json = readJsonFromAsset();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        if (json != null) {
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONArray jsonServices = jsonObject.getJSONArray("services");

                for (int i = 0; i < jsonServices.length(); i++) {
                    JSONObject jsonService = jsonServices.getJSONObject(i);
                    String name = jsonService.getString("name");
                    String id = jsonService.getString("id");
                    String type = jsonService.getString("type");
                    String desc = jsonService.getString("desc");
                    boolean enable = jsonService.getBoolean("enable");
                    Log.d(TAG, "服务json" + name + id + desc);

                    ContentValues values = new ContentValues();
                    values.put("id",id);
                    values.put("name",name);
                    values.put("type",type);
                    values.put("desc",desc);
                    values.put("enable",enable);
                    Uri uri = contentResolver.insert(ASSET_CONTENT_URI, values);
                    Log.d(TAG, "Inserted URI: " + uri);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }



    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        if (uri.equals(ASSET_CONTENT_URI)) {

            //读取本地文件并转换成list
            List<ServiceDefinitionItem> serviceDefinitionItemList = null;
            try {
                serviceDefinitionItemList = getServiceDefinition();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            String[] columns = {"id", "name", "type", "desc", "enable"};
            MatrixCursor matrixCursor = new MatrixCursor(columns);
            matrixCursor.addRow(serviceDefinitionItemList);
            Log.d(TAG, "query: 将数据转换成Cursor");
          /*  // 将数据转换为 Cursor
            MatrixCursor cursor = new MatrixCursor(new String[]{"service_definition"});
            for (String definition : serviceDefinitions) {
                cursor.addRow(new Object[]{definition});
            }*/
            return matrixCursor;
        }
        return null;


    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }


    public String readJsonFromAsset() throws IOException {
        String jsonString = "";
        InputStream inputStream = getContext().getAssets().open(ASSET_JSON_FILE);
        int size = inputStream.available();
        byte[] buffer = new byte[size];
        inputStream.read(buffer);
        inputStream.close();
        jsonString = new String(buffer, StandardCharsets.UTF_8);
        return jsonString;
    }


    // //读取本地文件
    public List<ServiceDefinitionItem> getServiceDefinition() throws JSONException, IOException {
        List<ServiceDefinitionItem> serviceslist = new ArrayList<>();
        InputStream inputStream = getContext().getAssets().open(ASSET_JSON_FILE);
        int size = inputStream.available();
        byte[] buffer = new byte[size];
        inputStream.read(buffer);
        inputStream.close();
        String json = new String(buffer, StandardCharsets.UTF_8);
        JSONObject jsonObject = new JSONObject(json);
        JSONArray jsonServices = jsonObject.getJSONArray("services");

        for (int i = 0; i < jsonServices.length(); i++) {
            JSONObject jsonService = jsonServices.getJSONObject(i);
            String name = jsonService.getString("name");
            String id = jsonService.getString("id");
            String type = jsonService.getString("type");
            String desc = jsonService.getString("desc");
            boolean enable = jsonService.getBoolean("enable");
            ServiceDefinitionItem service = new ServiceDefinitionItem(id, name, type, desc, enable);
            serviceslist.add(service);
            Log.d(TAG, "服务json" + service.desc + service.id + service.name);

        }
        return serviceslist;

    }


}
