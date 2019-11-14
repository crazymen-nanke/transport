package com.example.transport.car;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
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

public class car extends AppCompatActivity implements View.OnClickListener {

    private EditText query;
    private Button btn;
    private String carnumber;
    private ProgressDialog progressDialog;
    public List<Map<String, Object>> data = new ArrayList<>();
    private Button back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);

        initView();


    }

    private void initView() {
        query = findViewById(R.id.ed_query);
        btn = findViewById(R.id.btn);
        btn.setOnClickListener(this);
        back = findViewById(R.id.back_button);
        back.setOnClickListener(this);
        TextView title = findViewById(R.id.title_text);
        title.setText("车辆违章");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_button:
                finish();
                break;
            case R.id.btn:
                if (String.valueOf(query.getText()).equals("")) {
                    Toast.makeText(this, "请输入要查询的车牌号码！", Toast.LENGTH_SHORT).show();
                } else {
                    carnumber = String.valueOf(query.getText());
                    Log.d("carnumber", "onClick: " + carnumber);
                    new MyDataTask().execute(carnumber);
                }
                break;
        }
    }

    class MyDataTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgress();
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            try {

                JSONObject object = new JSONObject(post("http://"+IP+":8088/transportservice/action/GetAllCarPeccancy.do", "{\"UserName\":\"user1\"}"));
                JSONArray array = new JSONArray(object.getString("ROWS_DETAIL"));
                for (int i = 0; i < array.length(); i++) {
                    Map<String, Object> map = new HashMap<>();
                    JSONObject object1 = array.getJSONObject(i);
                    map.put("carnumber", object1.getString("carnumber"));
                    map.put("pcode", object1.getString("pcode"));
                    map.put("pdatetime", object1.getString("pdatetime"));
                    map.put("paddr", object1.getString("paddr"));
                    data.add(map);
                }
                Log.d("CAR_data", "doInBackground: " + data.toString());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).get("carnumber").equals("鲁" + strings[0])) {
                    return true;
                }
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            dismissProgress();
            if (aBoolean) {
                Intent intent = new Intent(car.this, Main2Activity.class);
                intent.putExtra("carnumber", "鲁" + carnumber);
                startActivity(intent);
            } else Toast.makeText(getApplicationContext(), "没有查询到鲁" + carnumber + "车的违章数据！", Toast.LENGTH_SHORT).show();
        }
    }

    private void dismissProgress() {
        if (progressDialog != null) progressDialog.dismiss();
    }

    private void showProgress() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(car.this);
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

}
