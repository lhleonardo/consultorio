package br.com.crescer.gerenciador.util.view;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

import javax.validation.constraints.NotEmpty;

import org.springframework.stereotype.Component;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;

import de.jensd.fx.glyphs.GlyphsBuilder;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

@Component
public class ValidadorDeTelas {

	/**
	 * Método responsável por validar todos os campos que possuem validadores de
	 * conteúdo. Seu retorno é associado a verificação deste conjunto. Verdadeiro
	 * quando todos os validadores retornaram verdadeiro quanto a sua validação ou
	 * falso quando algum deles falhou.
	 * 
	 * @param controller
	 * @return se todos os campos estão válidos
	 */
	public <T> boolean validaCampos(T controller) {
		Field[] campos = controller.getClass().getDeclaredFields();

		// percorre cada campo no controller
		for (Field campo : campos) {
			// se o campo for um JFXTextField
			if (campo.getType().isAssignableFrom(JFXTextField.class)) {
				try {
					// se existir a anotação @NotEmpty no campo, ele deve ser verificado
					NotEmpty anotacao = campo.getDeclaredAnnotation(NotEmpty.class);

					if (anotacao != null) {
						campo.setAccessible(true);
						JFXTextField validationField = (JFXTextField) campo.get(controller);

						// retorna false caso não esteja válido
						if (!validationField.validate()) {
							return false;
						}
					}
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					return false;
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					return false;
				}
			}
		}

		return true;
	}

	/**
	 * Método responsável por configurar os campos de texto e definir seus
	 * validadores, com suas mensagens respectivas, associadas a
	 * annotation @NotEmpty
	 * 
	 * @param controller
	 */
	public <T> void configuraValidacoes(T controller) {
		Field[] campos = controller.getClass().getDeclaredFields();

		// percorre todos os campos do controller
		for (Field campo : campos) {
			// se o campo for um JFXTextField...
			if (campo.getType().isAssignableFrom(JFXTextField.class)) {
				Optional<Annotation> optAnotacao = Optional.ofNullable(campo.getDeclaredAnnotation(NotEmpty.class));

				// se o campo possuir a anotação...
				optAnotacao.ifPresent(anotacao -> {
					try {
						// permite manipular membros privados
						campo.setAccessible(true);
						// adiciona o validador
						JFXTextField validationField = (JFXTextField) campo.get(controller);
						RequiredFieldValidator validator = new RequiredFieldValidator();
						validator.setMessage((String) anotacao.getClass().getMethod("message").invoke(anotacao));
						validator.setIcon(GlyphsBuilder.create(FontAwesomeIconView.class).glyph(FontAwesomeIcon.WARNING)
								.size("1em").styleClass("error").build());
						validationField.getValidators().add(validator);
						validationField.focusedProperty().addListener((o, oldVal, newVal) -> {
							if (!newVal) {
								validationField.validate();
							}
						});
					} catch (IllegalArgumentException | IllegalAccessException e) {
						e.printStackTrace();
					} catch (InvocationTargetException e) {
						e.printStackTrace();
					} catch (NoSuchMethodException e) {
						e.printStackTrace();
					} catch (SecurityException e) {
						e.printStackTrace();
					}
				});

			}
		}
	}

}
