package cn.zhangzuofeng.ZAlarm.ui.AlarmFrame;

import cn.zhangzuofeng.ZAlarm.util.ZResource;
import java.awt.AWTException;
import java.awt.CardLayout;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.UIManager;

public class AlertCommonDialog extends javax.swing.JFrame {

    private List<AlertContentEntity> messages;
    private int currentInfoIndex;
    private Timer autoScrollMessageTimer;

    public AlertCommonDialog(GraphicsConfiguration gc) {
        super(gc);
        initComponents();
        loadAction();
    }

    public void setMessages(List<AlertContentEntity> messages) {
        this.messages = messages;
        if (messages != null && messages.size() > 0) {
            currentInfoIndex %= messages.size();
            for (int i = 0; i < messages.size(); i++) {
                AlertContentEntity alertContentEntity = messages.get(i);
                AlertContentPanel alertContentPanel = new AlertContentPanel(alertContentEntity);
                imagePanel1.add(alertContentPanel);
            }
        }
        autoScrollMessage();
    }

    public void setBackgroundImage(Image image) {
        imagePanel1.setBackgroudImage(image);
    }

    private void loadAction() {
        moveMouseToCenter();
        this.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                System.err.println("lost focus!");
                AlertCommonDialog.this.requestFocus();
                moveMouseToCenter();
            }
        });
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e) {
                moveMouseToCenter();
            }
        });
        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowDeactivated(WindowEvent e) {
                super.windowDeactivated(e);
                /*
                 GraphicsConfiguration graphicsConfiguration = AlertFrame.this.getGraphicsConfiguration();
                 Point location = AlertFrame.this.getLocation();
                 Dimension size = AlertFrame.this.getSize();
                 AlertFrame alertFrame = new AlertFrame(graphicsConfiguration);
                 alertFrame.setSize(size);
                 alertFrame.setLocation(location);
                 alertFrame.beginRestTimer(restTime);
                 AlertFrame.this.setVisible(false);
                 */
            }

        });

        this.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getID() == KeyEvent.KEY_PRESSED) {
                    if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
                        long currentTimeMillis = System.currentTimeMillis();
                        if (currentTimeMillis - lastPressedEsc < 2 * 1000) {
                            AlertCommonDialog.this.dispose();
                        } else {
                            lastPressedEsc = currentTimeMillis;
                        }
                    }
                }
            }

        });
    }
    private long lastPressedEsc = 0;

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jProgressBar1 = new javax.swing.JProgressBar();
        imagePanel1 = new cn.zhangzuofeng.ZAlarm.ui.ImagePanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setAlwaysOnTop(true);
        setBackground(java.awt.Color.lightGray);
        setUndecorated(true);
        setResizable(false);
        setType(java.awt.Window.Type.POPUP);

        jProgressBar1.setBorderPainted(false);
        jProgressBar1.setEnabled(false);
        jProgressBar1.setFocusable(false);
        jProgressBar1.setRequestFocusEnabled(false);
        jProgressBar1.setStringPainted(true);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        getContentPane().add(jPanel3, java.awt.BorderLayout.SOUTH);

        imagePanel1.setLayout(new java.awt.CardLayout());
        getContentPane().add(imagePanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    java.util.Timer restTimer;

    public void beginRestTimer(final int restTime) {
        restTimer = new java.util.Timer();
        final long startTimeMillis = System.currentTimeMillis();
        restTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                long currentTimeMillis = System.currentTimeMillis();
                double p = (currentTimeMillis - startTimeMillis) / (double) (restTime);
                jProgressBar1.setValue((int) (p * 100));
                jProgressBar1.setString((restTime - currentTimeMillis + startTimeMillis) / 1000 + "");
                if (p >= 1.001) {
                    AlertCommonDialog.this.dispose();
                }
            }
        }, 1, 100);
        this.setVisible(true);
    }

    @Override
    public void dispose() {
        super.dispose(); 
        if (restTimer != null) {
            restTimer.cancel();
        }
        if (autoScrollMessageTimer != null) {
            autoScrollMessageTimer.cancel();
        }
    }

    private void moveMouseToCenter() {
        try {
            Robot robot = new Robot();
            int centerX = (int) (this.getX() + this.getWidth() / 2.0);
            int centerY = (int) (this.getY() + this.getHeight() / 2.0);
            robot.mouseMove(centerX, centerY);
        } catch (AWTException e1) {
            e1.printStackTrace();
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private cn.zhangzuofeng.ZAlarm.ui.ImagePanel imagePanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JProgressBar jProgressBar1;
    // End of variables declaration//GEN-END:variables

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
        }
        GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] screenDevices = graphicsEnvironment.getScreenDevices();
        for (GraphicsDevice screenDevice : screenDevices) {
            Rectangle rectangle = screenDevice.getDefaultConfiguration().getBounds();
            int screenSizeWidth = rectangle.width;
            int screenSizeHeight = rectangle.height;
            double x = screenSizeWidth / 4.0 + rectangle.x;
            double y = screenSizeHeight / 4.0 + rectangle.y;
            AlertCommonDialog alertFrame = new AlertCommonDialog(screenDevice.getDefaultConfiguration());
            alertFrame.setBackgroundImage(ZResource.getAppImage());
            alertFrame.setSize(screenSizeWidth / 2, screenSizeHeight / 2);
            alertFrame.setLocation((int) x, (int) y);

            List<AlertContentEntity> messages = new ArrayList<AlertContentEntity>();

            AlertContentEntity alertContentEntity = new AlertContentEntity();
            alertContentEntity.setTitle("text1");
            //
            List<String> sectionContent = new ArrayList<String>();
            sectionContent.add("时间");
            sectionContent.add("今天是 2015年8月16日 14:23");
            alertContentEntity.setSection(sectionContent); 
            alertContentEntity.setTitle("dsfsuusjfsjk"); 
            messages.add(alertContentEntity);
            //
            sectionContent = new ArrayList<String>();
            sectionContent.add("Dtv 终端设备");
            sectionContent.add("实现了设备状态的远程监控、设备重启等功能.");
            sectionContent.add("向设备推送DTV软件升级的功能,由于DTV软件必须与特定的电视操作系统兼容,加之地区广电定制版本较多,软件升级是本系统的重点之一.");
            sectionContent.add("通过数字电视设备收集节目和频道的收视数据的一项业务.");

            alertContentEntity = new AlertContentEntity();
            alertContentEntity.setTitle("llls");
            alertContentEntity.setSection(sectionContent);
            messages.add(alertContentEntity);
            alertFrame.setMessages(messages);

            alertFrame.beginRestTimer(100000);

        }
    }

    public void autoScrollMessage() {
        CardLayout cardLayout = (CardLayout) imagePanel1.getLayout();
        cardLayout.next(imagePanel1);
        System.out.println("new page");
        ++currentInfoIndex;
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b); //To change body of generated methods, choose Tools | Templates.
        if (b) {
            if (autoScrollMessageTimer == null) {
                autoScrollMessageTimer = new Timer();
            }
            autoScrollMessageTimer.schedule(new TimerTask() {

                @Override
                public void run() {
                    autoScrollMessage();
                }
            }, 5000, 5000);
        } else {
            if (autoScrollMessageTimer != null) {
                autoScrollMessageTimer.cancel();
            }
        }
    }

}
