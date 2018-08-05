package br.com.crescer.gerenciador.models.pessoas;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.br.CPF;

import br.com.crescer.gerenciador.models.Convenio;
import br.com.crescer.gerenciador.models.endereco.Endereco;
import br.com.crescer.gerenciador.models.endereco.Estado;



@Entity
public class Paciente {

	public Paciente() {
		this.endereco = new Endereco();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	@NotBlank(message = "Nome do paciente é de preenchimento obrigatório")
	private String nome;

	@Column
	private LocalDate dataNascimento;

	@Column
	private String naturalidade;

	@Enumerated(EnumType.STRING)
	@Column
	private Sexo sexo;

	@Column
	@CPF(message = "CPF precisa ser válido e não nulo.")
	private String cpf;

	@Column
	private String rg;

	@Column
	private String orgaoEmissorRg;

	@Column
	private String nomePai, nomeMae;

	@Column
	private String profissaoPai, profissaoMae;

	@Column
	private String nomeEscola;
	@Column
	private String serie;

	@Embedded
	private Endereco endereco;

	@Column
	private String email, telefone, celular;

	@ManyToOne
	private Convenio convenio;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public String getNaturalidade() {
		return naturalidade;
	}

	public void setNaturalidade(String naturalidade) {
		this.naturalidade = naturalidade;
	}

	public Sexo getSexo() {
		return sexo;
	}

	public void setSexo(Sexo sexo) {
		this.sexo = sexo;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getOrgaoEmissorRg() {
		return orgaoEmissorRg;
	}

	public void setOrgaoEmissorRg(String orgaoEmissorRg) {
		this.orgaoEmissorRg = orgaoEmissorRg;
	}

	public String getNomePai() {
		return nomePai;
	}

	public void setNomePai(String nomePai) {
		this.nomePai = nomePai;
	}

	public String getNomeMae() {
		return nomeMae;
	}

	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}

	public String getProfissaoPai() {
		return profissaoPai;
	}

	public void setProfissaoPai(String profissaoPai) {
		this.profissaoPai = profissaoPai;
	}

	public String getProfissaoMae() {
		return profissaoMae;
	}

	public void setProfissaoMae(String profissaoMae) {
		this.profissaoMae = profissaoMae;
	}

	public String getNomeEscola() {
		return nomeEscola;
	}

	public void setNomeEscola(String nomeEscola) {
		this.nomeEscola = nomeEscola;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getCep() {
		return endereco.getCep();
	}

	public void setCep(String cep) {
		endereco.setCep(cep);
	}

	public String getLogradouro() {
		return endereco.getLogradouro();
	}

	public void setLogradouro(String logradouro) {
		endereco.setLogradouro(logradouro);
	}

	public String getComplemento() {
		return endereco.getComplemento();
	}

	public void setComplemento(String complemento) {
		endereco.setComplemento(complemento);
	}

	public String getBairro() {
		return endereco.getBairro();
	}

	public void setBairro(String bairro) {
		endereco.setBairro(bairro);
	}

	public Integer getNumero() {
		return endereco.getNumero();
	}

	public void setNumero(Integer numero) {
		endereco.setNumero(numero);
	}

	public Estado getEstado() {
		return endereco.getEstado();
	}

	public void setEstado(Estado estado) {
		endereco.setEstado(estado);
	}

	public String getCidade() {
		return endereco.getCidade();
	}

	public void setCidade(String cidade) {
		endereco.setCidade(cidade);
	}

	public Convenio getConvenio() {
		return convenio;
	}

	public void setConvenio(Convenio convenio) {
		this.convenio = convenio;
	}
}
