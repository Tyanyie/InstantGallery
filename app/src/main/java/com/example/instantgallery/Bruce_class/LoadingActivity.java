package com.example.instantgallery.Bruce_class;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
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

public class LoadingActivity extends AppCompatActivity {




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


        startActivity(intent);
        finish();
    }
}