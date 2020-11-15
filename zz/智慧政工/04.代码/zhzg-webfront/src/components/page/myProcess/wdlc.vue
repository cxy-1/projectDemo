<template>
    <div class="infoMenu">
        <el-card shadow="" class="mgb20">
            <div class="crumbs">
                <el-breadcrumb separator="/">
                    <el-breadcrumb-item>个人中心</el-breadcrumb-item>
                    <el-breadcrumb-item>我的流程</el-breadcrumb-item>
                </el-breadcrumb>
            </div>
        </el-card>
        <el-card style="margin:0 20px;clear:none;position:relative">
            <el-button type="text" :class="!showes?'backBtn1':'backBtn3'" icon="el-icon-set-up" @click="showes = false"></el-button>
            <el-button type="text" :class="showes?'backBtn':'backBtn2'" icon="el-icon-s-operation" @click="showes = true"></el-button>
            <el-tabs v-model="activeName" @tab-click="handleClick">
                <el-tab-pane :label="`待提交(${divBox1.length})`" name="first">
                    <div class="myProcess" style="padding:1%;overflow:hidden;overflow-y:auto">
                        <template>
                            <el-row :gutter="20">
                                <el-col :span="8" v-if="showes == false" v-for="(item,j) in divBox1" :key="j">
                                    <div class="dtjBox" style="margin-bottom:16px;">
                                        <div class="dtjTop">
                                            <template v-if="item.flag=='请销假'">
                                                <p><i class="iconfont iconrili"></i>&emsp;请销假</p>
                                            </template>
                                            <template v-if="item.flag=='出国境'">
                                                <p><i class="iconfont iconfeiji"></i>&emsp;出国境</p>
                                            </template>
                                            <template>
                                                <div class="dtjForm" @click="linkTo3(item)">
                                                    <el-form label-width="74px" ref="subForm">
                                                        <!-- <el-form-item label="申请单号：">
                                                            <span>{{item.applyNo}}</span>
                                                        </el-form-item> -->
                                                        <el-form-item label="申请时间：">
                                                            <span>{{item.submitDate}}</span>
                                                        </el-form-item>
                                                        <el-form-item :label="item.flag=='请销假'?'请假事由：':'离境原因：'">
                                                            <span>{{item.cause}}</span>
                                                        </el-form-item>
                                                        <el-form-item :label="item.flag=='请销假'?'请假日期：':'离境日期：'">
                                                            <span>{{item.leaveDate}}</span>
                                                        </el-form-item>
                                                    </el-form>
                                                    <div class="wdlcType">
                                                        <div>
                                                            <span>
                                                                {{item.statusName}}
                                                            </span>
                                                        </div>
                                                    </div>
                                                </div>
                                            </template>
                                        </div>
                                    </div>
                                </el-col>
                                <el-col :span="8" v-if="showes == true">
                                    <el-table :data="divBox1" :show-header="false" style="width: 100%" @row-click="linkTo3">
                                        <el-table-column prop="" label="" sortable width="125px">
                                            <template slot-scope="scope">
                                                <div class="dtjBox1">
                                                    <p v-if="scope.row.flag=='请销假'" ><i class="iconfont iconrili"></i>&emsp;请销假</p>
                                                    <p v-if="scope.row.flag=='出国境'" ><i class="iconfont iconfeiji"></i>&emsp;出国境</p>
                                                </div>
                                            </template>
                                        </el-table-column>
                                        <el-table-column prop="cause">
                                            <template slot-scope="scope">
                                                <div v-if="scope.row.flag=='请销假'" >请假事由：{{scope.row.cause}}</div>
                                                <div v-if="scope.row.flag=='出国境'" >离境原因：{{scope.row.cause}}</div>
                                            </template>
                                        </el-table-column>
                                        <el-table-column prop="submitDate">
                                            <template slot-scope="scope">申请时间：{{scope.row.submitDate}}</template>
                                        </el-table-column>
                                        <el-table-column prop="leaveDate" align="right">
                                            <template slot-scope="scope">
                                                <div v-if="scope.row.flag=='请销假'" >请假日期：{{scope.row.leaveDate}}</div>
                                                <div v-if="scope.row.flag=='出国境'" >离境日期：{{scope.row.leaveDate}}</div>
                                            </template>
                                        </el-table-column>
                                    </el-table>
                                </el-col>
                                <el-col :span="8"></el-col>
                                <el-col :span="8"></el-col>
                            </el-row>
                        </template>
                        <!-- 提交提示框 -->
                        <el-dialog title="提示" :visible.sync="referTo" width="300px" center>
                            <div class="del-dialog-cnt" style="text-align:center;"><i class="el-icon-success" style="color:#67c23a;font-size:24px;"></i>&nbsp; 您已成功提交！</div>
                            <span slot="footer" class="dialog-footer">
                            <el-button type="primary" @click="referTo=false">确 定</el-button>
                        </span>
                        </el-dialog>
                    </div>
                </el-tab-pane>
                <el-tab-pane :label="`待审批(${divBox2.length})`" name="second">
                    <div class="myProcess" style="padding:1%;overflow:hidden;overflow-y:auto">
                        <template>
                            <el-row :gutter="20">
                                <el-col v-if="showes == false" :span="8" v-for="(item,j) in divBox2" :key="j">
                                    <div class="dtjBox" style="margin-bottom:16px;">
                                        <div class="dtjTop">
                                            <template v-if="item.flag=='请销假'">
                                                <p><i class="iconfont iconrili"></i>&emsp;请销假</p>
                                            </template>
                                            <template v-if="item.flag=='出国境'">
                                                <p><i class="iconfont iconfeiji"></i>&emsp;出国境</p>
                                            </template>
                                            <template>
                                                <div class="dtjForm" @click="linkTo(item)">
                                                    <el-form label-width="74px" ref="subForm">
                                                        <!-- <el-form-item label="申请单号：">
                                                            <span>{{item.applyNo}}</span>
                                                        </el-form-item> -->
                                                        <el-form-item label="申请时间：">
                                                            <span>{{item.submitDate}}</span>
                                                        </el-form-item>
                                                        <el-form-item :label="item.flag=='请销假'?'请假事由：':'离境原因：'">
                                                            <span>{{item.cause}}</span>
                                                        </el-form-item>
                                                        <el-form-item :label="item.flag=='请销假'?'请假日期：':'离境日期：'">
                                                            <span>{{item.leaveDate}}</span>
                                                        </el-form-item>
                                                    </el-form>
                                                    <div class="wdlcType">
                                                        <div>
                                                            <span>
                                                                {{item.statusName}}
                                                            </span>
                                                        </div>
                                                    </div>
                                                </div>
                                            </template>
                                        </div>
                                    </div>
                                </el-col>
                                <el-table v-if="showes == true" :data="divBox2" :show-header="false" style="width: 100%" @row-click="linkTo">
                                    <el-table-column prop="" label="" sortable width="125px">
                                        <template slot-scope="scope">
                                            <div class="dtjBox1">
                                                <p v-if="scope.row.flag=='请销假'" ><i class="iconfont iconrili"></i>&emsp;请销假</p>
                                                <p v-if="scope.row.flag=='出国境'" ><i class="iconfont iconfeiji"></i>&emsp;出国境</p>
                                            </div>
                                        </template>
                                    </el-table-column>
                                    <el-table-column prop="cause">
                                        <template slot-scope="scope">
                                            <div v-if="scope.row.flag=='请销假'" >请假事由：{{scope.row.cause}}</div>
                                            <div v-if="scope.row.flag=='出国境'" >离境原因：{{scope.row.cause}}</div>
                                        </template>
                                    </el-table-column>
                                    <el-table-column prop="submitDate">
                                        <template slot-scope="scope">申请时间：{{scope.row.submitDate}}</template>
                                    </el-table-column>
                                    <el-table-column prop="leaveDate" align="right">
                                        <template slot-scope="scope">
                                            <div v-if="scope.row.flag=='请销假'" >请假日期：{{scope.row.leaveDate}}</div>
                                            <div v-if="scope.row.flag=='出国境'" >离境日期：{{scope.row.leaveDate}}</div>
                                        </template>
                                    </el-table-column>
                                </el-table>
                            </el-row>
                        </template>
                    </div>
                </el-tab-pane>
                <el-tab-pane :label="`已审批(${divBox3.length})`" name="third">
                    <div class="myProcess" style="padding:1%;overflow:hidden;overflow-y:auto">
                        <template>
                            <el-row :gutter="20">
                                <el-col v-if="showes == false"  :span="8" v-for="(item,j) in divBox3" :key="j">
                                    <div class="dtjBox" style="margin-bottom:16px;">
                                        <div class="dtjTop">
                                            <template v-if="item.flag=='请销假'">
                                                <p><i class="iconfont iconrili"></i>&emsp;请销假</p>
                                            </template>
                                            <template v-if="item.flag=='出国境'">
                                                <p><i class="iconfont iconfeiji"></i>&emsp;出国境</p>
                                            </template>
                                            <template>
                                                <div class="dtjForm" @click="linkTo2(item)">
                                                    <el-form label-width="74px" ref="subForm">
                                                        <!-- <el-form-item label="申请单号：">
                                                            <span>{{item.applyNo}}</span>
                                                        </el-form-item> -->
                                                        <el-form-item label="申请时间：">
                                                            <span>{{item.submitDate}}</span>
                                                        </el-form-item>
                                                        <el-form-item :label="item.flag=='请销假'?'请假事由：':'离境原因：'">
                                                            <span>{{item.cause}}</span>
                                                        </el-form-item>
                                                        <el-form-item :label="item.flag=='请销假'?'请假日期：':'离境日期：'">
                                                            <span>{{item.leaveDate}}</span>
                                                        </el-form-item>
                                                    </el-form>
                                                    <div class="wdlcType">
                                                        <div>
                                                            <span>
                                                                {{item.statusName}}
                                                            </span>
                                                        </div>
                                                    </div>
                                                </div>
                                            </template>
                                        </div>
                                    </div>
                                </el-col>
                                 <el-table v-if="showes == true" :data="divBox3" :show-header="false" style="width: 100%" @row-click="linkTo2">
                                    <el-table-column prop="" label="" sortable width="125px">
                                        <template slot-scope="scope">
                                            <div class="dtjBox1">
                                                <p v-if="scope.row.flag=='请销假'" ><i class="iconfont iconrili"></i>&emsp;请销假</p>
                                                <p v-if="scope.row.flag=='出国境'" ><i class="iconfont iconfeiji"></i>&emsp;出国境</p>
                                            </div>
                                        </template>
                                    </el-table-column>
                                    <el-table-column prop="cause">
                                        <template slot-scope="scope">
                                            <div v-if="scope.row.flag=='请销假'" >请假事由：{{scope.row.cause}}</div>
                                            <div v-if="scope.row.flag=='出国境'" >离境原因：{{scope.row.cause}}</div>
                                        </template>
                                    </el-table-column>
                                    <el-table-column prop="submitDate">
                                        <template slot-scope="scope">申请时间：{{scope.row.submitDate}}</template>
                                    </el-table-column>
                                    <el-table-column prop="leaveDate" align="right">
                                        <template slot-scope="scope">
                                            <div v-if="scope.row.flag=='请销假'" >请假日期：{{scope.row.leaveDate}}</div>
                                            <div v-if="scope.row.flag=='出国境'" >离境日期：{{scope.row.leaveDate}}</div>
                                        </template>
                                    </el-table-column>
                                </el-table>
                            </el-row>
                        </template>
                    </div>
                </el-tab-pane>
            </el-tabs>
        </el-card>
    </div>
</template>
<script>
import { communication } from "../../../data/getAxios";
export default {
  data() {
    return {
      activeName: "first",
      dtjNum: "0",
      dspNum: "0",
      yspNum: "0",
      referTo: false, //提交提示框
      showes: false,
      divBox1: [],
      divBox2: [],
      data2: "", //用于向父组件传值
      divBox3: []
    };
  },
  created() {},
  methods: {
    handleClick(tab, event) {},
    linkTo3(item) {
      if (item.flag == "出国境") {
        //出国境申请详情
        this.$router.push({ path: "/dashboard2" }); //跳转到详情页面
      } else {
        //请销假申请
        this.$router.push({ path: "/LeaveManage" });
      }
    },
    //编辑按钮
    handleEdit(index) {
      var self = this;
      if (self.divBox1[index].title == "goAbroad") {
        //出国境
        self.$http
          .get(
            self.global.serverPath4 +
              "/zhzg/goAbroad/queryGoAbroadDetailInfoByApplicationFormId",
            {
              params: {
                applicationFormId: self.divBox1[index].subs[0].formInput1 //申请单号
              }
            }
          )
          .then(
            function(res) {
              self.$router.push({
                path: "/dashboard3",
                query: {
                  show1: true,
                  show2: false,
                  info: res.body.rows,
                  exist: true
                }
              }); //跳转到详情页面
            },
            function(res) {
              console.log("请求失败！！！！！");
            }
          );
      } else {
        //请销假
        // self.$http.get(self.global.serverPath2 +'/zhzg/PleaseSellOff/approvalList', {
        self.$router.push({
          path: "/LeaExamApproval",
          query: {
            alarmNo: self.divBox1[index].subs[0].formInput1,
            show1: false,
            show2: true
          }
        });
      }
    },
    // 提交按钮
    referBtn(index) {
      this.referTo = true;
    },
    //删除按钮
    handleDel(index) {
      var self = this;
      self.divBox1.splice(index, 1);
    },
    //获取数据
    getCardData() {
      var self = this;
      const obj = {
        url: self.global.serverPath8 + "/flow/center/getCenterInfo",
        isGet: true
      };
      communication(obj).then(res => {
        self.divBox1 = res.data.toBesubmitted;
        self.divBox2 = res.data.ToBeExamined;
        self.divBox3 = res.data.Approved;
      });
    },
    //待审批
    linkTo(item) {
      if (item.flag == "出国境") {
        //出国境申请详情
        if (item.statusName == "待销假") {
          this.$router.push({
            path: "/dashboard5",
            query: {
              btnTypeNum: 2,
              alarmNo: item.applyNo,
              num: "1",
              fid: item.fid,
              workId: item.workId
            }
          }); //跳转到详情页面
        } else {
          this.$router.push({
            path: "/dashboard3",
            query: {
              btnTypeNum: 0,
              alarmNo: item.applyNo,
              num: "1",
              fid: item.fid,
              workId: item.workId
            }
          }); //跳转到详情页面
        }
      } else {
        //请销假申请详情
        if (item.statusName == "待销假") {
          //true要销假  false直接返回
          this.$router.push({
            path: "/resumptionLeave",
            query: {
              btnTypeNum: 2,
              alarmNo: item.applyNo,
              fid: item.fid,
              workId: item.workId
            }
          }); //跳转到详情页面
        } else {
          this.$router.push({
            path: "/LeaExamApproval",
            query: {
              btnTypeNum: 0,
              alarmNo: item.applyNo,
              fid: item.fid,
              workId: item.workId
            }
          }); //跳转到详情页面
        }
      }
    },
    //已审批
    linkTo2(item) {
      if (item.flag == "出国境") {
        //出国境申请详情
        this.$router.push({
          path: "/dashboard3",
          query: {
            btnTypeNum: 0,
            alarmNo: item.applyNo,
            num: "1",
            fid: item.fid
          }
        }); //跳转到详情页面
      } else {
        //请销假申请详情
        this.$router.push({
          path: "/LeaExamApproval",
          query: { btnTypeNum: 0, alarmNo: item.applyNo, fid: item.fid }
        });
      }
    },
    inquiry() {
      var self = this;
      self.$http
        .get(
          self.global.serverPath4 +
            "/process_engine/Personal/queryAllFormsByPending",
          {
            //传递参数
            params: {
              personId: localStorage.getItem("loginpersonId"),
              flag: 0
            }
          }
        )
        .then(
          function(res) {
            var obj1 = {},
              obj2 = {},
              addArr1 = [],
              addArr2 = [],
              addSubs2 = {},
              addSubs1 = {},
              titArr1 = [],
              titArr2 = [];
            if (res.body.msg) {
              if (res.body.bsGoAbroadList.length > 0) {
                //出国境
                var cgj = res.body.bsGoAbroadList;
                for (var i = 0; i < cgj.length; i++) {
                  addSubs1 = {};
                  titArr1.push(res.body.bsGoAbroadList[i].flag);
                  addSubs1.formInput1 = cgj[i].applicationFormId;
                  addSubs1.formInput2 = cgj[i].departureReason;
                  addSubs1.formInput3 =
                    cgj[i].startTime + "至" + cgj[i].endTime;
                  addSubs1.formInput4 = cgj[i].applicationTime;
                  addArr1.push(addSubs1);
                }
                for (var k = 0; k < titArr1.length; k++) {
                  var arr = [];
                  obj1[k] = {};
                  obj1[k].title = titArr1[k];
                  obj1[k].subs = arr;
                  obj1[k].subs.push(addArr1[k]);
                  self.divBox2.push(obj1[k]);
                }
              }
              if (res.body.pleSeOffFormInfos.length > 0) {
                //请销假
                var qxj = res.body.pleSeOffFormInfos;
                for (var i = 0; i < qxj.length; i++) {
                  addSubs2 = {};
                  titArr2.push(res.body.pleSeOffFormInfos[i].flag);
                  addSubs2.formInput1 = qxj[i].applyFormId;
                  addSubs2.formInput2 = qxj[i].applyType;
                  addSubs2.formInput3 =
                    qxj[i].beginTime + "至" + qxj[i].endTime;
                  addSubs2.formInput4 = qxj[i].applicantTime;
                  addArr2.push(addSubs2);
                }
                for (var k = 0; k < titArr2.length; k++) {
                  var arr = [];
                  obj2[k] = {};
                  obj2[k].title = titArr2[k];
                  obj2[k].subs = arr;
                  obj2[k].subs.push(addArr2[k]);
                  self.divBox2.push(obj2[k]);
                }
              }
              self.dspNum = self.divBox2.length;
            }
            // else {
            //     console.log('暂无待审批数据！');
            // }
          },
          function(res) {
            self.$message.error("请求失败");
          }
        );
    },

    inquiry2() {
      var self = this;
      self.$http
        .get(
          self.global.serverPath4 +
            "/process_engine/Personal/queryAllFormsByPending",
          {
            //传递参数
            params: {
              personId: localStorage.getItem("loginpersonId"),
              flag: 1
            }
          }
        )
        .then(
          function(res) {
            var obj1 = {},
              obj2 = {},
              addArr1 = [],
              addArr2 = [],
              addSubs2 = {},
              addSubs1 = {},
              titArr1 = [],
              titArr2 = [];
            if (res.body.msg) {
              if (res.body.bsGoAbroadList.length > 0) {
                //出国境
                var cgj = res.body.bsGoAbroadList;
                for (var i = 0; i < cgj.length; i++) {
                  addSubs1 = {};
                  titArr1.push(res.body.bsGoAbroadList[i].flag);
                  addSubs1.formInput1 = cgj[i].applicationFormId;
                  addSubs1.formInput2 = cgj[i].departureReason;
                  addSubs1.formInput3 =
                    cgj[i].startTime + "至" + cgj[i].endTime;
                  addSubs1.formInput4 = cgj[i].applicationTime;
                  addArr1.push(addSubs1);
                }
                for (var k = 0; k < titArr1.length; k++) {
                  var arr = [];
                  obj1[k] = {};
                  obj1[k].title = titArr1[k];
                  obj1[k].subs = arr;
                  obj1[k].subs.push(addArr1[k]);
                  self.divBox3.push(obj1[k]);
                }
              }
              if (res.body.pleSeOffFormInfos.length > 0) {
                //请销假
                var qxj = res.body.pleSeOffFormInfos;
                for (var i = 0; i < qxj.length; i++) {
                  addSubs2 = {};
                  titArr2.push(res.body.pleSeOffFormInfos[i].flag);
                  addSubs2.formInput1 = qxj[i].applyFormId;
                  addSubs2.formInput2 = qxj[i].applyType;
                  addSubs2.formInput3 =
                    qxj[i].beginTime + "至" + qxj[i].endTime;
                  addSubs2.formInput4 = qxj[i].applicantTime;
                  addArr2.push(addSubs2);
                }
                for (var k = 0; k < titArr2.length; k++) {
                  var arr = [];
                  obj2[k] = {};
                  obj2[k].title = titArr2[k];
                  obj2[k].subs = arr;
                  obj2[k].subs.push(addArr2[k]);
                  self.divBox3.push(obj2[k]);
                }
              }
              self.yspNum = self.divBox3.length;
            }
            // else {
            //     console.log('暂无已审批数据！');
            // }
          },
          function(res) {
            self.$message.error("请求失败");
          }
        );
    }
  },
  mounted() {
    this.getCardData();
  }
};
</script>
<style scoped>
.dtjForm .el-form-item--mini.el-form-item,
.el-form-item--small.el-form-item {
  margin-bottom: 2px;
}
.dtjTop {
  position: relative;
}
.backBtn {
  position: absolute;
  right: 80px;
  z-index: 99;
  font-size: 24px !important;
}
.backBtn1 {
  position: absolute;
  right: 120px;
  z-index: 99;
  font-size: 24px !important;
}
.backBtn2 {
  color: #000000;
  position: absolute;
  right: 80px;
  z-index: 99;
  font-size: 24px !important;
}
.backBtn3 {
  color: #000000;
  position: absolute;
  right: 120px;
  z-index: 99;
  font-size: 24px !important;
}
.wdlcType {
  position: absolute;
  border-radius: 50%;
  height: 60px;
  width: 60px;
  top: 50%;
  right: 10px;
  transform: translateY(-50%);
  text-align: center;
  line-height: 60px;
  border: 2px solid #409eff;
  color: #409eff;
}
.wdlcType > div {
  width: 48px;
  height: 48px;
  position: relative;
  margin: 5px;
  border: 1px solid white;
  border-radius: 24px;
  transform: rotate(-20deg);
}
.dtjForm .wdlcType span {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  display: inline-block;
  width: 80px;
  height: 24px;
  line-height: 24px;
  text-align: center;
  font-size: 13px;
  color: #409eff;
}

.dtjForm span {
  color: #606266;
}

.dtjBox {
  border: 1px solid #e8e8e8;
  cursor: pointer;
  position: relative;
}
.dtjBox .seampImg {
  position: absolute;
  top: 0;
  right: 20px;
}
.dtjTop {
  padding: 6px 0 0 0;
}

.dtjTop .el-form-item--mini.el-form-item,
.el-form-item--small.el-form-item {
  padding-left: 15px;
}

.dtjTop .lastBtn {
  padding: 0;
  margin-bottom: 0 !important;
}

.dtjBox p {
  opacity: 0.85;
  font-family: PingFangSC-Regular;
  font-size: 16px;
  color: #000000;
  margin-bottom: 10px;
  padding-left: 15px;
}

.dtjBox p i {
  font-size: 26px;
  color: #d42425;
  position: relative;
  top: 2px;
}

.dtjForm .el-form-item--mini.el-form-item,
.el-form-item--small.el-form-item {
  margin-bottom: 2px;
}

.bmxy-info {
  font-family: PingFangSC-Regular;
  font-size: 16px;
  color: rgba(0, 0, 0, 1);
  text-align: justify;
  margin: 25px;
}

.last-info {
  text-indent: 46px;
}

.bmxy-tit {
  text-align: center;
  font-family: PingFangSC-Medium;
  font-size: 18px;
  font-weight: 900;
  color: rgba(0, 0, 0, 0.85);
  text-align: center;
}

.dtjBox {
  border: 1px solid #e8e8e8;
  cursor: pointer;
}

.dtjTop {
  padding: 6px 0 0 0;
}

.dtjTop .el-form-item--mini.el-form-item,
.el-form-item--small.el-form-item {
  padding-left: 15px;
}

.dtjTop .lastBtn {
  padding: 0;
  margin-bottom: 0 !important;
}

.dtjBox p {
  opacity: 0.85;
  font-family: PingFangSC-Regular;
  font-size: 16px;
  color: #000000;
  margin-bottom: 10px;
  padding-left: 15px;
}

.dtjBox p i {
  font-size: 26px;
  color: #d42425;
  position: relative;
  top: 2px;
}
.dtjBox1 p {
  font-family: PingFangSC-Regular;
  font-size: 16px;
  color: #000000;
}

.dtjBox1 p i {
  font-size: 26px;
  color: #d42425;
}
.dtjForm .el-form-item--mini.el-form-item,
.el-form-item--small.el-form-item {
  margin-bottom: 2px;
}

.dtjForm span {
  color: #606266;
}

.bmxy-info {
  font-family: PingFangSC-Regular;
  font-size: 16px;
  color: rgba(0, 0, 0, 1);
  text-align: justify;
  margin: 25px;
}

.last-info {
  text-indent: 46px;
}

.bmxy-tit {
  text-align: center;
  font-family: PingFangSC-Medium;
  font-size: 18px;
  font-weight: 900;
  color: rgba(0, 0, 0, 0.85);
  text-align: center;
}

.dtjBox {
  border: 1px solid #e8e8e8;
  cursor: pointer;
}

.dtjTop {
  padding: 6px 0 0 0;
}

.dtjTop .el-form-item--mini.el-form-item,
.el-form-item--small.el-form-item {
  padding-left: 15px;
}

.dtjTop .lastBtn {
  padding: 0;
  margin-bottom: 0 !important;
}

.dtjBox p {
  opacity: 0.85;
  font-family: PingFangSC-Regular;
  font-size: 16px;
  color: #000000;
  margin-bottom: 10px;
  padding-left: 15px;
}

.dtjBox p i {
  font-size: 26px;
  color: #d42425;
  position: relative;
  top: 2px;
}

.dtjForm .el-form-item--mini.el-form-item,
.el-form-item--small.el-form-item {
  margin-bottom: 2px;
}

.bmxy-info {
  font-family: PingFangSC-Regular;
  font-size: 16px;
  color: rgba(0, 0, 0, 1);
  text-align: justify;
  margin: 25px;
}

.last-info {
  text-indent: 46px;
}

.bmxy-tit {
  text-align: center;
  font-family: PingFangSC-Medium;
  font-size: 18px;
  font-weight: 900;
  color: rgba(0, 0, 0, 0.85);
  text-align: center;
}
</style>
<style>
.infoMenu .el-tabs__item {
  font-family: PingFangSC-Regular;
  font-size: 16px !important;
}

.infoMenu .el-menu-item a {
  display: inline-block;
  width: 100%;
  height: 100%;
}

.lastBtn .el-form-item__content {
  margin-left: 0 !important;
}

.dtjBottom .iconfont {
  font-size: 22px !important;
}

.dtjTop .el-form-item__label {
  padding: 0 !important;
}

.dtjBottom .el-button-group {
  width: 100%;
}

.dtjBottom .el-button-group > .el-button {
  border-bottom: none;
  display: inline-block;
  width: 33.3%;
}

.dtjBottom .el-button-group > .el-button:first-child {
  border-left: none;
}

.dtjBottom .el-button-group > .el-button:last-child {
  border-right: none;
}

.lastBtn .el-form-item__content {
  margin-left: 0 !important;
}

.dtjBottom .iconfont {
  font-size: 22px !important;
}

.dtjTop .el-form-item__label {
  padding: 0 !important;
}

.dtjBottom .el-button-group {
  width: 100%;
}

.dtjBottom .el-button-group > .el-button {
  border-bottom: none;
  display: inline-block;
  width: 33.3%;
}

.dtjBottom .el-button-group > .el-button:first-child {
  border-left: none;
}

.dtjBottom .el-button-group > .el-button:last-child {
  border-right: none;
}

.lastBtn .el-form-item__content {
  margin-left: 0 !important;
}

.dtjBottom .iconfont {
  font-size: 22px !important;
}

.dtjTop .el-form-item__label {
  padding: 0 !important;
}

.dtjBottom .el-button-group {
  width: 100%;
}

.dtjBottom .el-button-group > .el-button {
  border-bottom: none;
  display: inline-block;
  width: 33.3%;
}

.dtjBottom .el-button-group > .el-button:first-child {
  border-left: none;
}

.dtjBottom .el-button-group > .el-button:last-child {
  border-right: none;
}
</style>
