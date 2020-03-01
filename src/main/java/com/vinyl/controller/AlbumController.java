package com.vinyl.controller;

import com.vinyl.dto.SearchDto;
import com.vinyl.exception.AlbumAlreadyExistException;
import com.vinyl.model.Album;
import com.vinyl.service.AlbumService;
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
@RequestMapping("/album")
public class AlbumController {

	@Resource
	private AlbumService albumService;

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
	public ResponseEntity<?> getAlbumByCriteria(SearchDto searchDto) {
		List<Album> albums = albumService.searchAlbums(searchDto);
		if (albums.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(albums);
	}

}
