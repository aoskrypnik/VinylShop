package com.vinyl.utils;

import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.BooleanUtils.isFalse;
import static org.springframework.util.CollectionUtils.isEmpty;

public class QueryBuilder {

	private static final String STRING_TYPE_NAME = "String";
	private static final String NOT_STRING_TYPE_NAME = "Not String";

	public static String build(List<String> whereParams, List<String> likeParams, List<String> betweenParams,
							   List<String> joins, String sorting, String order, String tableName) {
		boolean whereAlreadyUsed = false;

		StringBuilder stringBuilder = new StringBuilder("SELECT ");
		stringBuilder.append(tableName).append(".* ").append("FROM ").append(tableName).append(" ");

		//?joins=artist
		if (isFalse(isEmpty(joins))) {
			for (String join : joins) {
				stringBuilder.append("INNER JOIN ").append(join).append(" ON ")
						.append(JOIN_TABLES_MAP.get(tableName + " " + join)).append(" ");
			}
		}

		//?wheres=age:10
		if (isFalse(isEmpty(whereParams))) {
			whereAlreadyUsed = true;
			stringBuilder.append("WHERE TRUE ");
			processWhereParams(whereParams, stringBuilder);
		}

		//?likes=name:ania
		if (isFalse(isEmpty(likeParams))) {
			if (isFalse(whereAlreadyUsed)) {
				stringBuilder.append("WHERE TRUE ");
				whereAlreadyUsed = true;
			}
			processLikeParams(likeParams, stringBuilder);
		}

		//?betweens=age:10:20
		if (isFalse(isEmpty(betweenParams))) {
			if (isFalse(whereAlreadyUsed)) {
				stringBuilder.append("WHERE TRUE ");
			}
			processBetweenParams(betweenParams, stringBuilder);
		}

		buildOrderingPart(sorting, order, stringBuilder);

		return stringBuilder.toString();
	}

	private static void processBetweenParams(List<String> betweenParams, StringBuilder stringBuilder) {
		for (String param : betweenParams) {
			String[] splitParam = param.split(":");
			String firstParam = formatUrlKey(splitParam[0]);
			String secondParam = formatUrlValue(splitParam[0], splitParam[1]);
			String thirdParam = formatUrlValue(splitParam[0], splitParam[2]);
			stringBuilder.append("AND ").append(firstParam).append(" BETWEEN ")
					.append(secondParam).append(" AND ").append(thirdParam).append(" ");
		}
	}

	private static void processLikeParams(List<String> likeParams, StringBuilder stringBuilder) {
		for (String param : likeParams) {
			String[] splitParam = param.split(":");
			String firstParam = formatUrlKey(splitParam[0]);
			String secondParam = formatUrlValue(splitParam[0], splitParam[1]);
			secondParam = secondParam.substring(1, secondParam.length() - 1);
			stringBuilder.append("AND ").append(firstParam).append(" LIKE ").append("'%").append(secondParam).append("%' ");
		}
	}

	private static void processWhereParams(List<String> whereParams, StringBuilder stringBuilder) {
		for (String param : whereParams) {
			String[] splitParam = param.split(":");
			String firstParam = formatUrlKey(splitParam[0]);
			String secondParam = formatUrlValue(splitParam[0], splitParam[1]);
			stringBuilder.append("AND ").append(firstParam).append("=").append(secondParam).append(" ");
		}
	}

	private static String formatUrlKey(String key) {
		return JAVA_NAME_TO_DATA_BASE_NAME_MAP.get(key).get(0);
	}

	private static String formatUrlValue(String key, String value) {
		String keyType = JAVA_NAME_TO_DATA_BASE_NAME_MAP.get(key).get(1);
		if (keyType.equals(STRING_TYPE_NAME)) {
			value = "'" + value + "'";
		}
		return value;
	}

	private static void buildOrderingPart(String sorting, String order, StringBuilder stringBuilder) {
		if (nonNull(sorting)) {
			stringBuilder.append("ORDER BY ").append(formatUrlKey(sorting)).append(" ");
		}
		if (nonNull(order)) {
			stringBuilder.append(order);
		}
	}

	private static final Map<String, String> JOIN_TABLES_MAP = ImmutableMap.<String, String>builder()
			.put("track track_language", "track.catalog_num=track_language.track_catalog_num")
			.put("album albumgenre","album.catalog_num=albumgenre.album_catalog_num")
			.build();

	private static final Map<String, List<String>> JAVA_NAME_TO_DATA_BASE_NAME_MAP = ImmutableMap.<String, List<String>>builder()
			.put("artistAlias", List.of("artist_alias", STRING_TYPE_NAME))
			.put("isArtistActive", List.of("activity", NOT_STRING_TYPE_NAME))
			.put("countryCode", List.of("country", STRING_TYPE_NAME))
			.put("artistName", List.of("artist_name", STRING_TYPE_NAME))
			.put("artistBirthDate", List.of("birth_date", STRING_TYPE_NAME))
			.put("artistDeathDate", List.of("death_date", STRING_TYPE_NAME))
			.put("bandAlias", List.of("band_alias", STRING_TYPE_NAME))
			.put("isBandActive", List.of("activity", NOT_STRING_TYPE_NAME))
			.put("startYear", List.of("start_year", STRING_TYPE_NAME))
			.put("endYear", List.of("end_year", STRING_TYPE_NAME))
			.put("composerName", List.of("composer_name", STRING_TYPE_NAME))
			.put("activityStart", List.of("activity_start", STRING_TYPE_NAME))
			.put("activityEnd", List.of("activity_end", STRING_TYPE_NAME))
			.put("trackCatalogNum", List.of("catalog_num", STRING_TYPE_NAME))
			.put("trackName", List.of("track_name", STRING_TYPE_NAME))
			.put("duration", List.of("duration", NOT_STRING_TYPE_NAME))
			.put("albumCatalogNum", List.of("catalog_num", STRING_TYPE_NAME))
			.put("releaseYear", List.of("release_year", NOT_STRING_TYPE_NAME))
			.put("albumName", List.of("album_name", STRING_TYPE_NAME))
			.put("variousArtists", List.of("various_artists", NOT_STRING_TYPE_NAME))
			.put("albumGenres", List.of("genre_name", STRING_TYPE_NAME))
			.put("languages", List.of("lang_name", STRING_TYPE_NAME))
			.build();

}
