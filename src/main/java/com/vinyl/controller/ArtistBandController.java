package com.vinyl.controller;

import com.vinyl.controller.authorization.response.ApiResponse;
import com.vinyl.dto.ArtistBandDto;
import com.vinyl.dto.SearchDto;
import com.vinyl.service.ArtistBandService;
import lombok.extern.slf4j.Slf4j;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.Resource;
import java.net.URI;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.BooleanUtils.isFalse;

@Slf4j
@RestController
@RequestMapping("/participation")
public class ArtistBandController {

	private static final String DATES_MISMATCH_MESSAGE = "Sorry, there is some conflict with dates";
	private static final String DUPLICATE_RECORD_IN_DB_MESSAGE = "Sorry, there is already such record in db";

	@Resource
	private ArtistBandService artistBandService;

	/**
	 * Dorofeeve@Vremya%20and%20Steklo
	 */
	@GetMapping("/{ids}")
	public ResponseEntity<ArtistBandDto> getArtistBandByPks(@PathVariable String ids) {
		ArtistBandDto foundArtistBand = artistBandService.getArtistBandByPks(ids);
		if (isNull(foundArtistBand)) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(foundArtistBand);
	}

	@PostMapping
	public ResponseEntity<Object> saveParticipation(@RequestBody ArtistBandDto artistBandDto) {
		String artistAlias = artistBandDto.getParticipationArtistAlias();
		String bandAlias = artistBandDto.getParticipationBandAlias();
		String potentialLocationPath = artistAlias + "@" + bandAlias;
		ArtistBandDto foundArtistBand = artistBandService.getArtistBandByPks(potentialLocationPath);

		if (nonNull(foundArtistBand)) {
			return new ResponseEntity<>(new ApiResponse(false, DUPLICATE_RECORD_IN_DB_MESSAGE), HttpStatus.CONFLICT);
		}
		if (isFalse(artistBandService.isValidArtistBandDates(artistBandDto))) {
			return new ResponseEntity<>(new ApiResponse(false, DATES_MISMATCH_MESSAGE), HttpStatus.CONFLICT);
		}

		artistBandService.save(artistBandDto);

		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{ids}")
				.buildAndExpand(potentialLocationPath)
				.toUri();
		return ResponseEntity.created(location).build();
	}

	/**
	 * Dorofeeve@Vremya%20and%20Steklo
	 */
	@PutMapping("/{ids}")
	public ResponseEntity<Object> updateParticipation(@PathVariable String ids,
													  @RequestBody ArtistBandDto artistBandDto) {
		if (isNull(artistBandService.getArtistBandByPks(ids))) {
			return ResponseEntity.notFound().build();
		}
		if (isFalse(artistBandService.isValidArtistBandDates(artistBandDto))) {
			return new ResponseEntity<>(new ApiResponse(false, DATES_MISMATCH_MESSAGE), HttpStatus.CONFLICT);
		}
		artistBandService.update(artistBandDto);
		return ResponseEntity.status(202).build();
	}

	@GetMapping("/search")
	public ResponseEntity<List<ArtistBandDto>> getParticipationByCriteria(SearchDto searchDto) {
		List<ArtistBandDto> participates = artistBandService.searchArtistBands(searchDto);
		if (participates.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(participates);
	}

	/**
	 * Dorofeeve@Vremya%20and%20Steklo
	 */
	@DeleteMapping("/{ids}")
	public ResponseEntity<Object> deleteCustomer(@PathVariable String ids) {
		if (isNull(artistBandService.getArtistBandByPks(ids))) {
			return ResponseEntity.notFound().build();
		}
		artistBandService.deleteByIds(ids);
		return ResponseEntity.noContent().build();
	}
}
