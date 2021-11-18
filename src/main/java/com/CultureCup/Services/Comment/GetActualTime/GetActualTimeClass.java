package com.CultureCup.Services.Comment.GetActualTime;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GetActualTimeClass {

    public static String getActualTime() {

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date date = new Date();
        return formatter.format((date));
    }
}
