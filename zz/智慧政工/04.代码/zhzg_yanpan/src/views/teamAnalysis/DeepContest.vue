<template>
  <div class="mianportrait"  style="min-height:960px;">
    <div class="mianportrait">
      <el-row>
        <el-col :span="24" style="padding: 15px 20px;">
          <el-col :span="12">
            <div class="mianportrait contrastbox" style="text-align: -webkit-left;padding-left: 3%;">
              <el-select style="width: 25%;"  v-model="valuedata" @change="slecktclick" placeholder="请选择" >
                <el-option
                  v-for="item in optionsdata"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
              <el-input style="width: 50%;" placeholder="请输入内容" v-if="valuedata == '单位名称'" @keyup.enter.native="slecktssearch('')" v-model="inputsearchse" class="input-with-select"  >
                <el-button slot="append" icon="el-icon-search" @click="slecktssearch('')" ></el-button>
              </el-input>
              <el-select style="width: 25%;" v-if="valuedata == '政工考核单元'" v-model="valuedatazgn" @change="slecktclickzgn" placeholder="请选择" >
                <el-option v-for="item in optionsdatazgn" :key="item.value" :label="item.label" :value="item.value" > </el-option>
              </el-select>
              <el-select style="width: 25%;" v-if="valuedata == '政工考核单元'&&valuedatazgn" v-model="valuedatazgs" @change="slecktclickzgs" placeholder="请选择" >
                <el-option
                  v-for="item in optionsdatazgs"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value" >
                </el-option>
              </el-select>
              <el-select style="width: 25%;" v-if="valuedata == '政工考核单元'&&valuedatazgs&&optionsdatazgd.length>0" v-model="valuedatazgd" @change="slecktclickzgd" placeholder="请选择" >
                <el-option
                  v-for="item in optionsdatazgd"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value" >
                </el-option>
              </el-select>
            </div>
          </el-col>
          <el-col :span="12">
            <div class="mianportrait contrastbox" style="clear:both;">
              <div v-if="sexs == '1'" style="float: left;width: 150px;line-height: 40px;background: #576ea9;color: #fff;text-align: center;"  @click="canelisclick">
                <span style="font-size: 15px;font-weight: 700;" v-if="depatename.length>5" :title="depatename">{{ depatename.slice(0,5) }}...</span><span style="font-size: 15px;font-weight: 700;" v-else>{{ depatename}}</span><img src="../../assets/image/policeporlit/返回.png" style="width: 18px;margin-left: 40px;"/>
              </div>
              <div style="float:right;">
                <img src="../../assets/image/policeporlit/组 14.png" style="margin-right: 10px;"/>
                <el-select v-model="valuelise" @change="init" placeholder="请选择">
                  <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                    >
                  </el-option>
                </el-select>
              </div>
            </div>
          </el-col>
        </el-col>
        <el-col :span="24" style="padding: 15px 20px;"  v-loading.fullscreen.lock="loading"  element-loading-background="rgba(255, 255, 255, 0.8)">
          <div class="mianportrait">
            <div :class="'carksboxcanel' + (items.isWhite== '0' ? ' _WHITE' : '')" v-for="items in contestlistcs">
            <div class="carksbox">
             <div class="mianportrait"  @click="tcryisclick(items)">
              <el-col :span="9" style="height: 185px;">
                <div class="mianportrait">
                  <div class="loubgs" v-if="sexs == '2'">
                    <img src="../../assets/image/policeporlit/组 9.png" style="width: 20px;"/>
                  </div>
                  <div class="loubgse" v-if="sexs == '1'">
                    <img src="../../assets/image/policeporlit/组 9-2.png" style="width: 20px;"/>
                  </div>
                  <div style="float: left;height: 60px;line-height: 60px;font-size: 16px;" v-if="items.deptName.length>10" :title="items.deptName">{{items.deptName.slice(0,10)}}...</div>
                  <div style="float: left;height: 60px;line-height: 60px;font-size: 16px;" v-else >{{items.deptName}}</div>
                </div>
                <div class="mianportrait rbitbox" style="padding: 10px 20px;text-align: center;height:107px;overflow-x: hidden;" v-if="items.headInfoList.length>2">
                  <div style="float: left;width:45%;margin-right: 5%;" v-for="itemesed in items.headInfoList">
                    <img :src="itemesed.img" style="width: 50px;"/>
                     <!-- <img src="../../assets/image/portasts/lou2.png" style="width: 50px;"/> -->
                    <div style="width:100%;"><div>{{itemesed.duty}}</div><div>{{itemesed.name}}</div></div>
                  </div>
                </div>
                <div class="mianportrait" style="padding: 10px 20px;text-align: center;height:110px;" v-if="items.headInfoList.length<3">
                  <div style="float: left;width:45%;margin-right: 5%;" v-for="itemesed in items.headInfoList">
                    <img :src="itemesed.img" style="width: 50px;"/>
                    <div style="width:100%;"><div>{{itemesed.duty}}</div><div>{{itemesed.name}}</div></div>
                  </div>
                </div>
              </el-col>
              <el-col :span="15">
                <div class="mianportrait">
                  <el-col :span="12">
                    <div class="mianportrait" style="margin: 30px 0 0 10px;">
                      <div style="float: left;margin-right: 5px;line-height: 40px;">
                        <img v-if="sexs=='2'" src="../../assets/image/policeporlit/组 10.png" style="width: 35px;"/>
                        <img v-if="sexs=='1'" src="../../assets/image/policeporlit/组 10-2.png" style="width: 35px;"/>
                      </div>
                      <div style="float: left;font-size: 12px;">
                        <div><span class="ageclor">{{items.aveAge}}</span>岁</div>
                        <div>平均年龄</div>
                      </div>
                    </div>
                    <div class="mianportrait" style="min-height:100px;padding-top: 15px;padding-left: 10px;">
                      <div style="float: left;margin-right: 10px;height:100px;line-height:100px;">
                        <img v-if="sexs=='2'" src="../../assets/image/policeporlit/组 11.png" style="width: 35px;"/>
                        <img v-if="sexs=='1'" src="../../assets/image/policeporlit/组 11-2.png" style="width: 35px;"/>
                      </div>
                      <div style="float: left;font-size: 12px;">
                        <div style="width:100%">
                          <span class="ageclorse">{{items.inThePost}}</span>人
                        </div>
                        <div style="width:100%">正处职（含）以上</div>
                        <div style="width:100%">
                          <span class="ageclorse">{{items.professionalDuties}}</span>人
                        </div>
                        <div style="width:100%">正科职</div>
                      </div>
                    </div>
                  </el-col>
                  <el-col :span="12">
                    <div class="mianportrait" style="margin:10px 0px 0px 40%;min-height:50px;">
                      <div style="float: left;font-size: 12px;margin-right: 5px;"> <img v-if="sexs == '2'" src="../../assets/image/policeporlit/人.png" style="width: 35px;"/><img v-if="sexs == '1'" src="../../assets/image/policeporlit/人-2.png" style="width: 35px;"/></div>
                      <div class="personcolor" style="float: left;font-size: 12px;">
                        <div style="font-size: 18px;">{{items.personSum}}</div>
                        <div>总人数</div>
                      </div>
                    </div>
                    <div class="mianportrait" style="min-height:100px;padding-top: 25px;">
                      <div style="float: left;font-size: 12px;">
                        <div>
                          <div><span class="ageclorse">{{items.deputyDuties}}</span>人</div>
                          <div>副处职</div>
                          <div><span class="ageclorse">{{items.subsidiaryDuties}}</span>人</div>
                          <div>副科职</div>
                        </div>
                      </div>
                    </div>
                  </el-col>
                </div>
              </el-col>
              </div>
              <el-col :span="24" v-if="items.isSub == '1'" style="text-align: right;padding-right: 6px;"><img @click="subisclick(items)" src="../../assets/image/policeporlit/组 12.png" style="width: 35px;"/></el-col>
            </div>
            </div>
          </div>
          <div class="mianportrait" style="color:#0f1cc2f2;cursor: pointer;background: rgba(211, 210, 215, 0.84);" @click="loaddatas"><span v-if="falges == '1'">加载更多</span><span v-if="falges == '2'">没有更多了</span></div>
        </el-col>
      </el-row>
    </div>
    <el-dialog :visible.sync="seriesclick" min-height="1600px" width="1300px">
      <div class="tcbgs" style="min-height:1600px;width:100%;text-align: -webkit-center">
        <header>
          <div class="mianportrait" style="color:#FFF;font-size: 22px;font-weight: 700;padding-top: 20px;">{{depatenamese}}</div>
          <div class="mianportrait" style="color:#FcF51c;font-size: 22px;font-weight: 700;">队伍人员立体剖析</div>
        </header>                    
        <div style="margin-top: 15px;width:90%;text-align: -webkit-center">
          <div class="mianportrait headerbg" style="height: 150px;">
            <el-row>
              <el-col :span="6">
                <div class="rbitbox" style="width:100%;min-width:290px;height:150px;padding: 30px 0 0px 4%;overflow-x: hidden;">
                  <div style="float: left;width:30%;margin-right: 2%;" v-for="itemes in headInfoLists">
                    <img :src="itemes.img" style="width: 50px;"/>
                    <!-- <img src="../../assets/image/portasts/lou2.png" style="width: 50px;"/> -->
                    <div style="width:100%;"><div>{{itemes.duty}}</div><div>{{itemes.name}}</div></div>
                  </div>
                </div>
              </el-col>
               <!-- <a :href="item.links" class="alinkto" target="_black"></a> -->
              <el-col :span="15">
                <div class="mianportrait" style="height: 150px;padding-left: 15px;">
                  <div class="mianboxs">
                    <el-popover placement="bottom" :title="detailtitless" width="400" trigger="click">
                      <div style="text-align: -webkit-center;height:400px;overflow-x: hidden;">
                        <div style="float: left;width:100px;height:120px;margin: 10px;cursor: pointer;" v-for="itemse in unitAgeDetailse" @click="myportarits(itemse)">
                          <img :src="itemse.img" style="width: 30px;"/>
                          <div style="width:100%;"><div>{{itemse.name}}</div><div>{{itemse.duty}}</div><div>{{itemse.deptName}}</div></div>
                        </div>
                      </div>
                      <div slot="reference"  @click="detailsclick('领导班子成员')" class="boxhesad" style="padding-top: 36px;">
                        <img src="../../assets/image/policeporlit/组 33.png"/>
                        <span style="position: absolute;left: 60px;top: 55px;">{{this.unitAgeDetail.headCount}}</span>                   
                      </div>
                    </el-popover>
                    <div class="mianportrait" style="font-size: 16px;">领导班子成员</div>
                  </div>
                  <div class="mianboxs" @click="detailsclick('内下设机构主要领导')">
                    <el-popover placement="bottom" :title="detailtitless" width="400" trigger="click">
                      <div style="text-align: -webkit-center;height:400px;overflow-x: hidden;">
                        <div style="float: left;width:100px;height:120px;margin: 10px;cursor: pointer;" v-for="itemse in unitAgeDetailse" @click="myportarits(itemse)">
                          <img :src="itemse.img" style="width: 30px;"/>
                          <div style="width:100%;"><div>{{itemse.name}}</div><div>{{itemse.duty}}</div><div>{{itemse.deptName}}</div></div>
                        </div>
                      </div>
                      <div  slot="reference"  @click="detailsclick('内下设机构主要领导')" class="boxhesad">
                        <img src="../../assets/image/policeporlit/组 34.png"/>
                        <span style="position: absolute;left: 60px;top: 55px;">{{this.unitAgeDetail.inSetsCount}}</span>
                      </div>
                    </el-popover>
                    <div class="mianportrait" style="font-size: 16px;"><div v-if="isWhite == '1'">内下设机构</div><div>主要领导</div></div>
                  </div>
                  <div class="mianboxs" @click="detailsclick('内下设机构政工干部')">
                    <el-popover placement="bottom" :title="detailtitless" width="400" trigger="click">
                      <div style="text-align: -webkit-center;height:400px;overflow-x: hidden;">
                        <div style="float: left;width:100px;height:120px;margin: 10px;cursor: pointer;" v-for="itemse in unitAgeDetailse" @click="myportarits(itemse)">
                          <img :src="itemse.img" style="width: 30px;"/>
                          <div style="width:100%;"><div>{{itemse.name}}</div><div>{{itemse.duty}}</div><div>{{itemse.deptName}}</div></div>
                        </div>
                      </div>
                      <div slot="reference"  @click="detailsclick('内下设机构政工干部')" class="boxhesad">
                        <img src="../../assets/image/policeporlit/组 35.png"/>
                        <span style="position: absolute;left: 60px;top: 55px;">{{this.unitAgeDetail.inSetsPoliticalCount}}</span> 
                      </div>
                    </el-popover>
                    <div class="mianportrait" style="font-size: 16px;"><div v-if="isWhite == '1'">内下设机构</div><div>政工干部</div></div>
                  </div>
                  <div class="mianboxs" @click="detailsclick('内下设机构副职领导')">
                     <el-popover placement="bottom" :title="detailtitless" width="400" trigger="click">
                      <div style="text-align: -webkit-center;height:400px;overflow-x: hidden;">
                        <div style="float: left;width:100px;height:120px;margin: 10px;cursor: pointer;" v-for="itemse in unitAgeDetailse" @click="myportarits(itemse)">
                          <img :src="itemse.img" style="width: 30px;"/>
                          <div style="width:100%;"><div>{{itemse.name}}</div><div>{{itemse.duty}}</div><div>{{itemse.deptName}}</div></div>
                        </div>
                      </div>
                      <div slot="reference"  @click="detailsclick('内下设机构副职领导')" class="boxhesad">
                        <img src="../../assets/image/policeporlit/组 36.png"/>
                        <span style="position: absolute;left: 60px;top: 55px;">{{this.unitAgeDetail.inSetsDeputyCount}}</span> 
                      </div>
                    </el-popover>
                    <div class="mianportrait" style="font-size: 16px;"><div v-if="isWhite == '1'">内下设机构</div><div>副职领导</div></div>
                  </div>
                  <div class="mianboxs" @click="detailsclick('民警')">
                    <el-popover placement="bottom" :title="detailtitless" width="400" trigger="click">
                      <div style="text-align: -webkit-center;height:400px;overflow-x: hidden;">
                        <div style="float: left;width:100px;height:120px;margin: 10px;cursor: pointer;" v-for="itemse in unitAgeDetailse" @click="myportarits(itemse)">
                          <img :src="itemse.img" style="width: 30px;"/>
                          <div style="width:100%;"><div>{{itemse.name}}</div><div>{{itemse.duty}}</div><div>{{itemse.deptName}}</div></div>
                        </div>
                      </div>
                      <div slot="reference"  @click="detailsclick('民警')" class="boxhesad">
                        <img src="../../assets/image/policeporlit/组 37.png"/>
                        <span style="position: absolute;left: 55px;top: 55px;">{{this.unitAgeDetail.policeCount}}</span>
                      </div>
                    </el-popover>
                    <div class="mianportrait" style="font-size: 16px;">民警</div>
                  </div>
                </div>
              </el-col>
              <el-col :span="3" style="padding-top: 10px;">
                <div class="personcolor" style="float: right;font-size: 12px;padding-right: 10px;">
                  <div style="font-size: 18px;">{{personals}}</div>
                  <div>总人数</div>
                </div>
                <div style="float: right;font-size: 12px;margin-right: 5px;"> <img src="../../assets/image/policeporlit/人.png" style="width: 35px;"/></div>
              </el-col>
            </el-row>
          </div>
          <div class="mianportrait articebg">
             <div class="mianportrait" style="font-size: 16px;text-indent:2em;">经分析，截止<span class="miancolorse">{{details.now}}</span>，{{details.unitName}}人员平均年龄<span class="miancolorse">{{details.avgAge}}</span>岁，{{details.ageCompare}}于市局总体平均年龄{{details.allAvgAge}}岁，<span class="miancolorse">{{details.tolAgeBetween}}</span>人员比例偏高。
             学历以<span class="miancolorse">{{details.educationTol}}</span>为主，占总体人员<span class="miancolorse">{{details.degreeOfEducation}}</span>，其中本科学历占比<span class="miancolorse">{{details.bachelorDegreeOf}}</span>，<span class="miancolorse2">{{details.educationCompare}}</span>市局本科平均值<span class="miancolorse">{{details.allBachelorDegreeOf}}</span>。
             副处职以上比例<span class="miancolorse">{{details.uprightPositionDegreeOf}}</span>，<span class="miancolorse2">{{details.dutyCompare}}</span>市局平均值<span class="miancolorse">{{details.allUprightPositionDegreeOf}}</span>。</div>
          </div>
          <div class="mianportrait" style="margin-top: 20px;font-size: 14px;">
            <div class="eachartstyle" v-if='seriesclick'>
              <div class="mianportrait" style="padding: 10px 10px 0 10px;text-align:left;">
                <div  style="float:left;"><img src="../../assets/image/policeporlit/单一来源.png" style="margin-right:10px;"/>来源分析</div>
                <div  style="float:right;" v-if="isWhite == '1'&&isWhitese=='1'"><span style="margin-right:10px;"  class="unitSourcesclickse clickcolor" @click="unitSourcesclick('机关')">机关</span><span class="unitSourcesclickse" @click="unitSourcesclick('派出所')">派出所</span></div>
              </div>
              <div class="mianportrait" style="width:100%;height:20px;text-align:right;"><span style="color: #3ad0f3;font-size: 25px;margin-right: 20px;cursor: pointer;" class="el-icon-s-grid" @click="detailtcs('来源分析')"></span></div>
              <div id="unitSources" style="width:100%;height:330px;"></div>
            </div>
            <div class="eachartstyle" v-if='seriesclick'>
              <div class="mianportrait" style="padding: 10px 10px 0 10px;text-align:left;">
                <div  style="float:left;"><img src="../../assets/image/policeporlit/年龄 (1).png" style="margin-right:10px;"/>年龄分析</div>
                <div  style="float:right;" v-if="isWhite == '1'&&isWhitese=='1'"><span style="margin-right:10px;"  class="unitAgeCountsclickse clickcolor" @click="unitAgeCountsclick('机关')">机关</span><span class="unitAgeCountsclickse" @click="unitAgeCountsclick('派出所')">派出所</span></div>
              </div>
              <div class="mianportrait" style="width:100%;height:20px;text-align:right;"><span style="color: #3ad0f3;font-size: 25px;margin-right: 20px;cursor: pointer;" class="el-icon-s-grid" @click="detailtcs('年龄分析')"></span></div>
              <div id="unitAgeCounts" style="width:100%;height:330px;"></div>
            </div>
            <div class="eachartstyle" v-if='seriesclick'>
              <div class="mianportrait" style="padding: 10px 10px 0 10px;text-align:left;">
                <div  style="float:left;"><img src="../../assets/image/policeporlit/教育.png" style="margin-right:10px;"/>学历分析</div>
                <div  style="float:right;" v-if="isWhite == '1'&&isWhitese=='1'"><span style="margin-right:10px;"  class="unitEducationCountsclickse clickcolor" @click="unitEducationCountsclick('机关')">机关</span><span class="unitEducationCountsclickse" @click="unitEducationCountsclick('派出所')">派出所</span></div>
              </div>
              <div class="mianportrait" style="width:100%;height:20px;text-align:right;"><span style="color: #3ad0f3;font-size: 25px;margin-right: 20px;cursor: pointer;" class="el-icon-s-grid" @click="detailtcs('学历分析')"></span></div>
              <div id="unitEducationCounts" style="width:100%;height:330px;"></div>
            </div>
            <!-- <div class="eachartstyle" v-if='seriesclick'>
              <div class="mianportrait" style="padding: 10px 10px 0 10px;text-align:left;">
                <div  style="float:left;"><img src="../../assets/image/policeporlit/学校概况.png" style="margin-right:10px;"/>毕业院校分析</div>
                <div  style="float:right;" v-if="isWhite == '1'"><span style="margin-right:10px;"  class="schoolsclickse" @click="schoolsclick('机关')">机关</span><span class="schoolsclickse clickcolor" @click="schoolsclick('派出所')">派出所</span></div>
              </div>
              <div class="mianportrait" style="width:100%;height:20px;text-align:right;"><span style="color: #3ad0f3;font-size: 25px;margin-right: 20px;cursor: pointer;" class="el-icon-s-grid" @click="detailtcs('毕业院校分析')"></span></div>
              <div id="schools" style="width:100%;height:330px;"></div>
            </div> -->
            <div class="eachartstyle" v-if='seriesclick'>
              <div class="mianportrait" style="padding: 10px 10px 0 10px;text-align:left;">
                <div  style="float:left;"><img src="../../assets/image/policeporlit/工作年限.png" style="margin-right:10px;"/>入警年限分析</div>
                <div  style="float:right;" v-if="isWhite == '1'&&isWhitese=='1'"><span style="margin-right:10px;"  class="unitWorkAgeCountsclickse clickcolor" @click="unitWorkAgeCountsclick('机关')">机关</span><span class="unitWorkAgeCountsclickse" @click="unitWorkAgeCountsclick('派出所')">派出所</span></div>
              </div>
              <div class="mianportrait" style="width:100%;height:20px;text-align:right;"><span style="color: #3ad0f3;font-size: 25px;margin-right: 20px;cursor: pointer;" class="el-icon-s-grid" @click="detailtcs('入警年限分析')"></span></div>
              <div id="unitWorkAgeCounts" style="width:100%;height:330px;"></div>
            </div>
            <div class="eachartstyle" v-if='seriesclick'>
              <div class="mianportrait" style="padding: 10px 10px 0 10px;text-align:left;">
                <div  style="float:left;"><img src="../../assets/image/policeporlit/婚姻状态.png" style="margin-right:10px;"/>婚姻分析</div>
                <div  style="float:right;" v-if="isWhite == '1'&&isWhitese=='1'"><span style="margin-right:10px;"  class="unitMaritalCountsclickse clickcolor" @click="unitMaritalCountsclick('机关')">机关</span><span class="unitMaritalCountsclickse" @click="unitMaritalCountsclick('派出所')">派出所</span></div>
              </div>
              <div class="mianportrait" style="width:100%;height:20px;text-align:right;"><span style="color: #3ad0f3;font-size: 25px;margin-right: 20px;cursor: pointer;" class="el-icon-s-grid" @click="detailtcs('婚姻分析')"></span></div>
              <div id="unitMaritalCounts" style="width:100%;height:330px;"></div>
            </div>
          </div>
        </div>
      </div>
      <el-dialog width="80%" :title="detailtitle" :visible.sync="innerVisible" append-to-body>
        <div class="mianportrait" style="font-size: 16px;margin:20px;">
          <div style="width:100%;clear:both;">
            <div style="width:50%;float: left;">
            <span>选择单位:</span>
            <el-select v-model="deptvalue" multiple collapse-tags style="margin-left: 20px;" :title="this.deptlabel"  @change="unitSourcestc('a')" placeholder="请选择单位" clearable>
              <el-option v-for="item in detaillistdata" :key="item.value" :label="item.label" :value="item.value">
              </el-option>
            </el-select>
            </div>
             <div style="width:50%;text-align:right;float: left;padding-right: 20px;"><el-button type="primary" class="exportbuttoncolor" @click="dataexport">数据导出</el-button></div>
          </div>
          <div class="mianportrait" style="padding-top: 20px;">
            <div style="width:100%;font-size: 16px;height: 600px;border-top: 1px solid #e8e8e8;">
              <div class="lefts" style="text-align:center;">
                <ul class="listStyle" style="overflow:auto">
                  <li v-for="(items,index) in detaillisttype" :key="index" :class="activeClass == index?'active':''" @click="btnGetItems(index)">{{ items }}</li>
                </ul>
              </div>
              <div style="width:82%;text-align:center;float: left;">
                <el-table :data="tableDatase" style="width: 100%" stripe class="tablestyle">
                  <el-table-column prop="deptName" width="140" label="单位">
                      <template slot-scope="props">
                        <span v-if="props.row.deptName&&props.row.deptName.length>6" :title= "props.row.deptName">{{props.row.deptName.slice(0,6)}}...</span>
                        <span v-else>{{props.row.deptName}}</span>
                      </template>
                  </el-table-column>
                  <el-table-column prop="userName" label="姓名"></el-table-column>
                  <el-table-column prop="sex" label="性别" ></el-table-column>
                  <el-table-column prop="age" label="年龄"></el-table-column>
                  <el-table-column prop="nation" label="民族"></el-table-column>
                  <el-table-column prop="party" label="政治面貌" min-width="120"></el-table-column>
                  <el-table-column prop="duty" label="职务" min-width="120">
                    <template slot-scope="props">
                        <span v-if="props.row.duty && props.row.duty.length>6" :title= "props.row.duty">{{props.row.duty.slice(0,6)}}...</span>
                        <span v-else>{{props.row.duty}}</span>
                      </template>
                  </el-table-column>
                  <el-table-column prop="jobGrade" label="职级" min-width="120">
                    <template slot-scope="props">
                        <span v-if="props.row.jobGrade && props.row.jobGrade.length>6" :title= "props.row.jobGrade">{{props.row.jobGrade.slice(0,6)}}...</span>
                        <span v-else>{{props.row.jobGrade}}</span>
                      </template>
                  </el-table-column>
                  <el-table-column prop="policeRank" label="警衔"></el-table-column>
                  <el-table-column prop="education" label="学历学位" min-width="140">
                    <template slot-scope="props">
                        <span v-if="props.row.education&&props.row.education.length>6" :title= "props.row.education">{{props.row.education.slice(0,6)}}...</span>
                        <span v-else>{{props.row.education}}</span>
                      </template>
                  </el-table-column>
                  <el-table-column prop="school" label="毕业院校" min-width="160">
                    <template slot-scope="props">
                        <span v-if="props.row.school&&props.row.school.length>8" :title= "props.row.school">{{props.row.school.slice(0,8)}}...</span>
                        <span v-else>{{props.row.school}}</span>
                      </template>
                  </el-table-column>   
                </el-table>
                <el-pagination
                  style="float: right;margin: 20px 40px 0 0;"
                  background
                  :total="totalRecord"
                  :page-size="10"
                  @current-change="handleCurrentChange"
                  :current-page.sync="pageno"
                  layout="total, prev, pager, next">
                </el-pagination>
                <table  id="tipMessageLabel" v-show="false">
                  <tr>
                      <th>单位</th>
                      <th>姓名</th>
                      <th>性别</th>
                      <th>年龄</th>
                      <th>民族</th>
                      <th>政治面貌</th>
                      <th>职务</th>
                      <th>职级</th>
                      <th>警衔</th>
                      <th>学历学位</th>
                      <th>毕业院校</th>
                  </tr>
                  <tr v-for="item in multipleSelection">
                      <td>{{item.deptName}}</td>
                      <td>{{item.userName}}</td>
                      <td>{{item.sex}}</td>
                      <td>{{item.age}}</td>
                      <td>{{item.nation}}</td>
                      <td>{{item.party}}</td>
                      <td>{{item.duty}}</td>
                      <td>{{item.jobGrade}}</td>
                      <td>{{item.policeRank}}</td>
                      <td>{{item.education}}</td>
                      <td>{{item.school}}</td>
                  </tr>
              </table>  
              </div>
            </div>
          </div>
        </div>
      </el-dialog>
    </el-dialog>     
  </div>
</template>
<script>
import { Base64 } from "js-base64";
import { getUrlParams } from "@/utils";
import { setTimeout } from "timers";
export default {
  props: [],
  name: "teampersonnelcontroller",
  data: function() {
    return {
      optionslist: [],
      inputsearchse: "",
      depatename: "",
      options: [
        {
          value: "总人数",
          label: "总人数"
        },
        {
          value: "平均年龄",
          label: "平均年龄"
        },
        {
          value: "组织机构",
          label: "组织机构"
        }
      ],
      valuelise: "总人数",
      valuedata: "单位名称",
      optionsdata: [
        {
          value: "单位名称",
          label: "单位名称"
        },
        {
          value: "政工考核单元",
          label: "政工考核单元"
        }
      ],
      valuedatazgn: "",
      optionsdatazgn: [],
      valuedatazgd: "",
      optionsdatazgd: [],
      optionsdatazgs: [],
      sexs: "2",
      // idNumberse: "320114196610232446",
      // idNumberse: "320114196707282415",
      idNumberse: "",
      contestlist: [],
      contestlistnext: [],
      contestlistcs: [],
      falges: "2",
      falgdata: 0,
      seriesclick: false,
      depatenamese: "",
      personals: "",
      unitIds: "",
      details: {},
      myChartunitSource: null,
      myChartunitAgeCount: null,
      myChartunitEducationCounts: null,
      myChartunitMaritalCounts: null,
      myChartunitWorkAgeCount: null,
      kesy: "1",
      kesy1: "1",
      kesy2: "1",
      kesy3: "1",
      kesy4: "1",
      kesy5: "1",
      isWhite: "1",
      isWhitese: "1",
      valuedatazgs: "",
      optionsdataset: [],
      innerVisible: false,
      detailtitle: "",
      detaillistdata: [],
      detaillisttype: [],
      unitSourcelistdata: [],
      unitSourcelisttype: [],
      unitAgeCountlistdata: [],
      unitAgeCountlisttype: [],
      unitEducationCountlistdata: [],
      unitEducationCountlisttype: [],
      unitWorkAgeCountlistdata: [],
      unitWorkAgeCountlisttype: [],
      unitMaritalCountlistdata: [],
      unitMaritalCountlisttype: [],
      schoolCountlistdata: [],
      schoolCountlisttype: [],
      deptvalue: [],
      depttype: "",
      deptlabel: [],
      activeClass: 0,
      totalRecord: 0,
      pageno: 1,
      tableDatase: [],
      multipleSelection: [],
      isclicks: false,
      headInfoLists: [],
      unitAgeDetail: [],
      detailtitless: "",
      unitAgeDetailse: [],
      routeres: "",
      loading: true,
      companyIdess: ""
    };
  },
  watch: {},
  mounted() {
    document.querySelector("body").style.height = "auto";
    document.querySelector("html").style.height = "auto";
    const params = getUrlParams(location.hash);
    const idNumberss = Base64.decode(params.no || "");
    this.idNumberse = idNumberss;
    let urls = window.location.href;
    let routers = urls.split("#");
    this.routeres = routers[0] + "#/policePortraitHome/myPortrait";
    console.log(this.routeres);
    setTimeout(() => {
      this.getPersonLog("队伍人员立体剖析");
      this.init();
    }, 300);
  },
  destroyed() {
    document.querySelector("body").style.height = "100%";
    document.querySelector("html").style.height = "100%";
  },
  methods: {
    slecktclick() {
      if (this.valuedata == "政工考核单元") {
        this.$axios
          .get("/teamPerson/getPerformanceAppraisal")
          .then(res => {
            if (res.code == 0) {
              this.optionsdatazgn = [];
              this.optionsdataset = [];
              this.optionsdataset = res.data;
              res.data.forEach(itemse => {
                this.optionsdatazgn.push({
                  value: itemse.id,
                  label: itemse.name
                });
              });
            } else {
              this.$message(res.message);
            }
          })
          .catch(function(error) {
            console.log(error);
          });
      } else {
        this.valuedatazgn = "";
        this.valuedatazgs = "";
        this.valuedatazgd = "";
      }
    },
    slecktclickzgn() {
      this.optionsdatazgs = [];
      this.optionsdataset.forEach(itemse => {
        if (this.valuedatazgn == itemse.id) {
          itemse.performVoList.forEach(items => {
            this.optionsdatazgs.push({
              value: items.id,
              label: items.name
            });
          });
        }
      });
    },
    slecktclickzgs() {
      this.valuedatazgd = "";
      this.optionsdatazgd = [];
      this.optionsdataset.forEach(itemse => {
        if (this.valuedatazgn == itemse.id) {
          itemse.performVoList.forEach(items => {
            if (this.valuedatazgs == items.id) {
              if (items.performVoList.length > 0) {
                items.performVoList.forEach(itemes => {
                  this.optionsdatazgd.push({
                    value: itemes.id,
                    label: itemes.name
                  });
                });
              } else {
                this.slecktssearch(this.valuedatazgs);
              }
            }
          });
        }
      });
    },
    slecktclickzgd() {
      this.loading = true;
      this.slecktssearch(this.valuedatazgd);
    },
    slecktssearch(parmes) {
      this.loading = true;
      var pattern = new RegExp(
        "[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）——|{}【】‘；：”“'。，、？]"
      );
      var rs = 0;
      for (var i = 0; i < this.inputsearchse.length; i++) {
        let s = this.inputsearchse.substr(i, 1).replace(pattern, "");
        let se = s.match(/\ /g);
        if (!s || se) {
          rs++;
        }
      }
      if (rs == 0) {
        this.sexs = "2";
        let groupId = parmes;
        if (this.valuedata == "政工考核单元") {
          this.inputsearchse = "";
        } else {
          this.valuedatazgn = "";
        }
        this.$axios
          .post("/teamPerson/getSearch", {
            groupId: groupId,
            idNumber: this.idNumberse,
            param: this.inputsearchse,
            performId: this.valuedatazgn,
            type: this.valuedata,
            sort: this.valuelise
          })
          .then(res => {
            if (res.code == 0) {
              this.contestlistnext = res.data;
              this.contestlist = res.data;
              this.contestlistcs = this.contestlist.slice(0, 9);
              if (this.contestlist.length > 9) {
                this.falges = "1";
              } else {
                this.falges = "2";
              }
              this.isclicks = true;
              this.loading = false;
            } else {
              this.$message(res.message);
            }
          })
          .catch(function(error) {
            console.log(error);
          });
      } else {
        this.inputsearchse = "";
        this.$message("不可输入特殊字符！");
      }
    },
    //判断那个页面
    getPersonLog(params) {
      this.$axios
        .post("/log/getPersonLog", {
          flagName: "队伍综合评估",
          idNumber: this.idNumberse,
          pageName: params
        })
        .then(res => {
          if (res.code == 0) {
            console.log(res.message);
          } else {
            this.$message(res.message);
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    //根据个人ID获取初始数据
    init() {
      this.loading = true;
      if (this.isclicks == true && this.sexs == "2") {
        if (this.valuedatazgs && !this.valuedatazgd) {
          this.slecktssearch(this.valuedatazgs);
        } else if (this.valuedatazgd) {
          this.slecktssearch(this.valuedatazgd);
        } else {
          this.slecktssearch("");
        }
      } else {
        console.log(this.sexs);
        if (this.sexs == "1") {
          let paremse = {
            isSub: "1",
            deptName: this.depatename,
            companyId: this.companyIdess
          };
          this.subisclick(paremse);
        } else if (this.isclicks == false) {
          this.$axios
            .post("/teamPerson/personStereo", {
              idNumber: this.idNumberse,
              companyId: "",
              type: this.valuelise
            })
            .then(res => {
              if (res.code == 0) {
                this.contestlistnext = res.data;
                this.contestlist = res.data;
                this.contestlistcs = this.contestlist.slice(0, 9);
                if (this.contestlist.length > 9) {
                  this.falges = "1";
                } else {
                  this.falges = "2";
                }
                // if (res.data[0].isWhite == "1") {
                this.sexs = "2";
                // } else {
                //   this.sexs = "1";
                // }
                this.loading = false;
              } else {
                this.$message(res.message);
              }
            })
            .catch(function(error) {
              console.log(error);
            });
        }
      }
    },
    //是否可以下钻
    subisclick(prame) {
      if (prame.isSub == "1") {
        this.loading = true;
        this.sexs = "1";
        this.falgdata = 0;
        this.depatename = prame.deptName;
        this.companyIdess = prame.companyId;
        this.$axios
          .post("/teamPerson/personStereo", {
            idNumber: this.idNumberse,
            companyId: prame.companyId,
            type: this.valuelise
          })
          .then(res => {
            if (res.code == 0) {
              this.contestlist = res.data;
              this.contestlistcs = this.contestlist.slice(0, 9);
              if (this.contestlist.length > 9) {
                this.falges = "1";
              } else {
                this.falges = "2";
              }
              this.loading = false;
            } else {
              this.$message(res.message);
            }
          })
          .catch(function(error) {
            console.log(error);
          });
      }
    },
    //是否有信息分析
    tcryisclick(pramese) {
      if (pramese.isClick == "1") {
        this.seriesclick = true;
        this.depatenamese = pramese.deptName;
        this.personals = pramese.personSum;
        this.unitIds = pramese.companyId;
        this.isWhite = pramese.isWhite;
        this.isWhitese = pramese.isWhite;
        this.headInfoLists = pramese.headInfoList;
        this.$axios
          .get("/teamPerson/personStereo?unitId=" + this.unitIds)
          .then(res => {
            if (res.code == 0) {
              this.details = res.data;
            } else {
              this.$message(res.message);
            }
          })
          .catch(function(error) {
            console.log(error);
          });
        this.$axios
          .get("/teamPerson/getUnitHeadDetail?unitId=" + this.unitIds)
          .then(res => {
            if (res.code == 0) {
              this.unitAgeDetail = res.data;
            } else {
              this.$message(res.message);
            }
          })
          .catch(function(error) {
            console.log(error);
          });
        this.unitSourcesclick("机关");
        this.unitAgeCountsclick("机关");
        this.unitEducationCountsclick("机关");
        this.unitWorkAgeCountsclick("机关");
        this.unitMaritalCountsclick("机关");
      }
    },
    //加载更多
    loaddatas() {
      if (this.falges == "1") {
        this.falgdata++;
        this.contestlistcs = this.contestlist.slice(0, 9 * (this.falgdata + 1));
        if (9 * (this.falgdata + 1) > this.contestlist.length) {
          this.falges = "2";
        }
      }
    },
    //返回分局数据
    canelisclick() {
      this.loading = true;
      this.falgdata = 0;
      this.sexs = "2";
      this.contestlist = this.contestlistnext;
      this.contestlistcs = this.contestlist.slice(0, 9);
      if (this.contestlist.length > 9) {
        this.falges = "1";
      } else {
        this.falges = "2";
      }
      this.loading = false;
    },
    //来源分析数据查询
    unitSource() {
      this.$axios
        .get("/teamPerson/unitSource?unitId=" + this.unitIds)
        .then(res => {
          if (res.code == 0) {
            this.myChartunitSource = this.$echarts.init(
              document.getElementById("unitSources")
            );
            if (res.data.unitSourceVoList.length == 1) {
              this.isWhitese = "0";
            }
            if (this.isWhite == "0" || res.data.unitSourceVoList.length == 1) {
              let policeSchools = res.data.unitSourceVoList[0].policeSchool
                ? res.data.unitSourceVoList[0].policeSchool
                : 0;
              let soldier = res.data.unitSourceVoList[0].soldier
                ? res.data.unitSourceVoList[0].soldier
                : 0;
              let social = res.data.unitSourceVoList[0].social
                ? res.data.unitSourceVoList[0].social
                : 0;
              let fold = res.data.unitSourceVoList[0].fold
                ? res.data.unitSourceVoList[0].fold
                : 0;
              let elseSource = res.data.unitSourceVoList[0].elseSource
                ? res.data.unitSourceVoList[0].elseSource
                : 0;
              let seriesdats = [
                {
                  value: policeSchools,
                  name: res.data.column[0],
                  itemStyle: {
                    color: "#ffd659"
                  }
                },
                {
                  value: soldier,
                  name: res.data.column[1],
                  itemStyle: {
                    color: "#4bdfff"
                  }
                },
                {
                  value: social,
                  name: res.data.column[2],
                  itemStyle: {
                    color: "#ff9667"
                  }
                },
                {
                  value: fold,
                  name: res.data.column[3],
                  itemStyle: {
                    color: "#94e4c0"
                  }
                },
                {
                  value: elseSource,
                  name: res.data.column[4],
                  itemStyle: {
                    color: "#a591f4"
                  }
                }
              ];
              this.unitSourcelistdata = [
                {
                  label: this.depatenamese,
                  value: res.data.unitSourceVoList[0].deptId
                }
              ];
              this.unitSourcelisttype = res.data.column;
              var optionunitSourcese = {
                tooltip: {
                  trigger: "axis",
                  axisPointer: {
                    // 坐标轴指示器，坐标轴触发有效
                    type: "shadow" // 默认为直线，可选为：'line' | 'shadow'
                  }
                },
                legend: {
                  data: res.data.column,
                  top: 10,
                  right: 0,
                  orient: "vertical"
                },
                grid: {
                  left: "0%",
                  right: "30%",
                  bottom: "3%",
                  containLabel: true
                },
                xAxis: {
                  type: "value"
                },
                yAxis: {
                  type: "category",
                  data: res.data.column
                },
                series: [
                  {
                    name: "",
                    type: "bar",
                    data: seriesdats
                  }
                ]
              };
              this.myChartunitSource.setOption(optionunitSourcese, true);
            } else if (
              this.isWhite == "1" &&
              res.data.unitSourceVoList.length > 1
            ) {
              let yAxisdata = [];
              let yAxisdata2 = [];
              let seriesdata = {
                column1: [],
                column2: [],
                column3: [],
                column4: [],
                column5: []
              };
              let seriesdata2 = {
                column1: [],
                column2: [],
                column3: [],
                column4: [],
                column5: []
              };
              let unitSourcelistdatas1 = [];
              let unitSourcelistdatas2 = [];
              for (let k = 0; k < res.data.unitSourceVoList.length; k++) {
                if (res.data.unitSourceVoList[k].type == "机关") {
                  yAxisdata.push(res.data.unitSourceVoList[k].deptName);
                  seriesdata.column1.push(
                    res.data.unitSourceVoList[k].policeSchool
                  );
                  seriesdata.column2.push(res.data.unitSourceVoList[k].soldier);
                  seriesdata.column3.push(res.data.unitSourceVoList[k].social);
                  seriesdata.column4.push(res.data.unitSourceVoList[k].fold);
                  seriesdata.column5.push(
                    res.data.unitSourceVoList[k].elseSource
                  );
                  unitSourcelistdatas1.push({
                    label: res.data.unitSourceVoList[k].deptName,
                    value: res.data.unitSourceVoList[k].deptId
                  });
                } else if (res.data.unitSourceVoList[k].type == "派出所") {
                  yAxisdata2.push(res.data.unitSourceVoList[k].deptName);
                  seriesdata2.column1.push(
                    res.data.unitSourceVoList[k].policeSchool
                  );
                  seriesdata2.column2.push(
                    res.data.unitSourceVoList[k].soldier
                  );
                  seriesdata2.column3.push(res.data.unitSourceVoList[k].social);
                  seriesdata2.column4.push(res.data.unitSourceVoList[k].fold);
                  seriesdata2.column5.push(
                    res.data.unitSourceVoList[k].elseSource
                  );
                  unitSourcelistdatas2.push({
                    label: res.data.unitSourceVoList[k].deptName,
                    value: res.data.unitSourceVoList[k].deptId
                  });
                }
              }
              let yAxisdatase = this.kesy == "0" ? yAxisdata2 : yAxisdata;
              let seriesdatase = this.kesy == "0" ? seriesdata2 : seriesdata;
              this.unitSourcelistdata =
                this.kesy == "0" ? unitSourcelistdatas2 : unitSourcelistdatas1;
              this.unitSourcelisttype = res.data.column;
              var optionunitSource = {
                tooltip: {
                  trigger: "axis",
                  axisPointer: {
                    // 坐标轴指示器，坐标轴触发有效
                    type: "shadow" // 默认为直线，可选为：'line' | 'shadow'
                  }
                },
                legend: {
                  data: res.data.column,
                  top: 10,
                  right: 0,
                  orient: "vertical"
                },
                grid: {
                  left: "0%",
                  right: "30%",
                  bottom: "3%",
                  containLabel: true
                },
                xAxis: {
                  type: "value"
                },
                yAxis: {
                  type: "category",
                  data: yAxisdatase
                },
                series: [
                  {
                    name: res.data.column[0],
                    type: "bar",
                    stack: "总量",
                    data: seriesdatase.column1,
                    color: "#ffd659"
                  },
                  {
                    name: res.data.column[1],
                    type: "bar",
                    stack: "总量",
                    data: seriesdatase.column2,
                    color: "#4bdfff"
                  },
                  {
                    name: res.data.column[2],
                    type: "bar",
                    stack: "总量",
                    data: seriesdatase.column3,
                    color: "#ff9667"
                  },
                  {
                    name: res.data.column[3],
                    type: "bar",
                    stack: "总量",
                    data: seriesdatase.column4,
                    color: "#94e4c0"
                  },
                  {
                    name: res.data.column[4],
                    type: "bar",
                    stack: "总量",
                    data: seriesdatase.column5,
                    color: "#a591f4"
                  }
                ]
              };
              this.myChartunitSource.setOption(optionunitSource, true);
            }
          } else {
            this.$message(res.message);
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    //来源分析机关以及派出所切换
    unitSourcesclick(parems) {
      let unitSourcs = document.querySelectorAll(".unitSourcesclickse");
      if (parems == "派出所") {
        this.kesy = "0";
        //根据索引数设置样式
        for (let kss = 0; kss < unitSourcs.length; kss++) {
          if (kss == 1) {
            unitSourcs[kss].classList.add("clickcolor");
            this.flag = true;
          } else {
            unitSourcs[kss].classList.remove("clickcolor");
          }
        }
      } else {
        this.kesy = "1";
        //根据索引数设置样式
        for (let kss = 0; kss < unitSourcs.length; kss++) {
          if (kss == 0) {
            unitSourcs[kss].classList.add("clickcolor");
            this.flag = true;
          } else {
            unitSourcs[kss].classList.remove("clickcolor");
          }
        }
      }
      this.unitSource();
    },
    //年龄分析机关以及派出所切换
    unitAgeCountsclick(parems) {
      let unitAgeCounts = document.querySelectorAll(".unitAgeCountsclickse");
      if (parems == "派出所") {
        this.kesy1 = "0";
        //根据索引数设置样式
        for (let kss = 0; kss < unitAgeCounts.length; kss++) {
          if (kss == 1) {
            unitAgeCounts[kss].classList.add("clickcolor");
            this.flag = true;
          } else {
            unitAgeCounts[kss].classList.remove("clickcolor");
          }
        }
      } else {
        this.kesy1 = "1";
        //根据索引数设置样式
        for (let kss = 0; kss < unitAgeCounts.length; kss++) {
          if (kss == 0) {
            unitAgeCounts[kss].classList.add("clickcolor");
            this.flag = true;
          } else {
            unitAgeCounts[kss].classList.remove("clickcolor");
          }
        }
      }
      this.unitAgeCount();
    },
    //年龄分析数据查询
    unitAgeCount() {
      this.$axios
        .get("/teamPerson/unitAgeCount?unitId=" + this.unitIds)
        .then(res => {
          if (res.code == 0) {
            this.myChartunitAgeCount = this.$echarts.init(
              document.getElementById("unitAgeCounts")
            );
            if (res.data.unitAgeCountVoList.length == 1) {
              this.isWhitese = "0";
            }
            if (
              this.isWhite == "0" ||
              res.data.unitAgeCountVoList.length == 1
            ) {
              let moreThanFity = res.data.unitAgeCountVoList[0].moreThanFity
                ? res.data.unitAgeCountVoList[0].moreThanFity
                : 0;
              let fortyOneAndFifty = res.data.unitAgeCountVoList[0]
                .fortyOneAndFifty
                ? res.data.unitAgeCountVoList[0].fortyOneAndFifty
                : 0;
              let thirtyAndForty = res.data.unitAgeCountVoList[0].thirtyAndForty
                ? res.data.unitAgeCountVoList[0].thirtyAndForty
                : 0;
              let twentyAndThirty = res.data.unitAgeCountVoList[0]
                .twentyAndThirty
                ? res.data.unitAgeCountVoList[0].twentyAndThirty
                : 0;
              let underTwenty = res.data.unitAgeCountVoList[0].underTwenty
                ? res.data.unitAgeCountVoList[0].underTwenty
                : 0;
              let seriesdase = [
                {
                  value: moreThanFity,
                  name: res.data.column[0],
                  itemStyle: {
                    color: "#ffd659"
                  }
                },
                {
                  value: fortyOneAndFifty,
                  name: res.data.column[1],
                  itemStyle: {
                    color: "#4bdfff"
                  }
                },
                {
                  value: thirtyAndForty,
                  name: res.data.column[2],
                  itemStyle: {
                    color: "#ff9667"
                  }
                },
                {
                  value: twentyAndThirty,
                  name: res.data.column[3],
                  itemStyle: {
                    color: "#94e4c0"
                  }
                },
                {
                  value: underTwenty,
                  name: res.data.column[4],
                  itemStyle: {
                    color: "#a591f4"
                  }
                }
              ];
              this.unitAgeCountlistdata = [
                {
                  label: this.depatenamese,
                  value: res.data.unitAgeCountVoList[0].deptId
                }
              ];
              this.unitAgeCountlisttype = res.data.column;
              var optionunitAgeCountse = {
                tooltip: {
                  trigger: "axis",
                  axisPointer: {
                    // 坐标轴指示器，坐标轴触发有效
                    type: "shadow" // 默认为直线，可选为：'line' | 'shadow'
                  }
                },
                legend: {
                  data: res.data.column,
                  top: 10,
                  right: 0,
                  orient: "vertical"
                },
                grid: {
                  left: "3%",
                  right: "15%",
                  bottom: "3%",
                  containLabel: true
                },
                xAxis: [
                  {
                    type: "category",
                    data: res.data.column,
                    axisLabel: {
                      rotate: 45
                    }
                  }
                ],
                yAxis: [
                  {
                    type: "value"
                  }
                ],
                series: [
                  {
                    name: "",
                    type: "bar",
                    data: seriesdase
                  }
                ]
              };
              this.myChartunitAgeCount.setOption(optionunitAgeCountse, true);
            } else if (
              this.isWhite == "1" &&
              res.data.unitAgeCountVoList.length > 1
            ) {
              let yAxisdata = [];
              let yAxisdata2 = [];
              let seriesdata = {
                column1: [],
                column2: [],
                column3: [],
                column4: [],
                column5: []
              };
              let seriesdata2 = {
                column1: [],
                column2: [],
                column3: [],
                column4: [],
                column5: []
              };
              let unitSourcelistdatas1 = [];
              let unitSourcelistdatas2 = [];
              for (let k = 0; k < res.data.unitAgeCountVoList.length; k++) {
                if (res.data.unitAgeCountVoList[k].type == "机关") {
                  yAxisdata.push(res.data.unitAgeCountVoList[k].deptName);
                  seriesdata.column1.push(
                    res.data.unitAgeCountVoList[k].moreThanFity
                  );
                  seriesdata.column2.push(
                    res.data.unitAgeCountVoList[k].fortyOneAndFifty
                  );
                  seriesdata.column3.push(
                    res.data.unitAgeCountVoList[k].thirtyAndForty
                  );
                  seriesdata.column4.push(
                    res.data.unitAgeCountVoList[k].twentyAndThirty
                  );
                  seriesdata.column5.push(
                    res.data.unitAgeCountVoList[k].underTwenty
                  );
                  unitSourcelistdatas1.push({
                    label: res.data.unitAgeCountVoList[k].deptName,
                    value: res.data.unitAgeCountVoList[k].deptId
                  });
                } else if (res.data.unitAgeCountVoList[k].type == "派出所") {
                  yAxisdata2.push(res.data.unitAgeCountVoList[k].deptName);
                  seriesdata2.column1.push(
                    res.data.unitAgeCountVoList[k].moreThanFity
                  );
                  seriesdata2.column2.push(
                    res.data.unitAgeCountVoList[k].fortyOneAndFifty
                  );
                  seriesdata2.column3.push(
                    res.data.unitAgeCountVoList[k].thirtyAndForty
                  );
                  seriesdata2.column4.push(
                    res.data.unitAgeCountVoList[k].twentyAndThirty
                  );
                  seriesdata2.column5.push(
                    res.data.unitAgeCountVoList[k].underTwenty
                  );
                  unitSourcelistdatas2.push({
                    label: res.data.unitAgeCountVoList[k].deptName,
                    value: res.data.unitAgeCountVoList[k].deptId
                  });
                }
              }
              let yAxisdatase = this.kesy1 == "0" ? yAxisdata2 : yAxisdata;
              let seriesdatase = this.kesy1 == "0" ? seriesdata2 : seriesdata;
              this.unitAgeCountlistdata =
                this.kesy1 == "0" ? unitSourcelistdatas2 : unitSourcelistdatas1;
              this.unitAgeCountlisttype = res.data.column;
              var optionunitAgeCount = {
                tooltip: {
                  trigger: "axis",
                  axisPointer: {
                    // 坐标轴指示器，坐标轴触发有效
                    type: "shadow" // 默认为直线，可选为：'line' | 'shadow'
                  }
                },
                legend: {
                  data: res.data.column,
                  top: 10,
                  right: 0,
                  orient: "vertical"
                },
                grid: {
                  left: "3%",
                  right: "15%",
                  bottom: "3%",
                  containLabel: true
                },
                xAxis: [
                  {
                    type: "category",
                    data: yAxisdatase,
                    axisLabel: {
                      rotate: 45
                    }
                  }
                ],
                yAxis: [
                  {
                    type: "value"
                  }
                ],
                series: [
                  {
                    name: res.data.column[0],
                    type: "bar",
                    stack: "总量",
                    data: seriesdatase.column1,
                    color: "#ffd659"
                  },
                  {
                    name: res.data.column[1],
                    type: "bar",
                    stack: "总量",
                    data: seriesdatase.column2,
                    color: "#4bdfff"
                  },
                  {
                    name: res.data.column[2],
                    type: "bar",
                    stack: "总量",
                    data: seriesdatase.column3,
                    color: "#ff9667"
                  },
                  {
                    name: res.data.column[3],
                    type: "bar",
                    stack: "总量",
                    data: seriesdatase.column4,
                    color: "#94e4c0"
                  },
                  {
                    name: res.data.column[4],
                    type: "bar",
                    stack: "总量",
                    data: seriesdatase.column5,
                    color: "#a591f4"
                  }
                ]
              };
              this.myChartunitAgeCount.setOption(optionunitAgeCount, true);
            }
          } else {
            this.$message(res.message);
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    //学历分析数据查询
    unitEducationCount() {
      this.$axios
        .get("/teamPerson/unitEducationCount?unitId=" + this.unitIds)
        .then(res => {
          if (res.code == 0) {
            this.myChartunitEducationCounts = this.$echarts.init(
              document.getElementById("unitEducationCounts")
            );
            if (res.data.unitEducationCountList.length == 1) {
              this.isWhitese = "0";
            }
            if (
              this.isWhite == "0" ||
              res.data.unitEducationCountList.length == 1
            ) {
              let doctor = res.data.unitEducationCountList[0].doctor
                ? res.data.unitEducationCountList[0].doctor
                : 0;
              let master = res.data.unitEducationCountList[0].master
                ? res.data.unitEducationCountList[0].master
                : 0;
              let college = res.data.unitEducationCountList[0].college
                ? res.data.unitEducationCountList[0].college
                : 0;
              let juniorCollege = res.data.unitEducationCountList[0]
                .juniorCollege
                ? res.data.unitEducationCountList[0].juniorCollege
                : 0;
              let senior = res.data.unitEducationCountList[0].senior
                ? res.data.unitEducationCountList[0].senior
                : 0;
              let seriesdase = [
                {
                  value: doctor,
                  name: res.data.column[0],
                  itemStyle: {
                    color: "#ffd659"
                  }
                },
                {
                  value: master,
                  name: res.data.column[1],
                  itemStyle: {
                    color: "#4bdfff"
                  }
                },
                {
                  value: college,
                  name: res.data.column[2],
                  itemStyle: {
                    color: "#ff9667"
                  }
                },
                {
                  value: juniorCollege,
                  name: res.data.column[3],
                  itemStyle: {
                    color: "#94e4c0"
                  }
                },
                {
                  value: senior,
                  name: res.data.column[4],
                  itemStyle: {
                    color: "#a591f4"
                  }
                }
              ];
              this.unitEducationCountlistdata = [
                {
                  label: this.depatenamese,
                  value: res.data.unitEducationCountList[0].deptId
                }
              ];
              this.unitEducationCountlisttype = res.data.column;
              var optionunitEducationCountse = {
                tooltip: {
                  trigger: "axis"
                },
                legend: {
                  data: res.data.column,
                  top: 10,
                  right: 0,
                  orient: "vertical"
                },
                grid: {
                  left: "3%",
                  right: "18%",
                  bottom: "0%",
                  containLabel: true
                },
                calculable: true,
                xAxis: [
                  {
                    type: "category",
                    data: res.data.column,
                    axisLabel: {
                      rotate: 45
                    }
                  }
                ],
                yAxis: [
                  {
                    type: "value"
                  }
                ],
                series: [
                  {
                    name: "",
                    type: "bar",
                    data: seriesdase
                  }
                ]
              };
              this.myChartunitEducationCounts.setOption(
                optionunitEducationCountse,
                true
              );
            } else if (
              this.isWhite == "1" &&
              res.data.unitEducationCountList.length > 1
            ) {
              let yAxisdata = [];
              let yAxisdata2 = [];
              let seriesdata = {
                column1: [],
                column2: [],
                column3: [],
                column4: [],
                column5: []
              };
              let seriesdata2 = {
                column1: [],
                column2: [],
                column3: [],
                column4: [],
                column5: []
              };
              let unitSourcelistdatas1 = [];
              let unitSourcelistdatas2 = [];
              for (let k = 0; k < res.data.unitEducationCountList.length; k++) {
                if (res.data.unitEducationCountList[k].type == "机关") {
                  yAxisdata.push(res.data.unitEducationCountList[k].deptName);
                  seriesdata.column1.push(
                    res.data.unitEducationCountList[k].doctor
                  );
                  seriesdata.column2.push(
                    res.data.unitEducationCountList[k].master
                  );
                  seriesdata.column3.push(
                    res.data.unitEducationCountList[k].college
                  );
                  seriesdata.column4.push(
                    res.data.unitEducationCountList[k].juniorCollege
                  );
                  seriesdata.column5.push(
                    res.data.unitEducationCountList[k].senior
                  );
                  unitSourcelistdatas1.push({
                    label: res.data.unitEducationCountList[k].deptName,
                    value: res.data.unitEducationCountList[k].deptId
                  });
                } else if (
                  res.data.unitEducationCountList[k].type == "派出所"
                ) {
                  yAxisdata2.push(res.data.unitEducationCountList[k].deptName);
                  seriesdata2.column1.push(
                    res.data.unitEducationCountList[k].doctor
                  );
                  seriesdata2.column2.push(
                    res.data.unitEducationCountList[k].master
                  );
                  seriesdata2.column3.push(
                    res.data.unitEducationCountList[k].college
                  );
                  seriesdata2.column4.push(
                    res.data.unitEducationCountList[k].juniorCollege
                  );
                  seriesdata2.column5.push(
                    res.data.unitEducationCountList[k].senior
                  );
                  unitSourcelistdatas2.push({
                    label: res.data.unitEducationCountList[k].deptName,
                    value: res.data.unitEducationCountList[k].deptId
                  });
                }
              }
              let yAxisdatase = this.kesy2 == "0" ? yAxisdata2 : yAxisdata;
              let seriesdatase = this.kesy2 == "0" ? seriesdata2 : seriesdata;
              this.unitEducationCountlistdata =
                this.kesy2 == "0" ? unitSourcelistdatas2 : unitSourcelistdatas1;
              this.unitEducationCountlisttype = res.data.column;
              var optionunitEducationCount = {
                tooltip: {
                  trigger: "axis"
                },
                legend: {
                  data: res.data.column,
                  top: 10,
                  right: 0,
                  orient: "vertical"
                },
                grid: {
                  left: "3%",
                  right: "18%",
                  bottom: "0%",
                  containLabel: true
                },
                calculable: true,
                xAxis: [
                  {
                    type: "category",
                    data: yAxisdatase,
                    axisLabel: {
                      rotate: 45
                    }
                  }
                ],
                yAxis: [
                  {
                    type: "value"
                  }
                ],
                series: [
                  {
                    name: res.data.column[0],
                    type: "bar",
                    data: seriesdatase.column1,
                    color: "#ffd659"
                  },
                  {
                    name: res.data.column[1],
                    type: "bar",
                    data: seriesdatase.column2,
                    color: "#4bdfff"
                  },
                  {
                    name: res.data.column[2],
                    type: "bar",
                    data: seriesdatase.column3,
                    color: "#ff9667"
                  },
                  {
                    name: res.data.column[3],
                    type: "bar",
                    data: seriesdatase.column4,
                    color: "#94e4c0"
                  },
                  {
                    name: res.data.column[4],
                    type: "bar",
                    data: seriesdatase.column5,
                    color: "#a591f4"
                  }
                ]
              };
              this.myChartunitEducationCounts.setOption(
                optionunitEducationCount,
                true
              );
            }
          } else {
            this.$message(res.message);
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    //学历分析机关以及派出所切换
    unitEducationCountsclick(parems) {
      let unitEducationCounts = document.querySelectorAll(
        ".unitEducationCountsclickse"
      );
      if (parems == "派出所") {
        this.kesy2 = "0";
        //根据索引数设置样式
        for (let kss = 0; kss < unitEducationCounts.length; kss++) {
          if (kss == 1) {
            unitEducationCounts[kss].classList.add("clickcolor");
            this.flag = true;
          } else {
            unitEducationCounts[kss].classList.remove("clickcolor");
          }
        }
      } else {
        this.kesy2 = "1";
        //根据索引数设置样式
        for (let kss = 0; kss < unitEducationCounts.length; kss++) {
          if (kss == 0) {
            unitEducationCounts[kss].classList.add("clickcolor");
            this.flag = true;
          } else {
            unitEducationCounts[kss].classList.remove("clickcolor");
          }
        }
      }
      this.unitEducationCount();
    },
    //毕业院校机关以及派出所切换
    schoolsclick(parems) {
      let schools = document.querySelectorAll(".schoolsclickse");
      if (parems == "派出所") {
        this.kesy3 = "0";
        //根据索引数设置样式
        for (let kss = 0; kss < schools.length; kss++) {
          if (kss == 1) {
            schools[kss].classList.add("clickcolor");
            this.flag = true;
          } else {
            schools[kss].classList.remove("clickcolor");
          }
        }
      } else {
        this.kesy3 = "1";
        //根据索引数设置样式
        for (let kss = 0; kss < schools.length; kss++) {
          if (kss == 0) {
            schools[kss].classList.add("clickcolor");
            this.flag = true;
          } else {
            schools[kss].classList.remove("clickcolor");
          }
        }
      }
      // this.schoolCount();
    },
    schoolCount() {
      this.$axios
        .get("/teamPerson/schoolCount?unitId=" + this.unitIds)
        .then(res => {
          if (res.code == 0) {
            this.myChartschoolCount = this.$echarts.init(
              document.getElementById("schools")
            );
            // if(res.data.unitEducationCountList.length == 1){this.isWhitese = '0';}
            if (
              this.isWhite == "0" ||
              res.data.unitEducationCountList.length == 1
            ) {
              var optionschoolCount = {
                tooltip: {
                  trigger: "axis"
                },
                legend: {
                  data: [
                    "邮件营销",
                    "联盟广告",
                    "视频广告",
                    "直接访问",
                    "搜索引擎"
                  ],
                  right: 0,
                  orient: "vertical"
                },
                grid: {
                  left: "0%",
                  right: "10%",
                  bottom: "3%",
                  containLabel: true
                },
                toolbox: {
                  feature: {
                    saveAsImage: {}
                  }
                },
                xAxis: {
                  type: "category",
                  boundaryGap: false,
                  data: ["周一", "周二", "周三", "周四", "周五", "周六", "周日"]
                },
                yAxis: {
                  type: "value"
                },
                series: [
                  {
                    name: "邮件营销",
                    type: "line",
                    stack: "总量",
                    data: [120, 132, 101, 134, 90, 230, 210]
                  },
                  {
                    name: "联盟广告",
                    type: "line",
                    stack: "总量",
                    data: [220, 182, 191, 234, 290, 330, 310]
                  },
                  {
                    name: "视频广告",
                    type: "line",
                    stack: "总量",
                    data: [150, 232, 201, 154, 190, 330, 410]
                  },
                  {
                    name: "直接访问",
                    type: "line",
                    stack: "总量",
                    data: [320, 332, 301, 334, 390, 330, 320]
                  },
                  {
                    name: "搜索引擎",
                    type: "line",
                    stack: "总量",
                    data: [820, 932, 901, 934, 1290, 1330, 1320]
                  }
                ]
              };
              // this.myChartschoolCount.setOption(optionschoolCount, true);
            } else if (this.isWhite == "1") {
              var optionschoolCount = {
                tooltip: {
                  trigger: "axis"
                },
                legend: {
                  data: [
                    "邮件营销",
                    "联盟广告",
                    "视频广告",
                    "直接访问",
                    "搜索引擎"
                  ],
                  right: 0,
                  orient: "vertical"
                },
                grid: {
                  left: "0%",
                  right: "10%",
                  bottom: "3%",
                  containLabel: true
                },
                toolbox: {
                  feature: {
                    saveAsImage: {}
                  }
                },
                xAxis: {
                  type: "category",
                  boundaryGap: false,
                  data: ["周一", "周二", "周三", "周四", "周五", "周六", "周日"]
                },
                yAxis: {
                  type: "value"
                },
                series: [
                  {
                    name: "邮件营销",
                    type: "line",
                    stack: "总量",
                    data: [120, 132, 101, 134, 90, 230, 210]
                  },
                  {
                    name: "联盟广告",
                    type: "line",
                    stack: "总量",
                    data: [220, 182, 191, 234, 290, 330, 310]
                  },
                  {
                    name: "视频广告",
                    type: "line",
                    stack: "总量",
                    data: [150, 232, 201, 154, 190, 330, 410]
                  },
                  {
                    name: "直接访问",
                    type: "line",
                    stack: "总量",
                    data: [320, 332, 301, 334, 390, 330, 320]
                  },
                  {
                    name: "搜索引擎",
                    type: "line",
                    stack: "总量",
                    data: [820, 932, 901, 934, 1290, 1330, 1320]
                  }
                ]
              };
              // this.myChartschoolCount.setOption(optionschoolCount, true);
            }
          } else {
            this.$message(res.message);
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    //年限分析机关以及派出所切换
    unitWorkAgeCountsclick(parems) {
      let unitWorkAgeCounts = document.querySelectorAll(
        ".unitWorkAgeCountsclickse"
      );
      if (parems == "派出所") {
        this.kesy4 = "0";
        //根据索引数设置样式
        for (let kss = 0; kss < unitWorkAgeCounts.length; kss++) {
          if (kss == 1) {
            unitWorkAgeCounts[kss].classList.add("clickcolor");
            this.flag = true;
          } else {
            unitWorkAgeCounts[kss].classList.remove("clickcolor");
          }
        }
      } else {
        this.kesy4 = "1";
        //根据索引数设置样式
        for (let kss = 0; kss < unitWorkAgeCounts.length; kss++) {
          if (kss == 0) {
            unitWorkAgeCounts[kss].classList.add("clickcolor");
            this.flag = true;
          } else {
            unitWorkAgeCounts[kss].classList.remove("clickcolor");
          }
        }
      }
      this.unitWorkAgeCount();
    },
    //年限分析数据查询
    unitWorkAgeCount() {
      this.$axios
        .get("/teamPerson/unitWorkAgeCount?unitId=" + this.unitIds)
        .then(res => {
          if (res.code == 0) {
            this.myChartunitWorkAgeCount = this.$echarts.init(
              document.getElementById("unitWorkAgeCounts")
            );
            if (res.data.unitWorkAgeCountVoList.length == 1) {
              this.isWhitese = "0";
            }
            if (
              this.isWhite == "0" ||
              res.data.unitWorkAgeCountVoList.length == 1
            ) {
              let underFive = res.data.unitWorkAgeCountVoList[0].underFive
                ? res.data.unitWorkAgeCountVoList[0].underFive
                : 0;
              let fiveAndNine = res.data.unitWorkAgeCountVoList[0].fiveAndNine
                ? res.data.unitWorkAgeCountVoList[0].fiveAndNine
                : 0;
              let tenAndNineteen = res.data.unitWorkAgeCountVoList[0]
                .tenAndNineteen
                ? res.data.unitWorkAgeCountVoList[0].tenAndNineteen
                : 0;
              let twentyAndTwentyNine = res.data.unitWorkAgeCountVoList[0]
                .twentyAndTwentyNine
                ? res.data.unitWorkAgeCountVoList[0].twentyAndTwentyNine
                : 0;
              let moreThanThirty = res.data.unitWorkAgeCountVoList[0]
                .moreThanThirty
                ? res.data.unitWorkAgeCountVoList[0].moreThanThirty
                : 0;
              this.unitWorkAgeCountlisttype = res.data.column;
              this.unitWorkAgeCountlistdata = [
                {
                  label: this.depatenamese,
                  value: res.data.unitWorkAgeCountVoList[0].deptId
                }
              ];
              let seriesdase = [
                {
                  value: underFive,
                  name: res.data.column[0],
                  itemStyle: {
                    color: "#ffd659"
                  }
                },
                {
                  value: fiveAndNine,
                  name: res.data.column[1],
                  itemStyle: {
                    color: "#4bdfff"
                  }
                },
                {
                  value: tenAndNineteen,
                  name: res.data.column[2],
                  itemStyle: {
                    color: "#ff9667"
                  }
                },
                {
                  value: twentyAndTwentyNine,
                  name: res.data.column[3],
                  itemStyle: {
                    color: "#94e4c0"
                  }
                },
                {
                  value: moreThanThirty,
                  name: res.data.column[4],
                  itemStyle: {
                    color: "#a591f4"
                  }
                }
              ];
              var optionunitWorkAgeCountse = {
                tooltip: {
                  trigger: "axis",
                  axisPointer: {
                    // 坐标轴指示器，坐标轴触发有效
                    type: "shadow" // 默认为直线，可选为：'line' | 'shadow'
                  }
                },
                legend: {
                  data: res.data.column,
                  top: 10,
                  right: 0,
                  orient: "vertical"
                },
                grid: {
                  left: "3%",
                  right: "18%",
                  bottom: "3%",
                  containLabel: true
                },
                xAxis: [
                  {
                    type: "category",
                    data: res.data.column,
                    axisLabel: {
                      rotate: 45
                    }
                  }
                ],
                yAxis: [
                  {
                    type: "value"
                  }
                ],
                series: [
                  {
                    name: "",
                    type: "bar",
                    data: seriesdase
                  }
                ]
              };
              this.myChartunitWorkAgeCount.setOption(
                optionunitWorkAgeCountse,
                true
              );
            } else if (
              this.isWhite == "1" &&
              res.data.unitWorkAgeCountVoList.length > 1
            ) {
              let yAxisdata = [];
              let yAxisdata2 = [];
              let seriesdata = {
                column1: [],
                column2: [],
                column3: [],
                column4: [],
                column5: []
              };
              let seriesdata2 = {
                column1: [],
                column2: [],
                column3: [],
                column4: [],
                column5: []
              };
              let unitSourcelistdatas1 = [];
              let unitSourcelistdatas2 = [];
              for (let k = 0; k < res.data.unitWorkAgeCountVoList.length; k++) {
                if (res.data.unitWorkAgeCountVoList[k].type == "机关") {
                  yAxisdata.push(res.data.unitWorkAgeCountVoList[k].deptName);
                  seriesdata.column1.push(
                    res.data.unitWorkAgeCountVoList[k].underFive
                  );
                  seriesdata.column2.push(
                    res.data.unitWorkAgeCountVoList[k].fiveAndNine
                  );
                  seriesdata.column3.push(
                    res.data.unitWorkAgeCountVoList[k].tenAndNineteen
                  );
                  seriesdata.column4.push(
                    res.data.unitWorkAgeCountVoList[k].twentyAndTwentyNine
                  );
                  seriesdata.column5.push(
                    res.data.unitWorkAgeCountVoList[k].moreThanThirty
                  );
                  unitSourcelistdatas1.push({
                    label: res.data.unitWorkAgeCountVoList[k].deptName,
                    value: res.data.unitWorkAgeCountVoList[k].deptId
                  });
                } else if (
                  res.data.unitWorkAgeCountVoList[k].type == "派出所"
                ) {
                  yAxisdata2.push(res.data.unitWorkAgeCountVoList[k].deptName);
                  seriesdata2.column1.push(
                    res.data.unitWorkAgeCountVoList[k].underFive
                  );
                  seriesdata2.column2.push(
                    res.data.unitWorkAgeCountVoList[k].fiveAndNine
                  );
                  seriesdata2.column3.push(
                    res.data.unitWorkAgeCountVoList[k].tenAndNineteen
                  );
                  seriesdata2.column4.push(
                    res.data.unitWorkAgeCountVoList[k].twentyAndTwentyNine
                  );
                  seriesdata2.column5.push(
                    res.data.unitWorkAgeCountVoList[k].moreThanThirty
                  );
                  unitSourcelistdatas2.push({
                    label: res.data.unitWorkAgeCountVoList[k].deptName,
                    value: res.data.unitWorkAgeCountVoList[k].deptId
                  });
                }
              }
              let yAxisdatase = this.kesy4 == "0" ? yAxisdata2 : yAxisdata;
              let seriesdatase = this.kesy4 == "0" ? seriesdata2 : seriesdata;
              this.unitWorkAgeCountlistdata =
                this.kesy4 == "0" ? unitSourcelistdatas2 : unitSourcelistdatas1;
              this.unitWorkAgeCountlisttype = res.data.column;
              var optionunitWorkAgeCount = {
                tooltip: {
                  trigger: "axis",
                  axisPointer: {
                    // 坐标轴指示器，坐标轴触发有效
                    type: "shadow" // 默认为直线，可选为：'line' | 'shadow'
                  }
                },
                legend: {
                  data: res.data.column,
                  top: 10,
                  right: 0,
                  orient: "vertical"
                },
                grid: {
                  left: "3%",
                  right: "18%",
                  bottom: "3%",
                  containLabel: true
                },
                xAxis: [
                  {
                    type: "category",
                    data: yAxisdatase,
                    axisLabel: {
                      rotate: 45
                    }
                  }
                ],
                yAxis: [
                  {
                    type: "value"
                  }
                ],
                series: [
                  {
                    name: res.data.column[0],
                    type: "bar",
                    stack: "总量",
                    data: seriesdatase.column1,
                    color: "#ffd659"
                  },
                  {
                    name: res.data.column[1],
                    type: "bar",
                    stack: "总量",
                    data: seriesdatase.column2,
                    color: "#4bdfff"
                  },
                  {
                    name: res.data.column[2],
                    type: "bar",
                    stack: "总量",
                    data: seriesdatase.column3,
                    color: "#ff9667"
                  },
                  {
                    name: res.data.column[3],
                    type: "bar",
                    stack: "总量",
                    data: seriesdatase.column4,
                    color: "#94e4c0"
                  },
                  {
                    name: res.data.column[4],
                    type: "bar",
                    stack: "总量",
                    data: seriesdatase.column5,
                    color: "#a591f4"
                  }
                ]
              };
              this.myChartunitWorkAgeCount.setOption(
                optionunitWorkAgeCount,
                true
              );
            }
          } else {
            this.$message(res.message);
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    //婚姻分析机关以及派出所切换
    unitMaritalCountsclick(parems) {
      let unitMaritalCounts = document.querySelectorAll(
        ".unitMaritalCountsclickse"
      );
      if (parems == "派出所") {
        this.kesy5 = "0";
        //根据索引数设置样式
        for (let kss = 0; kss < unitMaritalCounts.length; kss++) {
          if (kss == 1) {
            unitMaritalCounts[kss].classList.add("clickcolor");
            this.flag = true;
          } else {
            unitMaritalCounts[kss].classList.remove("clickcolor");
          }
        }
      } else {
        this.kesy5 = "1";
        //根据索引数设置样式
        for (let kss = 0; kss < unitMaritalCounts.length; kss++) {
          if (kss == 0) {
            unitMaritalCounts[kss].classList.add("clickcolor");
            this.flag = true;
          } else {
            unitMaritalCounts[kss].classList.remove("clickcolor");
          }
        }
      }
      this.unitMaritalCount();
    },
    //婚姻分析数据查询
    unitMaritalCount() {
      this.$axios
        .get("/teamPerson/unitMaritalCount?unitId=" + this.unitIds)
        .then(res => {
          if (res.code == 0) {
            this.myChartunitMaritalCounts = this.$echarts.init(
              document.getElementById("unitMaritalCounts")
            );
            if (res.data.unitMaritalVoList.length == 1) {
              this.isWhitese = "0";
            }
            if (this.isWhite == "0" || res.data.unitMaritalVoList.length == 1) {
              let column = res.data.column;
              // let column = ["已婚", "未婚", "离婚", "丧偶"];
              let married = res.data.unitMaritalVoList[0].married
                ? res.data.unitMaritalVoList[0].married
                : 0;
              let unmarried = res.data.unitMaritalVoList[0].unmarried
                ? res.data.unitMaritalVoList[0].unmarried
                : 0;
              let divorce = res.data.unitMaritalVoList[0].divorce
                ? res.data.unitMaritalVoList[0].divorce
                : 0;
              let bereftOfPerson = res.data.unitMaritalVoList[0].bereftOfPerson
                ? res.data.unitMaritalVoList[0].bereftOfPerson
                : 0;
              this.unitMaritalCountlisttype = column;
              this.unitMaritalCountlistdata = [
                {
                  label: this.depatenamese,
                  value: res.data.unitMaritalVoList[0].deptId
                }
              ];
              let seriesdase = [
                {
                  value: unmarried,
                  name: column[0],
                  itemStyle: {
                    color: "#ffd659"
                  }
                },
                {
                  value: married,
                  name: column[1],
                  itemStyle: {
                    color: "#4bdfff"
                  }
                },
                {
                  value: bereftOfPerson,
                  name: column[2],
                  itemStyle: {
                    color: "#ff9667"
                  }
                },
                {
                  value: divorce,
                  name: column[3],
                  itemStyle: {
                    color: "#94e4c0"
                  }
                }
              ];
              var optionunitMaritalCountse = {
                tooltip: {
                  trigger: "axis"
                },
                legend: {
                  data: column,
                  top: 10,
                  right: 0,
                  orient: "vertical"
                },
                grid: {
                  left: "3%",
                  right: "15%",
                  bottom: "0%",
                  containLabel: true
                },
                calculable: true,
                xAxis: [
                  {
                    type: "category",
                    data: column,
                    axisLabel: {
                      rotate: 45
                    }
                  }
                ],
                yAxis: [
                  {
                    type: "value"
                  }
                ],
                series: [
                  {
                    name: "",
                    type: "bar",
                    data: seriesdase
                  }
                ]
              };
              this.myChartunitMaritalCounts.setOption(
                optionunitMaritalCountse,
                true
              );
            } else if (
              this.isWhite == "1" &&
              res.data.unitMaritalVoList.length > 1
            ) {
              let yAxisdata = [];
              let yAxisdata2 = [];
              let column = res.data.column;
              // let column = ["已婚", "未婚", "离婚", "丧偶"];
              let seriesdata = {
                column1: [],
                column2: [],
                column3: [],
                column4: []
              };
              let seriesdata2 = {
                column1: [],
                column2: [],
                column3: [],
                column4: []
              };
              let unitSourcelistdatas1 = [];
              let unitSourcelistdatas2 = [];
              for (let k = 0; k < res.data.unitMaritalVoList.length; k++) {
                if (res.data.unitMaritalVoList[k].type == "机关") {
                  yAxisdata.push(res.data.unitMaritalVoList[k].deptName);
                  seriesdata.column1.push(
                    res.data.unitMaritalVoList[k].married
                  );
                  seriesdata.column2.push(
                    res.data.unitMaritalVoList[k].unmarried
                  );
                  seriesdata.column3.push(
                    res.data.unitMaritalVoList[k].divorce
                  );
                  seriesdata.column4.push(
                    res.data.unitMaritalVoList[k].bereftOfPerson
                  );
                  unitSourcelistdatas1.push({
                    label: res.data.unitMaritalVoList[k].deptName,
                    value: res.data.unitMaritalVoList[k].deptId
                  });
                } else if (res.data.unitMaritalVoList[k].type == "派出所") {
                  yAxisdata2.push(res.data.unitMaritalVoList[k].deptName);
                  seriesdata2.column1.push(
                    res.data.unitMaritalVoList[k].married
                  );
                  seriesdata2.column2.push(
                    res.data.unitMaritalVoList[k].unmarried
                  );
                  seriesdata2.column3.push(
                    res.data.unitMaritalVoList[k].divorce
                  );
                  seriesdata2.column4.push(
                    res.data.unitMaritalVoList[k].bereftOfPerson
                  );
                  unitSourcelistdatas2.push({
                    label: res.data.unitMaritalVoList[k].deptName,
                    value: res.data.unitMaritalVoList[k].deptId
                  });
                }
              }
              let yAxisdatase = this.kesy5 == "0" ? yAxisdata2 : yAxisdata;
              let seriesdatase = this.kesy5 == "0" ? seriesdata2 : seriesdata;
              this.unitMaritalCountlisttype = column;
              this.unitMaritalCountlistdata =
                this.kesy5 == "0" ? unitSourcelistdatas2 : unitSourcelistdatas1;
              var optionunitMaritalCount = {
                tooltip: {
                  trigger: "axis"
                },
                legend: {
                  data: column,
                  top: 10,
                  right: 0,
                  orient: "vertical"
                },
                grid: {
                  left: "3%",
                  right: "15%",
                  bottom: "0%",
                  containLabel: true
                },
                calculable: true,
                xAxis: [
                  {
                    type: "category",
                    data: yAxisdatase,
                    axisLabel: {
                      rotate: 45
                    }
                  }
                ],
                yAxis: [
                  {
                    type: "value"
                  }
                ],
                series: [
                  {
                    name: column[0],
                    type: "bar",
                    data: seriesdatase.column2,
                    color: "#ffd659"
                  },
                  {
                    name: column[1],
                    type: "bar",
                    data: seriesdatase.column1,
                    color: "#4bdfff"
                  },
                  {
                    name: column[2],
                    type: "bar",
                    data: seriesdatase.column4,
                    color: "#ff9667"
                  },
                  {
                    name: column[3],
                    type: "bar",
                    data: seriesdatase.column3,
                    color: "#94e4c0"
                  }
                ]
              };
              this.myChartunitMaritalCounts.setOption(
                optionunitMaritalCount,
                true
              );
            }
          } else {
            this.$message(res.message);
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    //详情点击
    detailtcs(parems) {
      this.innerVisible = true;
      this.activeClass = 0;
      this.pageno = 1;
      if (parems == "来源分析") {
        this.detailtitle = "来源分析详情";
        this.detaillistdata = this.unitSourcelistdata;
        this.detaillisttype = this.unitSourcelisttype;
        this.depttype = this.detaillisttype[0];
        let listas = [];
        this.detaillistdata.forEach(items => {
          listas.push(items.value);
        });
        this.deptvalue = listas;
        this.unitSourcestc();
      } else if (parems == "年龄分析") {
        this.detailtitle = "年龄分析详情";
        this.detaillistdata = this.unitAgeCountlistdata;
        this.detaillisttype = this.unitAgeCountlisttype;
        this.depttype = this.detaillisttype[0];
        let listas = [];
        this.detaillistdata.forEach(items => {
          listas.push(items.value);
        });
        this.deptvalue = listas;
        this.unitSourcestc();
      } else if (parems == "学历分析") {
        this.detailtitle = "学历分析详情";
        this.detaillistdata = this.unitEducationCountlistdata;
        this.detaillisttype = this.unitEducationCountlisttype;
        this.depttype = this.detaillisttype[0];
        let listas = [];
        this.detaillistdata.forEach(items => {
          listas.push(items.value);
        });
        this.deptvalue = listas;
        this.unitSourcestc();
      } else if (parems == "毕业院校分析") {
        this.detailtitle = "毕业院校分析详情";
        this.detaillistdata = this.schoolCountlistdata;
        this.detaillisttype = this.schoolCountlisttype;
        this.depttype = this.detaillisttype[0];
        let listas = [];
        this.detaillistdata.forEach(items => {
          listas.push(items.value);
        });
        this.deptvalue = listas;
        this.unitSourcestc();
      } else if (parems == "入警年限分析") {
        this.detailtitle = "入警年限分析详情";
        this.detaillistdata = this.unitWorkAgeCountlistdata;
        this.detaillisttype = this.unitWorkAgeCountlisttype;
        this.depttype = this.detaillisttype[0];
        let listas = [];
        this.detaillistdata.forEach(items => {
          listas.push(items.value);
        });
        this.deptvalue = listas;
        this.unitSourcestc();
      } else if (parems == "婚姻分析") {
        this.detailtitle = "婚姻分析详情";
        this.detaillistdata = this.unitMaritalCountlistdata;
        this.detaillisttype = this.unitMaritalCountlisttype;
        this.depttype = this.detaillisttype[0];
        let listas = [];
        this.detaillistdata.forEach(items => {
          listas.push(items.value);
        });
        this.deptvalue = listas;
        this.unitSourcestc();
      }
    },
    //来源详情点击
    unitSourcestc(listass) {
      this.deptlabel = [];
      this.detaillistdata.forEach(items => {
        this.deptvalue.forEach(element => {
          if (items.value == element) {
            this.deptlabel.push(items.label);
          }
        });
      });
      if (listass) {
        this.pageno = 1;
      }
      if (this.detailtitle == "来源分析详情") {
        this.$axios
          .post("/teamPerson/unitSourceDetail", {
            deptIds: this.deptvalue,
            type: this.depttype,
            isDownload: "0"
          })
          .then(res => {
            if (res.code == 0) {
              this.multipleSelection = res.data;
              this.tableDatase = res.data.slice(
                (this.pageno - 1) * 10,
                this.pageno * 10
              );
              this.totalRecord = res.data.length;
            } else {
              this.$message(res.message);
            }
          })
          .catch(function(error) {
            console.log(error);
          });
      } else if (this.detailtitle == "年龄分析详情") {
        this.$axios
          .post("/teamPerson/unitAgeDetail", {
            deptIds: this.deptvalue,
            type: this.depttype,
            isDownload: "0"
          })
          .then(res => {
            if (res.code == 0) {
              this.multipleSelection = res.data;
              this.tableDatase = res.data.slice(
                (this.pageno - 1) * 10,
                this.pageno * 10
              );
              this.totalRecord = res.data.length;
            } else {
              this.$message(res.message);
            }
          })
          .catch(function(error) {
            console.log(error);
          });
      } else if (this.detailtitle == "学历分析详情") {
        this.$axios
          .post("/teamPerson/unitEducationDetail", {
            deptIds: this.deptvalue,
            type: this.depttype,
            isDownload: "0"
          })
          .then(res => {
            if (res.code == 0) {
              this.multipleSelection = res.data;
              this.tableDatase = res.data.slice(
                (this.pageno - 1) * 10,
                this.pageno * 10
              );
              this.totalRecord = res.data.length;
            } else {
              this.$message(res.message);
            }
          })
          .catch(function(error) {
            console.log(error);
          });
      } else if (this.detailtitle == "毕业院校分析详情") {
        this.$axios
          .post("/teamPerson/schoolDeatil", {
            deptIds: this.deptvalue,
            type: this.depttype,
            isDownload: "0"
          })
          .then(res => {
            if (res.code == 0) {
              this.multipleSelection = res.data;
              this.tableDatase = res.data.slice(
                (this.pageno - 1) * 10,
                this.pageno * 10
              );
              this.totalRecord = res.data.length;
            } else {
              this.$message(res.message);
            }
          })
          .catch(function(error) {
            console.log(error);
          });
      } else if (this.detailtitle == "入警年限分析详情") {
        this.$axios
          .post("/teamPerson/unitWorkAgeDetail", {
            deptIds: this.deptvalue,
            type: this.depttype,
            isDownload: "0"
          })
          .then(res => {
            if (res.code == 0) {
              this.multipleSelection = res.data;
              this.tableDatase = res.data.slice(
                (this.pageno - 1) * 10,
                this.pageno * 10
              );
              this.totalRecord = res.data.length;
            } else {
              this.$message(res.message);
            }
          })
          .catch(function(error) {
            console.log(error);
          });
      } else if (this.detailtitle == "婚姻分析详情") {
        this.$axios
          .post("/teamPerson/unitMaritalDetail", {
            deptIds: this.deptvalue,
            type: this.depttype,
            isDownload: "0"
          })
          .then(res => {
            if (res.code == 0) {
              this.multipleSelection = res.data;
              this.tableDatase = res.data.slice(
                (this.pageno - 1) * 10,
                this.pageno * 10
              );
              this.totalRecord = res.data.length;
            } else {
              this.$message(res.message);
            }
          })
          .catch(function(error) {
            console.log(error);
          });
      }
    },
    //类型点击
    btnGetItems(index) {
      this.activeClass = index;
      this.depttype = this.detaillisttype[index];
      this.pageno = 1;
      this.unitSourcestc();
    },
    //分页
    handleCurrentChange(val) {
      this.pageno = val;
      this.unitSourcestc();
    },
    //数据导出
    dataexport() {
      // 生成表格
      setTimeout(() => {
        $("#tipMessageLabel").table2excel({
          // 不被导出的表格行的CSS class类
          exclude: ".noExl",
          name: "Excel Document Name",
          filename: "详情数据.xls"
        });
      }, 500);
    },
    //人员列表
    detailsclick(parems) {
      if (parems == "领导班子成员") {
        this.detailtitless = "领导班子成员列表";
        this.unitAgeDetailse = this.unitAgeDetail.headInfoList;
      } else if (parems == "内下设机构主要领导") {
        if (this.isWhite == "1") {
          this.detailtitless = "内下设机构主要领导列表";
        } else {
          this.detailtitless = "主要领导列表";
        }
        this.unitAgeDetailse = this.unitAgeDetail.inSetsList;
      } else if (parems == "内下设机构政工干部") {
        if (this.isWhite == "1") {
          this.detailtitless = "内下设机构政工干部列表";
        } else {
          this.detailtitless = "政工干部列表";
        }
        this.unitAgeDetailse = this.unitAgeDetail.inSetsPoliticalList;
      } else if (parems == "内下设机构副职领导") {
        if (this.isWhite == "1") {
          this.detailtitless = "内下设机构副职领导列表";
        } else {
          this.detailtitless = "副职领导列表";
        }
        this.unitAgeDetailse = this.unitAgeDetail.inSetsDeputy;
      } else if (parems == "民警") {
        this.detailtitless = "民警列表";
        this.unitAgeDetailse = this.unitAgeDetail.police;
      }
    },
    //画像跳转
    myportarits(parems) {
      let idNumbert = Base64.encode(parems.idNo || "");
      console.log(idNumbert);
      let urlse = this.routeres + "?no=" + idNumbert;
      window.open(urlse);
    }
  }
};
</script>

<style scoped>
.mianportrait {
  width: 100%;
  clear: both;
  font-size: 12px;
}
.contrastbox /deep/.el-input--suffix .el-input__inner {
  padding-right: 30px;
  background: #576ea9;
  color: #fff;
}
.contrastbox /deep/.el-input-group {
  line-height: normal;
  display: inline-table;
  width: 50%;
  border-collapse: separate;
  border-spacing: 0;
  margin-left: 10px;
}
.input-with-select /deep/.el-input__inner {
  -webkit-appearance: none;
  background-image: none;
  border-radius: 4px;
  border: 1px solid #dcdfe6;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
  display: inline-block;
  font-size: inherit;
  height: 40px;
  line-height: 40px;
  outline: 0;
  padding: 0 15px;
  -webkit-transition: border-color 0.2s cubic-bezier(0.645, 0.045, 0.355, 1);
  transition: border-color 0.2s cubic-bezier(0.645, 0.045, 0.355, 1);
  width: 100%;
  background: #576ea9;
  opacity: 0.6;
  color: #fff;
}
.contrastbox /deep/.el-input-group__append,
.el-input-group__prepend {
  vertical-align: middle;
  display: table-cell;
  position: relative;
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  padding: 0 20px;
  width: 1px;
  white-space: nowrap;
  background: #576ea9;
  opacity: 0.6;
  color: #fff;
}
.carksbox {
  width: 31%;
  height: 230px;
  margin: 10px 1%;
  color: #000;
  background: url("../../assets/image/policeporlit/组 13.png") no-repeat center;
  background-size: cover;
  float: left;
}
._WHITE .carksbox {
  width: 31%;
  height: 230px;
  margin: 10px 1%;
  color: #fff;
  background: url("../../assets/image/policeporlit/组 13-2.png") no-repeat
    center;
  background-size: cover;
  float: left;
}
.personcolor {
  color: #fff;
}
._WHITE .personcolor {
  color: #5f7cc2;
}
.loubgs {
  width: 35px;
  line-height: 40px;
  margin: 10px 0px 10px 7px;
  float: left;
}
.loubgse {
  width: 35px;
  line-height: 40px;
  margin: 10px 0px 10px 7px;
  float: left;
}
.ageclor {
  font-size: 18px;
  color: #67a9fe;
}
._WHITE .ageclor {
  font-size: 18px;
  color: #90e5dd;
}
.ageclorse {
  font-size: 18px;
  color: #ff6e4f;
}
._WHITE .ageclorse {
  font-size: 18px;
  color: #e291a2;
}
.tcbgs {
  background: url("../../assets/image/policeporlit/组 31.png") no-repeat center;
  background-size: cover;
}
.mianportrait /deep/.el-dialog,
.el-pager li {
  background: none;
}
.mianportrait /deep/.el-dialog__headerbtn {
  position: absolute;
  top: 0px;
  right: -40px;
  padding: 0;
  background: 0 0;
  border: none;
  outline: 0;
  cursor: pointer;
  font-size: 30px;
}
.mianportrait /deep/.el-dialog__headerbtn .el-dialog__close {
  color: #fff;
}
.mianportrait /deep/.el-dialog__body {
  padding: 0px 0px;
  color: #606266;
  font-size: 14px;
  word-break: break-all;
}
.mianportrait /deep/.el-dialog__header {
  padding: 0px 0px 0px;
}
.headerbg {
  background: url("../../assets/image/policeporlit/组 32.png") no-repeat center;
  background-size: cover;
}
.mianboxs {
  width: 20%;
  height: 100%;
  text-align: center;
  float: left;
}
.boxhesad {
  width: 100%;
  position: relative;
  text-align: -webkit-center;
  padding-top: 30px;
  color: #fff;
  font-size: 18px;
  cursor: pointer;
}
.articebg {
  height: 100px;
  text-align: left;
  background-color: #fff;
  margin-top: 20px;
  border-radius: 10px;
}
.miancolorse {
  color: #468efd;
}
.miancolorse2 {
  color: #fe6c6e;
}
.eachartstyle {
  width: 48%;
  height: 380px;
  background: #fff;
  margin: 10px 2% 10px 0;
  float: left;
}
.clickcolor {
  color: #468efd;
  border-bottom: 2px solid #468efd;
}
.unitSourcesclickse,
.unitAgeCountsclickse,
.unitEducationCountsclickse,
.schoolsclickse,
.unitWorkAgeCountsclickse,
.unitMaritalCountsclickse {
  cursor: pointer;
}
.listStyle li {
  width: 100%;
  height: 43px;
  line-height: 43px;
  text-indent: 50px;
  cursor: pointer;
  text-align: -webkit-left;
}
.lefts {
  float: left;
  width: 18%;
  /* height: 100%; */
  height: 500px;
  overflow: auto;
  border-right: 1px solid #e8e8e8;
}
.listStyle li.active {
  background-color: #1890ff;
  color: #ffffff;
}
.rbitbox::-webkit-scrollbar {
  /*滚动条整体样式*/
  width: 6px; /*高宽分别对应横竖滚动条的尺寸*/
  height: 4px;
}
.rbitbox::-webkit-scrollbar-thumb {
  /*滚动条里面小方块*/
  border-radius: 5px;
  -webkit-box-shadow: inset 0 0 5px rgba(0, 0, 255, 0.8);
  background: rgba(0, 0, 255, 0.2);
}
.rbitbox::-webkit-scrollbar-track {
  /*滚动条里面轨道*/
  -webkit-box-shadow: inset 0 0 5px rgba(117, 153, 214, 0.2);
  border-radius: 0;
  background: rgba(117, 153, 214, 0.1);
}
</style>