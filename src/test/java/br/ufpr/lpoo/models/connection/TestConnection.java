package br.ufpr.lpoo.models.connection;

import org.junit.Assert;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TestConnection {
    @Test
    public void testConnection() {
        try {
            Connection connection = ConnectionFactory.getConnection();
            Assert.assertNotNull(connection);
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM cliente WHERE cpf = ?;");
            stmt.setString(1, "63287208071");
            ResultSet rs = stmt.executeQuery();
            Assert.assertNotNull(rs);
            if (rs.next()) {
                Assert.assertEquals("63287208071", rs.getString("cpf"));
                Assert.assertEquals("Leonardo", rs.getString("nome"));
            }
        } catch (SQLException e) {
            Assert.fail("Erro ao conectar ao banco de dados: " + e.getMessage());
        }

    }
}
