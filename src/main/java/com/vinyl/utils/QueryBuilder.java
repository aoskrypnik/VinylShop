package com.vinyl.utils;

import com.google.common.collect.ImmutableMap;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;

import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.apache.commons.lang3.BooleanUtils.isFalse;
import static org.apache.commons.lang3.BooleanUtils.isTrue;
import static org.springframework.util.CollectionUtils.isEmpty;

@Slf4j
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
				stringBuilder
						.append("INNER JOIN ")
						.append(join)
						.append(JOIN_TABLES_MAP.get(tableName.compareTo(join) < 0 ? tableName + " " + join : join + " " + tableName))
						.append(" ");
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

		log.info("Performing next query: " + stringBuilder.toString());
		return stringBuilder.toString();
	}

	private static void buildSelectPart(String tableName, StringBuilder stringBuilder) {
		stringBuilder
				.append("SELECT DISTINCT ")
				.append(tableName)
				.append(".* FROM ")
				.append(tableName)
				.append(" ");
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
			if (splitParam.length == 1) return;
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

	private static boolean isPpkParam(String param) {
		return JAVA_PPK_NAME_TO_DATA_BASE_PPK_NAME_MAP.containsKey(param);
	}

	private static List<String> ppkParams(String param) {
		return JAVA_PPK_NAME_TO_DATA_BASE_PPK_NAME_MAP.get(param);
	}

	private static void processWhereParams(List<String> whereParams, StringBuilder stringBuilder) {
		Map<String, List<Integer>> repeatedWhereParamsMap = retrieveRepeatedWhereParamsMap(whereParams);

		for (String param : whereParams) {
			String[] splitParam = param.split(":");

			if (isTrue(repeatedWhereParamsMap.containsKey(splitParam[0])))
				continue;

			if (isPpkParam(splitParam[0])) {
				String[] realValues = splitParam[1].split("@");
				List<String> realParams = ppkParams(splitParam[0]);

				for (int i = 0; i < realValues.length; ++i) {
					String dbName = formatUrlKey(realParams.get(i));
					String dbValue = formatUrlValue(realParams.get(i), realValues[i]);
					stringBuilder.append("AND ").append(dbName).append("=").append(dbValue).append(" ");
				}
			} else {
				String firstParam = formatUrlKey(splitParam[0]);
				if (splitParam[0].equals("available")) {
					boolean value = Boolean.parseBoolean(formatUrlValue(splitParam[0], splitParam[1]));
					stringBuilder.append("AND ").append(firstParam).append(" IS ");
					if (!value)
						stringBuilder.append("NOT ");
					stringBuilder.append("NULL ");
				} else {
					String secondParam = formatUrlValue(splitParam[0], splitParam[1]);
					stringBuilder.append("AND ").append(firstParam).append("=").append(secondParam).append(" ");
				}
			}

		}

		for (Map.Entry<String, List<Integer>> entry : repeatedWhereParamsMap.entrySet()) {
			stringBuilder.append("AND (");
			for (Integer i : entry.getValue()) {
				String[] splitParam = whereParams.get(i).split(":");
				if (isPpkParam(splitParam[0])) {
					String[] realValues = splitParam[1].split("@");
					List<String> realParams = ppkParams(splitParam[0]);

					stringBuilder.append("(");
					for (int j = 0; j < realValues.length; ++j) {
						String dbName = formatUrlKey(realParams.get(j));
						String dbValue = formatUrlValue(realParams.get(j), realValues[j]);
						stringBuilder.append(dbName).append("=").append(dbValue).append(" AND ");
					}
					stringBuilder.delete(stringBuilder.length() - 5, stringBuilder.length()).append(")").append(" OR ");
				} else {
					String value = formatUrlValue(splitParam[0], splitParam[1]);
					String name = formatUrlKey(entry.getKey());
					stringBuilder.append(name).append("=").append(value).append(" OR ");
				}
			}
			stringBuilder.delete(stringBuilder.length() - 4, stringBuilder.length()).append(") ");
		}
	}

	private static Map<String, List<Integer>> retrieveRepeatedWhereParamsMap(List<String> whereParams) {
		Map<String, List<Integer>> repeatedWhereParamsMap = whereParams.stream()
				.map(e -> e.split(":")[0])
				.distinct()
				.collect(toMap(Function.identity(),
						e -> IntStream.range(0, whereParams.size())
								.filter(i -> whereParams.get(i).split(":")[0].equals(e))
								.boxed()
								.collect(toList())));


		repeatedWhereParamsMap.keySet().removeIf(key -> repeatedWhereParamsMap.get(key).size() < 2);
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
			.put("track track_language", " ON track.catalog_num=track_language.track_catalog_num")
			.put("track track2album", " ON track.catalog_num=track2album.track_catalog_num")
			.put("track track2composer", " ON track.catalog_num=track2composer.track_catalog_num")
			.put("artist2track track", " ON track.catalog_num=artist2track.track_catalog_num")
			.put("band2track track", " ON track.catalog_num=band2track.track_catalog_num")
			.put("cheq salesman", " ON cheq.salesman_tab_num=salesman.tab_num")
			.put("cheq customer", " ON cheq.customer_num=customer.customer_num")
			.put("cheq record", " ON cheq.check_num=record.check_num")
			.put("customer customer_phone_number", " ON customer.customer_num=customer_phone_number.customer_num")
			.put("record release", " ON record.release_bar_code=release.bar_code")
			.put("record supplier", " ON record.supplier_edrpou=supplier.edrpou")
			.put("artist artist2band", " ON artist2band.artist_alias=artist.artist_alias")
			.put("artist2band band", " ON artist2band.band_alias=band.band_alias")
			.put("artist artist2track", " USING (artist_alias)")
			.put("band band2track", " USING (band_alias)")
			.put("composer track2composer", " ON composer.composer_name=track2composer.composer_name")
			.put("album release", " ON album.catalog_num=release.album_catalog_num")
			.put("album track2album", " ON album.catalog_num=track2album.album_catalog_num")
			.put("album albumgenre", " ON album.catalog_num=albumgenre.album_catalog_num")
			.build();

	private static final Map<String, List<String>> JAVA_NAME_TO_DATA_BASE_NAME_MAP = ImmutableMap.<String, List<String>>builder()
			.put("artistAlias", List.of("artist.artist_alias", STRING_TYPE_NAME))
			.put("artistIds", List.of("artist2track.artist_alias", STRING_TYPE_NAME))
			.put("participationArtistAlias", List.of("artist2band.artist_alias", STRING_TYPE_NAME))
			.put("isArtistActive", List.of("activity", NOT_STRING_TYPE_NAME))
			.put("artistCountryCode", List.of("artist.country", STRING_TYPE_NAME))
			.put("bandCountryCode", List.of("band.country", STRING_TYPE_NAME))
			.put("composerCountryCode", List.of("composer.country", STRING_TYPE_NAME))
			.put("releaseCountryCode", List.of("release.country", STRING_TYPE_NAME))
			.put("artistName", List.of("artist_name", STRING_TYPE_NAME))
			.put("artistBirthDate", List.of("birth_date", STRING_TYPE_NAME))
			.put("artistDeathDate", List.of("death_date", STRING_TYPE_NAME))
			.put("bandAlias", List.of("band.band_alias", STRING_TYPE_NAME))
			.put("participationBandAlias", List.of("artist2band.band_alias", STRING_TYPE_NAME))
			.put("isBandActive", List.of("activity", NOT_STRING_TYPE_NAME))
			.put("checksNum", List.of("check_num", NOT_STRING_TYPE_NAME))
			.put("startYear", List.of("start_year", STRING_TYPE_NAME))
			.put("endYear", List.of("end_year", STRING_TYPE_NAME))
			.put("composerName", List.of("composer_name", STRING_TYPE_NAME))
			.put("activityStart", List.of("activity_start", STRING_TYPE_NAME))
			.put("activityEnd", List.of("activity_end", STRING_TYPE_NAME))
			.put("trackCatalogNum", List.of("catalog_num", STRING_TYPE_NAME))
			.put("bindingTrackCatalogNum", List.of("track_catalog_num", STRING_TYPE_NAME))
			.put("trackCatalogNums", List.of("track_catalog_num", STRING_TYPE_NAME))
			.put("trackName", List.of("track_name", STRING_TYPE_NAME))
			.put("duration", List.of("duration", NOT_STRING_TYPE_NAME))
			.put("albumCatalogNum", List.of("catalog_num", STRING_TYPE_NAME))
			.put("releaseYear", List.of("release_year", NOT_STRING_TYPE_NAME))
			.put("albumName", List.of("album_name", STRING_TYPE_NAME))
			.put("variousArtists", List.of("various_artists", NOT_STRING_TYPE_NAME))
			.put("albumGenres", List.of("genre_name", STRING_TYPE_NAME))
			.put("languages", List.of("lang_name", STRING_TYPE_NAME))
			.put("releaseBarcode", List.of("bar_code", STRING_TYPE_NAME))
			.put("releaseBarcodes", List.of("bar_code", STRING_TYPE_NAME))
			.put("releaseDate", List.of("release_date", STRING_TYPE_NAME))
			.put("recordSize", List.of("record_size", STRING_TYPE_NAME))
			.put("recordSpeed", List.of("record_speed", STRING_TYPE_NAME))
			.put("copiesCount", List.of("copies_cnt", STRING_TYPE_NAME))
			.put("isRepress", List.of("repress", STRING_TYPE_NAME))
			.put("label", List.of("label", STRING_TYPE_NAME))
			.put("albumIds", List.of("album_catalog_num", STRING_TYPE_NAME))
			.put("composerIds", List.of("composer_name", STRING_TYPE_NAME))
			.put("recordBarcode", List.of("record.bar_code", STRING_TYPE_NAME))
			.put("releaseBarcodeFk", List.of("release_bar_code", STRING_TYPE_NAME))
			.put("supplierEdrpou", List.of("supplier_edrpou", STRING_TYPE_NAME))
			.put("purchaseDate", List.of("purchase_date", STRING_TYPE_NAME))
			.put("purchasePrice", List.of("purchase_price", NOT_STRING_TYPE_NAME))
			.put("sellPrice", List.of("sell_price", NOT_STRING_TYPE_NAME))
			.put("available", List.of("check_num", NOT_STRING_TYPE_NAME))
			.put("recordState", List.of("state", STRING_TYPE_NAME))
			.put("stateCheckDate", List.of("state_check_date", STRING_TYPE_NAME))
			.put("edrpou", List.of("edrpou", STRING_TYPE_NAME))
			.put("supplierAddress", List.of("address", STRING_TYPE_NAME))
			.put("phoneNumber", List.of("phone_num", STRING_TYPE_NAME))
			.put("customerNum", List.of("customer.customer_num", NOT_STRING_TYPE_NAME))
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
			.put("isFeaturing", List.of("featuring", NOT_STRING_TYPE_NAME))
			.put("sumWithDiscount", List.of("sum_with_discount", NOT_STRING_TYPE_NAME))
			.put("trackIds", List.of("track_catalog_num", STRING_TYPE_NAME))
			.put("productBarcodes", List.of("bar_code", STRING_TYPE_NAME))
			.put("customerPhoneNumbers", List.of("phone_number", STRING_TYPE_NAME))
			.build();

	private static final Map<String, List<String>> JAVA_PPK_NAME_TO_DATA_BASE_PPK_NAME_MAP = ImmutableMap.<String, List<String>>builder()
			.put("artistBindings", List.of("bindingTrackCatalogNum", "artistIds"))
			.put("participations", List.of("participationArtistAlias", "participationBandAlias"))
			.build();

}
