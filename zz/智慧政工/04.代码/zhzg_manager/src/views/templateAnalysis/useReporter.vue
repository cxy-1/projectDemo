<template>
  <div class="table LeaveManage minwidth">
		<el-card shadow="" class="mgb20">
      <div class="crumbs">
        <el-row>
          <el-col :span="24">
            <el-col :span="24" style="clear:both">
              <el-col :span="7">
                <span>单位选择：</span>
                <el-cascader ref="cascader" :options="options" placeholder="请选择" :props="props" @change="changes" :show-all-levels="false" collapse-tags clearable @visible-change="visibleChange"></el-cascader>
              </el-col>
              <el-col :span="3">
                  
              </el-col>
              <el-col :span="10" style="float:right">
                <el-button type="primary" style="float: right; margin-left: 10px;"  @click="exportExcel">导出</el-button>
                <el-button type="primary" style="float: right; margin-left: 10px;"  @click="selectse">查询</el-button>
                <el-date-picker
                  v-model="applyType2"
                  type="datetimerange"
                  range-separator="至"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期"
                  format='yyyy-MM-dd HH:mm:ss' 
                  value-format="yyyy-MM-dd HH:mm:ss"
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
                <img src="./../../assets/image/3x.png" style="position: absolute;top: 25%;left: 25%;"/>
              </div>
              <div class="rightmian">
              <div class="PingFang">
                <p class="PingFangtitle">队伍可视化</p>
                <p class="PingFangconte">
                  <!-- {{logocountmans}}<span class="Fangcontes">人/</span> -->
                  <span class="Fangcontes">累计</span>
                  {{logocounts}}<span class="Fangcontes">人次</span>
                </p>
              </div>
              <div class="PingFangtabs">
                <div v-for="(v,k) in tabs" :key="k" style="padding-top:5px;clear:both">
                  <div class="point"></div>
                  <div class="pointRegular">
                    <div class="leftbodytext">
                      <span style="PingFangSC-Regular;color:rgba(0,0,0,0.65);" :title="v.name">{{v.name}}</span>
                    </div>
                    <div class="rightbodytext">
                      {{v.count}}<span style="PingFangSC-Regular;color:rgba(0,0,0,0.65);">人/</span>
                      {{v.personTime}}<span style="PingFangSC-Regular;color:rgba(0,0,0,0.65);">次</span>
                    </div>
                  </div>
                </div>
              </div>
              </div>
            </el-card>
          </el-col>
          <el-col :span="8" style="padding: 0px 5px;">
            <el-card shadow="" class="shadow">
              <div class="imgsse">
                <img src="./../../assets/image/2x.png" style="position: absolute;top: 25%;left: 25%;"/>
              </div>
              <div class="rightmian">
              <div class="PingFang">
                <p class="PingFangtitle">队伍综合评估</p>
                <p class="PingFangcontes">
                  <span class="Fangcontes">累计</span> {{pleseoffs}}
                  <span class="Fangcontes">人次</span>
                </p>
              </div>
              <div class="PingFangtabs">
                <div v-for="(item,i) in tabs1" :key="i" style="padding-top:5px;clear:both">
                  <div class="points"></div>
                  <div class="pointRegular" style="color: #8dd3b4;">
                    <div class="leftbodytext">
                      <span style="PingFangSC-Regular;color:rgba(0,0,0,0.65);" :title="item.name">{{item.name}}</span>
                    </div>
                    <div class="rightbodytext">
                      {{item.count}}<span style="PingFangSC-Regular;color:rgba(0,0,0,0.65);">人/</span>
                      {{item.personTime}}<span style="PingFangSC-Regular;color:rgba(0,0,0,0.65);">次</span>
                    </div>
                  </div>
                </div>
              </div>
              </div>
            </el-card>
          </el-col>
          <el-col :span="8" style="float:right">
            <el-card shadow="" class="shadow">
              <div class="imgsses">
                <img src="./../../assets/image/1x.png" style="position: absolute;top: 25%;left: 25%;"/>
              </div>
              <div class="rightmian">
              <div class="PingFang">
                <p class="PingFangtitle">民警精准画像</p>
                <p class="PingFangcontes" style="color:#e39588;">
                  <span class="Fangcontes">累计</span> {{goabroads}}
                  <span class="Fangcontes">人次</span>
                </p>
              </div>
              <div class="PingFangtabs">
                <div v-for="(items,m) in tabs2" :key="m" style="padding-top:5px;clear:both">
                  <div class="points" style="background: #e39588;"></div>
                  <div class="pointRegular" style="color: #e39588;">
                    <div class="leftbodytext">
                      <span style="PingFangSC-Regular;color:rgba(0,0,0,0.65);" :title="items.name">{{items.name}}</span>
                    </div>
                    <div class="rightbodytext">
                      {{items.count}}<span style="PingFangSC-Regular;color:rgba(0,0,0,0.65);">人/</span>
                      {{items.personTime}}<span style="PingFangSC-Regular;color:rgba(0,0,0,0.65);">次</span> 
                    </div>
                  </div>
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
						<div class="grid-content bg-purple-dark clearfix" style="text-align: -webkit-left; padding-left: 15px; font-size: 18px;"><span>使用情况</span></div>
					</el-col>
					<el-row>
					  	<el-col :span="24" class="echarts">
                <div style="width: 100%;height:385px;" v-if="this.databack == true" id="LoginStatistics"></div>
                <div style="width: 100%;height:385px;" v-if="this.databack == false" class="nodataback"></div>
              </el-col>
					</el-row>
				</el-row>
			</div>
		</div>
    <div class="containe">
			<div class="leave is-always-shadow">
        <el-row>
					<el-col :span="24" class="paddingbothse">
            <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
              <el-tab-pane label="总体累计" name="first">
                <el-col :span="24">
                  <el-col :span="10" class="paddingboths">
                    <img v-show="tableData.length>0?false:true" :src="require('./../../assets/image/noData.png')" class="noDataImg" />
                    <el-table v-show="tableData.length>0?true:false" :data="tableData" header-align = "center" style="width: 100%;">
                      <el-table-column align = "center" :show-overflow-tooltip="true" prop="RN" width="50" label="排名"></el-table-column>
                      <el-table-column align = "center" :show-overflow-tooltip="true" prop="name" label="单位"></el-table-column>
                      <el-table-column align = "center" :show-overflow-tooltip="true" prop="value" label="次数"></el-table-column>
                    </el-table>
                    <div class="pagination" v-show="tableData.length>0?true:false">
                        <el-pagination @current-change="handleCurrentChange" :current-page="cur_page" :page-size="pagesize"  layout="total, prev, pager, next, jumper" :total="inittableData">
                        </el-pagination>
                    </div>
                  </el-col>
                  <el-col :span="14" class="paddingboth">
                    <img v-show="tableDatas.length>0?false:true" :src="require('./../../assets/image/noData.png')" class="noDataImg" />
                    <el-table v-show="tableDatas.length>0?true:false" :data="tableDatas" header-align = "center" style="width: 100%;">
                      <el-table-column align = "center" :show-overflow-tooltip="true" prop="DEPT" label="单位"></el-table-column>
                      <el-table-column align = "center" :show-overflow-tooltip="true" prop="PERSON_NAME" label="姓名"></el-table-column>
                      <el-table-column align = "center" :show-overflow-tooltip="true" prop="POLICE_NO" label="警号"></el-table-column>
                      <el-table-column align = "center" :show-overflow-tooltip="true" prop="POST" label="职务"></el-table-column>
                      <el-table-column align = "center" :show-overflow-tooltip="true" prop="CNT" label="使用次数"></el-table-column>
                    </el-table>
                    <div class="pagination" v-show="tableDatas.length>0?true:false">
                        <el-pagination @current-change="handleCurrentChanges" :current-page="cur_pages" :page-size="pagesizes"  layout="total, prev, pager, next, jumper" :total="inittableDatas">
                        </el-pagination>
                    </div>
                  </el-col>
                </el-col>
              </el-tab-pane>
              <el-tab-pane label="队伍可视化" name="second">
                <el-col :span="24">
                  <el-col :span="10" class="paddingboths">
                    <img v-show="tableData.length>0?false:true" :src="require('./../../assets/image/noData.png')" class="noDataImg" />
                    <el-table v-show="tableData.length>0?true:false" :data="tableData" header-align = "center" style="width: 100%;">
                      <el-table-column align = "center" :show-overflow-tooltip="true" prop="RN" width="50" label="排名"></el-table-column>
                      <el-table-column align = "center" :show-overflow-tooltip="true" prop="name" label="单位"></el-table-column>
                      <el-table-column align = "center" :show-overflow-tooltip="true" prop="value" label="次数"></el-table-column>
                    </el-table>
                    <div class="pagination" v-show="tableData.length>0?true:false">
                        <el-pagination @current-change="handleCurrentChange" :current-page="cur_page" :page-size="pagesize"  layout="total, prev, pager, next, jumper" :total="inittableData">
                        </el-pagination>
                    </div>
                  </el-col>
                  <el-col :span="14" class="paddingboth">
                    <img v-show="tableDatas.length>0?false:true" :src="require('./../../assets/image/noData.png')" class="noDataImg" />
                    <el-table v-show="tableDatas.length>0?true:false" :data="tableDatas" header-align = "center" style="width: 100%;">
                      <el-table-column align = "center" :show-overflow-tooltip="true" prop="DEPT" label="单位"></el-table-column>
                      <el-table-column align = "center" :show-overflow-tooltip="true" prop="PERSON_NAME" label="姓名"></el-table-column>
                      <el-table-column align = "center" :show-overflow-tooltip="true" prop="POLICE_NO" label="警号"></el-table-column>
                      <el-table-column align = "center" :show-overflow-tooltip="true" prop="POST" label="职务"></el-table-column>
                      <el-table-column align = "center" :show-overflow-tooltip="true" prop="CNT" label="使用次数"></el-table-column>
                    </el-table>
                    <div class="pagination" v-show="tableDatas.length>0?true:false">
                        <el-pagination @current-change="handleCurrentChanges" :current-page="cur_pages" :page-size="pagesizes"  layout="total, prev, pager, next, jumper" :total="inittableDatas">
                        </el-pagination>
                    </div>
                  </el-col>
                </el-col>
              </el-tab-pane>
              <el-tab-pane label="队伍综合评估" name="third">
                <el-col :span="24">
                  <el-col :span="10" class="paddingboths">
                    <img v-show="tableData.length>0?false:true" :src="require('./../../assets/image/noData.png')" class="noDataImg" />
                    <el-table v-show="tableData.length>0?true:false" :data="tableData" header-align = "center" style="width: 100%;">
                      <el-table-column align = "center" :show-overflow-tooltip="true" prop="RN" width="50" label="排名"></el-table-column>
                      <el-table-column align = "center" :show-overflow-tooltip="true" prop="name" label="单位"></el-table-column>
                      <el-table-column align = "center" :show-overflow-tooltip="true" prop="value" label="次数"></el-table-column>
                    </el-table>
                    <div class="pagination" v-show="tableData.length>0?true:false">
                        <el-pagination @current-change="handleCurrentChange" :current-page="cur_page" :page-size="pagesize"  layout="total, prev, pager, next, jumper" :total="inittableData">
                        </el-pagination>
                    </div>
                  </el-col>
                  <el-col :span="14" class="paddingboth">
                    <img v-show="tableDatas.length>0?false:true" :src="require('./../../assets/image/noData.png')" class="noDataImg" />
                    <el-table v-show="tableDatas.length>0?true:false" :data="tableDatas" header-align = "center" style="width: 100%;">
                      <el-table-column align = "center" :show-overflow-tooltip="true" prop="DEPT" label="单位"></el-table-column>
                      <el-table-column align = "center" :show-overflow-tooltip="true" prop="PERSON_NAME" label="姓名"></el-table-column>
                      <el-table-column align = "center" :show-overflow-tooltip="true" prop="POLICE_NO" label="警号"></el-table-column>
                      <el-table-column align = "center" :show-overflow-tooltip="true" prop="POST" label="职务"></el-table-column>
                      <el-table-column align = "center" :show-overflow-tooltip="true" prop="CNT" label="使用次数"></el-table-column>
                    </el-table>
                    <div class="pagination" v-show="tableDatas.length>0?true:false">
                        <el-pagination @current-change="handleCurrentChanges" :current-page="cur_pages" :page-size="pagesizes"  layout="total, prev, pager, next, jumper" :total="inittableDatas">
                        </el-pagination>
                    </div>
                  </el-col>
                </el-col>
              </el-tab-pane>
              <el-tab-pane label="民警精准画像" name="fourth">
                <el-col :span="24">
                  <el-col :span="10" class="paddingboths">
                    <img v-show="tableData.length>0?false:true" :src="require('./../../assets/image/noData.png')" class="noDataImg" />
                    <el-table v-show="tableData.length>0?true:false" :data="tableData" header-align = "center" style="width: 100%;">
                      <el-table-column align = "center" :show-overflow-tooltip="true" prop="RN" width="50" label="排名"></el-table-column>
                      <el-table-column align = "center" :show-overflow-tooltip="true" prop="name" label="单位"></el-table-column>
                      <el-table-column align = "center" :show-overflow-tooltip="true" prop="value" label="次数"></el-table-column>
                    </el-table>
                    <div class="pagination" v-show="tableData.length>0?true:false">
                        <el-pagination @current-change="handleCurrentChange" :current-page="cur_page" :page-size="pagesize"  layout="total, prev, pager, next, jumper" :total="inittableData">
                        </el-pagination>
                    </div>
                  </el-col>
                  <el-col :span="14" class="paddingboth">
                    <img v-show="tableDatas.length>0?false:true" :src="require('./../../assets/image/noData.png')" class="noDataImg" />
                    <el-table v-show="tableDatas.length>0?true:false" :data="tableDatas" header-align = "center" style="width: 100%;">
                      <el-table-column align = "center" :show-overflow-tooltip="true" prop="DEPT" label="单位"></el-table-column>
                      <el-table-column align = "center" :show-overflow-tooltip="true" prop="PERSON_NAME" label="姓名"></el-table-column>
                      <el-table-column align = "center" :show-overflow-tooltip="true" prop="POLICE_NO" label="警号"></el-table-column>
                      <el-table-column align = "center" :show-overflow-tooltip="true" prop="POST" label="职务"></el-table-column>
                      <el-table-column align = "center" :show-overflow-tooltip="true" prop="CNT" label="使用次数"></el-table-column>
                    </el-table>
                    <div class="pagination" v-show="tableDatas.length>0?true:false">
                        <el-pagination @current-change="handleCurrentChanges" :current-page="cur_pages" :page-size="pagesizes"  layout="total, prev, pager, next, jumper" :total="inittableDatas">
                        </el-pagination>
                    </div>
                  </el-col>
                </el-col>
              </el-tab-pane>
            </el-tabs>
          </el-col>
        </el-row>
      </div>
		</div>
	</div>
</template>
<script>
    import { downloadFile } from "@/utils";

export default {
  data: function() {
    return {
      // applyType: [],
      applyTypevaule: [],
      applyType1: "",
      options: [],
      applyType2: "",
      tabs: [],
      tabs1: [],
      tabs2: [],
      tableData: [],
      inittableData: 0,
      databack: true,
      cur_page: 1, //表格当前页
      pagesize: 10,
      tableDatas: [],
      inittableDatas: 0,
      cur_pages: 1, //表格当前页
      pagesizes: 10,
      logocountmans: 0,
      logocounts: 0,
      pleseoffs: 0,
      goabroads: 0,
      applyTypesk: "",
      activeName: "first",
      props: { multiple: true },
       idNumberse: "320114196610232446"
//      idNumberse: ""
    };
  },
  created() {
//    let urls = window.location.href;
//    let idNumbersed = urls.split("=");
//    //加密解密
//    let Base64 = require("js-base64").Base64;
//    this.idNumberse = Base64.decode(idNumbersed[1]);
//    console.log(this.idNumberse);
    this.int();
  },
  watch: {},
  mounted() {},
  destroyed() {
    document.querySelector("body").style.height = "100%";
    document.querySelector("html").style.height = "100%";
  },
  methods: {
    int() {
      this.options = [];
      this.$axios
        .post("/common/unittreeAuto?idNumber=" + this.idNumberse)
        .then(res => {
          if (res.code == 0) {
            if (res.data && res.data.length > 0) {
              res.data.forEach(item => {
                let newVar = {
                  label: item.unitName,
                  value: item.id,
                  children: []
                  // leaf: true,
                };
                if (item.childCompany.length > 0) {
                  item.childCompany.forEach(element => {
                    let newVarse1 = {};
                    newVarse1 = this.chackese(element);
                    newVar.children.push(newVarse1);
                  });
                }
                this.options.push(newVar);
              });
              this.applyType1 = "本周";
              this.selects();
              this.selectse();
            }
          } else {
            this.$message.error(res.message);
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    //数据处理
    chackese(element) {
      let newVarse11 = {
        label: element.unitName,
        value: element.id,
        children: []
        // leaf: true,
      };
      if (element.childCompany.length > 0) {
        element.childCompany.forEach(itemse => {
          let newVarse111 = {};
          newVarse111 = this.chackese(itemse);
          newVarse11.children.push(newVarse111);
        });
      } else {
        newVarse11 = {
          label: element.unitName,
          value: element.id,
          children: null
          // leaf: true,
        };
      }
      return newVarse11;
    },
    changes() {
      let nodesObj = this.$refs["cascader"].getCheckedNodes();
      this.applyTypevaule = [];
      if (nodesObj.length > 0) {
        nodesObj.forEach(items => {
          let vaules = items.value;
          this.applyTypevaule.push(vaules);
        });
      }
    },
      visibleChange(change){
          if(!change){
              this.selectse();
          }
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
        die.substring(die.length - 2, die.length) +
        " " +
        "23:59:59";
      let miss = "0" + (start.getMonth() + 1);
      let dies = "0" + start.getDate();
      let datetimes =
        start.getFullYear() +
        "-" +
        miss.substring(miss.length - 2, miss.length) +
        "-" +
        dies.substring(dies.length - 2, dies.length) +
        " " +
        "00:00:00";
      this.applyType2 = [datetimes, datetime];
      this.selectse();
    },
    selectse() {
      this.loginDetail();
      this.goAbroadCount();
      this.pleseOffCount();
    },
    exportExcel(){
        var self = this;
        this.$axios
            .post("/userReport/export", {
                dateRange: self.applyType2,
                deptIds: self.applyTypevaule
            },{responseType: "arraybuffer"})
            .then(res => {
                downloadFile(res,"application/vnd.ms-excel", `用户使用日志.xls`)
            })
            .catch(function(error) {
                console.log(error);
            });
    },
    selectss() {
      this.applyType1 = "";
      console.log(this.applyType2);
    },
    loginChart(parems) {
      if (parems.countByDates.length > 0) {
        this.databack = true;
      } else {
        var myChartsse = this.$echarts.init(
          document.getElementById("LoginStatistics")
        );
        myChartsse.clear();
        this.databack = false;
      }
      setTimeout(() => {
        if (parems.countByDates.length > 0) {
          var myCharts = this.$echarts.init(
            document.getElementById("LoginStatistics")
          );
          var optionse = {
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
              data: []
            },
            grid: {
              left: "3%",
              right: "4%",
              bottom: "3%",
              containLabel: true
            },
            toolbox: {
              feature: {
                saveAsImage: {}
              }
            },
            xAxis: {
              type: "category",
              boundaryGap: false,
              data: []
            },
            yAxis: {
              type: "value"
            },
            series: []
          };
          optionse.xAxis.data = parems.days;
          parems.countByDates.forEach(itemse => {
            for (var key in itemse) {
              optionse.legend.data.push(key);
              optionse.series.push({
                name: key,
                type: "line",
                data: itemse[key]
              });
            }
          });
          console.log(optionse);
          myCharts.setOption(optionse, true);
        }
      }, 300);
    },
    handleCurrentChange(val) {
      this.cur_page = val;
      this.pleseOffCount();
    },
    handleCurrentChanges(val) {
      this.cur_pages = val;
      this.goAbroadCount();
    },
    loginDetail() {
      var self = this;
      this.$axios
        .post("/userReport/summary", {
          dateRange: self.applyType2,
          deptIds: self.applyTypevaule
        })
        .then(res => {
          if (res.code == 0) {
            this.logocounts = res.data.summary[0].total;
            this.tabs = res.data.summary[0].count;
            this.pleseoffs = res.data.summary[1].total;
            this.tabs1 = res.data.summary[1].count;
            this.goabroads = res.data.summary[2].total;
            this.tabs2 = res.data.summary[2].count;
            this.loginChart(res.data.summaryByDay);
          } else {
            this.$message.error(res.message);
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    goAbroadCount() {
      var self = this;
      let flagNames = "";
      if (this.activeName == "first") {
        flagNames = "";
      } else if (this.activeName == "second") {
        flagNames = "队伍可视化";
      } else if (this.activeName == "third") {
        flagNames = "队伍综合评估";
      } else if (this.activeName == "fourth") {
        flagNames = "民警精准画像";
      }
      this.$axios
        .post("/userReport/summaryByPeople", {
          dateRange: self.applyType2,
          deptIds: self.applyTypevaule,
          flagName: flagNames,
          pageSize: self.pagesizes,
          pageNo: self.cur_pages
        })
        .then(res => {
          if (res.code == 0) {
            this.tableDatas = res.data.rows;
            this.inittableDatas = res.data.total;
          } else {
            this.tableDatas = [];
            this.inittableDatas = 0;
            this.$message.error(res.message);
          }
        })
        .catch(function(error) {
          this.tableDatas = [];
          this.inittableDatas = 0;
          console.log(error);
        });
    },
    pleseOffCount() {
      var self = this;
      let flagNames = "";
      if (this.activeName == "first") {
        flagNames = "";
      } else if (this.activeName == "second") {
        flagNames = "队伍可视化";
      } else if (this.activeName == "third") {
        flagNames = "队伍综合评估";
      } else if (this.activeName == "fourth") {
        flagNames = "民警精准画像";
      }
      this.$axios
        .post("/userReport/summaryByDept", {
          dateRange: self.applyType2,
          deptIds: self.applyTypevaule,
          flagName: flagNames,
          pageSize: self.pagesize,
          pageNo: self.cur_page
        })
        .then(res => {
          if (res.code == 0) {
            this.tableData = res.data.rows;
            this.inittableData = res.data.total;
          } else {
            this.tableData = [];
            this.inittableData = 0;
            this.$message.error(res.message);
          }
        })
        .catch(function(error) {
          this.tableData = [];
          this.inittableData = 0;
          console.log(error);
        });
    },
    handleClick(tab, event) {
      console.log(tab, event);
      this.pagesizes = 10;
      this.cur_pages = 1;
      this.pagesize = 10;
      this.cur_page = 1;
      this.goAbroadCount();
      this.pleseOffCount();
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
  width: calc(100% - 15px);
  overflow: hidden;
}
.PingFangtabs {
  float: left;
  width: 65%;
  padding-left: 10px;
  padding-top: 15px;
  min-width: 147px;
  height: 100%;
}
.PingFang {
  float: left;
  width: 35%;
  padding-left: 10px;
  padding-top: 15px;
  min-width: 130px;
  height: 100%;
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
.rightmian {
  width: calc(100% - 100px);
  height: 100px;
  float: left;
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
  border-left: 1px solid #ccc;
}
.paddingboths {
  padding: 20px;
  border-right: 1px solid #ccc;
}
.paddingbothse {
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
.el-date-editor .el-range-separator {
  padding: 0 5px;
  line-height: 32px;
  width: 7%;
  color: #303133;
}
.nodataback {
  background: url("./../../assets/image/noData.png") no-repeat center;
  background-size: 20% 80%;
}
.minwidth {
  min-width: 1480px;
  /* overflow: auto; */
}
.leftbodytext {
  float: left;
  width: 60%;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.rightbodytext {
  float: right;
  width: 40%;
}
.el-cascader-menu__wrap {
    height: 400px;
}
</style>