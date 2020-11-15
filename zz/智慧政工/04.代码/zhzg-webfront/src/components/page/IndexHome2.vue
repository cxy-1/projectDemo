<template>
	<div class="index" style="background:#eee;min-width:1170px">
		<!-- min-width:1344px -->
		<el-container>
			<!-- <div class="aLink" @click="toHome1">
				<i class="el-icon-setting"></i>
			</div> -->
			<el-button class="aLink" type="primary" @click="dialogVisible = true">
			<!-- <el-button class="aLink" type="primary" @click="test()"> -->
				<i class="el-icon-setting" style="font-size:22px"></i>
			</el-button>
			<div class="indexCon">
				<el-main class="indexInfo">
					<!--头部  -->
					<div class="perInfo">
						<div class="infoHead">
							<img width="83px" height="110px" :src="imgIp" />
						</div>
						<div class="informa">
							<el-form :inline="true" label-position="right" label-width="90px" ref='leaveForm' :model="leaveForm" class="demo-form-inline">
								<el-row class="informaName">
									<el-col :span="24">
										<div class="grid-content bg-purple-dark">
											<b>{{leaveForm.name}}</b>
											<b style="margin:0 10px">{{leaveForm.unit}}</b>
											<el-tooltip class="item" effect="dark" :content="titleName" placement="top">
												<img style="position:relative;top:5px;width:60px;margin-right:40px;transform:rotate(180deg);" :src="policeRankImg" />
											</el-tooltip>
											<el-tooltip class="item" effect="dark" content="司法证书" placement="top">
												<i v-show="sfzsInfo" class="iconfont icontianping" style="font-size:24px;color:#1890FF"></i>
											</el-tooltip>
											<el-tooltip class="item" effect="dark" :content="jzjgSpan" placement="top">
												<i v-show="jzjgInfo" class="iconfont iconren" style="font-size:24px;color:#1890FF"></i>
											</el-tooltip>
											<el-tooltip class="item" effect="dark" content="333工程专业人才" placement="top">
												<i v-show="zyrcInfo" class="iconfont iconrencaizhongxin" style="font-size:24px;color:#1890FF"></i>
											</el-tooltip>
										</div>
									</el-col>
								</el-row>
								<el-row>
									<el-col :span="8">
										<div class="grid-content bg-purple">
											<span><span class="infoMsg">出生日期：</span>{{leaveForm.birth}}</span>
											<!--<span>出生日期：{{leaveForm.birth}}({{leaveForm.age}}岁)</span>-->
										</div>
									</el-col>
									<el-col :span="7">
										<div class="grid-content bg-purple">
											<span><span class="infoMsg">警号：</span>{{leaveForm.userId}}</span>
										</div>
									</el-col>
									<el-col :span="9">
										<div class="grid-content bg-purple">
											<span><span class="infoMsg">参加工作时间：</span>{{leaveForm.jobTime}}</span>
										</div>
									</el-col>
								</el-row>
								<el-row>
									<el-col :span="8">
										<div class="grid-content bg-purple">
											<span><span class="infoMsg">职务：</span>{{leaveForm.job}}</span>
										</div>
									</el-col>
									<el-col :span="7">
										<div class="grid-content bg-purple">
											<span><span class="infoMsg">政治面貌：</span>{{leaveForm.level}}</span>
										</div>
									</el-col>
									<el-col :span="9">
										<div class="grid-content bg-purple">
											<span><span class="infoMsg">毕业院校：</span>{{leaveForm.school}}</span>
										</div>
									</el-col>
								</el-row>
							</el-form>
						</div>
						<!--勋章跑马灯-->
						<div class="medals block">
							<el-carousel v-show="listTemp.length>0?true:false" :autoplay="false" :interval="5000" arrow="always">
								<el-carousel-item v-for="(row,i) in listTemp" :key="i">
									<div style="height:100px;text-align:center;display:flex;justify-content:space-between">
										<div style="width:130px;" v-for="(cell,j) in row" :key="j">
	<!--                                         <img width="80px" height="140px" style="" :src="cell== '三等功'?require('static/img/index/three.png'):cell== '二等功'?require('static/img/index/two.png'):cell== '一等功'?require('static/img/index/one.png'):cell== '南京市劳动模范'?require('static/img/index/lm.png'):cell== '二级英模'?require('static/img/index/ym.png'):cell== '全国优秀人民警察'?require('static/img/index/yx.png'):cell== '全国先进工作者'?require('static/img/index/xj.png'):cell=='全国三八红旗手'?require('static/img/index/38hq.png'):cell=='全国特级优秀人民警察'?require('static/img/index/tjyx.png'):cell=='省三八红旗手'?require('static/img/index/js38hq.png'):cell=='市三八红旗手'?require('static/img/index/nj38hq.png'):cell=='江苏省劳动模范'?require('static/img/index/jslm.png'):cell=='巾帼建功标兵'?require('static/img/index/qgjgbb.png'):require('static/img/index/yjym.png')" /> -->
	                                        <img width="80px" height="140px" style="" :src="cell== '一级英模'?require('static/img/index/yjym.png'):cell== '二等功'?require('static/img/index/two.png'):cell== '一等功'?require('static/img/index/one.png'):cell== '南京市劳动模范'?require('static/img/index/lm.png'):cell== '二级英模'?require('static/img/index/ym.png'):cell== '全国优秀人民警察'?require('static/img/index/yx.png'):cell== '全国先进工作者'?require('static/img/index/xj.png'):cell=='全国三八红旗手'?require('static/img/index/38hq.png'):cell=='全国特级优秀人民警察'?require('static/img/index/tjyx.png'):cell=='省三八红旗手'?require('static/img/index/js38hq.png'):cell=='市三八红旗手'?require('static/img/index/nj38hq.png'):cell=='江苏省劳动模范'?require('static/img/index/jslm.png'):cell=='巾帼建功标兵'?require('static/img/index/qgjgbb.png'):require('static/img/index/three.png')" />
										</div>
									</div>
								</el-carousel-item>
							</el-carousel>
							<!--是否有奖章图片-->
							<img v-show="listTemp.length==0?true:false" width="190px" style="height:120px;margin-left:14%;margin-top:10px" :src="require('static/img/index/noLevel.png')" />
						</div>
					</div>
				</el-main>
			</div>
		</el-container>
		<!-- 时间轴 -->
		<el-card class="timeY" :style="{'background':'url('+require('static/img/index/progressBg.png')+')','background-size':'100% 100%'}">
			<div class="timeLeft timeYBtn" @click='timeLeft()'><i class="el-icon-caret-left"></i></div>
			<div class="timeRight timeYBtn" @click='timeRight()'><i class="el-icon-caret-right"></i></div>
			<div class="timeUl">
				<ul class="liBox" v-drag='timeMove'></ul>
			</div>
		</el-card>
		<div class="installList" ref="installList">
			<div class="install" style="height:100%;position:absolute;right:18px;left:18px;">
				<el-row :gutter="24" style="margin-bottom:24px">
					<el-col :span="12" v-show="daohang"  class="installCon" style="height:100%;">
						<div class="grid-content bg-purple" style="height:330px;">
							<el-container>
								<el-header>
								<i class="iconfont iconheziguanligenduoshangpinkeji headIcon"></i>
								我的导航</el-header>
							</el-container>
							<div class="exitaNav" style="padding:12px 20px 12px 10px;height:245px;overflow-y:auto">
								<el-row>
									<template v-for="(item,index) in initData">
										<el-col :span="8">
											<router-link :to="{path:item.linkTo}">
												<!--<i :class="item.oneI"></i>&emsp;{{item.spanText}}</router-link>和党团有关的是红色-->
												<!--<i :class="item.oneI" :style="{'color':(item.spanText.indexOf('党')!=-1||item.spanText.indexOf('团')!=-1?'red':'#1890FF')}"></i>&emsp;{{item.spanText}}</router-link>-->
												<i :class="item.oneI" :style="{'color':(item.id.substring(0,3))<400 && (item.id.substring(0,3))>300?'red':(item.id.substring(0,3))<130 && (item.id.substring(0,3))>120?'#FAAD14':(item.id.substring(0,3))<140 && (item.id.substring(0,3))>130?'#FAAD14':(item.id.substring(0,3))<700 && (item.id.substring(0,3))>600?'#1890FF':(item.id.substring(0,3))<800 && (item.id.substring(0,3))>700?'#1890FF':(item.id.substring(0,3))<1000 && (item.id.substring(0,3))>900?'#1890FF':'#1890FF'}"></i>&emsp;{{item.spanText}}</router-link>
										</el-col>
									</template>
									<el-col :span="8">
										<i class="el-icon-plus hoverIcon" @click="addBtn()"></i>
										<!-- <img :src="require('static/img/index/addmore.png')" />  -->
									</el-col>
								</el-row>
							</div>
						</div>
					</el-col>
					<el-col :span="12" v-show="lvli" class="installCon" style="height:100%;">
						<div class="grid-content bg-purple-light load-div" style="height:330px;overflow:auto">
							<el-container>
								<el-header>
								<i class="iconfont iconheziguanligenduoshangpinkeji headIcon"></i>
								我的履历</el-header>
							</el-container>
							<div style="height:270px;overflow-y:auto">
								<img v-show="wdjcTable.length>0?false:true" :src="require('static/img/manage/noData.png')" width="128px" class="noDataSmall" />
								<el-table v-show="wdjcTable.length>0?true:false"  :data="wdjcTable" :show-header="false" style="height:100%;width: 100%;border-top-right-radius:30px;border-top-left-radius:30px;padding-left:10px;">
									<el-table-column prop="dealDate" label="日期" width="160px"></el-table-column>
									<el-table-column prop="deptName" label="单位"></el-table-column>
									<el-table-column prop="duty" label="岗位"></el-table-column>
								</el-table>
							</div>
						</div>
					</el-col>
					<el-col :span="12" v-show="jiangli" class="installCon" style="height:100%">
						<div class="grid-content bg-purple" style="height:330px;overflow:auto">
							<el-container>
								<el-header>
								<i class="iconfont iconheziguanligenduoshangpinkeji headIcon"></i>
								我的奖励</el-header>
							</el-container>
							<div class="reward rewards" style="height:calc(100% - 60px);padding:0">
								<img v-show="jlTable.length>0?false:true" :src="require('static/img/manage/noData.png')" width="128px" class="noDataSmall" />
								<el-table v-show="jlTable.length>0?true:false" :data="jlTable" height="100%" :show-header="false" style="width: 100%;border-top-right-radius:30px;border-top-left-radius:30px;padding-left:10px;">
									<el-table-column prop="rewardTime" label="日期" width="160px"></el-table-column>
                                    <el-table-column prop="duty" label="发证机关"></el-table-column>
                                    <el-table-column prop="rewardName" label="奖惩名称"></el-table-column>
								</el-table>
							</div>
						</div>
					</el-col>
					<el-col :span="12" v-show="xunli" class="installCon" style="height:100%">
						<div class="grid-content bg-purple" style="height:330px;overflow:auto">
							<el-container style="display:flex">
								<el-header>
								<i class="iconfont iconheziguanligenduoshangpinkeji headIcon"></i>
									我的训历
								</el-header>
							</el-container>
							<div class="reward rewards" style="height:calc(100% - 60px);padding:0">
								<img v-show="xlTable.length>0?false:true" :src="require('static/img/manage/noData.png')" width="128px" class="noDataSmall" />
								<el-table :data="xlTable" v-show="xlTable.length>0?true:false" height="100%" :show-header="false" style="width: 100%;border-top-right-radius:30px;border-top-left-radius:30px;padding-left:10px;">
									<el-table-column prop="trainingTime" label="训历时间" width="160px"></el-table-column>
									<el-table-column prop="trainingContent" label="班级"></el-table-column>
									<el-table-column prop="trainingResult" label="训历成绩"></el-table-column>
								</el-table>
							</div>
						</div>
					</el-col>
					<el-col :span="12" v-show="zhanyou" class="installCon" style="height:100%">
						<div class="grid-content bg-purple" style="height:330px;overflow:auto">
							<el-container style="display:flex">
								<el-header>
								<i class="iconfont iconheziguanligenduoshangpinkeji headIcon"></i>
									我的战友
									<router-link to="/myArms" class="headMore">
										<span>更多</span>
										<i class="el-icon-d-arrow-right"></i>
									</router-link>
								</el-header>
							</el-container>
							<div class="reward wdzyTable" style="height:calc(100% - 60px);padding:0">
								<img v-show="myArmsData.length>0?false:true" :src="require('static/img/manage/noData.png')" width="128px" class="noDataSmall" />
								<el-table v-show="myArmsData.length>0?true:false" :show-header="false" :data="myArmsData" style="width: 100%;border-top-right-radius:30px;border-top-left-radius:30px;padding-left:10px;">
									<el-table-column prop="amryFriendName" label="姓名" width="160px">
									</el-table-column>
									<el-table-column prop="nowWorkUnit" label="现单位名称">
									</el-table-column>
									<el-table-column prop="telePhone" label="联系方式">
									</el-table-column>
									<!-- <el-table-column prop="beginTime" label="开始时间">
									</el-table-column>
									<el-table-column prop="endTime" label="结束时间">
									</el-table-column> -->
								</el-table>
							</div>
						</div>
					</el-col>
				</el-row>
			</div>
		</div>
		<!-- 添加导航提示框 -->
		<el-dialog title="添加导航" class="tjdh" :visible.sync="delVisible" width="866px">
			<div class="ytjNav">
				<p>
					<i class="iconfont icontijiao"></i> 已添加导航</p>
				<div class="mainY">
					<el-row :gutter="24">
						<ul class="selfUl" v-for="(item,index) in selectDataList" :key="index">
							<li>
								<i :class="item.newIcon||item.icon"></i>
								&emsp;
								<span>{{item.title}}</span>
								<i class="el-icon-remove removeIcon" @click="removeIcon(item,index)"></i>
							</li>
						</ul>
					</el-row>
				</div>
			</div>
			<div class="qbNav">
				<p>
					<i class="iconfont icontijiao"></i> 全部导航
				</p>
				<template>
					<el-tabs v-model="qbNav">
						<el-tab-pane v-for="(tabs,j) in tabsData" :label="tabs.label" :name='tabs.name' :key="j">
							<div class="mainQ" style="padding-top:10px">
								<el-row :gutter="24">
									<!--<ul class="selfUl" v-for="(item,index) in AllDataList">-->
									<ul class="selfUl" v-for="(v,k) in tabs.subs" :key="k">
										<li>
											<i :class="v.newIcon"></i> &emsp;
											<span>{{v.title}}</span>
											<i class="el-icon-circle-plus addIcon" @click="addIcon(v,k,j)"></i>
										</li>
									</ul>
								</el-row>
							</div>
						</el-tab-pane>
					</el-tabs>
				</template>
			</div>
			<span slot="footer" class="dialog-footer">
				<el-button @click="delVisible = false">取 消</el-button>
				<el-button type="primary" @click="saveNav" :loading="saveNavFlag">确 定</el-button>
			</span>
		</el-dialog>
		<el-dialog class="customizeCon" title="自定义设置" :visible.sync="dialogVisible" width="300px" :before-close="handleClose">
			<div class="customize">
				<el-row>
					<el-col :span="20">
						<el-tree :data="data" node-key="id":allow-drop='allowDrop' @node-click="handleNodeClick" default-expand-all @node-drag-start="handleDragStart" @node-drag-enter="handleDragEnter" @node-drag-leave="handleDragLeave" @node-drag-over="handleDragOver" @node-drag-end="handleDragEnd" @node-drop="handleDrop" draggable></el-tree>
					</el-col>
  					<el-col class="switchList" :span="4">
						<el-switch v-for="(item,index) in switchList" v-model="item.derail" :key="index"  @change="valueFun($event,index)"></el-switch>
						<!-- <el-switch v-model="value1" @change="value1Fun($event,0)"></el-switch>
						<el-switch v-model="value2" @change="value2Fun($event,1)"></el-switch>
						<el-switch v-model="value3" @change="value3Fun($event,2)"></el-switch>
						<el-switch v-model="value4" @change="value4Fun($event,3)"></el-switch>
						<el-switch v-model="value5" @change="value5Fun($event,4)"></el-switch>  -->
					</el-col>
				</el-row>
				<span slot="footer" class="dialog-footer">
					<!-- <el-button @click="dialogVisible = false">取 消</el-button> -->
					<el-button type="primary"  @click="dialogVisiBtn">确 定</el-button>
					<!-- <el-button type="primary" @click="enterBtn">确 定</el-button> -->
				</span>
			</div>
		</el-dialog>
	</div>
</template>
<script>
/*$(function(){
    $("body").on("mouseover", ".beRewarded", function() {
		$(".beRewarded").eq($(this).attr('attrbix')).css({'z-index':'1000000','border':'1px solid #eee'});
    })
    $("body").on("mouseout", ".beRewarded", function() {
       	$(".beRewarded").eq($(this).attr('attrbix')).css({'z-index':'1','border':'0'})
    })
})*/
import { communication } from "../../data/getAxios";
export default {
  inject: ["reload"], //注入依赖
  data() {
    return {
      saveNavFlag: false,
      titleName: "",
      switchList: [],
      timeIndex: -1,
      timeNum: 0,
      imgIp: "",
      // imgIp: 'http://10.33.66.86:9999/download/userImage/'+localStorage.getItem("loginpersonId")+".jpg",
      clickNum: 0,
      leftbtnshow: false,
      rightbtnshow: true,
      sfzsInfo: false,
      jzjgInfo: false,
      jzjgSpan: "",
      zyrcInfo: false,
      timeLine: [],
      allowDrEndIndex: "",
      allowDrIndex: "",
      allowDrDelete: "",
      allowDropArr: [],
      allowDroType: [],
      allowDroNameArr: [],
      allowDroNameDelete: "",
      allowDroTypeDelete: "",
      domeDelete: "",
      daohang: true,
      lvli: true,
      jiangli: true,
      xunli: true,
      zhanyou: true,
      data: [],
      value1: false,
      value2: false,
      value3: false,
      value4: false,
      value5: false,
      dialogVisible: false,
      settingList: [],
      menuId1: "",
      menuId2: "",
      menuId3: "",
      cardDataList: [],
      dutyDataList: [],
      policeDataList: [],
      AllDataList: [],
      tabsData: [],
      selectDataList: [],
      jlTable: [],
      bonusPenalty: [],
      imgArr: [],
      qbNav: "0",
      initData: [],
      yxlistData1: [],
      qblistData2: [],
      ulWidth: 0,
      divArr: [],
      delVisible: false,
      policeRankImg: "", //警衔图片
      leaveForm: {
        name: "",
        depart: "",
        unit: "",
        birth: "",
        userId: "",
        jobTime: "",
        job: "",
        level: "",
        school: "",
        img: "",
        age: 0
      },
      wdjcTable: [],
      activities: [],
      myArmsData: [], //我的战友
      // 我的训历
      xlTable: [],
      navigationListCon: []
    };
  },
  created() {
    this.getNavInfo(); //获取我的导航
    this.ready();
    this.getwdzyData();
    this.getwdxlData(); //我的训历
    this.getMedal(); //勋章信息
    this.iconList();
    this.init();
  },
  mounted() {
    var self = this;
    self.myBonusPenalty(); //我的奖惩
    self.getMyresume(); //我的履历
    this.getTimeline(); //时间轴
    // $('.installList').get(0).style.minHeight=$('.content').height() - 214 + 'px';
  },
  computed: {
    listTemp: function() {
      var imgArr = this.imgArr;
      var arrTemp = [];
      var index = 0;
      var sectionCount = 3;
      for (var i = 0; i < imgArr.length; i++) {
        index = parseInt(i / sectionCount);
        if (arrTemp.length <= index) {
          arrTemp.push([]);
        }
        arrTemp[index].push(imgArr[i]);
      }
      return arrTemp;
    }
  },
  directives: {
    drag: {
      bind: function(el, binding) {
        let self = this;
        const deltRange = (binding.arg || 3) * 1;
        el.onmousedown = function(e) {
          let startPos = { x: e.pageX, y: e.pageY };
          document.onmousemove = function(ee) {
            el.style.cursor = "-webkit-grab";
            let endPos = {
              x: ee.pageX,
              y: ee.pageY
            };
            binding.value(startPos, endPos);
            startPos = endPos;
            ee.preventDefault();
          };
          document.onmouseup = function(ee) {
            el.style.cursor = "default";
            document.onmousemove = null;
            document.onmouseup = null;
            ee.preventDefault();
          };
          e.stopPropagation();
        };
      }
    }
  },
  methods: {
    //获取证书小图标
    iconList() {
      var self = this;
      const _obj = {
        url: self.global.serverPath8 + "/personnel/overview/getProfessionals",
        isGet: true
      };
      communication(_obj).then(
        res => {
          if (res.code == 0) {
            if (res.data.instructor.status) {
              self.sfzsInfo = true;
            }
            if (res.data.instructor.status) {
              self.jzjgInfo = true;
              self.jzjgSpan =
                "兼职教官：" + res.data.instructor.getProfessionalsName;
            }
            if (res.data.personnel.status) {
              self.zyrcInfo = true;
            }
          } else {
            if (res.code != 1) {
              self.$message.error(res.message);
            }
          }
        },
        function(res) {
          self.$message.error("请求失败");
        }
      );
    },
    ready() {
      var option = "";
      var self = this;
      const _obj = {
        url: self.global.serverPath8 + "/personnel/overview/getUserSetting",
        isGet: true
      };
      communication(_obj).then(
        res => {
          for (var i = 0; i < res.data.length; i++) {
            var obj = {};
            var obdSwitch = {};
            if (res.data[i].displayId == "我的导航") {
              self.allowDropArr[i] = "我的导航";
              self.allowDroNameArr[i] = "value1";
              obj.label = "我的导航";
              obdSwitch.is = "value1";
              self.divArr[i] = $(".install>div>div").eq(0);
              self.data[i] = obj;
              if (res.data[i].displayStatus == "true") {
                self.daohang = true;
                self.allowDroType[i] = 0;
                self.value1 = true;
                obdSwitch.derail = true;
              } else {
                self.daohang = false;
                self.allowDroType[i] = 1;
                self.value1 = false;
                obdSwitch.derail = false;
              }
              self.switchList[i] = obdSwitch;
            } else if (res.data[i].displayId == "我的履历") {
              self.allowDropArr[i] = "我的履历";
              self.allowDroNameArr[i] = "value2";
              obj.label = "我的履历";
              self.data[i] = obj;
              self.divArr[i] = $(".install>div>div").eq(1);
              obdSwitch.is = "value2";
              if (res.data[i].displayStatus == "true") {
                self.lvli = true;
                self.allowDroType[i] = 0;
                self.value2 = true;
                obdSwitch.derail = true;
              } else {
                self.lvli = false;
                self.allowDroType[i] = 1;
                self.value2 = false;
                obdSwitch.derail = false;
              }
              self.switchList[i] = obdSwitch;
            } else if (res.data[i].displayId == "我的奖励") {
              self.allowDropArr[i] = "我的奖励";
              self.allowDroNameArr[i] = "value3";
              obj.label = "我的奖励";
              self.data[i] = obj;
              self.divArr[i] = $(".install>div>div").eq(2);
              obdSwitch.is = "value3";
              self.switchList[i] = obdSwitch;
              if (res.data[i].displayStatus == "true") {
                self.jiangli = true;
                self.allowDroType[i] = 0;
                self.value3 = true;
                obdSwitch.derail = true;
              } else {
                self.jiangli = false;
                self.allowDroType[i] = 1;
                self.value3 = false;
                obdSwitch.derail = false;
              }
              self.switchList[i] = obdSwitch;
            } else if (res.data[i].displayId == "我的训历") {
              self.allowDropArr[i] = "我的训历";
              self.allowDroNameArr[i] = "value4";
              obj.label = "我的训历";
              self.data[i] = obj;
              self.divArr[i] = $(".install>div>div").eq(3);
              obdSwitch.is = "value4";
              self.switchList[i] = obdSwitch;
              if (res.data[i].displayStatus == "true") {
                self.xunli = true;
                self.allowDroType[i] = 0;
                self.value4 = true;
                obdSwitch.derail = true;
              } else {
                self.xunli = false;
                self.allowDroType[i] = 1;
                self.value4 = false;
                obdSwitch.derail = false;
              }
              self.switchList[i] = obdSwitch;
            } else if (res.data[i].displayId == "我的战友") {
              self.allowDropArr[i] = "我的战友";
              self.allowDroNameArr[i] = "value5";
              obj.label = "我的战友";
              self.data[i] = obj;
              self.divArr[i] = $(".install>div>div").eq(4);
              obdSwitch.is = "value5";
              self.switchList[i] = obdSwitch;
              if (res.data[i].displayStatus == "true") {
                self.zhanyou = true;
                self.allowDroType[i] = 0;
                self.value5 = true;
                obdSwitch.derail = true;
              } else {
                self.zhanyou = false;
                self.allowDroType[i] = 1;
                self.value5 = false;
                obdSwitch.derail = false;
              }
              self.switchList[i] = obdSwitch;
            }
          }
          $(".install>div").empty();
          for (var k = 0; k < res.data.length; k++) {
            $(".install>div").append(self.divArr[k]);
          }
          // console.log(self.switchList);
          // console.log(this.allowDroNameArr);
          // console.log(this.allowDroType);
          // console.log(this.allowDropArr);
        },
        function(res) {
          self.$message.error("请求失败");
        }
      );
    },
    dialogVisiBtn() {
      this.dialogVisible = false;
      var that = this;
      that.settingList = [];
      for (var i = 0; i < that.data.length; i++) {
        var setList = {};
        if (that.allowDropArr[i] == "我的导航") {
          setList.displayId = "我的导航";
          setList.displayOrder = i;
          setList.displayStatus = that.allowDroType[i] == "0" ? true : false;
          setList.personId = localStorage.getItem("loginpersonId");
          that.settingList.push(setList);
        } else if (that.allowDropArr[i] == "我的履历") {
          setList.displayId = "我的履历";
          setList.displayOrder = i;
          setList.displayStatus = that.allowDroType[i] == "0" ? true : false;
          setList.personId = localStorage.getItem("loginpersonId");
          that.settingList.push(setList);
        } else if (that.allowDropArr[i] == "我的奖励") {
          setList.displayId = "我的奖励";
          setList.displayOrder = i;
          setList.displayStatus = that.allowDroType[i] == "0" ? true : false;
          setList.personId = localStorage.getItem("loginpersonId");
          that.settingList.push(setList);
        } else if (that.allowDropArr[i] == "我的训历") {
          setList.displayId = "我的训历";
          setList.displayOrder = i;
          setList.displayStatus = that.allowDroType[i] == "0" ? true : false;
          setList.personId = localStorage.getItem("loginpersonId");
          that.settingList.push(setList);
        } else if (that.allowDropArr[i] == "我的战友") {
          setList.displayId = "我的战友";
          setList.displayOrder = i;
          setList.displayStatus = that.allowDroType[i] == "0" ? true : false;
          setList.personId = localStorage.getItem("loginpersonId");
          that.settingList.push(setList);
        }
      }
      // that.$http.post('http://192.168.8.114:8099/overview/saveUserSetting', {
      const obj = {
        url: that.global.serverPath8 + "/personnel/overview/saveUserSetting",
        postData: {
          userCustomSettingsVos: that.settingList
        }
      };
      communication(obj).then(res => {
        if (res.code == 0) {
          that.$message.success("保存成功");
        } else {
          that.$message.error("保存失败");
        }
      });
      // that.$http.post(that.global.serverPath8 + '/personnel/overview/saveUserSetting', {
      // 	"userCustomSettingsVos":that.settingList
      // }).then(function(res) {
      // 	console.log(res);
      // 	if(res.body.code == 0){
      // 		that.$message.success('保存成功')
      // 	}else{
      // 		that.$message.error('保存失败')
      // 	}
      // }, function(res) {
      //     that.$message.error('保存失败')
      // });
    },
    commonMove(distance) {
      let liBox = $(".liBox"),
        liWidth = 24 * 6,
        fixedWidth = parseInt(liBox.parent().width()),
        maxWidth = parseInt(liBox.width()) - fixedWidth,
        startX = parseInt(liBox.css("left")),
        endX =
          startX +
          (distance === "-"
            ? -liWidth * 2
            : distance === "+" ? liWidth * 2 : distance * 1);
      $(".timeLeft,.timeRight").show();
      if (endX >= 0) {
        endX = 0;
        $(".timeLeft").hide();
      }
      if (endX <= -maxWidth) {
        endX = -maxWidth;
        $(".timeRight").hide();
      }
      liBox[typeof distance === "string" ? "animate" : "css"]({ left: endX });
    },
    timeLeft() {
      this.commonMove("+");
    },
    timeMove(moveStart, moveEnd) {
      this.commonMove(moveEnd.x - moveStart.x);
    },
    timeRight() {
      this.commonMove("-");
    },
    // 获取时间线数据
    getTimeline() {
      var self = this;
      const _obj = {
        url: self.global.serverPath8 + "/personnel/overview/getTimeAxis",
        isGet: true
      };
      communication(_obj)
        .then(res => {
          if (res.code == 0) {
            const classNames = [
                "",
                "redDiv",
                "blueDiv",
                "orangeDiv",
                "greenDiv"
              ],
              calc = function(a, b) {
                if (!a) {
                  return b.getMonth() + 1;
                }
                return Math.abs(
                  a.getFullYear() * 12 +
                    a.getMonth() -
                    (b.getFullYear() * 12 + b.getMonth())
                );
              };
            let result = res.data || [],
              police,
              party;
            result.map(function(a) {
              let atime = new Date((a.dealTime || "").replace(/\./g, "-"));
              a.time = atime;
              if (a.status == 1) {
                //如果是入党
                party = a;
              }
              if (a.status == 2) {
                //如果是入警
                police = a;
              }
              return a;
            });
            if (
              police &&
              party &&
              police.time.getTime() > party.time.getTime()
            ) {
              //如果如果是先入党后入警
              party.time = police.time;
            }
            result.sort(function(a, b) {
              //排序
              return a.time.getTime() - b.time.getTime();
            });
            const renderTimeLine = function() {
              let option = "";
              //一屏显示十年(除以120)，但是最小是1366屏幕的（1366屏幕monthDistance正好是10个像素）
              let monthDistance =
                parseInt(
                  $(".liBox")
                    .parent()
                    .width()
                ) /
                (10 * 12);
              monthDistance = monthDistance > 10 ? monthDistance : 10;
              let months = 0,
                startYear = result[0].time.getFullYear();
              for (let i = 0; i < result.length; i++) {
                let ll = calc((result[i - 1] || {}).time, result[i].time),
                  del = 0;
                if (i == result.length - 1) {
                  //如果是最以后一位，为了避免最后一个上有标签，所以延长时间线，以免遮挡
                  del = Math.ceil(150 / monthDistance);
                  //del = del+12-(del + result[i].time.getMonth())%12;
                  ll = ll + del;
                }
                //如果入警和入党在同一时间点(包括入党在入警之前)
                let top = 50;
                if (result[i].status == 1 && ll == 0) {
                  top = 46;
                }
                if (
                  result[i].status == 2 &&
                  result[i + 1] &&
                  result[i + 1].status == 1 &&
                  calc(result[i].time, result[i + 1].time) == 0
                ) {
                  top = 54;
                }
                option +=
                  '<li class="liBoxList liBoxaward ' +
                  classNames[result[i].status] +
                  '" style="width:' +
                  ll * monthDistance +
                  'px">\
									<div class="roundDot1 beRewarded" attrbix="' +
                  i +
                  '" title="' +
                  result[i].thing +
                  '" style="' +
                  (result[i].status == 1 || result[i].status == 4
                    ? "top"
                    : "bottom") +
                  ":0;left:" +
                  ((ll - del) * monthDistance - 30) +
                  'px">\
										<div class="awardImg"></div>\
										<div class="awardCon">\
											<p>' +
                  result[i].dealTime +
                  "</p>\
											<p>" +
                  result[i].thing +
                  '</p>\
										</div>\
									</div>\
									<div class="graduate">' +
                  (function() {
                    let s = "";
                    for (let ii = 0; ii < ll; ii++) {
                      months++;
                      if (months % 12 === 1) {
                        //减去12是减去年份标签所占的宽度的一半
                        s +=
                          '<span style="height:18px;margin-right:' +
                          (monthDistance - 1) +
                          'px;"><span style="left:' +
                          (ii * monthDistance - 12) +
                          'px;">' +
                          startYear +
                          "</span></span>";
                        startYear++;
                      } else {
                        s +=
                          '<span style="margin-top:4px;margin-bottom: 4px;margin-right:' +
                          (monthDistance - 1) +
                          'px;"></span>';
                      }
                    }
                    return s;
                  })() +
                  '</div>\
									<div class="coordinateAxis" style="top:' +
                  top +
                  "%;left:" +
                  ((ll - del) * monthDistance - 6) +
                  'px;"></div>\
								</li>';
                //上面减去6是减去圆点所占的宽度的一半再加1
              }
              option +=
                '<div class="arrowhead"><i class="iconfont iconyoujiantou"></i></div>';
              $(".liBox")
                .html(option)
                .css({ left: 0 });
              $(".timeRight").show();
              $(".timeLeft").hide();
            };
            renderTimeLine();
            /*$('.liBox').on('click','.beRewarded',function(e){
						$(this).parents('.liBox').find('.beRewarded').removeClass('active');
						$(this).addClass('active');
					});*/
            $("body").on("mouseover", ".beRewarded", function(e) {
              $(this).addClass("active");
            });
            $("body").on("mouseout", ".beRewarded", function(e) {
              $(this).removeClass("active");
            });
            $(window).resize(function() {
              setTimeout(function() {
                renderTimeLine();
              }, 100);
            });
            /*
					var listArr = [];
					var firstDate = parseInt(res.data[0].year);
					var myDate = new Date();
					var lastDate = myDate.getFullYear();
					var yeatLength = lastDate-firstDate+1;			//总年份
					var awardArrName = [];							//嘉奖名称
					var yearArr = [];								//年份
					var awardArr = [];								//获嘉奖年份
					var result = [];								//拼接dome用
					var resultTypeList = [];						//按钮颜色
					var resultType = [];
					var resultTypeCon = [];
					var resultNameArr = [];
					var awardFrequency = [];
					var awardFrequencyNum = 0;
					var date = [];
					var dateCon = [];
					var dateList = [];
					for(var k=0;k<yeatLength;k++){
						yearArr.push(firstDate+k)
					};
					for(var i=0;i<res.data.length;i++){
						awardArr.push(parseInt(res.data[i].dealTime.substring(4,0)));
						awardArrName.push(res.data[i].thing);
						resultType.push(res.data[i].status);
						date.push(res.data[i].dealTime);
					};
					var currentMonth = Math.ceil((myDate.getMonth()+1)/2);
					$(".liBox").css({'width':yearArr.length*120+'px'});
					$(".lineBox").css({'width':(yearArr.length)*120+'px','display':'block'});
					if($(".liBox").width() > $(".timeY").width()){
						$('.timeRight').show()
					};
					// console.log(yearArr[0]);
					// console.log(awardArr[0]);
					for(var t=0;t<yearArr.length;t++){
						if($.inArray(yearArr[t],awardArr) == -1){
							result.push(0);
							resultNameArr.push(' ');
							resultTypeList.push(' ');
							dateList.push(' ');
						}else{
							awardFrequency = [];
							awardFrequencyNum = 0;
							resultTypeCon = [];
							dateCon = [];
							for(var p=0;p<awardArr.length;p++){
								if(yearArr[t] == awardArr[p]){
									awardFrequencyNum += 1;
									awardFrequency.push(awardArrName[p]);
									resultTypeCon.push(resultType[p]);
									dateCon.push(date[p]);
								}
							};
							resultNameArr.push(awardFrequency);
							result.push(awardFrequencyNum);
							resultTypeList.push(resultTypeCon);
							dateList.push(dateCon);
						};
					};
					if(yearArr[0] > awardArr[0]){
						result[0] += 1;
						resultNameArr[0].unshift(res.data[0].thing);
						resultTypeList[0].unshift(res.data[0].status);
						dateList[0].unshift(res.data[0].dealTime);
					};
					var option = '';
					for(var b=0;b<result.length;b++){
						if(result[b] == 0){
							option+='<li class="liBoxList"><div class="roundDot0"></div><div class="graduate"><span></span><span></span><span></span><span></span><span></span><span></span></div><span class="listYear">'+ yearArr[b] +'</span></li>'
						}else if(result[b] == 1){
							self.timeIndex += 1;
							option+='<li class="liBoxList liBoxaward '+ (parseInt(resultTypeList[b][0]) == 1?'redDiv':parseInt(resultTypeList[b][0]) == 2?'blueDiv':parseInt(resultTypeList[b][0]) == 3?'orangeDiv':'yellowDiv') +'"><div class="roundDot1 beRewarded" attrBix="'+ self.timeIndex +'" title="'+ resultNameArr[b][0] +'"><div class="awardImg"></div><div class="awardCon"><p>'+ dateList[b][0] +'</p><p>'+ resultNameArr[b][0] +'</p></div></div><div class="coordinateAxis '+ (parseInt(resultTypeList[b][0]) == 1?'red':parseInt(resultTypeList[b][0]) == 2?'blue':parseInt(resultTypeList[b][0]) == 3?'orange':'yellow') +'"></div><div class="graduate"><span></span><span></span><span></span><span></span><span></span><span></span></div><span class="listYear">'+ yearArr[b] +'</span></li>'
						}else{
							option+='<li class="liBoxList liBoxaward"><div class="roundDotMultiple"></div><div class="graduate"><span></span><span></span><span></span><span></span><span></span><span></span></div><span class="listYear">'+ yearArr[b] +'</span>'
							for(var y=0;y<result[b];y++){
								self.timeIndex += 1;
								option+='<div attrBix="'+ self.timeIndex +'" title="'+ resultNameArr[b][y] +'" class="awardMinute beRewarded '+ (parseInt(resultTypeList[b][y]) == 1?'redDiv':parseInt(resultTypeList[b][y]) == 2?'blueDiv':parseInt(resultTypeList[b][y]) == 3?'orangeDiv':'yellowDiv') +'"><div class="awardImg"></div><div class="awardCon"><p>'+ dateList[b][y] +'</p><p>'+ resultNameArr[b][y] +'<p></div></div><div class="coordinateAxis '+ (parseInt(resultTypeList[b][y]) == 1?'red':parseInt(resultTypeList[b][y]) == 2?'blue':parseInt(resultTypeList[b][y]) == 3?'orange':'yellow') +'"></div>';
							};
							option+= '</li>'
						};
					};
					$(".liBox").append(option);
					$(".liBoxList").eq(result.length-1).append('<div class="arrowhead"><i class="iconyoujiantou  iconfont"></i></div>');
					for(var g=0;g<res.data.length;g++){
						if(res.data[g].status == 3){
							$(".beRewarded").eq(g).css({'bottom':'-5px'});
						}else{
							$(".beRewarded").eq(g).css({'top':'-5px'});
						}
					};
					self.liLength = -Math.ceil(Math.ceil(($('.liBox').width() -  $(".timeY").width())/$(".liBoxList").width())/2);
					for(var g=0;g<result.length;g++){
						if(result[g] == 1){
							$(".liBoxList").eq(g).find('div').eq(0).css({'left':'50%','transform':'translateX(-50%)'});
							$(".liBoxList").eq(g).find('div').eq(3).css({'left':'50%','top':'50%','transform':'translate(-50%,-50%)'});
						}else if(result[g] == 2){
								$(".liBoxList").eq(g).find('div').eq(5).css({'left':'25px'});
								$(".liBoxList").eq(g).find('div').eq(9).css({'left':'initial','right':'25px'});
								$(".liBoxList").eq(g).find('div').eq(3).css({'left':'40px'});
								$(".liBoxList").eq(g).find('div').eq(6).css({'left':'40px'});
								$(".liBoxList").eq(g).find('div').eq(2).css({'right':'40px'});
								$(".liBoxList").eq(g).find('div').eq(4).css({'left':'40px'});

						}else if(result[g] == 3){
							$(".liBoxList").eq(g).find('div').eq(2).css({'right':'60px'});
							$(".liBoxList").eq(g).find('div').eq(6).css({'left':'50%','transform':'translateX(-50%)'});
							$(".liBoxList").eq(g).find('div').eq(5).css({'left':'10px'});
							$(".liBoxList").eq(g).find('div').eq(9).css({'left':'50%','top':'50%','transform':'translate(-50%,-50%)'});
							$(".liBoxList").eq(g).find('div').eq(10).css({'right':'-35px'});
							$(".liBoxList").eq(g).find('div').eq(13).css({'right':'10px'});
						}else if(result[g] == 4){

							$(".liBoxList").eq(g).find('div').eq(2).css({'left':'-40px'});
							$(".liBoxList").eq(g).find('div').eq(6).css({'left':'0px'});
							$(".liBoxList").eq(g).find('div').eq(10).css({'left':'40px'});
							$(".liBoxList").eq(g).find('div').eq(14).css({'left':'80px'});

							$(".liBoxList").eq(g).find('div').eq(5).css({'left':'20px'});
							$(".liBoxList").eq(g).find('div').eq(9).css({'left':'40px'});
							$(".liBoxList").eq(g).find('div').eq(13).css({'left':'60px'});
							$(".liBoxList").eq(g).find('div').eq(17).css({'left':'80px'});
						}else if(result[g] == 5){
							$(".liBoxList").eq(g).find('div').eq(2).css({'left':'-30px'});
							$(".liBoxList").eq(g).find('div').eq(6).css({'left':'-10px'});
							$(".liBoxList").eq(g).find('div').eq(10).css({'left':'10px'});
							$(".liBoxList").eq(g).find('div').eq(14).css({'left':'30px'});
							$(".liBoxList").eq(g).find('div').eq(18).css({'left':'50px'});

							$(".liBoxList").eq(g).find('div').eq(5).css({'left':'20px'});
							$(".liBoxList").eq(g).find('div').eq(9).css({'left':'40px'});
							$(".liBoxList").eq(g).find('div').eq(13).css({'left':'60px'});
							$(".liBoxList").eq(g).find('div').eq(17).css({'left':'80px'});
							$(".liBoxList").eq(g).find('div').eq(21).css({'left':'100px'});
						}else if(result[g] == 6){
							$(".liBoxList").eq(g).find('div').eq(2).css({'left':'-40px'});
							$(".liBoxList").eq(g).find('div').eq(6).css({'left':'-20px'});
							$(".liBoxList").eq(g).find('div').eq(10).css({'left':'0'});
							$(".liBoxList").eq(g).find('div').eq(14).css({'left':'20px'});
							$(".liBoxList").eq(g).find('div').eq(18).css({'left':'40px'});
							$(".liBoxList").eq(g).find('div').eq(22).css({'left':'60px'});

							$(".liBoxList").eq(g).find('div').eq(5).css({'left':'15px'});
							$(".liBoxList").eq(g).find('div').eq(9).css({'left':'30px'});
							$(".liBoxList").eq(g).find('div').eq(13).css({'left':'45px'});
							$(".liBoxList").eq(g).find('div').eq(17).css({'left':'60px'});
							$(".liBoxList").eq(g).find('div').eq(21).css({'left':'75px'});
							$(".liBoxList").eq(g).find('div').eq(25).css({'left':'90px'});
						}else if(result[g] == 7){

							$(".liBoxList").eq(g).find('div').eq(2).css({'left':'-40px'});
							$(".liBoxList").eq(g).find('div').eq(6).css({'left':'-20px'});
							$(".liBoxList").eq(g).find('div').eq(10).css({'left':'0px'});
							$(".liBoxList").eq(g).find('div').eq(14).css({'left':'20px'});
							$(".liBoxList").eq(g).find('div').eq(18).css({'left':'40px'});
							$(".liBoxList").eq(g).find('div').eq(22).css({'left':'60px'});
							$(".liBoxList").eq(g).find('div').eq(26).css({'left':'80px'});

							$(".liBoxList").eq(g).find('div').eq(5).css({'left':'5px'});
							$(".liBoxList").eq(g).find('div').eq(9).css({'left':'20px'});
							$(".liBoxList").eq(g).find('div').eq(13).css({'left':'35px'});
							$(".liBoxList").eq(g).find('div').eq(17).css({'left':'50px'});
							$(".liBoxList").eq(g).find('div').eq(21).css({'left':'65px'});
							$(".liBoxList").eq(g).find('div').eq(25).css({'left':'80px'});
							$(".liBoxList").eq(g).find('div').eq(29).css({'left':'95px'});
						}else if(result[g] == 8){
							$(".liBoxList").eq(g).find('div').eq(2).css({'left':'-40px'});
							$(".liBoxList").eq(g).find('div').eq(6).css({'left':'-20px'});
							$(".liBoxList").eq(g).find('div').eq(10).css({'left':'0px'});
							$(".liBoxList").eq(g).find('div').eq(14).css({'left':'20px'});
							$(".liBoxList").eq(g).find('div').eq(18).css({'left':'40px'});
							$(".liBoxList").eq(g).find('div').eq(22).css({'left':'60px'});
							$(".liBoxList").eq(g).find('div').eq(26).css({'left':'80px'});
							$(".liBoxList").eq(g).find('div').eq(30).css({'left':'100px'});

							$(".liBoxList").eq(g).find('div').eq(5).css({'left':'5px'});
							$(".liBoxList").eq(g).find('div').eq(9).css({'left':'20px'});
							$(".liBoxList").eq(g).find('div').eq(13).css({'left':'35px'});
							$(".liBoxList").eq(g).find('div').eq(17).css({'left':'50px'});
							$(".liBoxList").eq(g).find('div').eq(21).css({'left':'65px'});
							$(".liBoxList").eq(g).find('div').eq(25).css({'left':'80px'});
							$(".liBoxList").eq(g).find('div').eq(29).css({'left':'95px'});
							$(".liBoxList").eq(g).find('div').eq(33).css({'left':'110px'});
						}else if(result[g] == 9){
							console.log($(".liBoxList").eq(g).find('div'));
							$(".liBoxList").eq(g).find('div').eq(2).css({'left':'-45px'});
							$(".liBoxList").eq(g).find('div').eq(6).css({'left':'-30px'});
							$(".liBoxList").eq(g).find('div').eq(10).css({'left':'-15px'});
							$(".liBoxList").eq(g).find('div').eq(14).css({'left':'0px'});
							$(".liBoxList").eq(g).find('div').eq(18).css({'left':'15px'});
							$(".liBoxList").eq(g).find('div').eq(22).css({'left':'30px'});
							$(".liBoxList").eq(g).find('div').eq(26).css({'left':'45px'});
							$(".liBoxList").eq(g).find('div').eq(30).css({'left':'60px'});
							$(".liBoxList").eq(g).find('div').eq(34).css({'left':'75px'});

							$(".liBoxList").eq(g).find('div').eq(5).css({'left':'5px'});
							$(".liBoxList").eq(g).find('div').eq(9).css({'left':'17px'});
							$(".liBoxList").eq(g).find('div').eq(13).css({'left':'29px'});
							$(".liBoxList").eq(g).find('div').eq(17).css({'left':'41px'});
							$(".liBoxList").eq(g).find('div').eq(21).css({'left':'53px'});
							$(".liBoxList").eq(g).find('div').eq(25).css({'left':'65px'});
							$(".liBoxList").eq(g).find('div').eq(29).css({'left':'77px'});
							$(".liBoxList").eq(g).find('div').eq(33).css({'left':'89px'});
							$(".liBoxList").eq(g).find('div').eq(37).css({'left':'101px'});
						};
					};
					if(resultNameArr[0][0] == '入党'&&result[0] >= 2){
						$(".liBoxList").eq(0).find('div').eq(2).css({'left':'50%','transform':'translateX(-50%)','top':'-5px'});
						$(".liBoxList").eq(0).find('div').eq(6).css({'left':'50%','transform':'translateX(-50%)','bottom':'-5px','top':'inherit'});
						$(".liBoxList").eq(0).find('div').eq(5).css({'left':'50%','transform':'translate(-50%,-50%)','top':'45%'});
						$(".liBoxList").eq(0).find('div').eq(9).css({'left':'50%','transform':'translate(-50%,-50%)','top':'55%'});
						console.log(1111111111111111111111);
					}
					*/
          }
        })
        .catch(err => {
          this.$message.warning("获取时间轴数据失败！");
        });
    },
    getwdzyData() {
      var self = this;
      const _obj = {
        url:
          self.global.serverPath8 +
          "/personnel/armyFriend/getRecentArmyFriends",
        isGet: true
      };
      communication(_obj).then(
        res => {
          var trees = res.data;
          if (res.code == 0) {
            self.myArmsData = res.data;
          } else {
            if (res.code != 1) {
              self.$message.warning(res.error);
            }
          }
        },
        function(res) {
          self.$message.error("请求失败！");
        }
      );
    },
    //获取我的训历
    getwdxlData() {
      var self = this;
      const _obj = {
        url: self.global.serverPath8 + "/personnel/overview/getTraining",
        isGet: true
      };
      communication(_obj).then(
        res => {
          if (res.code == 0) {
            self.xlTable = res.data;
          }
        },
        function(res) {
          self.$message.error("请求失败！");
        }
      );
    },
    decideExhibit($event, num) {
      if (this.allowDropArr[num] == "我的导航") {
        if ($event) {
          this.allowDroType[num] = 0;
          this.daohang = true;
        } else {
          this.allowDroType[num] = 1;
          this.daohang = false;
        }
      } else if (this.allowDropArr[num] == "我的履历") {
        if ($event) {
          this.allowDroType[num] = 0;
          this.lvli = true;
        } else {
          this.allowDroType[num] = 1;
          this.lvli = false;
        }
      } else if (this.allowDropArr[num] == "我的奖励") {
        if ($event) {
          this.allowDroType[num] = 0;
          this.jiangli = true;
        } else {
          this.allowDroType[num] = 1;
          this.jiangli = false;
        }
      } else if (this.allowDropArr[num] == "我的训历") {
        if ($event) {
          this.allowDroType[num] = 0;
          this.xunli = true;
        } else {
          this.allowDroType[num] = 1;
          this.xunli = false;
        }
      } else if (this.allowDropArr[num] == "我的战友") {
        if ($event) {
          this.allowDroType[num] = 0;
          this.zhanyou = true;
        } else {
          this.allowDroType[num] = 1;
          this.zhanyou = false;
        }
      }
    },
    valueFun($event, num) {
      this.decideExhibit($event, num);
    },
    handleDragStart(node, ev) {
      for (var i = 0; i < this.allowDropArr.length; i++) {
        if (node.data.label == this.allowDropArr[i]) {
          this.allowDrIndex = i;
        }
      }
      this.allowDrDelete = this.allowDropArr[this.allowDrIndex];
      this.allowDroTypeDelete = this.allowDroType[this.allowDrIndex];
      this.allowDroNameDelete = this.allowDroNameArr[this.allowDrIndex];
      this.allowDropArr.splice(this.allowDrIndex, 1);
      this.allowDroType.splice(this.allowDrIndex, 1);
      this.allowDroNameArr.splice(this.allowDrIndex, 1);
      this.domeDelete = $(".install>div>div").eq(this.allowDrIndex);
    },
    handleDragEnd(draggingNode, dropNode, dropType, ev) {
      for (var i = 0; i < this.allowDropArr.length; i++) {
        if (dropNode.data.label == this.allowDropArr[i]) {
          this.allowDrEndIndex = i;
        }
      }
      $(".install>div>div")
        .eq(this.allowDrIndex)
        .remove();
      $(".install>div>div")
        .eq(this.allowDrEndIndex)
        .before(this.domeDelete);
      this.allowDroNameArr.splice(
        this.allowDrEndIndex,
        0,
        this.allowDroNameDelete
      );
      this.allowDroType.splice(
        this.allowDrEndIndex,
        0,
        this.allowDroTypeDelete
      );
      this.allowDropArr.splice(this.allowDrEndIndex, 0, this.allowDrDelete);
      for (var k = 0; k < this.allowDropArr.length; k++) {
        if (this.allowDropArr[k] == "我的导航") {
          if (this.allowDroType[k] == 0) {
            this.value1 = true;
          } else {
            this.value1 = false;
          }
        }
        if (this.allowDropArr[k] == "我的履历") {
          if (this.allowDroType[k] == 0) {
            this.value2 = true;
          } else {
            this.value2 = false;
          }
        }
        if (this.allowDropArr[k] == "我的奖励") {
          if (this.allowDroType[k] == 0) {
            this.value3 = true;
          } else {
            this.value3 = false;
          }
        }
        if (this.allowDropArr[k] == "我的训历") {
          if (this.allowDroType[k] == 0) {
            this.value4 = true;
          } else {
            this.value4 = false;
          }
        }
        if (this.allowDropArr[k] == "我的战友") {
          if (this.allowDroType[k] == 0) {
            this.value5 = true;
          } else {
            F;
            this.value5 = false;
          }
        }
      }
      for (var d = 0; d < this.allowDroType.length; d++) {
        if (this.allowDroType[d] == 0) {
          $(".switchList .el-switch")
            .eq(d)
            .addClass("is-checked");
        } else {
          $(".switchList .el-switch")
            .eq(d)
            .removeClass("is-checked");
        }
      }
    },
    handleNodeClick() {},
    allowDrop(draggingNode, dropNode, type) {
      if (draggingNode.level === dropNode.level) {
        if (draggingNode.parent.id === dropNode.parent.id) {
          return type === "prev";
        }
      } else {
        // 不同级进行处理
      }
    },
    handleDragEnter(draggingNode, dropNode, ev) {},
    handleDragLeave(draggingNode, dropNode, ev) {},
    handleDragOver(draggingNode, dropNode, ev) {},
    handleDrop(draggingNode, dropNode, dropType, ev) {},
    handleClose(done) {
      done();
    },
    // 确定添加导航
    saveNav() {
      var self = this;
      self.saveNavFlag = true;
      var selectArr = [];
      var objselect = {};
      for (var i = 0; i < self.selectDataList.length; i++) {
        if (self.selectDataList[i].title) {
          var obj = {};
          // 判断当前菜单是否是新添加的
          if (self.selectDataList[i].menuId) {
            obj.menuId = self.selectDataList[i].menuId;
          } else {
            obj.menuId = self.selectDataList[i].id.toString();
          }
          obj.title = self.selectDataList[i].title;
          obj.icon =
            self.selectDataList[i].newIcon || self.selectDataList[i].icon;
          obj.url =
            self.selectDataList[i].url || "/" + self.selectDataList[i].index;
          obj.parentId = self.selectDataList[i].parentId;
          obj.status = 1;
          // obj.parsonId = self.selectDataList[i].parsonId;
          selectArr.push(obj);
        } else {
          selectArr.push(self.selectDataList[i]);
        }
      }
      objselect.selectDataList = selectArr;
      const _obj = {
        url:
          self.global.serverPath8 + "/personnel/overview/updateMenuByPersonId",
        postData: {
          sendData: objselect
        },
        isGet: false
      };
      communication(_obj).then(res => {
        self.saveNavFlag = false;
        if (res.code == 0) {
          self.delVisible = false;
          self.getNavInfo();
          self.$message.success("设置导航成功！");
        } else {
          self.$message.error("添加导航失败！");
        }
      });
    },
    // 点击我的菜单添加按钮
    addBtn() {
      var self = this;
      self.delVisible = true;
      const _obj = {
        url:
          self.global.serverPath8 +
          "/personnel/overview/queryAllUserMenuByPersonId",
        isGet: true
      };
      communication(_obj).then(
        res => {
          if (res.code == 0) {
            var datas = res.data;
            self.navigationListCon = res.data.allMenuList;
            self.tabsData = [{ label: "全部", name: "0" }]; //先置空tabs标签分部
            // if(datas.allList.length>0){
            self.tabsData[0].subs = datas.allList;
            if (datas.allMenuList.length > 0) {
              for (var i = 0; i < datas.allMenuList.length; i++) {
                var objs = {};
                objs.label = datas.allMenuList[i].title;
                objs.name = (i + 1).toString();
                objs.subs = datas.allMenuList[i].subs;
                self.tabsData.push(objs);
              }
            }
            self.selectDataList = res.data.selectDataList;
            // console.log(self.selectDataList)
          }
        },
        function(res) {
          self.$message.error("请求失败");
        }
      );
    },
    // 页面加载获取已添加导航
    getNavInfo() {
      var self = this;
      const _obj = {
        url:
          self.global.serverPath8 +
          "/personnel/overview/queryAllUserMenuByPersonId",
        isGet: true
      };
      communication(_obj).then(
        res => {
          if (res.code == 0) {
            var eves = res.data;
            self.initData = []; //先置空已菜单
            if (eves.selectDataList.length > 0) {
              //已选菜单
              for (var i = 0; i < eves.selectDataList.length; i++) {
                var obj1 = {};
                obj1.oneI = eves.selectDataList[i].icon;
                obj1.linkTo = eves.selectDataList[i].url.replace("/", "");
                obj1.spanText = eves.selectDataList[i].title;
                obj1.id = eves.selectDataList[i].menuId;
                self.initData.push(obj1);
              }
            }
            // console.log(self.initData);
            self.selectDataList = res.data.selectDataList;
          }
        },
        function(res) {
          self.$message.error("请求失败");
        }
      );
    },
    myBonusPenalty() {
      var self = this;
      const _obj = {
        url: self.global.serverPath8 + "/personnel/overview/getRewards",
        isGet: true
      };
      communication(_obj)
        .then(res => {
          if (res.code == 0) {
            self.jlTable = res.data;
          } else {
            if (res.code != 1) {
              self.$message.warning(res.message);
            }
          }
        })
        .catch(err => {
          console.log("获取我的奖励失败！");
        });
    },
    getMyresume() {
      //我的履历
      var self = this;
      const _obj = {
        url: self.global.serverPath8 + "/personnel/overview/getRecord",
        isGet: true
      };
      communication(_obj)
        .then(res => {
          if (res.code == 0) {
            self.wdjcTable = res.data;
          } else {
            if (res.code != 1) {
              self.$message.warning(res.message);
            }
          }
        })
        .catch(err => {
          console.log("获取我的履历失败！");
        });
    },
    // 已添加导航
    removeIcon(item, index) {
      if (!item.newIcon) {
        item.newIcon = item.icon;
      }
      var self = this;
      var num = String(item.parentId).substring(0, 1);
      self.tabsData[0].subs.push(item);
      for (var i = 0; i < self.navigationListCon.length; i++) {
        if (num == String(self.navigationListCon[i].id).substring(0, 1)) {
          self.navigationListCon[i].subs.push(item);
        }
      }
      self.selectDataList.splice(index, 1);
    },
    delItem(dataList, item) {
      for (var i = 0; i < dataList.length; i++) {
        if (dataList[i].menuName == item.menuName) {
          return i;
        }
      }
    },
    // 点击导航右上角添加icon
    addIcon(item, index, flag) {
      var self = this;
      if (self.selectDataList.length > 7) {
        self.$message.warning("最多只能添加8个");
      } else {
        var ojbItem = item;
        var accept = {};
        for (var i = 1; i < self.tabsData.length; i++) {
          for (var k = 0; k < self.tabsData[i].subs.length; k++) {
            if (item.title == self.tabsData[i].subs[k].title) {
              self.tabsData[i].subs.splice(k, 1);
            }
          }
        }
        // console.log(self.tabsData);
        accept = item;
        self.selectDataList.push(accept);
        self.tabsData[0].subs.splice(index, 1);
      }
    },
    // 获取勋章信息
    getMedal() {
      var self = this;
      const _obj = {
        url: self.global.serverPath8 + "/personnel/overview/getRewardsImage",
        isGet: true
      };
      communication(_obj)
        .then(res => {
          if (res.code == 0) {
            self.imgArr = res.data;
          }
        })
        .catch(err => {
          console.log("获取勋章信息失败！");
        });
    },
    // 获取个人信息
    init() {
      var self = this;
      const _obj = {
        url: self.global.serverPath8 + "/personnel/overview/getUserInfo",
        isGet: true
      };
      communication(_obj)
        .then(res => {
          if (res.code == 0) {
            self.imgIp = res.data.policeImage; //警员照片
            if (res.data.policeRankImage != null) {
              self.policeRankImg = require("static/img/policeRank/" +
                res.data.policeRankImage); //警衔图片
            }
            self.leaveForm.name = res.data.userName; //名字
            self.leaveForm.depart = res.data.company;
            self.leaveForm.unit = res.data.deptName;
            self.leaveForm.birth = res.data.birthDate; //出生
            self.leaveForm.userId = res.data.policeNo; //警号
            self.leaveForm.jobTime = res.data.workingHours; //参加工作时间
            self.leaveForm.job = res.data.duty; //职务
            self.leaveForm.level = res.data.party; //政治面貌
            self.leaveForm.school = res.data.school; //毕业院校
            this.titleName = res.data.policeRank;
            this.$forceUpdate();
          } else {
            if (res.code != 1) {
              self.$message.warning(res.message);
            }
          }
        })
        .catch(err => {
          console.log("获取个人信息失败！");
        });
      // 			self.$http.get(self.global.serverPath + '/zhzg/userOrg/queryUserHomeInfo', {
      // 				params: { //传递参数
      // 					//					"personId": "0000002037"
      // 					"personId":'0000051922'
      // 				}
      // 				}).then(function(res) {
      // 				var tab = {},arrTab = [];
      // 				if (res.body.isRecord == 1) {//个人履历
      //                     for (var i = 0; i < res.body.bsRecords.length; i++) {
      //                         tab = {};
      //                         if(res.body.bsRecords[i].beginTime != ""){
      //                             if(res.body.bsRecords[i].endTime != ""){
      //                                 tab.date = res.body.bsRecords[i].beginTime + '至' + res.body.bsRecords[i].endTime;
      //                             }else{
      //                                 tab.date = res.body.bsRecords[i].beginTime + '至今'
      //                             }
      //                         }else{
      //                             if(res.body.bsRecords[i].endTime != ""){
      //                                 tab.date = '没有开始有时间'
      //                             }else{
      //                                 tab.date = ''
      //                             }
      //                         };
      //                         tab.unit = res.body.bsRecords[i].workUnit;
      //                         tab.duties = res.body.bsRecords[i].duty;
      //                         self.wdjcTable.push(tab)
      //                     }
      //                 }
      // 				if (res.body.isUserInfo == 1) { //个人信息数据
      // 					var datass = res.body.bsUserHomeInfoVOS[0];
      // 					self.leaveForm = {
      // 						age: datass.age,
      // 						name: datass.userName,
      // 						depart: datass.deptName,
      // 						unit: datass.companyName,
      // 						birth: datass.birthDate,
      // 						userId: datass.policeId,
      // 						jobTime: datass.workHour,
      // 						job: datass.duties,
      // 						level: datass.politicalLandScape,
      // 						school: datass.schoolName
      // 					}
      // 				}
      // 				if(res.body.isInstructor == 1){//兼职教官
      // 					self.jzjgInfo=true;
      // 					self.jzjgSpan=res.body.userInstructor[0].note;
      // 				}
      // 				if(res.body.isJudicial == 1){//司法证书
      // 					self.sfzsInfo=true;
      // 				}
      // 				if(res.body.isPersonnel == 1){//专业人才
      // 					self.zyrcInfo=true;
      // 				}
      // 				if (res.body.isAward == 1) { //个人奖惩
      // 					for (var i = 0; i < res.body.bsUserAwards.length; i++) {
      // 						self.imgArr.push(res.body.bsUserAwards[i].rewardName);
      // 					}
      // 					// self.imgArr=['一等功','二等功','三等功','南京市劳模','二级英模','全国优秀人民警察','全国特级优秀人民警察'];
      // 				} else {
      // 					console.log('没有数据')
      // 				}
      // 			}, function(res) {
      // 				self.$message.error('请求失败！')
      // 			});
    },
    toHome1() {
      this.$router.push("/home");
    }
  }
};
</script>
<style scoped>
.installConLvli .el-table {
  height: 270px;
  overflow-y: auto;
}
/**时间轴开始 */
.timeY .twoBtn1,
.timeY .twoBtn2 {
  position: absolute;
  top: 55px;
  z-index: 99;
  background: white;
  width: 45px;
  height: 32px;
}
.timeY .twoBtn1 {
  left: 0px;
}
.timeY .twoBtn2 {
  right: 0px;
}
#timeline {
  overflow: hidden;
  height: 220px;
}
.feature {
  display: none;
}
.liBox {
  position: relative;
  min-width: 100%;
  /**height:220px;*/
}
.timeY .el-button--small.is-circle {
  position: relative;
}
.twoBtn1 .el-button--small.is-circle {
  left: 6px;
}
.twoBtn2 .el-button--small.is-circle {
  left: 7px;
}
.timeY {
  margin: 13px 15px;
  position: relative;
  height: 172px;
  background: #f6f6f6;
}
.timeY .el-card__body {
  position: absolute;
  top: 0;
  left: 0;
}
.timeUl {
  list-style: none;
  /** display:flex;*/
  position: relative;
  height: 160px;
  /**border:1px solid green;*/
}
/* .timeUl .lineBox{
	position:absolute;
	top:50%;
	left:0;
	height:1px;
	z-index:9;
	transform: translateY(-50%);
	display: none;
	*background-image:linear-gradient(-90deg,#ffffff 0%,#66DEFF 60%,#35ADFB 100%);
	background-image:linear-gradient(-90deg,#66DEFF 0%,#35ADFB 100%);
} */
.timeUl li {
  display: inline-block;
  height: 60px;
  float: left;
  display: inline-block;
  line-height: 20px;
  position: relative;
}
.timeUl li > span {
  margin-left: -25px;
  font-family: "PingFangSC-Regular";
  font-size: 14px;
  color: rgba(0, 0, 0, 0.65);
}
.timeUl li > p {
  margin-left: -25px;
  font-family: "PingFangSC-Regular";
  font-size: 14px;
  color: rgba(0, 0, 0, 0.85);
  /**强制不换行 */
  white-space: nowrap;
}
.timeUl li > i {
  position: absolute;
  color: #1890ff;
  font-size: 16px;
  z-index: 10;
}
.timeUl .long1 {
  width: 170px;
}
.timeUl .long2 {
  width: 120px;
}
.timeUl .long3 {
  width: 220px;
}
.timeUl .jishu {
  /**border-bottom:3px solid #1890FF;*/
}
.timeUl .jishu > span,
.timeUl .jishu > p {
  top: 10px;
  position: relative;
}
.timeUl .oushu {
  padding-top: 10px;
  /**border-top:3px solid #1890FF;*/
  margin-top: 60px;
}
.timeUl .jishu > i {
  bottom: -10px;
}
.timeUl .oushu > i {
  top: -6px;
}
/**时间轴结束 */
.headIcon {
  color: #1890ff;
  position: relative;
  top: 4px;
  font-size: 26px;
}
.el-table {
  font-size: 14px !important;
}
.headMore {
  float: right;
  color: #1890ff;
}
.headMore > span {
  font-size: 14px;
}
.headMore > i {
  font-size: 16px;
}

.ytjNav > p,
.qbNav > p {
  padding: 0 20px 20px 4px;
  font-size: 16px;
  color: rgba(0, 0, 0, 0.85);
  letter-spacing: 0;
}

.hoverIcon {
  cursor: pointer;
}

.selfUl {
  list-style-type: none;
  padding: 0px;
  margin: 0px;
}

.selfUl i {
  font-size: 40px;
  color: #1890ff;
}

.selfUl span {
  font-size: 13px;
  color: rgba(0, 0, 0, 0.65);
  position: relative;
  bottom: 6px;
  position: absolute;
  right: 0;
  top: 0;
  width: calc(100% - 60px);
}

.selfUl li {
  width: 25%;
  float: left;
  margin-left: 25px;
  position: relative;
  /* text-align: center; */
  /* display: inline-block; */
  padding-left: 10px;
  width: 170px;
  height: 64px;
  margin-bottom: 20px;
  line-height: 64px;
  border: 1px dashed #aaa;
}

.removeIcon {
  position: absolute;
  top: -11px;
  right: -11px;
  cursor: pointer;
  font-size: 20px !important;
  color: #faad14 !important;
}

.addIcon {
  position: absolute;
  top: -11px;
  right: -11px;
  cursor: pointer;
  font-size: 20px !important;
  color: #1890ff !important;
}

.tjdh .el-dialog__header {
  height: 30px !important;
  border-bottom: 1px solid #ccc !important;
}

.aLink {
  display: block;
  position: absolute;
  width: 50px;
  height: 48px;
  background: #1890ff;
  border-top-left-radius: 4px;
  border-bottom-left-radius: 4px;
  right: 0;
  top: 42%;
  z-index: 999;
  cursor: pointer;
}

.load-div .grid-content[data-v-2dc8b6c1] {
  min-height: 56px !important;
}

.aLink > i {
  color: #fff;
  font-size: 24px;
  padding: 12px;
}

.reward {
  height: 230px;
  overflow-y: auto;
  padding: 0 20px;
}
.rewards > div {
  height: 100% !important;
}
.reward li {
  width: 50%;
  list-style: none;
  height: 59px;
  position: relative;
  border-bottom: 1px solid #a0a0a0;
}

.rewardImgBox {
  height: 59px;
  width: 79px;
  float: left;
  position: relative;
  background: white;
}

.reward li img {
  height: 40px;
  width: 40px;
  position: absolute;
  left: 30px;
  top: 50%;
  transform: translateY(-50%);
}

.rewardCon {
  height: 39px;
  float: left;
  padding: 10px 0;
  font-size: 14px;
  width: calc(100% - 80px);
}

.reward li p {
  line-height: 20px;
  height: 20px;
  white-space: normal;
  text-overflow: ellipsis;
  overflow: hidden;
}

.reward li:first-child .rewardImgBox {
  height: 60px !important;
  background: white;
}

.reward li:nth-of-type(1) {
  border-left: 0;
}

.reward li:nth-of-type(3) {
  border-left: 0;
}

.reward li:nth-of-type(2) {
  border-right: 0;
}

.reward .round {
  position: absolute;
  left: 75px;
  height: 12px;
  width: 12px;
  border-radius: 6px;
  border: 1px solid #52c41a;
  bottom: -7px;
  display: inline-block;
  background: white;
  z-index: 10;
}

.iconBtn {
  font-size: 14px;
  color: #409eff;
}

.installList {
  position: relative;
  padding: 18px;
  background: #eee;
  padding-top: 6px;
}

.install .grid-content {
  /**background: white;*/
  background: url("../../../static/img/index/partBg.png") no-repeat center;
  background-size: 100% 100%;
}

.install .el-header {
  height: 59px;
  line-height: 59px;
  /**border-bottom: 1px solid #f0f0f0;*/
  font-size: 18px;
  font-family: "PingFangSC-Medium";
  color: #262626;
}
.wagesList .el-header {
  height: 40px !important;
  line-height: 40px;
  border: 0;
  font-size: 14px;
}

.wages .grid-content .el-header {
  padding: 0 20px;
}

.wagesList .el-row {
  text-align: center;
  font-size: 14px;
  border-bottom: 1px dashed #f0f0f0;
  padding-bottom: 10px;
  padding: 0 20px;
}

.wagesList .el-container .el-header {
  padding: 0 20px;
}

.wagesList > .el-row {
  padding-bottom: 10px;
}

.wages .el-header {
  padding: 0;
}

.exitaNav i {
  font-size: 40px;
  color: #1890ff;
  position: relative;
  top: 10px;
  left: 10px;
}

.exitaNav a {
  color: #333;
}
.exitaNav .el-col {
  width: 25%;
  float: left;
  margin-left: 24px;
  position: relative;
  width: 170px;
  height: 64px;
  margin-bottom: 20px;
  line-height: 50px;
}
.exitaNav .el-col a {
  display: inline-block;
  height: 100%;
  width: 100%;
}
.exitaNav .el-col:last-child {
  width: 64px !important;
  height: 64px !important;
}
.indexInfo {
  margin-left: 0;
  margin-right: 0;
}

.indexCon > .el-main {
  padding: 0;
  background: white;
  background: #f0f0f0;
}

.index .el-container.is-vertical {
  /**background: white;*/
}

.perInfo {
  height: 100px;
  padding: 30px 10px;
  margin-left: 0 !important;
  margin-right: 0 !important;
  background: white;
  overflow: hidden;
  display: flex;
  white-space: normal;
}

.perInfo > div {
  float: left;
  margin-left: 3%;
}

.indexInfo .el-header {
  line-height: 60px;
}

.perInfo .informa {
  width: 60%;
  /* max-width:860px!important; */
  margin-left: 2%;
  font-size: 14px;
  float: left;
}
/**.threeIcon{
	width:35px;
	float: left;
	display:flex;
	flex-direction:column-reverse;
	justify-content:center;
	align-items:center;
	height:105px;
	margin-left:0!important;
}*/
.informa .el-row {
  margin-top: 22px;
}

.informa .el-row:first-child {
  margin-top: 0;
}

.perInfo .infoFun {
  margin-left: 6%;
  text-align: center;
  width: 29%;
  float: left;
}

.infoFun .bg-purple {
  padding-top: 12px;
}
.informa .informaName {
  font-size: 16px;
}
.informaName span {
  white-space: nowrap;
}
.informa span {
  height: 20px;
  line-height: 20px;
  float: left;
  font-family: PingFangSC-Regular;
  font-size: 14px;
  color: rgba(0, 0, 0, 0.65);
}
.informa .infoMsg {
  color: rgba(0, 0, 0, 0.85);
}
.informa .grid-content {
  text-overflow: ellipsis;
  white-space: nowrap;
  overflow: hidden;
  font-family: "PingFangSC-Medium";
}

.indexCon {
  width: 100%;
}

.class1 {
  float: left;
}

.class1 .rewardiamge {
  border-left: 1px solid #a0a0a0;
}

.class3 .rewardiamgeCon {
  border-right: 1px solid #a0a0a0;
}

.class1:first-child .rewardiamge {
  border-left: 0;
}

.reward li:nth-of-type(2) .rewardiamgeCon {
  border-right: 0 !important;
}

.class2 {
  float: left;
}

.class3 {
  float: right;
}

.class4 {
  float: right;
}

.reward li > div:nth-of-type(2) {
  width: calc(100% - 80px);
  float: left;
  height: 40px;
  display: flex;
  flex-direction: column;
  padding: 10px 0;
}

.reward li > div p {
  height: 20px;
  line-height: 20px;
}
</style>
<style>
body .el-table th.gutter {
  display: table-cell !important;
}
body .el-table colgroup.gutter {
  display: table-cell !important;
}
.timeY .el-card__body {
  padding: 5px 20px !important;
  /* background: url('~/static/img/index/progressBg.png') no-repeat; */
  /* background-size: 100% 100%; */
}
.tjdh .el-dialog__body {
  padding: 5px 20px !important;
}
.customizeCon {
  z-index: 100000000;
}
.customizeCon .el-dialog__body {
  padding: 5px 20px;
}
.customizeCon .el-dialog {
  position: absolute !important;
  width: 300px !important;
  margin: 0 !important;
  right: 0;
  box-shadow: 0 0 0 !important;
  height: 100%;
}
.customize .el-tree-node {
  margin-top: 30px;
}
.customize .dialog-footer {
  position: absolute;
  bottom: 30px;
  right: 20px;
}
.switchList > div {
  margin-top: 30px;
  height: 26px;
}
.index .install > div {
  align-content: flex-start;
}
.index .installCon {
  box-sizing: border-box;
  height: calc(50% - 24px);
  margin-bottom: 18px;
}
.wdzyTable li {
  width: 100% !important;
  height: auto !important;
  padding: 6px !important;
  border-bottom: none !important;
}
.wdzyTable .el-timeline-item__content {
  color: #606266;
  font-size: 14px;
}
.wdzyTable ul {
  padding: 2px 10px !important;
}
.wdzyTable .el-timeline-item__wrapper {
  padding-left: 2px !important;
  top: 0 !important;
}
.wdzyTable .el-timeline-item__tail {
  border: none !important;
}
.wdzyTable .el-timeline-item {
  padding: 5px 0 !important;
}
/*奖章  */

.medals {
  height: 150px;
  width: 260px;
  margin-left: 0 !important;
  margin-top: -18px !important;
  /**float:right!important;*/
}
.medals .el-carousel__arrow {
  display: none !important;
}
.medals .el-carousel__button {
  top: 44% !important;
  background-color: #555;
  width: 6px;
  height: 6px;
  border-radius: 3px;
}
.medals .el-carousel__indicators {
  bottom: 48% !important;
}
.flag-content span {
  font-family: "PingFangSC-Medium";
  font-size: 14px;
  color: rgba(0, 0, 0, 0.85);
  letter-spacing: 0;
}
.liBox {
  display: inline-flex;
  height: 100%;
  user-select: none;
}
.liBoxList {
  list-style: none;
  width: 120px;
  height: 100%;
  position: relative;
}
.listYear {
  position: absolute;
  top: 62%;
  transform: translateY(-50%);
  left: -12px;
  color: #666;
  font-size: 12px;
}
.graduate {
  position: absolute;
  width: 100%;
  height: 16px;
  left: 0;
  top: 50%;
  transform: translateY(-50%);
  align-items: center;
  display: -webkit-flex;
}
.graduate > span {
  float: left;
  display: inline-block;
  width: 1px;
  height: 10px;
  background: #666;
  margin-right: 5px;
}
.graduate > span > span {
  position: absolute;
  top: 22px;
}
/* .timeUl .lineBox{
	position:absolute;
	top:50%;
	left:0;
	transform: translateY(-50%);
	height:1px;
	z-index:9;
	background: #777;
	width: 100%
} */
.liBoxList > div {
  font-size: 12px;
}
.beRewarded {
  position: absolute;
  border-radius: 6px;
  width: 180px;
  height: 50px;
  color: #333;
  z-index: 10;
  /* background: #C8E4FC; */
  border: 1px solid #89c9ff;
  background: -webkit-linear-gradient(to top, #c8e4fc, #bce0ff);
  background: -moz-linear-gradient(to top, #c8e4fc, #bce0ff);
  background: -o-linear-gradient(to top, #c8e4fc, #bce0ff);
  background: linear-gradient(to top, #c8e4fc, #bce0ff);
  box-shadow: none;
  background: rgba(137, 201, 255, 0.3);
}
.beRewarded.active {
  background: -webkit-linear-gradient(to top, #9fd1fd 80%, #95ccfd 95%);
  background: -moz-linear-gradient(to top, #9fd1fd 80%, #95ccfd 95%);
  background: -o-linear-gradient(to top, #9fd1fd 80%, #95ccfd 95%);
  background: linear-gradient(to top, #9fd1fd 80%, #95ccfd 95%);
  box-shadow: rgba(0, 0, 0, 0.45) 0px 0px 10px;
  transition: all 0.3s;
  z-index: 1000;
}
.beRewarded p {
  line-height: 20px;
  margin: 2.5px 0;
  overflow: hidden;
  -o-text-overflow: ellipsis;
  text-overflow: ellipsis;
  white-space: nowrap;
  font-size: 14px;
  width: 132px;
}
.coordinateAxis {
  position: absolute;
  /*width: 9px;*/
  /*height: 9px;*/
  width: 11px;
  height: 11px;
  border-radius: 50%;
  z-index: 10;
  top: 50%;
  transform: translateY(-50%);
}
.blueDiv .coordinateAxis {
  background: #1890ff;
}
.greenDiv .coordinateAxis {
  background: #ffb400;
}
.orangeDiv .coordinateAxis {
  background: #49d000;
}
.redDiv .coordinateAxis {
  background: #ff0000;
}
.awardCon,
.beRewarded {
  display: inline-block;
  float: left;
}
.beRewarded .awardImg {
  margin: 10px 6px;
  float: left;
  display: inline-block;
  width: 30px;
  height: 30px;
  background-size: 100% 100%;
  background-repeat: no-repeat;
}
.blueDiv .awardImg {
  background-image: url("../../../static/img/index/jinghui.png");
}
.greenDiv .awardImg {
  background-image: url("../../../static/img/index/jinsheng.png");
}
.orangeDiv .awardImg {
  background-image: url("../../../static/img/index/jiajiang.png");
}
.redDiv .awardImg {
  background-image: url("../../../static/img/index/rudang.png");
}
.timeYBtn {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  z-index: 100;
  text-align: center;
}
.timeYBtn i {
  font-size: 40px;
  opacity: 0.4;
  border-radius: 50%;
}
.timeYBtn i:hover {
  cursor: pointer;
  background: rgba(0, 0, 0, 0.5);
  color: white;
}
.timeLeft {
  left: 0px;
  display: none;
}
.timeRight {
  right: 0px;
  display: none;
}
.arrowhead {
  position: absolute;
  top: 50.5%;
  transform: translateY(-50%);
  z-index: 10;
  right: -6px;
}
.wdzyTable .el-table {
  overflow-y: auto;
}
</style>
