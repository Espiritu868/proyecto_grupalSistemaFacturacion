
package MAIN;

import java.sql.*;
import DAO.Conexion;
import java.awt.Color;
import java.util.logging.Level;
import java.util.logging.Logger;
import static java.util.stream.Collectors.toList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ever Chavez
 */
public class FrmNewClient extends javax.swing.JFrame {
    
    DefaultTableModel modelo;
        
    Conexion conn = new Conexion("proyecto");

    String mensaje; 
 
    
private void toList(){
    
    Object dataClient[] = new Object[4];
    modelo = (DefaultTableModel) dtClientes.getModel();
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    Statement st = null;
    modelo.setRowCount(0);
    
    try {
        con = conn.getConexion();
        st = con.createStatement();
        rs = st.executeQuery("select * from clientes where clientName like '%" + txtShowForName.getText() + "%'");
        
        while(rs.next()){
            dataClient[0] = rs.getString("id");
            dataClient[1] = rs.getString("clientName");
            dataClient[2] = rs.getString("idClient");
            dataClient[3] = rs.getString("phoneClient");

            
            modelo.addRow(dataClient);
            
            dtClientes.setModel(modelo);
            
        }
    } catch (Exception e) {
        System.out.println("Error en la consulta.");
    }
        
}   

public void mostrarClienteNombre(String Id){
    
   
    Connection con = null;
 
    ResultSet rs = null;
    Statement st = null;
  
    
    try {
        con = conn.getConexion();
        st = con.createStatement();
        rs = st.executeQuery("select * from clientes where id = '"+ Id +"'");
        
        while(rs.next()){
            txtId.setText(rs.getString("id"));
            txtName.setText(rs.getString("clientName"));
            txtPhone.setText(rs.getString("phoneClient"));
            txtDireccion.setText(rs.getString("adressClient"));
            txtRTN.setText(rs.getString("rtnClient"));
            txtIdenty.setText(rs.getString("idClient")); 
            cboType.setSelectedItem(rs.getString("typeClient"));

        }
    } catch (Exception e) {
        System.out.println("Error en la consulta.");
    }
        
}   

    

public void InsertNewClient() {
    Connection con = null;
    PreparedStatement ps = null;

    try {
        // Capturar los valores de los campos de texto
        String name = txtName.getText().trim();
        String phone = txtPhone.getText().trim();
        String adress = txtDireccion.getText().trim();
        String rtn = txtRTN.getText().trim(); // Para idClient
        String identy = txtIdenty.getText().trim(); // Para typeClient
        String type = String.valueOf(cboType.getSelectedItem()).trim();

        // Validar el tamaño del RTN (idClient)
        if (rtn.length() > 15) { // Ajusta este valor según tu base de datos
            JOptionPane.showMessageDialog(this, "El valor de RTN excede el tamaño permitido (máx. 15 caracteres).", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Salir del método si el dato no es válido
        }

        // Establecer la conexión con la base de datos
        con = conn.getConexion();
        String sql = "INSERT INTO clientes (clientName, phoneClient, adressClient, idClient, typeClient, rtnClient) VALUES (?, ?, ?, ?, ?, ?)";
        ps = con.prepareStatement(sql);

        // Asignar los valores a los parámetros de la consulta
        ps.setString(1, name);
        ps.setString(2, phone);
        ps.setString(3, adress);
        ps.setString(4, identy);
        ps.setString(5, type);
        ps.setString(6, rtn);

        // Ejecutar la inserción y verificar el resultado
        int rowsInserted = ps.executeUpdate();

        if (rowsInserted > 0) {
            JOptionPane.showMessageDialog(this, "¡Datos insertados exitosamente!");
            toList(); // Refrescar la lista si es necesario
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
    public void buttonLock(){
    dtClientes.getSelectionModel().addListSelectionListener(e -> {
        if (!e.getValueIsAdjusting()) { // Asegurarse de que el evento no sea temporal
            int selectedRow = dtClientes.getSelectedRow(); // Obtener la fila seleccionada
            if (selectedRow != -1) {
                btnSave.setEnabled(false); // Deshabilitar el botón si hay una fila seleccionada
            } else {
                btnSave.setEnabled(true); // Habilitar el botón si no hay selección
            }
        }
    });
    
    btnActualizar.setEnabled(false);

    // Listener para habilitar/deshabilitar btnActualizar según la selección en la tabla
    dtClientes.getSelectionModel().addListSelectionListener(e -> {
        if (!e.getValueIsAdjusting()) {
            int selectedRow = dtClientes.getSelectedRow();
            if (selectedRow != -1) {
                btnActualizar.setEnabled(true); // Habilitar el botón si hay una fila seleccionada
            } else {
                btnActualizar.setEnabled(false); // Deshabilitar el botón si no hay selección
            }
        }
    });
    }
   
    public FrmNewClient() {
        initComponents();
        this.setLocationRelativeTo(null);
        transparentButton();
        buttonLock();
        
        toList();
        jPanel1.setVisible(false);

        
    }
    
    public void ModificarNewClient(){

        Connection con = null;
    PreparedStatement ps = null;

    try {

            String name = txtName.getText();
            String phone = txtPhone.getText();
            String adress = txtDireccion.getText();
            String rtn = txtRTN.getText();
            String identy = txtIdenty.getText();
            String type = String.valueOf(cboType.getSelectedItem());


            int idClient = Integer.parseInt(txtId.getText());


            con = conn.getConexion();


            String sql = "UPDATE clientes SET clientName = ?, phoneClient = ?, adressClient = ?, rtnClient = ?, idClient = ?, typeClient = ? WHERE id = ?";
            ps = con.prepareStatement(sql);


            ps.setString(1, name);
            ps.setString(2, phone);
            ps.setString(3, adress);
            ps.setString(4, rtn);
            ps.setString(5, identy);
            ps.setString(6, type);
            ps.setInt(7, idClient); 


            int rowsUpdated = ps.executeUpdate();


            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this, "¡Datos actualizados exitosamente!");
                toList(); 
            } else {
                JOptionPane.showMessageDialog(this, "No se encontró el registro a actualizar.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ocurrió un error al actualizar los datos: " + e.getMessage());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El ID debe ser un número válido.");
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void deleteClient (){
    
        Connection con = null;
        PreparedStatement ps = null;

        try {
            // Obtener la fila seleccionada
            int selectedRow = dtClientes.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Por favor, selecciona un cliente para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return; // Salir si no hay selección
            }

            // Obtener el ID del cliente seleccionado desde la tabla
            int idClient = Integer.parseInt(dtClientes.getValueAt(selectedRow, 0).toString()); // Suponiendo que el ID está en la primera columna

            // Confirmar la eliminación
            int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que deseas eliminar este cliente?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) {
                return; // Salir si el usuario no confirma
            }

            // Conexión a la base de datos
            con = conn.getConexion();

            // Consulta SQL para eliminar
            String sql = "DELETE FROM clientes WHERE id = ?";
            ps = con.prepareStatement(sql);

            // Asignar el valor del ID al parámetro
            ps.setInt(1, idClient);

            // Ejecutar la consulta
            int rowsDeleted = ps.executeUpdate();

            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(this, "¡Cliente eliminado exitosamente!");
                
                toList(); // Actualizar la tabla
                FrmInicio open = new FrmInicio();
                open.setVisible(true);
                this.setVisible(false);
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

        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        dtClientes = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtIdenty = new javax.swing.JTextField();
        btnSearchForIdClient = new javax.swing.JButton();
        btnSearchforNameClient = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        txtShowForId = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtId = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        btnReturn1 = new javax.swing.JButton();
        lblPhone3 = new javax.swing.JLabel();
        btnClose1 = new javax.swing.JButton();
        lblPhone5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        cboType = new javax.swing.JComboBox<>();
        lblPhone6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtPhone = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtShowForName = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtRTN = new javax.swing.JTextField();
        btnEliminar = new javax.swing.JButton();
        lblPhone7 = new javax.swing.JLabel();
        lblPhone8 = new javax.swing.JLabel();
        btnActualizar = new javax.swing.JButton();
        lblBackground = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        dtClientes.setBackground(new java.awt.Color(255, 255, 255));
        dtClientes.setForeground(new java.awt.Color(0, 0, 0));
        dtClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NOMBRE", "IDENTIDAD", "TELEFONO"
            }
        ));
        dtClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dtClientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(dtClientes);
        if (dtClientes.getColumnModel().getColumnCount() > 0) {
            dtClientes.getColumnModel().getColumn(0).setMinWidth(15);
            dtClientes.getColumnModel().getColumn(0).setPreferredWidth(15);
            dtClientes.getColumnModel().getColumn(1).setPreferredWidth(300);
            dtClientes.getColumnModel().getColumn(2).setMinWidth(180);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 649, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 70, Short.MAX_VALUE)
        );

        jPanel2.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 320, 650, 70));

        jLabel2.setIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\addclient 90x90.png")); // NOI18N
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 40, -1, -1));

        jLabel1.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Identidad");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 240, 190, -1));

        txtIdenty.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel2.add(txtIdenty, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 240, 170, -1));

        btnSearchForIdClient.setIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\search32x32.png")); // NOI18N
        btnSearchForIdClient.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSearchForIdClient.setPressedIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\search32x32.png")); // NOI18N
        btnSearchForIdClient.setRolloverIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\search48x48.png")); // NOI18N
        btnSearchForIdClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchForIdClientActionPerformed(evt);
            }
        });
        jPanel2.add(btnSearchForIdClient, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 110, 70, 50));

        btnSearchforNameClient.setIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\search32x32.png")); // NOI18N
        btnSearchforNameClient.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSearchforNameClient.setPressedIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\search32x32.png")); // NOI18N
        btnSearchforNameClient.setRolloverIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\search48x48.png")); // NOI18N
        btnSearchforNameClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchforNameClientActionPerformed(evt);
            }
        });
        jPanel2.add(btnSearchforNameClient, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 70, 70, 50));

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
        jPanel2.add(btnSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 410, 90, 80));

        jLabel3.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Buscar Por ID:");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 120, 220, -1));

        txtShowForId.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtShowForId.setText("Ingrese Id Cliente");
        txtShowForId.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtShowForId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtShowForIdActionPerformed(evt);
            }
        });
        txtShowForId.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtShowForIdKeyPressed(evt);
            }
        });
        jPanel2.add(txtShowForId, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 120, 130, 30));

        jLabel4.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("ID:");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 200, 190, -1));

        txtId.setEditable(false);
        txtId.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtId.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel2.add(txtId, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 200, 170, -1));

        jLabel5.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Tipo:");
        jPanel2.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 290, 190, -1));

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
        jPanel2.add(btnReturn1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 410, 90, 80));

        lblPhone3.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblPhone3.setForeground(new java.awt.Color(255, 255, 255));
        lblPhone3.setText("REGRESAR");
        jPanel2.add(lblPhone3, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 490, -1, -1));

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
        jPanel2.add(btnClose1, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 410, 90, 80));

        lblPhone5.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblPhone5.setForeground(new java.awt.Color(255, 255, 255));
        lblPhone5.setText("ELIMINAR");
        jPanel2.add(lblPhone5, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 490, -1, 20));

        jLabel6.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Direccion:");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 230, 190, -1));

        txtDireccion.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel2.add(txtDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 230, 170, 50));

        cboType.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        cboType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "        ", "PERSONA", "INSTITUCION", "EMPRESA" }));
        jPanel2.add(cboType, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 290, 170, -1));

        lblPhone6.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblPhone6.setForeground(new java.awt.Color(255, 255, 255));
        lblPhone6.setText("CERRAR");
        jPanel2.add(lblPhone6, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 490, -1, -1));

        jLabel7.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Telefono:");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 200, 190, -1));

        txtPhone.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel2.add(txtPhone, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 200, 170, -1));

        jLabel8.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Nombre Cliente:");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 160, 190, -1));

        txtName.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtName.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jPanel2.add(txtName, new org.netbeans.lib.awtextra.AbsoluteConstraints(300, 160, 480, -1));

        jLabel9.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Buscar Nombre Cliente:");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 80, 220, -1));

        txtShowForName.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        txtShowForName.setText("Ingrese Nombre Cliente");
        txtShowForName.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        txtShowForName.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtShowForNameMouseClicked(evt);
            }
        });
        txtShowForName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtShowForNameActionPerformed(evt);
            }
        });
        txtShowForName.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtShowForNameKeyPressed(evt);
            }
        });
        jPanel2.add(txtShowForName, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 80, 370, 30));

        jLabel10.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("RTN:");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 280, 190, -1));

        txtRTN.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jPanel2.add(txtRTN, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 280, 170, -1));

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
        jPanel2.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 410, 90, 80));

        lblPhone7.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblPhone7.setForeground(new java.awt.Color(255, 255, 255));
        lblPhone7.setText("GUARDAR");
        jPanel2.add(lblPhone7, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 490, -1, -1));

        lblPhone8.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblPhone8.setForeground(new java.awt.Color(255, 255, 255));
        lblPhone8.setText("ACTUALIZAR");
        jPanel2.add(lblPhone8, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 490, -1, -1));

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
        jPanel2.add(btnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 410, 90, 80));

        lblBackground.setIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\cat orange (1)redimensionado.jpg")); // NOI18N
        jPanel2.add(lblBackground, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnClose1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClose1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnClose1ActionPerformed

    private void btnReturn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReturn1ActionPerformed
        FrmInicio open = new FrmInicio();
        
        open.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnReturn1ActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        InsertNewClient();
        
        if (mensaje == "¡Datos insertados exitosamente!"){
        FrmInicio open = new FrmInicio();
        open.setVisible(true);
        this.setVisible(false);}
        
        String texto = txtShowForName.getText();
        if (texto != ("Ingrese Nombre Cliente")) {
            // Habilitar el botón
        } else {  
            btnSave.setEnabled(false); // Deshabilitar el botón
        }
    }//GEN-LAST:event_btnSaveActionPerformed
    
    
    
    private void btnSearchforNameClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchforNameClientActionPerformed
        

    }//GEN-LAST:event_btnSearchforNameClientActionPerformed

    private void txtShowForNameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtShowForNameKeyPressed
        String texto = txtShowForName.getText();
        if (!texto.equals("Ingrese Nombre Cliente") && !texto.isEmpty()) {
            jPanel1.setVisible(true);
        } else {
            jPanel1.setVisible(false);
        }
        
        
        toList();
    }//GEN-LAST:event_txtShowForNameKeyPressed

    private void dtClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dtClientesMouseClicked
        int fila =  dtClientes.getSelectedRow();
        String Id = dtClientes.getValueAt(fila, 0).toString();
       
        
        mostrarClienteNombre(Id);
        jPanel1.setVisible(false);
        
        
    }//GEN-LAST:event_dtClientesMouseClicked

    private void txtShowForIdKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtShowForIdKeyPressed
        String texto = txtShowForId.getText();
        if (!texto.equals("Ingrese Id Cliente") && !texto.isEmpty()) {
            jPanel1.setVisible(true);
            
        } else {
            jPanel1.setVisible(false);
        }
        
        
        
        toList();
    }//GEN-LAST:event_txtShowForIdKeyPressed
    
    
    
    private void txtShowForIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtShowForIdActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtShowForIdActionPerformed

    private void btnSearchForIdClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchForIdClientActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnSearchForIdClientActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        ModificarNewClient();
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        
        deleteClient();
        
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnActualizarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnActualizarMouseClicked
        btnSave.setVisible(false);
    }//GEN-LAST:event_btnActualizarMouseClicked

    private void txtShowForNameMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtShowForNameMouseClicked
        


    }//GEN-LAST:event_txtShowForNameMouseClicked

    private void txtShowForNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtShowForNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtShowForNameActionPerformed

    
    public static void main(String args[]) {
      
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmNewClient().setVisible(true);
            }
        });
    }
    
    public void transparentButton(){
        btnSearchforNameClient.setOpaque(false);
        btnSearchforNameClient.setContentAreaFilled(false);
        btnSearchforNameClient.setBorderPainted(false);
        
        btnSearchForIdClient.setOpaque(false);
        btnSearchForIdClient.setContentAreaFilled(false);
        btnSearchForIdClient.setBorderPainted(false);
    }
    
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnClose1;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnReturn1;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearchForIdClient;
    private javax.swing.JButton btnSearchforNameClient;
    private javax.swing.JComboBox<String> cboType;
    private javax.swing.JTable dtClientes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBackground;
    private javax.swing.JLabel lblPhone3;
    private javax.swing.JLabel lblPhone5;
    private javax.swing.JLabel lblPhone6;
    private javax.swing.JLabel lblPhone7;
    private javax.swing.JLabel lblPhone8;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtIdenty;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtRTN;
    private javax.swing.JTextField txtShowForId;
    private javax.swing.JTextField txtShowForName;
    // End of variables declaration//GEN-END:variables
}
