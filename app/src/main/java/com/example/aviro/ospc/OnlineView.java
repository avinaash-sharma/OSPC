package com.example.aviro.ospc;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

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

public class OnlineView extends AppCompatActivity {

    ArrayList<String> cStation = new ArrayList<String>();
    ArrayList<String> cPollution = new ArrayList<String>();
    Spinner Pollutant, stationId;
    // String sValue,fdateText,tdateText,pollutantSpinner;
//    TableView<String[]> tableView;
    TextView plantName, analyzer, fromDate, toDate, timeUnit, textView, textView2;
    String selectedId, selectedPollutant, ip, query, fdateText, tdateText;
    int length, length2;
    LinearLayout linearLeft, linearRight;
    ArrayAdapter<String> spinnerArrayAdapter,spinnerArrayAdapter2 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_view);

        linearLeft = (LinearLayout) findViewById(R.id.linearLeft);
        linearRight = (LinearLayout) findViewById(R.id.linearRight);

        cPollution = (ArrayList<String>) getIntent().getSerializableExtra("Pollutions");
        cStation = (ArrayList<String>) getIntent().getSerializableExtra("Stations");
        Bundle bundle = getIntent().getExtras();
        ip = getResources().getString(R.string.IP);

        fdateText = bundle.getString("FromDate");
        tdateText = bundle.getString("ToDate");

        String plantSpinner = bundle.getString("PlantName");
        String plantAnalyzer = bundle.getString("plantAnalyzer");
        String time = bundle.getString("Time");
        timeUnit = (TextView) findViewById(R.id.time);
        plantName = (TextView) findViewById(R.id.plantName);
        analyzer = (TextView) findViewById(R.id.analyzer);
        fromDate = (TextView) findViewById(R.id.fromDate);
        toDate = (TextView) findViewById(R.id.toDate);
        timeUnit = (TextView) findViewById(R.id.time);

        Pollutant = (Spinner) findViewById(R.id.pollutant);
        stationId = (Spinner) findViewById(R.id.stationID);


        fromDate.setText(fdateText);
        toDate.setText(tdateText);
        System.out.println(cPollution);
        System.out.println(cStation);
        timeUnit.setText(time);
        plantName.setText(plantSpinner);
        analyzer.setText(plantAnalyzer);


        bundle.clear();

        spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cPollution);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        Pollutant.setAdapter(spinnerArrayAdapter);

        spinnerArrayAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cStation);
        spinnerArrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
        stationId.setAdapter(spinnerArrayAdapter2);
        stationId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                Pollutant.setSelection(0);
                selectedId = stationId.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Pollutant.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> parent, android.view.View view, int position, long id) {
                selectedPollutant = Pollutant.getSelectedItem().toString();
                Query_build ob = new Query_build();
                query = ob.querybuild(fdateText, tdateText, selectedId, selectedPollutant);
                new ExecutionTask().execute(query);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
        System.out.println("FromDate:" + fdateText + " ToDate:" + tdateText);
    }

    class ExecutionTask extends AsyncTask<String, Integer, String> {

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
            HttpPost httpPost = new HttpPost("http://192.168.43.156:8181/os/online3.php");
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("query", valuse[0]));
//            list.add(new BasicNameValuePair("para_code", valuse[1]));
            httpPost.setEntity(new UrlEncodedFormEntity(list));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            s = readdata(httpResponse);
            System.out.println("List=" +list);

        } catch (Exception exception) {
        }
        return s;
    }

    private void fetch(String response) {
        try {
            JSONArray array = new JSONArray(response);
            length = array.length();
            String[] s1 = new String[length];
            String[] s2 = new String[length];


            for (int i = 0; i < length; i++) {
                JSONObject obj = array.getJSONObject(i);
                s1[i] = obj.getString("recorded_time");
                s2[i] = obj.getString("recorded_level");
                System.out.println("Datai1:" + s1[i]);
                System.out.println("Data:" + s2[i]);

            }


            int l = s1.length;
            int j = 0;
            System.out.println("Left Text");
            linearLeft.removeAllViewsInLayout();

            linearRight.removeAllViewsInLayout();
            for (int i = 0; i < l; i++) {

                textView = new TextView(this);
                textView.setId(i);
                textView.setText(s1[j]);

                System.out.println(i + "-->" + s1[j]);
                j++;
                linearLeft.addView(textView);
            }

            int k = 0;
            for (int i = 0; i < l; i++) {

                textView2 = new TextView(this);
                textView2.setId(i);
                textView2.setText(s2[k]);

                System.out.println(i + "-->" + s2[k]);
                k++;
                linearRight.addView(textView2);
            }


            s1 = null;
            s2 = null;
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
