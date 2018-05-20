package com.example.aviro.ospc;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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


public class MainActivity extends AppCompatActivity {
    EditText userid,password;
    String ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userid=(EditText)findViewById(R.id.editTextusername);
        password=(EditText)findViewById(R.id.editTextpassword);
        ip=getResources().getString(R.string.IP);
    }
    public void login_btnclick(View v)
    {
        //Intent in=new Intent(getApplication(),Main2Activity.class);
        //startActivity(in);
        String uid=userid.getText().toString();
        String pass=password.getText().toString();
        new ExecuteTask().execute(uid,pass);
    }

    class ExecuteTask extends AsyncTask<String,Integer,String>
    {

        @Override
        protected String doInBackground(String... params) {
            String res=PostData(params);
            return res;
        }

        @Override
        protected void onPostExecute(String result)
        {
            Toast.makeText(getApplication(),result, Toast.LENGTH_LONG).show();
            if(result.trim().equals("success"))
            {
                Intent in=new Intent(getApplication(),Main2Activity.class);
                startActivity(in);
            }
        }
        public String PostData(String[] values)
        {
            String s=" ";
            try {
                HttpClient httpClient=new DefaultHttpClient();
                HttpPost httpPost=new HttpPost(ip+"login.php");
                List<NameValuePair> list=new ArrayList<NameValuePair>();
                list.add(new BasicNameValuePair("user_id",values[0]));
                list.add(new BasicNameValuePair("password",values[1]));
                //int i= list.hashCode();
                httpPost.setEntity(new UrlEncodedFormEntity(list));
                HttpResponse httpResponse=httpClient.execute(httpPost);
                HttpEntity httpEntity=httpResponse.getEntity();
                s=readResponse(httpResponse);
            }
            catch (Exception e)
            {

            }
            return s;
        }
        public String readResponse(HttpResponse res)
        {
            InputStream is=null;
            String return_text=" ";
            try
            {
                is=res.getEntity().getContent();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is));
                String line=" ";
                StringBuffer sb=new StringBuffer();
                while ((line=bufferedReader.readLine())!=null)
                {
                    sb.append(line);
                }
                return_text=sb.toString();

            }
            catch (Exception e)
            {

            }
            return return_text;
        }
    }


}


