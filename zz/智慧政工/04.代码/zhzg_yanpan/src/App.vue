<template>
  <div id="app">
    <router-view />
  </div>
</template>
<script>
import Watermark from '@/utils/waterMark';

export default {
  name: 'App',
  mounted() {
    Watermark.set('南京市公安局');
  }
};
</script>
<style lang="scss">
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body,
html {
  width: 100%;
  height: 100%;
  overflow: auto;
  min-width: 1920px;
  min-height: 1080px;
  font-family: '微软雅黑';
}

::-webkit-scrollbar {
  /*滚动条整体样式*/
  width: 9px; /*高宽分别对应横竖滚动条的尺寸*/
  height: 9px;
}

::-webkit-scrollbar-thumb {
  /*滚动条里面小方块*/
  border-radius: 10px;
  -webkit-box-shadow: inset 0 0 10px rgba(0, 0, 0, 0.2);
  background: rgba(0, 0, 0, 0.2);
}

::-webkit-scrollbar-track {
  /*滚动条里面轨道*/
  -webkit-box-shadow: inset 0 0 10px rgba(0, 0, 0, 0.2);
  border-radius: 0;
  background: rgba(0, 0, 0, 0.1);
}

#app {
  width: 100%;
  height: 100%;
  text-align: center;
  overflow: hidden;
}

a {
  text-decoration: none;
}
ul,
li {
  list-style: none;
}
.content-box {
  position: absolute;
  left: 200px;
  right: 0;
  top: 60px;
  bottom: 0;
  -webkit-transition: left 0.3s ease-in-out;
  transition: left 0.3s ease-in-out;
  background: #f0f0f0;
}

.content {
  width: auto;
  height: 100%;
  padding: 10px 10px;
  overflow-y: scroll;
  box-sizing: border-box;
}

.content-collapse {
  left: 65px;
}

.container {
  background: #fff;
  border-radius: 5px;
  position: relative;
  z-index: 99;
}

.crumbs {
  margin-bottom: 10px;
}

.pagination {
  margin: 20px 0;
  text-align: right;
}

.plugins-tips {
  padding: 20px 10px;
  margin-bottom: 20px;
}

.el-button + .el-tooltip {
  margin-left: 10px;
}

.el-table tr:hover {
  background: #f6faff;
}

.mgb20 {
  margin-bottom: 20px;
}

.move-enter-active,
.move-leave-active {
  transition: opacity 0.5s;
}

.move-enter,
.move-leave {
  opacity: 0;
}

/*BaseForm*/

.form-box {
  width: 600px;
}

.form-box .line {
  text-align: center;
}

.el-time-panel__content::after,
.el-time-panel__content::before {
  margin-top: -7px;
}

.el-time-spinner__wrapper
  .el-scrollbar__wrap:not(.el-scrollbar__wrap--hidden-default) {
  padding-bottom: 0;
}

/*Upload*/

.pure-button {
  width: 150px;
  height: 40px;
  line-height: 40px;
  text-align: center;
  color: #fff;
  border-radius: 3px;
}

.g-core-image-corp-container .info-aside {
  height: 45px;
}
.el-upload--text .el-icon-upload {
  font-size: 67px;
  color: #97a8be;
  margin: 40px 0 16px;
  line-height: 50px;
}
.el-upload--text {
  color: #97a8be;
  font-size: 14px;
  text-align: center;
}

.el-upload--text em {
  font-style: normal;
}

/*VueEditor*/

.ql-container {
  min-height: 400px;
}

.ql-snow .ql-tooltip {
  transform: translateX(117.5px) translateY(10px) !important;
}

.editor-btn {
  margin-top: 20px;
}

/*markdown*/

.v-note-wrapper .v-note-panel {
  min-height: 500px;
}
.el-footer {
  width: 100%;
  position: absolute;
  width: 100%;
  text-align: center;
  height: 40px;
  bottom: 0px;
}
.content-center {
  display: flex;
  align-items: center;
  justify-content: center;
}
.white-bag {
  background-color: #fff;
  border-radius: 4px;
}
img {
  vertical-align: middle;
}
.el-tree {
  font-size: 14px;
  color: #000000;
}
.tree-icon {
  display: inline-block;
}

input:focus::-webkit-input-placeholder {
  color: transparent !important;
}

.pop-mask {
  position: fixed;
  width: 100%;
  height: 100%;
  background-color: #7f7f7f;
  opacity: 0.8;
  z-index: 999;
  top: 0;
  left: 0;
}

.no-data {
  width: 217px;
  height: 192px;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  background-image: url(./assets/image/teamAnalysis/developEffect/no-data.png);
  background-size: 100% 100%;
}
</style>
