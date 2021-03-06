package Util.Arithmetic.a0.Hive;

import org.apache.hive.jdbc.HiveStatement;

import java.util.List;

/**
 * @author xyl
 *
 * hive ���
 * ���� redis ��չ
 */
public class HiveJdbcMonitor implements Runnable {

    private static int monitorInterval = 2000;

    private final HiveStatement hiveStatement;
    private String sqlId = "es_xxx";

    HiveJdbcMonitor(HiveStatement hiveStatement)
    {
        this.hiveStatement = hiveStatement;
    }

    public HiveStatement getHiveStatement() {
        return hiveStatement;
    }

    public String getSqlId() {
        return sqlId;
    }

    public void setSqlId(String sqlId) {
        this.sqlId = sqlId;
    }

    private void updateQueryLog()
    {
        try
        {
            List<String> queryLogs = hiveStatement.getQueryLog();
            String applicationId = "";
            for (String logString : queryLogs)
            {
                if(logString.contains("with App id")){

                    applicationId = logString.substring(logString.indexOf("App id") +7, logString.length()-1);
                    System.out.println("applicationId======"+sqlId+"======" + applicationId );
                }

                System.out.println("===hive==step===="+applicationId+"=>"+logString);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run()
    {
        try
        {
            while (hiveStatement.hasMoreLogs())
            {
                updateQueryLog();
                Thread.sleep(monitorInterval);
            }
        } catch (InterruptedException e)
        {
            e.getStackTrace();
        }
    }
}
