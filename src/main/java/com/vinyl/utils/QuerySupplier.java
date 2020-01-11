package com.vinyl.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.FileInputStream;
import java.io.IOException;

import static org.apache.commons.lang3.CharEncoding.UTF_8;
import static org.apache.commons.lang3.StringUtils.EMPTY;

@Slf4j
public class QuerySupplier {

	private static final String ROOT_LOCATION = "src/main/resources/queries/";
	private static final String IO_EXCEPTION_MESSAGE = "Can't get sql query from file with path: ";

	public static String getQuery(String relativeFileLocation) {
		String fullFileLocation = ROOT_LOCATION + relativeFileLocation;
		try {
			FileInputStream fis = new FileInputStream(fullFileLocation);
			return IOUtils.toString(fis, UTF_8);
		} catch (IOException exception) {
			log.error(IO_EXCEPTION_MESSAGE + fullFileLocation);
			return EMPTY;
		}
	}

}
