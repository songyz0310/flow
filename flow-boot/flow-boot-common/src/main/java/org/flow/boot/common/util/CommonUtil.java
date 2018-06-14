package org.flow.boot.common.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

public class CommonUtil {

	/**
	 * convert texture ip to byte array
	 * 
	 * @param ip
	 * @return
	 */
	public static byte[] toIPBytes(String ip) {
		if (ip == null)
			return null;

		String[] tmp = ip.split("\\.");
		if (tmp.length != 4)
			return null;
		byte[] addr = new byte[4];
		for (int i = 0; i < tmp.length; i++)
			addr[i] = (byte) Short.parseShort(tmp[i]);
		return addr;
	}

	/**
	 * convert byte array to HEX string
	 * 
	 * @param data
	 * @return
	 */
	public static String toHex(byte[] data) {
		return toHex(data, false);
	}

	/**
	 * Convert byte array to Hex code
	 * 
	 * @param data
	 * @param isFormat
	 *            format output string: 16 byte one line, one byte is separted by
	 *            space
	 * @return
	 */
	public static String toHex(byte[] data, boolean isFormat) {
		if (data == null || data.length == 0)
			return "";

		int tmp;
		int s1, s2;
		StringBuilder sb = new StringBuilder(data.length * 2 + (isFormat ? data.length + data.length / 16 : 0));
		for (int i = 0; i < data.length; i++) {
			tmp = data[i] & 0x000000FF;
			s1 = tmp / 16;
			s2 = tmp % 16;
			if (s1 < 10)
				sb.append((char) (s1 + 48));
			else if (s1 >= 10)
				sb.append((char) (s1 + 55));
			if (s2 < 10)
				sb.append((char) (s2 + 48));
			else if (s2 >= 10)
				sb.append((char) (s2 + 55));

			if (isFormat) {
				sb.append(" ");

				if ((i + 1) % 16 == 0)
					sb.append("\n");
			}
		}
		return sb.toString();
	}

	/**
	 * Convert Hex string to byte array hex string format is: 2 char represent one
	 * byte,can use space as separator for example, "12 1e 3d ee FF 09"
	 * 
	 * @param hex
	 * @return
	 */
	public static byte[] toBytes(String hex) {
		byte[] buff = new byte[hex.length() / 2];

		int s1, s2, count = 0;
		for (int i = 0; i < hex.length(); i++) {
			if (hex.charAt(i) >= '0' && hex.charAt(i) <= '9')
				s1 = hex.charAt(i) - 48;
			else if (hex.charAt(i) >= 'A' && hex.charAt(i) <= 'F')
				s1 = hex.charAt(i) - 55;
			else if (hex.charAt(i) >= 'a' && hex.charAt(i) <= 'f')
				s1 = hex.charAt(i) - 87;
			else
				continue;

			i++;
			if (hex.charAt(i) >= '0' && hex.charAt(i) <= '9')
				s2 = hex.charAt(i) - 48;
			else if (hex.charAt(i) >= 'A' && hex.charAt(i) <= 'F')
				s2 = hex.charAt(i) - 55;
			else if (hex.charAt(i) >= 'a' && hex.charAt(i) <= 'f')
				s2 = hex.charAt(i) - 87;
			else
				continue;

			buff[count] = ((byte) (s1 * 16 + s2));
			count++;
		}
		byte[] result = new byte[count];
		System.arraycopy(buff, 0, result, 0, result.length);
		return result;
	}

	public static long getLong(Object value, long def) {
		if (value == null)
			return def;
		else if (value instanceof Integer)
			return ((Integer) value).longValue();
		else if (value instanceof Long)
			return ((Long) value).longValue();
		if (value instanceof BigInteger)
			return ((BigInteger) value).longValue();
		else if (value instanceof BigDecimal)
			return ((BigDecimal) value).longValue();
		else
			return def;
	}

	public static double getDouble(Object value, double def) {
		if (value == null)
			return def;
		else if (value instanceof Double)
			return ((Double) value).doubleValue();
		else if (value instanceof Float)
			return ((Float) value).doubleValue();
		else if (value instanceof Integer)
			return ((Integer) value).doubleValue();
		else if (value instanceof Long)
			return ((Long) value).doubleValue();
		if (value instanceof BigInteger)
			return ((BigInteger) value).doubleValue();
		else if (value instanceof BigDecimal)
			return ((BigDecimal) value).doubleValue();
		else
			return def;
	}

	/**
	 * md5加密
	 * 
	 * @author suo
	 * @param str
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String md5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		StringBuffer hexValue = new StringBuffer();
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		byte[] byteArray = str.getBytes("UTF-8");
		byte[] md5Array = md5.digest(byteArray);
		for (int i = 0; i < md5Array.length; i++) {
			int val = ((int) md5Array[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString().toUpperCase();
	}

	/**
	 * sha1摘要算法
	 * 
	 * @param str
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws UnsupportedEncodingException
	 */
	public static String sha1(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		byte[] digest = md.digest(str.getBytes("UTF-8"));
		return DatatypeConverter.printHexBinary(digest);
	}

	/**
	 * 清空StringBuffer
	 * 
	 * @param sbd
	 * @return
	 */
	public static StringBuilder clearStringBuilder(StringBuilder sbd) {
		return sbd.length() < 1 ? sbd : sbd.delete(0, sbd.length());
	}

	public static StringBuffer clearStringBuffer(StringBuffer sbf) {
		return sbf.length() < 1 ? sbf : sbf.delete(0, sbf.length());
	}

	/**
	 * 删除StringBuffer最后一个字符
	 * 
	 * @param sbd
	 * @return
	 */
	public static StringBuilder delStringBuilderLastChar(StringBuilder sbd) {
		return sbd.length() < 1 ? sbd : sbd.deleteCharAt(sbd.length() - 1);
	}

	/**
	 * 删除StringBuffer第一个字符
	 * 
	 * @param sbd
	 * @return
	 */
	public static StringBuilder delStringBuilderFirstChar(StringBuilder sbd) {
		return sbd.length() < 1 ? sbd : sbd.deleteCharAt(0);
	}

	public static String urlEncode(String url) throws UnsupportedEncodingException {
		return URLEncoder.encode(url, "utf-8");
	}

	public static String formString(String s, Map<String, String> param) {
		for (String key : param.keySet()) {
			s = s.replaceAll("#\\{" + key + "\\}", param.get(key));
		}
		return s;
	}

	/**
	 * 获取文件扩展名
	 * 
	 * @param fileName
	 * @return
	 */
	public static String getExt(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
	}

}
