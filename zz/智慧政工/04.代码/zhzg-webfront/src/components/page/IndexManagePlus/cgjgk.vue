<template>
    <div>
        <el-form>
            <el-form-item label="选择单位" :label-width="formLabelWidth" class="customSelect xzdw">
                <el-select v-model="UnitValue" popper-class="customManageBox" placeholder="请选择" multiple collapse-tags>
                    <el-option :value="UnitValue">
                            <el-tree
                            style="height: 200px"
                            :data="unitOptionsCGJ"
                            show-checkbox
                            node-key="unitId"
                            ref="RYGKtree"
                            check-on-click-node
                            @check="handleNodeCheckCGJ"
                            :props="dataProps">
                            </el-tree>
                    </el-option>
                </el-select>
            </el-form-item>
               <el-form-item label="选择年度" :label-width="formLabelWidth" class="customSelect xznd">              
                <el-select v-model="cgjgklYearValue" class="year" @change="selectYear">
                    <el-option v-for="item in cgjgklYearOptions" :key="item.value" :label="item.name" :value="item.value">
                    </el-option>
                </el-select>
            </el-form-item>               
            <div style="float: right;position: absolute;top: 60px;right: 21px;">
              <el-button type="primary"  @click="leadingout">导出</el-button>
            </div>
            <table  id="tipMessageLabel" v-show="false">
              <tr>
                <th>姓名</th>
                <th>单位</th>
                <th>出国境目的地</th>
                <th>开始日期</th>
                <th>结束日期</th>
                <th>申请时间</th>
              </tr>
              <tr v-for="item in multipleSelection">
                <td>{{item.userName}}</td>
                <td>{{item.deptName}}</td>
                <td>{{item.destination}}</td>
                <td>{{item.startTime}}</td>
                <td>{{item.endTime}}</td>
                <td>{{item.applyTime}}</td>
              </tr>
            </table>
        </el-form>
        <el-tabs class="customTabs" v-model="activeTab" @tab-click="handleClick">
            <el-tab-pane label="出国境申请时间" name="csAttList">
                <div :class="this.ifFullse ==false?'lefts':'left'">
                    <ul class="listStyle" style="overflow:auto">
                        <li v-for="(item,index) in tabList.csAttList" :key="index" :class="activeClass == index?'active':''" @click="btnGetItem(index)">{{ item.name }}</li>
                    </ul>
                </div>
                <!-- :width="col.length" -->
                <div :class="this.ifFullse ==false?'rights':'right'">
                    <el-table :data="tableDataCS.data" :height="this.ifFullse ==false?'360':''" :class="this.ifFullse ==false?'righttables':'righttable'">
                        <el-table-column :min-width="col.minWidth" :show-overflow-tooltip="true" v-for="col in tableDataCS.column" :prop="col.prop" :label="col.label" >
                        </el-table-column>
                    </el-table>
                    <el-pagination
                        style="float: right"
                        background
                        :total="tableDataCS.totalRecord"
                        :page-sizes="[20, 30, 40]"
                        :page-size="20"
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        :current-page.sync="listQuery.defaultPage"
                        layout="total, sizes, prev, pager, next">
                    </el-pagination>
                </div>
            </el-tab-pane>
            <el-tab-pane label="出国境目的地" name="mddAttList">
                <div :class="this.ifFullse ==false?'lefts':'left'">
                    <ul class="listStyle" style="overflow:auto">
                        <li v-for="(item,index) in tabList.mddAttList" :key="index" :class="activeClass == index?'active':''" @click="btnGetItem(index)">{{ item.name }}</li>
                    </ul>
                </div>
                <div :class="this.ifFullse ==false?'rights':'right'">
                    <el-table :data="tableDataMDD.data" :height="this.ifFullse ==false?'360':''" :class="this.ifFullse ==false?'righttables':'righttable'">
                        <el-table-column prop="applicantName" label="姓名" width="150"></el-table-column>
                        <el-table-column prop="deptName" label="单位"  ></el-table-column>
                        <el-table-column prop="count" label="次数" width="150"></el-table-column>
                    </el-table>
                    <el-pagination
                        style="float: right"
                        background
                        :total="tableDataMDD.totalRecord"
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
    </div>
</template>
<script>
import api from "./api";
import { handleTabSize } from "@/utils/utils";
export default {
  props: ["departIds1", "ifFullScreen"],
  data: function() {
    return {
      multipleSelection: [],
      departIds1: [],
      UnitValue: [],
      unitOptionsCGJ: [],
      dataProps: {
        children: "childCompany",
        label: "unitName"
      },
      value: "",
      formLabelWidth: "120px",
      cgjgklYearValue: "",
      cgjgklYearOptions: [],
      // tab
      activeTab: "csAttList",
      // ul
      tabList: {
        csAttList: [
          { name: "1月" },
          { name: "2月" },
          { name: "3月" },
          { name: "4月" },
          { name: "5月" },
          { name: "6月" },
          { name: "7月" },
          { name: "8月" },
          { name: "9月" },
          { name: "10月" },
          { name: "11月" },
          { name: "12月" }
        ],
        mddAttList: []
      },
      activeClass: "0",
      listQuery: {
        departIds: [],
        type: "巴西",
        year: "2019",
        // pagination
        pageNo: 1,
        pageSize: 20,
        defaultPage: 1
      },
      // table
      tableDataMDD: {
        data: [],
        column: [],
        totalRecord: 0
      },
      tableDataCS: {
        data: [],
        column: [],
        totalRecord: 0
      },
      tabCellWidth: 0,
      ifFullScreen: true,
      ifFullse: true
    };
  },
  watch: {
    ifFullScreen() {
      console.log(this.ifFullScreen);
      this.ifFullse = this.ifFullScreen;
    }
  },
  mounted() {
    this.ifFullse = this.ifFullScreen;
    this.listQuery.departIds = this.departIds1;
    this.tabCellWidth = parseFloat(
      document.getElementsByClassName("right")[0].offsetWidth
    );
    this.setDialogTitle();
    let idNumber = localStorage.getItem("idCard");
    // 调用获取单位方法，初始化单位及表格数据
    this.getUnitTree({ idNumber }).then(() => {
      this.setAllChecKeys();
      // 获取当前勾选的departIds数组
      this.getChecKeys();
    });
  },
  methods: {
    //设置弹出框标题
    setDialogTitle() {
      this.$emit("listenChild", "出国境概况");
    },
    //json对象转化成json数组对象
    stringToArray(result) {
      let arrColumn = [];
      // 格式转换{"prop":"name","label":"姓名"},
      for (let i in result.column) {
        arrColumn.push({ prop: i, label: result.column[i] });
      }
      return arrColumn;
    },
    // 获取单位
    getUnitTree(departIds) {
      return api
        .get_cgjgk_unitdata(departIds)
        .then(result => {
          return result.data;
        })
        .then(departIds => {
          this.unitOptionsCGJ = departIds;
        });
    },
    // 获取年份
    getYear(departIds) {
      let data = {departIds: departIds};
      return api
        .get_cgjgk_year(data)
        .then(result => {
          return result;
        })
        .then(yearList => {
          yearList = yearList || {};
          var options = yearList.map((a, i) => {
            if (a.name && a.value) {
              return {
                name: a.name,
                value: a.value,
                month: a.month
              };
            }
          });
          this.cgjgklYearOptions = options;
          this.cgjgklYearValue = (
            this.cgjgklYearOptions[0] || { value: "" }
          ).value;
          this.listQuery.year = this.cgjgklYearValue;
          if (this.activeTab == "csAttList") {
            if(yearList.length>0){
              this.activeClass = Number(yearList[0].month-1);
              this.listQuery.type = this.activeClass + 1 + "月";
            }else{
              this.activeClass = 0;
              this.listQuery.type = "1月";
            }
          }
          this.getTableData(
            this.listQuery.departIds,
            this.listQuery.type,
            this.listQuery.year,
            this.listQuery.pageNo,
            this.listQuery.pageSize
          );
          this.getCountry(this.listQuery.departIds, this.listQuery.year);
        });
    },
    //选择年份
    selectYear(item) {
      this.listQuery.year = item;
      this.cgjgklYearOptions.forEach(element => {
        if (item == element.value) {
          this.activeClass = Number(element.month-1);
          this.listQuery.type = this.activeClass + 1 + "月";
        }
      });
      this.getCountry(this.listQuery.departIds, this.listQuery.year);
      this.getTableData(
        this.listQuery.departIds,
        this.listQuery.type,
        this.listQuery.year,
        this.listQuery.pageNo,
        this.listQuery.pageSize
      );
    },
    // 获取目的地
    getCountry(departIds, year) {
      let data = {
          departIds: departIds,
          year: year
        },
        _this = this;
      return api
        .get_cgjgk_country(data)
        .then(result => {
          let arr = [];
          for (let key in result) {
            arr.push(key);
          }
          return result;
        })
        .then(countryList => {
          this.tabList.mddAttList = [];
          for (var i in countryList) {
            this.tabList.mddAttList.push({ name: i });
          }
          if (this.activeTab == "mddAttList") {
            this.listQuery.type = this.tabList.mddAttList[0].name;
          }
          // this.getTableData(
          //   this.listQuery.departIds,
          //   this.listQuery.type,
          //   this.listQuery.year,
          //   this.listQuery.pageNo,
          //   this.listQuery.pageSize
          // );
        });
    },
    // 设置默认勾选根目录
    setAllChecKeys() {
      this.$refs.RYGKtree.setCheckedKeys([this.unitOptionsCGJ[0].unitId]);
    },
    // 获取勾选的目录
    getChecKeys() {
      let departIdse = this.$refs.RYGKtree.getCheckedKeys();
      if (departIdse.length > 0) {
        this.listQuery.departIds = [];
        this.listQuery.departIds = this.$refs.RYGKtree.getCheckedKeys();
      }
      this.getYear(this.listQuery.departIds);
    },
    // 单位勾选 获取departIds
    handleNodeCheckCGJ(item, node) {
      var checkArray = node.checkedNodes.map((a, i) => {
        return {
          name: a.unitId
        };
      });
      this.listQuery.departIds = node.checkedKeys;
      this.getYear(this.listQuery.departIds)
      // 查询生成表
      this.getTableData(
        this.listQuery.departIds,
        this.listQuery.type,
        this.listQuery.year,
        this.listQuery.pageNo,
        this.listQuery.pageSize
      );
    },
    // tabs板块切换
    handleClick(tab, event) {
      this.activeTab == tab.paneName; //每次切换都预设activeTab到model里
      this.resetItem(); //重置
    },
    // 类别点击切换 获取type
    btnGetItem(index) {
      this.activeClass = index;
      this.listQuery.type = this.tabList[this.activeTab][index].name;
      this.listQuery.pageNo = 1;
      this.listQuery.pageSize = 20;
      this.listQuery.defaultPage = 1;
      this.getTableData(
        this.listQuery.departIds,
        this.listQuery.type,
        this.listQuery.year,
        this.listQuery.pageNo,
        this.listQuery.pageSize
      );
    },
    // tabs切换时候，重置 左侧item和分页、，默认选择第一行
    resetItem() {
      if (this.activeTab == "csAttList") {
        // let data = {};
        // return api
        //   .get_cgjgk_year(data)
        //   .then(result => {
        //     return result;
        //   })
        //   .then(yearList => {
        //     this.cgjgklYearOptions = yearList[0];
        //     if (this.activeTab == "csAttList") {
        //       let date = "";
        //       yearList.forEach(items => {
        //         if (items.month) {
        //           date = items.month;
        //         }
        //       });
        //       this.activeClass = Number(date) - 1;
        //       let months = Number(date);
        //       this.listQuery.type = months + "月";
        //     }
        this.btnGetItem(0);
        // });
      } else {
        this.btnGetItem(0);
      }
    },
    // 分页部分
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.listQuery.pageSize = val;
      this.getTableData(
        this.listQuery.departIds,
        this.listQuery.type,
        this.listQuery.year,
        this.listQuery.pageNo,
        this.listQuery.pageSize
      );
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.listQuery.pageNo = val;
      this.getTableData(
        this.listQuery.departIds,
        this.listQuery.type,
        this.listQuery.year,
        this.listQuery.pageNo,
        this.listQuery.pageSize
      );
    },
    // 6:获取表格数据
    getTableData(departIds, type, year, pageNo, pageSize) {
      let data = {
        departIds: departIds,
        type: type,
        year: year,
        pageNo: pageNo,
        pageSize: pageSize
      };
      // 各tabs（年龄、性别、名族...）接口请求
      if (this.activeTab == "mddAttList") {
        this.tableDataMDD.data = [];
        // this.tableDataMDD.totalRecord = 0;
        api.get_cgjgk_data(data).then(result => {
          //json对象转化成json数组对象
          // let arrColumn = this.stringToArray(result);
          // 生成表格
          // this.tableDataMDD.column = arrColumn;
          this.tableDataMDD.totalRecord = result.data.length;
          let length = pageSize * (pageNo - 1);
          this.tableDataMDD.data = result.data.splice(length, pageSize);
        });
      } else if (this.activeTab == "csAttList") {
        this.tableDataCS.data = [];
        this.tableDataCS.column = [];
        // this.tableDataCS.totalRecord = 0;
        api.get_cgjgk_abroad(data).then(result => {
          //json对象转化成json数组对象
          let arrColumn = handleTabSize(result, this.tabCellWidth);
          this.tableDataCS.data = result.data;
          this.tableDataCS.column = arrColumn;
          this.tableDataCS.totalRecord = result.totalRecord;
          Object.keys(result.column).forEach(key => {
            if (result.column[key] != "单位") {
              let minWidths = result.column[key].length * 20;
              this.tableDataCS.column.forEach((itemss, d) => {
                if (itemss.label == result.column[key]) {
                  this.tableDataCS.column[d].minWidth = minWidths;
                }
              });
            } else {
              this.tableDataCS.column[0].minWidth = 140;
            }
          });
          console.log(this.tableDataCS);
        });
      }
    },
    leadingout() {
      this.multipleSelection = [];
      let types = "";
      this.tabList.mddAttList.forEach(item => {
        types += item.name + ",";
      });
      let data = {
        departIds: this.listQuery.departIds,
        type: this.tabList.mddAttList.length > 0 ? types : "",
        year: this.cgjgklYearValue
      };
      api.get_cgjgk_BoundAll(data).then(result => {
        this.multipleSelection = result.data;
        // 生成表格
        setTimeout(() => {
          $("#tipMessageLabel").table2excel({
            // 不被导出的表格行的CSS class类
            exclude: ".noExl",
            name: "Excel Document Name",
            filename: "出国境概况.xls"
          });
        }, 1000);
      });
    }
  }
};
</script>

<style scoped>
.customSelect {
  position: absolute;
  top: 17px;
  left: 100px;
}
.xzdw {
  left: 100px;
}
.xznd {
  left: 420px;
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
  width: calc(100% - 151px);
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
  width: calc(100% - 151px);
  /* height: 80%; */
  overflow: auto;
  height: 400px;
}
.lefts {
  float: left;
  width: 150px;
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