import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import javax.swing.JOptionPane;




public class ProdutosDAO {

    Connection conn;
    PreparedStatement prep;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();
    private Connection conexao;

    public ProdutosDAO() {
        // Inicializa a conexão no construtor
        conectaDAO conexaoDAO = new conectaDAO();
        conexaoDAO.conectar();
        conn = conexaoDAO.getConexao();
    }
    
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
            e.printStackTrace();
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
            e.printStackTrace();
        }
    }

    public Connection getConexao() {
        return conexao;
    }

    public void cadastrarProduto(ProdutosDTO produto) {
        try {
            String sql = "INSERT INTO produtos (nome, valor, status) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setString(1, produto.getNome());
                preparedStatement.setInt(2, produto.getValor());
                preparedStatement.setString(3, produto.getStatus());
                preparedStatement.executeUpdate();
                System.out.println("Cadastro realizado com sucesso!");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar produto: " + e.getMessage());
        }
    }

    public ArrayList<ProdutosDTO> listarProdutos() {
        ArrayList<ProdutosDTO> listagem = new ArrayList<>();

        try {
            // Certifique-se de que a conexão está estabelecida
            if (conexao == null || conexao.isClosed()) {
                conectar();
            }

            String sql = "SELECT * FROM produtos";
            try (PreparedStatement ps = conexao.prepareStatement(sql)) {
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        // Substitua "ProdutosDTO" pelos métodos adequados da sua classe DTO
                        ProdutosDTO produto = new ProdutosDTO();
                        produto.setId(rs.getInt("id"));
                        produto.setNome(rs.getString("nome"));
                        produto.setValor(rs.getInt("valor"));
                        produto.setStatus(rs.getString("status")); // Adicione aqui
                        // Adicione o objeto à lista
                        listagem.add(produto);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Trate a exceção de acordo com a sua necessidade
        }

        return listagem;
    }
    
    public void venderProduto(int id){
      try {
            String sql = "UPDATE produtos SET status=? WHERE Id=?";
            try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
                preparedStatement.setString(1, "vendido");
                preparedStatement.setInt(2, id);
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "Produto atualizado com sucesso!");
            }
        } catch (SQLException ex) {
            System.out.println("Erro ao atualizar produto: " + ex.getMessage());
        }
    
}

    public void fecharConexao() {
        // Adicione um método para fechar a conexão quando necessário
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Conexão fechada com sucesso!");
            }
        } catch (SQLException e) {
            System.out.println("Erro ao fechar a conexão");
        }
    }
}

