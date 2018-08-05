package br.com.crescer.gerenciador.models.acesso;

import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Transient;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

@Entity
public class Usuario {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty(message = "O login é de preenchimento obrigatório")
	@Length(min = 6, message = "O login deve conter, pelo menos, 6 caracteres.")
	@Column(nullable = false, unique = true)
	private String login;

	@Transient
	@NotEmpty(message = "A senha é de preenchimento obrigatório")
	@Length(min = 6, message = "A senha deve conter, pelo menos, 6 caracteres.")
	@Column
	private String senha;

	@Column
	private boolean ativo;

	@ElementCollection(targetClass = Permissao.class, fetch = FetchType.EAGER)
	@CollectionTable(joinColumns = @JoinColumn(name = "usuario_id"))
	@Enumerated(EnumType.STRING)
	@Column(name = "permissao")
	private Set<Permissao> permissoes;
}
