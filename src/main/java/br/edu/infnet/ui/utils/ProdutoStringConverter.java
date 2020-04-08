package br.edu.infnet.ui.utils;

import br.edu.infnet.domain.produtos.Produto;
import br.edu.infnet.ui.models.produtos.Produtos;
import javafx.util.StringConverter;

public class ProdutoStringConverter extends StringConverter<Produto> {
    private Produtos _produtos;

    public ProdutoStringConverter(Produtos _produtos) {
        this._produtos = _produtos;
    }

    @Override
    public final String toString(Produto produto) {
        if (produto != null)
            return produto.getNome();

        return null;
    }

    @Override
    public final Produto fromString(String s) {
        for (Produto p : _produtos.listar())
            if (p.getNome().equals(s)) return p;

        return null;
    }
}