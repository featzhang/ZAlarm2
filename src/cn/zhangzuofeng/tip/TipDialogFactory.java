package cn.zhangzuofeng.tip;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class TipDialogFactory {

    private static List<TipDialog> waitTipDialog = new ArrayList<TipDialog>();

    public static void showDesktopTip(String title, String content) {
        showDesktopTip(title, content, true);
    }

    public static void showDesktopTip(String title, String content, boolean canClosed) {
        showDesktopTip(title, content, canClosed, null);
    }

    public static void showDesktopTip(String title, String content, boolean canClosed, List<TipDialogButtonEntity> extureButtonEntrys) {
        TipDataEntity dataEntity = new TipDataEntity();
        dataEntity.setTitle(title);
        dataEntity.setCanBeClosed(canClosed);
        dataEntity.setContent(content);
        dataEntity.setButtons(extureButtonEntrys);
        showDesktopTip(dataEntity);
    }

    private static synchronized TipDialog getTipDialog() {
        TipDialog tipDialog = null;
        Point lastPosition = new Point();
        lastPosition.x = Integer.MAX_VALUE;
        lastPosition.y = Integer.MAX_VALUE;
        for (TipDialog tipDialog1 : waitTipDialog) {
            if (tipDialog1.isVisible()) {
                Point tipDialog1Location = tipDialog1.getLocation();
                if (tipDialog1Location.x < lastPosition.x) {
                    lastPosition.x = tipDialog1Location.x;
                    lastPosition.y = tipDialog1Location.y-tipDialog1.getHeight();
                } else if (tipDialog1Location.x == lastPosition.x) {
                    if (tipDialog1Location.y < lastPosition.y) {
                        lastPosition.y = tipDialog1Location.y - tipDialog1.getHeight();
                    }
                }
            } else {
                tipDialog = tipDialog1;
            }
        }
        if (tipDialog == null) {
            tipDialog = new TipDialog(null, false);
            waitTipDialog.add(tipDialog);
        }
//jisuan weizhi
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        if (lastPosition.y  < 10) {
            lastPosition.x = lastPosition.x - tipDialog.getWidth() - 2;
            lastPosition.y = screenSize.height-tipDialog.getHeight();
        }

        if (lastPosition.x == Integer.MAX_VALUE) {
            lastPosition.x = screenSize.width - tipDialog.getWidth();
        }
        if (lastPosition.y == Integer.MAX_VALUE) {
            lastPosition.y = screenSize.height-tipDialog.getHeight();
        }
        System.out.println("loaction: " + lastPosition.x + " : " + (lastPosition.y - 2));
        tipDialog.setLocation(lastPosition.x, lastPosition.y - 10);
        return tipDialog;
    }

    public static void showDesktopTip(TipDataEntity tipDataEntity) {
        TipDialog tipDialog = getTipDialog();
        tipDialog.setVisible(true);
        tipDialog.setEntity(tipDataEntity);
    }

    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            if (i == 1) {
                System.out.println("");
            }
            TipDialogFactory.showDesktopTip("title " + (i + 1), "content");
        }
    }
}
