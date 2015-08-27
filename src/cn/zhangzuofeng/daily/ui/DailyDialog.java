package cn.zhangzuofeng.daily.ui;

import cn.zhangzuofeng.ZDateSelector.DateChangeListener;
import cn.zhangzuofeng.ZDateSelector.ZDateSelector;
import cn.zhangzuofeng.daily.dao.MissionScheduleManager;
import cn.zhangzuofeng.daily.entity.MissionEntity;
import cn.zhangzuofeng.daily.util.ZResource;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Date;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class DailyDialog extends JDialog {

    private ZDateSelector dateSelector;
    private JPanel dailyContentPanel;
    private MissionListPanel todayMissionListPanel;
    private MissionListPanel todayMemoListPanel;
    private MissionListPanel midPlanListPanel;
    private MissionListPanel someDayMissionListPanel;
    private final MissionScheduleManager missionScheduleManager = new MissionScheduleManager();
    private JButton cancelButton;
    private JButton sureButton;
    private Date date;
    private JCheckBox showCompeletedCheckbox;
    private JCheckBox showUnderwayCheckbox;
    private JCheckBox showDeletedCheckbox;
    private JButton saveButton;

    public DailyDialog(Date date) {
        this.date = date;
        if (date == null) {
            this.date = new Date();
        }
        setIconImage(ZResource.getAppImage());
        setTitle(ZResource.getLabel("dailyMission"));
        initComponents();
        loadData();
        loadAction();
    }

    private void initComponents() {
        dailyContentPanel = new JPanel();
        todayMissionListPanel = new MissionListPanel(MissionEntity.TODAY_MISSION_TYPE, date);
        todayMemoListPanel = new MissionListPanel(MissionEntity.TODAY_MEMO_TYPE, date);
        midPlanListPanel = new MissionListPanel(MissionEntity.MIDDLE_PLAN_TYPE);
        someDayMissionListPanel = new MissionListPanel(MissionEntity.SOME_DAY_MISSION_TYPE);

        dailyContentPanel.setLayout(new BoxLayout(dailyContentPanel, BoxLayout.Y_AXIS));

        dailyContentPanel.add(midPlanListPanel);
        dailyContentPanel.add(todayMissionListPanel);
        dailyContentPanel.add(todayMemoListPanel);
        dailyContentPanel.add(someDayMissionListPanel);

        JPanel eastPanel = new JPanel();
        eastPanel.setBorder(BorderFactory.createEtchedBorder());

        eastPanel.setLayout(new BorderLayout(0, 0));
        JScrollPane scrollPane = new JScrollPane(dailyContentPanel);

        JPanel dailyPanel = new JPanel();
        dailyPanel.setLayout(new BorderLayout());
        JPanel dailyToolPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        showCompeletedCheckbox = new JCheckBox();

        showCompeletedCheckbox.setLabel(ZResource.getLabel("completed"));
        showUnderwayCheckbox = new JCheckBox();
        showUnderwayCheckbox.setLabel(ZResource.getLabel("underway"));
        showDeletedCheckbox = new JCheckBox();
        showDeletedCheckbox.setLabel(ZResource.getLabel("deleted"));

        dailyToolPanel.add(showCompeletedCheckbox);
        dailyToolPanel.add(showUnderwayCheckbox);
        dailyToolPanel.add(showDeletedCheckbox);

        dailyPanel.add(dailyToolPanel, BorderLayout.NORTH);
        dailyPanel.add(scrollPane, BorderLayout.CENTER);
        eastPanel.add(dailyPanel, BorderLayout.CENTER);

        JPanel westPanel = new JPanel();

        dateSelector = new ZDateSelector(date);
        dateSelector.setBorder(BorderFactory.createRaisedBevelBorder());
        westPanel.add(dateSelector);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        buttonPanel.setBorder(BorderFactory.createRaisedBevelBorder());
        sureButton = new JButton();
        cancelButton = new JButton();
        saveButton = new JButton();

        sureButton.setText(ZResource.getLabel("sure"));
        cancelButton.setText(ZResource.getLabel("cancel"));
        saveButton.setText(ZResource.getLabel("save"));
        buttonPanel.add(sureButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout(0, 0));
        contentPanel.add(westPanel, BorderLayout.WEST);
        contentPanel.add(eastPanel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
        this.setContentPane(contentPanel);
    }

    private void loadData() {
        boolean showCompletedMission = showCompeletedCheckbox.isSelected();
        boolean showDeletedMission = showDeletedCheckbox.isSelected();
        boolean showUnderwayMission = showUnderwayCheckbox.isSelected();
//        missionScheduleManager.reloadDataFromDatabase();
        List<MissionEntity> todayMission = missionScheduleManager.getTodayMission();
        todayMissionListPanel.setMissionList(todayMission, date, showCompletedMission);

        todayMission = missionScheduleManager.getTodayMemo();
        todayMemoListPanel.setMissionList(todayMission, date, showCompletedMission);

        todayMission = missionScheduleManager.getMidPlan();
        midPlanListPanel.setMissionList(todayMission, date, showCompletedMission);

        todayMission = missionScheduleManager.getSomeDayMissions();
        someDayMissionListPanel.setMissionList(todayMission, date, showCompletedMission);
        
        dailyContentPanel.validate();
    }

    private void loadAction() {
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DailyDialog.this.dispose();
            }
        });
        sureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean todayMissionListPanelChanged = todayMissionListPanel.isChanged(missionScheduleManager.getTodayMission());
                boolean todayMemoListPanelChanged = todayMemoListPanel.isChanged(missionScheduleManager.getTodayMemo());
                boolean someDayMissionListPanelChanged = someDayMissionListPanel.isChanged(missionScheduleManager.getSomeDayMissions());
                boolean midPlanListPanelChanged = midPlanListPanel.isChanged(missionScheduleManager.getMidPlan());
                if (todayMemoListPanelChanged || todayMissionListPanelChanged || someDayMissionListPanelChanged || midPlanListPanelChanged) {
                    List<MissionEntity> todayMissionList = todayMissionListPanel.getChangedMissionList();
                    List<MissionEntity> todayMemoMissionList = todayMemoListPanel.getChangedMissionList();
                    List<MissionEntity> someDayMissionList = someDayMissionListPanel.getChangedMissionList();
                    List<MissionEntity> midPlanMissionList = midPlanListPanel.getChangedMissionList();
                    missionScheduleManager.setTodayMission(todayMissionList, date);
                    missionScheduleManager.setTodayMemo(todayMemoMissionList, date);
                    missionScheduleManager.setSomeDayMission(someDayMissionList);
                    missionScheduleManager.setMidPlan(midPlanMissionList);
                    missionScheduleManager.save();
                }
                DailyDialog.this.dispose();
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message;
                boolean todayMissionListPanelChanged = todayMissionListPanel.isChanged(missionScheduleManager.getTodayMission());
                boolean todayMemoListPanelChanged = todayMemoListPanel.isChanged(missionScheduleManager.getTodayMemo());
                boolean someDayMissionListPanelChanged = someDayMissionListPanel.isChanged(missionScheduleManager.getSomeDayMissions());
                boolean midPlanListPanelChanged = midPlanListPanel.isChanged(missionScheduleManager.getMidPlan());
                if (todayMemoListPanelChanged || todayMissionListPanelChanged || someDayMissionListPanelChanged || midPlanListPanelChanged) {
                    List<MissionEntity> todayMissionList = todayMissionListPanel.getChangedMissionList();
                    List<MissionEntity> todayMemoMissionList = todayMemoListPanel.getChangedMissionList();
                    List<MissionEntity> someDayMissionList = someDayMissionListPanel.getChangedMissionList();
                    List<MissionEntity> midPlanMissionList = midPlanListPanel.getChangedMissionList();
                    missionScheduleManager.setTodayMission(todayMissionList, date);
                    missionScheduleManager.setTodayMemo(todayMemoMissionList, date);
                    missionScheduleManager.setSomeDayMission(someDayMissionList);
                    missionScheduleManager.setMidPlan(midPlanMissionList);
                    missionScheduleManager.save();
                    message = ZResource.getLabel("saveSuccess");
                } else {
                    message = ZResource.getLabel("noChangedNotNeedSave");
                }
                JOptionPane.showMessageDialog(DailyDialog.this, message);
            }
        });
        dateSelector.addDateChangeListener(new DateChangeListener() {

            @Override
            public void actionPerformance() {
                Date date = dateSelector.getDate();
//                System.out.println("" + date);
                if (date == null) {
                    return;
                }
                String oldDateString = MissionScheduleManager.simpleDateFormat.format(DailyDialog.this.date);
                String newDateString = MissionScheduleManager.simpleDateFormat.format(date);
                if (oldDateString.equals(newDateString)) {
                    return;
                }
                DailyDialog.this.date = date;
                List<MissionEntity> todayMissionOfOneDay = missionScheduleManager.getTodayMissionOfOneDay(date);
                List<MissionEntity> todayMemoOfOneDay = missionScheduleManager.getTodayMemoOfOneDay(date);
                todayMissionListPanel.setMissionList(todayMissionOfOneDay, date);
                todayMemoListPanel.setMissionList(todayMemoOfOneDay, date);
                dailyContentPanel.validate();
            }
        });
        showCompeletedCheckbox.addItemListener(new ItemListener() {

            @Override
            public void itemStateChanged(ItemEvent e) {
                Object source = e.getSource();
                if (source != null) {
                    JCheckBox checkbox = (JCheckBox) source;
                    //TODO
                    loadData();
                }
            }
        });
    }
}
