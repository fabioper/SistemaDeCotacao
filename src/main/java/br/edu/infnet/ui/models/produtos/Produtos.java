package br.edu.infnet.ui.models.produtos;

import br.edu.infnet.domain.produtos.Produto;
import br.edu.infnet.infra.ProdutoRepositoryImpl;

import java.util.ArrayList;

public class Produtos extends ProdutoRepositoryImpl {
    private ArrayList<Listener> _listeners;
    private static Produtos _instance;

    private Produtos() {
        _listeners = new ArrayList<>();
        _instance = this;
    }

    public static Produtos getInstance() {
        if (_instance != null) return _instance;
        return new Produtos();
    }

    public final void onChange(Listener listener) {
        _listeners.add(listener);
    }

    @Override
    public final Produto inserir(Produto produto) {
        var result = super.inserir(produto);
        _listeners.forEach(Listener::update);
        return result;
    }

    @Override
    public final Produto alterar(Produto produto) {
        var result = super.alterar(produto);
        _listeners.forEach(Listener::update);
        return result;
    }

    @Override
    public final void excluir(int id) {
        super.excluir(id);
        _listeners.forEach(listener -> listener.update());
    }

    public interface Listener {
        void update();
    }
}
