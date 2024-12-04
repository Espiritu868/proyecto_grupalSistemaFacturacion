
package MAIN;

import DAO.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import static java.util.stream.Collectors.toList;
import javax.swing.JOptionPane;

/**
 *
 * @author Ever Chavez
 */
public class FrmAgregarProducto extends javax.swing.JFrame {

    public void InsertNewProduct(){
    Conexion conn = new Conexion("proyecto_grupal");
    Connection con = null;
    PreparedStatement ps = null;

    try {

        String productName = txtProductName.getText();
        int priceCost = Integer.parseInt(txtPriceCost.getText());
        int priceShop = Integer.parseInt(txtPriceShop.getText());
        int quantityInStock = Integer.parseInt(txtQuantityInStock.getText());
        

        con = conn.getConexion();
        String sql = "INSERT INTO bdaddnewproduct (ProductName, PriceCost, PriceShop, QuantityInStock) VALUES (?, ?, ?, ?)";
        ps = con.prepareStatement(sql);

        ps.setString(1, productName);   
        ps.setInt(2, priceCost);       
        ps.setInt(3, priceShop);
        ps.setInt(4, quantityInStock);
        
        
        int rowsInserted = ps.executeUpdate();

        if (rowsInserted > 0) {
            JOptionPane.showMessageDialog(this, "¡Datos insertados exitosamente!");
            toList(); 
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo insertar la información.");
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "La Edad debe ser un número válido.");
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Ocurrió un error al insertar los datos.");
    } finally {
        try {
            if (ps != null) ps.close();
            if (con != null) con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        }
    }
    
    public FrmAgregarProducto() {
        initComponents();
        this.setLocationRelativeTo(null);
        transparentButton();
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        btnSearch = new javax.swing.JButton();
        lblNombreProducto = new javax.swing.JLabel();
        txtNombreProducto = new javax.swing.JTextField();
        lblCodigo1 = new javax.swing.JLabel();
        txtCode = new javax.swing.JTextField();
        lblCodigo4 = new javax.swing.JLabel();
        txtPriceCost = new javax.swing.JTextField();
        txtQuantityInStock = new javax.swing.JTextField();
        lblCodigo2 = new javax.swing.JLabel();
        txtPriceShop = new javax.swing.JTextField();
        lblCodigo = new javax.swing.JLabel();
        lblNombreProducto1 = new javax.swing.JLabel();
        txtProductName = new javax.swing.JTextField();
        lblPhone7 = new javax.swing.JLabel();
        btnReturn1 = new javax.swing.JButton();
        lblPhone4 = new javax.swing.JLabel();
        btnClose1 = new javax.swing.JButton();
        lblPhone6 = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        lblBackground2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnSearch.setIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\search32x32.png")); // NOI18N
        btnSearch.setPressedIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\search32x32.png")); // NOI18N
        btnSearch.setRolloverIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\search48x48.png")); // NOI18N
        jPanel2.add(btnSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 150, 70, 50));

        lblNombreProducto.setFont(new java.awt.Font("Exotc350 Bd BT", 0, 18)); // NOI18N
        lblNombreProducto.setForeground(new java.awt.Color(255, 255, 255));
        lblNombreProducto.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNombreProducto.setText("Buscar Producto");
        jPanel2.add(lblNombreProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 160, 230, -1));

        txtNombreProducto.setEditable(false);
        txtNombreProducto.setFont(new java.awt.Font("Exotc350 Bd BT", 0, 14)); // NOI18N
        jPanel2.add(txtNombreProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 160, 450, -1));

        lblCodigo1.setFont(new java.awt.Font("Exotc350 Bd BT", 0, 18)); // NOI18N
        lblCodigo1.setForeground(new java.awt.Color(255, 255, 255));
        lblCodigo1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCodigo1.setText("Codigo");
        jPanel2.add(lblCodigo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 240, 220, -1));

        txtCode.setFont(new java.awt.Font("Exotc350 Bd BT", 0, 14)); // NOI18N
        jPanel2.add(txtCode, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 240, 130, -1));

        lblCodigo4.setFont(new java.awt.Font("Exotc350 Bd BT", 0, 18)); // NOI18N
        lblCodigo4.setForeground(new java.awt.Color(255, 255, 255));
        lblCodigo4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCodigo4.setText("Precio Costo");
        jPanel2.add(lblCodigo4, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 240, 220, -1));

        txtPriceCost.setFont(new java.awt.Font("Exotc350 Bd BT", 0, 14)); // NOI18N
        jPanel2.add(txtPriceCost, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 240, 130, -1));

        txtQuantityInStock.setFont(new java.awt.Font("Exotc350 Bd BT", 0, 14)); // NOI18N
        jPanel2.add(txtQuantityInStock, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 280, 130, -1));

        lblCodigo2.setFont(new java.awt.Font("Exotc350 Bd BT", 0, 18)); // NOI18N
        lblCodigo2.setForeground(new java.awt.Color(255, 255, 255));
        lblCodigo2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCodigo2.setText("Cantidad en Stock");
        jPanel2.add(lblCodigo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 280, 220, -1));

        txtPriceShop.setFont(new java.awt.Font("Exotc350 Bd BT", 0, 14)); // NOI18N
        jPanel2.add(txtPriceShop, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 280, 130, -1));

        lblCodigo.setFont(new java.awt.Font("Exotc350 Bd BT", 0, 18)); // NOI18N
        lblCodigo.setForeground(new java.awt.Color(255, 255, 255));
        lblCodigo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCodigo.setText("Precio Venta");
        jPanel2.add(lblCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 280, 220, -1));

        lblNombreProducto1.setFont(new java.awt.Font("Exotc350 Bd BT", 0, 18)); // NOI18N
        lblNombreProducto1.setForeground(new java.awt.Color(255, 255, 255));
        lblNombreProducto1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNombreProducto1.setText(" Nombre  Producto");
        jPanel2.add(lblNombreProducto1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 200, 230, -1));

        txtProductName.setFont(new java.awt.Font("Exotc350 Bd BT", 0, 14)); // NOI18N
        jPanel2.add(txtProductName, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 200, 450, -1));

        lblPhone7.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblPhone7.setForeground(new java.awt.Color(255, 255, 255));
        lblPhone7.setText("GUARDAR");
        jPanel2.add(lblPhone7, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 500, -1, -1));

        btnReturn1.setBackground(new java.awt.Color(255, 204, 51));
        btnReturn1.setForeground(new java.awt.Color(255, 204, 51));
        btnReturn1.setIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Internal Form Menu\\return_48.png")); // NOI18N
        btnReturn1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReturn1.setPressedIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Internal Form Menu\\return_48.png")); // NOI18N
        btnReturn1.setRolloverIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Internal Form Menu\\Retur_72.png")); // NOI18N
        btnReturn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReturn1ActionPerformed(evt);
            }
        });
        jPanel2.add(btnReturn1, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 430, 80, 70));

        lblPhone4.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblPhone4.setForeground(new java.awt.Color(255, 255, 255));
        lblPhone4.setText("REGRESAR");
        jPanel2.add(lblPhone4, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 500, -1, -1));

        btnClose1.setBackground(new java.awt.Color(255, 204, 51));
        btnClose1.setForeground(new java.awt.Color(255, 204, 51));
        btnClose1.setIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Internal Form Menu\\close_48.png")); // NOI18N
        btnClose1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnClose1.setPressedIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Internal Form Menu\\close_48.png")); // NOI18N
        btnClose1.setRolloverIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Internal Form Menu\\close_72.png")); // NOI18N
        btnClose1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClose1ActionPerformed(evt);
            }
        });
        jPanel2.add(btnClose1, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 430, 80, 70));

        lblPhone6.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblPhone6.setForeground(new java.awt.Color(255, 255, 255));
        lblPhone6.setText("CERRAR");
        jPanel2.add(lblPhone6, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 500, -1, -1));

        btnSave.setBackground(new java.awt.Color(255, 204, 51));
        btnSave.setForeground(new java.awt.Color(255, 204, 51));
        btnSave.setIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\SAVE48X48.png")); // NOI18N
        btnSave.setPressedIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\SAVE48X48.png")); // NOI18N
        btnSave.setRolloverIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\save64x64.png")); // NOI18N
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        jPanel2.add(btnSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 430, 80, 70));

        lblBackground2.setIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\cat orange (1)redimensionado.jpg")); // NOI18N
        jPanel2.add(lblBackground2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnReturn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReturn1ActionPerformed
        FrmInicio open = new FrmInicio();

        open.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnReturn1ActionPerformed

    private void btnClose1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClose1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnClose1ActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        InsertNewProduct();
    }//GEN-LAST:event_btnSaveActionPerformed

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
            java.util.logging.Logger.getLogger(FrmAgregarProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmAgregarProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmAgregarProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmAgregarProducto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmAgregarProducto().setVisible(true);
            }
        });
    
    
    
        }
    public void transparentButton(){
    
        btnSearch.setOpaque(false);
        btnSearch.setContentAreaFilled(false);
        btnSearch.setBorderPainted(false);
        
        
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose1;
    private javax.swing.JButton btnReturn1;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblBackground2;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblCodigo1;
    private javax.swing.JLabel lblCodigo2;
    private javax.swing.JLabel lblCodigo4;
    private javax.swing.JLabel lblNombreProducto;
    private javax.swing.JLabel lblNombreProducto1;
    private javax.swing.JLabel lblPhone4;
    private javax.swing.JLabel lblPhone6;
    private javax.swing.JLabel lblPhone7;
    private javax.swing.JTextField txtCode;
    private javax.swing.JTextField txtNombreProducto;
    private javax.swing.JTextField txtPriceCost;
    private javax.swing.JTextField txtPriceShop;
    private javax.swing.JTextField txtProductName;
    private javax.swing.JTextField txtQuantityInStock;
    // End of variables declaration//GEN-END:variables
}
