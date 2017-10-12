package com.example.assignment_1;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;



public class CustomAdapter extends ArrayAdapter<myDataClass> {
    Context c;
    int layoutFile;
    ArrayList<myDataClass> data;
    DatabaseHandler db;
    int position1;
    String check;

    public CustomAdapter(Context context, int resource, ArrayList<myDataClass> objects,DatabaseHandler db) {

        super(context, resource, objects);
        c = context;
        layoutFile = resource;
        data = objects;
        this.db=db;
    }

    @Override
    public int getCount() {
        if (data != null)
            return data.size();
        else
            return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v, row;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) c).getLayoutInflater();

            row = inflater.inflate(R.layout.listview, parent, false);

        } else {
            row = (View) convertView;
        }
         TextView txt = (TextView) row.findViewById(R.id.textView5);
        txt.setText(data.get(position).getTitle());

        TextView txt1 = (TextView) row.findViewById(R.id.textView6);
        txt1.setText(data.get(position).getDate());
        txt1.setTextColor(Color.LTGRAY);



        TextView txt3 = (TextView) row.findViewById(R.id.textView8);
        txt3.setText(data.get(position).getLink());

        WebView wbv =(WebView) row.findViewById(R.id.webView);
        wbv.loadData(data.get(position).getDes(), "text/html", null);

        Button btn_share=(Button)row.findViewById(R.id.save);
        Button details=(Button) row.findViewById (R.id.button2);
        btn_share.setOnClickListener(new View.OnClickListener() {
            int pos=position;
            @Override
            public void onClick(View v) {
                db.addNews(data.get(pos));
            }

        });

        details.setOnClickListener(new View.OnClickListener() {
            int pos = position;

            @Override
            public void onClick(View v) {
                Intent i = new Intent(c, DetailedNews.class);
                String Title = data.get(position).getTitle();
                 String Date = data.get(position).getDate();
                 String des = data.get(position).getDes();
                i.putExtra("title", Title);
                i.putExtra("date", Date);
                i.putExtra("description", des);
                c.startActivity(i);
            }

        });

        return row;
    }
}
