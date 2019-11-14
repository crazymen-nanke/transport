package com.example.transport;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;
import com.example.transport.bus.FragmentTabHostActivity;
import com.example.transport.car.car;
import com.example.transport.enviroment.enviroment;
import com.example.transport.etc.Main3Activity;
import com.example.transport.help.Main7Activity;
import com.example.transport.setting.Main4Activity;
import com.example.transport.transport.Main9Activity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import android.view.View;
import com.google.android.material.navigation.NavigationView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        TextView textView=new TextView(this);
        textView.setText("主界面");
        textView.setTextSize(18);
        textView.setTextColor(Color.parseColor("#FFFFFF"));
        textView.setGravity(Gravity.CENTER);
        Toolbar.LayoutParams layoutParams=new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT,Toolbar.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity=Gravity.CENTER;
        textView.setLayoutParams(layoutParams);
        toolbar.addView(textView);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_ETC) {
            Intent nav_ETC = new Intent(MainActivity.this, Main3Activity.class);
            startActivity(nav_ETC);
        } else if (id == R.id.nav_CAR) {
            Intent nav_CAR = new Intent(MainActivity.this, car.class);
            startActivity(nav_CAR);
        } else if (id == R.id.nav_enviroment) {
            Intent nav_enviroment = new Intent(MainActivity.this, enviroment.class);
            startActivity(nav_enviroment);
        } else if (id == R.id.nav_analyse) {

        } else if (id == R.id.nav_bus) {
            Intent nav_bus = new Intent(MainActivity.this, FragmentTabHostActivity.class);
            startActivity(nav_bus);

        } else if (id == R.id.nav_transport) {

            Intent nav_transport = new Intent(MainActivity.this, Main9Activity.class);
            startActivity(nav_transport);
        } else if (id == R.id.nav_help) {
                Intent nav_help = new Intent(MainActivity.this, Main7Activity.class);
                startActivity(nav_help);

        } else if (id == R.id.setting) {
            Intent setting = new Intent(MainActivity.this, Main4Activity.class);
            startActivity(setting);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
