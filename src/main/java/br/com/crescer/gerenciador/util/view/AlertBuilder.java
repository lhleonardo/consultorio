package br.com.crescer.gerenciador.util.view;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialog.DialogTransition;
import com.jfoenix.controls.JFXDialogLayout;

import br.com.crescer.gerenciador.controller.MainController;
import javafx.scene.text.Text;

@Component
public class AlertBuilder {

	@Autowired
	private MainController main;

	private static final String TITULO_INFORMACAO = "Mensagem de Informação";
	private static final String TITULO_ALERTA = "Mensagem de Alerta";

	public void showInformationMessage(String mensagem) {
		this.showMessage(TITULO_INFORMACAO, mensagem, null);
	}

	public void showWarningMessage(String mensagem) {
		this.showMessage(TITULO_ALERTA, mensagem, null);
	}

	public void showInformationMessage(String mensagem, Consumer<Void> onOkClick) {
		this.showMessage(TITULO_INFORMACAO, mensagem, onOkClick);
	}

	public void showWarningMessage(String mensagem, Consumer<Void> onOkClick) {
		this.showMessage(TITULO_ALERTA, mensagem, onOkClick);
	}

	private void showMessage(String title, String message, Consumer<Void> onOkClick) {
		JFXDialogLayout context = new JFXDialogLayout();
		context.setHeading(new Text(title));
		context.setBody(new Text(message));

		JFXDialog dialog = new JFXDialog(main.stackPane(), context, DialogTransition.CENTER);
		JFXButton ok = new JFXButton("OK");
		ok.setOnAction(evt -> {
			dialog.close();
			if (onOkClick != null)
				onOkClick.accept(null);
		});

		context.setActions(ok);

		dialog.show();
	}

	public void showConfirmDialog(String mensagem, Consumer<Void> onSuccess, Consumer<?> onError) {
		JFXDialogLayout context = new JFXDialogLayout();
		context.setHeading(new Text("Mensagem de Confirmação"));
		context.setBody(new Text(mensagem));

		JFXDialog dialog = new JFXDialog(main.stackPane(), context, DialogTransition.CENTER);

		JFXButton sim = new JFXButton("Sim");
		sim.setOnAction(evt -> {
			dialog.close();
			if (onSuccess != null)
				onSuccess.accept(null);
		});

		JFXButton nao = new JFXButton("Não");
		nao.setOnAction(evt -> {
			dialog.close();
			if (onError != null)
				onError.accept(null);
		});

		context.setActions(nao, sim);

		dialog.show();

	}
}
