package com.example.aviro.ospc;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

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

public class gra extends AppCompatActivity {
    String[] date,level;
    double l[];
    int t[];
    String pc[];
    Spinner spinnerplantname,spinnerstationname,spinneranalyzer;
    String[] analyzer = {"AAQ","STACK","WATER"};
    String selectedplantname,selectedstationname,selectedanalyzer;
    ArrayAdapter aa;
     String ip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gra);
        spinnerplantname=(Spinner)findViewById(R.id.spinnerplantname);
        spinnerstationname=(Spinner)findViewById(R.id.spinnerstationname);
        spinneranalyzer=(Spinner)findViewById(R.id.spinneranalyzer);
        aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,analyzer);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ip=getResources().getString(R.string.IP);


        new GetPlantName().execute("fetchuser");

        spinnerplantname.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                selectedplantname = spinnerplantname.getSelectedItem().toString();
                new GetStationName().execute(selectedplantname,"fetchuser");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerstationname.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                selectedstationname = spinnerstationname.getSelectedItem().toString();
                spinneranalyzer.setAdapter(aa);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        spinneranalyzer.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                selectedanalyzer=spinneranalyzer.getSelectedItem().toString();
                new GetPc().execute(selectedplantname,selectedstationname,selectedanalyzer,"fetchuser");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        Button bt=(Button)findViewById(R.id.button);
        bt.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                Intent intent=new Intent(getApplication(),Graph.class);
                intent.putExtra("plantname",selectedplantname);
                intent.putExtra("analyser",selectedanalyzer);
                intent.putExtra("stationid",selectedstationname);
                startActivity(intent);

            }
        });


    }

/////////////////////Fetch Plant Spinner//////////////////////////

    class GetPlantName extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            String res = PostDataGPN(params);
            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            fetchGPN(result);
        }
    }

    public String PostDataGPN(String[] valuse) {
        String s = "";
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(ip+"plant.php");
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("tablename", valuse[0]));
            httpPost.setEntity(new UrlEncodedFormEntity(list));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            s = readdataGPN(httpResponse);
        } catch (Exception exception) {
        }
        return s;
    }

    private void fetchGPN(String response) {
        try {
            JSONArray array = new JSONArray(response);
            int size = array.length();
            String[] p = new String[size];

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                p[i] = obj.getString("plant_name");

            }
            ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,p);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerplantname.setAdapter(aa);

        } catch (JSONException e) {

        }
    }


    public String readdataGPN(HttpResponse res) {
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


    /////////////////////Fetch Station Spinner//////////////////////////

    class GetStationName extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            String res = PostDataGSN(params);
            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            fetchGSN(result);
        }
    }

    public String PostDataGSN(String[] valuse) {
        String s = "";
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(ip+"station.php");
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("station",valuse[0]));
            list.add(new BasicNameValuePair("tablename", valuse[1]));
            httpPost.setEntity(new UrlEncodedFormEntity(list));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            s = readdataGSN(httpResponse);
        } catch (Exception exception) {
        }
        return s;
    }

    private void fetchGSN(String response) {
        try {
            JSONArray array = new JSONArray(response);
            int size = array.length();
            String[] s = new String[size];

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                s[i] = obj.getString("short_name");

            }
            ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,s);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerstationname.setAdapter(aa);

        } catch (JSONException e) {

        }
    }


    public String readdataGSN(HttpResponse res) {
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

    //////////////////////////////////////////////////
    class GetPc extends AsyncTask<String, Integer, String>
    {
        @Override
        protected String doInBackground(String... params) {
            String res = PostDataPc(params);
            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            fetchpc(result);
        }
    }

    public String PostDataPc(String[] valuse) {
        String s = "";
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(ip+"pc.php");
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("plant_name",valuse[0]));
            list.add(new BasicNameValuePair("short_name",valuse[1]));
            list.add(new BasicNameValuePair("analyzer", valuse[2]));
            list.add(new BasicNameValuePair("tablename", valuse[3]));
            httpPost.setEntity(new UrlEncodedFormEntity(list));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            s = readdatapc(httpResponse);
        } catch (Exception exception) {
        }
        return s;
    }

    private void fetchpc(String response) {
        try {
            JSONArray array = new JSONArray(response);
            int size = array.length();
            String[] s=new String[size];
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                s[i] = obj.getString("parameter_code");
            }
            pc=s;

        } catch (JSONException e) {

        }
    }


    public String readdatapc(HttpResponse res) {
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

