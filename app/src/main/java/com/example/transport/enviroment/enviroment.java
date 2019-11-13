package com.example.transport.enviroment;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.transport.R;
import okhttp3.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class enviroment extends AppCompatActivity {


    private SQLiteDatabase writableDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enviroment);


        TextView title = findViewById(R.id.title_text);
        title.setText("环境指标");

        Button back = findViewById(R.id.back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        MyDatabase database = new MyDatabase(this, "MyDatabase.db", null, 1);
        new MyTime(60000, 3000).start();
        writableDatabase = database.getWritableDatabase();

    }


    class MyData extends AsyncTask<Void, Void, Map> {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Map doInBackground(Void... voids) {
            Map<String, String> map = new HashMap<>();
            postData(map);
            postData1(map);
            return map;
        }

        @Override
        protected void onPostExecute(Map map) {

            ContentValues contentValues = new ContentValues();
            contentValues.put("temperature", (String) map.get("temperature"));
            contentValues.put("humidity", (String) map.get("humidity"));
            contentValues.put("LightIntensity", (String) map.get("LightIntensity"));
            contentValues.put("co2", (String) map.get("co2"));
            contentValues.put("pm", (String) map.get("pm2.5"));
            contentValues.put("Status", (String) map.get("Status"));

            writableDatabase.insert("data", null, contentValues);


            super.onPostExecute(map);

            TextView temperature = findViewById(R.id.data_temperature);
            temperature.setText(map.get("temperature") + "");
            int data1 = Integer.valueOf((String) map.get("temperature"));
            if (data1 > 32) {
                RelativeLayout layout1 = findViewById(R.id.layout1);
                layout1.setBackgroundColor(Color.parseColor("#c32136"));
            } else {
                RelativeLayout layout1 = findViewById(R.id.layout1);
                layout1.setBackgroundColor(Color.parseColor("#00bc12"));
            }

            TextView humidity = findViewById(R.id.data_humidity);
            humidity.setText(map.get("humidity") + "");
            int data2 = Integer.valueOf((String) map.get("humidity"));
            if (data2 > 80) {
                RelativeLayout layout2 = findViewById(R.id.layout2);
                layout2.setBackgroundColor(Color.parseColor("#c32136"));
            } else {
                RelativeLayout layout2 = findViewById(R.id.layout2);
                layout2.setBackgroundColor(Color.parseColor("#00bc12"));
            }

            TextView LightIntensity = findViewById(R.id.data_LightIntensity);
            LightIntensity.setText(map.get("LightIntensity") + "");
            int data3 = Integer.valueOf((String) map.get("LightIntensity"));
            if (data3 > 567) {
                RelativeLayout layout3 = findViewById(R.id.layout3);
                layout3.setBackgroundColor(Color.parseColor("#c32136"));
            } else {
                RelativeLayout layout3 = findViewById(R.id.layout3);
                layout3.setBackgroundColor(Color.parseColor("#00bc12"));
            }

            TextView co2 = findViewById(R.id.data_co);
            co2.setText(map.get("co2") + "");
            int data4 = Integer.valueOf((String) map.get("co2"));
            if (data4 > 324) {
                RelativeLayout layout4 = findViewById(R.id.layout4);
                layout4.setBackgroundColor(Color.parseColor("#c32136"));
            } else {
                RelativeLayout layout4 = findViewById(R.id.layout4);
                layout4.setBackgroundColor(Color.parseColor("#00bc12"));
            }

            TextView PM = findViewById(R.id.data_PM2_5);
            PM.setText(map.get("pm2.5") + "");
            int data5 = Integer.valueOf((String) map.get("pm2.5"));
            if (data5 > 254) {
                RelativeLayout layout5 = findViewById(R.id.layout5);
                layout5.setBackgroundColor(Color.parseColor("#c32136"));
            } else {
                RelativeLayout layout5 = findViewById(R.id.layout5);
                layout5.setBackgroundColor(Color.parseColor("#00bc12"));
            }

            TextView Status = findViewById(R.id.data_state);
            Status.setText(map.get("Status") + "");
            int data6 = Integer.valueOf((String) map.get("Status"));
            if (data6 > 4) {
                RelativeLayout layout6 = findViewById(R.id.layout6);
                layout6.setBackgroundColor(Color.parseColor("#c32136"));
            } else {
                RelativeLayout layout6 = findViewById(R.id.layout6);
                layout6.setBackgroundColor(Color.parseColor("#00bc12"));
            }


        }
    }

    private void postData(Map<String, String> map) {


        try {
            OkHttpClient client = new OkHttpClient();

            RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), "{\"UserName\":\"user1\"}");
            Request request = new Request.Builder()
                    .url("http://192.168.1.101:8088/transportservice/action/GetAllSense.do")//请求的url
                    .post(requestBody)
                    .build();

            Response response = client.newCall(request).execute();

            JSONObject object = new JSONObject(response.body().string());
            Log.d("TAG", "postData: " + object.toString());
            map.put("pm2.5", object.getString("pm2.5"));
            map.put("co2", object.getString("co2"));
            map.put("LightIntensity", object.getString("LightIntensity"));
            map.put("humidity", object.getString("humidity"));
            map.put("temperature", object.getString("temperature"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void postData1(Map<String, String> map) {
        try {
            OkHttpClient client = new OkHttpClient();

            RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), "{\"RoadId\":1,\"UserName\":\"user1\"}");
            Request request = new Request.Builder()
                    .url("http://192.168.1.101:8088/transportservice/action/GetRoadStatus.do")//请求的url
                    .post(requestBody)
                    .build();

            Response response = client.newCall(request).execute();

            JSONObject object = new JSONObject(response.body().string());
            Log.d("TAG", "postData1: " + object.toString());
            map.put("Status", object.getString("Status"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    class MyTime extends CountDownTimer {

        public MyTime(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            new MyData().execute();
        }

        @Override
        public void onFinish() {
            cancel();
        }
    }
}
