package com.vinyl.controller;

import com.vinyl.dto.SearchDto;
import com.vinyl.exception.ArtistExistException;
import com.vinyl.model.Artist;
import com.vinyl.service.ArtistService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import static org.apache.commons.lang3.BooleanUtils.isFalse;
import static org.springframework.util.CollectionUtils.isEmpty;

@RestController
@RequestMapping("/artist")
public class ArtistController {

	@Resource
	private ArtistService artistService;

	@PostMapping
	public ResponseEntity<?> saveArtist(@RequestBody Artist artist) {
		try {
			artistService.save(artist);
		} catch (ArtistExistException e) {
			return ResponseEntity.status(406).build();
		}
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{alias}")
				.buildAndExpand(artist.getArtistAlias()).toUri();

		return ResponseEntity.created(location).build();
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
	public ResponseEntity<?> getArtistByCriteria(SearchDto searchDto) {
		List<Artist> artists = artistService.searchArtists(searchDto);
		if (artists.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(artists);
	}

	@PutMapping("/{alias}")
	public ResponseEntity<?> updateArtist(@RequestBody Artist artist, @PathVariable String alias) {
		if (isNull(artistService.getArtistByAlias(alias))) {
			return ResponseEntity.notFound().build();
		}
		artistService.update(artist, alias);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/{alias}")
	public ResponseEntity<?> deleteArtist(@PathVariable String alias) {
		Artist foundArtist = artistService.getArtistByAlias(alias);
		if (isNull(foundArtist)) {
			return ResponseEntity.notFound().build();
		}

		if (isFalse(isEmpty(foundArtist.getCurrentBandAliases())) || isFalse(isEmpty(foundArtist.getPreviousBandAliases()))
				|| isFalse(isEmpty(foundArtist.getTrackCatalogNums())) || isFalse(isEmpty(foundArtist.getFeaturingTrackCatalogNums()))) {
			return ResponseEntity.status(403).build();
		}
		artistService.deleteArtist(alias);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/sold-out")
	public ResponseEntity<?> getArtistWhoseAlbumsWereSoldOut() {
		return ResponseEntity.ok(artistService.getArtistWhoseAlbumsWereSoldOut());
	}
}
