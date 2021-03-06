/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package Util.Arithmetic.Encryption.AES;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * AES对称加密
 */
public class AESUtils3 {

	private AESUtils3() {
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	/*
	 * 生成密钥
	 */
	public static byte[] initKey(String password) throws Exception {
		KeyGenerator keyGen = KeyGenerator.getInstance("AES");
		SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
		random.setSeed(password.getBytes());
		keyGen.init(128,random);
		SecretKey secretKey = keyGen.generateKey();
		return secretKey.getEncoded();
	}

	/*
	 * AES 加密
	 */
	public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		SecretKey secretKey = new SecretKeySpec(key, "AES");

		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);
		byte[] cipherBytes = cipher.doFinal(data);
		return cipherBytes;
	}

	/*
	 * AES 解密
	 */
	public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		SecretKey secretKey = new SecretKeySpec(key, "AES");

		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		byte[] plainBytes = cipher.doFinal(data);
		return plainBytes;
	}

	/**将二进制转换成16进制
	 * @param buf
	 * @return
	 */
	public static String parseByte2HexStr(byte buf[]) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < buf.length; i++) {
			String hex = Integer.toHexString(buf[i] & 0xFF);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			sb.append(hex.toUpperCase());
		}
		return sb.toString();
	}

	/**将16进制转换为二进制
	 * @param hexStr
	 * @return
	 */
	public static byte[] parseHexStr2Byte(String hexStr) {
		if (hexStr.length() < 1)
			return null;
		byte[] result = new byte[hexStr.length()/2];
		for (int i = 0;i< hexStr.length()/2; i++) {
			int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
			int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
			result[i] = (byte) (high * 16 + low);
		}
		return result;
	}

	/**
	 * base64 加密
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public static String encoder(String str) throws Exception {
		final Base64.Encoder encoder = Base64.getEncoder();
		final String encodedText = encoder.encodeToString(str.getBytes("UTF-8"));
		return encodedText;
	}

	/**
	 * base64 解密
	 * @param encodedText
	 * @return
	 * @throws Exception
	 */
	public static String decoder(String encodedText) throws Exception {
		final Base64.Decoder decoder = Base64.getDecoder();
		final String result = new String(decoder.decode(encodedText), "UTF-8");
		return result;
	}

}
