package com.example.sunsun1001.repfinder;

/**
 * Created by sunsun1001 on 3/1/16.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.widget.Button;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.graphics.Color;

public class CustomAdapter extends BaseAdapter{

    String [] senatorNames;
    String [] siteUrl;
    String [] emailAdd;
    String [] tweet;
    Context context;
    int [] imageId;
    public Intent newIntent;
    private static LayoutInflater inflater=null;

    public CustomAdapter(listReps mainActivity, int[] picture, String[] names,
                         String[] url, String[] email, String[] tweet) {
        // TODO Auto-generated constructor stub

        this.senatorNames = names;
        this.siteUrl = url;
        this.emailAdd = email;
        this.tweet = tweet;
        this.imageId = picture;
        this.newIntent = null;
        context = mainActivity;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return senatorNames.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder {
        TextView textSenatorNames;
        ImageView img;
        Button moreDetails;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder = new Holder();
        View myRowView = inflater.inflate(R.layout.row, null);

        // Formatting the Rows

        holder.textSenatorNames=(TextView) myRowView.findViewById(R.id.name);

        String output = "\n\n\t\t\t" + senatorNames[position] +
                "\n\n\n\t\t\t" + siteUrl[position] + "\n\n\t\t\t" +
                emailAdd[position] + "\n\n\t\t\t" + tweet[position] + "\n\n\n\n\n";
        SpannableString text = new SpannableString(output);
        holder.textSenatorNames.setText(output);



        holder.moreDetails = (Button) myRowView.findViewById(R.id.details);
        holder.img=(ImageView) myRowView.findViewById(R.id.imageView1);
        //holder.textSenatorNames.setText(senatorNames[position]);
        holder.img.setImageResource(imageId[position]);

        myRowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                newIntent.putExtra("name", senatorNames[position]);
                newIntent.putExtra("picture", imageId[position]);

                context.startActivity(newIntent);
                Toast.makeText(context, "You Clicked " + senatorNames[position], Toast.LENGTH_SHORT).show();
            }
        });

        holder.moreDetails.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                newIntent.putExtra("name", senatorNames[position]);
                newIntent.putExtra("picture", imageId[position]);

                context.startActivity(newIntent);
            }
        });

        holder.textSenatorNames.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                newIntent.putExtra("name", senatorNames[position]);
                newIntent.putExtra("picture", imageId[position]);

                context.startActivity(newIntent);
            }
        });

        return myRowView;
    }

}
