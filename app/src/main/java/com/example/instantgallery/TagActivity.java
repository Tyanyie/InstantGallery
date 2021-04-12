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
import com.example.instantgallery.tianyi_class.Tianyi_Single_Image_View;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class TagActivity extends AppCompatActivity implements View.OnClickListener
{

    //ImageView tagforImage;
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
    //private Button save;
    private Button cancel;

    boolean isEmpty;

    //Output
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_tag);

        ImageView Image = findViewById(R.id.tagforImage);
        Intent intent = getIntent();
        String imageId = intent.getStringExtra("image");
        Image.setImageURI(Uri.parse(imageId));

        animal_tag = findViewById(R.id.tag_animal);
        landscape_tag = findViewById(R.id.tag_landscape);
        food_tag = findViewById(R.id.tag_food);
        plant_tag = findViewById(R.id.tag_plant);
        human_tag = findViewById(R.id.tag_human);
        family_tag = findViewById(R.id.tag_family);
        friend_tag = findViewById(R.id.tag_friend);
        favorite_tag = findViewById(R.id.tag_favorite);
        other_tag = findViewById(R.id.tag_other);
        cancel = findViewById(R.id.but_cancel);
        //save = findViewById(R.id.but_save);

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
        cancel.setOnClickListener(this);
        //save.setOnClickListener(this);

    }

    @SuppressLint("NewApi")
    public void onClick(View view) {

        Intent intent = getIntent();
        String imageId = intent.getStringExtra("image");
        String currentTag;
        ArrayList<String> newTags = new ArrayList<String>();
        if(newTags.contains(imageId))
        {
            int i = newTags.indexOf(imageId) + 1;
            textView.setText(newTags.get(i));
        }
        //display
        currentTag = textView.getText().toString();
        String []Tags;
        Tags = currentTag.split(" ");

        String display = "";


        boolean isExisted = false;

        switch (view.getId()){

            case R.id.tag_animal:
                checking(currentTag, "#animal");
                //textView.setText(currentTag + "#" + ((Button)view).getText() + " ");
                break;
            case R.id.tag_family:
                checking(currentTag, "#family");
                //textView.setText(currentTag + "#" + ((Button)view).getText() + " ");
                break;
            case R.id.tag_favorite:
                checking(currentTag, "#favorite");
                //textView.setText(currentTag + "#" + ((Button)view).getText() + " ");
                break;
            case R.id.tag_food:
                checking(currentTag, "#food");
                //textView.setText(currentTag + "#" + ((Button)view).getText() + " ");
                break;
            case R.id.tag_friend:
                checking(currentTag, "#friend");
                //textView.setText(currentTag + "#" + ((Button)view).getText() + " ");
                break;
            case R.id.tag_human:
                checking(currentTag, "#human");
                //textView.setText(currentTag + "#" + ((Button)view).getText() + " ");
                break;
            case R.id.tag_landscape:
                checking(currentTag, "#landscape");
                //textView.setText(currentTag + "#" + ((Button)view).getText() + " ");
                break;
            case R.id.tag_other:
                checking(currentTag, "#other");
                //textView.setText(currentTag + "#" + ((Button)view).getText() + " ");
                break;
            case R.id.tag_plant:
                checking(currentTag, "#plant");
                //textView.setText(currentTag + "#" + ((Button)view).getText() + " ");
                break;
            case R.id.but_cancel:
                textView.setText(" ");
                break;
            /*case R.id.but_save:
                newTags.add(imageId);
                newTags.add(textView.getText().toString());
                break;*/
        }
    }
    public void checking(String currentTag, String add)
    {
        String display = "";
        boolean isExisted = false;
        String [] Tags = currentTag.split(" ");
        List<String> originalTags = new ArrayList<String>();
        for (int i=0; i<Tags.length; i++)
            originalTags.add(Tags[i]);

        HashSet<String> previous = new HashSet<String>(originalTags);
        previous.add(add);

        for(int i=0; i<Tags.length; i++){
            if (Tags[i].equals(add))
                isExisted = true;
        }
        if (isExisted)
            previous.remove(add);

        for (String i : previous)
            display = display + i + " ";

        textView.setText(display);
    }
}

