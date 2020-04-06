package br.edu.infnet.ui.models.cotacoes;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CotacaoDTO {
    private IntegerProperty id = new SimpleIntegerProperty();
    private StringProperty produtoNome = new SimpleStringProperty();
    private StringProperty produtoFornecedor = new SimpleStringProperty();

    private StringProperty data = new SimpleStringProperty();

    private StringProperty preco = new SimpleStringProperty();
    public static final Locale LOCALE = new Locale("pt", "BR");

    public static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("dd/MM/yyyy");
    public static final NumberFormat CURRENCY_FORMATTER = NumberFormat.getCurrencyInstance(LOCALE);
    public final int getId() {
        return id.get();
    }

    public final IntegerProperty idProperty() {
        return id;
    }

    public final void setId(int id) {
        this.id.set(id);
    }

    public final String getProdutoNome() {
        return produtoNome.get();
    }

    public final StringProperty produtoNomeProperty() {
        return produtoNome;
    }

    public final void setProdutoNome(String produtoNome) {
        this.produtoNome.set(produtoNome);
    }

    public final String getProdutoFornecedor() {
        return produtoFornecedor.get();
    }

    public final StringProperty produtoFornecedorProperty() {
        return produtoFornecedor;
    }

    public final void setProdutoFornecedor(String produtoFornecedor) {
        this.produtoFornecedor.set(produtoFornecedor);
    }

    public final String getData() {
        return data.get();
    }

    public final StringProperty dataProperty() {
        return data;
    }

    public final void setData(Date data) {
        var dataFormatada = DATE_FORMATTER.format(data);
        this.data.set(dataFormatada);
    }

    public final String getPreco() {
        return preco.get();
    }

    public final StringProperty precoProperty() {
        return preco;
    }

    public final void setPreco(double preco) {
        var precoFormatado = CURRENCY_FORMATTER.format(preco);
        this.preco.set(precoFormatado);
    }
}
