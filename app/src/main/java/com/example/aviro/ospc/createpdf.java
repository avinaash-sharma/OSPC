package com.example.aviro.ospc;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jagdish on 21-03-2018.
 */

public class createpdf extends FragmentActivity {
    String fdateText,tdateText,plantSpinner,plantAnalyzer,time;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adhoc);
        Bundle bundle = getIntent().getExtras();

        fdateText = bundle.getString("FromDate");
        tdateText = bundle.getString("ToDate");

        plantSpinner = bundle.getString("PlantName");
        plantAnalyzer = bundle.getString("plantAnalyzer");
        time = bundle.getString("Time");
    }

    class ExecutionTask extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            String res=PostData(params);
            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            //System.out.println("ONE");
            fetch(result);
        }


    }
    public String PostData(String[] valuse) {


        //System.out.println(query);


        String s = "";
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://192.168.43.156:8181/os/online3.php");
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("query", valuse[0]));
//            list.add(new BasicNameValuePair("para_code", valuse[1]));
            httpPost.setEntity(new UrlEncodedFormEntity(list));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            s = readdata(httpResponse);
            System.out.println("List="+list);
        } catch (Exception exception) {
        }
        return s;
    }

    private void fetch(String response) {
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


