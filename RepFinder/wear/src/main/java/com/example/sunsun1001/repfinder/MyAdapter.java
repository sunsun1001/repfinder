package com.example.sunsun1001.repfinder;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;

import android.support.wearable.view.CardFragment;
import android.support.wearable.view.FragmentGridPagerAdapter;

import android.util.Log;
import android.view.Gravity;
import android.view.View;



public class MyAdapter extends FragmentGridPagerAdapter implements View.OnClickListener {

    public final Context myContext;
    public static String data;
    public static Page[] names;

    public MyAdapter(Context ctx, FragmentManager fm, String data) {
        super(fm);
        myContext = ctx;
        this.data = data;


        /* load string as following (county|name...|obama%|romney%): "county|name|33.6|55.3" */
        /* name = "|display name, party, term date, bioID|" */
        String delims = "[|]";
        String delimsName = "[,]";

        String[] dataSplit = this.data.split(delims);

        this.names = new Page[dataSplit.length];
        for (int i = 0; i < dataSplit.length - 3; i++) {
            String[] nameSplit = dataSplit[i+1].split(delimsName);
            String party = "";
            if (nameSplit[1].equalsIgnoreCase("R")) {
                party += "Republican";
            } else if (nameSplit[1].equalsIgnoreCase("D")) {
                party += "Democrat";
            } else {
                party += "Independent";
            }
            Log.v("v", "Name: " + nameSplit[0]);
            Log.v("v", "Party: " + party);

            this.names[i] = new Page(party, nameSplit[0]);
        }
    }

    @Override
    public void onClick(View v) {
        Log.v("v", "clicked card");
    }

    private static class Page {
        String name;
        String party;
        int cardGravity = Gravity.CENTER_VERTICAL;
        boolean expansionEnabled = false;
        float expansionFactor = 1.0f;
        int expansionDirection = CardFragment.EXPAND_DOWN;

        public Page(String name, String party) {
            this.name = name;
            this.party = party;
        }
    }

    @Override
    public Fragment getFragment(int row, int col) {
        Log.v("v", "Data from getFrag: " + this.names);
        Page page = this.names[col];
        String title = page.name;
        String text = page.party;
        CardFragment fragment = CardFragment.create(title, text);
        // Advanced settings
        fragment.setCardGravity(page.cardGravity);
        fragment.setExpansionEnabled(page.expansionEnabled);
        fragment.setExpansionDirection(page.expansionDirection);
        fragment.setExpansionFactor(page.expansionFactor);
        return fragment;
    }

    @Override
    public int getRowCount() {
        return 1;
    }

    @Override
    public int getColumnCount(int rowNum) {
        String delims = "[|]";
        String[] dataSplit = this.data.split(delims);
        return dataSplit.length-3;
    }
}
