package com.example.instantgallery.Bruce_class;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.example.instantgallery.MainActivity;
import com.example.instantgallery.R;
import com.example.instantgallery.tianyi_class.Tianyi_Single_Image_View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class LoadingActivity extends AppCompatActivity {


    private String[] permissions;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);


        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        Bundle extras = getIntent().getExtras();
        String filepath = extras.getString("fPath");
        String fileName = extras.getString("fName");

        //create a folder to store hidden images
        File dir = new File (filepath + "/.vault");
        if(dir.mkdirs())
            System.out.println("directory created");
        else
            System.out.println("directory not created");
        File from = new File(filepath, fileName);


        //Moving the image to the vault folder
        File newFile = new File(filepath+"/.vault/" + fileName);
        FileChannel outputChannel = null;
        FileChannel inputChannel = null;
        try
        {
            outputChannel = new FileOutputStream(newFile).getChannel();
            inputChannel = new FileInputStream(from).getChannel();
            inputChannel.transferTo(0, inputChannel.size(), outputChannel);
            outputChannel.close();
            inputChannel.close();
        }

        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        System.out.println(from.getAbsolutePath());
        //deleteImage(from);

        startActivity(intent);
        finish();
    }

    private void deleteImage(File file)
    {
        // Set up the projection )
        String[] projection = {MediaStore.Images.Media._ID};

        // Match on the file path
        String selection = MediaStore.Images.Media.DATA + " = ?";
        String[] selectionArgs = new String[]{file.getAbsolutePath()};

        // Query for the ID of the media matching the file path
        Uri queryUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        ContentResolver contentResolver = getContentResolver();
        Cursor c = contentResolver.query(queryUri, projection, selection, selectionArgs, null);
        if (c.moveToFirst()) {
            // Deleting the item via the content provider will also remove the file
            long id = c.getLong(c.getColumnIndexOrThrow(MediaStore.Images.Media._ID));
            Uri deleteUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
            contentResolver.delete(deleteUri, null, null);
        } else {
            // File not found
        }
        c.close();

    }







}




