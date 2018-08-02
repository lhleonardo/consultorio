package br.com.crescer.gerenciador.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javafx.fxml.FXMLLoader;

@Component
public class SpringFXMLLoader {

	@Autowired
	private ApplicationContext context;

	public FXMLLoader getLoader(String url) {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(SpringFXMLLoader.class.getResource(url));
		loader.setControllerFactory(context::getBean);
		return loader;
	}
}
