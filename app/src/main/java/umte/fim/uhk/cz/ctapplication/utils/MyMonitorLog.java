package umte.fim.uhk.cz.ctapplication.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MyMonitorLog {

    private Date time;
    private boolean recieved;
    private String message;
    private SimpleDateFormat simpleDateFormat;

    public MyMonitorLog(String message, boolean recieved) {
        this.message = message;
        this.recieved = recieved;

        simpleDateFormat = new SimpleDateFormat("HH:mm:ss.SS");
        time = Calendar.getInstance().getTime();
        System.out.println("!!!!!!!!!!!!!!!!!!!!! ADDDED !!!!!!!!!!!!!!!!!");
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Date getTime() {
        return time;
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
