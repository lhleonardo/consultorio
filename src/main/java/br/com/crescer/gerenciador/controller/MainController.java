package br.com.crescer.gerenciador.controller;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

import org.springframework.stereotype.Controller;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTabPane;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface AbrirTela {
	String arquivo();

	String nome();
}

@Controller
public class MainController implements Initializable {

	private HashMap<String, Tab> janelasAbertas;

	@FXML
	private VBox menuLateral;

	@FXML
	private ImageView imagemLogo;

	@FXML
	private JFXListView<Label> opcoesMenu;

	@FXML
	private HBox menuSuperior;

	@FXML
	private Label path;

	@FXML
	private JFXTabPane viewPane;

	@FXML
	@AbrirTela(nome = "Cadastro de Pacientes", arquivo = "/fxml/paciente/cadastro-paciente.fxml")
	private Label linkPaciente;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		janelasAbertas = new HashMap<>();
		path.setText("Você está conectado como: Leonardo Braz");
		configuraAbertura();
		configuraExclusao();
	}

	private void configuraExclusao() {
	}

	private void configuraAbertura() {
		opcoesMenu.setOnMouseClicked(event -> {
			String idLink = opcoesMenu.getSelectionModel().getSelectedItem().getId();

			carregarConfiguracao(idLink);
		});
	}

	private void carregarConfiguracao(String idLink) {
		try {
			Field campo = this.getClass().getDeclaredField(idLink);
			AbrirTela annotation = campo.getAnnotation(AbrirTela.class);

			if (!janelasAbertas.containsKey(annotation.arquivo())) {
				Pane conteudo = FXMLLoader.load(MainController.class.getResource(annotation.arquivo()));

				Tab tab = new Tab(annotation.nome(), conteudo);

				this.viewPane.getTabs().add(tab);

				this.janelasAbertas.put(annotation.arquivo(), tab);
			} else {
				this.viewPane.getSelectionModel().select(this.janelasAbertas.get(annotation.arquivo()));
			}
		} catch (NoSuchFieldException | SecurityException e) {
			System.err.println("Não foi encontrado o campo " + idLink + " em MainController");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Não foi encontrado o arquivo FXML para abrir " + idLink);
			e.printStackTrace();
		}
	}

}