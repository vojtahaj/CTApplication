package umte.fim.uhk.cz.ctapplication.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyMonitorLog {

    private String sTime;
    private boolean recieved;
    private String message;
    private SimpleDateFormat simpleDateFormat;

    public MyMonitorLog(String message, boolean recieved) {
        this.message = message;
        this.recieved = recieved;

        simpleDateFormat = new SimpleDateFormat("HH:mm:ss.SS");
        String sTime = simpleDateFormat.format(new Date());
        System.out.println("Novy format datumu: " + sTime);
        System.out.println("!!!!!!!!!!!!!!!!!!!!! ADDDED !!!!!!!!!!!!!!!!!");
    }

    public void setTime(String time) {
        sTime = time;
    }

    public String getTime() {
        return sTime;
    }

    public boolean isRecieved() {
        return recieved;
    }

    public void setRecieved(boolean recieved) {
        this.recieved = recieved;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
