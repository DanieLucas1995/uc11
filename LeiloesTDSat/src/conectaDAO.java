

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;



public class conectaDAO {
    
    private Connection conexao;
    
public void conectar() {
    try {
        System.out.println("Connecting: Initializing connection to the database...");
        Class.forName("com.mysql.cj.jdbc.Driver");
        
        // Adicione as propriedades para lidar com o aviso SSL
        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "Daniel");
        props.setProperty("useSSL", "false"); // Desabilita o uso de SSL

        conexao = DriverManager.getConnection("jdbc:mysql://localhost/uc11", props);

        System.out.println("Connected!");
    } catch (SQLException e) {
        System.out.println("Fail: Cannot connect");
        e.printStackTrace(); // Isso ajuda a identificar problemas específicos
    } catch (ClassNotFoundException e) {
        System.out.println("Fail to find the connection class");
        e.printStackTrace();
    }
}

    public void desconectar() {
        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
                System.out.println("Disconnected with success!");
            }
        } catch (SQLException e) {
            System.out.println("Error to disconnect");
        }
    }
    
    public Connection getConexao() {
        return conexao;
    }
    

}


 
