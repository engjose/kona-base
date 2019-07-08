package com.kona.base.lib.util;

import java.util.Calendar;
import java.util.Date;

/**
 * @author : Yuan.Pan 2019/6/28 5:38 PM
 */
public class DateUtil {

    /**
     * 根据起始日和相隔月数计算终止日
     *
     * @param startDate
     * @param months
     * @return
     */
    public static Date getEndDateByMonths(Date startDate, int months) {
        Calendar calendarStartDate = Calendar.getInstance();
        calendarStartDate.setTime(startDate);
        calendarStartDate.add(Calendar.MONTH, months);
        return calendarStartDate.getTime();
    }

    /**
     * 得到上一天的日期(昨天)
     *
     * @param today
     * @return
     */
    public static Date getYesterday(Date today) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        cal.add(Calendar.DATE, -1);
        return cal.getTime();
    }
}
