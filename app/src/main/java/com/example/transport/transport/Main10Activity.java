package com.example.transport.transport;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.transport.R;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.transport.setting.Main4Activity.IP;

public class Main10Activity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private TextView number;
    private TextView site;
    private List<String> busData;
    private List<Map<String, Object>> otherData;
    private TextView name;
    private TextView time;
    private TextView info;
    private TextView price;
    private List<Map<String, Object>> data;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main10);
        initView();


        new MyAsync().execute();


    }

    private void initView() {
        name = findViewById(R.id.name);
        time = findViewById(R.id.time);
        info = findViewById(R.id.info);
        price = findViewById(R.id.price);
        title = findViewById(R.id.title_text);
        Button back = findViewById(R.id.back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    class MyAsync extends AsyncTask<Void, Void, List<Map<String, Object>>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            show();
        }

        @Override
        protected List<Map<String, Object>> doInBackground(Void... voids) {

            //存储公交车线路的名字，最短路程，票价等所有信息
            data = new ArrayList<>();

            try {
                JSONObject object = new JSONObject(post("http://"+IP+":8088/transportservice/action/GetBusInfo.do", "{\"Line\":1,\"UserName\":\"user1\"}"));
                JSONArray array = new JSONArray(object.getString("ROWS_DETAIL"));
                for (int i = 0; i < array.length(); i++) {
                    JSONObject jsonObject = array.getJSONObject(i);
                    Map<String, Object> busData = new HashMap<>();
                    busData.put("name", jsonObject.getString("name"));
                    busData.put("mileage", jsonObject.getString("mileage"));
                    busData.put("ticket", jsonObject.getString("ticket"));
                    JSONArray sites = jsonObject.getJSONArray("sites");
                    JSONArray time = jsonObject.getJSONArray("time");
                    for (int j = 0; j < sites.length(); j++) {
                        Log.d("sitesObject", "doInBackground: " + sites.get(j));
                        busData.put("site" + j, sites.get(j));
                    }
                    for (int k = 0; k < time.length(); k++) {
                        busData.put("time" + k, time.get(k));
                    }
                    data.add(busData);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(List<Map<String, Object>> stringObjectMap) {
            super.onPostExecute(stringObjectMap);
            Intent intent = getIntent();
            String id = intent.getStringExtra("Id");
            int id1 = Integer.valueOf(id);
            Log.d("data", "onPostExecute: " + stringObjectMap.get(id1 - 1).get("site0"));
            dismiss();

            //需要查询公交车的所有其他信息
            otherData = new ArrayList<>();

            System.out.println(stringObjectMap.get(id1 - 1).get("time1"));

            for (int j = 0; j < 2; j++) {
                try {
                    JSONObject object = new JSONObject(String.valueOf(stringObjectMap.get(id1 - 1).get("time" + j)));
                    Map<String, Object> map = new HashMap<>();
                    map.put("site", object.getString("site"));
                    map.put("starttime", object.getString("starttime"));
                    map.put("endtime", object.getString("endtime"));
                    otherData.add(map);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            System.out.println(otherData.toString());

            //需要查询公交车的所有站点信息
            busData = new ArrayList<>();
            for (int i = 0; i < stringObjectMap.get(id1 - 1).size(); i++)
                busData.add((String) stringObjectMap.get(id1 - 1).get("site" + i));

            Log.d("busData", "onPostExecute: " + busData.toString());
            //设置RecyclerView的布局方式
            RecyclerView recyclerView = findViewById(R.id.recyclerView);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
            recyclerView.setLayoutManager(linearLayoutManager);
            MyAdapter adapter = new MyAdapter();
            recyclerView.setAdapter(adapter);
            name.setText(otherData.get(0).get("site") + "-" + otherData.get(1).get("site"));
            time.setText("" + otherData.get(0).get("starttime") + "-" + otherData.get(1).get("endtime"));
            price.setText("\t\t\t\t" + "票价：最高票价" + data.get(id1 - 1).get("ticket") + "元");
            info.setText("\t\t\t\t" + new MyAdapter().getItemCount() + "站/" + data.get(id1 - 1).get("mileage") + "公里");
            title.setText(id1+"路");
            Intent intent1 = new Intent();
            intent.putExtra("item",otherData.get(0).get("site") + "-" + otherData.get(1).get("site"));
            setResult(RESULT_OK,intent);
        }
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(Main10Activity.this, R.layout.question72item, null);
            number = view.findViewById(R.id.number);
            site = view.findViewById(R.id.site);

            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

            Log.d("busData", "onBindViewHolder: " + busData.toString());
            if (!busData.get(position).equals("")) {
                holder.site.setText(busData.get(position));
                holder.number.setText("" + (position + 1));
            }
        }


        @Override
        public int getItemCount() {
            return busData.size() - 5;
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            private final TextView number;
            private final TextView site;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                number = itemView.findViewById(R.id.number);
                site = itemView.findViewById(R.id.site);
            }
        }
    }


    public void show() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(Main10Activity.this);
            progressDialog.setTitle("请稍等...");
            progressDialog.setMessage("正在加载中...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        } else
            progressDialog.show();
    }

    public void dismiss() {
        if (progressDialog != null) progressDialog.dismiss();
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
}
