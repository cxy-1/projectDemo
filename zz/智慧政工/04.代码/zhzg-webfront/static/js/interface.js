/**
 *
 * 地图交互接口
 * @author gaeainfo
 *
 */
var MapConfig = {
	mapId: 'szmap',
	mapURL: 'http://50.128.7.120:8080/xzeckf/' //地图flash文件地址 （可选配置，使用js嵌入地图时必须指定）
};

function SetData(UserID, TaskGuid, DataGuid, DataType, XmlData) {
	var mapHandler = getMapHandler();
	if (mapHandler && mapHandler.sendDataToGIS) {
		try {
			//alert(XmlData);
			mapHandler.sendDataToGIS(UserID, TaskGuid, DataGuid, DataType, XmlData);
		} catch (err) {}
	}
}


function GetData(UserID, TaskGuid, DataGuid, DataType) {
	var mapHandler = getMapHandler();
	return mapHandler.getDataFromGIS(UserID, TaskGuid, DataGuid, DataType);
}

function TransformData(UserID, TaskGuid, XmlTransform) {
	var mapHandler = getMapHandler();
	return mapHandler.transformData(UserID, TaskGuid, XmlTransform);
}

function SetData_CallBack(UserID, TaskGuid, DataGuid, DataType, XmlData) {

	// alert(XmlData);
	if (DataType == "LoadComplete") {
		var testToolbarData = '<Document TaskGuid="FF17D1B3-85C7-40F4-8CDC-73DC57CD29BC" DataGuid="" DataType="ToolBar">' +
			'<ButtonName Type="TEXT">ZoomIn</ButtonName>' +
			'<ButtonName Type="TEXT">ZoomOut</ButtonName>' +
			'<ButtonName Type="TEXT">Move</ButtonName>' +
			'<ButtonName Type="TEXT">MeasureDistance</ButtonName>' +
			'<ButtonName Type="TEXT">MeasureArea</ButtonName>' +
			'<ButtonName Type="TEXT">Clear</ButtonName>' +
			'<ButtonName Type="TEXT">MapPoint</ButtonName>' +
			'<ButtonName Type="TEXT">MapLocate</ButtonName>' +
			'<ButtonName Type="TEXT">DragCircle</ButtonName>' +
			'<ButtonName Type="TEXT">DragRectangle</ButtonName>' +
			'<ButtonName Type="TEXT">DragPolygon</ButtonName>' +
			'<ButtonName Type="TEXT">FullExtent</ButtonName>' +
			'<ButtonName Type="TEXT">POISearch</ButtonName>' +
			'<ButtonName Type="TEXT">SwitchMap</ButtonName>' +
			'<ButtonName Type="TEXT">AutoSwitchControl</ButtonName>' +
			'</Document>';

		addLayer("360");
		SetData(UserID, TaskGuid, DataGuid, "ToolBar", testToolbarData);
		init();
	}

}
/**
 *  调用GIS内部方法
 * @param UserID
 * @param TaskGuid
 * @param DataGuid
 * @param FunctionType GIS方法类型
 * @param xmlData
 * @constructor
 */
function ControlGISFunction(UserID, TaskGuid, DataGuid, FunctionType, xmlData) {
	var mapHandler = getMapHandler();
	if (mapHandler && mapHandler.controlGISFunction) {
		try {
			var xmlDataStr = arguments[4] || "";
			mapHandler.controlGISFunction(UserID, TaskGuid, DataGuid, FunctionType, xmlDataStr);
		} catch (err) {}
	}
}

function GetData_CallBack(UserID, TaskGuid, DataGuid, DataType) {

}

function TransformData_CallBack(UserID, TaskGuid, XmlTransform) {

}

function getMapHandler() {
	var mapId = MapConfig.mapId || 'szmap';
	return document.getElementById(mapId);
	//if(!element){
	//	var frameElement=document.getElementById("mapFrame").contentWindow;
	//	element=frameElement.document.getElementById(MapConfig.mapId[1]) || frameElement.document.getElementById(MapConfig.mapId[0]);
	//}
}

function embedMAP(containerId) {
	var baseURL = MapConfig.mapURL;
	var mapId = MapConfig.mapId || 'szmap';
	var content = '';
	var params = [];
	params.push('<param name="base" value="' + baseURL + '" />');
	params.push('<param name="bgcolor" value="#FFFFFF" />');
	params.push('<param name="allowScriptAccess" value="always" />');
	params.push('<param name="allowFullScreen" value="true" />');
	params.push('<param name="wmode" value="transparent" />');

	var common = params.join('');

	var isIE = navigator.userAgent.indexOf("MSIE") > 0;
	if (isIE) {
		content += '<object id="' + mapId + '" classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" width="100%" height="100%">';
		content += '<param name="movie" value="' + baseURL + 'index.swf" />';
		content += common;
		content += '<param name="wmode" value="transparent"/>';
		content += '</object>';

	} else {
		content += '<object id="' + mapId + '" type="application/x-shockwave-flash" data="' + baseURL + 'index.swf" width="100%" height="100%">';
		content += common;
		content += '</object>';
	}


	var element = document.getElementById(containerId);
	element.innerHTML = content;
}