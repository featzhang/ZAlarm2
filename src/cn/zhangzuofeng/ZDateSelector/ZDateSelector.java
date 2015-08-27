package cn.zhangzuofeng.ZDateSelector;

import java.awt.BorderLayout;
import java.util.Date;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class ZDateSelector extends JPanel {

    private Date date;
    private List<DateChangeListener> dateChangeListeners;
    private CalendarDatePanel calendarDatePanel = null;
    private DateSelectorToolbar dateSelectorToolbar = null;

    public ZDateSelector(Date date) {
        if (date == null) {
            date = new Date();
        }

        calendarDatePanel = new CalendarDatePanel(date);
        dateSelectorToolbar = new DateSelectorToolbar(date);
        this.setLayout(new BorderLayout(0, 0));
        this.add(dateSelectorToolbar, BorderLayout.NORTH);
        this.add(calendarDatePanel, BorderLayout.CENTER);
        calendarDatePanel.addDateChangeListener(new DateChangeListener() {

            @Override
            public void actionPerformance() {
                Date date1 = calendarDatePanel.getDate();
                dateSelectorToolbar.goToCalendar(date1);
                ZDateSelector.this.date = date1;
            }
        });
        dateSelectorToolbar.addDateChangeListener(new DateChangeListener() {

            @Override
            public void actionPerformance() {
                Date date1 = dateSelectorToolbar.getDate();
                calendarDatePanel.goToCalendar(date1);
                ZDateSelector.this.date = date1;
            }
        });
    }

    public static void main(String[] args) {
        ZDateSelector zDateSelector = new ZDateSelector(null);
        JFrame jFrame = new JFrame();
        jFrame.setContentPane(zDateSelector);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.pack();
        jFrame.setSize(400, 600);
        jFrame.setVisible(true);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void addDateChangeListener(DateChangeListener dateChangeListener) {
        calendarDatePanel.addDateChangeListener(dateChangeListener);
        dateSelectorToolbar.addDateChangeListener(dateChangeListener);
    }

}
