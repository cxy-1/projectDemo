<template>
	<div class="protArmsList">
		<div class="protArmsListCont is-always-shadow">
			<div class="protArmsListHead">
				<el-breadcrumb class="crumbs" separator="/">
					<!--<el-breadcrumb-item :to="{ path: '/' }">首页</el-breadcrumb-item>-->
					<el-breadcrumb-item>证件申请</el-breadcrumb-item>
					<el-breadcrumb-item>人员持枪库</el-breadcrumb-item>
				</el-breadcrumb>
			</div>
		</div>
		<div class="protArmsListCon is-always-shadow">
			<el-row>
				<el-col :span="24">
					<el-form :inline="true" :label-position="'right'" label-width="90px" ref='gunForm' :model="gunForm" class="demo-form-inline fuzzinfoCon">
						<el-row :gutter="20">
							<el-col :span="8">
								<el-form-item prop="" label="姓名：">
									<el-input v-model="gunForm.userName"></el-input>
								</el-form-item>
							</el-col>
							<el-col :span="8">
								<el-form-item prop="" label="警号：">
									<el-input v-model="gunForm.alarm"></el-input>
								</el-form-item>
							</el-col>
							<el-col :span="8">
								<el-form-item prop="" label="工作单位：">
									<el-input v-model="gunForm.unit"></el-input>
								</el-form-item>
							</el-col>
						</el-row>
						<div class="hide" v-show="toggleShow">
							<el-row :gutter="20">
								<el-col :span="8">
									<el-form-item prop="" label="是否在职：">
										<el-select v-model="gunForm.onJob" readonly="" style="width:93%">
											<el-option key="是" label="是" value="是"></el-option>
											<el-option key="否" label="否" value="否"></el-option>
										</el-select>
									</el-form-item>
								</el-col>
								<el-col :span="8">
									<el-form-item prop="" label="职务名称：">
										<el-input v-model="gunForm.jobName"></el-input>
									</el-form-item>
								</el-col>
								<el-col :span="8">
									<el-form-item prop="" label="工作部门：">
										<el-input v-model="gunForm.departMent"></el-input>
									</el-form-item>
								</el-col>
							</el-row>
							<el-row :gutter="20">
								<el-col :span="8">
									<el-form-item prop="" label="职务级别：">
										<el-input v-model="gunForm.jobLevel"></el-input>
									</el-form-item>
								</el-col>
								<el-col :span="8">
									<el-form-item prop="" label="政治面貌：">
										<el-input v-model="gunForm.politicCount"></el-input>
									</el-form-item>
								</el-col>
								<!--<el-col :span="8">
									<el-form-item prop="" label="持枪状态：">
										<el-input v-model="gunForm.gunshotState"></el-input>
									</el-form-item>
								</el-col>-->
							</el-row>
						</div>
						
						<el-row :gutter="20">
							<el-col :span="24">
								<el-form-item>
									<el-button type="primary" @click="getSearchData()">查询</el-button>
									<el-button @click="resetForm('searchList')">重置</el-button>
									<el-button type="text" @click="upDown()">{{toggleShow?'收起':'展开'}}
										<i :class="toggleShow?class1:class2"></i>
									</el-button>
								</el-form-item>
							</el-col>
						</el-row>
						<el-row :gutter="20">
							<el-col :span="24">
								<el-button type="primary" icon="el-icon-upload2" @click="export2Excel">导出</el-button>
								<el-button>删除</el-button>
							</el-col>
						</el-row>
					</el-form>
				</el-col>
			</el-row>
			<div class="protArmsTab">
				<template>
					<el-table ref="multipleTable" :data="tableData" tooltip-effect="dark" style="width: 100%">
						<el-table-column type="selection"></el-table-column>
						<el-table-column prop="userName" label="姓名"></el-table-column>
						<el-table-column prop="deptName" label="部门" show-overflow-tooltip></el-table-column>
						<el-table-column v-if="false" prop="personId" label="人员id" show-overflow-tooltip></el-table-column>
						<el-table-column prop="workUnits" label="工作单位" show-overflow-tooltip></el-table-column>
						<el-table-column prop="shortedUnitName" label="单位简称" show-overflow-tooltip></el-table-column>
						<el-table-column prop="job" label="职务" show-overflow-tooltip></el-table-column>
						<el-table-column prop="gender" label="性别" show-overflow-tooltip></el-table-column>
						<el-table-column prop="gunStatus" label="持枪状态" show-overflow-tooltip></el-table-column>
						<el-table-column label="操作">
							<template slot-scope="scope">
								<!-- <el-button type="text" @click="handleEdit(scope.$index, scope.row)">审批</el-button> -->
								<el-button type="text">详情</el-button>
							</template>
							<!-- <template slot-scope="scope">
								<el-button type="text">
									<router-link :to="{path:'/portArms3',query:{alarmNo:scope.row.applyFormId}}">详情</router-link>
								</el-button>
							</template> -->
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
			toggleShow: true,
			gunForm: {
				name: '',//姓名
				alarm: '',//警号
				unit: '',//工作单位
				onJob: '',//是否在职
				jobName: '',//职务名称
				departMent: '',//工作部门
				jobLevel: '',//职务级别
				politicCount: '',//政治面貌
				gunshotState:''	//	持枪状态
			},
			pageShow1: true,
			pageShow2: false,
			cur_page: 1,
			cur_page2: 1,
			pagesize: 10,
			pagesize2: 10,
			data_length: 0,
			data_length2: 0,
			class1: 'el-icon-arrow-up el-icon--right',
			class2: 'el-icon-arrow-down el-icon--right',
			tableData: []
		}
	},
	created() {
		this.getTableData();
	},
	methods: {
		// 查询按钮
		getSearchData() {
			var that = this;
			that.$http.post(that.global.serverPath + '/zhzg/armLicence/queryUserArmLicencePagesInfoByDeptId',
				JSON.stringify({
					"currentNum": that.cur_page2,
					"pageSize": that.pagesize2,
					"deptId": localStorage.getItem('loginDeptId'),
					"userName": that.gunForm.name,
					"userPoliceId": that.gunForm.alarm,
					"workUnits": that.gunForm.unit,
					"jobName": that.gunForm.jobName,
					"deptName": that.gunForm.departMent,
					"jobLevel": that.gunForm.jobLevel,
					"politicalLandscape": that.gunForm.politicCount,
					"status": that.gunForm.onJob
				})
			).then(function(res) {
				if (res.body != '') {
					if (res.body.rows != '') {
						console.log(res.body.rows)
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
			this.getSearchData();
		},
		// 导出excel
		export2Excel() {//兼容ie10
			var that = this;
			require.ensure([], () => {
				const { export_json_to_excel } = require('../../vendor/Export2Excel');//引入文件
				const tHeader = ['姓名', '部门', '工作单位', '单位简称', '职务', '性别', '持枪状态']; //对应表格输出的title
				const filterVal = ['name', 'part', 'unit', 'unitShort', 'job', 'sex', 'gunStatus'];// 对应表格输出的数据
				const list = that.tableData;
				const data = that.formatJson(filterVal, list);
				export_json_to_excel(tHeader, data, '申请列表excel'); //对应下载文件的名字
			})
		},
		formatJson(filterVal, jsonData) {
			return jsonData.map(v => filterVal.map(j => v[j]))
		},
		// 收起展开
		upDown() {
			this.toggleShow = !this.toggleShow;
		},
		getTableData() {
			var that = this;
			that.$http.get(that.global.serverPath + '/zhzg/armLicence/initQueryUserArmLicenceInfoByDeptId', {//传递参数
				params: {
					"deptId": localStorage.getItem('loginDeptId'),
					"currentNum": that.cur_page,
					"pageSize": that.pagesize
				}
			}).then(function(res) {
				console.log(res.body.rows)
				if (res.body.rows.data.length > 0) {
					that.tableData = res.body.rows.data;
					that.data_length = res.body.rows.totalSize;
					that.pageShow1 = true;
					that.pageShow2 = false;
				} else {
					that.$message.warning('暂无数据！')
				}
			}, function(res) {
				// self.$message.error('请求失败！')
			});
		},
	}
}
</script>
<style scoped>
.fuzzinfoCon .el-row {
	margin-bottom: 10px!important;
}
</style>
<style>
.pagination2 {
	height: 32px;
	margin-bottom: 10px;
	margin-top: 10px;
	position: relative;
}

.pagination2 .el-pagination {
	position: absolute;
	right: 30px;
}

.pagination2 .el-input--small .el-input__inner {
	width: 45px!important;
}

.pagination .el-input--small .el-input__inner {
	width: 45px!important;
}

.protArmsTab .el-table th>.cell {
	color: rgba(0, 0, 0, 0.85);
	font-size: 14px;
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
