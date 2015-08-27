package cn.zhangzuofeng.ZDateSelector;

import java.util.Calendar;
import java.util.Date;

public interface SyncCalendar {

    public void goToCalendar(Date date);

    public Date getDate();

    public void addDateChangeListener(DateChangeListener dataChangeActionListener);
}
