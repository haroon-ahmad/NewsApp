package com.example.assignment_1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends Activity {
    private myDataClass[] listData;
    String urr;
    String urr1;
    String urr2;

    ArrayList<myDataClass> filterList = new ArrayList<myDataClass>();
     //async task
    ListView lv;
    Context c;
    ProgressBar s;
    DatabaseHandler db=new DatabaseHandler(this);
    CustomAdapter ad;
    Handler handler = new Handler();
    Timer    timer = new Timer();
    int time_refresh=60000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TimerTask doAsynchronousTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        try {
                            refresh(null);
                        }
                        catch (Exception e) {
                            // TODO Auto-generated catch block
                        }
                    }
                });
            }
        };
        timer.schedule(doAsynchronousTask, 0, time_refresh);
        refresh(null);
        AdapterView.OnItemClickListener rowListner = new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String Title;
                        String Date;
                        String des;
                        Intent i = new Intent(MainActivity.this, DetailedNews.class);
                        Title = filterList.get(position).getTitle();
                        Date = filterList.get(position).getDate();
                        des = filterList.get(position).getDes();
                        i.putExtra("title", Title);
                        i.putExtra("date", Date);
                        i.putExtra("description", des);
                        startActivity(i);
                    }
                };
                lv.setOnItemClickListener(rowListner);
            }

            @Override
            public boolean onCreateOptionsMenu(Menu menu) {
                // Inflate the menu; this adds items to the action bar if it is present.
                getMenuInflater().inflate(R.menu.menu_main, menu);
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

            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuinfo) {
                super.onCreateContextMenu(menu, v, menuinfo);
                MenuInflater inflater = getMenuInflater();
                //inflater.inflate(R.menu.context_menu,menu);
                menu.setHeaderTitle("Select The Action");
                menu.add(0, v.getId(), 0, "Save");//groupId, itemId, order, title
            }

            // @Override
    /*public boolean onContextItemSelected(MenuItem item)
    {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;

    }*/
            public void savednews(View v) {
                Intent in = new Intent(MainActivity.this, SavedNews.class);
                //in.putExtra("database",db);
                //ArrayList<myDataClass> arr=new ArrayList<myDataClass>();
                //arr=db.getAllNews();
                // in.putExtra("arr",arr);
                startActivity(in);
            }
        public void refresh(View v)
        {
            urr="http://feeds.abcnews.com/abcnews/topstories";
            urr1="http://feeds.huffingtonpost.com/c/35496/f/677045/index.rss";
            urr2="http://rss.cnn.com/rss/edition.rss";
            lv = (ListView) findViewById(R.id.listView1);
            ProgressBar _p = (ProgressBar) findViewById(R.id.progressBar1);
            s=_p;
            final CustomAdapter adapter= new CustomAdapter(this,R.layout.listview,filterList,db);
            ad=adapter;
            lv.setAdapter(adapter);
            (new RssDataController(adapter,urr,urr1,urr2,this,_p,lv,filterList)).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }

    }



