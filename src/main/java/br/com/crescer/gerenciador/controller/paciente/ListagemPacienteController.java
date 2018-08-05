package br.com.crescer.gerenciador.controller.paciente;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import br.com.crescer.gerenciador.controller.MainController;
import br.com.crescer.gerenciador.models.pessoas.Paciente;
import br.com.crescer.gerenciador.service.PacienteService;
import br.com.crescer.gerenciador.util.SpringFXMLLoader;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

@Controller
public class ListagemPacienteController implements Initializable {

	@FXML
	private JFXTreeTableView<PacienteTableView> tbRegistros;

	@FXML
	private JFXTextField txtFiltro;

	@FXML
	private JFXButton btnNovoPaciente;

	@FXML
	private Text txtQtdPacientes;

	@Autowired
	private PacienteService service;

	@Autowired
	private MainController principal;

	@Autowired
	private SpringFXMLLoader loaderFactory;

	private Pane telaCadastro;

	private CadastroPacienteController controllerCadastro;

	private class PacienteTableView extends RecursiveTreeObject<PacienteTableView> {
		public StringProperty codigo;
		public StringProperty nome;
		public StringProperty cpf;
		public StringProperty telefone;

		public PacienteTableView(Long codigo, String nome, String cpf, String telefone) {
			super();
			this.codigo = new SimpleStringProperty(codigo.toString());
			this.nome = new SimpleStringProperty(nome);
			this.cpf = new SimpleStringProperty(cpf);
			this.telefone = new SimpleStringProperty(telefone);
		}
	}

	private void loadCadastro() throws IOException {
		if (telaCadastro == null) {
			FXMLLoader loader = this.loaderFactory.getLoader("/fxml/paciente/cadastro-paciente.fxml");
			this.telaCadastro = loader.load();
			this.controllerCadastro = loader.getController();
		}
	}

	@FXML
	void btnNovoPacienteAction(ActionEvent event) {
		abreTelaCadastro(EstadoTela.INSERCAO, null);
	}

	private void abreTelaCadastro(EstadoTela estado, Paciente p) {
		try {
			loadCadastro();

			this.controllerCadastro.setPaciente(p);
			this.controllerCadastro.setEstadoTela(estado);
			this.controllerCadastro.setPaneListagem(this.principal.getCurrentTab().getContent());
			this.controllerCadastro.setControllerListagem(this);
			this.principal.getCurrentTab().setContent(this.telaCadastro);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		configureColumns();
		populateTreeView();

		this.tbRegistros.setOnMouseClicked(evt -> {
			// double click
			if (evt.getClickCount() == 2) {
				Long codigo = Long
						.parseLong(this.tbRegistros.getSelectionModel().getSelectedItem().getValue().codigo.getValue());

				Optional<Paciente> retorno = this.service.findById(codigo);

				retorno.ifPresent(paciente -> this.abreTelaCadastro(EstadoTela.ALTERACAO, paciente));

			}
		});

	}

	private void populateTreeView() {
		ObservableList<PacienteTableView> pacientes = getObservableValues();

		final TreeItem<PacienteTableView> root = new RecursiveTreeItem<>(pacientes, RecursiveTreeObject::getChildren);

		this.tbRegistros.setRoot(root);
		this.tbRegistros.setShowRoot(false);
		
		this.txtQtdPacientes.setText(String.valueOf(tbRegistros.getCurrentItemsCount()));

	}

	private ObservableList<PacienteTableView> getObservableValues() {
		ObservableList<PacienteTableView> pacientes = FXCollections.observableArrayList();
		service.findAll().forEach(paciente -> pacientes.add(new PacienteTableView(paciente.getId(), paciente.getNome(),
				paciente.getCpf(), paciente.getTelefone())));
		return pacientes;
	}

	@SuppressWarnings("unchecked")
	private void configureColumns() {
		JFXTreeTableColumn<PacienteTableView, String> colunaCodigo = new JFXTreeTableColumn<>("Código");
		colunaCodigo.setPrefWidth(100);
		colunaCodigo.setCellValueFactory(param -> param.getValue().getValue().codigo);

		JFXTreeTableColumn<PacienteTableView, String> colunaNome = new JFXTreeTableColumn<>("Nome Completo");
		colunaNome.setPrefWidth(500);
		colunaNome.setCellValueFactory(param -> param.getValue().getValue().nome);

		JFXTreeTableColumn<PacienteTableView, String> colunaTelefone = new JFXTreeTableColumn<>("Contato");
		colunaTelefone.setPrefWidth(100);
		colunaTelefone.setCellValueFactory(param -> param.getValue().getValue().telefone);

		JFXTreeTableColumn<PacienteTableView, String> colunaCpf = new JFXTreeTableColumn<>("CPF");
		colunaCpf.setPrefWidth(100);
		colunaCpf.setCellValueFactory(param -> param.getValue().getValue().cpf);

		this.tbRegistros.getColumns().setAll(colunaCodigo, colunaNome, colunaCpf, colunaTelefone);
	}

	public void needUpdate() {
		populateTreeView();
	}

}
