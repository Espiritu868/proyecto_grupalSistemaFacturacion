
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
public class FrmImpresoras extends javax.swing.JFrame {
    
    public FrmImpresoras() {
        initComponents();
        this.setLocationRelativeTo(null);
        transparentButton();
        panel4.setVisible(false);
        jPanel2.setVisible(false);
        txtIdCliente.setVisible(false);
        txtIdentidadCliente.setVisible(false);
        btnActualizar.setEnabled(false);
        btnSave.setEnabled(false);
    }
    
        public void mostrarInicio(){
                    FrmInicio open = new FrmInicio();

                    open.setVisible(true);
                    this.setVisible(false);
        }
    
        DefaultTableModel modelo;

        Conexion conn = new Conexion("proyecto");
        String mensaje = "";
    
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
                // Obtener conexión
                con = conn.getConexion();

                // Consulta SQL para buscar cliente por id
                String consulta = "SELECT * FROM clientes WHERE id = ?"; // Uso de ? para seguridad

                // Crear el PreparedStatement
                pst = con.prepareStatement(consulta);

                // Establecer el parámetro de la consulta
                pst.setString(1, Id.trim()); // Usar .trim() para eliminar espacios si es necesario

                // Ejecutar la consulta
                rs = pst.executeQuery();

                // Comprobar si se encontraron datos
                if (rs.next()) {
                    // Asignar los datos a los campos correspondientes
                    txtIdentidadCliente.setText(rs.getString("idClient"));
                    txtNombreCliente.setText(rs.getString("clientName"));
                    txtTelefono.setText(rs.getString("phoneClient"));
                    txtDireccion.setText(rs.getString("adressClient"));

                } else {
                    // En caso de que no se encuentren resultados
                    JOptionPane.showMessageDialog(null, "No se encontraron datos para el cliente con el ID proporcionado.");
                    System.out.println("No se encontraron datos para el ID: " + Id);
                }
            } catch (Exception e) {
                // Manejo de excepciones
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
        
                public void InsertNewPrinter() {
            // Crear una instancia de Conexion para conectar con la base de datos
            Conexion conn = new Conexion("proyecto");  // "proyecto" es el nombre de tu base de datos

            // Obtener la conexión usando el método getConexion
            Connection con = conn.getConexion();

            // Verificar si la conexión es válida
            if (con == null) {
                JOptionPane.showMessageDialog(this, "No se pudo conectar a la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;  // Salir si la conexión no es válida
            }

            PreparedStatement ps = null;

            try {
                // Capturar y validar los valores de los componentes
                String idClient = txtIdentidadCliente.getText().trim();  // Obtener idClient
                String name = txtNombreCliente.getText().trim();
                String phone = txtTelefono.getText().trim();
                String adress = txtDireccion.getText().trim();
                String model = String.valueOf(cboModelo.getSelectedItem()).trim();
                String cablePoder = String.valueOf(cboCablePoder.getSelectedItem()).trim();
                String usb = String.valueOf(cboUsb.getSelectedItem()).trim();
                String other = String.valueOf(cboOtros.getSelectedItem()).trim();

                // Validar campos numéricos
                Integer costo = Integer.parseInt(txtCosto.getText().trim());
                Integer anticipo = Integer.parseInt(txtAnticipo.getText().trim());
                Integer pendiente = Integer.parseInt(txtFinal.getText().trim());

                String problema = txtProblem.getText().trim();
                String diagnostico = txtDiagnostic.getText().trim();

                // Validar campos obligatorios
                if (name.isEmpty() || phone.isEmpty() || adress.isEmpty() || model.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Por favor, llena todos los campos obligatorios.");
                    return;
                }

                // Preparar la consulta SQL (sin incluir 'id' ya que es autoincrementable)
                String sql = "INSERT INTO impresoras (idClient, clientName, phoneClient, adressClient, model, cPower, usb, other, coste, anticipo, final, equipmentProblem, diagnostic) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; 
                ps = con.prepareStatement(sql);

                // Asignar valores a los parámetros
                ps.setString(1, idClient);  // Asignar el valor de idClient
                ps.setString(2, name);
                ps.setString(3, phone);
                ps.setString(4, adress);
                ps.setString(5, model);
                ps.setString(6, cablePoder);
                ps.setString(7, usb);
                ps.setString(8, other);
                ps.setInt(9, costo);
                ps.setInt(10, anticipo);
                ps.setInt(11, pendiente);
                ps.setString(12, problema);
                ps.setString(13, diagnostico);

                // Ejecutar la consulta
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
                // Cerrar recursos
                try {
                    if (ps != null) ps.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        
    private void limpiarCamposCliente() {
    txtIdentidadCliente.setText("");
    txtNombreCliente.setText("");
    txtTelefono.setText("");
    txtDireccion.setText("");
    }
                
        public void buscarEquipoCliente() {
            // Crear una instancia de Conexion para conectar con la base de datos
            Conexion conn = new Conexion("proyecto"); // "proyecto" es el nombre de tu base de datos

            // Obtener la conexión usando el método getConexion
            Connection con = conn.getConexion();

            // Verificar si la conexión es válida
            if (con == null) {
                JOptionPane.showMessageDialog(this, "No se pudo conectar a la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
                return; // Salir si la conexión no es válida
            }

            Object[] dataClient = new Object[5];
            modelo = (DefaultTableModel) tblEquipoCliente.getModel();
            modelo.setRowCount(0); // Limpiar tabla antes de agregar nuevas filas

            String query = "SELECT id, model, clientName, equipmentProblem, idClient " +
                           "FROM impresoras WHERE clientName LIKE ?";

            try (PreparedStatement ps = con.prepareStatement(query)) {
                // Configurar el parámetro de la consulta
                ps.setString(1, "%" + txtBuscarEquipoCliente.getText() + "%");

                try (ResultSet rs = ps.executeQuery()) {
                    // Procesar los resultados de la consulta
                    while (rs.next()) {
                        dataClient[0] = rs.getString("id");
                        dataClient[1] = rs.getString("model");
                        dataClient[2] = rs.getString("clientName");
                        dataClient[3] = rs.getString("equipmentProblem");
                        dataClient[4] = rs.getString("idClient");

                        modelo.addRow(dataClient);
                    }
                }

                // Actualizar el modelo de la tabla
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
                rs = st.executeQuery("select * from impresoras where id = '"+ Identificador +"'");

            while(rs.next()){

                        // Asignar valores a los componentes
                        txtIdCliente.setText(rs.getString("id"));
                        txtIdentidadCliente.setText(rs.getString("idClient"));
                        txtNombreCliente.setText(rs.getString("clientName"));
                        txtTelefono.setText(rs.getString("phoneClient"));
                        txtDireccion.setText(rs.getString("adressClient"));
                        cboModelo.setSelectedItem(rs.getString("model"));
                        cboCablePoder.setSelectedItem(rs.getString("cPower"));
                        cboUsb.setSelectedItem(rs.getString("usb"));
                        cboOtros.setSelectedItem(rs.getString("other"));
                        int coste = rs.getInt("coste");
                        if (!rs.wasNull()) {
                            txtCosto.setText(String.valueOf(coste));
                        } else {
                            txtCosto.setText(""); // O algún valor predeterminado
                        }
                        int anticipo = rs.getInt("anticipo");
                        if (!rs.wasNull()) {
                            txtAnticipo.setText(String.valueOf(anticipo));
                        } else {
                            txtAnticipo.setText(""); // O algún valor predeterminado
                        }
                        int pendiente = rs.getInt("final");
                        if (!rs.wasNull()) {
                            txtFinal.setText(String.valueOf(pendiente));
                        } else {
                            txtFinal.setText(""); // O algún valor predeterminado
                        }
                        txtProblem.setText(rs.getString("equipmentProblem"));
                        txtDiagnostic.setText(rs.getString("diagnostic"));

                }
            } catch (Exception e) {
                System.out.println("Error en la consulta. Error en funcion MostrarEquipo.");
            }
        }
    
         public void ModificarNewClient(){

           Connection con = null;
           PreparedStatement ps = null;

           try {

               // Obtener los datos de los campos modificables
               String model = String.valueOf(cboModelo.getSelectedItem()); 
               String cpower = String.valueOf(cboCablePoder.getSelectedItem()); 
               String usb = String.valueOf(cboUsb.getSelectedItem());  
               String other = String.valueOf(cboOtros.getSelectedItem());        
               int coste = Integer.parseInt(txtCosto.getText());  
               int anticipo = Integer.parseInt(txtAnticipo.getText()); 
               int finalCost = Integer.parseInt(txtFinal.getText()); 
               String problem = txtProblem.getText();    
               String diagnostic = txtDiagnostic.getText();

               // Obtener el id de la computadora
               int idimpresoras = Integer.parseInt(txtIdCliente.getText());

               con = conn.getConexion();  // Conexión a la base de datos

               // Consulta SQL para actualizar los datos
               String sql = "UPDATE impresoras SET " +
                            "model = ?, cPower = ?, usb = ?, other = ?, " +
                            "coste = ?, anticipo = ?, final = ?, " +
                            "equipmentProblem = ?, diagnostic = ? WHERE id = ?";

               ps = con.prepareStatement(sql);

               // Asignar los valores a los parámetros del PreparedStatement
               ps.setString(1, model);
               ps.setString(2, cpower);
               ps.setString(3, usb);
               ps.setString(4, other);
               ps.setInt(5, coste);
               ps.setInt(6, anticipo);
               ps.setInt(7, finalCost );
               ps.setString(8, problem );
               ps.setString(9, diagnostic );
               ps.setInt(10, idimpresoras);  // Aseguramos que se actualiza el registro con el id específico

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
   
         public void deleteImpresoras(){
    
        Connection con = null;
        PreparedStatement ps = null;

        try {
            // Obtener la fila seleccionada
            int selectedRow = tblEquipoCliente.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Por favor, selecciona un equipo para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return; // Salir si no hay selección
            }

            // Obtener el ID del cliente seleccionado desde la tabla
            int idClient = Integer.parseInt(tblEquipoCliente.getValueAt(selectedRow, 0).toString()); // Suponiendo que el ID está en la primera columna

            // Confirmar la eliminación
            int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que deseas eliminar el equipo de este cliente?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) {
                return; // Salir si el usuario no confirma
            }

            // Conexión a la base de datos
            con = conn.getConexion();

            // Consulta SQL para eliminar
            String sql = "DELETE FROM impresoras WHERE id = ?";
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
            txtFinal.setText(""); // Limpiar el campo final
            return; // Salir del método
        }

        // Calcular el valor final
        int finalValue = costo - anticipo;

        // Mostrar el valor final en el campo de texto
        txtFinal.setText(String.valueOf(finalValue));
    } catch (NumberFormatException e) {
        // Manejo de errores: si los campos no tienen números, dejar final en blanco
        txtFinal.setText("");
    }
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        dtClientes = new javax.swing.JTable();
        txtIdentidadCliente1 = new javax.swing.JTextField();
        panel4 = new java.awt.Panel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblEquipoCliente = new javax.swing.JTable();
        txtNombreCliente = new javax.swing.JTextField();
        lblNombreCliente = new javax.swing.JLabel();
        lblDireccion = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        lblTelefono = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        lblModelo = new javax.swing.JLabel();
        lblProblema = new javax.swing.JLabel();
        lblMaletin = new javax.swing.JLabel();
        cboCablePoder = new javax.swing.JComboBox<>();
        cboUsb = new javax.swing.JComboBox<>();
        lblCargador = new javax.swing.JLabel();
        lblOtros = new javax.swing.JLabel();
        cboOtros = new javax.swing.JComboBox<>();
        txtProblem = new javax.swing.JTextField();
        lblProblema1 = new javax.swing.JLabel();
        txtDiagnostic = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        cboModelo = new javax.swing.JComboBox<>();
        txtAnticipo = new javax.swing.JTextField();
        lblServiceTag4 = new javax.swing.JLabel();
        txtCosto = new javax.swing.JTextField();
        lblServiceTag5 = new javax.swing.JLabel();
        lblServiceTag3 = new javax.swing.JLabel();
        txtFinal = new javax.swing.JTextField();
        btnSearchEquip = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        txtIngreseNombreCliente = new javax.swing.JTextField();
        txtBuscarEquipoCliente = new javax.swing.JTextField();
        btnReturn1 = new javax.swing.JButton();
        lblPhone4 = new javax.swing.JLabel();
        btnClose2 = new javax.swing.JButton();
        lblPhone6 = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        lblPhone7 = new javax.swing.JLabel();
        txtIdentidadCliente = new javax.swing.JTextField();
        txtIdCliente = new javax.swing.JTextField();
        lblPhone8 = new javax.swing.JLabel();
        btnActualizar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        lblPhone5 = new javax.swing.JLabel();
        lblBackground = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("IMPRESORAS");

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 620, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(txtIdentidadCliente1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(txtIdentidadCliente1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 50, 620, 70));

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
        jScrollPane5.setViewportView(tblEquipoCliente);

        javax.swing.GroupLayout panel4Layout = new javax.swing.GroupLayout(panel4);
        panel4.setLayout(panel4Layout);
        panel4Layout.setHorizontalGroup(
            panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 910, Short.MAX_VALUE)
        );
        panel4Layout.setVerticalGroup(
            panel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
        );

        jPanel1.add(panel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 910, 440));

        txtNombreCliente.setEditable(false);
        jPanel1.add(txtNombreCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 110, 360, -1));

        lblNombreCliente.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblNombreCliente.setForeground(new java.awt.Color(255, 255, 255));
        lblNombreCliente.setText("Nombre De Cliente:");
        jPanel1.add(lblNombreCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 110, -1, -1));

        lblDireccion.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblDireccion.setForeground(new java.awt.Color(255, 255, 255));
        lblDireccion.setText("Direccion: ");
        jPanel1.add(lblDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 140, -1, -1));

        txtDireccion.setEditable(false);
        jPanel1.add(txtDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 140, 360, -1));

        lblTelefono.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblTelefono.setForeground(new java.awt.Color(255, 255, 255));
        lblTelefono.setText("Telefono:");
        jPanel1.add(lblTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 110, -1, 20));

        txtTelefono.setEditable(false);
        jPanel1.add(txtTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 110, 100, -1));

        lblModelo.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblModelo.setForeground(new java.awt.Color(255, 255, 255));
        lblModelo.setText("C/Poder");
        jPanel1.add(lblModelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 180, -1, 20));

        lblProblema.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblProblema.setForeground(new java.awt.Color(255, 255, 255));
        lblProblema.setText("Problema del Equipo");
        jPanel1.add(lblProblema, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 240, -1, 20));

        lblMaletin.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblMaletin.setForeground(new java.awt.Color(255, 255, 255));
        lblMaletin.setText("Modelo:");
        jPanel1.add(lblMaletin, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 140, -1, 20));

        cboCablePoder.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "      ", "Si", "No", " " }));
        jPanel1.add(cboCablePoder, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 180, 60, -1));

        cboUsb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "      ", "Si", "No", " " }));
        jPanel1.add(cboUsb, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 180, 60, -1));

        lblCargador.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblCargador.setForeground(new java.awt.Color(255, 255, 255));
        lblCargador.setText("USB");
        jPanel1.add(lblCargador, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 180, -1, 20));

        lblOtros.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblOtros.setForeground(new java.awt.Color(255, 255, 255));
        lblOtros.setText("Otros");
        jPanel1.add(lblOtros, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 180, -1, 20));

        cboOtros.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "      ", "Si", "No", " " }));
        cboOtros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboOtrosActionPerformed(evt);
            }
        });
        jPanel1.add(cboOtros, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 180, 70, -1));
        jPanel1.add(txtProblem, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 270, 680, 60));

        lblProblema1.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblProblema1.setForeground(new java.awt.Color(255, 255, 255));
        lblProblema1.setText("Diagnostico");
        jPanel1.add(lblProblema1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 340, -1, 20));
        jPanel1.add(txtDiagnostic, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 370, 680, 60));

        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\impresora90x90.png")); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 10, 100, 90));

        cboModelo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "          ", "EPSON", "CANON", "BROTHER", "HP", "OTROS", " ", " ", " " }));
        jPanel1.add(cboModelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 140, 100, -1));

        txtAnticipo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAnticipoKeyReleased(evt);
            }
        });
        jPanel1.add(txtAnticipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 220, 60, 30));

        lblServiceTag4.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblServiceTag4.setForeground(new java.awt.Color(255, 255, 255));
        lblServiceTag4.setText("Costo");
        jPanel1.add(lblServiceTag4, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 220, -1, 20));

        txtCosto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCostoKeyReleased(evt);
            }
        });
        jPanel1.add(txtCosto, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 220, 60, 30));

        lblServiceTag5.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblServiceTag5.setForeground(new java.awt.Color(255, 255, 255));
        lblServiceTag5.setText("Anticipo");
        jPanel1.add(lblServiceTag5, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 220, -1, 20));

        lblServiceTag3.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblServiceTag3.setForeground(new java.awt.Color(255, 255, 255));
        lblServiceTag3.setText("Pendiente");
        jPanel1.add(lblServiceTag3, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 220, -1, 20));

        txtFinal.setEditable(false);
        txtFinal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFinalActionPerformed(evt);
            }
        });
        jPanel1.add(txtFinal, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 220, 70, 30));

        btnSearchEquip.setIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\search32x32.png")); // NOI18N
        btnSearchEquip.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSearchEquip.setPressedIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\search32x32.png")); // NOI18N
        btnSearchEquip.setRolloverIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\search48x48.png")); // NOI18N
        btnSearchEquip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchEquipActionPerformed(evt);
            }
        });
        jPanel1.add(btnSearchEquip, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 20, 70, 50));

        btnSearch.setIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\search32x32.png")); // NOI18N
        btnSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSearch.setPressedIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\search32x32.png")); // NOI18N
        btnSearch.setRolloverIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\search48x48.png")); // NOI18N
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });
        jPanel1.add(btnSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 20, 70, 50));

        txtIngreseNombreCliente.setText(" Ingrese Nombre Cliente");
        txtIngreseNombreCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtIngreseNombreClienteKeyPressed(evt);
            }
        });
        jPanel1.add(txtIngreseNombreCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 30, 310, -1));

        txtBuscarEquipoCliente.setText("Buscar Equipo Cliente");
        txtBuscarEquipoCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarEquipoClienteKeyPressed(evt);
            }
        });
        jPanel1.add(txtBuscarEquipoCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 30, 270, -1));

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
        jPanel1.add(btnReturn1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 440, 80, 70));

        lblPhone4.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblPhone4.setForeground(new java.awt.Color(255, 255, 255));
        lblPhone4.setText("REGRESAR");
        jPanel1.add(lblPhone4, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 510, -1, -1));

        btnClose2.setBackground(new java.awt.Color(255, 204, 51));
        btnClose2.setForeground(new java.awt.Color(255, 204, 51));
        btnClose2.setIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\cerrar 50x50pp.png")); // NOI18N
        btnClose2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnClose2.setPressedIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\cerrar 50x50pp.png")); // NOI18N
        btnClose2.setRolloverIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\cerrar 50x50.png")); // NOI18N
        btnClose2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClose2ActionPerformed(evt);
            }
        });
        jPanel1.add(btnClose2, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 440, 80, 70));

        lblPhone6.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblPhone6.setForeground(new java.awt.Color(255, 255, 255));
        lblPhone6.setText("CERRAR");
        jPanel1.add(lblPhone6, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 510, -1, -1));

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
        jPanel1.add(btnSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 440, 80, 70));

        lblPhone7.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblPhone7.setForeground(new java.awt.Color(255, 255, 255));
        lblPhone7.setText("GUARDAR");
        jPanel1.add(lblPhone7, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 510, -1, -1));
        jPanel1.add(txtIdentidadCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 80, -1));
        jPanel1.add(txtIdCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 80, -1));

        lblPhone8.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblPhone8.setForeground(new java.awt.Color(255, 255, 255));
        lblPhone8.setText("ACTUALIZAR");
        jPanel1.add(lblPhone8, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 510, -1, -1));

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
        jPanel1.add(btnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 440, 80, 70));

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
        jPanel1.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 440, 80, 70));

        lblPhone5.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblPhone5.setForeground(new java.awt.Color(255, 255, 255));
        lblPhone5.setText("ELIMINAR");
        jPanel1.add(lblPhone5, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 510, -1, 20));

        lblBackground.setIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\cat orange (1)redimensionado.jpg")); // NOI18N
        lblBackground.setText("jLabel1");
        jPanel1.add(lblBackground, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 936, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtFinalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFinalActionPerformed

    }//GEN-LAST:event_txtFinalActionPerformed

    private void btnReturn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReturn1ActionPerformed
        mostrarInicio();
    }//GEN-LAST:event_btnReturn1ActionPerformed

    private void btnClose2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClose2ActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnClose2ActionPerformed

    private void cboOtrosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboOtrosActionPerformed
        
    }//GEN-LAST:event_cboOtrosActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        InsertNewPrinter();
        
        if (mensaje == "¡Datos insertados exitosamente!"){
        mostrarInicio();}
    }//GEN-LAST:event_btnSaveActionPerformed

    private void txtIngreseNombreClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIngreseNombreClienteKeyPressed
        jPanel2.setVisible(true);
        buscarCliente();
    }//GEN-LAST:event_txtIngreseNombreClienteKeyPressed

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
        btnActualizar.setEnabled(false);
    }//GEN-LAST:event_dtClientesMouseClicked

    private void tblEquipoClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblEquipoClienteKeyPressed

    }//GEN-LAST:event_tblEquipoClienteKeyPressed

    private void tblEquipoClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEquipoClienteMouseClicked
        int fila =  tblEquipoCliente.getSelectedRow();
        String Identificador = tblEquipoCliente.getValueAt(fila, 0).toString();
        mostrarEquipo(Identificador);
        panel4.setVisible(false);
        btnSave.setEnabled(false);
        btnActualizar.setEnabled(true);
    }//GEN-LAST:event_tblEquipoClienteMouseClicked

    private void txtBuscarEquipoClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarEquipoClienteKeyPressed
        panel4.setVisible(true);
        buscarEquipoCliente();
    }//GEN-LAST:event_txtBuscarEquipoClienteKeyPressed

    private void btnActualizarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnActualizarMouseClicked
        
    }//GEN-LAST:event_btnActualizarMouseClicked

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
      ModificarNewClient();
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        deleteImpresoras();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void txtCostoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCostoKeyReleased
    CalcularPendiente();        
    }//GEN-LAST:event_txtCostoKeyReleased

    private void txtAnticipoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAnticipoKeyReleased
    CalcularPendiente();
    }//GEN-LAST:event_txtAnticipoKeyReleased

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
    txtIngreseNombreCliente.requestFocusInWindow(); // Da el foco al JTextField
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
        

      
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FrmImpresoras().setVisible(true);
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
    private javax.swing.JButton btnClose2;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnReturn1;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnSearchEquip;
    private javax.swing.JComboBox<String> cboCablePoder;
    private javax.swing.JComboBox<String> cboModelo;
    private javax.swing.JComboBox<String> cboOtros;
    private javax.swing.JComboBox<String> cboUsb;
    private javax.swing.JTable dtClientes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lblBackground;
    private javax.swing.JLabel lblCargador;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblMaletin;
    private javax.swing.JLabel lblModelo;
    private javax.swing.JLabel lblNombreCliente;
    private javax.swing.JLabel lblOtros;
    private javax.swing.JLabel lblPhone4;
    private javax.swing.JLabel lblPhone5;
    private javax.swing.JLabel lblPhone6;
    private javax.swing.JLabel lblPhone7;
    private javax.swing.JLabel lblPhone8;
    private javax.swing.JLabel lblProblema;
    private javax.swing.JLabel lblProblema1;
    private javax.swing.JLabel lblServiceTag3;
    private javax.swing.JLabel lblServiceTag4;
    private javax.swing.JLabel lblServiceTag5;
    private javax.swing.JLabel lblTelefono;
    private java.awt.Panel panel4;
    private javax.swing.JTable tblEquipoCliente;
    private javax.swing.JTextField txtAnticipo;
    private javax.swing.JTextField txtBuscarEquipoCliente;
    private javax.swing.JTextField txtCosto;
    private javax.swing.JTextField txtDiagnostic;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtFinal;
    private javax.swing.JTextField txtIdCliente;
    private javax.swing.JTextField txtIdentidadCliente;
    private javax.swing.JTextField txtIdentidadCliente1;
    private javax.swing.JTextField txtIngreseNombreCliente;
    private javax.swing.JTextField txtNombreCliente;
    private javax.swing.JTextField txtProblem;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
