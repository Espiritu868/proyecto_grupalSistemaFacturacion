
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
 * @author Ever Chavez
 */
public class FrmTelefonos extends javax.swing.JFrame {
    
    
    
    public FrmTelefonos() {
        initComponents();
        this.setLocationRelativeTo(null);
        transparentButton();
        jPanel2.setVisible(false);
        panel1.setVisible(false);
        txtIdCliente.setVisible(false);
        txtIdentidadCliente.setVisible(false);
        btnActualizar.setEnabled(false);
        btnSave.setEnabled(false);
    }
    DefaultTableModel modelo;
        
    Conexion conn = new Conexion("proyecto");
    String mensaje = "";
    
        public void mostrarInicio(){
                    FrmInicio open = new FrmInicio();

                    open.setVisible(true);
                    this.setVisible(false);
                }

        public void ModificarNewPhone(){

           Connection con = null;
           PreparedStatement ps = null;

           try {

               // Obtener los datos de los campos modificables
               String model = String.valueOf(cboModelo.getSelectedItem());  // Modelo de la computadora
               String imei = txtImei.getText(); // Etiqueta de servicio
               String charger = String.valueOf(cboCargador.getSelectedItem());    // Cargador
               String protector = String.valueOf(cboProtector.getSelectedItem());
               String other = String.valueOf(cboOtros.getSelectedItem());        // Otros
               String password = txtPassword.getText();  // Contraseña
               String problem = txtProblem.getText();    // Problema del equipo
               String diagnostic = txtDiagnostic.getText(); // Diagnóstico
               int coste = Integer.parseInt(txtCosto.getText());  // Coste
               int anticipo = Integer.parseInt(txtAnticipo.getText());  // Anticipo
               int finalCost = Integer.parseInt(txtPendiente.getText());  // Costo final

               // Obtener el id de la computadora
               int idTelefono = Integer.parseInt(txtIdCliente.getText());

               con = conn.getConexion();  // Conexión a la base de datos

               // Consulta SQL para actualizar los datos
               String sql = "UPDATE telefonos SET " +
                            "model = ?, imei = ?, charger = ?, protector = ?, other = ?, " +
                            "password = ?, coste = ?, anticipo = ?, pendiente = ?, " +
                            "equipmentProblem = ?, diagnostic = ? WHERE id = ?";

               ps = con.prepareStatement(sql);

               // Asignar los valores a los parámetros del PreparedStatement
               ps.setString(1, model);
               ps.setString(2, imei);
               ps.setString(3, protector);
               ps.setString(4, charger);
               ps.setString(5, other);
               ps.setString(6, password);
               ps.setInt(7, coste);
               ps.setInt(8, anticipo);
               ps.setInt(9, finalCost);
               ps.setString(10, problem);
               ps.setString(11, diagnostic);
               ps.setInt(12, idTelefono);  // Aseguramos que se actualiza el registro con el id específico

               // Ejecutar la actualización
               int rowsUpdated = ps.executeUpdate();

               // Verificar si la actualización fue exitosa
               if (rowsUpdated > 0) {
                   JOptionPane.showMessageDialog(this, "¡Datos actualizados exitosamente!");
                   toList();  // Llamada a un método para refrescar la lista de registros
                   mostrarInicio();
               } else {
                   JOptionPane.showMessageDialog(this, "No se encontró el registro a actualizar.");
               }
           } catch (SQLException e) {
               e.printStackTrace();
               JOptionPane.showMessageDialog(this, "Ocurrió un error al actualizar los datos: " + e.getMessage());
           } catch (NumberFormatException e) {
               JOptionPane.showMessageDialog(this, "Uno de los campos numéricos no tiene un valor válido.");
           } finally {
               try {
                   if (ps != null) ps.close();
                   if (con != null) con.close();
               } catch (SQLException e) {
                   e.printStackTrace();
               }
           }
       }

        
        public void buscarCliente(){

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
                rs = st.executeQuery("select * from clientes where clientName like '%" + txtIngreseNombreCliente.getText() + "%'");

                while(rs.next()){
                    dataClient[0] = rs.getString("id");
                    dataClient[1] = rs.getString("clientName");
                    dataClient[2] = rs.getString("idClient");
                    dataClient[3] = rs.getString("adressClient");

                    modelo.addRow(dataClient);

                    dtClientes.setModel(modelo);

                }
            } catch (Exception e) {
                System.out.println("Error en la consulta. problema en BuscarCliente");
            }

        }  
        
        public void mostrarCliente(String Id) {
            Connection con = null;
            ResultSet rs = null;
            PreparedStatement pst = null;

            try {
                
                con = conn.getConexion();
                String consulta = "SELECT * FROM clientes WHERE id = ?"; 
                pst = con.prepareStatement(consulta);
                pst.setString(1, Id.trim()); 
                rs = pst.executeQuery();

                if (rs.next()) {
                    
                    txtIdentidadCliente.setText(rs.getString("idClient"));
                    txtNombreCliente.setText(rs.getString("clientName"));
                    txtTelefono.setText(rs.getString("phoneClient"));
                    txtDireccion.setText(rs.getString("adressClient"));

                } else {
                    
                    JOptionPane.showMessageDialog(null, "No se encontraron datos para el cliente con el ID proporcionado.");
                    System.out.println("No se encontraron datos para el ID: " + Id);
                }
            } catch (Exception e) {
                
                e.printStackTrace();
                            JOptionPane.showMessageDialog(null, "Ocurrió un error al consultar los datos del cliente.");
              } finally {
                    // Cerrar solo los recursos necesarios
                    try {
                        if (rs != null) rs.close();
                        if (pst != null) pst.close();
                        // Nota: No cerramos la conexión aquí
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
        }
        // Método para limpiar los campos del formulario
        private void limpiarCamposCliente() {
            txtIdentidadCliente.setText("");
            txtNombreCliente.setText("");
            txtTelefono.setText("");
            txtDireccion.setText("");
        }
        
        public void InsertNewPhone() {
            
            Conexion conn = new Conexion("proyecto");   
            Connection con = conn.getConexion();
            
            if (con == null) {
                JOptionPane.showMessageDialog(this, "No se pudo conectar a la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;  
            }

            PreparedStatement ps = null;

            try {
                
                String idClient = txtIdentidadCliente.getText().trim();  // Obtener idClient
                String name = txtNombreCliente.getText().trim();
                String phone = txtTelefono.getText().trim();
                String adress = txtDireccion.getText().trim();
                String model = String.valueOf(cboModelo.getSelectedItem()).trim();
                String imei = txtImei.getText().trim();
                String protector = String.valueOf(cboProtector.getSelectedItem()).trim();
                String cargador = String.valueOf(cboCargador.getSelectedItem()).trim();
                String other = String.valueOf(cboOtros.getSelectedItem()).trim();
                String contrasenia = txtPassword.getText().trim();

                
                Integer costo = Integer.parseInt(txtCosto.getText().trim());
                Integer anticipo = Integer.parseInt(txtAnticipo.getText().trim());
                Integer pendiente = Integer.parseInt(txtPendiente.getText().trim());

                String problema = txtProblem.getText().trim();
                String diagnostico = txtDiagnostic.getText().trim();

                
                if (name.isEmpty() || phone.isEmpty() || adress.isEmpty() || model.isEmpty() || imei.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Por favor, llena todos los campos obligatorios.");
                    return;
                }
  
                String sql = "INSERT INTO telefonos (idClient, clientName, phoneClient, adressClient, model, imei, charger, protector, other, password, coste, anticipo, pendiente, equipmentProblem, diagnostic) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; 
                ps = con.prepareStatement(sql);
   
                ps.setString(1, idClient);  
                ps.setString(2, name);
                ps.setString(3, phone);
                ps.setString(4, adress);
                ps.setString(5, model);
                ps.setString(6, imei);
                ps.setString(7, cargador);
                ps.setString(8, protector);
                ps.setString(9, other);
                ps.setString(10, contrasenia);
                ps.setInt(11, costo);
                ps.setInt(12, anticipo);
                ps.setInt(13, pendiente);
                ps.setString(14, problema);
                ps.setString(15, diagnostico);

                int rowsInserted = ps.executeUpdate();

                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "¡Datos insertados exitosamente!");
                    toList(); // Refrescar la lista
                    mostrarInicio();
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo insertar la información.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Ocurrió un error al insertar los datos: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                
                try {
                    if (ps != null) ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        
        public void buscarEquipoCliente() {
            
            Conexion conn = new Conexion("proyecto"); 

            
            Connection con = conn.getConexion();

            
            if (con == null) {
                JOptionPane.showMessageDialog(this, "No se pudo conectar a la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
                return; 
            }

            Object[] dataClient = new Object[5];
            modelo = (DefaultTableModel) tblEquipoCliente.getModel();
            modelo.setRowCount(0); 

            String query = "SELECT id, model, clientName, equipmentProblem, idClient " +
                           "FROM telefonos WHERE clientName LIKE ?";

            try (PreparedStatement ps = con.prepareStatement(query)) {
                
                ps.setString(1, "%" + txtBuscarEquipoCliente.getText() + "%");

                try (ResultSet rs = ps.executeQuery()) {
                    
                    while (rs.next()) {
                        dataClient[0] = rs.getString("id");
                        dataClient[1] = rs.getString("model");
                        dataClient[2] = rs.getString("clientName");
                        dataClient[3] = rs.getString("equipmentProblem");
                        dataClient[4] = rs.getString("idClient");

                        modelo.addRow(dataClient);
                    }
                }

                
                tblEquipoCliente.setModel(modelo);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al buscar equipo: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        
        public void mostrarEquipo(String Identificador){
    
   
        Connection con = null;

        ResultSet rs = null;
        Statement st = null;


        try {
            con = conn.getConexion();
            st = con.createStatement();
            rs = st.executeQuery("select * from telefonos where id = '"+ Identificador +"'");

        while(rs.next()){

                    txtIdCliente.setText(rs.getString("id"));
                    txtIdentidadCliente.setText(rs.getString("idClient"));
                    txtNombreCliente.setText(rs.getString("clientName"));
                    txtTelefono.setText(rs.getString("phoneClient"));
                    txtDireccion.setText(rs.getString("adressClient"));
                    cboModelo.setSelectedItem(rs.getString("model"));
                    txtImei.setText(rs.getString("imei"));
                    cboCargador.setSelectedItem(rs.getString("charger"));
                    cboProtector.setSelectedItem(rs.getString("protector"));
                    cboOtros.setSelectedItem(rs.getString("other"));
                    txtPassword.setText(rs.getString("password"));
                    int coste = rs.getInt("coste");
                    if (!rs.wasNull()) {
                        txtCosto.setText(String.valueOf(coste));
                    } else {
                        txtCosto.setText(""); 
                    }
                    int anticipo = rs.getInt("anticipo");
                    if (!rs.wasNull()) {
                        txtAnticipo.setText(String.valueOf(anticipo));
                    } else {
                        txtAnticipo.setText(""); 
                    }
                    int pendiente = rs.getInt("pendiente");
                    if (!rs.wasNull()) {
                        txtPendiente.setText(String.valueOf(pendiente));
                    } else {
                        txtPendiente.setText(""); 
                    }
                    txtProblem.setText(rs.getString("equipmentProblem"));
                    txtDiagnostic.setText(rs.getString("diagnostic"));

            }
        } catch (Exception e) {
            System.out.println("Error en la consulta. Error en funcion MostrarEquipo.");
        }
        
        
        }
    
        public void deleteClient (){
    
        Connection con = null;
        PreparedStatement ps = null;

        try {
            // Obtener la fila seleccionada
            int selectedRow = tblEquipoCliente.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Por favor, selecciona un equipo para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return; 
            }

            
            int idClient = Integer.parseInt(tblEquipoCliente.getValueAt(selectedRow, 0).toString()); 

            
            int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que deseas eliminar el equipo de este cliente?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) {
                return; 
            }

            
            con = conn.getConexion();

            
            String sql = "DELETE FROM telefonos WHERE id = ?";
            ps = con.prepareStatement(sql);

            
            ps.setInt(1, idClient);

            
            int rowsDeleted = ps.executeUpdate();

            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(this, "¡Cliente eliminado exitosamente!");
                
                toList(); 
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
        
private void CalcularPendiente() {
    try {
        // Obtener los valores de los campos
        int costo = Integer.parseInt(txtCosto.getText());
        int anticipo = Integer.parseInt(txtAnticipo.getText());

        // Verificar que el anticipo no sea mayor que el costo
        if (anticipo > costo) {
            JOptionPane.showMessageDialog(this, 
                "El anticipo no puede ser mayor que el costo.", 
                "Error", 
                JOptionPane.ERROR_MESSAGE);
            txtAnticipo.setText(""); // Limpiar el campo de anticipo
            txtPendiente.setText(""); // Limpiar el campo final
            return; // Salir del método
        }

        // Calcular el valor final
        int finalValue = costo - anticipo;

        // Mostrar el valor final en el campo de texto
        txtPendiente.setText(String.valueOf(finalValue));
    } catch (NumberFormatException e) {
        // Manejo de errores: si los campos no tienen números, dejar final en blanco
        txtPendiente.setText("");
    }
}



    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        panel1 = new java.awt.Panel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblEquipoCliente = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        dtClientes = new javax.swing.JTable();
        txtNombreCliente = new javax.swing.JTextField();
        lblNombreCliente = new javax.swing.JLabel();
        lblDireccion = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        lblTelefono = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        lblModelo = new javax.swing.JLabel();
        lblProblema = new javax.swing.JLabel();
        txtImei = new javax.swing.JTextField();
        lblMaletin = new javax.swing.JLabel();
        cboCargador = new javax.swing.JComboBox<>();
        cboProtector = new javax.swing.JComboBox<>();
        lblCargador = new javax.swing.JLabel();
        lblOtros = new javax.swing.JLabel();
        cboModelo = new javax.swing.JComboBox<>();
        lblServiceTag1 = new javax.swing.JLabel();
        txtProblem = new javax.swing.JTextField();
        lblProblema1 = new javax.swing.JLabel();
        txtDiagnostic = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        lblServiceTag2 = new javax.swing.JLabel();
        txtPassword = new javax.swing.JTextField();
        txtAnticipo = new javax.swing.JTextField();
        lblServiceTag4 = new javax.swing.JLabel();
        txtCosto = new javax.swing.JTextField();
        lblServiceTag5 = new javax.swing.JLabel();
        lblServiceTag3 = new javax.swing.JLabel();
        txtPendiente = new javax.swing.JTextField();
        btnReturn = new javax.swing.JButton();
        lblPhone4 = new javax.swing.JLabel();
        btnClose = new javax.swing.JButton();
        lblPhone6 = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        lblPhone9 = new javax.swing.JLabel();
        btnSearchEquip = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        txtIngreseNombreCliente = new javax.swing.JTextField();
        txtBuscarEquipoCliente = new javax.swing.JTextField();
        cboOtros = new javax.swing.JComboBox<>();
        txtIdentidadCliente = new javax.swing.JTextField();
        txtIdCliente = new javax.swing.JTextField();
        lblPhone10 = new javax.swing.JLabel();
        btnActualizar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        lblPhone5 = new javax.swing.JLabel();
        lblBackground = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("TELEFONOS");

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblEquipoCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "EQUIPO", "NOMBRE", "DESCRIPCION", "FECHA"
            }
        ));
        tblEquipoCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblEquipoClienteMouseClicked(evt);
            }
        });
        tblEquipoCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblEquipoClienteKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblEquipoCliente);
        if (tblEquipoCliente.getColumnModel().getColumnCount() > 0) {
            tblEquipoCliente.getColumnModel().getColumn(0).setMinWidth(5);
            tblEquipoCliente.getColumnModel().getColumn(0).setPreferredWidth(5);
            tblEquipoCliente.getColumnModel().getColumn(0).setMaxWidth(150);
        }

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 910, Short.MAX_VALUE)
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
        );

        jPanel1.add(panel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 170, 910, -1));

        dtClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NOMBRE", "IDENTIDAD", "DIRECCION"
            }
        ));
        dtClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dtClientesMouseClicked(evt);
            }
        });
        dtClientes.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                dtClientesKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(dtClientes);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 90, 600, 60));

        txtNombreCliente.setEditable(false);
        jPanel1.add(txtNombreCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 110, 360, -1));

        lblNombreCliente.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblNombreCliente.setForeground(new java.awt.Color(255, 255, 255));
        lblNombreCliente.setText("Nombre De Cliente:");
        jPanel1.add(lblNombreCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 110, -1, -1));

        lblDireccion.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblDireccion.setForeground(new java.awt.Color(255, 255, 255));
        lblDireccion.setText("Direccion: ");
        jPanel1.add(lblDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 140, -1, -1));

        txtDireccion.setEditable(false);
        jPanel1.add(txtDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 140, 360, -1));

        lblTelefono.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblTelefono.setForeground(new java.awt.Color(255, 255, 255));
        lblTelefono.setText("Telefono:");
        jPanel1.add(lblTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 110, -1, 20));

        txtTelefono.setEditable(false);
        jPanel1.add(txtTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 110, 100, -1));

        lblModelo.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblModelo.setForeground(new java.awt.Color(255, 255, 255));
        lblModelo.setText("Cargador");
        jPanel1.add(lblModelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 170, -1, 20));

        lblProblema.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblProblema.setForeground(new java.awt.Color(255, 255, 255));
        lblProblema.setText("Problema del Equipo");
        jPanel1.add(lblProblema, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 240, -1, 20));
        jPanel1.add(txtImei, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 170, 100, -1));

        lblMaletin.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblMaletin.setForeground(new java.awt.Color(255, 255, 255));
        lblMaletin.setText("Modelo:");
        jPanel1.add(lblMaletin, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 140, -1, 20));

        cboCargador.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "      ", "Si", "No", " " }));
        jPanel1.add(cboCargador, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 170, -1, -1));

        cboProtector.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "      ", "Si", "No", " " }));
        jPanel1.add(cboProtector, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 170, -1, -1));

        lblCargador.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblCargador.setForeground(new java.awt.Color(255, 255, 255));
        lblCargador.setText("Protector");
        jPanel1.add(lblCargador, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 170, -1, 20));

        lblOtros.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblOtros.setForeground(new java.awt.Color(255, 255, 255));
        lblOtros.setText("Otros");
        jPanel1.add(lblOtros, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 170, -1, 20));

        cboModelo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "   ", "SAMSUNG", "IPHONE", "XIAOMI", "HUAWEI", "TECNO", "INIFINIX", "BLU", "ZTE", "MOTOROLA", "OTRO", " " }));
        jPanel1.add(cboModelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 140, 100, -1));

        lblServiceTag1.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblServiceTag1.setForeground(new java.awt.Color(255, 255, 255));
        lblServiceTag1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblServiceTag1.setText("IMEI:");
        jPanel1.add(lblServiceTag1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 180, 100, 20));

        txtProblem.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        jPanel1.add(txtProblem, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 270, 680, 60));

        lblProblema1.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblProblema1.setForeground(new java.awt.Color(255, 255, 255));
        lblProblema1.setText("Diagnostico");
        jPanel1.add(lblProblema1, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 340, -1, 20));
        jPanel1.add(txtDiagnostic, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 360, 680, 60));

        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\telefono90x90.png")); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 10, 100, 90));

        lblServiceTag2.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblServiceTag2.setForeground(new java.awt.Color(255, 255, 255));
        lblServiceTag2.setText("Contrasena:");
        jPanel1.add(lblServiceTag2, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 210, -1, 20));
        jPanel1.add(txtPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 210, 100, -1));

        txtAnticipo.setText("0");
        txtAnticipo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAnticipoKeyReleased(evt);
            }
        });
        jPanel1.add(txtAnticipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 210, 60, -1));

        lblServiceTag4.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblServiceTag4.setForeground(new java.awt.Color(255, 255, 255));
        lblServiceTag4.setText("Costo");
        jPanel1.add(lblServiceTag4, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 210, -1, 20));

        txtCosto.setText("0");
        txtCosto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCostoActionPerformed(evt);
            }
        });
        txtCosto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCostoKeyReleased(evt);
            }
        });
        jPanel1.add(txtCosto, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 210, 60, -1));

        lblServiceTag5.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblServiceTag5.setForeground(new java.awt.Color(255, 255, 255));
        lblServiceTag5.setText("Anticipo");
        jPanel1.add(lblServiceTag5, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 210, -1, 20));

        lblServiceTag3.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblServiceTag3.setForeground(new java.awt.Color(255, 255, 255));
        lblServiceTag3.setText("Pendiente");
        jPanel1.add(lblServiceTag3, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 210, -1, 20));

        txtPendiente.setEditable(false);
        txtPendiente.setText("0");
        txtPendiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPendienteActionPerformed(evt);
            }
        });
        jPanel1.add(txtPendiente, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 210, 70, -1));

        btnReturn.setBackground(new java.awt.Color(255, 204, 51));
        btnReturn.setForeground(new java.awt.Color(255, 204, 51));
        btnReturn.setIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\return48x48pp.png")); // NOI18N
        btnReturn.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReturn.setPressedIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\return48x48pp.png")); // NOI18N
        btnReturn.setRolloverIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\return48x48.png")); // NOI18N
        btnReturn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReturnActionPerformed(evt);
            }
        });
        jPanel1.add(btnReturn, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 430, 80, 70));

        lblPhone4.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblPhone4.setForeground(new java.awt.Color(255, 255, 255));
        lblPhone4.setText("REGRESAR");
        jPanel1.add(lblPhone4, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 500, -1, -1));

        btnClose.setBackground(new java.awt.Color(255, 204, 51));
        btnClose.setForeground(new java.awt.Color(255, 204, 51));
        btnClose.setIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\cerrar 50x50pp.png")); // NOI18N
        btnClose.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnClose.setPressedIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\cerrar 50x50pp.png")); // NOI18N
        btnClose.setRolloverIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\cerrar 50x50.png")); // NOI18N
        btnClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCloseActionPerformed(evt);
            }
        });
        jPanel1.add(btnClose, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 430, 80, 70));

        lblPhone6.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblPhone6.setForeground(new java.awt.Color(255, 255, 255));
        lblPhone6.setText("CERRAR");
        jPanel1.add(lblPhone6, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 500, -1, -1));

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
        jPanel1.add(btnSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 430, 80, 70));

        lblPhone9.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblPhone9.setForeground(new java.awt.Color(255, 255, 255));
        lblPhone9.setText("GUARDAR");
        jPanel1.add(lblPhone9, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 500, -1, -1));

        btnSearchEquip.setIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\search32x32.png")); // NOI18N
        btnSearchEquip.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSearchEquip.setPressedIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\search32x32.png")); // NOI18N
        btnSearchEquip.setRolloverIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\search48x48.png")); // NOI18N
        btnSearchEquip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchEquipActionPerformed(evt);
            }
        });
        jPanel1.add(btnSearchEquip, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 50, 70, 50));

        btnSearch.setIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\search32x32.png")); // NOI18N
        btnSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSearch.setPressedIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\search32x32.png")); // NOI18N
        btnSearch.setRolloverIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\search48x48.png")); // NOI18N
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });
        jPanel1.add(btnSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 50, 70, 50));

        txtIngreseNombreCliente.setText(" Ingrese Nombre Cliente");
        txtIngreseNombreCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtIngreseNombreClienteKeyPressed(evt);
            }
        });
        jPanel1.add(txtIngreseNombreCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 60, 340, -1));

        txtBuscarEquipoCliente.setText("Buscar Equipo Cliente");
        txtBuscarEquipoCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarEquipoClienteKeyPressed(evt);
            }
        });
        jPanel1.add(txtBuscarEquipoCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 60, 270, -1));

        cboOtros.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "      ", "Si", "No", " " }));
        jPanel1.add(cboOtros, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 170, -1, -1));
        jPanel1.add(txtIdentidadCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 20, 90, -1));
        jPanel1.add(txtIdCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 20, 40, -1));

        lblPhone10.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblPhone10.setForeground(new java.awt.Color(255, 255, 255));
        lblPhone10.setText("ACTUALIZAR");
        jPanel1.add(lblPhone10, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 500, -1, -1));

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
        jPanel1.add(btnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 430, 80, 70));

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
        jPanel1.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 430, 80, 70));

        lblPhone5.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblPhone5.setForeground(new java.awt.Color(255, 255, 255));
        lblPhone5.setText("ELIMINAR");
        jPanel1.add(lblPhone5, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 500, -1, 20));

        lblBackground.setIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\cat orange (1)redimensionado.jpg")); // NOI18N
        jPanel1.add(lblBackground, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 930, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 525, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtPendienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPendienteActionPerformed

    }//GEN-LAST:event_txtPendienteActionPerformed

    private void btnReturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReturnActionPerformed
        mostrarInicio();
    }//GEN-LAST:event_btnReturnActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void txtCostoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCostoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCostoActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
    
    InsertNewPhone();
    mostrarInicio();

    }//GEN-LAST:event_btnSaveActionPerformed

    private void dtClientesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dtClientesKeyPressed

    }//GEN-LAST:event_dtClientesKeyPressed

    private void dtClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dtClientesMouseClicked
        limpiarCamposCliente(); 
        
        int fila = dtClientes.getSelectedRow();  // Obtener la fila seleccionada
        String id = dtClientes.getValueAt(fila, 0).toString().trim();  // Obtener el ID y eliminar espacios en blanco

        System.out.println("ID obtenido de la tabla: " + id);  // Verificar el valor del ID
        
        mostrarCliente(id);  // Pasar el id al método mostrarCliente
        jPanel2.setVisible(false);  // Ocultar el panel (si es necesario)
        btnSave.setEnabled(true);
        
    }//GEN-LAST:event_dtClientesMouseClicked

    private void tblEquipoClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblEquipoClienteKeyPressed

    }//GEN-LAST:event_tblEquipoClienteKeyPressed

    private void tblEquipoClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEquipoClienteMouseClicked
        int fila =  tblEquipoCliente.getSelectedRow();
        String Identificador = tblEquipoCliente.getValueAt(fila, 0).toString();
        mostrarEquipo(Identificador);
        panel1.setVisible(false);
        btnSave.setEnabled(false);
        btnActualizar.setEnabled(true);

    }//GEN-LAST:event_tblEquipoClienteMouseClicked

    private void txtIngreseNombreClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIngreseNombreClienteKeyPressed
        jPanel2.setVisible(true);
        buscarCliente();
    }//GEN-LAST:event_txtIngreseNombreClienteKeyPressed

    private void txtBuscarEquipoClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarEquipoClienteKeyPressed
        panel1.setVisible(true);
        buscarEquipoCliente();
    }//GEN-LAST:event_txtBuscarEquipoClienteKeyPressed

    private void btnActualizarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnActualizarMouseClicked
        btnSave.setVisible(false);
    }//GEN-LAST:event_btnActualizarMouseClicked

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
    ModificarNewPhone();
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
      deleteClient();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void txtCostoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCostoKeyReleased
    CalcularPendiente();        // TODO add your handling code here:
    }//GEN-LAST:event_txtCostoKeyReleased

    private void txtAnticipoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAnticipoKeyReleased
    CalcularPendiente();        // TODO add your handling code here:
    }//GEN-LAST:event_txtAnticipoKeyReleased

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        txtIngreseNombreCliente.requestFocusInWindow();  
        txtIngreseNombreCliente.selectAll();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnSearchEquipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchEquipActionPerformed
        txtBuscarEquipoCliente.requestFocusInWindow();
        txtBuscarEquipoCliente.selectAll();
    }//GEN-LAST:event_btnSearchEquipActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
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
            java.util.logging.Logger.getLogger(FrmTelefonos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmTelefonos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmTelefonos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmTelefonos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmTelefonos().setVisible(true);
            }
        });
    }
    
    public void transparentButton(){
        btnSearch.setOpaque(false);
        btnSearch.setContentAreaFilled(false);
        btnSearch.setBorderPainted(false);
        
        btnSearchEquip.setOpaque(false);
        btnSearchEquip.setContentAreaFilled(false);
        btnSearchEquip.setBorderPainted(false);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnReturn;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnSearchEquip;
    private javax.swing.JComboBox<String> cboCargador;
    private javax.swing.JComboBox<String> cboModelo;
    private javax.swing.JComboBox<String> cboOtros;
    private javax.swing.JComboBox<String> cboProtector;
    private javax.swing.JTable dtClientes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblBackground;
    private javax.swing.JLabel lblCargador;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblMaletin;
    private javax.swing.JLabel lblModelo;
    private javax.swing.JLabel lblNombreCliente;
    private javax.swing.JLabel lblOtros;
    private javax.swing.JLabel lblPhone10;
    private javax.swing.JLabel lblPhone4;
    private javax.swing.JLabel lblPhone5;
    private javax.swing.JLabel lblPhone6;
    private javax.swing.JLabel lblPhone9;
    private javax.swing.JLabel lblProblema;
    private javax.swing.JLabel lblProblema1;
    private javax.swing.JLabel lblServiceTag1;
    private javax.swing.JLabel lblServiceTag2;
    private javax.swing.JLabel lblServiceTag3;
    private javax.swing.JLabel lblServiceTag4;
    private javax.swing.JLabel lblServiceTag5;
    private javax.swing.JLabel lblTelefono;
    private java.awt.Panel panel1;
    private javax.swing.JTable tblEquipoCliente;
    private javax.swing.JTextField txtAnticipo;
    private javax.swing.JTextField txtBuscarEquipoCliente;
    private javax.swing.JTextField txtCosto;
    private javax.swing.JTextField txtDiagnostic;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtIdCliente;
    private javax.swing.JTextField txtIdentidadCliente;
    private javax.swing.JTextField txtImei;
    private javax.swing.JTextField txtIngreseNombreCliente;
    private javax.swing.JTextField txtNombreCliente;
    private javax.swing.JTextField txtPassword;
    private javax.swing.JTextField txtPendiente;
    private javax.swing.JTextField txtProblem;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
