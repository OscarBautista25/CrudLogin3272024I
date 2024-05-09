import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class EjemploLogin extends JFrame {
    private JPanel panelLogin;
    private JTextField textUsuario;
    private JButton agregarAdministradorButton;
    private JButton validarButton;
    private JPasswordField textPass;
    Connection conexion;
    Statement st;
    ResultSet rs;

    public EjemploLogin() {
        validarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validarAdministrador();
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

    void validarAdministrador() {
        conectar();
        int validacion = 0;
        String usuario = textUsuario.getText();
        String pass = String.valueOf(textPass.getText());
        try {
            st = conexion.createStatement();
            rs = st.executeQuery("Select * from administrador where usuario = '" + usuario + "' and pass = '" + pass + "'");
            if (rs.next()) {
                validacion = 1;
                if (validacion == 1) {
                    JOptionPane.showMessageDialog(null, "Usuario validado exitosamente");
                }

            } else {
                JOptionPane.showMessageDialog(null, "Las credenciales no son correctas");
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error" + e.getMessage());

        }


    }

    public static void main(String[] args) {
        EjemploLogin login1 =  new EjemploLogin();
        login1.setContentPane(new EjemploLogin().panelLogin);
        login1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        login1.setVisible(true);
        login1.pack();

    }


}

