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

	public static String log_event_type = "��ѯ" ;

	static{

		//============������־����(ϸ�ּ�������)============================
		logParameter.put("com.sugon.dc.execute.controller.ServiceController", "��ѯ���ع���");
		logParameter.put("com.sugon.dc.execute.controller.StorageController", "��Դ��Ĵ�����HIVE������");
		logParameter.put("com.sugon.dc.rmi.service.", "RMI�ӿڵ���");
		logParameter.put("com.sugon.dc.loges.controller.Task", "��־��ѯ�ӿڵĵ���");


		//===================��־�ӿڵĵ���=======================
		logParameter.put("queryLogServiceBefore", "������־��ѯ����");
		logParameter.put("queryLogServiceAfter", "�����־��ѯ����");
		logParameter.put("queryLogServiceEx", "������־��ѯ�������쳣");
		logParameter.put("queryLogServiceResultProcess", "������");
		logParameter.put("queryLogServiceResultSuccess", "�ɹ�");
		logParameter.put("queryLogServiceResultFail", "ʧ��");


		logParameter.put("queryLogDetailByIdBefore", "���þ�����־��ѯ����");
		logParameter.put("queryLogDetailByIdAfter", "���þ�����־��ѯ����");
		logParameter.put("queryLogDetailByIdEx", "������־��ѯ�������쳣");
		logParameter.put("queryLogDetailByIdResultProcess", "������");
		logParameter.put("queryLogDetailByIdResultSuccess", "�ɹ�");
		logParameter.put("queryLogDetailByIdResultFail", "ʧ��");


		logParameter.put("queryLogServiceByTimeAndDefaultBefore", "���þ�����־��ѯ����");
		logParameter.put("queryLogServiceByTimeAndDefaultAfter", "���þ�����־��ѯ����");
		logParameter.put("queryLogServiceByTimeAndDefaultEx", "������־��ѯ�������쳣");
		logParameter.put("queryLogServiceByTimeAndDefaultResultProcess", "������");
		logParameter.put("queryLogServiceByTimeAndDefaultResultSuccess", "�ɹ�");
		logParameter.put("queryLogServiceByTimeAndDefaultResultFail", "ʧ��");


		//=============StorageController====================
		logParameter.put("registRelationBefore", "���ô���ר���");
		logParameter.put("registRelationAfter", "��ɵ��ô���ר���");
		logParameter.put("registRelationEx", "���ô���ר��ⷢ���쳣");
		logParameter.put("registRelationResultProcess", "������");
		logParameter.put("registRelationResultSuccess", "�ɹ�");
		logParameter.put("registRelationResultFail", "ʧ��");

		logParameter.put("registHiveTblBefore", "����ͨ��SQOOP�����ݵ��뵽HDFS");
		logParameter.put("registHiveTblAfter", "���ͨ��SQOOP�����ݵ��뵽HDFS");
		logParameter.put("registHiveTblEx", "����ͨ��SQOOP�����ݵ��뵽HDFS�����쳣");
		logParameter.put("registHiveTblResultProcess", "������");
		logParameter.put("registHiveTblResultSuccess", "�ɹ�");
		logParameter.put("registHiveTblResultFail", "ʧ��");

		logParameter.put("changeUpdateTypeBefore", "���øı�����״̬");
		logParameter.put("changeUpdateTypeAfter", "��ɸı�����״̬");
		logParameter.put("changeUpdateTypeEx", "���øı�����״̬�����쳣");
		logParameter.put("changeUpdateTypeResultProcess", "������");
		logParameter.put("changeUpdateTypeResultSuccess", "�ɹ�");
		logParameter.put("changeUpdateTypeResultFail", "ʧ��");


		logParameter.put("queryAllHivetableBefore", "���ò�ѯ����hive��");
		logParameter.put("queryAllHivetableAfter", "��ɵ��ò�ѯ����hive��");
		logParameter.put("queryAllHivetableEx", "���ò�ѯ����hive�������쳣");
		logParameter.put("queryAllHivetableResultProcess", "������");
		logParameter.put("queryAllHivetableResultSuccess", "�ɹ�");
		logParameter.put("queryAllHivetableResultFail", "ʧ��");

		logParameter.put("queryCreateTableBefore", "���ò�ѯ�������Ĵ������");
		logParameter.put("queryCreateTableAfter", "��ɵ��ò�ѯ�������Ĵ������");
		logParameter.put("queryCreateTableEx", "���ò�ѯ�������Ĵ�����䷢���쳣");
		logParameter.put("queryCreateTableResultProcess", "������");
		logParameter.put("queryCreateTableResultSuccess", "�ɹ�");
		logParameter.put("queryCreateTableResultFail", "ʧ��");

		logParameter.put("executeExportJobBefore", "����ִ�е�������");
		logParameter.put("executeExportJobAfter", "��ɵ���ִ�е�������");
		logParameter.put("executeExportJobEx", "����ִ�е���������쳣");
		logParameter.put("executeExportJobResultProcess", "������");
		logParameter.put("executeExportJobResultSuccess", "�ɹ�");
		logParameter.put("executeExportJobResultFail", "ʧ��");


		logParameter.put("fileToHiveJobBefore", "�����ļ�����hive����");
		logParameter.put("fileToHiveJobAfter", "����ļ�����hive����");
		logParameter.put("fileToHiveJobEx", "�����ļ�����hive�������쳣");
		logParameter.put("fileToHiveJobResultProcess", "������");
		logParameter.put("fileToHiveJobResultSuccess", "�ɹ�");
		logParameter.put("fileToHiveJobResultFail", "ʧ��");


		logParameter.put("monitorBigMessageBefore", "���ü�ش�����ƽ̨������ܷ���");
		logParameter.put("monitorBigMessageAfter", "��ɵ��ü�ش�����ƽ̨������ܷ���");
		logParameter.put("monitorBigMessageEx", "���ü�ش�����ƽ̨������ܷ��������쳣");
		logParameter.put("monitorBigMessageResultProcess", "������");
		logParameter.put("monitorBigMessageResultSuccess", "�ɹ�");
		logParameter.put("monitorBigMessageResultFail", "ʧ��");

		logParameter.put("createHivePartitionTableBefore", "���ô�������������");
		logParameter.put("createHivePartitionTableAfter", "��ɵ��ô�������������");
		logParameter.put("createHivePartitionTableEx", "���ô������������������쳣");
		logParameter.put("createHivePartitionTableResultProcess", "������");
		logParameter.put("createHivePartitionTableResultSuccess", "�ɹ�");
		logParameter.put("createHivePartitionTableResultFail", "ʧ��");




		//=============ServiceController====================

		logParameter.put("insertOverDateBefore", "���ò������ݷ���");
		logParameter.put("insertOverDateAfter", "��ɲ������ݷ���");
		logParameter.put("insertOverDateEx", "���ò������ݷ����쳣");
		logParameter.put("insertOverDateResultProcess", "������");
		logParameter.put("insertOverDateResultSuccess", "�ɹ�");
		logParameter.put("insertOverDateResultFail", "ʧ��");

		logParameter.put("queryServiceBefore", "���ò�ѯ����");
		logParameter.put("queryServiceAfter", "��ɵ��ò�ѯ����");
		logParameter.put("queryServiceEx", "���ò�ѯ�������쳣");
		logParameter.put("queryServiceResultProcess", "������");
		logParameter.put("queryServiceResultSuccess", "�ɹ�");
		logParameter.put("queryServiceResultFail", "ʧ��");



		logParameter.put("uploadFileBefore", "�����ϴ��ļ�");
		logParameter.put("uploadFileAfter", "��ɵ����ϴ��ļ�");
		logParameter.put("uploadFileEx", "�����ϴ��ļ������쳣");
		logParameter.put("uploadFileResultProcess", "������");
		logParameter.put("uploadFileResultSuccess", "�ɹ�");
		logParameter.put("uploadFileResultFail", "ʧ��");

		logParameter.put("downloadBefore", "���ô������");
		logParameter.put("downloadAfter", "��ɵ��ô������");
		logParameter.put("downloadEx", "���ô�����ط����쳣");
		logParameter.put("downloadResultProcess", "������");
		logParameter.put("downloadResultSuccess", "�ɹ�");
		logParameter.put("downloadResultFail", "ʧ��");

		//===============SearchController================
		logParameter.put("importTableBefore", "���ô���ȫ�Ŀ������������");
		logParameter.put("importTableAfter", "��ɵ��ô���ȫ�Ŀ������������");
		logParameter.put("importTableEx", "���ô���ȫ�Ŀ�����������淢���쳣");
		logParameter.put("importTableResultProcess", "������");
		logParameter.put("importTableResultSuccess", "�ɹ�");
		logParameter.put("importTableResultFail", "ʧ��");

		logParameter.put("importGisTableBefore", "���ô���GIS��");
		logParameter.put("importGisTableAfter", "��ɵ��ô���GIS��");
		logParameter.put("importGisTableEx", "���ô���GIS�ⷢ���쳣");
		logParameter.put("importGisTableResultProcess", "������");
		logParameter.put("importGisTableResultSuccess", "�ɹ�");
		logParameter.put("importGisTableResultFail", "ʧ��");

		logParameter.put("searchNetworkBefore", "����ȫ����ѯ");
		logParameter.put("searchNetworkAfter", "��ɵ���ȫ����ѯ");
		logParameter.put("searchNetworkEx", "����ȫ����ѯ�����쳣");
		logParameter.put("searchNetworkResultProcess", "������");
		logParameter.put("searchNetworkResultSuccess", "�ɹ�");
		logParameter.put("searchNetworkResultFail", "ʧ��");

		logParameter.put("showTableDataBefore", "���ò�ѯ����ĳһ�ű�");
		logParameter.put("showTableDataAfter", "��ɵ��ò�ѯ����ĳһ�ű�");
		logParameter.put("showTableDataEx", "���ò�ѯ����ĳһ�ű������쳣");
		logParameter.put("showTableDataResultProcess", "������");
		logParameter.put("showTableDataResultSuccess", "�ɹ�");
		logParameter.put("showTableDataResultFail", "ʧ��");

		logParameter.put("showTableDataGisBefore", "���ò�ѯ��ѯGIS");
		logParameter.put("showTableDataGisAfter", "��ɵ��ò�ѯGIS");
		logParameter.put("showTableDataGisEx", "���ò�ѯGIS�����쳣");
		logParameter.put("showTableDataGisResultProcess", "������");
		logParameter.put("showTableDataGisResultSuccess", "�ɹ�");
		logParameter.put("showTableDataGisResultFail", "ʧ��");

		logParameter.put("buildDtoBefore", "���û�ȡDTO");
		logParameter.put("buildDtoAfter", "��ɵ��û�ȡDTO");
		logParameter.put("buildDtoEx", "���û�ȡDTO�����쳣");
		logParameter.put("buildDtoResultProcess", "������");
		logParameter.put("buildDtoResultSuccess", "�ɹ�");
		logParameter.put("buildDtoResultFail", "ʧ��");

		//================JobMonitController==============================

		logParameter.put("killJobBefore", "����ɱ������");
		logParameter.put("killJobAfter", "���ɱ������");
		logParameter.put("killJobEx", "����ɱ���������쳣");
		logParameter.put("killJobResultProcess", "������");
		logParameter.put("killJobResultSuccess", "�ɹ�");
		logParameter.put("killJobResultFail", "ʧ��");

		//==================RMI=========================
		logParameter.put("DBActionBefore", "����DBAction");
		logParameter.put("DBActionAfter", "�������DBAction");
		logParameter.put("DBActionEX", "����DBAction�����쳣");
		logParameter.put("DBActionResultProcess", "������");
		logParameter.put("DBActionResultSuccess", "�ɹ�");
		logParameter.put("DBActionResultFail", "ʧ��");

		logParameter.put("FTPActionBefore", "����FTPAction");
		logParameter.put("FTPActionAfter", "�������FTPAction");
		logParameter.put("FTPActionEX", "����FTPAction�����쳣");
		logParameter.put("FTPActionResultProcess", "������");
		logParameter.put("FTPActionResultSuccess", "�ɹ�");
		logParameter.put("FTPActionResultFail", "ʧ��");

		logParameter.put("FTPForSdmActionBefore", "����FTPForSdmAction");
		logParameter.put("FTPForSdmActionAfter", "�������FTPForSdmAction");
		logParameter.put("FTPForSdmActionEX", "����FTPForSdmAction�����쳣");
		logParameter.put("FTPForSdmActionResultProcess", "������");
		logParameter.put("FTPForSdmActionResultSuccess", "�ɹ�");
		logParameter.put("FTPForSdmActionResultFail", "ʧ��");

		logParameter.put("FTPHarTaskActionBefore", "����FTPHarTaskAction");
		logParameter.put("FTPHarTaskActionAfter", "�������FTPHarTaskAction");
		logParameter.put("FTPHarTaskActionEX", "����FTPHarTaskAction�����쳣");
		logParameter.put("FTPHarTaskActionResultProcess", "������");
		logParameter.put("FTPHarTaskActionResultSuccess", "�ɹ�");
		logParameter.put("FTPHarTaskActionResultFail", "ʧ��");

		logParameter.put("FTPIndexTarActionBefore", "����FTPIndexTarAction");
		logParameter.put("FTPIndexTarActionAfter", "�������FTPIndexTarAction");
		logParameter.put("FTPIndexTarActionEX", "����FTPIndexTarAction�����쳣");
		logParameter.put("FTPIndexTarActionResultProcess", "������");
		logParameter.put("FTPIndexTarActionResultSuccess", "�ɹ�");
		logParameter.put("FTPIndexTarActionResultFail", "ʧ��");

		//==========================ES����ز�ѯ����=============================
		logParameter.put("queryESLeftDBListBefore", "����ES��ѯ����");
		logParameter.put("queryESLeftDBListAfter", "�������ES��ѯ����");
		logParameter.put("queryESLeftDBListEX", "�������ES��ѯ�������쳣");
		logParameter.put("queryESLeftDBListResultProcess", "������");
		logParameter.put("queryESLeftDBListResultSuccess", "�ɹ�");
		logParameter.put("queryESLeftDBListResultFail", "ʧ��");

		logParameter.put("queryESRightDBListBefore", "����ES��ѯ����");
		logParameter.put("queryESRightDBListAfter", "�������ES��ѯ����");
		logParameter.put("queryESRightDBListEX", "�������ES��ѯ�������쳣");
		logParameter.put("queryESRightDBListResultProcess", "������");
		logParameter.put("queryESRightDBListResultSuccess", "�ɹ�");
		logParameter.put("queryESRightDBListResultFail", "ʧ��");

		logParameter.put("queryESFileListBefore", "����ES��ѯFTP����");
		logParameter.put("queryESFileListAfter", "�������ES��ѯFTP����");
		logParameter.put("queryESFileListEX", "�������ES��ѯFTP�������쳣");
		logParameter.put("queryESFileListResultProcess", "������");
		logParameter.put("queryESFileListResultSuccess", "�ɹ�");
		logParameter.put("queryESFileListResultFail", "ʧ��");

	}

}