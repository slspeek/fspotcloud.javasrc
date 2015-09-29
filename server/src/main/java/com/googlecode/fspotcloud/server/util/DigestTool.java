package com.googlecode.fspotcloud.server.util;

import java.io.Serializable;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.SerializationUtils;

public class DigestTool {

	public String getHash(Serializable o) {
		byte[] serial = SerializationUtils.serialize(o);
		String hash = DigestUtils.md2Hex(serial);
		return hash;
	}

	public static String hash(String username, String plainPassword) {
		return DigestUtils.md5Hex(username + plainPassword);
	}

}
