package com.example.aviro.ospc;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

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

public class Main2Activity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,OnMapReadyCallback {
    private GoogleMap mMap;
    Spinner state,catagory,industry,analyser,shortname;
    String[] st,ca,ind,sh;
    String s,c,ins,sn,ana,ip;
    Double lati,longi;
    Button industrycount,stationcount,usercount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.maps);
        mapFragment.getMapAsync(this);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        ip=getResources().getString(R.string.IP);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        state=(Spinner)findViewById(R.id.spin2);
        catagory=(Spinner)findViewById(R.id.spin3);
        industry=(Spinner)findViewById(R.id.spin4);
        analyser=(Spinner)findViewById(R.id.spin5);
        shortname=(Spinner)findViewById(R.id.spin6);
        industrycount=(Button)findViewById(R.id.industry_count);
        stationcount=(Button)findViewById(R.id.station_count);
        usercount=(Button)findViewById(R.id.user_count);
        new ExecuteTask().execute("fetchuser");
        new ExecutTask().execute("fetchuser");
        new ExecuTask().execute("fetchuser");
        new ExecTask().execute("fetchuser");
        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                s=state.getSelectedItem().toString();

                new ExecuteeTask().execute(s,"fetchuser");


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        catagory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                c=catagory.getSelectedItem().toString();
                new ExecuteeeTask().execute(s,c,"fetchuser");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        industry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ins=industry.getSelectedItem().toString();
                new ExecuteeeeTask().execute(ins,"fetchuser");
                new ExecuteeeeeTask().execute(ins,"fetchuser");


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        analyser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ana=analyser.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        shortname.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sn=shortname.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Dashboard) {

        } else if (id == R.id.rtd) {
            Intent in=new Intent(getApplication(),gra.class);
            startActivity(in);

        } else if (id == R.id.Adhoc) {
            Intent in=new Intent(getApplication(),Adhoc.class);
            startActivity(in);

        } else if (id == R.id.Data) {
            Intent in=new Intent(getApplication(),Das.class);
            startActivity(in);

        } else if (id == R.id.logout) {
            Intent in=new Intent(getApplication(),MainActivity.class);
            startActivity(in);

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
            HttpPost httpPost = new HttpPost(ip+"state.php");
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
            st = new String[size];
            //String[] password = new String[size];
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                st[i] = obj.getString("state");
                //  password[i] = obj.getString("password");
            }
            //CustomStudent adapter = new CustomStudent(MainActivity.this, userid, password);
            // list.setAdapter(adapter);
            ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,st);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            state.setAdapter(aa);

        } catch (JSONException e) {

        }
    }
    class ExecutTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            String res = PostDat(params);
            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            fetc(result);
        }
    }

    public String PostDat(String[] valuse) {
        String s = "";
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(ip+"industrycount.php");
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

    private void fetc(String response) {
        try {
            JSONArray array = new JSONArray(response);
            int size = array.length();
            String[] s= new String[size];
            //String[] password = new String[size];
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                s[i] = obj.getString("COUNT(plant_slug)");
                //  password[i] = obj.getString("password");
            }
            //CustomStudent adapter = new CustomStudent(MainActivity.this, userid, password);
            // list.setAdapter(adapter);
            industrycount.setText(s[0]);


        } catch (JSONException e) {

        }
    }
    class ExecuTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            String res = PostDa(params);
            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            fet(result);
        }
    }

    public String PostDa(String[] valuse) {
        String s = "";
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(ip+"stationcount.php");
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

    private void fet(String response) {
        try {
            JSONArray array = new JSONArray(response);
            int size = array.length();
            String[] s= new String[size];
            //String[] password = new String[size];
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                s[i] = obj.getString("COUNT(short_name)");
                //  password[i] = obj.getString("password");
            }
            //CustomStudent adapter = new CustomStudent(MainActivity.this, userid, password);
            // list.setAdapter(adapter);
            stationcount.setText(s[0]);


        } catch (JSONException e) {

        }
    }
    class ExecTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            String res = PostD(params);
            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            fe(result);
        }
    }

    public String PostD(String[] valuse) {
        String s = "";
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(ip+"usercount.php");
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

    private void fe(String response) {
        try {
            JSONArray array = new JSONArray(response);
            int size = array.length();
            String[] s= new String[size];

            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                s[i] = obj.getString("COUNT(user_id)");
            }
            usercount.setText(s[0]);


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
            HttpPost httpPost = new HttpPost(ip+"category.php");
            List<NameValuePair> list = new ArrayList<NameValuePair>();

            list.add(new BasicNameValuePair("state", valuse[0]));
            list.add(new BasicNameValuePair("tablename", valuse[1]));
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
            int size = array.length();
            ca = new String[size];
            //String[] password = new String[size];
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                ca[i] = obj.getString("category");
                //  password[i] = obj.getString("password");
            }
            ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,ca);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            catagory.setAdapter(aa);

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
            HttpPost httpPost = new HttpPost(ip+"plantname.php");
            List<NameValuePair> list = new ArrayList<NameValuePair>();

            list.add(new BasicNameValuePair("state", valuse[0]));
            list.add(new BasicNameValuePair("category", valuse[1]));
            list.add(new BasicNameValuePair("tablename", valuse[2]));
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
            int size = array.length();
            ind= new String[size];
            //String[] password = new String[size];
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                ind[i] = obj.getString("plant_name");
                //  password[i] = obj.getString("password");
            }
            ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,ind);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            industry.setAdapter(aa);

        } catch (JSONException e) {

        }
    }
    class ExecuteeeeTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            String res = PostDataaaa(params);
            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            fetchhhh(result);
        }
    }

    public String PostDataaaa(String[] valuse) {
        String s = "";
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(ip+"station.php");
            List<NameValuePair> list = new ArrayList<NameValuePair>();

            list.add(new BasicNameValuePair("station", valuse[0]));
            //list.add(new BasicNameValuePair("category", valuse[1]));
            list.add(new BasicNameValuePair("tablename", valuse[1]));
            httpPost.setEntity(new UrlEncodedFormEntity(list));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            s = readdata(httpResponse);
        } catch (Exception exception) {
        }
        return s;
    }

    private void fetchhhh(String response) {
        try {
            JSONArray array = new JSONArray(response);
            int size = array.length();
            sh= new String[size];
            //String[] password = new String[size];
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                sh[i] = obj.getString("short_name");
                //  password[i] = obj.getString("password");
            }
            ArrayAdapter aa = new ArrayAdapter(this,android.R.layout.simple_spinner_item,sh);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            shortname.setAdapter(aa);

        } catch (JSONException e) {

        }
    }
    class ExecuteeeeeTask extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... params) {
            String res = PostDataaaaa(params);
            return res;
        }

        @Override
        protected void onPostExecute(String result) {
            fetchhhhh(result);
        }
    }

    public String PostDataaaaa(String[] valuse) {
        String s = "";
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(ip+"latlong.php");
            List<NameValuePair> list = new ArrayList<NameValuePair>();

            list.add(new BasicNameValuePair("plant_name", valuse[0]));
            //list.add(new BasicNameValuePair("category", valuse[1]));
            list.add(new BasicNameValuePair("tablename", valuse[1]));
            httpPost.setEntity(new UrlEncodedFormEntity(list));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            s = readdata(httpResponse);
        } catch (Exception exception) {
        }
        return s;
    }

    private void fetchhhhh(String response) {
        try {
            JSONArray array = new JSONArray(response);
            int size = array.length();
            String[] ll = new String[size];
            //String[] password = new String[size];
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                ll[i] = obj.getString("lat_long");
                //  password[i] = obj.getString("password");
            }
           /* ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, sh);
            aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            shortname.setAdapter(aa);*/
            latilongi(ll[0]);
            addmarkerlocation(lati,longi);

        } catch (JSONException e) {

        }
    }

    private void addmarkerlocation(Double lati, Double longi) {
        mMap.clear();
        LatLng indus = new LatLng(lati,longi);
        mMap.addMarker(new MarkerOptions().position(indus).title(ins));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(indus));
        mMap.setMinZoomPreference(3);
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Intent intent=new Intent(getApplication(),Graph.class);
                intent.putExtra("plantname",ins);
                intent.putExtra("analyser",ana);
                intent.putExtra("stationid",sn);
                startActivity(intent);

                return false;
            }
        });



    }

    private void latilongi(String s) {
        String arr[]=s.split(",");
        lati=Double.parseDouble(arr[0]);
        longi=Double.parseDouble(arr[1]);


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        //  mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}
