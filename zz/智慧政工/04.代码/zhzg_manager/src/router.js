import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

export default new Router({
  mode: 'hash',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/systemManagement', //队伍综合评估路由
      name: 'systemManagement',
      component: () => import('./views/templateAnalysis/Home.vue'),
      children: [
        {
          path: '/',
          redirect: 'templateSystem'
        },
        {
          path: 'templateSystem',
          name: 'templateSystem',
          component: () => import('./views/templateAnalysis/templateSystem.vue')
        },
      ]
    },
    {
      path: '/systemManagement', //队伍综合评估路由
      name: 'systemManagement',
      component: () => import('./views/templateAnalysis/Home2.vue'),
      children: [
        {
          path: 'useReporter',
          name: 'useReporter',
          component: () => import('./views/templateAnalysis/useReporter.vue')
        },
        {
          path: 'personalData',
          name: 'personalData',
          component: () => import('./views/templateAnalysis/personalData.vue')
        },
      ]
    },
    {
      path: '/',
      redirect: '/systemManagement/templateSystem'
    },
    {
      path: '*',
      component: () => import('./views/404.vue')
    }
  ]
});
