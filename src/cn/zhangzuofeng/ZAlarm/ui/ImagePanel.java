package cn.zhangzuofeng.ZAlarm.ui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import java.awt.Image;

/**
 * Created by aaron on 15-8-17.
 */
public class ImagePanel extends JPanel {

    private Image backgroudImage;

    public ImagePanel() {

    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D graphics2D = (Graphics2D) g;
        if (backgroudImage != null) {
            graphics2D.drawImage(backgroudImage, 0, 0, getWidth(), getHeight(), null);
        }
    }

    public Image getBackgroudImage() {
        return backgroudImage;
    }

    public void setBackgroudImage(Image backgroudImage) {
        this.backgroudImage = backgroudImage;
        repaint();
    }
}
