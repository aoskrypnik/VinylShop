package com.vinyl.controller;

import com.vinyl.exception.TrackAlreadyExistException;
import com.vinyl.model.Track;
import com.vinyl.service.TrackService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import java.util.List;

import static java.util.Objects.isNull;

@Slf4j
@RestController
@RequestMapping("/track")
public class TrackController {

	@Resource
	private TrackService trackService;

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
		try {
			trackService.save(track);
		} catch (TrackAlreadyExistException e) {
			log.error(e.getMessage());
			return ResponseEntity.status(406).build();
		}
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{catalogNum}")
				.buildAndExpand(track.getTrackCatalogNum()).toUri();
		return ResponseEntity.created(location).build();
	}

	@PutMapping("/{catalogNum}")
	public ResponseEntity<?> updateTrack(@RequestBody Track track, @PathVariable String catalogNum) {
		if (isNull(trackService.getTrackByCatalogNum(catalogNum))) {
			return ResponseEntity.notFound().build();
		}
		trackService.update(track, catalogNum);
		return ResponseEntity.ok().build();
	}


	@DeleteMapping("/{catalogNnum}")
	public ResponseEntity<?> deleteCustomer(@PathVariable String catalogNnum) {
		if (isNull(trackService.getTrackByCatalogNum(catalogNnum))) {
			return ResponseEntity.notFound().build();
		}
		trackService.deleteByCatalogNum(catalogNnum);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/search")
	public ResponseEntity<?> getArtistByCriteria(@RequestParam(value = "wheres", required = false) List<String> whereParams,
												 @RequestParam(value = "likes", required = false) List<String> likeParams,
												 @RequestParam(value = "betweens", required = false) List<String> betweenParams,
												 @RequestParam(value = "joins", required = false) List<String> joins,
												 @RequestParam(value = "sort", required = false) String sorting,
												 @RequestParam(value = "order", required = false) String order,
												 @RequestParam(value = "limit", required = false) Integer limit,
												 @RequestParam(value = "offset", required = false) Integer offset) {
		List<Track> tracks = trackService
				.searchTracks(whereParams, likeParams, betweenParams, joins, sorting, order, limit, offset);
		if (tracks.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(tracks);
	}

}
