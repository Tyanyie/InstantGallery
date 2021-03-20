package com.example.instantgallery;

import android.annotation.SuppressLint;
import android.app.AppComponentFactory;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.instantgallery.tianyi_class.Tianyi_ImageView;

import java.util.ArrayList;

public class TagActivity extends AppCompatActivity implements View.OnClickListener
{

    ImageView tagforImage;
    Intent intent;
    public static final String TAG_3 = "030";
    //Operator
    private Button animal_tag;
    private Button landscape_tag;
    private Button food_tag;
    private Button plant_tag;
    private Button human_tag;
    private Button family_tag;
    private Button friend_tag;
    private Button favorite_tag;
    private Button other_tag;

    boolean isEmpty;

    //Output
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_tag);

        tagforImage = findViewById(R.id.tagforImage);
        intent = getIntent();
        String imageId = intent.getStringExtra("image");

        Uri uri = Uri.parse(imageId);
        Log.v(TAG_3, "URI is :" + uri);
        tagforImage.setImageURI(uri);

        animal_tag = findViewById(R.id.tag_animal);
        landscape_tag = findViewById(R.id.tag_landscape);
        food_tag = findViewById(R.id.tag_food);
        plant_tag = findViewById(R.id.tag_plant);
        human_tag = findViewById(R.id.tag_human);
        family_tag = findViewById(R.id.tag_family);
        friend_tag = findViewById(R.id.tag_friend);
        favorite_tag = findViewById(R.id.tag_favorite);
        other_tag = findViewById(R.id.tag_other);


        //output
        textView = findViewById(R.id.output);

        animal_tag.setOnClickListener(this);
        landscape_tag.setOnClickListener(this);
        food_tag.setOnClickListener(this);
        plant_tag.setOnClickListener(this);
        human_tag.setOnClickListener(this);
        family_tag.setOnClickListener(this);
        friend_tag.setOnClickListener(this);
        favorite_tag.setOnClickListener(this);
        other_tag.setOnClickListener(this);
    }

    @SuppressLint("NewApi")
    public void onClick(View view) {

        //display
        String currentTag = textView.getText().toString();
        String []Tags;
        Tags = currentTag.split(" ");
        ArrayList<String> newTags = new ArrayList<String>();
        String display = "";

        boolean isExisted = false;

        switch (view.getId()){

            case R.id.tag_animal:

                //checking(currentTag);
                textView.setText(currentTag + "#" + ((Button)view).getText() + " ");
                break;
            case R.id.tag_family:
                textView.setText(currentTag + "#" + ((Button)view).getText() + " ");
                break;
            case R.id.tag_favorite:
                textView.setText(currentTag + "#" + ((Button)view).getText() + " ");
                break;
            case R.id.tag_food:
                textView.setText(currentTag + "#" + ((Button)view).getText() + " ");
                break;
            case R.id.tag_friend:
                textView.setText(currentTag + "#" + ((Button)view).getText() + " ");
                break;
            case R.id.tag_human:
                textView.setText(currentTag + "#" + ((Button)view).getText() + " ");
                break;
            case R.id.tag_landscape:
                textView.setText(currentTag + "#" + ((Button)view).getText() + " ");
                break;
            case R.id.tag_other:
                textView.setText(currentTag + "#" + ((Button)view).getText() + " ");
                break;
            case R.id.tag_plant:
                textView.setText(currentTag + "#" + ((Button)view).getText() + " ");
                break;


        }
    }
    public void checking(String currentTag)
    {
        String [] Tags = currentTag.split(" ");
        ArrayList<String> newTags = new ArrayList<String>();


        for (int i=0; i<Tags.length; i++)
        {
            /*if (Tags[i].equals("animal"))
            {
                //textView.setText(currentTag + "#" + "test" + " ");
            } */
            /*if (!Tags[i].equals("animal"))
            {
                //textView.setText(currentTag + "#" + "animal" + " ");
                newTags.add(Tags[i]);
            }*/
            String s = Tags[i];
            newTags.add(s);
            //display = display + "#" + Tags[i] + " ";
            //display = display + Tags[i] + "test ";
        }

        String newOutput = newTags.get(0);
        for (int j=1; j<newTags.size(); j++)
        {
            newOutput = newOutput + newTags.get(j) + " ";
        }

        //String newOutput = newTags.get(1) + " " + "#" + newTags.get(2);
        textView.setText(newOutput);
    }
}

