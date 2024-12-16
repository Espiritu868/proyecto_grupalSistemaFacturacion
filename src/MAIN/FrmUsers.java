
package MAIN;

import DAO.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static java.util.stream.Collectors.toList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author chave
 */
public class FrmUsers extends javax.swing.JFrame {

    
    public FrmUsers() {
        initComponents();
        jPanel3.setVisible(false);
        btnActualizar.setEnabled(false);
        btnSave.setEnabled(false);
        this.setLocationRelativeTo(null);
    }
    
    DefaultTableModel modelo;
        
    Conexion conn = new Conexion("proyecto");
    String mensaje = "";

    public void mostrarInicio(){
                    FrmInicio open = new FrmInicio();

                    open.setVisible(true);
                    this.setVisible(false);
            }

    public void InsertNewUser() {
        Connection con = null;
        PreparedStatement ps = null;

            try {
                
                String user = txtUsuario.getText().trim();
                String password = pswrPassword.getText().trim();

                
                con = conn.getConexion();
                String sql = "INSERT INTO users (user, password) VALUES (?, ?)";
                ps = con.prepareStatement(sql);

                
                ps.setString(1, user);
                ps.setString(2, password);

                
                int rowsInserted = ps.executeUpdate();

                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "¡Datos insertados exitosamente!");
                    toList(); 
                    mostrarInicio();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo insertar la información.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Ocurrió un error al insertar los datos.", "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                try {
                    if (ps != null) ps.close();
                    if (con != null) con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }    
    public void buscarUsuario(){

            Object dataClient[] = new Object[3];
            modelo = (DefaultTableModel) tbUsuario.getModel();
            Connection con = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            Statement st = null;
            modelo.setRowCount(0);

            try {
                con = conn.getConexion();
                st = con.createStatement();
                rs = st.executeQuery("select * from users where user like '%" + txtBuscar.getText() + "%'");

                while(rs.next()){
                    dataClient[0] = rs.getString("id");
                    dataClient[1] = rs.getString("user");
                    dataClient[2] = rs.getString("password");

                    modelo.addRow(dataClient);

                    tbUsuario.setModel(modelo);

                }
            } catch (Exception e) {
                System.out.println("Error en la consulta. problema en BuscarCliente");
            }

        }
    
        public void mostrarUsuario(String Id){


            Connection con = null;

            ResultSet rs = null;
            Statement st = null;


            try {
                con = conn.getConexion();
                st = con.createStatement();
                rs = st.executeQuery("select * from users where id = '"+ Id +"'");

                    while(rs.next()){
                        txtId.setText(rs.getString("id"));
                        txtUsuario.setText(rs.getString("user"));
                        pswrPassword.setText(rs.getString("password"));
                        
                    }
                } catch (Exception e) {
                    System.out.println("Error en la consulta.");
                }

            } 
        public void ModificarUsuario() {
            Connection con = null;
            PreparedStatement ps = null;

            try {
                // Capturar los valores de los campos de texto
                String user = txtUsuario.getText().trim();       
                String password = pswrPassword.getText().trim();
                String idText = txtId.getText().trim();  

                // Validar los campos antes de proceder
                if (idText.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El campo ID no puede estar vacío.");
                    return;
                }
                if (user.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El campo Usuario no puede estar vacío.");
                    return;
                }
                if (password.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El campo Contraseña no puede estar vacío.");
                    return;
                }

                // Validar que el ID sea numérico
                int idUsuario;
                try {
                    idUsuario = Integer.parseInt(idText);  
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "El campo ID debe contener un número válido.");
                    return;
                }

                // Obtener conexión con la base de datos
                con = conn.getConexion();

                // Consulta SQL para actualizar el usuario
                String sql = "UPDATE users SET user = ?, password = ? WHERE id = ?";
                ps = con.prepareStatement(sql);

                // Asignar valores a los parámetros de la consulta
                ps.setString(1, user);
                ps.setString(2, password);
                ps.setInt(3, idUsuario);

                // Ejecutar la actualización
                int rowsUpdated = ps.executeUpdate();

                // Verificar si la actualización fue exitosa
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(this, "¡Datos actualizados exitosamente!");
                    toList();  // Refrescar la lista de usuarios
                    mostrarInicio();  // Redirigir al inicio o realizar otra acción necesaria
                } else {
                    JOptionPane.showMessageDialog(this, "No se encontró ningún usuario con el ID proporcionado.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Ocurrió un error al actualizar los datos: " + e.getMessage());
            } finally {
                // Liberar recursos
                try {
                    if (ps != null) ps.close();
                    if (con != null) con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        public void deleteUser(){
    
        Connection con = null;
        PreparedStatement ps = null;

        try {
         
            int selectedRow = tbUsuario.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Por favor, selecciona un producto para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return; 
            }

       
            int idClient = Integer.parseInt(tbUsuario.getValueAt(selectedRow, 0).toString());

   
            int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que deseas eliminar este cliente?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) {
                return; 
            }

            con = conn.getConexion();

            String sql = "DELETE FROM users WHERE id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, idClient);

            int rowsDeleted = ps.executeUpdate();

            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(this, "¡Producto eliminado exitosamente!");
                
                toList();
                mostrarInicio();
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró el cliente a eliminar.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ocurrió un error al eliminar el cliente: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El ID del cliente debe ser un número válido.");
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbUsuario = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        pswrPassword = new javax.swing.JPasswordField();
        btnReturn1 = new javax.swing.JButton();
        lblPhone4 = new javax.swing.JLabel();
        btnClose1 = new javax.swing.JButton();
        lblPhone6 = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        lblPhone7 = new javax.swing.JLabel();
        btnActualizar = new javax.swing.JButton();
        lblPhone8 = new javax.swing.JLabel();
        btnEliminar = new javax.swing.JButton();
        lblPhone5 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        background = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("USUARIOS");

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tbUsuario.setBackground(new java.awt.Color(255, 255, 255));
        tbUsuario.setForeground(new java.awt.Color(0, 0, 0));
        tbUsuario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "USUARIO"
            }
        ));
        tbUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbUsuarioMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbUsuario);
        if (tbUsuario.getColumnModel().getColumnCount() > 0) {
            tbUsuario.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 207, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 3, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 300, 210, -1));

        jPanel2.setBackground(new java.awt.Color(255, 102, 0));
        jPanel2.setForeground(new java.awt.Color(255, 102, 0));

        jLabel4.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("RECUERDA JAMAS COMPARTIR ESTOS DATOS");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 140, 430, 30));

        jLabel2.setFont(new java.awt.Font("Times New Roman", 1, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("USUARIOS");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 80, -1, -1));

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("CONTRASEÑA");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 350, -1, -1));

        jLabel3.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("BUSCAR");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 260, -1, -1));

        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarKeyPressed(evt);
            }
        });
        jPanel1.add(txtBuscar, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 260, 160, 30));

        jLabel5.setFont(new java.awt.Font("Dialog", 0, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("USUARIO");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 300, -1, -1));

        txtUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtUsuarioKeyPressed(evt);
            }
        });
        jPanel1.add(txtUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 300, 160, 30));
        jPanel1.add(pswrPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 350, 160, 30));

        btnReturn1.setBackground(new java.awt.Color(255, 204, 51));
        btnReturn1.setForeground(new java.awt.Color(255, 204, 51));
        btnReturn1.setIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\return48x48pp.png")); // NOI18N
        btnReturn1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReturn1.setPressedIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\return48x48pp.png")); // NOI18N
        btnReturn1.setRolloverIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\return48x48.png")); // NOI18N
        btnReturn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReturn1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnReturn1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 430, 80, 70));

        lblPhone4.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblPhone4.setForeground(new java.awt.Color(255, 255, 255));
        lblPhone4.setText("REGRESAR");
        jPanel1.add(lblPhone4, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 500, -1, -1));

        btnClose1.setBackground(new java.awt.Color(255, 204, 51));
        btnClose1.setForeground(new java.awt.Color(255, 204, 51));
        btnClose1.setIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\cerrar 50x50pp.png")); // NOI18N
        btnClose1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnClose1.setPressedIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\cerrar 50x50pp.png")); // NOI18N
        btnClose1.setRolloverIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\cerrar 50x50.png")); // NOI18N
        btnClose1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClose1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnClose1, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 430, 80, 70));

        lblPhone6.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblPhone6.setForeground(new java.awt.Color(255, 255, 255));
        lblPhone6.setText("CERRAR");
        jPanel1.add(lblPhone6, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 500, -1, -1));

        btnSave.setBackground(new java.awt.Color(255, 204, 51));
        btnSave.setForeground(new java.awt.Color(255, 204, 51));
        btnSave.setIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\SAVE48X48pp.png")); // NOI18N
        btnSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSave.setPressedIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\SAVE48X48pp.png")); // NOI18N
        btnSave.setRolloverIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\SAVE48X48.png")); // NOI18N
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        jPanel1.add(btnSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 430, 80, 70));

        lblPhone7.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblPhone7.setForeground(new java.awt.Color(255, 255, 255));
        lblPhone7.setText("GUARDAR");
        jPanel1.add(lblPhone7, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 500, -1, -1));

        btnActualizar.setBackground(java.awt.Color.orange);
        btnActualizar.setIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\refresh48x48pp.png")); // NOI18N
        btnActualizar.setToolTipText("");
        btnActualizar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnActualizar.setPressedIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\refresh48x48pp.png")); // NOI18N
        btnActualizar.setRolloverIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\refresh48x48.png")); // NOI18N
        btnActualizar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnActualizarMouseClicked(evt);
            }
        });
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });
        jPanel1.add(btnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 430, 80, 70));

        lblPhone8.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblPhone8.setForeground(new java.awt.Color(255, 255, 255));
        lblPhone8.setText("ACTUALIZAR");
        jPanel1.add(lblPhone8, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 500, -1, -1));

        btnEliminar.setBackground(java.awt.Color.orange);
        btnEliminar.setIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\delete48x48pp.png")); // NOI18N
        btnEliminar.setToolTipText("");
        btnEliminar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEliminar.setPressedIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\delete48x48pp.png")); // NOI18N
        btnEliminar.setRolloverIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\delete48x48.png")); // NOI18N
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });
        jPanel1.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 430, 80, 70));

        lblPhone5.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblPhone5.setForeground(new java.awt.Color(255, 255, 255));
        lblPhone5.setText("ELIMINAR");
        jPanel1.add(lblPhone5, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 500, -1, 20));
        jPanel1.add(txtId, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 260, 60, -1));

        background.setIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\cat orange (1)980.jpg")); // NOI18N
        jPanel1.add(background, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -20, -1, 570));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 536, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbUsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbUsuarioMouseClicked
        int fila =  tbUsuario.getSelectedRow();
        String Id = tbUsuario.getValueAt(fila, 0).toString();
        mostrarUsuario(Id);
        
        jPanel3.setVisible(false);
        btnSave.setEnabled(false);
        btnActualizar.setEnabled(true);
    }//GEN-LAST:event_tbUsuarioMouseClicked

    private void btnReturn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReturn1ActionPerformed
        mostrarInicio();
    }//GEN-LAST:event_btnReturn1ActionPerformed

    private void btnClose1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClose1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnClose1ActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        InsertNewUser();
        
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnActualizarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnActualizarMouseClicked
        btnSave.setVisible(false);
    }//GEN-LAST:event_btnActualizarMouseClicked

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        ModificarUsuario();
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        deleteUser();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void txtBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyPressed
        buscarUsuario();
        jPanel3.setVisible(true);
    }//GEN-LAST:event_txtBuscarKeyPressed

    private void txtUsuarioKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsuarioKeyPressed
        btnSave.setEnabled(true);
    }//GEN-LAST:event_txtUsuarioKeyPressed

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
            java.util.logging.Logger.getLogger(FrmUsers.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmUsers.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmUsers.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmUsers.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmUsers().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel background;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnClose1;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnReturn1;
    private javax.swing.JButton btnSave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblPhone4;
    private javax.swing.JLabel lblPhone5;
    private javax.swing.JLabel lblPhone6;
    private javax.swing.JLabel lblPhone7;
    private javax.swing.JLabel lblPhone8;
    private javax.swing.JPasswordField pswrPassword;
    private javax.swing.JTable tbUsuario;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
