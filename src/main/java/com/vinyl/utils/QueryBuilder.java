package com.vinyl.utils;

import com.google.common.collect.ImmutableMap;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.apache.commons.lang3.BooleanUtils.isFalse;
import static org.springframework.util.CollectionUtils.isEmpty;

public class QueryBuilder {

	private static final String STRING_TYPE_NAME = "String";
	private static final String NOT_STRING_TYPE_NAME = "Not String";

	public static String build(List<String> whereParams, List<String> likeParams, List<String> betweenParams,
							   List<String> joins, String sorting, String order,
							   Integer limit, Integer offset, String tableName) {

		boolean whereAlreadyUsed = false;
		StringBuilder stringBuilder = new StringBuilder();

		buildSelectPart(tableName, stringBuilder);

		if (isFalse(isEmpty(joins))) {
			for (String join : joins) {
				stringBuilder.append("INNER JOIN ").append(join).append(" ON ")
						.append(JOIN_TABLES_MAP.get(tableName + " " + join)).append(" ");
			}
		}

		if (isFalse(isEmpty(whereParams))) {
			whereAlreadyUsed = true;
			stringBuilder.append("WHERE TRUE ");
			processWhereParams(whereParams, stringBuilder);
		}

		if (isFalse(isEmpty(likeParams))) {
			if (isFalse(whereAlreadyUsed)) {
				stringBuilder.append("WHERE TRUE ");
				whereAlreadyUsed = true;
			}
			processLikeParams(likeParams, stringBuilder);
		}

		if (isFalse(isEmpty(betweenParams))) {
			if (isFalse(whereAlreadyUsed)) {
				stringBuilder.append("WHERE TRUE ");
			}
			processBetweenParams(betweenParams, stringBuilder);
		}

		buildOrderingPart(sorting, order, stringBuilder);

		buildingOffsetPart(limit, offset, stringBuilder);

		return stringBuilder.toString();
	}

	private static void buildSelectPart(String tableName, StringBuilder stringBuilder) {
		stringBuilder.append("SELECT ");
		stringBuilder.append(tableName).append(".* ").append("FROM ").append(tableName).append(" ");
	}

	private static void processBetweenParams(List<String> betweenParams, StringBuilder stringBuilder) {
		for (String param : betweenParams) {
			String[] splitParam = param.split(":");
			String firstParam = formatUrlKey(splitParam[0]);
			String secondParam = formatUrlValue(splitParam[0], splitParam[1]);
			String thirdParam = "";
			if (splitParam.length > 2) {
				thirdParam = formatUrlValue(splitParam[0], splitParam[2]);
			}
			boolean isSecondParamPresent = false;
			stringBuilder.append("AND ").append(firstParam);
			if (secondParam.length() > 0 && isFalse(secondParam.equals("''"))) {
				stringBuilder.append(">").append(secondParam).append(" ");
				isSecondParamPresent = true;
			}
			if (thirdParam.length() > 0 && isFalse(thirdParam.equals("''"))) {
				if (isSecondParamPresent) {
					stringBuilder.append(" AND ").append(firstParam);
				}
				stringBuilder.append("<").append(thirdParam).append(" ");
			}
		}
	}

	private static void processLikeParams(List<String> likeParams, StringBuilder stringBuilder) {
		for (String param : likeParams) {
			String[] splitParam = param.split(":");
			String firstParam = formatUrlKey(splitParam[0]);
			String secondParam = formatUrlValue(splitParam[0], splitParam[1]);
			secondParam = secondParam.substring(1, secondParam.length() - 1);
			stringBuilder.append("AND ")
					.append(firstParam)
					.append(" LIKE ")
					.append("'%")
					.append(secondParam)
					.append("%' ");
		}
	}

	private static void processWhereParams(List<String> whereParams, StringBuilder stringBuilder) {
		Map<String, List<Integer>> repeatedWhereParamsMap = retrieveRepeatedWhereParamsMap(whereParams);

		for (String param : whereParams) {
			String[] splitParam = param.split(":");
			String firstParam = formatUrlKey(splitParam[0]);
			if (isFalse(repeatedWhereParamsMap.containsKey(firstParam))) {
				String secondParam = formatUrlValue(splitParam[0], splitParam[1]);
				stringBuilder.append("AND ").append(firstParam).append("=").append(secondParam).append(" ");
			}
		}

		for (Map.Entry<String, List<Integer>> entry : repeatedWhereParamsMap.entrySet()) {
			stringBuilder.append("And (");
			for (Integer i : entry.getValue()) {
				String[] splitParam = whereParams.get(i).split(":");
				String value = formatUrlValue(splitParam[0], splitParam[1]);
				stringBuilder.append(entry.getKey()).append("=").append(value).append(" OR ");
			}
			stringBuilder.delete(stringBuilder.length() - 4, stringBuilder.length()).append(") ");
		}
	}

	private static Map<String, List<Integer>> retrieveRepeatedWhereParamsMap(List<String> whereParams) {
		Map<String, List<Integer>> repeatedWhereParamsMap = whereParams.stream()
				.map(e -> formatUrlKey(e.split(":")[0]))
				.distinct()
				.collect(toMap(Function.identity(),
						e -> IntStream.range(0, whereParams.size())
								.filter(i -> formatUrlKey(whereParams.get(i).split(":")[0]).equals(e))
								.boxed()
								.collect(toList())));

		for (String key : repeatedWhereParamsMap.keySet()) {
			if (repeatedWhereParamsMap.get(key).size() < 2) {
				repeatedWhereParamsMap.remove(key);
			}
		}

		return repeatedWhereParamsMap;
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
			stringBuilder.append(order).append(" ");
		}
	}

	private static void buildingOffsetPart(Integer limit, Integer offset, StringBuilder stringBuilder) {
		if (nonNull(limit)) {
			stringBuilder.append("LIMIT ").append(limit).append(" ");
		}
		if (nonNull(offset)) {
			stringBuilder.append("OFFSET ").append(offset);
		}
	}

	private static final Map<String, String> JOIN_TABLES_MAP = ImmutableMap.<String, String>builder()
			.put("track track_language", "track.catalog_num=track_language.track_catalog_num")
			.put("track track2album", "track.catalog_num=track2album.track_catalog_num")
			.put("track track2composer", "track.catalog_num=track2composer.track_catalog_num")
			.put("album albumgenre", "album.catalog_num=albumgenre.album_catalog_num")
			.put("cheq salesman", "cheq.salesman_tab_num=salesman.tab_num")
			.put("cheq customer", "cheq.customer_num=customer.customer_num")
			.put("record release", "record.release_bar_code=release.bar_code")
			.put("artist2band artist", "artist2band.artist_alias=artist.artist_alias")
			.put("artist2band band", "artist2band.band_alias=band.band_alias")
			.build();

	private static final Map<String, List<String>> JAVA_NAME_TO_DATA_BASE_NAME_MAP = ImmutableMap.<String, List<String>>builder()
			.put("artistAlias", List.of("artist_alias", STRING_TYPE_NAME))
			.put("isArtistActive", List.of("activity", NOT_STRING_TYPE_NAME))
			.put("artistCountryCode", List.of("artist.country", STRING_TYPE_NAME))
			.put("bandCountryCode", List.of("band.country", STRING_TYPE_NAME))
			.put("composerCountryCode", List.of("composer.country", STRING_TYPE_NAME))
			.put("releaseCountryCode", List.of("release.country", STRING_TYPE_NAME))
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
			.put("releaseBarcode", List.of("bar_code", STRING_TYPE_NAME))
			.put("releaseDate", List.of("release_date", STRING_TYPE_NAME))
			.put("recordSize", List.of("record_size", STRING_TYPE_NAME))
			.put("recordSpeed", List.of("record_speed", STRING_TYPE_NAME))
			.put("copiesCount", List.of("copies_cnt", STRING_TYPE_NAME))
			.put("isRepress", List.of("repress", STRING_TYPE_NAME))
			.put("label", List.of("label", STRING_TYPE_NAME))
			.put("albumIds", List.of("album_catalog_num", STRING_TYPE_NAME))
			.put("composerIds", List.of("composer_name", STRING_TYPE_NAME))
			.put("recordBarcode", List.of("bar_code", STRING_TYPE_NAME))
			.put("releaseBarcodeFk", List.of("release_bar_code", STRING_TYPE_NAME))
			.put("supplierEdrpou", List.of("supplier_edrpou", STRING_TYPE_NAME))
			.put("purchaseDate", List.of("purchase_date", STRING_TYPE_NAME))
			.put("purchasePrice", List.of("purchase_price", NOT_STRING_TYPE_NAME))
			.put("sellPrice", List.of("sell_price", NOT_STRING_TYPE_NAME))
			.put("available", List.of("available", NOT_STRING_TYPE_NAME))
			.put("recordState", List.of("state", STRING_TYPE_NAME))
			.put("stateCheckDate", List.of("state_check_date", STRING_TYPE_NAME))
			.put("edrpou", List.of("edrpou", STRING_TYPE_NAME))
			.put("supplierAddress", List.of("address", STRING_TYPE_NAME))
			.put("phoneNumber", List.of("phone_num", STRING_TYPE_NAME))
			.put("customerNum", List.of("customer_num", NOT_STRING_TYPE_NAME))
			.put("customerName", List.of("customer_name", STRING_TYPE_NAME))
			.put("customerEmail", List.of("email", STRING_TYPE_NAME))
			.put("customerDiscount", List.of("discount", NOT_STRING_TYPE_NAME))
			.put("tabNum", List.of("tab_num", NOT_STRING_TYPE_NAME))
			.put("salesmanName", List.of("salesman_name", STRING_TYPE_NAME))
			.put("passportNum", List.of("passport_num", STRING_TYPE_NAME))
			.put("addressCity", List.of("address_city", STRING_TYPE_NAME))
			.put("addressStr", List.of("address_str", STRING_TYPE_NAME))
			.put("addressHome", List.of("address_home", STRING_TYPE_NAME))
			.put("addressApt", List.of("address_apt", NOT_STRING_TYPE_NAME))
			.put("salesmanPhoneNum", List.of("phone_num", STRING_TYPE_NAME))
			.put("worksFrom", List.of("works_from", STRING_TYPE_NAME))
			.put("worksTo", List.of("works_to", STRING_TYPE_NAME))
			.put("salary", List.of("salary", NOT_STRING_TYPE_NAME))
			.put("salesmanLogin", List.of("login", STRING_TYPE_NAME))
			.put("checkNum", List.of("check_num", NOT_STRING_TYPE_NAME))
			.put("dateTime", List.of("date_time", NOT_STRING_TYPE_NAME))
			.put("salesmanTabNum", List.of("salesman_tab_num", NOT_STRING_TYPE_NAME))
			.put("overallSum", List.of("overall_sum", NOT_STRING_TYPE_NAME))
			.put("checkDiscount", List.of("check_discount", NOT_STRING_TYPE_NAME))
			.put("writeOffNum", List.of("write_off_num", NOT_STRING_TYPE_NAME))
			.put("offRecordBarcode", List.of("record_bar_code", NOT_STRING_TYPE_NAME))
			.put("salesmanNum", List.of("salesman_num", NOT_STRING_TYPE_NAME))
			.put("writeOffDate", List.of("write_off_date", STRING_TYPE_NAME))
			.put("fee", List.of("fee", NOT_STRING_TYPE_NAME))
			.put("reason", List.of("reason", STRING_TYPE_NAME))
			.build();

}
