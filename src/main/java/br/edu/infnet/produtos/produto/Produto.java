package br.edu.infnet.produtos.produto;

public class Produto {
    private int id;
    private String nome;
    private String fornecedor;

    public int getId() {
        return id;
    }

    public Produto setId(int id) {
        this.id = id;
        return this;
    }

    public String getNome() {
        return nome;
    }

    public Produto setNome(String nome) {
        this.nome = nome;
        return this;
    }

    public String getFornecedor() {
        return fornecedor;
    }

    public Produto setFornecedor(String fornecedor) {
        this.fornecedor = fornecedor;
        return this;
    }
}
