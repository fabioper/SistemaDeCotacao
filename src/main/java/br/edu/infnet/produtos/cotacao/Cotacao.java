package br.edu.infnet.produtos.cotacao;

import br.edu.infnet.produtos.produto.Produto;

import java.util.Date;

public class Cotacao {
    private int id;
    private Produto produto;
    private Date data;
    private double preco;

    public int getId() {
        return id;
    }

    public Cotacao setId(int id) {
        this.id = id;
        return this;
    }

    public Produto getProduto() {
        return produto;
    }

    public Cotacao setProduto(Produto produto) {
        this.produto = produto;
        return this;
    }

    public double getPreco() {
        return preco;
    }

    public Cotacao setPreco(double preco) {
        this.preco = preco;
        return this;
    }

    public Date getData() {
        return data;
    }

    public Cotacao setData(Date data) {
        this.data = data;
        return this;
    }
}
