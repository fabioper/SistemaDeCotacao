package br.edu.infnet.infra;

import br.edu.infnet.domain.produtos.Produto;
import br.edu.infnet.domain.produtos.ProdutoRepository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ProdutoRepositoryImpl implements ProdutoRepository {
    @Override
    public Produto inserir(Produto produto) {
        try (var conexao = FabricaDeConexoes.conectar()) {
            var sql = "INSERT INTO produtos (nome, fornecedor) VALUES (?, ?)";

            try (var statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, produto.getNome());
                statement.setString(2, produto.getFornecedor());
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

    @Override
    public Produto alterar(Produto produto) {
        try (var conexao = FabricaDeConexoes.conectar()) {
            var sql = "UPDATE produtos SET produtos.nome=?, produtos.fornecedor=? " +
                    "   WHERE produtos.id=?";

            var statement = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, produto.getNome());
            statement.setString(2, produto.getFornecedor());
            statement.setInt(3, produto.getId());
            statement.executeUpdate();

            var resultado = statement.getGeneratedKeys();
            if (resultado != null && resultado.next()) {
                statement.close();
                return obterPorId(resultado.getInt(0));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void excluir(int id) {
        try (var conn = FabricaDeConexoes.conectar()) {
            var sql = "DELETE FROM produtos WHERE id=?";
            try (var statement = conn.prepareStatement(sql)) {
                statement.setInt(1, id);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Produto> listar() {
        try (var conexao = FabricaDeConexoes.conectar()) {
            var query = "SELECT produtos.id, produtos.nome, produtos.fornecedor FROM produtos";
            var statement = conexao.createStatement();
            var produtosRetorno = statement.executeQuery(query);

            var produtos = new ArrayList<Produto>();
            while (produtosRetorno.next()) {
                produtos.add(mapearProduto(produtosRetorno));
            }

            produtosRetorno.close();
            return produtos;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Produto obterPorId(int id) {
        try (var conn = FabricaDeConexoes.conectar()) {
            var sql = "SELECT produtos.id, produtos.nome, produtos.fornecedor" +
                    "   FROM produtos WHERE produtos.id=?";
            try (var statement = conn.prepareStatement(sql)) {
                statement.setInt(1, id);

                var retorno = statement.executeQuery();

                if (retorno != null && retorno.next()) {
                    return mapearProduto(retorno);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public List<Produto> obterPorFornecedor(String fornecedor) {
        try (var conn = FabricaDeConexoes.conectar()) {
            var sql = "SELECT produtos.id, produtos.nome, produtos.fornecedor FROM produtos" +
                    "   WHERE produtos.fornecedor=?";

            var statement = conn.prepareStatement(sql);
            statement.setString(1, fornecedor);

            var retorno = statement.executeQuery();

            var produtos = new ArrayList<Produto>();
            while (retorno.next()) {
                produtos.add(mapearProduto(retorno));
            }

            statement.close();
            return produtos;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Produto mapearProduto(ResultSet retorno) throws SQLException {
        var produto = new Produto();
        produto.setId(retorno.getInt("id"));
        produto.setNome(retorno.getString("nome"));
        produto.setFornecedor(retorno.getString("fornecedor"));

        return produto;
    }
}
