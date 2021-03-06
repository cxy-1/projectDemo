package Util.Arithmetic.Excel;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * @program: analysis->ExportExcelUtil_1
 * @description:
 * @author: gaowh
 * @create: 2020-08-11 14:16
 **/
public class ExportExcelUtil_1 {
    public static void main(String[] args) {
        new ExportExcelUtil_1().a1();
    }

    private int a1() {
        List<Object[]> dataList = new ArrayList<>();
        Object[] objs;
        List<Map<String,Object>> resultList=new ArrayList<>();
        Map<String, Object> map1=new HashMap<>(16);
        map1.put("xm","王伟");
        map1.put("xh","100001");
        map1.put("dwbzmc","计科");
        map1.put("zymc","软工");
        map1.put("bjmc","一班");
        map1.put("stxfcs","22");
        map1.put("avgmonstxfcs","33");
        resultList.add(map1);
        String[] rowsName = new String[]{"学生姓名", "学生学号", "所属学院", "所属专业", "所属班级", "消费次数", "平均金额"};
        for (Map<String, Object> star : resultList) {//  这个循环的作用的 取出list集合里的map里的每一个数据 然后把符合要求的数据重新存储到里面
            objs = new Object[rowsName.length];
            objs[0] = star.get("xm");
            objs[1] = star.get("xh");
            objs[2] = star.get("dwbzmc");
            objs[3] = star.get("zymc");
            objs[4] = star.get("bjmc");
            objs[5] = star.get("stxfcs");
            objs[6] = star.get("avgmonstxfcs");
            dataList.add(objs);
        }
        try {
            exportExcel("老师信息", rowsName, dataList);
            return 0;
        } catch (Exception e) {
        }
        return 1;
    }
    /**
     * excel导出
     */
    public void exportExcel(String title, String[] rowsName, List<Object[]> list) {
        //给文件命名。随机命名
        String fileName = "Excel-学生信息-" + String.valueOf(System.currentTimeMillis()).substring(4, 13) + ".xls";
        OutputStream out = null;
        try {
            out = new FileOutputStream("f:\\"+fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //调用poi的工具类
        ExportExcelUtil_2 ex = new ExportExcelUtil_2(title, rowsName, list);
        try {
            ex.export(out);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Objects.requireNonNull(out).flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Objects.requireNonNull(out).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
