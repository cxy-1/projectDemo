<template>
  <div class="home">
    <header>
      <aside>
        <img src="../../assets/image/teamAnalysis/developEffect/logo.png" />
        <span>南京公安智慧政工</span>
        <strong>队伍综合评估系统</strong>
      </aside>
      <aside>
        <button
          v-for="item in nameArr"
          :key="item.hash"
          @click="btnClick(item)"
          :class="item.hash === selectedItem && 'btn-selected'"
        >
          {{ item.name }}
        </button>
      </aside>
    </header>
    <router-view></router-view>
  </div>
</template>

<script>
import { getUrlParams } from '@/utils';

export default {
  data() {
    return {
      nameArr: [
        {
          name: '队伍综合建设成效',
          hash: '/teamAnalysis/developEffect'
        },
        {
          name: '队伍人员立体剖析',
          hash: '/teamAnalysis/deepContest'
        },
        {
          name: '队伍效能深度较量',
          hash: '/teamAnalysis/dissect'
        },
        {
          name: '队伍人员画像对比',
          hash: '/teamAnalysis/portraitContrast'
        }
      ],
      selectedItem: ''
    };
  },
  methods: {
    // 菜单点击事件
    btnClick(item) {
      this.selectedItem = item.hash;
      const hash = location.hash;
      const query = getUrlParams(hash);
      this.$router.push({ path: this.selectedItem, query });
    }
  },
  mounted() {
    // 加载路由中的选中的item
    const hash = location.hash;
    this.selectedItem = hash.match(/[^?=&#]+/)[0] || '';
  }
};
</script>
<style lang="scss">
@font-face {
  font-family: 'hyliliangheij';
  src: url('../../assets/fonts/hyliliangheij.ttf');
}
.home {
  height: 100%;
  background-image: url(../../assets/image/teamAnalysis/developEffect/bg.png);
  background-size: 100% 100%;
  overflow: hidden;

  & > header {
    height: 100px;
    display: flex;
    justify-content: space-between;
    justify-items: center;
    padding: 0 50px;
    margin-top: 20px;

    span {
      margin-left: 15px;
      font-family: MicrosoftYaHei-Bold;
      font-size: 24px;
      letter-spacing: 1px;
      color: #434343;
      line-height: 50px;
    }

    strong {
      margin-left: 10px;
      font-family: 'hyliliangheij';
      font-size: 24px;
      font-weight: normal;
      font-stretch: normal;
      letter-spacing: 1px;
      color: #3b71fb;
      line-height: 50px;
      position: relative;
      top: -2px;
    }

    button {
      width: 180px;
      height: 50px;
      background-color: #a7b3d1;
      border-radius: 25px;
      color: #fff;
      opacity: 0.66;
      box-shadow: 0px 6px 10px 0px rgba(92, 106, 129, 0.34);
      border-radius: 25px;
      margin-left: 20px;
      outline: none;
      cursor: pointer;
      font-size: 16px;
      font-weight: bold;
      letter-spacing: 1px;

      &:hover {
        background-color: #576ea9;
        opacity: 1;
      }
    }

    .btn-selected {
      background-color: #576ea9;
      opacity: 1;
    }
  }

  & > div {
    height: calc(100% - 120px);
  }
}
</style>