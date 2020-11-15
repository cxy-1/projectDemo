<template>
    <div class="header">
        <div class="head2" :style="{width:headWidth,left:headLeft,transition:headTrans}">
            <!-- 折叠按钮 -->
            <div class="collapse-btn" @click="collapseChage">
                <i :class="collapse?'el-la1':'el-la2'"></i>
            </div>
             <!--<div class="linkManage">
                    <router-link to="/indexManage">
                        管理首页
                    </router-link>
                </div>  -->
            <div class="header-right">
                <div class="header-user-con">
                    <!-- 消息中心 -->
                    <div class="btn-bell">
                         <!--<el-badge :value="message" class="item">-->
                         <el-badge class="item" :value="message" :max="99">
                             <!-- <el-tooltip effect="dark" :content="message?`有${message}条未读消息`:`消息中心`" placement="bottom"> -->
                             <el-tooltip effect="dark" content="消息中心" placement="bottom">
                                <router-link to="/tabs">
                                    <i class="iconfont iconweidu" style="font-size:27px;color:#606266;position:relative;bottom:3px"></i>
                                    <!--<template v-if="message>0">
                                        <i class="iconfont iconweidu" style="font-size:27px;color:#606266;position:relative;bottom:3px"></i>
                                    </template>
                                    <template v-else>
                                        <i class="iconfont iconyidu" style="font-size:27px;color:#606266;position:relative;bottom:3px"></i>
                                    </template>-->
                                </router-link>
                            </el-tooltip>
                            <span class="btn-bell-badge" v-if="message"></span> 
                         </el-badge> 
                    </div>
                    <!--宝典图标  -->
                    <div class="btn-bell">
                        <el-tooltip effect="dark" content="规范宝典" placement="bottom">
                            <router-link to="/regBox">
                                <i class="iconfont iconzidianguanli" style="font-size:19px;color:#606266;"></i>
                            </router-link>
                        </el-tooltip>
                    </div>
                    <div class="btn-bell">
                        <el-tooltip effect="dark" content="在线表格" placement="bottom">
                            <router-link to="/onlineTable">
                                <i class="iconfont iconbiaoge" style="font-size:22px;color:#606266;"></i>
                            </router-link>
                        </el-tooltip>
                    </div>
                    <!-- 帮助文档 -->
                    <!-- <div class="btn-help">
                        <el-tooltip effect="dark" :content="`帮助文档`" placement="bottom">
                            <router-link to="/tabs">
                                <i class="el-icon-document"></i>
                            </router-link>
                        </el-tooltip>
                    </div> -->
                    <!-- 全屏显示 -->
                    <!-- <div class="btn-fullscreen" @click="handleFullScreen">
                        <el-tooltip effect="dark" :content="fullscreen?`取消全屏`:`全屏`" placement="bottom">
                            <i class="iconfont iconquanpingzuidahua" style="font-size:18px;"></i>
                        </el-tooltip>
                    </div> -->

                    <div class="standard">
                        <el-tooltip effect="dark" placement="bottom">
                            <i class="iconfont iconStandard" style="font-size:18px;"></i>
                        </el-tooltip>
                    </div>
                    <!-- 用户头像 -->
                     <div class="user-avator" style=""><img :src="require('static/img/index/head.png')"></div> 
                    <!-- 用户名下拉菜单 -->
                    <el-dropdown class="user-name" trigger="click" @command="handleCommand">
                        <span class="el-dropdown-link">{{username}}
                            <i class="el-icon-caret-bottom"></i>
                        </span>
                        <el-dropdown-menu slot="dropdown">
                            <!-- <a href="http://blog.gdfengshuo.com/about/" target="_blank">
                                    <el-dropdown-item>关于作者</el-dropdown-item>
                                </a>
                                <a href="https://github.com/lin-xin/vue-manage-system" target="_blank">
                                    <el-dropdown-item>项目仓库</el-dropdown-item>
                                </a> -->
                            <el-dropdown-item divided command="loginout">退出登录</el-dropdown-item>
                        </el-dropdown-menu>
                    </el-dropdown>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
import { communication } from "../../data/getAxios";
import bus from "../common/bus";
export default {
  data() {
    return {
      username: "",
      collapse: false,
      fullscreen: false,
      name: "linxin",
      headWidth: "",
      headTrans: "",
      headLeft: "",
      message: "",
      messages: "/home2"
    };
  },
  created() {
    this.init();
    this.gettabInfo();
  },
  watch: {
    //使用这个可以监听路由中指定数据（路径）的变化,当指定的数据变化了时触发watch中对应的function的处理
    $route(newValue, oldValue) {
      if (newValue.fullPath == this.messages) {
        this.messages = newValue.fullPath;
      } else {
        this.gettabInfo();
        this.messages = newValue.fullPath;
      }
    }
  },
  computed: {},
  methods: {
    gettabInfo() {
      //待办数据
      const objs = {
        url:
          this.global.serverPath8 +
          "/flow/message/getMessageSum?personId=" +
          localStorage.getItem("loginpersonId"),
        isGet: true
      };
      communication(objs).then(res => {
        this.message = res.data;
      });
      const obj = {
        url: this.global.serverPath8 + "/message/message/list",
        isGet: true
      };
      communication(obj).then(res => {
        this.news = res.result;
      });
    },
    init() {
      var self = this;
      const _obj = {
        url: self.global.serverPath8 + "/personnel/overview/getUserName",
        isGet: true
      };
      communication(_obj).then(
        res => {
          if (res.code == 0) {
            self.username = res.data;
          }
        },
        function(res) {
          console.log("获取姓名信息失败！");
        }
      );
    },
    // 用户名下拉菜单选择事件
    handleCommand(command) {
      if (command == "loginout") {
        localStorage.removeItem("jc_name");
        // 清空缓存
        localStorage.removeItem("setToken");
        localStorage.removeItem("loginUser");
        localStorage.removeItem("loginpersonId");
        localStorage.removeItem("loginDeptId");
        localStorage.removeItem("loginCompanyId");
        localStorage.removeItem("loginPhone");
        localStorage.removeItem("idCard");
        localStorage.removeItem("policeNo");
        this.$router.push("/login");
        const obj = {
          url: this.global.serverPath8 + "/user-mgmt/logout",
          postData: {}
        };
        communication(obj).then(res => {
          if (res.code == 0) {
          } else {
            this.$message.error("系统错误");
          }
        });
      }
    },
    // 侧边栏折叠
    collapseChage() {
      this.collapse = !this.collapse;
      if (this.collapse) {
        this.headWidth = "calc(100% - 65px)";
        this.headLeft = "65px";
        this.headTrans = "left .2s";
      } else {
        this.headWidth = "calc(100% - 210px)";
        this.headLeft = "210px";
        this.headTrans = "left .2s";
      }
      bus.$emit("collapse", this.collapse);
    },
    // 全屏事件
    handleFullScreen() {
      let element = document.documentElement;
      if (this.fullscreen) {
        if (document.exitFullscreen) {
          document.exitFullscreen();
        } else if (document.webkitCancelFullScreen) {
          document.webkitCancelFullScreen();
        } else if (document.mozCancelFullScreen) {
          document.mozCancelFullScreen();
        } else if (document.msExitFullscreen) {
          document.msExitFullscreen();
        }
      } else {
        if (element.requestFullscreen) {
          element.requestFullscreen();
        } else if (element.webkitRequestFullScreen) {
          element.webkitRequestFullScreen();
        } else if (element.mozRequestFullScreen) {
          element.mozRequestFullScreen();
        } else if (element.msRequestFullscreen) {
          // IE11
          element.msRequestFullscreen();
        }
      }
      this.fullscreen = !this.fullscreen;
    }
  },
  mounted() {
    $(".el-la1").css({
      "background-image":
        "url(" + require("../../../static/img/outdent.png") + ")",
      "background-size": "100% 100%"
    });
    $(".el-la2").css({
      "background-image":
        "url(" + require("../../../static/img/outdent.png") + ")",
      "background-size": "100% 100%"
    });
    if (this.collapse) {
      this.headWidth = "calc(100% - 65px)";
      this.headLeft = "65px";
      this.headTrans = "left .2s";
    } else {
      this.headWidth = "calc(100% - 210px)";
      this.headLeft = "210px";
      this.headTrans = "left .2s";
    }
    if (document.body.clientWidth < 1500) {
      this.collapseChage();
    }
  }
};
</script>
<style scoped>
.linkManage {
  float: left;
  margin-top: 11px;
  margin-left: 6px;
}
.linkManage > a {
  color: #409eff;
  /**color:rgb(96, 98, 102);*/
  font-size: 14px;
}
.el-la1 {
  width: 18px;
  height: 16px;
  display: inline-block;
  transform: rotate(0deg);
}
.el-la2 {
  width: 18px;
  height: 16px;
  display: inline-block;
  transform: rotate(180deg);
}
.header {
  position: relative;
  box-sizing: border-box;
  /* width: calc(100% - 200px); */
  height: 50px;
  font-size: 22px;
  width: 100%;
  /* -webkit-transition: width .3s; */
  /* transition: width .3s; */
  /* left: 200px; */
  color: #fff;
  background: #fff;
}

.head2 {
  position: relative;
  box-sizing: border-box;
  height: 50px;
  font-size: 22px;
  color: #606266;
  border-bottom: solid 1px #e6e6e6;
}

.collapse-btn {
  float: left;
  padding: 0 21px;
  cursor: pointer;
  line-height: 50px;
}

.header .logo {
  float: left;
  text-align: center;
  line-height: 92px;
  margin-left: 20px;
}

.header-right {
  float: right;
  padding-right: 25px;
}

.header-user-con {
  display: flex;
  height: 60px;
  align-items: center;
}

.btn-fullscreen {
  transform: rotate(45deg);
  margin-right: 5px;
  font-size: 24px;
}

.btn-bell,
.btn-fullscreen,
.btn-help {
  position: relative;
  width: 40px;
  height: 30px;
  text-align: center;
  border-radius: 15px;
  cursor: pointer;
}

.btn-help .el-tooltip {
  color: #606266;
  display: inline-block;
  margin: 2px 0 0 10px;
}

/**.btn-bell-badge {
    position: absolute;
    right: 0;
    top: -2px;
    width: 8px;
    height: 8px;
    border-radius: 4px;
    background: #f56c6c;
    color: #fff;
}*/

.btn-bell .el-icon-bell {
  color: #606266;
}

.user-name {
  margin-left: 10px;
}

.user-avator {
  margin-left: 10px;
  margin-bottom: 5px;
}

.user-avator img {
  display: block;
  width: 38px;
  height: 38px;
  border-radius: 50%;
}

.header-right .el-dropdown-link {
  color: #606266;
  cursor: pointer;
}

.header-right .el-dropdown-menu__item {
  text-align: center;
}
</style>
<style>
.header-right .el-badge__content {
  background-color: #f56c6c;
  border-radius: 10px;
  color: transparent;
  display: inline-block;
  font-size: 12px;
  height: 15px;
  width: 20px;
  overflow: hidden;
  line-height: 18px;
  padding: 1px 1px;
  text-align: center;
  white-space: nowrap;
  border: 1px solid #fff;
  color: #fff;
}
</style>
