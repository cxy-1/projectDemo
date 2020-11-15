import api from './';

// 获取元数据
const getMetaData = params => {
  return api.get('teamBuild/getTeamInfo', { params });
};

// 获取主要的数据
const getSideData = params => {
  return api.post('teamBuild/getTeamDataInfo', params);
};

// 获取地图颜色
const getDeptColor = params => {
  return api.post('teamBuild/getDeptColor', params);
};

// 下钻接口
const getZoomData = params => {
  return api.get('teamBuild/getChildUnit', { params });
};

// 地图浮框接口
const getWindowsInfo = params => {
  return api.post('teamBuild/getWindowsInfo', params);
};

// 排行榜数据列表
const getRankList = params => {
  return api.post('teamBuild/getRankList', params);
};

// 获取人员详情
const getPersonDetail = params => {
  return api.post('teamBuild/getPersonDetail', params);
};

// 获取年龄结构
const getAgeDetail = (params, config) => {
  return api.post('teamBuild/getAgeDetail', params, config);
};

// 获取教育培训
const getTrainDetail = (params, config) => {
  return api.post('teamBuild/getTrainDetail', params, config);
};

// 获取集体荣誉
const getOrgHonorDetail = (params, config) => {
  return api.post('teamBuild/getOrgHonorDetail', params, config);
};

// 获取个人荣誉
const getPersonHonorDetail = (params, config) => {
  return api.post('teamBuild/getPersonHonorDetail', params, config);
};

// 获取政工绩效
const getKPI = (params, config) => {
  return api.post('teamBuild/getKPI', params, config);
};

// 获取违纪案件
const getBreakRuleCase = (params, config) => {
  return api.post('teamBuild/getBreakRuleCase', params, config);
};

// 报告导出功能
const getTeamReport = (params, config) => {
  return api.post('report/getTeamReport', params, config);
};

// 领导班子eacharts接口
const getLeaderData = (params, config) => {
  return api.post('report/getLeaderData', params, config);
};
// 隐藏的非领导班子eacharts接口
const getHiddenData = (params, config) => {
  return api.post('report/getHiddenData', params, config);
};
// 领导班子eacharts接口
const getLeaderDetail = (params, config) => {
  return api.post('teamLeaderBuild/getLeaderDetail', params, config);
};
export {
  getHiddenData,
  getMetaData,
  getSideData,
  getDeptColor,
  getZoomData,
  getWindowsInfo,
  getRankList,
  getPersonDetail,
  getAgeDetail,
  getTrainDetail,
  getOrgHonorDetail,
  getPersonHonorDetail,
  getKPI,
  getBreakRuleCase,
  getTeamReport,
  getLeaderData,
  getLeaderDetail
};
