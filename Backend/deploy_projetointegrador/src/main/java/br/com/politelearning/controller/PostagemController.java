package br.com.politelearning.controller;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.politelearning.model.PostagemModel;
import br.com.politelearning.repository.PostagemRepository;

@RestController
@RequestMapping("/postagens")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class PostagemController {

	@Autowired
	private PostagemRepository postagemRepository;

	@GetMapping 
	public ResponseEntity<List<PostagemModel>> getAll() {
		return ResponseEntity.ok(postagemRepository.findAll());

	}

	@GetMapping("/{id}") 
	public ResponseEntity<PostagemModel> getById(@PathVariable long id) {
		return postagemRepository.findById(id).map(resposta -> ResponseEntity.ok(resposta))
				.orElse(ResponseEntity.notFound().build());

	}

	@GetMapping("/titulo/{titulo}") 
	public ResponseEntity<List<PostagemModel>> getByTitulo(@PathVariable String titulo) {
		return ResponseEntity.ok(postagemRepository.findAllByTituloContainingIgnoreCase(titulo));

	}

	@PostMapping 
	public ResponseEntity<PostagemModel> postPostagemModel(@Valid @RequestBody PostagemModel postagem) {
		return ResponseEntity.status(HttpStatus.CREATED).body(postagemRepository.save(postagem));
	}

	@PutMapping 
	public ResponseEntity<PostagemModel> putPostagemModel(@Valid @RequestBody PostagemModel postagem) {
		return postagemRepository.findById(postagem.getId()).map(resposta -> {
			return ResponseEntity.ok().body(postagemRepository.save(postagem));
		}).orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}") 
	public ResponseEntity<?> deletePLPostagemModel(@PathVariable long id) {

		return postagemRepository.findById(id).map(resposta -> {
			postagemRepository.deleteById(id);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

		}).orElse(ResponseEntity.notFound().build());
	}

}
