package umte.fim.uhk.cz.ctapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.net.Socket;
import java.util.ArrayList;

import umte.fim.uhk.cz.ctapplication.fragments.CTFragment;
import umte.fim.uhk.cz.ctapplication.fragments.LightImpl;
import umte.fim.uhk.cz.ctapplication.fragments.MonitorFragment;
import umte.fim.uhk.cz.ctapplication.fragments.SettingFragment;
import umte.fim.uhk.cz.ctapplication.utils.MyMonitorLog;
import umte.fim.uhk.cz.ctapplication.utils.SocketData;
import umte.fim.uhk.cz.ctapplication.weather.WeatherFragment;

public class CTActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Fragment monitorFragment = new MonitorFragment(), weatherFragment, settingFragment, ctFragment;
    private FragmentManager fragmentManager;

    public static ArrayList<MyMonitorLog> monitorLogs;
    public static LightImpl lightImpl;

   private String IpAddress = "10.0.0.58";
   private SocketData socketData;
   private int port = 1024;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ct);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        port = intent.getIntExtra("port", 0);
        IpAddress = intent.getStringExtra("IP");


//        System.out.println("Port z MainActivity: "+ port);
//        System.out.println("IP z MainActivity: "+ IpAddress);

        monitorLogs = new ArrayList<>();

        fragmentManager = getSupportFragmentManager();
        ctFragment = new CTFragment();
        weatherFragment = new WeatherFragment();
        settingFragment = new SettingFragment();
        monitorFragment = new MonitorFragment();

        lightImpl = new LightImpl(ctFragment);

        connect();

       // socket = socketData.getSocket();
    }

    private void connect() {
        socketData = new SocketData(IpAddress, port);
        Thread t = new Thread(socketData);
        t.start();
        //todo vyresit pri spatnem nastaveni aby se nepripojil
        System.out.println("socketdata.isstate(): " + socketData.isState());
        if (socketData.isState())
            Toast.makeText(this, R.string.connected, Toast.LENGTH_LONG).show();
        else {
            Toast.makeText(this, R.string.not_connected, Toast.LENGTH_LONG).show();
            t.interrupt();
            finish();
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ct, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (id == R.id.nav_home) {
            fragmentTransaction.replace(R.id.fragmentFrameLayout, ctFragment);
        } else if (id == R.id.nav_monitor) {
            fragmentTransaction.replace(R.id.fragmentFrameLayout, monitorFragment);
        } else if (id == R.id.nav_setting) {
            fragmentTransaction.replace(R.id.fragmentFrameLayout, settingFragment);
        } else if (id == R.id.nav_weather) {
            fragmentTransaction.replace(R.id.fragmentFrameLayout, weatherFragment);
        }
        fragmentTransaction.commit();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public Socket getSocket() {
        return socketData.getSocket();
    }
}
