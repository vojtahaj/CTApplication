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

public class SocketData implements Runnable, SocketWriter {

    private BufferedReader inputData;
    private DataOutputStream outputStream;

    private Inet4Address serverAddress;
    private Socket socket;
    private int port; // = 1024;
    private String ipAddress;// = "192.168.2.106";

  //  public static Handler handler;

    private boolean state = true;

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public Socket getSocket() {
        return socket;
    }

    public SocketData(String ipAddress, int port) {
        this.ipAddress = ipAddress;
        this.port = port;
    }

    @Override
    public void run() {
        try {
            serverAddress = (Inet4Address) Inet4Address.getByName(ipAddress);
            socket = new Socket(serverAddress, port);
            setState(true);
            if (socket.isConnected()) {
                System.out.println("Connected");
                outputStream = new DataOutputStream(socket.getOutputStream());
                String s = "A\r\n";
                outputStream.writeBytes(s);
                CTActivity.monitorLogs.add(new MyMonitorLog(s, false));

//                Looper.prepare();
//                handler = new Handler() {
//                    public void handleMessage(Message message) {
//                        super.handleMessage(message);
//                        try {
//                            outputStream.writeBytes(message.toString());
//                            CTActivity.monitorLogs.add(new MyMonitorLog(message.toString(), false));
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//
//                };
//                Looper.loop();
            }

            inputData = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (socket.isConnected()) {
                //  System.out.println(inputData.readLine());
                //    System.out.println(inputData.readLine());
                System.out.println("ctu data");
                String s = inputData.readLine();
                CTActivity.monitorLogs.add(new MyMonitorLog(s, true));

                CTActivity.lightImpl.parse(s, CTActivity.lightImpl.getCT());
            }

        } catch (UnknownHostException e) {
            System.out.println(R.string.connect_fail);
            setState(false);
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            setState(false);
            System.out.println(R.string.connect_fail);
        }

    }

    @Override
    public void write(String messsage) throws IOException {
        outputStream.writeBytes(messsage);
    }

    public DataOutputStream getOutputStream() {
        return outputStream;
    }

}
