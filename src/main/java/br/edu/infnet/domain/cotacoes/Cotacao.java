package br.edu.infnet.domain.cotacoes;

import br.edu.infnet.domain.BaseEntity;
import br.edu.infnet.domain.produtos.Produto;

import java.util.Date;

public class Cotacao extends BaseEntity {
    private Produto produto;
    private Date data;
    private double preco;

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }
}
