package Util.Arithmetic.a0.ES;


import com.alibaba.fastjson.JSONObject;
import com.sugon.searchfores.entity.EsFieldInfoResult;
import com.sugon.searchfores.entity.beans.KeyPairMo;
import com.sugon.searchfores.entity.pojo.ApproximateSearchPojo;
import com.sugon.searchfores.utils.elasticsearch.constants.EsConstant;
import com.sugon.searchfores.utils.elasticsearch.entity.PageEntity;
import org.elasticsearch.action.search.MultiSearchRequest;
import org.elasticsearch.action.search.MultiSearchResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
import static org.elasticsearch.index.query.QueryBuilders.wildcardQuery;

/**
 * es查询操作类
 */
@Component
public class EsQueryOperator {
    private final Logger LOG = LoggerFactory.getLogger(EsQueryOperator.class);

    //初始化
    @Resource
    private RestHighLevelClient restHighLevelClient;

    private EsCommonOperator esCommonOperator = EsCommonOperator.getInstance();


    /**
     * 单个详情 search word all the same
     *
     * @param approximateSearchPojo
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PageEntity<JSONObject> getIndexHitInfoByOne(ApproximateSearchPojo approximateSearchPojo, int pageNum, int pageSize) {

        PageEntity<JSONObject> results = null;
        //模糊查询
        MultiSearchRequest request = queryForApproximate(Arrays.asList(approximateSearchPojo), pageNum, pageSize);
        SearchResponse searchResponse = null;
        try {
            MultiSearchResponse response = restHighLevelClient.msearch(request, RequestOptions.DEFAULT);
            Iterator<MultiSearchResponse.Item> itemIterator = response.iterator();
            long totalHits = 0L;
            while (itemIterator.hasNext()) {
                MultiSearchResponse.Item item = itemIterator.next();
                searchResponse = item.getResponse();
            }
            //组装数据
            results = esCommonOperator.analyzeSearchResponse(searchResponse);
            if (results != null) {
                results.setPageSize(pageSize);
                results.setCurrentPageNo(pageNum);
            }

        } catch (Exception e) {
            LOG.error("exception:"+e);
        }
        return results;
    }


    /**
     * 模糊匹配
     *
     * @param listQuery
     * @param pageNum
     * @param pageSize
     * @return
     */
    private MultiSearchRequest queryForApproximate(List<ApproximateSearchPojo> listQuery, int pageNum, int pageSize) {

        MultiSearchRequest multiSearchRequest = new MultiSearchRequest();

        for (int i = 0; i < listQuery.size(); i++) {
            ApproximateSearchPojo approximateQuery = listQuery.get(i);
            List<KeyPairMo<String, List<String>>> keyPairMoList = approximateQuery.getHandlerKeyWords();

            // use alias
            SearchRequest searchRequest = new SearchRequest(approximateQuery.getIndexAlias());

            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

            BoolQueryBuilder boolQueryBuilder = boolQuery();
            List<EsFieldInfoResult> esFieldInfoList = approximateQuery.getFields();

            for (int j = 0; j < keyPairMoList.size(); j++) {
                KeyPairMo<String, List<String>> keyPairMo = keyPairMoList.get(j);
                BoolQueryBuilder fieldBuilder = boolQuery();
                for (int k = 0; k < esFieldInfoList.size(); k++) {
                    EsFieldInfoResult esFieldInfo = esFieldInfoList.get(k);
                    List<String> conditionList = keyPairMo.getPairValue();
                    QueryBuilder wildcardCondition;
                    for (String searchValue : conditionList) {
                        String esField = esFieldInfo.getFieldEsName();
                        if (esFieldInfo.isPinYinSupport()) {
                            wildcardCondition = wildcardQuery(esField + EsConstant.ES_FIELD_CONNECT_STRING + EsConstant.ES_FIELD_SUFFIX_PINYIN, EsConstant.WILDSTRING+searchValue + EsConstant.WILDSTRING);
                            fieldBuilder.should(wildcardCondition);
                        }
                        if (esFieldInfo.isIkSupport()) {
                            wildcardCondition = wildcardQuery(esField + EsConstant.ES_FIELD_CONNECT_STRING + EsConstant.ES_FIELD_SUFFIX_IK, EsConstant.WILDSTRING+searchValue + EsConstant.WILDSTRING);
                            fieldBuilder.should(wildcardCondition);
                        }
                        if (esFieldInfo.isSynonymSupport()) {
                            wildcardCondition = wildcardQuery(esField + EsConstant.ES_FIELD_CONNECT_STRING + EsConstant.ES_FIELD_SUFFIX_SYNO, EsConstant.WILDSTRING+searchValue + EsConstant.WILDSTRING);
                            fieldBuilder.should(wildcardCondition);
                        }
                        // 默认都是 key进行检索
                        wildcardCondition = wildcardQuery(esField + "", EsConstant.WILDSTRING + searchValue + EsConstant.WILDSTRING);
                        fieldBuilder.should(wildcardCondition);
                    }
                }
                if (EsConstant.ES_QUERY_CONDITION_AND.equals(keyPairMo.getPairKey())) {
                    boolQueryBuilder.must(fieldBuilder);
                } else {
                    boolQueryBuilder.should(fieldBuilder);
                }
            }

            searchSourceBuilder.query(boolQueryBuilder).from((pageNum - 1) * pageSize).size(pageSize);
            searchRequest.source(searchSourceBuilder);
            multiSearchRequest.add(searchRequest);
        }
        return multiSearchRequest;
    }



//    /**
//     * 将搜索词转换成 MultiSearchRequest
//     *
//     * @param listQuery
//     * @param pageNum
//     * @param pageSize
//     * @return
//     */
//    public MultiSearchRequest queryForApproximateFilledConfirm(List<ApproximateSearchPojo> listQuery, int pageNum, int pageSize) {
//        MultiSearchRequest multiSearchRequest = new MultiSearchRequest();
//        for (int i = 0; i < listQuery.size(); i++) {
//            ApproximateSearchPojo approximateQuery = listQuery.get(i);
//            // 用别名 alias - 设置查询指定索引名（不指定则查询所有）
//            SearchRequest searchRequest = new SearchRequest(approximateQuery.getIndexAlias());
//            //创建并设置SearchSourceBuilder对象
//            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//            //查询上层
//            BoolQueryBuilder boolQueryBuilder = boolQuery();
//            List<EsFieldInfoResult> tesFieldInfoList = approximateQuery.getFields();
//            Map<String, String> fieldFilledValueMap = approximateQuery.getFieldValue();
//            Map<String, List<RangeFieldMo>> fieldRangeListMap = approximateQuery.getFieldRangeFieldInfo();
//            //创建字段查询
//            BoolQueryBuilder fieldBuilder = boolQuery();
//            for (int k = 0; k < tesFieldInfoList.size(); k++) {
//                EsFieldInfoResult tesFieldInfo = tesFieldInfoList.get(k);
//                String fieldFillValue = fieldFilledValueMap.get(tesFieldInfo.getFieldEsName());
//                QueryBuilder wildcardCondition;
//
//                String esField = tesFieldInfo.getFieldEsName();
//                // 字段值 不为空 才进行查询
//                if (StringUtils.isNotBlank(fieldFillValue)) {
//                    if (fieldRangeListMap != null && fieldRangeListMap.size() > 0) {
//                        List<RangeFieldMo> rangeFieldMoList = fieldRangeListMap.get(esField);
//                        if (rangeFieldMoList != null && rangeFieldMoList.size() > 0) {
//                            RangeQueryBuilder rangeQueryBuilder = QueryBuilders.rangeQuery(esField);
//                            // default noly support key
//                            for (RangeFieldMo rangeFieldMo : rangeFieldMoList) {
//                                rangeQueryBuilder.from(rangeFieldMo.getStartValue()).includeLower(rangeFieldMo.isIncludeStartValue());
//                                rangeQueryBuilder.to(rangeFieldMo.getEndValue()).includeUpper(rangeFieldMo.isIncludeEndValue());
//                                fieldBuilder.must(rangeQueryBuilder);
//                            }
//                        } else {
//                            if (tesFieldInfo.isPinYinSupport()) {
//                                wildcardCondition = wildcardQuery(esField + EsConstant.ES_FIELD_CONNECT_STRING + EsConstant.ES_FIELD_SUFFIX_PINYIN, fieldFillValue + EsConstant.WILDSTRING);
//                                fieldBuilder.should(wildcardCondition);
//                            }
//                            if (tesFieldInfo.isIkSupport()) {
//                                wildcardCondition = wildcardQuery(esField + EsConstant.ES_FIELD_CONNECT_STRING + EsConstant.ES_FIELD_SUFFIX_IK, fieldFillValue + EsConstant.WILDSTRING);
//                                fieldBuilder.should(wildcardCondition);
//                            }
//                            if (tesFieldInfo.isSynonymSupport()) {
//                                wildcardCondition = wildcardQuery(esField + EsConstant.ES_FIELD_CONNECT_STRING + EsConstant.ES_FIELD_SUFFIX_SYNO, fieldFillValue + EsConstant.WILDSTRING);
//                                fieldBuilder.should(wildcardCondition);
//                            }
//                            // 默认都是 key进行检索
//                            wildcardCondition = wildcardQuery(esField + "", fieldFillValue + EsConstant.WILDSTRING);
//                            fieldBuilder.should(wildcardCondition);
//                        }
//                    } else {
//                        if (tesFieldInfo.isPinYinSupport()) {
//                            wildcardCondition = wildcardQuery(esField + EsConstant.ES_FIELD_CONNECT_STRING + EsConstant.ES_FIELD_SUFFIX_PINYIN, EsConstant.WILDSTRING + fieldFillValue + EsConstant.WILDSTRING);
//                            fieldBuilder.should(wildcardCondition);
//                        }
//                        if (tesFieldInfo.isIkSupport()) {
//                            wildcardCondition = wildcardQuery(esField + EsConstant.ES_FIELD_CONNECT_STRING + EsConstant.ES_FIELD_SUFFIX_IK, EsConstant.WILDSTRING + fieldFillValue + EsConstant.WILDSTRING);
//                            fieldBuilder.should(wildcardCondition);
//                        }
//                        if (tesFieldInfo.isSynonymSupport()) {
//                            wildcardCondition = wildcardQuery(esField + EsConstant.ES_FIELD_CONNECT_STRING + EsConstant.ES_FIELD_SUFFIX_SYNO, EsConstant.WILDSTRING + fieldFillValue + EsConstant.WILDSTRING);
//                            fieldBuilder.should(wildcardCondition);
//                        }
//                        wildcardCondition = wildcardQuery(esField + "", EsConstant.WILDSTRING + fieldFillValue + EsConstant.WILDSTRING);
//                        fieldBuilder.should(wildcardCondition);
//                    }
//                    // 默认全部都是 and 操作
//                    boolQueryBuilder.must(fieldBuilder);
//                }
//            }
//            searchSourceBuilder.query(boolQueryBuilder).from((pageNum - 1) * pageSize).size(pageSize);
//            //将SearchSourceBuilder对象添加搜索请求中
//            searchRequest.source(searchSourceBuilder);
//            multiSearchRequest.add(searchRequest);
//        }
//        return multiSearchRequest;
//    }


}
