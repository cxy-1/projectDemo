package Util.Arithmetic.sql.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author zhuwz
 * @date 2019/12/11 9:21
 */

public class JDBCUtil_Jsb1 {
    /**
     * 注册并获取数据库连接
     * @return
     * @throws Exception
     */
    public static Connection getConnection() throws Exception {
        //1.注册驱动
        Class.forName("com.mysql.jdbc.Driver");
        //2.声明数据库连接信息，获取数据库连接
        String url = "jdbc:mysql://10.10.10.23:3309/jsbrgl";
        String username = "root";
        String password = "root123";
        Connection cn = DriverManager.getConnection(url, username, password);
        return cn;
    }

    /**
     * 释放资源
     * @param cn
     * @param st
     * @param rs
     * @throws Exception
     */
    public static void relase(Connection cn, Statement st, ResultSet rs) throws Exception {
        if (rs != null) {
            rs.close();
        }
        if (st != null) {
            st.close();
        }
        if (cn != null) {
            cn.close();
        }
    }

//    public static void main(String[] args) throws Exception {
//        JDBCUtil.ceshi();
//    }
//
//    public static  void ceshi() throws Exception {
//        StringBuffer resultBuffer = new StringBuffer();
//        String[] strArr = new String[]{"18168788297",
//                "13291036889",
//                "13337937616",
//                "13357950100",
//                "13407530702",
//                "13685100769",
//                "13775919727",
//                "13813282108",
//                "13813291101",
//                "13813476532",
//                "13815348052",
//                "13852043546",
//                "13914886387",
//                "13952196932",
//                "15050841350",
//                "15052001674",
//                "15062126041",
//                "15162221989",
//                "15252081944",
//                "15852047882",
//                "15852180135",
//                "15852309107",
//                "15852323976",
//                "15852385715",
//                "15852498601",
//                "15862184646",
//                "15862201597",
//                "15895200275",
//        };
//
//
//        Connection cn = JDBCUtil.getConnection();
//        //2.创建执行SQL语句对象
//        Statement st = cn.createStatement();
//        ResultSet rs = null;
//        //3.书写SQL语句
//        for (String account : strArr) {
//            StringBuffer stringBuffer = new StringBuffer();
//            stringBuffer.append(" select * from t_users where account in ( ");
//            stringBuffer.append(account);
//            stringBuffer.append(" ) ");
//            rs = st.executeQuery(stringBuffer.toString());
//
//            int i=0;
//            int tmp =0 ;
//            //5.处理结果集
//            while (rs.next()) {
//                if(i==0){
//                    tmp = rs.getInt("id");
//                }else{
//                    resultBuffer.append(" DELETE FROM `t_users` WHERE id = ");
//                    resultBuffer.append(rs.getInt("id"));
//                    resultBuffer.append(";");
//                    resultBuffer.append(" UPDATE t_user_addr_rel SET user_id = ");
//                    resultBuffer.append(tmp +" where id = "+rs.getInt("id"));
//                    resultBuffer.append(";");
//
//                }
//                i++;
////                System.out.println(rs.getInt("uid") + "  " + rs.getString("username"));
//            }
//        }
//
//        System.out.println(resultBuffer.toString());
//        //6.释放资源
//        JDBCUtil.relase(cn, st, rs);
//
//    }


}
