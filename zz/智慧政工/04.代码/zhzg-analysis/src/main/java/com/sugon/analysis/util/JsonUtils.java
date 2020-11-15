package com.sugon.analysis.util;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.PropertyFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
/**
 * @program: analysis->JsonUtils
 * @description:
 * @author: gaowh
 * @create: 2019-12-24 17:32
 **/
public class JsonUtils {
	public static final int TYPE_FASTJSON = 0;
	public static final int TYPE_GSON = 1;

	/**
	 * <pre>
	 * ����ת��Ϊjson�ַ���
	 *
	 * @param obj ��ת������
	 * @return ����ö����Json�ַ���
	 */
	public static final String toJson(final Object obj) {
		return JSON.toJSONString(obj);
		// return gson.toJson(obj);
	}

	/**
	 * <pre>
	 * ����ת��Ϊjson�ַ���
	 *
	 * @param obj ��ת������
	 * @return ����ö����Json�ַ���
	 */
	public static final String toJson(final Object obj, SerializerFeature... features) {
		return JSON.toJSONString(obj, features);
		// return gson.toJson(obj);
	}

	/**
	 * ����ת��Ϊjson�ַ�������ʽ��
	 *
	 * @param obj
	 * @param format �Ƿ�Ҫ��ʽ��
	 * @return
	 */
	public static final String toJson(final Object obj, final boolean format) {
		return JSON.toJSONString(obj, format);
	}

	/**
	 * �����ָ���ֶν��й��˴�������json�ַ���
	 *
	 * @param obj
	 * @param fields ���˴����ֶ�
	 * @param ignore true�����Դ���false����������
	 * @param features json������Ϊnull����
	 * @return
	 */
	public static final String toJson(final Object obj, final String[] fields, final boolean ignore,
									  SerializerFeature... features) {
		if (fields == null || fields.length < 1) {
			return toJson(obj);
		}
		if (features == null)
			features = new SerializerFeature[] { SerializerFeature.QuoteFieldNames };
		return JSON.toJSONString(obj, new PropertyFilter() {
			@Override
			public boolean apply(Object object, String name, Object value) {
				for (int i = 0; i < fields.length; i++) {
					if (name.equals(fields[i])) {
						return !ignore;
					}
				}
				return ignore;
			}
		}, features);
	}

	/**
	 * <pre>
	 * ����json�ַ�����ĳ·����ֵ
	 *
	 * @param json
	 * @param path
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static final <E> E parse(final String json, final String path) {
		String[] keys = path.split(",");
		JSONObject obj = JSON.parseObject(json);
		for (int i = 0; i < keys.length - 1; i++) {
			obj = obj.getJSONObject(keys[i]);
		}
		return (E) obj.get(keys[keys.length - 1]);
	}

	/**
	 * <pre>
	 * json�ַ�������Ϊ����
	 *
	 * @param json ����һ�������Json�ַ���
	 * @param clazz ָ��Ŀ���������ͣ������ض��������
	 * @return ��json�ַ������������Ķ���
	 */
	public static final <T> T parse(final String json, final Class<T> clazz) {
		return JSON.parseObject(json, clazz);
	}

	/**
	 * <pre>
	 * json�ַ�������Ϊ����
	 *
	 * @param json json�ַ���
	 * @param path ���ŷָ���json��νṹ
	 * @param clazz Ŀ����
	 */
	public static final <T> T parse(final String json, final String path, final Class<T> clazz) {
		String[] keys = path.split(",");
		JSONObject obj = JSON.parseObject(json);
		for (int i = 0; i < keys.length - 1; i++) {
			obj = obj.getJSONObject(keys[i]);
		}
		String inner = obj.getString(keys[keys.length - 1]);
		return parse(inner, clazz);
	}

	/**
	 * ���ƶ��Ķ��󾭹��ֶι��˴���󣬽�����Ϊjson����
	 *
	 * @param obj
	 * @param fields
	 * @param ignore
	 * @param clazz
	 * @param features
	 * @return
	 */
	public static final <T> List<T> parseArray(final Object obj, final String[] fields, boolean ignore,
											   final Class<T> clazz, final SerializerFeature... features) {
		String json = toJson(obj, fields, ignore, features);
		return parseArray(json, clazz);
	}

	/**
	 * <pre>
	 * ��json�ַ����н�����һ������ļ��ϣ��������ַ���Ҫ���ǺϷ��ļ�������
	 * ������:["k1":"v1","k2":"v2",..."kn":"vn"]��
	 *
	 * @param json - [key-value-pair...]
	 * @param clazz
	 * @return
	 */
	public static final <T> List<T> parseArray(final String json, final Class<T> clazz) {
		return JSON.parseArray(json, clazz);
	}

	/**
	 * <pre>
	 * ��json�ַ����а���·��Ѱ�ң���������һ������ļ��ϣ����磺
	 * ��Person��һ������name��Ҫ������json�н������伯�ϣ�
	 * {
	 * 	"page_info":{
	 * 		"items":{
	 * 			"item":[{"name":"KelvinZ"},{"name":"Jobs"},...{"name":"Gates"}]
	 * 	}
	 * }
	 * ʹ�÷�����parseArray(json, "page_info,items,item", Person.class)��
	 * ������ָ��·������ȷ�Ľ��������輯�ϣ��ų�������
	 *
	 * @param json json�ַ���
	 * @param path ���ŷָ���json��νṹ
	 * @param clazz Ŀ����
	 * @return
	 */
	public static final <T> List<T> parseArray(final String json, final String path, final Class<T> clazz) {
		String[] keys = path.split(",");
		JSONObject obj = JSON.parseObject(json);
		for (int i = 0; i < keys.length - 1; i++) {
			obj = obj.getJSONObject(keys[i]);
		}
		String inner = obj.getString(keys[keys.length - 1]);
		List<T> ret = parseArray(inner, clazz);
		return ret;
	}

	/**
	 * <pre>
	 * ��Щjson�ĳ�����ʽ����������Դ����Ա�������ķ�������
	 * ��������ʹ����\" ���� "{ ���� }"����Ѷ��ҳ���г������ָ�ʽ
	 *
	 * @param invalidJson �����Ƿ���ʽ��json�ַ���
	 * @return
	 */
	public static final String correctJson(final String invalidJson) {
		String content = invalidJson.replace("\\\"", "\"").replace("\"{", "{").replace("}\"", "}");
		return content;
	}

	/**
	 * ��ʽ��Json
	 *
	 * @param json
	 * @return
	 */
	public static final String formatJson(String json) {
		Map<?, ?> map = (Map<?, ?>) JSON.parse(json);
		return JSON.toJSONString(map, true);
	}

	/**
	 * ��ȡjson���е���json
	 *
	 * @param json
	 * @param path
	 * @return
	 */
	public static final String getSubJson(String json, String path) {
		String[] keys = path.split(",");
		JSONObject obj = JSON.parseObject(json);
		for (int i = 0; i < keys.length - 1; i++) {
			obj = obj.getJSONObject(keys[i]);
			System.out.println(obj.toJSONString());
		}
		return obj != null ? obj.getString(keys[keys.length - 1]) : null;
	}

}
