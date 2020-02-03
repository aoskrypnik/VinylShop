package com.vinyl.controller;

import com.vinyl.exception.AlbumAlreadyExistException;
import com.vinyl.model.Album;
import com.vinyl.model.Composer;
import com.vinyl.service.AlbumService;
import lombok.extern.slf4j.Slf4j;
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
import java.util.List;

import static java.util.Objects.isNull;

@Slf4j
@RestController
@RequestMapping("/album")
public class AlbumController {

	@Resource
	private AlbumService albumService;

	@GetMapping
	public List<Album> getAllAlbums() {
		return albumService.getAll();
	}

	@PostMapping
	public ResponseEntity<?> saveAlbum(@RequestBody Album album) {
		try {
			albumService.save(album);
		} catch (AlbumAlreadyExistException e) {
			log.error(e.getMessage());
			return ResponseEntity.status(406).build();
		}
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{albumCatalogNum}")
				.buildAndExpand(album.getAlbumCatalogNum()).toUri();
		return ResponseEntity.created(location).build();
	}

	@GetMapping("/{albumCatalogNum}")
	public ResponseEntity<?> getAlbumByCatalogNum(@PathVariable String albumCatalogNum) {
		Album foundAlbum = albumService.getAlbumByCatalogNum(albumCatalogNum);
		if (isNull(foundAlbum)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(foundAlbum);
	}

	@PutMapping("/{catalogNum}")
	public ResponseEntity<?> updateAlbum(@RequestBody Album album, @PathVariable String catalogNum) {
		if (isNull(albumService.getAlbumByCatalogNum(catalogNum))) {
			return ResponseEntity.notFound().build();
		}
		albumService.update(album, catalogNum);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/search")
	public ResponseEntity<?> getAlbumByCriteria(@RequestParam(value = "wheres", required = false) List<String> whereParams,
												@RequestParam(value = "likes", required = false) List<String> likeParams,
												@RequestParam(value = "betweens", required = false) List<String> betweenParams,
												@RequestParam(value = "joins", required = false) List<String> joins,
												@RequestParam(value = "sort", required = false) String sorting,
												@RequestParam(value = "order", required = false) String order) {
		List<Album> albums = albumService.searchAlbums(whereParams, likeParams, betweenParams, joins, sorting, order);
		if (albums.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(albums);
	}

}
