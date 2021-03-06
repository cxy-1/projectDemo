package Util.Arithmetic.a0.ES;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.common.settings.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;


/**
 * ES索引操作
 *
 * @author cccpf
 */
public class EsIndexHandler {

    private Client client;

    private EsClient esClient = new EsClient();

    public static String tableName = "sugon_index";

    public static String INDEX_NAME = "sugon-fulltext";

    public static String TABLE_INFO_COLUMN_SUFFIX = "_column_tableinfo";

    public static String TABLE_INFO_ROWID = "1";

    public void init() throws NumberFormatException, UnknownHostException {
        client = esClient.getClient();
    }

    public void close() throws NumberFormatException, UnknownHostException {
        esClient.close();
    }

    private static Logger LOG = LoggerFactory.getLogger(EsIndexHandler.class);

    /**
     * 判断索引是否存在
     *
     * @return
     * @throws NumberFormatException
     * @throws UnknownHostException
     */
    public Boolean existsIndex(String indexname) throws NumberFormatException,
            UnknownHostException {
        IndicesExistsResponse response = client.admin().indices()
                .prepareExists(indexname).execute().actionGet();

        return response.isExists();
    }

    /**
     * 创建索引
     *
     * @return
     * @throws NumberFormatException
     * @throws UnknownHostException
     */
    public Boolean buildIndex(String indexname) throws NumberFormatException,
            UnknownHostException {
        IndicesExistsResponse response = client.admin().indices()
                .prepareExists(indexname).execute().actionGet();
        Boolean flag = true;
        if (!response.isExists()) {
            JSONObject allObj = new JSONObject();
            JSONObject analysisObj = new JSONObject();
            JSONObject analyzerObj = new JSONObject();
            JSONObject pinyinAnalyzerObj = new JSONObject();
            pinyinAnalyzerObj.put("tokenizer", "my_pinyin");
            pinyinAnalyzerObj.put("filter", "word_delimiter");
            analyzerObj.put("pinyin_analyzer", pinyinAnalyzerObj);
            analysisObj.put("analyzer", analyzerObj);

            JSONObject pinyinObj = new JSONObject();
            JSONObject myPinyinObj = new JSONObject();
            pinyinObj.put("type", "pinyin");
            pinyinObj.put("first_letter", "none");
            myPinyinObj.put("my_pinyin", pinyinObj);
            analysisObj.put("tokenizer", myPinyinObj);

            allObj.put("analysis", analysisObj);
            allObj.put("number_of_replicas", 1);
            allObj.put("number_of_shards", 5);
            allObj.put("index.translog.flush_threshold_ops", 10000000);
            allObj.put("refresh_interval", "300s");
            allObj.put("index.codec", "best_compression");

            Settings settings = Settings.settingsBuilder().loadFromSource(allObj.toJSONString()).build();

            //[Settings settings = Settings.settingsBuilder()]
            //[.put("number_of_replicas", 1).put("max_result_window", "1000000").put("number_of_shards", 5)]
            //[.put("index.translog.flush_threshold_ops", 10000000)]
            //[.put("refresh_interval", "300s")]
            //[.put("index.codec", "best_compression").build();]
            CreateIndexResponse createIndxeResponse = client.admin().indices()
                    .prepareCreate(indexname).setSettings(settings).execute()
                    .actionGet();
            flag = createIndxeResponse.isAcknowledged();

            LOG.info("创建索引返回值为:" + flag);
        }
        return flag;
    }

    /**
     * 给索引添加别名
     *
     * @param index 索引名称
     * @param alias 别名
     * @return
     */
    public Boolean addAlias(String index, String alias) {

        IndicesAdminClient indicesAdminClient = client.admin().indices();

        IndicesAliasesResponse response = indicesAdminClient.prepareAliases().addAlias(index, alias).get();

        return response.isAcknowledged();
    }

    /**
     * 删除指定索引
     *
     * @return
     * @throws NumberFormatException
     * @throws UnknownHostException
     */
    public Boolean deleteIndex(String indexname) throws NumberFormatException,
            UnknownHostException {
        boolean flag = true;
        IndicesExistsResponse response = client.admin().indices()
                .prepareExists(indexname).execute().actionGet();
        if (response.isExists()) {
            DeleteIndexResponse response2 = client.admin().indices()
                    .prepareDelete(indexname).execute().actionGet();
            flag = response2.isAcknowledged();
        }
        return flag;
    }

    /**
     * 创建MAPPING
     * <p>
     * type = 1: 全文库
     * <p>
     * type = 2: GIS地图
     *
     * @param mappingname
     * @param listColumns
     * @return
     * @throws NumberFormatException
     * @throws UnknownHostException
     * @throws JSONException
     */
    public Boolean buildMapping(String indexname, String mappingname,
                                List<MappingColumn> listColumns) throws JSONException {
        Boolean flag = false;
        if (client == null) {
            return flag;
        }

        JSONObject typeJson = new JSONObject();
        JSONObject propertiesJson = new JSONObject();
        JSONObject allJson = new JSONObject();

        // _all
        JSONObject allProperties = new JSONObject();
        //[allProperties.put("analyzer", "ik_max_word");]
        //[allProperties.put("search_analyzer", "ik_max_word");]
        //[allProperties.put("term_vector", "no");]
        allProperties.put("enabled", "false");
        allJson.put("_all", allProperties);

        // _source
        JSONObject sourceProperties = new JSONObject();
        List<String> s = new ArrayList<String>();
        s.add("*" + TABLE_INFO_COLUMN_SUFFIX);
        sourceProperties.put("excludes", s);
        allJson.put("_source", sourceProperties);

        // _time
        JSONObject timeProperties = new JSONObject();
        timeProperties.put("enabled", "true");
        allJson.put("_timestamp", timeProperties);

        if (listColumns != null) {
            for (MappingColumn column : listColumns) {
                JSONObject columnProperties = new JSONObject();
                String columnType = "string";
                if (StringUtils.isNotBlank(column.getColumntype())) {
                    columnType = column.getColumntype();
                }
                // column类型
                columnProperties.put("type", columnType);
                columnProperties.put("store", column.getStore());
                columnProperties.put("term_vector", "with_positions_offsets");

                if (column.isIndex()) {
                    columnProperties.put("analyzer", "ik_max_word");
                    columnProperties.put("search_analyzer", "ik_max_word");
                } else {
                    columnProperties.put("index", "not_analyzed");
                }
                if (column.isPinyin()) {
                    JSONObject pinyinPropertiesObj = new JSONObject();
                    JSONObject pinyinObj = new JSONObject();
                    pinyinPropertiesObj.put("type", columnType);
                    pinyinPropertiesObj.put("analyzer", "pinyin_analyzer");
                    pinyinPropertiesObj.put("search_analyzer", "pinyin_analyzer");
                    pinyinPropertiesObj.put("store", column.getStore());
                    pinyinObj.put(column.getColumnname().trim() + "_pinyin", pinyinPropertiesObj);

                    columnProperties.put("fields", pinyinObj);
                }
                //[columnProperties.put("include_in_all", "true");]
                propertiesJson.put(column.getColumnname(), columnProperties);
            }
        }
        allJson.put("properties", propertiesJson);
        typeJson.put(mappingname, allJson);
        String data = typeJson.toString();

        LOG.info("生成创建TYPE的JSON字符串：" + data);
        PutMappingResponse response = null;
        int retryTimes = 5;
        // 防止30s超时
        for (int i = 0; i < retryTimes; i++) {
            try {
                response = client.admin().indices().preparePutMapping(indexname)
                        .setType(mappingname).setSource(data).execute().actionGet();
                flag = response.isAcknowledged();
                if (flag) {
                    break;
                }
            } catch (Exception e) {
                LOG.info("生成创建TYPE错误："+i+" ", e);
                //[e.printStackTrace();]
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                continue;
            }
        }
        LOG.info("生成TYPE的请求返回值：" + flag);
        return flag;
    }
}
