package com.example.transport.bus;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.transport.R;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.example.transport.setting.Main4Activity.IP;

public class Fragment1 extends Fragment {


    private TextView distance1;
    private TextView distance2;
    private TextView pm2_5;
    private TextView humidity;


    public Fragment1() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment1, container, false);
        distance1 = view.findViewById(R.id.tv1_Distance1);
        distance2 = view.findViewById(R.id.tv1_Distance2);
        pm2_5 = view.findViewById(R.id.tv1_PM2_5);
        humidity = view.findViewById(R.id.tv1_humidity);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        new MyTime(60000, 5000).start();
    }

    class MyTask extends AsyncTask<Void, Void, Map<String, Object>> {

        private Map<String, Object> data = new HashMap<>();

        @Override
        protected Map<String, Object> doInBackground(Void... voids) {
            try {
                JSONObject object = new JSONObject(post("http://"+IP+":8088/transportservice/action/GetBusStationInfo.do", "{\"BusStationId\":1,\"UserName\":\"user1\"}"));
                JSONArray array = new JSONArray(object.getString("ROWS_DETAIL"));
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object1 = array.getJSONObject(i);
                    data.put("Distance" + (i + 1), object1.getString("Distance"));
                }
                JSONObject object1 = new JSONObject(post("http://"+IP+":8088/transportservice/action/GetAllSense.do", "{\"UserName\":\"user1\"}"));
                data.put("pm2.5", object1.getString("pm2.5"));
                data.put("temperature", object1.getString("temperature"));
                data.put("humidity", object1.getString("humidity"));
                data.put("co2", object1.getString("co2"));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("data", "doInBackground: " + data.toString());
            return data;
        }

        @Override
        protected void onPostExecute(Map<String, Object> stringObjectMap) {
            super.onPostExecute(stringObjectMap);
            distance1.setText("一号公交车：" + stringObjectMap.get("Distance1") + "m");
            distance2.setText("二号公交车：" + stringObjectMap.get("Distance2") + "m");
            pm2_5.setText("PM2.5：" + stringObjectMap.get("pm2.5") + "μg/m³，温度：" + stringObjectMap.get("temperature") + "°C");
            humidity.setText("湿度：" + stringObjectMap.get("humidity") + "%，CO2：" + stringObjectMap.get("co2") + "PPM");
        }
    }


    //    使用okhttp发送一个post请求,接收一个地址参数和一个Json类型的参数
    public static String post(String address, String json) throws IOException {
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        Request request = new Request.Builder()
                .url(address)//请求的url
                .post(requestBody)
                .build();
        return client.newCall(request).execute().body().string();
    }

    class MyTime extends CountDownTimer {
        public MyTime(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            new MyTask().execute();
        }

        @Override
        public void onFinish() {
            cancel();
        }
    }
}
