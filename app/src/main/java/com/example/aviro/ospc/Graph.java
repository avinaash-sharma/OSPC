package com.example.aviro.ospc;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Graph extends AppCompatActivity {
    String ins, ana, sn;
    Spinner pc;
    String pol,ip;
    String[] date,level;
    double l[];
    int t[];
    String tvtextdate;
    TextView tvDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        Intent intent = getIntent();
        ins = intent.getStringExtra("plantname");
        ana = intent.getStringExtra("analyser");
        sn = intent.getStringExtra("stationid");
       // Toast.makeText(this,ins+" "+ana+" "+sn,Toast.LENGTH_LONG).show();
        pc = (Spinner) findViewById(R.id.spinnerpc);
        ip=getResources().getString(R.string.IP);
        new ExecuteTask().execute(ins,ana,sn);
        pc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            Timer timer;
            TimerTask timerTask;
            final Handler handler = new Handler();

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pol=pc.getSelectedItem().toString();
                new ExecutegTask().execute(pol,ins,ana,sn);
                //startTimer();
            }


            public void initializeTimerTask() {

                timerTask = new TimerTask() {
                    public void run() {
                        //use a handler to run a toast that shows the current timestamp
                        handler.post(new Runnable() {
                            public void run() {
                                new ExecuteTask().execute(ins,ana,sn);
                                //  Toast.makeText(getApplicationContext(), "Repeat ", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                };
            }

            public void startTimer() {
                //set a new Timer
                timer = new Timer();

                //initialize the TimerTask's job
                initializeTimerTask();

                //schedule the timer, after the first 0ms the TimerTask will run every 1m
                timer.schedule(timerTask, 0, 180 * 1000); //
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    class ExecuteTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            String res = PostData(params);
            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            fetch(result);
        }
    }

    public String PostData(String[] valuse) {
        String s = "";
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(ip+"pc.php");
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("plant_name", valuse[0]));
            list.add(new BasicNameValuePair("analyzer", valuse[1]));
            list.add(new BasicNameValuePair("short_name", valuse[2]));
            //list.add(new BasicNameValuePair("tablename", valuse[3]));
            httpPost.setEntity(new UrlEncodedFormEntity(list));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            s = readdata(httpResponse);
        } catch (Exception exception) {
        }
        return s;
    }

    private void fetch(String response) {
        try {
            JSONArray array = new JSONArray(response);
            int size = array.length();
            String[] st = new String[size];

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                st[i] = obj.getString("parameter_code");

            }
            ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, st);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            pc.setAdapter(aa);

        } catch (JSONException e) {

        }
    }

    public String readdata(HttpResponse res) {
        InputStream is = null;
        String return_text = "";
        try {
            is = res.getEntity().getContent();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is));
            String line = "";
            StringBuffer sb = new StringBuffer();
            while (null != (line = bufferedReader.readLine())) {
                sb.append(line);
            }
            return_text = sb.toString();
        } catch (Exception e) {

        }
        return return_text;
    }
    class ExecutegTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            String res = PostDat(params);
            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            fetc(result);
            final GraphView graph = (GraphView) findViewById(R.id.graph1);
            this.initi();
            //Toast.makeText(getApplicationContext(),t[0]+" "+l[0],Toast.LENGTH_LONG).show();

            DataPoint[] dd = new DataPoint[10];
            for (int k = 0; k < 10; k++)
                dd[k] = new DataPoint(t[k], l[k]);
            LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dd);
            graph.getGridLabelRenderer().setNumHorizontalLabels(3);
            graph.getViewport().setXAxisBoundsManual(true);
            graph.getViewport().setMinX(t[0]);
            graph.getViewport().setMaxX(t[4]);

            graph.getViewport().setScrollable(true);
            graph.getViewport().setScalable(true);

            graph.addSeries(series);
            graph.getViewport().setScalable(true);
            graph.getViewport().setScalableY(true);
            graph.getViewport().setScrollable(true);
            series.setDrawDataPoints(true);
            series.setBackgroundColor(Color.argb(255,255,60,60));
            series.setColor(Color.BLACK);
           // Toast.makeText(getApplicationContext(),"Graph Sucess  "+dd[0],Toast.LENGTH_LONG).show();
            graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
                @Override
                public String formatLabel(double value, boolean isValueX) {
                    if (isValueX) {
                        // show normal x values
                        int v=(int)value;
                        int a=v%100;
                        String b;
                        if(a<10)
                            b="0"+a;
                        else
                            b=Integer.toString(a);
                        v=v/100;
                        String d=Integer.toString(v)+":"+b;
                        return  d;
                        //return  super.formatLabel(value, isValueX);
                    } else {
                        // show currency for y values
                        return super.formatLabel(value, isValueX);
                    }
                }
            });
            series.setOnDataPointTapListener(new OnDataPointTapListener() {
                @Override
                public void onTap(Series series, DataPointInterface dataPoint) {
                    int v=(int)dataPoint.getX();
                    int a=v%100;
                    String b;
                    if(a<10)
                        b="0"+a;
                    else
                        b=Integer.toString(a);
                    v=v/100;
                    String d=Integer.toString(v)+":"+b;

                    Toast.makeText(graph.getContext(),"Time: "+d+" Value: "+dataPoint.getY(),Toast.LENGTH_LONG).show();
                }
            });


        }


    
        private int getTime(String s) {
            String t[] = s.split(" ");
            String h[] = t[1].split(":");
            String temp = h[0] + h[1];
            int i = Integer.parseInt(temp);
            return i;
        }

        public void initi() {
            for (int i = 0; i < level.length; i++) {
                l[i] = Double.parseDouble(level[i]);
                t[i] = getTime(date[i]);
            }
        }

    }
    public String PostDat(String[] valuse) {
        String s = "";
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(ip+"graph.php");
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("parameter_code", valuse[0]));
            list.add(new BasicNameValuePair("plant_name", valuse[1]));
            list.add(new BasicNameValuePair("analyzer", valuse[2]));
            list.add(new BasicNameValuePair("short_name", valuse[3]));
            //list.add(new BasicNameValuePair("tablename", valuse[3]));
            httpPost.setEntity(new UrlEncodedFormEntity(list));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            s = readdata(httpResponse);
        } catch (Exception exception) {
        }
        return s;
    }

    private void fetc(String response) {
        try {
            JSONArray array = new JSONArray(response);
            int size = array.length();
            date = new String[size];
            level=new String[size];
            l=new double[size];
            t=new int[size];

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                date[i] = obj.getString("recorded_time");
                level[i]=obj.getString("recorded_level");

            }


        } catch (JSONException e) {

        }
    }

}

