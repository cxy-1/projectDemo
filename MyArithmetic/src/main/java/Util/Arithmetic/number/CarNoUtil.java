package Util.Arithmetic.number;

import java.util.Random;

public class CarNoUtil {

	public static String getCarNo() {
		String [] a = {"A", "B", "C", "D", "E", "F","G","H","J","K","L","M","N"};
		int index = new Random().nextInt(a.length);
		String cardno = a[index];
		return cardno + getRandomCharAndNumr(4);
	}

	private static String getRandomCharAndNumr(Integer length) {
		String str = "";
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			boolean b = random.nextBoolean();
			if (b) { // �ַ���
				// int choice = random.nextBoolean() ? 65 : 97; ȡ��65��д��ĸ����97Сд��ĸ
				str += (char) (65 + random.nextInt(26));// ȡ�ô�д��ĸ
			} else { // ����
				str += String.valueOf(random.nextInt(10));
			}
		}
		return str;
	}

}
