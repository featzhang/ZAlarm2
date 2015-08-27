package cn.zhangzuofeng.tip;

import java.util.List;

/**
 * Created by Mariaaron on 15/8/15.
 */
public class TipDataEntity {
    private String title;
    private java.util.List<TipDialogButtonEntity> buttons;
    private String content;
    private SHOW_TIME showTime = SHOW_TIME.Short;
    private Boolean canBeClosed = true;

    enum SHOW_TIME {Long, Middle, Short}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<TipDialogButtonEntity> getButtons() {
        return buttons;
    }

    public void setButtons(List<TipDialogButtonEntity> buttons) {
        this.buttons = buttons;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public SHOW_TIME getShowTime() {
        return showTime;
    }

    public void setShowTime(SHOW_TIME showTime) {
        this.showTime = showTime;
    }

    public Boolean getCanBeClosed() {
        return canBeClosed;
    }

    public void setCanBeClosed(Boolean canBeClosed) {
        this.canBeClosed = canBeClosed;
    }
}
