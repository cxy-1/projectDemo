package com.sugon.analysis.util;

import com.sugon.analysis.domain.data.object.oracle.TPersonDo;
import com.sugon.analysis.domain.data.subset.CompanyNew;
import com.sugon.analysis.domain.data.subset.CompanyNewTreeSubset;
import com.sugon.analysis.domain.data.subset.CustomCache;
import com.sugon.analysis.enums.result.ResultModel;
import com.sugon.analysis.repository.oracle.TPersonDao;
import com.sugon.analysis.service.PersonStereoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * @program: analysis->ChildUnit
 * @description:
 * @author: gaowh
 * @create: 2019-12-23 11:57
 **/
@Component
public class ChildUnit {

    private static MongoTemplate mongoTemplate;

    private static TPersonDao tPersonDao;


    private static PersonStereoService personStereoService;

    @Resource
    public void setPersonStereoService(PersonStereoService personStereoService) {
        ChildUnit.personStereoService = personStereoService;
    }

    @Resource
    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        ChildUnit.mongoTemplate = mongoTemplate;
    }

    @Resource
    public void setTPersonDao(TPersonDao tPersonDao) {
        ChildUnit.tPersonDao = tPersonDao;
    }

    /**���β���������*/
    public static List<String> zzbSubList = Stream.of(
            "2014111018472484800000000567", "2014111018472484800000000566", "2014111018472522300000002932", "2014111018472481700000000388",
            "2014111018472487900000000748", "2014111018472480100000000340", "1609121651201070000000004947", "2014111018472475400000000147"
    ).collect(toList());

    /**���β�id*/
    public static String ZZB_ID = "2014111018472522300000002937";

    /**�Ͼ��й�����id*/
    public static String NJSGAJ_ID = "2014110416460086100000002942";

    /**
     * ��Ҫͳ�Ƶļ��������ƺ�
     */
    public static List<String> deptRewards = new ArrayList<>(Arrays.asList(new String[]{
            "ȫ�����㹫����",
            "ȫ�����㹫�����㵥λ",
            "ȫʡ���㹫����",
            "����һ�ȹ�",
            "������ȹ�",
            "�������ȹ�",
            "����ν�",
            "ͨ������",
            "�����ƺ�",
            "�Ƚ�����"
    }));

    /**
     * ��ѯ�Ӳ������г�Ա�����֤��
     *
     * @param deptId
     * @return
     */
    public List<String> getSubPersonIdNos(String deptId) {
//        //������Ͼ��й�����
//        if(NJSGAJ_ID.equals(deptId)){
//            return tPersonDao.findAllIdNosByDelStatusAndPoliceNoIsNotNullAndPClassId();
//        }
        //����װ���֤�ţ��������
        List<String> idNos = new ArrayList<>();
        List<TPersonDo> tPersonDoList = getSubPerson(deptId);
        //���˼������ó����֤��
        if (tPersonDoList.size() > 0) {
            tPersonDoList.forEach(f -> idNos.add(f.getIdNo()));
        }
        //�����֤�Ž���ȥ��
        return idNos.stream().distinct().collect(Collectors.toList());
    }

    /**
     * ��ѯ�Ӳ������г�Ա�����֤��
     *
     * @param deptIds
     * @return
     */
    public List<String> getSubPersonIdNos(List<String> deptIds) {
        String cacheKey = "getSubPersonIdNos&"+ StringUtils.join(deptIds,",");
        List<String> resultModel1 = com.sugon.analysis.util.Cache.getList(cacheKey, String.class);
        if(resultModel1!=null){
            return resultModel1;
        }

        //������Ͼ��й�����
        if(deptIds.contains(NJSGAJ_ID)){
            return tPersonDao.findAllIdNosByDelStatusAndPoliceNoIsNotNullAndPClassId();
        }
        //����װ���֤�ţ��������
        List<String> idNos = new ArrayList<>();

        if(deptIds.contains(ZZB_ID)){
            deptIds.addAll(zzbSubList);
        }
        Query query = Query.query(Criteria.where("ID").in(deptIds));
        //����װ�Ӳ��ŵ�unitId
        List<String> units = new ArrayList<>();
        List<CompanyNewTreeSubset> list = mongoTemplate.find(query, CompanyNewTreeSubset.class, "companyNewTreeSubset");
        list.forEach(v -> getUnitIds(v.getCompanyList(), units));

        if (units.size() > 1000) {
            int listSize = units.size() / 1000;
            for (int i = 0; i < units.size() / 1000; i++) {
                idNos.addAll(tPersonDao.findIdNosBydeptIdIn(units.subList(1000 * i, 1000 * (i + 1))));
            }
            idNos.addAll(tPersonDao.findIdNosBydeptIdIn(units.subList(1000 * listSize, units.size())));
        } else {
            idNos.addAll(tPersonDao.findIdNosBydeptIdIn(units));
        }
        if(deptIds.contains(ZZB_ID)){
            idNos.addAll(personStereoService.getDeptHead(ZZB_ID).stream().map(a->a.getIdNo()).collect(Collectors.toList()));
        }
        idNos = idNos.stream().distinct().collect(Collectors.toList());
        com.sugon.analysis.util.Cache.save(cacheKey,idNos);
        return idNos;
    }

    @Scheduled(cron = "0 30 4 * * ?")
    public void updateCache(){
        List<CustomCache> list = com.sugon.analysis.util.Cache.getCacheListByKeyLike("getSubPerson&");
        for (CustomCache cache : list) {
            String[] keys =cache.getKey().split("&");
            com.sugon.analysis.util.Cache.delete(cache.getKey());
            getSubPerson(keys[1]);
        }


        list = com.sugon.analysis.util.Cache.getCacheListByKeyLike("getSubPersonIdNos&");
        for (CustomCache cache : list) {
            String[] keys =cache.getKey().split("&");
            com.sugon.analysis.util.Cache.delete(cache.getKey());
            getSubPersonIdNos(Arrays.asList(keys[1].split(",")));
        }
    }


    /**
     * ��ѯ�Ӳ������г�Ա
     *
     * @param deptId
     * @return
     */
    public  List<TPersonDo>  getSubPerson(String deptId) {
        String cacheKey = "getSubPerson&"+deptId;
        List<TPersonDo> resultModel1 = com.sugon.analysis.util.Cache.getList(cacheKey, TPersonDo.class);
        if(resultModel1!=null){
            return resultModel1;
        }
        if (NJSGAJ_ID.equals(deptId)){
            List<TPersonDo> all = tPersonDao.findAllByDelStatusAndPoliceNoIsNotNullAndPClassId();
            com.sugon.analysis.util.Cache.save(cacheKey,all);
            return all;
        }

        //����װ�Ӳ��ŵ�unitId
        List<String> units = new ArrayList<>();

        Query query;
        //��������β�
        if (ZZB_ID.equals(deptId)){
            //��ѯ���β����е������Ӳ���
            query = Query.query(Criteria.where("ID").in(zzbSubList));
        }else {
            //��ѯ���е��Ӳ��ţ���ѯ������
            query = Query.query(Criteria.where("ID").is(deptId));
        }
        //ִ��mongodb��ѯ ����CompanyNewTreeSubsetʵ��list����
        List<CompanyNewTreeSubset> list = mongoTemplate.find(query, CompanyNewTreeSubset.class, "companyNewTreeSubset");
        //��������β�
        if (ZZB_ID.equals(deptId)){
            list.forEach(v -> getUnitIds(v.getCompanyList(), units));
            units.add(ZZB_ID);
        }else {
            if(list.size()>0){
                getUnitIds(list.get(0).getCompanyList(), units);
            }
        }
        //����װTPersonDo��Ա
        List<TPersonDo> tPersonDoList = new ArrayList<>();
        //��in��������1000������� ��ѯ��deptId in ��ЩunitId�е���
        if (units.size() > 1000) {
            int listSize = units.size() / 1000;
            for (int i = 0; i < units.size() / 1000; i++) {
                tPersonDoList.addAll(tPersonDao.findAllBydeptIdIn(units.subList(1000 * i, 1000 * (i + 1))));
            }
            tPersonDoList.addAll(tPersonDao.findAllBydeptIdIn(units.subList(1000 * listSize, units.size())));
        } else {
            tPersonDoList.addAll(tPersonDao.findAllBydeptIdIn(units));
        }
        if(ZZB_ID.equals(deptId)){
            tPersonDoList.addAll(tPersonDao.findAllByIdNoIn(personStereoService.getDeptHead(ZZB_ID).stream().map(a->a.getIdNo()).collect(Collectors.toList())));
        }
        tPersonDoList = tPersonDoList.stream().collect(
                Collectors.collectingAndThen(
                        Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(TPersonDo::getIdNo))), ArrayList::new)
        );
        com.sugon.analysis.util.Cache.save(cacheKey,tPersonDoList);
        return tPersonDoList;
    }

    /**
     * ��ѯ�Ӳ���id
     *
     * @param deptId
     * @return
     */
    public List<String> getSubDeptId(String deptId) {
        List<String> units = new ArrayList<>();
        if (ZZB_ID.equals(deptId)){
            units.addAll(zzbSubList);
            return units;
        }else {
            //��ѯ���е��Ӳ���
            Query query = Query.query(Criteria.where("ID").is(deptId));
            List<CompanyNewTreeSubset> list = mongoTemplate.find(query, CompanyNewTreeSubset.class, "companyNewTreeSubset");
            getUnitIds(list.get(0).getCompanyList(), units);
            return units;
        }
    }

    /**
     * �ݹ��ȡ�¼���λ
     *
     * @param companies
     * @param unitIds
     */
    private void getUnitIds(List<CompanyNew> companies, List<String> unitIds) {
        for (CompanyNew company : companies) {
            unitIds.add(company.getId());
            if (company.getChildCompany() != null) {
                getUnitIds(company.getChildCompany(), unitIds);
            }
        }
    }

    public ResultModel departmentTree(String idNumber) {
        String unitId = RoleOfCompany.getRoleCompanyId(idNumber);
        ResultModel resultModel = new ResultModel();
        Query query = Query.query(Criteria.where("ID").is(unitId));
        List<CompanyNewTreeSubset> list = mongoTemplate.find(query, CompanyNewTreeSubset.class, "companyNewTreeSubset");
        CompanyNewTreeSubset companyNewTreeSubset = list.get(0);
        List<CompanyNew> companyList = companyNewTreeSubset.getCompanyList();
        if(NJSGAJ_ID.equals(unitId)){
            List<CompanyNew> zzbList = new ArrayList<>();
            List<CompanyNew> childCompany = companyList.get(0).getChildCompany();
            Iterator<CompanyNew> iterator = childCompany.iterator();
            while (iterator.hasNext()){
                CompanyNew next = iterator.next();
                if (zzbSubList.contains(next.getId())){
                    zzbList.add(next);
                    iterator.remove();
                }
            }

            for (CompanyNew deptDo : childCompany) {
                if(deptDo.getId().equals(ZZB_ID)){
                    deptDo.getChildCompany().addAll(zzbList);
                }
            }
        }else if(ZZB_ID.equals(unitId)){
            List<CompanyNew> childCompany = companyList.get(0).getChildCompany();
            childCompany.clear();
            query = Query.query(Criteria.where("ID").in(zzbSubList));
            list = mongoTemplate.find(query, CompanyNewTreeSubset.class, "companyNewTreeSubset");
            for (CompanyNewTreeSubset newTreeSubset : list) {
                childCompany.addAll(newTreeSubset.getCompanyList());
            }
        }
        resultModel.setResult(companyList);
        return resultModel;
    }


}
