package br.com.crescer.gerenciador.controller.paciente;

import java.net.URL;
import java.util.ResourceBundle;

import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;

import br.com.crescer.gerenciador.controller.MainController;
import br.com.crescer.gerenciador.models.Convenio;
import br.com.crescer.gerenciador.models.endereco.Estado;
import br.com.crescer.gerenciador.models.pessoas.Paciente;
import br.com.crescer.gerenciador.models.pessoas.Sexo;
import br.com.crescer.gerenciador.service.PacienteService;
import br.com.crescer.gerenciador.util.view.AlertBuilder;
import br.com.crescer.gerenciador.util.view.TextFieldFormatter;
import br.com.crescer.gerenciador.util.view.ValidadorDeTelas;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;

enum EstadoTela {
	INSERCAO, ALTERACAO;
}

@Controller
public class CadastroPacienteController implements Initializable {

	@Autowired
	private ValidadorDeTelas validador;

	@Autowired
	private PacienteService service;

	@Autowired
	private AlertBuilder alert;

	@Autowired
	private MainController principal;

	@FXML
	private JFXButton btnSalvar, btnExcluir, btnPesquisar;

	@FXML
	@NotEmpty(message = "* O nome precisa ser preenchido.")
	private JFXTextField mNome;

	@FXML
	private JFXDatePicker mDataNascimento;

	@FXML
	private JFXTextField mNaturalidade;

	@FXML
	@NotEmpty
	private JFXComboBox<Sexo> mSexo;

	@FXML
	private JFXTextField mCpf;

	@FXML
	private JFXTextField mRg;

	@FXML
	private JFXTextField mOrgaoEmissorRg;

	@FXML
	private JFXTextField mNomePai;

	@FXML
	private JFXTextField mProfissaoPai;

	@FXML
	private JFXTextField mNomeMae;

	@FXML
	private JFXTextField mProfissaoMae;

	@FXML
	private JFXTextField mNomeEscola;

	@FXML
	private JFXTextField mSerieAtual;

	@FXML
	private JFXTextField mCodigo;

	@FXML
	private JFXTextField mEndCep;

	@FXML
	private JFXTextField mEndLogradouro;

	@FXML
	private JFXTextField mEndNumero;

	@FXML
	private JFXTextField mEndBairro;

	@FXML
	private JFXTextField mEndComplemento;

	@FXML
	private JFXComboBox<Estado> mEndEstado;

	@FXML
	private JFXTextField mEndCidade;

	@FXML
	@NotEmpty(message = "* O e-mail é obrigatório.")
	private JFXTextField mContEmail;

	@FXML
	@NotEmpty(message = "* o número do telefone celular é obrigatório.")
	private JFXTextField mContTelefone;

	@FXML
	private JFXTextField mContCelular;

	@FXML
	private JFXToggleButton mVerificaConvenio;

	@FXML
	private JFXComboBox<Convenio> mConvenio;

	private Node painelListagem;

	private ListagemPacienteController controllerListagem;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		validador.configuraValidacoes(this);
		configuraComboBox();
		configuraMascaras();

	}

	private void configuraMascaras() {
		mCpf.setOnKeyReleased(event -> {
			TextFieldFormatter f = new TextFieldFormatter();
			f.setMask("###.###.###-##");
			f.setCaracteresValidos("0123456789");
			f.setTf(mCpf);
			f.formatter();
		});

		mContTelefone.setOnKeyReleased(evt -> {
			TextFieldFormatter f = new TextFieldFormatter();
			f.setMask("(##)#####-####");
			f.setCaracteresValidos("0123456789");
			f.setTf(mContTelefone);
			f.formatter();
		});

		mContCelular.setOnKeyReleased(evt -> {
			TextFieldFormatter f = new TextFieldFormatter();
			f.setMask("(##)#####-####");
			f.setCaracteresValidos("0123456789");
			f.setTf(mContCelular);
			f.formatter();
		});
	}

	private void configuraComboBox() {
		mEndEstado.getItems().addAll(Estado.values());
		mSexo.getItems().addAll(Sexo.values());

		mVerificaConvenio.selectedProperty().addListener(evt -> {
			mConvenio.setDisable(!mVerificaConvenio.isSelected());
		});
		mVerificaConvenio.setSelected(false);
	}

	public void setPaciente(Paciente p) {
		if (p != null) {
			mCodigo.setText(p.getId() == null ? "" : String.valueOf(p.getId()));
			mNome.setText(p.getNome());
			mDataNascimento.setValue(p.getDataNascimento());
			mNaturalidade.setText(p.getNaturalidade());
			mSexo.getSelectionModel().select(p.getSexo());
			mCpf.setText(p.getCpf());
			mRg.setText(p.getRg());
			mOrgaoEmissorRg.setText(p.getOrgaoEmissorRg());
			mNomePai.setText(p.getNomePai());
			mProfissaoPai.setText(p.getProfissaoPai());
			mNomeMae.setText(p.getNomeMae());
			mProfissaoMae.setText(p.getProfissaoMae());

			mNomeEscola.setText(p.getNomeEscola());
			mSerieAtual.setText(p.getSerie());
			mContTelefone.setText(p.getTelefone());
			mContCelular.setText(p.getCelular());
			mContEmail.setText(p.getEmail());

			if (p.getConvenio() != null) {
				mVerificaConvenio.setSelected(true);
				mConvenio.getSelectionModel().select(p.getConvenio());
			} else {
				mVerificaConvenio.setSelected(false);
			}

			mEndBairro.setText(p.getBairro());
			mEndCep.setText(p.getCep());
			mEndCidade.setText(p.getCidade());
			mEndEstado.getSelectionModel().select(p.getEstado());
			mEndComplemento.setText(p.getComplemento());
			mEndLogradouro.setText(p.getLogradouro());
			mEndNumero.setText(p.getNumero() == null ? "" : String.valueOf(p.getNumero()));
		} else {
			mCodigo.setText("");
			mNome.setText("");
			mDataNascimento.setValue(null);
			mNaturalidade.setText("");
			mSexo.getSelectionModel().clearSelection();
			mCpf.setText("");
			mRg.setText("");
			mOrgaoEmissorRg.setText("");
			mNomePai.setText("");
			mProfissaoPai.setText("");
			mNomeMae.setText("");
			mProfissaoMae.setText("");

			mNomeEscola.setText("");
			mSerieAtual.setText("");
			mContTelefone.setText("");
			mContCelular.setText("");
			mContEmail.setText("");

			mVerificaConvenio.setSelected(false);
			mConvenio.getSelectionModel().clearSelection();

			mEndBairro.setText("");
			mEndCep.setText("");
			mEndCidade.setText("");
			mEndEstado.getSelectionModel().clearSelection();
			mEndComplemento.setText("");
			mEndLogradouro.setText("");
			mEndNumero.setText("");
		}

	}

	private Paciente montaPaciente() {
		Paciente p = new Paciente();
		p.setId(mCodigo.getText().isEmpty() ? null : Long.parseLong(mCodigo.getText()));
		p.setNome(mNome.getText());
		p.setDataNascimento(mDataNascimento.getValue());
		p.setNaturalidade(mNaturalidade.getText());
		p.setSexo(mSexo.getValue());
		p.setCpf(mCpf.getText());
		p.setRg(mRg.getText());
		p.setOrgaoEmissorRg(mOrgaoEmissorRg.getText());
		p.setNomePai(mNomePai.getText());
		p.setProfissaoPai(mProfissaoPai.getText());
		p.setNomeMae(mNomeMae.getText());
		p.setProfissaoMae(mProfissaoMae.getText());
		p.setNomeEscola(mNomeEscola.getText());
		p.setSerie(mSerieAtual.getText());
		p.setTelefone(mContTelefone.getText());
		p.setCelular(mContCelular.getText());
		p.setEmail(mContEmail.getText());
		if (mVerificaConvenio.isSelected()) {
			p.setConvenio(mConvenio.getValue());
		}

		p.setBairro(mEndBairro.getText());
		p.setCep(mEndCep.getText());
		p.setCidade(mEndCidade.getText());
		p.setComplemento(mEndComplemento.getText());
		p.setEstado(mEndEstado.getValue());
		p.setLogradouro(mEndLogradouro.getText());
		p.setNumero(mEndNumero.getText().isEmpty() ? null : Integer.parseInt(mEndNumero.getText()));

		return p;
	}

	@FXML
	void excluiPaciente(ActionEvent event) {
		if (!this.mCodigo.getText().isEmpty()) {
			this.alert.showConfirmDialog("Deseja realmente excluir este paciente? PS: Operação não reversível.",
					yesOption -> {
						this.service.deleteById(Long.parseLong(mCodigo.getText()));
						this.alert.showInformationMessage("O paciente foi devidamente excluído da base de dados.",
								success -> {
									this.voltaParaListagem(event);
								});
					}, null);
		} else {
			this.alert.showWarningMessage("Nada foi apagado pois não há um registro aberto na tela.");
		}
	}

	@FXML
	void salvaPaciente(ActionEvent event) {
		if (validador.validaCampos(this)) {
			service.save(montaPaciente());
			String mensagem = String.format("O paciente foi %s com sucesso!",
					mCodigo.getText().isEmpty() ? "cadastrado" : "atualizado");
			alert.showInformationMessage(mensagem, success -> {
				this.voltaParaListagem(event);
			});

		} else {
			alert.showWarningMessage("Existem informações obrigatórias que não foram preenchidas. Verifique!");
		}
	}

	@FXML
	void voltaParaListagem(ActionEvent event) {
		principal.getCurrentTab().setContent(painelListagem);
		this.controllerListagem.needUpdate();
	}

	public void setPaneListagem(Node content) {
		this.painelListagem = content;

	}

	public void setControllerListagem(ListagemPacienteController controlelrListagem) {
		this.controllerListagem = controlelrListagem;
	}

	public void setEstadoTela(EstadoTela estado) {
		if (estado == EstadoTela.INSERCAO) {
			this.btnSalvar.setDisable(false);
			this.btnExcluir.setDisable(true);
		} else {
			this.btnSalvar.setDisable(false);
			this.btnExcluir.setDisable(false);
		}
	}
}
