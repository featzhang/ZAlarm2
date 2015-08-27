package cn.zhangzuofeng.ZAlarm.ui.AlarmFrame;

import cn.zhangzuofeng.daily.dao.MissionScheduleManager;
import cn.zhangzuofeng.daily.entity.MissionEntity;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AlertFrameControl {
    private static boolean fullOnScreen = true;

    private static Image getNextImage() {
        return null;
    }

    public static void showAlertFrameWithRestTime(int restTime) {
        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] screenDevices = graphicsEnvironment.getScreenDevices();
        Image nextImage = getNextImage();
        List<AlertContentEntity> messages = getMessages();
        for (GraphicsDevice screenDevice : screenDevices) {
            Rectangle rectangle = screenDevice.getDefaultConfiguration().getBounds();
            int screenSizeWidth = rectangle.width;
            int screenSizeHeight = rectangle.height;
            double x = screenSizeWidth / 4.0 + rectangle.x;
            double y = screenSizeHeight / 4.0 + rectangle.y;
            AlertCommonDialog alertFrame = new AlertCommonDialog(screenDevice.getDefaultConfiguration());
            alertFrame.setSize(screenSizeWidth / 2, screenSizeHeight / 2);
            alertFrame.setLocation((int) x, (int) y);
            alertFrame.setBackgroundImage(nextImage);
            alertFrame.setMessages(messages);
            alertFrame.beginRestTimer(restTime);

        }
    }

    private static List<AlertContentEntity> getMessages() {
        MissionScheduleManager missionScheduleManager = new MissionScheduleManager();
        List<MissionEntity> todayMission = missionScheduleManager.getTodayMission();
        List<MissionEntity> midPlan = missionScheduleManager.getMidPlan();
        List<MissionEntity> todayMemo = missionScheduleManager.getTodayMemo();
        List<MissionEntity> someDayMissions = missionScheduleManager.getSomeDayMissions();

        ArrayList<AlertContentEntity> alertContentEntities = new ArrayList<AlertContentEntity>();
        AlertContentEntity alertContentEntity = missionEntity2AlertContent(todayMission);
        if (alertContentEntity != null) {
            alertContentEntity.setTitle("今日任务");
            alertContentEntities.add(alertContentEntity);
        }
        alertContentEntity = missionEntity2AlertContent(todayMemo);
        if (alertContentEntity != null) {
            alertContentEntity.setTitle("今日备忘");
            alertContentEntities.add(alertContentEntity);

        }
        alertContentEntity = missionEntity2AlertContent(midPlan);
        if (alertContentEntity != null) {
            alertContentEntity.setTitle("中期计划");
            alertContentEntities.add(alertContentEntity);
        }
        alertContentEntity = missionEntity2AlertContent(someDayMissions);
        if (alertContentEntity != null) {
            alertContentEntity.setTitle("备忘");
            alertContentEntities.add(alertContentEntity);
        }
        return alertContentEntities;
    }

    private static AlertContentEntity missionEntity2AlertContent(List<MissionEntity> mission) {
        ArrayList<String> strings = new ArrayList<String>();
        if (mission == null) {
            return null;
        }
        for (MissionEntity missionEntity : mission) {
            MissionEntity.MissionStatus status = missionEntity.getStatus();
            if (status == MissionEntity.MissionStatus.DELETED) {
                continue;
            }
            String title = missionEntity.getTitle();
            Date alertTime = missionEntity.getCreateTimestamp();
            strings.add(title + ":" + alertTime);
        }
        if (strings.size() > 0) {
            AlertContentEntity alertContentEntity = new AlertContentEntity();
            alertContentEntity.setSection(strings);
            return alertContentEntity;
        }
        return null;
    }

    public static void main(String[] args) {
        AlertFrameControl.showAlertFrameWithRestTime(30000);
    }

}
