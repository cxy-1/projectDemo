<template>
    <div class="systembody minwidth">
       <div class="systemrow titlese">首页/人员发展数据维护</div>
       <div class="systemrow tabletitle">人员发展数据列表</div>
       <div class="systemrow tablechoel">
         <el-row>
           <el-col style="padding-left: 40px;" :span="9">
             <span style="padding-right: 10px;">姓名:</span>
             <el-input style="width: 60%;" maxlength="10" v-model="templatevalue"></el-input>
           </el-col>
           <el-col style="padding-left: 40px;" :span="9">
             <span style="padding-right: 10px;">警号:</span>
             <el-input style="width: 60%;" maxlength="10" v-model="plicenumber"></el-input>
           </el-col>
           <el-col :span="6">
             <el-button type="primary" round @click="queryclick">查询</el-button>
             <el-button round @click="rizesclick">重置</el-button>
           </el-col>
         </el-row>
       </div>
       <div class="systemrow">
          <div class="systemrow" style="line-height: 50px;padding-left: 20px;">
            <el-button type="primary" round @click="addtemplate">新增</el-button>
            <el-button type="danger" round @click="deteleshow('')">删除</el-button>
          </div>
          <div class="systemrow">
            <el-table :data="tableDatase" v-loading="loadingse" @select="selection"  @select-all="selectionallChange"  stripe style="width: 100%">
              <el-table-column type="selection" width="55"></el-table-column>
              <el-table-column align="center" :show-overflow-tooltip="true" prop="PERSON_NAME" label="姓名" />
              <el-table-column align="center" :show-overflow-tooltip="true" prop="SEX" label="性别" />
              <el-table-column align="center" :show-overflow-tooltip="true" prop="POLICE_NO" label="警号" />
              <el-table-column align="center" :show-overflow-tooltip="true" prop="POST" label="职务" />
              <!-- <el-table-column align="center" :show-overflow-tooltip="true" prop="dept" label="部门" /> -->
              <el-table-column align="center" :show-overflow-tooltip="true" prop="DEPT_NAME" label="工作单位" />
              <el-table-column  align="center" label="操作">
                <template slot-scope="scope">
                  <el-button
                    size="mini"
                    @click="handleEdit(scope.row)">详情</el-button>
                  <el-button
                    size="mini"
                    type="primary"
                    @click="handleload(scope.row)">修改</el-button>
                  <el-button
                    size="mini"
                    type="danger"
                    @click="deteleshow(scope.row)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
            <el-pagination style="margin: 20px 40px 0 0;text-align: -webkit-right;" background :total="totalRecord"
                  :page-sizes="[10, 20, 30, 40]" :page-size="pagesize" :current-page.sync="pageno" @size-change="handleSizeChange" @current-change="handleCurrentChange" layout="total, sizes, prev, pager, next, jumper">
            </el-pagination> 
          </div>
       </div>
       <el-dialog width="60%" :title="agetitle" :visible.sync="ageVisible" append-to-body>
         <div v-if="agetitle == '新增'||agetitle == '修改'" class="systemrow" style="margin-top: 20px;background: #fff;">
           <div  v-if="agetitle == '新增'" class="systemrow" style="line-height: 60px;padding-left: 20px;">
             <el-row>
              <el-col :span="14" style="padding-left: 20px;" >
                <div class="systemrow" @click="personalsecets()" style="cursor: pointer;">
                    <span style="color:red;">*</span><span style="padding-right: 10px;">人员选择:</span>
                    <el-input style="width: 80%;" readonly v-model="pliceman"></el-input>
                </div>
              </el-col>
            </el-row>
           </div>
          <div class="systemrow" style="padding: 20px;">
            <el-row>
              <el-col :span="24" style="padding-left: 20px;margin-bottom: 20px;">
                  <el-button type="primary" round @click="addpersondatase">新增</el-button>
                  <el-button type="danger" round @click="detepersonshow(multipleSelection,'')">删除</el-button>
              </el-col>
              <el-col :span="24" style="border: 2px solid #ccc;">
                <el-table ref="multipleTable" :data="addtableData" max-height="500" tooltip-effect="dark" style="width: 100%"  @selection-change="handleSelectionChange">
                    <el-table-column type="index" label="序号" align="center" width="50">
                        <template slot-scope="scope">
                            {{scope.row.index}}
                        </template>
                    </el-table-column>
                    <el-table-column type="selection" width="55"></el-table-column>
                    <el-table-column align="center" label="日期" width="260">
                        <template slot-scope="scope">
                            <el-date-picker format="yyyy 年 MM 月" value-format="yyyyMM" :picker-options="pickerOptions" v-model="scope.row.time" type="month" placeholder="选择月" :disabled="!scope.row.timeEnable"></el-date-picker>
                        </template>
                    </el-table-column>
                    <el-table-column align="center" label="维护内容">
                        <template slot-scope="scope">
                            <div style="padding-bottom: 5px;"><span style="width:80px;display: inline-block;">单位:</span><el-input style="width: 85%;" placeholder="请输入内容" v-model="scope.row.deptName" :disabled="true"></el-input></div>
                            <div style="padding-bottom: 5px;"><span style="width:80px;display: inline-block;">任职:</span><el-input style="width: 85%;" type="textarea" :rows="1" placeholder="请输入内容" v-model="scope.row.dutyChange"></el-input></div>
                            <div style="padding-bottom: 5px;"><span style="width:80px;display: inline-block;">年度考核:</span><el-input style="width: 85%;" type="textarea" :rows="1" placeholder="请输入内容" v-model="scope.row.assess"></el-input></div>
                            <div style="padding-bottom: 5px;"><span style="width:80px;display: inline-block;">个人荣誉:</span><el-input style="width: 85%;" type="textarea" :rows="1" placeholder="请输入内容" v-model="scope.row.honor"></el-input></div>
                            <div style="padding-bottom: 5px;"><span style="width:80px;display: inline-block;">附加信息:</span><el-input style="width: 85%;" type="textarea" :rows="1" placeholder="请输入内容" v-model="scope.row.extra"></el-input></div>
                        </template>
                    </el-table-column>
                    <el-table-column align="center" width="100" label="操作">
                        <template slot-scope="scope">
                            <el-button type="danger" size="mini" round @click="detepersonshow(scope.$index,'删除')">删除</el-button>
                        </template>
                    </el-table-column>
                </el-table>
              </el-col>
            </el-row>
          </div>
          <div class="systemrow" style="text-align: -webkit-center;min-height: 60px;padding-top: 10px;padding-left: 20px;">
            <el-button round @click="canelclick">取消</el-button>
            <el-button type="primary" round @click="addcureclick">保存</el-button>
         </div>
         </div>
         <div v-if="agetitle == '详情'" class="systemrow" style="margin-top: 20px;background: #fff;">
          <div class="systemrow" style="padding: 20px;">
            <el-row>
              <el-col :span="24" style="border: 2px solid #ccc;">
                <el-table ref="multipleTable" :data="detailtableData" max-height="500" tooltip-effect="dark" style="width: 100%">
                    <el-table-column type="index" align="center" width="50"></el-table-column>
                    <el-table-column align="center" label="日期" width="260">
                        <template slot-scope="scope">
                            <el-date-picker format="yyyy 年 MM 月" :picker-options="pickerOptions" value-format="yyyyMM" :disabled="true" v-model="scope.row.time" type="month" placeholder="选择月"></el-date-picker>
                        </template>
                    </el-table-column>
                    <el-table-column align="center" label="维护内容">
                        <template slot-scope="scope">
                            <div style="padding-bottom: 5px;"><span style="width:80px;display: inline-block;">单位:</span><el-input style="width: 85%;" placeholder="请输入内容" v-model="scope.row.deptName" :disabled="true"></el-input></div>
                            <div style="padding-bottom: 5px;"><span style="width:80px;display: inline-block;">任职:</span><el-input style="width: 85%;" type="textarea" :rows="1" placeholder="请输入内容" :disabled="true" v-model="scope.row.dutyChange"></el-input></div>
                            <div style="padding-bottom: 5px;"><span style="width:80px;display: inline-block;">年度考核:</span><el-input style="width: 85%;" type="textarea" :rows="1" placeholder="请输入内容" :disabled="true" v-model="scope.row.assess"></el-input></div>
                            <div style="padding-bottom: 5px;"><span style="width:80px;display: inline-block;">个人荣誉:</span><el-input style="width: 85%;" type="textarea" :rows="1" placeholder="请输入内容" :disabled="true" v-model="scope.row.honor"></el-input></div>
                        </template>
                    </el-table-column>
                </el-table>
              </el-col>
            </el-row>
          </div>
          <div class="systemrow" style="text-align: -webkit-center;min-height: 60px;padding-top: 10px;padding-left: 20px;">
            <el-button round @click="canelclick">取消</el-button>
          </div>
         </div>
       </el-dialog>
       <el-dialog width="40%" title="警员基本信息列表" :visible.sync="ageVisiblese" append-to-body>
          <div class="systemrow" style="padding: 20px;">
            <el-row>
              <el-col :span="24" style="padding-left: 20px;margin-bottom: 20px;">
                <div class="systemrow" style="border: 1px solid #ccc;padding: 10px 0;">
                    <el-row style="margin-bottom: 20px;">
                        <el-col style="padding-left: 20px;" :span="8">
                            <span style="padding-right: 10px;">姓名:</span>
                            <el-input style="width: 60%;" maxlength="10" v-model="templatevalue1"></el-input>
                        </el-col>
                        <el-col :span="8">
                            <span style="padding-right: 10px;">警号:</span>
                            <el-input style="width: 60%;" maxlength="10" v-model="plicenumber1"></el-input>
                        </el-col>
                        <el-col style="padding-right: 10px;"  :span="8">
                            <span style="padding-right: 10px;display: inline-block;width: 20%;">单位:</span>
                            <!-- <el-input style="width: 60%;" v-model="plicenumber2"></el-input> -->
                            <span style="display: inline-block;width: 80%;">
                              <el-cascader ref="cascader" :options="optionslist" placeholder="请选择" :props="props" @change="changes" :show-all-levels="false" collapse-tags clearable></el-cascader>
                            </span>
                        </el-col>
                    </el-row>
                    <el-row style="text-align: -webkit-center;">
                        <el-col :span="24">
                            <el-button type="primary" round @click="queryclicklist">查询</el-button>
                            <el-button round @click="rizesclicklist">重置</el-button>
                        </el-col>
                    </el-row>
                </div>
              </el-col>
              <el-col :span="24" style="border: 2px solid #ccc;">
                <el-table ref="multipleTable" :data="addtableDatalist" max-height="400" tooltip-effect="dark" style="width: 100%">
                    <el-table-column align="center"  prop="personName" label="姓名" :show-overflow-tooltip="true"></el-table-column>
                    <el-table-column align="center"  prop="policeNo" label="警号" :show-overflow-tooltip="true"></el-table-column>
                    <el-table-column align="center"  prop="deptName" label="单位" :show-overflow-tooltip="true"></el-table-column>
                    <el-table-column align="center" label="操作">
                        <template slot-scope="scope">
                          <el-button type="primary" v-if="scope.row.editable == false" size="mini" round @click="selctetemplatelist(scope.row)">选择</el-button>
                          <el-button size="mini" v-if="scope.row.editable == true" round @click="primarytemplatelist(scope.row)">修改</el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <el-pagination style="margin: 20px 40px 0 0;text-align: -webkit-right;" background :total="totalRecordlist"
                  :page-sizes="[10, 20, 30, 40]" :pager-count='5' :page-size="pagesizelist" :current-page.sync="pagenolist" @size-change="handleSizeChangelist" @current-change="handleCurrentChangelist" layout="total, sizes, prev, pager, next, jumper">
                </el-pagination> 
              </el-col>
            </el-row>
          </div>
       </el-dialog>
       <el-dialog width="20%" title="删除" :visible.sync="deteVisible" append-to-body>
          <div class="systemrow" style="padding: 20px;text-align: -webkit-center;">
            删除后信息不可恢复,确认要删除此数据吗？
          </div>
          <div class="systemrow" style="text-align: -webkit-center;min-height: 60px;padding-top: 10px;padding-left: 20px;">
            <el-button type="primary" @click="deteletemplate(deteparem)">确认</el-button>
            <el-button @click="detecanelclick">取消</el-button>
          </div>
       </el-dialog>
       <el-dialog width="20%" title="删除" :visible.sync="deteVisiblese" append-to-body>
          <div class="systemrow" style="padding: 20px;text-align: -webkit-center;">
            删除后信息不可恢复,确认要删除此数据吗？
          </div>
          <div class="systemrow" style="text-align: -webkit-center;min-height: 60px;padding-top: 10px;padding-left: 20px;">
            <el-button type="primary" @click="detepersondatase(dedeltitemslist,dedeltparems)">确认</el-button>
            <el-button @click="detecanelclickse">取消</el-button>
          </div>
       </el-dialog>
    </div>
</template>
<script>
export default {
  data: function() {
    return {
      templatevalue: "",
      plicenumber: "",
      pliceman: "",
      datetimeranges: [],
      optionslist: [],
      props: { multiple: true },
      addtemplatevalue: "",
      loadingse: false,
      tableDatase: [],
      selectDatase: [],
      totalRecord: 0,
      pagesize: 10,
      pageno: 1,
      totalRecordlist: 0,
      pagesizelist: 10,
      pagenolist: 1,
      agetitle: "",
      ageVisible: false,
      ageVisiblese: false,
      deteVisible: false,
      deteVisiblese: false,
      deteparem:'',
      dedeltparems:'',
      dedeltitemslist:[],
      coent: {},
      addtableData: [],
      detetableData: [],
      multipleSelection: [],
      sumber: 0,
      templatevalue1: "",
      plicenumber1: "",
      plicenumber2: [],
      detailtableData: [],
      addtableDatalist: [],
      pickerOptions: {
        // disabledDate(time) {
        //   console.log(time);
        //   return time.getTime() > Date.now();
        // }
      },
      newDate1:"",
      newDate2:"",
      idNo: "",
      // idNumberse: "320114196610232446",
      idNumberse: ""
    };
  },
  watch: {},
  created() {
    let urls = window.location.href;
    let idNumbersed = urls.split("=");
    //加密解密
    let Base64 = require("js-base64").Base64;
    this.idNumberse = Base64.decode(idNumbersed[1]);
    console.log(this.idNumberse);
    this.queryclick();
  },
  mounted() {},
  destroyed() {
    document.querySelector("body").style.height = "100%";
    document.querySelector("html").style.height = "100%";
  },
  methods: {
    //查询
    queryclick() {
      this.loadingse = true;
      this.$axios
        .post("/beTheBest/searchEditablePerson", {
          personName: this.templatevalue,
          policeNo: this.plicenumber,
          idNumber: this.idNumberse,
          pageNo: this.pageno,
          pageSize: this.pagesize
        })
        .then(res => {
          if (res.code == 0) {
            this.tableDatase = res.data.rows;
            this.totalRecord = res.data.total;
            this.loadingse = false;
          } else {
            this.$message.error(res.message);
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    //重置
    rizesclick() {
      this.templatevalue = "";
      this.plicenumber = "";
    },
    //选人查询
    queryclicklist() {
      this.$axios
        .post("/beTheBest/searchPerson", {
          personName: this.templatevalue1,
          policeNo: this.plicenumber1,
          deptIds: this.plicenumber2,
          pageNo: this.pagenolist,
          pageSize: this.pagesizelist
        })
        .then(res => {
          if (res.code == 0) {
            this.addtableDatalist = res.data.rows;
            this.totalRecordlist = res.data.total;
          } else {
            this.$message.error(res.message);
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    //选人重置
    rizesclicklist() {
      this.templatevalue1 = "";
      this.plicenumber1 = "";
      this.plicenumber2 = "";
    },
    //选人修改
    primarytemplatelist(row) {
      this.ageVisiblese = false;
      this.agetitle = "修改";
      this.ageVisible = true;
      this.addtemplatevalue = "";
      this.detetableData = [];
      this.addtableData = [];
      this.multipleSelection = [];
      this.sumber = 0;
      this.idNo = row.idNo;
      this.dateRange(row.idNo);
      this.$axios
        .get("/perComparison/beTheBestLabel?idNumbers=" + row.idNo)
        .then(res => {
          if (res.code == 0) {
            this.addtableData = res.data;
            if (this.addtableData.length > 0) {
              this.addtableData.forEach((element, k) => {
                element["index"] = k + 1;
              });
              this.sumber = this.addtableData.length;
            }
          } else {
            this.$message.error(res.message);
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    //选人选择
    selctetemplatelist(row) {
      this.ageVisiblese = false;
      this.idNo = row.idNo;
      this.pliceman = row.personName;
      this.dateRange(row.idNo);
      this.$axios
        .get("/perComparison/beTheBestLabel?idNumbers=" + row.idNo)
        .then(res => {
          if (res.code == 0) {
            this.addtableData = res.data;
            if (this.addtableData.length > 0) {
              this.addtableData.forEach((element, k) => {
                element["index"] = k + 1;
              });
              this.sumber = this.addtableData.length;
            }
          } else {
            this.$message.error(res.message);
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    //新增
    addtemplate() {
      this.agetitle = "新增";
      this.ageVisible = true;
      this.addtemplatevalue = "";
      this.detetableData = [];
      this.addtableData = [];
      this.multipleSelection = [];
      this.sumber = 0;
      this.idNo = "";
      this.pliceman = '';
    },
    //删除
    deteletemplate(parem) {
      let detedatase = [];
      if (parem) {
        detedatase.push(parem.ID_NO);
      } else {
        this.selectDatase.forEach(element => {
          detedatase.push(element.ID_NO);
        });
      }
      this.$axios
        .post("/beTheBest/deleteInfo", detedatase)
        .then(res => {
          if (res.code == 0) {
            this.$message({
              message: "删除成功！",
              type: "success"
            });
            this.pageno = 1;
            // this.pagesize = 10;
            this.queryclick();
          } else {
            this.$message.error(res.message);
          }
        })
        .catch(function(error) {
          console.log(error);
        });
        this.deteVisible = false;
    },
    //翻页
    handleSizeChange(val) {
      console.log(`每页 ${val} 条`);
      this.pagesize = val;
      this.queryclick();
    },
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.pageno = val;
      this.queryclick();
    },
    //翻页
    handleSizeChangelist(val) {
      console.log(`每页 ${val} 条`);
      this.pagesizelist = val;
      this.queryclicklist();
    },
    handleCurrentChangelist(val) {
      console.log(`当前页: ${val}`);
      this.pagenolist = val;
      this.queryclicklist();
    },
    //详情
    handleEdit(row) {
      this.agetitle = "详情";
      this.ageVisible = true;
      this.coent = row;
      this.detailtableData = [];
      this.$axios
        .get("/perComparison/beTheBestLabel?idNumbers=" + row.ID_NO)
        .then(res => {
          if (res.code == 0) {
            this.detailtableData = res.data;
          } else {
            this.$message.error(res.message);
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    //修改
    handleload(row) {
      this.agetitle = "修改";
      this.ageVisible = true;
      this.addtemplatevalue = "";
      this.detetableData = [];
      this.addtableData = [];
      this.multipleSelection = [];
      this.sumber = 0;
      this.idNo = row.ID_NO;
      this.dateRange(row.ID_NO);
      this.$axios
        .get("/perComparison/beTheBestLabel?idNumbers=" + row.ID_NO)
        .then(res => {
          if (res.code == 0) {
            this.addtableData = res.data;
            if (this.addtableData.length > 0) {
              this.addtableData.forEach((element, k) => {
                element["index"] = k + 1;
              });
              this.sumber = this.addtableData.length;
            }
          } else {
            this.$message.error(res.message);
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    //保存
    addcureclick() {
      if (!this.idNo) {
        this.$message.error("人员未选择！");
        return;
      }
      if (this.addtableData.length == 0) {
        this.$message.error("至少需要有一条记录！");
        return;
      }
      let dataliste = [];
      let timererror = false,
        timename = "",
        timenull = false,
        detailerror = false,
        detailerrortime = "";
      if (this.addtableData.length > 0) {
        this.addtableData.forEach(item => {
          let keys = 0;
          if (item.time) {
            if (!item.dutyChange && !item.assess && !item.honor) {
              detailerror = true;
              detailerrortime = item.time;
            }
            this.addtableData.forEach(itemes => {
              if (item.time == itemes.time) {
                keys++;
                if (keys > 1) {
                  timererror = true;
                  timename = item.time;
                }
              }
            });
          } else {
            timenull = true;
          }
        });
        if (timenull == true) {
          this.$message.error("有日期未选择，请填写完整!");
          return;
        }
        if (timererror == true) {
          this.$message.error(timename + "此记录有多条记录,请重新选择!");
          return;
        }
        if (detailerror == true) {
          this.$message.error(
            detailerrortime +
              "此记录维护内容中任职，年度考核以及个人荣誉都为空，请至少填写一项!"
          );
          return;
        }
      }
      if (this.detetableData.length > 0) {
        this.detetableData.forEach(items => {
          items["delstatus"] = "1";
          dataliste.push(items);
        });
      }
      dataliste = this.addtableData.concat(this.detetableData);
      this.$axios
        .post("/beTheBest/update", {
          idNo: this.idNo,
          data: dataliste
        })
        .then(res => {
          if (res.code == 0) {
            this.ageVisible = false;
            this.$message({
              message: this.agetitle == '修改'?"修改成功!":"新增成功!",
              type: "success"
            });
            this.pageno = 1;
            // this.pagesize = 10;
            this.queryclick();
          } else {
            this.$message.error(res.message);
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    //取消
    canelclick() {
      this.ageVisible = false;
    },
    selection(selection, row) {
      this.selectDatase = selection;
    },
    selectionallChange(selection) {
      this.selectDatase = selection;
    },
    //人员选择
    personalsecets() {
      this.ageVisiblese = true;
      this.optionslist = [];
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
                this.optionslist.push(newVar);
              });
              this.queryclicklist();
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
      this.plicenumber2 = [];
      if (nodesObj.length > 0) {
        nodesObj.forEach(items => {
          let vaules = items.value;
          this.plicenumber2.push(vaules);
        });
      }
    },
    //新增人员数据
    addpersondatase() {
      this.sumber++;
      this.addtableData.push({
        index: this.sumber,
        time: "",
        deptName: "",
        dutyChange: "",
        assess: "",
        honor: "",
        timeEnable:true
      });
    },
    //删除人员数据
    detepersondatase(parem, item) {
      console.log(parem, item);
      let that=this;
      if (item == "删除") {
        this.detetableData.push(this.addtableData[parem]);
        this.addtableData.splice(parem, 1);
      } else {
        if (parem.length > 0) {
          let munberlist = [],
            munberlist2 = [];
          parem.forEach(items => {
            munberlist.push(items.index - 1);
          });
          munberlist.sort();
            let length = munberlist.length;
            for (var i = 0; i < length; i++) {
            munberlist2.push(munberlist.pop());
          }
          munberlist2.forEach(itemes => {
              that.detetableData.push(that.addtableData[itemes]);
              that.addtableData.splice(itemes, 1);
          });
        }
      }
      this.addtableData.forEach((element, k) => {
        element.index = k + 1;
      });
      this.sumber = this.addtableData.length;
      this.deteVisiblese = false;
    },
    //修改
    primarysepersondatase() {
      console.log(this.addtableData);
    },
    //复选框变动
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    // 取消
    canelclickse() {
      this.ageVisiblese = false;
    },
    //日期查询
    dateRange(idNo) {
      this.$axios
        .get("/beTheBest/dateRange?idNumber=" + idNo)
        .then(res => {
          if (res.code == 0) {
            let year1 = res.data[0].slice(0,4);
            let month1 = res.data[0].slice(4,6);
            let year2 = res.data[1].slice(0,4);
            let month2 = res.data[1].slice(4,6);
            this.newDate1 = new Date(year1,month1-1);
            this.newDate2 = new Date(year2,month2-1);
            var self = this;
            this.pickerOptions = {
              disabledDate(time) {
                return self.newDate1.getTime() > time.getTime() || time.getTime()> self.newDate2.getTime();
              }
            };
          } else {
            this.$message.error(res.message);
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    //外表格删除弹框
    deteleshow(parems){
      this.deteVisible = true;
      this.deteparem = parems;
    },
    //外表格删除弹框取消
    detecanelclick(){
      this.deteVisible = false;
    },
    //弹框内表格删除弹框
    detepersonshow(items,parems){
      this.deteVisiblese = true;
      this.dedeltparems = parems;
      this.dedeltitemslist = items;
    },
    detecanelclickse(){
      this.deteVisiblese = false;
    },
  }
};
</script>
<style scoped>
.systembody {
  width: 100%;
  height: 100%;
  background-color: #fff;
  padding: 20px;
  text-align: left;
  font-family: 微软雅黑;
  font-size: 14px;
}
.systemrow {
  width: 100%;
  margin-bottom: 10px;
}
#persondatase {
  width: 100%;
  margin-bottom: 10px;
}
.titlese {
  height: 40px;
  line-height: 40px;
  font-size: 18px;
  border-left: 8px solid #12afe7;
  padding-left: 10px;
}
.tabletitle {
  font-size: 16px;
  padding-left: 20px;
  height: 35px;
  line-height: 35px;
}
.tablechoel {
  border: 1px solid #c0bdbd;
  height: 100px;
  line-height: 100px;
  padding: 0 20px;
}
/deep/ .el-table th {
  background: #ccc;
  color: #000;
}
/deep/ .el-dialog__body {
  padding: 0px 0px 20px 0;
  color: #606266;
  font-size: 14px;
  word-break: break-all;
  border-radius: 0px 0px 10px 10px;
  /* background: #5ab1ef; */
}
/deep/ .el-dialog__header {
  padding: 10px 10px;
  background: #5ab1ef;
  border-radius: 10px 10px 0 0;
}
/deep/ .el-dialog__title {
  line-height: 24px;
  font-size: 18px;
  color: #fff;
}
/deep/ .el-dialog__headerbtn .el-dialog__close {
  color: #fff;
}
/deep/ .el-dialog {
  position: relative;
  margin: 0 auto 50px;
  -webkit-box-shadow: 0 1px 3px rgba(0, 0, 0, 0.3);
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.3);
  box-sizing: border-box;
  border-radius: 20px;
}
/deep/ .el-pagination.is-background .el-pager li:not(.disabled).active {
  background-color: #5ab1ef;
  color: #fff;
}
.minwidth{
  min-width: 1480px;
}
</style>
<style>
/deep/ .el-table th {
  background: #ccc;
  color: #000;
}
</style>