<template>
	<div>
        <el-card shadow="">
            <div class="crumbs">
                <el-breadcrumb separator="/">
                    <el-breadcrumb-item>首页</el-breadcrumb-item>
                    <el-breadcrumb-item>勤务管理</el-breadcrumb-item>
                    <el-breadcrumb-item>销假</el-breadcrumb-item>
                </el-breadcrumb>
			</div>
            <div class="titIcon">
                <i class="faSelf sq-icon"></i>
                <p v-show="danhao?true:false">申请单号：{{danhao}}</p>
            </div>
			<div class="ResumpCon" v-show="noLead"></div>
			<div class="processBarLead" v-show="lead"></div>
            <div class="user-info">
                <p></p>
                <p></p>
            </div>
        </el-card>
        <div class="info applicantInfo ResumptionLeave is-always-shadow">
        	<div class="">
				<div slot="header" class="clearfix" style="padding-bottom:10px;border-bottom:1px solid #f0f0f0">
					<span>申请信息</span>
				</div>
        		<el-row style="margin-top:10px">
                    <el-form :inline="true" label-position="left" label-width="" ref='formInline' :model="formInline" class="demo-form-inline fuzzinfoCon">
                        <el-row :gutter="6" style="padding-left:40px">
                            <el-col :span="8"><el-form-item class="" label="姓名："><span>{{formInline.name}}</span></el-form-item></el-col>
                            <el-col :span="8"><el-form-item class="" label="警号："><span>{{formInline.jinghao}}</span></el-form-item></el-col>
                            <el-col :span="8"><el-form-item class="" label="工作单位："><span>{{formInline.danwei}}</span></el-form-item></el-col>
                            <el-col :span="8"><el-form-item class="" label="部门："><span>{{formInline.bumen}}</span></el-form-item></el-col>
                            <el-col :span="8">
                                <el-form-item class="" prop="beginTime" label="开始时间：" :rules="[{ required: true, message: '开始时间不能为空'}]">
									<!-- <el-date-picker class="" v-model="formInline.beginTime" :picker-options="pickerOptions0" style="width: 76%;" format='yyyy-MM-dd' value-format="yyyy-MM-dd" type="date"></el-date-picker> -->
									<el-date-picker class="" v-model="formInline.beginTime" style="width: 76%;" format='yyyy-MM-dd' value-format="yyyy-MM-dd" type="date"></el-date-picker>
                                </el-form-item>
                            </el-col>
                            <el-col :span="8">
                                <el-form-item class="" prop="endTime" label="结束时间：" :rules="[{ required: true, message: '结束时间不能为空'}]">
									<!-- <el-date-picker class="" v-model="formInline.endTime" :picker-options="pickerOptions1" style="width: 76%;" format='yyyy-MM-dd' value-format="yyyy-MM-dd" type="date"></el-date-picker> -->
									<el-date-picker class="" v-model="formInline.endTime"  style="width: 76%;" format='yyyy-MM-dd' value-format="yyyy-MM-dd" type="date"></el-date-picker>
                                </el-form-item>
                            </el-col>
                            <el-col :span="8">
								 <!-- :rules="[{ required: true, message: '请假天数不能为空'},{ type: 'number', message: '请假天数必须为数字值'},{ type: 'number',validator: checkNumber}]" -->
                                <el-form-item class="" prop="leaveDay" label="请假天数：" :rules="[{ required: true, message: '休假天数不能为空' },{ type: 'number', message: '休假天数必须为数字值'}]">
                                    <el-input type="number" style="display: block" v-model.number="formInline.leaveDay"  autocomplete="off"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="8"><el-form-item class="" label="是否离宁："><span>{{formInline.lining}}</span></el-form-item></el-col>
                            <el-col :span="8"><el-form-item class="" label="休假类型："><span>{{formInline.applyType}}</span></el-form-item></el-col>
                            <el-col :span="8">
                                <el-form-item class="" label="请假事由："><span>{{formInline.shiyou}}</span> </el-form-item>
                            </el-col>
                            <el-col :span="8">
                                <el-form-item class="" label="备注：">
                                    <span>{{formInline.remarks}}</span>
                                </el-form-item>
                            </el-col>
                            <el-col :span="8" v-if="jiaotong"><el-form-item class="" label="交通工具："><span>{{formInline.vehicle}}</span></el-form-item></el-col>
                            <el-col :span="8" v-if="chanjialeixing"><el-form-item class="" label="产假类型："><span>{{formInline.maternityLeaveType}}</span></el-form-item></el-col>
                            <el-col :span="8" v-if="wanyu"><el-form-item class="" label="是否晚育："><span>{{formInline.isLateChildbirth}}</span></el-form-item></el-col>
                            <el-col :span="8" v-if="taishu"><el-form-item class="" label="胎数："><span>{{formInline.births}}</span></el-form-item></el-col>
                            <el-col :span="8" v-if="qinshuguanxi"><el-form-item class="" label="亲属关系："><span>{{formInline.kinship}}</span></el-form-item></el-col>
                            <el-col :span="8" v-if="waichudidian"><el-form-item class="" label="外出地点："><span>{{formInline.address}}</span></el-form-item></el-col>
                            <el-col :span="8" v-if="gongzuonianxian"><el-form-item class="" label="工作年限："><span>{{formInline.workingYears}}</span></el-form-item></el-col>
                            <el-col :span="24" class="fujian" v-if="fujian">
                                <el-form-item label="请假审批表：" prop="attOption">
                                    <div v-for="(item,index) in wordPathUrl " class="el-upload__text downLoad" @click="downWorld(item)">
                                        <span class="icon-world"></span>
                                        <span ref="tipModel">离宁外出请假审批表{{index+1}}</span>
                                    </div>
                                    <div class="exampleIm"></div>
                                    <div ref="redTips" class="el-upload__tip"></div>
                                </el-form-item>
                            </el-col>
                            <el-col :span="24" v-show="showAttachment"><el-form-item class="" label="证明材料："><a :href="fileUrl" >{{formInline.upFileName}}</a></u></el-form-item></el-col>

                        </el-row>
                    </el-form>
					<div class="approvalTab">
						<el-table :data="xlTable" height="100%" v-show="approvalTab" :show-header="true" style="width: 100%">
							<el-table-column prop="userName" label="姓名"></el-table-column>
							<el-table-column prop="userNo" label="称谓"></el-table-column>
							<el-table-column prop="workUnit" label="单位"></el-table-column>
							<el-table-column prop="department" label="职务"></el-table-column>
						</el-table>
					</div>
				</el-row>
				<div class="btn-group leaveApprovalBtn" v-show="now2">
					<el-button type="primary" @click="cancelBtn">关闭</el-button>
				</div>
				<div class="btn-group" v-show="now1">
					<el-button @click="cancelBtn">返回</el-button>
					<el-button type="primary" class="submit" @click="submitBtn">销假</el-button>
				</div>
        	</div>
		</div>
        <el-dialog class="rejectProp" title="驳回" :visible.sync="reject" width="30%">
		  	<el-form ref="rejectForm" :rules="rejectRules" :model="rejectForm" label-width="100px" class="demo-ruleForm">
                <el-form-item label="审批意见" class="rejectLabel">
    				<el-input type="textarea" v-model="rejectForm.rejectCon"></el-input>
  				</el-form-item>
			 	<el-form-item>
	                <el-button @click="cancel">取 消</el-button>
	                <el-button type="primary" @click="">确 定</el-button>
	            </el-form-item>
            </el-form>
		</el-dialog>
	</div>
</template>
<script>
    import {communication} from '../../data/getAxios';
	export default {
	    data() {
	        return {
				noLead:false,
				lead:false,
				now1: true,//按钮切换
            	now2: false,
				approvalTab:false,
	        	valueBegin: '',
        		valueEnd:'',
        		danhao:'',
				stepType:3,
				fujian:false,
				fujian2:false,
	        	jiaotong:false,
	        	chanjialeixing:false,
	        	wanyu:false,
	        	taishu:false,
	        	qinshuguanxi:false,
	        	waichudidian:false,
	        	gongzuonianxian:false,
        		formInline:{
        			userName:'',
        			userNo:'',
        			workUnits:'',
        			userDepartment:'',
        			beginTime:'',
        			endTime:'',
        			leaveDay:'',
        			isLeaveNJ:'',
        			applyType:'',
					leaveCause:'',
					vehicle:'',
					enclosure:'',
					maternityLeaveType:'',
					isLateChildbirth:'',
					kinship:'',
					address:'',
					workingYears:'',
					xlTable:'',
					isTogether:'',
                    remarks:'',
                    upFileName:''

				},
                showAttachment:false,
                fileUrl:'',
                fujian:false,
				fujian2:false,
				enclosure:false,
				maternityLeaveType:false,
				isLateChildbirth:false,
				births:false,
				kinship:false,
				address:false,
				workingYears:false,
				vehicle:false,
				xlTable:[],
        		reject:false,
        		rejectForm:{
	                rejectCon:''
	            },
        		rejectRules:{
	            	rejectCon:[
	            		{ required: true, message: '请输入审批意见', trigger: 'blur' },
	            	]
	           },
				wordPathUrl:[]
	        }
	    },
	    created() {
	  		this.init();
	  		// this.ready()
	    },
        methods:{
			// pickerOptions0: {//开始时间
            //     disabledDate: time => {
            //         let endDateVal = this.formInline.endTime;
            //         if (endDateVal) {//不能早于当前日期
            //             return (time.getTime() > new Date(endDateVal).getTime()) || (time.getTime()  < Date.now() - 8.64e7);
            //         }else{
            //             return time.getTime()  < Date.now() - 8.64e7;
            //         }
            //     }
            // },
            // pickerOptions1: {//结束时间
            //     disabledDate: time => {
            //         let beginDateVal = this.formInline.beginTime;
            //         if (beginDateVal) {
            //             return (
            //                 time.getTime() < new Date(beginDateVal).getTime() - 1 * 24 * 60 * 60 * 1000
            //             );
            //         }else{
            //             return time.getTime()  < Date.now() - 8.64e7;
            //         }
            //     }
            // },
            ready(){
                var self = this;
                const _obj={
                    url:self.global.serverPath8+'/flow/process/getProcessBar?workId='+self.workId,
                    isGet:true
                };
                communication(_obj).then((res) => {
                    console.log(res);
                    if(res.code == 0){
                        var index = -1;
                        var stepArr = [];
                        if(res.data.processNo == '011'){
                            self.lead = true;
                            var msg = res.data.hashMap;
                            for (var val in msg) {
                                stepArr.push(msg[val])
                            };
                            for(var i=0;i<stepArr.length;i++){
                                $(".processBarLead").append('<div class="aloneF '+ (stepArr[i].applyStatus == 0?"gray":stepArr[i].applyStatus ==1?"green":stepArr[i].applyStatus == 2?"red":"gray")+'"><div class="alone"><div class="xian"></div><div class="dept"><span class="yuan"></span><span class="deptName">'+ stepArr[i].applyName +'</span></div><div class="leadName">'+ (stepArr[i].applyUser || '') +'</div><div class="leadDate">'+ (stepArr[i].endTime || '') +'</div></div></div>')
                            };
                            var processBarLeadLiWidth = $(".processBarLead").width()/(stepArr.length-1);
                            $(".processBarLead>div").width(processBarLeadLiWidth);
                            for(var k=0;k<stepArr.length;k++){
                                if(k == 5){
                                    $(".processBarLead>div").eq(k).css({'left':processBarLeadLiWidth*2,'top':'65px','width':processBarLeadLiWidth*3})
                                }else if(k > 5){
                                    $(".processBarLead>div").eq(k).css({'left':processBarLeadLiWidth*(k-1)})
                                }else{
                                    $(".processBarLead>div").eq(k).css({'left':processBarLeadLiWidth*k})
                                };
                            };
                        }else{
                            self.noLead = true;
                            var msg = res.data.hashMap;
                            var optionCon = '';
                            for (var val in msg) {
                                stepArr.push(msg[val])
                            };
                            for(var i=0;i<stepArr.length;i++){ //applyStatus=0 不亮/灰色    applyStatus=1 亮/蓝色   applyStatus=2 亮/红色
                                $(".ResumpCon").append('<div class="alone '+ (stepArr[i].applyStatus == 0?"gray":stepArr[i].applyStatus ==1?"green":stepArr[i].applyStatus == 2?"red":"gray") +'"><div class="circleDot finishType"><div class="circle"><div></div>'+ (stepArr[i].applyName ||'') +'</div></div><div class="figure"></div><div class="contentCon"><p>'+ (stepArr[i].applyUser ||'')  +'</p><p>'+  (stepArr[i].endTime ||'') +'</p> </div></div>')
                            };
                            for(var r=0;r<$(".data").length;r++){
                                $(".data").eq(r).css({'height':$(".daughter").eq(r).height()+1+'px','line-height':$(".daughter").eq(r).height()+'px','width':$(".data").eq(r).width()+15});
                                $(".roundDot").eq(r).css({'height':$(".daughter").eq(r).height()+1+'px'});
                            };
                            if(stepArr[stepArr.length-1].status==1){
                                self.pingfen=true;
                            }
                        }
                    }else if(res.code!=1){
                        self.$message.error(res.message)
                    }
                }, function(res) {
                    self.$message.error('请求失败')
                });
            },
        	// ready(){
			// 	var self = this;
			// 	if(self.$route.query.show1){
			// 		self.now1 = false;
			// 		self.now2 = true;
			// 	};
			// 	self.$http.get(self.global.serverPath4+ '/process_engine/PleaseSellOff/getAppProcessBar', {//传递参数
			// 		params: {
			// 			"applyFormId":self.$route.query.alarmNo,
			// 			// "processName":"请销假",
			// 		}
			// 	}).then(function(res) {
			// 		var stepArr = [];
			// 		var msg = res.body;
			// 		for (var val in msg) {
			// 			stepArr.push(msg[val])
			// 		};
			// 		console.log(stepArr);
			// 		for(var i=0;i<stepArr.length;i++){
			// 			$(".ResumpCon").append('<div class="alone '+ (stepArr[i].applyUser?"green":"gray") +'"><div class="circleDot finishType"><div class="circle"><div></div></div></div><div class="figure"></div><div class="contentCon"><p class="">'+ (stepArr[i].applyUser?stepArr[i].applyUser:"") +'</p><p>'+ (stepArr[i].endTime?stepArr[i].endTime:"")  +'</p><p>'+  (stepArr[i].applyNAME?stepArr[i].applyNAME:"")  +'</p> </div></div>')
			// 		};
			// 		for(var r=0;r<$(".data").length;r++){
			// 			$(".data").eq(r).css({'height':$(".daughter").eq(r).height()+1+'px','line-height':$(".daughter").eq(r).height()+'px','width':$(".data").eq(r).width()+15});
			// 			$(".roundDot").eq(r).css({'height':$(".daughter").eq(r).height()+1+'px'});
			// 		};
			// 		// }else{
			// 		// 	self.$message.error(res.body.message)
			// 		// }
			// 	}, function(res) {
			// 		self.$message.error('请求失败')
			// 	});
			// },
        	cancel(){
        		this.rejectRules = false
			},
			checkNumber(value){
        		if (value && value% (0.5) != 0) {
          			return false;
        		}else{
          			return true;
        		}
    		},
        	cancelBtn(){
        		this.$router.push('/approveManage')
        	},
			//下载‘提交’后的申请表
	        downWorld(item){
				var $eleForm = $("<form method='get'></form>");
	            $eleForm.attr("action", item);	//待修改0
	            $(document.body).append($eleForm);
	            $eleForm.submit();
			},
			downWorld2(){
				var $eleForm = $("<form method='get'></form>");
	            $eleForm.attr("action", self.global.serverPath8+"/download/applyDir/"+ this.$route.query.alarmNo +"/qxj.docx");	//待修改0
	            $(document.body).append($eleForm);
	            $eleForm.submit();
	        },
            init(){
                var self = this;
                const _obj={
                    url:self.global.serverPath8+'/flow/query/formInformation?type=qxjInfo&id='+self.$route.query.alarmNo,
                    isGet:true
                };
                communication(_obj).then((res) => {
                    if(res.code==0){
                        self.workId=res.data.pleSeOffForm.workId
                        self.ready()
                        self.applicantPeople=res.data.pleSeOffForm.applicantPeople
                        if(res.data.pleSeOffForm.vehicle!=null && res.data.pleSeOffForm.vehicle!=''){
                            self.jiaotong= true;
                        };
                        if(res.data.pleSeOffForm.maternityLeaveType!=null){
                            self.chanjialeixing= true;
                        };
                        if(res.data.pleSeOffForm.applyType=="产假"){
                            self.wanyu= true;
                        };
                        if(res.data.pleSeOffForm.kinship!=null){
                            self.qinshuguanxi= true;
                        };
                        if(res.data.pleSeOffForm.workingYears!=null){
                            self.gongzuonianxian= true;
                        };
                        if(res.data.pleSeOffForm.enclosure != ''&&res.data.pleSeOffForm.enclosure != 'undefined'&&res.data.pleSeOffForm.enclosure != null){
                            self.fujian2= true;
                        };
                        if(res.data.pleSeOffForm.isLeaveNj == '是'){
                            self.waichudidian = true;
                        }
                        if(res.data.pleSeOffForm.applyType=="产假"){
                            self.taishu= true;
                        };
                        if(res.data.wordPath != null){
                            self.fujian = true;
                            var downPathArr = res.data.wordPath.split(",");
                            downPathArr.forEach((item,index)=>{
                                var downPath=item.replace("/home/apache-tomcat-8.5.40/webapps","")
                                self.wordPathUrl.push(this.global.serverPath6+ downPath);
                            })
                        }
                        // if(res.data.wordPath != null){
                        //     self.fujian = true;
                        //     var downPath = res.data.wordPath.replace("/home/apache-tomcat-8.5.40/webapps","");
                        //     self.wordPathUrl ='http://10.33.66.86:9999'+ downPath;
                        //     // self.wordPathUrl ='http://10.33.66.86:9999/download/applyDir/chuguojing1842135303403990110.pdf';
                        // };

                        if(res.data.pleSeOffForm.upFilePath!=null && res.data.pleSeOffForm.upFilePath!=''){
                            self.showAttachment=true;
                        }
                        // self.fujian2Url = res.body.row[0].enclosure;
                        self.formInline={
                            chanjialeixing:res.data.pleSeOffForm.maternityLeaveType,
                            name : res.data.pleSeOffForm.applicantName,
                            jinghao : res.data.pleSeOffForm.policeNo,
                            danwei : res.data.pleSeOffForm.companyName,
                            bumen : res.data.pleSeOffForm.deptName,
                            beginTime : res.data.pleSeOffForm.beginTime,
                            endTime : res.data.pleSeOffForm.endTime,
                            leaveDay : Number(res.data.pleSeOffForm.leaveDay),
                            lining : res.data.pleSeOffForm.isLeaveNj,
                            zhuti : res.data.pleSeOffForm.leaveSubject,
                            applyType:res.data.pleSeOffForm.applyType,
                            shiyou:res.data.pleSeOffForm.leaveCause,
                            vehicle:res.data.pleSeOffForm.vehicle,
                            births:res.data.pleSeOffForm.births,
                            address:res.data.pleSeOffForm.address,
                            remarks:res.data.pleSeOffForm.remarks,
                            maternityLeaveType:res.data.pleSeOffForm.maternityLeaveType,
                            isLateChildbirth:res.data.pleSeOffForm.isLateChildbirth,
                            kinship:res.data.pleSeOffForm.kinship,
                            workingYears:res.data.pleSeOffForm.workingYears,
                            upFileName:res.data.pleSeOffForm.upFileName,
                            upFilePath:res.data.pleSeOffForm.upFilePath,
                        };
                        self.fileUrl=self.global.serverPath10+'/process/download?fileName='+self.formInline.upFileName+'&filePath='+encodeURI(self.formInline.upFilePath);
                    }else if(res.code!=1){
                        self.$message.warning(res.message)
                    }
                }).catch((err) => {
                    console.log('获取信息失败！')
                })
// 				self.$http.get(self.global.serverPath4 + '/process_engine/PleaseSellOff/approvalList', {//传递参数
// 					params: {
// 						"applyFormId":self.$route.query.alarmNo,
// 					}
// 				}).then(function(res){
// 					self.fujian= true;//待完善功能
// 					if(res.body.row[0].vehicle!=null){
// 						self.jiaotong= true;
// 					};
// 					if(res.body.row[0].maternityLeaveType!=null){
// 						self.chanjialeixing= true;
// 					};
// 					if(res.body.row[0].isLateChildbirth!=null){
// 						self.wanyu= true;
// 					};
// 					if(res.body.row[0].kinship!=null){
// 						self.qinshuguanxi= true;
// 					};
// 					if(res.body.row[0].workingYears!=null){
// 						self.gongzuonianxian= true;
// 					};
// 					if(res.body.row[0].enclosure != ''&&res.body.row[0].enclosure != 'undefined'&&res.body.row[0].enclosure != null){
// 						self.fujian2= true;
// 					};
// 					if(res.body.row[0].isLeaveNJ == '是'){
// 						self.waichudidian = true;
// 					}
// 					// if(res.body.row[0].births!=null){
// 					// 	self.taishu= true;
// 					// };
// 					self.fujian2Url = res.body.row[0].enclosure;
// 					self.formInline={
// 						chanjialeixing:res.body.row[0].maternityLeaveType,
// 						jiaotong:res.body.row[0].vehicle,
// 						name : res.body.row[0].applicantUserInfo[0].userName,
// 						jinghao : res.body.row[0].applicantUserInfo[0].userNo,
// 						danwei : res.body.row[0].applicantUserInfo[0].workUnits,
// 						bumen : res.body.row[0].applicantUserInfo[0].userDepartment,
// 						beginTime : res.body.row[0].beginTime,
// 						endTime : res.body.row[0].endTime,
// 						tianshu : res.body.row[0].leaveDay,
// 						lining : res.body.row[0].isLeaveNJ,
// 						zhuti : res.body.row[0].leaveSubject,
// 						shiyou:res.body.row[0].applyType,
// 						yuanyin:res.body.row[0].leaveCause,
// 						vehicle:res.body.row[0].vehicle,
// 						births:res.body.row[0].births,
// 						address:res.body.row[0].address,
// 						maternityLeaveType:res.body.row[0].maternityLeaveType,
// 						isLateChildbirth:res.body.row[0].isLateChildbirth,
// 						kinship:res.body.row[0].kinship,
// 						workingYears:res.body.row[0].workingYears,
// 					};
// 					if(res.body.togetherList != null){
// 						self.approvalTab = true
// 					}
// 					self.xlTable = res.body.togetherList
// 					self.danhao=res.body.row[0].applyFormId;
// //					self.stepType = res.body.row[0].applyType;
// 				},function(res) {
// 					self.$message.error('请求失败！')
// 				});
            },
	    	submitBtn(){
				var self = this;
        		if(self.checkNumber(self.formInline.leaveDay)){
                const _obj={
                    url:self.global.serverPath8+'/flow/process/agreeForm',
                    postData:{
                        "id":self.$route.query.alarmNo,
                        "workId":self.workId,
                        "type":'qxj',
                        "fid":self.$route.query.fid,
                        updatePleOffForm:{
                            'startTime':self.formInline.beginTime,                //开始时间
                            'endTime':self.formInline.endTime,                    //结束时间
                            'days':self.formInline.leaveDay,  //出国目的地
                        },
                    },
                    isGet:false
                };
                communication(_obj).then((res) => {
                    console.log(res);
                    if(res.code==-1){
                        self.$message.error("请配置下一步审批人");
                    }else if(res.code == 0){
                        self.$message.success('销假成功');
                        self.xzlogBox = false;
                        this.$router.push('/approveManage');
                    }else if(res.code!=1){
                        self.$message.error(res.message)
                    }
                }, function(res) {
                    self.$message.error('请求失败')
				});
				}else{
          			self.$message.error("休假天数必须是0.5的倍数");
        		}
	    	},
		}
		
	}
</script>
<style scoped>
.el-table td, .el-table th{
	text-align: center;
}
.downLoad span{
	color:#409EFF
}
.downLoad span:hover{
	cursor: pointer
}
</style>
<style>
	.applicantInfo{
	    overflow: hidden;
		background: #F0F0F0;
	}
	.info{
		margin: 20px;
    	background: white;
		padding: 10px 20px;
	}
	.approvalTab{
		padding-top: 20px
	}
	.submit{
		color: white;
	}
	.rejectProp .el-form-item__content{
		margin-left:75px!important;
		text-align: right;
	}
	.rejectBtn{
		padding: 9px 15px;
	    border: 1px solid #DCDFE6;
	    color: #606266;
	}
	.rejectBtn:hover{
		border-color: rgb(32, 160, 255)!important;
		color: rgb(32, 160, 255)!important;
	}
	.rejectProp .el-form-item__label{
		width: 75px!important;
	}
	.el-textarea__inner{
		height: 80px;
	}
	.applicantInfo .grid-content{
		padding: 15px;
	}
	.applyNum{
		padding:10px 15px;
		background: white;
	}
	.apply{
		float:left
	}
	.step{
		width: 90%;
		margin: 0 auto;
	}
	.stepCon .el-step__icon-inner[class*=el-icon]:not(.is-status){
		font-size: 16px!important;
	}
	.applyNum .apply {
	    /*background: url(../../assets/leave/esInfor.png) no-repeat;*/
	    background-position: 10px;
	    height: 30px;
	    line-height: 30px;
	    padding-left: 45px;
	}
	.gridright{
		float:right;
		text-align: right;
	}
	.stepCon {
	    width: 100%;
	    overflow: hidden;
	    background: white;
	    margin: 0 auto;
	}
	.reasonLeaveBox .formSelect {
		width: 80%;
	}
	.reasonLeaveBox .el-form-item__content {
	    width: calc(100% - 130px)!important;
	}
	.fujian .el-form-item__label{
		width: 140px!important;
	}
.ResumpCon{display: flex;flex-direction:row;width: 90%;margin:0 auto}
.ResumpCon>div{flex: 1;position: relative;height: 130px;overflow: hidden;}
.ResumpCon .multiple{display: flex;flex-direction: column;}
.ResumpCon .daughter{
	flex: 1;
	position: relative;
}
.ResumpCon .circleDot ,.roundDot{
	position: absolute;
	height: 10px;
	background: white;
	z-index: 10;
}
.ResumpCon .circle{
	position: relative;
	width: 100%;
	height: 100%
}
.ResumpCon .circle div{
	position: absolute;
	width: 10px;
	height: 10px;
	left: 50%;
	top:50%;
	transform: translate(-50%,-50%);
	background: #eee;
	border-radius: 5px;
}
.ResumpCon .circleDot{
	left:50%;
	transform: translate(-50%,-50%);
	top:50%;
}
.ResumpCon .roundDot{
	top:50%;
	left:5%;
	transform: translate(-50%,-50%);
}
.ResumpCon .data{
	position: absolute;
	top:50%;
	left: 10%;
	transform: translateY(-50%);
	font-size: 12px;
	min-width:180px ;
	background: white;
}
.ResumpCon .name{
	position: absolute;
	top:50%;
	right: 10%;
	transform: translateY(-50%);
	font-size: 12px;
}
.ResumpCon .finishColor{
	background: #3385ff;
}
.ResumpCon .procedure{
	background: #eee;;
}
.ResumpCon .contentCon{
	width: 100%;
	position: absolute;
	left:50%;
	top:105px;
	transform: translate(-50%,-50%);
	text-align: center;
}
.ResumpCon .alone p{
	font-size: 12px;
	line-height: 20px;
	font-weight:700
}
.ResumpCon>div .figure{
	width: 100%;
	height: 2px;
	position: absolute;
	top:50%;
	transform: translateY(-50%);
}
.ResumpCon>div:first-child .figure{
	width: calc(50% - 20px);
	height: 2px;
	position: absolute;
	top:50%;
	transform: translateY(-50%);
	right: 0;
}
.ResumpCon>div:last-child .figure{
	width: calc(50% - 20px);
	height: 2px;
	position: absolute;
	top:50%;
	transform: translateY(-50%);
	left: 0;
}
.ResumpCon .multiple .figure{
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
.red{
	color: #F56C6C;
}
.gray{
	color: gray;
}
.processBarLead .green{
    color: #409EFF
}
.processBarLead .green .xian{
    background: #409EFF
}
.processBarLead .green .yuan{
    background: #409EFF
}
.processBarLead .red{
    color: red
}
.processBarLead .red .xian{
    background:  red
}
.processBarLead .red .yuan{
    background:  red
}
.ResumptionLeave .btn-group{
	margin-top: 20px
}
.ResumpCon .circle {
    position: relative;
    width: 100%;
    height: 100%;
    padding-left: 20px;
    padding-right: 20px;
    font-size: 12px;
    white-space: nowrap;
}
.ResumpCon .circleDot {
    left: 50%;
    -webkit-transform: translate(-50%,-77%);
    -ms-transform: translate(-50%,-77%);
    transform: translate(-50%,-77%);
    top: 50%;
}
.ResumpCon .circle>div {
	position: absolute;
	width: 10px;
	height: 10px;
	left: 5px;
	top: 50%;
	-webkit-transform: translateY(-50%);
	-ms-transform: translateY(-22%);
	transform: translateY(-22%);
	/*background: #eee;*/
	border-radius: 5px;
}
.processBarLead{
	display: flex;
	flex-direction:row;
	width: 90%;
	margin:0 auto;
	height: 130px;
	position: relative;
	text-align: center;
	font-size: 12px
}
.processBarLead>div{
	position: absolute;
	height: 65px;
}
.processBarLead .alone{
	position: relative;
	height: 100%;
	width: 100%;
	margin-left: 10px
}
.processBarLead>div:nth-of-type(1){
	height: 130px;
}
.processBarLead>div:nth-of-type(2){
	height: 130px;
}
.processBarLead .circle{
	position: absolute;
	top: 50%;
	left: 50%;
	transform: translate(-50%,-50%);
	height: 14px;
}
.processBarLead>div:nth-of-type(7){
	height: 130px;
}
.processBarLead>div:nth-of-type(8){
	height: 130px;
}
.processBarLead .dept{
	height: 14px;
	position: absolute;
	left:0;
	top: 50%;
	transform: translateY(-50%);
}
.processBarLead .leadName{
    position: absolute;
    left: 0;
    top: 65%;
    padding-left: 20px
}
.processBarLead .leadDate{
    position: absolute;
    left: 0;
    top: 84%;
    padding-left: 10px
}
.processBarLead .deptName{
	text-align: center;
	background: white;
	z-index: 10;
	padding: 0 10px
}
.processBarLead .yuan{
	height: 10px;
	width:10px;
	border-radius: 5px;
	background: gray;
	display: inline-block;
}
.processBarLead .xian{
	height:1px;
	background: gray;
	position: absolute;
	top: 50%;
	transform: translateY(-50%);
	width: 100%
}
.aloneF:last-child .xian{
	display: none
} 
.processBarLead .green{
    color: #409EFF
}
.processBarLead .green .xian{
    background: #409EFF
}
.processBarLead .green .yuan{
    background: #409EFF
}
.processBarLead .red{
    color: red
}
.processBarLead .red .xian{
    background:  red
}
.processBarLead .red .yuan{
    background:  red
}.aloneF:last-child .xian{
	display: none
} 
</style>
