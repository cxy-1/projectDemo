import { getMetaData, getZoomData } from '@/api/developEffectApi';

const teamAnalysis = {
  namespaced: true,
  state: {
    // 查询条件
    searchRuler: {},
    // 身份证信息
    idNumber: '',
    unitId: ''
  },
  mutations: {
    // 设置查询条件
    setSearchRuler(state, searchRuler) {
      state.searchRuler = searchRuler;
    },
    // 设置身份证信息
    setIdNumber(state, idNumber = '') {
      state.idNumber = idNumber;
    },
    // 设置分析页面单位id
    setUnitId(state, unitId = '') {
      state.unitId = unitId;
    }
  },
  actions: {
    // 设置查询条件
    async setSearchRuler({ commit }, { idNumber, isZoom, unitId }) {
      let result = isZoom
        ? await getZoomData({ unitId })
        : await getMetaData({ idNumber });
      commit('setSearchRuler', result.data);
    }
  },
  getters: {}
};

export default teamAnalysis;
