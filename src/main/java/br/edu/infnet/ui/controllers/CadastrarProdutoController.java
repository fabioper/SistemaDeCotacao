package br.edu.infnet.ui.controllers;

import br.edu.infnet.domain.produtos.Produto;
import br.edu.infnet.ui.models.produtos.Produtos;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;

public class CadastrarProdutoController {
    private Produtos _produtos;

    @FXML
    public TextField inputNome;

    @FXML
    public TextField fornecedorInput;

    public CadastrarProdutoController() {
        _produtos = Produtos.getInstance();
    }

    @FXML
    public final void cadastrarProduto(ActionEvent actionEvent) {
        var produto = new Produto();
        produto.setNome(inputNome.getText());
        produto.setFornecedor(fornecedorInput.getText());
        _produtos.inserir(produto);
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
}
