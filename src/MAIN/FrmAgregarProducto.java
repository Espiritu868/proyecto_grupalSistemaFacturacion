
package MAIN;

import DAO.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static java.util.stream.Collectors.toList;
import javax.swing.JOptionPane;
import javax.swing.plaf.PanelUI;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Ever Chavez
 */
public class FrmAgregarProducto extends javax.swing.JFrame {
    
    public FrmAgregarProducto() {
        initComponents();
        this.setLocationRelativeTo(null);
        transparentButton();
        jPanel1.setVisible(false);
        txtIdProducto.setVisible(false);
        btnActualizar.setEnabled(false);
        
    }
    DefaultTableModel modelo;
        
    Conexion conn = new Conexion("proyecto");
    String mensaje = "";
            
            public void mostrarInicio(){
                    FrmInicio open = new FrmInicio();

                    open.setVisible(true);
                    this.setVisible(false);
            }
    
            public void InsertNewProduct() {
            Connection con = null;
            PreparedStatement ps = null;

            try {
                // Capturar los valores de los campos de texto
                String name = txtProductName.getText().trim();
                String code = txtCode.getText().trim();
                Integer costo = Integer.parseInt(txtPriceCost.getText().trim());
                Integer venta = Integer.parseInt(txtPriceShop.getText().trim()); 
                Integer cantidad = Integer.parseInt(txtQuantityInStock.getText().trim()); 

                // Establecer la conexión con la base de datos
                con = conn.getConexion();
                String sql = "INSERT INTO productos (productCode, productName, precioCoste, precioVenta, cantidadStock) VALUES (?, ?, ?, ?,?)";
                ps = con.prepareStatement(sql);

                // Asignar los valores a los parámetros de la consulta
                ps.setString(1, code);
                ps.setString(2, name);
                ps.setInt(3, costo);
                ps.setInt(4, venta);
                ps.setInt(5, cantidad);

                // Ejecutar la inserción y verificar el resultado
                int rowsInserted = ps.executeUpdate();

                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "¡Datos insertados exitosamente!");
                    toList(); // Refrescar la lista si es necesario
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
                rs = st.executeQuery("select * from productos where productName like '%" + txtNombreProducto.getText() + "%'");

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
            
        public void mostrarProducto(String Id){


                Connection con = null;

                ResultSet rs = null;
                Statement st = null;


                try {
                    con = conn.getConexion();
                    st = con.createStatement();
                    rs = st.executeQuery("select * from productos where id = '"+ Id +"'");

                    while(rs.next()){
                        txtIdProducto.setText(rs.getString("id"));
                        txtCode.setText(rs.getString("productCode"));
                        txtProductName.setText(rs.getString("productName"));
                        txtPriceCost.setText(rs.getString("precioCoste"));
                        txtPriceShop.setText(rs.getString("precioVenta"));
                        txtQuantityInStock.setText(rs.getString("cantidadStock"));
                    }
                } catch (Exception e) {
                    System.out.println("Error en la consulta.");
                }

            } 
        public void ModificarProducto() {

            Connection con = null;
            PreparedStatement ps = null;

            try {
                
                String name = txtProductName.getText();       
                String code = txtCode.getText();              
                int costo = Integer.parseInt(txtPriceCost.getText());  
                int shop = Integer.parseInt(txtPriceShop.getText());   
                int cantidadStock = Integer.parseInt(txtQuantityInStock.getText());  

                
                String idText = txtIdProducto.getText();  
                if (idText == null || idText.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El ID no puede estar vacío.");
                    return;
                }

                int idProducto;
                try {
                    idProducto = Integer.parseInt(idText);  
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(this, "El ID debe ser un número válido.");
                    return;
                }

                
                con = conn.getConexion();

                
                String sql = "UPDATE productos SET productCode = ?, productName = ?, precioCoste = ?, precioVenta = ?, cantidadStock = ? WHERE id = ?";
                ps = con.prepareStatement(sql);

                
                ps.setString(1, code);          
                ps.setString(2, name);          
                ps.setInt(3, costo);            
                ps.setInt(4, shop);             
                ps.setInt(5, cantidadStock);    
                ps.setInt(6, idProducto);       

                
                int rowsUpdated = ps.executeUpdate();

                
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(this, "¡Datos actualizados exitosamente!");
                    toList();  
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

            public void deleteProducto (){
    
        Connection con = null;
        PreparedStatement ps = null;

        try {
            // Obtener la fila seleccionada
            int selectedRow = tblProducto.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Por favor, selecciona un producto para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
                return; // Salir si no hay selección
            }

            // Obtener el ID del cliente seleccionado desde la tabla
            int idClient = Integer.parseInt(tblProducto.getValueAt(selectedRow, 0).toString()); // Suponiendo que el ID está en la primera columna

            // Confirmar la eliminación
            int confirm = JOptionPane.showConfirmDialog(this, "¿Estás seguro de que deseas eliminar este cliente?", "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) {
                return; // Salir si el usuario no confirma
            }

            con = conn.getConexion();

            String sql = "DELETE FROM productos WHERE id = ?";
            ps = con.prepareStatement(sql);
            ps.setInt(1, idClient);

            int rowsDeleted = ps.executeUpdate();

            if (rowsDeleted > 0) {
                JOptionPane.showMessageDialog(this, "¡Producto eliminado exitosamente!");
                
                toList(); // Actualizar la tabla
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

        jPanel = new javax.swing.JPanel();
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
        txtIdProducto = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProducto = new javax.swing.JTable();
        btnActualizar = new javax.swing.JButton();
        lblPhone8 = new javax.swing.JLabel();
        btnEliminar = new javax.swing.JButton();
        lblPhone5 = new javax.swing.JLabel();
        lblBackground2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnSearch.setIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\search32x32.png")); // NOI18N
        btnSearch.setPressedIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\search32x32.png")); // NOI18N
        btnSearch.setRolloverIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\Iconos\\search48x48.png")); // NOI18N
        jPanel.add(btnSearch, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 150, 70, 50));

        lblNombreProducto.setFont(new java.awt.Font("Exotc350 Bd BT", 0, 18)); // NOI18N
        lblNombreProducto.setForeground(new java.awt.Color(255, 255, 255));
        lblNombreProducto.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNombreProducto.setText("Buscar Producto");
        jPanel.add(lblNombreProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 160, 230, -1));

        txtNombreProducto.setFont(new java.awt.Font("Exotc350 Bd BT", 0, 14)); // NOI18N
        txtNombreProducto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtNombreProductoKeyPressed(evt);
            }
        });
        jPanel.add(txtNombreProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 160, 450, -1));

        lblCodigo1.setFont(new java.awt.Font("Exotc350 Bd BT", 0, 18)); // NOI18N
        lblCodigo1.setForeground(new java.awt.Color(255, 255, 255));
        lblCodigo1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCodigo1.setText("Codigo");
        jPanel.add(lblCodigo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 240, 220, -1));

        txtCode.setFont(new java.awt.Font("Exotc350 Bd BT", 0, 14)); // NOI18N
        jPanel.add(txtCode, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 240, 130, -1));

        lblCodigo4.setFont(new java.awt.Font("Exotc350 Bd BT", 0, 18)); // NOI18N
        lblCodigo4.setForeground(new java.awt.Color(255, 255, 255));
        lblCodigo4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCodigo4.setText("Precio Costo");
        jPanel.add(lblCodigo4, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 240, 220, -1));

        txtPriceCost.setFont(new java.awt.Font("Exotc350 Bd BT", 0, 14)); // NOI18N
        jPanel.add(txtPriceCost, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 240, 130, -1));

        txtQuantityInStock.setFont(new java.awt.Font("Exotc350 Bd BT", 0, 14)); // NOI18N
        jPanel.add(txtQuantityInStock, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 280, 130, -1));

        lblCodigo2.setFont(new java.awt.Font("Exotc350 Bd BT", 0, 18)); // NOI18N
        lblCodigo2.setForeground(new java.awt.Color(255, 255, 255));
        lblCodigo2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCodigo2.setText("Cantidad en Stock");
        jPanel.add(lblCodigo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 280, 220, -1));

        txtPriceShop.setFont(new java.awt.Font("Exotc350 Bd BT", 0, 14)); // NOI18N
        jPanel.add(txtPriceShop, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 280, 130, -1));

        lblCodigo.setFont(new java.awt.Font("Exotc350 Bd BT", 0, 18)); // NOI18N
        lblCodigo.setForeground(new java.awt.Color(255, 255, 255));
        lblCodigo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblCodigo.setText("Precio Venta");
        jPanel.add(lblCodigo, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 280, 220, -1));

        lblNombreProducto1.setFont(new java.awt.Font("Exotc350 Bd BT", 0, 18)); // NOI18N
        lblNombreProducto1.setForeground(new java.awt.Color(255, 255, 255));
        lblNombreProducto1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblNombreProducto1.setText(" Nombre  Producto");
        jPanel.add(lblNombreProducto1, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 200, 230, -1));

        txtProductName.setFont(new java.awt.Font("Exotc350 Bd BT", 0, 14)); // NOI18N
        jPanel.add(txtProductName, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 200, 450, -1));

        lblPhone7.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblPhone7.setForeground(new java.awt.Color(255, 255, 255));
        lblPhone7.setText("GUARDAR");
        jPanel.add(lblPhone7, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 500, -1, -1));

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
        jPanel.add(btnReturn1, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 430, 80, 70));

        lblPhone4.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblPhone4.setForeground(new java.awt.Color(255, 255, 255));
        lblPhone4.setText("REGRESAR");
        jPanel.add(lblPhone4, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 500, -1, -1));

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
        jPanel.add(btnClose1, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 430, 80, 70));

        lblPhone6.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblPhone6.setForeground(new java.awt.Color(255, 255, 255));
        lblPhone6.setText("CERRAR");
        jPanel.add(lblPhone6, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 500, -1, -1));

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
        jPanel.add(btnSave, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 430, 80, 70));

        txtIdProducto.setEditable(false);
        jPanel.add(txtIdProducto, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 90, 100, -1));

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
        jScrollPane1.setViewportView(tblProducto);

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
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
        );

        jPanel.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 320, 650, 80));

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
        jPanel.add(btnActualizar, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 430, 80, 70));

        lblPhone8.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblPhone8.setForeground(new java.awt.Color(255, 255, 255));
        lblPhone8.setText("ACTUALIZAR");
        jPanel.add(lblPhone8, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 500, -1, -1));

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
        jPanel.add(btnEliminar, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 430, 80, 70));

        lblPhone5.setFont(new java.awt.Font("Exotc350 Bd BT", 1, 18)); // NOI18N
        lblPhone5.setForeground(new java.awt.Color(255, 255, 255));
        lblPhone5.setText("ELIMINAR");
        jPanel.add(lblPhone5, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 500, -1, 20));

        lblBackground2.setIcon(new javax.swing.ImageIcon("C:\\Users\\chave\\OneDrive\\Documentos\\UTH\\II Parcial\\Programacion Orientada a Objetos\\PROYECTO GRUPAL\\PROYECTO_GRUPAL\\Pictures\\cat orange (1)redimensionado.jpg")); // NOI18N
        jPanel.add(lblBackground2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnReturn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReturn1ActionPerformed
        
    }//GEN-LAST:event_btnReturn1ActionPerformed

    private void btnClose1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClose1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnClose1ActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        InsertNewProduct();
    }//GEN-LAST:event_btnSaveActionPerformed

    private void tblProductoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblProductoMouseClicked
        int fila =  tblProducto.getSelectedRow();
        String Identificador = tblProducto.getValueAt(fila, 0).toString();
        mostrarProducto(Identificador);
        jPanel1.setVisible(false);
        btnSave.setEnabled(false);
        btnActualizar.setEnabled(true);

    }//GEN-LAST:event_tblProductoMouseClicked

    private void txtNombreProductoKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNombreProductoKeyPressed
        buscarProducto();
        jPanel1.setVisible(true);
    }//GEN-LAST:event_txtNombreProductoKeyPressed

    private void btnActualizarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnActualizarMouseClicked
        btnSave.setVisible(false);
    }//GEN-LAST:event_btnActualizarMouseClicked

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        ModificarProducto();
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
    deleteProducto();

    }//GEN-LAST:event_btnEliminarActionPerformed

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
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnClose1;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnReturn1;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JPanel jPanel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBackground2;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblCodigo1;
    private javax.swing.JLabel lblCodigo2;
    private javax.swing.JLabel lblCodigo4;
    private javax.swing.JLabel lblNombreProducto;
    private javax.swing.JLabel lblNombreProducto1;
    private javax.swing.JLabel lblPhone4;
    private javax.swing.JLabel lblPhone5;
    private javax.swing.JLabel lblPhone6;
    private javax.swing.JLabel lblPhone7;
    private javax.swing.JLabel lblPhone8;
    private javax.swing.JTable tblProducto;
    private javax.swing.JTextField txtCode;
    private javax.swing.JTextField txtIdProducto;
    private javax.swing.JTextField txtNombreProducto;
    private javax.swing.JTextField txtPriceCost;
    private javax.swing.JTextField txtPriceShop;
    private javax.swing.JTextField txtProductName;
    private javax.swing.JTextField txtQuantityInStock;
    // End of variables declaration//GEN-END:variables
}
