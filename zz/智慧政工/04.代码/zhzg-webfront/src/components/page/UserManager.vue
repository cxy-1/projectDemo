<template>
    <div class="depart">
        <div class="sqxx-option">
            <el-row :gutter="20">
                <el-col :span="24">
                    <el-row>
                        <el-col>
                            <el-card shadow="" class="mgb20">
                                <div class="crumbs">
                                    <el-breadcrumb separator="/">
                                        <el-breadcrumb-item>首页</el-breadcrumb-item>
                                        <el-breadcrumb-item>用户管理</el-breadcrumb-item>
                                    </el-breadcrumb>
                                </div>
                            </el-card>
                        </el-col>
                    </el-row>
                </el-col>
            </el-row>
            <el-row style="margin:0 25px;">
                <el-col  :span="24">
                    <el-container  class="special">
                        <el-aside width="300px" style="margin-right: 35px;">
                            <el-card  style="height: 900px;overflow: scroll">
                                <div slot="header" style="overflow: hidden;">
                                    <span>组织结构树</span>
                                </div>
                                <el-tree 
                                :highlight-current="true"  
                                :props="props" 
                                :load="loadNode"                              
                                draggable
                                @node-drag-end="handleDragEnd"
                                @node-click="handleNodeClick"
                                :allow-drop="allowDrop"
                                lazy
                                node-key="id">
                                    <span class="custom-tree-node" slot-scope="{ node, data }">
                                        <span>
                                            <i v-if="node.data.isUnit==1&&!node.expanded"><img src="static/img/wenjianclosed.png" alt=""></i>
                                            <i v-if="node.data.isUnit==1&&node.expanded"><img src="static/img/wenjianopened.png" alt=""></i>
                                            <i v-if="node.data.isUnit==0"  style="color: #178efe" class="el-icon-user-solid"></i>
                                            <span v-if="node.data.isUnit==1" :title=node.data.unitName >{{ node.data.unitName }}</span>
                                            <span v-if="node.data.isUnit==0" :title=node.data.userName >{{ node.data.userName }}</span>
                                        </span>
                                    </span>
                                </el-tree>
                            </el-card>

                        </el-aside>
                        <el-main v-if="!isData">
                            <el-card style="height: 900px;">
                                <div slot="header" class="clearfix">
                                    <span>用户管理功能介绍</span>
                                </div>
                                <p>
                                    欢迎使用用户管理
                                </p>
                            </el-card>
                        </el-main>
                        <el-main v-if="isData">
                            <el-card style="height: 900px;">
                                <div style="margin-bottom: 25px;">
                                    <div style="margin-bottom: 10px;">
<!--                                        <el-button type="primary" round>密码重置</el-button>-->
                                        <el-button type="primary" @click="bindrole" round>关联角色</el-button>
                                    </div>
                                    <div style="border: 1px solid rgb(235, 238, 245);">
                                        <el-row style="margin: 20px 0;" :gutter="20">
                                            <el-form  ref="form" :model="form" label-width="80px">
                                                <el-col :span="8">
                                                    <el-form-item label="姓名:">
                                                        <el-input readonly v-model="form.userName"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8">
                                                    <el-form-item label="性别:">
                                                        <el-input readonly v-model="form.sex"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8">
                                                    <el-form-item label="警号:">
                                                        <el-input readonly v-model="form.policeNo"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8">
                                                    <el-form-item label="身份证号:">
                                                        <el-input readonly v-model="form.idNumber"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="8">
                                                    <el-form-item label="电话号码:">
                                                        <el-input readonly v-model="form.phone"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                            </el-form>
                                        </el-row>
                                    </div>
                                </div>

                                <div class="special">
                                    <el-tabs type="card">
                                        <el-tab-pane label="已授权角色">
                                            <div style="height: 325px;overflow-y:scroll;">
                                                <el-table
                                                ref="singleTable"
                                                :data="tableData"
                                                highlight-current-row
                                                @current-change="handleCurrentChange"
                                                style="width: 100%">
                                                <el-table-column
                                                    type="index"
                                                    label="序号"
                                                    width="50">
                                                </el-table-column>
                                                <el-table-column
                                                    prop="name"
                                                    label="名称"
                                                    >
                                                </el-table-column>
                                                <el-table-column
                                                    prop="type"
                                                    label="类型"
                                                    >
                                                    <template slot-scope="scope">
                                                        <span v-if="scope.row.type==0">用户</span>
                                                        <span v-if="scope.row.type==1">管理</span>
                                                    </template>
                                                </el-table-column>
                                                <el-table-column
                                                    prop="remark"
                                                    label="描述">
                                                </el-table-column>
                                                <el-table-column label="操作" align="center" width="200">
                                                    <template slot-scope="scope">
                                                        <el-button  type="text" @click="handleDel(scope.$index, scope.row)">删除</el-button>
                                                    </template>
                                                </el-table-column>
                                                </el-table>
                                            </div>
                                        </el-tab-pane>
                                        <el-tab-pane label="包含资源">
                                            <el-tree
                                                :data="treeData"
                                                node-key="id"
                                                ref="tree"
                                                highlight-current
                                                :props="defaultProps">
                                            </el-tree>
                                        </el-tab-pane>
                                    </el-tabs>
                                </div>
                            </el-card>
                        </el-main>
                    </el-container>
                </el-col>
            </el-row>
        </div>
        <!-- <div style="wdith: 1000px;
        height: 500px; position:absolute;z-index: 1000; top: 0; right: 0;">
            {{dragTreeData}}
        </div> -->
        <el-dialog
            title="分配角色"
            :visible.sync="bindroleVisible"
            width="80%"
        >
            <div style="height: 400px;overflow-y:scroll;">
                <el-table
                    ref="bintable"
                    :data="binddata"
                    highlight-current-row
                    @selection-change="handleSelectionChange"
                    style="width: 100%">
                    <el-table-column
                        type="selection"
                        :selectable="checkSelectable"
                        width="55">
                    </el-table-column>
                    <el-table-column
                        prop="name"
                        label="名称"
                    >
                    </el-table-column>
                    <el-table-column
                        prop="type"
                        label="类型"
                    >
                        <template slot-scope="scope">
                            <span v-if="scope.row.type==0">用户</span>
                            <span v-if="scope.row.type==1">管理</span>
                        </template>
                    </el-table-column>
                    <el-table-column
                        prop="remark"
                        label="描述">
                    </el-table-column>
                </el-table>
            </div>

            <span slot="footer" class="dialog-footer">
                <el-button @click="bindroleVisible = false">取 消</el-button>
                <el-button type="primary" @click="bindroleSubmit">确 定</el-button>
            </span>
        </el-dialog>
        <el-footer style="text-align:center;height:20px;">&copy;2019-2022 南京市公安局&emsp;版权所有</el-footer>
    </div>
</template>

<script>
import { communication } from "../../data/getAxios";
export default {
  name: "passportList",
  data() {
    return {
      treeData: [],
      tableData: [],
      form: {
        userName: "",
        sex: "",
        policeNo: "",
        idNumber: "",
        phone: ""
      },
      // data: [{
      //     label: '政治部',
      //     children: [{
      //         label: '二级 1-1',
      //         children: [{
      //             label: '三级 1-1-1'
      //         }]
      //     }]
      // }, {
      //     label: '一级 2',
      //     children: [{
      //         label: '二级 2-1',
      //         children: [{
      //             label: '三级 2-1-1'
      //         }]
      //     }, {
      //         label: '二级 2-2',
      //         children: [{
      //             label: '三级 2-2-1'
      //         }]
      //     }]
      // }, {
      //     label: '一级 3',
      //     children: [{
      //         label: '二级 3-1',
      //         children: [{
      //             label: '三级 3-1-1'
      //         }]
      //     }, {
      //         label: '二级 3-2',
      //         children: [{
      //             label: '三级 3-2-1'
      //         }]
      //     }]
      // }],
      props: {
        label: "name",
        children: "zones",
        isLeaf: "leaf"
      },
      defaultProps: {
        children: "subs",
        label: "title"
      },
      cur_page: 1,
      pagesize: 10,
      bindroleVisible: false,
      unitId: null,
      dataId: null,
      personId: null,
      binddata: [],
      multipleSelection: [],
      bindarr: [],
      isData: false,
      dragTreeData: {
        deptId: "",
        baseUserListJson: []
      }
    };
  },
  created() {},
  mounted() {},
  methods: {
    handleCurrentChange() {},
    loadNode(node, resolve) {
      // console.log(node)
      if (node.level === 0) {
        const obj = {
          url:
            this.global.serverPath8 +
            "/user-mgmt/orgmanage/headOrgPeoTree?personId=" +
            localStorage.getItem("loginpersonId"),
          // url:"http://192.168.8.141:8088/orgmanage/headOrgPeoTree?personId="+localStorage.getItem("loginpersonId"),
          isGet: true
        };
        communication(obj).then(res => {
          resolve(res.data);
        });
      } else {
        const _obj = {
          url:
            this.global.serverPath8 +
            "/user-mgmt/orgmanage/nodeOrgPeoTree?unitId=" +
            node.data.unitId,
          // url:"http://192.168.8.141:8088/orgmanage/nodeOrgPeoTree?unitId="+node.data.unitId,
          isGet: true
        };
        communication(_obj).then(res => {
          const data = res.data;
          resolve(data);
        });
      }
    },
    handleDragEnd(draggingNode, dropNode, dropType, ev) {
      console.log(dropNode.parent.data.unitId);
      this.dragTreeData.baseUserListJson = [];
      var nodeData = [];
      var baseUserListJsonOrigin = [];
      dropNode.parent.childNodes.forEach(response => {
        console.log(response.data.leaf);
        if (response.data.leaf == true) {
          baseUserListJsonOrigin.push(response.data);
        }
      });
      this.dragTreeData.baseUserListJson = JSON.stringify(
        baseUserListJsonOrigin
      );
      this.dragTreeData.deptId = dropNode.parent.data.unitId;
      nodeData = this.dragTreeData;
      console.log(nodeData);
      const _obj = {
        url: this.global.serverPath8 + "/user-mgmt/orgmanage/rakeNewTree",
        // url:"http://192.168.8.141:8088/orgmanage/nodeOrgPeoTree?unitId="+node.data.unitId,
        postData: nodeData,
        isGet: false
      };

      communication(_obj).then(
        res => {
          console.log(res);
        },
        function(res) {
          self.$message.error("请求失败");
        }
      );
    },
    allowDrop(draggingNode, dropNode, type) {
      // console.log(draggingNode);
      // 如果同级别目录下
      if (
        draggingNode.level === dropNode.level &&
        draggingNode.isLeaf == true
      ) {
        if (draggingNode.parent.id === dropNode.parent.id) {
          return type === "prev";
        }
      } else {
        // 不同级不进行处理
        return false;
      }
    },
    handleNodeClick(a, b, c) {
      console.log(a, b, c);
      this.unitId = a.unitId;
      this.dataId = a.dataId;
      this.personId = a.personId;
      this.bindarr = [];
      if (a.isUnit == 0) {
        this.isData = true;
        const obj = {
          url:
            this.global.serverPath8 +
            "/user-mgmt/user/info?personId=" +
            b.data.personId,
          isGet: true
        };
        communication(obj).then(res => {
          this.form = res.data;
        });
        const _obj = {
          // url:"http://192.168.8.147:8088/role/list?personId="+b.data.personId,
          url:
            this.global.serverPath8 +
            "/user-mgmt/role/list?personId=" +
            b.data.personId,
          isGet: true
        };
        communication(_obj).then(res => {
          this.tableData = res.data;
          this.tableData.forEach((item, index) => {
            // return row.roleId != item.roleId
            this.bindarr.push(item.roleId);
          });
        });
        const __obj = {
          url:
            this.global.serverPath8 +
            "/user-mgmt/menu/list?personId=" +
            this.personId,
          isGet: true
        };
        communication(__obj).then(res => {
          this.treeData = res.data;
        });
      }
    },
    handleDel(index, row) {
      // this.roleName=row.roleName;//传入角色名称
      this.$confirm("此操作将永久删除该数据, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      })
        .then(() => {
          this.deleteRow(row.ruId);
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除"
          });
        });
    },
    deleteRow(id) {
      const obj = {
        url: this.global.serverPath8 + "/user-mgmt/user/delRole?ruId=" + id,
        // url:"http://192.168.8.147:8088/user/delRole?ruId="+id,
        isGet: true
      };
      communication(obj).then(res => {
        if (res.code == 0) {
          this.$message.success("删除成功");
          const _obj = {
            // url:"http://192.168.8.147:8088/role/list?personId="+this.personId,
            url:
              this.global.serverPath8 +
              "/user-mgmt/role/list?personId=" +
              this.personId,
            isGet: true
          };
          communication(_obj).then(res => {
            this.tableData = res.data;
            this.bindarr = [];
            this.tableData.forEach((item, index) => {
              // return row.roleId != item.roleId
              this.bindarr.push(item.roleId);
            });
          });
        } else if(res.code != 1){
          this.$message.error("系统错误");
        }
      });
    },
    bindrole() {
      this.bindroleVisible = true;
      this.binddata = [];
      const obj = {
        url:
          this.global.serverPath8 +
          "/user-mgmt/orgmanage/roleOrgs?unitId=" +
          this.unitId,
        isGet: true
      };
      communication(obj).then(res => {
        this.binddata = res.data;

        this.$nextTick(function() {
          this.bindarr.forEach((item, index) => {
            this.binddata.forEach((value, index2) => {
              if (item == value.roleId) {
                this.$refs.bintable.toggleRowSelection(value);
              }
            });
          });
        });
        //

        // this.tableData.forEach(row => {
        //
        // });
      });
    },
    bindroleSubmit() {
      this.bindroleVisible = false;
      this.bindarr.forEach((item, index) => {
        this.multipleSelection.forEach((value, index2) => {
          if (item == value.roleId) {
            this.multipleSelection.splice(index2, 1);
          }
        });
      });
      const obj = {
        // url:"http://192.168.8.147:8088/user/buildingRole",
        url: this.global.serverPath8 + "/user-mgmt/user/buildingRole",
        postData: {
          personId: this.personId,
          roleList: this.multipleSelection,
          dataId: this.dataId
        }
      };
      communication(obj).then(res => {
        const _obj = {
          url:
            this.global.serverPath8 +
            "/user-mgmt/role/list?personId=" +
            this.personId,
          isGet: true
        };
        communication(_obj).then(res => {
          this.tableData = res.data;
          this.bindarr = [];
          this.tableData.forEach((item, index) => {
            // return row.roleId != item.roleId
            this.bindarr.push(item.roleId);
          });
        });
      });
    },
    handleSelectionChange(val) {
      this.multipleSelection = val;
    },
    checkSelectable(row) {
      console.log(this.bindarr);
      return this.bindarr.indexOf(row.roleId) == -1;
    }
  }
};
</script>

<style scoped>
.special .el-main {
  padding: 0;
}
.el-tabs__header,
.is-top {
  margin-bottom: 0px !important;
}
.el-dialog {
  position: absolute;
  top: 50%;
  left: 50%;
  margin: 0 !important;
  transform: translate(-50%, -50%);
  max-height: calc(100% - 30px);
  max-width: calc(100% - 30px);
  display: flex;
  flex-direction: column;
}

.el-dialog__body {
  overflow: auto;
}
</style>
<style>
</style>
