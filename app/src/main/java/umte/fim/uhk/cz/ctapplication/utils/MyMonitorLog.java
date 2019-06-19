package umte.fim.uhk.cz.ctapplication.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MyMonitorLog {

    private String sTime;
    private boolean recieved;
    private String message;

    public MyMonitorLog(String message, boolean recieved) {
        this.message = message;
        this.recieved = recieved;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss.SS");
        sTime = simpleDateFormat.format(new Date());
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
