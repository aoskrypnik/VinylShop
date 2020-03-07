package com.vinyl.controller;

import com.vinyl.dto.SearchDto;
import com.vinyl.exception.ReleaseAlreadyExistException;
import com.vinyl.model.Release;
import com.vinyl.service.ReleaseService;
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
@RequestMapping("/release")
public class ReleaseController {

	@Resource
	private ReleaseService releaseService;

	@PostMapping
	public ResponseEntity<?> saveRelease(@RequestBody Release release) {
		try {
			releaseService.save(release);
		} catch (ReleaseAlreadyExistException e) {
			log.error(e.getMessage());
			return ResponseEntity.status(406).build();
		}
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{releaseBarcode}")
				.buildAndExpand(release.getReleaseBarcode()).toUri();

		return ResponseEntity.created(location).build();
	}

	@GetMapping("/{releaseBarCode}")
	public ResponseEntity<?> getReleaseByBarCode(@PathVariable String releaseBarCode) {
		Release foundRelease = releaseService.getReleaseByBarcode(releaseBarCode);
		if (isNull(foundRelease)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(foundRelease);
	}

	@PutMapping("/{barCode}")
	public ResponseEntity<?> updateRelease(@RequestBody Release release, @PathVariable String barCode) {
		if (isNull(releaseService.getReleaseByBarcode(barCode))) {
			return ResponseEntity.notFound().build();
		}
		releaseService.update(release, barCode);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/search")
	public ResponseEntity<?> getAlbumByCriteria(SearchDto searchDto) {
		List<Release> releases = releaseService.searchReleases(searchDto);
		if (releases.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(releases);
	}

}
