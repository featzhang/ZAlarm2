package cn.zhangzuofeng.tip;

import javax.swing.*;

/**
 * Created by Mariaaron on 15/8/15.
 */
public class TipDialogButtonEntity {
    private TipDialogButtonActionListener action = null;
    private javax.swing.ImageIcon icon;
    private String tooltip;

    public TipDialogButtonActionListener getAction() {
        return action;
    }

    public void setAction(TipDialogButtonActionListener action) {
        this.action = action;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public String getTooltip() {
        return tooltip;
    }

    public void setTooltip(String tooltip) {
        this.tooltip = tooltip;
    }
}
