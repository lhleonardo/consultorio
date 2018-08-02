package br.com.crescer.gerenciador.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import br.com.crescer.gerenciador.util.SpringFXMLLoader;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

@Controller
public class LoginController implements Initializable {

	@Autowired
	private SpringFXMLLoader loaderFactory;

	@FXML
	private JFXTextField txtUsuario;

	@FXML
	private JFXPasswordField txtSenha;

	@FXML
	private JFXButton btnLogin;

	private Stage stage;

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		this.btnLogin.setOnAction(evento -> {
			try {
				autenticar();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	private void autenticar() throws IOException {
		if (stage == null) {
			stage = new Stage();
		}

		Pane conteudo = loaderFactory.getLoader("/fxml/menu-principal.fxml").load();

		stage.setTitle("Gerenciador do Crescer - Desenvolvido por Leonardo Braz");
		stage.setScene(new Scene(conteudo));

		stage.setResizable(true);
		stage.setMaximized(true);

		stage.setOnCloseRequest(req -> System.exit(0));

		stage.show();
	}

}