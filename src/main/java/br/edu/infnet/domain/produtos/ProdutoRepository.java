package br.edu.infnet.domain.produtos;

import br.edu.infnet.domain.Repository;

import java.util.List;

public interface ProdutoRepository extends Repository<Produto> {
    List<Produto> obterPorFornecedor(String fornecedor);
}
