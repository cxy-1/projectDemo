package Util.Arithmetic.String.UUID;

import java.util.Random;
import java.util.UUID;

/**
 * UUID生成类(32位)      唯一id
 * @author wuyl
 *
 */
public final class UUIDUtil_Jsb1 {

	private UUIDUtil_Jsb1(){
	}

	/**
	 * 获取字符ID
	 * @return
	 */
	public static final String getNextValue() {
		String id=UUID.randomUUID().toString();
		return id.replaceAll("-", "");
	}

	/**
	 * 获取数字ID
	 * @return
	 */
	public static final String getNextIntValue() {
		int totalLength=32;
		Random random=new Random();
		StringBuffer num=new StringBuffer();
		String time=String.valueOf(System.currentTimeMillis());
		int timeLength=time.length();
		int cycleLength=totalLength-timeLength;
		for(int i=0;i<cycleLength;i++){
			num.append(random.nextInt(10));
		}
		return time+num.toString();
	}
	/**
	 * 获得一个UUID
	 *
	 * @return String UUID
	 */
	public static String getUUID() {
		String s = UUID.randomUUID().toString();
		return s.replaceAll("-", "");
	}
	public static String[] chars = new String[] { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

	public static String getShortUUID() {
		StringBuffer shortBuffer = new StringBuffer();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		int size = 6;
		for (int i = 0; i < size; i++) {
			String str = uuid.substring(i, i + 4);
			int x = Integer.parseInt(str, 16);
			shortBuffer.append(chars[x % 0x24]);
		}
		return shortBuffer.toString();
	}
	/**
	 *
	 * 说明：替换字符串
	 * @param
	 * @return String
	 *
	 */
	public static final String replace(String template, String placeholder, String replacement) {
		return replace(template, placeholder, replacement, false );
	}
	public static final String replace(String template, String placeholder, String replacement, boolean wholeWords) {
		int loc = template.indexOf( placeholder );
		if ( loc < 0 ) {
			return template;
		}
		else {
			final boolean actuallyReplace = !wholeWords ||
					loc + placeholder.length() == template.length() ||
					!Character.isJavaIdentifierPart( template.charAt( loc + placeholder.length() ) );
			String actualReplacement = actuallyReplace ? replacement : placeholder;
			return new StringBuffer( template.substring( 0, loc ) )
					.append( actualReplacement )
					.append( replace( template.substring( loc + placeholder.length() ),
							placeholder,
							replacement,
							wholeWords ) ).toString();
		}
	}
















	/**
	 * 获得一个UUID
	 *
	 * @return String UUID
	 */
	public static String getUUID() {
		String s = UUID.randomUUID().toString();
		return s.replaceAll("-", "");
	}

	public static String[] chars = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

	public static String getShortUUID() {
		StringBuffer shortBuffer = new StringBuffer();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		for (int i = 0; i < 6; i++) {
			String str = uuid.substring(i, i + 4);
			int x = Integer.parseInt(str, 16);
			shortBuffer.append(chars[x % 0x24]);
		}
		return shortBuffer.toString();
	}

/*	public static void main(String[] args) {
		System.out.println(getShortUUID());
	}
	*/



}
