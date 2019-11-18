package com.example.transport.etc;


import android.app.AlertDialog;
import android.content.ContentValues;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.*;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.transport.R;
import com.example.transport.enviroment.MyDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.transport.enviroment.enviroment.database;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment implements View.OnClickListener {


    private View view;
    private int car_number;
    private int car_money = 0;
    private Button btn1;
    private Button btn2;
    private Button btn3;
    private Button ok;
    private EditText money;
    private Calendar now=Calendar.getInstance();

    public BlankFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_blank, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if (database == null) {
            database = new MyDatabase(getContext(), "MyDatabase.db", null, 1);
        }
        initView();
        Spinner spinner = view.findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        car_number = 1;
                        break;
                    case 1:
                        car_number = 2;
                        break;
                    case 2:
                        car_number = 3;
                        break;
                    case 3:
                        car_number = 4;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        super.onActivityCreated(savedInstanceState);
    }

    private void initView() {
        btn1 = view.findViewById(R.id.btn_1);
        btn2 = view.findViewById(R.id.btn_2);
        btn3 = view.findViewById(R.id.btn_3);
        ok = view.findViewById(R.id.ok);
        money = view.findViewById(R.id.money);
        ok.setOnClickListener(this);
        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                car_money = 10;
                btn1.setBackgroundColor(Color.parseColor("#6666FF"));
                btn2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                btn3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                break;
            case R.id.btn_2:
                btn1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                btn2.setBackgroundColor(Color.parseColor("#6666FF"));
                btn3.setBackgroundColor(Color.parseColor("#FFFFFF"));
                car_money = 100;
                break;
            case R.id.btn_3:
                btn1.setBackgroundColor(Color.parseColor("#FFFFFF"));
                btn2.setBackgroundColor(Color.parseColor("#FFFFFF"));
                btn3.setBackgroundColor(Color.parseColor("#6666FF"));
                car_money = 200;
                break;
            case R.id.ok:
                if (money.getText().toString().equals("")) {
                    if (car_money == 0) Toast.makeText(getContext(), "请填写完整信息！", Toast.LENGTH_SHORT).show();
                    else {
                        ContentValues values = new ContentValues();
                        values.put("car_number", car_number);
                        values.put("car_money", car_money);
                        values.put("car_time",now.get(Calendar.YEAR)+"年"+ (Integer.valueOf(now.get(Calendar.MONTH))+1)+"月"+ now.get(Calendar.DAY_OF_MONTH)+"日");
                        database.getWritableDatabase().insert("etc", null, values);
                        database.close();
                        showAlertDialog();
                    }
                    ;

                } else {
                    car_money = Integer.valueOf(String.valueOf(money.getText()));
                    ContentValues values = new ContentValues();
                    values.put("car_number", car_number);
                    values.put("car_money", car_money);
                    values.put("car_time", now.get(Calendar.YEAR)+"年"+ (Integer.valueOf(now.get(Calendar.MONTH))+1)+"月"+ now.get(Calendar.DAY_OF_MONTH)+"日");
                    database.getWritableDatabase().insert("etc", null, values);
                    showAlertDialog();
                    database.close();
                }
                break;
        }
    }

    private void showAlertDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(getContext()).
                setTitle("提示：").setMessage("充值成功")
                .setPositiveButton("确定", null)
                .create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }

}
