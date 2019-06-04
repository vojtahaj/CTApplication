package umte.fim.uhk.cz.ctapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import umte.fim.uhk.cz.ctapplication.utils.IPValidator;


public class MainActivity extends AppCompatActivity {

    private Socket socket;
    private SocketAddress socketAddress;
    private IPValidator ipValidator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(View view) {
        EditText txtIP = findViewById(R.id.editText_IP);
        EditText txtPort = findViewById(R.id.editText_Port);
        //todo validace ipAdresy
        if (!ipValidator.isIPAddress(txtIP.toString()))
            Toast.makeText(this, R.string.bad_IP_Format, Toast.LENGTH_LONG).show();
        else if (!ipValidator.isPort(txtPort.toString()))
            Toast.makeText(this, R.string.bad_port + ": " + txtPort.toString(), Toast.LENGTH_LONG).show();
        else {
            //pokud je to ok zkus otevrit socket, bude li to cajk, tak otevri CTactivity
            socketAddress = new InetSocketAddress(txtIP.toString(), Integer.parseInt(txtPort.toString()));
            try {
                socket.connect(socketAddress, 5000);
                Intent intent = new Intent(this, CTActivity.class);
                startActivity(intent);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "nepovedlo se pripojit",Toast.LENGTH_LONG).show();
            }
        }
    }
}
