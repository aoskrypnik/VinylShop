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

import javax.annotation.Resource;
import java.util.List;

import static java.util.Objects.isNull;

@RestController
@RequestMapping("/artist2track")
public class ArtistToTrackController {

	@Resource
	private TrackPerformerService trackPerformerService;

	@PostMapping
	public ResponseEntity<?> save(@RequestBody TrackArtistDto trackArtistDto) {
		trackPerformerService.save(trackArtistDto);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/{trackAndArtistAlias}")
	public ResponseEntity<?> update(@PathVariable String trackAndArtistAlias,
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

	@GetMapping("/{trackAndArtistAlias}")
	public ResponseEntity<?> getByTrackAndPerformerName(@PathVariable String trackAndArtistAlias) {
		TrackArtistDto trackArtistDto = trackPerformerService.getTrackArtistByTrackNumAndArtistAlias(trackAndArtistAlias);
		if (isNull(trackArtistDto)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(trackArtistDto);
	}

	@DeleteMapping("/{trackAndArtistAlias}")
	public ResponseEntity<?> delete(@PathVariable String trackAndArtistAlias) {
		TrackArtistDto trackArtistDto = trackPerformerService.getTrackArtistByTrackNumAndArtistAlias(trackAndArtistAlias);
		if (isNull(trackArtistDto)) {
			return ResponseEntity.notFound().build();
		}
		trackPerformerService.deleteTrackArtist(trackArtistDto);
		return ResponseEntity.noContent().build();
	}

	@GetMapping("/search")
	public ResponseEntity<?> getTrackArtistByCriteria(SearchDto searchDto) {
		List<TrackArtistDto> trackArtistDtoList = trackPerformerService.searchArtistTrackPerformance(searchDto);
		if (trackArtistDtoList.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(trackArtistDtoList);
	}
}
