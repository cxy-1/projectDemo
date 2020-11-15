<!--<template>
	<div class="portArms">
		<el-card shadow="" class="mgb20">
            <div class="crumbs">
                <el-breadcrumb separator="/">
                    <el-breadcrumb-item>首页</el-breadcrumb-item>
                    <el-breadcrumb-item>证件申请</el-breadcrumb-item>
                    <el-breadcrumb-item>持枪证申报</el-breadcrumb-item>
                </el-breadcrumb>
            </div>
            <div class="titIcon">
                <i class="faSelf sq-icon"></i>
                <p>申请单号：
                    <span v-model="portArmsmId"></span>
                </p>
                <div class="btn-group">
            		<el-button @click="saveForm()">保存</el-button>
                    <el-button @click="resetForm('leaveForm')">取消</el-button>
                    <el-button type="primary" @click="submitLeave()">提交</el-button>
                </div>
            </div>
            <div class="steps">
                <el-steps :active="stepActive" align-center>
                    <el-step title="持枪证申请" icon="el-icon-location"></el-step>
                    <el-step title="单位领导初审" icon="el-icon-location"></el-step>
                    <el-step icon="el-icon-minus" class="long-step">
                    	<template slot="description">
                    		<div class="step-row multiple">
                    			<el-steps direction="vertical" :active="multilevel">
                    				<el-step title="政工复核" icon="el-icon-location"></el-step>
                    				<el-step title="纪检复核" icon="el-icon-location"></el-step>
                    			</el-steps>
                    		</div>
                    	</template>
                    </el-step>
                    <el-step title="业务处/分局主要领导审批" icon="el-icon-location"></el-step>
                    <el-step title="政治部审批" icon="el-icon-location"></el-step>
                    <el-step title="完成" icon="el-icon-location"></el-step>
                </el-steps>
            </div>
            <div class="user-info"> 
                <p></p>
                <p></p>
            </div>
        </el-card>
        <div class="portArmsAskFor">
        	<div class="portArmsAsk">
        		<el-row :gutter="20">
				  	<el-col :span="18">
						<div class="grid-content bg-purple-dark">申请信息</div>
					</el-col>
				  	<el-col :span="2">
				  		<el-upload :on-success="handleSuccess" v-model="leaveForm.enclosure" class="upload-demo" action="http://192.168.8.105:8083/zhzg/upload/uploadFile" :on-preview="handlePreview" :on-remove="handleRemove" :before-remove="beforeRemove" multiple :limit="3" :on-exceed="handleExceed" :file-list="fileList">
							<el-button size="small" type="primary">点击上传</el-button>
						</el-upload>
				  	</el-col>
				  	<el-col :span="2">
                            <div class="el-upload__text downLoad" @click="downWorld()">
                                <span class="icon-world"></span>
                                <span ref="tipModel">人员信息表</span>
                            </div>
                            <div class="exampleIm"></div>
                            <div ref="redTips" class="el-upload__tip"></div>
				  	</el-col>
				  	<el-col :span="2">
				  		<el-button type="text" @click="dialogVisible = true">新增申请人员</el-button>
				  		<el-dialog class="addform" title="新增申请人员信息" :visible.sync="dialogVisible" width="30%" :before-close="handleClose">
  							<el-form :model="addForm" status-icon ref="addForm" label-width="100px" class="demo-ruleForm">
							  	<el-form-item label="身份证号：">
							    	<el-input class="addFormDis " v-model="addForm.IDcard" auto-complete="off" @change="handlerPageNo"></el-input>
							  	</el-form-item>
							  	<el-form-item label="警号：">
							    	<el-input class="addFormDis" v-model="addForm.alarm" auto-complete="off" @change="handlerPageNo"></el-input>
							  	</el-form-item>
							  	<el-form-item label="姓名：" >
							    	<el-input disabled="disabled" v-model="addForm.userName" auto-complete="off"></el-input>
							  	</el-form-item>
							  	<el-form-item label="工作年限：" prop="alarm">
							    	<el-input disabled="disabled" v-model="addForm.workingYears" auto-complete="off"></el-input>
							  	</el-form-item>
							  	<el-form-item label="工作单位：" prop="alarm">
							    	<el-input disabled="disabled" v-model="addForm.workUnits" auto-complete="off"></el-input>
							  	</el-form-item>
							  	<el-form-item label="部门：" prop="alarm">
							    	<el-input disabled="disabled" v-model="addForm.userDepartment" auto-complete="off"></el-input>
							  	</el-form-item>
							  	<el-form-item label="职务：" prop="alarm">
							    	<el-input disabled="disabled" v-model="addForm.job" auto-complete="off"></el-input>
							  	</el-form-item>
							</el-form>
  							<span slot="footer" class="dialog-footer">
							    <el-button @click="dialogVisible = false">取 消</el-button>
							    <el-button type="primary" @click="addStaff">确 定</el-button>
  							</span>
						</el-dialog>
				  	</el-col>
				</el-row>
				<div class="AskForTable">
					<el-row :gutter="20">
					  	<el-col :span="16">
					  		<el-col :span="24" class="title">
								<div class="grid-content bg-purple-dark">人员信息</div>
							</el-col>
					  		<template>
							  	<el-table :data="personnelTab" border style="width: 100%">
								    <el-table-column prop="userName" label="姓名"></el-table-column>
								    <el-table-column prop="workingYears" label="公安工作年限"></el-table-column>
								    <el-table-column prop="workUnits" label="工作单位"></el-table-column>
								    <el-table-column prop="userDepartment" label="部门"></el-table-column>
								    <el-table-column prop="job" label="职务"></el-table-column>
								    <el-table-column prop="idNumber" label="身份证号" v-if="isShow"></el-table-column>
								    <el-table-column label="操作">
								    	<template slot-scope="scope">
									    	<el-button type="text">审批</el-button>
								    	</template>
								    </el-table-column>
							  	</el-table>
							</template>
					  	</el-col>
					  	<el-col :span="8">
					  		<el-col :span="24" class="title">
								<div class="grid-content bg-purple-dark">培训考核</div>
							</el-col>
					  		<template>
							  	<el-table :data="trainTab" border style="width: 100%">
								    <el-table-column prop="theory" label="理论考核" width="80">
								    	<template slot-scope = "scope">
								    		<span v-if="parseInt(scope.row.theory) < 60" style="color: red;">{{scope.row.theory}}</span>
								    		<span v-if="parseInt(scope.row.theory) >= 60" >{{scope.row.theory}}</span>
								    	</template>
								    </el-table-column>
								    <el-table-column prop="firingPractice" label="实弹射击" width="80">
								    	<template slot-scope = "scope">
								    		<span v-if="parseInt(scope.row.firingPractice) < 60" style="color: red;">{{scope.row.firingPractice}}</span>
								    		<span v-if="parseInt(scope.row.firingPractice) >= 60">{{scope.row.firingPractice}}</span>
								    	</template>
								    </el-table-column>
								    <el-table-column prop="serviceOperation" label="勤务操作" width="80">
								    	<template slot-scope = "scope">
								    		<span v-if="scope.row.serviceOperation == '不合格'" style="color: red;">{{scope.row.serviceOperation}}</span>
								    		<span v-if="scope.row.serviceOperation == '合格'">{{scope.row.serviceOperation}}</span>
								    	</template>
								    </el-table-column>
								    <el-table-column prop="inspectionTime" label="考核时间"></el-table-column>
							  	</el-table>
							</template>
					  	</el-col>
					</el-row>
				</div>
        	</div>
        </div>
        <div class="portArmsUpload">
        	<el-col :span="24" class="title">
				<div class="grid-content bg-purple-dark">附件信息</div>
			</el-col>
	    	<el-row :gutter="24">
	            <el-upload class="upload-demo" action="http://192.168.8.105:8083/zhzg/upload/uploadFile" :on-preview="handlePreview" :on-remove="handleRemove" :before-remove="beforeRemove" multiple :limit="3" :on-exceed="handleExceed" :file-list="fileList">
					<el-button size="small" type="primary">点击上传</el-button>
				</el-upload>
	        </el-row>
	    </div>
	</div>
</template>-->
<template>
	<div class="portArms">
		<el-card shadow="" class="mgb20">
			<div class="crumbs">
				<el-breadcrumb separator="/">
					<el-breadcrumb-item>证件申请</el-breadcrumb-item>
					<el-breadcrumb-item>我的持枪证</el-breadcrumb-item>
				</el-breadcrumb>
			</div>
			<div class="titIcon">
				<i class="faSelf sq-icon"></i>
				<!--<p>申请单号：
					<span v-model="portArmsmId"></span>
				</p>-->
				<div class="btn-group">
					<el-button @click="saveForm()">保存</el-button>
					<el-button @click="resetForm('leaveForm')">取消</el-button>
					<el-button type="primary" @click="submitLeave()">提交</el-button>
				</div>
			</div>
			<div class="steps">
				<el-steps :active="stepActive" align-center>
					<el-step title="持枪证申请" icon="el-icon-location"></el-step>
					<el-step title="单位领导初审" icon="el-icon-location"></el-step>
					<el-step icon="el-icon-minus" class="long-step">
						<template slot="description">
							<div class="step-row multiple">
								<el-steps direction="vertical" :active="multilevel">
									<el-step title="政工复核" icon="el-icon-location"></el-step>
									<el-step title="纪检复核" icon="el-icon-location"></el-step>
								</el-steps>
							</div>
						</template>
					</el-step>
					<el-step title="业务处/分局主要领导审批" icon="el-icon-location"></el-step>
					<el-step title="政治部审批" icon="el-icon-location"></el-step>
					<el-step title="完成" icon="el-icon-location"></el-step>
				</el-steps>
			</div>
			<div class="user-info">
				<p></p>
				<p></p>
			</div>
		</el-card>
		<div class="portArmsAsk">
			<el-card class="mag20">
				<div class="AskForTable">
					<el-row :gutter="20">
						<el-col :span="24" class="readInput">
							<el-col :span="24" class="title">
								<div class="grid-content bg-purple-dark">申请信息</div>
							</el-col>
							<img :src="imgIp" alt="" width="96px" height="120px" class="peoImg">
							<el-form :inline="true" :label-position="'right'" label-width="110px" ref='formInline' :model="formInline" class="demo-form-inline PortArms">
								<el-row :gutter="20">
									<el-col :span="8">
										<el-form-item class="formSelect" prop="" label="姓名：">
											<el-input v-model="formInline.userName" readonly="" placeholder="暂无数据"></el-input>
										</el-form-item>
									</el-col>
									<el-col :span="8">
										<el-form-item class="formSelect" prop="" label="警号：">
											<el-input v-model="formInline.userPoliceId" readonly="" placeholder="暂无数据"></el-input>
										</el-form-item>
									</el-col>
									<el-col :span="8">
										<el-form-item class="formSelect" prop="" label="工作单位：">
											<span class="gzdwSpan" v-if="formInline.workUnits">{{formInline.workUnits}}</span>
											<span v-else style="color:#ccc">暂无数据</span>
										</el-form-item>
									</el-col>
								</el-row>

								<el-row :gutter="20">
									<el-col :span="8">
										<el-form-item class="formSelect" prop="" label="部门：">
											<el-input v-model="formInline.deptName" readonly="" placeholder="暂无数据"></el-input>
										</el-form-item>
									</el-col>
									<el-col :span="8">
										<el-form-item class="formSelect" prop="" label="身份证号">
											<el-input v-model="formInline.idCardNo" readonly="" placeholder="暂无数据"></el-input>
										</el-form-item>
									</el-col>
									<el-col :span="8">
										<el-form-item class="formSelect" prop="" label="职务：">
											<el-input v-model="formInline.job" readonly="" placeholder="暂无数据"></el-input>
										</el-form-item>
									</el-col>
								</el-row>
								<el-row :gutter="20">
									<el-col :span="8">
										<el-form-item class="formSelect" prop="" label="民族：">
											<el-input v-model="formInline.national" readonly="" placeholder="暂无数据"></el-input>
										</el-form-item>
									</el-col>
									<el-col :span="8">
										<el-form-item class="formSelect" prop="" label="政治面貌：">
											<el-input v-model="formInline.politicalOrientation" readonly="" placeholder="暂无数据"></el-input>
										</el-form-item>
									</el-col>
									<el-col :span="8">
										<el-form-item class="formSelect" prop="" label="文化程度：">
											<el-input v-model="formInline.eduDegree" readonly="" placeholder="暂无数据"></el-input>
										</el-form-item>
									</el-col>
								</el-row>
								<el-row :gutter="20">
									<el-col :span="8">
										<el-form-item class="formSelect" prop="" label="住址：">
											<span class="gzdwSpan" v-if="formInline.address">{{formInline.zhuzhi}}</span>
											<span v-else style="color:#ccc">暂无数据</span>
										</el-form-item>
									</el-col>
									<el-col :span="8">
										<el-form-item class="formSelect" prop="" label="户口所在地：">
											<el-input v-model="formInline.householdAddress" readonly="" placeholder="暂无数据"></el-input>
										</el-form-item>
									</el-col>
								</el-row>
							</el-form>
						</el-col>
					</el-row>
				</div>
			</el-card>
		</div>
		<el-card class="mag20" style="padding:0 0 20px 0">
			<el-col :span="24">
				<el-col :span="24" class="title">
					<div class="grid-content bg-purple-dark">培训考核</div>
				</el-col>
				<template>
					<el-table :data="trainTab" style="width: 100%" class="tabsss">
						<el-table-column prop="theory" label="理论考核">
							<template slot-scope="scope">
								<span v-if="parseInt(scope.row.theory) < 60" style="color: red;">{{scope.row.theory}}</span>
								<span v-if="parseInt(scope.row.theory) >= 60">{{scope.row.theory}}</span>
							</template>
						</el-table-column>
						<el-table-column prop="firingPractice" label="实弹射击">
							<template slot-scope="scope">
								<span v-if="parseInt(scope.row.firingPractice) < 60" style="color: red;">{{scope.row.firingPractice}}</span>
								<span v-if="parseInt(scope.row.firingPractice) >= 60">{{scope.row.firingPractice}}</span>
							</template>
						</el-table-column>
						<el-table-column prop="serviceOperation" label="勤务操作">
							<template slot-scope="scope">
								<span v-if="scope.row.serviceOperation == '不合格'" style="color: red;">{{scope.row.serviceOperation}}</span>
								<span v-if="scope.row.serviceOperation == '合格'">{{scope.row.serviceOperation}}</span>
							</template>
						</el-table-column>
						<el-table-column prop="inspectionTime" label="考核时间"></el-table-column>
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
			</el-col>
		</el-card>
		<!--<div class="portArmsUpload">
						        	<el-col :span="24" class="title">
										<div class="grid-content bg-purple-dark">附件信息</div>
									</el-col>
							    	<el-row :gutter="24">
							            <el-upload class="upload-demo" action="http://192.168.8.105:8083/zhzg/upload/uploadFile" :on-preview="handlePreview" :on-remove="handleRemove" :before-remove="beforeRemove" multiple :limit="3" :on-exceed="handleExceed" :file-list="fileList">
											<el-button size="small" type="primary">点击上传</el-button>
										</el-upload>
							        </el-row>
							    </div>-->
	</div>
</template>

<script>
export default {
	data() {
		return {
			imgIp:'http://10.33.66.86:9999/download/userImage/'+localStorage.getItem("loginpersonId")+".jpg",
			formInline: {
				name: '',
				alarm: '',
				unit: '',
				bumen: '',
				idCard: '',
				zhiwu: '',
				minzu: '',
				zhengzhimianmao: '',
				wenhuachengdu: '',
				zhuzhi: '',
				hukou: '',
			},
			queryUserScoreList: '',
			idCardArr: [],
			userIdNumbers: [],
			dialogVisible: false,
			downShow: true,
			isShow: false,
			personnelTab: [],
			trainTab: [],
			stepActive: 1,
			multilevel: 0,
			portArmsmId: '',
			fileList: [],
			attOption: '',
			addForm: {
				IDcard: '',
				alarm: '',
				userName: '',
				workingYears: '',
				workUnits: '',
				userDepartment: '',
				job: ''
			},
			leaveForm: {
				enclosure: ''
			},
			pageShow1: true,
			pageShow2: false,
			cur_page: 1,
			cur_page2: 1,
			pagesize: 10,
			pagesize2: 10,
			data_length: 0,
			data_length2: 0,
			ReadTableUrl: ''
		};
	},
	created() {
		this.getTabData();
	},
	methods: {
		// 表格分页导航
		handleCurrentChange(val) {
			// this.loading = true;
			this.cur_page = val;
			this.getTabData();
		},
		// 查询分页导航
		handleCurrentChange2(val) {
			// this.loading = true;
			this.cur_page2 = val;
			// this.getSearchData();
		},
		handlerPageNo() {
			// var self = this;
			// self.$http.get(self.global.serverPath + '/zhzg/armLicence/queryMyArmLicenceInfoByPersonId', {
			// 	//传递参数
			// 	// "idNumber": self.addForm.IDcard || "",
			// 	// "userNo": self.addForm.alarm || ""
			// 	 params: {
			//         "personId" : localStorage.getItem("loginpersonId")
			//         "currentNum": self.cur_page,
			//         "pageSize": that.pagesize
			//     }
			// 	}).then(function(res) {
			// 	armLicence/queryMyArmLicenceInfoByPersonId
			// 	if (res.body.isnull == 1) {
			// 		console.log(res);
			// 		self.queryUserScoreList = res
			// 		self.addForm = {
			// 			userName: res.body.userInfo[0].userName,
			// 			workingYears: res.body.userInfo[0].workingYears,
			// 			workUnits: res.body.userInfo[0].workUnits,
			// 			userDepartment: res.body.userInfo[0].userDepartment,
			// 			job: res.body.userInfo[0].job
			// 		};
			// 	} else {
			// 		self.$message.error('查无数据！')
			// 	}
			// }, function(res) {
			// 	self.$message.error('查询失败！')
			// });
		},
		getTabData() {
			var that = this;
			that.$http.get(that.global.serverPath + '/zhzg/armLicence/queryMyArmLicenceInfoByPersonId', {
				params: {
					"personId": localStorage.getItem('loginpersonId'),
					"currentNum": that.cur_page,
					"pageSize": that.pagesize
				}
			}).then(function(res) {
				if (res.body != '') {
					if (res.body.rows != '') {
						that.formInline = res.body.rows.armLicence[0]
						console.log(res.body.rows)
						that.trainTab = res.body.rows.training;
						that.pageShow1 = true;
						that.pageShow2 = false;
					}
				}
			}, function(res) {
				console.log('获取表格数据失败！')
			});
		},



		addProtArms() {
			var self = this;
			for (var i = 0; i < self.queryUserScoreList.body.userInfo.length; i++) {
				self.personnelTab.push(self.queryUserScoreList.body.userInfo[i]);
				self.trainTab.push(self.queryUserScoreList.body.scores[i]);
				self.idCardArr.push(self.queryUserScoreList.body.userInfo[i].idNumber);
			}
		},
		//点击提交
		submitLeave() {
			var self = this;
			for (var k = 0; k < self.idCardArr.length; k++) {
				self.userIdNumbers.push({ 'idNumber': self.idCardArr[k] });
			};
			console.log(self.userIdNumbers);
			self.$http.post(self.global.serverPath + '/zhzg/armLicence/Submit', {//传递参数
				"userIdNumbers": self.userIdNumbers
			}).then(function(res) {
				console.log(res);
				if (res.body.result == 1) {
					this.$message.success("提交成功");
				} else {
					self.$message.error('提交失败！')
				}
			}, function(res) {
				self.$message.error('请求失败！')
			});
		},
		//点击保存
		saveForm() {
			var self = this;
			for (var k = 0; k < self.idCardArr.length; k++) {
				self.userIdNumbers.push({ 'idNumber': self.idCardArr[k] });
			};
			console.log(self.userIdNumbers);
			self.$http.post(self.global.serverPath + '/zhzg/armLicence/saveForm', {//传递参数
				"userIdNumbers": self.userIdNumbers
			}).then(function(res) {
				console.log(res);
				if (res.body.result == 1) {
					this.$message.success("保存成功");
				} else {
					this.$message.error("保存失败！");
				}
			}, function(res) {
				this.$message.error("请求失败！");
			});
		},
		handleChange(file, fileList) {
			var _this = this;
			if (file.raw) {
				let reader = new FileReader()
				reader.onload = function(e) {
					_this.contentHtml = e.target.result;
				};
				reader.readAsText(file.raw, 'gb2312');

			}
			console.log(fileList);
		},
		handleRemove(file, fileList) {
			console.log(file, fileList);
		},
		handlePreview(file) {
			console.log(file);
		},
		handleExceed(files, fileList) {
			this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
		},
		beforeRemove(file, fileList) {
			return this.$confirm(`确定移除 ${file.name}？`);
		},
		handleClose(done) {
			done();
		},
		addStaff() {
			this.addProtArms();
			this.dialogVisible = false;
		},
		handleSuccess(response, file, fileList) {
			var self = this;
			this.ReadTableUrl = response.rows;
			self.$http.get(self.global.serverPath + '/zhzg/armLicence/ReadTable', {//传递参数
				params: {
					"file": self.ReadTableUrl
				}
			}).then(function(res) {
				for (var i = 0; i < res.body.row.length; i++) {
					self.personnelTab.push(res.body.row[i].userInfo[0]);
					self.trainTab.push(res.body.row[i].scores[0]);
					self.idCardArr.push(res.body.row[i].userInfo[0].idNumber);
				}
				this.$message.error("上传成功");
			}, function(res) {
				this.$message.error("保存失败！");
			});
		},
		// 下载文档
		downWorld() {
			var $eleForm = $("<form method='get'></form>");
			$eleForm.attr("action", "http://39.100.56.78:9999/download/holdGun/web-info.xls");
			$(document.body).append($eleForm);
			//提交表单，实现下载
			$eleForm.submit();
		}
	},
	mounted() {
        var self = this;
		if (self.$route.query.alarmNo) {//详情跳转
			var that = this;
			that.$http.get(that.global.serverPath + '/zhzg/armLicence/queryMyArmLicenceInfoByPersonId', {
				params: {
					"personId": self.$route.query.alarmNo,
					"currentNum": that.cur_page,
					"pageSize": that.pagesize
				}
			}).then(function(res) {
				if (res.body != '') {
					if (res.body.rows != '') {
						that.formInline = res.body.rows.armLicence[0]
						console.log(res.body.rows)
						that.trainTab = res.body.rows.training;
						that.pageShow1 = true;
						that.pageShow2 = false;
					}
				}
			}, function(res) {
				console.log('获取表格数据失败！')
			});
		}}
	}
</script>

<style scoped>
.peoImg {
	position: absolute;
	top: 35%;
	right: 0;
	z-index: 99;
}

.portArms .title {
	float: inherit;
	padding: 4px 0 20px 0;
	border-bottom: 1px solid #EBEEF5;
	-webkit-box-sizing: border-box;
	box-sizing: border-box;
}

.portArmsAsk>.el-row {
	margin-left: 0!important;
	margin-right: 0!important;
	padding: 20px;
	border-bottom: 1px solid #EBEEF5;
}

.portArmsAsk .el-icon-download {
	color: #409EFF;
}

.el-icon-download:hover {
	cursor: pointer;
}

.portArmsAsk>.el-row .grid-content {
	font-family: PingFangSC-Medium;
	font-size: 18px;
	color: #262626;
	font-weight: bold;
}

.el-table-add-row {
	cursor: pointer;
	width: 100%;
	text-align: center;
	height: 32px;
	line-height: 32px;
	font-family: PingFangSC-Regular;
	font-size: 14px;
	border: 1px dashed #D9D9D9;
	color: rgba(0, 0, 0, 0.85);
	margin-top: 10px;
}

.AskForTable .title {
	border-bottom: 0
}

.steps {
	padding: 3% 3% 0 3%;
}

.portArmsUpload .el-form-item__label {
	font-family: PingFangSC-Regular;
	font-size: 14px;
	color: rgba(0, 0, 0, 0.85);
	letter-spacing: 0;
}
</style>
<style>
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

.tabsss .el-table th>.cell {
	color: rgba(0, 0, 0, 0.85)!important;
	font-size: 14px;
}

.readInput .el-input__inner {
	width: 98%;
	border: none;
	padding: 0;
}

.steps .el-step__title {
	font-size: 14px!important;
}

.steps .el-step__icon-inner[class*=el-icon]:not(.is-status) {
	font-size: 17px!important;
}

.steps .el-step__title.is-process {
	font-weight: normal;
	color: rgba(0, 0, 0, 0.65);
}

.steps .el-step__head.is-process {
	color: #C0C4CC;
}

.steps .el-step__title.is-wait {
	color: rgba(0, 0, 0, 0.65);
}

.steps .el-step__title.is-finish {
	color: rgba(0, 0, 0, 0.85);
	font-family: PingFangSC-Medium;
	font-size: 14px;
	font-weight: 600;
}

.mag20 {
	margin: 20px;
}

.portArmsAsk .el-button {
	background: inherit;
	color: #409EFF;
	border: 0;
	font-size: 14px;
}

.portArmsAsk .el-upload {
	height: inherit!important;
	width: inherit!important;
	border: 0
}

.portArmsUpload {
	background: white;
	margin: 20px;
}

.portArmsUpload .el-row {
	margin-left: 0!important;
	margin-right: 0!important;
}

.portArmsUpload .el-upload {
	height: inherit!important;
	border: 0;
	width: inherit;
	float: left;
	padding: 0;
}

.portArmsAsk .el-button {
	padding: 0!important
}

.portArmsAsk .el-upload-list {
	display: none;
}

.portArmsUpload .el-upload-list {
	float: left
}

.portArms .el-step__icon-inner {
	font-size: 13px;
}

.portArms .el-step__title {
	font-size: 14px;
}

.portArms .title {
	font-weight: bold;
}

.portArmsUpload .el-button {
	margin: 20px;
}

.step-row {
	height: 130px;
	margin-top: -74px
}

.long-step.is-center .el-step__main {
	text-align: left!important;
}

.long-step .is-vertical .el-step__title {
	padding-bottom: 0!important;
}

.long-step.is-center .el-step__description {
	background: #fff;
	position: relative;
	z-index: 999;
	width: 130px;
}

.long-step.is-center .el-step__description {
	padding-left: 5%;
	padding-right: 5%;
}

.multiple .el-step__main {
	height: 40px;
}

.multiple .el-step__head {
	height: 40px;
}

.multiple .el-step.is-vertical {
	height: 40px;
}

.portArms .el-step__icon-inner {
	font-size: 17px!important;
}

.addform .el-input__inner {
	width: 200px!important;
}

.addform .el-dialog__footer button {
	width: 56px;
	height: 32px;
	border: 1px solid #dcdfe6;
	font-size: 12px;
}

.addform button:nth-of-type(1) {
	color: #606266;
}

.addform button:nth-of-type(2) {
	color: #FFF;
	background-color: #409EFF;
	border-color: #409EFF;
}

.addform .el-dialog__header {
	border-bottom: 1px solid #DCDFE6;
}

.addform .el-dialog__footer {
	border-top: 1px solid #DCDFE6;
}

.addform .el-form-item {
	width: 70%;
	margin: 20px auto
}

.downLoad span {
	color: #409EFF;
	font-size: 14px;
}

.downLoad span:hover {
	cursor: pointer;
}

.gzdwSpan {
	color: #606266;
	font-size: 14px;
	word-break: break-all!important;
	word-wrap: break-word!important;
}
</style>
