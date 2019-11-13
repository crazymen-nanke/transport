package com.example.transport.transport;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.transport.R;

import java.util.ArrayList;
import java.util.List;

public class Main9Activity extends AppCompatActivity implements View.OnClickListener {

    private EditText inf;
    private Button search;
    private Button clear;
    private ListView listView;
    List<String> item = new ArrayList<>();
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main9);
        initView();


    }

    private void initView() {
        inf = findViewById(R.id.inf);
        search = findViewById(R.id.search);
        clear = findViewById(R.id.clear);
        listView = findViewById(R.id.listView);
        search.setOnClickListener(this);
        clear.setOnClickListener(this);
        TextView title = findViewById(R.id.title_text);
        title.setText("实时交通");
        Button back = findViewById(R.id.back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search: {
                if (String.valueOf(inf.getText()).equals("")) {
                    Toast.makeText(getApplicationContext(), "请输入要查询的信息！", Toast.LENGTH_SHORT).show();
                } else {
                    if (String.valueOf(inf.getText()).equals("1") || String.valueOf(inf.getText()).equals("2") || String.valueOf(inf.getText()).equals("3") || String.valueOf(inf.getText()).equals("4") || String.valueOf(inf.getText()).equals("5") || String.valueOf(inf.getText()).equals("6") || String.valueOf(inf.getText()).equals("7") || String.valueOf(inf.getText()).equals("8") || String.valueOf(inf.getText()).equals("9") || String.valueOf(inf.getText()).equals("10")) {
                        Intent intent = new Intent(Main9Activity.this, Main10Activity.class);
                        intent.putExtra("Id", String.valueOf(inf.getText()));
                        startActivityForResult(intent, 1);
                    } else Toast.makeText(getApplicationContext(), "请输入需要查询的公交车号码！", Toast.LENGTH_SHORT).show();

                }
            }
            break;
            case R.id.clear: {

                if (item.size() > 0) {
                    item.clear();
                    myAdapter.notifyDataSetChanged();
                    listView.setAdapter(myAdapter);
                } else {
                    Toast.makeText(this, "无历史记录！", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            default:
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1: {
                if (resultCode == RESULT_OK) {
                    item.add(inf.getText() + "路" + "(" + data.getStringExtra("item") + ")");
                    myAdapter = new MyAdapter();
                    listView.setAdapter(myAdapter);
                }
            }
        }
    }

    class MyAdapter extends BaseAdapter {

        private View view;
        private ViewHolder viewHolder;

        @Override
        public int getCount() {
            return item.size();
        }

        @Override
        public Object getItem(int position) {
            return item.size();
        }

        @Override
        public long getItemId(int position) {
            return item.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                view = View.inflate(getApplicationContext(), R.layout.question7item, null);
                viewHolder = new ViewHolder();
                viewHolder.info = view.findViewById(R.id.history);
                view.setTag(viewHolder);
            } else {
                view = convertView;
                viewHolder = (ViewHolder) view.getTag();
            }
            viewHolder.info.setText(item.get(position));
            return view;
        }
    }

    class ViewHolder {
        TextView info;
    }

}

