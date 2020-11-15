<template>
    <div class="systembody">
       <div class="systemrow titlese">首页/报告模板管理</div>
       <div class="systemrow tabletitle">模板管理列表</div>
       <div class="systemrow tablechoel">
         <el-row>
           <el-col :span="8">
             <span style="padding-right: 10px;">报告类型:</span>
             <el-select v-model="templatevalue" clearable placeholder="请选择">
                <el-option
                  v-for="item in templateoptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
           </el-col>
           <el-col :span="10">
             <span style="padding-right: 10px;">创建日期:</span>
             <el-date-picker v-model="datetimeranges" type="datetimerange" format="yyyy-MM-dd HH:mm:ss" value-format='yyyy-MM-dd HH:mm:ss' range-separator="至" start-placeholder="开始日期"  end-placeholder="结束日期"></el-date-picker>
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
            <el-button type="danger" round @click="deteletemplate('')">删除</el-button>
          </div>
          <div class="systemrow">
            <el-table :data="tableDatase" v-loading="loadingse" @select="selection"  @select-all="selectionallChange"  stripe style="width: 100%">
              <el-table-column type="selection" width="55"></el-table-column>
              <el-table-column align="center" :show-overflow-tooltip="true" prop="modelType" label="报告类型" />
              <el-table-column align="center" :show-overflow-tooltip="true" prop="modelLevel" label="模板级别" />
              <el-table-column align="center" :show-overflow-tooltip="true" prop="createTime" label="创建时间" />
              <el-table-column  align="center" label="操作">
                <template slot-scope="scope">
                  <el-button
                    size="mini"
                    @click="handleEdit(scope.row)">详情</el-button>
                  <el-button
                    size="mini"
                    type="primary"
                    @click="handleload(scope.row.filePath)">下载</el-button>
                  <el-button
                    size="mini"
                    type="danger"
                    @click="deteletemplate(scope.row)">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
            <el-pagination style="margin: 20px 40px 0 0;text-align: -webkit-right;" background :total="totalRecord"
                  :page-sizes="[10, 20, 30, 40]" :page-size="pagesize" :current-page.sync="pageno" @size-change="handleSizeChange" @current-change="handleCurrentChange" layout="total, sizes, prev, pager, next, jumper">
            </el-pagination> 
          </div>
       </div>
       <el-dialog width="50%" :title="agetitle" :visible.sync="ageVisible" append-to-body>
         <div v-if="agetitle == '新增'" class="systemrow" style="margin-top: 20px;background: #fff;">
           <div class="systemrow" style="line-height: 60px;padding-left: 20px;">
             <el-row>
              <el-col :span="12">
                <span style="padding-right: 10px;">报告类型:</span>
                <el-select v-model="addtemplatevalue" clearable placeholder="请选择">
                    <el-option
                      v-for="item in templateoptions"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
              </el-col>
              <el-col :span="12">
                <span style="padding-right: 10px;">模板级别:</span>
                <el-select v-model="addsystemevalue" clearable placeholder="请选择">
                    <el-option
                      v-for="item in addsystemeoptions"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                    </el-option>
                  </el-select>
              </el-col>
            </el-row>
           </div>
          <div class="systemrow" style="min-height: 80px;padding-left: 20px;">
            <span style="padding-right: 10px;margin-bottom: 10px;display: inline-block;">模板文件:</span>
            <!-- http://50.32.9.139:8085/ -->
            <!-- http://10.33.72.236:8085/ -->
            <!-- http://192.168.8.11:8085/reportModelManager/uploadFile -->
            <el-upload class="upload-demo" :multiple="true" :limit="1" :on-success="handlesuccess" :on-error="handleerror" ref="upload" action="http://50.32.9.139:8085/reportModelManager/uploadFile" :on-preview="handlePreview" :on-remove="handleRemove" :file-list="fileList" :auto-upload="true">
              <!-- <el-button slot="trigger" size="small" type="primary">选取文件</el-button> -->
              <el-button size="small" type="primary">上传</el-button>
              <!-- <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button> -->
            </el-upload>
          </div>
          <div class="systemrow" style="min-height: 100px;padding-top: 10px;padding-left: 20px;">
            <span style="padding-right: 10px; padding-left: 30px; margin-bottom: 10px; display: inline-block; line-height: 40px;">备注:</span>
            <el-input type="textarea" style="width: 80%;" maxlength="100" v-model="desction"></el-input>
          </div>
          <div class="systemrow" style="text-align: -webkit-center;min-height: 60px;padding-top: 10px;padding-left: 20px;">
            <el-button round @click="canelclick">取消</el-button>
            <el-button type="primary" round @click="addcureclick">保存</el-button>
         </div>
         </div>
         <div v-if="agetitle == '详情'" class="systemrow" style="margin-top: 20px;background: #fff;">
          <div class="systemrow" style="line-height: 60px;padding-left: 20px;">
             <el-row>
              <el-col :span="12">
                <span style="padding-right: 10px;">报告类型:</span>
                <span style="padding-right: 10px;">{{this.coent.modelType}}</span>
              </el-col>
              <el-col :span="12">
                <span style="padding-right: 10px;">模板级别:</span>
                <span style="padding-right: 10px;">{{this.coent.modelLevel}}</span>
              </el-col>
            </el-row>
           </div>
           <div class="systemrow" style="line-height: 60px;padding-left: 20px;">
             <el-row>
              <el-col :span="24">
                <span style="padding-right: 10px;">模板文件:</span>
                <span style="padding-right: 10px;">
                  <el-button type="primary" @click="handleload('')">下载</el-button>
                </span>
              </el-col>
            </el-row>
           </div>
           <div class="systemrow" style="line-height: 60px;padding-left: 20px;">
             <el-row>
              <el-col :span="24">
                <span style="padding-right: 10px;">备注:</span>
                <span style="padding-right: 10px;">{{this.coent.remark}}</span>
              </el-col>
            </el-row>
           </div>
          <div class="systemrow" style="text-align: -webkit-center;min-height: 60px;padding-top: 10px;padding-left: 20px;">
            <el-button round @click="canelclick">取消</el-button>
          </div>
         </div>
       </el-dialog>
    </div>
</template>
<script>
export default {
  data: function() {
    return {
      templatevalue: "",
      datetimeranges: [],
      addsystemevalue: "",
      addtemplatevalue: "",
      loadingse: false,
      templateoptions: [
        {
          value: "1",
          label: "民警画像报告模板"
        },
        {
          value: "2",
          label: "综合评估报告模板"
        }
      ],
      addsystemeoptions: [
        {
          value: "1",
          label: "市局"
        },
        {
          value: "2",
          label: "分局/部门"
        },
        {
          value: "3",
          label: "科所队"
        }
      ],
      tableDatase: [],
      selectDatase: [],
      totalRecord: 0,
      pagesize: 10,
      pageno: 1,
      agetitle: "",
      ageVisible: false,
      fileList: [],
      desction: "",
      coent: {},
      fileListse: ""
    };
  },
  watch: {},
  mounted() {
    this.queryclick();
  },
  destroyed() {
    document.querySelector("body").style.height = "100%";
    document.querySelector("html").style.height = "100%";
  },
  methods: {
    //查询
    queryclick() {
      this.loadingse = true;
      this.$axios
        .post("/reportModelManager/queryList", {
          type: this.templatevalue,
          dateRange: this.datetimeranges,
          pageNo: this.pageno,
          size: this.pagesize
        })
        .then(res => {
          if (res.code == 0) {
            this.tableDatase = res.data.content;
            this.totalRecord = res.data.totalElements;
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
      this.datetimeranges = [];
    },
    //新增
    addtemplate() {
      this.agetitle = "新增";
      this.ageVisible = true;
      this.addtemplatevalue = "";
      this.addsystemevalue = "";
      this.desction = "";
      this.fileListse = "";
      this.fileList = [];
    },
    //删除
    deteletemplate(parem) {
      let detedatase = [];
      if (parem) {
        detedatase.push(parem.id);
      } else {
        this.selectDatase.forEach(element => {
          detedatase.push(element.id);
        });
      }
      this.$axios
        .post("/reportModelManager/deleteModel", detedatase)
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
    //详情
    handleEdit(row) {
      this.agetitle = "详情";
      this.ageVisible = true;
      this.coent = row;
    },
    //下载
    handleload(row) {
      if (row) {
        window.location.href = row;
      } else {
        window.location.href = this.coent.filePath;
      }
    },
    //上传
    // submitUpload() {
    //   this.$refs.upload.submit();
    // },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
    },
    //上传成功的事件
    handlesuccess(response, file, fileList) {
      if (response.code == 0) {
        this.$message({
          message: "上传成功!",
          type: "success"
        });
        this.fileListse = response.data;
      } else {
        this.$message.error("上传失败!");
      }
    },
    handleerror(err, file, fileList) {
      this.$message.error("上传失败!");
    },
    //保存
    addcureclick() {
      if (!this.addtemplatevalue) {
        this.$message.error("报告类型未选择！");
        return;
      }
      if (!this.addsystemevalue) {
        this.$message.error("模板级别未选择！");
        return;
      }
      if (!this.fileListse.length) {
        this.$message.error("模板级别未选择！");
        return;
      }
      this.$axios
        .post("/reportModelManager/uploadModel", {
          type: this.addtemplatevalue,
          level: this.addsystemevalue,
          remark: this.desction,
          file: this.fileListse
        })
        .then(res => {
          if (res.code == 0) {
            this.ageVisible = false;
            this.$message({
              message: "新增成功!",
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
    }
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
  background: #5ab1ef;
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
</style>
<style>
/deep/ .el-table th {
  background: #ccc;
  color: #000;
}
</style>