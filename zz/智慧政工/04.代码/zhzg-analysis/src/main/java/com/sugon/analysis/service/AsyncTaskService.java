package com.sugon.analysis.service;

import com.sugon.analysis.domain.param.TeamSelect;
import com.sugon.analysis.domain.view.teambuild.RankListVo;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * @program: analysis->AsyncTaskService
 * @description:
 * @author: gaowh
 * @create: 2019-12-11 18:22
 **/
@Service
public class AsyncTaskService {
    @Resource
    private TeamBuildService teamBuildService;

    /*********************************************************
     *                    ��ȡ��ҳ��ǩ��Ϣ                    *
     *********************************************************/
    /**�������Ķ���*/
    @Async
    public Future<Map<String, Object>> getPersonCountMax(TeamSelect teamSelect){
        List<Map<String, Object>> personCount = teamBuildService.getPersonCount(teamSelect);
        return new AsyncResult<> (personCount.size()>0? personCount.get(0): new HashMap<String, Object>(){{
            put("name", "");
            put("value", "");
        }});
    }

    /**ƽ��������С�Ķ���*/
    @Async
    public Future<Map<String, Object>> getAgeMapMin(TeamSelect teamSelect){
        List<Map<String, Object>> ageInfo = teamBuildService.getAgeInfo(teamSelect);
        return new AsyncResult<> (ageInfo.size()>0? ageInfo.get(0): new HashMap<String, Object>(){{
            put("name", "");
            put("value", "");
        }});
    }

    /**Ů��ռ����ߵĶ���*/
    @Async
    public Future<Map<String, Object>> getFemaleCountMax(TeamSelect teamSelect){
        List<Map<String, Object>> femaleCount = teamBuildService.getFemaleCount(teamSelect);
        return new AsyncResult<> (femaleCount.size()>0? femaleCount.get(0): new HashMap<String, Object>(){{
            put("name", "");
            put("value", "");
        }});
    }

    /**�����������Ķ���*/
    @Async
    public Future<Map<String, Object>> getHonorMax(TeamSelect teamSelect){
        List<Map<String, Object>> honorInfo = teamBuildService.getHonorInfo(teamSelect);
        return new AsyncResult<> (honorInfo.size()>0? honorInfo.get(0): new HashMap<String, Object>(){{
            put("name", "");
            put("value", "");
        }});
    }

    /**�ۺ��������ߵĶ���*/
    @Async
    public Future<Map<String, String>> getSatisfiedMax(TeamSelect teamSelect){
        List<Map<String, String>> resultList = teamBuildService.getSatisfiedInfo(teamSelect);
        return new AsyncResult<> (resultList.size()>0?resultList.get(0): new HashMap<String, String>(){{
            put("name", "");
            put("value", "");
        }});
    }

    /**Υ���Ҽ����ٵĶ���*/
    @Async
    public Future<Map<String, Object>> getBreakRuleCaseMin(TeamSelect teamSelect){
        List<Map<String, Object>> breakRuleCase = teamBuildService.getBreakRuleCase(teamSelect);
        return new AsyncResult<> (breakRuleCase.size()>0? breakRuleCase.get(0): new HashMap<String, Object>(){{
            put("name", "");
            put("value", "");
        }});
    }

    /*********************************************************
     *                     ��ȡ���а���Ϣ                     *
     *********************************************************/
    /**��������*/
    @Async
    public Future<Boolean> getPersonCount(TeamSelect teamSelect, List<RankListVo> rankList){
        List<Map<String, Object>> list = teamBuildService.getPersonCount(teamSelect);
        list.forEach(v -> rankList.forEach(x -> {
            if (v.get("name").equals(x.getName())){
                x.setCount(Integer.parseInt(v.get("value").toString()));
            }
        }));
        return null;
    }

    /**����ƽ������*/
    @Async
    public Future<Boolean> getAvgAge(TeamSelect teamSelect, List<RankListVo> rankList){
        List<Map<String, Object>> list = teamBuildService.getAgeInfo(teamSelect);
        list.forEach(v -> rankList.forEach(x -> {
            if (v.get("name").equals(x.getName())){
                x.setAge(Double.parseDouble(v.get("value").toString()));
            }
        }));
        return null;
    }

    /**����Ů��ռ��*/
    @Async
    public Future<Boolean> getFemalePercent(TeamSelect teamSelect, List<RankListVo> rankList){
        List<Map<String, Object>> list = teamBuildService.getFemaleCount(teamSelect);
        list.forEach(v -> rankList.forEach(x -> {
            if (v.get("name").equals(x.getName()) && v.get("value")!=null){
                x.setFemale(Double.parseDouble(v.get("value").toString().substring(0, v.get("value").toString().indexOf("%"))));
            }
        }));
        return null;
    }

    /**���鼯������ռ��*/
    @Async
    public Future<Boolean> getOrgHonorPercent(TeamSelect teamSelect, List<RankListVo> rankList){
        List<Map<String, Object>> list = teamBuildService.getOrgHonorInfo(teamSelect);
        list.forEach(v -> rankList.forEach(x -> {
            if (v.get("name").equals(x.getName())){
                x.setOrgHonor(Double.parseDouble(v.get("value").toString()));
            }
        }));
        return null;
    }

    /**�����������ռ��*/
    @Async
    public Future<Boolean> getPersonHonorPercent(TeamSelect teamSelect, List<RankListVo> rankList){
        List<Map<String, Object>> list = teamBuildService.getPersonHonorInfo(teamSelect);
        list.forEach(v -> rankList.forEach(x -> {
            if (v.get("name").equals(x.getName())){
                x.setPersonHonor(Double.parseDouble(v.get("value").toString()));
            }
        }));
        return null;
    }

    /**�����ۺ������ռ��*/
    @Async
    public Future<Boolean> getSatisfied(TeamSelect teamSelect, List<RankListVo> rankList){
        List<Map<String, String>> list = teamBuildService.getSatisfiedInfo(teamSelect);
        if (list.size() > 0){
            list.forEach(v -> rankList.forEach(x -> {
                if (v.get("name").equals(x.getName())){
                    x.setSatisfaction(Double.parseDouble(String.format("%.2f", Double.parseDouble(v.get("value")))));
                }
            }));
        }
        return null;
    }

    /**�ͼ참���˾�ռ��*/
    @Async
    public Future<Boolean> getCasePercent(TeamSelect teamSelect, List<RankListVo> rankList){
        List<Map<String, Object>> list = teamBuildService.getBreakRuleCase(teamSelect);
        list.forEach(v -> rankList.forEach(x -> {
            if (v.get("name").equals(x.getName())){
                x.setIllegal(Double.parseDouble(v.get("value").toString()));
            }
        }));
        return null;
    }

}
