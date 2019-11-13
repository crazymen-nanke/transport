package com.example.transport.bus;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTabHost;
import com.example.transport.R;

public class FragmentTabHostActivity extends AppCompatActivity {
    FragmentTabHost fragmentTabHost;
    //加载的Fragment
    private Class<?> mFragmentClasses[] = {Fragment1.class, Fragment2.class};
    //标签文字
    private String[] text = new String[]{"一号站台", "二号站台"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_tab_host);
        final TextView title = findViewById(R.id.title_text);
        title.setText("1号站台");
        fragmentTabHost = findViewById(android.R.id.tabhost);
        //初始化
        fragmentTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        fragmentTabHost.getTabWidget().setDividerDrawable(null); // 去掉分割线
        //添加标签
        for (int i = 0; i < text.length; i++) {
            // Tab按钮添加文字
            TabHost.TabSpec tabSpec = fragmentTabHost.newTabSpec(i + "").setIndicator(getIndicatorView(i));
            // 添加Fragment
            fragmentTabHost.addTab(tabSpec, mFragmentClasses[i], null);
        }
        fragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                //切换标签时调用
                title.setText((Integer.valueOf(tabId) + 1) + "号站台");

            }
        });

        Button back = findViewById(R.id.back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //获取标签的View
    private View getIndicatorView(int i) {
        View view = View.inflate(this,
                R.layout.layout_indicator_view, null);
        TextView tvTag = view.findViewById(R.id.tv_tab);
        tvTag.setText(text[i]);
        return view;
    }
}
