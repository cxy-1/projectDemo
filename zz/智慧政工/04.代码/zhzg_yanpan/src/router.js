import Vue from 'vue';
import Router from 'vue-router';

Vue.use(Router);

export default new Router({
  mode: 'hash',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/policePortraitHome', //民警画像路由
      name: 'policePortraitHome',
      component: () => import('./views/policePortrait/policePortraitHome.vue'),
      children: [
        {
          path: '/', //民警画像路由
          redirect: 'myPortrait'
        },
        {
          path: 'myPortrait', //我的画像路由
          name: 'myPortrait',
          component: () => import('./views/policePortrait/myPortrait.vue')
        }
      ]
    },
    {
      path: '/teamAnalysis', //队伍综合评估路由
      name: 'teamAnalysis',
      component: () => import('./views/teamAnalysis/Home.vue'),
      children: [
        {
          path: '/',
          redirect: 'developEffect'
        },
        {
          path: 'developEffect',
          name: 'developEffect',
          component: () => import('./views/teamAnalysis/DevelopEffect.vue')
        },
        {
          path: 'dissect',
          name: 'dissect',
          component: () => import('./views/teamAnalysis/Dissect.vue')
        },
        {
          path: 'deepContest',
          name: 'deepContest',
          component: () => import('./views/teamAnalysis/DeepContest.vue')
        },
        {
          path: 'portraitContrast', //队伍人员画像对比路由
          name: 'portraitContrast',
          component: () => import('./views/teamAnalysis/PortraitContrast.vue')
        }
      ]
    },
    {
      path: '*',
      component: () => import('./views/404.vue')
    }
  ]
});
