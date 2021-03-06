package ScheduledTask;

import com.sugon.jsbrgl.dto.THelpRecordsDTO;
import com.sugon.jsbrgl.enums.CodeEnums;
import com.sugon.jsbrgl.utils.Config;
import com.sugon.jsbrgl.utils.DateUtil;
import com.sugon.jsbrgl.utils.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * springboot定时执行
 *
 * @author zhuwz
 */
@Component
@Configurable
@EnableScheduling
@EnableAsync
public class ScheduledTask {
    @Resource
    private TTasksService tTasksService;
    @Resource
    private TMessagesService tMessagesService;
    @Resource
    private TUserService tUserService;
    @Resource
    private THelpService tHelpService;
    @Resource
    private TArchivesService tArchivesService;
    @Resource
    private TGrantRecordService tGrantRecordService;
    @Resource
    private THelpRecordsService tHelpRecordsService;


    private static final Logger logger = LoggerFactory.getLogger(ScheduledTask.class);

    /**
     * 每天晚上一点钟执行任务
     */
    @Async
    @Scheduled(cron = "0 0 1 * * ?")
    public void scheduleTask() {
        sendMsgForTimeOut();//扫描超时任务，进行信息发送提醒
    }

    /**
     * 每月1号6点执行定时任务
     */
    @Async
    @Scheduled(cron = "0 0 6 1 * ?")
    public void updateGrantRecord() {
        //定时更新补助金模块
        getGrantRecord();
        //定时判断帮扶模块能否删除
        checkIsDeleteHelpRecord();
    }

    /**
     * 定时删除下载文件夹
     */
    @Async
    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteFile() {
        Calendar cal=Calendar.getInstance();
        cal.add(Calendar.DATE,-1);
        Date d=cal.getTime();
        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
        String path = Config.getInstance().getProperty("zip.path")+sf.format(d).toString();
        FileUtil.deleteDirectory(path);
    }


    /**
     * @description 更新补助金操作任务
     */
    public void getGrantRecord() {
        List<TArchives> tArchivesList = tArchivesService.selectAll();
        /**
         * 获取上个月的时间区间
         */
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        Date lastDate = cal.getTime();
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date firstDate = cal.getTime();
        for (TArchives tArchives : tArchivesList) {
            List<THelpRecords> tHelpRecordsList = tHelpRecordsService.selectBytArchivesIdForDate(tArchives.getId(), firstDate, lastDate);
            //当进入帮扶但是没填任何信息时，统计未审核情况
            if(tArchives.getStatus()==6&&tHelpRecordsList.size()==0){
                TGrantsRecord tGrantsRecord = new TGrantsRecord();
                tGrantsRecord.setArchivesId(tArchives.getId());
                tGrantsRecord.setGridCheck(2);
                tGrantsRecord.setPoliceCheck(2);
                tGrantsRecord.setDoctorCheck(2);
                //设置档案审核时间为审核当月最后一天
                tGrantsRecord.setCheckTime(lastDate);
                tGrantRecordService.insert(tGrantsRecord);
            }else{
                if(tHelpRecordsList.size()>0){
                    TGrantsRecord tGrantsRecord = new TGrantsRecord();
                    tGrantsRecord.setArchivesId(tArchives.getId());
                    for (THelpRecords tHelpRecords : tHelpRecordsList) {
                        if (tHelpRecords.getType() == 0) {
                            if (null == tGrantsRecord.getGridCheck()) {
                                tGrantsRecord.setGridCheck(tHelpRecords.getIsubsidy());
                                check(tGrantsRecord, tHelpRecords);
                            } else if (tGrantsRecord.getGridCheck() == 0) {
                                tGrantsRecord.setGridCheck(tHelpRecords.getIsubsidy());
                                check(tGrantsRecord, tHelpRecords);
                            }
                        } else if (tHelpRecords.getType() == 1) {
                            if (null == tGrantsRecord.getPoliceCheck()) {
                                tGrantsRecord.setPoliceCheck(tHelpRecords.getIsubsidy());
                                check(tGrantsRecord, tHelpRecords);
                            } else if (tGrantsRecord.getPoliceCheck() == 0) {
                                tGrantsRecord.setPoliceCheck(tHelpRecords.getIsubsidy());
                                check(tGrantsRecord, tHelpRecords);
                            }
                        } else if (tHelpRecords.getType() == 2) {
                            if (null == tGrantsRecord.getDoctorCheck()) {
                                tGrantsRecord.setDoctorCheck(tHelpRecords.getIsubsidy());
                                check(tGrantsRecord, tHelpRecords);
                            } else if (tGrantsRecord.getDoctorCheck() == 0) {
                                tGrantsRecord.setDoctorCheck(tHelpRecords.getIsubsidy());
                                check(tGrantsRecord, tHelpRecords);
                            }
                        }
                    }
                    if (null == tGrantsRecord.getGridCheck()) {
                        tGrantsRecord.setGridCheck(2);
                    }
                    if (null == tGrantsRecord.getPoliceCheck()) {
                        tGrantsRecord.setPoliceCheck(2);
                    }
                    if (null == tGrantsRecord.getDoctorCheck()) {
                        tGrantsRecord.setDoctorCheck(2);
                    }
                    if(2 ==tGrantsRecord.getGridCheck()&&2 == tGrantsRecord.getPoliceCheck()&&2 == tGrantsRecord.getDoctorCheck()){
                        //设置档案审核时间为审核当月最后一天
                        tGrantsRecord.setCheckTime(lastDate);
                    }
                    tGrantRecordService.insert(tGrantsRecord);
                }
            }
        }
    }

    /**
     * @param tGrantsRecord
     * @param tHelpRecords
     * @description:更新审核时间
     */
    public void check(TGrantsRecord tGrantsRecord, THelpRecords tHelpRecords) {
        if (null != tGrantsRecord.getCheckTime() && tGrantsRecord.getCheckTime().compareTo(tHelpRecords.getHelpDate()) == -1) {
            tGrantsRecord.setCheckTime(tHelpRecords.getHelpDate());
        } else if (null == tGrantsRecord.getCheckTime()) {
            tGrantsRecord.setCheckTime(tHelpRecords.getHelpDate());
        }
    }

    /**
     * 针对所有的帮扶数据，进行时间比对，历史（上个月以前的）数据全部变更成不可删除
     */
    private void checkIsDeleteHelpRecord() {
        THelpRecordsDTO tHelpRecordsDTO = new THelpRecordsDTO();
        tHelpRecordsDTO.setIsdelete(0);
        Date d2 = new Date();
        List<THelpRecords> tHelpRecordsList = tHelpRecordsService.selectAllByDto(tHelpRecordsDTO);
        if (null != tHelpRecordsList && tHelpRecordsList.size() > 0) {
            for (THelpRecords tHelpRecords : tHelpRecordsList) {
                Date d1 = tHelpRecords.getHelpDate();
                boolean flag = DateUtil.getComperDate2(d1, d2) == 0 ? true : false;
                if (flag) {
                    //变成不可删除
                    tHelpRecords.setIsdelete(1);
                }
                tHelpRecordsService.update(tHelpRecords);
            }
        }
    }

    /**
     * 根据任务信息 发送超时消息
     */
    private void sendMsgForTimeOut() {
        List<TTasks> tTasksList = tTasksService.selectByStatus();
        Integer[] userarr = null;
        for (TTasks tTasks : tTasksList) {
            //当前时间
            Date date1 = new Date();
            //目标执行时间
            Date date2 = timeReckon(tTasks.getType(), tTasks.getCreateDate());
            if(tTasks.getType()== CodeEnums.TASK_VISIT_OVERTIME.getEndCode()){
                date2 = timeReckon(tTasks.getType(), tTasks.getUpdateTime());
            }
            //date1小于date2返回-1，date1大于date2返回1，相等返回0
            int compareTo = date1.compareTo(date2);

            //帮扶超时，只有每个月1号发出通知
            if (tTasks.getType() == 6) {
                if (DateUtil.today_date() == 1) {
                    compareTo = 1;
                } else {
                    compareTo = -1;
                }
            }
            if (compareTo >= 0 && tTasks.getOperNum() < tTasks.getMaxNum()) {
                userarr = new Integer[]{tTasks.getUserId()};
                //发送超时通知
                List<TUsers> usersList = tUserService.selectByTaskId(tTasks.getId());
                tMessagesService.insertByUser(userarr, date1, usersList.get(0), usersList.get(0).getArchiveId(), tTasks.getType());
                //更新Task
                tTasks.setOperNum(tTasks.getOperNum() + 1);
                tTasks.setUpdateTime(date1);
                tTasksService.updateByPrimaryKey(tTasks);
            }
        }
    }
    public Date timeReckon(Integer type, Date dateTime) {
        switch (type) {
            //转发超时
            case 4:
                return DateUtil.getFetureDate(dateTime, 7);
            //退回超时
            case 5:
                return DateUtil.getFetureDate(dateTime, 7);
            //帮扶超时
            case 6:
                return DateUtil.getFetureDateForMon(dateTime, 1);
            //走访超时
            case 7:
                return DateUtil.getFetureDate(dateTime, 10);
            default:
                return dateTime;
        }
    }


}
