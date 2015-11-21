package com.vladstarikov.openweather;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by vladstarikov on 21.11.15.
 */
public class MyDateFormatter {

    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US);
    SimpleDateFormat myFormatDate = new SimpleDateFormat("E MMM dd", Locale.US);
    SimpleDateFormat myFormatTime = new SimpleDateFormat("HH:mm", Locale.US);

    Date date;

    public MyDateFormatter(String input) {
        try {
            date = inputFormat.parse(input);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String getTime() {
        return myFormatTime.format(date);
    }

    public String getDate() {
        return myFormatDate.format(date);
    }

    public String getDateTime() {
        return myFormatDate.format(date) + " " + myFormatTime.format(date);
    }

    @Override
    public String toString() {
        return getDateTime();
    }
}
