package com.example.transport.etc;

import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.Space;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.transport.R;

public class Main3Activity extends AppCompatActivity implements View.OnClickListener {

    private Button back;
    private TextView title;
    private Button log;
    private Button speed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        initView();
    }

    private void initView() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.detail, new BlankFragment());
        transaction.commit();
        back = findViewById(R.id.back_button);
        title = findViewById(R.id.title_text);
        title.setText("ETC管理");
        log = findViewById(R.id.log);
        speed = findViewById(R.id.speed);
        back.setOnClickListener(this);
        log.setOnClickListener(this);
        speed.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_button:
                finish();
                break;
            case R.id.log:
                replaceFragment(new BlankFragment2());
                break;
            case R.id.speed:
                replaceFragment(new BlankFragment());
                break;

        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.detail, fragment);
        transaction.commit();
    }
}
