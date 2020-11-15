<template>
    <div class="wdda">
        <div class="wdda-option">
            <el-row :gutter="20">
                <!--占据整行  -->
                <el-col :span="24">
                    <el-row>
                        <el-col>
                            <el-card shadow="">
                                <div class="crumbs">
                                    <el-breadcrumb separator="/">
                                        <el-breadcrumb-item>个人中心</el-breadcrumb-item>
                                        <el-breadcrumb-item>我的档案</el-breadcrumb-item>
                                    </el-breadcrumb>
                                </div>
                            </el-card>
                            <el-card class="card2 newPage mgb20">
                                <div class="titleTop" :style="{'background':'url('+require('static/img/leave/files.png')+') no-repeat center','background-size':'100% 100%'}">
                                    <div class="circleImg is-always-shadow" v-show="false" >
                                	    <img :src="imgIp" width="86px" class="policePhoto" />
                                	    <!--<img :src="require('static/img/leave/3.jpg')" width="86px" class="policePhoto" />-->
                                    </div>
                                </div>
                                <div class="wdda-info" >
                                    <p class="titleFont">基本信息</p>
                                    <div class="wdda-box" style="padding:10px 2px 0 36px">
                                        <el-form :inline="true" label-position="left" label-width="" ref='wddaForm' :model="wddaForm" class="demo-form-inline">
                                            <el-row :gutter="6"style="margin-bottom:8px">
                                                <el-col :span="6">
                                                    <el-form-item label="姓名：" prop="jyxm">
                                                        <el-input v-model="wddaForm.jyxm" readonly="" placeholder="暂无数据"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="6" >
                                                    <el-form-item label="工作单位：" prop="workPlace">
                                                        <span class="gzdwSpan" v-show="wddaForm.workPlace?true:false">{{wddaForm.workPlace}}</span>
                                                        <span class="gzdwSpan2" v-show="wddaForm.workPlace?false:true">暂无数据</span>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="6">
                                                    <el-form-item label="民族：" prop="nation">
                                                        <el-input v-model="wddaForm.nation" readonly="" placeholder="暂无数据"></el-input>
                                                    </el-form-item>
                                                    <el-form-item label="personId" prop="personId" v-show="false">
                                                        <el-input v-model="wddaForm.personId" readonly="" placeholder="暂无数据"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="6">
                                                    <el-form-item label="出生日期：" prop="birthday">
                                                        <el-input v-model="wddaForm.birthday" readonly="" placeholder="暂无数据"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="6">
                                                    <el-form-item label="籍贯：" prop="nativePlace">
                                                        <el-input v-model="wddaForm.nativePlace" readonly="" placeholder="暂无数据"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="6">
                                                    <el-form-item label="参加工作时间：" prop="workTime">
                                                        <span class="gzdwSpan" v-show="wddaForm.workTime?true:false">{{wddaForm.workTime}}</span>
                                                        <span class="gzdwSpan2" v-show="wddaForm.workTime?false:true">暂无数据</span>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="6">
                                                    <el-form-item label="出生地：" prop="birthPlace">
                                                        <el-input v-model="wddaForm.birthPlace" readonly="" placeholder="暂无数据"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="6">
                                                    <el-form-item label="入党时间：" prop="joinParty">
                                                        <el-input v-model="wddaForm.joinParty" readonly="" placeholder="暂无数据"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="6">
                                                    <el-form-item label="婚姻状况：" prop="maritalStatus">
                                                        <el-input v-model="wddaForm.maritalStatus" readonly="" placeholder="暂无数据"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="6">
                                                    <el-form-item label="血型：" prop="bloodType">
                                                        <el-input v-model="wddaForm.bloodType" readonly="" placeholder="暂无数据"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="6">
                                                    <el-form-item label="性别：" prop="sexual">
                                                        <el-input v-model="wddaForm.sexual" readonly="" placeholder="暂无数据"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <!--<el-col :span="6">
                                                    <el-form-item label="进入来源：" prop="inSource">
                                                        <el-input v-model="wddaForm.inSource" readonly="" placeholder="暂无数据"></el-input>
                                                    </el-form-item>
                                                </el-col>-->
                                                <el-col :span="6">
                                                    <el-form-item label="身份证号：" prop="idNumber">
                                                        <el-input v-model="wddaForm.idNumber" readonly="" placeholder="暂无数据"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="6" >
                                                    <el-form-item label="参加公安工作日期：" prop="policeDate">
                                                        <!--<el-input v-model="wddaForm.policeDate" readonly="" placeholder="暂无数据"></el-input>-->
                                                        <span class="gzdwSpan" v-show="wddaForm.policeDate?true:false">{{wddaForm.policeDate}}</span>
                                                        <span class="gzdwSpan2" v-show="wddaForm.policeDate?false:true">暂无数据</span>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="6">
                                                    <el-form-item label="警号：" prop="policeNumb">
                                                        <el-input v-model="wddaForm.policeNumb" readonly="" placeholder="暂无数据"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="6">
                                                    <el-form-item label="警衔：" prop="policeRank">
                                                        <el-input v-model="wddaForm.policeRank" readonly="" placeholder="暂无数据"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="6" >
                                                    <el-form-item label="是否涉密：" prop="isSecret">
                                                        <el-input v-model="wddaForm.isSecret" readonly="" placeholder="暂无数据"></el-input>
                                                    </el-form-item>
                                                </el-col>
                                                <el-col :span="24">
                                                    <el-form-item label="个人专长：" prop="personLike">
                                                        <span class="gzdwSpan" v-show="wddaForm.personLike?true:false">{{wddaForm.personLike}}</span>
                                                        <span class="gzdwSpan2" v-show="wddaForm.personLike?false:true">暂无数据</span>
                                                    </el-form-item>
                                                </el-col>
                                            </el-row>
                                        </el-form>
                                    </div>
                                </div>
                            </el-card>
                            <!--button组  -->
                            <el-card class="card2" style="background: #fff;">
                                <div class="btn-info btnMall" style="padding-bottom:3px">
                                    <el-row class="mgb0">
                                        <el-checkbox v-model="checked" @change="checkChange()">全选</el-checkbox>
                                        <span style="color:#D8D8D8;padding-right:6px">|</span>
                                        <el-button @click="xlxxClick()">学历信息</el-button>
                                        <el-button @click="zwxxClick()">职务信息</el-button>
                                        <el-button @click="llxxClick()">履历信息</el-button>
                                        <el-button @click="zzmmClick()">政治面貌</el-button>
                                        <el-button @click="ndkhClick()">年度考核</el-button>
                                        <el-button @click="bzjlClick()">表彰奖励</el-button>
                                        <el-button @click="jtcyClick()">家庭成员</el-button>
                                        <el-button @click="txlClick()">通讯录</el-button>
                                        <el-button @click="zyjsClick()">专业技术</el-button>
                                    </el-row>
                                </div>
                            </el-card>
                            <!--基础信息  -->
                            <div class="myFilesList">
                                <el-row>
                                    <el-col :span="24">
                                    <!-- <el-col :span="20">-->
                                        <div class="myFilesListCon">
                                            <!--基础信息-->
                                            <el-card class="card2" style="margin-top:15px;" v-show="!jcxx">
                                                <div slot="header" class="clearfix">
                                                    <span>基础信息</span>
                                                </div>
                                                <div class="wdda-info">
                                                    <div class="wdda-box">
                                                        <el-form :inline="true" label-position="left" label-width="" ref='jcxxForm' :model="jcxxForm" class="demo-form-inline">
                                                            <el-row :gutter="6">
                                                                <el-col :span="8">
                                                                    <el-form-item label="身份证号：" prop="idCard">
                                                                        <el-input v-model="jcxxForm.idCard" readonly="" placeholder="暂无数据"></el-input>
                                                                    </el-form-item>
                                                                </el-col>
                                                                <el-col :span="8">
                                                                    <el-form-item label="警号：" prop="jyNumber">
                                                                        <el-input v-model="jcxxForm.jyNumber" readonly="" placeholder="暂无数据"></el-input>
                                                                    </el-form-item>
                                                                </el-col>
                                                                <el-col :span="8">
                                                                    <el-form-item label="参加公安工作日期：" prop="policeDate">
                                                                        <el-input v-model="jcxxForm.policeDate" readonly="" placeholder="暂无数据"></el-input>
                                                                    </el-form-item>
                                                                </el-col>
                                                            </el-row>
                                                            <el-row :gutter="6">
                                                                <el-col :span="8">
                                                                    <el-form-item label="进入来源：" prop="enterSource">
                                                                        <el-input v-model="jcxxForm.enterSource" readonly="" placeholder="暂无数据"></el-input>
                                                                    </el-form-item>
                                                                </el-col>
                                                                <el-col :span="8">
                                                                    <el-form-item label="进入时间：" prop="enterTime">
                                                                        <el-input v-model="jcxxForm.enterTime" readonly="" placeholder="暂无数据"></el-input>
                                                                    </el-form-item>
                                                                </el-col>
                                                                <el-col :span="8">
                                                                    <el-form-item label="个人身份：" prop="personalStatus">
                                                                        <el-input v-model="jcxxForm.personalStatus" readonly="" placeholder="暂无数据"></el-input>
                                                                    </el-form-item>
                                                                </el-col>
                                                            </el-row>
                                                            <el-row :gutter="6">
                                                                <el-col :span="8">
                                                                    <el-form-item label="是否涉密：" prop="isConfiden">
                                                                        <el-input v-model="jcxxForm.isConfiden" readonly="" placeholder="暂无数据"></el-input>
                                                                    </el-form-item>
                                                                </el-col>
                                                            </el-row>
                                                            <el-row :gutter="6">
                                                                <el-col :span="24">
                                                                    <el-form-item label="个人专长：" prop="PersonExpert">
                                                                        <el-input v-model="jcxxForm.PersonExpert" readonly="" placeholder="暂无数据"></el-input>
                                                                    </el-form-item>
                                                                </el-col>
                                                            </el-row>
                                                        </el-form>
                                                    </div>
                                                </div>
                                            </el-card>
                                            <!--学历信息  -->
                                            <el-card class="card2" style="margin-top:15px;" v-show="!xlxx">
                                                <div slot="header" class="clearfix">
                                                    <span>学历信息</span>
                                                </div>
                                                <div class="wdda-info">
                                                    <img v-show="xlxxTable.length>0?false:true" :src="require('static/img/manage/noData.png')" width="128px" class="noDataImg" />
                                                    <div class="wdda-table" v-show="xlxxTable.length>0?true:false">
                                                        <el-table :data="xlxxTable" style="width: 100%">
                                                            <el-table-column type="index" :index="xlxxindexMethod" label="序号" width="55" align="center">
                                                            </el-table-column>
                                                            <el-table-column prop="educationCategory" label="教育类别" width="100">
                                                            </el-table-column>
                                                            <el-table-column prop="education" label="学历名称">
                                                            </el-table-column>
                                                            <el-table-column prop="admissionTime" label="入学时间">
                                                            </el-table-column>
                                                            <el-table-column prop="graduationTime" label="毕业日期">
                                                            </el-table-column>
                                                            <el-table-column prop="professionalTitle" label="所学专业名称">
                                                            </el-table-column>
                                                            <el-table-column prop="schoolName" label="学校名称">
                                                            </el-table-column>
                                                            <el-table-column prop="policeSchool" label="政法院校">
                                                            </el-table-column>
                                                            <el-table-column prop="bachelorScience" label="学位名称">
                                                            </el-table-column>
                                                        </el-table>
                                                        <div class="pagination" style="margin:10px 0 0 0" v-show="xlxxTableLeg.length>5?true:false">
                                                            <el-pagination @current-change="xlxxhandleCurrentChange" :current-page="xlxx_cur_page" :page-size="xlxx_pagesize" :page-sizes="[5]" layout="total, sizes, prev, pager, next, jumper" :total="xlxxtableData.length">
                                                            </el-pagination>
                                                        </div>
                                                    </div>
                                                </div>
                                            </el-card>
                                            <!--职务信息  -->
                                            <el-card class="card2" style="margin-top:15px;" v-show="!zwxx">
                                                <div slot="header" class="clearfix">
                                                    <span>职务信息</span>
                                                </div>
                                                <div class="wdda-info">
                                                    <img v-show="zwxxTable1.length>0?false:true" :src="require('static/img/manage/noData.png')" width="128px" class="noDataImg" />
                                                    <div class="wdda-table" v-show="zwxxTable1.length>0?true:false">
                                                        <el-table :data="zwxxTable1" style="width: 100%">
                                                            <el-table-column type="index" label="序号" :index="zwxxindexMethod"  width="55" align="center">
                                                            </el-table-column>
                                                            <el-table-column prop="jobName" label="职务名称">
                                                            </el-table-column>
                                                            <el-table-column prop="rank" label="职级">
                                                            </el-table-column>
                                                            <el-table-column prop="offciceTime" label="任职时间">
                                                            </el-table-column>
                                                        </el-table>
                                                        <div class="pagination" style="margin:10px 0 0 0" v-show="zwxxTableLeg.length>5?true:false">
                                                            <el-pagination @current-change="zwxxhandleCurrentChange" :current-page="zwxx_cur_page" :page-size="zwxx_pagesize" :page-sizes="[5]" layout="total, sizes, prev, pager, next, jumper" :total="zwxxtableData.length">
                                                            </el-pagination>
                                                        </div>
                                                    </div>
                                                </div>
                                            </el-card>
                                            <!--履历信息  -->
                                            <el-card class="card2" style="margin-top:15px;" v-show="!llxx">
                                                <div slot="header" class="clearfix">
                                                    <span>履历信息</span>
                                                </div>
                                                <div class="wdda-info">
                                                    <img v-show="llxxTable.length>0?false:true" :src="require('static/img/manage/noData.png')" width="128px" class="noDataImg" />
                                                    <div class="wdda-table" v-show="llxxTable.length>0?true:false">
                                                        <el-table :data="llxxTable" style="width: 100%">
                                                            <el-table-column type="index" label="序号" :index="llxxindexMethod" width="55" align="center">
                                                            </el-table-column>
                                                            <el-table-column prop="beginTime" label="起始日期" width="160">
                                                            </el-table-column>
                                                            <el-table-column prop="endTime" label="截止日期">
                                                            </el-table-column>
                                                            <el-table-column prop="workUnit" label="所在单位">
                                                            </el-table-column>
                                                            <el-table-column prop="duty" label="身份或职务">
                                                            </el-table-column>
                                                        </el-table>
                                                        <div class="pagination" style="margin:10px 0 0 0" v-show="llxxTableLeg.length>5?true:false">
                                                            <el-pagination @current-change="llxxhandleCurrentChange" :current-page="llxx_cur_page" :page-size="llxx_pagesize" :page-sizes="[5]" layout="total, sizes, prev, pager, next, jumper" :total="llxxtableData.length">
                                                            </el-pagination>
                                                        </div>
                                                    </div>
                                                </div>
                                            </el-card>
                                            <!--政治面貌  -->
                                            <el-card class="card2" style="margin-top:15px;" v-show="!zzmm">
                                                <div slot="header" class="clearfix">
                                                    <span>政治面貌信息</span>
                                                </div>
                                                <div class="wdda-info">
                                                    <img v-show="zzmmTable.length>0?false:true" :src="require('static/img/manage/noData.png')" width="128px" class="noDataImg" />
                                                    <div class="wdda-table" v-show="zzmmTable.length>0?true:false">
                                                        <el-table :data="zzmmTable" style="width: 100%">
                                                            <el-table-column type="index" label="序号" :index="zzmmindexMethod" width="55" align="center">
                                                            </el-table-column>
                                                            <el-table-column prop="politicalIdentity" label="政治身份">
                                                            </el-table-column>
                                                            <el-table-column prop="attendPartyTime" label="参加党派时间">
                                                            </el-table-column>
                                                            <el-table-column prop="introducer" label="介绍人">
                                                            </el-table-column>
                                                        </el-table>
                                                        <div class="pagination" style="margin:10px 0 0 0" v-show="zzmmTableLeg.length>5?true:false">
                                                            <el-pagination @current-change="zzmmhandleCurrentChange" :current-page="zzmm_cur_page" :page-size="zzmm_pagesize" :page-sizes="[5]" layout="total, sizes, prev, pager, next, jumper" :total="zzmmtableData.length">
                                                            </el-pagination>
                                                        </div>
                                                    </div>
                                                </div>
                                            </el-card>
                                            <!--年度考核  -->
                                            <el-card class="card2" style="margin-top:15px;" v-show="!ndkh">
                                                <div slot="header" class="clearfix">
                                                    <span>年度考核</span>
                                                </div>
                                                <div class="wdda-info">
                                                    <img v-show="ndkhTable.length>0?false:true" :src="require('static/img/manage/noData.png')" width="128px" class="noDataImg" />
                                                    <div class="wdda-table" v-show="ndkhTable.length>0?true:false">
                                                        <el-table :data="ndkhTable" style="width: 100%">
                                                            <el-table-column type="index" label="序号" :index="ndkhindexMethod"  width="55" align="center">
                                                            </el-table-column>
                                                            <el-table-column prop="inspectionYear" label="考核年度">
                                                            </el-table-column>
                                                            <el-table-column prop="inspectionResult" label="考核结论">
                                                            </el-table-column>
                                                        </el-table>
                                                        <div class="pagination" style="margin:10px 0 0 0" v-show="ndkhTableLeg.length>5?true:false">
                                                            <el-pagination @current-change="ndkhhandleCurrentChange" :current-page="ndkh_cur_page" :page-size="ndkh_pagesize" :page-sizes="[5]" layout="total, sizes, prev, pager, next, jumper" :total="ndkhtableData.length">
                                                            </el-pagination>
                                                        </div>
                                                    </div>
                                                </div>
                                            </el-card>
                                            <!--表彰奖励  -->
                                            <el-card class="card2" style="margin-top:15px;" v-show="!bzjl">
                                                <div slot="header" class="clearfix">
                                                    <span>表彰奖励信息</span>
                                                </div>
                                                <div class="wdda-info">
                                                    <img v-show="bzjlTable.length>0?false:true" :src="require('static/img/manage/noData.png')" width="128px" class="noDataImg" />
                                                    <div class="wdda-table" v-show="bzjlTable.length>0?true:false">
                                                        <el-table :data="bzjlTable" style="width: 100%">
                                                            <el-table-column type="index" label="序号" :index="bzjlindexMethod" width="55" align="center">
                                                            </el-table-column>
                                                            <el-table-column prop="rewardName" label="奖励名称">
                                                            </el-table-column>
                                                            <el-table-column prop="duty" label="授奖单位">
                                                            </el-table-column>
                                                            <el-table-column prop="rewardTime" label="批准日期">
                                                            </el-table-column>
                                                        </el-table>
                                                        <div class="pagination" style="margin:10px 0 0 0" v-show="bzjlTableLeg.length>5?true:false">
                                                            <el-pagination @current-change="bzjlhandleCurrentChange" :current-page="bzjl_cur_page" :page-size="bzjl_pagesize" :page-sizes="[5]" layout="total, sizes, prev, pager, next, jumper" :total="bzjltableData.length">
                                                            </el-pagination>
                                                        </div>
                                                    </div>
                                                </div>
                                            </el-card>
                                            <!--家庭成员  -->
                                            <el-card class="card2" style="margin-top:15px;" v-show="!jtcy">
                                                <div slot="header" class="clearfix">
                                                    <span>家庭成员信息</span>
                                                </div>
                                                <img v-show="jtcyTable.length>0?false:true" :src="require('static/img/manage/noData.png')" width="128px" class="noDataImg" />
                                                <div class="wdda-table" v-show="jtcyTable.length>0?true:false">
                                                    <el-table :data="jtcyTable" style="width: 100%">
                                                        <el-table-column type="index" label="序号" :index="jtcyindexMethod"  width="55" align="center">
                                                        </el-table-column>
                                                        <el-table-column prop="personName" label="姓名">
                                                        </el-table-column>
                                                        <el-table-column prop="relationship" label="称谓">
                                                        </el-table-column>
                                                        <el-table-column prop="bithOfDate" label="出生日期">
                                                        </el-table-column>
                                                        <el-table-column prop="workUnitName" label="工作单位名称">
                                                        </el-table-column>
                                                        <el-table-column prop="personDegree" label="学历">
                                                        </el-table-column>
                                                        <el-table-column prop="politicalLandscape" label="政治面貌">
                                                        </el-table-column>
                                                        <el-table-column prop="nowState" label="现状">
                                                        </el-table-column>
                                                        <el-table-column prop="moveAbroad" label="配偶子女移居（境）外情况">
                                                        </el-table-column>
                                                    </el-table>
                                                    <div class="pagination" style="margin:10px 0 0 0" v-show="jtcyTableLeg.length>5?true:false">
                                                        <el-pagination @current-change="jtcyhandleCurrentChange" :current-page="jtcy_cur_page" :page-size="jtcy_pagesize" :page-sizes="[5]" layout="total, sizes, prev, pager, next, jumper" :total="jtcytableData.length">
                                                        </el-pagination>
                                                    </div>
                                                </div>
                                                <!--<div class="wdda-info">
                                                    <div class="wdda-table" v-show="master_user.data.length>0?true:false">
                                                        <el-table :data="master_user.data" style="width: 100%" highlight-current-row>
                                                        <el-table :data="master_user.data" style="width: 100%;">
                                                            <el-table-column type="index" label="序号" width="55" align="center"></el-table-column>
                                                            <el-table-column v-for="(item,j) in master_user.columns" :key="j" :prop="item.field" :label="item.title" :width="item.width">
                                                                <template slot-scope="scope">
                                                                    <span v-if="scope.row.isSet">
                                                                        <el-input v-model="master_user.sel[item.field]">
                                                                        </el-input>
                                                                    </span>
                                                                    <span v-else>{{scope.row[item.field]}}</span>
                                                                </template>
                                                            </el-table-column>
                                                        </el-table>
                                                        <div class="el-table-add-row" style="width: 100%;" @click="addMasterUser()">
                                                            <span>+ 新增家庭成员</span>
                                                        </div>
                                                        <div class="pagination" style="margin:10px 0 0 0" v-show="jtcyTableLeg.length>5?true:false">
                                                            <el-pagination @current-change="jtcyhandleCurrentChange" :current-page="jtcy_cur_page" :page-size="jtcy_pagesize" :page-sizes="[5]" layout="total, sizes, prev, pager, next, jumper" :total="jtcytableData.length">
                                                            </el-pagination>
                                                        </div>
                                                    </div>
                                                </div>-->
                                            </el-card>
                                            <!--通讯录  -->
                                            <el-card class="card2" style="margin-top:15px;" v-show="!txl">
                                                <div slot="header" class="clearfix">
                                                    <span>通讯录</span>
                                                </div>
                                                <div class="wdda-info">
                                                    <div class="wdda-box txlBox">
                                                        <el-form :inline="true" label-position="left" label-width="" ref='txlForm' :model="txlForm" class="demo-form-inline hjForm">
                                                            <el-row :gutter="6" style="margin-bottom:8px">
                                                                <el-col :span="12">
                                                                    <el-form-item label="家庭住址：" prop="address">
                                                                        <span class="gzdwSpan" v-show="txlForm.addressPlace?true:false">{{txlForm.addressPlace}}</span>
                                                                        <span class="gzdwSpan2" v-show="txlForm.addressPlace?false:true">暂无数据</span>
                                                                    </el-form-item>
                                                                </el-col>
                                                                <el-col :span="12">
                                                                    <el-form-item label="户籍地址：" prop="householdAddress">
                                                                        <span class="gzdwSpan" v-show="txlForm.nativePlcae?true:false">{{txlForm.nativePlcae}}</span>
                                                                        <span class="gzdwSpan2" v-show="txlForm.nativePlcae?false:true">暂无数据</span>
                                                                    </el-form-item>
                                                                </el-col>
                                                                <!--<el-col :span="8">
                                                                    <el-form-item label="住址邮政编码：" prop="postalCode">
                                                                        <el-input v-model="txlForm.postalCode" readonly="" placeholder="暂无数据"></el-input>
                                                                    </el-form-item>
                                                                </el-col>-->
                                                                <!--<el-col :span="8">
                                                                    <el-form-item label="家庭电话：" prop="telephone">
                                                                        <el-input v-model="txlForm.telephone" readonly="" placeholder="暂无数据"></el-input>
                                                                    </el-form-item>
                                                                </el-col>-->
                                                                <el-col :span="12">
                                                                    <el-form-item label="办公电话：" prop="officePhone">
                                                                        <el-input v-model="txlForm.officePhone" readonly="" placeholder="暂无数据"></el-input>
                                                                    </el-form-item>
                                                                </el-col>
                                                                <!--<el-col :span="8">
                                                                    <el-form-item label="电子邮箱：" prop="email">
                                                                        <el-input v-model="txlForm.email" readonly="" placeholder="暂无数据"></el-input>
                                                                    </el-form-item>
                                                                </el-col>-->
                                                                <el-col :span="12" class="txlPhone">
                                                                    <el-form-item label="手机：" prop="telePhone">
                                                                        <el-input v-model="txlForm.telePhone" readonly="" placeholder="暂无数据"></el-input>
                                                                    </el-form-item>
                                                                </el-col>
                                                                <!--<el-col :span="8">
                                                                    <el-form-item label="手机2：" prop="mobilephone2">
                                                                        <el-input v-model="txlForm.mobilephone2" readonly="" placeholder="暂无数据"></el-input>
                                                                    </el-form-item>
                                                                </el-col>-->
                                                            </el-row>
                                                        </el-form>
                                                    </div>
                                                </div>
                                            </el-card>
                                            <!--专业技术  -->
                                            <el-card class="card2" style="margin-top:15px;" v-show="!zyjs">
                                                <div slot="header" class="clearfix">
                                                    <span>专业技术任职资格（职称）</span>
                                                </div>
                                                <div class="wdda-info">
                                                    <img v-show="zyjsTable.length>0?false:true" :src="require('static/img/manage/noData.png')" width="128px" class="noDataImg" />
                                                    <div class="wdda-table" v-show="zyjsTable.length>0?true:false">
                                                        <el-table :data="zyjsTable" style="width: 100%">
                                                            <el-table-column type="index" label="序号" :index="zyjsindexMethod"  width="55" align="center">
                                                            </el-table-column>
                                                            <el-table-column prop="accreditationUnit" label="资格审批单位">
                                                            </el-table-column>
                                                            <el-table-column prop="professionalQualification" label="专业资格证书">
                                                            </el-table-column>
                                                            <el-table-column prop="makeWay" label="取得途径">
                                                            </el-table-column>
                                                            <el-table-column prop="professional" label="专业技术">
                                                            </el-table-column>
                                                            <el-table-column prop="acquireTime" label="取得时间">
                                                            </el-table-column>
                                                        </el-table>
                                                        <div class="pagination" style="margin:10px 0 0 0" v-show="zyjsTableLeg.length>5?true:false">
                                                            <el-pagination @current-change="zyjshandleCurrentChange" :current-page="zyjs_cur_page" :page-size="zyjs_pagesize" :page-sizes="[5]" layout="total, sizes, prev, pager, next, jumper" :total="zyjstableData.length">
                                                            </el-pagination>
                                                        </div>
                                                    </div>
                                                </div>
                                            </el-card>
                                            <!--职称信息  -->
                                            <!-- <el-card class="card2" style="margin-top:15px;" v-show="!zcxx">
                                                <div slot="header" class="clearfix">
                                                    <span>职称信息</span>
                                                </div>
                                                <div class="wdda-info">
                                                    <div class="wdda-table">
                                                        <el-table :data="zcxxTable" style="width: 100%">
                                                            <el-table-column type="index" label="序号" width="100">
                                                            </el-table-column>
                                                            <el-table-column prop="policeQualificationName" label="警务技术任职资格名称">
                                                            </el-table-column>
                                                            <el-table-column prop="earnQualificationTime" label="取得资格日期">
                                                            </el-table-column>
                                                        </el-table>
                                                    </div>
                                                </div>
                                            </el-card> -->
                                            <!--职务职级  -->
                                            <!-- <el-card class="card2" style="margin-top:15px;" v-show="!zwzj">
                                                <div slot="header" class="clearfix">
                                                    <span>职务职级并行信息</span>
                                                </div>
                                                <div class="wdda-info">
                                                    <div class="wdda-table">
                                                        <el-table :data="zwzjTable" style="width: 100%">
                                                            <el-table-column type="index" label="序号" width="100">
                                                            </el-table-column>
                                                            <el-table-column prop="enjoyCountyLevel" label="享受县以下机关职级">
                                                            </el-table-column>
                                                            <el-table-column prop="jobRankBeginTime" label="职级起算时间">
                                                            </el-table-column>
                                                        </el-table>
                                                    </div>
                                                </div>
                                            </el-card> -->
                                            <!--出国（境）证件  -->
                                            <!-- <el-card class="card2" style="margin-top:15px;" v-show="!cgjzj">
                                                <div slot="header" class="clearfix">
                                                    <span>出国（境）证件</span>
                                                </div>
                                                <div class="wdda-info">
                                                    <div class="wdda-table">
                                                        <el-table :data="cgjzjTable" style="width: 100%">
                                                            <el-table-column type="index" label="序号" width="60">
                                                            </el-table-column>
                                                            <el-table-column prop="idType" label="证件种类">
                                                            </el-table-column>
                                                            <el-table-column prop="idNum" label="证件号码">
                                                            </el-table-column>
                                                            <el-table-column prop="issuceDate" label="签发日期">
                                                            </el-table-column>
                                                            <el-table-column prop="validTo" label="有效期至">
                                                            </el-table-column>
                                                            <el-table-column prop="turnState" label="上交状态">
                                                            </el-table-column>
                                                        </el-table>
                                                        <div class="pagination">
                                                            <el-pagination @current-change="cgjzjhandleCurrentChange" :current-page="cgjzj_cur_page" :page-size="cgjzj_pagesize" :page-sizes="[10]" layout="total, sizes, prev, pager, next, jumper" :total="cgjzj_data_length">
                                                            </el-pagination>
                                                        </div>
                                                    </div>
                                                </div>
                                            </el-card> -->
                                        </div>
                                    </el-col>
                                </el-row>                              
                            </div>
                        </el-col>
                    </el-row>
                </el-col>
            </el-row>
        </div>
        <!-- 同意提示框 -->
        <el-dialog title="操作提示" :visible.sync="agreeVisible" width="580px" class="agreelog">
            <div class="del-dialog-cnt">
                <p>
                    <i class="el-icon-success"></i>
                </p>
                <br/>
                <p>您已成功审批</p>
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="agreeTip">确 定</el-button>
            </span>
        </el-dialog>
        <!--copyright  -->
        <el-footer style="text-align:center;height:20px;">&copy;2019-2022 南京市公安局&emsp;版权所有</el-footer>
    </div>
</template>

<script>
var generateId = {
    _count: 1,
    get() { return ((+new Date()) + "_" + (this._count++)) }
};
import {communication} from '../../data/getAxios';
export default {
    data() {
        return {
            timelineShow:false,
            reverse: true,
            imgIp:'',
            ndkhtableData:[],
            xlxxtableData:[],
            llxxtableData:[],
            bzjltableData:[],
            zyjstableData:[],
            zwxxtableData:[],
            zzmmtableData:[],
            jtcytableData:[],
            xlxxTableLeg:[],
            ndkhTableLeg:[],
            bzjlTableLeg:[],
            jtcyTableLeg:[],
            llxxTableLeg:[],
            zyjsTableLeg:[],
            zwxxTableLeg:[],
            zzmmTableLeg:[],
        	// imgIp:'http://10.33.66.86:9999/download/userImage/'+'0000051922'+".jpg",
            agreeVisible: false,//同意弹窗
            checked: false,
            toggleShow: true,
            zcxxTable: [],
            zwxxTable1: [],
            zwxxTable2: [],
            zwxxTable3: [],
            ndkhTable: [],
            zzmmTable: [],
            bzjlTable: [],
            ccxxTable: [],
            jtcyTable:[],
            llxxTable: [],
            cgjzjTable: [],
            zwzjTable: [],
            zyjsTable:[],
            xlxxTable:[],
            cur_page: 1,
            pagesize: 5,
            data_length: 0,
            zwxx_cur_page: 1,
            zwxx_pagesize: 5,
            zwxx_data_length: 0,
            zwxx_cur_page2: 1,
            zwxx_pagesize2: 5,
            zwxx_data_length2: 0,
            zwxx_cur_page3: 1,
            zwxx_pagesize3: 5,
            zwxx_data_length3: 0,
            zcxx_cur_page: 1,
            zcxx_pagesize: 5,
            zcxx_data_length: 0,
            zwzj_cur_page: 1,
            zwzj_pagesize: 5,
            zwzj_data_length: 0,
            ndkh_cur_page: 1,
            ndkh_pagesize: 5,
            ndkh_data_length: 0,
            zzmm_cur_page: 1,
            zzmm_pagesize: 5,
            zzmm_data_length: 0,
            bzjl_cur_page: 1,
            bzjl_pagesize: 5,
            bzjl_data_length: 0,
            ccxx_cur_page: 1,
            ccxx_pagesize: 5,
            ccxx_data_length: 0,
            llxx_cur_page: 1,
            llxx_pagesize: 5,
            llxx_data_length: 0,
            cgjzj_cur_page: 1,
            cgjzj_pagesize: 5,
            cgjzj_data_length: 0,
            jtcy_cur_page: 1,
            jtcy_pagesize: 5,
            jtcy_data_length: 0,
            zyjs_cur_page: 1,
            zyjs_pagesize: 5,
            zyjs_data_length: 0,
            xlxx_cur_page: 1,
            xlxx_pagesize: 5,
            xlxx_data_length: 0,
            jcxx: true,
            zcxx: true,
            zwxx: true,
            xlxx: true,
            xwxx: true,
            jtcy: true,
            ndkh: true,
            zzmm: true,
            bzjl: true,
            ccxx: true,
            llxx: true,
            cgjzj: true,
            gzbd: true,
            txl: true,
            zwzj: true,
            zyjs:true,
            class1: 'el-icon-arrow-up el-icon--right',
            class2: 'el-icon-arrow-down el-icon--right',
            startDatePicker: '',
            conferDatePicker: '',
            endDatePicker: this.processDate(),
            master_user: {
                sel: null,//选中行 
                columns: [
                    { field: "personName", title: "姓名", width: 80 },
                    { field: "relationship", title: "称谓", width: 140 },
                    { field: "bithOfDate", title: "出生日期", width: 130 },
                    { field: "workUnitName", title: "工作单位名称" },
                    { field: "personDegree", title: "学历", width: 110 },
                    { field: "politicalLandscape", title: "政治面貌", width: 120 },
                    { field: "nowState", title: "现状" ,width: 80},
                    { field: "moveAbroad", title: "配偶子女移居（境）外情况", width: 130 },
                ],
                data: [],
            },
            txlForm: {
                addressPlace: '',//家庭住址
                email: '',//电子邮箱
                nativePlcae: '',//户籍地址
                telePhone: '',//手机1
                mobilephone2: '',//手机2
                officePhone: '',//办公室号码
                postalCode: '',//住址邮政编号  
                telephone: ''//家庭电话  
            },
            // xlxxForm: {//学历信息
            //     degreeCode: '',//学历代码
            //     degreeName: '',//学历名称
            //     startTime: '',//入学时间
            //     awardDate: '',//毕业日期
            //     studyCode: '',//所学专业代码
            //     stadyName: '',//所学专业名称
            //     schoolName: '',//学校名称
            //     lawSchool: '',//政法院校
            //     degreeName: '',//学位名称
            //     conferTime: '',//学位授予时间
            //     conferUnit: '',//学位授予单位
            //     conferContory: ''//学位授予国家
            // },
            jcxxForm: {//基本信息
                idCard: '',
                jyNumber: '',
                policeDate: '',
                enterSource: '',
                enterTime: '',
                personalStatus: '',
                isConfiden: '',
                PersonExpert: ''
            },
            wddaForm: {
                jyxm: '',//姓名
                sexual: '',//性别
                nation: '',//民族
                personId: '',//key
                birthday: '',//出生日期
                nativePlace: '',//籍贯
                workTime: '',//参加工作时间
                birthPlace: '',//出生地
                joinParty: '',//入党时间
                maritalStatus: '',//婚姻状况
                bloodType:'',//血型
                workPlace: '',//工作单位
                inSource:'',//进入来源
                idNumber:'',//身份证号
                policeDate:'',//参加公安工作日期
                policeNumb:'',//警号
                policeRank:'',//警衔
                isSecret:'',//是否涉密
                personLike:''//个人专长
            },
            idx: -1,
        }
    },
    created() {
    },
    methods: {
        // 职务信息表格序号
        zwxxindexMethod(index){
            return (this.zwxx_cur_page-1)*this.zwxx_pagesize+index+1;
        },
        // 学历信息序号
        xlxxindexMethod(index){
            return (this.xlxx_cur_page-1)*this.xlxx_pagesize+index+1;
        },
        // 履历信息序号
        llxxindexMethod(index){
            return (this.llxx_cur_page-1)*this.llxx_pagesize+index+1;
        },
        // 政治面貌序号
        zzmmindexMethod(index){
            return (this.zzmm_cur_page-1)*this.zzmm_pagesize+index+1;
        },
        // 年度考核
        ndkhindexMethod(index){
            return (this.ndkh_cur_page-1)*this.ndkh_pagesize+index+1;
        },
        // 表彰奖励
        bzjlindexMethod(index){
            return (this.bzjl_cur_page-1)*this.bzjl_pagesize+index+1;
        },
        // 专业技术
        zyjsindexMethod(index){
            return (this.zyjs_cur_page-1)*this.zyjs_pagesize+index+1;
        },
        // 家庭成员表格序号
        jtcyindexMethod(index){
            return (this.jtcy_cur_page-1)*this.jtcy_pagesize+index+1;
        },
        // 全选
        checkChange() {
            var that=this;
            console.log(1122)
            // var htmls = '<i class="el-icon-close el-icon--right">' + '</i>';
            if (!that.checked) {
                $('.btn-info .el-button').removeClass('btnss2');
                // $('.btn-info .el-button').find("i").remove();
                console.log(2233)
                that.jcxx= true;
                that.zcxx= true;
                that.zwxx= true;
                that.xlxx= true;
                that.zyjs=true;
                that.xwxx= true;
                that.jtcy= true;
                that.ndkh= true;
                that.zzmm= true;
                that.bzjl= true;
                that.ccxx= true;
                that.llxx= true;
                that.cgjzj= true;
                that.gzbd= true;
                that.txl= true;
                that.zwzj= true;
            } else {
            console.log(3344)
                $('.btn-info .el-button').addClass('btnss2');
                for(var i=0;i<$('.btn-info .el-button').length;i++){
                    if($('.btn-info .el-button').find('i')){
                        $('.btn-info .el-button').each(function(){
                            // $(this).find("i").remove();
                        });
                        $('.btn-info .el-button').each(function(){
                            // $(this).append(htmls)
                        });
                    }
                }
                that.zwxxClick(); 
                that.xlxxClick(); 
                // that.xwxxClick(); 
                that.jtcyClick(); 
                that.ndkhClick();
                that.zyjsClick(); 
                that.zzmmClick(); 
                that.bzjlClick(); 
                // that.ccxxClick(); 
                that.llxxClick(); 
                that.cgjzjClick(); 
                // that.gzbdClick(); 
                that.txlClick(); 
            };
        },
        // 点击职务信息
        // if (this.$route.query.clickInfo) {
        //     var self=this;
        //     const _obj={
        //         url:self.global.serverPath8+'/personnel/userInfo/getUserInfo?personId2='+this.$route.query.alarmNo,
		// 		isGet:true
        //     };
        zwxxClick() {
            var self = this;
            self.zwxx = !self.zwxx;
            if (!self.zwxx) {
                const _obj={//详情跳转或者页面登录人信息
                    url:self.$route.query.clickInfo?self.global.serverPath8+'/personnel/userInfo/duty?personId2='+self.$route.query.alarmNo:self.global.serverPath8+'/personnel/userInfo/duty?personId2=a',
                    isGet:true
                };
                communication(_obj).then((res) => {
                    if(res.code==0){
                        self.zwxxTable1 = res.data;
                        self.zwxxTableLeg= res.data;
                        self.zwxxtableData=res.data;
                        self.zwxxTable1=self.zwxxtableData.slice((self.zwxx_cur_page-1)*self.zwxx_pagesize,(self.zwxx_cur_page)*self.zwxx_pagesize);
                    }else if(res.code != 1){
                        self.$message.error(res.message)
                    }
                }).catch((err) => {
                    console.log('获取职务信息失败！')
                })
            }
        },
        // 点击学历信息
        xlxxClick() {
            var self = this;
            self.xlxx = !self.xlxx;
            if (!self.xlxx) {
                const _obj={
                    url:self.$route.query.clickInfo?self.global.serverPath8+'/personnel/userInfo/getEduInfo?personId2='+self.$route.query.alarmNo:self.global.serverPath8+'/personnel/userInfo/getEduInfo?personId2=a',
                    isGet:true
                };
                communication(_obj).then((res) => {
                    if(res.code==0){
                        self.xlxxTableLeg=res.data;
                        self.xlxxTable=res.data;
                        self.xlxxtableData=res.data;
                        self.xlxxTable=self.xlxxtableData.slice((self.xlxx_cur_page-1)*self.xlxx_pagesize,(self.xlxx_cur_page)*self.xlxx_pagesize);
                    }else if(res.code != 1){
                        self.$message.warning(res.message)
                    }
                }).catch((err) => {
                    console.log('获取学历信息失败！')
                })
            }
        },
        // 点击年度考核
        ndkhClick() {
            var self = this;
            self.ndkh = !self.ndkh;
            if (!self.ndkh) {
                const _obj={
                    url:self.$route.query.clickInfo?self.global.serverPath8+'/personnel/userInfo/getAssessment?personId2='+self.$route.query.alarmNo:self.global.serverPath8+'/personnel/userInfo/getAssessment?personId2=a',
                    isGet:true
                };
                communication(_obj).then((res) => {
                    if(res.code==0){
                        self.ndkhTableLeg=res.data;
                        self.ndkhTable = res.data;
                        self.ndkhtableData=res.data;
                        self.ndkhTable=self.ndkhtableData.slice((self.ndkh_cur_page-1)*self.ndkh_pagesize,(self.ndkh_cur_page)*self.ndkh_pagesize);
                    }else if(res.code != 1){
                        self.$message.warning(res.message)
                    }
                }).catch((err) => {
                    console.log('获取年度考核失败！')
                })
            }
        },
        // 点击政治面貌
        zzmmClick() {
            var self = this;
            self.zzmm = !self.zzmm;
            if (!self.zzmm) {
                const _obj={
                    url:self.$route.query.clickInfo?self.global.serverPath8+'/personnel/userInfo/political?personId2='+self.$route.query.alarmNo:self.global.serverPath8+'/personnel/userInfo/political?personId2=a',
                    isGet:true
                };
                communication(_obj).then((res) => {
                    if(res.code==0){
                        var tableArr=[];//返回数据有问题
                        tableArr.push(res.data)
                        self.zzmmTableLeg=tableArr;
                        self.zzmmTable = tableArr;
                        self.zzmmtableData=tableArr;
                        self.zzmmTable=self.zzmmtableData.slice((self.zzmm_cur_page-1)*self.zzmm_pagesize,(self.zzmm_cur_page)*self.zzmm_pagesize);
                    }else if(res.code != 1){
                        self.$message.warning(res.message)
                    }
                }).catch((err) => {
                    console.log('获取政治面貌失败！')
                })
            }
        },
        //点击专业技术
        zyjsClick(){
            var self = this;
            self.zyjs = !self.zyjs;
            if (!self.zyjs) {
                const _obj={
                    url:self.$route.query.clickInfo?self.global.serverPath8+'/personnel/userInfo/professional?personId2='+self.$route.query.alarmNo:self.global.serverPath8+'/personnel/userInfo/professional?personId2=a',
                    isGet:true
                };
                communication(_obj).then((res) => {
                    if(res.code==0){
                        self.zyjsTable = res.data;
                        self.zyjsTableLeg=res.data;
                        self.zyjstableData=res.data;
                        self.zyjsTable=self.zyjstableData.slice((self.zyjs_cur_page-1)*self.zyjs_pagesize,(self.zyjs_cur_page)*self.zyjs_pagesize);
                    }else if(res.code != 1){
                        self.$message.warning(res.message)
                    }
                }).catch((err) => {
                    console.log('获取专业技术失败！')
                })
                // self.$http.post(self.global.serverPath + '/zhzg/archive/queryMyArchivePersonPoliticalLandscapeByPerosnId',
                //     JSON.stringify({
                //         "personId": '0000051922',
                //         "currentNum": self.zyjs_cur_page,
                //         "pageSize": self.zyjs_pagesize
                //     })
                // ).then(function(res) {
                //     self.zyjsTable = res.body.rows.data;
                //     self.zyjs_data_length = res.body.rows.totalSize;
                // }, function(res) {
                //     console.log('获取专业技术失败！')
                // })
            }
        },
        // 点击表彰奖励willa
        bzjlClick() {
            var self = this;
            self.bzjl = !self.bzjl;
            if (!self.bzjl) {
                const _obj={
                    url:self.$route.query.clickInfo?self.global.serverPath8+'/personnel/userInfo/getRewards?personId2='+self.$route.query.alarmNo:self.global.serverPath8+'/personnel/userInfo/getRewards?personId2=a',
                    isGet:true
                };
                communication(_obj).then((res) => {
                    if(res.code==0){
                        self.bzjlTable = res.data;
                        self.bzjlTableLeg=res.data;
                        self.bzjltableData=res.data;
                        self.bzjlTable=self.bzjltableData.slice((self.bzjl_cur_page-1)*self.bzjl_pagesize,(self.bzjl_cur_page)*self.bzjl_pagesize);
                    }else if(res.code != 1){
                        self.$message.warning(res.message)
                    }
                }).catch((err) => {
                    console.log('获取表彰奖励失败！')
                })
            }
        },
        // 点击履历信息
        llxxClick() {
            var self = this;
            self.llxx = !self.llxx;
            if (!self.llxx) {
                const _obj={
                    url:self.$route.query.clickInfo?self.global.serverPath8+'/personnel/userInfo/record?personId2='+self.$route.query.alarmNo:self.global.serverPath8+'/personnel/userInfo/record?personId2=a',
                    isGet:true
                };
                communication(_obj).then((res) => {
                    if(res.code==0){
                        self.llxxTable=res.data;
                        self.llxxTableLeg=res.data;
                        self.llxxtableData=res.data;
                        self.llxxTable=self.llxxtableData.slice((self.llxx_cur_page-1)*self.llxx_pagesize,(self.llxx_cur_page)*self.llxx_pagesize);
                    }else if(res.code != 1){
                        self.$message.warning(res.message)
                    }
                }).catch((err) => {
                    console.log('获取履历信息失败！')
                })
            }
        },
        // 点击家庭成员willa2
        jtcyClick() {
            var self = this;
            self.jtcy = !self.jtcy;
            if (!self.jtcy) {
                const _obj={
                    url:self.$route.query.clickInfo?self.global.serverPath8+'/personnel/userInfo/family?personId2='+self.$route.query.alarmNo:self.global.serverPath8+'/personnel/userInfo/family?personId2=a',
                    isGet:true
                };
                communication(_obj).then((res) => {
                    if(res.code==0){
                        self.jtcyTable = res.data;
                        self.jtcyTableLeg=res.data;
                        self.jtcytableData=res.data;
                        self.jtcyTable=self.jtcytableData.slice((self.jtcy_cur_page-1)*self.jtcy_pagesize,(self.jtcy_cur_page)*self.jtcy_pagesize);
                    }else if(res.code != 1){
                        self.$message.error(res.message)
                    }
                }).catch((err) => {
                    console.log(err)
                })
            }
        },
        // 点击出国（境）证件
        cgjzjClick() {
            var self = this;
            self.cgjzj = !self.cgjzj;
            if (!self.cgjzj) {
                self.$http.post(self.$route.query.clickInfo?self.global.serverPath + '/zhzg/archive/queryMyArchivePersonHealthExamByPersonId?personId2='+self.$route.query.alarmNo:self.global.serverPath + '/zhzg/archive/queryMyArchivePersonHealthExamByPersonId?personId2=a',
                    JSON.stringify({
                        "personId": '0000051922',
                        "currentNum": self.cgjzj_cur_page,
                        "pageSize": self.cgjzj_pagesize
                    })
                ).then(function(res) {
                    self.cgjzjTable = res.body.rows.data;
                    self.cgjzj_data_length = res.body.rows.totalSize;
                    console.log(res.body)
                }, function(res) {
                    console.log('获取出国（境）证件失败！')
                })
            }
        },
        // 点击通讯录
        txlClick() {
            var self = this;
            self.txl = !self.txl;
            const _obj={
                url:self.$route.query.clickInfo?self.global.serverPath8 + '/personnel/userInfo/addressList?personId2='+self.$route.query.alarmNo:self.global.serverPath8 + '/personnel/userInfo/addressList',
                isGet:true
            };
            communication(_obj).then((res) => {
                console.log(res);
                if(res.code==0){
                    self.txlForm.addressPlace = res.data.addressPlace;
                    self.txlForm.nativePlcae = res.data.nativePlcae;
                    self.txlForm.officePhone = res.data.officePhone;
                    self.txlForm.telePhone = res.data.telePhone;
                }else if(res.code != 1){
                    self.$message.warning(res.message)
                }
            }).catch((err) => {
                console.log('获取通讯录信息失败！')
            })
        },

        // 结束时间限制//////////////////////////////////////////////////////////////
        processDate() {
            const self = this;
            return {
                disabledDate(time) {
                    if (self.sqxxForm.startTime) {  //如果开始时间不为空，则结束时间大于开始时间
                        return new Date(self.sqxxForm.startTime).getTime() > time.getTime()
                    } else {
                        return time.getTime() > Date.now()//开始时间不选时，结束时间最大值小于等于当天
                    }
                }
            }
        },
        // 删除表格行
        deleteRow(index, row) {
            this.idx = index;
            this.master_user.data.splice(this.idx, 1);
            this.$message.success('删除成功');
        },
        //读取表格数据
        readMasterUser() {
            //根据实际情况，自己改下啊
            this.master_user.data.map(i => {
                i.id = generateId.get();//模拟后台插入成功后有了id
                i.isSet = false;//给后台返回数据添加`isSet`标识
                return i;
            });
        },
        //添加账号
        addMasterUser() {
            for (let i of this.master_user.data) {
                if (i.isSet) return this.$message.warning("请先保存当前编辑项");
            }
            let j = { id: 0, "personName": "", "relationship": "", "bithOfDate": "", "workUnitName": "", "personDegree": "", "politicalLandscape": "", "nowState": "", "moveAbroad": "", "isSet": true, "_temporary": true };
            this.master_user.data.push(j);

            this.master_user.sel = JSON.parse(JSON.stringify(j));
        },
        //修改
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
                // (function() {
                let data = JSON.parse(JSON.stringify(this.master_user.sel));
                console.log(7777779)
                console.log(data)
                if(data.personName.length<1&&data.relationship.length<1&&data.bithOfDate.length<1&&data.workUnitName.length<1&&data.personDegree.length<1&&data.politicalLandscape.length<1&&data.nowState.length<1&&data.moveAbroad.length<1){
                    this.$message({
                        type: 'error',
                        message: "请填写数据!"
                    });
                }else{
                    for (let k in data) row[k] = data[k];
                    this.$message({
                        type: 'success',
                        message: "保存表格!"
                    });
                    //然后这边重新读取表格数据
                    this.readMasterUser();
                    row.isSet = false;
                    // })();
                }
            } else {
                this.master_user.sel = JSON.parse(JSON.stringify(row));
                row.isSet = true;
            }
        },
        // 表格分页导航willa
        handleCurrentChange(val) {
            // this.loading = true;
            this.cur_page = val;
            // this.getTabData();
        },
        cgjzjhandleCurrentChange(val){
            this.cgjzj_cur_page = val;
            if (!this.cgjzj) {
                this.$http.post(this.$route.query.clickInfo?this.global.serverPath + '/zhzg/archive/queryMyArchivePersonHealthExamByPersonId?personId2='+this.$route.query.alarmNo:this.global.serverPath + '/zhzg/archive/queryMyArchivePersonHealthExamByPersonId?personId2=a',
                    JSON.stringify({
                        "personId": '0000051922',
                        "currentNum": this.cgjzj_cur_page,
                        "pageSize": this.cgjzj_pagesize
                    })
                ).then(function(res) {
                    this.cgjzjTable = res.body.rows.data;
                    this.cgjzj_data_length = res.body.rows.totalSize;
                    console.log(res.body)
                }, function(res) {
                    console.log('获取出国（境）证件失败！')
                })
            }
        },
        
        zyjshandleCurrentChange(val){
            this.zyjs_cur_page=val;
            this.zyjsTable=this.zyjstableData.slice((this.zyjs_cur_page-1)*this.zyjs_pagesize,(this.zyjs_cur_page)*this.zyjs_pagesize);
            // this.zyjs_cur_page = val;
            // if (!this.zyjs) {
            //     this.$http.post(this.global.serverPath + '/zhzg/archive/queryMyArchivePersonRewardByPersonId?personId2=a',
            //         JSON.stringify({
            //             "personId": '0000051922',
            //             "currentNum": this.zyjs_cur_page,
            //             "pageSize": this.zyjs_pagesize
            //         })
            //     ).then(function(res) {
            //         this.zyjsTable = res.body.rows.data;
            //         this.zyjs_data_length = res.body.rows.totalSize;
            //     }, function(res) {
            //         console.log('获取专业技术失败！')
            //     })
            // }
        },
        zwxxhandleCurrentChange(val){
            this.zwxx_cur_page=val;
            this.zwxxTable1=this.zwxxtableData.slice((this.zwxx_cur_page-1)*this.zwxx_pagesize,(this.zwxx_cur_page)*this.zwxx_pagesize);
        },
        jtcyhandleCurrentChange(val){
            this.jtcy_cur_page=val;
            this.jtcyTable=this.jtcytableData.slice((this.jtcy_cur_page-1)*this.jtcy_pagesize,(this.jtcy_cur_page)*this.jtcy_pagesize);
        },
        bzjlhandleCurrentChange(val){
            this.bzjl_cur_page=val;
            this.bzjlTable=this.bzjltableData.slice((this.bzjl_cur_page-1)*this.bzjl_pagesize,(this.bzjl_cur_page)*this.bzjl_pagesize);
            // this.bzjl_cur_page = val;
            // if (!this.bzjl) {                
            //     this.$http.post(this.$route.query.clickInfo?this.global.serverPath + '/zhzg/archive/queryMyArchivePersonRewardByPersonId?personId2='+this.$route.query.alarmNo:this.global.serverPath + '/zhzg/archive/queryMyArchivePersonRewardByPersonId?personId2=a',
            //         JSON.stringify({
            //             "personId": '0000051922',
            //             "currentNum": this.bzjl_cur_page,
            //             "pageSize": this.bzjl_pagesize
            //         })
            //     ).then(function(res) {
            //         this.bzjlTable = res.body.rows.data;
            //         this.bzjl_data_length = res.body.rows.totalSize;
            //     }, function(res) {
            //         console.log('获取表彰奖励失败！')
            //     })
            // }
        },
        zzmmhandleCurrentChange(val){
            this.zzmm_cur_page=val;
            this.zzmmTable=this.zzmmtableData.slice((this.zzmm_cur_page-1)*this.zzmm_pagesize,(this.zzmm_cur_page)*this.zzmm_pagesize);
            // this.zzmm_cur_page = val;
            // if (!this.zzmm) {
            //     this.$http.post(this.global.serverPath + '/zhzg/archive/queryMyArchivePersonPoliticalLandscapeByPerosnId?personId2=a',
            //         JSON.stringify({
            //             "personId": '0000051922',
            //             "currentNum": this.zzmm_cur_page,
            //             "pageSize": this.zzmm_pagesize
            //         })
            //     ).then(function(res) {
            //         this.zzmmTable = res.body.rows.data;
            //         this.zzmm_data_length = res.body.rows.totalSize;
            //         console.log(res.body)
            //     }, function(res) {
            //         console.log('获取政治面貌失败！')
            //     })
            // }
        },
        ndkhhandleCurrentChange(val){
            this.ndkh_cur_page=val;
            this.ndkhTable=this.ndkhtableData.slice((this.ndkh_cur_page-1)*this.ndkh_pagesize,(this.ndkh_cur_page)*this.ndkh_pagesize);
        },
        xlxxhandleCurrentChange(val){
            this.xlxx_cur_page=val;
            this.xlxxTable=this.xlxxtableData.slice((this.xlxx_cur_page-1)*this.xlxx_pagesize,(this.xlxx_cur_page)*this.xlxx_pagesize);
        },
        
        zwzjhandleCurrentChange(val){
            this.zwzj_cur_page = val;
            if (!this.zwzj) {
                this.$http.post(this.$route.query.clickInfo?this.global.serverPath + '/zhzg/archive/queryMyArchivePersonJobRankByPerosnId?personId2='+this.$route.query.alarmNo:this.global.serverPath + '/zhzg/archive/queryMyArchivePersonJobRankByPerosnId?personId2=a',
                    JSON.stringify({
                        "personId": '0000051922',
                        "currentNum": this.zwzj_cur_page,
                        "pageSize": this.zwzj_pagesize
                    })
                ).then(function(res) {
                    this.zwzjTable = res.body.rows.data;
                    this.zwzj_data_length = res.body.rows.totalSize;
                }, function(res) {
                    console.log('获取职务职级失败！')
                })
            }
        },
        zcxxhandleCurrentChange(val){
            this.zcxx_cur_page = val;
            if (!this.zcxx) {
                this.$http.post(this.$route.query.clickInfo?this.global.serverPath + '/zhzg/archive/queryMyArchivePersonQualificationByPerosnId?personId2='+this.$route.query.alarmNo:this.global.serverPath + '/zhzg/archive/queryMyArchivePersonQualificationByPerosnId?personId2=a',
                    JSON.stringify({
                        "personId": '0000051922',
                        "currentNum": this.zcxx_cur_page,
                        "pageSize": this.zcxx_pagesize
                    })
                ).then(function(res) {
                    this.zcxxTable = res.body.rows.data;
                    this.zcxx_data_length = res.body.rows.totalSize;
                }, function(res) {
                    console.log('获取职称信息失败！')
                })
            }
        },
        llxxhandleCurrentChange(val){
            this.llxx_cur_page = val;
            if (!this.llxx) {
                this.$http.post(this.$route.query.clickInfo?this.global.serverPath + '/zhzg/archive/queryMyArchivePersonRecordByPersonId?personId2='+this.$route.query.alarmNo:this.global.serverPath + '/zhzg/archive/queryMyArchivePersonRecordByPersonId?personId2=a',
                    JSON.stringify({
                        "personId": '0000051922',
                        "currentNum": this.llxx_cur_page,
                        "pageSize": this.llxx_pagesize
                    })
                ).then(function(res) {
                    this.llxxTable = res.body.rows.data;
                    this.llxx_data_length = res.body.rows.totalSize;
                }, function(res) {
                    console.log('获取履历信息失败！')
                })
            }
            // this.llxxClick();
        },
        llxxhandleCurrentChange(val){
            this.llxx_cur_page=val;
            this.llxxTable=this.llxxtableData.slice((this.llxx_cur_page-1)*this.llxx_pagesize,(this.llxx_cur_page)*this.llxx_pagesize);
        },
        // 收起展开
        upDown() {
            this.toggleShow = !this.toggleShow;
        },
        // 审批同意
        agreeTip() {
            var self = this;
            self.$http.get(self.$route.query.clickInfo?self.global.serverPath + '/zhzg/goAbroad/checkGoAbroadApplicationInfo?personId2='+self.$route.query.alarmNo:self.global.serverPath + '/zhzg/goAbroad/checkGoAbroadApplicationInfo?personId2=a', {
                params: {
                    "opinions": '',//申请单号
                }
            }).then(function(res) {
                self.agreeVisible = false;
                self.$router.push('/dashboard4');
            }, function(res) {
                console.log('请求失败！！！！！')
            });
        },

    },
    mounted() {
        this.checked=true;
        this.checkChange();//默认全选
        // 按钮样式变化
        // var htmls = '<i class="el-icon-close el-icon--right">' + '</i>';
        $('.btn-info .el-button').click(function() {
            if ($(this).hasClass('btnss2')) {
                $(this).removeClass('btnss2');
                // $(this).find("i").remove();
            } else {
                $(this).addClass('btnss2');
                // $(this).append(htmls)
            }
        });

        // 详情跳转
        if (this.$route.query.clickInfo) {
            var self=this;
            const _obj={
                url:self.global.serverPath8+'/personnel/userInfo/getUserInfo?personId2='+this.$route.query.alarmNo,
				isGet:true
            };
            communication(_obj).then((res) => {
                if(res.code==0){
                    var formInfo = res.data;
                    self.imgIp=formInfo.policeImage;
                    self.wddaForm = {
                        jyxm: formInfo.userName,//姓名
                        sexual: formInfo.sex,//性别
                        nation: formInfo.nation,//民族
                        // personId: formInfo.,//key
                        birthday: formInfo.birthDate,//出生日期
                        nativePlace: formInfo.nativePlace,//籍贯
                        workTime: formInfo.workingHours,//参加工作时间
                        birthPlace: formInfo.nativeHeath,//出生地
                        joinParty: formInfo.inPartyTime,//入党时间
                        maritalStatus: formInfo.maritalStatus,//婚姻状况
                        bloodType:formInfo.bloodType,//血型
                        workPlace: formInfo.workUnit,//工作单位
                        inSource:formInfo.sourceEntry,//进入来源
                        idNumber:formInfo.idNumber,//身份证号
                        policeDate:formInfo.inPoliceTime,//参加公安工作日期
                        policeNumb:formInfo.policeNo,//警号
                        policeRank:formInfo.policeRank,//警衔
                        isSecret:formInfo.secretStatus,//是否涉密
                        personLike:formInfo.formInfopersonalExpertise//个人专长
                    };
                }else if(res.code != 1){
                    self.$message.error(res.message)
                }
            }).catch((err) => {
                console.log(err)
            })
        } else {
            var self = this;
            const _obj={
                url:self.global.serverPath8+'/personnel/userInfo/getUserInfo?personId2=a',
				isGet:true
            };
            communication(_obj).then((res) => {
                if(res.code==0){
                    var formInfo = res.data;
                    self.imgIp=formInfo.policeImage;
                    self.wddaForm = {
                        jyxm: formInfo.userName,//姓名
                        sexual: formInfo.sex,//性别
                        nation: formInfo.nation,//民族
                        // personId: formInfo.,//key
                        birthday: formInfo.birthDate,//出生日期
                        nativePlace: formInfo.nativePlace,//籍贯
                        workTime: formInfo.workingHours,//参加工作时间
                        birthPlace: formInfo.nativeHeath,//出生地
                        joinParty: formInfo.inPartyTime,//入党时间
                        maritalStatus: formInfo.maritalStatus,//婚姻状况
                        bloodType:formInfo.bloodType,//血型
                        workPlace: formInfo.workUnit,//工作单位
                        inSource:formInfo.sourceEntry,//进入来源
                        idNumber:formInfo.idNumber,//身份证号
                        policeDate:formInfo.inPoliceTime,//参加公安工作日期
                        policeNumb:formInfo.policeNo,//警号
                        policeRank:formInfo.policeRank,//警衔
                        isSecret:formInfo.secretStatus,//是否涉密
                        personLike:formInfo.formInfopersonalExpertise//个人专长
                    };
                }else if(res.code != 1){
                    self.$message.error(res.message)
                }
            }).catch((err) => {
                console.log(err)
            })
            // self.$http.get(self.global.serverPath + '/zhzg/archive/queryMyArchiveBasicInfoByPersonId', {//传递参数
            //     params: {
            //         "personId": '0000051922',
            //     }
            // }).then(function(res) {
            //     var formInfo = res.body.rows[0];
            //     self.wddaForm = {
            //         jyxm: formInfo.personName,//姓名
            //         sexual: formInfo.gender,//性别
            //         nation: formInfo.national,//民族
            //         birthday: formInfo.dateOfBirth,//出生日期
            //         nativePlace: formInfo.nativePlace,//籍贯
            //         workTime: formInfo.attendWorkDate,//参加工作时间
            //         birthPlace: formInfo.birthPlace,//出生地
            //         personId: formInfo.personId,//key
            //         joinParty: formInfo.partyTime,//入党时间
            //         maritalStatus: formInfo.maritalStatus,//婚姻状况
            //         healthCondition: formInfo.heath,//健康状况
            //         workPlace: formInfo.workUnits,//工作单位
            //         workDepart: formInfo.deptName//工作部门
            //     };
            // }, function(res) {
            //     console.log('请求失败！！！！！')
            // });
        };

    },
    computed: {

    }
}
</script>
<style scoped>
.titleFont{
    font-family:'PingFangSC-Medium';
    font-size: 18px;
    padding-left:20px;
    padding-top:10px;
    color: #272727;
    font-weight: bold;
}
.titleTop{
    width:100%;
    height:112px;
    position:relative;
}
.circleImg{
    width:112px;
    height:112px;
    border-radius:56px;
    background:#fff;
    left:50%;
    margin-left:-56px;
    position:absolute;
    top:35px;
    overflow:hidden;
}
.btn-info .el-button:not(.el-button--text) {
    border: none;
    font-size: 14px;
    color: rgba(0, 0, 0, 0.65);
    background: transparent;
}

.btn-info .btnss2:not(.el-button--text) {
    border: none;
    font-size: 14px;
    color: #1890FF;
}

.textBtn {
    color: #409EFF;
    background: 0 0;
    padding-left: 0;
    padding-right: 0;
    font-size: 14px;
    cursor: pointer;
}

.btn-info .el-checkbox {
    margin-right: 6px
}

.policePhoto {
    margin:0 auto;
    display:block;
    /* z-index: 99; */
}

.wdda-option .el-row[data-v-12f3b31b] {
    margin-bottom: 0;
}

.del-dialog-cnt p {
    font-size: 14px;
    color: rgba(0, 0, 0, 0.85);
    text-align: center;
}

.del-dialog-cnt i {
    font-family: PingFangSC-Regular;
    font-size: 32px;
    color: green;
    opacity: 0.7;
    text-align: center;
}

.jb-icon {
    background: url('../../../static/img/jb.png') no-repeat center;
    background-size: 100% 100%;
}

.steps {
    padding: 3% 3% 0 3%;
}

.el-row {
    margin-bottom: 20px;
}

.mgb20 {
    margin-bottom: 18px;
}
.hjForm .el-form-item--mini.el-form-item, .el-form-item--small.el-form-item{
    margin-bottom:4px!important;
}
</style>
<style>
.txlPhone .el-form-item__content{
    width:80%!important;
}
.txlPhone .el-form-item{
    width:100%!important;
}
.btnMall .el-checkbox__label{
    padding-left:6px;
}
.newPage .el-card__body{
    padding:0!important;
}
.txlBox{
    padding:10px 20px 0 20px;
}
.wdda-table{
    padding:0 14px 10px;
}
.wdda-table .el-table th>.cell,
.jyxx-table .el-table td div {
    font-family: PingFangSC-Medium;
    font-size: 14px;
    color: rgba(0, 0, 0, 0.85);
}

.btn-info .el-icon-close {
    color: red!important;
}

.mgb0 {
    margin-bottom: 0px!important;
}

.agreelog .el-dialog__header,
.bhlog .el-dialog__header {
    border-bottom: 1px solid #E8E8E8;
}

.agreelog .el-dialog__footer,
.bhlog .el-dialog__footer {
    border-top: 1px solid #E8E8E8;
}

.agreelog .el-dialog__body {
    padding: 50px 20px;
}

.wdda-info {
    position: relative;
}

.wdda-info .el-row[data-v-6aef4342] {
    margin-bottom: 0;
}

.wdda-info .el-form-item__label {
    padding: 0;
}

.wdda-info .el-input__inner {
    border: none;
    padding: 0;
}

.wdda-box .el-form-item--small .el-form-item__content,
.el-form-item--small .el-form-item__label {
    line-height: 32px;
}

.wdda-box .el-form-item__label {
    font-family: PingFangSC-Regular;
    font-size: 14px;
    color: rgba(0, 0, 0, 0.85);
    letter-spacing: 0;
}

.wdda-box .el-select {
    width: 76%;
}

.wdda-box .el-input__inner {
    width: 98%;
}

.wdda-box .el-select .el-input__inner {
    width: 100%;
}

.wdda-box .el-upload--text {
    width: 76%;
    height: 28px;
    border: none;
    text-align: left;
}

.wdda-box .el-upload-dragger {
    border: none;
    height: 100%;
    text-align: left;
    padding-left: 3px;
}

.wdda-box .el-upload__tip {
    margin-top: 0;
}

.wdda-box .el-upload-dragger {
    width: 100%;
}

.wdda-box .el-upload-dragger .el-upload__text {
    text-align: left;
}

.wdda-form .el-form-item__label {
    font-family: PingFangSC-Regular;
    font-size: 14px;
    color: rgba(0, 0, 0, 0.85);
    letter-spacing: 0;
}

.wdda-form .el-select {
    width: 76%;
}

.wdda-form .el-input__inner {
    width: 76%;
}

.wdda-form .el-select .el-input__inner {
    width: 100%;
}

.wdda-form .el-upload-dragger .el-upload__tex downloadt {
    text-align: left;
}

.wdda-form .el-input__inner {
    border: 1px solid #DCDFE6;
    padding: 0 15px;
}

.wdda-form .el-input--prefix .el-input__inner {
    padding: 0 30px;
    width: 100%;
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
.gzdwSpan{
    color:#606266;
    font-size:14px;
    word-break: break-all!important;
    word-wrap: break-word!important;
}
.gzdwSpan2{
    color:#ccc;
    font-size:14px;
    word-break: break-all!important;
    word-wrap: break-word!important;
}
.timeline{
    background:white;
    margin:15px 0 0;
    padding:20px;
    border-radius: 3px;
}
.timeline .el-timeline-item{
    padding-bottom:40px
}
.timeline .el-timeline-item__node{
    background:#409EFF
}
.myFilesListCon .el-card__body{
    padding:0
}
.myFilesListCon .el-card__header{
    padding:10px 16px;
}
</style>

