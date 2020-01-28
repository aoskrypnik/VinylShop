package com.vinyl.utils;

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
			for (String param : whereParams) {
				String[] splitParam = param.split(":");
				String firstParam = formatUrlKey(splitParam[0]);
				String secondParam = formatUrlValue(splitParam[0], splitParam[1]);
				stringBuilder.append("AND ").append(firstParam).append("=").append(secondParam).append(" ");
			}
		}

		//?likes=name:ania
		if (isFalse(isEmpty(likeParams))) {
			if (isFalse(whereAlreadyUsed)) {
				stringBuilder.append("WHERE TRUE ");
				whereAlreadyUsed = true;
			}
			for (String param : likeParams) {
				String[] splitParam = param.split(":");
				String firstParam = formatUrlKey(splitParam[0]);
				String secondParam = formatUrlValue(splitParam[0], splitParam[1]);
				stringBuilder.append("AND ").append(firstParam).append(" LIKE ").append("'%").append(secondParam).append("%' ");
			}
		}

		//?betweens=age:10:20
		if (isFalse(isEmpty(betweenParams))) {
			if (isFalse(whereAlreadyUsed)) {
				stringBuilder.append("WHERE TRUE ");
			}
			for (String param : betweenParams) {
				String[] splitParam = param.split(":");
				String firstParam = formatUrlKey(splitParam[0]);
				String secondParam = formatUrlValue(splitParam[0], splitParam[1]);
				String thirdParam = formatUrlValue(splitParam[0], splitParam[2]);
				stringBuilder.append("AND ").append(firstParam).append(" BETWEEN ")
						.append(secondParam).append(" AND ").append(thirdParam).append(" ");
			}
		}

		buildOrderingPart(sorting, order, stringBuilder);

		return stringBuilder.toString();
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

	private static final Map<String, String> JOIN_TABLES_MAP = Map.of("", "");

	private static final Map<String, List<String>> JAVA_NAME_TO_DATA_BASE_NAME_MAP = Map.of(
			"artistAlias", List.of("artist_alias", STRING_TYPE_NAME),
			"isArtistActive", List.of("activity", NOT_STRING_TYPE_NAME),
			"countryCode", List.of("country", STRING_TYPE_NAME),
			"artistName", List.of("artist_name", STRING_TYPE_NAME),
			"artistBirthDate", List.of("birth_date", STRING_TYPE_NAME),
			"artistDeathDate", List.of("death_date", STRING_TYPE_NAME)
	);
}


//	private String artistAlias;
//	private Boolean isArtistActive;
//	private String countryCode;
//	private String artistName;
//	private Date artistBirthDate;
//	private Date artistDeathDate;

//GET /sellers?likes[]=“check.sellerId;tet”
//
//
//		ArrayList<String> wheres = new ArrayList();
//
//		For (String like : likes) {
//		String[] likeParts = like.split(‘;’);
//		wheres.add(likeParts[0] + “ LIKE `%“ + likeParts[1] + “%`”);
//		}
//
//		wheres.join(“ AND “)
//
//		SELECT check.*
//		FROM check
//		%JOIN seller ON check.sellerId = seller.id%\
//		WHERE …
//
//
//		/searchChecks
//
//		Wheres
//		client.id=2
//		Seller.id = 2
//
//		Betweens
//		check.date=5,10
//		check.sum=10,100
//
//		Likes
//
//
//		sort=check.date
//		order=desc,asc
//
//		1. [client.id, check.date, check.sum, seller.id]
//		2. [client, check, seller]
//		3. [client, seller] // Array of tables that have to be joined
//
//		JOIN %tableName% on %criteria%
//
//
//		Map criteria (
//		Client,check => ‘’client.id = check.clientId”
//		Seller,check => “seller.id = check.sellerId”
//		)
//
//		4. [“JOIN client on client.id = check.clientId”, “JOIN seller on seller.id = check.sellerId” ]
//		5. “JOIN client on client.id = check.clientId JOIN seller on seller.id = check.sellerId”
//
//		SELECT check.*
//		FROM check
//		%joins%
//		%if wheres is not empty, then%WHERE %wheres%
//		%if sort is not empty, then%ORDER BY %order%
//		%if sort is not empty, desc ? DESC : ASC%
