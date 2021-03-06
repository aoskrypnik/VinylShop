package com.vinyl.service.impl;

import com.vinyl.dao.ComposerDao;
import com.vinyl.dto.SearchDto;
import com.vinyl.exception.ComposerExistException;
import com.vinyl.model.Composer;
import com.vinyl.service.ComposerService;
import com.vinyl.utils.QueryBuilder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
public class ComposerServiceImpl implements ComposerService {

	private final static String COMPOSER_TABLE_NAME = "composer";
	private static final String COMPOSER_ALREADY_EXIST = "Composer already exists with such name: ";

	@Resource
	private ComposerDao composerDao;

	@Override
	public void save(Composer composer) throws ComposerExistException {
		String composerName = composer.getComposerName();
		Composer foundComposer = composerDao.getComposerByName(composerName);
		if (nonNull(foundComposer)) {
			throw new ComposerExistException(COMPOSER_ALREADY_EXIST + composerName);
		}
		composerDao.save(composer);
	}

	@Override
	public Composer getComposerByName(String name) {
		return composerDao.getComposerByName(name);
	}

	@Override
	public void update(Composer composer, String composerName) {
		composerDao.update(composer, composerName);
	}

	@Override
	public List<String> getTracksByName(String composerName) {
		Composer foundComposer = getComposerByName(composerName);
		return isNull(foundComposer) ? null : foundComposer.getTrackIds();
	}

	@Override
	public List<Composer> searchComposers(SearchDto searchDto) {
		String query = QueryBuilder
				.build(searchDto.getWheres(), searchDto.getLikes(), searchDto.getBetweens(),
						searchDto.getJoins(), searchDto.getSort(), searchDto.getOrder(),
						searchDto.getLimit(), searchDto.getOffset(), COMPOSER_TABLE_NAME);
		return composerDao.searchComposers(query);
	}

}
