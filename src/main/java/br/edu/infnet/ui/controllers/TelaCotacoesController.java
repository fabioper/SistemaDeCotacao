package br.edu.infnet.ui.controllers;

import br.edu.infnet.domain.cotacoes.Cotacao;
import br.edu.infnet.ui.models.cotacoes.CotacaoDTO;
import br.edu.infnet.ui.models.cotacoes.Cotacoes;
import br.edu.infnet.ui.utils.CSVConverter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.DirectoryChooser;
import javafx.stage.Window;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TelaCotacoesController implements Initializable {
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
    public final void initialize(URL url, ResourceBundle resourceBundle) {
        definirColunas();
        carregarCotacoes(_cotacoes.listar());
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
        cotacoes.forEach((Cotacao produto) -> dtos.add(mapearDTO(produto)));
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

    @FXML
    public final void exportarCotacoesCSV(ActionEvent event) {
        var homeDir = System.getProperty("user.home");
        var directoryChooser = openDirectoryChooserDialog(homeDir);
        var selectedDirectory = directoryChooser.showDialog(getWindowFrom(event));

        if (selectedDirectory.canWrite()) {
            var converter = new CSVConverter<Cotacao>();
            var content = converter.convert(_cotacoes.listar(), this::format);
            var file = new File(selectedDirectory + "/cotacoes.csv");
            writeToFile(file, content);
            return;
        }

        var alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Não é possível escrever neste diretório.");
        alert.showAndWait();
    }

    private void writeToFile(File file, String content) {
        Alert alert;

        try (var writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(content);
            writer.flush();
            alert = new Alert(Alert.AlertType.INFORMATION, "O arquivo pode ser acessado em: " + file.getAbsolutePath());
            alert.setHeaderText("Produto exportado com sucesso!");
            alert.showAndWait();
        } catch (IOException e) {
            alert = new Alert(Alert.AlertType.ERROR, e.getLocalizedMessage());
            alert.setHeaderText("Ocorreu um erro durante a gravação do arquivo.");
            alert.showAndWait();
        }
    }

    private DirectoryChooser openDirectoryChooserDialog(String homeDir) {
        var directoryChooser = new DirectoryChooser();
        directoryChooser.setInitialDirectory(new File(homeDir));
        directoryChooser.setTitle("Escolha o destino");
        return directoryChooser;
    }

    private Window getWindowFrom(ActionEvent event) {
        var source = (Button) event.getSource();
        return source.getScene().getWindow();
    }

    private String format(Cotacao cotacao) {
        return cotacao.getId() + "," +
                cotacao.getProduto().getNome() + "," +
                cotacao.getData() + "," +
                cotacao.getPreco();
    }
}
