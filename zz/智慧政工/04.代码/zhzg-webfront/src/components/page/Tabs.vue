<template>
    <div class="xxtz">
        <el-card class="card2 tabCard" ref="tabCard" style="position:relative">
            <el-button type="text" class="backBtn" icon="el-icon-d-arrow-left" @click="backBtn">返回</el-button>
            <el-tabs v-model="message">
                <el-tab-pane name="first" :label="`消息(${deal.length})`">
                    <el-table :data="deal" :show-header="false" style="width: 100%" @row-click="openDetails">
                        <el-table-column prop="imgPath" label="图片路径" sortable width="48px">
                            <template slot-scope="scope">
                                <img :src="scope.row.status == 0?require('static/img/db.png'):require('static/img/db2.png')" alt="" style="width: 32px;height: 32px">
                            </template>
                        </el-table-column>
                        <el-table-column prop="content"></el-table-column>
                        <el-table-column prop="createTime" width="170"></el-table-column>
                    </el-table>
                </el-tab-pane>
                <el-tab-pane :label="`提醒(${news.length})`" name="second">
                    <template v-if="message === 'second'">
                        <el-table @row-click="handClick" :data="news" :show-header="false" style="width: 100%">
                            <el-table-column prop="imgPath" label="图片路径" sortable width="48px">
                                <template slot-scope="scope">
                                    <img :src="scope.row.status==0?require('static/img/xx.png'):require('static/img/xx2.png')" alt="" style="width: 32px;height: 32px">
                                </template>
                            </el-table-column>
                            <el-table-column prop="content"></el-table-column>
                            <el-table-column prop="createTime" align="right"></el-table-column>
                        </el-table>
                    </template>
                </el-tab-pane>
                <div class="special">
                    <el-dialog  :visible.sync="birthday" :close-on-click-modal="false" width="750px">
                        <div style="height:420px;position: relative;" :style="{backgroundImage:'url('+require('static/img/birthday.png')+')'}">
                            <div @click="birthday=false;" style="position: absolute;top: 14px;right: 20px;width: 22px;height: 22px;"></div>
                            <div style="position: absolute;top: 190px;left: 48px;width: 278px;height: 150px;">
                                <div style="margin-bottom: 20px;" v-if="selfmessage == false">
                                    <span style="font-size: 21px;font-weight: bold;">{{biruser}}</span>
                                    <span style="font-size: 20px;">同志：</span>
                                </div>
                                <div v-if="selfmessage == false">
                                    <p style="text-indent: 40px;line-height: 28px;font-size: 16px;">感谢您对公安工作的辛勤付出，祝您生日快乐，阖家幸福！</p>
                                </div>
                                <div style="margin-bottom: 20px;margin-top: 10px;" v-if="selfmessage == true">
                                    <span style="text-indent: 40px;line-height: 28px;font-size: 16px;">{{contentes}}</span>
                                </div>
                                <div style="position: absolute;right: 5px;bottom: 5px;font-size: 16px;">
                                    <p>{{birdate}}</p>
                                </div>
                            </div>
                        </div>
                    </el-dialog>
                    <el-dialog  :visible.sync="passport" :close-on-click-modal="false" width="391px">
                        <div style="height:377px;position: relative;" :style="{backgroundImage:'url('+require('static/img/passpost_bg.png')+')'}">
                            <p style="font-size: 20px;width: 377px;position: absolute;text-align: center;top: 176px;">{{zjmc}}</p>
                            <p style="font-size:20px;width:377px;position:absolute;text-align: center;top: 220px;">即将到期</p>
                            <p style="color: rgba(0,0,0,0.6);font-size:16px;width:377px;position:absolute;text-align: center;top: 266px;">请您及时更换证件，以免耽误您的出行</p>
                            <el-button @click="passport=false" style="position:absolute;top: 320px;left: 121px;width: 136px;" type="primary" round>我知道了</el-button>
                        </div>

                    </el-dialog>
                </div>
                <el-tab-pane :label="`通知(${notice.length})`" name="third">
                    <template v-if="message === 'third'">
                        <el-table :data="notice" :show-header="false" style="width: 100%;cursor:pointer;">
                            <el-table-column prop="imgPath" label="图片路径" sortable width="48px">
                                <template slot-scope="scope">
                                    <img :src="require('static/img/tz.png')" alt="" style="width: 32px;height: 32px">
                                </template>
                            </el-table-column>
                            <el-table-column prop="info"></el-table-column>
                            <el-table-column prop="date" width="170"></el-table-column>
                        </el-table>
                    </template>
                </el-tab-pane>
            </el-tabs>
        </el-card>
    </div>
</template>

<script>
import bus from "../common/bus"; //非父子组件之间传值
import { communication } from "../../data/getAxios";
import crypto from "crypto";

export default {
  name: "tabs",
  data() {
    return {
      passport: false,
      birthday: false,
      selfmessage: false,
      contentes: "",
      message: "first",
      showHeader: false,
      tz_cur_page: 1,
      tz_pagesize: 10,
      tz_data_length: 0,
      xx_cur_page: 1,
      xx_pagesize: 10,
      xx_data_length: 0,
      db_cur_page: 1,
      db_pagesize: 10,
      db_data_length: 0,
      notice: [],
      news: [],
      deal: [],
      biruser: "",
      birdate: "",
      zjmc: ""
    };
  },
  created() {},
  methods: {
    // 返回上一页
    backBtn() {
      this.$router.go(-1);
    },
    gettabInfo() {
      //待办数据
      const _obj = {
        url: this.global.serverPath8 + "/flow/message/getAllMessage",
        isGet: true
      };
      communication(_obj).then(res => {
        this.deal = res.data.content;
      });
      const obj = {
        url: this.global.serverPath8 + "/message/message/list",
        isGet: true
      };
      communication(obj).then(res => {
        this.news = res.result;
      });
    },
    openDetails(row) {
      const obj = {
        url:
          this.global.serverPath8 + "/flow/message/getMessageInfo?id=" + row.id,
        isGet: true
      };
      communication(obj).then(res => {
        if (res.data.type == "请销假") {
          if (res.data.terminateStatus == 1) {
            if (res.data.toDoStatus == true) {
              //true要审批  false直接返回
              this.$router.push({
                path: "/LeaExamApproval",
                query: { btnTypeNum: 1, alarmNo: row.formId, fid: row.fid }
              }); //跳转到详情页面
            } else {
              this.$router.push({
                path: "/LeaExamApproval",
                query: { btnTypeNum: 0, alarmNo: row.formId, fid: row.fid }
              }); //跳转到详情页面
            }
          } else if (res.data.terminateStatus == 2) {
            if (res.data.toDoStatus == true) {
              //true要销假  false直接返回
              this.$router.push({
                path: "/resumptionLeave",
                query: { btnTypeNum: 2, alarmNo: row.formId, fid: row.fid }
              }); //跳转到详情页面
            } else {
              this.$router.push({
                path: "/LeaExamApproval",
                query: { btnTypeNum: 0, alarmNo: row.formId, fid: row.fid }
              }); //跳转到详情页面
            }
          } else {
            this.$router.push({
              path: "/LeaExamApproval",
              query: { btnTypeNum: 0, alarmNo: row.formId, fid: row.fid }
            }); //跳转到详情页面
          }
        } else if (res.data.type == "出国境") {
          //1 审批   2销假   0结束 -1
          if (res.data.terminateStatus == 1) {
            if (res.data.toDoStatus == true) {
              //true要审批  false直接返回
              this.$router.push({
                path: "/dashboard3",
                query: { btnTypeNum: 1, alarmNo: row.formId, fid: row.fid }
              }); //跳转到详情页面
            } else {
              this.$router.push({
                path: "/dashboard3",
                query: { btnTypeNum: 0, alarmNo: row.formId, fid: row.fid }
              }); //跳转到详情页面
            }
          } else if (res.data.terminateStatus == 2) {
            if (res.data.toDoStatus == true) {
              //true要销假  false直接返回
              this.$router.push({
                path: "/dashboard5",
                query: { btnTypeNum: 2, alarmNo: row.formId, fid: row.fid }
              }); //跳转到详情页面
            } else {
              this.$router.push({
                path: "/dashboard3",
                query: { btnTypeNum: 0, alarmNo: row.formId, fid: row.fid }
              }); //跳转到详情页面
            }
          } else {
            this.$router.push({
              path: "/dashboard3",
              query: { btnTypeNum: 0, fid: row.fid, alarmNo: row.formId }
            }); //跳转到详情页面
          }
        }
      });
    },
    // 表格分页导航
    handleCurrentChange(val) {
      // this.loading = true;
      this.tz_cur_page = val;
      // this.getTabData();
    },
    handlenews(index) {
      const item = this.notice.splice(index, 1);
      this.news = item.concat(this.news);
    },
    handleDel(index) {
      // const item = this.news.splice(index, 1);
      // this.deal = item.concat(this.deal);
    },
    handleRestore(index) {
      const item = this.deal.splice(index, 1);
      this.news = item.concat(this.news);
    },
    handClick(row, column, event) {
      if (row.type == "生日提醒") {
        this.biruser = localStorage.getItem("loginUser");
        this.birdate = row.createTime.split(" ")[0];
        console.log(row.content);
        this.contentes = row.content;
        let contents = row.content.split("，");
        console.log(contents);
        if (contents[1] == "请为他/她送上最好的祝福吧！") {
          this.selfmessage = true;
        } else {
          this.selfmessage = false;
        }
        this.birthday = true;
      } else if (row.type == "护照提醒") {
        this.passport = true;
        this.zjmc = row.zjmc;
      }
      if (row.status == 0) {
        const obj = {
          url: this.global.serverPath8 + "/message/message/changeStatus",
          postData: {
            id: row.id
          }
        };
        communication(obj).then(res => {
          if (res.code == 0) {
            row.status = 1;
          }
        });
      }
    }
  },
  mounted() {
    this.gettabInfo(); //待办
  },
  computed: {
    noticeNum() {
      // return this.notice.length;
    }
  }
};
</script>

<style scoped>
.backBtn {
  position: absolute;
  right: 30px;
  z-index: 99;
  font-size: 14px !important;
}
.pagination .el-button--small,
.pagination .el-button--small.is-round {
  position: absolute;
  left: 10px;
}
.card2 {
  margin: 20px;
}

.message-title {
  cursor: pointer;
}

.handle-row {
  margin-top: 30px;
}
</style>
<style>
.special .el-dialog {
  position: relative;
  margin: 0 auto 50px;
  border-radius: 2px;
  -webkit-box-shadow: none;
  box-shadow: none;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
  width: 50%;
  background: transparent;
}
.special .el-dialog__body {
  padding: 0;
}
.special .el-dialog__header {
  padding: 0;
  display: none;
}
.xxtz .el-table td,
.el-table th {
  font-size: 14px;
}

.xxtz .el-tabs__item {
  font-size: 16px !important;
  height: 46px;
}

.xxtz .el-tabs__active-bar {
  width: 76px !important;
  left: -10px;
}

.xxtz .el-tabs__nav-scroll {
  padding-left: 20px;
}
</style>


