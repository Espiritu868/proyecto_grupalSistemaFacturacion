
package MAIN;

import DAO.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import static java.util.stream.Collectors.toList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ever Chavez
 */
public class FrmComputadoras extends javax.swing.JFrame {
        
    public FrmComputadoras() {
        initComponents();
        this.setLocationRelativeTo(null);
        transparentButton();
        toList();
        jPanel2.setVisible(false);
        panel2.setVisible(false);
        btnActualizar.setEnabled(false);
        txtIdCliente.setVisible(false);
        txtIdentidadCliente.setVisible(false);
        btnSave.setEnabled(false);
        setInitialValues();
        
    }
    
    DefaultTableModel modelo;
        
    Conexion conn = new Conexion("proyecto");
    String mensaje = "";
    
        public void mostrarInicio(){
                    FrmInicio open = new FrmInicio();

                    open.setVisible(true);
                    this.setVisible(false);
                    
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
        private void limpiarCamposCliente() {
        txtIdentidadCliente.setText("");
        txtNombreCliente.setText("");
        txtTelefono.setText("");
        txtDireccion.setText("");
        }
        public void InsertNewComputer() {
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
                String serviceTag = txtServiceTag.getText().trim();
                String maletin = String.valueOf(cboMaletin.getSelectedItem()).trim();
                String cargador = String.valueOf(cboCargador.getSelectedItem()).trim();
                String other = String.valueOf(cboOtros.getSelectedItem()).trim();
                String contrasenia = txtPassword.getText().trim();

                // Validar campos numéricos
                Integer costo = Integer.parseInt(txtCosto.getText().trim());
                Integer anticipo = Integer.parseInt(txtAnticipo.getText().trim());
                Integer pendiente = Integer.parseInt(txtFinal.getText().trim());

                String problema = txtProblema.getText().trim();
                String diagnostico = txtProblema.getText().trim();

                // Validar campos obligatorios
                if (name.isEmpty() || phone.isEmpty() || adress.isEmpty() || model.isEmpty() || serviceTag.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Por favor, llena todos los campos obligatorios.");
                    return;
                }

                // Preparar la consulta SQL (sin incluir 'id' ya que es autoincrementable)
                String sql = "INSERT INTO computadoras (idClient, clientName, phoneClient, adressClient, model, serviceTag, briegcase, charger, other, password, coste, anticipo, final, equipmentProblem, diagnostic) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"; 
                ps = con.prepareStatement(sql);

                // Asignar valores a los parámetros
                ps.setString(1, idClient);  // Asignar el valor de idClient
                ps.setString(2, name);
                ps.setString(3, phone);
                ps.setString(4, adress);
                ps.setString(5, model);
                ps.setString(6, serviceTag);
                ps.setString(7, maletin);
                ps.setString(8, cargador);
                ps.setString(9, other);
                ps.setString(10, contrasenia);
                ps.setInt(11, costo);
                ps.setInt(12, anticipo);
                ps.setInt(13, pendiente);
                ps.setString(14, problema);
                ps.setString(15, diagnostico);

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
    
        private void CalcularPendiente() {
            try {
                // Obtener los valores de los campos como double
                double costo = Double.parseDouble(txtCosto.getText());
                double anticipo = Double.parseDouble(txtAnticipo.getText());

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
                double finalValue = costo - anticipo;

                // Crear un formato para mostrar los valores con dos decimales
                DecimalFormat df = new DecimalFormat("0.00");

                // Mostrar el valor final en el campo de texto
                txtFinal.setText(df.format(finalValue));

            } catch (NumberFormatException e) {
                // Manejo de errores: si los campos no tienen números, dejar final en blanco
                txtFinal.setText("");
            }
        }

        // Para asegurarte de que los JTextField inicien con el valor 0.00
        private void setInitialValues() {
            DecimalFormat df = new DecimalFormat("0.00");
            txtCosto.setText(df.format(0.00));
            txtAnticipo.setText(df.format(0.00));
            txtFinal.setText(df.format(0.00));
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
            modelo = (DefaultTableModel) tblEquipoCliente1.getModel();
            modelo.setRowCount(0); // Limpiar tabla antes de agregar nuevas filas

            String query = "SELECT id, model, clientName, equipmentProblem, idClient " +
                           "FROM computadoras WHERE clientName LIKE ?";

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
                tblEquipoCliente1.setModel(modelo);
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
            rs = st.executeQuery("select * from computadoras where id = '"+ Identificador +"'");

        while(rs.next()){

                    // Asignar valores a los componentes
                    txtIdCliente.setText(rs.getString("id"));
                    txtIdentidadCliente.setText(rs.getString("idClient"));
                    txtNombreCliente.setText(rs.getString("clientName"));
                    txtTelefono.setText(rs.getString("phoneClient"));
                    txtDireccion.setText(rs.getString("adressClient"));
                    cboModelo.setSelectedItem(rs.getString("model"));
                    txtServiceTag.setText(rs.getString("serviceTag"));
                    cboMaletin.setSelectedItem(rs.getString("briegcase"));
                    cboCargador.setSelectedItem(rs.getString("charger"));
                    cboOtros.setSelectedItem(rs.getString("other"));
                    txtPassword.setText(rs.getString("password"));
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
                    txtProblema.setText(rs.getString("equipmentProblem"));
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
            int selectedRow = tblEquipoCliente1.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Por favor, selecciona un equipo para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return; // Salir si no hay selección
            }

            // Obtener el ID del cliente seleccionado desde la tabla
            int idClient = Integer.parseInt(tblEquipoCliente1.getValueAt(selectedRow, 0).toString()); // Suponiendo que el ID está en la primera columna

            // Confirmar la eliminación
            int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que deseas eliminar el equipo de este cliente?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) {
                return; // Salir si el usuario no confirma
            }

            // Conexión a la base de datos
            con = conn.getConexion();

            // Consulta SQL para eliminar
            String sql = "DELETE FROM computadoras WHERE id = ?";
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
            
        public void ModificarNewClient(){

           Connection con = null;
           PreparedStatement ps = null;

           try {

               // Obtener los datos de los campos modificables
               String model = String.valueOf(cboModelo.getSelectedItem());  // Modelo de la computadora
               String serviceTag = txtServiceTag.getText(); // Etiqueta de servicio
               String briegcase = String.valueOf(cboMaletin.getSelectedItem()); // Maletín
               String charger = String.valueOf(cboCargador.getSelectedItem());    // Cargador
               String other = String.valueOf(cboOtros.getSelectedItem());        // Otros
               String password = txtPassword.getText();  // Contraseña
               String problem = txtProblema.getText();    // Problema del equipo
               String diagnostic = txtDiagnostic.getText(); // Diagnóstico
               int coste = Integer.parseInt(txtCosto.getText());  // Coste
               int anticipo = Integer.parseInt(txtAnticipo.getText());  // Anticipo
               int finalCost = Integer.parseInt(txtFinal.getText());  // Costo final

               // Obtener el id de la computadora
               int idComputer = Integer.parseInt(txtIdCliente.getText());

               con = conn.getConexion();  // Conexión a la base de datos

               // Consulta SQL para actualizar los datos
               String sql = "UPDATE computadoras SET " +
                            "model = ?, serviceTag = ?, briegcase = ?, charger = ?, other = ?, " +
                            "password = ?, coste = ?, anticipo = ?, final = ?, " +
                            "equipmentProblem = ?, diagnostic = ? WHERE id = ?";

               ps = con.prepareStatement(sql);

               // Asignar los valores a los parámetros del PreparedStatement
               ps.setString(1, model);
               ps.setString(2, serviceTag);
               ps.setString(3, briegcase);
               ps.setString(4, charger);
               ps.setString(5, other);
               ps.setString(6, password);
               ps.setInt(7, coste);
               ps.setInt(8, anticipo);
               ps.setInt(9, finalCost);
               ps.setString(10, problem);
               ps.setString(11, diagnostic);
               ps.setInt(12, idComputer);  // Aseguramos que se actualiza el registro con el id específico

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
        

        
        
        


        
        
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        panel2 = new java.awt.Panel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblEquipoCliente1 = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        dtClientes = new javax.swing.JTable();
        btnSearchEquip = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        txtNombreCliente = new javax.swing.JTextField();
        txtBuscarEquipoCliente = new javax.swing.JTextField();
        lblNombreCliente = new javax.swing.JLabel();
        lblDireccion = new javax.swing.JLabel();
        txtPassword = new javax.swing.JTextField();
        lblTelefono = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        lblModelo = new javax.swing.JLabel();
        lblProblema = new javax.swing.JLabel();
        lblMaletin = new javax.swing.JLabel();
        cboCargador = new javax.swing.JComboBox<>();
        cboMaletin = new javax.swing.JComboBox<>();
        lblCargador = new javax.swing.JLabel();
        lblOtros = new javax.swing.JLabel();
        cboOtros = new javax.swing.JComboBox<>();
        lblProblema1 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        cboModelo = new javax.swing.JComboBox<>();
        lblServiceTag2 = new javax.swing.JLabel();
        txtFinal = new javax.swing.JTextField();
        txtIngreseNombreCliente = new javax.swing.JTextField();
        lblServiceTag5 = new javax.swing.JLabel();
        txtCosto = new javax.swing.JTextField();
        btnReturn1 = new javax.swing.JButton();
        lblPhone4 = new javax.swing.JLabel();
        btnClose2 = new javax.swing.JButton();
        lblPhone6 = new javax.swing.JLabel();
        btnSave = new javax.swing.JButton();
        lblPhone7 = new javax.swing.JLabel();
        txtIdCliente = new javax.swing.JTextField();
        lblServiceTag6 = new javax.swing.JLabel();
        txtAnticipo = new javax.swing.JTextField();
        txtDireccion = new javax.swing.JTextField();
        lblDireccion1 = new javax.swing.JLabel();
        lblDireccion2 = new javax.swing.JLabel();
        txtServiceTag = new javax.swing.JTextField();
        txtIdentidadCliente = new javax.swing.JTextField();
        lblFecha = new javax.swing.JLabel();
        btnActualizar = new javax.swing.JButton();
        lblPhone8 = new javax.swing.JLabel();
        btnEliminar = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtProblema = new javax.swing.JTextArea();
        jScrollPane5 = new javax.swing.JScrollPane();
        txtDiagnostic = new javax.swing.JTextArea();
        lblPhone5 = new javax.swing.JLabel();
        lblBackground = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("COMPUTADORAS");

        jPanel1.setBackground(new java.awt.Color(102, 102, 102));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblEquipoCliente1.setModel(new javax.swing.table.DefaultTableModel(
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
        tblEquipoCliente1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblEquipoClienteMouseClicked(evt);
            }
        });
        tblEquipoCliente1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblEquipoClienteKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblEquipoCliente1);
        if (tblEquipoCliente1.getColumnModel().getColumnCount() > 0) {
            tblEquipoCliente1.getColumnModel().getColumn(0).setPreferredWidth(30);
            tblEquipoCliente1.getColumnModel().getColumn(1).setPreferredWidth(150);
            tblEquipoCliente1.getColumnModel().getColumn(2).setPreferredWidth(250);
            tblEquipoCliente1.getColumnModel().getColumn(3).setPreferredWidth(250);
            tblEquipoCliente1.getColumnModel().getColumn(4).setPreferredWidth(120);
        }

        javax.swing.GroupLayout panel2Layout = new javax.swing.GroupLayout(panel2);
        panel2.setLayout(panel2Layout);
        panel2Layout.setHorizontalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 910, Short.MAX_VALUE)
        );
        panel2Layout.setVerticalGroup(
            panel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel2Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel1.add(panel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 910, 80));

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
        if (dtClientes.getColumnModel().getColumnCount() > 0) {
            dtClientes.getColumnModel().getColumn(0).setPreferredWidth(15);
            dtClientes.getColumnModel().getColumn(1).setPreferredWidth(200);
            dtClientes.getColumnModel().getColumn(3).setPreferredWidth(200);
        }

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 620, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 80, 620, 70));

        btnSearchEquip.setIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\search32x32.png")); // NOI18N
        btnSearchEquip.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSearchEquip.setPressedIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\search32x32.png")); // NOI18N
        btnSearchEquip.setRolloverIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\search48x48.png")); // NOI18N
        btnSearchEquip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchEquipActionPerformed(evt);
            }
        });
        jPanel1.add(btnSearchEquip, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 10, 70, 50));

        btnSearch.setIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\search32x32.png")); // NOI18N
        btnSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSearch.setPressedIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\search32x32.png")); // NOI18N
        btnSearch.setRolloverIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\search48x48.png")); // NOI18N
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });
        jPanel1.add(btnSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 10, 70, 50));

        txtNombreCliente.setEditable(false);
        txtNombreCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreClienteActionPerformed(evt);
            }
        });
        jPanel1.add(txtNombreCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 70, 360, -1));

        txtBuscarEquipoCliente.setText("Buscar Equipo Cliente");
        txtBuscarEquipoCliente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txtBuscarEquipoClienteMouseClicked(evt);
            }
        });
        txtBuscarEquipoCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarEquipoClienteActionPerformed(evt);
            }
        });
        txtBuscarEquipoCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarEquipoClienteKeyPressed(evt);
            }
        });
        jPanel1.add(txtBuscarEquipoCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 20, 270, -1));

        lblNombreCliente.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblNombreCliente.setForeground(new java.awt.Color(255, 255, 255));
        lblNombreCliente.setText("Nombre De Cliente:");
        jPanel1.add(lblNombreCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 70, -1, -1));

        lblDireccion.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblDireccion.setForeground(new java.awt.Color(255, 255, 255));
        lblDireccion.setText("Password");
        jPanel1.add(lblDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 200, -1, -1));

        txtPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswordActionPerformed(evt);
            }
        });
        jPanel1.add(txtPassword, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 200, 110, -1));

        lblTelefono.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblTelefono.setForeground(new java.awt.Color(255, 255, 255));
        lblTelefono.setText("Telefono:");
        jPanel1.add(lblTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 70, -1, 20));

        txtTelefono.setEditable(false);
        jPanel1.add(txtTelefono, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 70, 100, -1));

        lblModelo.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblModelo.setForeground(new java.awt.Color(255, 255, 255));
        lblModelo.setText("Cargador");
        jPanel1.add(lblModelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 160, -1, 20));

        lblProblema.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblProblema.setForeground(new java.awt.Color(255, 255, 255));
        lblProblema.setText("Problema del Equipo");
        jPanel1.add(lblProblema, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 230, -1, 20));

        lblMaletin.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblMaletin.setForeground(new java.awt.Color(255, 255, 255));
        lblMaletin.setText("Modelo:");
        jPanel1.add(lblMaletin, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 110, -1, 20));

        cboCargador.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "      ", "Si", "No", " " }));
        jPanel1.add(cboCargador, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 160, -1, -1));

        cboMaletin.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "      ", "Si", "No", " " }));
        jPanel1.add(cboMaletin, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 160, -1, -1));

        lblCargador.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblCargador.setForeground(new java.awt.Color(255, 255, 255));
        lblCargador.setText("Maletin");
        jPanel1.add(lblCargador, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 160, -1, 20));

        lblOtros.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblOtros.setForeground(new java.awt.Color(255, 255, 255));
        lblOtros.setText("Otros");
        jPanel1.add(lblOtros, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 160, -1, 20));

        cboOtros.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "      ", "Si", "No", " " }));
        jPanel1.add(cboOtros, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 160, -1, -1));

        lblProblema1.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblProblema1.setForeground(new java.awt.Color(255, 255, 255));
        lblProblema1.setText("Diagnostico");
        jPanel1.add(lblProblema1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 330, -1, 20));

        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Internal Form Menu\\ordenador (1)90x90.png")); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 0, -1, 70));

        cboModelo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "         ", "DELL", "HP", "LENOVO", "ACER", "ASUS", "MSI", "OTROS", " ", " " }));
        cboModelo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboModeloActionPerformed(evt);
            }
        });
        jPanel1.add(cboModelo, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 110, 100, -1));

        lblServiceTag2.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblServiceTag2.setForeground(new java.awt.Color(255, 255, 255));
        lblServiceTag2.setText("Pendiente");
        jPanel1.add(lblServiceTag2, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 210, -1, 20));

        txtFinal.setEditable(false);
        txtFinal.setText("0.00");
        txtFinal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFinalActionPerformed(evt);
            }
        });
        jPanel1.add(txtFinal, new org.netbeans.lib.awtextra.AbsoluteConstraints(760, 210, 70, -1));

        txtIngreseNombreCliente.setText(" Ingrese Nombre Cliente");
        txtIngreseNombreCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIngreseNombreClienteActionPerformed(evt);
            }
        });
        txtIngreseNombreCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtIngreseNombreClienteKeyPressed(evt);
            }
        });
        jPanel1.add(txtIngreseNombreCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 20, 310, -1));

        lblServiceTag5.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblServiceTag5.setForeground(new java.awt.Color(255, 255, 255));
        lblServiceTag5.setText("Costo");
        jPanel1.add(lblServiceTag5, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 210, -1, 20));

        txtCosto.setText("0.00");
        txtCosto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCostoKeyReleased(evt);
            }
        });
        jPanel1.add(txtCosto, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 210, 60, -1));

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
        jPanel1.add(btnReturn1, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 430, 80, 70));

        lblPhone4.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblPhone4.setForeground(new java.awt.Color(255, 255, 255));
        lblPhone4.setText("REGRESAR");
        jPanel1.add(lblPhone4, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 500, -1, -1));

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
        jPanel1.add(btnClose2, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 430, 80, 70));

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
        jPanel1.add(btnSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 430, 80, 70));

        lblPhone7.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblPhone7.setForeground(new java.awt.Color(255, 255, 255));
        lblPhone7.setText("GUARDAR");
        jPanel1.add(lblPhone7, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 500, -1, -1));
        jPanel1.add(txtIdCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 60, -1));

        lblServiceTag6.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblServiceTag6.setForeground(new java.awt.Color(255, 255, 255));
        lblServiceTag6.setText("Anticipo");
        jPanel1.add(lblServiceTag6, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 210, -1, 20));

        txtAnticipo.setText("0.00");
        txtAnticipo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtAnticipoKeyReleased(evt);
            }
        });
        jPanel1.add(txtAnticipo, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 210, 60, -1));

        txtDireccion.setEditable(false);
        jPanel1.add(txtDireccion, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 110, 360, -1));

        lblDireccion1.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblDireccion1.setForeground(new java.awt.Color(255, 255, 255));
        lblDireccion1.setText("Direccion: ");
        jPanel1.add(lblDireccion1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 110, -1, -1));

        lblDireccion2.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblDireccion2.setForeground(new java.awt.Color(255, 255, 255));
        lblDireccion2.setText("Service Tag");
        jPanel1.add(lblDireccion2, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 160, -1, -1));
        jPanel1.add(txtServiceTag, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 160, 110, -1));
        jPanel1.add(txtIdentidadCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 80, -1));

        lblFecha.setFont(new java.awt.Font("Times New Roman", 1, 14)); // NOI18N
        lblFecha.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(lblFecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 20, 70, 30));

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

        txtProblema.setColumns(20);
        txtProblema.setLineWrap(true);
        txtProblema.setRows(5);
        jScrollPane3.setViewportView(txtProblema);

        jPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 260, 690, 60));

        txtDiagnostic.setColumns(20);
        txtDiagnostic.setLineWrap(true);
        txtDiagnostic.setRows(5);
        jScrollPane5.setViewportView(txtDiagnostic);

        jPanel1.add(jScrollPane5, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 360, 690, 60));

        lblPhone5.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblPhone5.setForeground(new java.awt.Color(255, 255, 255));
        lblPhone5.setText("ELIMINAR");
        jPanel1.add(lblPhone5, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 500, -1, 20));

        lblBackground.setIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\cat orange (1)redimensionado.jpg")); // NOI18N
        jPanel1.add(lblBackground, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 928, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    
    private void txtFinalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFinalActionPerformed
        
    }//GEN-LAST:event_txtFinalActionPerformed

    private void txtNombreClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreClienteActionPerformed
        
    }//GEN-LAST:event_txtNombreClienteActionPerformed

    private void btnReturn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReturn1ActionPerformed
        mostrarInicio();
    }//GEN-LAST:event_btnReturn1ActionPerformed

    private void btnClose2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClose2ActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnClose2ActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        InsertNewComputer();
        
        if (mensaje == "¡Datos insertados exitosamente!"){
        FrmInicio open = new FrmInicio();
        open.setVisible(true);
        this.setVisible(false);}
        
        
    }//GEN-LAST:event_btnSaveActionPerformed

    private void cboModeloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboModeloActionPerformed
        
    }//GEN-LAST:event_cboModeloActionPerformed

    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordActionPerformed
        
    }//GEN-LAST:event_txtPasswordActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        txtIngreseNombreCliente.requestFocusInWindow();   
        txtIngreseNombreCliente.selectAll();
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnSearchEquipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchEquipActionPerformed
        txtBuscarEquipoCliente.requestFocusInWindow();
        txtBuscarEquipoCliente.selectAll();
    }//GEN-LAST:event_btnSearchEquipActionPerformed

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

    private void txtIngreseNombreClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIngreseNombreClienteKeyPressed
        jPanel2.setVisible(true);
        buscarCliente();
        
    }//GEN-LAST:event_txtIngreseNombreClienteKeyPressed

    private void txtIngreseNombreClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIngreseNombreClienteActionPerformed

    }//GEN-LAST:event_txtIngreseNombreClienteActionPerformed

    private void dtClientesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dtClientesKeyPressed

    }//GEN-LAST:event_dtClientesKeyPressed

    private void txtBuscarEquipoClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarEquipoClienteActionPerformed

    }//GEN-LAST:event_txtBuscarEquipoClienteActionPerformed

    private void txtBuscarEquipoClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarEquipoClienteKeyPressed
        panel2.setVisible(true);
        buscarEquipoCliente();
    }//GEN-LAST:event_txtBuscarEquipoClienteKeyPressed

    private void txtBuscarEquipoClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtBuscarEquipoClienteMouseClicked

    }//GEN-LAST:event_txtBuscarEquipoClienteMouseClicked

    private void btnActualizarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnActualizarMouseClicked
        btnSave.setVisible(false);
    }//GEN-LAST:event_btnActualizarMouseClicked

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        ModificarNewClient();
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        deleteClient();
        
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void txtCostoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCostoKeyReleased
     CalcularPendiente();            // TODO add your handling code here:
    }//GEN-LAST:event_txtCostoKeyReleased

    private void txtAnticipoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAnticipoKeyReleased
    CalcularPendiente();        // TODO add your handling code here:
    }//GEN-LAST:event_txtAnticipoKeyReleased

    private void tblEquipoClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblEquipoClienteKeyPressed

    }//GEN-LAST:event_tblEquipoClienteKeyPressed

    private void tblEquipoClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEquipoClienteMouseClicked

        int fila =  tblEquipoCliente1.getSelectedRow();
        String Identificador = tblEquipoCliente1.getValueAt(fila, 0).toString();
        mostrarEquipo(Identificador);
        panel2.setVisible(false);
        btnSave.setEnabled(false);
        btnActualizar.setEnabled(true);
    }//GEN-LAST:event_tblEquipoClienteMouseClicked
    
    public void transparentButton(){
        btnSearch.setOpaque(false);
        btnSearch.setContentAreaFilled(false);
        btnSearch.setBorderPainted(false);
        
        btnSearchEquip.setOpaque(false);
        btnSearchEquip.setContentAreaFilled(false);
        btnSearchEquip.setBorderPainted(false);
    }
    
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
            java.util.logging.Logger.getLogger(FrmComputadoras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmComputadoras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmComputadoras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmComputadoras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FrmComputadoras().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnClose2;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnReturn1;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnSearchEquip;
    private javax.swing.JComboBox<String> cboCargador;
    private javax.swing.JComboBox<String> cboMaletin;
    private javax.swing.JComboBox<String> cboModelo;
    private javax.swing.JComboBox<String> cboOtros;
    private javax.swing.JTable dtClientes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lblBackground;
    private javax.swing.JLabel lblCargador;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblDireccion1;
    private javax.swing.JLabel lblDireccion2;
    private javax.swing.JLabel lblFecha;
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
    private javax.swing.JLabel lblServiceTag2;
    private javax.swing.JLabel lblServiceTag5;
    private javax.swing.JLabel lblServiceTag6;
    private javax.swing.JLabel lblTelefono;
    private java.awt.Panel panel2;
    private javax.swing.JTable tblEquipoCliente1;
    private javax.swing.JTextField txtAnticipo;
    private javax.swing.JTextField txtBuscarEquipoCliente;
    private javax.swing.JTextField txtCosto;
    private javax.swing.JTextArea txtDiagnostic;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtFinal;
    private javax.swing.JTextField txtIdCliente;
    private javax.swing.JTextField txtIdentidadCliente;
    private javax.swing.JTextField txtIngreseNombreCliente;
    private javax.swing.JTextField txtNombreCliente;
    private javax.swing.JTextField txtPassword;
    private javax.swing.JTextArea txtProblema;
    private javax.swing.JTextField txtServiceTag;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
