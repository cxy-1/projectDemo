<template>
	 <div class="table LeaveManage">
		<el-card shadow="" class="mgb20">
      <div class="crumbs">
          <el-breadcrumb separator="/">
              <el-breadcrumb-item>系统管理</el-breadcrumb-item>
              <el-breadcrumb-item>使用报告</el-breadcrumb-item>
          </el-breadcrumb>
      </div>
      <div class="crumbs">
        <el-row>
          <el-col :span="24">
            <el-col :span="24" style="clear:both">
              <el-col :span="6">
                <span>单位选择：</span>
                <el-select v-model="applyType" clearable placeholder="请选择" @change="changes">
                  <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-col>
              <el-col :span="4">
                  
              </el-col>
              <el-col :span="9" style="float:right">
                <el-button type="primary" style="float: right;"  @click="selectse">查询</el-button>
                <el-date-picker
                  v-model="applyType2"
                  type="daterange"
                  range-separator="至"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期"
                  format='yyyy-MM-dd' 
                  value-format="yyyy-MM-dd"
                   @change="selectss()"
                    style="float: right;">
                </el-date-picker>

              </el-col>
              <el-col :span="5" style="float:right;text-align: right;">
                <el-radio-group v-model="applyType1" size="small"  @change="selects()">
                  <el-radio-button label="本周"></el-radio-button>
                  <el-radio-button label="本月"></el-radio-button>
                  <el-radio-button label="半年"></el-radio-button>
                  <el-radio-button label="全年"></el-radio-button>
                </el-radio-group>
              </el-col>
            </el-col>
          </el-col>
        </el-row>
      </div>
    </el-card>
    <div class="containes">
      <el-row>
        <el-col :span="24" style="clear:both">
          <el-col :span="8" style="float:left">
            <el-card shadow="" class="shadow">
              <div class="imgss">
                <img src="static/img/1x.png" style="position: absolute;top: 25%;left: 25%;"/>
              </div>
              <div class="PingFang">
                <p class="PingFangtitle">累计登录</p>
                <p class="PingFangconte">
                  {{logocountmans}}<span class="Fangcontes">人/</span>
                  {{logocounts}}<span class="Fangcontes">次</span>
                </p>
              </div>
              <div class="PingFangtabs">
                <div v-for="(v,k) in tabs" :key="k" style="padding-top:5px;clear:both">
                  <div class="point"></div>
                  <div class="pointRegular">
                    <span style="PingFangSC-Regular;color:rgba(0,0,0,0.65);">{{v.companyName}}</span>
                    {{v.totalInfoa}}<span style="PingFangSC-Regular;color:rgba(0,0,0,0.65);">人/</span>
                    {{v.totalInfos}}<span style="PingFangSC-Regular;color:rgba(0,0,0,0.65);">次</span>
                  </div>
                </div>
              </div>
            </el-card>
          </el-col>
          <el-col :span="8" style="padding: 0px 5px;">
            <el-card shadow="" class="shadow">
              <div class="imgsse">
                <img src="static/img/2x.png" style="position: absolute;top: 25%;left: 25%;"/>
              </div>
              <div class="PingFang">
                <p class="PingFangtitle">请销假发起</p>
                <p class="PingFangcontes">
                  <span class="Fangcontes">累计</span>{{pleseoffs}}
                  <span class="Fangcontes">条</span>
                </p>
              </div>
              <div class="PingFangtabs">
                <div v-for="(item,i) in tabs1" :key="i" style="padding-top:5px;clear:both">
                  <div class="points"></div>
                  <div class="pointRegular" style="color: #8dd3b4;">
                    <span style="PingFangSC-Regular;color:rgba(0,0,0,0.65);">{{item.deptName}}</span>
                    {{item.totalInfos}}<span style="PingFangSC-Regular;color:rgba(0,0,0,0.65);">条</span>
                  </div>
                </div>
              </div>
            </el-card>
          </el-col>
          <el-col :span="8" style="float:right">
            <el-card shadow="" class="shadow">
              <div class="imgsses">
                <img src="static/img/3x.png" style="position: absolute;top: 25%;left: 25%;"/>
              </div>
              <div class="PingFang">
                <p class="PingFangtitle">出国境发起</p>
                <p class="PingFangcontes" style="color:#e39588;">
                  <span class="Fangcontes">累计</span>{{goabroads}}
                  <span class="Fangcontes">条</span>
                </p>
              </div>
              <div class="PingFangtabs">
                <div v-for="(items,m) in tabs2" :key="m" style="padding-top:5px;clear:both">
                  <div class="points" style="background: #e39588;"></div>
                  <div class="pointRegular" style="color: #e39588;">
                    <span style="PingFangSC-Regular;color:rgba(0,0,0,0.65);">{{items.deptName}}</span>
                    {{items.totalInfos}}<span style="PingFangSC-Regular;color:rgba(0,0,0,0.65);">条</span>
                  </div>
                </div>
              </div>
            </el-card>
          </el-col>
        </el-col>
      </el-row>
    </div>
		<div class="containe">
			<div class="leave is-always-shadow">
				<el-row>
					<el-col :span="24" class="title">
						<div class="grid-content bg-purple-dark clearfix"><span>用户登录</span></div>
					</el-col>
					<el-row>
					  	<el-col :span="24" class="echarts">
                <div style="width: 100%;height:385px;" id="LoginStatistics">

                </div>
              </el-col>
              <el-col :span="24">
                <el-col :span="10" class="paddingboth">
                  <img v-show="tableData.length>0?false:true" :src="require('static/img/manage/noData.png')" class="noDataImg" />
                  <el-table v-show="tableData.length>0?true:false" :data="tableData" header-align = "center" style="width: 100%;">
                    <el-table-column align = "center" type="index" width="50" label="排名"></el-table-column>
                    <el-table-column align = "center" prop="companyName" label="单位"></el-table-column>
                    <el-table-column align = "center" prop="totalInfo" label="登录用户/次数"></el-table-column>
                  </el-table>
                  <!--表格分页  -->
                  <div class="pagination" v-show="tableData.length>0?true:false">
                      <el-pagination @current-change="handleCurrentChange" :current-page="cur_page" :page-size="pagesize" :page-sizes="[10]" layout="total, sizes, prev, pager, next, jumper" :total="inittableData.length">
                      </el-pagination>
                  </div>
                </el-col>
                <el-col :span="14" class="paddingboth">
                  <img v-show="tableDatas.length>0?false:true" :src="require('static/img/manage/noData.png')" class="noDataImg" />
                  <el-table v-show="tableDatas.length>0?true:false" :data="tableDatas" header-align = "center" style="width: 100%;">
                    <el-table-column align = "center" prop="policeNo" label="用户"></el-table-column>
                    <el-table-column align = "center" prop="personName" label="姓名"></el-table-column>
                    <el-table-column align = "center" prop="deptName" label="单位"></el-table-column>
                    <el-table-column align = "center" prop="operateTime" label="登录时间"></el-table-column>
                  </el-table>
                  <!--表格分页  -->
                  <div class="pagination" v-show="tableDatas.length>0?true:false">
                      <el-pagination @current-change="handleCurrentChanges" :current-page="cur_pages" :page-size="pagesizes" :page-sizes="[10]" layout="total, sizes, prev, pager, next, jumper" :total="inittableDatas.length">
                      </el-pagination>
                  </div>
                </el-col>
              </el-col>
					</el-row>
				</el-row>
			</div>
		</div>
    <div class="containe">
			<div class="leave is-always-shadow">
				<el-row>
					<el-col :span="24" class="title">
						<div class="grid-content bg-purple-dark clearfix"><span>请销假</span></div>
					</el-col>
					<el-row>
					  	<el-col :span="24" class="paddingboth">
                <img v-show="tableDataleave.length>0?false:true" :src="require('static/img/manage/noData.png')" class="noDataImg" />
                <el-table v-show="tableDataleave.length>0?true:false" :data="tableDataleave" header-align = "center" style="width: 100%;">
                  <el-table-column  align= "center" prop="policeNo" label="用户"></el-table-column>
                  <el-table-column  align= "center" prop="name" label="姓名"></el-table-column>
                  <el-table-column  align= "center" prop="companyName" label="单位"></el-table-column>
                  <el-table-column  align= "center" prop="reason" label="请假类型"></el-table-column>
                  <el-table-column  align= "center" prop="nodeName" label="当前流程环节"></el-table-column>
                  <el-table-column  align= "center" prop="applicationTime" label="申请时间"></el-table-column>
                </el-table>
                <!--表格分页  -->
                <div class="pagination" v-show="tableDataleave.length>0?true:false">
                  <el-pagination @current-change="handleCurrentChangel" :current-page="cur_pagel" :page-size="pagesizel" :page-sizes="[10]" layout="total, sizes, prev, pager, next, jumper" :total="inittableDataleave.length">
                  </el-pagination>
                </div>
              </el-col>
					</el-row>
				</el-row>
			</div>
		</div>
    <div class="containe">
			<div class="leave is-always-shadow">
				<el-row>
					<el-col :span="24" class="title">
						<div class="grid-content bg-purple-dark clearfix"><span>出国境</span></div>
					</el-col>
					<el-row>
					  	<el-col :span="24" class="paddingboth">
                <img v-show="tableDataresum.length>0?false:true" :src="require('static/img/manage/noData.png')" class="noDataImg" />
                <el-table v-show="tableDataresum.length>0?true:false" :data="tableDataresum" header-align = "center" style="width: 100%;">
                  <el-table-column align = "center" prop="policeNo" label="用户"></el-table-column>
                  <el-table-column align = "center" prop="name" label="姓名"></el-table-column>
                  <el-table-column align = "center" prop="companyName" label="单位"></el-table-column>
                  <el-table-column align = "center" prop="leaveReason" label="离境原因"></el-table-column>
                  <el-table-column align = "center" prop="destinationBroad" label="目的地"></el-table-column>
                  <el-table-column align = "center" prop="nodeName" label="当前流程环节"></el-table-column>
                  <el-table-column align = "center" prop="applicationTime" label="申请时间"></el-table-column>
                </el-table>
                <!--表格分页  -->
                <div class="pagination" v-show="tableDataresum.length>0?true:false">
                  <el-pagination @current-change="handleCurrentChanger" :current-page="cur_pager" :page-size="pagesizer" :page-sizes="[10]" layout="total, sizes, prev, pager, next, jumper" :total="inittableDataresum.length">
                  </el-pagination>
                </div>
              </el-col>
					</el-row>
				</el-row>
			</div>
		</div>
		 <el-footer :style="'text-align:center','height: 20px'">&copy;2019-2022&emsp;公安局&emsp;版权所有</el-footer>
	 </div>
</template>

<script>
import { communication } from "../../data/getAxios";
export default {
  data() {
    return {
      applyType: "",
      applyType1: "",
      options: [],
      applyType2: "",
      tabs: [],
      tabs1: [],
      tabs2: [],
      tableData: [],
      inittableData: [],
      cur_page: 1, //表格当前页
      pagesize: 10,
      tableDatas: [],
      inittableDatas: [],
      cur_pages: 1, //表格当前页
      pagesizes: 10,
      tableDataleave: [],
      inittableDataleave: [],
      cur_pagel: 1, //表格当前页
      pagesizel: 10,
      tableDataresum: [],
      inittableDataresum: [],
      cur_pager: 1, //表格当前页
      pagesizer: 10,
      logocountmans: 0,
      logocounts: 0,
      pleseoffs: 0,
      goabroads: 0,
      applyTypesk: ""
    };
  },
  created() {
    this.int();
  },
  mounted() {
    // this.loginChart();
  },
  methods: {
    int() {
      const _obj = {
        url:
          this.global.serverPath8 +
          "/statistics/mangagerData/getDeptRewordTree?aboutSelf=false",
        isGet: true
      };
      communication(_obj).then(
        res => {
          if (res && res.length > 0) {
            for (var s = 0; s < res.length; s++) {
              let item = res[s];
              let newVar = {
                label: item.companyName,
                name: item.companyName,
                value: item.id,
                leaf: true,
                dataId: item.dataId
              };
              this.options.push(newVar);
            }
            this.options.unshift({
              label: "南京市公安局",
              name: "南京市公安局",
              value: "320100000000",
              leaf: true,
              dataId: "3201"
            });
            this.applyType = "320100000000";
            this.applyTypesk = "3201";
            this.applyType1 = "本周";
            this.selects();
            this.selectse();
          }
        },
        err => {
          this.$message.error("获取单位列表数据失败！");
        }
      );
    },
    changes() {
      this.options.forEach(items => {
        if (this.applyType == items.value) {
          this.applyTypesk = items.dataId;
        }
      });
      console.log(this.applyTypesk);
    },
    selects() {
      const end = new Date();
      const start = new Date();
      if (this.applyType1 == "本周") {
        let datas = start.getDay() - 1;
        datas == -1 ? (datas = 6) : (datas = datas);
        start.setTime(start.getTime() - 3600 * 1000 * 24 * datas);
      } else if (this.applyType1 == "本月") {
        const datas = start.getDate() - 1;
        start.setTime(start.getTime() - 3600 * 1000 * 24 * datas);
      } else if (this.applyType1 == "半年") {
        start.setTime(start.getTime() - 3600 * 1000 * 24 * 30 * 6);
      } else if (this.applyType1 == "全年") {
        start.setTime(start.getTime() - 3600 * 1000 * 24 * 365);
      }
      let mss = "0" + (end.getMonth() + 1);
      let die = "0" + end.getDate();
      let datetime =
        end.getFullYear() +
        "-" +
        mss.substring(mss.length - 2, mss.length) +
        "-" +
        die.substring(die.length - 2, die.length);
      let miss = "0" + (start.getMonth() + 1);
      let dies = "0" + start.getDate();
      let datetimes =
        start.getFullYear() +
        "-" +
        miss.substring(miss.length - 2, miss.length) +
        "-" +
        dies.substring(dies.length - 2, dies.length);
      this.applyType2 = [datetimes, datetime];
    },
    selectse() {
      if (
        this.applyType == null ||
        this.applyType == "" ||
        this.applyType == "undefined"
      ) {
        this.$message.error("请选择工作单位");
        return;
      } else {
        this.resumquery();
        this.dashquery();
        this.loginDetail();
        this.goAbroadCount();
        this.pleseOffCount();
        this.loginCount();
        this.loginChart();
      }
    },
    selectss() {
      this.applyType1 = "";
      console.log(this.applyType2);
    },
    loginChart() {
      var myCharts = this.$echarts.init(
        document.getElementById("LoginStatistics")
      );
      var option = {
        tooltip: {
          trigger: "axis",
          axisPointer: {
            type: "cross",
            label: {
              backgroundColor: "#6a7985"
            }
          }
        },
        legend: {
          data: ["登录次数", "登录用户", "登录次数平均数", "登录用户平均数"]
        },
        toolbox: {
          //可以下载图片
          // feature: {
          //   saveAsImage: {}
          // }
        },
        grid: {
          left: "3%",
          right: "4%",
          bottom: "3%",
          containLabel: true
        },
        xAxis: [
          {
            type: "category",
            boundaryGap: false,
            data: []
          }
        ],
        yAxis: [
          {
            type: "value"
          }
        ],
        series: [
          {
            name: "登录次数",
            type: "line",
            smooth: true,
            symbol: "circle",
            symbolSize: 5,
            sampling: "average",
            stack: "总量",
            areaStyle: {},
            data: []
          },
          {
            name: "登录用户",
            type: "line",
            smooth: true,
            symbol: "circle",
            symbolSize: 5,
            sampling: "average",
            stack: "总量",
            areaStyle: {},
            data: []
          },
          {
            name: "登录次数平均数",
            type: "line",
            data: []
          },
          {
            name: "登录用户平均数",
            type: "line",
            data: []
          }
        ]
      };
      let applyTypesd = "";
      let applyTypesr = "";
      if (this.applyType2 && this.applyType2.length > 0) {
        applyTypesd = this.applyType2[0];
        applyTypesr = this.applyType2[1];
      }
      const _obj = {
        url: this.global.serverPath8 + "/user-mgmt/usage/loginChart",
        postData: {
          companyId: this.applyType,
          startDate: applyTypesd,
          endDate: applyTypesr,
          dataId: this.applyTypesk
        }
      };
      communication(_obj).then(res => {
        if (res.code == 0) {
          option.xAxis[0].data = res.data.days;
          option.series[0].data = res.data.loginTimesPerDay;
          option.series[1].data = res.data.personTimesPerDay;
          res.data.days.forEach(item => {
            option.series[2].data.push(res.data.loginTimesAvg);
            option.series[3].data.push(res.data.loginPersonsAvg);
          });
          myCharts.setOption(option);
        } else {
          option.series[2].data = [];
          option.series[3].data = [];
          option.series[1].data = [];
          option.series[0].data = [];
          option.xAxis[0].data = [];
        }
      });
    },
    handleCurrentChange(val) {
      this.cur_page = val;
      this.tableData = this.inittableData.slice(
        (this.cur_page - 1) * this.pagesize,
        this.cur_page * this.pagesize
      );
    },
    handleCurrentChanges(val) {
      this.cur_pages = val;
      this.tableDatas = this.inittableDatas.slice(
        (this.cur_pages - 1) * this.pagesizes,
        this.cur_pages * this.pagesizes
      );
    },
    handleCurrentChangel(val) {
      this.cur_pagel = val;
      this.tableDataleave = this.inittableDataleave.slice(
        (this.cur_pagel - 1) * this.pagesizel,
        this.cur_pagel * this.pagesizel
      );
    },
    handleCurrentChanger(val) {
      this.cur_pager = val;
      this.tableDataresum = this.inittableDataresum.slice(
        (this.cur_pager - 1) * this.pagesizer,
        this.cur_pager * this.pagesizer
      );
    },
    resumquery() {
      var self = this;
      self.cur_pagel = 1;
      let applyTypesd = "";
      let applyTypesr = "";
      if (self.applyType2 && self.applyType2.length > 0) {
        applyTypesd = self.applyType2[0];
        applyTypesr = self.applyType2[1];
      }
      const _obj = {
        url: self.global.serverPath8 + "/flow/query/getPleaseOff",
        isGet: false,
        postData: {
          beginTime: applyTypesd,
          endTime: applyTypesr,
          deptID: self.applyTypesk,
          type: "tj" //使用页面专用
        }
      };
      communication(_obj).then(
        res => {
          if (res.code == 0) {
            self.inittableDataleave = res.data;
            self.tableDataleave = self.inittableDataleave.slice(
              (self.cur_pagel - 1) * self.pagesizel,
              self.cur_pagel * self.pagesizel
            );
          } else {
            self.tableDataleave = [];
          }
        },
        function(res) {
          self.$message.error("请求失败！");
        }
      );
    },
    dashquery() {
      // this.applyType = "320102";
      var self = this;
      self.cur_pager = 1;
      console.log(self.applyType2);
      let applyTypesd = "";
      let applyTypesr = "";
      if (self.applyType2 && self.applyType2.length > 0) {
        applyTypesd = self.applyType2[0];
        applyTypesr = self.applyType2[1];
      }
      const _obj = {
        url: self.global.serverPath8 + "/flow/query/getGoAbroad",
        postData: {
          beginTime: applyTypesd,
          endTime: applyTypesr,
          deptID: self.applyTypesk,
          type: "tj" //使用页面专用
        }
      };
      communication(_obj)
        .then(res => {
          if (res.code == 0) {
            self.tableDataresum = [];
            self.tableDataresum = res.data;
            self.inittableDataresum = res.data;
            self.tableDataresum = self.inittableDataresum.slice(
              (self.cur_pager - 1) * self.pagesizer,
              self.cur_pager * self.pagesizer
            );
          } else if (res.code != 1) {
            self.$message.warning(res.message);
          }
        })
        .catch(err => {
          console.log("请求失败！");
        });
    },
    loginDetail() {
      var self = this;
      self.cur_pages = 1;
      let applyTypesd = "";
      let applyTypesr = "";
      if (self.applyType2 && self.applyType2.length > 0) {
        applyTypesd = self.applyType2[0];
        applyTypesr = self.applyType2[1];
      }
      const _obj = {
        url: self.global.serverPath8 + "/user-mgmt/usage/loginDetail",
        isGet: false,
        postData: {
          companyId: self.applyType,
          startDate: applyTypesd,
          endDate: applyTypesr,
          dataId: this.applyTypesk
        }
      };
      communication(_obj).then(
        res => {
          self.tableDatas = [];
          if (res.code == 0) {
            self.inittableDatas = res.data;
            self.tableDatas = self.inittableDatas.slice(
              (self.cur_pages - 1) * self.pagesizes,
              self.cur_pages * self.pagesizes
            );
          } else {
            self.tableDatas = [];
          }
        },
        function(res) {
          self.$message.error("请求失败！");
        }
      );
    },
    goAbroadCount() {
      var self = this;
      let applyTypesd = "";
      let applyTypesr = "";
      if (self.applyType2 && self.applyType2.length > 0) {
        applyTypesd = self.applyType2[0];
        applyTypesr = self.applyType2[1];
      }
      const _obj = {
        url: self.global.serverPath8 + "/user-mgmt/usage/goAbroadCount",
        isGet: false,
        postData: {
          companyId: self.applyType,
          startDate: applyTypesd,
          endDate: applyTypesr,
          dataId: this.applyTypesk
        }
      };
      communication(_obj).then(
        res => {
          self.tabs2 = [];
          if (res.code == 0) {
            let sgoabroad = res.data.totalInfo.split("条");
            self.goabroads = sgoabroad[0];
            if (res.data.detailInfo.length > 0) {
              res.data.detailInfo.forEach(items => {
                let totalInfod = items.totalInfo.split("条");
                self.tabs2.push({
                  companyId: items.companyId,
                  companyName: items.companyName,
                  deptId: items.deptId,
                  deptName: items.deptName,
                  totalInfos: totalInfod[0]
                });
              });
            } else {
              self.tabs2 = [];
            }
          }
        },
        function(res) {
          self.$message.error("请求失败！");
        }
      );
    },
    pleseOffCount() {
      var self = this;
      let applyTypesd = "";
      let applyTypesr = "";
      if (self.applyType2 && self.applyType2.length > 0) {
        applyTypesd = self.applyType2[0];
        applyTypesr = self.applyType2[1];
      }
      const _obj = {
        url: self.global.serverPath8 + "/user-mgmt/usage/pleseOffCount",
        isGet: false,
        postData: {
          companyId: self.applyType,
          startDate: applyTypesd,
          endDate: applyTypesr,
          dataId: this.applyTypesk
        }
      };
      communication(_obj).then(
        res => {
          self.tabs1 = [];
          if (res.code == 0) {
            let spleseoff = res.data.totalInfo.split("条");
            self.pleseoffs = spleseoff[0];
            if (res.data.detailInfo.length > 0) {
              res.data.detailInfo.forEach(items => {
                let totalInfod = items.totalInfo.split("条");
                self.tabs1.push({
                  companyId: items.companyId,
                  companyName: items.companyName,
                  deptId: items.deptId,
                  deptName: items.deptName,
                  totalInfos: totalInfod[0]
                });
              });
            } else {
              self.tabs1 = [];
            }
          }
        },
        function(res) {
          self.$message.error("请求失败！");
        }
      );
    },
    loginCount() {
      var self = this;
      self.cur_page = 1;
      let applyTypesd = "";
      let applyTypesr = "";
      if (self.applyType2 && self.applyType2.length > 0) {
        applyTypesd = self.applyType2[0];
        applyTypesr = self.applyType2[1];
      }
      const _obj = {
        url: self.global.serverPath8 + "/user-mgmt/usage/loginCount",
        isGet: false,
        postData: {
          companyId: self.applyType,
          startDate: applyTypesd,
          endDate: applyTypesr,
          dataId: this.applyTypesk
        }
      };
      communication(_obj).then(
        res => {
          if (res.code == 0) {
            self.tabs = [];
            let totalInfok = res.data.totalInfo.split("/");
            self.logocountmans = totalInfok[0];
            self.logocounts = totalInfok[1];
            self.inittableData = res.data.detailInfo;
            if (res.data.detailInfo.length > 0) {
              res.data.detailInfo.forEach(items => {
                let totalInfod = items.totalInfo.split("/");
                self.tabs.push({
                  companyId: items.companyId,
                  companyName: items.companyName,
                  deptId: items.deptId,
                  deptName: items.deptName,
                  totalInfoa: totalInfod[0],
                  totalInfos: totalInfod[1]
                });
              });
            } else {
              self.tabs = [];
            }
            self.tableData = self.inittableData.slice(
              (self.cur_page - 1) * self.pagesize,
              self.cur_page * self.pagesize
            );
          } else {
            self.tableData = [];
            self.tabs = [];
            self.logocountmans = 0;
            self.logocounts = 0;
          }
        },
        function(res) {
          self.$message.error("请求失败！");
        }
      );
    }
  }
};
</script>

<style scoped>
@media screen and (max-width: 1504px) {
  .shadow {
    min-height: 173px;
  }
  .imgss {
    height: 173px !important;
  }
  .imgss > img {
    position: absolute;
    top: 40% !important;
    left: 25%;
  }
  .imgsse {
    height: 173px !important;
  }
  .imgsse > img {
    position: absolute;
    top: 40% !important;
    left: 25%;
  }
  .imgsses {
    height: 173px !important;
  }
  .imgsses > img {
    position: absolute;
    top: 40% !important;
    left: 25%;
  }
}
.pointRegular {
  font-family: PingFangSC-Medium;
  font-size: 14px;
  color: #70b9ff;
  letter-spacing: 0;
  float: left;
  word-wrap: break-word;
  word-break: break-all;
  width: 130px;
}
.PingFangtabs {
  float: left;
  width: 35%;
  padding-left: 10px;
  padding-top: 15px;
  min-width: 147px;
}
.PingFang {
  float: left;
  width: 35%;
  padding-left: 10px;
  padding-top: 15px;
  min-width: 130px;
}
.PingFangtitle {
  font-family: PingFangSC-Medium;
  font-size: 18px;
  color: rgba(0, 0, 0, 0.65);
  letter-spacing: 0;
  font-weight: 700;
}
.PingFangconte {
  font-family: PingFangSC-Medium;
  font-size: 25px;
  color: #70b9ff;
  letter-spacing: 0;
  padding-top: 10px;
}
.PingFangcontes {
  font-family: PingFangSC-Medium;
  font-size: 25px;
  color: #8dd3b4;
  letter-spacing: 0;
  padding-top: 10px;
}
.Fangcontes {
  font-size: 14px;
  font-family: PingFangSC-Regular;
  color: rgba(0, 0, 0, 0.65);
  line-height: 36px;
}
.point {
  width: 6px;
  height: 6px;
  background: #70b9ff;
  border-radius: 50% 50%;
  float: left;
  margin-top: 8px;
  margin-right: 5px;
}
.points {
  width: 6px;
  height: 6px;
  background: #8dd3b4;
  border-radius: 50% 50%;
  float: left;
  margin-top: 8px;
  margin-right: 5px;
}
.imgss {
  width: 100px;
  height: 100px;
  background-image: linear-gradient(-180deg, #c1f8ff 0%, #7bbeff 100%);
  border-radius: 3px 0px 0px 3px;
  position: relative;
  float: left;
  min-width: 100px;
}
.imgsse {
  width: 100px;
  height: 100px;
  background-image: linear-gradient(-180deg, #e0ffd6 0%, #8dd3b4 100%);
  border-radius: 3px 0px 0px 3px;
  position: relative;
  float: left;
  min-width: 100px;
}
.imgsses {
  width: 100px;
  height: 100px;
  background-image: linear-gradient(-180deg, #ffe5bd 0%, #e39588 100%);
  border-radius: 3px 0px 0px 3px;
  position: relative;
  float: left;
  min-width: 100px;
}
.leave .title {
  float: inherit;
}
.leaveList .leave .el-row {
  margin-bottom: 0px !important;
}
.leave .el-rowL:first-child {
  margin-bottom: 0;
}
.leave {
  margin: 16px 0px;
  background: white;
}
.containe .title {
  float: inherit;
  padding: 10px 10px;
  border-bottom: 1px solid #ebeef5;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
}
.table .crumbs {
  background: white;
  margin-bottom: 0;
  font-size: 14px;
  padding: 10px 10px;
}
.fuzzinfoCon {
  padding: 20px 20px 10px;
}
.containe {
  margin: 0px 10px;
}
.containes {
  margin: 0px 10px;
}
.paddingboth {
  padding: 20px;
}
.echarts {
  border-bottom: 1px solid #ebeef5;
  padding: 20px;
}
</style>
<style>
.el-card__body {
  padding: 0px;
}
</style>

