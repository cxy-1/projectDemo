import Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import echarts from 'echarts';
// 自主封装的axios
import axios from './api';
// 引入阿里巴巴图标库
import './assets/iconfont/iconfont.css';

// 使用mockjs模拟数据，打包时请注释掉
// import './api/mock';

Vue.prototype.$echarts = echarts;
Vue.prototype.$store = store;
Vue.use(ElementUI);

Vue.prototype.$axios = axios;
Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app');
