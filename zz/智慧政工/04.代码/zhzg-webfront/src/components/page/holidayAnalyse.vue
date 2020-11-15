<template>
    <!--    <div>-->
    <!--        <el-table-->
    <!--            ref="multipleTable"-->
    <!--            :data="tableData"-->
    <!--            tooltip-effect="dark"-->
    <!--            style="width: 100%">-->
    <!--            <el-table-column  width="55">-->
    <!--                <template slot="header" slot-scope="scope">-->
    <!--                    <el-checkbox v-model="checked" @change.native.stop="hahaa()">&nbsp;</el-checkbox>-->
    <!--&lt;!&ndash;                    <el-checkbox v-model="idarr"  @change.native.stop="hahaa()">&nbsp;</el-checkbox>&ndash;&gt;-->
    <!--                </template>-->
    <!--                <template slot-scope="scope">-->
    <!--                    <el-checkbox v-model="idarr" :label="scope.row.id" @change.native.stop="haha(scope, scope.row)" :key="scope.id">&nbsp;</el-checkbox>-->
    <!--                </template>-->
    <!--            </el-table-column>-->
    <!--            <el-table-column-->
    <!--                label="id"-->
    <!--                width="120">-->
    <!--                <template slot-scope="scope">{{ scope.row.id }}</template>-->
    <!--            </el-table-column>-->
    <!--            <el-table-column-->
    <!--                label="日期"-->
    <!--                width="120">-->
    <!--                <template slot-scope="scope">{{ scope.row.date }}</template>-->
    <!--            </el-table-column>-->
    <!--            <el-table-column-->
    <!--                prop="name"-->
    <!--                label="姓名"-->
    <!--                width="120">-->
    <!--            </el-table-column>-->
    <!--            <el-table-column-->
    <!--                prop="address"-->
    <!--                label="地址"-->
    <!--                show-overflow-tooltip>-->
    <!--            </el-table-column>-->
    <!--        </el-table>-->
    <!--        <el-pagination @current-change="handleCurrentChange" :current-page="cur_page" :page-size="pagesize" :page-sizes="[4]" layout="total, sizes, prev, pager, next, jumper" :total="inittableData.length">-->
    <!--        </el-pagination>-->
    <!--        <table  id="tipMessageLabel" v-show="false">-->
    <!--           <tr>-->
    <!--               <th>id</th>-->
    <!--               <th>日期</th>-->
    <!--               <th>姓名</th>-->
    <!--               <th>地址</th>-->
    <!--           </tr>-->
    <!--            <tr v-for="item in multipleSelection">-->
    <!--                <td>{{item.id}}</td>-->
    <!--                <td>{{item.date}}</td>-->
    <!--                <td>{{item.name}}</td>-->
    <!--                <td>{{item.address}}</td>-->
    <!--            </tr>-->
    <!--        </table>-->
    <!--        <el-button @click="caonima">导出</el-button>-->
    <!--    </div>-->
    <div class="depart holidayAn">
        <div class="sqxx-option">
            <el-row :gutter="20">
                <el-col :span="24">
                    <el-row>
                        <el-col>
                            <el-card shadow="" class="mgb20">
                                <div class="crumbs">
                                    <el-breadcrumb separator="/">
                                        <el-breadcrumb-item>勤务管理</el-breadcrumb-item>
                                        <el-breadcrumb-item>请销假统计</el-breadcrumb-item>
                                    </el-breadcrumb>
                                </div>
                            </el-card>
                            <el-card style="margin-bottom: 12px;" class="card2">
                                <el-form :inline="true" :label-position="'right'" label-width="85px" ref='passportForm' :model="passportForm" class="demo-form-inline">
                                    <el-row :gutter="6">
                                        <el-col :span="8">
                                            <el-form-item  label="工作单位：" prop="company">
                                                <!-- <el-select @change="selectCompanys" filterable  v-model="passportForm.company">
                                                    <el-option
                                                        v-for="item in initdata"
                                                        :key="item.id"
                                                        :label="item.name"
                                                        :value="item.id">
                                                    </el-option>
                                                </el-select> -->
                                                <el-select  @focus="aa=''" ref="select"  clearable v-model="valueTitle"   @clear="clearHandle">
                                                    <el-input style="width: 80%;margin-left: 20px;" v-model="aa"></el-input>
                                                    <el-option :value="valueTitle" :label="valueTitle">
                                                        <div class="tree-list">
                                                            <el-tree id="tree-option"
                                                                    ref="selectTree"
                                                                    :expand-on-click-node="false"
                                                                    :data="selectdata"
                                                                    :props="dataProps"
                                                                    node-key="id"
                                                                    :highlight-current="true"
                                                                    @node-click="handleNodeClick2"
                                                                    check-on-click-node
                                                                    :filter-node-method="filterNode"
            >
                                                                    <span class="custom-tree-node" slot-scope="{ node, data }">
                                                                        <span>{{ node.label }}</span>
                                                                        <span v-if="data.childCompany.length!=0">
                                                                            <el-button
                                                                                type="text"
                                                                                size="mini"
                                                                                @click.stop="() => append(data)">
                                                                             包含
                                                                            </el-button>
                                                                        </span>
                                                                    </span>
                                                            </el-tree>
                                                        </div>
                                                    </el-option>
                                                </el-select>
                                                
                                            </el-form-item>
                                        </el-col>
                                        <el-col :span="10">
                                            <el-form-item  label="日期：" prop="date">
                                                <el-date-picker
                                                    v-model="passportForm.date"
                                                    type="monthrange"
                                                    value-format="yyyy-MM"
                                                    range-separator="至"
                                                    start-placeholder="开始月份"
                                                    end-placeholder="结束月份">
                                                </el-date-picker>
                                            </el-form-item>
                                        </el-col>
                                        <el-col :span="6">
                                            <el-form-item>
                                                <el-button type="primary" @click = "inquiry">查询</el-button>
                                                <el-button @click="resetForm('passportForm')">重置</el-button>
                                            </el-form-item>
                                        </el-col>
                                    </el-row>
                                </el-form>
                            </el-card>
                            <el-card style="margin-bottom: 12px;" class="card2">
                                <div slot="header" class="clearfix">
                                    <span>概况</span>
                                </div>
                                <div v-html="content">
<!--                                    <p class="titleContent"> <span class="fblue">2019-01-01</span>至<span class="fblue">2019-06-01</span>期间，<span class="fblue">建邺分局</span>共<span class="fblue">145人</span>，请假人数共<span class="fblue">66</span>人，请假天数共<span class="fblue">10</span>天，其中请假事由中<span class="fblue">产假</span>请假天数最多，共<span class="fblue">124</span>天。事假超出规定天数的有<span class="fblue">5</span>人，年休假未休人数共<span class="fblue">5</span>人，请假天数较上一年度同比增长<span class="fblue">5%</span>，其中请假天数最多的为<span class="fblue">5</span>月份，共<span class="fblue">98</span>天。</p>-->
                                </div>
                            </el-card>
                            <el-row :gutter="12">
                                <el-col :span="8">
                                    <el-card style="margin-bottom: 12px;margin-left: 20px;">
                                        <div slot="header" class="clearfix">
                                            <span>请假类型</span>

                                        </div>
                                        <div style="width: 100%;height:400px;" id="qingjialeixing">

                                        </div>
                                    </el-card>
                                </el-col>
                                <el-col :span="8">
                                    <el-card style="margin-bottom: 12px;">
                                        <div slot="header" class="clearfix">
                                            <span>请假天数</span>
                                        </div>
                                        <div style="width: 100%;height:400px;" id="qingjiatianshu">

                                        </div>
                                    </el-card>
                                </el-col>
                                <el-col :span="8">
                                    <el-card style="margin-bottom: 12px;margin-right: 20px;">
                                        <div slot="header" class="clearfix">
                                            <span>外出地点</span>
                                        </div>
                                        <div style="width: 100%;height:400px;" id="waichudidian">
                                        </div>
                                    </el-card>
                                </el-col>
                            </el-row>
                            <el-card class="card2">
                                <div slot="header">
                                    <span style="font-size: 18px;color: #262626;font-weight: bold;">列表统计</span>
                                            <el-button style="float: right;margin-right: 25px; " type="primary"  @click="leadingout">导出</el-button>
                                </div>
                                <div>
                                    <!--申请信息表单  -->
                                    <!--申请信息表格  -->
                                    <div  class="departTable">
                                        <img v-show="tableData.length>0?false:true" :src="require('static/img/manage/noData.png')" width="128px" class="noDataImg" />
                                        <el-table v-show="tableData.length>0?true:false" :data="tableData" style="width: 100%">
                                            <el-table-column  width="55">
                                                <template slot="header" slot-scope="scope">
                                                    <el-checkbox v-model="checked" @change.native.stop="hahaa()">&nbsp;</el-checkbox>
                                                    <!--<el-checkbox v-model="idarr"  @change.native.stop="hahaa()">&nbsp;</el-checkbox>-->
                                                </template>
                                                <template slot-scope="scope">
                                                    <el-checkbox v-model="idarr" :label="scope.row.id" @change.native.stop="haha(scope, scope.row)" :key="scope.id">&nbsp;</el-checkbox>
                                                </template>
                                            </el-table-column>
                                            <el-table-column  v-if="name" :key="Math.random()" prop="name" label="单位名称"></el-table-column>
                                            <el-table-column  v-if="deptname" :key="Math.random()" prop="deptname" label="单位名称"></el-table-column>
                                            <el-table-column  v-if="deptname" :key="Math.random()" prop="personname" label="姓名"></el-table-column>
                                            <el-table-column  v-if="name" :key="Math.random()" prop="people" label="总人数"></el-table-column>
                                            <el-table-column  v-if="name" :key="Math.random()" prop="leavecon" label="请假人数"></el-table-column>
                                            <el-table-column  v-if="name" :key="Math.random()" prop="leaverad" label="请假占比"></el-table-column>
                                            <el-table-column  prop="leavenj"  label="离宁外出"></el-table-column>
                                            <el-table-column :key="Math.random()"  v-if="name" prop="annualLeave" label="年休假">
                                            </el-table-column>
                                            <el-table-column :key="Math.random()" v-if="deptname"  label="年休假">
                                                <el-table-column
                                                    prop="unAnnualLeave"
                                                    label="未休">
                                                </el-table-column>
                                                <el-table-column
                                                    prop="annualLeave"
                                                    label="已休">
                                                </el-table-column>
                                            </el-table-column>
                                            <el-table-column prop="familyLeave" label="探亲假"></el-table-column>
                                            <el-table-column prop="PaternityLeave" label="陪产假"></el-table-column>
                                            <el-table-column prop="MaternityLeave" label="产假"></el-table-column>
                                            <el-table-column prop="MarriageLeave" label="婚假"></el-table-column>
                                            <el-table-column prop="sickLeave" label="病假"></el-table-column>
                                            <el-table-column prop="leave" label="事假" ></el-table-column>
                                            <el-table-column prop="FuneralLeave" label="丧假"></el-table-column>
                                            <el-table-column prop="Change" label="调休 "></el-table-column>
                                        </el-table>
                                        <!--表格分页  -->
                                        <div class="pagination" v-show="tableData.length>0?true:false">
                                            <el-pagination @current-change="handleCurrentChange" :current-page="cur_page" :page-size="pagesize" :page-sizes="[10]" layout="total, sizes, prev, pager, next, jumper" :total="inittableData.length">
                                            </el-pagination>
                                        </div>
                                        <table  id="tipMessageLabel" v-show="false">
                                            <tr>
                                                <th >单位名称</th>
                                                <th  v-if="name">总人数</th>
                                                <th v-if="deptname">姓名</th>
                                                <th  v-if="name">请假人数</th>
                                                <th  v-if="name">请假占比</th>
                                                <th >离宁外出</th>
                                                <th v-if="name">年休假</th>
                                                <th  v-if="deptname">年休假(未休)</th>
                                                <th  v-if="deptname">年休假(已休)</th>
                                                <th>探亲假</th>
                                                <th >陪产假</th>
                                                <th >产假</th>
                                                <th >婚假</th>
                                                <th >病假</th>
                                                <th >事假</th>
                                                <th >丧假</th>
                                                <th >调休</th>
                                            </tr>
                                            <tr v-for="item in multipleSelection">
                                                <td v-if="name">{{item.name}}</td>
                                                <td v-if="deptname">{{item.deptname}}</td>
                                                <td v-if="name">{{item.people}}</td>
                                                <td v-if="deptname">{{item.personname}}</td>

                                                <td v-if="name">{{item.leavecon}}</td>
                                                <td v-if="name">{{item.leaverad}}</td>
                                                <td>{{item.leavenj}}</td>
                                                <td v-if="name">{{item.annualLeave}}</td>
                                                <td v-if="deptname">{{item.unAnnualLeave}}</td>
                                                <td v-if="deptname">{{item.annualLeave}}</td>
                                                <td>{{item.familyLeave}}</td>
                                                <td>{{item.PaternityLeave}}</td>
                                                <td>{{item.MaternityLeave}}</td>
                                                <td>{{item.MarriageLeave}}</td>
                                                <td>{{item.sickLeave}}</td>
                                                <td>{{item.leave}}</td>
                                                <td>{{item.FuneralLeave}}</td>
                                                <td>{{item.Change}}</td>
                                            </tr>
                                        </table>
                                    </div>
                                </div>
                            </el-card>
                        </el-col>
                    </el-row>
                </el-col>
            </el-row>
        </div>
        <el-footer style="text-align:center;height:20px;">&copy;2019-2022 南京市公安局&emsp;版权所有</el-footer>
    </div>
</template>

<script>
import { communication } from "../../data/getAxios";
export default {
  name: "passportList",
  data() {
    return {
      acValue: 1,
      inacValue: 0,
      checked: false,
      getRokes(row) {
        return row.id;
      },
      pagesize: 4,
      inittableData: [],
      multipleSelection: [],
      tableData: [],
      idarr: [],
      sqxxForm: {
        upOption: ""
      },
      headers: { token: localStorage.getItem("setToken") },
      actss: this.global.serverPath8 + "/personnel/passport/import",
      class1: "el-icon-arrow-down el-icon--right",
      class2: "el-icon-arrow-up el-icon--right",
      tableData: [],
      toggleShow: false,
      pageShow1: true,
      pageShow2: false,
      cur_page: 1, //表格当前页
      cur_page2: 1,
      pagesize: 10, //表格分页条数
      pagesize2: 10,
      data_length: 0,
      data_length2: 0,
      multipleSelection: [],
      recordId: "",
      passportForm: {
        company: "",
        date: null,
        companyName: ""
      },
      idCardArr: ["152104198602040010", "320113197512179512"],
      initdata: [],
      loading: null,
      content: "",
      deptname: false,
      name: false,
      loginDeptIdNum: null,
      selectdata: [],
      dataProps: {
        children: "childCompany",
        label: "unitName"
      },
      valueTitle: "",
      aa: null,
      dataId: null,
      contain: ""
    };
  },
  created() {
    this.$nextTick(() => {
      this.initselectdata();
    });
  },
  mounted() {
    this.passportForm.company = localStorage.getItem("loginDeptId")
      ? localStorage.getItem("loginCompanyId")
      : localStorage.getItem("loginDeptId");
    this.$nextTick(() => {
      this.selectCompany();
    });
  },
  methods: {
    selectCompany(company) {
      var companyNum;
      if (company) {
        companyNum = company;
      } else {
        companyNum = localStorage.getItem("loginDeptId")
          ? localStorage.getItem("loginCompanyId")
          : localStorage.getItem("loginDeptId");
      }
      this.passportForm.company = localStorage.getItem("loginDeptId")
        ? localStorage.getItem("loginCompanyId")
        : localStorage.getItem("loginDeptId");
      console.log(typeof this.passportForm.company);
      const obj = {
        // url:this.global.serverPath8+"/statistics/LMStatistics/departList",
        // postData:{
        //     deptId:localStorage.getItem("loginDeptId")?localStorage.getItem("loginCompanyId"):localStorage.getItem("loginDeptId")
        // }
        url: this.global.serverPath8 + "/user-mgmt/orgmanage/unittreeAuto",
        isGet: true
      };
      communication(obj).then(res => {
        if (res.code == 0) {
          this.initdata = res.data;
        }
      });
      const _obj = {
        url: this.global.serverPath8 + "/statistics/LMStatistics/departInfo",
        postData: {
          startDate:
            this.passportForm.date == null ? "" : this.passportForm.date[0],
          endDate:
            this.passportForm.date == null ? "" : this.passportForm.date[1],
          deptId: this.dataId,
          contain: this.contain
        }
      };
      communication(_obj).then(res => {
        if (res.code == 0) {
          this.content = res.data;
        }
      });
    },

    hahaa(v) {
      if (this.checked) {
        this.idarr = [];
        this.multipleSelection = this.inittableData;
        this.multipleSelection.map((c, i) => {
          if (isNaN(c)) {
            this.idarr.push(c.id);
          }
        });
      } else {
        this.idarr = [];
        this.multipleSelection = [];
      }
    },
    haha(v, k) {
      this.idarr = [];
      var hasRow = false;
      var newArr = [];
      var _this = this;
      this.multipleSelection.map((c, i) => {
        if (c.id == k.id) {
          hasRow = true;
        } else {
          newArr.push(c);
        }
      });
      if (hasRow) {
        this.multipleSelection = newArr;
      } else {
        this.multipleSelection = this.multipleSelection.concat(k);
      }
      this.multipleSelection.map((c, i) => {
        if (!isNaN(c)) {
          this.multipleSelection.splice(i, 1);
        } else {
          this.idarr.push(c.id);
        }
      });

      if (this.multipleSelection.length != this.inittableData.length) {
        this.checked = false;
      } else {
        this.checked = true;
      }
    },
    handleCurrentChange(val) {
      this.cur_page = val;
      this.tableData = this.inittableData.slice(
        (this.cur_page - 1) * this.pagesize,
        this.cur_page * this.pagesize
      );
    },
    selectall() {
      this.multipleSelection = this.inittableData;
    },
    selectCompanys(v) {
      this.initdata.forEach((item, index) => {
        if (item.id == v) {
          this.passportForm.companyName = item.name;
        }
      });
      this.loginDeptIdNum = v;
      console.log(v);
    },
    initTabledatas(company) {
      var companyNum;
      if (company) {
        companyNum = company;
      } else {
        companyNum = localStorage.getItem("loginDeptId")
          ? localStorage.getItem("loginCompanyId")
          : localStorage.getItem("loginDeptId");
      }
      const _obj = {
        url: this.global.serverPath8 + "/statistics/LMStatistics/report",
        postData: {
          startDate:
            this.passportForm.date == null ? "" : this.passportForm.date[0],
          endDate:
            this.passportForm.date == null ? "" : this.passportForm.date[1],
          deptId: this.dataId,
          contain: this.contain
        }
      };
      communication(_obj).then(res => {
        this.deptname = false;
        this.name = false;
        if (res.code == 0) {
          this.inittableData = res.data;
          if (
            this.inittableData.length != 0 &&
            this.inittableData[0].deptname
          ) {
            this.deptname = true;
          }
          //  else {
          //   this.deptname = false;
          // }
          if (this.inittableData.length != 0 && this.inittableData[0].name) {
            this.name = true;
          }
          // else {
          //   this.name = false;
          // }
          this.inittableData.forEach((item, index) => {
            item.id = index;
          });
          this.tableData = this.inittableData.slice(
            (this.cur_page - 1) * this.pagesize,
            this.cur_page * this.pagesize
          );
        } else if (res.code != 1) {
          this.$message.error(res.message);
        }
      });
    },
    qingjialeixing(company) {
      var companyNum;
      if (company) {
        companyNum = company;
      } else {
        companyNum = localStorage.getItem("loginDeptId")
          ? localStorage.getItem("loginCompanyId")
          : localStorage.getItem("loginDeptId");
      }
      var myChart = this.$echarts.init(
        document.getElementById("qingjialeixing")
      );
      var option = {
        tooltip: {
          trigger: "item",
          formatter: "{a} <br/>{b} : {c} ({d}%)"
        },
        legend: {
          x: "center",
          y: "bottom"
        },
        toolbox: {
          show: true,
          feature: {
            magicType: {
              show: true,
              type: ["pie", "funnel"]
            }
          }
        },
        calculable: true,
        series: [
          {
            name: "请假类型",
            type: "pie",
            radius: [30, 110],
            center: ["50%", "50%"],
            roseType: "area"
          }
        ]
      };
      // this.passportForm.company=localStorage.getItem("loginDeptId")==null?localStorage.getItem("loginCompanyId"):localStorage.getItem("loginDeptId")
      const _obj = {
        url: this.global.serverPath8 + "/statistics/LMStatistics/LeaveType",
        postData: {
          startDate:
            this.passportForm.date == null ? "" : this.passportForm.date[0],
          endDate:
            this.passportForm.date == null ? "" : this.passportForm.date[1],
          deptId: this.dataId,
          contain: this.contain
        }
      };
      communication(_obj).then(res => {
        if (res.code == 0) {
          option.legend.data = res.data.nameList;
          option.series[0].data = res.data;
          myChart.setOption(option);
        }
      });
    },
    qingjiatianshu(company) {
      var companyNum;
      if (company) {
        companyNum = company;
      } else {
        companyNum = localStorage.getItem("loginDeptId")
          ? localStorage.getItem("loginCompanyId")
          : localStorage.getItem("loginDeptId");
      }
      var myChart = this.$echarts.init(
        document.getElementById("qingjiatianshu")
      );
      var option = {
        tooltip: {
          trigger: "axis"
        },
        legend: {
          bottom: "0px"
        },
        grid: {
          left: "0",
          right: "1%",
          bottom: "7%",
          containLabel: true
        },
        xAxis: {
          type: "category",
          name: "月",

          axisLine: {
            show: true,
            lineStyle: {
              color: "#289dd6"
            }
          },
          axisTick: {
            show: false,
            alignWithLabel: false
          },
          splitLine: { show: false }
          // data: ['一', '二', '三', '四', '五', '六', '七', '八', '九', '十', '十一', '十二']
        },
        yAxis: {
          type: "value",
          axisLine: {
            show: true,
            lineStyle: {
              color: "#068dce"
            }
          },
          axisTick: {
            show: false,
            alignWithLabel: false
          },
          splitLine: {
            show: true,
            lineStyle: {
              type: "dashed",
              color: "#BFBFBF"
            }
          }
        }
      };
      const _obj = {
        url: this.global.serverPath8 + "/statistics/LMStatistics/LeaveDate",
        postData: {
          startDate:
            this.passportForm.date == null ? "" : this.passportForm.date[0],
          endDate:
            this.passportForm.date == null ? "" : this.passportForm.date[1],
          deptId: this.dataId,
          contain: this.contain
        }
      };
      communication(_obj).then(res => {
        if (res.code == 0) {
          option.legend.data = res.data.nameList;
          option.xAxis.data = res.data.dateList;
          option.series = res.data.data;
          myChart.setOption(option);
        }
      });
    },
    waichudidian(company) {
      var companyNum;
      if (company) {
        companyNum = company;
      } else {
        companyNum = localStorage.getItem("loginDeptId")
          ? localStorage.getItem("loginCompanyId")
          : localStorage.getItem("loginDeptId");
      }
      var myChart = this.$echarts.init(document.getElementById("waichudidian"));
      // var option = {}

      var option = {
        tooltip: {
          show: true
        },
        series: [
          {
            type: "wordCloud",
            gridSize: 6,
            shape: "diamond",
            sizeRange: [25, 60],
            width: 500,
            height: 400,
            textStyle: {
              normal: {
                color: function() {
                  return (
                    "rgb(" +
                    [
                      Math.round(Math.random() * 160),
                      Math.round(Math.random() * 160),
                      Math.round(Math.random() * 160)
                    ].join(",") +
                    ")"
                  );
                }
              },
              emphasis: {
                shadowBlur: 10,
                shadowColor: "#333"
              }
            },
            data: [
              {
                name: "安徽",
                value: 30
              },
              {
                name: "六安",
                value: 24
              },
              {
                name: " 芜湖",
                value: 21
              },
              {
                name: " 江西",
                value: 19
              },
              {
                name: " 湖南",
                value: 18
              },
              {
                name: " 四川",
                value: 18
              },
              {
                name: " 上海",
                value: 17
              },
              {
                name: " 北京",
                value: 12
              },
              {
                name: " 日本",
                value: 12
              },
              {
                name: " 西班牙",
                value: 11
              },
              {
                name: " 泰国",
                value: 11
              },
              {
                name: " 缅甸",
                value: 10
              },
              {
                name: " 芜湖",
                value: 0
              },
              {
                name: " 四川",
                value: 9
              },
              {
                name: " 哈尔滨",
                value: 9
              }
            ]
          }
        ]
      };
      const _obj = {
        url: this.global.serverPath8 + "/statistics/LMStatistics/leaveLocation",
        postData: {
          startDate:
            this.passportForm.date == null ? "" : this.passportForm.date[0],
          endDate:
            this.passportForm.date == null ? "" : this.passportForm.date[1],
          deptId: this.dataId,
          contain: this.contain
        }
      };
      communication(_obj).then(res => {
        if (res.code == 0) {
          option.series[0].data = res.data;
          myChart.setOption(option);
        }
      });
    },
    // init(){
    //     const _obj={
    //         url:this.global.serverPath8+'/personnel/passport/list',
    //         postData:{
    //             "userName":this.passportForm.name,
    //             "policeNo":this.passportForm.policeNo,
    //             "idNumber":this.passportForm.idNumber,
    //             "company":this.passportForm.company,
    //             "papersType":this.passportForm.papersType,
    //             "state":this.passportForm.state,
    //         }
    //     };
    //     communication(_obj).then((res) => {
    //         if(res.code==0){
    //             this.inittableData = res.data
    //             this.tableData=this.inittableData.slice((this.cur_page-1)*this.pagesize,(this.cur_page)*this.pagesize)
    //         }else if(res.code!=1){
    //             this.$message.warning(res.message)
    //         }
    //     }).catch((err) => {
    //         console.log('请求失败！')
    //     })
    //
    // },
    handleSuccess(response, file, fileList) {
      if (response.code == 0) {
        this.$message.success("导入成功");
        this.cur_page = 1;
        this.init();
        this.multipleSelection = [];
        this.idarr = [];
        this.checked = false;
        this.loading.close();
      } else {
        this.$message.error("系统错误，请检查上传文件");
        this.loading.close();
      }
    },
    leadingout() {
      console.log(this.multipleSelection);
      $("#tipMessageLabel").table2excel({
        // 不被导出的表格行的CSS class类
        exclude: ".noExl",
        name: "Excel Document Name",
        filename: "请销假分析.xls"
      });
      this.multipleSelection = [];
      this.idarr = [];
      this.checked = false;
    },
    inquiry() {
      this.cur_page = 1;
      this.initTabledatas(this.loginDeptIdNum);
      this.qingjialeixing(this.loginDeptIdNum);
      this.qingjiatianshu(this.loginDeptIdNum);
      this.waichudidian(this.loginDeptIdNum);
      this.selectCompany(this.loginDeptIdNum);
      this.multipleSelection = [];
      this.idarr = [];
    },
    // 收起展开
    upDown() {
      this.toggleShow = !this.toggleShow;
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
      console.log(this.multipleSelection);
      // for(var i=0;i<this.multipleSelection.length;i++){
      //     this.idCardArr += this.multipleSelection[i].characterID+','
      // };
    },
    // 重置表单
    resetForm(formName) {
      this.$refs[formName].resetFields();
      this.cur_page = 1;
      this.init();
      this.multipleSelection = [];
      this.idarr = [];
      this.contain = "";
      this.dataId = "";
      this.valueTitle = "";
      this.aa = "";
    },
    initselectdata() {
      const _obj = {
        url: this.global.serverPath8 + "/user-mgmt/orgmanage/unittreeAuto",
        isGet: true
      };
      communication(_obj).then(res => {
        this.selectdata = res.data;
        this.dataId = res.data[0].dataId;
        this.valueTitle = res.data[0].unitName + "(包含子部门)";
        setTimeout(() => {
          console.log(this.selectdata);
          console.log(this.dataId);
          this.contain = "包含";
          this.qingjialeixing();
          this.qingjiatianshu();
          this.initTabledatas();
          this.waichudidian();
          this.selectCompany();
        }, 500);
      });
    },
    append(data) {
      console.log(data);
      // this.flag=false
      this.valueTitle = data.unitName + "(包含子部门)";
      this.dataId = data.dataId;
      this.contain = "包含";
    },
    filterNode(value, data) {
      console.log(data);
      if (!value) return true;
      console.log(data);
      return data.label.indexOf(value) !== -1;
    },
    handleNodeClick2(node) {
      console.log(node);
      this.valueTitle = node.unitName;
      this.dataId = node.unitId;
      this.contain = "不包含";
    },
    // 清除选中
    clearHandle() {
      this.valueTitle = "";
      this.dataId = null;
      this.contain = "";
      // this.valueId = null
      // this.defaultExpandedKey = []
      // this.clearSelected()
    }
  }
};
</script>

<style scoped>
.departTable {
  margin-top: 20px;
}
.titleContent {
  text-indent: 32px;
}
.fblue {
  color: #2d8cf0;
}
.el-scrollbar .el-scrollbar__view .el-select-dropdown__item {
  height: auto;
  /*max-height: 274px;*/
  overflow: auto;
  padding: 0;
}
.el-select-dropdown__item.selected {
  font-weight: normal;
}
ul li >>> .el-tree .el-tree-node__content {
  height: auto;
  padding: 0 20px;
}
.el-tree-node__label {
  font-weight: normal;
}
.el-tree >>> .is-current .el-tree-node__label {
  color: #409eff;
  font-weight: 700;
}
.el-tree >>> .is-current .el-tree-node__children .el-tree-node__label {
  color: #606266;
  font-weight: normal;
}
</style>
<style>
.holidayAn .el-card__header {
  padding: 10px 16px;
}
.titleContent {
  text-indent: 32px;
}
.fblue {
  color: #2d8cf0;
}
.depart .el-table th > .cell {
  font-family: "PingFangSC-Medium" !important;
  font-size: 14px;
  color: rgba(0, 0, 0, 0.85);
}
.depart .el-table .cell {
  font-family: "PingFangSC-Regular";
  font-size: 14px;
  color: rgba(0, 0, 0, 0.65);
}
.departForm .el-form-item__label {
  font-family: "PingFangSC-Regular";
  font-size: 14px;
  color: rgba(0, 0, 0, 0.85);
  text-align: right;
}
.departForm .demo-form-inline input {
  width: 200px !important;
}
.departForm .demo-form-inline select {
  width: 200px !important;
}
.departUpload .el-upload-list {
  display: none;
}
.departUpload .el-upload--text {
  width: inherit;
  height: inherit;
  border: 0;
  border-radius: 3px;
}
.departUpload .el-upload-dragger {
  width: inherit;
  height: inherit;
  border: 0;
  border-radius: 3px;
}
.departUpload > button {
  float: left;
}
.departUpload > div {
  float: left;
  margin-left: 20px;
}
</style>
