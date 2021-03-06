package Util.Arithmetic.a0.ES;

import com.alibaba.fastjson.JSONObject;
import com.sugon.searchfores.utils.elasticsearch.entity.PageEntity;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;

import java.util.*;

/**
 * es 转化
 */

public class EsCommonOperator {

	private static volatile EsCommonOperator esCommonOperator = null;

	private EsCommonOperator() {
	}

	/**
	 * 单例模式
	 *
	 * @return
	 */
	public static EsCommonOperator getInstance() {
		if (esCommonOperator == null) {
			synchronized (EsCommonOperator.class) {
				if (esCommonOperator == null) {
					esCommonOperator = new EsCommonOperator();
				}
			}
		}
		return esCommonOperator;
	}

	private String ES_DEFAULT_UNIQUE_COLUMN_NAME = "_id";

	/**
	 * SearchResponse 解析转化成 pageEntity
	 *
	 * @param searchResponse
	 * @return
	 */
	public PageEntity<JSONObject> analyzeSearchResponse(SearchResponse searchResponse) {
		PageEntity<JSONObject> analyzeResult = new PageEntity<JSONObject>();
		if (searchResponse != null) {
			long totalCount = searchResponse.getHits().getTotalHits();
			Iterator<SearchHit> searchHitIterator = searchResponse.getHits().iterator();
			List<JSONObject> jsonObjectList = new ArrayList<JSONObject>();
			while (searchHitIterator.hasNext()) {
				SearchHit searchHit = searchHitIterator.next();
				Map<String, Object> map = searchHit.getSourceAsMap();

				JSONObject jsonObject = new JSONObject();
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					jsonObject.put(entry.getKey(), entry.getValue());
				}
				jsonObject.put(ES_DEFAULT_UNIQUE_COLUMN_NAME, searchHit.getId());
				jsonObjectList.add(jsonObject);
			}
			analyzeResult.setContents(jsonObjectList);
			analyzeResult.setTotalCount(totalCount);
		}
		return analyzeResult;
	}


}

c