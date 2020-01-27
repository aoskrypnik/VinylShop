package com.vinyl.controller;

import com.vinyl.model.Artist;
import com.vinyl.model.Customer;
import com.vinyl.model.Track;
import com.vinyl.service.ArtistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static java.util.Objects.isNull;

@RestController
@RequestMapping("/artist")
public class ArtistController {

	@Resource
	private ArtistService artistService;

	@GetMapping
	public List<Artist> getAllTracks() {
		return artistService.getAll();
	}

	@GetMapping("/{alias}")
	public ResponseEntity<?> getArtistByAlias(@PathVariable String alias) {
		Artist foundArtist = artistService.getArtistByAlias(alias);
		if (isNull(foundArtist)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(foundArtist);
	}

	@GetMapping("/search")
	public ResponseEntity<?> getArtistByCriteria(@RequestParam(value = "wheres", required = false) List<String> whereParams,
												 @RequestParam(value = "likes", required = false) List<String> likeParams,
												 @RequestParam(value = "betweens", required = false) List<String> betweenParams,
												 @RequestParam(value = "joins", required = false) List<String> joins,
												 @RequestParam(value = "sort", required = false) String sorting,
												 @RequestParam(value = "order", required = false) String order) {
		List<Artist> artists = artistService.searchArtists(whereParams, likeParams, betweenParams, joins, sorting, order);
		if (artists.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(artists);
	}
}
