package com.vinyl.service;

import com.vinyl.exception.ComposerExistException;
import com.vinyl.model.Composer;

import java.util.List;

public interface ComposerService {

	String save(Composer composer) throws ComposerExistException;

	Composer getComposerByName(String name);

	List<Composer> getAll();

	void update(Composer composer, String composerName);

	List<String> getTracksByName(String composerName);

	List<Composer> searchComposers(List<String> whereParams, List<String> likeParams, List<String> betweenParams,
								   List<String> joins, String sorting, String order, Integer limit, Integer offset);
}
