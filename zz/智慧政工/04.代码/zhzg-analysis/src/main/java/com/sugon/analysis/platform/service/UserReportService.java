package com.sugon.analysis.platform.service;

import com.sugon.analysis.domain.data.object.oracle.TPersonDo;
import com.sugon.analysis.domain.data.object.sugon.oracle.UserLog;
import com.sugon.analysis.enums.result.ResultModel;
import com.sugon.analysis.platform.dto.UserReportQuery;
import com.sugon.analysis.repository.oracle.TPersonDao;
import com.sugon.analysis.repository.sugon.oracle.UserLogDao;
import com.sugon.analysis.util.DateUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.sugon.analysis.util.CommonUnit.convertValues2OrSql;

/**
 * @description: 用户报告
 * @author: liuwei
 * @create: 2020-06-04 09:56
 **/
@Service
public class UserReportService {

    @Value("${spring.profiles.active}")
    private String active;

    @Resource(name = "tertiaryJdbcTemplate")
    private JdbcTemplate tertiaryJdbcTemplate;

    private List<String> flagNames = Arrays.asList("队伍可视化","队伍综合评估","民警精准画像");

    private static  final int MAX_LENGTH=1000;

    @Resource
    private UserLogDao userLogDao;

    @Resource
    private TPersonDao tPersonDao;


    public ResultModel summary(UserReportQuery userReportQuery) {
        Map<String,Object> result= new HashMap<>();
        List<Map<String,Object>> summary= new ArrayList<>();

        List<UserLog> all = userLogDao.findAll(new Specification<UserLog>() {
            @Override
            public Predicate toPredicate(Root<UserLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<Predicate>();
                List<Predicate> listOr = new ArrayList<Predicate>();
                Predicate[] predicateOr = null;
                List<String> deptIds = userReportQuery.getDeptIds();
                if (CollectionUtils.isNotEmpty(deptIds)) {
                    Path<String> path = root.get("deptId");
                    if (deptIds.size() > MAX_LENGTH) {
                        List<List<String>> lists = segmentationList(deptIds, MAX_LENGTH - 1);
                        for (List<String> list : lists) {
                            CriteriaBuilder.In<String> in = cb.in(path);
                            for (int i = 0; i < list.size(); i++) {
                                in.value(list.get(i));
                            }
                            listOr.add(in);
                        }
                        predicateOr = new Predicate[listOr.size()];
                    } else {
                        CriteriaBuilder.In<String> in = cb.in(path);
                        for (int i = 0; i < deptIds.size(); i++) {
                            in.value(deptIds.get(i));
                        }
                        predicates.add(in);
                    }
                }
                if (userReportQuery.getDateRange() != null && userReportQuery.getDateRange().length == 2) {
                    Timestamp endDate = null;
                    Timestamp startDate = null;
                    try {
                        DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        startDate = new Timestamp(dateTimeFormat.parse(userReportQuery.getDateRange()[0]).getTime());
                        endDate = new Timestamp(dateTimeFormat.parse(userReportQuery.getDateRange()[1]).getTime());
                        predicates.add(cb.greaterThanOrEqualTo(root.get("createTime"), startDate));
                        predicates.add(cb.lessThanOrEqualTo(root.get("createTime"), endDate));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                predicates.add(cb.equal(root.get("active"), active));
                Predicate[] predicateAnd = new Predicate[predicates.size()];
                if (predicateOr != null) {
                    return query.where(cb.and(predicates.toArray(predicateAnd)), cb.or(listOr.toArray(predicateOr))).getRestriction();
                } else {
                    return query.where(cb.and(predicates.toArray(predicateAnd))).getRestriction();
                }
            }
        });

        Map<String, List<UserLog>> collect = all.parallelStream().collect(Collectors.groupingBy(u -> u.getFlagName(), Collectors.toList()));
        Map<String, Object> summaryByDay = new HashMap<>();
        List<String> days = DateUtil.getDays(userReportQuery.getDateRange()[0].substring(0, 10), userReportQuery.getDateRange()[1].substring(0, 10));
        summaryByDay.put("days",days);
        List<Map<String,List>> countByDates= new ArrayList<> ();
        for (String flagName : flagNames) {
            List<UserLog> logs = collect.get(flagName);
            Map<String, Object> map = new HashMap<>();
            if(logs !=null){
                map.put("total",logs.size());
                Map<String, List<UserLog>> listMap = logs.parallelStream().collect(Collectors.groupingBy(u -> u.getOrgSimpleName()+(u.getOfficeSimpleName()==null?"":u.getOfficeSimpleName()), Collectors.toList()));
                List<Map<String,Object>> countMaps = new ArrayList<>();
                for (Map.Entry<String, List<UserLog>> entry : listMap.entrySet()) {

                    Map<String, Object> countMap = new HashMap<>();
                    countMap.put("name",entry.getKey());
                    countMap.put("count",entry.getValue().parallelStream().map(a->a.getIdNo()).distinct().count());
                    countMap.put("personTime",entry.getValue().size());
                    countMaps.add(countMap);
                }
                countMaps.sort((o1, o2) -> Integer.parseInt(String.valueOf(o2.get("personTime"))) - Integer.parseInt(String.valueOf(o1.get("personTime"))));
                if(countMaps.size()>3){
                    countMaps = countMaps.subList(0,3);
                }
                map.put("count",countMaps);


                Map<String, Long> countByDay = logs.parallelStream().collect(Collectors.groupingBy(u -> new SimpleDateFormat("yyyy-MM-dd").format(u.getCreateTime()), Collectors.counting()));
                List<Long> dayCountArray = new ArrayList<>();
                for (String day : days) {
                    dayCountArray.add(countByDay.get(day)==null?0L:countByDay.get(day));
                }
                Map<String, List> countByDate = new HashMap<>();
                countByDate.put(flagName,dayCountArray);
                countByDates.add(countByDate);
            }else{
                map.put("total",0);
                map.put("count",new ArrayList<> ());
            }
            summary.add(map);
        }
        result.put("summary",summary);
        summaryByDay.put("countByDates",countByDates);
        result.put("summaryByDay",summaryByDay);


        return ResultModel.success(result);
    }

    public static List<List<String>> segmentationList(List<String> targe, int size) {
        List<List<String>> listArr = new ArrayList<List<String>>();
        // 获取被拆分的数组个数
        int arrSize = targe.size() % size == 0 ? targe.size() / size : targe
                .size() / size + 1;
        for (int i = 0; i < arrSize; i++) {
            List<String> sub = new ArrayList<String>();
            // 把指定索引数据放入到list中
            for (int j = i * size; j <= size * (i + 1) - 1; j++) {
                if (j <= targe.size() - 1) {
                    sub.add(targe.get(j));
                }
            }
            listArr.add(sub);
        }
        return listArr;
    }

    public ResultModel summaryByDept(UserReportQuery userReportQuery) {
        Map<String,Object> result= new HashMap<>();
        String sql = "SELECT ORG_SIMPLE_NAME||OFFICE_SIMPLE_NAME \"name\",COUNT(*) \"value\" FROM USER_LOG WHERE 1=1 AND ACTIVE='"+active+"' ";
        if(userReportQuery.getDeptIds()!=null && !userReportQuery.getDeptIds().isEmpty()){
            sql += " AND "+convertValues2OrSql("DEPT_ID",userReportQuery.getDeptIds(),true);
        }
        if(userReportQuery.getDateRange()!=null && userReportQuery.getDateRange().length==2){
            sql += " AND CREATE_TIME BETWEEN TO_DATE('"+userReportQuery.getDateRange()[0]+"','YYYY-MM-DD HH24:MI:SS') AND TO_DATE('"+userReportQuery.getDateRange()[1]+"','YYYY-MM-DD HH24:MI:SS')";
        }
        if(StringUtils.isNotBlank(userReportQuery.getFlagName())){
            sql += "  AND FLAG_NAME='"+userReportQuery.getFlagName()+"' ";
        }
        sql += " GROUP BY ORG_SIMPLE_NAME,OFFICE_SIMPLE_NAME ORDER BY \"value\" DESC";

        result.put("total",tertiaryJdbcTemplate.queryForMap("SELECT COUNT(*) COUNT FROM ("+sql+")").get("COUNT"));


        List<Map<String, Object>> maps = tertiaryJdbcTemplate.queryForList("SELECT * FROM (SELECT T.*,ROWNUM RN FROM ("+sql+") T WHERE ROWNUM<="+userReportQuery.getEnd()+") WHERE RN>="+userReportQuery.getStart());
        result.put("rows",maps);
        return ResultModel.success(result);
    }

    public ResultModel summaryByPeople(UserReportQuery userReportQuery) {
        Map<String,Object> result= new HashMap<>();
        String sql = "SELECT ID_NO,POLICE_NO,PERSON_NAME,ORG_SIMPLE_NAME || OFFICE_SIMPLE_NAME DEPT,COUNT(*) CNT FROM USER_LOG WHERE 1=1 AND ACTIVE='"+active+"' ";
        if(userReportQuery.getDeptIds()!=null && !userReportQuery.getDeptIds().isEmpty()){
            sql += " AND "+convertValues2OrSql("DEPT_ID",userReportQuery.getDeptIds(),true);
        }
        if(userReportQuery.getDateRange()!=null && userReportQuery.getDateRange().length==2){
            sql += " AND CREATE_TIME BETWEEN TO_DATE('"+userReportQuery.getDateRange()[0]+"','YYYY-MM-DD HH24:MI:SS') AND TO_DATE('"+userReportQuery.getDateRange()[1]+"','YYYY-MM-DD HH24:MI:SS')";
        }
        if(StringUtils.isNotBlank(userReportQuery.getFlagName())){
            sql += "  AND FLAG_NAME='"+userReportQuery.getFlagName()+"' ";
        }
        sql += " GROUP BY ID_NO,POLICE_NO,PERSON_NAME,ORG_SIMPLE_NAME,OFFICE_SIMPLE_NAME ORDER BY CNT DESC";

        result.put("total",tertiaryJdbcTemplate.queryForMap("SELECT COUNT(*) COUNT FROM ("+sql+")").get("COUNT"));

        List<Map<String, Object>> maps = tertiaryJdbcTemplate.queryForList("SELECT * FROM (SELECT T.*,ROWNUM RN FROM ("+sql+") T WHERE ROWNUM<="+userReportQuery.getEnd()+") WHERE RN>="+userReportQuery.getStart());
        List<String> idNos = maps.stream().map(a -> String.valueOf(a.get("ID_NO"))).collect(Collectors.toList());
        List<TPersonDo> allByDelStatusAndIdNoIn = tPersonDao.findAllByDelStatusAndIdNoIn("0", idNos);
        Map<String, String> postMap = allByDelStatusAndIdNoIn.stream().collect(Collectors.toMap(a -> a.getIdNo(), a -> a.getPost()));
        for (Map<String, Object> map : maps) {
            map.put("POST",postMap.get(map.get("ID_NO")));
        }

        result.put("rows",maps);
        return ResultModel.success(result);
    }

    public ResultModel export(UserReportQuery userReportQuery, HttpServletResponse response) {
        //给文件命名。随机命名
        String fileName = "Excel-" + String.valueOf(System.currentTimeMillis()).substring(4, 13) + ".xls";
        //告诉浏览器数据格式，将头和数据传到前台
        String headStr = "attachment; filename=\"" + fileName + "\"";
        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition", headStr);
        OutputStream out = null;
        try {
            out = response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] rowName = new String[]{"单位","姓名","警号","职务","访问时间"};
        HSSFWorkbook workbook = new HSSFWorkbook();
        for (String flagName : flagNames) {
            HSSFSheet sheet = workbook.createSheet(flagName);
            List<UserLog> all = userLogDao.findAll(new Specification<UserLog>() {
                @Override
                public Predicate toPredicate(Root<UserLog> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                    List<Predicate> predicates = new ArrayList<Predicate>();
                    List<Predicate> listOr = new ArrayList<Predicate>();
                    Predicate[] predicateOr = null;
                    List<String> deptIds = userReportQuery.getDeptIds();
                    if (CollectionUtils.isNotEmpty(deptIds)) {
                        Path<String> path = root.get("deptId");
                        if (deptIds.size() > MAX_LENGTH) {
                            List<List<String>> lists = segmentationList(deptIds, MAX_LENGTH - 1);
                            for (List<String> list : lists) {
                                CriteriaBuilder.In<String> in = cb.in(path);
                                for (int i = 0; i < list.size(); i++) {
                                    in.value(list.get(i));
                                }
                                listOr.add(in);
                            }
                            predicateOr = new Predicate[listOr.size()];
                        } else {
                            CriteriaBuilder.In<String> in = cb.in(path);
                            for (int i = 0; i < deptIds.size(); i++) {
                                in.value(deptIds.get(i));
                            }
                            predicates.add(in);
                        }
                    }
                    if (userReportQuery.getDateRange() != null && userReportQuery.getDateRange().length == 2) {
                        Timestamp endDate = null;
                        Timestamp startDate = null;
                        try {
                            DateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            startDate = new Timestamp(dateTimeFormat.parse(userReportQuery.getDateRange()[0]).getTime());
                            endDate = new Timestamp(dateTimeFormat.parse(userReportQuery.getDateRange()[1]).getTime());
                            predicates.add(cb.greaterThanOrEqualTo(root.get("createTime"), startDate));
                            predicates.add(cb.lessThanOrEqualTo(root.get("createTime"), endDate));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    predicates.add(cb.equal(root.get("active"), active));
                    predicates.add(cb.equal(root.get("flagName"), flagName));
                    Predicate[] predicateAnd = new Predicate[predicates.size()];
                    if (predicateOr != null) {
                        return query.where(cb.and(predicates.toArray(predicateAnd)), cb.or(listOr.toArray(predicateOr))).getRestriction();
                    } else {
                        return query.where(cb.and(predicates.toArray(predicateAnd))).getRestriction();
                    }
                }
            },new Sort(Sort.Direction.DESC,"createTime"));

            List<String> idNos = all.stream().map(a -> String.valueOf(a.getIdNo())).collect(Collectors.toList());
            List<TPersonDo> allByDelStatusAndIdNoIn = tPersonDao.findAllByDelStatusAndIdNoIn("0", idNos);
            Map<String, String> postMap = allByDelStatusAndIdNoIn.stream().collect(Collectors.toMap(a -> a.getIdNo(), a -> a.getPost()));

            List<Object[]> dataList = new ArrayList<>();
            for (UserLog userLog : all) {
                Object[] arr = new Object[rowName.length];
                arr[0] = userLog.getDeptName();
                arr[1] = userLog.getPersonName();
                arr[2] = userLog.getPoliceNo();
                arr[3] = postMap.get(userLog.getIdNo());
                arr[4] = userLog.getCreateTime();

                dataList.add(arr);
            }

            // 产生表格标题行
            HSSFRow rowm = sheet.createRow(0);
            HSSFCell cellTitle = rowm.createCell(0);


            //sheet样式定义【】
            HSSFCellStyle columnTopStyle = this.getColumnTopStyle(workbook);
            HSSFCellStyle style = this.getStyle(workbook);
            sheet.addMergedRegion(new CellRangeAddress(0, 1, 0, (rowName.length - 1)));
            cellTitle.setCellStyle(columnTopStyle);
            cellTitle.setCellValue(flagName);

            // 定义所需列数
            int columnNum = rowName.length;
            HSSFRow rowRowName = sheet.createRow(2);

            // 将列头设置到sheet的单元格中
            for (int n = 0; n < columnNum; n++) {
                HSSFCell cellRowName = rowRowName.createCell(n);
                cellRowName.setCellType(CellType.STRING);
                HSSFRichTextString text = new HSSFRichTextString(rowName[n]);
                cellRowName.setCellValue(text);
                cellRowName.setCellStyle(columnTopStyle);

            }
            // 将查询到的数据设置到sheet对应的单元格中
            for (int i = 0; i < dataList.size(); i++) {
                Object[] obj = dataList.get(i);// 遍历每个对象
                HSSFRow row = sheet.createRow(i + 3);// 创建所需的行数

                for (int j = 0; j < obj.length; j++) {
                    HSSFCell cell = null;
                    cell = row.createCell(j, CellType.STRING);
                    if (!"".equals(obj[j]) && obj[j] != null) {
                        cell.setCellValue(obj[j].toString());
                    }else {
                        cell.setCellValue("");
                    }
                    cell.setCellStyle(style);

                }

            }

// 必须在单元格设值以后进行
            // 设置为根据内容自动调整列宽
            for (int k = 0; k < columnNum; k++) {
                sheet.autoSizeColumn(k);
            }
            // 处理中文不能自动调整列宽的问题
            this.setSizeColumn(sheet, columnNum);



        }
        try {
            workbook.write(out);

        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }
    /**
     * 列头单元格样式
     */
    public HSSFCellStyle getColumnTopStyle(HSSFWorkbook workbook) {
        // 设置字体
        HSSFFont font = workbook.createFont();

        // 设置字体大小
        font.setFontHeightInPoints((short) 11);
        // 字体加粗
        font.setBold(true);
        // 设置字体名字
        font.setFontName("Courier New");
        // 设置样式
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置低边框
        style.setBorderBottom(BorderStyle.THIN);
        // 设置低边框颜色
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        // 设置右边框
        style.setBorderRight(BorderStyle.THIN);
        // 设置顶边框
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        // 设置顶边框颜色
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        // 在样式中应用设置的字体
        style.setFont(font);
        // 设置自动换行
        style.setWrapText(false);
        // 设置水平对齐的样式为居中对齐；
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;

    }

    public HSSFCellStyle getStyle(HSSFWorkbook workbook) {
        // 设置字体
        HSSFFont font = workbook.createFont();
        // 设置字体大小
        font.setFontHeightInPoints((short) 10);
        // 字体加粗
        font.setBold(true);
        // 设置字体名字
        font.setFontName("Courier New");
        // 设置样式;
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置底边框;
        style.setBorderBottom(BorderStyle.THIN);
        // 设置底边框颜色;
        style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
        // 设置左边框;
        style.setBorderLeft(BorderStyle.THIN);
        // 设置左边框颜色;
        style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
        // 设置右边框;
        style.setBorderRight(BorderStyle.THIN);
        // 设置右边框颜色;
        style.setRightBorderColor(IndexedColors.BLACK.getIndex());
        // 设置顶边框;
        style.setBorderTop(BorderStyle.THIN);
        // 设置顶边框颜色;
        style.setTopBorderColor(IndexedColors.BLACK.getIndex());
        // 在样式用应用设置的字体;
        style.setFont(font);
        // 设置自动换行;
        style.setWrapText(false);
        // 设置水平对齐的样式为居中对齐;
        style.setAlignment(HorizontalAlignment.CENTER);
        // 设置垂直对齐的样式为居中对齐;
        style.setVerticalAlignment(VerticalAlignment.CENTER);
        return style;
    }
    // 自适应宽度(中文支持)
    private void setSizeColumn(HSSFSheet sheet, int size) {
        for (int columnNum = 0; columnNum < size; columnNum++) {
            int columnWidth = sheet.getColumnWidth(columnNum) / 256;
            for (int rowNum = 2; rowNum <= sheet.getLastRowNum(); rowNum++) {
                HSSFRow currentRow;
                //当前行未被使用过
                if (sheet.getRow(rowNum) == null) {
                    currentRow = sheet.createRow(rowNum);
                } else {
                    currentRow = sheet.getRow(rowNum);
                }

                if (currentRow.getCell(columnNum) != null) {
                    HSSFCell currentCell = currentRow.getCell(columnNum);
                    if (currentCell.getCellType() == CellType.STRING) {
                        int length = currentCell.getStringCellValue().getBytes(StandardCharsets.UTF_8).length;
                        if (columnWidth < length) {
                            columnWidth = length;
                        }
                    }
                }
            }
            sheet.setColumnWidth(columnNum, columnWidth * 10 * 256 / 12 );
        }
    }
}
