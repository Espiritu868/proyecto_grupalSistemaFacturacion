
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
public class FrmComputadoras extends javax.swing.JFrame {
        
    public FrmComputadoras() {
        initComponents();
        this.setLocationRelativeTo(null);
        transparentButton();
        toList();
        jPanel2.setVisible(false);
        panel1.setVisible(false);       
    }
    
    DefaultTableModel modelo;
        
    Conexion conn = new Conexion("proyecto");
    String mensaje = "";
    
        public void mostrarInicio(){
                FrmInicio open = new FrmInicio();

                open.setVisible(true);
                this.setVisible(false);
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

                String problema = txtProblem.getText().trim();
                String diagnostico = txtDiagnostic.getText().trim();

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
                // Cerrar recursos
                try {
                    if (rs != null) rs.close();
                    if (pst != null) pst.close();
                    if (con != null) con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
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
                    txtProblem.setText(rs.getString("equipmentProblem"));
                    txtDiagnostic.setText(rs.getString("diagnostic"));

            }
        } catch (Exception e) {
            System.out.println("Error en la consulta. Error en funcion MostrarEquipo.");
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
        txtProblem = new javax.swing.JTextField();
        lblProblema1 = new javax.swing.JLabel();
        txtDiagnostic = new javax.swing.JTextField();
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
        lblBackground = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("FORMULARIO COMPUTADORAS");

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
            tblEquipoCliente.getColumnModel().getColumn(0).setPreferredWidth(30);
            tblEquipoCliente.getColumnModel().getColumn(1).setPreferredWidth(150);
            tblEquipoCliente.getColumnModel().getColumn(2).setPreferredWidth(250);
            tblEquipoCliente.getColumnModel().getColumn(3).setPreferredWidth(250);
            tblEquipoCliente.getColumnModel().getColumn(4).setPreferredWidth(120);
        }

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 910, Short.MAX_VALUE)
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(panel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 910, 430));

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

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 50, 620, 70));

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
        jPanel1.add(txtProblem, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 260, 680, 60));

        lblProblema1.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblProblema1.setForeground(new java.awt.Color(255, 255, 255));
        lblProblema1.setText("Diagnostico");
        jPanel1.add(lblProblema1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 330, -1, 20));
        jPanel1.add(txtDiagnostic, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 360, 680, 60));

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
        jPanel1.add(txtCosto, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 210, 60, -1));

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
        jPanel1.add(btnReturn1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 430, 80, 70));

        lblPhone4.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblPhone4.setForeground(new java.awt.Color(255, 255, 255));
        lblPhone4.setText("REGRESAR");
        jPanel1.add(lblPhone4, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 500, -1, -1));

        btnClose2.setBackground(new java.awt.Color(255, 204, 51));
        btnClose2.setForeground(new java.awt.Color(255, 204, 51));
        btnClose2.setIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Internal Form Menu\\close_48.png")); // NOI18N
        btnClose2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnClose2.setPressedIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Internal Form Menu\\close_48.png")); // NOI18N
        btnClose2.setRolloverIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Internal Form Menu\\close_72.png")); // NOI18N
        btnClose2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClose2ActionPerformed(evt);
            }
        });
        jPanel1.add(btnClose2, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 430, 80, 70));

        lblPhone6.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblPhone6.setForeground(new java.awt.Color(255, 255, 255));
        lblPhone6.setText("CERRAR");
        jPanel1.add(lblPhone6, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 500, -1, -1));

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
        jPanel1.add(btnSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 430, 80, 70));

        lblPhone7.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblPhone7.setForeground(new java.awt.Color(255, 255, 255));
        lblPhone7.setText("GUARDAR");
        jPanel1.add(lblPhone7, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 500, -1, -1));
        jPanel1.add(txtIdCliente, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 60, -1));

        lblServiceTag6.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblServiceTag6.setForeground(new java.awt.Color(255, 255, 255));
        lblServiceTag6.setText("Anticipo");
        jPanel1.add(lblServiceTag6, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 210, -1, 20));
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
        
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnSearchEquipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchEquipActionPerformed

    }//GEN-LAST:event_btnSearchEquipActionPerformed

    private void dtClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dtClientesMouseClicked
    int fila = dtClientes.getSelectedRow();  // Obtener la fila seleccionada
    String id = dtClientes.getValueAt(fila, 0).toString().trim();  // Obtener el ID y eliminar espacios en blanco

    System.out.println("ID obtenido de la tabla: " + id);  // Verificar el valor del ID

    mostrarCliente(id);  // Pasar el id al método mostrarCliente
    jPanel2.setVisible(false);  // Ocultar el panel (si es necesario)
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
        panel1.setVisible(true);
        buscarEquipoCliente();
    }//GEN-LAST:event_txtBuscarEquipoClienteKeyPressed

    private void txtBuscarEquipoClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txtBuscarEquipoClienteMouseClicked

    }//GEN-LAST:event_txtBuscarEquipoClienteMouseClicked

    private void tblEquipoClienteKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblEquipoClienteKeyPressed

    }//GEN-LAST:event_tblEquipoClienteKeyPressed

    private void tblEquipoClienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblEquipoClienteMouseClicked

        int fila =  tblEquipoCliente.getSelectedRow();
        String Identificador = tblEquipoCliente.getValueAt(fila, 0).toString();
        mostrarEquipo(Identificador);
        panel1.setVisible(false);

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
    private javax.swing.JButton btnClose2;
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
    private javax.swing.JLabel lblPhone6;
    private javax.swing.JLabel lblPhone7;
    private javax.swing.JLabel lblProblema;
    private javax.swing.JLabel lblProblema1;
    private javax.swing.JLabel lblServiceTag2;
    private javax.swing.JLabel lblServiceTag5;
    private javax.swing.JLabel lblServiceTag6;
    private javax.swing.JLabel lblTelefono;
    private java.awt.Panel panel1;
    private javax.swing.JTable tblEquipoCliente;
    private javax.swing.JTextField txtAnticipo;
    private javax.swing.JTextField txtBuscarEquipoCliente;
    private javax.swing.JTextField txtCosto;
    private javax.swing.JTextField txtDiagnostic;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtFinal;
    private javax.swing.JTextField txtIdCliente;
    private javax.swing.JTextField txtIdentidadCliente;
    private javax.swing.JTextField txtIngreseNombreCliente;
    private javax.swing.JTextField txtNombreCliente;
    private javax.swing.JTextField txtPassword;
    private javax.swing.JTextField txtProblem;
    private javax.swing.JTextField txtServiceTag;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
