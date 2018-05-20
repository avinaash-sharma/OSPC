package com.example.aviro.ospc;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

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

public class Subtable extends AppCompatActivity {

   // SQLiteListAdapter list;
    ListView listview1;
    String ins,ana,ip;
    String[] pollutant,color;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subtable);
        listview1 = (ListView) findViewById(R.id.listview1);
        Intent intent=getIntent();
        ins=intent.getStringExtra("ins");
        ana=intent.getStringExtra("ana");
        ip=getResources().getString(R.string.IP);
        new ExecuteTask().execute(ins,ana);


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
            HttpPost httpPost = new HttpPost(ip+"data.php");
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("plant_name", valuse[0]));
            list.add(new BasicNameValuePair("analyzer", valuse[1]));
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
            String[] p = new String[size];
            String[] ad=new String[size];
            String[] si=new String[size];
            String[] pol=new String[size];
            String[] sta=new String[size];


            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                p[i] = obj.getString("plant_name");
                ad[i] = obj.getString("address");
                si[i]=obj.getString("station_id");
                pol[i]=obj.getString("pollutants");
                sta[i]=obj.getString("status_");

            }

            SQLiteListAdapter adapter = new SQLiteListAdapter(Subtable.this,p,ad,si,pol,sta);
            listview1.setAdapter(adapter);



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

}
