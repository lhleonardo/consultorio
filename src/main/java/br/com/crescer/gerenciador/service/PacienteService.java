package br.com.crescer.gerenciador.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.crescer.gerenciador.models.pessoas.Paciente;
import br.com.crescer.gerenciador.repository.PacienteRepository;

@Service
public class PacienteService {

	@Autowired
	private PacienteRepository repository;

	public <S extends Paciente> S save(S entity) {
		return repository.save(entity);
	}

	public Page<Paciente> findAll(Pageable pageable) {
		return repository.findAll(pageable);
	}

	public List<Paciente> findAll() {
		return repository.findAll();
	}

	public List<Paciente> findAll(Sort sort) {
		return repository.findAll(sort);
	}

	public Optional<Paciente> findById(Long id) {
		return repository.findById(id);
	}

	public boolean existsById(Long id) {
		return repository.existsById(id);
	}

	public long count() {
		return repository.count();
	}

	public void deleteById(Long id) {
		repository.deleteById(id);
	}

	public Paciente getOne(Long id) {
		return repository.getOne(id);
	}

	public void delete(Paciente entity) {
		repository.delete(entity);
	}

}
