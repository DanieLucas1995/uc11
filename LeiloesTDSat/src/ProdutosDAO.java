import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProdutosDAO {

    Connection conn;
    PreparedStatement prep;
    ArrayList<ProdutosDTO> listagem = new ArrayList<>();

    public ProdutosDAO() {
        // Inicializa a conexão no construtor
        conectaDAO conexaoDAO = new conectaDAO();
        conexaoDAO.conectar();
        conn = conexaoDAO.getConexao();
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
        return listagem;
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
