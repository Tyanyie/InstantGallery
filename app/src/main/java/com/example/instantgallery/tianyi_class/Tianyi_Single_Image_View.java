package com.example.instantgallery.tianyi_class;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.instantgallery.Bruce_class.LoadingActivity;
import com.example.instantgallery.MainActivity;
import com.example.instantgallery.R;
import com.example.instantgallery.TagActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class Tianyi_Single_Image_View extends AppCompatActivity
{
    public static final String TAG = "Too";
    Tianyi_ImageView clickedImage;
    Intent intent;
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
            case R.id.copy:
                copyImage();
                break;
            case R.id.wallpaper:
                setAsWallpaper(clickedImage);
                break;
            case R.id.tag:
                addaTag();
                break;
            case R.id.hide:
                hidePhoto();
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
        Bitmap bitmap = setimage(view);
        Intent intent = new Intent(Tianyi_Single_Image_View.this, TagActivity.class);
        intent.putExtra("image", bitmap);
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
    public void hidePhoto()
    {

        String path = intent.getStringExtra("image");
        String fileName = path.substring(29);
        String filePath = path.substring(0,29);



        Intent intent = new Intent(getApplicationContext(), LoadingActivity.class);

        intent.putExtra("fName", fileName);
        intent.putExtra("fPath", filePath);

        startActivity(intent);
        finish();
    }



}