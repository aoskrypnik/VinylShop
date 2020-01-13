package com.vinyl.utils;

import org.springframework.jdbc.support.KeyHolder;

import java.util.Objects;

public class KeyHolderUtils {

	public static int extractInt(KeyHolder keyHolder, String key) {
		return (int) Objects.requireNonNull(keyHolder.getKeys()).get(key);
	}

}
