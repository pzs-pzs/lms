package com.lms.demo.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtils {
    public static Date getBeforeDays(Date now,int days){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DATE,-days);
        Date before = new Date(calendar.getTimeInMillis());
        return before;
    }


}
