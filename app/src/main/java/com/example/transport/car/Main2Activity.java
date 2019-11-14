package com.example.transport.car;

import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.cardemulation.HostApduService;
import android.os.AsyncTask;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.transport.R;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import static com.example.transport.setting.Main4Activity.IP;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    private TextView title;
    private Button back;
    private String carnumber;
    //存储违章时间与地点
    List<Map<String, Object>> data = new ArrayList<>();

    //存储违章详情
    List<Map<String, Object>> details = new ArrayList<>();
    List<List<Map<String, Object>>> all = new ArrayList<>();
    private TextView pdatetime;
    private TextView paddr;
    private TextView info;
    private TextView detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        initView();
        change();
    }

    private void change() {
        Intent intent = getIntent();
        carnumber = intent.getStringExtra("carnumber");
        new GetDatatask().execute();
    }

    class GetDatatask extends AsyncTask<Void, Void, List<List<Map<String, Object>>>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress();

        }

        @Override
        protected List<List<Map<String, Object>>> doInBackground(Void... voids) {

            try {
                JSONObject object = new JSONObject(post("http://"+IP+":8088/transportservice/action/GetCarPeccancy.do", "{\"UserName\":\"user1\",\"carnumber\":\"" + carnumber + "\"}"));
                JSONArray array = object.getJSONArray("ROWS_DETAIL");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object1 = array.getJSONObject(i);
                    Map<String, Object> map = new HashMap<>();
                    map.put("pcode", object1.getString("pcode"));
                    map.put("pdatetime", object1.getString("pdatetime"));
                    map.put("paddr", object1.getString("paddr"));
                    data.add(map);
                }
                Log.d("data", "data" + data.size());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                JSONObject object = new JSONObject(post("http://"+IP+":8088/transportservice/action/GetPeccancyType.do", "{\"UserName\":\"user1\"}"));
                JSONArray array = object.getJSONArray("ROWS_DETAIL");
                for (int i = 0; i < array.length(); i++) {
                    JSONObject object1 = array.getJSONObject(i);
                    for (int j = 0; j < data.size(); j++) {
                        if (object1.getString("pcode").equals(data.get(j).get("pcode"))) {
                            Map<String, Object> map = new HashMap<>();
                            map.put("premarks", object1.getString("premarks"));
                            map.put("pmoney", object1.getString("pmoney"));
                            map.put("pscore", object1.getString("pscore"));
                            map.put("pcode", object1.getString("pcode"));
                            details.add(map);
                        }
                    }

                }
                Log.d("details", "details: " + details.size());
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            all.add(data);
            all.add(details);
            return all;
        }

        @Override
        protected void onPostExecute(List<List<Map<String, Object>>> lists) {
            super.onPostExecute(lists);
            dismissProgress();

            all = lists;
            Log.d("all", "onPostExecute: " + lists.toString());
            LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
            manager.setOrientation(RecyclerView.HORIZONTAL);
            recyclerView.setLayoutManager(manager);
            MyAdapter adapter = new MyAdapter();
            recyclerView.setAdapter(adapter);
        }
    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerView);
        title = findViewById(R.id.title_text);
        back = findViewById(R.id.back_button);
        title.setText("查询结果");
        back.setOnClickListener(this);

        pdatetime = findViewById(R.id.pdatetime);
        paddr = findViewById(R.id.paddr);
        info = findViewById(R.id.info);
        detail = findViewById(R.id.detail);
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    private void dismissProgress() {
        if (progressDialog != null) progressDialog.dismiss();
    }

    private void showProgress() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(Main2Activity.this);
            progressDialog.setTitle("正在获取数据");
            progressDialog.setMessage("请稍等...");
            progressDialog.show();
        } else progressDialog.show();
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

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.car_item,parent,false);
            ViewHolder viewHolder = new ViewHolder(view);

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.pdatetime.setText(String.valueOf(all.get(0).get(position).get("pdatetime")));
            holder.paddr.setText(String.valueOf(all.get(0).get(position).get("paddr")));

            holder.detail.setText(String.valueOf(all.get(1).get(position).get("premarks")));
            holder.info.setText("违章：" + String.valueOf(all.get(1).get(position).get("pcode")) + "\t\t罚款：" + String.valueOf(all.get(1).get(position).get("pmoney")) + "\t\t扣分：" + all.get(1).get(position).get("pscore"));
        }

        @Override
        public int getItemCount() {
            return all.get(0).size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            TextView pdatetime;
            TextView detail;
            TextView paddr;
            TextView info;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                this.pdatetime = itemView.findViewById(R.id.pdatetime);
                this.paddr = itemView.findViewById(R.id.paddr);
                this.info = itemView.findViewById(R.id.info);
                this.detail = itemView.findViewById(R.id.detail);
            }


        }
    }
}
