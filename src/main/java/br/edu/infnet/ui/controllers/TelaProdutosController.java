package br.edu.infnet.ui.controllers;

import br.edu.infnet.domain.produtos.Produto;
import br.edu.infnet.ui.models.produtos.ProdutoDTO;
import br.edu.infnet.ui.models.produtos.Produtos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TelaProdutosController implements Initializable {
    @FXML
    private ChoiceBox<String> filtro;
    @FXML
    private TableView<ProdutoDTO> tabelaProdutos;
    @FXML
    private TableColumn<ProdutoDTO, Integer> colunaId;
    @FXML
    private TableColumn<ProdutoDTO, String> colunaProduto;
    @FXML
    private TableColumn<ProdutoDTO, String> colunaFornecedor;

    private Produtos _produtos;

    public TelaProdutosController() {
        this._produtos = Produtos.getInstance();
    }

    @Override
    public final void initialize(URL url, ResourceBundle resourceBundle) {
        definirColunas();
        carregarProdutos(_produtos.listar());
        carregarFiltros();
        _produtos.onChange(() -> carregarProdutos(_produtos.listar()));
    }

    private void definirColunas() {
        colunaId.setCellValueFactory(param -> param.getValue().idProperty().asObject());
        colunaProduto.setCellValueFactory(param -> param.getValue().nomeProperty());
        colunaFornecedor.setCellValueFactory(param -> param.getValue().fornecedorProperty());
        colunaProduto.setCellFactory(TextFieldTableCell.forTableColumn());
        colunaFornecedor.setCellFactory(TextFieldTableCell.forTableColumn());

        colunaProduto.setOnEditCommit(ev -> {
            int row = ev.getTablePosition().getRow();
            ev.getTableView().getItems().get(row).setNome(ev.getNewValue());
            editarProduto();
        });

        colunaFornecedor.setOnEditCommit(ev -> {
            int row = ev.getTablePosition().getRow();
            ev.getTableView().getItems().get(row).setFornecedor(ev.getNewValue());
            editarProduto();
        });
    }

    private void carregarProdutos(List<Produto> produtoList) {
        ObservableList<ProdutoDTO> dtos = FXCollections.observableArrayList();
        produtoList.stream().map(this::mapearDTO).forEach(dtos::add);
        tabelaProdutos.setItems(dtos);
    }

    private void carregarFiltros() {
        var opcoes = FXCollections.observableArrayList("Fornecedor", "Nome", "Id");
        filtro.setItems(opcoes);
        var selection = filtro.getSelectionModel();
        selection.selectFirst();
    }

    @FXML
    public final void abrirTabCadastro(ActionEvent actionEvent) throws IOException {
        var source = (Button) actionEvent.getSource();
        var root = source.getScene().getRoot();
        var tabPane = (TabPane) root.lookup("#tabPane");

        var view = getClass().getResource("/views/produtos/CadastrarProduto.fxml");
        var content = (Parent) FXMLLoader.load(view);
        content.getStylesheets().add("/styles/Styles.css");

        var tab = new Tab();
        tab.setText("Cadastrar Produto");
        tab.setContent(content);
        tabPane.getTabs().add(tab);

        var selection = tabPane.getSelectionModel();
        selection.select(tab);
    }

    @FXML
    public final void excluirProduto() {
        var selectionModel = tabelaProdutos.getSelectionModel();
        var selectedItem = selectionModel.getSelectedItem();
        if (selectedItem != null) {
            _produtos.excluir(selectedItem.getId());
        }
    }

    private void editarProduto() {
        var selectedRow = tabelaProdutos.getSelectionModel();
        var dto = selectedRow.getSelectedItem();
        var produto = new Produto();
        produto.setId(dto.getId());
        produto.setNome(dto.getNome());
        produto.setFornecedor(dto.getFornecedor());
        _produtos.alterar(produto);
    }

    private ProdutoDTO mapearDTO(Produto produto) {
        var dto = new ProdutoDTO();
        dto.setId(produto.getId());
        dto.setNome(produto.getNome());
        dto.setFornecedor(produto.getFornecedor());
        return dto;
    }
}
