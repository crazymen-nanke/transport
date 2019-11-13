package com.example.transport.help;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.transport.R;

public class Main8Activity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main8);

        TextView title = findViewById(R.id.title_text);
        title.setText("支付界面");

        Button button = findViewById(R.id.back_button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
