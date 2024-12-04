
package MAIN;

/**
 *
 * @author Ever Chavez
 */
public class FrmInicio extends javax.swing.JFrame {

    
    public FrmInicio() {
        initComponents();
        this.setLocationRelativeTo(null);
        transparentButton();
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblComputadoras1 = new javax.swing.JLabel();
        lblComputadoras2 = new javax.swing.JLabel();
        lblPhone1 = new javax.swing.JLabel();
        lblComputadoras3 = new javax.swing.JLabel();
        lblComputadoras4 = new javax.swing.JLabel();
        lblPhone2 = new javax.swing.JLabel();
        btnComputer = new javax.swing.JButton();
        btnPrinter = new javax.swing.JButton();
        btnPhone = new javax.swing.JButton();
        btnFacture = new javax.swing.JButton();
        btnNewClient = new javax.swing.JButton();
        btnProductAdd = new javax.swing.JButton();
        btnReturn = new javax.swing.JButton();
        lblPhone3 = new javax.swing.JLabel();
        btnClose = new javax.swing.JButton();
        lblPhone5 = new javax.swing.JLabel();
        lblBackground = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(6);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblComputadoras1.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblComputadoras1.setForeground(new java.awt.Color(255, 255, 255));
        lblComputadoras1.setText("COMPUTADORAS");
        jPanel1.add(lblComputadoras1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 160, -1, -1));

        lblComputadoras2.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblComputadoras2.setForeground(new java.awt.Color(255, 255, 255));
        lblComputadoras2.setText("FACTURAS");
        jPanel1.add(lblComputadoras2, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 160, -1, -1));

        lblPhone1.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblPhone1.setForeground(new java.awt.Color(255, 255, 255));
        lblPhone1.setText("PHONE");
        jPanel1.add(lblPhone1, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 160, -1, -1));

        lblComputadoras3.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblComputadoras3.setForeground(new java.awt.Color(255, 255, 255));
        lblComputadoras3.setText("IMPRESORAS ");
        jPanel1.add(lblComputadoras3, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 160, -1, -1));

        lblComputadoras4.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblComputadoras4.setForeground(new java.awt.Color(255, 255, 255));
        lblComputadoras4.setText("AGREGAR PRODUCTO");
        jPanel1.add(lblComputadoras4, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 350, -1, -1));

        lblPhone2.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblPhone2.setForeground(new java.awt.Color(255, 255, 255));
        lblPhone2.setText("NUEVO CLIENTE");
        jPanel1.add(lblPhone2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 350, -1, -1));

        btnComputer.setIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\desktop90x90pp.png")); // NOI18N
        btnComputer.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnComputer.setPressedIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\desktop90x90pp.png")); // NOI18N
        btnComputer.setRolloverIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Internal Form Menu\\ordenador (1)90x90.png")); // NOI18N
        btnComputer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnComputerActionPerformed(evt);
            }
        });
        jPanel1.add(btnComputer, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 50, -1, -1));

        btnPrinter.setIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\printer90x90pp.png")); // NOI18N
        btnPrinter.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPrinter.setPressedIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\printer90x90pp.png")); // NOI18N
        btnPrinter.setRolloverIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\impresora90x90.png")); // NOI18N
        btnPrinter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrinterActionPerformed(evt);
            }
        });
        jPanel1.add(btnPrinter, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 50, -1, -1));

        btnPhone.setIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\phone90x90pp.png")); // NOI18N
        btnPhone.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPhone.setPressedIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\phone90x90pp.png")); // NOI18N
        btnPhone.setRolloverIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\telefono90x90.png")); // NOI18N
        btnPhone.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPhoneActionPerformed(evt);
            }
        });
        jPanel1.add(btnPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 50, -1, -1));

        btnFacture.setIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\Factura90x90pp.png")); // NOI18N
        btnFacture.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnFacture.setPressedIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\Factura90x90pp.png")); // NOI18N
        btnFacture.setRolloverIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\factura90x90.png")); // NOI18N
        btnFacture.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFactureActionPerformed(evt);
            }
        });
        jPanel1.add(btnFacture, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 50, -1, -1));

        btnNewClient.setIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\addclient90x90pp.png")); // NOI18N
        btnNewClient.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNewClient.setPressedIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\addclient 90x90.png")); // NOI18N
        btnNewClient.setRolloverIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\addclient 90x90.png")); // NOI18N
        btnNewClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewClientActionPerformed(evt);
            }
        });
        jPanel1.add(btnNewClient, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 250, -1, -1));

        btnProductAdd.setIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\agregarproducto90x90pp.png")); // NOI18N
        btnProductAdd.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnProductAdd.setPressedIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\agregarproducto90x90pp.png")); // NOI18N
        btnProductAdd.setRolloverIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\agregarproducto90x90png.png")); // NOI18N
        btnProductAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductAddActionPerformed(evt);
            }
        });
        jPanel1.add(btnProductAdd, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 250, -1, -1));

        btnReturn.setBackground(new java.awt.Color(255, 204, 51));
        btnReturn.setForeground(new java.awt.Color(255, 204, 51));
        btnReturn.setIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Internal Form Menu\\return_48.png")); // NOI18N
        btnReturn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReturn.setPressedIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Internal Form Menu\\return_48.png")); // NOI18N
        btnReturn.setRolloverIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Internal Form Menu\\Retur_72.png")); // NOI18N
        btnReturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReturnActionPerformed(evt);
            }
        });
        jPanel1.add(btnReturn, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 420, 80, 70));

        lblPhone3.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblPhone3.setForeground(new java.awt.Color(255, 255, 255));
        lblPhone3.setText("REGRESAR");
        jPanel1.add(lblPhone3, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 490, -1, -1));

        btnClose.setBackground(new java.awt.Color(255, 204, 51));
        btnClose.setForeground(new java.awt.Color(255, 204, 51));
        btnClose.setIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Internal Form Menu\\close_48.png")); // NOI18N
        btnClose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnClose.setPressedIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Internal Form Menu\\close_48.png")); // NOI18N
        btnClose.setRolloverIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Internal Form Menu\\close_72.png")); // NOI18N
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });
        jPanel1.add(btnClose, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 420, 80, 70));

        lblPhone5.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblPhone5.setForeground(new java.awt.Color(255, 255, 255));
        lblPhone5.setText("CERRAR");
        jPanel1.add(lblPhone5, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 490, -1, -1));

        lblBackground.setIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\cat orange (1)redimensionado.jpg")); // NOI18N
        jPanel1.add(lblBackground, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnReturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReturnActionPerformed
        FrmSetup open = new FrmSetup();
        
        open.setVisible(true);
            this.setVisible(false);
    }//GEN-LAST:event_btnReturnActionPerformed

    private void btnComputerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComputerActionPerformed
        FrmComputadoras open = new FrmComputadoras();
        
        open.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnComputerActionPerformed

    private void btnPrinterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrinterActionPerformed
        FrmImpresoras open = new FrmImpresoras();
        
        open.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnPrinterActionPerformed

    private void btnPhoneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPhoneActionPerformed
        FrmTelefonos open = new FrmTelefonos();
        
        open.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnPhoneActionPerformed

    private void btnFactureActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFactureActionPerformed
        FrmFactura open = new FrmFactura();
        
        open.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnFactureActionPerformed

    private void btnNewClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewClientActionPerformed
        FrmNewClient open = new FrmNewClient();
        
        open.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnNewClientActionPerformed

    private void btnProductAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductAddActionPerformed
        FrmAgregarProducto open = new FrmAgregarProducto();
        
        open.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnProductAddActionPerformed
    
    public void transparentButton(){
       
        btnProductAdd.setOpaque(false);
        btnProductAdd.setContentAreaFilled(false);
        btnProductAdd.setBorderPainted(false);
        
        btnComputer.setOpaque(false);
        btnComputer.setContentAreaFilled(false);
        btnComputer.setBorderPainted(false);
        
        btnNewClient.setOpaque(false);
        btnNewClient.setContentAreaFilled(false);
        btnNewClient.setBorderPainted(false);
        
        btnNewClient.setOpaque(false);
        btnNewClient.setContentAreaFilled(false);
        btnNewClient.setBorderPainted(false);
        
        btnPhone.setOpaque(false);
        btnPhone.setContentAreaFilled(false);
        btnPhone.setBorderPainted(false);
        
        btnFacture.setOpaque(false);
        btnFacture.setContentAreaFilled(false);
        btnFacture.setBorderPainted(false);
        
        btnPrinter.setOpaque(false);
        btnPrinter.setContentAreaFilled(false);
        btnPrinter.setBorderPainted(false);
                
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmInicio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmInicio().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnComputer;
    private javax.swing.JButton btnFacture;
    private javax.swing.JButton btnNewClient;
    private javax.swing.JButton btnPhone;
    private javax.swing.JButton btnPrinter;
    private javax.swing.JButton btnProductAdd;
    private javax.swing.JButton btnReturn;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblBackground;
    private javax.swing.JLabel lblComputadoras1;
    private javax.swing.JLabel lblComputadoras2;
    private javax.swing.JLabel lblComputadoras3;
    private javax.swing.JLabel lblComputadoras4;
    private javax.swing.JLabel lblPhone1;
    private javax.swing.JLabel lblPhone2;
    private javax.swing.JLabel lblPhone3;
    private javax.swing.JLabel lblPhone5;
    // End of variables declaration//GEN-END:variables

    
}
