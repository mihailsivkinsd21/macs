/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import snmp.SNMPGetException;

/**
 *
 * @author Praktikant
 */
public class MainInterface extends javax.swing.JFrame {
    private MainInterfaceWrapper wrapper;
    private IridaBindingGroup ibg;
    /**
     * Creates new form NewMainJFrame
     */
    public MainInterface() throws SNMPGetException, Exception {
        initComponents();
        
        wrapper = new MainInterfaceWrapper();
        ibg = new IridaBindingGroup(wrapper);
        
        ibg.add("ip", fieldIp);
        ibg.add("community", fieldCommunity);
        
        ArrayList<BindingColumn> listTableSwitches = new ArrayList<BindingColumn>();
        listTableSwitches.add(new BindingColumn("ip"));
        listTableSwitches.add(new BindingColumn("model"));
        listTableSwitches.add(new BindingColumn("gatewayMac"));
        //listTableSwitches.add(new BindingColumn("status"));
        ibg.add("curSwitch", "switches", listTableSwitches, tableSwitches);
        
        
        
        ArrayList<BindingColumn> listTableVlans = new ArrayList<BindingColumn>();
        listTableVlans.add(new BindingColumn("vlanNbr"));
        listTableVlans.add(new BindingColumn("gatewayMacCheck"));
        listTableVlans.add(new BindingColumn("macCount"));
        ibg.add("vlan", "curSwitch.vlans", listTableVlans, tableVlans);

        ArrayList<BindingColumn> listTableMacs = new ArrayList<BindingColumn>();
        listTableMacs.add(new BindingColumn("address"));
        ibg.add("mac", "vlan.macs", listTableMacs, tableMacs);
        
        
        
        
        ArrayList<BindingColumn> listTablePorts = new ArrayList<BindingColumn>();
        listTablePorts.add(new BindingColumn("portNbr"));
        listTablePorts.add(new BindingColumn("portName"));
        listTablePorts.add(new BindingColumn("vlanSize"));
        listTablePorts.add(new BindingColumn("vlansString"));
        ibg.add("port", "curSwitch.ports", listTablePorts, tablePorts);
        
        ArrayList<BindingColumn> listTablePortVlans = new ArrayList<BindingColumn>();
        listTablePortVlans.add(new BindingColumn("vlanNbr"));
        ibg.add("vlanOnPort", "port.vlans", listTablePortVlans, tablePortVlans);
        
        

        ArrayList<BindingColumn> listTablePortToVlan = new ArrayList<BindingColumn>();
        listTablePortToVlan.add(new BindingColumn("portNbr"));
        listTablePortToVlan.add(new BindingColumn("portName"));
        listTablePortToVlan.add(new BindingColumn("vlanNbr"));
        listTablePortToVlan.add(new BindingColumn("macsSize"));
        ibg.add("portToVlan", "curSwitch.vlanToPortList", listTablePortToVlan, tablePortToVlan);
        //ibg.add("portToVlan", "vlanToPortList", listTablePortToVlan, tablePortToVlan);
        
        ArrayList<BindingColumn> listTableMacsPort = new ArrayList<BindingColumn>();
        listTableMacsPort.add(new BindingColumn("address"));
        ibg.add("portMac", "portToVlan.macs", listTableMacsPort, tableMacsPort);
        
        
        
        ArrayList<BindingColumn> listTableSwitchConnections = new ArrayList<BindingColumn>();
        listTableSwitchConnections.add(new BindingColumn("switchUp.ip"));
        listTableSwitchConnections.add(new BindingColumn("upPort.portNbr"));
        listTableSwitchConnections.add(new BindingColumn("switchDown.ip"));
        listTableSwitchConnections.add(new BindingColumn("downPort.portNbr"));
        listTableSwitchConnections.add(new BindingColumn("vlanErrorsString"));
        ibg.add("curSwitchConnection", "switchConnections", listTableSwitchConnections, tableSwitchConnections);
        
        ArrayList<BindingColumn> listTableUplinkDownlink = new ArrayList<BindingColumn>();
        listTableUplinkDownlink.add(new BindingColumn("portNbr"));
        listTableUplinkDownlink.add(new BindingColumn("portName"));
        listTableUplinkDownlink.add(new BindingColumn("isUplink"));
        listTableUplinkDownlink.add(new BindingColumn("problemVlans"));
        ibg.add("portUplinkTable", "curSwitch.ports", listTableUplinkDownlink, tableUplinkDownlink);
        
        ibg.add("curSwitch.uplinkHasDownlinkVlans", fieldUplinkStatus);
       
        ibg.bind();

    }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tableVlans = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tableMacs = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablePorts = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        tablePortVlans = new javax.swing.JTable();
        jScrollPane5 = new javax.swing.JScrollPane();
        tableSwitches = new javax.swing.JTable();
        jScrollPane6 = new javax.swing.JScrollPane();
        tablePortToVlan = new javax.swing.JTable();
        jScrollPane7 = new javax.swing.JScrollPane();
        tableMacsPort = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        checkBtn = new javax.swing.JToggleButton();
        fieldIp = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        fieldCommunity = new javax.swing.JTextField();
        refreshSwitch = new javax.swing.JButton();
        jScrollPane9 = new javax.swing.JScrollPane();
        tableSwitchConnections = new javax.swing.JTable();
        jScrollPane8 = new javax.swing.JScrollPane();
        tableUplinkDownlink = new javax.swing.JTable();
        jScrollPane10 = new javax.swing.JScrollPane();
        fieldUplinkStatus = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        tableVlans.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tableVlans);

        tableMacs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(tableMacs);

        tablePorts.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(tablePorts);

        tablePortVlans.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane4.setViewportView(tablePortVlans);

        tableSwitches.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane5.setViewportView(tableSwitches);

        tablePortToVlan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane6.setViewportView(tablePortToVlan);

        tableMacsPort.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane7.setViewportView(tableMacsPort);

        checkBtn.setText("check");
        checkBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkBtnActionPerformed(evt);
            }
        });

        fieldIp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldIpActionPerformed(evt);
            }
        });

        jLabel1.setText("IP");

        jLabel2.setText("Community");

        refreshSwitch.setText("refresh");
        refreshSwitch.setEnabled(false);
        refreshSwitch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshSwitchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(refreshSwitch, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fieldIp, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(checkBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fieldCommunity, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fieldIp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(fieldCommunity, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(46, 46, 46)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(refreshSwitch)
                    .addComponent(checkBtn))
                .addContainerGap())
        );

        tableSwitchConnections.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane9.setViewportView(tableSwitchConnections);

        tableUplinkDownlink.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane8.setViewportView(tableUplinkDownlink);

        jScrollPane10.setViewportView(fieldUplinkStatus);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane9)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
                            .addComponent(jScrollPane3)
                            .addComponent(jScrollPane1)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
                            .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 399, Short.MAX_VALUE)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jScrollPane8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 103, Short.MAX_VALUE)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane10, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void checkBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkBtnActionPerformed
       try {
          wrapper.checkSwitch();
          refreshSwitch.setEnabled(true);
       } catch (Exception ex) {
          Utility.showTimeoutError();
          throw new RuntimeException(ex);
       }
    
    }//GEN-LAST:event_checkBtnActionPerformed

    private void fieldIpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fieldIpActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fieldIpActionPerformed

    private void refreshSwitchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshSwitchActionPerformed
        // TODO add your handling code here:
        try {
            wrapper.refreshCurSwitch();
        } catch(Exception ex) {
            Utility.showTimeoutError();
            throw new RuntimeException(ex);
        }
    }//GEN-LAST:event_refreshSwitchActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new MainInterface().setVisible(true);
                } catch (Exception ex) {
                    Logger.getLogger(MainInterface.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton checkBtn;
    private javax.swing.JTextField fieldCommunity;
    private javax.swing.JTextField fieldIp;
    private javax.swing.JTextField fieldUplinkStatus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JButton refreshSwitch;
    private javax.swing.JTable tableMacs;
    private javax.swing.JTable tableMacsPort;
    private javax.swing.JTable tablePortToVlan;
    private javax.swing.JTable tablePortVlans;
    private javax.swing.JTable tablePorts;
    private javax.swing.JTable tableSwitchConnections;
    private javax.swing.JTable tableSwitches;
    private javax.swing.JTable tableUplinkDownlink;
    private javax.swing.JTable tableVlans;
    // End of variables declaration//GEN-END:variables
}
