
package MAIN;


import DAO.Conexion;
import java.sql.Statement;
import javax.swing.JOptionPane;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;



/**
 *
 * @author Ever Chavez
 */
public class FrmSetup extends javax.swing.JFrame {
    
    Conexion conn = new Conexion("proyecto");
    
    public FrmSetup() {
        initComponents();
        this.setLocationRelativeTo(null);
        transparentButton();
       
        
    }
    
    
   public void VerificarUsuario() {
    Conexion conn = new Conexion("proyecto");
    PreparedStatement pst = null;
    ResultSet rs = null;

    try {
        // Obtener la conexión existente
        Connection con = conn.getConexion();

        // Consulta SQL para verificar el usuario y contraseña
        String consulta = "SELECT * FROM users WHERE user = ? AND password = ?";

        // Crear el PreparedStatement
        pst = con.prepareStatement(consulta);

        // Establecer los parámetros con los datos de los JTextField
        pst.setString(1, txtUsuario.getText().trim());
        pst.setString(2, pswrContrasenia.getText().trim());

        // Ejecutar la consulta
        rs = pst.executeQuery();

        // Comprobar si los datos coinciden
        if (rs.next()) {
            // Usuario y contraseña correctos
            JOptionPane.showMessageDialog(null, "¡Bienvenido!");

            // Abrir el formulario de menú
            FrmInicio menu = new FrmInicio();
            menu.setVisible(true);

            // Cerrar el formulario actual (login)
            this.dispose();
        } else {
            // Si no se encuentran coincidencias
            JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos.");
        }
    } catch (Exception e) {
        // Manejo de errores
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Ocurrió un error al verificar el usuario: " + e.getMessage());
    } finally {
        // Cerrar recursos utilizados (ResultSet y PreparedStatement)
        try {
            if (rs != null) rs.close();
            if (pst != null) pst.close();
            // No cerramos la conexión aquí porque es manejada en otra parte
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnIngresar1 = new javax.swing.JButton();
        pswrContrasenia = new javax.swing.JPasswordField();
        txtUsuario = new javax.swing.JTextField();
        btnHelp = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblSairTech = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("SETUP");

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Exotc350 Bd BT", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("INGRESAR");
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 430, -1, -1));

        btnIngresar1.setBackground(new java.awt.Color(0, 204, 0));
        btnIngresar1.setFont(new java.awt.Font("Exotc350 Bd BT", 0, 18)); // NOI18N
        btnIngresar1.setIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Imagen1.png")); // NOI18N
        btnIngresar1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnIngresar1.setPressedIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Imagen1.png")); // NOI18N
        btnIngresar1.setRolloverIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Imagen4.png")); // NOI18N
        btnIngresar1.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        btnIngresar1.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnIngresar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresar1ActionPerformed(evt);
            }
        });
        jPanel2.add(btnIngresar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 420, 240, 40));

        pswrContrasenia.setBackground(new java.awt.Color(102, 102, 102));
        pswrContrasenia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pswrContraseniaActionPerformed(evt);
            }
        });
        jPanel2.add(pswrContrasenia, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 330, 230, 30));

        txtUsuario.setBackground(new java.awt.Color(102, 102, 102));
        txtUsuario.setFont(new java.awt.Font("Exotc350 Bd BT", 0, 18)); // NOI18N
        txtUsuario.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(txtUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 270, 230, 30));

        btnHelp.setIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\help32x32pp.png")); // NOI18N
        btnHelp.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHelp.setPressedIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\help32x32pp.png")); // NOI18N
        btnHelp.setRolloverIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\help32x32.png")); // NOI18N
        btnHelp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHelpActionPerformed(evt);
            }
        });
        jPanel2.add(btnHelp, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 420, -1, -1));

        jLabel5.setFont(new java.awt.Font("Exotc350 Bd BT", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("TU MEJOR OPCION EN TECNOLOGIA");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 150, -1, -1));

        jLabel3.setFont(new java.awt.Font("Exotc350 Bd BT", 0, 48)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("SAIRTECH");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 100, -1, -1));

        lblSairTech.setFont(new java.awt.Font("Exotc350 Bd BT", 0, 18)); // NOI18N
        lblSairTech.setForeground(new java.awt.Color(255, 255, 255));
        lblSairTech.setIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\240x240 sairtech (1).png")); // NOI18N
        jPanel2.add(lblSairTech, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 90, 60, 60));

        jLabel6.setFont(new java.awt.Font("Exotc350 Bd BT", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("USUARIO");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 280, -1, -1));

        jLabel1.setFont(new java.awt.Font("Exotc350 Bd BT", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("CONTRASEÑA");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 330, 140, -1));

        jLabel7.setIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\cat orange (1)redimensionado.jpg")); // NOI18N
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 940, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnIngresar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresar1ActionPerformed
        VerificarUsuario();
    }//GEN-LAST:event_btnIngresar1ActionPerformed

    private void btnHelpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHelpActionPerformed
        JOptionPane.showMessageDialog(null,"Debe ingresar la contraseña otorgada al usuario al momento de la instalación del sistema.  ");
        JOptionPane.showMessageDialog(null,"En caso de tener algun problema, comuniquese al +504 3368-0903");
    }//GEN-LAST:event_btnHelpActionPerformed

    private void pswrContraseniaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pswrContraseniaActionPerformed
                                                 
    btnIngresar1.doClick(); // Simula el clic en el botón
    
    }//GEN-LAST:event_pswrContraseniaActionPerformed

    
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
            java.util.logging.Logger.getLogger(FrmSetup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmSetup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmSetup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmSetup.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FrmSetup().setVisible(true);
            }
        });
    
    
        
        
    

    }
    public void transparentButton(){
    
        btnHelp.setOpaque(false);
        btnHelp.setContentAreaFilled(false);
        btnHelp.setBorderPainted(false);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHelp;
    private javax.swing.JButton btnIngresar1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblSairTech;
    private javax.swing.JPasswordField pswrContrasenia;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
