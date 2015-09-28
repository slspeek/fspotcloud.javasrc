package com.googlecode.fspotcloud.server.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.SerializationUtils;

import java.io.Serializable;

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
