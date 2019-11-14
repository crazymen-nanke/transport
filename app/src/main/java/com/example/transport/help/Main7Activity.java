package com.example.transport.help;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import com.bumptech.glide.Glide;
import com.example.transport.R;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.example.transport.setting.Main4Activity.IP;

public class Main7Activity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main7);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);

        //实例化瀑布流布局管理器
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        //设置recyclerView的布局管理器
        recyclerView.setLayoutManager(layoutManager);

        MyAdapter adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);

        TextView title = findViewById(R.id.title_text);
        title.setText("旅行助手");

        Button button = findViewById(R.id.back_button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = View.inflate(getApplicationContext(), R.layout.question8item, null);
            ViewHolder viewHolder = new ViewHolder(view);
            viewHolder.buy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), Main8Activity.class);
                    startActivity(intent);
                }
            });

            return viewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

            new Thread(new Runnable() {
                Map<String, Object> data = new HashMap<>();

                @Override
                public void run() {
                    try {
                        JSONObject object = new JSONObject(post("http://"+IP+":8088/transportservice/action/GetSpotInfo.do", "{\"UserName\":\"user1\"}"));
                        JSONArray array = new JSONArray(object.getString("ROWS_DETAIL"));
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object1 = array.getJSONObject(i);
                            data.put("name", object1.getString("name"));
                            data.put("ticket", object1.getString("ticket"));
                            data.put("img", "http://"+IP+":8088/transportservice" + object1.getString("img"));
                        }

                        Log.d("TAG", "run: " + data.toString());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                holder.name.setText(data.get("name") + "");
                                holder.ticket.setText("票价￥" + data.get("ticket"));
                                Glide.with(getApplicationContext()).load(data.get("img")).into(holder.image);
                            }
                        });

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        @Override
        public int getItemCount() {
            return 6;
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            ImageView image;
            TextView name;
            TextView ticket;
            TextView buy;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                image = itemView.findViewById(R.id.image);
                name = itemView.findViewById(R.id.name);
                ticket = itemView.findViewById(R.id.ticket);
                buy = itemView.findViewById(R.id.buy);
            }
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

}
