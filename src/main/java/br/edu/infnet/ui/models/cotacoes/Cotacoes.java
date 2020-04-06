package br.edu.infnet.ui.models.cotacoes;

import br.edu.infnet.domain.cotacoes.Cotacao;
import br.edu.infnet.infra.CotacaoRepositoryImpl;

import java.util.ArrayList;

public class Cotacoes extends CotacaoRepositoryImpl {
    private ArrayList<Listener> _listeners;
    private static Cotacoes _instance;

    private Cotacoes() {
        _listeners = new ArrayList<>();
        _instance = this;
    }

    public static Cotacoes getInstance() {
        if (_instance != null) return _instance;
        return new Cotacoes();
    }

    public final void onChange(Listener listener) {
        _listeners.add(listener);
    }

    @Override
    public final Cotacao inserir(Cotacao cotacao) {
        var result = super.inserir(cotacao);
        _listeners.forEach(Listener::update);
        return result;
    }

    @Override
    public final Cotacao alterar(Cotacao cotacao) {
        var result = super.alterar(cotacao);
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
