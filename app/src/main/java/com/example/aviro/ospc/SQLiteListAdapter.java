package com.example.aviro.ospc;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by USER on 2/4/2018.
 */

public class SQLiteListAdapter extends ArrayAdapter<String> {


    private final Activity context;
    String[] PlantName;
    String[] Add;
    String[] Loc;
    String[] Pollut;
    String[] stts;
    String[] polutant,color;

    public SQLiteListAdapter(Activity context, String[] pname, String[] address, String[] loaction, String[] pollutants, String[] status) {
        super(context,R.layout.listviewdatalayout,pname);

        this.context = context;
        this.Add = address;
        this.PlantName = pname;
        this.Loc = loaction;
        this.Pollut = pollutants;
        this.stts = status;
    }

    @Override
    public View getView(final int position, final View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.listviewdatalayout, null, true);
        TextView pname = (TextView) rowView.findViewById(R.id.pname);
        pname.setText(PlantName[position]+"          ");
        TextView add = (TextView) rowView.findViewById(R.id.address);
        add.setText(Add[position]+"          ");
        TextView si = (TextView) rowView.findViewById(R.id.location);
        si.setText(Loc[position]+"          ");
       getcolorpol(Pollut[position]);
        TextView pol = (TextView) rowView.findViewById(R.id.pollutants);
        String s="  ";
        for(int i=1;i<polutant.length;i++)
        {
            if(polutant[i]!=null)
            {
                s=s+"       "+polutant[i];
            }
        }


                pol.setText(s+"          ");
              // Toast.makeText(context,polutant[i],Toast.LENGTH_SHORT).show();

//                if (color[i].equalsIgnoreCase("RED")) {
//                    pol.setTextColor(Color.RED);
//                } else {
//                    pol.setTextColor(Color.GREEN);
//                }
//                if(polutant[i]!=null)
//                Toast.makeText(context,polutant[i],Toast.LENGTH_SHORT).show();

        TextView status = (TextView) rowView.findViewById(R.id.status);
        status.setText(stts[position]+"           ");
        return rowView;




    }

    private void getcolorpol(String s) {

        String a[],b[] = null,temp[] = null;
        //String color[],polutant[];
        String p[]=new String[10];
        a=s.split(">");
        int i,j,k;
        int co,po;
        co=0;
        po=0;
        int z,y;
        z=1;
        y=1;
        color=new String[a.length];
        polutant=new String[a.length];
        for(i=0;i<a.length;i++)
        {
            //System.out.println(a[i]);
            temp=a[i].split("<");
            for(j=0;j<temp.length;j++)
            {
                //  System.out.println(temp[j]);
                if(temp[j].equals("HF"))
                {
                    //System.out.println("true"+temp[j]);
                    polutant[z]="HF";
                    p[z]=temp[j];
                    z=z+1;
                }
                if(temp[j].equals("PM10"))
                {
                    //System.out.println("true"+temp[j]);
                    polutant[z]=temp[j];
                    p[z]=temp[j];
                    z=z+1;
                }
                if(temp[j].equals("PM2.5"))
                {
                    //System.out.println("true"+temp[j]);
                    polutant[z]=temp[j];
                    p[z]=temp[j];
                    z=z+1;
                }


            }

            po=temp.length;
            b=a[i].split("=");
            for(k=0;k<b.length;k++)
            {
                //	  System.out.println(b[k]);
                if(b[k].equals("red"))
                {
                    color[y]=b[k].toUpperCase();
                    y=y+1;
                }
            }
            co=b.length;

        }

    }

}