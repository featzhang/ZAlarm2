/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cn.zhangzuofeng.ZDateSelector;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Mariaaron
 */
public class CalendarUtil {

    public static boolean isToday(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar todayStartCalendar = Calendar.getInstance();
        todayStartCalendar.set(Calendar.HOUR_OF_DAY, 0);
        todayStartCalendar.set(Calendar.MINUTE, 0);
        todayStartCalendar.set(Calendar.SECOND, 0);

        Calendar todayEndCalendar = (Calendar) todayStartCalendar.clone();
        todayEndCalendar.add(Calendar.DAY_OF_YEAR, 1);
        todayEndCalendar.add(Calendar.SECOND, -1);
        return calendar.before(todayEndCalendar) && calendar.after(todayStartCalendar);
    }
}
