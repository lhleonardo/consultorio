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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTabPane;

import br.com.crescer.gerenciador.util.SpringFXMLLoader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@interface AbrirTela {
	String arquivo();

	String nome();
}

@Controller
@Scope(scopeName = "singleton")
public class MainController implements Initializable {

	private HashMap<String, Tab> janelasAbertas;

	@Autowired
	private SpringFXMLLoader loaderFactory;

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
	private StackPane stack;

	@FXML
	@AbrirTela(nome = "Controle de Pacientes", arquivo = "/fxml/paciente/listagem-paciente.fxml")
	private Label linkPaciente;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		janelasAbertas = new HashMap<>();
		path.setText("Você está conectado como: Leonardo Braz");
		configuraAbertura();
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
				FXMLLoader loader = loaderFactory.getLoader(annotation.arquivo());
				Pane conteudo = loader.load();

				Tab tab = new Tab(annotation.nome(), conteudo);
				tab.setOnClosed(evt -> {
					this.janelasAbertas.remove(annotation.arquivo());
				});

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

	public StackPane stackPane() {
		return this.stack;
	}

	public Tab getCurrentTab() {
		return viewPane.getSelectionModel().getSelectedItem();
	}

}