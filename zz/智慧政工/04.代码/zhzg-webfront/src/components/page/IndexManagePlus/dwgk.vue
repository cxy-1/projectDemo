<template>
    <div>
        <el-form>
            <div class="customSelect xzdw">
                <el-form-item label="选择单位" :label-width="formLabelWidth" v-show="!tabJXKH">
                    <el-select v-model="UnitValue" popper-class="customManageBox" placeholder="请选择" v-show="UnitShowJTBM" multiple collapse-tags>
                        <el-option :value="UnitValue">
                                <el-tree
                                class="treeSelect"
                                :data="deptRewordRightUnitOptions"
                                :props="dataProps2"
                                node-key="unitId"
                                ref="QJGKtree2"
                                @check="checkGroupNodeBumen_jiti"
                                show-checkbox
                                check-on-click-node>
                                </el-tree>
                        </el-option>
                    </el-select>
                    <el-select v-model="UnitValue" popper-class="customManageBox" placeholder="请选择" v-show="UnitShowGRBM" multiple collapse-tags>
                        <el-option :value="UnitValue">
                                <el-tree
                                class="treeSelect"
                                :data="personRewordRightUnitOptions"
                                :props="dataProps2"
                                node-key="unitId"
                                ref="QJGKtree4"
                                @check="checkGroupNodeBumen_geren"
                                show-checkbox
                                check-on-click-node>
                                </el-tree>
                        </el-option>
                    </el-select>
                </el-form-item>
            </div>
            <div class="customSelect xznd" v-show="tabJXKH">
                <el-form-item label="选择年度" :label-width="formLabelWidth" v-show="tabJXKH">
                    <el-select v-model="jxkhYearValue" class="year" @change="selectYear">
                        <el-option v-for="item in jxkhYearOptions" :key="item" :label="item" :value="item">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="选择季度" :label-width="formLabelWidth">
                    <el-select v-model="jxkhJdValue" class="year" @change="selectQuarter">
                        <el-option v-for="item in jxkhJdOptions" :key="item" :label="item" :value="item">
                        </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="考核单位" :label-width="formLabelWidth">
                    <el-select v-model="jxkhKhdwValue" class="year" @change="selectUnit">
                        <el-option v-for="item in jxkhKhdwOptions" :key="item" :label="item" :value="item">
                        </el-option>
                    </el-select>
                </el-form-item>
            </div>
        </el-form>
        <el-tabs class="customTabs" v-model="activeTab" @tab-click="handleClick">
            <el-tab-pane label="集体荣誉" name="jtryAttList">
                <!-- <div :class="this.ifFullse ==false?'lefts':'left'">
                    <ul class="listStyle" style="overflow:auto">
                        <li v-for="(item,index) in tabList.jtryAttList" :key="index" :class="activeClass == index?'active':''" @click="btnGetItem(index)">{{ item.name }}</li>
                    </ul>
                </div> -->
                <div :class="this.ifFullse ==false?'rights':'right'">
                    <el-table :height="this.ifFullse ==false?'360':''" row-key="id" :tree-props="{children: 'children', hasChildren: 'hasChildren'}" :class="this.ifFullse ==false?'righttables':'righttable'" :data="tableDataJTRY.data">
                        <el-table-column :min-width="col.minWidth" :show-overflow-tooltip="true" v-for="col in tableDataJTRY.column" :prop="col.prop" :label="col.label" >
                        </el-table-column>
                    </el-table>
                    <el-pagination
                        style="float: right"
                        background
                        :total="tableDataJTRY.totalRecord"
                        :page-sizes="[20, 30, 40]"
                        :page-size="20"
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        :current-page.sync="listQuery.defaultPage"
                        layout="total, sizes, prev, pager, next">
                    </el-pagination>
                </div>
            </el-tab-pane>
            <el-tab-pane label="个人荣誉" name="grryAttList">
                <!-- <div :class="this.ifFullse ==false?'lefts':'left'">
                    <ul class="listStyle" style="overflow:auto">
                        <li v-for="(item,index) in tabList.grryAttList" :key="index" :class="activeClass == index?'active':''" @click="btnGetItem(index)">{{ item.name }}</li>
                    </ul> @cell-click="handleSelectionChange"
                </div> -->
                <div :class="this.ifFullse ==false?'rights':'right'">
                    <el-table :data="tableDataGRRY.data" row-key="id" :tree-props="{children: 'children', hasChildren: 'hasChildren'}" :height="this.ifFullse ==false?'360':''" :class="this.ifFullse ==false?'righttables':'righttable'">
                        <el-table-column prop="deptName" width="180" label="单位">
                           <template slot-scope="props">
                             <span v-if="props.row.deptName&&props.row.deptName.length>6" :title= "props.row.deptName">{{props.row.deptName.slice(0,6)}}...</span>
                             <span v-else>{{props.row.deptName}}</span>
                           </template>
                        </el-table-column>
                        <el-table-column prop="martyrs" label="革命烈士"></el-table-column>
                        <el-table-column prop="nationFirstHonor" label="一级英模"></el-table-column>
                        <el-table-column prop="nationSecondHonor" label="二级英模"></el-table-column>
                        <el-table-column prop="nationLaomo" label="全国劳模 "></el-table-column>
                        <el-table-column prop="nationYouPolice" label="全国特级优秀人民警察" min-width="165"></el-table-column>
                        <el-table-column prop="nationHonorPlice" label="全国优秀人民警察" min-width="140"></el-table-column>
                        <el-table-column prop="nationSanba" label="全国三八红旗手"  min-width="125"></el-table-column>
                        <el-table-column prop="personFirstHonor" label="一等功"></el-table-column>
                        <el-table-column prop="prinviceLaomo" label="省劳模 "></el-table-column>
                        <el-table-column prop="prinviceSanba" label="省三八红旗手" min-width="110"></el-table-column>
                        <el-table-column prop="prinviceWusi" label="省五四青年奖章" min-width="125"></el-table-column>
                        <el-table-column prop="personSecondHonor" label="二等功"></el-table-column>
                        <el-table-column prop="nationModelWorker" label="市劳模"></el-table-column>
                        <el-table-column prop="citySanba" label="市三八红旗手" min-width="110"></el-table-column>
                        <el-table-column prop="personThirdHonor" label="三等功"></el-table-column>
                    </el-table>
                    <el-pagination
                        style="float: right"
                        background
                        :total="tableDataGRRY.totalRecord"
                        :page-sizes="[20, 30, 40]"
                        :page-size="20"
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        layout="total, sizes, prev, pager, next">
                    </el-pagination>
                </div>
            </el-tab-pane>
            <el-tab-pane label="绩效考核" name="jxkhAttList">
                <div>
                    <el-table :data="tableDataJXKH.data" :height="this.ifFullse ==false?'360':''" :class="this.ifFullse ==false?'righttables':'righttable'">
                        <el-table-column  v-for="col in tableDataJXKH.column" :prop="col.label" :label="col.label" >
                        </el-table-column>
                    </el-table>
                    <el-pagination
                        style="float: right"
                        background
                        :total="tableDataJXKH.totalRecord"
                        :page-sizes="[20, 30, 40]"
                        :page-size="20"
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        :current-page.sync="listQuery.defaultPage"
                        layout="total, sizes, prev, pager, next">
                    </el-pagination>
                </div>
            </el-tab-pane>
        </el-tabs>
        <!-- <el-dialog width="50%"  title="个人荣誉明细" :visible.sync="innerVisible" append-to-body>
          <div style="height:400px">
            <el-table :data="tableDataGRRYs.data" height="360" style="width: 100%">
                <el-table-column prop="deptName" width="200" label="单位"></el-table-column>
                <el-table-column prop="personFirstHonor" label="姓名"></el-table-column>
                <el-table-column prop="personSecondHonor" label="奖章" ></el-table-column>
            </el-table>
            <el-pagination
                style="float: right"
                background
                :total="tableDataGRRYs.totalRecord"
                :page-sizes="[20, 30, 40]"
                :page-size="20"
                @size-change="handleSizeChanges"
                @current-change="handleCurrentChanges"
                :current-page.sync="listQuerys.defaultPage"
                layout="total, sizes, prev, pager, next">
            </el-pagination>
          </div>
        </el-dialog>            -->
    </div>
</template>
<script>
import api from "./api";
import axios from "axios";
import { handleTabSize } from "@/utils/utils";
let gloabPath = require("../../../../src/Global");
const baseURLHead = gloabPath.default.serverPath8;
let $axios = axios.create({
  baseURL: baseURLHead + "/statistics/mangagerData"
});
const $fetch = function(method, url, params) {
  return new Promise((resolve, reject) => {
    $axios({
      method: method,
      url: url.remoteUrl,
      data: params,
      headers: {
        contentType: "application/json",
        token: localStorage.getItem("setToken")
      }
    })
      .then(res => {
        resolve(res.data);
      })
      .catch(err => {
        reject(err);
      });
  });
};
let $axiosUsers = axios.create({
  baseURL: baseURLHead + "/statistics"
});
const $fetchUsers = function(method, url, params) {
  return new Promise((resolve, reject) => {
    $axiosUsers({
      method: method,
      url: url.remoteUrl,
      data: params,
      headers: {
        contentType: "application/json",
        token: localStorage.getItem("setToken")
      }
    })
      .then(res => {
        resolve(res.data);
      })
      .catch(err => {
        reject(err);
      });
  });
};
export default {
  props: ["activeTabs", "activeClasse", "UnitValues", "ifFullScreen"],
  data: function() {
    return {
      //传值
      activeTabs: "",
      activeClasse: "",
      UnitValues: [],
      // tabs
      innerVisible: false,
      tabJXKH: false,
      UnitShowJTBM: true,
      UnitShowGRBM: false,
      // UnitData
      UnitValue: [],
      deptRewordLeftUnitOptions: [],
      deptRewordRightUnitOptions: [],
      personRewordLeftUnitOptions: [],
      personRewordRightUnitOptions: [],
      personRewordLeftUnit: "",
      personRewordRightUnit: "",

      // checkArray
      checkArrayBm: [],
      checkArrayFj: [],

      props: {
        label: "name",
        value: "id",
        isLeaf: true
      },
      dataProps2: {
        children: "childCompany",
        label: "unitName"
      },
      value: "",
      formLabelWidth: "100px",

      jxkhYearValue: "2019",
      jxkhKhdwValue: "",
      jxkhJdValue: "",
      jxkhYearOptions: [],
      jxkhKhdwOptions: [],
      jxkhJdOptions: [],

      // tab
      activeTab: "jtryAttList",
      // ul
      tabList: {
        jtryAttList: [{ name: "部门" }, { name: "分局" }],
        grryAttList: [{ name: "部门" }, { name: "分局" }],
        jxkhAttList: [
          { name: "2016年" },
          { name: "2017年" },
          { name: "2018年" },
          { name: "2019年" }
        ]
      },
      activeClass: "0",
      listQuery: {
        departIds: [],
        type: "部门",
        year: "2019",
        groupName: "",
        quarter: "",
        // pagination
        pageNo: 1,
        pageSize: 20,
        defaultPage: 1
      },
      listQuerys: {
        departIds: [],
        pageNo: 1,
        pageSize: 20,
        defaultPage: 1
      },
      // table
      tableDataJTRY: {
        data: [],
        column: [],
        totalRecord: 0
      },
      tableDataGRRY: {
        data: [],
        column: [],
        totalRecord: 0
      },
      tableDataGRRYs: {
        data: [],
        column: [],
        totalRecord: 0
      },
      tableDataJXKH: {
        data: [],
        column: [],
        totalRecord: 0
      },
      tabCellWidth: 0,
      tabMinWidth: 0,
      ifFullScreen: true,
      ifFullse: true
    };
  },
  watch: {
    ifFullScreen() {
      this.ifFullse = this.ifFullScreen;
    }
  },
  mounted() {
    this.ifFullse = this.ifFullScreen;
    this.tabCellWidth = parseFloat(
      document.getElementsByClassName("right")[0].offsetWidth
    );
    this.setDialogTitle();
    // 调用获取单位方法，初始化单位及表格数据
    if (this.activeClasse) {
      this.activeTab =
        this.activeClasse == "first"
          ? "jtryAttList"
          : this.activeClasse == "second" ? "grryAttList" : "jxkhAttList";
      this.showHide();
    }
    if (this.activeTab == "grryAttList") {
      this.loadUnitOptionsData();
    } else if (this.activeTab == "jtryAttList") {
      this.loadUnitOptionsData();
    } else if (this.activeTab == "jxkhAttList") {
    }
    // 回头放到tabs 绩效里面执行
    this.getKpiYear("");
  },
  methods: {
    //设置弹出框标题
    setDialogTitle() {
      this.$emit("listenChild", "单位概况");
    },
    fixName(value) {
      if (value == "江苏省南京市公安局") {
        return value;
      } else {
        return value.replace("江苏省南京市公安局", "");
      }
    }, //获取新单位
    loadUnitOptionsData() {
      var self = this;
      if (this.activeTab == "grryAttList") {
        this.personRewordRightUnitOptions = [];
      } else if (this.activeTab == "jtryAttList") {
        this.deptRewordRightUnitOptions = [];
      }
      $fetchUsers(
        "post",
        {
          remoteUrl: "mangagerData/getRewardTree"
        },
        {}
      ).then(
        res => {
          if (this.activeTab == "grryAttList") {
            this.personRewordRightUnitOptions = res.data;
            let self = this;
            this.listQuery.departIds = [];
            if (this.UnitValues && this.UnitValues.id) {
              this.listQuery.departIds.push(this.UnitValues.id);
              this.personRewordRightUnitOptions.forEach(item => {
                if (item.id == this.UnitValues.id) {
                  let unitIds = [];
                  unitIds.push(item.unitId);
                  // this.UnitValue = [];
                  // this.UnitValue.push(item.unitName);
                  self.$refs.QJGKtree4.setCheckedKeys(unitIds);
                } else if (item.childCompany && item.childCompany.length > 0) {
                  item.childCompany.forEach(items => {
                    if (items.id == this.UnitValues.id) {
                      let unitIds = [items.id];
                      // this.UnitValue = [];
                      // this.UnitValue.push(item.unitName);
                      self.$refs.QJGKtree4.setCheckedKeys([unitIds]);
                    }
                  });
                }
              });
            }
            this.getTableData(
              this.listQuery.departIds,
              this.listQuery.type,
              this.listQuery.year,
              this.listQuery.groupName,
              this.listQuery.quarter,
              this.listQuery.pageNo,
              this.listQuery.pageSize
            );
          } else if (this.activeTab == "jtryAttList") {
            this.deptRewordRightUnitOptions = res.data;
            let self = this;
            this.listQuery.departIds = [];
            if (this.UnitValues && this.UnitValues.id) {
              this.listQuery.departIds.push(this.UnitValues.id);
              this.deptRewordRightUnitOptions.forEach(item => {
                if (item.id == this.UnitValues.id) {
                  let unitIds = [];
                  unitIds.push(item.unitId);
                  // this.UnitValue = [];
                  // this.UnitValue.push(item.unitName);
                  self.$refs.QJGKtree2.setCheckedKeys(unitIds);
                } else if (item.childCompany && item.childCompany.length > 0) {
                  item.childCompany.forEach(items => {
                    if (items.id == this.UnitValues.id) {
                      let unitIds = [items.id];
                      // this.UnitValue = [];
                      // this.UnitValue.push(item.unitName);
                      self.$refs.QJGKtree2.setCheckedKeys([unitIds]);
                    }
                  });
                }
              });
            }
            this.getTableData(
              this.listQuery.departIds,
              this.listQuery.type,
              this.listQuery.year,
              this.listQuery.groupName,
              this.listQuery.quarter,
              this.listQuery.pageNo,
              this.listQuery.pageSize
            );
          }
        },
        err => {
          this.$message.error("获取单位列表数据失败！");
        }
      );
    },
    // 获取年份
    getKpiYear(departIds) {
      return api
        .get_dwgk_year("")
        .then(result => {
          result = result || {};
          let items = [];
          for (let r in result) {
            items.push(result[r]);
          }
          this.jxkhYearOptions = items;
          this.jxkhYearValue = items[0];
          this.departIds = departIds;
          return items;
        })
        .then(yearList => {
          this.getKpiJd();
          this.selectUnit();
          // this.getKpiKhdw();
        });
    },
    // 获取考核单位
    getKpiKhdw() {
      $fetch(
        "post",
        {
          remoteUrl:
            "getKPIGroupMenu?year=" +
            this.jxkhYearValue +
            "&quarter=" +
            this.jxkhJdValue
        },
        {}
      ).then(
        res => {
          res = res || {};
          let items = [];
          for (let r in res) {
            items.push(res[r]);
          }
          this.jxkhKhdwOptions = items;
          this.jxkhKhdwValue = items[0];
          this.listQuery.groupName = items[0];
          if (this.activeTab == "jxkhAttList") {
            this.getTableData(
              this.listQuery.departIds,
              this.listQuery.type,
              this.listQuery.year,
              this.listQuery.groupName,
              this.listQuery.quarter,
              this.listQuery.pageNo,
              this.listQuery.pageSize
            );
          }
        },
        err => {
          this.$message.error("获取考核单位菜单数据失败！");
        }
      );
    },
    // 获取季度
    getKpiJd() {
      $fetch(
        "post",
        {
          remoteUrl: "getKPIQuarterMenu?year=" + this.jxkhYearValue
        },
        {}
      ).then(
        res => {
          res = res || {};
          let items = [];
          for (let r in res) {
            items.push(res[r]);
          }
          this.jxkhJdOptions = items;
          this.jxkhJdValue = items[0];
          this.listQuery.quarter = this.jxkhJdValue;
          this.getKpiKhdw();
        },
        err => {
          this.$message.error("获取考核单位菜单数据失败！");
        }
      );
    },
    //选择年份 jxkhYearValue
    selectYear(item) {
      this.listQuery.year = item;
      // this.getKpiKhdw(item);
      this.getKpiJd();
    },
    //选择考核单位
    selectUnit(item) {
      this.listQuery.groupName = item;
      if (this.activeTab == "jxkhAttList") {
        this.getTableData(
          this.listQuery.departIds,
          this.listQuery.type,
          this.listQuery.year,
          this.listQuery.groupName,
          this.listQuery.quarter,
          this.listQuery.pageNo,
          this.listQuery.pageSize
        );
      }
    },
    //选择季度
    selectQuarter(item) {
      console.log(item);
      this.listQuery.quarter = item;
      this.getKpiKhdw();
      this.getTableData(
        this.listQuery.departIds,
        this.listQuery.type,
        this.listQuery.year,
        this.listQuery.groupName,
        this.listQuery.quarter,
        this.listQuery.pageNo,
        this.listQuery.pageSize
      );
    },
    // 设置默认勾选根目录
    setAllChecKeys(treeOption) {
      this.$refs.QJGKtree2.setCheckedKeys(treeOption[1]);
      this.$refs.QJGKtree4.setCheckedKeys(treeOption[1]);
      this.getTableData(
        this.listQuery.departIds,
        this.listQuery.type,
        this.listQuery.year,
        this.listQuery.groupName,
        this.listQuery.quarter,
        this.listQuery.pageNo,
        this.listQuery.pageSize
      );
    },
    // 单位勾选 获取departIds
    checkGroupNodeBumen_jiti(item, node) {
      var checkArray = node.checkedNodes.map((a, i) => {
        return a.id;
      });
      // var checkArrays = node.checkedNodes.map((a, i) => {
      //   return a.unitName;
      // });
      this.listQuery.pageNo = 1;
      this.listQuery.pageSize = 20;
      this.listQuery.departIds = checkArray;
      // this.UnitValue = checkArrays;
      this.getTableData(
        this.listQuery.departIds,
        this.listQuery.type,
        this.listQuery.year,
        this.listQuery.groupName,
        this.listQuery.quarter,
        this.listQuery.pageNo,
        this.listQuery.pageSize
      );
    },
    checkGroupNodeBumen_geren(item, node) {
      var checkArray = node.checkedNodes.map((a, i) => {
        return a.id;
      });
      // var checkArrays = node.checkedNodes.map((a, i) => {
      //   return a.unitName;
      // });
      this.listQuery.pageNo = 1;
      this.listQuery.pageSize = 20;
      this.listQuery.departIds = checkArray;
      // this.UnitValue = checkArrays;
      this.getTableData(
        this.listQuery.departIds,
        this.listQuery.type,
        this.listQuery.year,
        this.listQuery.groupName,
        this.listQuery.quarter,
        this.listQuery.pageNo,
        this.listQuery.pageSize
      );
    },
    // tabs板块切换
    handleClick(tab, event) {
      this.activeTab == tab.paneName; //每次切换都预设activeTab到model里
      this.showHide();
      this.resetItem(); //重置
      if (this.activeTab == "grryAttList") {
        this.UnitValue = [];
        this.listQuery.departIds = [];
        this.UnitValues = {};
        this.loadUnitOptionsData();
      } else if (this.activeTab == "jtryAttList") {
        this.UnitValue = [];
        this.listQuery.departIds = [];
        this.UnitValues = {};
        this.loadUnitOptionsData();
      } else if (this.activeTab == "jxkhAttList") {
        this.getTableData(
          this.listQuery.departIds,
          this.listQuery.type,
          this.listQuery.year,
          this.listQuery.groupName,
          this.listQuery.quarter,
          this.listQuery.pageNo,
          this.listQuery.pageSize
        );
      }
    },
    // 切换tabs显示和隐藏
    showHide() {
      this.UnitShowJTBM = false;
      this.UnitShowGRBM = false;
      this.tabJXKH = false;
      this.$nextTick(() => {
        if (this.activeTab == "jtryAttList") {
          this.UnitShowJTBM = true;
        } else if (this.activeTab == "grryAttList") {
          this.UnitShowGRBM = true;
        } else if (this.activeTab == "jxkhAttList") {
          this.tabJXKH = true;
        }
      });
    },
    // 类别点击切换 获取type
    btnGetItem(index) {
      this.activeClass = index;
      this.listQuery.type = this.tabList[this.activeTab][index].name;
      this.listQuery.pageNo = 1;
      this.listQuery.pageSize = 20;
      this.listQuery.defaultPage = 1;
      this.showHide();
      if (this.listQuery.type == "部门") {
        this.getTableData(
          this.checkArrayBm,
          this.listQuery.type,
          this.listQuery.year,
          this.listQuery.groupName,
          this.listQuery.quarter,
          this.listQuery.pageNo,
          this.listQuery.pageSize
        );
      } else {
        this.getTableData(
          this.checkArrayFj,
          this.listQuery.type,
          this.listQuery.year,
          this.listQuery.groupName,
          this.listQuery.quarter,
          this.listQuery.pageNo,
          this.listQuery.pageSize
        );
      }
    },
    // tabs切换时候，重置 左侧item和分页、，默认选择第一行
    resetItem() {
      this.activeClass = 0;
      this.listQuery.type = this.tabList[this.activeTab][0].name;
      this.listQuery.pageNo = 1;
      this.listQuery.pageSize = 20;
      this.listQuery.defaultPage = 1;
      this.showHide();
    },
    // 分页部分
    handleSizeChange(val) {
      console.log(val);
      this.listQuery.pageSize = val;
      this.getTableData(
        this.listQuery.departIds,
        this.listQuery.type,
        this.listQuery.year,
        this.listQuery.groupName,
        this.listQuery.quarter,
        this.listQuery.pageNo,
        this.listQuery.pageSize
      );
    },
    handleCurrentChange(val) {
      console.log(val);
      this.listQuery.pageNo = val;
      this.getTableData(
        this.listQuery.departIds,
        this.listQuery.type,
        this.listQuery.year,
        this.listQuery.groupName,
        this.listQuery.quarter,
        this.listQuery.pageNo,
        this.listQuery.pageSize
      );
    },
    // 6:获取表格数据
    getTableData(departIds, type, year, groupName, quarter, pageNo, pageSize) {
      let data = {
        departIds: departIds,
        type: type,
        year: year,
        groupName: groupName,
        quarter: quarter,
        pageNo: pageNo,
        pageSize: pageSize
      };
      // 各tabs（年龄、性别、名族...）接口请求
      if (this.activeTab == "jtryAttList") {
        this.tableDataJTRY.data = [];
        this.tableDataJTRY.column = [];
        api.get_dwgk_collective_data(data).then(result => {
          this.tabMinWidth = 0;
          Object.keys(result.column).forEach(key => {
            let length = 0;
            for (let i = 0; i < String(result.column[key]).length; i++) {
              length += String(result.column[key]).charCodeAt(i) > 255 ? 2 : 1;
            }
            this.tabMinWidth = Math.max(this.tabMinWidth, length);
          });
          this.tabMinWidth *= 14;
          //json对象转化成json数组对象
          let arrColumn = handleTabSize(result, this.tabCellWidth);
          // 生成表格
          this.tableDataJTRY.totalRecord = result.data.length;
          let length = pageSize * (pageNo - 1);
          this.tableDataJTRY.data = result.data.splice(length, pageSize);
          this.tableDataJTRY.column = arrColumn;
          Object.keys(result.column).forEach(key => {
            if (result.column[key] != "单位") {
              let minWidths = result.column[key].length * 20;
              this.tableDataJTRY.column.forEach((itemss, d) => {
                if (itemss.label == result.column[key]) {
                  this.tableDataJTRY.column[d].minWidth = minWidths;
                }
              });
            } else {
              this.tableDataJTRY.column[0].minWidth = 140;
            }
          });
          // this.tableDataJTRY.totalRecord = result.totalRecord;
        });
      } else if (this.activeTab == "grryAttList") {
        this.tableDataGRRY.data = [];
        api.get_dwgk_grry_data(data).then(result => {
          this.tableDataGRRY.totalRecord = result.data.length;
          let length1 = (pageNo - 1) * pageSize;
          let resultdata = result.data.splice(length1, pageSize);
          resultdata.forEach((items, k) => {
            let nuber = k + 1;
            let childrens = [];
            if (items.resultMode2 && items.resultMode2.length > 0) {
              childrens = items.resultMode2;
              this.tableDataGRRY.data.push({
                id: nuber,
                citySanba: items.citySanba,
                companyId: items.companyId,
                deptName: items.deptName,
                martyrs: items.martyrs,
                nationFirstHonor: items.nationFirstHonor,
                nationHonorPlice: items.nationHonorPlice,
                nationLaomo: items.nationLaomo,
                nationModelWorker: items.nationModelWorker,
                nationPerPolice: items.nationPerPolice,
                nationSanba: items.nationSanba,
                nationSecondHonor: items.nationSecondHonor,
                nationYouPolice: items.nationYouPolice,
                personFirstHonor: items.personFirstHonor,
                personSecondHonor: items.personSecondHonor,
                personThirdHonor: items.personThirdHonor,
                prinviceLaomo: items.prinviceLaomo,
                prinviceSanba: items.prinviceSanba,
                prinviceWusi: items.prinviceWusi,
                children: childrens
              });
              if (items.resultMode2 && items.resultMode2.length > 0) {
                this.tableDataGRRY.data[k].children = [];
                items.resultMode2.forEach((itemse, m) => {
                  let nubers = nuber * 1000 + m + 1;
                  this.tableDataGRRY.data[k].children.push({
                    id: nubers,
                    citySanba: itemse.citySanba,
                    companyId: itemse.companyId,
                    deptName: itemse.deptName,
                    martyrs: itemse.martyrs,
                    nationFirstHonor: itemse.nationFirstHonor,
                    nationHonorPlice: itemse.nationHonorPlice,
                    nationLaomo: itemse.nationLaomo,
                    nationModelWorker: itemse.nationModelWorker,
                    nationPerPolice: itemse.nationPerPolice,
                    nationSanba: itemse.nationSanba,
                    nationSecondHonor: itemse.nationSecondHonor,
                    nationYouPolice: itemse.nationYouPolice,
                    personFirstHonor: itemse.personFirstHonor,
                    personSecondHonor: itemse.personSecondHonor,
                    personThirdHonor: itemse.personThirdHonor,
                    prinviceLaomo: itemse.prinviceLaomo,
                    prinviceSanba: itemse.prinviceSanba,
                    prinviceWusi: itemse.prinviceWusi
                  });
                });
              }
            } else {
              this.tableDataGRRY.data.push({
                // id: nuber,
                citySanba: items.citySanba,
                companyId: items.companyId,
                deptName: items.deptName,
                martyrs: items.martyrs,
                nationFirstHonor: items.nationFirstHonor,
                nationHonorPlice: items.nationHonorPlice,
                nationLaomo: items.nationLaomo,
                nationModelWorker: items.nationModelWorker,
                nationPerPolice: items.nationPerPolice,
                nationSanba: items.nationSanba,
                nationSecondHonor: items.nationSecondHonor,
                nationYouPolice: items.nationYouPolice,
                personFirstHonor: items.personFirstHonor,
                personSecondHonor: items.personSecondHonor,
                personThirdHonor: items.personThirdHonor,
                prinviceLaomo: items.prinviceLaomo,
                prinviceSanba: items.prinviceSanba,
                prinviceWusi: items.prinviceWusi
                // children: childrens
              });
            }
          });
        });
      } else if (this.activeTab == "jxkhAttList") {
        this.tableDataJXKH.data = [];
        this.tableDataJXKH.column = [];
        api.get_dwgk_jxkh_data(data).then(result => {
          let arrColumn = handleTabSize(result, this.tabCellWidth);
          this.tableDataJXKH.data = result.data;
          this.tableDataJXKH.column = arrColumn;
          this.tableDataJXKH.totalRecord = result.totalRecord;
        });
      }
    },
    //个人荣誉行数据选中方法,弹出弹出框
    // handleSelectionChange(items) {
      //   console.log(items);
      // this.innerVisible = true;
      // this.listQuerys.departIds = [];
      // this.listQuerys.departIds.push(items.companyId);
      // let data = {
      //   departIds: this.listQuerys.departIds,
      //   pageSize: 999,
      //   pageNo: 1
      // };
      // api.get_dwgk_person_data(data).then(result => {
      // 生成表格
      // let length = this.listQuerys.pageSize * (this.listQuerys.pageNo - 1);
      // this.tableDataGRRYs.data = result.data.splice(
      //   length,
      //   this.listQuerys.pageSize
      // );
      //     this.tableDataGRRYs.data = result.data;
      //     this.tableDataGRRYs.totalRecord = result.data.length;
      //   });
    // }
    // handleSizeChanges(val) {
    //   this.listQuerys.pageSize = val;
    //   let data = {
    //     departIds: this.listQuerys.departIds,
    //     pageSize: this.listQuerys.pageSize,
    //     pageNo: this.listQuerys.pageNo
    //   };
    //   api.get_dwgk_person_data(data).then(result => {
    //     // 生成表格
    //     let length = this.listQuerys.pageSize * (this.listQuerys.pageNo - 1);
    //     this.tableDataGRRYs.data = result.data.splice(
    //       length,
    //       this.listQuerys.pageSize
    //     );
    //     this.tableDataGRRYs.totalRecord = result.data.length;
    //   });
    // },
    // handleCurrentChanges(val) {
    //   this.listQuerys.pageNo = val;
    //   let data = {
    //     departIds: this.listQuerys.departIds,
    //     pageSize: this.listQuerys.pageSize,
    //     pageNo: this.listQuerys.pageNo
    //   };
    //   api.get_dwgk_person_data(data).then(result => {
    //     // 生成表格
    //     let length = this.listQuerys.pageSize * (this.listQuerys.pageNo - 1);
    //     this.tableDataGRRYs.data = result.data.splice(
    //       length,
    //       this.listQuerys.pageSize
    //     );
    //     this.tableDataGRRYs.totalRecord = result.data.length;
    //   });
    // }
  }
};
</script>

<style scoped>
.el-form-item {
  display: inline-block;
}
.customSelect {
  position: relative;
  width: 100%;
}
.xzdw {
  top: -40px;
  left: 100px;
}
.xznd {
  top: -40px;
  /* left: 100px; */
}
.customTabs {
  position: relative;
  top: -55px;
}
.left {
  float: left;
  width: 150px;
  height: 80%;
  overflow: auto;
  border-right: 1px solid #e8e8e8;
}
.listStyle li {
  width: 100%;
  height: 43px;
  line-height: 43px;
  text-indent: 50px;
  cursor: pointer;
}
.listStyle li.active {
  background-color: #1890ff;
  color: #ffffff;
}
.right {
  float: left;
  width: 100%;
  height: 80%;
  overflow: auto;
}
.el-pagination {
  margin-top: 5px;
}
.righttable {
  width: 100%;
  height: 90%;
  min-height: 360px;
}
.rights {
  float: left;
  width: 100%;
  /* height: 80%; */
  overflow: auto;
  height: 400px;
}
.lefts {
  float: left;
  width: 150px;
  /* height: 80%; */
  height: 400px;
  overflow: auto;
  border-right: 1px solid #e8e8e8;
}
.righttables {
  width: 100%;
  /* height: 90%; */
  height: 360px;
}
</style>
