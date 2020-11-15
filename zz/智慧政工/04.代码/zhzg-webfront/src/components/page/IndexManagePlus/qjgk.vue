<template>
    <div>
        <el-form>
            <el-form-item label="选择单位" :label-width="formLabelWidth" class="customSelect xzdw">
                <el-select v-model="UnitValue" popper-class="customManageBox" placeholder="请选择" multiple collapse-tags>
                    <el-option :value="UnitValue">
                            <el-tree
                            style="height: 200px"
                            :data="unitOptionsQJGK"
                            show-checkbox
                            node-key="unitId"
                            ref="QJGKtree"
                            check-on-click-node
                            @check="handleNodeCheckQJGK"
                            :props="dataProps">
                            </el-tree>
                    </el-option>
                </el-select>
            </el-form-item>       
            <el-form-item label="选择年度" :label-width="formLabelWidth" class="customSelect xznd">              
                <el-select v-model="qjgklYearValue" class="year" @change="selectYear">
                    <el-option v-for="item in qjgklYearOptions" :key="item.value" :label="item.name" :value="item.value">
                    </el-option>
                </el-select>
            </el-form-item>
        <div style="float: right;position: absolute;top: 60px;right: 121px;">
          <el-button type="primary"  @click="leadingoutss">指定导出</el-button>
        </div>
        <div style="float: right;position: absolute;top: 60px;right: 21px;">
          <el-button type="primary"  @click="leadingout">全部导出</el-button>
        </div>
        <table  id="tipMessageLabel" v-show="false">
             <tr>
                <th>姓名</th>
                <th>单位</th>
                <th>请假类型</th>
                <th>开始日期</th>
                <th>结束日期</th>
                <th>申请时间</th>
              </tr>
              <tr v-for="item in multipleSelection">
                <td>{{item.userName}}</td>
                <td>{{item.deptName}}</td>
                <td>{{item.leaveType}}</td>
                <td>{{item.startTime}}</td>
                <td>{{item.endTime}}</td>
                <td>{{item.applyTime}}</td>
              </tr>
        </table> 
        </el-form>
        <el-tabs class="customTabs" v-model="activeTab">
            <div>
                <div :class="this.ifFullse ==false?'lefts':'left'">
                    <ul class="listStyle" style="overflow:auto">
                        <li v-for="(item,index) in tabList.qjAttList" :key="index" :class="activeClass == index?'active':''" @click="btnGetItem(index)">{{ item.name }}</li>
                    </ul>
                </div>
                <div :class="this.ifFullse ==false?'rights':'right'">
                    <el-table  :height="this.ifFullse ==false?'360':''" :class="this.ifFullse ==false?'righttables':'righttable'" :data="tableDataQJGK.data">
                        <el-table-column :min-width="col.minWidth" :show-overflow-tooltip="true" v-for="col in tableDataQJGK.column" :prop="col.prop" :label="col.label" >
                        </el-table-column>
                    </el-table>
                    <el-pagination
                        style="float: right"
                        background
                        :total="tableDataQJGK.totalRecord"
                        :page-sizes="[20, 30, 40]"
                        :page-size="20"
                        @size-change="handleSizeChange"
                        @current-change="handleCurrentChange"
                        :current-page.sync="listQuery.defaultPage"
                        layout="total, sizes, prev, pager, next">
                    </el-pagination>
                </div>
            </div>
        </el-tabs>           
    </div>
</template>
<script>
import api from "./api";
import { handleTabSize } from "@/utils/utils";
export default {
  props: ["departIds2", "ifFullScreen"],
  data: function() {
    return {
      // UnitData
      UnitValue: [],
      multipleSelection: [],
      unitOptionsQJGK: [],
      dataProps: {
        children: "childCompany",
        label: "unitName"
      },
      value: "",
      qjgklYearValue: "2019",
      qjgklYearOptions: [],
      formLabelWidth: "120px",
      // tab
      activeTab: "qjAttList",
      // ul
      tabList: {
        qjAttList: [
          { name: "节假日离宁" },
          { name: "年休假" },
          { name: "探亲假" },
          { name: "陪产假" },
          { name: "产假" },
          { name: "婚假" },
          { name: "病假" },
          { name: "事假" },
          { name: "丧假" },
          { name: "调休" },
          { name: "因公离宁" },
          { name: "集体离宁" }
        ]
      },
      activeClass: "0",
      listQuery: {
        departIds: [],
        type: "节假日离宁",
        year: "2019",
        // pagination
        pageNo: 1,
        pageSize: 20,
        defaultPage: 1
      },
      // table
      tableDataQJGK: {
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
    this.listQuery.departIds = this.departIds2;
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
    // UnitValues(unitOptions) {
    //   unitOptions.forEach(itemse => {
    //     this.listQuery.departIds.forEach(items => {
    //       if (itemse.unitId == items) {
    //         this.UnitValue.push(itemse.unitName);
    //       }
    //     });
    //     if (itemse.childCompany && itemse.childCompany.length > 0) {
    //       this.UnitValues(itemse.childCompany);
    //     }
    //   });
    // },
    //设置弹出框标题
    setDialogTitle() {
      this.$emit("listenChild", "请假概况");
    },
    // 获取单位
    getUnitTree(departIds) {
      return api
        .get_qjgk_unitdata(departIds)
        .then(result => {
          return result.data;
        })
        .then(departIds => {
          this.unitOptionsQJGK = departIds;
        });
    },
    // 获取年份
    getYear(departIds) {
      let data = { departIds: departIds };
      return api
        .get_qjgk_year(data)
        .then(result => {
          return result;
        })
        .then(yearList => {
          yearList = yearList || {};
          var options = yearList.map((a, i) => {
            if (a.name && a.value) {
              return {
                name: a.name,
                value: a.value
              };
            }
          });
          this.qjgklYearOptions = options;
          this.qjgklYearValue = (
            this.qjgklYearOptions[0] || { value: "" }
          ).value;
          this.listQuery.year = this.qjgklYearValue;
          // 传参 单位、type、pageNo、pageSize
          this.getTableData(
            this.listQuery.departIds,
            this.listQuery.type,
            this.listQuery.year,
            this.listQuery.pageNo,
            this.listQuery.pageSize
          );
        });
    },
    //选择年份
    selectYear(item) {
      console.log(item);
      this.listQuery.year = item;
      this.getTableData(
        this.listQuery.departIds,
        this.listQuery.type,
        this.listQuery.year,
        this.listQuery.pageNo,
        this.listQuery.pageSize
      );
    },
    // 设置默认勾选根目录
    setAllChecKeys() {
      this.$refs.QJGKtree.setCheckedKeys([this.unitOptionsQJGK[0].unitId]);
    },
    // 获取勾选的目录
    getChecKeys() {
      let departIdsd = this.$refs.QJGKtree.getCheckedKeys();
      if (departIdsd.length > 0) {
        this.listQuery.departIds = departIdsd;
        // if (this.unitOptionsQJGK.length > 0) {
        //   this.UnitValue = [];
        //   this.UnitValues(this.unitOptionsQJGK);
        // }
      }
      this.getYear(this.listQuery.departIds);
    },
    // 单位勾选 获取departIds
    handleNodeCheckQJGK(item, node) {
      var checkArray = node.checkedNodes.map((a, i) => {
        return {
          name: a.unitId
        };
      });
      // var checkArrays = node.checkedNodes.map((a, i) => {
      //   return a.unitName;
      // });
      this.listQuery.departIds = node.checkedKeys;
      // this.UnitValue = checkArrays;
      this.getYear(this.listQuery.departIds);
      // 查询生成表
      // this.getTableData(
      //   this.listQuery.departIds,
      //   this.listQuery.type,
      //   this.listQuery.year,
      //   this.listQuery.pageNo,
      //   this.listQuery.pageSize
      // );
    },
    // 类别点击切换 获取type
    btnGetItem(index) {
      this.activeClass = index;
      this.listQuery.type = this.tabList[this.activeTab][index].name;
      this.listQuery.pageNo = 1;
      this.listQuery.pageSize = 20;
      this.listQuery.defaultPage = 1;
      // tabList.nlAttList
      this.getTableData(
        this.listQuery.departIds,
        this.listQuery.type,
        this.listQuery.year,
        this.listQuery.pageNo,
        this.listQuery.pageSize
      );
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
      this.tableDataQJGK.data = [];
      this.tableDataQJGK.column = [];
      api.get_qjgk_data(data).then(result => {
        //json对象转化成json数组对象
        let arrColumn = handleTabSize(result, this.tabCellWidth);
        // 生成表格
        this.tableDataQJGK.data = result.data;
        this.tableDataQJGK.column = arrColumn;
        this.tableDataQJGK.totalRecord = result.totalRecord;
        Object.keys(result.column).forEach(key => {
          if (result.column[key] != "单位") {
            let minWidths = result.column[key].length * 30;
            this.tableDataQJGK.column.forEach((itemss, d) => {
              if (itemss.label == result.column[key]) {
                this.tableDataQJGK.column[d].minWidth = minWidths;
              }
            });
          }
        });
      });
    },
    //全部导出
    leadingout() {
      this.multipleSelection = [];
      let types = [];
      this.tabList.qjAttList.forEach(item => {
        types.push(item.name);
      });
      let data = {
        departIds: this.listQuery.departIds,
        types: this.tabList.qjAttList.length > 0 ? types : [],
        year: this.qjgklYearValue
      };
      api.get_qjgk_PleseOffAll(data).then(result => {
        this.multipleSelection = result.data;
        // 生成表格
        setTimeout(() => {
          $("#tipMessageLabel").table2excel({
            // 不被导出的表格行的CSS class类
            exclude: ".noExl",
            name: "Excel Document Name",
            filename: "请假概况.xls"
          });
        }, 1000);
      });
    },
    //指定导出
    leadingoutss() {
      this.multipleSelection = [];
      let types = [];
      types.push(this.listQuery.type);
      let data = {
        departIds: this.listQuery.departIds,
        types: this.tabList.qjAttList.length > 0 ? types : [],
        year: this.qjgklYearValue
      };
      api.get_qjgk_PleseOffAll(data).then(result => {
        this.multipleSelection = result.data;
        // 生成表格
        setTimeout(() => {
          console.log(this.multipleSelection);
          $("#tipMessageLabel").table2excel({
            // 不被导出的表格行的CSS class类
            exclude: ".noExl",
            name: "Excel Document Name",
            filename: "请假概况.xls"
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