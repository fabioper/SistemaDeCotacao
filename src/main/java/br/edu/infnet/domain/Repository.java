package br.edu.infnet.domain;

import java.util.List;

public interface Repository<T extends BaseEntity> {
    T inserir(T entity);

    T alterar(T entity);

    void excluir(int id);

    List<T> listar();

    T obterPorId(int id);
}
