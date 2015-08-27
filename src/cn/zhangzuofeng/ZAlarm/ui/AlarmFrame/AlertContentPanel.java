package cn.zhangzuofeng.ZAlarm.ui.AlarmFrame;

import java.util.List;

public class AlertContentPanel extends javax.swing.JPanel {

    private AlertContentEntity alertContentEntity = null;

    public AlertContentPanel(AlertContentEntity alertContentEntity) {
        initComponents();
        setEntity(alertContentEntity);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titleLabel = new javax.swing.JLabel();
        javax.swing.JScrollPane jScrollPane1 = new javax.swing.JScrollPane();
        contentEditorPane = new javax.swing.JEditorPane();

        titleLabel.setFont(new java.awt.Font("宋体", 1, 24)); // NOI18N
        titleLabel.setText("jLabel1");

        contentEditorPane.setEditable(false);
        contentEditorPane.setBorder(null);
        contentEditorPane.setContentType("text/html"); // NOI18N
        contentEditorPane.setEnabled(false);
        contentEditorPane.setOpaque(false);
        contentEditorPane.setRequestFocusEnabled(false);
        contentEditorPane.setVerifyInputWhenFocusTarget(false);
        jScrollPane1.setViewportView(contentEditorPane);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 354, Short.MAX_VALUE)
                .addGap(23, 23, 23))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JEditorPane contentEditorPane;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables

    private void setEntity(AlertContentEntity entity) {
        this.alertContentEntity = entity;
        titleLabel.setText(alertContentEntity.getTitle());
        String string = "<html>";
        List<String> section = alertContentEntity.getSection();
        for (String s : section) {
            string += "<h3>" + s + "</h3>";
        }
        string += "</html>";
        contentEditorPane.setText(string);
    }
}
