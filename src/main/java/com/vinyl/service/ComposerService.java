package com.vinyl.service;

import com.vinyl.model.Composer;

import java.util.Date;
import java.util.List;

public interface ComposerService {

	void save(Composer composer);

	Composer getComposerByName(String name);

	List<Composer> getAll();

	void update(Composer composer, String composerName);

	List<Composer> findComposersByCountry(String country);

	List<Composer> findComposersByActivityPeriod(Date activityStart, Date activityEnd);

	List<Composer> findComposerByCriteria(String countryCode, Date activityStart, Date activityEnd);

}
