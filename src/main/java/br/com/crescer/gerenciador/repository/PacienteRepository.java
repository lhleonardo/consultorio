package br.com.crescer.gerenciador.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.crescer.gerenciador.models.pessoas.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {

}
