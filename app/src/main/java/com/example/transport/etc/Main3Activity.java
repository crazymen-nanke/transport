package com.example.transport.etc;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import com.example.transport.R;

public class Main3Activity extends AppCompatActivity implements View.OnClickListener {

    private Button back;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        initView();
    }

    private void initView() {
        back = findViewById(R.id.back_button);
        title = findViewById(R.id.title_text);
        title.setText("ETC管理");
        back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
