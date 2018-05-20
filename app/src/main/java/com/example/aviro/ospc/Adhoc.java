package com.example.aviro.ospc;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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


public class Adhoc extends FragmentActivity {


    RadioButton time;
    int size, size2;
    RadioGroup radioGroup;

    private DatePickerDialog.OnDateSetListener onDateSetListener;
    static EditText fromdatetext;
    static EditText todatetext;
    Spinner plantnamespinner, analyzerspinner, saveas;
    CheckBox selectAll, allStation, checkBox, checkBox2, chty, chty2;
    ArrayList<String> cPollutions = new ArrayList<String>();
    ArrayList<String> cStations = new ArrayList<String>();
    TextView infoDisp;
    ScrollView scrollView;
    LinearLayout linearlayout, innerLayout;
    String[] s;
    String industry, file;
    String analyzer, ip, fromdaytext, todaytext;
    String selectedAnalyzer, selectedPlantname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adhoc);

        fromdatetext = (EditText) findViewById(R.id.fromdatetext);

        todatetext = (EditText) findViewById(R.id.todatetext);
        plantnamespinner = (Spinner) findViewById(R.id.plantnamespinner);
        saveas = (Spinner) findViewById(R.id.saveasspinner);
        analyzerspinner = (Spinner) findViewById(R.id.analyzerspinner);
        selectAll = (CheckBox) findViewById(R.id.selectAll);
        allStation = (CheckBox) findViewById(R.id.allStations);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        linearlayout = (LinearLayout) findViewById(R.id.linearLayout);
//        linearlayoutr2 = (LinearLayout) findViewById(R.id.linearLayoutr2);
        radioGroup = findViewById(R.id.radioGroup);
        innerLayout = (LinearLayout) findViewById(R.id.innerLayout);
        ip = getResources().getString(R.string.IP);

        new ExecuteTask().execute("fetchuser");
        analyzerspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                analyzer = analyzerspinner.getSelectedItem().toString();
                //new ExecuteeTask().execute(industry,"fetchuser");
                new ExecuteeTask().execute(industry, analyzer);
                new ExecuteeeTask().execute(industry, analyzer);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        saveas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                file = saveas.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        plantnamespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                industry = plantnamespinner.getSelectedItem().toString();
                analyzerspinner.setSelection(0);
                new ExecuteeTask().execute(industry, analyzer);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        allStation.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked == true) {
                    for (int i = 0; i < size; i++) {
                        chty2 = (CheckBox) findViewById(i);
                        chty2.setChecked(true);
                    }
                } else {
                    for (int i = 0; i < size; i++) {
                        chty2 = (CheckBox) findViewById(i);
                        chty2.setChecked(false);
                    }
                }
            }
        });

        selectAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {


                int j = size;
                if (isChecked == true) {
                    for (int i = 0; i < size2; i++) {


                        chty = (CheckBox) findViewById(j);
                        chty.setChecked(true);
                        j++;
                    }
                } else {
                    for (int i = 0; i < size2; i++) {
                        chty = (CheckBox) findViewById(j);
                        chty.setChecked(false);
                        j++;
                    }
                }
            }
        });


        fromdatetext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog1(v);
            }
        });
        todatetext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog2(v);
            }
        });

    }

    public void showDatePickerDialog1(View v) {
        DialogFragment newFragment = new DatePickerFragment1();
        newFragment.show(getSupportFragmentManager(), "datePicker");

    }

    public static class DatePickerFragment1 extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @RequiresApi(api = Build.VERSION_CODES.N)
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            fromdatetext.setText(year + "-" + (month + 1) + "-" + day);
        }

    }

    public void showDatePickerDialog2(View v) {
        DialogFragment newFragment = new DatePickerFragment2();
        newFragment.show(getSupportFragmentManager(), "datePicker");

    }

    public static class DatePickerFragment2 extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        @RequiresApi(api = Build.VERSION_CODES.N)
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            todatetext.setText(year + "-" + (month + 1) + "-" + day);
        }
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
            HttpPost httpPost = new HttpPost("http://192.168.43.156:8181/os/plant.php");
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
            s = new String[size];

            // String[] password = new String[size];
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                s[i] = obj.getString("plant_name");
                // password[i] = obj.getString("password");
            }

            ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, s);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            plantnamespinner.setAdapter(arrayAdapter);
            new ExecuteeTask().execute(industry, analyzer);
            new ExecuteeeTask().execute(industry, analyzer);


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

    class ExecuteeTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            String res = PostDataa(params);
            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            fetchh(result);
        }
    }

    public String PostDataa(String[] valuse) {
        String s = "";
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://192.168.43.156:8181/os/station_res.php");
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("plt_name", valuse[0]));
            list.add(new BasicNameValuePair("analyzer", valuse[1]));
            httpPost.setEntity(new UrlEncodedFormEntity(list));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            s = readdata(httpResponse);
        } catch (Exception exception) {
        }
        return s;
    }

    private void fetchh(String response) {
        try {
            JSONArray array = new JSONArray(response);
            size = array.length();
            String[] s1 = new String[size];

            // String[] password = new String[size];
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                s1[i] = obj.getString("short_name");
                System.out.println("Data:" + s1[i]);

                // password[i] = obj.getString("password");
            }
            linearlayout.removeAllViews();
            for (int i = 0; i < s1.length; i++) {
                checkBox = new CheckBox(this);
                checkBox.setId(i);
                checkBox.setText(s1[i]);
                linearlayout.addView(checkBox);
            }

        } catch (JSONException e) {
        }
    }

    class ExecuteeeTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            String res = PostDataaa(params);
            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            fetchhh(result);
        }
    }

    public String PostDataaa(String[] valuse) {
        String s = "";
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost("http://192.168.43.156:8181/os/pollutant.php");
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            list.add(new BasicNameValuePair("plt_name", valuse[0]));
            list.add(new BasicNameValuePair("analyzer", valuse[1]));
            httpPost.setEntity(new UrlEncodedFormEntity(list));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            s = readdata(httpResponse);
        } catch (Exception exception) {
        }
        return s;
    }

    private void fetchhh(String response) {
        try {
            JSONArray array = new JSONArray(response);
            size2 = array.length();
            String[] s2 = new String[size2];

            // String[] password = new String[size];
            for (int i = 0; i < size2; i++) {
                JSONObject obj = array.getJSONObject(i);
                s2[i] = obj.getString("parameter_code");
                System.out.println("Data:" + s2[i]);
                // password[i] = obj.getString("password");
            }
            innerLayout.removeAllViews();
            int j = size;
            for (int i = 0; i < size2; i++) {
                checkBox2 = new CheckBox(this);
                checkBox2.setId(j);
                System.out.print(i);
                System.out.println("=" + s2[i]);
                checkBox2.setText(s2[i]);
                j++;

                innerLayout.addView(checkBox2);
            }
        } catch (JSONException e) {
        }
    }

    public void onlineview(View view) {

        for (int i = 0; i < innerLayout.getChildCount(); i++) {
            View nextChild = innerLayout.getChildAt(i);
            if (nextChild instanceof CheckBox) {
                CheckBox check = (CheckBox) nextChild;
                if (check.isChecked()) {
                    cPollutions.add(check.getText().toString());
                }
            }
        }
        for (int i = 0; i < linearlayout.getChildCount(); i++) {
            View nextChild = linearlayout.getChildAt(i);
            if (nextChild instanceof CheckBox) {
                CheckBox check = (CheckBox) nextChild;
                if (check.isChecked()) {
                    cStations.add(check.getText().toString());
                }
            }
        }
        //System.out.println(cPollutions);
        Intent intent = new Intent(Adhoc.this, OnlineView.class);
        intent.putExtra("Pollutions", cPollutions);
        intent.putExtra("Stations", cStations);
        fromdaytext = fromdatetext.getText().toString();
        todaytext = todatetext.getText().toString();
        int selectedId = radioGroup.getCheckedRadioButtonId();
        time = findViewById(selectedId);
        String tFormat = time.getText().toString();
        try {
            selectedAnalyzer = analyzerspinner.getSelectedItem().toString();
        } catch (Exception e) {
            selectedAnalyzer = null;
        }

        try {
            selectedPlantname = plantnamespinner.getSelectedItem().toString();
        } catch (Exception e) {
            selectedPlantname = null;
        }

        intent.putExtra("PlantName", selectedPlantname);
        intent.putExtra("plantAnalyzer", selectedAnalyzer);
        intent.putExtra("Time", tFormat);
        //System.out.println("Date is:"+fdateText);
        intent.putExtra("FromDate", fromdaytext);
        intent.putExtra("ToDate", todaytext);
        startActivity(intent);
    }

    public void Download(View view) {
        String query;
        if (file == ".xls") {


            fromdaytext = fromdatetext.getText().toString();
            todaytext = todatetext.getText().toString();
            System.out.println("from:" + fromdaytext + " " + todaytext);
            String query2;
            BuildQueryxls ob = new BuildQueryxls();
            query2 = ob.getquery(fromdaytext, todaytext);
            String filename;
            filename = fromdaytext + "_" + selectedAnalyzer + "_" + selectedPlantname + ".xls";
            String url, ip;
            System.out.println("Query:"+query2);
            url = "http://192.168.43.156:8181/os/xls/export-book.php?filename="+filename+"&query="+query2+"&fromdate="+fromdaytext+"&todate"+todaytext+"&station_id="+selectedAnalyzer+"&plantname="+selectedPlantname;
            new DownloadTask(Adhoc.this, url, filename);
        } else {
            fromdaytext = fromdatetext.getText().toString();
            todaytext = todatetext.getText().toString();
            System.out.println("from:" + fromdaytext + " " + todaytext);
            String query2;
            BuildQueryxls ob = new BuildQueryxls();
            query2 = ob.getquery(fromdaytext, todaytext);
            String filename;
            filename = fromdaytext + "_" + selectedAnalyzer + "_" + selectedPlantname + ".pdf";
            String url, ip;

            url = "http://192.168.43.156:8181/os/pdf/export-book.php?filename="+filename+"&query="+query2+"&fromdate="+fromdaytext+"&todate"+todaytext+"&station_id="+selectedAnalyzer+"&plantname="+selectedPlantname;
            new DownloadTask(Adhoc.this, url, filename);
        }
    }
}