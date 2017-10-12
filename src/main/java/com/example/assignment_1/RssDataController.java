package com.example.assignment_1;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


/**
 * Created by Haroon on 10/16/2015.
 */
public class RssDataController extends AsyncTask<Void,Integer,String> {
        private XmlPullParserFactory xmlFactoryObject;
        public volatile boolean parsingComplete = true;

        //ImageView img;
        ListView lv;
        String URL1;
        String URL2;
        String URL3;
        TextView title1;
        //ArrayList <DataClass> NewsDataList;
        Context c;
        ProgressBar s;
        ArrayList<myDataClass> filterList;
        ArrayList<myDataClass> abc=new ArrayList<myDataClass>();
        ArrayList<myDataClass> cnn=new ArrayList<myDataClass>();
        ArrayList<myDataClass> huffington=new ArrayList<myDataClass>();


        TextView main_news;
         CustomAdapter adapter;
        RssDataController(CustomAdapter _adapter,String u1,String u2,String u3,Context c_,ProgressBar s_,ListView v,/*ImageView i*/ArrayList<myDataClass> l)
        {
            adapter=_adapter;
                lv=v;
                URL1=u1;
                URL2=u2;
                URL3=u3;
                c=c_;
                s=s_;
                //img=i;
                filterList=l;
        }
        @Override
        protected String doInBackground(Void... params) {
                publishProgress(1);
                try {
                        URL url = new URL(URL1);
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setReadTimeout(10000 /* milliseconds */);
                        conn.setConnectTimeout(15000 /* milliseconds */);
                        conn.setRequestMethod("GET");
                        conn.setDoInput(true);
                        // Starts the query
                        conn.connect();
                    //Toast.makeText(RssDataController.this,"Connected",Toast.LENGTH_SHORT).show();
                        InputStream stream = conn.getInputStream();
                        xmlFactoryObject = XmlPullParserFactory.newInstance();
                        XmlPullParser myparser1 = xmlFactoryObject.newPullParser();
                        myparser1.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                        myparser1.setInput(stream, null);
                        parseXMLAndStoreIt(myparser1,abc);
                        stream.close();
                } catch (Exception e) {
                }
            try {
                URL url = new URL(URL2);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();
                //Toast.makeText(RssDataController.this,"Connected",Toast.LENGTH_SHORT).show();
                InputStream stream = conn.getInputStream();
                xmlFactoryObject = XmlPullParserFactory.newInstance();
                XmlPullParser myparser2 = xmlFactoryObject.newPullParser();
                myparser2.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                myparser2.setInput(stream, null);
                parseXMLAndStoreIt(myparser2,huffington);
                stream.close();
            } catch (Exception e) {
            }
            try {
                URL url = new URL(URL3);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                // Starts the query
                conn.connect();
                //Toast.makeText(RssDataController.this,"Connected",Toast.LENGTH_SHORT).show();
                InputStream stream = conn.getInputStream();
                xmlFactoryObject = XmlPullParserFactory.newInstance();
                XmlPullParser myparser3 = xmlFactoryObject.newPullParser();
                myparser3.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
                myparser3.setInput(stream, null);
                parseXMLAndStoreIt(myparser3,cnn);
                stream.close();
            } catch (Exception e) {
            }
            join(abc,huffington,cnn);
                return null;
        }

        protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                s.setVisibility(View.VISIBLE);
        }



        public void parseXMLAndStoreIt(XmlPullParser myParser,ArrayList<myDataClass> arr)
        {
                ArrayList<String> title=new ArrayList<String>();
                ArrayList<String> link=new ArrayList<String>();
                ArrayList<String> date=new ArrayList<String>();
                ArrayList<String> desc=new ArrayList<String>();
                ArrayList<String> img=new ArrayList<String>();
                int event;
                String text=null;
                try {
                        event = myParser.getEventType();
                        while (event != XmlPullParser.END_DOCUMENT) {


                                String name=myParser.getName();
                                switch (event){
                                        case XmlPullParser.START_TAG:
                                                break;
                                        case XmlPullParser.TEXT:
                                                text = myParser.getText();
                                                break;
                                        case XmlPullParser.END_TAG:

                                                if(name.equals("title")){
                                                        title.add(text);

                                                }
                                                else if(name.equals("link")){
                                                        link.add(text);

                                                }
                                                else if(name.equals("description")){
                                                        desc.add(text);

                                                }
                                                else if(name.equals("url"))
                                                {
                                                        img.add(text);
                                                }
                                                else if (name.equals("pubDate"))
                                                {
                                                        date.add(text);

                                                }


                                                else
                                                {

                                                }
                                                break;
                                }

                                event = myParser.next();

                        }

                        parsingComplete = false;
                } catch (Exception e) {
                        e.printStackTrace();
                }

                for(int i=0;i<title.size();i++)
                {
                        myDataClass obj=new myDataClass();
                        obj.setTitle(title.get(i));
                        obj.setLink(link.get(i));
                        obj.setDate(date.get(i));
                        obj.setDes(desc.get(i));
                       // obj.setImage(img.get(i));
                        arr.add(obj);
                }
                //title1.setText(title.get(0));
        }
        /*public static Bitmap getBitmapFromURL(String src) {
                try {
                        URL url = new URL(src);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setDoInput(true);
                        connection.connect();
                        InputStream input = connection.getInputStream();
                        Bitmap myBitmap = BitmapFactory.decodeStream(input);
                        return myBitmap;
                } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                }
        }*/
    public void join(ArrayList<myDataClass> abc,ArrayList<myDataClass> huffington,ArrayList<myDataClass> cnn) {
        for(int i=0;i<10;i++)
        {
            myDataClass obj=new myDataClass();
            obj=abc.get(i);
            filterList.add(obj);
            obj=huffington.get(i);
            filterList.add(obj);
            obj=cnn.get(i);
            filterList.add(obj);
        }
    }

        protected void onPostExecute(String result) {
                s.setVisibility(View.INVISIBLE);
                //main_news.setText(filterList.get(1).getTitle());

               // CustomAdapter adapter= new CustomAdapter(c,R.layout.listview,filterList,"row");
            adapter.notifyDataSetChanged();
                /*String urr1="http://images.thenews.com.pk/updates_pics/Pakistan-Plane-Landingpermission_4-5-2015_180478_l.jpg";
                String urr2=filterList.get(1).getImage();
                String urr3=filterList.get(1).getTitle();

                (new PostTask_live(title1,s,urr3)).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                (new ImageDownloaderTask(img,urr2)).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);*/


        }


}
