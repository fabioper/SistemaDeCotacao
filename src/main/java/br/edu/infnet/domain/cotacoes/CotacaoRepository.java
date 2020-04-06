package br.edu.infnet.domain.cotacoes;

import br.edu.infnet.domain.Repository;
import br.edu.infnet.domain.produtos.Produto;

import java.util.List;

public interface CotacaoRepository extends Repository<Cotacao> {
    List<Cotacao> obterPorProduto(Produto produto);
}
