package umte.fim.uhk.cz.ctapplication.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.Socket;
import java.net.UnknownHostException;

import umte.fim.uhk.cz.ctapplication.CTActivity;
import umte.fim.uhk.cz.ctapplication.R;

public class SocketData implements Runnable {

    private BufferedReader inputData;
    private DataOutputStream outputStream;

    private Inet4Address serverAddress;
    private Socket socket;
    private int PORT;// = 1024;
    private String ipAddress;// = "192.168.2.106";

    public SocketData(String ipAddress, int port) {
        this.ipAddress = ipAddress;
        PORT = port;
    }

    @Override
    public void run() {
        try {
            serverAddress = (Inet4Address) Inet4Address.getByName(ipAddress);
            socket = new Socket(serverAddress, PORT);
            if (socket.isConnected()) {
                System.out.println("Connected");
                outputStream = new DataOutputStream(socket.getOutputStream());
                String s = "hello from client\r\n";
                outputStream.writeBytes(s);
                CTActivity.monitorLogs.add(new MyMonitorLog(s, false));
            }
            inputData = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            System.out.println(inputData.readLine());
//            while (!Thread.currentThread().isInterrupted()) {
//                if (inputData.ready()) {
//                    System.out.println(inputData.readLine());
//                    System.out.println("ctu data");
//                    CTActivity.monitorLogs.add(new MyMonitorLog(inputData.readLine(), true));
//                    //todo zpracuj nactena data
//                }
//            }

        } catch (UnknownHostException e) {
            System.out.println(R.string.connect_fail);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(R.string.connect_fail);
        }

    }
}
