package entidades;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;

public class DatoUsuario extends JFrame {

    // Menu
    public JMenuBar mb;
    public JMenu archivo, bd;
    public JMenuItem bd_C, bd_R, bd_U, bd_D, mi_salir;

    // Etiquetas y campos
    public JLabel lb_id, lb_nombre, lb_identidad, lb_cp, lb_ref, lb_desc;
    public JTextField txtId, txtNombre, txtIdentidad, txtCp, txtRef, txtDesc;

    // Botones CRUD
    public JButton btnCrear, btnLeer, btnActualizar, btnEliminar;

    // Este es la ventana: campos, botones y menú
    public DatoUsuario() {
        this.setTitle("CRUD Identidades");
        this.setBounds(150, 50, 800, 600);
        this.getContentPane().setBackground(new Color(30,30,30));
        this.setResizable(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);

        menu();

        // ID
        this.lb_id = new JLabel("ID");
        this.lb_id.setBounds(170, 40, 100, 30);
        this.txtId = new JTextField();
        this.txtId.setBounds(270, 40, 370, 30);
        this.lb_id.setForeground(Color.WHITE);

        // Nombre
        this.lb_nombre = new JLabel("Nombre");
        this.lb_nombre.setBounds(170, 75, 100, 30);
        this.txtNombre = new JTextField();
        this.txtNombre.setBounds(270, 75, 370, 30);
        this.lb_nombre.setForeground(Color.WHITE);
        
        // Identidad
        this.lb_identidad = new JLabel("Identidad");
        this.lb_identidad.setBounds(170, 110, 100, 30);
        this.txtIdentidad = new JTextField();
        this.txtIdentidad.setBounds(270, 110, 370, 30);
        this.lb_identidad.setForeground(Color.WHITE);

        // CP
        this.lb_cp = new JLabel("CP");
        this.lb_cp.setBounds(170, 145, 100, 30);
        this.txtCp = new JTextField();
        this.txtCp.setBounds(270, 145, 370, 30);
        this.lb_cp.setForeground(Color.WHITE);

        // Referencias
        this.lb_ref = new JLabel("Referencias");
        this.lb_ref.setBounds(170, 180, 100, 30);
        this.txtRef = new JTextField();
        this.txtRef.setBounds(270, 180, 370, 30);
        this.lb_ref.setForeground(Color.WHITE);

        // Descripción
        this.lb_desc = new JLabel("Descripción");
        this.lb_desc.setBounds(170, 215, 100, 30);
        this.txtDesc = new JTextField();
        this.txtDesc.setBounds(270, 215, 370, 30);
        this.lb_desc.setForeground(Color.WHITE);

        // Botones
        this.btnCrear = new JButton("Crear");
        this.btnCrear.setBounds(170, 270, 110, 40);
        this.btnCrear.addActionListener((e) -> crear());

        this.btnLeer = new JButton("Leer");
        this.btnLeer.setBounds(290, 270, 110, 40);
        this.btnLeer.addActionListener((e) -> leer());

        this.btnActualizar = new JButton("Actualizar");
        this.btnActualizar.setBounds(410, 270, 110, 40);
        this.btnActualizar.addActionListener((e) -> actualizar());

        this.btnEliminar = new JButton("Eliminar");
        this.btnEliminar.setBounds(530, 270, 110, 40);
        this.btnEliminar.addActionListener((e) -> eliminar());

        // Agregar todo a la ventana
        this.add(lb_id);
        this.add(txtId);
        this.add(lb_nombre);
        this.add(txtNombre);
        this.add(lb_identidad);
        this.add(txtIdentidad);
        this.add(lb_cp);
        this.add(txtCp);
        this.add(lb_ref);
        this.add(txtRef);
        this.add(lb_desc);
        this.add(txtDesc);
        this.add(btnCrear);
        this.add(btnLeer);
        this.add(btnActualizar);
        this.add(btnEliminar);

        this.setVisible(true);
    }

    // Crea el menu despregable con las opciones CRUD
    public final void menu() {
        this.mb = new JMenuBar();
        this.setJMenuBar(this.mb);

        this.archivo = new JMenu("Archivo");
        this.mb.add(this.archivo);

        this.mi_salir = new JMenuItem("Salir");
        this.mi_salir.addActionListener((e) -> System.exit(0));
        this.archivo.add(this.mi_salir);

        this.bd = new JMenu("Base de datos");
        this.mb.add(this.bd);

        this.bd_C = new JMenuItem("C - Crear");
        this.bd_C.addActionListener((e) -> crear());
        this.bd.add(this.bd_C);

        this.bd_R = new JMenuItem("R - Leer");
        this.bd_R.addActionListener((e) -> leer());
        this.bd.add(this.bd_R);

        this.bd_U = new JMenuItem("U - Actualizar");
        this.bd_U.addActionListener((e) -> actualizar());
        this.bd.add(this.bd_U);

        this.bd_D = new JMenuItem("D - Eliminar");
        this.bd_D.addActionListener((e) -> eliminar());
        this.bd.add(this.bd_D);
    }

    // Revisa que no hay campos vacios
    public boolean validar() {
        if (txtNombre.getText().trim().isEmpty()
                || txtIdentidad.getText().trim().isEmpty()
                || txtCp.getText().trim().isEmpty()
                || txtRef.getText().trim().isEmpty()
                || txtDesc.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se permiten campos vacíos");
            return false;
        }
        return true;
    }

    // Inserta un nuevo registro en la tabla identidades
    public void crear() {
        if (!validar()) return;
        try {
            Connection cnx;
            PreparedStatement ps;

            cnx = DriverManager.getConnection("jdbc:mysql://localhost/2026TI32", "root", "");
            ps = cnx.prepareStatement("insert into identidades(nombre, identidad, cp, referencias, descripcion) values (?, ?, ?, ?, ?)");
            ps.setString(1, txtNombre.getText());
            ps.setString(2, txtIdentidad.getText());
            ps.setString(3, txtCp.getText());
            ps.setString(4, txtRef.getText());
            ps.setString(5, txtDesc.getText());

            ps.execute();

            JOptionPane.showMessageDialog(this, "Registro guardado");
            limpiar();
            cnx.close();
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }

    // Busca un registro por ID y llena los campos
    public void leer() {
        try {
            Connection cnx;
            PreparedStatement ps;
            ResultSet rs;

            cnx = DriverManager.getConnection("jdbc:mysql://localhost/2026TI32", "root", "");
            ps = cnx.prepareStatement("select * from identidades where id = ?");
            ps.setInt(1, Integer.parseInt(txtId.getText()));

            rs = ps.executeQuery();

            if (rs.next()) {
                txtNombre.setText(rs.getString("nombre"));
                txtIdentidad.setText(rs.getString("identidad"));
                txtCp.setText(rs.getString("cp"));
                txtRef.setText(rs.getString("referencias"));
                txtDesc.setText(rs.getString("descripcion"));
            } else {
                JOptionPane.showMessageDialog(this, "No existe ese ID");
            }
            cnx.close();
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ID debe ser un número");
        }
    }

    // Modifica el registro con el ID ya escrito
    public void actualizar() {
        if (!validar()) return;
        try {
            Connection cnx;
            PreparedStatement ps;

            cnx = DriverManager.getConnection("jdbc:mysql://localhost/2026TI32", "root", "");
            ps = cnx.prepareStatement("update identidades set nombre=?, identidad=?, cp=?, referencias=?, descripcion=? where id=?");
            ps.setString(1, txtNombre.getText());
            ps.setString(2, txtIdentidad.getText());
            ps.setString(3, txtCp.getText());
            ps.setString(4, txtRef.getText());
            ps.setString(5, txtDesc.getText());
            ps.setInt(6, Integer.parseInt(txtId.getText()));

            ps.execute();

            JOptionPane.showMessageDialog(this, "Registro actualizado");
            limpiar();
            cnx.close();
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ID debe ser un número");
        }
    }

    // Elimina el registro con el ID escrito
    public void eliminar() {
        try {
            Connection cnx;
            PreparedStatement ps;

            cnx = DriverManager.getConnection("jdbc:mysql://localhost/2026TI32", "root", "");
            ps = cnx.prepareStatement("delete from identidades where id = ?");
            ps.setInt(1, Integer.parseInt(txtId.getText()));

            ps.execute();

            JOptionPane.showMessageDialog(this, "Registro eliminado");
            limpiar();
            cnx.close();
        } catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ID debe ser un número");
        }
    }

    // Vacía todos los campos de texto
    public void limpiar() {
        txtId.setText("");
        txtNombre.setText("");
        txtIdentidad.setText("");
        txtCp.setText("");
        txtRef.setText("");
        txtDesc.setText("");
    }

    public static void main(String[] args) {
        new DatoUsuario();
    }
}

    
