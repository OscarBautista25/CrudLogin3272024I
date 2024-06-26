import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Usuario extends JFrame{
    private JPanel panelUsuario;
    private JTextField idText;
    private JTextField nombreText;
    private JTextField rolText;
    private JButton consultarBoton;
    private JButton ingresarBoton;
    private JButton actualizarBoton;
    private JButton eliminarBoton;
    private JList lista;
    private JTable tablaDatos;
    Connection conexion;
    DefaultListModel mod = new DefaultListModel<>();
    PreparedStatement ps;
    String[] campos = {"id", "nombre", "rol"};
    String[] registros = new String[10];
    DefaultTableModel modTab =new DefaultTableModel(null, campos);
    Statement st;
    ResultSet r;


    public Usuario() {
        consultarBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    consultar();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        ingresarBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    insertar();
                } catch (SQLException ex) {

                }
            }
        });
        actualizarBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    actualizar();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
        eliminarBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    eliminar();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }

    void conectar() {
        try {
            conexion = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/usuario327", "root", "1234");
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }
    void consultar() throws SQLException{
        conectar();
        modTab.setRowCount(0);
        tablaDatos.setModel(modTab);
        st = conexion.createStatement();
        r = st.executeQuery("select id, nombre, rol from usuario");
        while(r.next()){
            registros[0] =r.getString("id");
            registros[1] =r.getString("nombre");
            registros[2] =r.getString("rol");
            modTab.addRow(registros);
        }
    }
    void insertar() throws SQLException{
        conectar();
        ps = conexion.prepareStatement("insert into usuario(id, nombre, rol) values (?,?,?)");
        ps.setInt(1, Integer.parseInt(idText.getText()));
        ps.setString(2, nombreText.getText());
        ps.setString(3, rolText.getText());
        if (ps.executeUpdate()>0){
            lista.setModel(mod);
            mod.removeAllElements();
            mod.addElement("Elemento agregado");

            idText.setText("");
            nombreText.setText("");
            rolText.setText("");
            consultar();
        }
    }
    void actualizar() throws SQLException{
        conectar();
        ps = conexion.prepareStatement("update usuario set nombre=?, rol=? where id=?");
        ps.setString(1, nombreText.getText());
        ps.setString(2, rolText.getText());
        ps.setInt(3, Integer.parseInt(idText.getText()));
        if (ps.executeUpdate()>0){
            lista.setModel(mod);
            mod.removeAllElements();
            mod.addElement("Elemento Actualizado");

            idText.setText("");
            nombreText.setText("");
            rolText.setText("");
            consultar();
        }
    }
    void eliminar() throws SQLException{
        conectar();
        ps = conexion.prepareStatement("delete from usuario where id=?");
        ps.setInt(1, Integer.parseInt(idText.getText()));
        if (ps.executeUpdate()>0){
            lista.setModel(mod);
            mod.removeAllElements();
            mod.addElement("Elemento Eliminado");

            idText.setText("");
            nombreText.setText("");
            rolText.setText("");
            consultar();
        }
    }

    public void mostrarVentanaUsuario(){
        Usuario usuario1 = new Usuario();
        usuario1.setContentPane(new Usuario().panelUsuario);
        usuario1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        usuario1.setVisible(true);
        usuario1.pack();
    }

   /* public static void main(String[] args) {
        Usuario usuario1 = new Usuario();
        usuario1.setContentPane(new Usuario().panelUsuario);
        usuario1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        usuario1.setVisible(true);
        usuario1.pack();
    }*/


}
