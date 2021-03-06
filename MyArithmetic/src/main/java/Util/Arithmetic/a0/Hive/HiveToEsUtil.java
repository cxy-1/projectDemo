package Util.Arithmetic.a0.Hive;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sugon.executor.core.constants.ExecutorEndCodeEnums;
import com.sugon.executor.core.model.fz.ExecutorException;
import com.sugon.executor.core.util.jdbc.HiveJdbcUtil;
import com.sugon.executor.core.util.jdbc.PrestoJdbcUtil;
import com.sugon.executor.hivetoes5x.dao.THiveToEs5xTabDao;
import com.sugon.executor.hivetoes5x.enums.EsConstant;
import com.sugon.executor.hivetoes5x.model.db.EsDBFieldMo;
import com.sugon.executor.hivetoes5x.model.db.EsDBIndexMo;
import com.sugon.executor.hivetoes5x.model.db.THiveToEs5xMo;
import com.sugon.executor.hivetoes5x.model.es.*;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.client.RestClient;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xyl
 *
 * Es hive 操作类
 */
public class HiveToEsUtil {



    public static List<EsFieldMo> transformFzColToEsField(List<EsDBFieldMo> esDBFieldMoList){
        List<EsFieldMo> esFieldMos = new ArrayList<EsFieldMo>();
        for(EsDBFieldMo esDBFieldMo : esDBFieldMoList){
            EsFieldMo esFieldMo = new EsFieldMo();

            esFieldMo.setFieldName(esDBFieldMo.getFieldEsName());
            esFieldMo.setFieldType(esDBFieldMo.getFieldEsType());

            esFieldMo.setFieldType(esDBFieldMo.getFieldType());
            esFieldMo.setEsFieldType(esDBFieldMo.getFieldEsType());
            esFieldMo.setIgnoreLength(esDBFieldMo.getIgnoreLength());
            esFieldMo.setIKSupport(esDBFieldMo.isIKSupport());
            esFieldMo.setKeySupport(esDBFieldMo.isKeySupport());
            esFieldMo.setSynonymSupport(esDBFieldMo.isSynonymSupport());
            esFieldMo.setPinYinSupport(esDBFieldMo.isPinYinSupport());
            esFieldMo.setDefaultAnalyzerType(esDBFieldMo.getDefaultAnalyzerType());
            esFieldMos.add(esFieldMo);
        }
        return esFieldMos;
    }

    public static EsIndexMo transformIndexMo(EsDBIndexMo esDBIndexMo){

        EsIndexMo esIndexMo = new EsIndexMo();
        esIndexMo.setIndexName(esDBIndexMo.getIndexName());
        esIndexMo.setTypeName(esDBIndexMo.getTypeName());
        EsSettingMo esSettingMo = getEsSettingsMo(esDBIndexMo);
        esIndexMo.setEsSettingModel(esSettingMo);
        return  esIndexMo;
    }

    public static EsSettingMo getEsSettingsMo(EsDBIndexMo esDBIndexMo){
        EsSettingMo esSettingMo = new EsSettingMo();
        EsIndexPropertiesMo indexPropertiesModel  = new EsIndexPropertiesMo();
        indexPropertiesModel.setpNumberOfReplicas(esDBIndexMo.getpNumberOfReplicas());
        indexPropertiesModel.setpNumberOfShards(esDBIndexMo.getpNumberOfShards());
        esSettingMo.setIndexPropertiesModel(indexPropertiesModel);

        esSettingMo.setPinYinSupport(esDBIndexMo.isIndPinYinSupport());
        esSettingMo.setSynonymSupport(esDBIndexMo.isIndSynonymSupport());
        esSettingMo.setAnalysisSupport(esDBIndexMo.isIndAnalysisSt());
        esSettingMo.setIKSupport(esDBIndexMo.isIndIKSupport());
        // 两种方式，一种新方式，一种兼容方式
        if(esSettingMo.isAnalysisSupport()){
            EsAnalysisMo esAnalysisModel = new EsAnalysisMo();
            JSONObject analyzerJson = new JSONObject();
            JSONObject tokenizerJson = new JSONObject();
            JSONObject filterJson = new JSONObject();
            JSONObject charFilterJson = new JSONObject();

            EsAnalyzerMo ikAnalyzer = new EsAnalyzerMo();
            ikAnalyzer.setTokenizerName(EsConstant.ES_ANALYZER_IK_MAX);

            EsAnalyzerMo pinyinAnalyzer = new EsAnalyzerMo();
            pinyinAnalyzer.setTokenizerName(EsConstant.ES_SG_TOKENIZER_PINYIN);

            EsAnalyzerMo ikSynonymAnalyzer = new EsAnalyzerMo();
            ikSynonymAnalyzer.setAnalyzerType(EsConstant.ES_ANALYZER_CUSTOM_TYPE);
            ikSynonymAnalyzer.setTokenizerName(EsConstant.ES_ANALYZER_IK_MAX);
            JSONArray filterJsonArray = new JSONArray();
            filterJsonArray.add(EsConstant.ES_FILGER_STOP_NAME);
            filterJsonArray.add(EsConstant.ES_FILGER_SYNONYM_NAME);
            ikSynonymAnalyzer.setEsFilterArray(filterJsonArray);
            JSONArray filterCharJsonArray = new JSONArray();
            filterCharJsonArray.add(EsConstant.ES_CHARFILTER_MAPPING_NAME);
            ikSynonymAnalyzer.setEsFilterCharArray(filterCharJsonArray);

            if(esSettingMo.isIKSupport()){
                analyzerJson.put(EsConstant.ES_SG_ANALYZER_IK,ikAnalyzer);
            }
            if(esSettingMo.isPinYinSupport()){
                //pinyin
                analyzerJson.put(EsConstant.ES_SG_ANALYZER_PINTYIN,pinyinAnalyzer);
                //====tokenizer========
                EsTokenizerPinYinMo esTokenizerPinYinModel = EsTokenizerPinYinMo.getInstance();
                esTokenizerPinYinModel.setKeep_first_letter(esDBIndexMo.isPyKpFirstLetter());
                esTokenizerPinYinModel.setKeep_full_pinyin(esDBIndexMo.isPyKpFullPinyin());
                esTokenizerPinYinModel.setKeep_original(esDBIndexMo.isPyKpOriginal());
                esTokenizerPinYinModel.setKeep_separate_first_letter(esDBIndexMo.isPyKpSepFirstLetter());
                esTokenizerPinYinModel.setLimit_first_letter_length(esDBIndexMo.getPyLimitFtLetterLength());
                esTokenizerPinYinModel.setLowercase(esDBIndexMo.isPyLowercase());

                tokenizerJson.put(esTokenizerPinYinModel.getTokenizerName(),esTokenizerPinYinModel);
            }
            if(esSettingMo.isSynonymSupport()){
                analyzerJson.put(EsConstant.ES_SG_ANALYZER_SYNONYM,ikSynonymAnalyzer);
                //====filter====
                EsFilterMo esFilterMo = EsFilterMo.getSynonymsFilterInstance();
                filterJson.put(esFilterMo.getFilterName(),esFilterMo);
                EsFilterMo esFilterMoStop = EsFilterMo.getStopFilterInstance();
                filterJson.put(esFilterMoStop.getFilterName(),esFilterMoStop);

                //====charfilter=====
                EsCharFilterMo esCharFilterMo = EsCharFilterMo.getInstance();
                charFilterJson.put(esCharFilterMo.getCharFilterName(),esCharFilterMo);
            }

            esAnalysisModel.setAnalyzerList(analyzerJson);
            esAnalysisModel.setTokenizerList(tokenizerJson);
            esAnalysisModel.setFilterList(filterJson);
            esAnalysisModel.setCharfilterList(charFilterJson);

            esSettingMo.setAnalysisModel(esAnalysisModel);
            return  esSettingMo;
        }else{
            // 兼容原来方舟
            return esSettingMo;
        }
    }


    public static EsMappingMo transformEsMapping(EsDBIndexMo esDBIndexMo,List<EsDBFieldMo> esDBFieldMoList){
        EsMappingMo esMappingMo = new EsMappingMo();
        String indexName = esDBIndexMo.getIndexName();
        String typeName = esDBIndexMo.getTypeName();
        esMappingMo.setTypeName(typeName);
        esMappingMo.setIndexName(indexName);
        List<EsFieldMo> esFieldMoList = transformFzColToEsField(esDBFieldMoList);
        JSONObject fieldJson = new JSONObject();
        for(int i=0;i<esFieldMoList.size();i++){
            EsFieldMo esFieldMo = esFieldMoList.get(i);
            fieldJson.put(esFieldMo.getFieldName(),constructEsFieldMo(esFieldMo));
        }
        esMappingMo.setEsFieldMo(fieldJson);
        return esMappingMo;
    }


    public static Object constructEsFieldMo(EsFieldMo esFieldMo){

        if(!EsConstant.ES_DEFAULT_ANALYZER_FZ.equals(esFieldMo.getDefaultAnalyzerType()))
        {
//            if(EsConstant.ES_FIEDLD_TYPE_TEXT.contains(esFieldMo.getEsFieldType())){
            JSONObject esFiledExtend = new JSONObject();
            if(esFieldMo.isSynonymSupport()&&!EsConstant.ES_DEFAULT_ANALYZER_SYNONYM.equals(esFieldMo.getDefaultAnalyzerType())){
                esFiledExtend.put(EsConstant.ES_FIELD_SUFFIX_SYNO,EsFieldMo.getInstanceSyno(EsConstant.ES_FIEDLD_TYPE_TEXT));
            }
            if(esFieldMo.isPinYinSupport()&&!EsConstant.ES_DEFAULT_ANALYZER_PINTYIN.equals(esFieldMo.getDefaultAnalyzerType())){
                esFiledExtend.put(EsConstant.ES_FIELD_SUFFIX_PINYIN,EsFieldMo.getInstancePinYin(EsConstant.ES_FIEDLD_TYPE_TEXT));
            }
            if(esFieldMo.isIKSupport()&&!EsConstant.ES_DEFAULT_ANALYZER_IK.equals(esFieldMo.getDefaultAnalyzerType())){
                esFiledExtend.put(EsConstant.ES_FIELD_SUFFIX_IK,EsFieldMo.getInstanceIK(EsConstant.ES_FIEDLD_TYPE_TEXT));
            }
            esFieldMo.setEsFieldDetailExtend(esFiledExtend);
//                esFieldMo.setIgnoreLength(null);
            esFieldMo.setEsFieldType(EsConstant.ES_DEFAULT_ANALYZER_NOT_ANALYZER);
//            }else{
//                esFieldMo.setIgnoreLength(null);
//            }
            if(EsConstant.ES_DEFAULT_ANALYZER_NOT_ANALYZER.equals(esFieldMo.getDefaultAnalyzerType())){
                // 默认将keyword 设置为主类型
                esFieldMo.setDefaultAnalyzerType(null);
            }
            return esFieldMo;
        }else{
            // not analyzer 兼容老版本方舟 3.0 所有为keyword
            JSONObject jsonTmp = JSONObject.parseObject(JSONObject.toJSONString(esFieldMo));;
            jsonTmp.remove("analyzer");
            jsonTmp.put("type",EsConstant.ES_DEFAULT_ANALYZER_NOT_ANALYZER);
            int numTen = 10;
            if(esFieldMo.getIgnoreLength()<numTen){
                jsonTmp.put("ignore_above",EsConstant.ES_FIELD_DEFAULT_LENGTH);
            }else{
                jsonTmp.put("ignore_above",esFieldMo.getIgnoreLength());
            }

            return jsonTmp;
        }
    }


    public static ExecutorException dropHiveEsTable(String hiveTabName) throws ExecutorException{
        boolean returnFlag = false;
        ExecutorException executorException = null;
        String dropSql = "drop table if exists " + hiveTabName;
        String[] multiSql = {EsConstant.ES_DEFAULT_HIVE_FUNCTION_NAME_HADOOP,EsConstant.ES_DEFAULT_HIVE_FUNCTION_NAME_HTTP,dropSql};
        try {
            HiveJdbcUtil hiveJdbcUtil =   HiveJdbcUtil.getInstance();
            returnFlag = hiveJdbcUtil.excuteMultSql(multiSql);
        }catch (Exception e){
            if(executorException ==null){
                executorException = new ExecutorException(ExecutorEndCodeEnums.HIVE_DROP_TABLE_ERROR.getEndCode());
            }
            e.printStackTrace();
            throw executorException;
        }
        return executorException;
    }

    public static ExecutorException createHiveEsTable(String esIp,int esPort,String hiveTableName, String indexName, String typeName, List<EsDBFieldMo> esDBFieldMos)  throws ExecutorException{

        ExecutorException executorException = null;
        StringBuffer sb = new StringBuffer();
        sb.append("CREATE EXTERNAL TABLE IF NOT EXISTS ");
        sb.append(hiveTableName);
        sb.append("(");
        for(EsDBFieldMo esDBFieldMo : esDBFieldMos){
            if(EsConstant.ES_DEFAULT_ANALYZER_FZ.equals(esDBFieldMo.getDefaultAnalyzerType())){
                // 兼容原先方舟，默认全是string
                sb.append(esDBFieldMo.getFieldName()+"  "+" string ");
            }else{
                sb.append(esDBFieldMo.getFieldName()+"  "+" string ");
//                sb.append(esDBFieldMo.getFieldName()+"  "+esDBFieldMo.getFieldType());
            }

            sb.append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(")");
        sb.append("ROW FORMAT SERDE 'org.elasticsearch.hadoop.hive.EsSerDe' ");
        sb.append("STORED BY 'org.elasticsearch.hadoop.hive.EsStorageHandler' ");
        //[sb.append("WITH SERDEPROPERTIES ('serialization.format'='1')");]
        sb.append("TBLPROPERTIES (");
        sb.append("'es.index.auto.create'='true',");
        sb.append("'es.nodes'='"+esIp+"',");
        sb.append(" 'es.port'='"+esPort+"',");
        sb.append("'es.resource'='");
        sb.append(indexName);
        sb.append("/");
        sb.append(typeName);
        sb.append("'");
        sb.append(")");

        String[] multiSql = {EsConstant.ES_DEFAULT_HIVE_FUNCTION_NAME_HADOOP,EsConstant.ES_DEFAULT_HIVE_FUNCTION_NAME_HTTP,sb.toString()};
        try {
            HiveJdbc5xUtil.getInstance().excuteMultSql(multiSql);
        }catch (Exception e){
            if(executorException==null){
                executorException = new ExecutorException(ExecutorEndCodeEnums.HIVE_CREATE_ERROR.getEndCode());
            }
            e.printStackTrace();
            throw executorException;
        }
        //执行SQL
        return executorException;
    }

    /**
     * 需要根据自定义对象 进行插入操作
     * @param tHiveToEs5xMo
     * @param esDBFieldMoList
     * @return
     */
    public static ExecutorException insertInfoEsTable(THiveToEs5xMo tHiveToEs5xMo,List<EsDBFieldMo> esDBFieldMoList)  throws ExecutorException{
        ExecutorException executorException = null;
        String jobType = tHiveToEs5xMo.getJobType();
        String sqlText = tHiveToEs5xMo.getSqlText();

        StringBuffer sb = new StringBuffer();
        //[tHiveToEs5xMo.getHtabName();]
        sb.append("insert ");
        if(jobType.contains(EsConstant.HIVE_TO_ES5X_JOB_TYPE_FULL)){
            sb.append(" overwrite table ");
        }else{
            sb.append(" into table ");
        }
        sb.append(tHiveToEs5xMo.gethEsDbName());
        sb.append(".");
        sb.append(tHiveToEs5xMo.gethEsTabName());

        if(StringUtils.isNotBlank(sqlText)){
            sb.append(sqlText);
        }else{
            //// TODO: 后期需要需要增加 hive 时间转化函数 保证入库elasticsearch中的数据类型
            sb.append(" select ");
            for(EsDBFieldMo esDBFieldMo : esDBFieldMoList){
                sb.append(esDBFieldMo.getFieldEsName());
                sb.append(",");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append("  from ");
            sb.append(tHiveToEs5xMo.gethDbName());
            sb.append(".");
            sb.append(tHiveToEs5xMo.gethTabName());
            if(jobType.contains(EsConstant.HIVE_TO_ES5X_JOB_TYPE_INCREMENT)){
                if(StringUtils.isNotBlank(tHiveToEs5xMo.getUpdateCondition())){
                    sb.append(" where  ");
                    sb.append(tHiveToEs5xMo.getUpdateCondition());
                }
            }
        }
        String[] multiSql = {EsConstant.ES_DEFAULT_HIVE_FUNCTION_NAME_HADOOP,EsConstant.ES_DEFAULT_HIVE_FUNCTION_NAME_HTTP,sb.toString()};
        try {
            HiveJdbc5xUtil.getInstance().excuteMultSql(multiSql);
        }catch (Exception e){
            if(executorException==null){
                executorException = new ExecutorException(ExecutorEndCodeEnums.HIVE_EXECUTE_SQL_ERROR.getEndCode());
            }
            e.printStackTrace();
            throw executorException;
        }
        //执行SQL
        return executorException;
    }

    /**
     * 兼容方舟 presto 方式获取数据
     * @param tHiveToEs5xTabDao
     * @param tHiveToEs5xMo
     * @return
     */
    public static ExecutorException updateTHiveToEs5xColByPresto(THiveToEs5xTabDao tHiveToEs5xTabDao,THiveToEs5xMo tHiveToEs5xMo) throws ExecutorException{
        ExecutorException executorException = null;
        String tableName = tHiveToEs5xMo.gethDbName()+"."+tHiveToEs5xMo.gethTabName();
        String updateColName = tHiveToEs5xMo.getUpdateColName();
        String updateCondition = "";

        try {
            String updateValue = PrestoJdbcUtil.getInstance().queryForMaxCondition(tHiveToEs5xMo.getUpdateColName(),tableName);
            if(EsConstant.HIVE_INCREMENT_COL_NUMBERIC_TYPE.contains(tHiveToEs5xMo.getUpdateColType())){
                updateCondition = updateColName + EsConstant.HIVE_INCREMENT_COL_CONDITION +updateValue;
            }else{
                updateCondition = updateColName +  EsConstant.HIVE_INCREMENT_COL_CONDITION +"'"+updateValue+"'";
            }
            tHiveToEs5xTabDao.updateIncrementColValueById(tHiveToEs5xMo.getId(),updateValue,updateCondition);

        }catch (Exception e){
            if(executorException == null){
                executorException = new ExecutorException(ExecutorEndCodeEnums.PRESTO_MAX_COL_VALUE_ERROR.getEndCode());
            }
            e.printStackTrace();
            throw executorException;
        }
        return executorException;
    }

    /**
     * es的方式获取更新值
     * @param restClient
     * @param tHiveToEs5xTabDao
     * @param tHiveToEs5xMo
     * @return
     */
    public static ExecutorException updateTHiveToEs5xColByEs(RestClient restClient,THiveToEs5xTabDao tHiveToEs5xTabDao, THiveToEs5xMo tHiveToEs5xMo){
        ExecutorException executorException = null;
        String updateColName = tHiveToEs5xMo.getUpdateColName();
        String updateCondition = "";
        String indexName = tHiveToEs5xMo.getIndexName()+EsConstant.ES_INDEX_SUFFIX_ALIAS;

        try {
            String updateValue = EsUtil.getMaxFieldValue(restClient,tHiveToEs5xMo.getIndexName(),tHiveToEs5xMo);

            if(EsConstant.HIVE_INCREMENT_COL_NUMBERIC_TYPE.contains(tHiveToEs5xMo.getUpdateColType())){
                updateCondition = updateColName + EsConstant.HIVE_INCREMENT_COL_CONDITION +updateValue;
            }else{
                updateCondition = updateColName +  EsConstant.HIVE_INCREMENT_COL_CONDITION +"'"+updateValue+"'";
            }
            tHiveToEs5xTabDao.updateIncrementColValueById(tHiveToEs5xMo.getId(),updateValue,updateCondition);

        }catch (Exception e){
            if(executorException == null){
                executorException = new ExecutorException(ExecutorEndCodeEnums.ELASTICSEARCH_GET_MAX_VALUE_ERROR.getEndCode());
            }
            e.printStackTrace();
        }
        return executorException;
    }

}
