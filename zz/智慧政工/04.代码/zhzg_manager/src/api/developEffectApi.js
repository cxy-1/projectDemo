import api from './';

// 获取元数据
const getMetaData = params => {
  return api.get('teamBuild/getTeamInfo', { params });
};
// 下钻接口
const getZoomData = params => {
  return api.get('teamBuild/getChildUnit', { params });
}

export {
  getMetaData,
  getZoomData,
};
