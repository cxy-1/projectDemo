<template>
  <div class="home wrapper">
    <router-view></router-view>
  </div>
</template>

<script>
import { getUrlParams } from "@/utils";
import vSidebar from "./Sidebar.vue";
import bus from "../templateAnalysis/bus";
export default {
  data() {
    return {
      selectedItem: "",
      tagsList: [],
      collapse: false,
    };
  },
  components: { vSidebar },
  created() {
    bus.$on("collapse", msg => {
      this.collapse = msg;
    });

    // 只有在标签页列表里的页面才使用keep-alive，即关闭标签之后就不保存到内存中了。
    bus.$on("tags", msg => {
      let arr = [];
      for (let i = 0, len = msg.length; i < len; i++) {
        msg[i].name && arr.push(msg[i].name);
      }
      this.tagsList = arr;
    });
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
    this.selectedItem = hash.match(/[^?=&#]+/)[0] || "";

  }
};
</script>
<style lang="scss">
body, html {
    width: 100%;
    height: 100%;
    overflow: auto;
    // min-width: 1480px;
    // min-height: 1360px;
    font-family: '\5FAE\8F6F\96C5\9ED1';
}
// .home {
//   height: 100%;

//   & > header {
//     height: 100px;
//     display: flex;
//     justify-content: space-between;
//     justify-items: center;
//     padding: 0 50px;
//     margin-top: 20px;

//     span {
//       margin-left: 15px;
//       font-family: MicrosoftYaHei-Bold;
//       font-size: 24px;
//       letter-spacing: 1px;
//       color: #434343;
//       line-height: 50px;
//     }

//     strong {
//       margin-left: 10px;
//       font-family: "hyliliangheij";
//       font-size: 24px;
//       font-weight: normal;
//       font-stretch: normal;
//       letter-spacing: 1px;
//       color: #3b71fb;
//       line-height: 50px;
//       position: relative;
//       top: -2px;
//     }

//     button {
//       width: 180px;
//       height: 50px;
//       background-color: #a7b3d1;
//       border-radius: 25px;
//       color: #fff;
//       opacity: 0.66;
//       box-shadow: 0px 6px 10px 0px rgba(92, 106, 129, 0.34);
//       border-radius: 25px;
//       margin-left: 20px;
//       outline: none;
//       cursor: pointer;
//       font-size: 16px;
//       font-weight: bold;
//       letter-spacing: 1px;

//       &:hover {
//         background-color: #576ea9;
//         opacity: 1;
//       }
//     }

//     .btn-selected {
//       background-color: #576ea9;
//       opacity: 1;
//     }
//   }

  // & > div {
  //   height: calc(100% - 120px);
  // }
// }
</style>
<style >
.wrapper {
  width: 100%;
  height: 100%;
  font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB",
    "Microsoft YaHei", Arial, sans-serif !important;
  /* overflow: hidden; */
}
.content-box {
  /* min-width: 1270px; */
  position: absolute;
  left: 210px;
  right: 0;
  top:0;
  bottom: 0;
  -webkit-transition: left 0.3s ease-in-out;
  transition: left 0.3s ease-in-out;
  background: #f0f0f0;
}

.content {
  width: auto;
  height: 100%;
  padding: 0px;
  overflow-y: auto;
  box-sizing: border-box;
}

.content-collapse {
  left: 65px;
}
</style>