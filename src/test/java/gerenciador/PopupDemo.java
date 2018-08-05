package gerenciador;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPopup;
import com.jfoenix.controls.JFXPopup.PopupHPosition;
import com.jfoenix.controls.JFXPopup.PopupVPosition;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PopupDemo extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		VBox actionList = new VBox();
		JFXButton btnVerMais = new JFXButton("Ver mais...");
		JFXButton btnEditar = new JFXButton("Editar...");
		JFXButton btnRemover = new JFXButton("Remover...");

		btnVerMais.setPrefSize(100, 40);
		btnEditar.setPrefSize(100, 40);
		btnRemover.setPrefSize(100, 40);

		actionList.getChildren().addAll(btnEditar, btnRemover, btnVerMais);

		JFXPopup popup = new JFXPopup(actionList);
		JFXButton button = new JFXButton("popup");
		StackPane main = new StackPane();
		main.getChildren().add(button);

		button.setOnAction(e -> popup.show(button, PopupVPosition.TOP, PopupHPosition.LEFT));

		final Scene scene = new Scene(main, 800, 800);

		primaryStage.setTitle("JFX Popup Demo");
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}