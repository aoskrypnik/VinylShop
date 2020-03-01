package com.vinyl.service;

import com.vinyl.dto.SearchDto;
import com.vinyl.exception.ComposerExistException;
import com.vinyl.model.Composer;

import java.util.List;

public interface ComposerService {

	void save(Composer composer) throws ComposerExistException;

	Composer getComposerByName(String name);

	void update(Composer composer, String composerName);

	List<String> getTracksByName(String composerName);

	List<Composer> searchComposers(SearchDto searchDto);
}
