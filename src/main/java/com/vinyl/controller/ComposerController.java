package com.vinyl.controller;

import com.vinyl.model.Composer;
import com.vinyl.service.ComposerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.Resource;
import java.net.URI;
import java.util.Date;
import java.util.List;

import static java.util.Objects.isNull;

@Slf4j
@RestController
@RequestMapping("/composer")
public class ComposerController {

	@Resource
	private ComposerService composerService;

	@GetMapping
	public List<Composer> getAllComposers() {
		return composerService.getAll();
	}

	@GetMapping("/name/{composerName}")
	public ResponseEntity<?> getComposerByName(@PathVariable String composerName) {
		Composer foundComposer = composerService.getComposerByName(composerName);
		if (isNull(foundComposer)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(foundComposer);
	}

	@PostMapping
	public ResponseEntity<?> saveComposer(@RequestBody Composer composer) {
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

	@GetMapping("/searchComposer")
	public ResponseEntity<?> findComposerByCriteria
			(@RequestParam(value = "code", required = false) String countryCode,
			 @RequestParam(value = "start", required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date activityStart,
			 @RequestParam(value = "end", required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") Date activityEnd) {
		List<Composer> foundComposers = composerService.findComposerByCriteria(countryCode, activityStart, activityEnd);
		if (foundComposers.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(foundComposers);
	}

}
