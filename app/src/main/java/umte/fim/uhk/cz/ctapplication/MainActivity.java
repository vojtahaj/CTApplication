package umte.fim.uhk.cz.ctapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import umte.fim.uhk.cz.ctapplication.utils.IPValidator;


public class MainActivity extends AppCompatActivity {

    public static final String SHARED_PREF = "SHARED_PREF";
    public static final String SHARED_PREF_IP = "IP_ADDRESS";
    public static final String SHARED_PREF_PORT = "PORT";
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    EditText txtIP;
    EditText txtPort;

    @SuppressLint({"SetTextI18n", "CommitPrefEdits"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtIP = findViewById(R.id.editText_IP);
        txtPort = findViewById(R.id.editText_Port);
        sharedPreferences = getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if(sharedPreferences.contains(SHARED_PREF_IP)){
            txtIP.setText(sharedPreferences.getString(SHARED_PREF_IP, ""));
        }
        if(sharedPreferences.contains(SHARED_PREF_PORT)){
            int port = sharedPreferences.getInt(SHARED_PREF_PORT,0);
            txtPort.setText(Integer.toString(port));
        }

    }

    public void login(View view) {

        int port = 0;
        String sIP = txtIP.getText().toString();

        //validace ipAdresy
        if (IPValidator.isIPAddress(sIP))
            Toast.makeText(this, R.string.bad_IP_Format + " IP> " + sIP, Toast.LENGTH_LONG).show();
        else if (!IPValidator.isPort(txtPort.getText().toString())) {
            Toast.makeText(this, R.string.bad_port + ": " + txtPort.getText().toString(), Toast.LENGTH_LONG).show();
        } else {
            port = Integer.parseInt(txtPort.getText().toString());
            //pokud je to posli na CTactivity
            Intent intent = new Intent(this, CTActivity.class);
            Toast.makeText(this, "posilam do ct" + port + " ip: " + txtIP.getText().toString(), Toast.LENGTH_LONG).show();
            intent.putExtra("IP", sIP);
            intent.putExtra("port", port);

            editor.putString(SHARED_PREF_IP, sIP);
            editor.putInt(SHARED_PREF_PORT, port);
            editor.commit();
            startActivity(intent);

        }
    }

}
