package com.vinyl.controller;

import com.vinyl.model.Composer;
import com.vinyl.service.ComposerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

import static java.util.Objects.isNull;

@Slf4j
@RestController
@RequestMapping("/composers")
public class ComposerController {

	@Resource
	private ComposerService composerService;

	@GetMapping
	public List<Composer> getAllComposers() {
		return composerService.getAll();
	}

	@GetMapping("/{composerName}")
	public ResponseEntity<?> getComposerByName(@PathVariable String composerName) {
		Composer foundComposer = composerService.getComposerByName(composerName);
		if (isNull(foundComposer)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(foundComposer);
	}

	//TODO make method save return saved object
	@PostMapping
	public ResponseEntity<?> saveNewComposer(@RequestBody Composer composer) {
		composerService.save(composer);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{composerName}")
				.buildAndExpand(composer.getName()).toUri();

		return ResponseEntity.created(location).build();
	}

	@PutMapping("/{composerName}")
	public ResponseEntity<?> updateComposer(@RequestBody Composer composer, @PathVariable String composerName) {
		if (isNull(composerService.getComposerByName(composerName))) {
			return ResponseEntity.notFound().build();
		}
		composerService.update(composer, composerName);
		return ResponseEntity.ok().build();
	}
}
