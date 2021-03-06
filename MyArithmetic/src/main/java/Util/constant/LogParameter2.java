package com.sugon.dc.utils;

import java.util.HashMap;
import java.util.Map;

public class LogParameter2 {

	public static Map<String,String> logParameter= new HashMap<String,String>();

	public static String rmi_package = "com.sugon.dc.rmi.service.";

	public static String rmi_method_name = ".execute" ;

	public static String log_type = "dc";

	public static String log_level_low = "low";

	public static String log_level_middle = "middle";

	public static String log_level_high = "high" ;

	public static String log_event_type = "查询" ;

	static{

		//============几种日志类型(细分几种类型)============================
		logParameter.put("com.sugon.dc.execute.controller.ServiceController", "查询下载功能");
		logParameter.put("com.sugon.dc.execute.controller.StorageController", "资源库的创建和HIVE表操作");
		logParameter.put("com.sugon.dc.rmi.service.", "RMI接口调用");
		logParameter.put("com.sugon.dc.loges.controller.Task", "日志查询接口的调用");


		//===================日志接口的调用=======================
		logParameter.put("queryLogServiceBefore", "调用日志查询服务");
		logParameter.put("queryLogServiceAfter", "完成日志查询服务");
		logParameter.put("queryLogServiceEx", "调用日志查询服务发生异常");
		logParameter.put("queryLogServiceResultProcess", "调用中");
		logParameter.put("queryLogServiceResultSuccess", "成功");
		logParameter.put("queryLogServiceResultFail", "失败");


		logParameter.put("queryLogDetailByIdBefore", "调用具体日志查询服务");
		logParameter.put("queryLogDetailByIdAfter", "调用具体日志查询服务");
		logParameter.put("queryLogDetailByIdEx", "调用日志查询服务发生异常");
		logParameter.put("queryLogDetailByIdResultProcess", "调用中");
		logParameter.put("queryLogDetailByIdResultSuccess", "成功");
		logParameter.put("queryLogDetailByIdResultFail", "失败");


		logParameter.put("queryLogServiceByTimeAndDefaultBefore", "调用具体日志查询服务");
		logParameter.put("queryLogServiceByTimeAndDefaultAfter", "调用具体日志查询服务");
		logParameter.put("queryLogServiceByTimeAndDefaultEx", "调用日志查询服务发生异常");
		logParameter.put("queryLogServiceByTimeAndDefaultResultProcess", "调用中");
		logParameter.put("queryLogServiceByTimeAndDefaultResultSuccess", "成功");
		logParameter.put("queryLogServiceByTimeAndDefaultResultFail", "失败");


		//=============StorageController====================
		logParameter.put("registRelationBefore", "调用创建专题库");
		logParameter.put("registRelationAfter", "完成调用创建专题库");
		logParameter.put("registRelationEx", "调用创建专题库发生异常");
		logParameter.put("registRelationResultProcess", "调用中");
		logParameter.put("registRelationResultSuccess", "成功");
		logParameter.put("registRelationResultFail", "失败");

		logParameter.put("registHiveTblBefore", "调用通过SQOOP将数据导入到HDFS");
		logParameter.put("registHiveTblAfter", "完成通过SQOOP将数据导入到HDFS");
		logParameter.put("registHiveTblEx", "调用通过SQOOP将数据导入到HDFS发生异常");
		logParameter.put("registHiveTblResultProcess", "调用中");
		logParameter.put("registHiveTblResultSuccess", "成功");
		logParameter.put("registHiveTblResultFail", "失败");

		logParameter.put("changeUpdateTypeBefore", "调用改变任务状态");
		logParameter.put("changeUpdateTypeAfter", "完成改变任务状态");
		logParameter.put("changeUpdateTypeEx", "调用改变任务状态发生异常");
		logParameter.put("changeUpdateTypeResultProcess", "调用中");
		logParameter.put("changeUpdateTypeResultSuccess", "成功");
		logParameter.put("changeUpdateTypeResultFail", "失败");


		logParameter.put("queryAllHivetableBefore", "调用查询所有hive表");
		logParameter.put("queryAllHivetableAfter", "完成调用查询所有hive表");
		logParameter.put("queryAllHivetableEx", "调用查询所有hive表发生异常");
		logParameter.put("queryAllHivetableResultProcess", "调用中");
		logParameter.put("queryAllHivetableResultSuccess", "成功");
		logParameter.put("queryAllHivetableResultFail", "失败");

		logParameter.put("queryCreateTableBefore", "调用查询导出表的创建语句");
		logParameter.put("queryCreateTableAfter", "完成调用查询导出表的创建语句");
		logParameter.put("queryCreateTableEx", "调用查询导出表的创建语句发生异常");
		logParameter.put("queryCreateTableResultProcess", "调用中");
		logParameter.put("queryCreateTableResultSuccess", "成功");
		logParameter.put("queryCreateTableResultFail", "失败");

		logParameter.put("executeExportJobBefore", "调用执行导出命令");
		logParameter.put("executeExportJobAfter", "完成调用执行导出命令");
		logParameter.put("executeExportJobEx", "调用执行导出命令发生异常");
		logParameter.put("executeExportJobResultProcess", "调用中");
		logParameter.put("executeExportJobResultSuccess", "成功");
		logParameter.put("executeExportJobResultFail", "失败");


		logParameter.put("fileToHiveJobBefore", "调用文件导入hive任务");
		logParameter.put("fileToHiveJobAfter", "完成文件导入hive任务");
		logParameter.put("fileToHiveJobEx", "调用文件导入hive任务发生异常");
		logParameter.put("fileToHiveJobResultProcess", "调用中");
		logParameter.put("fileToHiveJobResultSuccess", "成功");
		logParameter.put("fileToHiveJobResultFail", "失败");


		logParameter.put("monitorBigMessageBefore", "调用监控大数据平台组件性能方法");
		logParameter.put("monitorBigMessageAfter", "完成调用监控大数据平台组件性能方法");
		logParameter.put("monitorBigMessageEx", "调用监控大数据平台组件性能方法发生异常");
		logParameter.put("monitorBigMessageResultProcess", "调用中");
		logParameter.put("monitorBigMessageResultSuccess", "成功");
		logParameter.put("monitorBigMessageResultFail", "失败");

		logParameter.put("createHivePartitionTableBefore", "调用创建分区表方法");
		logParameter.put("createHivePartitionTableAfter", "完成调用创建分区表方法");
		logParameter.put("createHivePartitionTableEx", "调用创建分区表方法发生异常");
		logParameter.put("createHivePartitionTableResultProcess", "调用中");
		logParameter.put("createHivePartitionTableResultSuccess", "成功");
		logParameter.put("createHivePartitionTableResultFail", "失败");




		//=============ServiceController====================

		logParameter.put("insertOverDateBefore", "调用插入数据服务");
		logParameter.put("insertOverDateAfter", "完成插入数据服务");
		logParameter.put("insertOverDateEx", "调用插入数据发生异常");
		logParameter.put("insertOverDateResultProcess", "调用中");
		logParameter.put("insertOverDateResultSuccess", "成功");
		logParameter.put("insertOverDateResultFail", "失败");

		logParameter.put("queryServiceBefore", "调用查询服务");
		logParameter.put("queryServiceAfter", "完成调用查询服务");
		logParameter.put("queryServiceEx", "调用查询服务发生异常");
		logParameter.put("queryServiceResultProcess", "调用中");
		logParameter.put("queryServiceResultSuccess", "成功");
		logParameter.put("queryServiceResultFail", "失败");



		logParameter.put("uploadFileBefore", "调用上传文件");
		logParameter.put("uploadFileAfter", "完成调用上传文件");
		logParameter.put("uploadFileEx", "调用上传文件发生异常");
		logParameter.put("uploadFileResultProcess", "调用中");
		logParameter.put("uploadFileResultSuccess", "成功");
		logParameter.put("uploadFileResultFail", "失败");

		logParameter.put("downloadBefore", "调用打包下载");
		logParameter.put("downloadAfter", "完成调用打包下载");
		logParameter.put("downloadEx", "调用打包下载发生异常");
		logParameter.put("downloadResultProcess", "调用中");
		logParameter.put("downloadResultSuccess", "成功");
		logParameter.put("downloadResultFail", "失败");

		//===============SearchController================
		logParameter.put("importTableBefore", "调用创建全文库表和搜索引擎");
		logParameter.put("importTableAfter", "完成调用创建全文库表和搜索引擎");
		logParameter.put("importTableEx", "调用创建全文库表和搜索引擎发生异常");
		logParameter.put("importTableResultProcess", "调用中");
		logParameter.put("importTableResultSuccess", "成功");
		logParameter.put("importTableResultFail", "失败");

		logParameter.put("importGisTableBefore", "调用创建GIS库");
		logParameter.put("importGisTableAfter", "完成调用创建GIS库");
		logParameter.put("importGisTableEx", "调用创建GIS库发生异常");
		logParameter.put("importGisTableResultProcess", "调用中");
		logParameter.put("importGisTableResultSuccess", "成功");
		logParameter.put("importGisTableResultFail", "失败");

		logParameter.put("searchNetworkBefore", "调用全网查询");
		logParameter.put("searchNetworkAfter", "完成调用全网查询");
		logParameter.put("searchNetworkEx", "调用全网查询发生异常");
		logParameter.put("searchNetworkResultProcess", "调用中");
		logParameter.put("searchNetworkResultSuccess", "成功");
		logParameter.put("searchNetworkResultFail", "失败");

		logParameter.put("showTableDataBefore", "调用查询具体某一张表");
		logParameter.put("showTableDataAfter", "完成调用查询具体某一张表");
		logParameter.put("showTableDataEx", "调用查询具体某一张表发生异常");
		logParameter.put("showTableDataResultProcess", "调用中");
		logParameter.put("showTableDataResultSuccess", "成功");
		logParameter.put("showTableDataResultFail", "失败");

		logParameter.put("showTableDataGisBefore", "调用查询查询GIS");
		logParameter.put("showTableDataGisAfter", "完成调用查询GIS");
		logParameter.put("showTableDataGisEx", "调用查询GIS发生异常");
		logParameter.put("showTableDataGisResultProcess", "调用中");
		logParameter.put("showTableDataGisResultSuccess", "成功");
		logParameter.put("showTableDataGisResultFail", "失败");

		logParameter.put("buildDtoBefore", "调用获取DTO");
		logParameter.put("buildDtoAfter", "完成调用获取DTO");
		logParameter.put("buildDtoEx", "调用获取DTO发生异常");
		logParameter.put("buildDtoResultProcess", "调用中");
		logParameter.put("buildDtoResultSuccess", "成功");
		logParameter.put("buildDtoResultFail", "失败");

		//================JobMonitController==============================

		logParameter.put("killJobBefore", "调用杀掉任务");
		logParameter.put("killJobAfter", "完成杀掉任务");
		logParameter.put("killJobEx", "调用杀掉任务发生异常");
		logParameter.put("killJobResultProcess", "调用中");
		logParameter.put("killJobResultSuccess", "成功");
		logParameter.put("killJobResultFail", "失败");

		//==================RMI=========================
		logParameter.put("DBActionBefore", "调用DBAction");
		logParameter.put("DBActionAfter", "调用完成DBAction");
		logParameter.put("DBActionEX", "调用DBAction发生异常");
		logParameter.put("DBActionResultProcess", "调用中");
		logParameter.put("DBActionResultSuccess", "成功");
		logParameter.put("DBActionResultFail", "失败");

		logParameter.put("FTPActionBefore", "调用FTPAction");
		logParameter.put("FTPActionAfter", "调用完成FTPAction");
		logParameter.put("FTPActionEX", "调用FTPAction发生异常");
		logParameter.put("FTPActionResultProcess", "调用中");
		logParameter.put("FTPActionResultSuccess", "成功");
		logParameter.put("FTPActionResultFail", "失败");

		logParameter.put("FTPForSdmActionBefore", "调用FTPForSdmAction");
		logParameter.put("FTPForSdmActionAfter", "调用完成FTPForSdmAction");
		logParameter.put("FTPForSdmActionEX", "调用FTPForSdmAction发生异常");
		logParameter.put("FTPForSdmActionResultProcess", "调用中");
		logParameter.put("FTPForSdmActionResultSuccess", "成功");
		logParameter.put("FTPForSdmActionResultFail", "失败");

		logParameter.put("FTPHarTaskActionBefore", "调用FTPHarTaskAction");
		logParameter.put("FTPHarTaskActionAfter", "调用完成FTPHarTaskAction");
		logParameter.put("FTPHarTaskActionEX", "调用FTPHarTaskAction发生异常");
		logParameter.put("FTPHarTaskActionResultProcess", "调用中");
		logParameter.put("FTPHarTaskActionResultSuccess", "成功");
		logParameter.put("FTPHarTaskActionResultFail", "失败");

		logParameter.put("FTPIndexTarActionBefore", "调用FTPIndexTarAction");
		logParameter.put("FTPIndexTarActionAfter", "调用完成FTPIndexTarAction");
		logParameter.put("FTPIndexTarActionEX", "调用FTPIndexTarAction发生异常");
		logParameter.put("FTPIndexTarActionResultProcess", "调用中");
		logParameter.put("FTPIndexTarActionResultSuccess", "成功");
		logParameter.put("FTPIndexTarActionResultFail", "失败");

		//==========================ES的相关查询服务=============================
		logParameter.put("queryESLeftDBListBefore", "调用ES查询服务");
		logParameter.put("queryESLeftDBListAfter", "调用完成ES查询服务");
		logParameter.put("queryESLeftDBListEX", "调用完成ES查询服务发生异常");
		logParameter.put("queryESLeftDBListResultProcess", "调用中");
		logParameter.put("queryESLeftDBListResultSuccess", "成功");
		logParameter.put("queryESLeftDBListResultFail", "失败");

		logParameter.put("queryESRightDBListBefore", "调用ES查询服务");
		logParameter.put("queryESRightDBListAfter", "调用完成ES查询服务");
		logParameter.put("queryESRightDBListEX", "调用完成ES查询服务发生异常");
		logParameter.put("queryESRightDBListResultProcess", "调用中");
		logParameter.put("queryESRightDBListResultSuccess", "成功");
		logParameter.put("queryESRightDBListResultFail", "失败");

		logParameter.put("queryESFileListBefore", "调用ES查询FTP服务");
		logParameter.put("queryESFileListAfter", "调用完成ES查询FTP服务");
		logParameter.put("queryESFileListEX", "调用完成ES查询FTP服务发生异常");
		logParameter.put("queryESFileListResultProcess", "调用中");
		logParameter.put("queryESFileListResultSuccess", "成功");
		logParameter.put("queryESFileListResultFail", "失败");

	}

}
