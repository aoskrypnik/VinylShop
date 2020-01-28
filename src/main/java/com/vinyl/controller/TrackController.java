package com.vinyl.controller;

import com.vinyl.model.Track;
import com.vinyl.service.TrackService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

import static java.util.Objects.isNull;

@RestController
@RequestMapping("/track")
public class TrackController {

	@Resource
	private TrackService trackService;

	@GetMapping
	public List<Track> getAllTracks() {
		return trackService.getAll();
	}

	@GetMapping("/{catalogNum}")
	public ResponseEntity<?> getComposerByName(@PathVariable String catalogNum) {
		Track foundTrack = trackService.getTrackByCatalogNum(catalogNum);
		if (isNull(foundTrack)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(foundTrack);
	}

	@PostMapping
	public ResponseEntity<?> saveTrack(@RequestBody Track track) {
		int generatedKey = trackService.save(track);
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{catalog-num}")
				.buildAndExpand(generatedKey).toUri();
		return ResponseEntity.created(location).build();
	}

	@GetMapping("/{catalogNum}/albums")
	public ResponseEntity<?> getTracksByComposerName(@PathVariable String catalogNum) {
		List<String> albums = trackService.findAlbumsWithThisTrack(catalogNum);
		if (isNull(albums)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(albums);
	}

}
