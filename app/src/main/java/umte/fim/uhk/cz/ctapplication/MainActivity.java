package umte.fim.uhk.cz.ctapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import umte.fim.uhk.cz.ctapplication.utils.IPValidator;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(View view) {
        EditText txtIP = findViewById(R.id.editText_IP);
        EditText txtPort = findViewById(R.id.editText_Port);
        int port = 0;
        String sIP = txtIP.getText().toString();

        //todo validace ipAdresy
        if (IPValidator.isIPAddress(sIP))
            Toast.makeText(this, R.string.bad_IP_Format + " IP> " + sIP, Toast.LENGTH_LONG).show();
        else if (!IPValidator.isPort(txtPort.getText().toString())) {
            Toast.makeText(this, R.string.bad_port + ": " + txtPort.getText().toString(), Toast.LENGTH_LONG).show();
        } else {
            port = Integer.parseInt(txtPort.getText().toString());
            //pokud je to posli na CTactivity
            Intent intent = new Intent(this, CTActivity.class);
            Toast.makeText(this, "posilam do ct"+ port + " ip: " + txtIP.getText().toString(), Toast.LENGTH_LONG).show();
            intent.putExtra("IP", sIP);
            intent.putExtra("port", port);
            startActivity(intent);

        }
    }
}
