package com.sugon.analysis.service;

import com.sugon.analysis.domain.data.object.oracle.SysDeptDo;
import com.sugon.analysis.domain.data.object.oracle.TEducationDo;
import com.sugon.analysis.domain.data.object.oracle.TPersonDo;
import com.sugon.analysis.domain.data.query.PersonQo;
import com.sugon.analysis.domain.data.query.TeamLeaderDetailQuery;
import com.sugon.analysis.domain.view.teambuild.BreakRuleCaseVo;
import com.sugon.analysis.domain.view.teambuild.TeamInfoVo;
import com.sugon.analysis.enums.result.ResultModel;
import com.sugon.analysis.mappper.oracle.PersonalAnalysisMapper;
import com.sugon.analysis.mappper.oracle.TeamBuildMapper;
import com.sugon.analysis.repository.oracle.SgQueryCategoryDao;
import com.sugon.analysis.repository.oracle.SysDeptDao;
import com.sugon.analysis.repository.oracle.TEducationDao;
import com.sugon.analysis.repository.oracle.TPersonDao;
import com.sugon.analysis.util.ChildUnit;
import com.sugon.analysis.util.CommonUnit;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.sugon.analysis.util.CommonUnit.ZZB_ID;
import static com.sugon.analysis.util.CommonUnit.zzbSubList;

/**
 * @program: analysis->TeamBuildService
 * @description:
 * @author: gaowh
 * @create: 2019-10-28 15:34
 **/
@Service
public class TeamLeaderBuildService extends TeamBaseService {
    @Resource
    private TeamBuildMapper teamBuildMapper;
    @Resource
    private SysDeptDao sysDeptDao;
    @Resource
    private TeamComparisonService teamComparisonService;
    @Resource
    private TPersonDao tPersonDao;
    @Resource
    private ChildUnit childUnit;
    @Resource
    private TeamBuildService teamBuildService;
    @Resource
    private PersonStereoService personStereoService;
    @Resource
    private TEducationDao tEducationDao;
    @Resource
    private SgQueryCategoryDao sgQueryCategoryDao;
    @Resource
    private PersonalAnalysisMapper personalAnalysisMapper;

    public ResultModel getDetail(TeamLeaderDetailQuery query) {

        //�Ƿ�ȡ�ϼ�����
        boolean fetchSuper = "appointmentTime".equals(query.getCategory()) || !query.getSub();
        //�Ƿ�ȡ�¼�����
        boolean fetchSub = "appointmentTime".equals(query.getCategory()) || query.getSub();

        //��λ�㼶 1-�о֣�2-�־֡����� 3-�ɳ������־ֲ��š���������
        String unitId = query.getUnitId();
        final SysDeptDo sysDept = sysDeptDao.findByIdAndDelStatus(unitId, 0);
        String deptLevel = sysDept.getDeptLevel();
        if(zzbSubList.contains(unitId)){
            deptLevel = "3";
        }

        //��ȡ�о������� �־ֺͲ�����ص���Ϣ��ͨ����ԱȨ��idNumber��
        Map<String, Object> teamInfo ;
        List<String> fjpcsIds = null;
        List<String> bmIds = null;

        /**
         * �쵼�������֤����
         */
        //deptLevel=1ʱ��superIdNosΪ�о��쵼���ӣ�deptLevel=2ʱ��superIdNosΪ���Ż�־��쵼����
        final List<String> superIdNos = new ArrayList<>();
        final List<String> fjpcsIdNos = new ArrayList<>();
        final List<String> bmIdIdNos = new ArrayList<>();
        final List<String> idNos = new ArrayList<>();

        //�Ƿ����о�
        if("1".equals(deptLevel)){
            teamInfo = teamComparisonService.getTeamInfoByUnitId(unitId);

            if (fetchSuper) {
                superIdNos.addAll(teamBuildMapper.findOneLeaderIdNum().stream().distinct().collect(Collectors.toList()));
            }

            List<String> temp = new ArrayList<>();
            if (fetchSub) {
                List<TeamInfoVo> teamInfos = (List<TeamInfoVo>) teamInfo.get("fj");
                fjpcsIds = teamInfos.stream().map(a->a.getId()).collect(Collectors.toList());
                fjpcsIdNos.addAll(childUnit.getSubPersonIdNos(fjpcsIds));

                teamInfos = (List<TeamInfoVo>) teamInfo.get("bm1");
                bmIds = teamInfos.stream().map(a->a.getId()).collect(Collectors.toList());
                bmIdIdNos.addAll(childUnit.getSubPersonIdNos(bmIds));

                temp = teamBuildMapper.findTwoLeaderIdNum(fjpcsIdNos);
                fjpcsIdNos.clear();
                fjpcsIdNos.addAll(temp.stream().distinct().collect(Collectors.toList()));

                temp = teamBuildMapper.findTwoLeaderIdNum(bmIdIdNos);
                bmIdIdNos.clear();
                bmIdIdNos.addAll(temp.stream().distinct().collect(Collectors.toList()));
            }

            temp.clear();
            temp.addAll(superIdNos);
            temp.addAll(fjpcsIdNos);
            temp.addAll(bmIdIdNos);
            idNos.addAll(temp);

        }else if("2".equals(deptLevel)){
            teamInfo = (Map<String, Object>) teamBuildService.getChildUnit(unitId).getData();

            if (fetchSuper) {
                List<String> collect = tPersonDao.findAllBydeptIdIn(Arrays.asList(unitId)).stream().map(a -> a.getIdNo()).collect(Collectors.toList());
                superIdNos.addAll(teamBuildMapper.findTwoLeaderIdNum(collect));
            }

            List<String> temp=new ArrayList<>();
            if (fetchSub) {
                List<TeamInfoVo> teamInfos = (List<TeamInfoVo>)teamInfo.get("pcs");
                if(teamInfos.size()>0){
                    fjpcsIds = teamInfos.stream().map(a->a.getId()).collect(Collectors.toList());
                    fjpcsIdNos.addAll(childUnit.getSubPersonIdNos(fjpcsIds));
                }

                teamInfos = (List<TeamInfoVo>) teamInfo.get("bm2");
                bmIds = teamInfos.stream().map(a->a.getId()).collect(Collectors.toList());
                bmIdIdNos.addAll(childUnit.getSubPersonIdNos(bmIds));


                if(fjpcsIdNos.size()>0){
                    temp = teamBuildMapper.findThreeLeaderIdNum(fjpcsIdNos);
                    fjpcsIdNos.clear();
                    fjpcsIdNos.addAll(temp.stream().distinct().collect(Collectors.toList()));
                }
                if(bmIdIdNos.size()>0){
                    temp = teamBuildMapper.findThreeLeaderIdNum(bmIdIdNos);
                    bmIdIdNos.clear();
                    bmIdIdNos.addAll(temp.stream().distinct().collect(Collectors.toList()));
                }
            }
            if(ZZB_ID.equals(unitId)){
                if(fetchSuper){
                    superIdNos.clear();
                    superIdNos.addAll(personStereoService.getDeptHead(unitId).stream().map(a->a.getIdNo()).collect(Collectors.toList()));
                }

                if (fetchSub) {
                    bmIdIdNos.clear();
                    bmIdIdNos.addAll(childUnit.getSubPersonIdNos(zzbSubList));
                    temp = teamBuildMapper.findTwoLeaderIdNum(bmIdIdNos);
                    bmIdIdNos.clear();
                    bmIdIdNos.addAll(temp);
                }
            }

            temp.clear();
            temp.addAll(superIdNos);
            temp.addAll(fjpcsIdNos);
            temp.addAll(bmIdIdNos);
            idNos.addAll(temp);

        }else if("3".equals(deptLevel)){
            List<String> collect = tPersonDao.findAllBydeptIdIn(Arrays.asList(unitId)).stream().map(a -> a.getIdNo()).collect(Collectors.toList());
            if(zzbSubList.contains(unitId)){
                superIdNos.addAll(teamBuildMapper.findTwoLeaderIdNum(collect));
            }else{
                superIdNos.addAll(teamBuildMapper.findThreeLeaderIdNum(collect));
            }

            idNos.addAll(superIdNos.stream().distinct().collect(Collectors.toList()));

        }
        String category = query.getCategory();

        List<TPersonDo> personDos = tPersonDao.findAllByDelStatusAndIdNoIn("0", idNos);
        personDos.forEach((p->{
            switch (p.getPoliceJobGrade()) {
                case "0925":
                case "102":
                case "10":
                    p.setPoliceJobGrade("����ְ");
                    break;
                case "0830":
                case "0920":
                    p.setPoliceJobGrade("����ְ");
                    break;
                case "0730":
                case "0820":
                    p.setPoliceJobGrade("����ְ");
                    break;
                case "0720":
                    p.setPoliceJobGrade("����ְ");
                    break;
                case "0717":
                case "0718":
                    p.setPoliceJobGrade("����ְ���쵼");
                    break;
                case "0715":
                case "0716":
                    p.setPoliceJobGrade("����ְ");
                    break;
                case "0620":
                    p.setPoliceJobGrade("����ְ");
                    break;
                case "0520":
                    p.setPoliceJobGrade("����ְ");
                    break;
                default:
                    break;
            }
        }));
        ResultModel resultModel = new ResultModel();
        switch (category){
            case "duty":
                resultModel =  getDutyDetail(personDos,query);
                break;
            case "age":
                resultModel = getAgeDetail(personDos,query);
                break;
            case "sex":
                resultModel = getSexDetail(personDos,query);
                break;
            case "education":
                resultModel = getEducationDetail(personDos,query);
                break;
            case "nation":
                resultModel = getNationDetail(personDos,query);
                break;
            case "rule":
                resultModel = getRuleDetail(personDos,query);
                break;
            case "appointmentTime":
                resultModel = getAppointmentTimeDetail(personDos,query);
                break;
            case "entryPeriod":
                resultModel = getEntryPeriodDetail(personDos,query);
                break;
        }
        if(query.getIsDownload()){
            Map<String,Object> data = (Map<String,Object>)resultModel.getData();
            String[] heads = (String[]) data.get("heads");
            String excelTitle = data.get("excelTitle").toString();
            List<Map<String, String>> rows = (List<Map<String, String>>) data.get("rows");
            List<Object[]> datas = new ArrayList<>();
            int length = heads.length;
            for (Map<String, String> row : rows) {
                Object[] obj = new Object[length];
                for (int i = 0; i < heads.length; i++) {
                    obj[i] = row.get(heads[i]);
                }
                datas.add(obj);
            }

            teamBuildService.exportExcel(excelTitle,heads,datas);
            return null;
        }
        return resultModel;
    }

    private ResultModel getAgeDetail(List<TPersonDo> personDos, TeamLeaderDetailQuery query) {
        List<Map<String,String>> rows = new ArrayList<>();
        Stream<TPersonDo> stream = personDos.stream();
        if(query.getType()!=null){
            if(query.getType().endsWith("����")){
                query.setType("0");
            }else if(query.getType().endsWith("����")){
                query.setType("2");
            }else{
                query.setType("1");
            }
            switch (query.getType()){
                case "0":
                    stream = stream.filter(p->Integer.parseInt(p.getAge())<35);
                    break;
                case "1" :
                    stream = stream.filter(p->Integer.parseInt(p.getAge())>=35&&Integer.parseInt(p.getAge())<50);
                    break;
                case "2" :
                    stream = stream.filter(p->Integer.parseInt(p.getAge())>=50);
                    break;
            }
        }
        stream.sorted(Comparator.comparingInt((TPersonDo p)->CommonUnit.duties.indexOf(p.getPoliceJobGrade())).thenComparing((TPersonDo o) -> Integer.parseInt(o.getAge())).thenComparing((TPersonDo o) -> CommonUnit.deptList.indexOf(o.getDeptName()))).forEach(p->{
            Map<String, String> row = new HashMap<>();
            row.put("����",p.getPersonName());
            row.put("��λ",p.getDeptName());
            row.put("����ְ�񼶱�",p.getPoliceJobGrade());
            row.put("ְ��",p.getPost());
            row.put("����",p.getAge());
            rows.add(row);
        });
        Map<String,Object> result = new HashMap<>();
        result.put("heads",new String[]{"����","��λ","����ְ�񼶱�","ְ��","����"});
        result.put("rows",rows);
        result.put("excelTitle","����");
        return ResultModel.success(result);
    }
    private ResultModel getSexDetail(List<TPersonDo> personDos, TeamLeaderDetailQuery query) {
        List<Map<String,String>> rows = new ArrayList<>();
        Stream<TPersonDo> stream = personDos.stream();
        stream.map(p->{
            p.setSex("1".equals(p.getSex())?"��":"Ů");
            return p;
        }).filter(p->query.getType()==null?true:p.getSex().equals(query.getType())).sorted(Comparator.comparing((TPersonDo p)->CommonUnit.duties.indexOf(p.getPoliceJobGrade())).thenComparing(p->CommonUnit.deptList.indexOf(p.getDeptName()))).forEach(p->{
            Map<String, String> row = new HashMap<>();
            row.put("����",p.getPersonName());
            row.put("��λ",p.getDeptName());
            row.put("����ְ�񼶱�",p.getPoliceJobGrade());
            row.put("ְ��",p.getPost());
            row.put("�Ա�",p.getSex());
            rows.add(row);
        });
        Map<String,Object> result = new HashMap<>();
        result.put("heads",new String[]{"����","��λ","����ְ�񼶱�","ְ��","�Ա�"});
        result.put("rows",rows);
        result.put("excelTitle","�Ա�");
        return ResultModel.success(result);
    }

    private ResultModel getEducationDetail(List<TPersonDo> personDos, TeamLeaderDetailQuery query) {
        List<Map<String,String>> rows = new ArrayList<>();
        List<TEducationDo> tEducationDos = tEducationDao.getFirstByIdNo(personDos.stream().map(p -> p.getIdNo()).collect(Collectors.toList()), "0");
        Map<String, String> educationMap = tEducationDos.stream().collect(Collectors.toMap(t -> t.getIdNo(), t -> t.getEducationalTitle()));
        Stream<TPersonDo> stream = personDos.stream();
        stream.map(p->{
            Map<String, String> row = new HashMap<>();
            row.put("����",p.getPersonName());
            row.put("��λ",p.getDeptName());
            row.put("ְ��",p.getPost());
            String education = educationMap.get(p.getIdNo());
            if (StringUtils.isNotEmpty(education)) {
                if ("11001".equals(education)) {
                    education="��ʿ";
                } else if (education.charAt(0) == '1') {
                    education="˶ʿ";
                } else if (education.charAt(0) == '2') {
                    education="����";
                } else if (education.charAt(0) == '3') {
                    education="��ר";
                } else {
                    education="���м�����";
                }
            }
            row.put("ѧ��",education);
            row.put("����ְ�񼶱�",p.getPoliceJobGrade());
            row.put("ְ��",p.getPost());
            return row;
        }).filter(m->query.getType()==null?true: query.getType().equals(m.get("ѧ��"))).sorted(Comparator.comparing((Map<String, String> p)->CommonUnit.duties.indexOf(p.get("����ְ�񼶱�"))).thenComparing((Map<String, String> p)->CommonUnit.education.indexOf(p.get("ѧ��"))).thenComparing((Map<String, String> p)->CommonUnit.education.indexOf(p.get("��λ")))).forEach(m->rows.add(m));
        Map<String,Object> result = new HashMap<>();
        result.put("heads",new String[]{"����","��λ","����ְ�񼶱�","ְ��","ѧ��"});
        result.put("rows",rows);
        result.put("excelTitle","�Ļ��̶�");
        return ResultModel.success(result);
    }

    private ResultModel getNationDetail(List<TPersonDo> personDos, TeamLeaderDetailQuery query) {
        Map<String, String> nationMap = sgQueryCategoryDao.findByCvType("AE").stream().collect(Collectors.toMap(a -> a.getCvFlag(), a -> a.getCvName()));
        List<Map<String,String>> rows = new ArrayList<>();
        Stream<TPersonDo> stream = personDos.stream();
        stream.map(p->{p.setNation(nationMap.get(p.getNation()));return p;}).filter(p->query.getType()==null?true:p.getNation().equals(query.getType())).sorted(Comparator.comparing((TPersonDo p)->CommonUnit.duties.indexOf(p.getPoliceJobGrade())).thenComparing(p->CommonUnit.deptList.indexOf(p.getDeptName()))).forEach(p->{
            Map<String, String> row = new HashMap<>();
            row.put("����",p.getPersonName());
            row.put("��λ",p.getDeptName());
            row.put("����ְ�񼶱�",p.getPoliceJobGrade());
            row.put("ְ��",p.getPost());
            row.put("����",p.getNation());
            rows.add(row);
        });
        Map<String,Object> result = new HashMap<>();
        result.put("heads",new String[]{"����","��λ","����ְ�񼶱�","ְ��","����"});
        result.put("rows",rows);
        result.put("excelTitle","����");
        return ResultModel.success(result);
    }

    private ResultModel getEntryPeriodDetail(List<TPersonDo> personDos, TeamLeaderDetailQuery query) {
        List<PersonQo> personQos = personalAnalysisMapper.queryPeopleByJoinTime("","",personDos.stream().map(a->a.getIdNo()).collect(Collectors.toList()));
        personQos.forEach((p->{
            switch (p.getPoliceJobGrade()) {
                case "0925":
                case "102":
                case "10":
                    p.setPoliceJobGrade("����ְ");
                    break;
                case "0830":
                case "0920":
                    p.setPoliceJobGrade("����ְ");
                    break;
                case "0730":
                case "0820":
                    p.setPoliceJobGrade("����ְ");
                    break;
                case "0720":
                    p.setPoliceJobGrade("����ְ");
                    break;
                case "0717":
                case "0718":
                    p.setPoliceJobGrade("����ְ���쵼");
                    break;
                case "0715":
                case "0716":
                    p.setPoliceJobGrade("����ְ");
                    break;
                case "0620":
                    p.setPoliceJobGrade("����ְ");
                    break;
                case "0520":
                    p.setPoliceJobGrade("����ְ");
                    break;
                default:
                    break;
            }
        }));
        Stream<PersonQo> stream = personQos.stream();

        String type = query.getType();
        if(StringUtils.isNotBlank(type)){
            if(type.endsWith("����")){
                type = "6";
            }else if(type.endsWith("����")){
                type = "0";
            }else if(type.startsWith("5")){
                type = "1";
            }else if(type.startsWith("10")){
                type = "2";
            }else if(type.startsWith("15")){
                type = "3";
            }else if(type.startsWith("20")){
                type = "4";
            }else if(type.startsWith("25")){
                type = "5";
            }

            List<Pair<Integer,Integer>> ranges = new ArrayList<>();
            ranges.add(Pair.of(null, 5));
            ranges.add(Pair.of(5, 10));
            ranges.add(Pair.of(10, 15));
            ranges.add(Pair.of(15, 20));
            ranges.add(Pair.of(20, 25));
            ranges.add(Pair.of(25, 30));
            ranges.add(Pair.of(30, null));
            Pair<Integer, Integer> pair = ranges.get(Integer.parseInt(type));
            if(null!=pair.getLeft()){
                stream = stream.filter(a->Integer.parseInt(a.getTimeToWorkPsb())>pair.getLeft());
            }
            if(null!=pair.getRight()){
                stream = stream.filter(a->Integer.parseInt(a.getTimeToWorkPsb())<=pair.getRight());
            }
        }

        List<Map<String,String>> rows = new ArrayList<>();
        stream.sorted(Comparator.comparing((PersonQo p)->CommonUnit.duties.indexOf(p.getPoliceJobGrade())).thenComparing((PersonQo m)->Integer.parseInt(m.getTimeToWorkPsb()),Comparator.reverseOrder()).thenComparing((PersonQo m)->CommonUnit.deptList.indexOf(m.getDeptName()))).forEach(p->{
            Map<String, String> row = new HashMap<>();
            row.put("����",p.getPersonName());
            row.put("��λ",p.getDeptName());
            row.put("ְ��",p.getPost());
            row.put("����ְ�񼶱�",p.getPoliceJobGrade());
            row.put("�뾯����","0".equals(p.getTimeToWorkPsb())?"δ��һ��":p.getTimeToWorkPsb());
            rows.add(row);
        });

        Map<String,Object> result = new HashMap<>();
        result.put("heads",new String[]{"����","��λ","ְ��","����ְ�񼶱�","�뾯����"});
        result.put("rows",rows);
        result.put("excelTitle","�뾯����");
        return ResultModel.success(result);
    }

    private ResultModel getRuleDetail(List<TPersonDo> personDos, TeamLeaderDetailQuery query) {
        List<BreakRuleCaseVo> breakRuleCaseDetail = teamBuildMapper.findBreakRuleCaseDetail("%%", personDos.stream().map(a -> a.getIdNo()).collect(Collectors.toList()));

        Stream<BreakRuleCaseVo> stream = breakRuleCaseDetail.stream();
        if(StringUtils.isNotBlank(query.getType())){
            stream = stream.filter(a->a.getDate().startsWith(query.getType()));
        }

        List<Map<String,String>> rows = new ArrayList<>();
        stream.sorted(Comparator.comparing(BreakRuleCaseVo::getDate,Comparator.reverseOrder())).forEach(p->{
            Map<String, String> row = new HashMap<>();
            row.put("����",p.getPersonName());
            row.put("��λ",p.getDeptName());
            row.put("����",p.getPoliceNo());
            row.put("���־���ʱ��",p.getDate());
            row.put("��������",p.getDetail());
            rows.add(row);
        });

        Map<String,Object> result = new HashMap<>();
        result.put("heads",new String[]{"����","��λ","����","���־���ʱ��","��������"});
        result.put("rows",rows);
        result.put("excelTitle","Υ�Ͱ���");
        return ResultModel.success(result);
    }

    private ResultModel getAppointmentTimeDetail(List<TPersonDo> personDos, TeamLeaderDetailQuery query) {
        List<Map<String, Object>> maps = personalAnalysisMapper.queryAppointmentTime(personDos.stream().map(p->p.getIdNo()).collect(Collectors.toList()));

        Stream<Map<String, Object>> stream = maps.stream();
        stream = stream.map(m->{
            switch (String.valueOf(m.get("POLICE_JOB_GRADE"))) {
                case "0925":
                case "102":
                case "10":
                    m.put("POLICE_JOB_GRADE","����ְ");
                    break;
                case "0830":
                case "0920":
                    m.put("POLICE_JOB_GRADE","����ְ");
                    break;
                case "0730":
                case "0820":
                    m.put("POLICE_JOB_GRADE","����ְ");
                    break;
                case "0720":
                    m.put("POLICE_JOB_GRADE","����ְ");
                    break;
                case "0717":
                case "0718":
                    m.put("POLICE_JOB_GRADE","����ְ���쵼");
                    break;
                case "0715":
                case "0716":
                    m.put("POLICE_JOB_GRADE","����ְ");
                    break;
                case "0620":
                    m.put("POLICE_JOB_GRADE","����ְ");
                    break;
                case "0520":
                    m.put("POLICE_JOB_GRADE","����ְ");
                    break;
                default:
                    break;
            }
            return m;
        });
        if(!query.getSub()){
            stream = stream.filter(m->String.valueOf(m.get("POLICE_JOB_GRADE")).startsWith("��"));
        }else{
            stream = stream.filter(m->String.valueOf(m.get("POLICE_JOB_GRADE")).startsWith("��"));
        }
        if(StringUtils.isNotBlank(query.getType())){
            stream = stream.filter(m->query.getType().equals(m.get("POLICE_JOB_GRADE")));
        }
        if(StringUtils.isNotBlank(query.getExtraType())){
            Integer year;
            if(query.getType().startsWith("��")){
                year=3;
            }else{
                year=2;
            }
            if(query.getExtraType().endsWith("����")){
                stream = stream.filter(m->Integer.parseInt(String.valueOf(m.get("YEAR")))<=year);
            }else if(query.getExtraType().endsWith("����")){
                stream = stream.filter(m->Integer.parseInt(String.valueOf(m.get("YEAR")))>year);
            }

        }

        List<Map<String,Object>> rows = new ArrayList<>();
        stream.sorted(Comparator.comparing((Map<String,Object> m)->CommonUnit.duties.indexOf(String.valueOf(m.get("POLICE_JOB_GRADE")))).thenComparing((Map<String,Object> m)->Integer.parseInt(String.valueOf(m.get("YEAR"))),Comparator.reverseOrder()).thenComparing((Map<String,Object> m)->CommonUnit.deptList.indexOf(String.valueOf(m.get("DEPTNAME"))))).collect(Collectors.toList()).forEach(p->{
            Map<String, Object> row = new HashMap<>();
            row.put("����",p.get("PERSON_NAME"));
            row.put("��λ",p.get("DEPTNAME"));
            row.put("ְ��",p.get("POST"));
            row.put("����ְ�񼶱�",p.get("POLICE_JOB_GRADE"));
            row.put("��ְʱ��(��)","0".equals(String.valueOf(p.get("YEAR")))?"δ��һ��":p.get("YEAR"));
            rows.add(row);
        });
        Map<String,Object> result = new HashMap<>();
        result.put("heads",new String[]{"����","��λ","ְ��","����ְ�񼶱�","��ְʱ��(��)"});
        result.put("rows",rows);
        result.put("excelTitle","��ְʱ��");
        return ResultModel.success(result);
    }



    private ResultModel getDutyDetail(List<TPersonDo> personDos, TeamLeaderDetailQuery query) {
        List<Map<String,String>> rows = new ArrayList<>();
        personDos.stream().filter(p->query.getType()==null?true:p.getPoliceJobGrade().equals(query.getType())).sorted(Comparator.comparing((TPersonDo p)->CommonUnit.duties.indexOf(p.getPoliceJobGrade())).thenComparing((TPersonDo p)->CommonUnit.deptList.indexOf(p.getDeptName()))).forEach(p->{
            Map<String, String> row = new HashMap<>();
            row.put("����",p.getPersonName());
            row.put("��λ",p.getDeptName());
            row.put("ְ��",p.getPost());
            row.put("����ְ�񼶱�",p.getPoliceJobGrade());
            rows.add(row);
        });
        Map<String,Object> result = new HashMap<>();
        result.put("heads",new String[]{"����","��λ","ְ��","����ְ�񼶱�"});
        result.put("rows",rows);
        result.put("excelTitle","����ְ�񼶱�");
        return ResultModel.success(result);
    }
}
