<template>
	 <div class="table LeaveManage" v-loading="loading" element-loading-text="拼命加载中" element-loading-spinner="el-icon-loading" element-loading-background="rgba(0, 0, 0, 0.8)"> 
		<el-card shadow="" class="mgb20">
            <div class="crumbs">
                <el-breadcrumb separator="/">
                    <el-breadcrumb-item>勤务管理</el-breadcrumb-item>
                    <el-breadcrumb-item>集体离宁申请</el-breadcrumb-item>
                </el-breadcrumb>
            </div>
            <div class="titIcon">
                <i class="faSelf sq-icon"></i>
                <p v-show="applyFormIdShow">申请单号：
                    <span>{{applyFormId}}</span>
                </p>
            </div>
            <div class="LeaveManCon"></div>
            <div class="LeaveUser-info">
                <p></p>
                <p></p>
            </div>
        </el-card>
		<div class="containe">
			<div class="leave is-always-shadow">
				<el-row>
					<el-col :span="24" class="title">
						<div class="grid-content bg-purple-dark">申请信息</div>
					</el-col>
					<el-row>
					  	<el-col :span="24">
					  		<el-form :inline="true" :rules="rules2" :label-position="'right'" label-width="120px" ref='leaveForm' :model="leaveForm" class="demo-form-inline fuzzinfoCon">
								<el-row :gutter="6">
									<el-col :span="24">
										<el-form-item prop="outReason" class="formSelect" label="外出事由：">
											<el-select v-model="leaveForm.outReason" @change="selects()">
												<el-option label="开会" value="开会"></el-option>
												<el-option label="学习" value="学习"></el-option>
												<el-option label="调研" value="调研"></el-option>
											</el-select>
										</el-form-item>
									</el-col>
                                    <el-col :span="8">
										<el-form-item label="开始时间" prop="valueBegin">
											<el-date-picker v-model="leaveForm.valueBegin" :picker-options="pickerOptions0" format='yyyy-MM-dd' style="width: 76%;" value-format="yyyy-MM-dd" type="date"></el-date-picker>
										</el-form-item>
									</el-col>
									<el-col :span="8">
										<el-form-item label="结束时间：" prop="valueEnd">
											<el-date-picker :picker-options="pickerOptions1" v-model="leaveForm.valueEnd" format='yyyy-MM-dd' value-format="yyyy-MM-dd" type="date"></el-date-picker>
										</el-form-item>
									</el-col>
									<el-col :span="8">
										<el-form-item prop="outDay" label="外出天数：">
											<el-input v-model="leaveForm.outDay" ></el-input>
										</el-form-item>
									</el-col>
									<el-col :span="8">
										<el-form-item prop="unitOut" class="formSelect isLeaveNJ" label="单位：">
											<el-select v-model="leaveForm.unitOut" v-bind:disabled="disabledInput">
												<el-option label="是" value="是" key="是" ></el-option>
												<el-option label="否" value="否" key="否" ></el-option>
											</el-select>
										</el-form-item>
									</el-col>
                                    <el-col :span="8">
										<el-form-item prop="teamLead" class="formSelect isLeaveNJ" label="带队领导：">
											<el-select v-model="leaveForm.teamLead" v-bind:disabled="disabledInput">
												<el-option label="是" value="是" key="是" ></el-option>
												<el-option label="否" value="否" key="否" ></el-option>
											</el-select>
										</el-form-item>
									</el-col>
                                    <el-col :span="8">
										<el-form-item prop="phoneNum" label="联系电话：">
											<el-input v-model="leaveForm.phoneNum" ></el-input>
										</el-form-item>
									</el-col>
                                    <el-col :span="8">
										<el-form-item prop="outPlace" label="外出地点：">
											<el-input v-model="leaveForm.outPlace" ></el-input>
										</el-form-item>
									</el-col>
									<el-col :span="8">
										<el-form-item prop="transportation" class="formSelect" label="交通工具：">
											<el-select v-model="leaveForm.transportation">
												<el-option label="火车" value="火车" key="火车" ></el-option>
												<el-option label="飞机" value="飞机" key="飞机" ></el-option>
												<el-option label="客车" value="客车" key="客车" ></el-option>
												<el-option label="自驾" value="自驾" key="自驾" ></el-option>
												<el-option label="其他" value="其他" key="其他" ></el-option>
											</el-select>
										</el-form-item>
									</el-col>
                                    <el-col :span="8">
										<el-form-item class="formSelect upClass" prop="enclosure" label="上传附件：" pro="upOption">
											<el-upload :on-success="handleSuccess" v-model="leaveForm.enclosure" class="upload-demo" :action="actss" drag  multiple>
												<el-button size="small" type="primary">证明材料请点击上传</el-button>
											</el-upload>
										</el-form-item>
									</el-col>
                                    <el-col :span="24" class="mag20"><span>同行人员</span></el-col>
									<div class="sqxx-table txryTable txryTableInput">
					                    <el-table size="mini" :data="master_user.data" border style="width: 100%" highlight-current-row>
					                        <el-table-column v-for="(v,j) in master_user.columns" :key="j" :prop="v.field" :label="v.title" :width="v.width">
					                            <template slot-scope="scope">
					                                <span v-if="scope.row.isSet">
					                                    <el-input size="mini" v-model="master_user.sel[v.field]">
					                                    </el-input>
					                                </span>
					                                <span v-else>{{scope.row[v.field]}}</span>
					                            </template>
					                        </el-table-column>
					                        <el-table-column label="操作" width="180">
					                            <template slot-scope="scope" style="text-align: center;">
					                                <span class="el-tag el-tag--info el-tag--mini" style="cursor: pointer;" @click="pwdChange(scope.row,scope.$index,true)">
					                                    {{scope.row.isSet?'保存':"编辑"}}
					                                </span>
					                                <span v-if="!scope.row.isSet" class="el-tag el-tag--danger el-tag--mini" @click="deleteRow(scope.$index, scope.row)" style="cursor: pointer;">删除</span>
					                                <span v-else class="el-tag  el-tag--mini" style="cursor: pointer;" @click="pwdChange(scope.row,scope.$index,false)">取消</span>
					                            </template>
					                        </el-table-column>
					                    </el-table>
					                    <div class="el-table-add-row" style="width: 100%;" @click="addMasterUser()">
					                        <span>+ 新增同行人员</span>
					                    </div>
					                </div>
									<!--<el-col :span="24" class="reasonLeaveBox leaveManage">
										<el-form-item class="formSelect" label="备注：" prop="cause">
											<el-input class="reasonLeaveCon" maxlength="500"  v-model="leaveForm.cause"  @input="descInput" ></el-input>											
										</el-form-item>
									</el-col>-->
								</el-row>
								<el-row>
									<el-col :span="24">
										 <div class="btn-group">
											<el-button @click="saveForm('leaveForm')">保存</el-button>
											<el-button @click="resetForm('leaveForm')">取消</el-button>
											<el-button type="primary" @click="submitLeave('leaveForm')">提交</el-button>
										 </div>
									</el-col>
								</el-row>
							</el-form>
					  	</el-col>
					</el-row>
				</el-row>
				
			</div>
		</div> 
		 <el-footer :style="'text-align:center'">&copy;2019-2022&emsp;公安局&emsp;版权所有</el-footer>
	 </div> 
</template>

<script>

import crypto from 'crypto';
export default {
	data() {
		return {
            leaveForm: {
				outReason:'',//外出事由
				valueBegin: '',//开始时间
				valueEnd: '',//结束时间
				outDay: '',//外出天数
				unitOut: '',//单位
                teamLead:'',//带队领导
                phoneNum:'',//联系电话
                outPlace:'',//外出地点
                transportation:'',//交通工具
                enclosure:'',//上传附件
                fraternity:''//同行人员
			},
			loading:false,
			disabledInput:false,
			actss:this.global.serverPath+"/zhzg/upload/uploadFile?pathUrl=download/qxj&personId="+localStorage.getItem('loginpersonId'),
			workId:'',
			txtVal: 0,
			applyFormIdShow:false,
			imgUrl:'',
			upFileName:[],
			fileList: [],
			addTableForm: false,
			applyFormId: '',	//申请单号
			formPerson: {
				appellation: '',
				name: '',
				unit: '',
				duties: ''
			},
			pickerOptions0: {//开始时间
                disabledDate: time => {
                    let endDateVal = this.leaveForm.valueEnd;
                    if (endDateVal) {//不能早于当前日期
                        return (time.getTime() > new Date(endDateVal).getTime()) || (time.getTime()  < Date.now() - 8.64e7);
                    }else{
                        return time.getTime()  < Date.now() - 8.64e7;
                    }
                }
            },
            pickerOptions1: {//结束时间
                disabledDate: time => {
                    let beginDateVal = this.leaveForm.valueBegin;
                    if (beginDateVal) {
                        return (
                            time.getTime() < new Date(beginDateVal).getTime() - 1 * 24 * 60 * 60 * 1000
                        );
                    }else{
                        return time.getTime()  < Date.now() - 8.64e7;
                    }
                }
            },
			master_user: {
				sel: null,//选中行
                columns: [
                    { field: "userName", title: "姓名", width: 140 },
                    { field: "userNo", title: "称谓", width: 160 },
                    { field: "workUnit", title: "工作单位" },
                    { field: "department", title: "职务",width: 240 },
                    // { field: "info", title: "其他信息" }
                ],
                data: [],
            },
			
			idx: -1,
            
			rules1: {
				appellation: [
					{ required: true, message: '请输入同行人称谓', trigger: 'change' },
				],
				name: [
					{ required: true, message: '请输入同行人姓名', trigger: 'change' },
				],
				unit: [
					{ required: true, message: '请输入同行人工作单位', trigger: 'change' },
				],
				duties: [
					{ required: true, message: '请输入同行人职务', trigger: 'change' },
				]
			},
			rules2: {//表单验证
                outReason: [
					{ required: true, message: '请选择外出事由', trigger: 'change' }
				],
				valueBegin: [
					{ required: true, message: '请选择日期', trigger: 'change' }
				],
				valueEnd: [
					{ required: true, message: '请选择日期', trigger: 'change' }
				],
				unitOut: [
					{ required: true, message: '请选择单位', trigger: 'change' }
				],
				teamLead: [
					{ required: true, message: '请选择单位领导', trigger: 'change' }
				],
                phoneNum: [
					{ required: true, message: '请填写联系电话', trigger: 'change' }
				],
                outPlace: [
					{ required: true, message: '请填写外出地点', trigger: 'change' }
				],
                transportation: [
					{ required: true, message: '请选择交通工具', trigger: 'change' }
				],
				fraternity:[
					{ required: true, message: '同行人员', trigger: 'change' }
				]
			},
		}
	},
	created() {
		this.submitForm();
		// this.getData();
		this.init()
	},
	methods:{
		 // 文本域计数
        descInput() {
            this.txtVal = this.leaveForm.cause.length;
        },
		init(){
			var self = this;
			// self.$http.get(self.global.serverPath4+ '/process_engine/PleaseSellOff/getProcessBar', {//传递参数
			// 	params: {
			// 		"personId":localStorage.getItem("loginpersonId"),
			// 		"processName":"请销假",
			// 	}
			// }).then(function(res) {
			// 	var index = -1;
			// 	var stepArr = [];
			// 	var msg = res.body;
			// 	var optionCon = '';
			// 	for (var val in msg) {
			// 		stepArr.push(msg[val])
			// 	};
			// 	for(var i=0;i<stepArr.length;i++){ //applyStatus=0 不亮/灰色    applyStatus=1 亮/蓝色   applyStatus=2 亮/红色 
			// 		$(".LeaveManCon").append('<div class="alone '+ (stepArr[i].applyStatus == 0?"gray":stepArr[i].applyStatus ==1?"green":stepArr[i].applyStatus == 2?"red":"gray") +'"><div class="circleDot finishType"><div class="circle"><div></div>'+ (stepArr[i].applyNAME ||'') +'</div></div><div class="figure"></div><div class="contentCon"><p>'+ (stepArr[i].applyUser ||'')  +'</p><p>'+  (stepArr[i].endTime ||'') +'</p> </div></div>') 
			// 	};
			// 	for(var r=0;r<$(".data").length;r++){
			// 		$(".data").eq(r).css({'height':$(".daughter").eq(r).height()+1+'px','line-height':$(".daughter").eq(r).height()+'px','width':$(".data").eq(r).width()+15});
			// 		$(".roundDot").eq(r).css({'height':$(".daughter").eq(r).height()+1+'px'});
			// 	};
			// }, function(res) {
			// 	self.$message.error('请求失败')
			// });
		},
		handleSuccess(response,file,fileList){//上传文件成功
			this.upFileName.push(response.rows);
		},
		resetForm() {//重置
			this.addTableForm = false
		},
		// 同行人员删除表格行
        deleteRow(index, row) {
            this.idx = index;
            this.master_user.data.splice(this.idx, 1);
            this.$message.success('删除成功');
        },
        //同行人员读取表格数据
        readMasterUser() {
            this.master_user.data.map(i => {
                i.isSet = false;//给后台返回数据添加`isSet`标识
                return i;
            });
        },
        //同行人员添加账号
        addMasterUser() {
            for (let i of this.master_user.data) {
                if (i.isSet) return this.$message.warning("请先保存当前编辑项");
            }
            let j = { id: 0, "userName": "", "userNo": "", "workUnit": "", "department": "", "isSet": true, "_temporary": true };
            this.master_user.data.push(j);
            this.master_user.sel = JSON.parse(JSON.stringify(j));
        },
        //同行人员修改
        pwdChange(row, index, cg) {
            //点击修改 判断是否已经保存所有操作
            for (let i of this.master_user.data) {
                if (i.isSet && i.id != row.id) {
                    this.$message.warning("请先保存当前编辑项");
                    return false;
                }
            }
            //是否是取消操作
            if (!cg) {
                if (!this.master_user.sel.id) this.master_user.data.splice(index, 1);
                return row.isSet = !row.isSet;
            }
            //提交数据
            if (row.isSet) {
                //项目是模拟请求操作 自己修改下
                let data = JSON.parse(JSON.stringify(this.master_user.sel));
                for (let k in data) row[k] = data[k];
                this.$message({
                    type: 'success',
                    message: "保存表格!"
                });

                //然后这边重新读取表格数据
                this.readMasterUser();
                row.isSet = false;
            } else {
                this.master_user.sel = JSON.parse(JSON.stringify(row));
                row.isSet = true;
            }
        },
		//点击  提交
		submitLeave(leaveForm) {
			this.$refs[leaveForm].validate((rules2) => {
				this.loading = true;
				if (rules2) {
					var self = this;
					// self.$http.post(self.global.serverPath4 + '/process_engine/PleaseSellOff/Submit', {//传递参数
					// 	"applyFormId":self.applyFormId,
					// 	"userId": localStorage.getItem("loginpersonId"), //申请人id
					// 	"workId":self.workId,
					// 	"beginTime": self.leaveForm.valueBegin, 					//开始时间
					// 	"endTime": self.leaveForm.valueEnd, 						//结束时间
					// 	"leaveDay": self.leaveForm.willa,						//请假天数
					// 	"leaveSubject": self.leaveForm.Subject,						//请假主体
					// 	"isLeaveNJ": self.leaveForm.Partir,							//是否离宁
					// 	"address": self.leaveForm.address,
					// 	"applyType": self.leaveForm.applyType,						//请假事由
					// 	"leaveCause": self.leaveForm.cause,						    //请假原因
					// 	"enclosure":self.upFileName,								//附件
					// 	"pleSeOffTogether":self.master_user.data,
					// 	"lateMarriage":self.leaveForm.lateMarriage,					//是否晚婚
					// 	"maternityLeaveType":self.leaveForm.maternityLeaveType,		//产假类型
					// 	"isLateChildbirth":self.leaveForm.isLateChildbirth,			//是否晚育
					// 	"births":self.leaveForm.births,								//胎数
					// 	"kinship":self.leaveForm.kinship,							//亲属关系
					// 	"isTogether":self.leaveForm.fraternity,
					// 	"workingYears":self.leaveForm.workingYears,					//工作年限
					// 	"vehicle":self.leaveForm.transportation
					// }).then(function(res) {
					// 	this.loading = false;
					// 	if(res.body.result == 1){
					// 		self.$message.success('提交成功');
					// 		self.$refs[leaveForm].resetFields();
					// 	}else{
					// 		self.$message.error('提交失败')
					// 	}
					// })
				}
			})
		},
		//点击 保存
		saveForm(leaveForm) {
			this.$refs[leaveForm].validate((rules2) => {
				if (rules2) {
					var self = this;
					// self.$http.post(self.global.serverPath4 + '/process_engine/PleaseSellOff/saveForm', {//传递参数
					// 	"userId": localStorage.getItem("loginpersonId"), //申请人id
					// 	"workId":self.workId,
					// 	"beginTime": self.leaveForm.valueBegin, 				//开始时间
					// 	"endTime": self.leaveForm.valueEnd, 					//结束时间
					// 	"leaveDay": self.leaveForm.willa,						//请假天数
					// 	"applyType": self.leaveForm.applyType,						//请假事由
					// 	"leaveSubject": self.leaveForm.Subject,					//请假主体
					// 	"isLeaveNJ": self.leaveForm.Partir,						//是否离宁
					// 	"address": self.leaveForm.address,						//外出地点
					// 	"leaveCause": self.leaveForm.cause,						//请假原因
					// 	"pleSeOffTogether":self.master_user.data,
					// 	"lateMarriage":self.leaveForm.lateMarriage,					//是否晚婚
					// 	"maternityLeaveType":self.leaveForm.maternityLeaveType,		//产假类型
					// 	"isLateChildbirth":self.leaveForm.isLateChildbirth,			//是否晚育
					// 	"births":self.leaveForm.births,								//胎数
					// 	"kinship":self.leaveForm.kinship,							//亲属关系
					// 	"workingYears":self.leaveForm.workingYears,					//工作年限
					// 	"isTogether":self.leaveForm.fraternity,
					// 	"vehicle":self.leaveForm.transportation
					// }).then(function(res) {
					// 	if (res.body.result == 1) {
					// 		self.master_user.sel=null;
					// 		self.master_user.data=[];
					// 		self.$message.success('保存成功');
					// 		self.$refs[leaveForm].resetFields();
					// 	} else {
					// 		self.$message.error('保存失败！')
					// 	}
					// }, function(res) {
					// 	self.$message.error('提交失败')
					// });
				}
			})
		},
        //外出事由
		selects() {
			var self = this;
            // if (self.leaveForm.applyType == "调休" ||self.leaveForm.applyType == "其他") {
			// 	self.disabledInput = false;
			// 	this.toggleShow = false;
			// 	self.leaveForm.Partir = '';
            // }else if(self.leaveForm.applyType == "离宁外出"){
			// 	this.toggleShow = false;	
			// 	self.leaveForm.Partir = '是';
			// 	this.disabledInput=true;
			// }else{
			// 	self.disabledInput = false;
			// 	this.toggleShow = true;
			// 	self.leaveForm.Partir = '请选择';
            // }
		},
		submitForm() {
			var self = this;
			// self.$http.get(self.global.serverPath4 + '/process_engine/PleaseSellOff/queryForm', {//传递参数
			// 	params: {
			// 		"userid": window.localStorage['loginpersonId'],
			// 	}
			// }).then(function(res) {
			// 	self.imgUrl = res.body.userImage;
			// 	if (res.body.isnull == 1) {
			// 		console.log(res.body.togetherList)
			// 		self.stepType = res.body.row[0].applyType;
			// 		self.applyFormId = res.body.row[0].applyFormId;
			// 		self.workId = res.body.row[0].workId;
			// 		self.leaveForm.workingYears = res.body.row[0].workingYears;
			// 		self.leaveForm.valueBegin = res.body.row[0].beginTime;
			// 		self.leaveForm.valueEnd = res.body.row[0].endTime;
			// 		self.leaveForm.willa = res.body.row[0].leaveDay;
			// 		self.leaveForm.address = res.body.row[0].address;
			// 		self.leaveForm.cause = res.body.row[0].leaveCause;
			// 		self.leaveForm.applyType = res.body.row[0].applyType;
			// 		self.leaveForm.Subject = res.body.row[0].leaveSubject;
			// 		self.leaveForm.Partir = res.body.row[0].isLeaveNJ;
			// 		self.leaveForm.lateMarriage = res.body.row[0].lateMarriage;
			// 		self.leaveForm.maternityLeaveType = res.body.row[0].maternityLeaveType;
			// 		self.leaveForm.isLateChildbirth = res.body.row[0].isLateChildbirth;
			// 		self.leaveForm.births = res.body.row[0].births;
			// 		self.leaveForm.kinship = res.body.row[0].kinship;
			// 		self.leaveForm.fraternity = res.body.row[0].isTogether;
			// 		// self.master_user.data = res.body.togetherList;
			// 		self.leaveForm.transportation = res.body.row[0].vehicle;
			// 		if(res.body.row[0].applyFormId != '' || res.body.row[0].applyFormId != undefined ||res.body.row[0].applyFormId!= null){
			// 			self.applyFormIdShow = true;

			// 		};
			// 	}else{
			// 		self.workId = res.body.workId;
			// 		self.leaveForm.workingYears = res.body.workingYears
			// 	};
			// 	if(res.body.isDays == 1){
			// 		self.leaveForm.casualLeaveDays = res.body.leaveDays[0].casualLeaveDays || 0;
			// 		self.leaveForm.sickLeaveDays = res.body.leaveDays[0].sickLeaveDays || 0;
			// 		self.leaveForm.homeLeaveDays = res.body.leaveDays[0].homeLeaveDays || 0;
			// 		self.leaveForm.paidLeaveDays = res.body.leaveDays[0].paidLeaveDays || 0;
			// 		// console.log(self.leaveForm.casualLeaveDays);
			// 		// console.log(self.leaveForm.sickLeaveDays);
			// 		// console.log(self.leaveForm.homeLeaveDays);
			// 		// console.log(self.leaveForm.paidLeaveDays);
			// 	}else{
			// 		self.leaveForm.casualLeaveDays = res.body.isDays;
			// 		self.leaveForm.sickLeaveDays = res.body.isDays;
			// 		self.leaveForm.homeLeaveDays = res.body.isDays;
			// 		self.leaveForm.paidLeaveDays = res.body.isDays;
			// 	}
			// }, function(res) {
			// 	self.$message.error('请求失败！')
			// });
		},
		//新增同行人员确定
		defineForm(formName1) {
	        this.$refs[formName1].validate((valid) => {
	          	if (valid) {
	          		this.$refs.formPerson.clearValidate()
	          		this.addTableForm = false;
	          	}
	        });
      	},
	}
}
</script>

<style scoped>
.txryTable .el-table--border th,.txryTable .el-table__fixed-right-patch{
	text-align:center;
}
.mag20 {
	margin: 4px 0 20px 0;
}
.wrapper .el-table th{
	text-align: center!important;
}
.el-form-item {
	margin-bottom: 12px
}
.el-tabs__nav-wrap,
.el-tabs {
	width: 100%!important;
	margin-left: 0px!important;
}
.right-btn {
	float: right;
}
.handle-select {
	width: 200px;
}
.pagination {
	margin-bottom: 10px
}
.pagination2 {
	height: 32px;
	margin-bottom: 10px
}
.pagination2 .el-pagination {
	position: absolute;
	right: 30px;
	bottom: 10px;
}
.handle-input {
	width: 300px;
	display: inline-block;
}
.el-footer {
	height: 20px!important;
	font-size: 12px;
	color: #333;
	line-height: 20px
}
.del-dialog-cnt {
	font-size: 16px;
	text-align: center
}
/*表格内容居中*/
.el-table td, .el-table th{
	text-align: center;
}
.LeaveManage .el-table__row td{
	text-align: center
}

</style>
<style>
	.upClass .el-upload-dragger{
		width:auto;
		height:auto;
		border:none;
	}
.leaveManage .el-form-item__content{
	width:calc(100% - 145px)!important;
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
    color: #409eff;
    margin-top: 10px;
	border-color: #b3d8ff
}
.table .crumbs {
	background: white;
	margin-bottom: 0;
}
.table .titIcon{
	margin-top:10px
}
.leave {
	margin:16px 20px;
	background: white;
}
.leaveList .leave .el-row {
	margin-bottom: 0px!important;
}
.leave .el-rowL:first-child {
	margin-bottom: 0;
}
.infoTab .el-table--border {
	border: 0
}
.applyNum .grid-content {
	padding:20px;
	border-bottom: 1px solid #f0f0f0;
	margin-bottom: 15px;
	font-weight: bold;
}
.fuzzinfoCon {
	padding:20px 20px 10px;
}
.fuzzinfoCon input {
    width: 208px!important;
}
.txryTableInput input{
	width: 72%!important;
}
.fuzzinfoCon select {
    width: 208px!important;
}
.el-upload {
	width: initial;
	height: initial;
	border: 0
}
.el-upload-list {
	float: left;
}
.el-upload {
	float: left
}
.el-upload-list__item:first-child {
	margin-top: 5px
}
.infoTab .el-table__empty-block {
	width: 100%!important
}
.el-upload-list__item-name {
	color: rgb(32, 160, 255)
}
.content .apply {
	background-position: 10px!important;
	height: 30px;
	line-height: 30px;
	padding-left: 45px;
}
.applyNum {
	padding: 10px 15px;
	background: white;
}
.applyNum .grid-content {
	margin-bottom: 0;
	border: 0;
}
.applyNum .grid-content:nth-of-type(2) {
	padding: 0;
}
.apply, .gridright {
	padding: 0;
}
.gridright {
	text-align: right;
}
.submit {
	color: white;
}
.stepCon {
	width: 100%;
	overflow: hidden;
	background: white;
	margin: 0 auto;
	padding:20px;
}
.steps {
    padding: 3% 3% 0 3%;
}
.infoTab .el-table__empty-block {
	min-height: 53px;
}
.sqxx-table{
	padding: 0 20px 20px;
}
.sqxx-table .el-tag--info {
    background-color: transparent;
    border: none;
}
.containe .title{
	float:inherit;
    padding: 18px 20px;
	border-bottom: 1px solid #EBEEF5;
	-webkit-box-sizing: border-box;
	box-sizing: border-box;
}
.sqxx-table .el-tag--info,
.sqxx-table .el-tag--info .el-tag__close {
    font-size: 14px;
    color: #1890FF;
}
.sqxx-table .el-tag--danger {
    background-color: transparent;
    border: none;
    font-size: 14px;
}
.steps .el-step__icon-inner[class*=el-icon]:not(.is-status) {
    font-size: 17px!important;
}
.steps .el-step__title {
    font-size: 14px!important;
}
.sqxx-table .el-tag {
    border: none;
    font-size: 14px;
    background-color: transparent;
}
.LeaveUser-info {
    padding: 0 11.7%;
    font-family: PingFangSC-Regular;
    font-size: 14px;
    color: rgba(0, 0, 0, 0.65);
}
.LeaveUser-info p {
    margin: 10px 0;
}
.sq-icon {
    background: url('../../../static/img/sq.png') no-repeat center;
    background-size: 100% 100%;
}
.faSelf {
    display: inline-block;
    width: 30px;
    height: 30px;
    position: relative;
}
.leave .title{
	float:inherit
}
.figurePortrait{
	position: relative;
	width: 125px;
	height: 175px;
}
.figurePortrait img{
	position: absolute;
	left: 26%;
	top: 50%;
	transform: translate(-50%,-50%);
	width: 100%;
}
.prompt{
	font-size: 12px;
	color: red;
	line-height: 22px;
    text-indent: 2.5rem;
}
.reasonLeaveBox .formSelect{
	width: 100%;
	position:relative
}
.reasonLeaveBox .reasonLeaveCon .el-textarea__inner{
	height: 60px!important;
}
.reasonLeaveBox .numberV{
	position:absolute;
	left:80%;
	bottom:55px
}
.promptCon{
	border-radius: 5px;
	padding: 8px;
	margin-bottom: 15px;
}
.fuzzinfoCon .borderShow{
	border:1px solid  #DCDFE6;
	display:block;
	width:calc(100% - 50px);
}
.fuzzinfoCon .borderHide{
	border:0;
	display: none;
}
.LeaveManCon{display: flex;flex-direction:row;width: 90%;margin:0 auto}
.LeaveManCon>div{flex: 1;position: relative;height: 130px;overflow: hidden;}
.LeaveManCon .multiple{display: flex;flex-direction: column;}
.LeaveManCon .daughter{
	flex: 1;
	position: relative;
}
.LeaveManCon .circleDot ,.roundDot{
	position: absolute;
	background: white;
	z-index: 10;
}
.LeaveManCon .circle{
	position: relative;
	width: 100%;
	height: 100%;
	padding-left:20px;
	padding-right:20px;
	font-size: 12px;
	white-space: nowrap;
}
.LeaveManCon .circle>div{
	position: absolute;
	width: 10px;
	height: 10px;
	left: 5px;
	top:50%;
	transform: translateY(-50%);
	background: #eee;
	border-radius: 5px;
}
.LeaveManCon .circleDot{
	left:50%;
	transform: translate(-50%,-50%);
	top:50%;
}
.LeaveManCon .roundDot{
	top:50%;
	left:5%;
	transform: translate(-50%,-50%);
}
.LeaveManCon .data{
	position: absolute;
	top:50%;
	left: 10%;
	transform: translateY(-50%);
	font-size: 12px;
	min-width:180px ;
	background: white;
}
.LeaveManCon .name{
	position: absolute;
	top:50%;
	right: 10%;
	transform: translateY(-50%);
	font-size: 12px;
}
.LeaveManCon .finishColor{
	background: #3385ff;
}
.LeaveManCon .procedure{
	background: #eee;;
}
.LeaveManCon .contentCon{
	width: 100%;
	position: absolute;
	left:50%;
	top:105px;
	transform: translate(-50%,-50%);
	text-align: center;
}
.LeaveManCon .alone p{
	font-size: 12px;
	line-height: 20px;
}
.LeaveManCon>div .figure{
	width: 100%;
	height: 2px;
	position: absolute;
	top:50%;
	transform: translateY(-50%);
}
.LeaveManCon>div:first-child .figure{
	width: calc(50% - 20px);
	height: 2px;
	position: absolute;
	top:50%;
	transform: translateY(-50%);
	right: 0;
}
.LeaveManCon>div:last-child .figure{
	width: calc(50% - 20px);
	height: 2px;
	position: absolute;
	top:50%;
	transform: translateY(-50%);
	left: 0;
}
.LeaveManCon .multiple .figure{
	position: absolute;
	width: calc(100% - 20px);
	height: 2px;
	top:50%;
	transform: translateY(-50%);
	left: 50%;
}
.gray .circle div{
	background:gray;
}
.gray .figure{
	background: gray;
}
.gray .contentCon p{
	color:gray ;
}
.green .circle div{
	background: #409EFF;
}
.green .figure{
	background: #409EFF;
}
.green .contentCon{
	color:#409EFF ;
}
.red .circle div{
	background: #F56C6C;
}
.red .figure{
	background: #F56C6C;
}
.red .contentCon{
	color:#F56C6C ;
}
.green{
	color: #409EFF;
}
.red .data{
	color: #F56C6C;
}
.gray .data{
	color: gray;
}

.reasonLeaveBox .reasonLeaveCon{
	width:100%
}
.reasonLeaveBox .reasonLeaveCon input{
	width: 80%!important;
}

</style>

