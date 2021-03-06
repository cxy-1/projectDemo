package Util.Arithmetic.String.encode;

import com.sugon.searchfores.entity.beans.KeyPairMo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * �ַ�ת��
 * @author xyl
 */
public class WordsTransformUtil {


	public static String trim(String s) {
		String result = "";
		if (null != s && !"".equals(s)) {
			result = s.replaceAll("^[��*| *| *|\\s*]*", "").replaceAll("[��*| *| *|\\s*]*$", "");
		}
		return result;
	}


	public static String[] splitByBlank(String line)
	{
		String[] tt=line.split("\\s+");
		return tt;
	}

	public static String[] splitByVertical(String line)
	{
		String[] tt=line.split("\\|+");
		return tt;
	}


	public static List<KeyPairMo<String,List<String>>> transformInputKeyToSearchKey(String inputKey){

		String removeBlankString = WordsTransformUtil.trim(inputKey);
		String[] andArray = WordsTransformUtil.splitByBlank(removeBlankString);
		List<KeyPairMo<String,List<String>>> keywordsList = new ArrayList<KeyPairMo<String,List<String>>>();

		for(String strAnd : andArray){
			KeyPairMo<String,List<String>> keyPairMo = new KeyPairMo<String,List<String>>();
			String[] orArray = WordsTransformUtil.splitByVertical(strAnd);
			if(orArray.length>1){
				keyPairMo.setPairKey("or");
				keyPairMo.setPairValue(Arrays.asList(orArray));
			}else{
				keyPairMo.setPairKey("and");
				keyPairMo.setPairValue(Arrays.asList(strAnd));
			}
			keywordsList.add(keyPairMo);
		}
		return keywordsList;
	}

	public static void main(String[] args) {

		List<KeyPairMo<String,List<String>>> test =  WordsTransformUtil.transformInputKeyToSearchKey("a b|f c|d|e");

		System.out.println(test);




	}
}
