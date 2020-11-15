<template>
    <div :class="'manageBox'+(this.skin==='white'?' _WHITE':'')">
        <div class="manageHead">
            <div class="toggleSkin" @click="()=>{this.skin = this.skin==='white'?'blue':'white';}">
                <i class="skin-arrow"></i>
                <span>一键换肤</span>
            </div>
            <div class="leftBg"></div>
            <div class="headInfo">南京公安队伍管理数据可视化</div>
            <div class="rightBg"></div>
            <div class="linkManage" v-if="showBackMenu">
                <router-link to="/home2">
                    个人首页
                </router-link> |
                <router-link to="/indexManage">
                    管理首页
                </router-link>
            </div>
        </div>
        <div class="manageMain">
            <el-row>
                <el-col :span="12">
                    <div class="threeBox">
                        <el-row>
                            <el-col :span="24">
                                <div class="rygkBox" >
                                    <div class="partHead">
                                        <span class="partTit" style="font-weight: 900;cursor: unset;">人员概况</span>
                                        <div class="partTab" style="margin-right:40px;">
                                            <el-tabs v-model="activeRygk" @tab-click="handleClick">
                                                <el-tab-pane label="来源" name="one" class="fontfamily">
                                                    <JrlyChart :active="activeRygk=='one'" :skin="skin" :unit="rygkUnitValue" />
                                                </el-tab-pane>
                                                <el-tab-pane class="fontfamily" label="年龄" name="two">
                                                    <XbnlChart :active="activeRygk=='two'" :skin="skin" :unit="rygkUnitValue" />
                                                </el-tab-pane>
                                                <el-tab-pane label="民族" name="three">
                                                    <RymzChart :active="activeRygk=='three'" :skin="skin" :unit="rygkUnitValue" />
                                                </el-tab-pane>
                                                <el-tab-pane label="职务" name="four">
                                                    <ZwmcChart :active="activeRygk=='four'" :skin="skin" :unit="rygkUnitValue" />
                                                </el-tab-pane>
                                                <el-tab-pane label="职级" name="nine">
                                                  <div class="nine">
                                                   <div :style="'color:'+(this.skin=='white'?'black':'white')+';float:right;padding-right: 10px;font-size: 14px;line-height: 32px;'">
                                                     <span>职级序列:</span>
                                                      <el-select v-model="UnitValuezj" @change="handleNodeCheckRYGKxlzj">
                                                            <el-option
                                                              v-for="item in zjAttListsoptions"
                                                              :key="item.value"
                                                              :label="item.label"
                                                              :value="item.value">
                                                            </el-option>
                                                      </el-select>
                                                    </div>
                                                  </div>
                                                  <div v-show="!showdatas&&UnitValuezjb" class="mycharts" id="zjallcharts"></div>
                                                  <div v-show="!showdatas&&!UnitValuezjb" class="mycharts" id="zjother"></div>
                                                  <div v-show="showdatas" class="mycharts" :style="'color:'+(this.skin=='white'?'black':'white')+';text-align: center;line-height: 250px;'">暂无数据</div>
                                                </el-tab-pane>
                                                <el-tab-pane label="警衔" name="five">
                                                    <JxfbChart :active="activeRygk=='five'" :skin="skin" :unit="rygkUnitValue" />
                                                </el-tab-pane>
                                            </el-tabs>
                                        </div>
                                        <span class="partTit el-icon-caret-bottom" style="left: 90px; position: absolute; float: right;font-size: 25px;padding-top: 17px;"></span>
                                        <div class="customManageBoxs" style="left: 90px;position: absolute;float: right;top: 0;">
                                            <el-select v-model="cgjUnitValue" popper-class="customManageBox">
                                                <el-option :value="cgjUnitValue">
                                                        <el-tree
                                                        style="height: 200px"
                                                        :data="unitOptionsRYGK"
                                                        show-checkbox
                                                        node-key="unitId"
                                                        ref="RTYGKtree"
                                                        check-on-click-node
                                                        @check="handleNodeCheckRYGK"
                                                        :props="dataProps2">
                                                        </el-tree>
                                                </el-option>
                                            </el-select>
                                        </div>
                                        <div class="partTit" style="right: 20px;position: absolute;top: 0;float: right;" @click="btnDialog('人员概况','人员概况','')">
                                          <img src="../../../static/img/list_icon.png" style="width: 11px;" alt=""><span style="font-size: 14px;padding-left: 3px;">详情</span>
                                        </div>
                                    </div>
                                </div>
                            </el-col>
                        </el-row>
                        <el-row style="border-top: 1px solid rgba(232, 232, 232, .2);">
                            <el-col :span="12">
                                <div class="zzmmBox">
                                    <div class="partHead partHeads">
                                       <!-- @click="btnDialog('人员概况','政治面貌','')" -->
                                      <span class="partTit" style="font-weight: 900;cursor: unset;">性别</span>
                                      <div class="partTit" style="float: right;cursor: pointer;" @click="btnDialog('人员概况','性别','')">
                                        <img src="../../../static/img/list_icon.png" style="width: 11px;" alt=""><span style="font-size: 14px;padding-left: 3px;">详情</span>
                                      </div>
                                    </div>
                                    <div class="zzmmCon" v-loading="zzmmDataLoadingse">
                                        <el-row v-if="zzmmDatase&&zzmmDatase.length>0">
                                            <el-col :span="12" v-for="(item,index) in zzmmDatase" :key="index">
                                                <div class="zzmmInfo" style="cursor: pointer;" @click="btnDialog('人员概况','性别',item.name)">
                                                    <i :class="item.type"></i>
                                                    <p>{{item.name}}</p>
                                                    <span>{{item.value}}</span><span class="unit">人</span>
                                                    <div><span class="unit">占比:</span><span>{{item.valuese}}</span></div>
                                                </div>
                                            </el-col>
                                        </el-row>
                                        <div v-else-if="zzmmDatase&&!zzmmDataLoadingse" class="v-charts-data-empty"> 暂无数据 </div>
                                        <div v-else-if="!zzmmDatase&&!zzmmDataLoadingse" class="v-charts-data-error"> 获取数据失败 </div>
                                    </div>
                                </div>
                                <div class="zzmmBox">
                                    <div class="partHead partHeads">
                                       <!-- @click="btnDialog('人员概况','政治面貌','')" -->
                                      <span class="partTit" style="font-weight: 900;cursor: unset;">政治面貌</span>
                                      <div class="partTit" style="float: right;cursor: pointer;" @click="btnDialog('人员概况','政治面貌','')">
                                        <img src="../../../static/img/list_icon.png" style="width: 11px;" alt=""><span style="font-size: 14px;padding-left: 3px;">详情</span>
                                      </div>
                                    </div>
                                    <div class="zzmmCon" v-loading="zzmmDataLoading">
                                        <el-row v-if="zzmmData&&zzmmData.length>0">
                                            <el-col :span="6" v-for="(item,index) in zzmmData" :key="index">
                                                <div class="zzmmInfo" style="cursor: pointer;" @click="btnDialog('人员概况','政治面貌',item.name)">
                                                    <i :class="item.type"></i>
                                                    <p>{{item.name}}</p>
                                                    <span>{{item.value}}</span><span class="unit">人</span>
                                                </div>
                                            </el-col>
                                        </el-row>
                                        <div v-else-if="zzmmData&&!zzmmDataLoading" class="v-charts-data-empty"> 暂无数据 </div>
                                        <div v-else-if="!zzmmData&&!zzmmDataLoading" class="v-charts-data-error"> 获取数据失败 </div>
                                    </div>
                                </div>
                            </el-col>
                            <el-col :span="12" style="border-left: 1px solid rgba(232, 232, 232, .2);">
                                <div class="xlxwBox">
                                    <div class="partHead partHeads">
                                       <!-- @click="btnDialog('人员概况','学历学位','')" -->
                                      <span class="partTit" style="font-weight: 900;cursor: unset;">学历学位</span>
                                      <div class="partTit" style="float: right;" @click="btnDialog('人员概况','学历学位','')">
                                        <img src="../../../static/img/list_icon.png" style="width: 11px;" alt=""><span style="font-size: 14px;padding-left: 3px;">详情</span>
                                      </div>
                                    </div>
                                    <div class="partCon" @click="btnDialog('人员概况','学历学位','')">
                                    <!-- <div class="partCon"> -->
                                        <XlxwChart :skin="skin" :unit="rygkUnitValue"/>
                                    </div>
                                </div>
                            </el-col>
                        </el-row>
                    </div>
                </el-col>
                <el-col :span="12">
                    <div class="dwgkBox">
                        <div class="partHead">
                            <span class="partTit" style="font-weight: 900;cursor: unset;">单位概况</span>
                            <div class="partTit" style="float: right;" @click="btnDialog('单位概况','单位概况','')">
                              <img src="../../../static/img/list_icon.png" style="width: 11px;" alt=""><span style="font-size: 14px;padding-left: 3px;">详情</span>
                            </div>
                            <div class="partTab">
                                <div class="el-tabs">
                                    <div class="el-tabs__header">
                                        <span :class="'btn-tab-ry'+(this.activeDwgk==='first' ?' active':'')" @click="FunActiveDwgk('first')">荣誉列表</span>
                                        <span :class="'btn-tab-ry'+(this.activeDwgk==='third' ?' active':'')" @click="FunActiveDwgk('third')">绩效考核</span>
                                    </div>
                                    <div class="el-tabs__content">
                                        <div class="el-tab-pane" v-if="activeDwgk=='first'">
                                            <div class="panel" style="border-right: 1px solid rgba(232, 232, 232, .2); position: relative">
                                                <div class="p-header" style="line-height: 30px;">
                                                    <span class="partTit fontfamily" style="left: 20px; cursor: unset;">集体荣誉</span>
                                                    <span class="partTit el-icon-caret-bottom" style="left: 80px;font-size: 25px !important;">
                                                    </span>
                                                    <div class="customManageBox customManageBoxe">
                                                        <el-select v-model="cgjUnitValue" popper-class="customManageBox">
                                                            <el-option :value="cgjUnitValue">
                                                                  <el-tree
                                                                    ref="RYGKtree1"
                                                                    class="treeSelect"
                                                                    :data="deptRewordLeftUnitOptions"
                                                                    :props="dataProps2"
                                                                    @check="checkGroupNodeFenju_jiti"
                                                                    show-checkbox
                                                                    node-key="unitId"
                                                                    check-on-click-node>
                                                                  </el-tree>
                                                            </el-option>
                                                        </el-select>
                                                    </div>
                                                    <div class="p-text">
                                                        <div style="width: 33%;float: left; text-align: center; position: relative; top: 14px;">
                                                            <img src="../../../static/img/manage/pai-yin.png" alt="">
                                                            <p v-if="paiming_fenju!==undefined && paiming_fenju.length > 1" :title="paiming_fenju[1].orgName" >{{paiming_fenju[1].orgName}}</p>
                                                        </div>
                                                        <div style="width: 33%;float: left; text-align: center; position: relative; top: 0">
                                                            <img src="../../../static/img/manage/pai-jin.png" alt="">
                                                            <p v-if="paiming_fenju!==undefined && paiming_fenju.length > 0" :title="paiming_fenju[0].orgName" >{{paiming_fenju[0].orgName}}</p>
                                                        </div>
                                                        <div style="width: 33%;float: left; text-align: center; position: relative; top: 18px">
                                                            <img src="../../../static/img/manage/pai-tong.png" alt="">
                                                            <p v-if="paiming_fenju!==undefined && paiming_fenju.length > 2" :title="paiming_fenju[2].orgName" >{{paiming_fenju[2].orgName}}</p>
                                                        </div>
                                                    </div>
                                                    <div class="p-jiangtai"></div>
                                                </div>
                                                <div class="p-body">
                                                    <div class="p-list">
                                                        <ul>
                                                            <li v-for="(item,index) in paiming_fenju" v-if="paiming_fenju.length > 0"  @click="btnDialog('荣誉列表','first',item)">
                                                                <div class="p-list-num">{{item.index}}</div>
                                                                <div class="p-list-name" :title="item.orgName">{{item.orgName}}</div>
                                                                <div class="p-list-progress blue">
                                                                    <el-progress :title="item.count" :text-inside="false" :show-text="false" :stroke-width="12" :percentage="item.count/paiming_fenju[0].count*100"></el-progress>
                                                                </div>
                                                            </li>
                                                        </ul>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="panel" style="solid rgba(232, 232, 232, .2);  position: relative;">
                                                <div class="p-header" style="line-height: 30px;">
                                                    <span class="partTit fontfamily" style="left: 20px; cursor: unset;">个人荣誉</span>
                                                    <span class="partTit el-icon-caret-bottom" style="left: 80px;font-size: 25px !important; ">
                                                    </span>
                                                    <div class="customManageBox customManageBoxe">
                                                        <el-select v-model="cgjUnitValue" popper-class="customManageBox">
                                                            <el-option :value="cgjUnitValue">
                                                                    <el-tree
                                                                    ref="RYGKtree2"
                                                                    class="treeSelect"
                                                                    :data="deptRewordRightUnitOptions"
                                                                    :props="dataProps2"
                                                                    @check="checkGroupNodeBumen_jiti"
                                                                    show-checkbox
                                                                    node-key="unitId"
                                                                    check-on-click-node>
                                                                    </el-tree>
                                                            </el-option>
                                                        </el-select>
                                                    </div>
                                                    <div class="p-text">
                                                        <div style="width: 33%;float: left; text-align: center; position: relative; top: 14px;">
                                                            <img src="../../../static/img/manage/pai-yin.png" alt="">
                                                            <p v-if="paiming_bumen!==undefined && paiming_bumen.length > 1" :title="paiming_bumen[1].orgName" >{{paiming_bumen[1].orgName}}</p>
                                                        </div>
                                                        <div style="width: 33%;float: left; text-align: center; position: relative; top: 0">
                                                            <img src="../../../static/img/manage/pai-jin.png" alt="">
                                                            <p v-if="paiming_bumen!==undefined && paiming_bumen.length > 0" :title="paiming_bumen[0].orgName" >{{paiming_bumen[0].orgName}}</p>
                                                        </div>
                                                        <div style="width: 33%;float: left; text-align: center; position: relative; top: 18px">
                                                            <img src="../../../static/img/manage/pai-tong.png" alt="">
                                                            <p v-if="paiming_bumen!==undefined && paiming_bumen.length > 2" :title="paiming_bumen[2].orgName" >{{paiming_bumen[2].orgName}}</p>
                                                        </div>
                                                    </div>
                                                    <div class="p-jiangtai"></div>
                                                </div>
                                                <div class="p-body">
                                                    <div class="p-list">
                                                        <ul>
                                                            <li v-for="(item,index) in paiming_bumen" v-if="paiming_bumen.length > 0"  @click="btnDialog('荣誉列表','second',item)">
                                                                <div class="p-list-num">{{item.index}}</div>
                                                                <div class="p-list-name" :title="item.orgName">{{item.orgName}}</div>
                                                                <div class="p-list-progress purple">
                                                                    <el-progress :title="item.count" :text-inside="false" :show-text="false" :stroke-width="12" :percentage="item.count/paiming_bumen[0].count*100"></el-progress>
                                                                </div>
                                                            </li>
                                                        </ul>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="el-tab-pane" :style="activeDwgk=='third'?{}:{display:'none'}">
                                            <div class="partSelect" style="width: 100%; padding-right: 20px; z-index: 200; position: relative;">
                                                <span>年份：</span>
                                                <el-select v-model="kpiYear" @change="kpiYearChange" style="width: 76px!important;">
                                                    <el-option v-for="item in kpiYearOptions" :key="item" :label="item" :value="item">
                                                    </el-option>
                                                </el-select>
                                                <span>季度：</span>
                                                <el-select v-model="kpiQuarter" @change="kpiQuarterChange" style="width: 100px!important;">
                                                    <el-option v-for="item in kpiQuarterOptions" :key="item" :label="item" :value="item">
                                                    </el-option>
                                                </el-select>
                                                <span>考核单元：</span>
                                                <el-select v-model="kpiUnit" @change="getKPIItemMenu" style="width: 100px!important;">
                                                    <el-option v-for="item in kpiUnitOptions" :key="item" :label="item" :value="item">
                                                    </el-option>
                                                </el-select>
                                                <span>考核项：</span>
                                                <el-select v-model="valuecontest" collapse-tags multiple placeholder="请选择" style="width: 180px!important;">
                                                  <el-option v-for="item in cities" :key="item.value" :label="item.name" :value="item.value">
                                                  </el-option>
                                                </el-select>
                                            </div>
                                            <JxkhChart :active="showJxkhChart == true" :valuecontest="valuecontest" :kpiQuarter="kpiQuarter" :kpiYear="kpiYear" :kpiUnit="kpiUnit" :skin="skin" />
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </el-col>
                <el-col :span="12">
                    <div class="cgjBox">
                        <div class="partHead">
                            <span class="partTit" style="float: left;font-weight: 900;cursor: unset;">出国境概览</span>
                             <div class="partTit" style="float: right;padding-left: 20px;" @click="btnDialog('出国境概况','出国境概况','')">
                                <img src="../../../static/img/list_icon.png" style="width: 11px;" alt=""><span style="font-size: 14px;padding-left: 3px;">详情</span>
                              </div>
                              <span class="partTit el-icon-caret-bottom" style="position: absolute;left: 110px;top: 15px;font-size: 25px !important; "></span>
                              <div class="customManageBox customManageBoxes">
                                  <el-select v-model="cgjValue" popper-class="customManageBox">
                                      <el-option :value="cgjValue">
                                              <el-tree
                                              class="treeSelect"
                                            style="height: 200px"
                                            :data="unitOptionsCGJ"
                                            show-checkbox
                                            node-key="unitId"
                                            ref="tree"
                                            check-on-click-node
                                            @check="handleNodeCheckCGJ"
                                            :props="dataProps2">
                                              </el-tree>
                                      </el-option>
                                  </el-select>
                              </div>
                              <div class="partSelect cgjSelect partSelects" style="float: right;width: 25%;">
                                <span> 年度：</span>
                                <el-select v-model="cgjYearValue"  class="year" style="width: 50% !important;">
                                    <el-option v-for="item in cgjYearOptions" :key="item.value" :label="item.name" :value="item.value">
                                    </el-option>
                                </el-select>
                              </div>
                        </div>
                        <div class="cgjCon">
                            <CgjWordChart :year="cgjYearValue" :unit="cgjUnitValue" :skin="skin" />
                            <CgjHgramChart :year="cgjYearValue" :unit="cgjUnitValue" :skin="skin" />
                        </div>
                    </div>
                </el-col>
                <el-col :span="12">
                    <div class="qjglBox">
                        <div class="partHead">
                            <span class="partTit" style="float: left;font-weight: 900;cursor: unset;">请假概览</span>
                            <div class="partTit" style="float: right;padding-left: 20px;" @click="btnDialog('请假概况','请假概况','')">
                              <img src="../../../static/img/list_icon.png" style="width: 11px;" alt=""><span style="font-size: 14px;padding-left: 3px;">详情</span>
                            </div>
                             <span class="partTit el-icon-caret-bottom" style="position: absolute;left: 90px;top: 15px;font-size: 25px !important; "></span>
                              <div class="customManageBox customManageBoxess">
                                  <el-select v-model="qjglValue" popper-class="customManageBox">
                                      <el-option :value="qjglValue">
                                          <el-tree
                                            class="treeSelect"
                                            style="height: 200px"
                                            :data="unitOptionsQJGL"
                                            show-checkbox
                                            node-key="unitId"
                                            ref="qjTree"
                                            check-on-click-node
                                            @check="handleNodeCheckQJGL"
                                            :props="dataProps2">
                                          </el-tree>
                                      </el-option>
                                  </el-select>
                              </div>
                              <div class="partSelect qxjSelect partSelects" style="float: right;width: 25%;">
                                <span>年度：</span>
                                <el-select v-model="qjglYearValue" class="year" style="width: 50% !important;">
                                    <el-option v-for="item in qjglYearOptions" :key="item.value" :label="item.name" :value="item.value">
                                    </el-option>
                                </el-select>
                              </div>
                        </div>
                        <div class="qjglCon">
                            <QjglChart :year="qjglYearValue" :unit="qjglUnitValue" :skin="skin" />
                        </div>
                    </div>
                </el-col>
            </el-row>
            <el-dialog class="dialogVisible" left :title="dialogTitle" :fullscreen="ifFullScreen" :visible.sync="dialogFormVisible" width="1000px" min-height="700px">
                <div class="el-icon-full-screen" @click="btnFullScreen"></div>
                <dialog-rygk @listenChild="dialogEmit" :ifFullScreen ="ifFullScreen" :poster="poster" :unitOptionsRYGKs="unitOptionsRYGKs" :names="names" v-if="activeDialog=='人员概况'&&dialogFormVisible"></dialog-rygk>
                <dialog-dwgk @listenChild="dialogEmit" :ifFullScreen ="ifFullScreen" :activeTabs ="activeTabs"  :activeClasse="activeClasse" :UnitValues="UnitValues" v-if="activeDialog=='单位概况'&&dialogFormVisible"></dialog-dwgk>
                <dialog-cgjgk @listenChild="dialogEmit" :ifFullScreen ="ifFullScreen" :departIds1 ="departIds1" v-if="activeDialog=='出国境概况'&&dialogFormVisible"></dialog-cgjgk>
                <dialog-qjgk @listenChild="dialogEmit" :ifFullScreen ="ifFullScreen" :departIds2 ="departIds2"  v-if="activeDialog=='请假概况'&&dialogFormVisible"></dialog-qjgk>
            </el-dialog>
            <el-dialog title="职级明细" :visible.sync="seriesclick"  min-height="700px" width="1000px">
              <div style="height:400px;width:100%;">
                <div class="mychartse" id="zjalldetail"></div>
              </div>
            </el-dialog>
        </div>
        <el-footer style="text-align:center;height:20px;color:#fff">&copy;2019-2022 南京市公安局&emsp;版权所有</el-footer>
    </div>
</template>
<script>
import axios from "axios";
let gloabPath = require("../../../src/Global");
const baseURLHead = gloabPath.default.serverPath8;
const baseURLHeadse = gloabPath.default.serverPath8;
let $axios = axios.create({
  baseURL: baseURLHead + "/statistics/mangagerData"
});
const $fetch = function(method, url, params) {
  return new Promise((resolve, reject) => {
    $axios({
      method: method,
      url: url.remoteUrl,
      data: params,
      headers: {
        contentType: "application/json",
        token: localStorage.getItem("setToken")
      }
    })
      .then(res => {
        resolve(res.data);
      })
      .catch(err => {
        reject(err);
      });
  });
};
let $axiosUser = axios.create({
  baseURL: baseURLHead + "/user-mgmt"
  // baseURL: baseURLHead + "/statistics"
});
const $fetchUser = function(method, url, params) {
  return new Promise((resolve, reject) => {
    $axiosUser({
      method: method,
      url: url.remoteUrl,
      data: params,
      headers: {
        contentType: "application/json",
        token: localStorage.getItem("setToken")
      }
    })
      .then(res => {
        resolve(res.data);
      })
      .catch(err => {
        reject(err);
      });
  });
};
let $axiosUsers = axios.create({
  // baseURL: baseURLHead + "/user-mgmt"
  baseURL: baseURLHead + "/statistics"
});
const $fetchUsers = function(method, url, params) {
  return new Promise((resolve, reject) => {
    $axiosUsers({
      method: method,
      url: url.remoteUrl,
      data: params,
      headers: {
        contentType: "application/json",
        token: localStorage.getItem("setToken")
      }
    })
      .then(res => {
        resolve(res.data);
      })
      .catch(err => {
        reject(err);
      });
  });
};
let $axiosUsernames = axios.create({
  baseURL: baseURLHeadse
});
const $fetchUsernames = function(method, url, params) {
  return new Promise((resolve, reject) => {
    $axiosUsernames({
      method: method,
      url: url.remoteUrl,
      data: params,
      headers: {
        contentType: "application/json",
        token: localStorage.getItem("setToken")
      }
    })
      .then(res => {
        resolve(res.data);
      })
      .catch(err => {
        reject(err);
      });
  });
};
const chartStyle = {
  white: {
    timelineColor: "#1890ff",
    timelineSymbolColor: "#fff",
    tooltipColor: "rgba(0,0,0,0.5)",
    fontColor: "rgba(0,0,0,0.65)",
    inactiveColor: "#666",
    fontFamily: "PingFangSC-Regular",
    chartColors: [
      "#1890FF",
      "#5254CF",
      "#975FE4",
      "#F2637B",
      "#FACC14",
      "#2FC258",
      "#13C2C2"
    ]
  },
  blue: {
    timelineColor: "#1890ff",
    timelineSymbolColor: "#00438F",
    tooltipColor: "rgba(0,62,145,0.85)",
    fontColor: "#fff",
    inactiveColor: "#ccc",
    fontFamily: "PingFangSC-Regular",
    chartColors: [
      "#1890FF",
      "#5254CF",
      "#975FE4",
      "#F2637B",
      "#FACC14",
      "#2FC258",
      "#13C2C2"
    ]
  },
  bar: {
    timelineColor: "#1890ff",
    timelineSymbolColor: "#00438F",
    tooltipColor: "rgba(0,62,145,0.85)",
    fontColor: "#fff",
    inactiveColor: "#ccc",
    fontFamily: "PingFangSC-Regular",
    chartColors: [
      "rgba(24,144,255,0.85)",
      "rgba(20,198,202,0.85)",
      "rgba(105,41,252,0.75)",
      "rgba(242,99,123,0.75)",
      "rgba(254,171,103,0.85)",
      "rgba(19,129,194,0.85)"
    ]
  }
};
const COUNT = 15;
const EMPTY_DATA = function() {
  return {
    columns: [],
    rows: []
  };
};
const XbnlChart = {
  data() {
    this.grid = {
      containLabel: true,
      top: "7%",
      right: "3%",
      left: "3%",
      bottom: "7%"
    };
    this.tooltip = {
      show: true,
      padding: [8, 12],
      textStyle: {
        color: chartStyle["blue"].fontColor,
        fontFamily: chartStyle["blue"].fontFamily
      },
      formatter: function(f) {
        let s = "";
        s += '<h4 style="font-size:15px;line-height:28px;">' + f.name + "</h4>";
        s +=
          '<p style="font-size:12px;line-height:24px;">数量: ' +
          f.value +
          "</p>";
        return s;
      }
    };
    this.legend = {
      show: false
    };
    this.xAxis = {
      axisLine: {
        //坐标轴线
        show: false
      },
      axisTick: {
        //坐标轴刻度
        show: false
      },
      splitLine: {
        //坐标轴分割线
        lineStyle: {
          type: "dashed",
          opacity: ".25"
        }
      },
      minInterval: 1
    };
    this.extend = {
      series: v => {
        if (v)
          v.forEach(i => {
            i.barWidth = 30;
            this.boyAge =
              this.chartData.rows.length > 0 ? this.mklineData[0].value : 0;
            this.girlAge =
              this.chartData.rows.length > 0 ? this.mklineData[1].value : 0;
            this.allAge =
              this.chartData.rows.length > 0 ? this.mklineData[2].value : 0;
          });
        return v;
      },
      tooltip: t => {
        t.backgroundColor = chartStyle[this.themeName].tooltipColor;
        t.borderColor = chartStyle[this.themeName].tooltipColor;
        return t;
      },
      color: () => {
        return chartStyle[this.themeName].chartColors;
      },
      xAxis: xa => {
        let color = chartStyle[this.themeName].fontColor,
          fontFamily = chartStyle[this.themeName].fontFamily;
        xa.axisLabel = {
          color: color,
          fontFamily: fontFamily
        };
        xa.splitLine.lineStyle.color = color;
        return xa;
      },
      yAxis: ya => {
        let color = chartStyle[this.themeName].fontColor,
          fontFamily = chartStyle[this.themeName].fontFamily;
        ya.forEach(i => {
          i.axisLine = {
            //坐标轴线
            show: i.data.length !== 0,
            lineStyle: {
              color: color,
              type: "solid",
              opacity: ".85"
            }
          };
          i.axisTick = {
            //坐标轴刻度
            show: false
          };
          i.axisLabel = {
            // interval: 0 //横坐标间隔显示
          };
          // nowrong
          i.data.map(v => {
            return {
              value: v,
              textStyle: {
                color: color,
                fontFamily: fontFamily
              }
            };
          });
        });
        return ya;
      }
    };
    return {
      chartData: EMPTY_DATA(),
      themeName: "blue",
      loading: false,
      dataEmpty: true,
      dataError: false,
      boyAge: 0,
      girlAge: 0,
      allAge: 0
    };
  },
  props: ["skin", "active", "unit"],
  watch: {
    skin() {
      this.themeName = this.skin;
    },
    active(a) {
      if (!a) return;
      if (this.dataEmpty) {
        this.loadData();
      }
      this.$nextTick(_ => {
        this.$refs["chart"].echarts.resize();
      });
    },
    unit(newUnit, oldUnit) {
      this.loadData(newUnit);
    }
  },
  methods: {
    parse(res) {
      let data = EMPTY_DATA();
      for (let r in res) {
        if (r !== "avg") {
          data.rows.push({
            type: r,
            value: res[r] || 0
          });
        } else {
        }
      }
      data.rows.sort((a, b) => {
        return (
          parseInt(a.type.substring(0, 2)) - parseInt(b.type.substring(0, 2))
        );
      });
      data.columns = ["type", "value"];
      return data;
    },
    avg(res) {
      let data = EMPTY_DATA();
      for (let r in res) {
        if (r == "avg") {
          data = res[r];
          if (res[r][0].value <= 30) {
            data[0].area = "30岁及以下";
          } else if (res[r][0].value > 30 && res[r][0].value <= 40) {
            data[0].area = "31-40（岁）";
          } else if (res[r][0].value > 40 && res[r][0].value <= 50) {
            data[0].area = "41-50（岁）";
          } else if (res[r][0].value > 50) {
            data[0].area = "50岁及以上";
          }
          if (res[r][1].value <= 30) {
            data[1].area = "30岁及以下";
          } else if (res[r][1].value > 30 && res[r][1].value <= 40) {
            data[1].area = "31-40（岁）";
          } else if (res[r][1].value > 40 && res[r][1].value <= 50) {
            data[1].area = "41-50（岁）";
          } else if (res[r][1].value > 50) {
            data[1].area = "50岁及以上";
          }
        } else {
        }
      }
      return data;
    },
    loadData(newUnit) {
      this.loading = true;
      var self = this;
      $fetch(
        "post",
        {
          remoteUrl: "getSexAndAge"
        },
        { departIds: newUnit }
      ).then(
        res => {
          this.chartData = this.parse(res || {});
          this.mklineData = this.avg(res || {});
          this.dataEmpty = !this.chartData.rows.length;
          this.loading = false;
          this.dataError = false;
        },
        err => {
          this.chartData = EMPTY_DATA();
          this.dataEmpty = true;
          this.loading = false;
          this.dataError = true;
          this.$message.error("获取年龄数据失败！");
        }
      );
    }
  },
  created() {
    if (this.active) {
      this.themeName = this.skin;
    }
  },
  template: `<ve-bar :data="chartData" :legend="legend" :grid="grid" :extend="extend" :xAxis="xAxis" :tooltip="tooltip" :theme-name="themeName" :data-empty="dataEmpty&&!loading&&!dataError" :loading="loading" height="100%" ref="chart"><div class="v-charts-data-error" :style="{display: dataError&&!loading ? '' : 'none'}">获取数据失败</div>
    <div v-if="skin=='blue'" style="position: absolute; z-index: 20; right: -20px; top: -10px;color: #ffffff; font-size: 12px; width:150px;height: 50px;">
        <p style="height: 20px;">男性平均年龄：{{boyAge}}</p>
        <p style="height: 20px;">女性平均年龄：{{girlAge}}</p>
        <p style="height: 20px;">总平均年龄：{{allAge}}</p>
    </div>
    <div v-if="skin=='white'" style="position: absolute; z-index: 20; right: -20px; top: -10px;color: #000000; font-size: 12px; width:150px;height: 50px;">
        <p style="height: 20px;">男性平均年龄：{{boyAge}}</p>
        <p style="height: 20px;">女性平均年龄：{{girlAge}}</p>
        <p style="height: 20px;">总平均年龄：{{allAge}}</p>
    </div>
    </ve-bar>`
};
const NldxChart = {
  data() {
    this.chartSettings = {
      //limitShowNum: 9,
      label: {
        show: true,
        position: "outside"
      },
      labelLine: {
        show: true,
        smooth: false,
        lineStyle: {
          show: true,
          type: "solid",
          opacity: 0.85
        }
      }
    };
    this.tooltip = {
      show: true,
      padding: [8, 12],
      textStyle: {
        color: chartStyle["blue"].fontColor,
        fontFamily: chartStyle["blue"].fontFamily
      },
      formatter: function(f) {
        let s = "";
        s += '<h4 style="font-size:15px;line-height:28px;">' + f.name + "</h4>";
        s +=
          '<p style="font-size:12px;line-height:24px;">数量: ' +
          f.value +
          "</p>";
        s +=
          '<p style="font-size:12px;line-height:24px;">占比: ' +
          f.percent +
          "%</p>";
        return s;
      }
    };
    this.grid = {
      containLabel: true,
      top: "7%",
      right: "3%",
      left: "3%",
      bottom: "7%"
    };
    this.legend = {
      type: "scroll",
      pageIconSize: 14,
      bottom: "5%",
      width: "90%",
      height: "10%",
      itemGap: 15,
      orient: "horizontal"
    };
    this.extend = {
      tooltip: t => {
        t.backgroundColor = chartStyle[this.themeName].tooltipColor;
        t.borderColor = chartStyle[this.themeName].tooltipColor;
        return t;
      },
      color: () => {
        return chartStyle[this.themeName].chartColors;
      },
      legend: le => {
        let color = chartStyle[this.themeName].fontColor,
          inactiveColor = chartStyle[this.themeName].inactiveColor,
          fontFamily = chartStyle[this.themeName].fontFamily;
        le.pageIconColor = color;
        le.pageIconInactiveColor = inactiveColor;
        le.pageTextStyle = {
          color: color,
          fontFamily: fontFamily
        };
        le.textStyle = {
          color: color,
          fontFamily: fontFamily
        };
        return le;
      },
      series: se => {
        if (!!se) {
          se.forEach((i, x) => {
            i.label.color = chartStyle[this.themeName].fontColor;
            i.fontFamily = chartStyle[this.themeName].fontFamily;
            i.center = ["50%", "42.5%"];
            i.radius = "70%";
            i.minAngle = 5;
            i.minShowLabelAngle = 15;
            //i.stillShowZeroSum = false;
          });
        }
        return se;
      }
    };
    return {
      chartData: EMPTY_DATA(),
      themeName: "blue",
      loading: false,
      dataEmpty: true,
      dataError: false
    };
  },
  props: ["skin", "active", "unit"],
  watch: {
    skin() {
      this.themeName = this.skin;
    },
    active(a) {
      if (!a) return;
      if (this.dataEmpty) {
        this.loadData();
      }
      this.$nextTick(_ => {
        this.$refs["chart"].echarts.resize();
      });
    },
    unit(newUnit, oldUnit) {
      this.loadData(newUnit);
    }
  },
  methods: {
    parse(res) {
      let data = EMPTY_DATA();
      for (let r in res) {
        data.rows.push({
          type: r,
          value: res[r] || 0
        });
      }
      data.columns = ["type", "value"];
      return data;
    },
    loadData(newUnit) {
      this.loading = true;
      $fetch(
        "post",
        {
          remoteUrl: "getSexCount"
        },
        { departIds: newUnit }
      ).then(
        res => {
          this.chartData = this.parse(res || {});
          this.dataEmpty = !this.chartData.rows.length;
          this.loading = false;
          this.dataError = false;
        },
        err => {
          this.chartData = EMPTY_DATA();
          this.dataEmpty = true;
          this.loading = false;
          this.dataError = true;
          this.$message.error("获取性别数据失败！");
        }
      );
    }
  },
  created() {
    if (this.active) {
      this.themeName = this.skin;
    }
  },
  template: `<ve-pie :data="chartData" :settings="chartSettings" :grid="grid" :theme-name="themeName" :legend="legend" :extend="extend" :tooltip="tooltip" :data-empty="dataEmpty&&!loading&&!dataError" :loading="loading" height="100%" ref="chart"><div class="v-charts-data-error" :style="{display: dataError&&!loading ? '' : 'none'}">获取数据失败</div></ve-pie>`
};
const RymzChart = {
  data() {
    this.grid = {
      containLabel: true,
      top: "7%",
      right: "3%",
      left: "3%",
      bottom: "7%"
    };
    this.tooltip = {
      show: true,
      padding: [8, 12],
      textStyle: {
        color: chartStyle["blue"].fontColor,
        fontFamily: chartStyle["blue"].fontFamily
      },
      formatter: function(f) {
        let s = "";
        s += '<h4 style="font-size:15px;line-height:28px;">' + f.name + "</h4>";
        s +=
          '<p style="font-size:12px;line-height:24px;">数量: ' +
          f.value +
          "</p>";
        if (f.percent == undefined) {
        } else {
          s +=
            '<p style="font-size:12px;line-height:24px;">占比: ' +
            f.percent +
            "%</p>";
        }
        return s;
      }
    };
    this.legend = {
      show: false
    };
    this.yAxis = {
      axisLine: {
        //坐标轴线
        show: false
      },
      axisTick: {
        //坐标轴刻度
        show: false
      },
      splitLine: {
        //坐标轴分割线
        lineStyle: {
          type: "dashed",
          opacity: ".25"
        }
      },
      minInterval: 1
    };
    this.extend = {
      tooltip: t => {
        t.backgroundColor = chartStyle[this.themeName].tooltipColor;
        t.borderColor = chartStyle[this.themeName].tooltipColor;
        return t;
      },
      color: () => {
        return chartStyle[this.themeName].chartColors;
      },
      yAxis: ya => {
        let color = chartStyle[this.themeName].fontColor,
          fontFamily = chartStyle[this.themeName].fontFamily;
        ya.axisLabel = {
          color: color,
          fontFamily: fontFamily
        };
        ya.splitLine.lineStyle.color = color;
        return ya;
      },
      series: se => {
        let color = chartStyle[this.themeName].fontColor,
          fontFamily = chartStyle[this.themeName].fontFamily;
        if (!!se) {
          se.push({
            name: "荣誉",
            type: "pie",
            center: !this.chartData.rows.length
              ? ["50%", "50%"]
              : ["26%", "28%"],
            radius: !this.chartData.rows.length ? "70%" : "38%",
            //stillShowZeroSum: false, //是否在数据和为0（一般情况下所有数据为0） 的时候不显示扇区。
            zlevel: 1, //zlevel用于 Canvas 分层，不同zlevel值的图形会放置在不同的 Canvas 中。
            minAngle: 5, //最小的扇区角度（0 ~ 360），用于防止某个值过小导致扇区太小影响交互。
            minShowLabelAngle: 15, //小于这个角度（0 ~ 360）的扇区，不显示标签（label 和 labelLine）。
            animationType: "scale",
            label: {
              color: color,
              fontFamily: fontFamily
            },
            hoverOffset: 5,
            labelLine: {
              lineStyle: {
                show: true,
                type: "solid",
                opacity: 0.85
              }
            },
            data: this.chartData.pies || []
          });
        }
        return se;
      },
      xAxis: xa => {
        let color = chartStyle[this.themeName].fontColor,
          fontFamily = chartStyle[this.themeName].fontFamily;
        xa.forEach(i => {
          i.axisLine = {
            //坐标轴线
            show: i.data.length !== 0,
            lineStyle: {
              color: color,
              type: "solid",
              opacity: ".85"
            }
          };
          i.axisTick = { show: false };
          i.axisLabel = {
            rotate: 45
            // interval: 0 //横坐标间隔显示
          };
          i.data.map(v => {
            return {
              value: v,
              textStyle: {
                color: color,
                fontFamily: fontFamily
              }
            };
          });
        });
        return xa;
      }
    };
    return {
      chartData: EMPTY_DATA(),
      themeName: "blue",
      loading: false,
      dataEmpty: true,
      dataError: false
    };
  },
  props: ["skin", "active", "unit"],
  watch: {
    skin() {
      this.themeName = this.skin;
    },
    active(a) {
      if (!a) return;
      if (this.dataEmpty) {
        this.loadData(this.newUnit);
      }
      this.$nextTick(_ => {
        this.$refs["chart"].echarts.resize();
      });
    },
    unit(newUnit, oldUnit) {
      this.loadData(newUnit);
    }
  },
  methods: {
    parse(res) {
      let data = EMPTY_DATA();
      data.pies = [];
      for (let r in res.detail) {
        data.rows.push({
          type: r,
          value: res.detail[r] || 0
        });
      }
      for (let r in res.total) {
        data.pies.push({
          name: r,
          value: res.total[r] || 0
        });
      }
      data.columns = ["type", "value"];
      return data;
    },
    loadData(newUnit) {
      this.loading = true;
      $fetch(
        "post",
        {
          remoteUrl: "getNationCount"
        },
        { departIds: newUnit }
      ).then(
        res => {
          this.chartData = this.parse(res || {});
          this.dataEmpty =
            !this.chartData.rows.length && !(this.chartData.pies || []).length;
          this.loading = false;
          this.dataError = false;
        },
        err => {
          this.chartData = EMPTY_DATA();
          this.dataEmpty = true;
          this.loading = false;
          this.dataError = true;
          this.$message.error("获取民族数据失败！");
        }
      );
    }
  },
  created() {
    if (this.active) {
      this.themeName = this.skin;
    }
  },
  template: `<ve-histogram :data="chartData" :grid="grid" :legend="legend" :extend="extend" :yAxis="yAxis" :tooltip="tooltip" :theme-name="themeName" :data-empty="dataEmpty&&!loading&&!dataError" :loading="loading" height="100%" ref="chart"><div class="v-charts-data-error" :style="{display: dataError&&!loading ? '' : 'none'}">获取数据失败</div></ve-histogram>`
};
const JrlyChart = {
  data() {
    this.chartSettings = {
      //limitShowNum: 9,
      radius: ["50%", "70%"],
      label: {
        show: true,
        position: "outside"
      },
      labelLine: {
        show: true,
        smooth: false,
        lineStyle: {
          show: true,
          type: "solid",
          opacity: 0.85
        }
      }
    };
    this.tooltip = {
      show: true,
      padding: [8, 12],
      textStyle: {
        color: chartStyle["blue"].fontColor,
        fontFamily: chartStyle["blue"].fontFamily
      },
      formatter: function(f) {
        let s = "";
        s += '<h4 style="font-size:15px;line-height:28px;">' + f.name + "</h4>";
        s +=
          '<p style="font-size:12px;line-height:24px;">数量: ' +
          f.value +
          "</p>";
        s +=
          '<p style="font-size:12px;line-height:24px;">占比: ' +
          f.percent +
          "%</p>";
        return s;
      }
    };
    this.grid = {
      containLabel: true,
      top: "7%",
      right: "3%",
      left: "3%",
      bottom: "7%"
    };
    this.legend = {
      type: "scroll",
      pageIconSize: 14,
      left: "5%",
      top: "5%",
      bottom: "5%",
      width: "25%",
      itemGap: 15,
      orient: "vertical",
      data: []
    };
    this.extend = {
      tooltip: t => {
        t.backgroundColor = chartStyle[this.themeName].tooltipColor;
        t.borderColor = chartStyle[this.themeName].tooltipColor;
        return t;
      },
      color: () => {
        return chartStyle[this.themeName].chartColors;
      },
      legend: le => {
        let color = chartStyle[this.themeName].fontColor,
          inactiveColor = chartStyle[this.themeName].inactiveColor,
          fontFamily = chartStyle[this.themeName].fontFamily;
        le.pageIconColor = color;
        le.pageIconInactiveColor = inactiveColor;
        le.pageTextStyle = {
          color: color,
          fontFamily: fontFamily
        };
        le.textStyle = {
          color: color,
          fontFamily: fontFamily
        };
        return le;
      },
      series: se => {
        if (!!se) {
          se.forEach((i, x) => {
            i.label.color = chartStyle[this.themeName].fontColor;
            i.fontFamily = chartStyle[this.themeName].fontFamily;
            i.center = ["60%", "50%"];
            i.minAngle = 5;
            i.minShowLabelAngle = 15;
            //i.stillShowZeroSum = false;
          });
        }
        return se;
      }
    };
    return {
      chartData: EMPTY_DATA(),
      themeName: "blue",
      loading: false,
      dataEmpty: true,
      dataError: false
    };
  },
  props: ["skin", "active", "unit"],
  watch: {
    skin() {
      this.themeName = this.skin;
    },
    active(a) {
      if (!a) return;
      if (this.dataEmpty) {
        this.loadData();
      }
      this.$nextTick(_ => {
        this.$refs["chart"].echarts.resize();
      });
    },
    unit(newUnit, oldUnit) {
      this.loadData(newUnit);
    }
  },
  methods: {
    parse(res) {
      let data = EMPTY_DATA();
      data.columns = ["type", "value"];
      for (let r in res) {
        data.rows.push({
          type: r,
          value: res[r] || 0
        });
      }
      return data;
    },
    loadData(newUnit) {
      this.loading = true;
      $fetch(
        "post",
        {
          remoteUrl: "getSourceEntryCount"
        },
        { departIds: newUnit }
      ).then(
        res => {
          let resed = [
            "警察院校毕业生",
            "转业及复员军人",
            "社会招考",
            "实施公务员法机关调入",
            "其他"
          ];
          this.legend.data = [];
          resed.forEach(items => {
            if (res[items]) {
              this.legend.data.push(items);
            }
          });
          this.chartData = this.parse(res || {});
          this.dataEmpty = !this.chartData.rows.length;
          this.loading = false;
          this.dataError = false;
        },
        err => {
          this.chartData = EMPTY_DATA();
          this.dataEmpty = true;
          this.loading = false;
          this.dataError = true;
          this.$message.error("获取来源数据失败！");
        }
      );
    }
  },
  created() {
    if (this.active) {
      this.themeName = this.skin;
    }
  },
  template: `<ve-ring :data="chartData" :settings="chartSettings" :grid="grid" :tooltip="tooltip" :theme-name="themeName" :legend="legend" :extend="extend" :data-empty="dataEmpty&&!loading&&!dataError" :loading="loading" height="100%" ref="chart"><div class="v-charts-data-error" :style="{display: dataError&&!loading ? '' : 'none'}">获取数据失败</div></ve-ring>`
};
const ZwmcChart = {
  data() {
    this.grid = {
      containLabel: true,
      top: "6%",
      right: "3%",
      left: "3%",
      bottom: "6%"
    };
    this.tooltip = {
      show: true,
      padding: [8, 12],
      textStyle: {
        color: chartStyle["blue"].fontColor,
        fontFamily: chartStyle["blue"].fontFamily
      },
      formatter: function(f) {
        let s = "";
        s += '<h4 style="font-size:15px;line-height:28px;">' + f.name + "</h4>";
        s +=
          '<p style="font-size:12px;line-height:24px;">数量: ' +
          f.value +
          "</p>";
        return s;
      }
    };
    this.yAxis = {
      axisLine: {
        //坐标轴线
        show: false
      },
      axisTick: {
        //坐标轴刻度
        show: false
      },
      splitLine: {
        //坐标轴分割线
        lineStyle: {
          type: "dashed",
          opacity: ".25"
        }
      },
      minInterval: 1
    };
    this.legend = {
      show: false
    };
    this.extend = {
      series: se => {
        let color = chartStyle[this.themeName].fontColor,
          fontFamily = chartStyle[this.themeName].fontFamily;
        if (!!se) {
          se.forEach((i, x) => {
            i.label = {
              show: true,
              position: "top",
              color: color,
              fontFamily: fontFamily
            };
            i.barWidth = 30;
          });
        }
        return se;
      },
      tooltip: t => {
        t.backgroundColor = chartStyle[this.themeName].tooltipColor;
        t.borderColor = chartStyle[this.themeName].tooltipColor;
        return t;
      },
      color: () => {
        return chartStyle[this.themeName].chartColors;
      },
      xAxis: xa => {
        let color = chartStyle[this.themeName].fontColor,
          fontFamily = chartStyle[this.themeName].fontFamily;
        xa.forEach(i => {
          i.axisLine = {
            //坐标轴线
            show: i.data.length !== 0,
            lineStyle: {
              color: color,
              type: "solid",
              opacity: ".85"
            }
          };
          i.axisTick = { show: false };
          i.axisLabel = {
            rotate: 45
            // interval: 0 //横坐标间隔显示
          };
          i.data.map(v => {
            return {
              value: v,
              textStyle: {
                color: color,
                fontFamily: fontFamily
              }
            };
          });
        });
        return xa;
      },
      yAxis: ya => {
        let color = chartStyle[this.themeName].fontColor,
          fontFamily = chartStyle[this.themeName].fontFamily;
        ya.axisLabel = {
          color: color,
          fontFamily: fontFamily
        };
        ya.splitLine.lineStyle.color = color;
        return ya;
      }
    };
    return {
      chartData: EMPTY_DATA(),
      themeName: "blue",
      loading: false,
      dataEmpty: true,
      dataError: false
    };
  },
  props: ["skin", "active", "unit"],
  watch: {
    skin() {
      this.themeName = this.skin;
    },
    active(a) {
      if (!a) return;
      if (this.dataEmpty) {
        this.loadData();
      }
      this.$nextTick(_ => {
        this.$refs["chart"].echarts.resize();
      });
    },
    unit(newUnit, oldUnit) {
      this.loadData(newUnit);
    }
  },
  methods: {
    parse(res) {
      let data = EMPTY_DATA();
      data.columns = ["type", "value"];
      for (let r in res) {
        data.rows.push({
          type: r,
          value: res[r]
        });
      }
      return data;
    },
    loadData(newUnit) {
      this.loading = true;
      $fetch(
        "post",
        {
          remoteUrl: "getPosSubCount"
        },
        { departIds: newUnit }
      ).then(
        res => {
          this.chartData = this.parse(res || {});
          this.dataEmpty = !this.chartData.rows.length;
          this.loading = false;
          this.dataError = false;
        },
        err => {
          this.chartData = EMPTY_DATA();
          this.dataEmpty = true;
          this.loading = false;
          this.dataError = true;
          this.$message.error("获取职务数据失败！");
        }
      );
    }
  },
  created() {
    if (this.active) {
      this.themeName = this.skin;
    }
  },
  template: `<ve-histogram :data="chartData" :grid="grid" :legend="legend" :extend="extend" :yAxis="yAxis" :tooltip="tooltip" :theme-name="themeName" :data-empty="dataEmpty&&!loading&&!dataError" :loading="loading" ref="chart" height="100%"><div class="v-charts-data-error" :style="{display: dataError&&!loading ? '' : 'none'}">获取数据失败</div></ve-histogram>`
};
const JxfbChart = {
  data() {
    this.grid = {
      containLabel: true,
      top: "6%",
      right: "3%",
      left: "3%",
      bottom: "6%"
    };
    this.tooltip = {
      show: true,
      padding: [8, 12],
      textStyle: {
        color: chartStyle["blue"].fontColor,
        fontFamily: chartStyle["blue"].fontFamily
      },
      formatter: function(f) {
        let s = "";
        s += '<h4 style="font-size:15px;line-height:28px;">' + f.name + "</h4>";
        s +=
          '<p style="font-size:12px;line-height:24px;">数量: ' +
          f.value +
          "</p>";
        return s;
      }
    };
    this.legend = {
      show: false
    };
    this.yAxis = {
      axisLine: {
        //坐标轴线
        show: false
      },
      axisTick: {
        //坐标轴刻度
        show: false
      },
      splitLine: {
        //坐标轴分割线
        lineStyle: {
          type: "dashed",
          opacity: ".25"
        }
      },
      minInterval: 1
    };

    this.extend = {
      series: se => {
        let color = chartStyle[this.themeName].fontColor,
          fontFamily = chartStyle[this.themeName].fontFamily;
        if (!!se) {
          se.forEach((i, x) => {
            i.label = {
              show: true,
              position: "top",
              color: color,
              fontFamily: fontFamily
            };
            i.barWidth = 30;
          });
        }
        return se;
      },
      tooltip: t => {
        t.backgroundColor = chartStyle[this.themeName].tooltipColor;
        t.borderColor = chartStyle[this.themeName].tooltipColor;
        return t;
      },
      color: () => {
        return chartStyle[this.themeName].chartColors;
      },
      yAxis: ya => {
        let color = chartStyle[this.themeName].fontColor,
          fontFamily = chartStyle[this.themeName].fontFamily;
        ya.axisLabel = {
          color: color,
          fontFamily: fontFamily
        };
        ya.splitLine.lineStyle.color = color;
        return ya;
      },
      xAxis: xa => {
        let color = chartStyle[this.themeName].fontColor,
          fontFamily = chartStyle[this.themeName].fontFamily;
        xa.forEach(i => {
          i.axisLine = {
            //坐标轴线
            show: i.data.length !== 0,
            lineStyle: {
              color: color,
              type: "solid",
              opacity: ".85"
            }
          };
          i.axisTick = { show: false };
          i.axisLabel = {
            rotate: 45
            // interval: 0 //横坐标间隔显示
          };
          i.data.map(v => {
            return {
              value: v,
              textStyle: {
                color: color,
                fontFamily: fontFamily
              }
            };
          });
        });
        return xa;
      }
    };
    return {
      chartData: EMPTY_DATA(),
      themeName: "blue",
      loading: false,
      dataEmpty: true,
      dataError: false
    };
  },
  props: ["skin", "active", "unit"],
  watch: {
    skin() {
      this.themeName = this.skin;
    },
    active(a) {
      if (!a) return;
      if (this.dataEmpty) {
        this.loadData();
      }
      this.$nextTick(_ => {
        this.$refs["chart"].echarts.resize();
      });
    },
    unit(newUnit, oldUnit) {
      this.loadData(newUnit);
    }
  },
  methods: {
    parse(res) {
      let data = EMPTY_DATA();
      for (let r in res) {
        data.rows.push({
          type: r,
          value: res[r] || 0
        });
      }
      data.columns = ["type", "value"];
      return data;
    },
    loadData(newUnit) {
      this.loading = true;
      $fetch(
        "post",
        {
          remoteUrl: "getPoliceRankCount"
        },
        { departIds: newUnit }
      ).then(
        res => {
          this.chartData = this.parse(res || {});
          this.dataEmpty = !this.chartData.rows.length;
          this.loading = false;
          this.dataError = false;
        },
        err => {
          this.chartData = EMPTY_DATA();
          this.dataEmpty = true;
          this.loading = false;
          this.dataError = true;
          this.$message.error("获取警衔数据失败！");
        }
      );
    }
  },
  created() {
    if (this.active) {
      this.themeName = this.skin;
    }
  },
  template: `<ve-histogram :data="chartData" :grid="grid" :legend="legend" :extend="extend" :yAxis="yAxis" :tooltip="tooltip" :theme-name="themeName" :data-empty="dataEmpty&&!loading&&!dataError" :loading="loading" ref="chart" height="100%"><div class="v-charts-data-error" :style="{display: dataError&&!loading ? '' : 'none'}">获取数据失败</div></ve-histogram>`
};
const JtryChart = {
  data() {
    this.chartSettings = {
      dataOrder: {
        label: "value",
        order: "desc"
      }
    };
    this.grid = {
      containLabel: true,
      top: "3%",
      right: "3%",
      left: "14%",
      bottom: "3%"
    };
    this.tooltip = {
      show: true,
      padding: [8, 12],
      textStyle: {
        color: chartStyle["blue"].fontColor,
        fontFamily: chartStyle["blue"].fontFamily
      },
      formatter: function(f) {
        let s = "";
        s += '<h4 style="font-size:15px;line-height:28px;">' + f.name + "</h4>";
        s +=
          '<p style="font-size:12px;line-height:24px;">数量: ' +
          f.value +
          "</p>";
        return s;
      }
    };
    this.legend = {
      show: false
    };
    this.timeline = {
      axisType: "category",
      orient: "vertical",
      autoPlay: true,
      playInterval: 2000,
      top: "3%",
      left: "3%",
      bottom: "3%",
      width: "10%",
      label: {
        // interval: 0,
        position: 15,
        align: "left"
      },
      checkpointStyle: {
        borderWidth: 2
      },
      controlStyle: {
        showPlayBtn: false,
        itemGap: 0,
        itemSize: 18
      },
      data: [],
      symbol: "none"
    };
    this.xAxis = {
      show: false,
      minInterval: 1
    };
    this.extend = {
      series: v => {
        if (v)
          v.forEach(i => {
            i.barWidth = 30;
          });
        return v;
      },
      tooltip: t => {
        t.backgroundColor = chartStyle[this.themeName].tooltipColor;
        t.borderColor = chartStyle[this.themeName].tooltipColor;
        return t;
      },
      color: () => {
        return chartStyle[this.themeName].chartColors;
      },
      timeline: tl => {
        tl.lineStyle = { color: chartStyle[this.themeName].timelineColor };
        tl.controlStyle.borderColor = chartStyle[this.themeName].timelineColor;
        tl.checkpointStyle.borderColor =
          chartStyle[this.themeName].timelineColor;
        tl.checkpointStyle.color =
          chartStyle[this.themeName].timelineSymbolColor;
        tl.label.color = chartStyle[this.themeName].fontColor;
        tl.label.fontFamily = chartStyle[this.themeName].fontFamily;
        tl.lineStyle.show = this.chartData.rows.length !== 0;
        tl.controlStyle.show = this.chartData.rows.length !== 0;
        return tl;
      },
      yAxis: ya => {
        let color = chartStyle[this.themeName].fontColor,
          fontFamily = chartStyle[this.themeName].fontFamily;
        ya.forEach(i => {
          i.axisLine = {
            //坐标轴线
            show: false
          };
          i.axisTick = {
            //坐标轴刻度
            show: false
          };
          i.splitLine = {
            //坐标轴分割线
            show: false
          };
          i.axisLabel = {
            color: color,
            fontFamily: fontFamily
            // interval: 0
          };
        });
        return ya;
      },
      series: se => {
        if (!!se) {
          se.push({
            name: "荣誉",
            type: "pie",
            center: ["75%", "75%"],
            radius: ["15%", "25%"],
            //stillShowZeroSum: false, //是否在数据和为0（一般情况下所有数据为0） 的时候不显示扇区。
            zlevel: 1, //zlevel用于 Canvas 分层，不同zlevel值的图形会放置在不同的 Canvas 中。
            startAngle: 90, //起始角度，支持范围[0, 360]。
            minAngle: 5, //最小的扇区角度（0 ~ 360），用于防止某个值过小导致扇区太小影响交互。
            minShowLabelAngle: 15, //小于这个角度（0 ~ 360）的扇区，不显示标签（label 和 labelLine）。
            animationType: "scale",
            label: {
              color: chartStyle[this.themeName].fontColor,
              fontFamily: chartStyle[this.themeName].fontFamily
            },
            hoverOffset: 5,
            labelLine: {
              lineStyle: {
                show: true,
                type: "solid",
                opacity: 0.85
              }
            }
          });
        }
        return se;
      }
    };
    this.afterConfig = function(opts) {
      let types = [],
        values = [];
      this.data.rows.forEach(tl => {
        types.push(tl.type);
        values.push(tl.value);
      });
      let newOptions = this.data.rows.map((tl, i) => {
        let newTypes = types.slice();
        newTypes[i] = {
          value: types[i],
          textStyle: {
            color: "#CD4568"
          }
        };
        return {
          series: [
            {
              data: values
            },
            {
              data: tl.pies
            }
          ],
          yAxis: [
            {
              data: newTypes
            }
          ]
        };
      });
      opts.timeline.data = types;
      return {
        baseOption: opts,
        options: newOptions
      };
    };
    this.TOTALDATE = [];
    this.TOTALPAGE = 0;
    this.CURPAGE = 1;
    return {
      chartData: EMPTY_DATA(),
      themeName: "blue",
      loading: false,
      dataEmpty: true,
      dataError: false
    };
  },
  props: ["skin", "range", "active"],
  watch: {
    skin() {
      this.themeName = this.skin;
    },
    range(a) {
      this.loadData(a);
    },
    active(a) {
      if (!a) return;
      if (this.dataEmpty && !this.loading) {
        this.loadData(this.range);
      }
      this.$nextTick(_ => {
        this.$refs["chart"].echarts.resize();
      });
    }
  },
  methods: {
    parse(res) {
      let data = EMPTY_DATA();
      data.columns = ["type", "value"];
      if (res !== undefined) {
        data.rows = res.map((m, i) => {
          let rtn = {
            type: m.orgName,
            value: m.count || 0,
            pies: []
          };
          for (let t in m.map) {
            rtn.pies.push({
              name: t,
              value: m.map[t]
            });
          }
          return rtn;
        });
      }
      return data;
    },
    loadData(range) {
      if (!range) return;
      this.loading = true;
      $fetch(
        "post",
        {
          remoteUrl:
            range == "0"
              ? "getRewardInfo"
              : range == "1" ? "getDeptRewardInfo" : "getPCSRewardInfo"
        },
        {}
      ).then(
        res => {
          this.TOTALDATE = res || [];
          this.TOTALPAGE = Math.ceil(this.TOTALDATE.length / COUNT);
          this.CURPAGE = 1;
          this.chartData = this.parse(this.TOTALDATE.slice(0, COUNT).reverse());
          this.dataEmpty = !this.chartData.rows.length;
          this.loading = false;
          this.dataError = false;
        },
        err => {
          this.TOTALDATE = [];
          this.TOTALPAGE = 0;
          this.CURPAGE = 1;
          this.chartData = EMPTY_DATA();
          this.dataEmpty = true;
          this.loading = false;
          this.dataError = true;
          this.$message.error("获取集体荣誉数据失败！");
        }
      );
    },
    toggleNext() {
      if (this.TOTALPAGE <= 1) return;
      this.CURPAGE += 1;
      if (this.TOTALPAGE < this.CURPAGE) {
        this.CURPAGE = 1;
      }
      let start = (this.CURPAGE - 1) * COUNT;
      this.chartData = this.parse(
        this.TOTALDATE.slice(start, start + COUNT).reverse()
      );
      this.dataEmpty = !this.chartData.rows.length;
    }
  },
  created() {
    if (this.active) {
      this.themeName = this.skin;
      this.loadData(this.range);
    }
  },
  template: `<div style="width:100%;height:100%"><ve-bar :data="chartData" :settings="chartSettings" :grid="grid" :tooltip="tooltip" :legend="legend" :timeline="timeline" :xAxis="xAxis" :extend="extend" :after-config="afterConfig" height="100%" :theme-name="themeName" :data-empty="dataEmpty&&!loading&&!dataError" :loading="loading" ref="chart"><div class="v-charts-data-error" :style="{display: dataError&&!loading ? '' : 'none'}">获取数据失败</div></ve-bar><button :style="{display: TOTALPAGE <= 1 ? 'none' : ''}" type="button" class="el-carousel__arrow el-carousel__arrow--right" @click="toggleNext"><i class="el-icon-arrow-right"></i></button></div>`
};
const GrryChart = {
  data() {
    this.chartSettings = {
      stack: {}
    };
    this.grid = {
      containLabel: true,
      top: "3%",
      right: "26%",
      left: "3%",
      bottom: "3%"
    };
    this.tooltip = {
      show: true,
      padding: [8, 12],
      textStyle: {
        color: chartStyle["blue"].fontColor,
        fontFamily: chartStyle["blue"].fontFamily
      },
      trigger: "axis",
      axisPointer: { lineStyle: { color: "#eee" } },
      formatter: function(f) {
        let s =
          '<h4 style="font-size:15px;line-height:28px;">' +
          f[0].axisValue +
          "</h4>";
        f.forEach(i => {
          s +=
            '<p style="font-size:12px;line-height:24px;">' +
            i.seriesName +
            ": " +
            i.value +
            "</p>";
        });
        return s;
      }
    };
    this.legend = {
      type: "scroll",
      pageIconSize: 14,
      right: "3%",
      bottom: "3%",
      width: "22%",
      height: "77%",
      itemGap: 15,
      orient: "vertical"
    };
    this.xAxis = {
      show: false,
      minInterval: 1
    };
    this.extend = {
      series: v => {
        if (v)
          v.forEach(i => {
            i.barWidth = 30;
          });
        return v;
      },
      tooltip: t => {
        t.backgroundColor = chartStyle[this.themeName].tooltipColor;
        t.borderColor = chartStyle[this.themeName].tooltipColor;
        return t;
      },
      color: () => {
        return chartStyle[this.themeName].chartColors;
      },
      legend: le => {
        let color = chartStyle[this.themeName].fontColor,
          inactiveColor = chartStyle[this.themeName].inactiveColor,
          fontFamily = chartStyle[this.themeName].fontFamily;
        le.pageIconColor = color;
        le.pageIconInactiveColor = inactiveColor;
        le.pageTextStyle = {
          color: color,
          fontFamily: fontFamily
        };
        le.textStyle = {
          color: color,
          fontFamily: fontFamily
        };
        return le;
      },
      yAxis: ya => {
        let color = chartStyle[this.themeName].fontColor,
          fontFamily = chartStyle[this.themeName].fontFamily;
        ya.forEach(i => {
          i.axisLine = {
            //坐标轴线
            show: false
          };
          i.axisTick = {
            //坐标轴刻度
            show: false
          };
          i.splitLine = {
            //坐标轴分割线
            show: false
          };
          i.axisLabel = {
            color: color,
            fontFamily: fontFamily
            // interval: 0
          };
        });
        return ya;
      }
    };
    this.TOTALDATE = [];
    this.TOTALPAGE = 0;
    this.CURPAGE = 1;
    return {
      chartData: EMPTY_DATA(),
      themeName: "blue",
      loading: false,
      dataEmpty: true,
      dataError: false
    };
  },
  props: ["skin", "range", "active"],
  watch: {
    skin() {
      this.themeName = this.skin;
    },
    range(a) {
      this.loadData(a);
    },
    active(a) {
      if (!a) return;
      if (this.dataEmpty && !this.loading) {
        this.loadData(this.range);
      }
      this.$nextTick(_ => {
        this.$refs["chart"].echarts.resize();
      });
    }
  },
  methods: {
    parse(res) {
      let data = EMPTY_DATA();
      let columns = {};
      res.forEach((m, i) => {
        for (let t in m.map) {
          columns[t] = true;
        }
      });
      data.columns = ["type"];
      data.rows = res.map((m, i) => {
        let rtn = {
          type: m.orgName
        };
        for (let t in columns) {
          rtn[t] = (m.map || {})[t] || 0;
          if (i === 0) {
            data.columns.push(t);
          }
        }
        return rtn;
      });
      return data;
    },
    loadData(range) {
      if (!range) return;
      this.loading = true;
      $fetch(
        "post",
        {
          remoteUrl:
            range == "0"
              ? "getPersonRewordInfos"
              : range == "1"
                ? "getPersonDeptRewordInfos"
                : "getPersonPCSRewordInfos"
        },
        {}
      ).then(
        res => {
          this.TOTALDATE = res || [];
          this.TOTALPAGE = Math.ceil(this.TOTALDATE.length / COUNT);
          this.CURPAGE = 1;
          this.chartData = this.parse(this.TOTALDATE.slice(0, COUNT).reverse());
          this.chartSettings = {
            stack: { 荣誉: this.chartData.columns.slice(1) }
          };
          this.dataEmpty = !this.chartData.rows.length;
          this.loading = false;
          this.dataError = false;
        },
        err => {
          this.TOTALDATE = [];
          this.TOTALPAGE = 0;
          this.CURPAGE = 1;
          this.chartData = EMPTY_DATA();
          this.chartSettings = {
            stack: {}
          };
          this.dataEmpty = true;
          this.loading = false;
          this.dataError = true;
          this.$message.error("获取个人荣誉数据失败！");
        }
      );
    },
    toggleNext() {
      if (this.TOTALPAGE <= 1) return;
      this.CURPAGE += 1;
      if (this.TOTALPAGE < this.CURPAGE) {
        this.CURPAGE = 1;
      }
      let start = (this.CURPAGE - 1) * COUNT;
      this.chartData = this.parse(
        this.TOTALDATE.slice(start, start + COUNT).reverse()
      );
      this.chartSettings = {
        stack: { 荣誉: this.chartData.columns.slice(1) }
      };
      this.dataEmpty = !this.chartData.rows.length;
    }
  },
  created() {
    if (this.active) {
      this.themeName = this.skin;
      this.loadData(this.range);
    }
  },
  template: `<div style="width:100%;height:100%"><ve-bar :data="chartData" :settings="chartSettings" :grid="grid"  :tooltip="tooltip" :xAxis="xAxis" :extend="extend" :legend="legend" :theme-name="themeName" :data-empty="dataEmpty&&!loading&&!dataError" :loading="loading" height="100%" ref="chart"><div class="v-charts-data-error" :style="{display: dataError&&!loading ? '' : 'none'}">获取数据失败</div></ve-bar><button :style="{display: TOTALPAGE <= 1 ? 'none' : ''}" type="button" class="el-carousel__arrow el-carousel__arrow--right" @click="toggleNext"><i class="el-icon-arrow-right"></i></button></div>`
};
const JxkhChart = {
  data() {
    this.chartSettings = {
      stack: {}
    };
    this.grid = {
      containLabel: true,
      top: "13%",
      right: "3%",
      left: "3%",
      bottom: "13%"
    };
    this.tooltip = {
      show: true,
      padding: [8, 12],
      textStyle: {
        color: chartStyle["blue"].fontColor,
        fontFamily: chartStyle["blue"].fontFamily
      },
      trigger: "axis",
      formatter: function(f) {
        let s = "";
        var total = 0;
        for (var i = 0; i < f.length; i++) {
          total += parseFloat(f[i].value);
        }
        s +=
          '<h4 style="font-size:15px;line-height:28px;">' +
          f[0].name +
          // "(" +
          // total.toFixed(3) +
          // ")" +
          "</h4>";
        for (var i = 0; i < f.length; i++) {
          s +=
            '<div style="width: 8px; height: 8px; border-radius: 100px; display: inline-block;' +
            "background-color:" +
            f[i].color +
            '">' +
            "</div>&nbsp;" +
            '<div style="font-size:14px;line-height:24px; display:inline-block;">' +
            f[i].seriesName +
            ":" +
            f[i].value +
            "</div><br>";
        }
        return s;
      }
    };
    this.xAxis = {
      axisLine: {
        //坐标轴线
        show: false
      },
      axisTick: {
        //坐标轴刻度
        show: false
      },
      splitLine: {
        //坐标轴分割线
        lineStyle: {
          type: "dashed",
          opacity: ".25"
        }
      }
      // minInterval: 0.05
    };
    this.legend = {
      type: "scroll",
      pageIconSize: 14,
      top: "3%",
      width: "90%",
      height: "10%",
      itemGap: 15
    };
    this.extend = {
      series: v => {
        if (v)
          v.forEach(i => {
            i.barWidth = 30;
          });
        return v;
      },
      tooltip: t => {
        t.backgroundColor = chartStyle[this.themeName].tooltipColor;
        t.borderColor = chartStyle[this.themeName].tooltipColor;
        return t;
      },
      color: () => {
        return chartStyle[this.themeName].chartColors;
      },
      xAxis: xa => {
        let color = chartStyle[this.themeName].fontColor,
          fontFamily = chartStyle[this.themeName].fontFamily;
        xa.axisLabel = {
          color: color,
          fontFamily: fontFamily
        };
        xa.splitLine.lineStyle.color = color;
        return xa;
      },
      yAxis: ya => {
        let color = chartStyle[this.themeName].fontColor,
          fontFamily = chartStyle[this.themeName].fontFamily;
        ya.forEach(i => {
          i.axisLine = {
            //坐标轴线
            show: i.data.length !== 0,
            lineStyle: {
              color: color,
              type: "solid",
              opacity: ".85"
            }
          };
          i.axisTick = {
            //坐标轴刻度
            show: false
          };
          i.axisLabel = {
            // interval: 0 //横坐标间隔显示
          };
          // nowrong
          i.data = [];
          this.chartData.rows.forEach(item => {
            i.data.push(item["单位"] + "(" + item["平台考核总分"] + ")");
          });
        });
        return ya;
      },
      legend: le => {
        let color = chartStyle[this.themeName].fontColor,
          inactiveColor = chartStyle[this.themeName].inactiveColor,
          fontFamily = chartStyle[this.themeName].fontFamily;
        le.pageIconColor = color;
        le.pageIconInactiveColor = inactiveColor;
        le.pageTextStyle = {
          color: color,
          fontFamily: fontFamily
        };
        le.textStyle = {
          color: color,
          fontFamily: fontFamily
        };
        return le;
      }
    };
    this.TOTALDATE = [];
    this.TOTALPAGE = 0;
    this.CURPAGE = 1;
    let _this = this;
    this.chartEvents = {
      legendselectchanged: function(e) {
        console.log(e.selected);
        // _this.tooltip.formatter = function(f) {
        //   let s = "";
        //   var total = 0;
        //   for (var i = 0; i < f.length; i++) {
        //     if (e.selected[f[i].seriesName] == true) {
        //       total += parseFloat(f[i].value);
        //     }
        //   }
        //   s +=
        //     '<h4 style="font-size:15px;line-height:28px;">' +
        //     f[0].name +
        //     "</h4>";
        //   for (var i = 0; i < f.length; i++) {
        //     s +=
        //       '<div style="width: 8px; height: 8px; border-radius: 100px; display: inline-block;' +
        //       "background-color:" +
        //       f[i].color +
        //       '">' +
        //       "</div>&nbsp;" +
        //       '<div style="font-size:14px;line-height:24px; display:inline-block;">' +
        //       f[i].seriesName +
        //       ":" +
        //       f[i].value +
        //       "</div><br>";
        //   }
        //   return s;
        // };
        // _this.loadData(_this.unit, _this.year, _this.quarter);
        // _this.chartData.rows.forEach(item => {
        //   let totalse = 0;
        //   for (var ide in item) {
        //     if (ide!== "单位"&&e.selected[ide] == true) {
        //       totalse += parseFloat(item[ide]);
        //     }
        //   }
        //   i.data.push(item["单位"] + "(" + totalse + ")");
        // });
      }
    };
    return {
      chartData: EMPTY_DATA(),
      themeName: "bar",
      loading: false,
      dataEmpty: true,
      dataError: false,
      unit: "",
      year: "",
      quarter: ""
    };
  },
  props: ["skin", "kpiUnit", "kpiYear", "kpiQuarter", "active", "valuecontest"],
  watch: {
    skin() {
      this.themeName = this.skin;
    },
    kpiUnit(unit) {
      this.unit = unit;
      // this.loadData(this.unit, this.year, this.quarter, this.valuecontest);
    },
    kpiYear(year) {
      this.year = year;
    },
    kpiQuarter(quarter) {
      this.quarter = quarter;
      // this.loadData(this.unit, this.year, this.quarter, this.valuecontest);
    },
    valuecontest(valuecontest) {
      this.valuecontest = valuecontest;
      this.loadData(this.unit, this.year, this.quarter,this.valuecontest);
    },
    active(a) {
      if (!a) return;
      if (this.dataEmpty) {
        this.loadData();
      }
      this.$nextTick(_ => {
        this.$refs["chart"].echarts.resize();
      });
    }
  },
  methods: {
    loadData(unit, year, quarter,valuecontest) {
      var map = {
        companyName: "单位",
        basicPartDev: "党建基础",
        workHandlePower: "工作执行力",
        lgcm: "立功创模",
        zgjs: "政工竞赛",
        xwxc: "新闻宣传",
        zgxxdy: "政工信息调研"
      };
      if (!unit || !year || !quarter) return;
      this.loading = true;
      $fetch("get", {
        remoteUrl:
          "getAllKPIs?groupName=" +
          unit +
          "&year=" +
          year +
          "&quarter=" +
          quarter+
          "&items=" +
          valuecontest
      }).then(
        res => {
          function truncate(arr) {
            var m = arr.slice();

            m.pop();
            return m;
          }
          if (res.columns) {
            if (res.columns.length > 2) {
              this.chartData.columns = truncate(res.columns);
            } else {
              this.chartData.columns = res.columns;
            }
            // this.chartData.columns = res.columns;//展示全部
            this.stack = {};
            this.chartSettings.stack = {
              stack: this.chartData.columns.slice(1)
            };
          } else {
            this.chartData.columns = [];
            this.stack = {};
            this.chartSettings.stack = {
              stack: {}
            };
          }
          if (res.rows.length > 0) {
            this.chartData.rows = res.rows;
            this.chartData.rows = this.chartData.rows.reverse();
            this.dataEmpty = !this.chartData.rows.length;
            this.loading = false;
            this.dataError = false;
          } else {
            this.chartData = EMPTY_DATA();
            this.dataEmpty = true;
            this.loading = false;
            this.dataError = true;
          }
        },
        err => {
          this.chartData = EMPTY_DATA();
          this.chartSettings = {
            stack: {}
          };
          this.dataEmpty = true;
          this.loading = false;
          this.dataError = true;
          this.$message.error("获取绩效数据失败！");
        }
      );
    }
  },
  created() {},
  template: `<div style="width:100%;height:100%"><ve-bar :data="chartData" :settings="chartSettings" :events="chartEvents" :grid="grid" :legend="legend" :extend="extend" :xAxis="xAxis" :tooltip="tooltip" :theme-name="themeName" :data-empty="dataEmpty&&!loading&&!dataError" :loading="loading" height="100%" ref="chart"><div class="v-charts-data-error" :style="{display: dataError&&!loading ? '' : 'none'}">获取数据失败</div></ve-bar></div>`
};
const XlxwChart = {
  data() {
    this.grid = {
      containLabel: true,
      top: "7%",
      right: "7%",
      left: "7%",
      bottom: "7%"
    };
    this.tooltip = {
      show: true,
      padding: [8, 12],
      textStyle: {
        color: chartStyle["blue"].fontColor,
        fontFamily: chartStyle["blue"].fontFamily
      },
      formatter: function(f) {
        let s = "";
        s += '<h4 style="font-size:15px;line-height:28px;">' + f.name + "</h4>";
        s +=
          '<p style="font-size:12px;line-height:24px;">数量: ' +
          f.value +
          "</p>";
        return s;
      }
    };
    this.legend = {
      show: false
    };
    this.yAxis = {
      axisLine: {
        //坐标轴线
        show: false
      },
      axisTick: {
        //坐标轴刻度
        show: false
      },
      axisLabel: {
        //坐标轴名称
        show: false
      },
      splitLine: {
        //坐标轴分割线
        lineStyle: {
          type: "dashed",
          opacity: ".25"
        }
      },
      minInterval: 1
    };
    this.extend = {
      series: v => {
        if (v)
          v.forEach(i => {
            i.barWidth = 30;
          });
        return v;
      },
      tooltip: t => {
        t.backgroundColor = chartStyle[this.themeName].tooltipColor;
        t.borderColor = chartStyle[this.themeName].tooltipColor;
        return t;
      },
      color: () => {
        return chartStyle[this.themeName].chartColors;
      },
      yAxis: ya => {
        ya.splitLine.lineStyle.color = chartStyle[this.themeName].fontColor;
        return ya;
      },
      series: se => {
        let color = chartStyle[this.themeName].fontColor,
          fontFamily = chartStyle[this.themeName].fontFamily;
        if (!!se) {
          se.forEach((i, x) => {
            i.label = {
              show: true,
              position: "top",
              color: color,
              fontFamily: fontFamily
            };
            i.barWidth = 30;
          });
        }
        return se;
      },
      xAxis: xa => {
        let color = chartStyle[this.themeName].fontColor,
          fontFamily = chartStyle[this.themeName].fontFamily;
        xa.forEach(i => {
          i.axisLine = {
            //坐标轴线
            show: i.data.length !== 0,
            lineStyle: {
              color: color,
              type: "solid",
              opacity: ".85"
            }
          };
          i.axisTick = { show: false };
          i.axisLabel = {
            rotate: 45
            // interval: 0 //横坐标间隔显示
          };
          i.data.map(v => {
            return {
              value: v,
              textStyle: {
                color: color,
                fontFamily: fontFamily
              }
            };
          });
        });
        return xa;
      }
    };
    return {
      chartData: EMPTY_DATA(),
      themeName: "blue",
      loading: false,
      dataEmpty: true,
      dataError: false
    };
  },
  props: ["skin", "unit"],
  watch: {
    skin() {
      this.themeName = this.skin;
    },
    unit(newUnit, oldUnit) {
      this.loadData(newUnit);
    }
  },
  methods: {
    parse(res) {
      let data = EMPTY_DATA();
      for (let r in res) {
        data.rows.push({
          type: r,
          value: res[r] || 0
        });
      }
      data.columns = ["type", "value"];
      return data;
    },
    loadData(newUnit) {
      this.loading = true;
      $fetch(
        "post",
        {
          remoteUrl: "getEduSubCount"
        },
        { departIds: newUnit }
      ).then(
        res => {
          this.chartData = this.parse(res || {});
          this.dataEmpty = !this.chartData.rows.length;
          this.loading = false;
          this.dataError = false;
        },
        err => {
          this.TOTALDATE = [];
          this.TOTALPAGE = 0;
          this.CURPAGE = 1;
          this.chartData = EMPTY_DATA();
          this.dataEmpty = true;
          this.loading = false;
          this.dataError = true;
          this.$message.error("获取学历学位数据失败！");
        }
      );
    }
  },
  created() {
    this.themeName = this.skin;
    // this.loadData();
  },
  template: `<ve-histogram :data="chartData" :grid="grid" :legend="legend" :extend="extend" :yAxis="yAxis" :tooltip="tooltip" :theme-name="themeName" :data-empty="dataEmpty&&!loading&&!dataError" :loading="loading" height="100%"><div class="v-charts-data-error" :style="{display: dataError&&!loading ? '' : 'none'}">获取数据失败</div></ve-histogram>`
};
const CgjWordChart = {
  data() {
    this.chartSettings = {
      sizeMin: 12,
      sizeMax: 24,
      shape: "pentagon",
      color: chartStyle["blue"].chartColors
    };
    this.tooltip = {
      show: false
    };
    this.extend = {
      tooltip: t => {
        t.backgroundColor = chartStyle[this.themeName].tooltipColor;
        t.borderColor = chartStyle[this.themeName].tooltipColor;
        return t;
      },
      series: se => {
        if (!!se) {
          se.forEach((i, x) => {
            i.rotationRange = [0, 0];
            i.textPadding = 200;
            i.gridSize = 14;
            i.drawOutOfBound = true;
            i.textStyle.emphasis = {
              shadowBlur: 10,
              shadowColor: chartStyle[this.themeName].fontColor
            };
          });
        }
        return se;
      }
    };
    this.grid = {
      containLabel: true,
      top: "7%",
      right: "3%",
      left: "3%",
      bottom: "7%"
    };
    return {
      chartData: EMPTY_DATA(),
      loading: false,
      dataEmpty: true,
      dataError: false
    };
  },
  props: ["skin", "year", "unit"],
  watch: {
    skin() {
      this.themeName = this.skin;
    },
    year(newYear, oldRange) {
      this.loadData(newYear, this.unit);
    },
    unit(newUnit, oldUnit) {
      this.loadData(this.year, newUnit);
    }
  },
  methods: {
    parse(res) {
      let data = EMPTY_DATA();
      data.columns = ["word", "count"];
      for (var key in res) {
        data.rows.push({
          word: key,
          count: res[key]
        });
      }
      return data;
    },
    loadData(year, unit) {
      if (!unit) return;
      this.loading = true;
      var newUnit = [];
      if (unit) {
        for (var i = 0; i < unit.length; i++) {
          newUnit.push(parseInt(unit[i].name));
        }
      }
      $fetch(
        "post",
        {
          remoteUrl: "getGoAbroadCountCount"
        },
        {
          departIds: newUnit,
          year: year
        },
        true
      ).then(
        res => {
          this.chartData = this.parse(res || []);
          this.dataEmpty = !this.chartData.rows.length;
          this.loading = false;
          this.dataError = false;
        },
        err => {
          this.chartData = EMPTY_DATA();
          this.dataEmpty = true;
          this.loading = false;
          this.dataError = true;
          this.$message.error("获取出国境国家数据失败！");
        }
      );
    }
  },
  created() {
    this.themeName = this.skin;
    this.loadData(this.year, this.unit);
  },
  template: `<div style="width:40%;height:100%;"><ve-wordcloud :data="chartData" :grid="grid" :settings="chartSettings" :extend="extend" :theme-name="themeName" :data-empty="dataEmpty&&!loading&&!dataError" :loading="loading" width="100%" height="100%"><div class="v-charts-data-error" :style="{display: dataError&&!loading ? '' : 'none'}">获取数据失败</div></ve-wordcloud></div>`
};

const CgjHgramChart = {
  data() {
    this.chartSettings = {
      stack: {}
    };
    this.grid = {
      containLabel: true,
      top: "6%",
      right: "3%",
      left: "3%",
      bottom: "16%"
    };
    this.tooltip = {
      show: true,
      padding: [8, 12],
      textStyle: {
        color: chartStyle["blue"].fontColor,
        fontFamily: chartStyle["blue"].fontFamily
      },
      trigger: "axis",
      axisPointer: { lineStyle: { color: "#eee" } },
      formatter: function(f) {
        let s =
          '<h4 style="font-size:15px;line-height:28px;">' +
          f[0].axisValue +
          "</h4>";
        f.forEach(i => {
          s +=
            '<p style="font-size:12px;line-height:24px;">' +
            i.seriesName +
            ": " +
            i.value +
            "</p>";
        });
        return s;
      }
    };
    this.yAxis = {
      axisLine: {
        //坐标轴线
        show: false
      },
      axisTick: {
        //坐标轴刻度
        show: false
      },
      splitLine: {
        //坐标轴分割线
        lineStyle: {
          type: "dashed",
          opacity: ".25"
        }
      },
      minInterval: 1
    };
    this.legend = {
      type: "scroll",
      pageIconSize: 14,
      bottom: "5%",
      width: "90%",
      height: "10%",
      itemGap: 15
    };
    this.extend = {
      tooltip: t => {
        t.backgroundColor = chartStyle[this.themeName].tooltipColor;
        t.borderColor = chartStyle[this.themeName].tooltipColor;
        return t;
      },
      color: () => {
        return chartStyle[this.themeName].chartColors;
      },
      yAxis: ya => {
        let color = chartStyle[this.themeName].fontColor,
          fontFamily = chartStyle[this.themeName].fontFamily;
        ya.axisLabel = {
          color: color,
          fontFamily: fontFamily
        };
        ya.splitLine.lineStyle.color = color;
        return ya;
      },
      legend: {
        show: false
      },
      //            legend: (le) => {
      //                let color = chartStyle[this.themeName].fontColor,
      //                    inactiveColor = chartStyle[this.themeName].inactiveColor,
      //                    fontFamily = chartStyle[this.themeName].fontFamily;
      //                le.pageIconColor = color;
      //                le.pageIconInactiveColor = inactiveColor;
      //                le.pageTextStyle = {
      //                    color: color,
      //                    fontFamily: fontFamily
      //                };
      //                le.textStyle = {
      //                    color: color,
      //                    fontFamily: fontFamily
      //                };
      //                return le;
      //            },
      xAxis: xa => {
        let color = chartStyle[this.themeName].fontColor,
          fontFamily = chartStyle[this.themeName].fontFamily;
        xa.forEach(i => {
          i.axisLine = {
            //坐标轴线
            show: i.data.length !== 0,
            lineStyle: {
              color: color,
              type: "solid",
              opacity: ".85"
            }
          };
          i.axisTick = { show: false };
          i.axisLabel = {
            // interval: 0 //横坐标间隔显示
          };
          i.data.map(v => {
            return {
              value: v,
              textStyle: {
                color: color,
                fontFamily: fontFamily
              }
            };
          });
        });
        return xa;
      },
      series: {
        label: { show: true, position: "top" }
      }
    };
    return {
      chartData: EMPTY_DATA(),
      themeName: "blue",
      loading: false,
      dataEmpty: true,
      dataError: false
    };
  },
  props: ["skin", "year", "unit"],
  watch: {
    skin() {
      this.themeName = this.skin;
    },
    year(newYear, oldRange) {
      this.loadData(newYear, this.unit);
    },
    unit(newUnit, oldUnit) {
      this.loadData(this.year, newUnit);
    }
  },
  methods: {
    parse(res) {
      let data = EMPTY_DATA();
      const months = [
        "1月",
        "2月",
        "3月",
        "4月",
        "5月",
        "6月",
        "7月",
        "8月",
        "9月",
        "10月",
        "11月",
        "12月"
      ];
      data.columns = ["name", "次数"];
      data.rows = months.map((m, i) => {
        let rtn = { name: m, 次数: "" };
        rtn["次数"] = res[i];
        return rtn;
      });
      //如果data.columns里没有再增添数据，则表示数据返还为空
      return data.columns.length === 1 ? EMPTY_DATA() : data;
    },
    loadData(year, unit) {
      if (!unit) return;
      this.loading = true;
      var newUnit = [];
      if (unit) {
        for (var i = 0; i < unit.length; i++) {
          newUnit.push(parseInt(unit[i].name));
        }
      }
      $fetch(
        "post",
        {
          remoteUrl: "findRecordsCount"
        },
        { departIds: newUnit, year: year }
      ).then(
        res => {
          this.chartData = this.parse(res || []);
          this.dataEmpty = !this.chartData.rows.length;
          this.loading = false;
          this.dataError = false;
        },
        err => {
          this.chartData = EMPTY_DATA();
          this.chartSettings = {
            stack: {}
          };
          this.dataEmpty = true;
          this.loading = false;
          this.dataError = true;
          this.$message.error("获取请假概览数据失败！");
        }
      );
    }
  },
  created() {
    this.themeName = this.skin;
    this.loadData(this.year, this.unit);
  },
  template: `<div style="width:60%;height:100%;"><ve-histogram :data="chartData" :settings="chartSettings" :grid="grid" :legend="legend" :extend="extend" :yAxis="yAxis" :tooltip="tooltip" :theme-name="themeName" :data-empty="dataEmpty&&!loading&&!dataError" :loading="loading" width="100%" height="100%"><div class="v-charts-data-error" :style="{display: dataError&&!loading ? '' : 'none'}">获取数据失败</div></ve-histogram></div>`
};
const QjglChart = {
  data() {
    this.grid = {
      containLabel: true,
      top: "6%",
      right: "3%",
      left: "3%",
      bottom: "16%"
    };
    this.tooltip = {
      show: true,
      padding: [8, 12],
      textStyle: {
        color: chartStyle["blue"].fontColor,
        fontFamily: chartStyle["blue"].fontFamily
      },
      trigger: "axis",
      axisPointer: { lineStyle: { color: "#eee" } },
      formatter: function(f) {
        let s =
          '<h4 style="font-size:15px;line-height:28px;">' +
          f[0].axisValue +
          "</h4>";
        f.forEach(i => {
          s +=
            '<p style="font-size:12px;line-height:24px;">' +
            i.seriesName +
            ": " +
            i.value[1] +
            "</p>";
        });
        return s;
      }
    };
    this.yAxis = {
      axisLine: {
        //坐标轴线
        show: false
      },
      axisTick: {
        //坐标轴刻度
        show: false
      },
      splitLine: {
        //坐标轴分割线
        lineStyle: {
          type: "dashed",
          opacity: ".25"
        }
      },
      minInterval: 1
    };
    this.legend = {
      type: "scroll",
      pageIconSize: 14,
      bottom: "5%",
      width: "90%",
      height: "10%",
      itemGap: 15
    };
    this.extend = {
      tooltip: t => {
        t.backgroundColor = chartStyle[this.themeName].tooltipColor;
        t.borderColor = chartStyle[this.themeName].tooltipColor;
        return t;
      },
      color: () => {
        return chartStyle[this.themeName].chartColors;
      },
      yAxis: ya => {
        let color = chartStyle[this.themeName].fontColor,
          fontFamily = chartStyle[this.themeName].fontFamily;
        ya.axisLabel = {
          color: color,
          fontFamily: fontFamily
        };
        ya.splitLine.lineStyle.color = color;
        return ya;
      },
      legend: le => {
        let color = chartStyle[this.themeName].fontColor,
          inactiveColor = chartStyle[this.themeName].inactiveColor,
          fontFamily = chartStyle[this.themeName].fontFamily;
        le.pageIconColor = color;
        le.pageIconInactiveColor = inactiveColor;
        le.pageTextStyle = {
          color: color,
          fontFamily: fontFamily
        };
        le.textStyle = {
          color: color,
          fontFamily: fontFamily
        };
        return le;
      },
      series: se => {
        if (!!se) {
          se.forEach(i => {
            i.symbol = "";
            i.smooth = false;
          });
        }
        return se;
      },
      xAxis: xa => {
        let color = chartStyle[this.themeName].fontColor,
          fontFamily = chartStyle[this.themeName].fontFamily;
        xa.forEach(i => {
          i.axisLine = {
            //坐标轴线
            show: i.data.length !== 0,
            lineStyle: {
              color: color,
              type: "solid",
              opacity: ".85"
            }
          };
          i.axisTick = { show: false };
          i.axisLabel = {
            // interval: 0 //横坐标间隔显示
          };
          i.data.map(v => {
            return {
              value: v,
              textStyle: {
                color: color,
                fontFamily: fontFamily
              }
            };
          });
        });
        return xa;
      }
    };
    return {
      chartData: EMPTY_DATA(),
      themeName: "blue",
      loading: false,
      dataEmpty: true,
      dataError: false
    };
  },
  props: ["skin", "year", "unit"],
  watch: {
    skin() {
      this.themeName = this.skin;
    },
    year(newYear, oldRange) {
      this.loadData(newYear, this.unit);
    },
    unit(newUnit, oldUnit) {
      this.loadData(this.year, newUnit);
    }
  },
  methods: {
    parse(res) {
      let data = EMPTY_DATA();
      const months = [
        "1月",
        "2月",
        "3月",
        "4月",
        "5月",
        "6月",
        "7月",
        "8月",
        "9月",
        "10月",
        "11月",
        "12月"
      ];
      data.columns = ["month"];
      data.rows = months.map((m, i) => {
        let rtn = { month: m };
        for (let r in res) {
          rtn[r] = res[r][i];
          if (i === 0) {
            data.columns.push(r);
          }
        }
        return rtn;
      });
      //如果data.columns里没有再增添数据，则表示数据返还为空
      return data.columns.length === 1 ? EMPTY_DATA() : data;
    },
    loadData(year, unit) {
      if (!unit) return;
      this.loading = true;
      var newUnit = [];
      if (unit) {
        for (var i = 0; i < unit.length; i++) {
          newUnit.push(parseInt(unit[i].name));
        }
      }
      $fetch(
        "post",
        {
          remoteUrl: "getPleSeCount"
        },
        { departIds: newUnit, year: year }
      ).then(
        res => {
          this.chartData = this.parse(res || {});
          this.dataEmpty = !this.chartData.rows.length;
          this.loading = false;
          this.dataError = false;
        },
        err => {
          this.chartData = EMPTY_DATA();
          this.dataEmpty = true;
          this.loading = false;
          this.dataError = true;
          this.$message.error("获取请假概览数据失败！");
        }
      );
    }
  },
  created() {
    this.themeName = this.skin;
    this.loadData(this.year, this.unit);
  },
  template: `<ve-line :data="chartData" :legend="legend" :grid="grid" :extend="extend" :yAxis="yAxis" :tooltip="tooltip" :theme-name="themeName" :data-empty="dataEmpty&&!loading&&!dataError" :loading="loading" height="100%"><div class="v-charts-data-error" :style="{display: dataError&&!loading ? '' : 'none'}">获取数据失败</div></ve-line>`
};

import DialogRygk from "./IndexManagePlus/rygk";
import DialogDwgk from "./IndexManagePlus/dwgk";
import DialogCgjgk from "./IndexManagePlus/cgjgk";
import DialogQjgk from "./IndexManagePlus/qjgk";

export default {
  name: "indexManage",
  components: {
    JtryChart,
    GrryChart,
    JxkhChart,
    XbnlChart,
    NldxChart,
    JrlyChart,
    ZwmcChart,
    RymzChart,
    JxfbChart,
    XlxwChart,
    CgjWordChart,
    CgjHgramChart,
    QjglChart,
    DialogRygk,
    DialogDwgk,
    DialogCgjgk,
    DialogQjgk
  },
  data() {
    return {
      companyList: [],
      skin: "blue",
      activeDwgk: "first",
      activeRygk: "one",
      unitOptionsRYGK: [],
      rangeItems: [],
      rangeJtry: "",
      rangeGrry: "",
      rangeJxkh: "",
      rygkUnitValue: [],
      unitRYGK: "",
      cgjUnitValue: [],
      cgjYearValue: "",
      qjglUnitValue: "",
      qjglYearValue: "",
      unitOptions: [],
      unitOptionsCGJ: [],
      unitOptionsQJGL: [],
      cgjYearOptions: [],
      qjglYearOptions: "",
      kpiUnit: "",
      kpiYear: "",
      kpiQuarter: "",
      kpiUnitOptions: [],
      kpiYearOptions: [],
      kpiQuarterOptions: [],
      showJxkhChart: false,
      zzmmData: [],
      zzmmDataLoading: false,
      zzmmDatase: [],
      zzmmDataLoadingse: false,
      props: {
        label: "name",
        children: "zones",
        isLeaf: "leaf"
      },
      treedata: [
        {
          id: 1,
          label: "一级 1",
          children: [
            { id: 2, label: "二级 1-1" },
            { id: 3, label: "二级 1-1" },
            { id: 4, label: "二级 1-1" },
            { id: 5, label: "二级 1-1" },
            { id: 6, label: "二级 1-1" }
          ]
        },
        {
          id: 7,
          label: "一级 2",
          children: [
            { id: 8, label: "二级 1-1" },
            { id: 9, label: "二级 1-1" },
            { id: 10, label: "二级 1-1" },
            { id: 11, label: "二级 1-1" },
            { id: 12, label: "二级 1-1" }
          ]
        }
      ],
      defaultProps: {
        children: "children",
        label: "label"
      },
      paiming_fenju: [],
      paiming_bumen: [],
      cgjData: [],
      deptRewordLeftUnitOptions: [],
      deptRewordRightUnitOptions: [],
      personRewordLeftUnitOptions: [],
      personRewordRightUnitOptions: [],
      personRewordLeftUnit: "",
      personRewordRightUnit: "",
      cgjTreeData: [
        {
          id: 1,
          label: "一级 1",
          children: [
            {
              id: 4,
              label: "二级 1-1",
              children: [
                {
                  id: 9,
                  label: "三级 1-1-1"
                },
                {
                  id: 10,
                  label: "三级 1-1-2"
                }
              ]
            }
          ]
        },
        {
          id: 2,
          label: "一级 2",
          children: [
            {
              id: 5,
              label: "二级 2-1"
            },
            {
              id: 6,
              label: "二级 2-2"
            }
          ]
        },
        {
          id: 3,
          label: "一级 3",
          children: [
            {
              id: 7,
              label: "二级 3-1"
            },
            {
              id: 8,
              label: "二级 3-2"
            }
          ]
        }
      ],
      dataProps2: {
        children: "childCompany",
        label: "unitName"
      },
      cgjValue: "",
      qjglValue: "",
      rygkValue: "",
      // dialog
      dialogFormVisible: false,
      dialogTitle: "单位概况",
      ifFullScreen: false,
      activeDialog: "单位概况",
      showBackMenu: true,
      poster: "",
      names: "",
      unitOptionsRYGKs: [],
      UnitValues: [],
      activeClasse: "",
      activeTabs: "",
      departIds1: [],
      departIds2: [],
      zjAttListsoptions: [
        {
          value: "全部",
          label: "全部"
        },
        {
          value: "综合管理序列",
          label: "综合管理序列"
        },
        {
          value: "执法勤务序列",
          label: "执法勤务序列"
        },
        {
          value: "警务技术序列",
          label: "警务技术序列"
        }
      ],
      UnitValuezj: "全部",
      UnitValuezjs: "",
      UnitValuezjb: true,
      seriesDataclick: [],
      seriesclick: false,
      showdatas: false,
      falgses: false,
      valuecontest: [],
      cities: []
    };
  },
  methods: {
    FunActiveDwgk(params) {
      this.activeDwgk = params;
      if (params == "first") {
        this.awardPaiming("", "fenju_jiti", "getCollectiveHonor");
        this.personalAwardPaiming("right");
      } else if (params == "third") {
        this.showJxkhChart = true;
        this.loadKPIYear();
      }
    },
    kpiYearChange(value) {
      this.loadKPIQuarter(value);
    },
    loadKPIYear() {
      $fetch("get", {
        remoteUrl: "getKPIYearMenu"
      })
        .then(
          res => {
            res = res || {};
            let items = [];
            for (let r in res) {
              items.push(res[r]);
            }
            this.kpiYearOptions = items;
            this.kpiYear = items[0];
          },
          err => {
            this.$message.error("获取考核年份菜单数据失败！");
          }
        )
        .then(() => {
          this.loadKPIQuarter();
          this.loadKPIGroup();
        });
    },
    loadKPIGroup(value, kpiQuarterss) {
      if (value) {
        this.kpiYear = value;
      }
      let kpiQuartersse = "";
      if (kpiQuarterss) {
        kpiQuartersse = kpiQuarterss;
      } else {
        kpiQuartersse = this.kpiQuarter;
      }
      this.kpiUnitOptions = [];
      this.kpiUnit = "";
      $fetch(
        "post",
        {
          remoteUrl:
            "getKPIGroupMenu?year=" + this.kpiYear + "&quarter=" + kpiQuartersse
        },
        {}
      ).then(
        res => {
          res = res || {};
          let items = [];
          for (let r in res) {
            items.push(res[r]);
          }
          this.kpiUnitOptions = items;
          this.kpiUnit = items[0];
          this.getKPIItemMenu(this.kpiYear, this.kpiQuarter, this.kpiUnit);
        },
        err => {
          this.$message.error("获取考核单位菜单数据失败！");
        }
      );
    },
    getKPIItemMenu(kpiYear, kpiQuarter, kpiUnit) {
      let _this = this;
      if (!kpiUnit || !kpiQuarter || !kpiYear) {
        $fetch(
          "post",
          {
            remoteUrl:
              "getKPIItemMenu?year=" +
              _this.kpiYear +
              "&quarter=" +
              _this.kpiQuarter +
              "&groupName=" +
              _this.kpiUnit
          },
          {}
        ).then(
          res => {
            res = res || {};
            this.cities = res;
            let valuecontestse = [];
            for (let ks = 0; ks < res.length; ks++) {
              valuecontestse.push(res[ks].value);
            }
            this.valuecontest = valuecontestse;
          },
          err => {
            this.$message.error("获取考核项菜单数据失败！");
          }
        );
      } else {
      $fetch(
        "post",
        {
          remoteUrl:
            "getKPIItemMenu?year=" +
            kpiYear +
            "&quarter=" +
            kpiQuarter +
            "&groupName=" +
            kpiUnit
        },
        {}
      ).then(
        res => {
          res = res || {};
          this.cities = res;
          let valuecontestse = [];
          for (let ks = 0; ks < res.length; ks++) {
            valuecontestse.push(res[ks].value);
          }
          this.valuecontest = valuecontestse;
        },
        err => {
          this.$message.error("获取考核项菜单数据失败！");
        }
      );
      }
    },
    loadKPIQuarter(value) {
      if (value) {
        this.kpiYear = value;
      }
      this.kpiQuarterOptions = [];
      this.kpiQuarter = "";
      $fetch("get", {
        remoteUrl: "getKPIQuarterMenu?year=" + this.kpiYear
      }).then(
        res => {
          res = res || {};
          let items = [];
          for (let r in res) {
            items.push(res[r]);
          }
          this.kpiQuarterOptions = items;
          this.kpiQuarter = items[0];
          this.loadKPIGroup(value, this.kpiQuarter);
        },
        err => {
          this.$message.error("获取考核季度菜单数据失败！");
        }
      );
    },
    kpiQuarterChange(value) {
      let kpiQuartersse = "";
      if (value) {
        kpiQuartersse = value;
      } else {
        kpiQuartersse = this.kpiQuarter;
      }
      $fetch(
        "post",
        {
          remoteUrl:
            "getKPIGroupMenu?year=" + this.kpiYear + "&quarter=" + kpiQuartersse
        },
        {}
      ).then(
        res => {
          res = res || {};
          let items = [];
          for (let r in res) {
            items.push(res[r]);
          }
          this.kpiUnitOptions = items;
          this.kpiUnit = items[0];
          this.getKPIItemMenu(this.kpiYear, this.kpiQuarter, this.kpiUnit);
        },
        err => {
          this.$message.error("获取考核单位菜单数据失败！");
        }
      );
    },
    checkGroupNodeFenju_jiti(item, node) {
      var checkArray = node.checkedNodes.map((a, i) => {
        return a.id;
      });
      let checkedKeyse = checkArray;
      this.awardPaiming(checkedKeyse, "fenju_jiti", "getCollectiveHonor");
    },
    checkGroupNodeBumen_jiti(item, node) {
      var checkArray = node.checkedNodes.map((a, i) => {
        return a.id;
      });
      let checkedKeyse = checkArray;
      this.personalAwardPaiming("right", checkedKeyse);
    },
    awardPaiming(params, role, baseURL) {
      var self = this;
      var params = params;
      var newParams = [];
      for (var i = 0; i < params.length; i++) {
        newParams.push(params[i]);
      }
      $fetch(
        "post",
        {
          remoteUrl: baseURL
        },
        { departIds: newParams }
      ).then(
        res => {
          var newRes = res;
          if (role == "fenju_jiti") {
            self.paiming_fenju = newRes;
          } else if (role == "bumen_jiti") {
            self.paiming_bumen = newRes;
          }
          for (var i = 0; i < newRes.length; i++) {
            newRes[i].index = i + 1;
          }
        },
        err => {
          this.$message.error("获取单位列表数据失败！");
        }
      );
    },
    personalAwardPaiming(type, deptIds) {
      var self = this;
      var newParams = [];
      if (deptIds) {
        for (var i = 0; i < deptIds.length; i++) {
          newParams.push(deptIds[i]);
        }
      }
      $fetch(
        "post",
        {
          remoteUrl: "getPersonalReward"
        },
        {
          deptIds: newParams,
          type: type
        }
      ).then(
        res => {
          var newRes = [];
          res.forEach((item, index) => {
            newRes.push({
              index: index + 1,
              orgName: item.name,
              count: item.value,
              id: item.id
            });
          });
          if (type == "left") {
            self.paiming_fenju = newRes;
          } else if (type == "right") {
            self.paiming_bumen = newRes;
          }
        },
        err => {
          this.$message.error("获取数据失败！");
        }
      );
    },
    percent(item) {
      if (item) {
        if (
          parseInt(item.count / this.paiming_fenju[0].count * 100) <= 100 ||
          parseInt(item.count / this.paiming_fenju[0].count * 100) > 0
        ) {
          return parseInt(item.count / this.paiming_fenju[0].count * 100) - 1;
        } else {
          return 0;
        }
      } else {
        return 0;
      }
    },
    loadRangeItems() {
      $fetch(
        "post",
        {
          remoteUrl: "getMenu"
        },
        {}
      ).then(
        res => {
          res = res || {};
          let items = [];
          for (let r in res) {
            items.push({
              name: r,
              value: res[r]
            });
          }
          this.rangeItems = items;
          this.rangeJtry = items[0].value;
          this.rangeGrry = items[0].value;
          this.rangeJxkh = items[0].value;
        },
        err => {
          this.$message.error("获取荣誉菜单数据失败！");
        }
      );
    },
    loadZzmmData(newUnit) {
      this.zzmmDataLoading = true;
      $fetch(
        "post",
        {
          remoteUrl: "getPoliticalIdentity"
        },
        { departIds: newUnit }
      ).then(
        res => {
          res = res || {};
          // "其他党派",
          let data = ["中共党员", "预备党员", "共青团员", "群众"];
          this.zzmmData = data.map((a, i) => {
            return {
              type:
                i == 0
                  ? "zzmmdy"
                  : i == 1
                    ? "zzmmybdy"
                    : i == 2 ? "zzmmty" : i == 3 ? "zzmmqz" : "zzmmqtdp",
              name: a,
              value: res[a] || 0
            };
          });
          this.zzmmDataLoading = false;
        },
        err => {
          this.zzmmDataLoading = false;
          this.zzmmData = null;
          this.$message.error("获取政治面貌数据失败！");
        }
      );
    },
    loadDatass(newUnit) {
      this.zzmmDataLoadingse = true;
      $fetch(
        "post",
        {
          remoteUrl: "getSexCount"
        },
        { departIds: newUnit }
      ).then(
        res => {
          res = res || {};
          let data = ["男警员", "女警员"];
          let valueses = 0;
          this.zzmmDatase = data.map((a, i) => {
            valueses = valueses + res[a];
          });
          this.zzmmDatase = data.map((a, i) => {
            let valuese = res[a] / valueses * 100;
            let number = valuese.toFixed(2) + "%";
            return {
              type: i == 0 ? "zzmmqnan" : "zzmmqnv",
              name: a,
              value: res[a] || 0,
              valuese: number
            };
          });
          this.zzmmDataLoadingse = false;
        },
        err => {
          this.zzmmDataLoadingse = false;
          this.zzmmDatase = null;
          this.$message.error("获取政治面貌数据失败！");
        }
      );
    },
    fixName(value) {
      if (value == "江苏省南京市公安局") {
        return value;
      } else {
        return value.replace("江苏省南京市公安局", "");
      }
    },
    filterNode(value, data) {
      if (!value) return true;
      return data.unitName.indexOf(value) !== -1;
    },
    handleNodeCheckRYGK(item, node) {
      var checkArray = node.checkedNodes.map((a, i) => {
        return {
          name: a.unitId
        };
      });
      this.rygkValue = node.checkedKeys;
      this.rygkUnitValue = node.checkedKeys;
      this.loadZzmmData(this.rygkUnitValue);
      this.loadDatass(this.rygkUnitValue);
      if (this.activeRygk == "nine") {
        this.handleNodeCheckRYGKzj(this.rygkUnitValue);
      }
    },
    handleNodeCheckCGJ(item, node) {
      var checkArray = node.checkedNodes.map((a, i) => {
        return {
          name: a.unitId
        };
      });
      this.cgjValue = node.unitName;
      this.cgjUnitValue = checkArray;
    },
    loadUnitOptionsData() {
      var self = this;
      let idCard = localStorage.getItem("idCard");
      $fetchUsernames(
        "post",
        {
          remoteUrl: "/statistics/log/getPersonLog"
        },
        {
          flagName: "队伍可视化",
          idNumber: idCard,
          pageName: "队伍可视化"
        }
      ).then(
        res => {
          console.log(res.message);
        },
        err => {
          this.$message.error("获取数据失败！");
        }
      );
      $fetchUsers(
        "post",
        {
          remoteUrl: "orgmanage/unittreeAuto"
        },
        {}
      ).then(
        res => {
          this.unitOptionsRYGK = res.data;
          self.$refs.RTYGKtree.setCheckedKeys([self.unitOptionsRYGK[0].unitId]);
          setTimeout(() => {
            let checkedKeys = self.$refs.RTYGKtree.getCheckedKeys();
            self.rygkUnitValue = checkedKeys;
            self.unitRYGK = self.rygkUnitValue;
            this.loadZzmmData(self.rygkUnitValue);
            this.loadDatass(this.rygkUnitValue);
          }, 50);
          this.unitOptionsQJGL = res.data;
          this.qjglUnitValue = (this.unitOptionsQJGL[0] || { value: "" }).value;
          self.$refs.qjTree.setCheckedKeys([self.unitOptionsQJGL[0].unitId]);
          setTimeout(() => {
            let checkedKeys = self.$refs.qjTree.getCheckedKeys();
            var checkArray = checkedKeys.map((a, i) => {
              return {
                name: a
              };
            });
            self.qjglUnitValue = checkArray;
            this.loadQjglYearOptionsData(checkedKeys);
          }, 50);

          this.unitOptionsCGJ = res.data;
          this.cgjUnitValue = (this.unitOptionsCGJ[0] || { value: "" }).value;
          self.$refs.tree.setCheckedKeys([self.unitOptionsCGJ[0].unitId]);
          setTimeout(() => {
            let checkedKeys = self.$refs.tree.getCheckedKeys();
            var checkArray = checkedKeys.map((a, i) => {
              return {
                name: a
              };
            });
            this.cgjUnitValue = checkArray;
            this.loadCgjYearOptionsData(checkedKeys);
          }, 50);
          if (this.rygkUnitValue && this.rygkUnitValue.length > 0) {
            this.falgses = true;
          }
        },
        err => {
          this.$message.error("获取单位列表数据失败！");
        }
      );
      $fetchUsers(
        "post",
        {
          remoteUrl: "mangagerData/getRewardTree"
        },
        {}
      ).then(
        res => {
          this.deptRewordLeftUnitOptions = res.data;
          this.deptRewordRightUnitOptions = res.data;
          // self.$refs.RYGKtree1.setCheckedKeys([self.deptRewordLeftUnitOptions[0].unitId]);
          // self.$refs.RYGKtree2.setCheckedKeys([self.deptRewordRightUnitOptions[0].unitId]);
        },
        err => {
          this.$message.error("获取单位列表数据失败！");
        }
      );
    },
    handleNodeCheckQJGL(item, node) {
      var checkArray = node.checkedNodes.map((a, i) => {
        return {
          name: a.unitId
        };
      });
      this.qjglValue = node.unitName;
      this.qjglUnitValue = checkArray;
    },
    loadCgjYearOptionsData(checkedKeys) {
      $fetch(
        "post",
        {
          remoteUrl: "getAbroadYear"
        },
        { departIds: checkedKeys }
      ).then(
        res => {
          res = res || {};
          var options = res.map((a, i) => {
            if (a.name && a.value) {
              return {
                name: a.name,
                value: a.value
              };
            }
          });
          this.cgjYearOptions = options;
          this.cgjYearValue = (this.cgjYearOptions[0] || { value: "" }).value;
        },
        err => {
          this.$message.error("获取出国境年度数据失败！");
        }
      );
    },
    loadQjglYearOptionsData(checkedKeys) {
      $fetch(
        "post",
        {
          remoteUrl: "getFormYear"
        },
        { departIds: checkedKeys }
      ).then(
        res => {
          res = res || {};
          var options = res.map((a, i) => {
            return {
              name: a.name,
              value: a.value
            };
          });
          this.qjglYearOptions = res;
          this.qjglYearValue = (this.qjglYearOptions[0] || { value: "" }).value;
        },
        err => {
          this.$message.error("获取请假年度数据失败！");
        }
      );
    },
    // 高级详情部分
    dialogEmit(e) {
      this.activeDialog = e;
      this.dialogTitle = e;
    },
    btnFullScreen() {
      if (this.ifFullScreen) {
        this.ifFullScreen = false;
      } else {
        this.ifFullScreen = true;
      }
    },
    btnDialog(item, items, itemse) {
      this.dialogFormVisible = true;
      this.ifFullScreen = false;
      this.names = "";
      this.poster = "";
      this.activeTabs = "";
      this.activeClasse = "";
      this.UnitValues = [];
      if (item == "人员概况") {
        this.activeDialog = item;
        this.unitOptionsRYGKs = this.rygkUnitValue;
        this.poster = items;
        if (items == "政治面貌") {
          this.names = itemse;
        }
        if (items == "性别") {
          this.names = itemse;
        }
      } else if (item == "单位概况") {
        this.activeDialog = item;
      } else if (item == "荣誉列表") {
        this.activeDialog = "单位概况";
        this.activeTabs = item;
        this.activeClasse = items;
        this.UnitValues = itemse;
      } else if (item == "出国境概况") {
        this.activeDialog = item;
        let newUnits = [];
        if (this.cgjUnitValue) {
          for (var i = 0; i < this.cgjUnitValue.length; i++) {
            newUnits.push(parseInt(this.cgjUnitValue[i].name));
          }
        }
        this.departIds1 = newUnits;
      } else if (item == "请假概况") {
        this.activeDialog = item;
        let newUnits = [];
        if (this.qjglUnitValue) {
          for (var i = 0; i < this.qjglUnitValue.length; i++) {
            newUnits.push(parseInt(this.qjglUnitValue[i].name));
          }
        }
        this.departIds2 = newUnits;
      }
    },
    //职级下拉选择
    handleNodeCheckRYGKxlzj() {
      if (this.UnitValuezj == "综合管理序列") {
        this.UnitValuezjb = false;
        this.UnitValuezjs = "综合管理";
      } else if (this.UnitValuezj == "执法勤务序列") {
        this.UnitValuezjb = false;
        this.UnitValuezjs = "执法勤务";
      } else if (this.UnitValuezj == "警务技术序列") {
        this.UnitValuezjb = false;
        this.UnitValuezjs = "警务技术";
      } else {
        this.UnitValuezjb = true;
      }
      this.handleNodeCheckRYGKzj(this.rygkUnitValue);
    },
    handleNodeCheckRYGKzj(rygkUnitValue) {
      let self = this;
      let UnitValuezje = "";
      if (this.UnitValuezj == "全部") {
        UnitValuezje = this.UnitValuezj;
      } else {
        UnitValuezje = this.UnitValuezjs;
      }
      $fetchUsers(
        "post",
        {
          remoteUrl: "mangagerData/getDutyRank"
        },
        {
          departIds: rygkUnitValue,
          type: UnitValuezje
        }
      ).then(
        res => {
          let fontsize = this.skin == "white" ? "black" : "white";
          if (res.data == null || JSON.stringify(res.data) == "{}") {
            this.showdatas = true;
          } else {
            this.showdatas = false;
            setTimeout(() => {
              if (this.UnitValuezj == "全部") {
                var myChartallse = this.$echarts.init(
                  document.getElementById("zjallcharts")
                );
                let seriesData = res.data;
                let optionallss = [];
                $.each(seriesData, function(key, value) {
                  let columnss = "";
                  if (key == "科级") {
                    columnss = "#1581ec";
                  } else if (key == "科员") {
                    columnss = "#dbc821";
                  } else if (key == "厅级") {
                    columnss = "#f2637b";
                  } else if (key == "处级") {
                    columnss = "#29ab5d";
                  }
                  optionallss.push({
                    name: key,
                    value: seriesData[key].count,
                    itemStyle: { color: columnss }
                  });
                });
                var optionall = {
                  tooltip: {
                    trigger: "item",
                    formatter: "{b} : {c} ({d}%)"
                  },
                  series: [
                    {
                      name: "姓名",
                      type: "pie",
                      radius: "80%",
                      center: ["50%", "50%"],
                      label: {
                        normal: {
                          color: fontsize
                        }
                      },
                      data: optionallss
                    }
                  ]
                };
                myChartallse.setOption(optionall, true);
                myChartallse.on("click", function(params) {
                  $.each(seriesData, function(keye, valuee) {
                    if (params.name == keye) {
                      self.seriesDataclick = seriesData[keye];
                    }
                  });
                  self.alldetail(params, self.seriesDataclick);
                });
              } else {
                var myCharother = this.$echarts.init(
                  document.getElementById("zjother")
                );
                var optionse = {
                  tooltip: {
                    trigger: "item",
                    formatter: "{b}: {c} ({d}%)"
                  },
                  series: [
                    {
                      type: "pie",
                      selectedMode: "single",
                      radius: [0, "40%"],
                      label: {
                        normal: {
                          position: "inner"
                        }
                      },
                      labelLine: {
                        normal: {
                          show: false
                        }
                      },
                      data: [],
                      center: ["40%", "56%"]
                    },
                    {
                      type: "pie",
                      radius: ["60%", "80%"],
                      label: {
                        normal: {
                          color: fontsize
                        }
                      },
                      data: [],
                      center: ["40%", "56%"]
                    }
                  ]
                };
                let seriesData = res.data;
                $.each(seriesData, function(key, value) {
                  let columnss = "";
                  if (key == "科级") {
                    columnss = "#1581ec";
                  } else if (key == "科员") {
                    columnss = "#dbc821";
                  } else if (key == "厅级") {
                    columnss = "#f2637b";
                  } else if (key == "处级") {
                    columnss = "#29ab5d";
                  }
                  optionse.series[0].data.push({
                    name: key,
                    value: seriesData[key].count,
                    itemStyle: { color: columnss }
                  });
                });
                $.each(seriesData, function(key, value) {
                  let columnss = [
                    "#f1624f",
                    "#ff004e",
                    "#11acb8",
                    "#0ebf7f",
                    "#78d462",
                    "#5aad23",
                    "#6cb5f9",
                    "#3590ea",
                    "#3daef6",
                    "#35c8ff",
                    "#e8f250",
                    "#e2be15"
                  ];
                  let kess = 0;
                  if (key == "科级") {
                    kess = 6;
                  } else if (key == "科员") {
                    kess = 10;
                  } else if (key == "厅级") {
                    kess = 0;
                  } else if (key == "处级") {
                    kess = 2;
                  }
                  $.each(seriesData[key], function(keys, values) {
                    if (keys != "count") {
                      let colors = columnss[kess];
                      optionse.series[1].data.push({
                        name: keys,
                        value: seriesData[key][keys],
                        itemStyle: { color: colors }
                      });
                      kess++;
                    }
                  });
                });
                myCharother.setOption(optionse);
              }
            }, 100);
          }
        },
        err => {
          this.$message.error("获取单位列表数据失败！");
        }
      );
    },
    alldetail(params, seriesDataclick) {
      this.seriesclick = true;
      setTimeout(() => {
        let barWidths = "60%";
        if (params.name == "科员") {
          barWidths = "30%";
        } else {
          barWidths = "60%";
        }
        var myChartalldetail = this.$echarts.init(
          document.getElementById("zjalldetail")
        );
        var optionss = {
          color: ["#3398DB"],
          tooltip: {
            trigger: "axis",
            axisPointer: {
              type: "shadow"
            }
          },
          grid: {
            left: "3%",
            right: "4%",
            bottom: "3%",
            containLabel: true
          },
          xAxis: [
            {
              type: "category",
              axisTick: {
                alignWithLabel: true
              },
              axisLabel: {
                rotate: 45
              },
              data: []
            }
          ],
          yAxis: [
            {
              type: "value"
            }
          ],
          series: [
            {
              type: "bar",
              barWidth: barWidths,
              data: []
            }
          ]
        };
        $.each(seriesDataclick, function(key, value) {
          if (key != "count") {
            optionss.xAxis[0].data.push(key);
            optionss.series[0].data.push(seriesDataclick[key]);
          }
        });
        myChartalldetail.setOption(optionss);
      }, 100);
    },
    handleClick(tab, event) {
      if (tab.name == "nine") {
        if (this.falgses == false) {
          this.falgses = true;
          if (this.rygkUnitValue && this.rygkUnitValue.length > 0) {
            this.handleNodeCheckRYGKzj(this.rygkUnitValue);
          } else {
            let self = this;
            $fetchUsers(
              "post",
              {
                remoteUrl: "orgmanage/unittreeAuto"
              },
              {}
            ).then(
              res => {
                this.unitOptionsRYGK = res.data;
                self.$refs.RTYGKtree.setCheckedKeys([
                  self.unitOptionsRYGK[0].unitId
                ]);
                setTimeout(() => {
                  let checkedKeys = self.$refs.RTYGKtree.getCheckedKeys();
                  self.rygkUnitValue = checkedKeys;
                  this.handleNodeCheckRYGKzj(self.rygkUnitValue);
                }, 50);
              },
              err => {
                this.$message.error("获取单位列表数据失败！");
              }
            );
          }
        } else {
          this.handleNodeCheckRYGKzj(this.rygkUnitValue);
        }
      }
    }
  },
  mounted() {
    if (this.$route.params.token) {
      localStorage.setItem("setToken", this.$route.params.token);
      this.showBackMenu = false;
    }
    this.loadRangeItems();
    this.loadUnitOptionsData();
    this.awardPaiming("", "fenju_jiti", "getCollectiveHonor");
    this.personalAwardPaiming("right");
  },
  watch: {
    skin() {
      this.handleNodeCheckRYGKxlzj();
    }
  }
};
</script>
<style>
.customManageBoxs .el-select {
  width: 60px;
  position: absolute;
  left: 0px;
  opacity: 0;
}
.customManageBoxe .el-select {
  width: 60px;
  position: absolute;
  left: 80px;
  opacity: 0;
}
.customManageBoxes .el-select {
  width: 60px;
  position: absolute;
  left: 110px;
  top: 0;
  opacity: 0;
}
.customManageBoxess .el-select {
  width: 60px;
  position: absolute;
  left: 90px;
  top: 0;
  opacity: 0;
}
.manageBox {
  width: 100%;
  min-width: 1460px;
  background: url("../../../static/img/manage/bg.png") no-repeat center;
  background-size: cover;
  position: relative;
}

.manageBox .manageHead {
  height: 80px;
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.manageBox .leftBg {
  height: 50px;
  background: url("../../../static/img/manage/title_bg_left.png") no-repeat
    center;
  background-size: 100% 100%;
  width: calc(50% - 230px);
}

._WHITE .leftBg {
  background: none;
}

.manageBox .headInfo {
  font-family: "PingFangSC-Medium";
  font-size: 34px;
  color: #ffffff;
  letter-spacing: 0;
  text-align: center;
  height: 50px;
  line-height: 50px;
  /**text-shadow: 0 0 6px 0 #1882FF;*/
  background-image: -webkit-linear-gradient(bottom, #1890ff, #c3f1ff, #fff);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  font-weight: 900;
}

._WHITE .manageHead {
  background: url("../../../static/img/manage/title_headInfo.png") no-repeat
    center;
}

._WHITE .headInfo {
  color: #1890ff;
  -webkit-text-fill-color: inherit;
  background: none;
}

.manageBox .rightBg {
  height: 50px;
  background: url("../../../static/img/manage/title_bg_right.png") no-repeat
    center;
  background-size: 100% 100%;
  width: calc(50% - 230px);
}

._WHITE .rightBg {
  background: none;
}

.manageBox .linkManage {
  position: absolute;
  color: #fff;
  right: 40px;
}

.manageBox .linkManage > a {
  font-family: "PingFangSC-Regular";
  font-size: 14px;
  color: #fff;
}

.manageBox .manageMain {
  width: 100%;
  height: calc(100% - 81px);
  padding: 2px 20px 20px 20px;
  box-sizing: border-box;
}

._WHITE .manageMain > .el-row {
  background-color: rgba(245, 245, 245, 0.8);
  border-radius: 4px;
}

.manageBox .manageMain .el-row {
  height: 100%;
}

.manageBox .threeBox {
  background: none;
}

/* 排行列表 */
.manageBox .panel {
  width: calc(50% - 1px);
  float: left;
  height: 100%;
  position: relative;
}
.manageBox .p-header {
  padding: 0 16%;
  height: 100%;
  height: 190px;
}
.manageBox .p-header .partTit {
  position: absolute;
  top: 5px;
  line-height: 20px;
  font-size: 16px !important;
  cursor: pointer;
}
.manageBox .treeSelect {
  position: absolute;
  z-index: 20;
  right: 0px;
  top: 20px;
  padding-top: 4px;
  width: 200px;
  height: 280px;
  background-color: rgba(16, 85, 184, 0.9);
  color: #fff;
  overflow: auto;
}
.manageBox .treeSelect ::-webkit-scrollbar {
  width: 6px;
  height: 6px;
  background-color: transparent;
}

/*定义滚动条轨道 内阴影+圆角*/
.manageBox .treeSelect::-webkit-scrollbar-track {
  -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.3);
  border-radius: 10px;
  background-color: transparent;
}

/*定义滑块 内阴影+圆角*/
.manageBox .treeSelect::-webkit-scrollbar-thumb {
  border-radius: 10px;
  -webkit-box-shadow: inset 0 0 6px rgba(0, 0, 0, 0.3);
  background-color: transparent;
}
.p-list ul::-webkit-scrollbar-track {
  background-color: transparent !important;
}

.manageBox .p-text {
  position: relative;
  max-width: 220px;
  margin: 0 auto;
  top: 25px;
  line-height: 18px;
  color: #fff;
  font-size: 13px;
}
._WHITE .p-text {
  color: #262626;
  background-image: none;
  -webkit-text-fill-color: #262626;
}
.manageBox .p-text p {
  position: relative;
  white-space: nowrap;
  width: 92px;
  overflow: hidden;
  text-overflow: ellipsis;
}
.manageBox .p-jiangtai {
  width: 100%;
  max-width: 220px;
  margin: 0 auto;
  top: 80px;
  height: 89px;
  position: relative;
  background: url("../../../static/img/manage/jiangtai1.png") no-repeat center;
  background-size: 100% auto;
}
.manageBox .p-body {
  position: relative;
  height: calc(100% - 190px);
}
.manageBox .p-list {
  overflow: auto;
  height: 100%;
  width: 92%;
  left: 4%;
  position: relative;
  background: url("../../../static/img/manage/content_bg6.png") no-repeat center;
  background-size: 100% 100%;
}
._WHITE .p-list {
  color: #262626;
  background-image: none;
  -webkit-text-fill-color: #262626;
  position: relative;
  background-size: 100% 100%;
}
.manageBox .p-list ul {
  /* padding-top: 15px; */
}
.manageBox .p-list ul li {
  width: 100%;
  list-style: none;
}
.manageBox .p-list ul {
  position: relative;
  top: 15px;
  height: 560px;
  line-height: 39px;
}
.manageBox .p-list ul li {
  height: 39px;
}
.manageBox .p-list-num,
.p-list-name,
.p-list-progress {
  text-align: center;
  float: left;
  font-size: 14px;
  color: #fff;
}
.manageBox .p-list-num {
  width: 30px;
}
.manageBox .p-list-name {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  text-align: left;
  width: 92px;
}
.manageBox .p-list-progress {
  width: calc(100% - 180px);
  position: relative;
  left: 10px;
  top: 13px;
}
.manageBox .el-progress-bar__outer {
  border-radius: 0;
}

/**大于1600分辨率 */

@media screen and (min-width: 1600px) {
  .manageBox .dwgkBox,
  .manageBox .threeBox {
    min-height: 840px;
    height: 56%;
    margin: 10px;
    position: relative;
    background: url("../../../static/img/manage/content_bg1.png") no-repeat
      center;
    background-size: 100% 100%;
  }

  ._WHITE .dwgkBox {
    background: #fff;
    border-radius: 4px;
  }

  .manageBox .rygkBox {
    min-height: 420px;
    box-sizing: border-box;
    /* background: url('../../../static/img/manage/content_bg4.png') no-repeat center; */
    background-size: 100% 100%;
  }

  ._WHITE .rygkBox {
    background: #fff;
    border-radius: 4px;
  }

  .manageBox .zzmmBox {
    min-height: 209.5px;
    height: 16%;
    position: relative;
    /* margin: 20px 10px 0 10px; */
    /* background: url('../../../static/img/manage/content_bg6.png') no-repeat center; */
    background-size: 100% 100%;
  }
  .manageBox .xlxwBox {
    min-height: 419px;
    height: 32%;
    position: relative;
    /* margin: 20px 10px 0 10px; */
    /* background: url('../../../static/img/manage/content_bg6.png') no-repeat center; */
    background-size: 100% 100%;
  }

  ._WHITE .zzmmBox,
  ._WHITE .xlxwBox {
    background: #fff;
    border-radius: 4px;
  }

  .manageBox .threeBox {
    background: url("../../../static/img/manage/content_bg1.png") no-repeat
      center;
    background-size: 100% 100%;
  }

  .manageBox .cgjBox,
  .manageBox .qjglBox {
    min-height: 420px;
    margin: 10px;
    position: relative;
    background: url("../../../static/img/manage/content_bg2.png") no-repeat
      center;
    background-size: 100% 100%;
  }

  ._WHITE .cgjBox,
  ._WHITE .qjglBox {
    background: #fff;
    border-radius: 4px;
  }
}

/**小于1600分辨率 */

@media screen and (max-width: 1600px) {
  .manageBox .dwgkBox,
  .manageBox .threeBox {
    min-height: 745px;
    height: 56%;
    margin: 10px;
    position: relative;
    background: url("../../../static/img/manage/content_bg1.png") no-repeat
      center;
    background-size: 100% 100%;
  }

  ._WHITE .dwgkBox {
    background: #fff;
    border-radius: 4px;
  }

  .manageBox .rygkBox {
    min-height: 350px;
    box-sizing: border-box;
    /* background: url('../../../static/img/manage/content_bg4.png') no-repeat center; */
    background-size: 100% 100%;
  }

  ._WHITE .rygkBox {
    background: #fff;
    border-radius: 4px;
  }

  .manageBox .zzmmBox {
    min-height: 197px;
    height: 16%;
    position: relative;
    /* margin: 20px 10px 0 10px; */
    /* background: url('../../../static/img/manage/content_bg6.png') no-repeat center; */
    background-size: 100% 100%;
  }
  .manageBox .xlxwBox {
    min-height: 394px;
    height: 32%;
    position: relative;
    /* margin: 20px 10px 0 10px; */
    /* background: url('../../../static/img/manage/content_bg6.png') no-repeat center; */
    background-size: 100% 100%;
  }

  ._WHITE .zzmmBox {
    background: #fff;
    border-radius: 4px;
    min-height: 197px;
  }
  ._WHITE .xlxwBox {
    background: #fff;
    border-radius: 4px;
    min-height: 394px;
  }

  .manageBox .threeBox {
    background: none;
    background: url("../../../static/img/manage/content_bg1.png") no-repeat
      center;
    background-size: 100% 100%;
  }

  ._WHITE .threeBox {
    background: none;
    background-size: 100% 100%;
  }

  .manageBox .cgjBox,
  .manageBox .qjglBox {
    min-height: 330px;
    margin: 10px;
    position: relative;
    background: url("../../../static/img/manage/content_bg2.png") no-repeat
      center;
    background-size: 100% 100%;
  }

  ._WHITE .cgjBox,
  ._WHITE .qjglBox {
    background: #fff;
    border-radius: 4px;
  }
}

.manageBox .zzmmBox {
  margin-left: 0;
}

.manageBox .xlxwBox {
  margin-right: 0;
}

.manageBox .partHead {
  height: 59px;
  width: 100%;
  line-height: 59px;
  padding: 0 20px;
  box-sizing: border-box;
  border-bottom: 1px solid rgba(232, 232, 232, 0.2);
  background-color: #2f42b4;
}

.manageBox .partHeads {
  height: 45px;
  width: 100%;
  line-height: 45px;
  padding: 0 20px;
  box-sizing: border-box;
  border-bottom: 1px solid rgba(232, 232, 232, 0.2);
  background-color: #4f65e7;
}

._WHITE .partHead {
  height: 59px;
  width: 100%;
  line-height: 59px;
  padding: 0 20px;
  box-sizing: border-box;
  border-bottom: 1px solid rgba(232, 232, 232, 0.2);
  background-color: #ddeeff;
}

._WHITE .partHeads {
  height: 45px;
  width: 100%;
  line-height: 45px;
  padding: 0 20px;
  box-sizing: border-box;
  border-bottom: 1px solid rgba(232, 232, 232, 0.2);
  background-color: #ddeeff;
}

.manageBox .partSelect {
  float: right;
  width: 60%;
  font-family: "PingFangSC-Regular";
  font-size: 12px;
  color: #fff;
  letter-spacing: 0;
  text-align: right;
}

._WHITE .partSelect {
  color: rgba(0, 0, 0, 0.65);
}

/* .manageBox .partSelect .el-select {
  width: 35% !important;
} */

.manageBox .partSelect .el-select {
  width: 17% !important;
}
.manageBox .partSelects .el-select {
  width: 70% !important;
}
/**文字渐变 */

.manageBox .partTit {
  font-family: "PingFangSC-Medium";
  font-size: 18px;
  color: #262626;
  /* background-image: -webkit-linear-gradient(bottom, #1890ff, #c3f1ff, #fff); */
  background-image: -webkit-linear-gradient(bottom, #1890ff, #87c6d9, #c3f1ff);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  cursor: pointer;
}

._WHITE .partTit {
  color: #262626;
  background-image: none;
  -webkit-text-fill-color: #262626;
}

.manageBox .partTab {
  float: right;
}

.manageBox .partCon,
.manageBox .qjglCon,
.manageBox .cgjCon {
  width: 100%;
  height: calc(100% - 60px);
  left: 0;
  bottom: 0;
  position: absolute;
}

.manageBox .cgjCon {
  display: flex;
}

.manageBox .zzmmCon {
  width: 100%;
  height: calc(100% - 60px);
  position: absolute;
  padding-top: 16px;
  box-sizing: border-box;
  bottom: 0;
  left: 0;
}

.manageBox .zzmmInfo {
  text-align: center;
  margin-bottom: 20px;
}

.manageBox .zzmmInfo > i {
  color: #fff;
  font-size: 36px;
}

._WHITE .zzmmInfo > i {
  color: rgba(0, 0, 0, 0.65);
}

.manageBox .zzmmInfo p,
.manageBox .zzmmInfo span {
  font-family: "PingFangSC-Regular";
  font-size: 12px;
  text-align: center;
}

.manageBox .zzmmInfo p,
.manageBox .zzmmInfo span.unit {
  color: #fff;
}

._WHITE .zzmmInfo p,
._WHITE .zzmmInfo span.unit {
  color: rgba(0, 0, 0, 0.65);
}

.manageBox .zzmmInfo span {
  color: #51e1fafc;
}

.manageBox .zzmmdy {
  display: inline-block;
  width: 45px;
  height: 45px;
  background: url("../../../static/img/manage/dy.png") no-repeat center;
  background-size: 100% 100%;
}

.manageBox .zzmmybdy {
  display: inline-block;
  width: 45px;
  height: 45px;
  background: url("../../../static/img/manage/ybdy.png") no-repeat center;
  background-size: 100% 100%;
}

.manageBox .zzmmty {
  display: inline-block;
  width: 45px;
  height: 45px;
  background: url("../../../static/img/manage/ty.png") no-repeat center;
  background-size: 100% 100%;
}

.manageBox .zzmmqz {
  display: inline-block;
  width: 45px;
  height: 45px;
  background: url("../../../static/img/manage/qz.png") no-repeat center;
  background-size: 100% 100%;
}

.manageBox .zzmmqnan {
  display: inline-block;
  width: 45px;
  height: 45px;
  background: url("../../../static/img/manage/nan_police.png") no-repeat center;
  background-size: 100% 100%;
}

.manageBox .zzmmqnv {
  display: inline-block;
  width: 45px;
  height: 45px;
  background: url("../../../static/img/manage/nv_police.png") no-repeat center;
  background-size: 100% 100%;
}

.manageBox .zzmmqtdp {
  display: inline-block;
  width: 45px;
  height: 45px;
  background: url("../../../static/img/manage/qtdp.png") no-repeat center;
  background-size: 100% 100%;
}

.manageBox .toggleSkin {
  position: absolute;
  color: #fff;
  left: 40px;
  cursor: pointer;
  font-size: 13px;
  user-select: none;
}

.manageBox .toggleSkin .skin-arrow {
  display: inline-block;
  width: 20px;
  height: 20px;
  background: url(../../../static/img/manage/arrow-right.png) no-repeat center;
  background-size: 100% 100%;
  vertical-align: middle;
}

/**头部tab切换样式 */
.partTab .el-tabs__item {
  /* font-family: "PingFangSC-Regular" !important; */
  font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB",
    "Microsoft YaHei", Arial, sans-serif !important;
  font-size: 13px !important;
  color: #fff !important;
}

._WHITE .partTab .el-tabs__item {
  color: rgba(0, 0, 0, 0.65) !important;
}

.partTab .el-tabs__item.is-active {
  /* font-family: "PingFangSC-Regular" !important; */
  font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB",
    "Microsoft YaHei", Arial, sans-serif !important;
  font-size: 13px !important;
  color: #18c3ff !important;
  text-shadow: 0 0 4px rgba(0, 78, 255, 0.5) !important;
}

._WHITE .partTab .el-tabs__item.is-active {
  text-shadow: none !important;
}

.partTab .el-tabs__active-bar {
  background-color: transparent;
}

.partTab .el-tabs__nav-wrap::after {
  background-color: transparent;
}

.partTab .el-tabs__header {
  margin: 0 !important;
}

.partTab .btn-tab-ry {
  color: #ffffff;
  margin-right: 10px;
  cursor: pointer;
  font-size: 13px;
}

.partTab .btn-tab-ry.active {
  color: #18c3ff;
  margin-right: 10px;
}

._WHITE .btn-tab-ry {
  color: #262626;
  background-image: none;
  -webkit-text-fill-color: #262626;
}

._WHITE .partTab .btn-tab-ry.active {
  color: #18c3ff;
  margin-right: 10px;
  -webkit-text-fill-color: #18c3ff;
}

.partTab .el-tabs__content {
  width: 100% !important;
  height: calc(100% - 60px) !important;
  position: absolute;
  left: 0;
  top: 60px;
}

.partTab .el-tabs__content > div {
  width: 100%;
  height: 100%;
}

.partSelect .el-select .el-input__inner {
  background: rgba(0, 0, 0, 0.1) !important;
  color: #fff !important;
}

._WHITE .partSelect .el-select .el-input__inner {
  color: rgba(0, 0, 0, 0.65) !important;
}

.partTab .el-carousel__arrow {
  background: transparent;
  color: #fff;
  border: 1px solid #fff;
  font-weight: 900;
  width: 22px;
  height: 22px;
}

._WHITE .partTab .el-carousel__arrow {
  color: #58c1fd;
  border: 1px solid #58c1fd;
}

.partTab .el-carousel__arrow {
  background: none;
}

.v-charts-data-empty,
.v-charts-data-error {
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-pack: center;
  -ms-flex-pack: center;
  justify-content: center;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
  color: #fff;
  font-size: 14px;
  background: none;
}

._WHITE .v-charts-data-empty,
._WHITE .v-charts-data-error {
  color: rgba(0, 0, 0, 0.65);
}

.el-loading-mask,
.v-charts-component-loading {
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  display: -webkit-box;
  display: -ms-flexbox;
  display: flex;
  -webkit-box-pack: center;
  -ms-flex-pack: center;
  justify-content: center;
  -webkit-box-align: center;
  -ms-flex-align: center;
  align-items: center;
  background: none;
}

.v-charts-mask-status {
  filter: blur(1px);
}

.el-loading-mask .circular,
.v-charts-component-loading .circular {
  width: 42px;
  height: 42px;
  -webkit-animation: loading-rotate 2s linear infinite;
  animation: loading-rotate 2s linear infinite;
}

.el-loading-mask .path,
.v-charts-component-loading .path {
  -webkit-animation: loading-dash 1.5s ease-in-out infinite;
  animation: loading-dash 1.5s ease-in-out infinite;
  stroke-dasharray: 90, 150;
  stroke-dashoffset: 0;
  stroke-width: 2;
  stroke: #c1e3fd;
  stroke-linecap: round;
}

@keyframes loading-rotate {
  100% {
    transform: rotate(360deg);
  }
}

@keyframes loading-dash {
  0% {
    stroke-dasharray: 1, 200;
    stroke-dashoffset: 0;
  }

  50% {
    stroke-dasharray: 90, 150;
    stroke-dashoffset: -40px;
  }

  100% {
    stroke-dasharray: 90, 150;
    stroke-dashoffset: -120px;
  }
}

.partTab .el-tabs__header .el-dropdown {
  margin: 0 20px;
  height: 40px;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
  line-height: 40px;
  display: inline-block;
  list-style: none;
  font-size: 14px;
  font-weight: 500;
  position: relative;
  font-family: "PingFangSC-Regular";
  color: #fff;
}

._WHITE .partTab .el-tabs__header .el-dropdown {
  color: rgba(0, 0, 0, 0.65);
}

.partTab .el-tabs__header .el-dropdown:first-child {
  margin-left: 0;
}

.partTab .el-tabs__header .el-dropdown:last-child {
  margin-right: 0;
}

.partTab .el-tabs__header .el-dropdown.is-active {
  color: #18c3ff;
  text-shadow: 0 0 4px rgba(0, 78, 255, 0.5);
}

._WHITE .partTab .el-tabs__header .el-dropdown.is-active {
  text-shadow: none;
}

.partTab .el-tabs__header .el-dropdown .el-dropdown-link {
  cursor: pointer;
}

.part-toggle {
  background: rgba(255, 255, 255, 0.3) !important;
  margin-top: 8px !important;
  border: none !important;
  border-top: 1px solid #1890ff !important;
  border-radius: 0 0 4px 4px !important;
}

._WHITE.part-toggle {
  background: rgba(255, 255, 255, 1) !important;
}

.part-toggle > .el-dropdown-menu__item {
  line-height: 26px !important;
  padding: 0 20px !important;
  cursor: pointer !important;
  color: #fff;
}

._WHITE.part-toggle > .el-dropdown-menu__item {
  color: rgba(0, 0, 0, 0.65);
}

.part-toggle > .el-dropdown-menu__item.active {
  color: #1890ff;
}

.part-toggle > .el-dropdown-menu__item:focus,
.part-toggle > .el-dropdown-menu__item:not(.is-disabled):hover {
  background: none !important;
}

.part-toggle > .popper__arrow {
  display: none !important;
}

.partHead .cgjSelect .el-select.unit {
  width: 50% !important;
}
.partHead .qxjSelect .el-select.unit {
  width: 50% !important;
}
.partHead .cgjSelect .el-select.year {
  width: 25% !important;
}
.partHead .qxjSelect .el-select.year {
  width: 25% !important;
}

.el-icon-full-screen {
  position: absolute;
  top: 22px;
  right: 43px;
  cursor: pointer;
}
.fontfamily {
  font-family: "Helvetica Neue", Helvetica, "PingFang SC", "Hiragino Sans GB",
    "Microsoft YaHei", Arial, sans-serif !important;
}
.el-tabs__item {
  padding: 0 10px !important;
}
.nine .el-input--small .el-input__inner {
  height: 24px;
  line-height: 24px;
}
.nine {
  height: 32px;
  width: 100%;
}
.mycharts {
  width: 100%;
  height: calc(100% - 32px) !important;
}
.mychartse {
  width: 100%;
  height: 95%;
  line-height: 100%;
}
.nine .el-select .el-select--small {
  line-height: 32px !important;
}
.manageBox .p-list-num,
.p-list-name,
.p-list-progress {
  font-size: 13px;
}
</style>
