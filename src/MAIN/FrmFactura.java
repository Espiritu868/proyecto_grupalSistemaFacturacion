
package MAIN;

import DAO.Conexion;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static java.util.stream.Collectors.toList;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ever Chavez
 */
public class FrmFactura extends javax.swing.JFrame {


    public FrmFactura() {
        initComponents();
        this.setLocationRelativeTo(null);
        transparentButton();
        jPanel5.setVisible(false);
        jPanel3.setVisible(false);
        
        //Llamamos la tabla de agregar producto
        this.inicializarTablaProducto();
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
                rs = st.executeQuery("select * from clientes where clientName like '%" + txtFinalClient.getText() + "%'");

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
        private void limpiarCamposCliente() {
        txtClientCode.setText("");
        txtFinalClient.setText("");
        txtRtn.setText("");
        txtDirection.setText("");
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
                    txtClientCode.setText(rs.getString("id"));
                    txtFinalClient.setText(rs.getString("clientName"));
                    txtRtn.setText(rs.getString("rtnClient"));
                    txtDirection.setText(rs.getString("adressClient"));

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
            public void buscarProducto(){

            Object dataClient[] = new Object[3];
            modelo = (DefaultTableModel) tblProducto.getModel();
            Connection con = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            Statement st = null;
            modelo.setRowCount(0);

            try {
                con = conn.getConexion();
                st = con.createStatement();
                rs = st.executeQuery("select * from productos where productName like '%" + txtBuscarProducto.getText() + "%'");

                while(rs.next()){
                    dataClient[0] = rs.getString("id");
                    dataClient[1] = rs.getString("productName");
                    dataClient[2] = rs.getString("productCode");

                    modelo.addRow(dataClient);

                    tblProducto.setModel(modelo);

                }
            } catch (Exception e) {
                System.out.println("Error en la consulta. problema en BuscarCliente");
            }

        }
            
        //Modelo de los datos 
            
            private DefaultTableModel modeloDatosProductos;
        // METODO PARA INICIALIZAR LA TABLA
        private void inicializarTablaProducto(){
            modeloDatosProductos = new DefaultTableModel();
            //añadir  columnas 
            
            modeloDatosProductos.addColumn("N");
            modeloDatosProductos.addColumn("Nombre");
            modeloDatosProductos.addColumn("Cantidad");
            modeloDatosProductos.addColumn("P. Unitario");
            modeloDatosProductos.addColumn("SubTotal");
            modeloDatosProductos.addColumn("Descuento");
            modeloDatosProductos.addColumn("Iva");
            modeloDatosProductos.addColumn("Total Pagar");
            modeloDatosProductos.addColumn("Acción");
            
            //agregar los datos del modelo a la tabla 
            
            this.jTableProductos.setModel(modeloDatosProductos);
            
        }
       

            
 
            
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        btnReturn = new javax.swing.JButton();
        btnClose = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        lblPhone3 = new javax.swing.JLabel();
        lblPhone5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        lblPhone7 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        dtClientes = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblProducto = new javax.swing.JTable();
        btnSave = new javax.swing.JButton();
        btnSearchFactureNumber = new javax.swing.JButton();
        btnSearch = new javax.swing.JButton();
        cboFactureState = new javax.swing.JComboBox<>();
        txtNumeroFactura = new javax.swing.JTextField();
        txtDirection = new javax.swing.JTextField();
        txtRtn = new javax.swing.JTextField();
        btnSave1 = new javax.swing.JButton();
        lblPhone8 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTableProductos = new javax.swing.JTable();
        txtClientCode = new javax.swing.JTextField();
        txtFinalClient = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        cboFormaDePago = new javax.swing.JComboBox<>();
        txtTotal = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtEfectivo = new javax.swing.JTextField();
        txtCambio = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        txtBuscarProducto = new javax.swing.JTextField();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblBackground = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Exotc350 Bd BT", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Numero de Factura");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 100, -1, -1));

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
        jPanel1.add(btnReturn, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 600, 80, 70));

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
        jPanel1.add(btnClose, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 600, 80, 70));

        jLabel3.setIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Imagen_de_WhatsApp_2024-10-28_a_las_13.58.31_647d7da7-removebg-preview (1).png")); // NOI18N
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(-30, -40, -1, -1));

        lblPhone3.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblPhone3.setForeground(new java.awt.Color(255, 255, 255));
        lblPhone3.setText("REGRESAR");
        jPanel1.add(lblPhone3, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 690, -1, -1));

        lblPhone5.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblPhone5.setForeground(new java.awt.Color(255, 255, 255));
        lblPhone5.setText("CERRAR");
        jPanel1.add(lblPhone5, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 690, -1, -1));

        jLabel6.setFont(new java.awt.Font("Exotc350 Bd BT", 0, 24)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("RTN");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 180, -1, -1));

        jLabel7.setFont(new java.awt.Font("Exotc350 Bd BT", 0, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Direccion");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 180, 150, -1));

        lblPhone7.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblPhone7.setForeground(new java.awt.Color(255, 255, 255));
        lblPhone7.setText("IMPRIMIR");
        jPanel1.add(lblPhone7, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 690, -1, -1));

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
            dtClientes.getColumnModel().getColumn(0).setPreferredWidth(30);
            dtClientes.getColumnModel().getColumn(0).setMaxWidth(50);
        }

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1050, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 124, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 220, 1050, 130));

        tblProducto.setBackground(new java.awt.Color(255, 255, 255));
        tblProducto.setForeground(new java.awt.Color(0, 0, 0));
        tblProducto.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NOMBRE", "CODIGO"
            }
        ));
        tblProducto.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblProductoMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tblProducto);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 890, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 440, 890, 150));

        btnSave.setBackground(new java.awt.Color(255, 204, 51));
        btnSave.setForeground(new java.awt.Color(255, 204, 51));
        btnSave.setIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\print64x64.png")); // NOI18N
        btnSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSave.setPressedIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\print64x64.png")); // NOI18N
        btnSave.setRolloverIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\print72x72.png")); // NOI18N
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });
        jPanel1.add(btnSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 600, 80, 70));

        btnSearchFactureNumber.setIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\search32x32.png")); // NOI18N
        btnSearchFactureNumber.setPressedIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\search32x32.png")); // NOI18N
        btnSearchFactureNumber.setRolloverIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\search48x48.png")); // NOI18N
        jPanel1.add(btnSearchFactureNumber, new org.netbeans.lib.awtextra.AbsoluteConstraints(920, 90, 70, 50));

        btnSearch.setIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\search32x32.png")); // NOI18N
        btnSearch.setPressedIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\search32x32.png")); // NOI18N
        btnSearch.setRolloverIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\search48x48.png")); // NOI18N
        jPanel1.add(btnSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(1050, 130, 70, 50));

        cboFactureState.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Factura Activa", "Factura Inactiva" }));
        cboFactureState.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel1.add(cboFactureState, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 60, 160, 30));

        txtNumeroFactura.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNumeroFacturaKeyPressed(evt);
            }
        });
        jPanel1.add(txtNumeroFactura, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 100, 400, 30));
        jPanel1.add(txtDirection, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 180, 530, 30));

        txtRtn.setEditable(false);
        jPanel1.add(txtRtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 180, 160, 30));

        btnSave1.setBackground(new java.awt.Color(255, 204, 51));
        btnSave1.setForeground(new java.awt.Color(255, 204, 51));
        btnSave1.setIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\SAVE48X48.png")); // NOI18N
        btnSave1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSave1.setPressedIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\SAVE48X48.png")); // NOI18N
        btnSave1.setRolloverIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\save64x64.png")); // NOI18N
        btnSave1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSave1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnSave1, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 600, 80, 70));

        lblPhone8.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblPhone8.setForeground(new java.awt.Color(255, 255, 255));
        lblPhone8.setText("GUARDAR");
        jPanel1.add(lblPhone8, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 690, -1, -1));

        jLabel10.setFont(new java.awt.Font("Exotc350 Bd BT", 0, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Codigo Cliente");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 100, -1, -1));

        jLabel9.setFont(new java.awt.Font("Exotc350 Bd BT", 0, 24)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Cliente Final");
        jPanel1.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 140, 180, -1));

        jTableProductos.setBackground(new java.awt.Color(255, 255, 255));
        jTableProductos.setForeground(new java.awt.Color(255, 255, 255));
        jTableProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "NOMBRE", "CODIGO", "CANTIDAD", "PRECIO"
            }
        ));
        jScrollPane2.setViewportView(jTableProductos);
        if (jTableProductos.getColumnModel().getColumnCount() > 0) {
            jTableProductos.getColumnModel().getColumn(0).setPreferredWidth(50);
            jTableProductos.getColumnModel().getColumn(0).setMaxWidth(50);
            jTableProductos.getColumnModel().getColumn(2).setPreferredWidth(200);
            jTableProductos.getColumnModel().getColumn(2).setMaxWidth(200);
            jTableProductos.getColumnModel().getColumn(3).setPreferredWidth(80);
            jTableProductos.getColumnModel().getColumn(3).setMaxWidth(80);
            jTableProductos.getColumnModel().getColumn(4).setPreferredWidth(70);
            jTableProductos.getColumnModel().getColumn(4).setMaxWidth(70);
        }

        jPanel1.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 230, 1060, 360));

        txtClientCode.setEditable(false);
        jPanel1.add(txtClientCode, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 140, 160, 30));

        txtFinalClient.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtFinalClientKeyPressed(evt);
            }
        });
        jPanel1.add(txtFinalClient, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 140, 530, 30));

        jPanel2.setBackground(new java.awt.Color(255, 204, 102));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("FORMA DE PAGO");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, 150, 20));

        jLabel8.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("OBSERVACIONES");
        jPanel2.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 360, 160, 20));

        cboFormaDePago.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "EFECTIVO", "TRANSFERENCIA", "TARJETA VISA/DEBITO", " " }));
        jPanel2.add(cboFormaDePago, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, 280, 40));

        txtTotal.setEditable(false);
        txtTotal.setForeground(new java.awt.Color(0, 0, 0));
        txtTotal.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtTotal.setText("1");
        jPanel2.add(txtTotal, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, 280, 30));

        jLabel11.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("TOTAL A PAGAR");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 40, 150, 20));

        jLabel12.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("EFECTIVO");
        jPanel2.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 200, 150, 20));

        txtEfectivo.setForeground(new java.awt.Color(0, 0, 0));
        txtEfectivo.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtEfectivo.setText("1");
        txtEfectivo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtEfectivoActionPerformed(evt);
            }
        });
        jPanel2.add(txtEfectivo, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 230, 280, 30));

        txtCambio.setEditable(false);
        txtCambio.setForeground(new java.awt.Color(0, 0, 0));
        txtCambio.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        txtCambio.setText("1");
        txtCambio.setToolTipText("");
        jPanel2.add(txtCambio, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 310, 280, 30));

        jLabel13.setFont(new java.awt.Font("Dialog", 0, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("CAMBIO");
        jPanel2.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 280, 150, 20));

        jScrollPane4.setViewportView(jTextPane1);

        jPanel2.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 390, 280, 280));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(1150, 10, 370, 700));

        txtBuscarProducto.setText("Buscar Producto");
        txtBuscarProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarProductoKeyPressed(evt);
            }
        });
        jPanel1.add(txtBuscarProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 630, 230, 40));

        jPanel4.setBackground(new java.awt.Color(255, 204, 153));
        jPanel4.setForeground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Exotc350 Bd BT", 0, 36)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Facturas SairCell");

        jLabel5.setFont(new java.awt.Font("Exotc350 Bd BT", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("12/1/2024");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(95, 95, 95)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(105, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel4)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 38, Short.MAX_VALUE)
                .addComponent(jLabel5))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jLabel4)
                    .addGap(0, 17, Short.MAX_VALUE)))
        );

        jPanel1.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 30, 290, 60));

        lblBackground.setBackground(new java.awt.Color(255, 255, 102));
        lblBackground.setForeground(new java.awt.Color(255, 255, 102));
        lblBackground.setIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\cat1600x900 (1).jpg")); // NOI18N
        lblBackground.setText(".");
        jPanel1.add(lblBackground, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1520, 720));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnReturnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReturnActionPerformed
        FrmInicio open = new FrmInicio();

        open.setVisible(true);
        this.setVisible(false);
    }//GEN-LAST:event_btnReturnActionPerformed

    private void btnCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCloseActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnCloseActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnSave1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSave1ActionPerformed
      
        
    }//GEN-LAST:event_btnSave1ActionPerformed

    private void txtEfectivoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtEfectivoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtEfectivoActionPerformed

    private void dtClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dtClientesMouseClicked
        limpiarCamposCliente();
        int fila = dtClientes.getSelectedRow();  // Obtener la fila seleccionada
        String id = dtClientes.getValueAt(fila, 0).toString().trim();  // Obtener el ID y eliminar espacios en blanco

        System.out.println("ID obtenido de la tabla: " + id);  // Verificar el valor del ID

        mostrarCliente(id);  // Pasar el id al método mostrarCliente
        
        jPanel5.setVisible(false);  // Ocultar el panel (si es necesario)        
    }//GEN-LAST:event_dtClientesMouseClicked

    private void dtClientesKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_dtClientesKeyPressed
        
    }//GEN-LAST:event_dtClientesKeyPressed

    private void txtNumeroFacturaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroFacturaKeyPressed
        
    }//GEN-LAST:event_txtNumeroFacturaKeyPressed

    private void txtFinalClientKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFinalClientKeyPressed
        jPanel5.setVisible(true);
        buscarCliente();
    }//GEN-LAST:event_txtFinalClientKeyPressed

    private void tblProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProductoMouseClicked
        int combo = this.tblProducto.getSelectedColumn();
        //validamos que selecciono un producto
        
    }//GEN-LAST:event_tblProductoMouseClicked

    private void txtBuscarProductoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarProductoKeyPressed
        buscarProducto();
        jPanel3.setVisible(true);
    }//GEN-LAST:event_txtBuscarProductoKeyPressed

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
            java.util.logging.Logger.getLogger(FrmFactura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmFactura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmFactura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmFactura.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

       
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmFactura().setVisible(true);
            }
        });
    }
    
    public void transparentButton(){
        btnSearch.setOpaque(false);
        btnSearch.setContentAreaFilled(false);
        btnSearch.setBorderPainted(false);
        
        btnSearchFactureNumber.setOpaque(false);
        btnSearchFactureNumber.setContentAreaFilled(false);
        btnSearchFactureNumber.setBorderPainted(false);
    }
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnClose;
    private javax.swing.JButton btnReturn;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSave1;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnSearchFactureNumber;
    private javax.swing.JComboBox<String> cboFactureState;
    private javax.swing.JComboBox<String> cboFormaDePago;
    private javax.swing.JTable dtClientes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTable jTableProductos;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JLabel lblBackground;
    private javax.swing.JLabel lblPhone3;
    private javax.swing.JLabel lblPhone5;
    private javax.swing.JLabel lblPhone7;
    private javax.swing.JLabel lblPhone8;
    private javax.swing.JTable tblProducto;
    private javax.swing.JTextField txtBuscarProducto;
    private javax.swing.JTextField txtCambio;
    private javax.swing.JTextField txtClientCode;
    private javax.swing.JTextField txtDirection;
    private javax.swing.JTextField txtEfectivo;
    private javax.swing.JTextField txtFinalClient;
    private javax.swing.JTextField txtNumeroFactura;
    private javax.swing.JTextField txtRtn;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables
}
