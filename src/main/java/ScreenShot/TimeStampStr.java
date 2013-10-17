package ScreenShot;


import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * User: mavolkov
 * Date: 17.10.13
 */
public class TimeStampStr {
  private static TimeStampStr ourInstance;
  private String lastValue;

  public static TimeStampStr getInstance() {
    if (ourInstance == null) {
      ourInstance = new TimeStampStr();
    }
    return ourInstance;
  }

  private TimeStampStr() {
    if (getLastValue() == null || "".equals(getLastValue())) {
      getCurValue();
    }
  }

  public String getLastValue() {
    return lastValue;
  }

  public String getCurValue() {
    Calendar mycalendar = Calendar.getInstance();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
    String timeStamp = formatter.format(mycalendar.getTime());
    setLastValue(timeStamp);
    return lastValue;
  }

  private void setLastValue(String lastValue) {
    this.lastValue = lastValue;
  }
}
