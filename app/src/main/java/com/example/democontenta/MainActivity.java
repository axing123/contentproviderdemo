package com.example.democontenta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.democontenta.cp.MserviceContentProvider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {
  public static final String TAG="MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // getAssetFiledataFromA(getContentResolver());
            }
        });
    }
    public void getAssetFiledataFromA(ContentResolver contentResolver)  {
        Uri fileUri = Uri.parse("content://com.demoa.mservicecontentprovide/service_definitions");
        AssetFileDescriptor fileDescriptor = null;
        FileInputStream fis=null;
        InputStream inputStream=null;
        try {
            // fileDescriptor = contentResolver.openFileDescriptor(ASSET_CONTENT_URI, "r");
            Log.d(TAG, "getAssetFiledataFromA: 打开文件");
            fileDescriptor =  contentResolver.openAssetFileDescriptor(MserviceContentProvider.CONTENT_URI,"r");
            Log.d(TAG, "getAssetFiledataFromA: 读取数据");
            fis = new FileInputStream(fileDescriptor.getFileDescriptor());
            if (fileDescriptor != null) {
                // 读取文件数据
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(fis));
                StringBuilder stringBuilder=new StringBuilder();
                String line;
                while ((line=bufferedReader.readLine())!=null){
                    stringBuilder.append(line);
                }
                bufferedReader.close();
                // 解析 JSON 数据并进行格式检查
                String stringJson=stringBuilder.toString();
                Log.d(TAG, "getAssetFiledataFromA: "+stringJson);
                checkJsonString(stringJson);
                // 保存服务定义到本地
                fis.close();
                fileDescriptor.close();
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

            }


            public void checkJsonString(String stringJson){
                if (stringJson != null) {
                    try {
                        JSONObject jsonObject = new JSONObject(stringJson);
                        JSONArray jsonServices = jsonObject.getJSONArray("services");
                        for (int i = 0; i < jsonServices.length(); i++) {
                            JSONObject jsonService = jsonServices.getJSONObject(i);
                            String name = jsonService.getString("name");
                            String id = jsonService.getString("id");
                            String type = jsonService.getString("type");
                            String desc = jsonService.getString("desc");
                            boolean enable = jsonService.getBoolean("enable");
                            Log.d(TAG, "服务json" + name + id + desc);

                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }






}