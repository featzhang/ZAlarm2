package cn.zhangzuofeng.ZDateSelector;

import cn.zhangzuofeng.ZDateSelector.resources.ZResource;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.Border;

public class DateSelectorToolbar extends javax.swing.JPanel implements SyncCalendar {

    private Border todayLabelStrongBorder = BorderFactory.createLineBorder(Color.RED);
    private Border enterBorder = BorderFactory.createLineBorder(Color.YELLOW);
    private Date date;
    private Border normalBorder = BorderFactory.createLineBorder(Color.BLACK);

    public DateSelectorToolbar(Date date) {
        this.date = date;
        initComponents();
        optimizeLabels();
        loadAction();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lastYearLabel = new javax.swing.JLabel();
        lastMonthLabel = new javax.swing.JLabel();
        todayLabel = new javax.swing.JLabel();
        nextMonthLabel = new javax.swing.JLabel();
        nextYearLabel = new javax.swing.JLabel();
        infoLabel = new javax.swing.JLabel();

        setOpaque(false);
        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 5, 0);
        flowLayout1.setAlignOnBaseline(true);
        setLayout(flowLayout1);

        lastYearLabel.setText(" << ");
        lastYearLabel.setToolTipText(ZResource.getLabel("lastYear"));
        add(lastYearLabel);

        lastMonthLabel.setText(" < ");
        lastMonthLabel.setToolTipText(ZResource.getLabel("lastMonth"));
        add(lastMonthLabel);

        todayLabel.setText("Today");
        add(todayLabel);

        nextMonthLabel.setText(" > ");
        nextMonthLabel.setToolTipText(ZResource.getLabel("nextMonth"));
        add(nextMonthLabel);

        nextYearLabel.setText(" >> ");
        nextYearLabel.setToolTipText(ZResource.getLabel("nextYear"));
        add(nextYearLabel);

        infoLabel.setText("info");
        add(infoLabel);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel infoLabel;
    private javax.swing.JLabel lastMonthLabel;
    private javax.swing.JLabel lastYearLabel;
    private javax.swing.JLabel nextMonthLabel;
    private javax.swing.JLabel nextYearLabel;
    private javax.swing.JLabel todayLabel;
    // End of variables declaration//GEN-END:variables

    @Override
    public void goToCalendar(Date date) {

        this.date = date;

        if (CalendarUtil.isToday(date)) {
            todayLabel.setBorder(todayLabelStrongBorder);
        } else {
            todayLabel.setBorder(normalBorder);
        }
        refreshInfoLabel();
    }


    @Override
    public Date getDate() {
        return date;
    }

    private MouseListener mouseListener = new MouseListener() {

        @Override
        public void mouseClicked(MouseEvent e) {
            JLabel label = (JLabel) e.getSource();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            if (label == lastMonthLabel) {
                calendar.add(Calendar.MONTH, -1);
            } else if (label == lastYearLabel) {
                calendar.add(Calendar.YEAR, -1);
            } else if (label == todayLabel) {
                calendar = Calendar.getInstance();
            } else if (label == nextYearLabel) {
                calendar.add(Calendar.YEAR, 1);
            } else if (label == nextMonthLabel) {
                calendar.add(Calendar.MONTH, 1);
            }
            date = calendar.getTime();
            if (CalendarUtil.isToday(date)) {
                todayLabel.setBorder(todayLabelStrongBorder);
            } else {
                todayLabel.setBorder(normalBorder);
            }

            refreshInfoLabel();

            if (dataChangeActionListeners != null) {
                for (DateChangeListener DataChangeActionListener1 : dataChangeActionListeners) {
                    DataChangeActionListener1.actionPerformance();
                }
            }

        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            JLabel label = (JLabel) e.getSource();
            if (label == todayLabel) {

            } else {
                label.setBorder(enterBorder);
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            JLabel label = (JLabel) e.getSource();
            if (label == todayLabel) {

            } else {
                label.setBorder(normalBorder);
            }
        }
    };

    private void loadAction() {
        lastMonthLabel.addMouseListener(mouseListener);
        lastYearLabel.addMouseListener(mouseListener);
        todayLabel.addMouseListener(mouseListener);
        nextYearLabel.addMouseListener(mouseListener);
        nextMonthLabel.addMouseListener(mouseListener);
    }
    private List<DateChangeListener> dataChangeActionListeners;

    @Override
    public void addDateChangeListener(DateChangeListener dataChangeActionListener) {
        if (dataChangeActionListener == null) {
            return;
        }
        if (dataChangeActionListeners == null) {
            dataChangeActionListeners = new ArrayList<DateChangeListener>();
        }
        dataChangeActionListeners.add(dataChangeActionListener);
    }

    private void optimizeLabels() {
//        infoLabel.setBorder(normalBorder);
        lastMonthLabel.setBorder(normalBorder);
        lastYearLabel.setBorder(normalBorder);
        if (CalendarUtil.isToday(date)) {
            todayLabel.setBorder(todayLabelStrongBorder);
        } else {
            todayLabel.setBorder(normalBorder);
        }
        nextYearLabel.setBorder(normalBorder);
        nextMonthLabel.setBorder(normalBorder);
        refreshInfoLabel();
    }

    private void refreshInfoLabel() {
        if (date != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            infoLabel.setText(sdf.format(date));
        }
    }
}
