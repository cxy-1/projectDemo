<template>
	<div class="protArmsList">
		<div class="protArmsListCont is-always-shadow">
			<div class="protArmsListHead">
				<el-breadcrumb class="crumbs" separator="/">
					<!--<el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>-->
					<el-breadcrumb-item>证件申请</el-breadcrumb-item>
					<el-breadcrumb-item>持枪证审批</el-breadcrumb-item>
				</el-breadcrumb>
			</div>
		</div>
		<div class="protArmsListCon is-always-shadow">
			<el-row>
				<el-col :span="24">
					<el-form :inline="true" :label-position="'right'" label-width="90px" ref='formInline' :model="formInline" class="demo-form-inline fuzzinfoCon">
						<el-row :gutter="20">
							<el-col :span="8">
								<el-form-item class="formSelect" prop="" label="申请人：">
									<el-input v-model="formInline.name"></el-input>
								</el-form-item>
							</el-col>
							<el-col :span="8">
								<el-form-item class="formSelect" prop="" label="警号：">
									<el-input v-model="formInline.alarm"></el-input>
								</el-form-item>
							</el-col>
							<el-col :span="8">
								<el-form-item class="formSelect" prop="" label="工作单位：">
									<el-input v-model="formInline.unit"></el-input>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="20">
							<el-col :span="8">
								<el-form-item class="formSelect" prop="" label="申请单号：">
									<el-input v-model="formInline.oddNum"></el-input>
								</el-form-item>
							</el-col>
							<el-col :span="8">
								<el-form-item></el-form-item>
							</el-col>
							<el-col :span="24">
								<el-form-item>
									<el-button type="primary" @click="getSearchData()">查询</el-button>
									<el-button @click="resetForm('searchList')">重置</el-button>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="20">
							<el-col :span="24">
								<el-button type="primary" icon="el-icon-upload2" @click="export2Excel">导出</el-button>
								<el-button>批量删除</el-button>
							</el-col>
						</el-row>
					</el-form>
				</el-col>
			</el-row>
			<div class="protArmsTab">
				<template>
					<el-table ref="multipleTable" :data="tableData" tooltip-effect="dark" style="width: 100%">
						<el-table-column type="selection"></el-table-column>
						<el-table-column prop="applicationId" label="申请单号">
							 <!-- <template slot-scope="scope">
								<router-link :to="{path:'/portArms3',query:{alarmNo:scope.row.applicationId}}">{{scope.row.applicationId}}</router-link>
							</template>  -->
						</el-table-column>
						<el-table-column prop="userName" label="姓名" show-overflow-tooltip></el-table-column>
						<el-table-column v-if="false" prop="personId" label="姓名" show-overflow-tooltip></el-table-column>
						<el-table-column prop="workUnits" label="工作单位" show-overflow-tooltip></el-table-column>
						<el-table-column prop="deptName" label="部门" show-overflow-tooltip></el-table-column>
						<el-table-column prop="theory" label="理论考核" show-overflow-tooltip></el-table-column>
						<el-table-column prop="firingPractice" label="实弹射击" show-overflow-tooltip></el-table-column>
						<el-table-column prop="serviceOperation" label="勤务操作" show-overflow-tooltip></el-table-column>
						<el-table-column label="操作">
							<template slot-scope="scope">
								<el-button type="text">
									<router-link :to="{path:'/portArms3',query:{alarmNo:scope.row.personId}}">审批</router-link>
								</el-button>
								<el-button type="text">
									<router-link :to="{path:'/portArms3',query:{alarmNo:scope.row.personId}}">详情</router-link>
								</el-button>
							</template>
						</el-table-column>
					</el-table>
					<!--表格分页  -->
					<div class="pagination" v-show="pageShow1">
						<el-pagination @current-change="handleCurrentChange" :current-page="cur_page" :page-size="pagesize" :page-sizes="[10]" layout="total, sizes, prev, pager, next, jumper" :total="data_length">
						</el-pagination>
					</div>
					<!--查询分页  -->
					<div class="pagination2" v-show="pageShow2" style="margin:10px 0 0 0">
						<el-pagination @current-change="handleCurrentChange2" :current-page="cur_page2" :page-size="pagesize2" :page-sizes="[10]" layout="total, sizes, prev, pager, next, jumper" :total="data_length2">
						</el-pagination>
					</div>
				</template>
			</div>
		</div>
	</div>
</template>

<script>
export default {
	data() {
		return {
			formInline: {
				name: '',
				alarm: '',
				unit: '',
				oddNum: '',
				address: ''
			},
			pageShow1:true,
			pageShow2:false,
			cur_page:1,
			cur_page2:1,
			pagesize:10,
			pagesize2:10,
			data_length:0,
			data_length2:0,
			tableData: []
		}
	},
	created() {
		this.getTableData();
	},
	methods: {
		// 表格分页导航
        handleCurrentChange(val) {
            // this.loading = true;
            this.cur_page = val;
            this.getTableData();
        },
        // 查询分页导航
        handleCurrentChange2(val) {
            // this.loading = true;
            this.cur_page2 = val;
            // this.getSearchData();
        },
		// 导出excel
		export2Excel() {//兼容ie10
			var that = this;
			require.ensure([], () => {
				const { export_json_to_excel } = require('../../vendor/Export2Excel');//引入文件
				const tHeader = ['申请单号', '姓名', '工作单位', '部门', '理论考核', '实弹射击', '勤务操作']; //对应表格输出的title
				const filterVal = ['applyFormId', 'name', 'workUnits', 'userDepartment', 'examTest', 'liveFire', 'dutyOperat'];// 对应表格输出的数据
				const list = that.tableData;
				const data = that.formatJson(filterVal, list);
				export_json_to_excel(tHeader, data, '申请列表excel'); //对应下载文件的名字
			})
		},
		getTableData() {
			var that = this;
			that.$http.get(that.global.serverPath + '/zhzg/armLicence/initQueryArmLicenceApproveByCon', {//传递参数
				params: {
					"personId": localStorage.getItem('loginpersonId'),
					"currentNum": that.cur_page,
					"pageSize": that.pagesize
				}
			}).then(function(res) {
				if (res.body.rows.data.length > 0) {
					that.tableData = res.body.rows.data;
					that.data_length = res.body.rows.totalSize;
					console.log(res.body.rows.data)
					that.pageShow1 = true;
					that.pageShow2 = false;
				} else {
					that.$message.warning('暂无数据！')
				}
			}, function(res) {
				// self.$message.error('请求失败！')
			});
		},
		// 查询按钮
		getSearchData() {
			var that = this;
			that.$http.post(that.global.serverPath + '/zhzg/armLicence/queryUserArmLicencePagesInfoByDeptId',
				JSON.stringify({
                    "currentNum": that.cur_page2,
					"pageSize": that.pagesize2,
					"personId": localStorage.getItem('loginpersonId'),
					"userName": that.formInline.name,
					"userPoliceId": that.formInline.alarm,
					"workUnits": that.formInline.unit,
					"applicaionId": that.formInline.oddNum
				})
			).then(function(res) {
				if (res.body != '') {
					if (res.body.rows != '') {
						that.tableData = res.body.rows.data;
						that.data_length2 = res.body.rows.totalSize;
						that.pageShow1 = false;
						that.pageShow2 = true;
					}
				}
			}, function(res) {
				console.log('查询表格数据失败！')
			});
		},
		formatJson(filterVal, jsonData) {
			return jsonData.map(v => filterVal.map(j => v[j]))
		},
	}
}
</script>
<style scoped>

</style>
<style>
.protArmsTab .el-table th>.cell {
	color: rgba(0, 0, 0, 0.85);
	font-size: 14px;
}
.pagination2 {
    height: 32px;
    margin-bottom: 10px;
    margin-top: 10px;
}

.pagination2 .el-pagination {
    position: absolute;
    right: 30px;
    bottom: 10px;
}
.pagination2 .el-input--small .el-input__inner {
    width: 45px!important;
}

.pagination .el-input--small .el-input__inner {
    width: 45px!important;
}
.protArmsListHead .approvehead {
	font-family: PingFangSC-Medium;
	font-size: 16px;
	color: #272727;
	font-weight: bold;
}

.protArmsListCont {
	background: white;
	padding: 20px;
}

.protArmsListCon {
	background: white;
	margin: 20px;
	padding: 20px;
}

.protArmsTab {
	margin-top: 20px;
}
</style>
