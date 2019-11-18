package com.example.transport.etc;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.transport.R;
import com.example.transport.enviroment.MyDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.transport.enviroment.enviroment.database;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment2 extends Fragment {


    private View view;
    private List<String> car_number = new ArrayList<>();
    private List<String> car_money = new ArrayList<>();
    private List<String> car_time = new ArrayList<>();
    private ListView listView;
    private int allMoney = 0;
    private TextView total;

    public BlankFragment2() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_blank_fragment2, container, false);
        listView = view.findViewById(R.id.listView);
        total = view.findViewById(R.id.total);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (database == null) {
            database = new MyDatabase(getContext(), "MyDatabase.db", null, 1);
        }
        Cursor etc = database.getWritableDatabase().query("etc", null, null, null, null, null, null);
        while (etc.moveToNext()) {
            car_number.add(String.valueOf(etc.getString(etc.getColumnIndex("car_number"))));
            car_money.add(String.valueOf(etc.getString(etc.getColumnIndex("car_money"))));
            car_time.add(String.valueOf(etc.getString(etc.getColumnIndex("car_time"))));

        }
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < car_money.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("car_number", car_number.get(i));
            map.put("car_money", car_money.get(i));
            map.put("car_time", car_time.get(i));
            allMoney += Integer.valueOf(car_money.get(i));
            list.add(map);
        }
        SimpleAdapter adapter = new SimpleAdapter(getContext(), list, R.layout.tab_item, new String[]{"car_number", "car_money", "car_time"}, new int[]{R.id.car_number, R.id.car_money, R.id.car_time});
        listView.setAdapter(adapter);
        total.setText("充值总金额："+allMoney+"元");

    }

}
