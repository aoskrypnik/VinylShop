package com.vinyl.dao;

import com.vinyl.model.Composer;

import java.sql.Date;
import java.util.List;

public interface ComposerDao {

	void save(Composer composer);

	Composer getComposerByName(String name);

	List<Composer> findComposersByCountry(String country);

	List<Composer> findComposersByActivityPeriod(Date activityStart, Date activityEnd);

	void update(Composer composer, String composerName);

	List<Composer> getAll();
}
