package com.example.transport.setting;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.transport.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main4Activity extends AppCompatActivity implements View.OnClickListener {

    public static String IP;
    private EditText ed1;
    private EditText ed2;
    private EditText ed3;
    private EditText ed4;
    private String pattern = "(2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2}";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        initView();
    }

    private void initView() {
        TextView title = findViewById(R.id.title_text);
        title.setText("IP设置");

        Button button = findViewById(R.id.back_button);
        button.setOnClickListener(this);
        Button ok = findViewById(R.id.ok);
        ok.setOnClickListener(this);
        final TextInputLayout textinput1 = findViewById(R.id.textinput1);
        final TextInputLayout textinput2 = findViewById(R.id.textinput2);
        final TextInputLayout textinput3 = findViewById(R.id.textinput3);
        final TextInputLayout textinput4 = findViewById(R.id.textinput4);
        ed1 = findViewById(R.id.ed_1);
        ed2 = findViewById(R.id.ed_2);
        ed3 = findViewById(R.id.ed_3);
        ed4 = findViewById(R.id.ed_4);
        ed1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!Pattern.compile(pattern).matcher(String.valueOf(ed1.getText())).matches()||String.valueOf(ed1.getText()).equals("0")) {
                    textinput1.setError("IP格式错误！");
                    textinput1.setErrorEnabled(true);
                } else textinput1.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ed2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!Pattern.compile(pattern).matcher(String.valueOf(ed2.getText())).matches()) {
                    textinput2.setError("IP格式错误！");
                    textinput2.setErrorEnabled(true);
                } else textinput2.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ed3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!Pattern.compile(pattern).matcher(String.valueOf(ed3.getText())).matches()) {
                    textinput3.setError("IP格式错误！");
                    textinput3.setErrorEnabled(true);
                } else textinput3.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ed4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!Pattern.compile(pattern).matcher(String.valueOf(ed4.getText())).matches()) {
                    textinput4.setError("IP格式错误！");
                    textinput4.setErrorEnabled(true);
                } else textinput4.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_button:
                finish();
                break;
            case R.id.ok:
                IP = ed1.getText() + "." + ed2.getText() + "." + ed3.getText() + "." + ed4.getText();
                pattern = "((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})(\\.((2(5[0-5]|[0-4]\\d))|[0-1]?\\d{1,2})){3}";

                if (Pattern.compile(pattern).matcher(IP).matches()){
                    IP = ed1.getText() + "." + ed2.getText() + "." + ed3.getText() + "." + ed4.getText();
                    Toast.makeText(this,"IP设置成功！",Toast.LENGTH_SHORT).show();
                }else {
                    ed1.setText("");
                    ed2.setText("");
                    ed3.setText("");
                    ed4.setText("");
                    IP="";
                    Toast.makeText(this,"IP格式错误！请重新输入！",Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}
