import Vue from 'vue';
import Vuex from 'vuex';
import teamAnalysis from './teamAnalysis';

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    teamAnalysis
  }
});
