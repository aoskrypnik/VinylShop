package com.vinyl.dao;

import com.vinyl.model.Composer;

import java.util.List;

public interface ComposerDao {

	void save(Composer composer);

	Composer getComposerByName(String name);

	void update(Composer composer, String composerName);

	List<Composer> getAll();

	List<Composer> searchComposers(String query);
}
