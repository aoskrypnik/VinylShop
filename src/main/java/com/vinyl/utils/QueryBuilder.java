package com.vinyl.utils;

import java.util.List;
import java.util.Map;

import static java.util.Objects.nonNull;
import static org.apache.commons.lang3.BooleanUtils.isFalse;

public class QueryBuilder {

	public static String build(List<String> whereParams, List<String> likeParams, List<String> betweenParams,
							   List<String> joins, String sorting, String order, String tableName) {
		boolean whereAlreadyUsed = false;
		StringBuilder stringBuilder = new StringBuilder("SELECT ");
		stringBuilder.append(tableName).append(".* ").append("FROM ").append(tableName).append(" ");

		//?joins=artist
		if (isFalse(joins.isEmpty())) {
			for (String join : joins) {
				stringBuilder.append("INNER JOIN ").append(join).append(" ON ")
						.append(joinTablesMap.get(tableName + "," + join)).append(" ");
			}
		}

		//?wheres=age:10
		if (isFalse(whereParams.isEmpty())) {
			whereAlreadyUsed = true;
			stringBuilder.append("WHERE TRUE ");
			for (String param : whereParams) {
				String[] splitParam = param.split(":");
				stringBuilder.append("AND ").append(splitParam[0]).append("=").append(splitParam[1]).append(" ");
			}
		}

		//?likes=name:ania
		if (isFalse(likeParams.isEmpty())) {
			if (isFalse(whereAlreadyUsed)) {
				stringBuilder.append("WHERE TRUE ");
				whereAlreadyUsed = true;
			}
			for (String param : likeParams) {
				String[] splitParam = param.split(":");
				stringBuilder.append("AND ").append(splitParam[0]).append(" LIKE ").append("'%")
						.append(splitParam[1]).append("%' ");
			}
		}

		//?betweens=age:10:20
		if (isFalse(betweenParams.isEmpty())) {
			if (isFalse(whereAlreadyUsed)) {
				stringBuilder.append("WHERE TRUE ");
			}
			for (String param : betweenParams) {
				String[] splitParam = param.split(":");
				stringBuilder.append("AND ").append(splitParam[0]).append(" BETWEEN ")
						.append(splitParam[1]).append(" AND ").append(splitParam[2]).append(" ");
			}
		}

		buildOrderingPart(sorting, order, stringBuilder);

		return stringBuilder.toString();
	}

	private static void buildOrderingPart(String sorting, String order, StringBuilder stringBuilder) {
		if (nonNull(sorting)) {
			stringBuilder.append("ORDER BY ").append(sorting).append(" ");
		}
		if (nonNull(order)) {
			stringBuilder.append(order);
		}
	}

	private final static Map<String, String> joinTablesMap = Map.of("", "");
}

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
