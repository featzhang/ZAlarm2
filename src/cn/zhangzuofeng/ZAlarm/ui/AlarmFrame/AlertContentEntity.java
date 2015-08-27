package cn.zhangzuofeng.ZAlarm.ui.AlarmFrame;

import java.util.ArrayList;
import java.util.List;

public class AlertContentEntity {

    private String title;
    private List<String> section;

    public void addSection(String section1) {
        if (section == null) {
            section = new ArrayList<String>();
        }
        section.add(section1);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getSection() {
        return section;
    }

    public void setSection(List<String> section) {
        this.section = section;
    }

}
