package com.vinyl.controller;

import com.vinyl.dto.SearchDto;
import com.vinyl.dto.TrackArtistDto;
import com.vinyl.service.TrackPerformerService;
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

@RestController
@RequestMapping("/artist2track")
public class TrackArtistController {

	@Resource
	private TrackPerformerService trackPerformerService;

	/**
	 * 12345@Monatik
	 */
	@GetMapping("/{trackNumAndArtistAlias}")
	public ResponseEntity<TrackArtistDto> getByTrackAndPerformerName(@PathVariable String trackNumAndArtistAlias) {
		TrackArtistDto trackArtistDto = trackPerformerService.getTrackArtistByTrackNumAndArtistAlias(trackNumAndArtistAlias);
		if (isNull(trackArtistDto)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(trackArtistDto);
	}

	@PostMapping
	public ResponseEntity<Object> save(@RequestBody TrackArtistDto trackArtistDto) {
		String trackCatalogNum = trackArtistDto.getTrackCatalogNum();
		String artistAlias = trackArtistDto.getArtistAlias();
		String potentialLocationPath = trackCatalogNum + "@" + artistAlias;

		trackPerformerService.save(trackArtistDto);

		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{ids}")
				.buildAndExpand(potentialLocationPath)
				.toUri();
		return ResponseEntity.created(location).build();
	}

	/**
	 * 12345@Monatik
	 */
	@PutMapping("/{trackAndArtistAlias}")
	public ResponseEntity<Object> update(@PathVariable String trackAndArtistAlias,
										 @RequestBody TrackArtistDto trackArtistDto) {
		TrackArtistDto foundTrackArtistDto = trackPerformerService.getTrackArtistByTrackNumAndArtistAlias(trackAndArtistAlias);
		if (isNull(foundTrackArtistDto)
				|| trackPerformerService.isNotEqualTrackNums(trackAndArtistAlias, trackArtistDto.getTrackCatalogNum())
				|| trackPerformerService.isNotEqualArtistAliases(trackAndArtistAlias, trackArtistDto.getArtistAlias())) {
			return ResponseEntity.badRequest().build();
		}
		trackPerformerService.update(trackArtistDto);
		return ResponseEntity.ok().build();
	}

	/**
	 * 12345@Monatik
	 */
	@DeleteMapping("/{trackAndArtistAlias}")
	public ResponseEntity<Object> delete(@PathVariable String trackAndArtistAlias) {
		TrackArtistDto trackArtistDto = trackPerformerService.getTrackArtistByTrackNumAndArtistAlias(trackAndArtistAlias);
		if (isNull(trackArtistDto)) {
			return ResponseEntity.notFound().build();
		}
		trackPerformerService.deleteTrackArtist(trackArtistDto);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/search")
	public ResponseEntity<List<TrackArtistDto>> getTrackArtistByCriteria(SearchDto searchDto) {
		List<TrackArtistDto> trackArtistDtoList = trackPerformerService.searchArtistTrackPerformance(searchDto);
		if (trackArtistDtoList.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(trackArtistDtoList);
	}
}
