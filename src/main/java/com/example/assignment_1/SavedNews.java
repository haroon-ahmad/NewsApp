package com.example.assignment_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.assignment_1.R;

import java.util.ArrayList;

public class SavedNews extends AppCompatActivity {
     DatabaseHandler db;
    ArrayList<myDataClass> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_news);
        db=new DatabaseHandler(this);
        //Intent i=getIntent();
        //list=(ArrayList<myDataClass>)i.getIntegerArrayListExtra("arr");
        list=db.getAllNews();
        ListView lv=(ListView) findViewById(R.id.savednews);
        CustomAdapter adapter= new CustomAdapter(this,R.layout.listview,list,db);
        lv.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_saved_news, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
