package Util.Arithmetic.sql;

import com.sun.deploy.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class SqlUtil {
    /**
     * 拼接sql，解决sql语句in中数据超过1000的问题
     *
     * @param column
     * @param values
     * @return
     */

    public static String convertValues2Sql(String column, List<String> values) {
        StringBuilder sqlBuilder = new StringBuilder(" AND (1=0 ");
        int remains = values.size() % 1000;
        int quotient = values.size() / 1000;
        for (int i = 0; i < quotient; i++) {
            String join = StringUtils.join(values.subList(i * 1000, (i + 1) * 1000), "','");
            sqlBuilder.append("  OR " + column + " IN ('" + join + "') ");
        }
        sqlBuilder.append("  OR " + column + " IN ('" + StringUtils.join(values.subList(quotient * 1000, quotient * 1000 + remains), "','") + "') ");
        sqlBuilder.append(")");
        return sqlBuilder.toString();
    }

    /**
     * 拼接sql，解决sql语句in中数据超过1000的问题
     * @param column
     * @param values
     * @return
     */
    public static String convertValues2OrSql(String column, List<String> values,Boolean equal) {
        if(values==null||values.size()==0){
            return " 1=0 ";
        }
        StringBuilder sqlBuilder = new StringBuilder("(");
        for (String value : values) {
            if(equal){
                sqlBuilder.append(" "+column+"='"+value+"' OR");
            }else{
                sqlBuilder.append(" "+column+"!='"+value+"' AND");
            }
        }
        if(equal){
            sqlBuilder.delete(sqlBuilder.lastIndexOf("OR"),sqlBuilder.length());
        }else{
            sqlBuilder.delete(sqlBuilder.lastIndexOf("AND"),sqlBuilder.length());
        }
        sqlBuilder.append(")");
        return sqlBuilder.toString();
    }
    /**
     * list分页
     */
    public static List splitList(List list, int pageNum, int pageSize) {
        int sum = list.size(); //记录总数
        int pageNo = (pageNum - 1) * pageSize;   //每页的起始索引
        if (pageNo > sum) {
            return new ArrayList();
        }
        if (pageNo + pageSize > sum) {
            return list.subList(pageNo, sum);
        } else {
            return list.subList(pageNo, pageNo + pageSize);
        }
    }
}
