package br.edu.infnet.ui.controllers;

import br.edu.infnet.domain.cotacoes.Cotacao;
import br.edu.infnet.domain.produtos.Produto;
import br.edu.infnet.ui.models.cotacoes.Cotacoes;
import br.edu.infnet.ui.models.produtos.Produtos;
import br.edu.infnet.ui.utils.CurrencyFilter;
import br.edu.infnet.ui.utils.ProdutoStringConverter;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

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
        comboProdutos.setConverter(new ProdutoStringConverter(_produtos));
        inputPreco.setTextFormatter(new TextFormatter<>(new CurrencyFilter()));
    }

    @FXML
    public final void cadastrarCotacao(ActionEvent actionEvent) {
        var selectedItem = comboProdutos.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            var alert = new Alert(Alert.AlertType.ERROR, "Por favor, escolha um produto.");
            alert.setHeaderText("Produto Ausente");
            alert.show();
            return;
        }

        _cotacoes.inserir(mapCotacao(selectedItem));

        var alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Cotação inserida com sucesso!");
        alert.showAndWait();
        fecharTab(actionEvent);
    }

    private Cotacao mapCotacao(Produto selectedItem) {
        var produto = _produtos.obterPorId(selectedItem.getId());
        var date = Instant.from(inputData.getValue().atStartOfDay(ZoneId.systemDefault()));
        var preco = inputPreco.getText().replaceAll(",", ".");

        var cotacao = new Cotacao();
        cotacao.setProduto(produto);
        cotacao.setData(Date.from(date));
        cotacao.setPreco(Double.parseDouble(preco));
        return cotacao;
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
}
