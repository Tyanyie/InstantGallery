package com.example.instantgallery.tianyi_class;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Picture;
import android.graphics.drawable.BitmapDrawable;
import android.media.ExifInterface;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.instantgallery.MainActivity;
import com.example.instantgallery.R;
import com.example.instantgallery.TagActivity;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.channels.FileChannel;

public class Tianyi_Single_Image_View extends AppCompatActivity
{

    int fileId;
    public static final String TAG = "Too";
    Tianyi_ImageView clickedImage;
    Intent intent;

    public boolean nightmodeSingle = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tianyi_single_photo_view);

        clickedImage = findViewById(R.id.single_image);
        intent = getIntent();
        String imageId = intent.getStringExtra("image");

        Uri uri = Uri.parse(imageId);
        Log.v(TAG, "URI is :" + uri);
        clickedImage.setImageURI(uri);


    }



    //Ssu-Ting's
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.single_options_menu, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.nightMode:
                //Robert's part start
                nightModeSingle();
                break;
                //Robert's part end
            case R.id.copy:
                copyImage();
                break;
            case R.id.wallpaper:
                setAsWallpaper(clickedImage);
                break;
            case R.id.tag:
                addaTag();
                break;
            case R.id.info:
                info();
                break;
            case R.id.crop:
                crop();
                break;

        }
        return true;
    }
    public void copyImage()
    {
        try {
            ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            Intent appIntent = new Intent();
            ClipData clip = ClipData.newIntent("Intent",appIntent);
            Toast.makeText(getApplicationContext(), "Copy image successfully！", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Copy image unsuccessfully！", Toast.LENGTH_LONG).show();
        }

    }
    public void setAsWallpaper(View view)
    {
        try {
            Tianyi_Single_Image_View.this.clearWallpaper();
            ImageView iv = (ImageView) view;
            iv.setDrawingCacheEnabled(true);
            Bitmap bmp = Bitmap.createBitmap(iv.getDrawingCache());
            Tianyi_Single_Image_View.this.setWallpaper(bmp);
            iv.setDrawingCacheEnabled(false);
            Toast.makeText(getApplicationContext(), "Wallpaper set successfully！", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Wallpaper set unsuccessfully！", Toast.LENGTH_LONG).show();
        }
    }
    public void addaTag()
    {
        ImageView view = (ImageView)findViewById(R.id.single_image);
        //Bitmap bitmap = setimage(view);
        Intent intent = new Intent(this, TagActivity.class);
        //intent.putExtra("image", bitmap);
        startActivity(intent);
    }
    private Bitmap setimage(ImageView view1)
    {
        Bitmap image = ((BitmapDrawable)view1.getDrawable()).getBitmap();
        Bitmap bitmap1 = Bitmap.createBitmap(image);
        return bitmap1;
    }
    private byte[] Bitmap2Bytes(Bitmap bm)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }


    //Bruce's part
    private void crop()
    {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        String id = "file://"+intent.getStringExtra("image");
        Uri uri = Uri.parse(id);
        CropImage.activity(uri).setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true).start(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(requestCode == RESULT_OK);
            clickedImage.setImageURI(result.getUri());
            //System.out.println(result.getUri());



            String pictureDir = intent.getStringExtra("image").substring(0,29);
            String newPicDir = pictureDir + "cropped/";
            System.out.println(newPicDir);
            File dir = new File(pictureDir + "cropped");
            System.out.println(dir);
            if(dir.mkdirs())
                System.out.println("directory created");
            else
                System.out.println("directory not created");



            SharedPreferences id = getSharedPreferences("PREFS", 0);
            SharedPreferences.Editor editor = id.edit();
            fileId = id.getInt("fileId", 0);

            //copy file
            File src = new File(result.getUri().getPath());
            File dst = new File(newPicDir + fileId + ".jpg");
            editor.putInt("fileId", fileId + 1);
            editor.apply();
            FileChannel outputChannel = null;
            FileChannel inputChannel = null;

            try {
                outputChannel = new FileOutputStream(dst).getChannel();
                inputChannel = new FileInputStream(src).getChannel();
                inputChannel.transferTo(0, inputChannel.size(), outputChannel);
                outputChannel.close();
                inputChannel.close();


            } catch (FileNotFoundException e ) {
                System.out.println("FileNotFoundException");
            } catch (IOException e) {
                System.out.println("IOException");
            }

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();


        }
    }




    public void info()
    {
        String path = intent.getStringExtra("image");
        try
        {
            ExifInterface exifInterface = new ExifInterface(path);
            String exif = "";
            exif += "Date: " + exifInterface.getAttribute(ExifInterface.TAG_DATETIME);
            exif += "\nSize: " + exifInterface.getAttribute(ExifInterface.TAG_IMAGE_LENGTH) + "X" + exifInterface.getAttribute(ExifInterface.TAG_IMAGE_WIDTH);
            exif += "\nPath: " + intent.getStringExtra("image");



            new AlertDialog.Builder(Tianyi_Single_Image_View.this).setTitle("Details").setMessage(exif).setPositiveButton("Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    closeContextMenu();
                }
            })
            .show();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }


    //Robert's part
    public void setBackgroundColor(String color) {
        View singleView = this.getWindow().getDecorView();
        singleView.setBackgroundColor(Color.parseColor(color));
    }

    //Robert's part
    public void nightModeSingle() {
        if (!nightmodeSingle)
        {
            setBackgroundColor("#2c2c2c");
            nightmodeSingle = true;
        }
        else
        {
            setBackgroundColor("#FFFFFF");
            nightmodeSingle = false;
        }
    }
}