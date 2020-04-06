package br.edu.infnet.ui.controllers;

import br.edu.infnet.domain.cotacoes.Cotacao;
import br.edu.infnet.ui.models.cotacoes.CotacaoDTO;
import br.edu.infnet.ui.models.cotacoes.Cotacoes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TelaCotacoesController implements Initializable {
    @FXML
    private ChoiceBox<String> filtroCotacoes;

    @FXML
    private TableView<CotacaoDTO> tabelaCotacoes;

    @FXML
    private TableColumn<CotacaoDTO, Integer> colunaCotacaoId;

    @FXML
    private TableColumn<CotacaoDTO, String> colunaCotacaoProduto;

    @FXML
    private TableColumn<CotacaoDTO, String> colunaCotacaoFornecedor;

    @FXML
    private TableColumn<CotacaoDTO, String> colunaData;

    @FXML
    private TableColumn<CotacaoDTO, String> colunaPreco;

    private Cotacoes _cotacoes;

    public TelaCotacoesController() {
        this._cotacoes = Cotacoes.getInstance();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        definirColunas();
        carregarCotacoes(_cotacoes.listar());
        carregarFiltros();
        _cotacoes.onChange(() -> carregarCotacoes(_cotacoes.listar()));
    }

    private void definirColunas() {
        colunaCotacaoId.setCellValueFactory(param -> param.getValue().idProperty().asObject());
        colunaCotacaoProduto.setCellValueFactory(param -> param.getValue().produtoNomeProperty());
        colunaCotacaoFornecedor.setCellValueFactory(param -> param.getValue().produtoFornecedorProperty());
        colunaData.setCellValueFactory(param -> param.getValue().dataProperty());
        colunaPreco.setCellValueFactory(param -> param.getValue().precoProperty());
    }

    private void carregarCotacoes(List<Cotacao> cotacoes) {
        ObservableList<CotacaoDTO> dtos = FXCollections.observableArrayList();
        cotacoes.stream().map((Cotacao produto) -> mapearDTO(produto)).forEach(dtos::add);
        tabelaCotacoes.setItems(dtos);
    }

    private CotacaoDTO mapearDTO(Cotacao cotacao) {
        var dto = new CotacaoDTO();
        dto.setId(cotacao.getId());
        dto.setProdutoNome(cotacao.getProduto().getNome());
        dto.setProdutoFornecedor(cotacao.getProduto().getFornecedor());
        dto.setData(cotacao.getData());
        dto.setPreco(cotacao.getPreco());
        return dto;
    }

    private void carregarFiltros() {
        var opcoes = FXCollections.observableArrayList("Produto", "Id");
        filtroCotacoes.setItems(opcoes);
        var selection = filtroCotacoes.getSelectionModel();
        selection.selectFirst();
    }

    public final void abrirTabCadastroCotacao(ActionEvent actionEvent) throws IOException {
        var source = (Button) actionEvent.getSource();
        var root = source.getScene().getRoot();
        var tabPane = (TabPane) root.lookup("#tabPane");

        var view = getClass().getResource("/views/cotacoes/CadastrarCotacao.fxml");
        var parent = (Parent) FXMLLoader.load(view);
        parent.getStylesheets().add("/styles/Styles.css");

        var tab = new Tab();
        tab.setText("Cadastrar Cotação");
        tab.setContent(parent);
        tabPane.getTabs().add(tab);

        var selection = tabPane.getSelectionModel();
        selection.select(tab);
    }

    public final void excluirCotacao() {
        var selectionModel = tabelaCotacoes.getSelectionModel();
        var selectedItem = selectionModel.getSelectedItem();
        if (selectedItem != null) {
            _cotacoes.excluir(selectedItem.getId());
        }
    }

    private void editarCotacao() {
    }
}
