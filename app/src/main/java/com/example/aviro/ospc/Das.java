package com.example.aviro.ospc;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

public class Das extends AppCompatActivity {


    Spinner spinnerins,spinnerana;
    String[] analyzer = {"AAQ","STACK","WATER"};
    String ins,ana,ip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_das);
        spinnerins=(Spinner)findViewById(R.id.spinnerins);
        spinnerana=(Spinner)findViewById(R.id.spinnerplt);

        ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,analyzer);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerana.setAdapter(aa);
        ip=getResources().getString(R.string.IP);
        new ExecuteTask().execute("fetchuser");
        spinnerana.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                ana=spinnerana.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerins.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ins = spinnerins.getSelectedItem().toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }
    public void submit(View v)
    {
        Intent intent=new Intent(getApplication(),Subtable.class);
        intent.putExtra("ins",ins);
        intent.putExtra("ana",ana);
        startActivity(intent);
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
            HttpPost httpPost = new HttpPost(ip+"plan.php");
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("tablename", valuse[0]));
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
            String[] s = new String[size];

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                s[i] = obj.getString("plant_id");

            }
            ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,s);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerins.setAdapter(aa);

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



