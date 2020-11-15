<template>
    <div class="mianportrait">
        <div class="mianportrait">
            <el-row>
                <el-col :span="7">
                    <div class="mianportrait" style="min-height: 700px;">
                        <div class="leftseach">
                            <div class="fontstyle">添加对比人员</div>
                            <div class="leftseachss">
                                <div class="searchfont actives" @click="searchdata">全库搜索</div>
                                <div class="searchfont" @click="searchdatas">快速查询</div>
                            </div>
                            <div>
                              <div v-if="falg == '1'">
                                <div>
                                  <span style="margin: 20px 0 10px 0;">查询条件：</span>
                                  <el-cascader style="margin: 20px 0 10px 0;" :props="props" clearable  v-model="valuedata" @visible-change="slecktscader" :options="options" :show-all-levels="false" collapse-tags></el-cascader>
                                </div>
                                <!-- <div>
                                  <el-input placeholder="请输入内容" type="number" v-if="valuedata[0] == '同年龄'||valuedata[0] == '同入警年限'" v-model="inputsearchse" style="margin: 20px 0;" class="input-with-select">
                                    <el-button slot="append" icon="el-icon-search" @click="slecktssearch"></el-button>
                                  </el-input>
                                </div> -->
                              </div>
                              <el-input placeholder="请输入内容" v-if="falg == '0'" v-model="inputsearch" style="margin: 20px 0;" class="input-with-select">
                                <el-button slot="append" @click="leftsearch" icon="el-icon-search"></el-button>
                              </el-input>
                            </div>
                            <div style="width:100%;height:600px;">
                              <div style="width:100%;height:100%;">
                                <div style="width:100%;" class="lefttable">
                                    <el-table ref="tableDatase" :data="tableData" tooltip-effect="dark" style="width: 100%;background-color: #7375c8;" @select="handleSelectionChange" @select-all="handleSelectionselect">
                                      <el-table-column type="index" width="40"> </el-table-column>
                                      <el-table-column type="selection" width="40"> </el-table-column>
                                      <el-table-column prop="name" label="姓名" width="80"> </el-table-column>
                                      <el-table-column prop="deptName" label="工作单位" show-overflow-tooltip></el-table-column>
                                    </el-table>
                                    <el-pagination style="margin: 15px 0;" small @current-change="handleCurrentChange" :current-page.sync="currentPage1" :page-size="10" layout="total, prev, pager, next" :total="tatals">
                                    </el-pagination>
                                </div>
                              </div>
                            </div>
                        </div>
                    </div>
                </el-col>
                <el-col :span="17">
                  <div class="mianportrait">
                    <el-col :span="12">
                      <div class="mianportrait buttoncolor" style="width: 530px;;padding: 15px 15px;margin-left: 20px;">
                        <span>对比维度:</span>
                        <el-checkbox  style="color:#FFF;" @change="contrastdata" v-model="isAgese">职位变动</el-checkbox>
                        <el-checkbox  style="color:#FFF;" @change="contrastdata" v-model="isInPolicese">警衔晋升</el-checkbox>
                        <el-checkbox  style="color:#FFF;" @change="contrastdata" v-model="isToWorkse">表彰奖励</el-checkbox>
                        <!-- <el-checkbox  style="color:#FFF;" @change="contrastdata" v-model="isInPartyse">民意满意度</el-checkbox> -->
                      </div>
                    </el-col>
                    <el-col :span="12" style="text-align: -webkit-right;padding: 0px 20px;">
                      <div v-if="kes == '1'" style="padding-right: 30px;">
                        <span>对比人员:</span>
                        <img v-if="images.length>0" :src="images[0]" style="width: 40px; margin: 0 10px;"/>
                        <img v-if="images.length>1" :src="images[1]" style="width: 40px;"/>
                      </div>
                    </el-col>
                    <el-col :span="24">
                      <el-col :span="12">
                        <div class="mianportrait contrastbox" style="text-align: -webkit-left;padding: 10px 10px 10px 25px;">
                          <el-date-picker
                            v-model="year"
                            type="year"
                            placeholder="选择年"
                            @change="contrastdata"
                            format="yyyy"
                            value-format='yyyy'
                            :editable='false'
                            :clearable='false'>
                          </el-date-picker>
                        </div>
                      </el-col>
                      <el-col :span="12">
                        <div class="mianportrait" style="text-align: -webkit-right;padding: 10px 0px 10px 0;">
                          <div v-if="kes == '2'" style="padding-right: 30px;" class="exportbutton"><el-button type="primary" class="exportbuttoncolor" @click="dataexport">数据导出</el-button></div>
                        </div>
                      </el-col>
                      <div v-if="kes == '1'" class="mianportrait" style="text-align: -webkit-center;">
                        <div class="rightearchts">
                          <div  class="rightearchtnext">
                            <div  class="baright1">
                              <div style="text-align:left;padding: 10px 20px;">
                                <img src="../../assets/image/portasts/zidb.png" style="width: 20px;margin-right: 10px;" /><span>职位变动</span>
                              </div>
                              <div id="dutyAndYearVo" v-if="isAgese== true&&this.tableDataleftse.length > 1" style="width:100%;height:355px;background: #fff;"></div>
                            </div>
                          </div>
                          <div  class="rightearchtnext">
                            <div  class="baright2">
                              <div style="text-align:left;padding: 10px 20px;">
                                <img src="../../assets/image/portasts/policerankman.png"  style="width: 20px;margin-right: 10px;"/><span>警衔晋升</span>
                              </div>
                              <div id="policeRankAndYearVo" v-if="isInPolicese== true&&this.tableDataleftse.length > 1" style="width:100%;height:355px;background: #fff;"></div>
                            </div>
                          </div>
                          <div  class="rightearchtnext">
                            <div  class="baright3">
                              <div style="text-align:left;padding: 10px 20px;">
                                <img src="../../assets/image/portasts/recognitionkman.png"  style="width: 20px;margin-right: 10px;"/><span>表彰奖励</span>
                              </div>
                              <div id="assessAndYearVo" v-if="isToWorkse== true&&this.tableDataleftse.length > 1" style="width:100%;height:355px;background: #fff;"></div>
                            </div>
                          </div>
                          <!-- <div  class="rightearchtnext">
                            <div  class="baright4">
                              <div style="text-align:left;padding: 10px 20px;">
                                <img src="../../assets/image/portasts/opinionsatisfactionman.png"  style="width: 20px;margin-right: 10px;"/><span>民意满意度</span>
                              </div>
                              <div id="myAndMonthVo" v-if="isInPartyse== true&&this.tableDataleftse.length > 1" style="width:100%;height:355px;background: #fff;"></div>  
                            </div>                          
                          </div> -->
                        </div>
                      </div>
                      <div v-if="kes == '2'" class="mianportrait" style="text-align: -webkit-center;">
                        <el-table :data="tableDatase" style="width: 96%" height="600" stripe class="tablestyle" :default-sort = "{prop: 'date', order: 'descending'}">
                          <el-table-column  type="index" label="排序" width="50"></el-table-column>
                          <el-table-column prop="name" label="人员姓名"></el-table-column>
                          <el-table-column prop="deptName" label="单位"></el-table-column>
                          <el-table-column  prop="age" label="年龄"></el-table-column>
                          <el-table-column v-if=" isAgese==true"  prop="duty" label="职位" sortable></el-table-column>
                          <el-table-column v-if=" isToWorkse==true"  prop="assess" label="表彰奖励" sortable></el-table-column>
                          <el-table-column v-if=" isInPolicese==true"  prop="policeRank" label="警衔" sortable></el-table-column>
                          <!-- <el-table-column v-if=" isInPartyse==true"  prop="opinion" label="民意评价" sortable></el-table-column> -->
                        </el-table>

                        <table  id="tipMessageLabel" v-show="false">
                          <tr>
                              <th>人员姓名</th>
                              <th>单位</th>
                              <th>年龄</th>
                              <th>职位</th>
                              <th>表彰奖励</th>
                              <th>警衔</th>
                              <!-- <th>民意评价</th> -->
                            </tr>
                            <tr v-for="item in multipleSelection">
                              <td>{{item.name}}</td>
                              <td>{{item.deptName}}</td>
                              <td>{{item.age}}</td>
                              <td>{{item.duty}}</td>
                              <td>{{item.assess}}</td>
                              <td>{{item.policeRank}}</td>
                              <!-- <td>{{item.opinion}}</td> -->
                            </tr>
                      </table>
                      </div>
                    </el-col>
                  </div>
                </el-col>
            </el-row>
        </div>
    </div>
</template>
<script>
import { Base64 } from "js-base64";
import { getUrlParams } from "@/utils";
import { setTimeout } from "timers";
export default {
  props: [],
  name: "teamAnalysisportrait",
  data: function() {
    return {
      falg: 0,
      inputsearch: "",
      inputsearchse: "",
      props: { multiple: true },
      options: [
        {
          value: "同年龄段",
          label: "同年龄",
          children: []
        },
        {
          value: "同入警年限",
          label: "同入警年限",
          children: []
        },
        {
          value: "同职位",
          label: "同职位",
          children: [
            {
              value: "职务",
              label: "职务",
              children: []
            },
            {
              value: "职级",
              label: "职级",
              children: []
            }
          ]
        },
        {
          value: "同警衔",
          label: "同警衔",
          children: []
        },
        {
          value: "同学历",
          label: "同学历",
          children: []
        },
        {
          value: "同入警来源",
          label: "同入警来源",
          children: []
        }
      ],
      valuedata: [],
      valuedatase: "",
      isAgese: true,
      isInPolicese: true,
      isToWorkse: true,
      isInPartyse: true,
      year: "",
      kes: "1",
      tableDatase: [],
      tableDataQJGK: [],
      tableData: [],
      tableDataleft: [],
      tableDataleftse: [],
      multipleSelection: [],
      tatals: 0,
      currentPage1: 1,
      dutyAndYearVo: null,
      policeRankAndYearVoearchts: null,
      assessAndYearVoearchts: null,
      myAndMonthVoearchts: null,
      checked: false,
      vehiclevaule0: false,
      vehiclevaule1: false,
      vehiclevaule2: false,
      vehiclevaule3: false,
      vehiclevaule4: false,
      vehiclevaule5: false,
      vehiclevaule6: false,
      vehiclevaule7: false,
      vehiclevaule8: false,
      vehiclevaule9: false,
      images: [],
      idNumberse: ""
    };
  },
  watch: {},
  mounted() {
    const params = getUrlParams(location.hash);
    const idNumber = Base64.decode(params.no || "");
    this.idNumberse = idNumber;
    let yeardata = new Date();
    this.year = yeardata.getFullYear().toString();
    setTimeout(() => {
      this.getPersonLog("队伍人员画像对比");
      this.getPeople();
      this.getSearchParam();
    }, 300);
  },
  methods: {
    searchdata() {
      this.valuedata = [];
      this.falg = 0;
      let searchNav = document.querySelectorAll(".leftseachss>.searchfont");
      for (let k = 0; k < searchNav.length; k++) {
        k == 0
          ? searchNav[k].classList.add("actives")
          : searchNav[k].classList.remove("actives");
      }
      this.leftsearch();
    },
    searchdatas() {
      this.falg = 1;
      let searchNavs = document.querySelectorAll(".leftseachss>.searchfont");
      for (let index = 0; index < searchNavs.length; index++) {
        index == 1
          ? searchNavs[index].classList.add("actives")
          : searchNavs[index].classList.remove("actives");
      }
      this.tableDataleft = [];
      this.tatals = 0;
      this.tableData = [];
    },
    contrastdata() {
      if (this.tableDataleftse.length > 2) {
        // this.kes = "2";
        this.companyTwoMore();
      } else if (this.tableDataleftse.length == 2) {
        // this.kes = "1";
        this.companyTwoPeople();
      }
    },
    //表格数据导出
    dataexport() {
      this.multipleSelection = [];
      this.multipleSelection = this.tableDatase;
      // 生成表格
      setTimeout(() => {
        $("#tipMessageLabel").table2excel({
          // 不被导出的表格行的CSS class类
          exclude: ".noExl",
          name: "Excel Document Name",
          filename: "队伍人员画像对比.xls"
        });
      }, 1000);
    },
    //左侧表格分页
    handleCurrentChange(item) {
      this.tableData = this.tableDataleft.slice((item - 1) * 10, 10 * item);
      setTimeout(() => {
        if (this.tableDataleftse.length > 0) {
          this.tableData.forEach((items, k) => {
            this.tableDataleftse.forEach(itemse => {
              if (items.idNo == itemse.idNo) {
                this.$refs.tableDatase.toggleRowSelection(items, true);
              }
            });
          });
        }
      }, 200);
    },
    //查询人员
    getPeople() {
        let urls = window.location.href;
        let idNumbersed = urls.split("=");
        //加密解密
        let Base64 = require("js-base64").Base64;
        this.idNumberse = Base64.decode(idNumbersed[1]);

      this.$axios
        .get("/perComparison/getPeople?idNumber="+this.idNumberse+"&query=" + this.inputsearch)
        .then(res => {
          if (res.code == 0) {
            this.tableDataleft = res.data;
            this.tatals = res.data.length;
            this.tableData = res.data.slice(0, 10);
            setTimeout(() => {
              if (this.tableDataleftse.length > 0) {
                this.tableData.forEach(items => {
                  this.tableDataleftse.forEach(itemse => {
                    if (items.idNo == itemse.idNo) {
                      this.$refs.tableDatase.toggleRowSelection(items, true);
                    }
                  });
                });
              }
            }, 300);
          } else {
            this.$message(res.message);
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    //左侧输入框搜索
    leftsearch() {
      var pattern = new RegExp(
        "[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？]"
      );
      var rs = 0;
      for (var i = 0; i < this.inputsearch.length; i++) {
        let s = this.inputsearch.substr(i, 1).replace(pattern, "");
        let se = s.match(/\ /g);
        if (!s || se) {
          rs++;
        }
      }
      if (rs == 0) {
        this.getPeople();
      } else {
        this.inputsearch = "";
        this.$message("不可输入特殊字符！");
      }
    },
    //对比两个民警
    companyTwoPeople() {
      let idNumbers = [];
      let namese = [];
      let isAssesse = this.isToWorkse == true ? "1" : "0";
      let isDutye = this.isAgese == true ? "1" : "0";
      // let isOpinione = this.isInPartyse == true ? "1" : "0";
      let isPoliceRanke = this.isInPolicese == true ? "1" : "0";
      this.tableDataleftse.forEach(itense => {
        idNumbers.push(itense.idNo);
        namese.push(itense.name);
      });
      this.$axios
        .post("/perComparison/companyTwoPeople", {
          idNumber: idNumbers,
          isAssess: isAssesse,
          isDuty: isDutye,
          // isOpinion: isOpinione,
          isOpinion: "0",
          isPoliceRank: isPoliceRanke,
          year: this.year
        })
        .then(res => {
          if (res.code == 0) {
            //职位变动
            let yearse = res.data.years;
            if (res.data.dutyAndYearVo) {
              this.dutyAndYearVo = this.$echarts.init(
                document.getElementById("dutyAndYearVo")
              );
              let dutyColumn = res.data.dutyAndYearVo.dutyColumn;
              let line1 = [];
              let line2 = [];
              yearse.forEach(itemsse => {
                if (res.data.dutyAndYearVo.dutyMap[idNumbers[0]][itemsse]) {
                  let listzts = dutyColumn.indexOf(
                    res.data.dutyAndYearVo.dutyMap[idNumbers[0]][itemsse]
                  );
                  line1.push(listzts);
                } else {
                  line1.push(0);
                }
                if (res.data.dutyAndYearVo.dutyMap[idNumbers[1]][itemsse]) {
                  let listztss = dutyColumn.indexOf(
                    res.data.dutyAndYearVo.dutyMap[idNumbers[1]][itemsse]
                  );
                  line2.push(listztss);
                } else {
                  line2.push(0);
                }
              });
              let optiondutyAndYearVo = {
                tooltip: {
                  trigger: "axis",
                  formatter: function(res) {
                    return (
                      "<div style='text-align:left;'>" +
                      res[0].name +
                      "</br>" +
                      res[0].seriesName +
                      ":" +
                      dutyColumn[res[0].value] +
                      "</br>" +
                      res[1].seriesName +
                      ":" +
                      dutyColumn[res[1].value] +
                      "</div>"
                    );
                  }
                },
                legend: {
                  data: namese
                },
                grid: {
                  left: "1%",
                  right: "1%",
                  bottom: "3%",
                  containLabel: true
                },
                xAxis: {
                  type: "category",
                  boundaryGap: false,
                  data: yearse,
                  axisLabel: {
                    rotate: 60
                  }
                },
                yAxis: {
                  type: "value",
                  axisLabel: {
                    formatter: function(value) {
                      return dutyColumn[value];
                    }
                    //  rotate: 45
                  }
                },
                series: [
                  {
                    name: namese[0],
                    type: "line",
                    color: "#2fdade",
                    data: line1
                  },
                  {
                    name: namese[1],
                    type: "line",
                    color: "#a4b9f8",
                    data: line2
                  }
                ]
              };
              this.dutyAndYearVo.setOption(optiondutyAndYearVo);
            }
            //警衔晋升
            if (res.data.policeRankAndYearVo) {
              this.policeRankAndYearVoearchts = this.$echarts.init(
                document.getElementById("policeRankAndYearVo")
              );
              let column = res.data.policeRankAndYearVo.column;
              let lineRank1 = [];
              let lineRank2 = [];
              yearse.forEach(items => {
                if (
                  res.data.policeRankAndYearVo.policeRankMap[idNumbers[0]][
                    items
                  ]
                ) {
                  let listzts = column.indexOf(
                    res.data.policeRankAndYearVo.policeRankMap[idNumbers[0]][
                      items
                    ]
                  );
                  lineRank1.push(listzts);
                } else {
                  lineRank1.push(0);
                }
                if (
                  res.data.policeRankAndYearVo.policeRankMap[idNumbers[1]][
                    items
                  ]
                ) {
                  let listztss = column.indexOf(
                    res.data.policeRankAndYearVo.policeRankMap[idNumbers[1]][
                      items
                    ]
                  );
                  lineRank2.push(listztss);
                } else {
                  lineRank2.push(0);
                }
              });
              let optionpoliceRankAndYearVo = {
                tooltip: {
                  trigger: "axis",
                  formatter: function(res) {
                    return (
                      "<div style='text-align:left;'>" +
                      res[0].name +
                      "</br>" +
                      res[0].seriesName +
                      ":" +
                      column[res[0].value] +
                      "</br>" +
                      res[1].seriesName +
                      ":" +
                      column[res[1].value] +
                      "</div>"
                    );
                  }
                },
                legend: {
                  data: namese
                },
                grid: {
                  left: "1%",
                  right: "1%",
                  bottom: "3%",
                  containLabel: true
                },
                xAxis: {
                  type: "category",
                  boundaryGap: false,
                  data: yearse,
                  axisLabel: {
                    rotate: 60
                  }
                },
                yAxis: {
                  type: "value",
                  axisLabel: {
                    formatter: function(value) {
                      return column[value];
                    }
                  }
                },
                series: [
                  {
                    name: namese[0],
                    type: "line",
                    color: "#a4b9f8",
                    data: lineRank1
                  },
                  {
                    name: namese[1],
                    type: "line",
                    color: "#2fdade",
                    data: lineRank2
                  }
                ]
              };
              this.policeRankAndYearVoearchts.setOption(
                optionpoliceRankAndYearVo
              );
            }
            //表彰奖励
            if (res.data.assessAndYearVo) {
              this.assessAndYearVoearchts = this.$echarts.init(
                document.getElementById("assessAndYearVo")
              );
              let lineRank11 = [];
              let lineRank21 = [];
              yearse.forEach(items1 => {
                if (
                  res.data.assessAndYearVo.policeRankMap[idNumbers[0]][items1]
                ) {
                  lineRank11.push(
                    res.data.assessAndYearVo.policeRankMap[idNumbers[0]][items1]
                  );
                } else {
                  lineRank11.push(0);
                }
                if (
                  res.data.assessAndYearVo.policeRankMap[idNumbers[1]][items1]
                ) {
                  lineRank21.push(
                    res.data.assessAndYearVo.policeRankMap[idNumbers[1]][items1]
                  );
                } else {
                  lineRank21.push(0);
                }
              });
              let optionassessAndYearVo = {
                tooltip: {
                  trigger: "axis"
                },
                legend: {
                  data: namese
                },
                grid: {
                  left: "1%",
                  right: "1%",
                  bottom: "3%",
                  containLabel: true
                },
                calculable: true,
                xAxis: [
                  {
                    type: "category",
                    data: yearse,
                    axisLabel: {
                      rotate: 60
                    }
                  }
                ],
                yAxis: [
                  {
                    type: "value"
                  }
                ],
                series: [
                  {
                    name: namese[0],
                    type: "bar",
                    color: "#f6dd86",
                    data: lineRank11
                  },
                  {
                    name: namese[1],
                    type: "bar",
                    color: "#ff9899",
                    data: lineRank21
                  }
                ]
              };
              this.assessAndYearVoearchts.setOption(optionassessAndYearVo);
            }
            //民意满意度
            if (res.data.myAndMonthVo) {
              this.myAndMonthVoearchts = this.$echarts.init(
                document.getElementById("myAndMonthVo")
              );
              let lineRank13 = [];
              let lineRank23 = [];
              res.data.myAndMonthVo.months.forEach(items21 => {
                lineRank13.push(
                  res.data.myAndMonthVo.mymydMap[idNumbers[0]][items21]
                );
                lineRank23.push(
                  res.data.myAndMonthVo.mymydMap[idNumbers[1]][items21]
                );
              });
              let optionmyAndMonthVo = {
                tooltip: {
                  trigger: "axis",
                  axisPointer: {
                    type: "shadow"
                  }
                },
                grid: {
                  left: "1%",
                  right: "1%",
                  bottom: "3%",
                  containLabel: true
                },
                legend: {
                  data: namese
                },
                xAxis: {
                  type: "value",
                  boundaryGap: [0, 0.01]
                },
                yAxis: {
                  type: "category",
                  data: [
                    "1月",
                    "2月",
                    "3月",
                    "4月",
                    "5月",
                    "6月",
                    "7月",
                    "8月",
                    "9月",
                    "10月",
                    "11月",
                    "12月"
                  ]
                },
                series: [
                  {
                    name: namese[0],
                    type: "bar",
                    color: "#85b1fa",
                    data: lineRank13
                  },
                  {
                    name: namese[1],
                    type: "bar",
                    color: "#ff9898",
                    data: lineRank23
                  }
                ]
              };
              this.myAndMonthVoearchts.setOption(optionmyAndMonthVo);
            }
          } else {
            this.$message(res.message);
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    //对比两个以上民警
    companyTwoMore() {
      let idNumbers = [];
      let isAssesse = this.isToWorkse == true ? "1" : "0";
      let isDutye = this.isAgese == true ? "1" : "0";
      // let isOpinione = this.isInPartyse == true ? "1" : "0";
      let isPoliceRanke = this.isInPolicese == true ? "1" : "0";
      this.tableDataleftse.forEach(itense => {
        idNumbers.push(itense.idNo);
      });
      this.$axios
        .post("/perComparison/companyTwoMore", {
          idNumber: idNumbers,
          isAssess: isAssesse,
          isDuty: isDutye,
          // isOpinion: isOpinione,
          isOpinion: "0",
          isPoliceRank: isPoliceRanke,
          year: this.year
        })
        .then(res => {
          if (res.code == 0) {
            this.tableDatase = res.data;
          } else {
            this.$message(res.message);
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    //两个警员时的警员画像
    getImgPath(itense) {
      this.$axios
        .get("/perComparison/getImgPath?idNumber=" + itense.idNo)
        .then(res => {
          if (res.code == 0) {
            this.images.push(res.data);
          } else {
            this.$message(res.message);
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    //搜索条件查询
    getSearchParam() {
      let parmse = [
        {
          value: "同年龄段",
          label: "同年龄"
        },
        {
          value: "同入警年限",
          label: "同入警年限"
        },
        {
          value: "同职位",
          label: "同职位"
        },
        {
          value: "同警衔",
          label: "同警衔"
        },
        {
          value: "同学历",
          label: "同学历"
        },
        {
          value: "同入警来源",
          label: "同入警来源"
        }
      ];
      for (let kss = 0; kss < parmse.length; kss++) {
        this.$axios
          .get("/perComparison/getSearchParam?type=" + parmse[kss].value)
          .then(res => {
            if (res.code == 0) {
              if (parmse[kss].value == "同职位") {
                console.log(res.data.duty);
                res.data.duty.forEach(items => {
                  this.options[2].children[0]["children"].push({
                    value: items,
                    label: items
                  });
                });
                for (let key in res.data.dutyRank) {
                  let childrense = [];
                  for (let keys in res.data.dutyRank[key]) {
                    childrense.push({
                      value: res.data.dutyRank[key][keys],
                      label: res.data.dutyRank[key][keys]
                    });
                  }
                  this.options[2].children[1]["children"].push({
                    value: key,
                    label: key,
                    children: childrense
                  });
                }
              } else if (parmse[kss].value == "同警衔") {
                res.data.policeRank.forEach(itemsd => {
                  this.options[3]["children"].push({
                    value: itemsd,
                    label: itemsd
                  });
                });
              } else if (parmse[kss].value == "同学历") {
                res.data.education.forEach(itemsd => {
                  this.options[4]["children"].push({
                    value: itemsd,
                    label: itemsd
                  });
                });
              } else if (parmse[kss].value == "同入警来源") {
                res.data.source.forEach(itemsd => {
                  this.options[5]["children"].push({
                    value: itemsd,
                    label: itemsd
                  });
                });
              } else if (parmse[kss].value == "同年龄段") {
                res.data.ageScope.forEach(itemsd => {
                  this.options[0]["children"].push({
                    value: itemsd,
                    label: itemsd
                  });
                });
              } else if (parmse[kss].value == "同入警年限") {
                res.data.timeWork.forEach(itemsd => {
                  this.options[1]["children"].push({
                    value: itemsd,
                    label: itemsd
                  });
                });
              }
            } else {
              this.$message(res.message);
            }
          })
          .catch(function(error) {
            console.log(error);
          });
      }
    },
    //左侧下拉框选择
    slecktscader(item) {
      console.log(item);
      // this.inputsearchse = "";
      if (item == false) {
        this.quickSearch();
      }
    },
    //左侧快速输入框
    // slecktssearch() {
    //   if (this.inputsearchse) {
    //     this.quickSearch(this.inputsearchse);
    //   } else {
    //     this.$message("搜索条件不可为空！");
    //   }
    // },
    //quickSearch
    quickSearch() {
      let paramse = "";
      let types = "";
      let datases = [];
      if (this.valuedata.length > 0) {
        this.valuedata.forEach(itemse => {
          if (itemse[0] == "同职位") {
            datases.push({ param: itemse[itemse.length - 1], type: itemse[1] });
          } else {
            datases.push({ param: itemse[1], type: itemse[0] });
          }
        });
      }
      console.log(datases);
      this.$axios
        .post("/perComparison/quickSearch?idNumber="+this.idNumberse, {
          // param: "",
          // type: "",
          param: datases
        })
        .then(res => {
          if (res.code == 0) {
            this.tableDataleft = res.data;
            this.tatals = res.data.length;
            this.tableData = res.data.slice(0, 10);
            if (this.tableDataleftse.length > 0) {
              this.tableData.forEach(items => {
                this.tableDataleftse.forEach(itemse => {
                  if (items.idNo == itemse.idNo) {
                    this.$refs.tableDatase.toggleRowSelection(items, true);
                  }
                });
              });
            }
          } else {
            this.$message(res.message);
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    //表格多选框
    handleSelectionChange(selection, row) {
      if (this.tableDataleftse.length == 0) {
        this.tableDataleftse.push(row);
      } else {
        let nones = true;
        this.tableDataleftse.forEach((itemss, m) => {
          if (itemss.idNo == row.idNo) {
            nones = false;
            this.tableDataleftse.splice(m, 1);
          }
        });
        if (nones == true) {
          this.tableDataleftse.push(row);
        }
      }
      if (this.tableDataleftse.length > 2) {
        this.kes = "2";
        this.companyTwoMore();
      } else if (this.tableDataleftse.length == 2) {
        this.kes = "1";
        this.companyTwoPeople();
      }
      if (
        this.tableDataleftse.length == 1 ||
        this.tableDataleftse.length == 2
      ) {
        this.images = [];
        for (let maxs = 0; maxs < this.tableDataleftse.length; maxs++) {
          this.getImgPath(this.tableDataleftse[maxs]);
        }
      } else if (this.tableDataleftse.length == 0) {
        this.images = [];
      }
    },
    //表格全选框
    handleSelectionselect(selection) {
      if (selection.length > 0) {
        if (this.tableDataleftse.length == 0) {
          selection.forEach(itmes => {
            this.tableDataleftse.push(itmes);
          });
        } else {
          selection.forEach(itmese => {
            let nones = true;
            this.tableDataleftse.forEach((itemss, m) => {
              if (itemss.idNo == itmese.idNo) {
                nones = false;
              }
            });
            if (nones == true) {
              this.tableDataleftse.push(itmese);
            }
          });
        }
      } else {
        let nome = [];
        this.tableData.forEach(itmese => {
          this.tableDataleftse.forEach((itemss, m) => {
            if (itemss.idNo == itmese.idNo) {
              nome.push(m);
            }
          });
        });
        nome.sort(function(a, b) {
          return b - a;
        });
        nome.forEach(item => {
          this.tableDataleftse.splice(item, 1);
        });
      }
      if (this.tableDataleftse.length > 2) {
        this.kes = "2";
        this.companyTwoMore();
      } else if (this.tableDataleftse.length == 2) {
        this.kes = "1";
        this.companyTwoPeople();
      }
      if (
        this.tableDataleftse.length == 1 ||
        this.tableDataleftse.length == 2
      ) {
        this.images = [];
        for (let maxs = 0; maxs < this.tableDataleftse.length; maxs++) {
          this.getImgPath(this.tableDataleftse[maxs]);
        }
      } else if (this.tableDataleftse.length == 0) {
        this.images = [];
        this.tableDatase = [];
        this.kes = "1";
      }
    },
    //判断那个页面
    getPersonLog(params) {
      this.$axios
        .post("/log/getPersonLog", {
          flagName: "队伍综合评估",
          idNumber: this.idNumberse,
          pageName: params
        })
        .then(res => {
          if (res.code == 0) {
            console.log(res.message);
          } else {
            this.$message(res.message);
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    }
  }
};
</script>

<style scoped>
.mianportrait {
  width: 100%;
  clear: both;
  font-size: 16px;
}
.leftseach {
  width: 90%;
  min-width: 340px;
  min-height: 660px;
  margin: 0 auto;
  background-color: #7375c8;
  /* opacity: 0.8; */
  border-radius: 20px;
  color: #fff;
  text-align: center;
}
.fontstyle {
  height: 40px;
  line-height: 40px;
  font-weight: 700;
}
.searchfont {
  float: left;
  text-align: center;
  width: 50%;
  color: #dddbdb;
  line-height: 40px;
  cursor: pointer;
}
.actives {
  color: #fff;
  border-bottom: 2px solid #fff;
}
/deep/ .el-input-group {
  line-height: normal;
  display: inline-table;
  width: 90%;
  border-collapse: separate;
  border-spacing: 0;
}
/deep/ .el-input__inner {
  -webkit-appearance: none;
  background-color: #fff;
  background-image: none;
  border-radius: 4px;
  border: 1px solid #dcdfe6;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
  color: #606266;
  display: inline-block;
  font-size: inherit;
  height: 30px;
  line-height: 30px;
  outline: 0;
  padding: 0 0 0 15px;
  -webkit-transition: border-color 0.2s cubic-bezier(0.645, 0.045, 0.355, 1);
  transition: border-color 0.2s cubic-bezier(0.645, 0.045, 0.355, 1);
  width: 100%;
}
/deep/ .el-input-group--append .el-input__inner,
.el-input-group__prepend {
  border-top-right-radius: 0;
  border-bottom-right-radius: 0;
  opacity: 0.8;
}
/deep/ .el-input-group__append {
  border-left: 0;
  opacity: 0.8;
}
/deep/ .el-input__icon {
  height: 100%;
  width: 25px;
  text-align: center;
  -webkit-transition: all 0.3s;
  transition: all 0.3s;
  line-height: 30px;
}
.buttoncolor {
  color: #fff;
  background-color: #7375c8;
  border-color: #7375c8;
  border-radius: 20px;
}
/deep/.el-input--suffix .el-input__inner {
  padding-right: 30px;
  background: #7375c8;
  color: #fff;
}
/deep/.el-input__inner {
  -webkit-appearance: none;
  background-color: #fff;
  background-image: none;
  border-radius: 4px;
  border: 1px solid #dcdfe6;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
  color: #606266;
  display: inline-block;
  font-size: inherit;
  height: 30px;
  line-height: 30px;
  outline: 0;
  padding: 0 0 0 30px;
  -webkit-transition: border-color 0.2s cubic-bezier(0.645, 0.045, 0.355, 1);
  transition: border-color 0.2s cubic-bezier(0.645, 0.045, 0.355, 1);
  width: 100%;
}
.exportbutton /deep/.el-button {
  display: inline-block;
  line-height: 1;
  white-space: nowrap;
  cursor: pointer;
  background: #fff;
  border: 1px solid #dcdfe6;
  color: #606266;
  -webkit-appearance: none;
  text-align: center;
  box-sizing: border-box;
  outline: 0;
  margin: 0;
  -webkit-transition: 0.1s;
  transition: 0.1s;
  font-weight: 500;
  -moz-user-select: none;
  padding: 10px 20px;
  font-size: 13px;
  border-radius: 4px;
}
.exportbutton .exportbuttoncolor {
  color: #fff;
  background-color: #7375c8;
  border-color: #7375c8;
}
.rightearchts {
  width: 100%;
  min-width: 840px;
  height: 760px;
}
.rightearchtnext {
  width: 45%;
  height: 400px;
  float: left;
  background: #fff;
  margin: 10px 2%;
  border-radius: 15px;
}
.baright1 {
  width: 100%;
  height: 100%;
  background: url("../../assets/image/portasts/矢量智能对象-1.png") no-repeat
    center;
  background-size: 50%;
}
.baright2 {
  width: 100%;
  height: 100%;
  background: url("../../assets/image/portasts/矢量智能对象-3.png") no-repeat
    center;
  background-size: 50%;
}
.baright3 {
  width: 100%;
  height: 100%;
  background: url("../../assets/image/portasts/矢量智能对象-2.png") no-repeat
    center;
  background-size: 50%;
}
.baright4 {
  width: 100%;
  height: 100%;
  background: url("../../assets/image/portasts/矢量智能对象.png") no-repeat
    center;
  background-size: 50%;
}
.lefttable /deep/.el-table th,
.el-table tr {
  background-color: #7375c8;
  color: #fff;
}
.lefttable /deep/.el-pager li {
  padding: 0 4px;
  font-size: 13px;
  min-width: 22px;
  height: 28px;
  line-height: 28px;
  box-sizing: border-box;
  text-align: center;
  background: #7375c8;
}
.lefttable /deep/.el-pagination button:disabled {
  color: #c0c4cc;
  background-color: #7375c8;
  cursor: not-allowed;
}
.lefttable /deep/.el-table tr {
  background-color: #7375c8;
  color: #fff;
  border: 1 solid #7375c8;
}
.lefttable /deep/.el-table tr:hover {
  color: #000;
}
.lefttable /deep/.el-pagination .btn-next,
.el-pagination .btn-prev {
  background: center center no-repeat #7375c8;
  background-size: 16px;
  cursor: pointer;
  margin: 0;
  color: #fff;
}
.lefttable /deep/ .el-pagination .btn-prev {
  background: center center no-repeat #7375c8;
  background-size: 16px;
  cursor: pointer;
  margin: 0;
  color: #fff;
}
.lefttable /deep/ .el-pagination__total,
.el-pagination {
  color: #fff;
}
.lefttable /deep/ .el-pager li.btn-quicknext {
  color: #fff;
}
.lefttable /deep/ .el-pager li.btn-quickprev {
  color: #fff;
}
</style>