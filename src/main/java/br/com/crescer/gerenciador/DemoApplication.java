package br.com.crescer.gerenciador;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.crescer.gerenciador.controller.LoginController;
import br.com.crescer.gerenciador.util.SpringFXMLLoader;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

@SpringBootApplication
public class DemoApplication extends Application {

	private SpringFXMLLoader loaderFactory;

	@Override
	public void init() throws Exception {
		loaderFactory = SpringApplication.run(DemoApplication.class).getBean(SpringFXMLLoader.class);
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = loaderFactory.getLoader("/fxml/login.fxml");
			
			Pane load = loader.load();
			LoginController controller = loader.getController();
			controller.setStage(primaryStage);

			primaryStage.setTitle("Autenticação - Gerenciador do Crescer");
			primaryStage.setScene(new Scene(load));
			primaryStage.setResizable(false);
			primaryStage.centerOnScreen();
			primaryStage.show();

			primaryStage.setOnCloseRequest(req -> System.exit(0));

		} catch (IOException e) {
			System.out.println("Não consegui carregar a tela de login...");
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		Application.launch(DemoApplication.class);
	}
}