import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class Database {
    private Connection connection;
    private Statement statement;

    // Constructor
    public Database() {
        try {
            // Memuat driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Koneksi ke MySQL
            String url = "jdbc:mysql://localhost:3306/db_mahasiswa"; // Ganti dengan nama database kamu
            String user = "root";  // Ganti dengan username MySQL
            String password = "Putri1234#.,";  // Ganti dengan password MySQL

            connection = DriverManager.getConnection(url, user, password);
            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException("Gagal terkoneksi ke database!", e);
        }
    }

    // Digunakan untuk SELECT query
    public ResultSet selectQuery(String sql) {
        try {
            return statement.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Gagal menjalankan query SELECT!", e);
        }
    }

    // Digunakan untuk INSERT, UPDATE, DELETE
    public int insertUpdateDeleteQuery(String sql) {
        try {
            return statement.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Gagal menjalankan query INSERT/UPDATE/DELETE!", e);
        }
    }

    // Getter untuk Statement
    public Statement getStatement() {
        return statement;
    }


}
