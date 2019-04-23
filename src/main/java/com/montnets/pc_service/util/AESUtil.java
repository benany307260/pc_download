package com.montnets.pc_service.util;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.hutool.core.util.HexUtil;

public class AESUtil
{
	private final static Logger logger = LoggerFactory.getLogger(AESUtil.class);
	
	/**
	 * 加密
	 * @param content 明文
	 * @param keyByte 秘钥
	 * @return
	 */
	public static byte[] encrypt(String content, byte[] keyByte)
	{
		if (keyByte == null)
		{
			return null;
		}
		try
		{
			SecretKeySpec key = new SecretKeySpec(keyByte, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/NOPadding");
			byte[] byteContent = content.getBytes("UTF-8");
			int len = byteContent.length;
			int l = (len%16>0)?(16 - len % 16):0;
			if (l > 0)
			{
				byteContent = conectArrays(byteContent, new byte[l]);
			}
			cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化

			byte[] result = cipher.doFinal(byteContent);

			return result; // 加密

		} catch (Exception e)
		{
			logger.error("AES加密，异常。content="+content, e);
			return null;
		}
	}

	public static byte[] decrypt(byte[] b, byte[] aesKey)
	{
		if (aesKey == null)
		{
			return null;
		}
		try
		{
			byte[] raw = aesKey;
			SecretKeySpec skp = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
			cipher.init(Cipher.DECRYPT_MODE, skp);
			byte[] original = cipher.doFinal(b);
			int i = 0;
			for (i = 0; i < original.length; i++)
			{
				if (original[i] == 0)
				{
					break;
				}
			}
			byte[] bbb = new byte[i];
			System.arraycopy(original, 0, bbb, 0, i);
			return bbb;
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	private static synchronized byte[] conectArrays(byte a[], byte b[])
	{
		byte c[] = new byte[a.length + b.length];
		System.arraycopy(a, 0, c, 0, a.length);
		System.arraycopy(b, 0, c, a.length, b.length);
		return c;
	}
	
	private static byte[] toByteArray(String hexString) 
	{
        hexString = hexString.toLowerCase();
        final byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        for (int i = 0; i < byteArray.length; i++) {//因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
            byteArray[i] = (byte) (high << 4 | low);
            k += 2;
        }
        return byteArray;
    }
	
	public static void main(String[] args) {
		String dbkey = "e10adc3949ba59abbe56e057f20f883e";
		byte[] pwdMi = AESUtil.encrypt("12345", dbkey.getBytes());
		String pwdMiStr = HexUtil.encodeHexStr(pwdMi);
		System.out.println(pwdMiStr);
		
		byte[] pwdByte = AESUtil.decrypt(pwdMi, dbkey.getBytes());
		String pwd = new String(pwdByte);
		System.out.println(pwd);
	}
}
