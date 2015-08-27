package cn.zhangzuofeng.ZAlarm.ui;

import cn.zhangzuofeng.ZAlarm.AlarmMain;
import cn.zhangzuofeng.ZAlarm.regularBreaks.RegularBreaksConfigEntry;
import cn.zhangzuofeng.ZAlarm.regularBreaks.RegularBreaksTimer;
import cn.zhangzuofeng.ZAlarm.util.ZResource;
import cn.zhangzuofeng.daily.ui.DailyDialog;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.Serializable;
import java.sql.Timestamp;

public enum AlarmSystemTray implements Serializable {

    instance;

    private MenuItem exitMenuItem;
    private MenuItem detailSettingMenuItem;
    private MenuItem restImmediatelyMenuItem;
    private MenuItem restNextTimeMenuItem;
    private TrayIcon trayIcon;
    private MenuItem dailyMenuItem;

    public TrayIcon getTrayIcon() {
        return trayIcon;
    }

    public void create() {
        SystemTray systemTray = SystemTray.getSystemTray();
        PopupMenu popupMenu = createPopupMenu();

        trayIcon = new TrayIcon(ZResource.getImage("systemTray"), ZResource.getAppName(), popupMenu);
        try {
            systemTray.add(trayIcon);
        } catch (AWTException e) {
            e.printStackTrace();
        }
        loadAction();
    }

    private void loadAction() {
        exitMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        detailSettingMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DetailSettingDialog dialog = new DetailSettingDialog();
                RegularBreaksConfigEntry dataEntry = AlarmMain.regularBreaksTimer.getDataEntry();
                dialog.setData(dataEntry);
                dialog.pack();
                dialog.setSize(400, 450);
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });
        dailyMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                DailyDialog dailyDialog = new DailyDialog(null);

                dailyDialog.setAlwaysOnTop(true);
                dailyDialog.setLocationByPlatform(true);
                dailyDialog.pack();
                dailyDialog.setSize(800, 700);
                dailyDialog.setVisible(true);
            }
        });
        restImmediatelyMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RegularBreaksTimer timer = RegularBreaksTimer.instance;
                timer.restImmediately();
            }
        });
        restNextTimeMenuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                RegularBreaksTimer timer = RegularBreaksTimer.instance;
                timer.restBeyondImmediately();
            }
        });
        trayIcon.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                int clickCount = e.getClickCount();
                if (clickCount==2) {
                    DailyDialog dailyDialog = new DailyDialog(new Timestamp(System.currentTimeMillis()));
                    dailyDialog.setTitle("DailyDialog");
                    dailyDialog.pack();
                    dailyDialog.setSize(800, 600);
                    dailyDialog.setLocationRelativeTo(null);
                    dailyDialog.setVisible(true);
                }
            }
        });
    }

    private PopupMenu createPopupMenu() {
        PopupMenu popupMenu = new PopupMenu(ZResource.getAppName());

        dailyMenuItem = new MenuItem(ZResource.getLabel("dailyMission"));
        popupMenu.add(dailyMenuItem);

        restImmediatelyMenuItem = new MenuItem(ZResource.getLabel("restImmediately"));
        popupMenu.add(restImmediatelyMenuItem);

        restNextTimeMenuItem = new MenuItem(ZResource.getLabel("restNextTime"));
        popupMenu.add(restNextTimeMenuItem);

        detailSettingMenuItem = new MenuItem(ZResource.getLabel("detailSetting"));
        popupMenu.add(detailSettingMenuItem);

        exitMenuItem = new MenuItem(ZResource.getLabel("exit"));
        popupMenu.add(exitMenuItem);

        return popupMenu;
    }
}
