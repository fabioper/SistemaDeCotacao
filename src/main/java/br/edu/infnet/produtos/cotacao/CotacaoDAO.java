package br.edu.infnet.produtos.cotacao;

import br.edu.infnet.dados.FabricaDeConexoes;
import br.edu.infnet.produtos.produto.Produto;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CotacaoDAO {
    public final Cotacao inserir(Cotacao cotacao) {
        try (var conexao = FabricaDeConexoes.conectar()) {
            var sql = "INSERT INTO cotacoes (cotacoes.produto_id, cotacoes.data, cotacoes.preco)" +
                    "   VALUES (?, ?, ?)";

            try (var statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                System.out.println(cotacao.getProduto().getId());
                statement.setInt(1, cotacao.getProduto().getId());
                statement.setDate(2, new Date(cotacao.getData().getTime()));
                statement.setDouble(3, cotacao.getPreco());
                statement.executeUpdate();

                var resultado = statement.getGeneratedKeys();
                if (resultado != null && resultado.next()) {
                    return obterPorId(resultado.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public final Cotacao alterar(int id, Cotacao cotacao) {
        try (var conexao = FabricaDeConexoes.conectar()) {
            var sql = "UPDATE cotacoes SET cotacoes.preco = ?, cotacoes.data = ?, cotacoes.produto_id = ?";

            try (var statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setDouble(1, cotacao.getPreco());
                statement.setDate(2, (Date) cotacao.getData());
                statement.setInt(3, cotacao.getProduto().getId());
                statement.executeUpdate();

                var resultado = statement.getGeneratedKeys();
                if (resultado != null && resultado.next()) {
                    return obterPorId(resultado.getInt(0));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public final void excluir(int id) {
        try (var conn = FabricaDeConexoes.conectar()) {
            var sql = "DELETE FROM cotacoes WHERE id=?";
            try (var statement = conn.prepareStatement(sql)) {
                statement.setInt(1, id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public final List<Cotacao> listar() {
        try (var conexao = FabricaDeConexoes.conectar()) {
            var query = "SELECT cotacoes.id, cotacoes.produto_id, cotacoes.data, cotacoes.preco, " +
                    "   produtos.nome, produtos.fornecedor FROM cotacoes" +
                    "   JOIN produtos ON cotacoes.produto_id=produtos.id";

            var statement = conexao.createStatement();
            try (var cotacoesRetorno = statement.executeQuery(query)) {
                var cotacoes = new ArrayList<Cotacao>();
                while (cotacoesRetorno.next()) {
                    cotacoes.add(mapearCotacao(cotacoesRetorno));
                }
                return cotacoes;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public final List<Cotacao> listarPorProduto(Produto produto) {
        try (var conn = FabricaDeConexoes.conectar()) {
            var sql = "SELECT cotacoes.id, cotacoes.produto_id, cotacoes.data," +
                    "   produtos.nome, produtos.fornecedor, cotacoes.preco" +
                    "   FROM cotacoes " +
                    "   JOIN produtos ON cotacoes.produto_id=produtos.id " +
                    "   WHERE produtos.nome=?";

            try (var statement = conn.prepareStatement(sql)) {
                statement.setString(1, produto.getNome());

                var retorno = statement.executeQuery();

                var cotacoes = new ArrayList<Cotacao>();
                while (retorno.next()) {
                    cotacoes.add(mapearCotacao(retorno));
                }
                return cotacoes;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public final Cotacao obterPorId(int id) {
        try (var conn = FabricaDeConexoes.conectar()) {
            var sql = "SELECT cotacoes.id, cotacoes.produto_id, cotacoes.data," +
                    "   produtos.nome, produtos.fornecedor, cotacoes.preco" +
                    "   FROM cotacoes " +
                    "   JOIN produtos ON cotacoes.produto_id=produtos.id " +
                    "   WHERE cotacoes.id=?";
            try (var statement = conn.prepareStatement(sql)) {
                statement.setInt(1, id);

                var retorno = statement.executeQuery();

                if (retorno != null && retorno.next()) {
                    return mapearCotacao(retorno);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Cotacao mapearCotacao(ResultSet retorno) throws SQLException {
        var produto = new Produto();
        produto.setId(retorno.getInt("produto_id"));
        produto.setNome(retorno.getString("nome"));
        produto.setFornecedor(retorno.getString("fornecedor"));

        var cotacao = new Cotacao();
        cotacao.setId(retorno.getInt("id"));
        cotacao.setPreco(retorno.getDouble("preco"));
        cotacao.setData(retorno.getDate("data"));
        cotacao.setProduto(produto);

        return cotacao;
    }
}
