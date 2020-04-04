package com.vinyl.controller;

import com.vinyl.controller.authorization.response.ApiResponse;
import com.vinyl.dto.ArtistBandDto;
import com.vinyl.dto.SearchDto;
import com.vinyl.service.ArtistBandService;
import org.springframework.http.HttpStatus;
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
import static org.apache.commons.lang3.BooleanUtils.isFalse;

@RestController
@RequestMapping("/participation")
public class ArtistBandController {

	private static final String DATES_MISMATCH_MESSAGE = "Sorry, there is some conflict with dates";

	@Resource
	private ArtistBandService artistBandService;

	@GetMapping("/{ids}")
	public ResponseEntity<?> getArtistBandByPks(@PathVariable String ids) {
		ArtistBandDto foundArtistBand = artistBandService.getArtistBandByPks(ids);
		if (isNull(foundArtistBand)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(foundArtistBand);
	}

	@PostMapping
	public ResponseEntity<?> saveParticipation(@RequestBody ArtistBandDto artistBandDto) {
		ArtistBandDto foundArtistBand = artistBandService
				.getArtistBandByPks(artistBandDto.getParticipationArtistAlias() + "," + artistBandDto.getParticipationBandAlias());
		if (isNull(foundArtistBand)) {
			return ResponseEntity.badRequest().build();
		}
		if (isFalse(artistBandService.validateArtistBand(artistBandDto))) {
			return new ResponseEntity<>(new ApiResponse(false, DATES_MISMATCH_MESSAGE), HttpStatus.CONFLICT);
		}
		artistBandService.save(artistBandDto);

		return ResponseEntity.status(201).build();
	}

	@PutMapping("/{ids}")
	public ResponseEntity<?> updateParticipation(@PathVariable String ids, @RequestBody ArtistBandDto artistBandDto) {
		if (isNull(artistBandService.getArtistBandByPks(ids))) {
			return ResponseEntity.notFound().build();
		}
		if (isFalse(artistBandService.validateArtistBand(artistBandDto))) {
			return new ResponseEntity<>(new ApiResponse(false, DATES_MISMATCH_MESSAGE), HttpStatus.CONFLICT);
		}
		artistBandService.update(artistBandDto);
		return ResponseEntity.status(202).build();
	}

	@GetMapping("/search")
	public ResponseEntity<?> getParticipationByCriteria(SearchDto searchDto) {
		List<ArtistBandDto> participates = artistBandService.searchArtistBands(searchDto);
		if (participates.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(participates);
	}

	@DeleteMapping("/{ids}")
	public ResponseEntity<?> deleteCustomer(@PathVariable String ids) {
		if (isNull(artistBandService.getArtistBandByPks(ids))) {
			return ResponseEntity.notFound().build();
		}
		artistBandService.deleteByIds(ids);
		return ResponseEntity.noContent().build();
	}
}
