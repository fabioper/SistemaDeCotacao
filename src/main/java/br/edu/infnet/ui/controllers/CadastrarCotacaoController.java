package br.edu.infnet.ui.controllers;

import br.edu.infnet.domain.cotacoes.Cotacao;
import br.edu.infnet.domain.produtos.Produto;
import br.edu.infnet.ui.models.cotacoes.Cotacoes;
import br.edu.infnet.ui.models.produtos.Produtos;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.net.URL;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;

public class CadastrarCotacaoController implements Initializable {
    private Cotacoes _cotacoes;
    private Produtos _produtos;

    @FXML
    private ComboBox<Produto> comboProdutos;

    @FXML
    private DatePicker inputData;

    @FXML
    private TextField inputPreco;

    public CadastrarCotacaoController() {
        _cotacoes = Cotacoes.getInstance();
        _produtos = Produtos.getInstance();
    }

    @Override
    public final void initialize(URL url, ResourceBundle resourceBundle) {
        var produtos = FXCollections.observableArrayList(_produtos.listar());
        comboProdutos.setItems(produtos);
        comboProdutos.setConverter(new ProdutoStringConverter());
    }

    @FXML
    public final void cadastrarCotacao(ActionEvent actionEvent) {
        var cotacao = new Cotacao();
        var produtoId = comboProdutos.getSelectionModel().getSelectedItem().getId();
        var produto = _produtos.obterPorId(produtoId);
        var date = Instant.from(inputData.getValue().atStartOfDay(ZoneId.systemDefault()));
        var preco = inputPreco.getText();

        cotacao.setProduto(produto);
        cotacao.setData(Date.from(date));
        cotacao.setPreco(Double.parseDouble(preco));
        _cotacoes.inserir(cotacao);
        fecharTab(actionEvent);
    }

    @FXML
    public final void cancelarCadastro(ActionEvent actionEvent) {
        fecharTab(actionEvent);
    }

    private void fecharTab(ActionEvent actionEvent) {
        var source = (Button) actionEvent.getSource();
        var root = source.getScene().getRoot();
        var tabPane = (TabPane) root.lookup("#tabPane");
        tabPane.getTabs().remove(tabPane.getSelectionModel().getSelectedIndex());
    }

    private class ProdutoStringConverter extends StringConverter<Produto> {
        @Override
        public final String toString(Produto produto) {
            if (produto != null) {
                return produto.getNome();
            }
            return null;
        }

        @Override
        public final Produto fromString(String s) {
            return _produtos.listar()
                    .stream()
                    .filter(p -> p.getNome().equals(s))
                    .findFirst()
                    .orElse(null);
        }
    }
}
