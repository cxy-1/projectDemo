import Vue from 'vue';
import App from './App';
import router from './router';
import axios from 'axios';
import global from './Global';
import ElementUI from 'element-ui';


import Resource from 'vue-resource';
import VueSplit from 'vue-split-panel';//分割面板拉伸拖拽
import VueDND from 'awe-dnd';
import timeline from '../static/js/timeline-min.js';
import echarts from "echarts";
import VCharts from 'v-charts'

// import {communication} from '../../data/getAxios';


import 'element-ui/lib/theme-chalk/index.css';
import './assets/iconfont/iconfont.css';//引入阿里图标
import 'echarts-wordcloud/dist/echarts-wordcloud.js';//引入词云
// import '../static/css/theme-green/index.css';       // 浅绿色主题
import "babel-polyfill";
Vue.use(Resource);
Vue.use(VueDND);
Vue.use(ElementUI, { size: 'small' });
Vue.use(VueSplit);
Vue.use(VCharts);
Vue.use(timeline);
Vue.prototype.$echarts = echarts;
Vue.prototype.global=global;
Vue.prototype.$axios=axios;






//使用钩子函数对路由进行权限跳转
// router.beforeEach((to, from, next) => {
//     const role = localStorage.getItem('ms_username');
//     if(!role && to.path !== '/login'){
//         next('/login');
//     }else if(to.meta.permission){
//         // 如果是管理员权限则可进入，这里只是简单的模拟管理员权限而已
//         role === 'admin' ? next() : next('/403');
//     }else{
//         // 简单的判断IE10及以下不进入富文本编辑器，该组件不兼容
//         if(navigator.userAgent.indexOf('MSIE') > -1 && to.path === '/editor'){
//             Vue.prototype.$alert('vue-quill-editor组件不兼容IE10及以下浏览器，请使用更高版本的浏览器查看', '浏览器不兼容通知', {
//                 confirmButtonText: '确定'
//             });
//         }else{
//             next();
//         }
//     }
// })

window.myVue=new Vue({
    router,
    render: h => h(App)
}).$mount('#app');
