<template>
    <div class="mianportrait" style="min-height:960px;position: relative;clear:both;">
      <!-- <transition enter-active-class="animated fadeInLeft" leave-active-class="animated fadeOutLeft"> -->
      <div class="mianportrait" v-if="flags=='0'">
        <el-row>
          <el-col :span="24" style="padding: 15px 50px;">
            <el-col :span="8">
              <div style="width:66%;min-width:380px;min-height:800px;float:left;padding-right:20px;">
                <div class="mianportrait leftbox">
                  <div class="mianportrait leftheader">添加单位对比</div>
                  <div class="mianportrait leftheadernext"><span class="headernextcolor nextbottom" v-if="falgbm1 == true" @click="leftclick('市局机关')">市局机关</span><span class="headernextcolor" v-if="falgfj == true" @click="leftclick('分局')">分局</span></div>
                   <div class="mianportrait contrastbox">
                      <el-input style="width: 90%;margin: 20px 0;" placeholder="请输入内容" v-model="inputsearchleft" class="input-with-select" >
                        <el-button slot="append" icon="el-icon-search" @click="slecktssearch" ></el-button>
                      </el-input>
                    </div> 
                    <div class="mianportrait" style="height:600px">

                      <div class="mianportrait" style="height:100%;text-align:left;" v-if="nextse=='1'">
                        <div class="mianportrait" style="height:40px;text-align: -webkit-right;padding-right: 10%;" v-if="getteamjbse[0]=='bm2'||getteamjbse[0]=='pcs'"><span style="color: #fff;cursor: pointer;" @click="canelse('市局机关')">返回上级</span></div>
                        <div class="mianportrait" style="height:520px;">
                          <div class="miannext" @mousedown="move(k,$event,items)" v-for="(items,k) in getteamlistshow" v-if="getteamjbse[0]=='bm1'||getteamjbse[1]=='bm1'" @click="getChildUnit(items,'市局机关')"><span class="fontstyles">{{items.simpleName}}</span></div>
                          <div class="miannext" @mousedown="move(ks,$event,itemss)" v-for="(itemss,ks) in getteamlistshow" v-if="getteamjbse[0]=='bm2'||getteamjbse[0]=='pcs'"><span  class="fontstyles" v-if="itemss.simpleName.length<16">{{itemss.simpleName}}</span><span class="fontstyles" v-if="itemss.simpleName.length>=16" :title="itemss.simpleName">{{itemss.simpleName.slice(0,15)}}...</span></div>
                        </div>
                        <div class="mianportrait" style="height:40px;text-align: -webkit-center;padding-left: 35%;">
                          <div class="bottombutton" v-for="kes in sumer" @click="sumerclick(kes)"></div>
                        </div>
                      </div>

                      <div class="mianportrait" style="height:100%;text-align:left;" v-if="nextse=='2'">
                        <div class="mianportrait" style="height:40px;text-align: -webkit-right;padding-right: 10%;" v-if="getteamjbse1[0]=='bm2'||getteamjbse1[0]=='pcs'"><span style="color: #fff;cursor: pointer;" @click="canelse('分局')">返回上级</span></div>
                        <div class="mianportrait" style="height:520px;">
                          <div class="miannext" @mousedown="move(kes,$event,itemes)" v-for="(itemes,kes) in getteamdatashow" v-if="getteamjbse1[0]=='fj'" @click="getChildUnit(itemes,'分局')"><span class="fontstyles">{{itemes.simpleName}}</span></div>
                          <div class="miannext" @mousedown="move(kse,$event,itemess)" v-for="(itemess,kse) in getteamdatashow" v-if="getteamjbse1[0]=='pcs'||getteamjbse1[0]=='bm2'"><span class="fontstyles">{{itemess.simpleName}}</span></div>
                        </div>
                        <div class="mianportrait" style="height:40px;text-align: -webkit-center;padding-left: 35%;">
                          <div class="bottombuttons" v-for="kess in sumers" @click="sumerclickes(kess)"></div>
                        </div>
                      </div>

                      <div class="mianportrait" style="height:100%;text-align:left;" v-if="nextse=='3'">
                        <div class="mianportrait" style="height:560px;">
                          <div class="miannext" @mousedown="move(key,$event,item)" v-for="(item,key) in getteamdatashowse"><span class="fontstyles">{{item.simpleName}}</span></div>
                        </div>
                        <div class="mianportrait" style="height:40px;text-align: -webkit-center;padding-left: 35%;">
                          <div class="bottombuttons" v-for="key in sumerse" @click="sumerseclickes(key)"></div>
                        </div>
                      </div>
                    </div>
                </div>
              </div>
              <div class="contrastbox" style="width:34%;min-width:200px;float:left;">
                  <el-select v-model="valuecontest" collapse-tags multiple placeholder="选择维度">
                    <el-option
                      v-for="item in cities"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                      <!-- <span ><img :src="item.imgs" style="margin-right: 10px;"/><span style="color: #8492a6; font-size: 13px">{{ item.label }}</span></span> -->
                    </el-option>
                  </el-select>
              </div>
            </el-col>
            <el-col :span="16">
              <div class="mianportrait backcontest">
                <div style="width:75%;height:100%;float:left;position: relative;">
                  <div class="backcontestnext backnext1" style="top: 3%;left: 11%;">
                    <div @click="checks('1')" style="text-align: -webkit-right;padding-right: 10px;color: #ffd;cursor: pointer;">x</div>
                  </div>
                  <div class="backcontestnext backnext2" style="left: 78%;top: 3%;">
                    <div @click="checks('2')" style="text-align: -webkit-right;padding-right: 10px;color: #ffd;cursor: pointer;">x</div>
                  </div>
                  <div class="backcontestnext backnext3" style="left: 6%;top: 46%;">
                    <div @click="checks('3')" style="text-align: -webkit-right;padding-right: 10px;color: #ffd;cursor: pointer;">x</div>
                  </div>
                  <div class="backcontestnext backnext4" style="left: 84%;top: 46%;">
                    <div @click="checks('4')" style="text-align: -webkit-right;padding-right: 10px;color: #ffd;cursor: pointer;">x</div>
                  </div>
                  <div class="backcontestnext backnext5" style="left: 45%;top: 70%;">
                    <div @click="checks('5')" style="text-align: -webkit-right;padding-right: 10px;color: #ffd;cursor: pointer;">x</div>
                  </div>
                </div> 
                <div style="width:25%;height:100%;float:left;position: relative;">
                  <div class="rightbuttons" @click="contestclicks" :duration="2000">开始对比</div>
                </div>  
                <section style="display: none" id="template">
                    <div class="miannext miannexts" style="margin:-4px 8px;z-index:999;overflow: hidden;"><span style="height: 70px;display: table-cell;vertical-align: middle;text-align: center;width: 80px;"></span></div>
                </section>    
              </div>
            </el-col>
          </el-col>
        </el-row>
      </div>
      <!-- </transition> -->
      <!-- <transition enter-active-class="animated fadeInRight" leave-active-class="animated fadeOutRight"> -->
      <div :class="'mianportrait'+ (this.drawer== true? ' _WHITE' : '')"  v-if="flags=='1'">
        <el-row>
          <el-col :span="24" style="padding: 5px 50px;text-align:left;">
            <div class="mianportrait contrastbox" style="margin: 30px 0 0 40px;">
              <el-col :span="4" class="contrastbox" style="line-height: 60px;">
                <el-select v-model="valuecontest" @change="clickconttest" collapse-tags multiple placeholder="选择维度">
                    <el-option
                      v-for="item in cities"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value">
                      <!-- <span style="float: left">{{ item.label }}</span>
                      <span style="float: right; color: #8492a6; font-size: 13px">{{ item.value }}</span> -->
                    </el-option>
                  </el-select>
              </el-col>
              <el-col :span="8" style="line-height: 60px;">
                <el-date-picker v-model="valueyear" type="year" placeholder="选择开始年度" @change="contrastclick" format="yyyy" value-format='yyyy' :editable='false' :clearable='false'></el-date-picker>
                <span class="linestyle">——</span>
                <el-date-picker v-model="valueyears" type="year" placeholder="选择结束年度" @change="contrastdata" format="yyyy" value-format='yyyy' :editable='false' :clearable='false'> </el-date-picker>
              </el-col>
              <el-col :span="12" style="text-align:right;padding-right: 2%;">
                <div class="miannextse"  v-for="itemse in movelistshows"><span style="height: 50px;display: table-cell;vertical-align: middle;text-align: center;width: 60px;word-wrap: break-word;word-break: break-all;" v-if="itemse.simpleName.length>10" :title="itemse.simpleName">{{itemse.simpleName.slice(0,10)}}...</span><span style="height: 50px;display: table-cell;vertical-align: middle;text-align: center;width: 60px;word-wrap: break-word;word-break: break-all;" v-if="itemse.simpleName.length<11">{{itemse.simpleName}}</span></div>
                <!-- <div class="miannextse"></div> -->
                <div style="float:right;line-height: 60px;">对比单位:</div>
              </el-col>
            </div>
            <div class="mianportraitboxx">
              <div class="mianportrait">
                <div class="biaomianleft colorbiao"><span  class="biaomiannext">对比维度</span></div>
                <div class="biaomian colorbiao"><span  class="biaomiannext">{{this.moveuplists[0].simpleName}}</span></div>
                <div class="biaomian colorbiao"><span  class="biaomiannext">{{this.moveuplists[1].simpleName}}</span></div>
                <div class="biaomian colorbiao" v-if="this.unitIdses.length>2"><span  class="biaomiannext">{{this.moveuplists[2].simpleName}}</span></div>
                <div class="biaomian colorbiao" v-if="this.unitIdses.length>3"><span  class="biaomiannext">{{this.moveuplists[3].simpleName}}</span></div>
                <div class="biaomian colorbiao" v-if="this.unitIdses.length>4"><span  class="biaomiannext">{{this.moveuplists[4].simpleName}}</span></div>
              </div>
              <div style="width:100%;height:630px;overflow-x: auto;">
              <div class="mianportrait" v-if="flagse1==true">
                <div class="biaomian1left"><span  class="biaomiannext1">年龄结构</span></div>
                <div class="biaomian1"><div id="agecomparison1" style="width: 100%;height:300px;" v-if="this.agecomparisonse == true"></div><img  src="./../../assets/image/teamAnalysis/developEffect/no-data.png"  v-if="this.agecomparisonse == false" style="width: 40%;height:200px;margin-top: 50px;"/></div>
                <div class="biaomian1"><div id="agecomparison2" style="width: 100%;height:300px;" v-if="this.agecomparisonse1 == true"></div><img  src="./../../assets/image/teamAnalysis/developEffect/no-data.png"  v-if="this.agecomparisonse1 == false" style="width: 40%;height:200px;margin-top: 50px;"/></div>
                <div class="biaomian1" v-if="this.unitIdses.length>2"><div id="agecomparison3" style="width: 100%;height:300px;" v-if="this.agecomparisonse2 == true"></div><img  src="./../../assets/image/teamAnalysis/developEffect/no-data.png"  v-if="this.agecomparisonse2 == false" style="width: 40%;height:200px;margin-top: 50px;"/></div>
                <div class="biaomian1" v-if="this.unitIdses.length>3"><div id="agecomparison4" style="width: 100%;height:300px;" v-if="this.agecomparisonse3 == true"></div><img  src="./../../assets/image/teamAnalysis/developEffect/no-data.png"  v-if="this.agecomparisonse3 == false" style="width: 40%;height:200px;margin-top: 50px;"/></div>
                <div class="biaomian1" v-if="this.unitIdses.length>4"><div id="agecomparison5" style="width: 100%;height:300px;" v-if="this.agecomparisonse4 == true"></div><img  src="./../../assets/image/teamAnalysis/developEffect/no-data.png"  v-if="this.agecomparisonse4 == false" style="width: 40%;height:200px;margin-top: 50px;"/></div>
              </div>
              <div class="mianportrait" v-if="flagse5==true">
                <div class="biaomian1left"><span  class="biaomiannext1">教育培训</span></div>
                <div class="biaomian1"><div id="getTrainComparisonse1" style="width: 100%;height:300px;" v-if="this.Trainse == true"></div><img  src="./../../assets/image/teamAnalysis/developEffect/no-data.png"  v-if="this.Trainse == false" style="width: 40%;height:200px;margin-top: 50px;"/></div>
                <div class="biaomian1"><div id="getTrainComparisonse2" style="width: 100%;height:300px;" v-if="this.Trainse1 == true"></div><img  src="./../../assets/image/teamAnalysis/developEffect/no-data.png"  v-if="this.Trainse1 == false" style="width: 40%;height:200px;margin-top: 50px;"/></div>
                <div class="biaomian1" v-if="this.unitIdses.length>2"><div id="getTrainComparisonse3" style="width: 100%;height:300px;" v-if="this.Trainse2 == true"></div><img  src="./../../assets/image/teamAnalysis/developEffect/no-data.png"  v-if="this.Trainse2 == false" style="width: 40%;height:200px;margin-top: 50px;"/></div>
                <div class="biaomian1" v-if="this.unitIdses.length>3"><div id="getTrainComparisonse4" style="width: 100%;height:300px;" v-if="this.Trainse3 == true"></div><img  src="./../../assets/image/teamAnalysis/developEffect/no-data.png"  v-if="this.Trainse3 == false" style="width: 40%;height:200px;margin-top: 50px;"/></div>
                <div class="biaomian1" v-if="this.unitIdses.length>4"><div id="getTrainComparisonse5" style="width: 100%;height:300px;" v-if="this.Trainse4 == true"></div><img  src="./../../assets/image/teamAnalysis/developEffect/no-data.png"  v-if="this.Trainse4 == false" style="width: 40%;height:200px;margin-top: 50px;"/></div>
              </div>
              <div class="mianportrait" v-if="flagse2==true">
                <div class="biaomian1left"><span  class="biaomiannext1">集体荣誉</span></div>
                <div class="biaomian1"><div id="getOrgHonorComparison1" style="width: 100%;height:300px;" v-if="this.orghonor == true"></div><img  src="./../../assets/image/teamAnalysis/developEffect/no-data.png"  v-if="this.orghonor == false" style="width: 40%;height:200px;margin-top: 50px;"/></div>
                <div class="biaomian1"><div id="getOrgHonorComparison2" style="width: 100%;height:300px;" v-if="this.orghonor1 == true"></div><img  src="./../../assets/image/teamAnalysis/developEffect/no-data.png"  v-if="this.orghonor1 == false" style="width: 40%;height:200px;margin-top: 50px;"/></div>
                <div class="biaomian1" v-if="this.unitIdses.length>2"><div id="getOrgHonorComparison3" style="width: 100%;height:300px;" v-if="this.orghonor2 == true"></div><img  src="./../../assets/image/teamAnalysis/developEffect/no-data.png"  v-if="this.orghonor2 == false" style="width: 40%;height:200px;margin-top: 50px;"/></div>
                <div class="biaomian1" v-if="this.unitIdses.length>3"><div id="getOrgHonorComparison4" style="width: 100%;height:300px;" v-if="this.orghonor3 == true"></div><img  src="./../../assets/image/teamAnalysis/developEffect/no-data.png"  v-if="this.orghonor3 == false" style="width: 40%;height:200px;margin-top: 50px;"/></div>
                <div class="biaomian1" v-if="this.unitIdses.length>4"><div id="getOrgHonorComparison5" style="width: 100%;height:300px;" v-if="this.orghonor4 == true"></div><img  src="./../../assets/image/teamAnalysis/developEffect/no-data.png"  v-if="this.orghonor4 == false" style="width: 40%;height:200px;margin-top: 50px;"/></div>
              </div>
              <div class="mianportrait" v-if="flagse3==true">
                <div class="biaomian1left"><span  class="biaomiannext1">个人荣誉</span></div>
                <div class="biaomian1"><div id="getPersonHonorComparison1" style="width: 100%;height:300px;" v-if="this.PersonHonor == true"></div><img  src="./../../assets/image/teamAnalysis/developEffect/no-data.png"  v-if="this.PersonHonor == false" style="width: 40%;height:200px;margin-top: 50px;"/></div>
                <div class="biaomian1"><div id="getPersonHonorComparison2" style="width: 100%;height:300px;" v-if="this.PersonHonor1 == true"></div><img  src="./../../assets/image/teamAnalysis/developEffect/no-data.png"  v-if="this.PersonHonor1 == false" style="width: 40%;height:200px;margin-top: 50px;"/></div>
                <div class="biaomian1" v-if="this.unitIdses.length>2"><div id="getPersonHonorComparison3" style="width: 100%;height:300px;" v-if="this.PersonHonor2 == true"></div><img  src="./../../assets/image/teamAnalysis/developEffect/no-data.png"  v-if="this.PersonHonor2 == false" style="width: 40%;height:200px;margin-top: 50px;"/></div>
                <div class="biaomian1" v-if="this.unitIdses.length>3"><div id="getPersonHonorComparison4" style="width: 100%;height:300px;" v-if="this.PersonHonor3 == true"></div><img  src="./../../assets/image/teamAnalysis/developEffect/no-data.png"  v-if="this.PersonHonor3 == false" style="width: 40%;height:200px;margin-top: 50px;"/></div>
                <div class="biaomian1" v-if="this.unitIdses.length>4"><div id="getPersonHonorComparison5" style="width: 100%;height:300px;" v-if="this.PersonHonor4 == true"></div><img  src="./../../assets/image/teamAnalysis/developEffect/no-data.png"  v-if="this.PersonHonor4 == false" style="width: 40%;height:200px;margin-top: 50px;"/></div>
              </div>
              <div class="mianportrait" v-if="flagse6==true">
                <div class="biaomian1left"><span  class="biaomiannext1">违纪案件</span></div>
                <div class="biaomian1"><div v-if="this.BreakRuleCase == true" id="getBreakRuleCaseComparison1" style="width: 100%;height:300px;"></div><img  src="./../../assets/image/teamAnalysis/developEffect/no-data.png"  v-if="this.BreakRuleCase == false" style="width: 40%;height:200px;margin-top: 50px;"/></div>
                <div class="biaomian1"><div v-if="this.BreakRuleCase1 == true" id="getBreakRuleCaseComparison2" style="width: 100%;height:300px;"></div><img  src="./../../assets/image/teamAnalysis/developEffect/no-data.png"  v-if="this.BreakRuleCase1 == false" style="width: 40%;height:200px;margin-top: 50px;"/></div>
                <div class="biaomian1" v-if="this.unitIdses.length>2"><div  v-if="this.BreakRuleCase2 == true" id="getBreakRuleCaseComparison3" style="width: 100%;height:300px;"></div><img  src="./../../assets/image/teamAnalysis/developEffect/no-data.png"  v-if="this.BreakRuleCase2 == false" style="width: 40%;height:200px;margin-top: 50px;"/></div>
                <div class="biaomian1" v-if="this.unitIdses.length>3"><div  v-if="this.BreakRuleCase3 == true" id="getBreakRuleCaseComparison4" style="width: 100%;height:300px;"></div><img  src="./../../assets/image/teamAnalysis/developEffect/no-data.png"  v-if="this.BreakRuleCase3 == false" style="width: 40%;height:200px;margin-top: 50px;"/></div>
                <div class="biaomian1" v-if="this.unitIdses.length>4"><div  v-if="this.BreakRuleCase4 == true" id="getBreakRuleCaseComparison5" style="width: 100%;height:300px;"></div><img  src="./../../assets/image/teamAnalysis/developEffect/no-data.png"  v-if="this.BreakRuleCase4 == false" style="width: 40%;height:200px;margin-top: 50px;"/></div>
              </div>
              <div class="mianportrait" v-if="flagse7==true">
                <div class="biaomian1left"><span  class="biaomiannext1">民意满意度</span></div>
                <div class="biaomian1"><div  v-if="this.Satisfied == true" id="getSatisfiedComparison1" style="width: 100%;height:300px;"></div><img  src="./../../assets/image/teamAnalysis/developEffect/no-data.png"  v-if="this.Satisfied == false" style="width: 40%;height:200px;margin-top: 50px;"/></div>
                <div class="biaomian1"><div v-if="this.Satisfied1 == true" id="getSatisfiedComparison2" style="width: 100%;height:300px;"></div><img  src="./../../assets/image/teamAnalysis/developEffect/no-data.png"  v-if="this.Satisfied1 == false" style="width: 40%;height:200px;margin-top: 50px;"/></div>
                <div class="biaomian1" v-if="this.unitIdses.length>2"><div  v-if="this.Satisfied2 == true" id="getSatisfiedComparison3" style="width: 100%;height:300px;"></div><img  src="./../../assets/image/teamAnalysis/developEffect/no-data.png"  v-if="this.Satisfied2 == false" style="width: 40%;height:200px;margin-top: 50px;"/></div>
                <div class="biaomian1" v-if="this.unitIdses.length>3"><div  v-if="this.Satisfied3 == true" id="getSatisfiedComparison4" style="width: 100%;height:300px;"></div><img  src="./../../assets/image/teamAnalysis/developEffect/no-data.png"  v-if="this.Satisfied3 == false" style="width: 40%;height:200px;margin-top: 50px;"/></div>
                <div class="biaomian1" v-if="this.unitIdses.length>4"><div  v-if="this.Satisfied4 == true" id="getSatisfiedComparison5" style="width: 100%;height:300px;"></div><img  src="./../../assets/image/teamAnalysis/developEffect/no-data.png"  v-if="this.Satisfied4 == false" style="width: 40%;height:200px;margin-top: 50px;"/></div>
              </div>
              <div class="mianportrait" v-if="flagse4==true">
                <div class="biaomian1left"><span  class="biaomiannext1">政工绩效</span></div>
                <div class="biaomian1"><div id="getfages1" style="width: 100%;height:300px;" v-if="this.getfagest1 == true"></div><img  src="./../../assets/image/teamAnalysis/developEffect/no-data.png"  v-if="this.getfagest1 == false" style="width: 40%;height:200px;margin-top: 50px;"/></div>
                <div class="biaomian1"><div id="getfages2" style="width: 100%;height:300px;" v-if="this.getfagest2 == true"></div><img  src="./../../assets/image/teamAnalysis/developEffect/no-data.png"  v-if="this.getfagest2 == false" style="width: 40%;height:200px;margin-top: 50px;"/></div>
                <div class="biaomian1" v-if="this.unitIdses.length>2"><div id="getfages3" style="width: 100%;height:300px;" v-if="this.getfagest3 == true"></div><img  src="./../../assets/image/teamAnalysis/developEffect/no-data.png"  v-if="this.getfagest3 == false" style="width: 40%;height:200px;margin-top: 50px;"/></div>
                <div class="biaomian1" v-if="this.unitIdses.length>3"><div id="getfages4" style="width: 100%;height:300px;" v-if="this.getfagest4 == true"></div><img  src="./../../assets/image/teamAnalysis/developEffect/no-data.png"  v-if="this.getfagest4 == false" style="width: 40%;height:200px;margin-top: 50px;"/></div>
                <div class="biaomian1" v-if="this.unitIdses.length>4"><div id="getfages5" style="width: 100%;height:300px;" v-if="this.getfagest5 == true"></div><img  src="./../../assets/image/teamAnalysis/developEffect/no-data.png"  v-if="this.getfagest5 == false" style="width: 40%;height:200px;margin-top: 50px;"/></div>
              </div>
              </div>
            </div>
            <div class="buttonses"><div @click="flgseclick" :duration="2000" type="primary" class="buttonsyles"><span v-if="this.flags== '1'"> <</span></div><div class="fontse" v-if="this.flags== '1'">单位选择</div></div>
          </el-col>
        </el-row>
        <el-dialog width="50%" :title="agetitle" :visible.sync="ageVisible" append-to-body>
          <div class="mianportrait" style="margin-top: 20px;background: #fff;">
          <div style="width:100%;font-size: 16px;height: 600px;" v-if="this.agetitle == '年龄结构'">
            <el-table :data="agedatase" v-loading="loadingse" stripe style="width: 100%">
              <el-table-column align="center" prop="personName" label="姓名" />
              <el-table-column align="center" prop="policeNo" label="警号" />
              <el-table-column align="center" prop="age" label="年龄" />
            </el-table>
            <el-button round @click="downloadse" style="margin: 15px;">下载表格</el-button>
            <el-pagination style="float: right;margin: 20px 40px 0 0;" background :total="totalRecord"
                  :page-size="10" @current-change="handleCurrentChange" :current-page.sync="pageno" layout="total, prev, pager, next">
            </el-pagination> 
          </div>
          <div style="width:100%;font-size: 16px;height: 600px;" v-if="this.agetitle == '教育培训'">
            <el-table :data="agedatase" v-loading="loadingse" stripe style="width: 100%">
              <el-table-column align="center" prop="personName" label="姓名" />
              <el-table-column align="center" prop="policeNo" label="警号" />
              <el-table-column align="center" :show-overflow-tooltip='true' prop="unitName" label="部门" />
              <el-table-column align="center" prop="trainingStartTime" label="培训时间" />
              <el-table-column  align="center" :show-overflow-tooltip='true' prop="trainCourse" label="培训班名称" />
            </el-table>
            <el-button round @click="downloadse" style="margin: 15px;">下载表格</el-button>
            <el-pagination style="float: right;margin: 20px 40px 0 0;" background :total="totalRecord"
                  :page-size="10" @current-change="handleCurrentChange" :current-page.sync="pageno" layout="total, prev, pager, next">
            </el-pagination> 
          </div>
          <div style="width:100%;font-size: 16px;height: 600px;" v-if="this.agetitle == '集体荣誉'">
            <el-table :data="agedatase" v-loading="loadingse" stripe style="width: 100%">
              <el-table-column align="center"  :show-overflow-tooltip='true' prop="honorName" label="表彰称号" />
              <el-table-column align="center"  :show-overflow-tooltip='true' prop="dept" label="部门" />
              <el-table-column align="center" prop="hdate" label="授奖时间" />
              <el-table-column align="center"  :show-overflow-tooltip='true' prop="hdept" label="表彰部门" />
            </el-table>
            <el-button round @click="downloadse" style="margin: 15px;">下载表格</el-button>
            <el-pagination style="float: right;margin: 20px 40px 0 0;" background :total="totalRecord"
                  :page-size="10" @current-change="handleCurrentChange" :current-page.sync="pageno" layout="total, prev, pager, next">
            </el-pagination> 
          </div>
          <div style="width:100%;font-size: 16px;height: 600px;" v-if="this.agetitle == '个人荣誉'">
            <el-table :data="agedatase" v-loading="loadingse" stripe style="width: 100%">
              <el-table-column align="center" prop="personName" label="姓名" />
              <el-table-column align="center" prop="policeNo" label="警号" />
              <el-table-column align="center"  :show-overflow-tooltip='true' prop="awardName" label="表彰称号" />
              <el-table-column align="center" prop="approvalDate" label="授奖时间" />
              <el-table-column align="center"  :show-overflow-tooltip='true' prop="awardWinningUnit" label="表彰部门"/>
            </el-table>
            <el-button round @click="downloadse" style="margin: 15px;">下载表格</el-button>
            <el-pagination style="float: right;margin: 20px 40px 0 0;" background :total="totalRecord"
                  :page-size="10" @current-change="handleCurrentChange" :current-page.sync="pageno" layout="total, prev, pager, next">
            </el-pagination> 
          </div>
          <div style="width:100%;font-size: 16px;height: 600px;" v-if="this.agetitle == '违纪案件'">
            <el-table :data="agedatase" v-loading="loadingse" stripe style="width: 100%">
              <el-table-column align="center" prop="xm" label="姓名" />
              <el-table-column align="center" prop="policeNo" label="警号" />
              <el-table-column align="center"  :show-overflow-tooltip='true' prop="dw" label="单位" />>
              <el-table-column align="center" prop="cfjdsj" label="处分决定时间" />
              <el-table-column align="center"  :show-overflow-tooltip='true' prop="wtxz" label="问题性质" />
            </el-table>
            <el-button round @click="downloadse" style="margin: 15px;">下载表格</el-button>
            <el-pagination style="float: right;margin: 20px 40px 0 0;" background :total="totalRecord"
                  :page-size="10" @current-change="handleCurrentChange" :current-page.sync="pageno" layout="total, prev, pager, next">
            </el-pagination> 
          </div>
          <table  id="tipMessageLabel" v-show="false">
            <tr>
                <th v-if="agetitle !== '集体荣誉'">姓名</th>
                <th v-if="agetitle !== '集体荣誉'">警号</th>
                <th v-if="agetitle == '年龄结构'">年龄</th>
                <th v-if="agetitle == '教育培训'">部门</th>
                <th v-if="agetitle == '教育培训'">培训时间</th>
                <th v-if="agetitle == '教育培训'">培训班名称</th>
                <th v-if="agetitle == '集体荣誉'||agetitle == '个人荣誉'">表彰称号</th>
                <th v-if="agetitle == '集体荣誉'">部门</th>
                <th v-if="agetitle == '集体荣誉'||agetitle == '个人荣誉'">授奖时间</th>
                <th v-if="agetitle == '集体荣誉'||agetitle == '个人荣誉'">表彰部门</th>
                <th v-if="agetitle == '违纪案件'">单位</th>
                <th v-if="agetitle == '违纪案件'">处分决定时间</th>
                <th v-if="agetitle == '违纪案件'">问题性质</th>
            </tr>
            <tr v-for="item in multipleSelection">
                <td v-if="agetitle !== '集体荣誉'&&agetitle !== '违纪案件'">{{item.personName}}</td>
                <td v-if="agetitle !== '集体荣誉'&&agetitle !== '违纪案件'">{{item.policeNo}}</td>
                <td  v-if="agetitle == '年龄结构'">{{item.age}}</td>
                <td v-if="agetitle == '教育培训'">{{item.unitName}}</td>
                <td v-if="agetitle == '教育培训'">{{item.trainingStartTime}}</td>
                <td v-if="agetitle == '教育培训'">{{item.trainCourse}}</td>
                <td  v-if="agetitle == '集体荣誉'">{{item.honorName}}</td>
                <td v-if="agetitle == '集体荣誉'">{{item.dept}}</td>
                <td v-if="agetitle == '集体荣誉'">{{item.hdate}}</td>
                <td v-if="agetitle == '集体荣誉'">{{item.hdept}}</td>
                <td v-if="agetitle == '个人荣誉'">{{item.awardName}}</td>
                <td v-if="agetitle == '个人荣誉'">{{item.approvalDate}}</td>
                <td v-if="agetitle == '个人荣誉'">{{item.awardWinningUnit}}</td>
                <td v-if="agetitle == '违纪案件'">{{item.xm}}</td>
                <td v-if="agetitle == '违纪案件'">{{item.policeNo}}</td>
                <td v-if="agetitle == '违纪案件'">{{item.dw}}</td>
                <td v-if="agetitle == '违纪案件'">{{item.cfjdsj}}</td>
                <td v-if="agetitle == '违纪案件'">{{item.wtxz}}</td>
            </tr>
          </table> 
          </div>
        </el-dialog>
      </div>
      <!-- </transition> -->
    </div>
</template>

<script>
import { Base64 } from "js-base64";
import { getUrlParams } from "@/utils";
import { setTimeout } from "timers";
import "./../../assets/css/animate.css";
export default {
  data: function() {
    return {
      el: "#app",
      flags: "0",
      nextse: "1",
      inputsearchleft: "",
      cities: [
        {
          value: "年龄结构",
          label: "年龄结构",
          imgs: "../../assets/image/policeporlit/年龄1.png"
        },
        {
          value: "集体荣誉",
          label: "集体荣誉",
          imgs: "../../assets/image/policeporlit/企业荣誉.png"
        },
        {
          value: "个人荣誉",
          label: "个人荣誉",
          imgs: "../../assets/image/policeporlit/校园荣誉.png"
        },
        {
          value: "政工绩效",
          label: "政工绩效",
          imgs: "../../assets/image/policeporlit/荣誉表彰.png"
        },
        {
          value: "教育培训",
          label: "教育培训",
          imgs: "../../assets/image/policeporlit/教育11.png"
        },
        {
          value: "违纪案件",
          label: "违纪案件",
          imgs: "../../assets/image/policeporlit/违纪处分.png"
        },
        {
          value: "民意满意度",
          label: "民意满意度",
          imgs: "../../assets/image/policeporlit/年龄6.png"
        }
      ],
      valuecontest: [
        "年龄结构",
        "集体荣誉",
        "个人荣誉",
        "教育培训",
        "违纪案件",
        "民意满意度",
        "政工绩效"
      ],
      getteamdata: [],
      getteamlist: [],
      getteamjb: [],
      getteamjb1: [],
      getteamdatase: [],
      getteamlistse: [],
      getteamdataseold: [],
      getteamlistseold: [],
      getteamjbse: [],
      getteamjbse1: [],
      getteamdatashow: [],
      getteamlistshow: [],
      getteamdatashowse: [],
      getteamdatasel: [],
      getteamdataselold: [],
      valueyear: "2010",
      valueyears: "",
      flagse1: true,
      flagse2: true,
      flagse3: true,
      flagse4: false,
      flagse5: true,
      flagse6: true,
      flagse7: true,
      drawer: false,
      // idNumberse: "320106197408242821",
      // idNumberse: "320114196610232446",
      unitIdses: [],
      // idNumberse: "320114196707282415",
      idNumberse: "",
      yearvalues: "",
      sumer: [],
      sumers: [],
      sumerse: [],
      yearvaluesde: "",
      movelists: [],
      moveuplists: [],
      movelistshows: [],
      drawers: false,
      drawersee: 0,
      falgfj: false,
      falgbm1: false,
      Satisfied: false,
      Satisfied1: false,
      Satisfied2: false,
      Satisfied3: false,
      Satisfied4: false,
      BreakRuleCase: false,
      BreakRuleCase1: false,
      BreakRuleCase2: false,
      BreakRuleCase3: false,
      BreakRuleCase4: false,
      getfagest1: false,
      getfagest2: false,
      getfagest3: false,
      getfagest4: false,
      getfagest5: false,
      PersonHonor: false,
      PersonHonor1: false,
      PersonHonor2: false,
      PersonHonor3: false,
      PersonHonor4: false,
      orghonor: false,
      orghonor1: false,
      orghonor2: false,
      orghonor3: false,
      orghonor4: false,
      Trainse: false,
      Trainse1: false,
      Trainse2: false,
      Trainse3: false,
      Trainse4: false,
      agecomparisonse: false,
      agecomparisonse1: false,
      agecomparisonse2: false,
      agecomparisonse3: false,
      agecomparisonse4: false,
      listsumsjt: [],
      listsumsgr: [],
      agetitle: "年龄结构",
      ageVisible: false,
      loadingse: true,
      totalRecord: 0,
      agedatase: [],
      agedatasese: [],
      multipleSelection: [],
      pageno: 1,
      dataIndex: "",
      unitId: ""
    };
  },
  watch: {},
  mounted() {
    document.querySelector("body").style.height = "auto";
    document.querySelector("html").style.height = "auto";
    const params = getUrlParams(location.hash);
    const idNumber = Base64.decode(params.no || "");
    this.idNumberse = idNumber;
    this.getPersonLog("队伍效能深度较量");
    this.init();
  },
  destroyed() {
    document.querySelector("body").style.height = "100%";
    document.querySelector("html").style.height = "100%";
  },
  methods: {
    //单位选择
    flgseclick() {
      this.flags = "0";
      this.init();
      setTimeout(() => {
        if (this.movelists.length > 1) {
          $(".backnext1").append($("#template").html());
          $(
            ".backnext1>.miannexts>span"
          )[0].innerHTML = this.movelists[0].simpleName;
          $(".backnext2").append($("#template").html());
          $(
            ".backnext2>.miannexts>span"
          )[0].innerHTML = this.movelists[1].simpleName;
          if ($(".backnext1>.miannexts>span")[0].innerHTML.length > 15) {
            $(".backnext1>.miannexts>span").attr(
              "title",
              this.movelists[0].simpleName
            );
          }
          if ($(".backnext2>.miannexts>span")[0].innerHTML.length > 15) {
            $(".backnext2>.miannexts>span").attr(
              "title",
              this.movelists[1].simpleName
            );
          }
        }
        if (this.movelists.length > 2) {
          $(".backnext3").append($("#template").html());
          $(
            ".backnext3>.miannexts>span"
          )[0].innerHTML = this.movelists[2].simpleName;
          if ($(".backnext3>.miannexts>span")[0].innerHTML.length > 15) {
            $(".backnext3>.miannexts>span").attr(
              "title",
              this.movelists[2].simpleName
            );
          }
        }
        if (this.movelists.length > 3) {
          $(".backnext4").append($("#template").html());
          $(
            ".backnext4>.miannexts>span"
          )[0].innerHTML = this.movelists[3].simpleName;
          if ($(".backnext4>.miannexts>span")[0].innerHTML.length > 15) {
            $(".backnext4>.miannexts>span").attr(
              "title",
              this.movelists[3].simpleName
            );
          }
        }
        if (this.movelists.length > 4) {
          $(".backnext5").append($("#template").html());
          $(
            ".backnext5>.miannexts>span"
          )[0].innerHTML = this.movelists[4].simpleName;
          if ($(".backnext5>.miannexts>span")[0].innerHTML.length > 15) {
            $(".backnext5>.miannexts>span").attr(
              "title",
              this.movelists[4].simpleName
            );
          }
        }
      }, 200);
    },
    //单位去除
    checks(parmes) {
      if (parmes == "1") {
        if ($(".backnext1")[0].childNodes.length == 2) {
          this.movelists.forEach((itemsd, i) => {
            if (
              itemsd.simpleName == $(".backnext1>.miannexts>span")[0].innerHTML
            ) {
              this.movelists.splice(i, 1);
            }
          });
          $(".backnext1>.miannexts")[0].parentNode.removeChild(
            $(".backnext1>.miannexts")[0]
          );
        }
      }
      if (parmes == "2") {
        if ($(".backnext2")[0].childNodes.length == 2) {
          this.movelists.forEach((itemsd, i) => {
            if (
              itemsd.simpleName == $(".backnext2>.miannexts>span")[0].innerHTML
            ) {
              this.movelists.splice(i, 1);
            }
          });
          $(".backnext2>.miannexts")[0].parentNode.removeChild(
            $(".backnext2>.miannexts")[0]
          );
        }
      }
      if (parmes == "3") {
        if ($(".backnext3")[0].childNodes.length == 2) {
          this.movelists.forEach((itemsd, i) => {
            if (
              itemsd.simpleName == $(".backnext3>.miannexts>span")[0].innerHTML
            ) {
              this.movelists.splice(i, 1);
            }
          });
          $(".backnext3>.miannexts")[0].parentNode.removeChild(
            $(".backnext3>.miannexts")[0]
          );
        }
      }
      if (parmes == "4") {
        if ($(".backnext4")[0].childNodes.length == 2) {
          this.movelists.forEach((itemsd, i) => {
            if (
              itemsd.simpleName == $(".backnext4>.miannexts>span")[0].innerHTML
            ) {
              this.movelists.splice(i, 1);
            }
          });
          $(".backnext4>.miannexts")[0].parentNode.removeChild(
            $(".backnext4>.miannexts")[0]
          );
        }
      }
      if (parmes == "5") {
        if ($(".backnext5")[0].childNodes.length == 2) {
          this.movelists.forEach((itemsd, i) => {
            if (
              itemsd.simpleName == $(".backnext5>.miannexts>span")[0].innerHTML
            ) {
              this.movelists.splice(i, 1);
            }
          });
          $(".backnext5>.miannexts")[0].parentNode.removeChild(
            $(".backnext5>.miannexts")[0]
          );
        }
      }
      console.log(this.movelists);
    },
    //单位拖动
    move(item, event, itemse) {
      var items = $(".miannext")[item];
      var disx = event.pageX;
      var disy = event.pageY;
      var _this = this;
      let kese = false;
      _this.movelists.forEach((itemsds, i) => {
        if (itemsds.simpleName == itemse.simpleName) {
          kese = true;
        }
      });
      document.onmousemove = function(e) {
        _this.drawersee = 1;
        _this.drawers = true;
        items.style.left = e.pageX - disx + "px";
        items.style.top = e.pageY - disy + "px";
      };
      document.onmouseup = function(e) {
        if (_this.drawersee == 1) {
          if (kese == false) {
            var lefts = e.pageX,
              tops = e.pageY;
            // var topse = Number(tops.replace(/px/g, "")),
            //   leftse = Number(lefts.replace(/px/g, ""));
            if (lefts > 755 && tops > 155 && (lefts < 855 && tops < 255)) {
              if ($(".backnext1")[0].childNodes.length == 1) {
                $(".backnext1").append($("#template").html());
              } else {
                _this.movelists.forEach((itemsd, i) => {
                  if (
                    itemsd.simpleName ==
                    $(".backnext1>.miannexts>span")[0].innerHTML
                  ) {
                    _this.movelists.splice(i, 1);
                  }
                });
              }
              $(".backnext1>.miannexts>span")[0].innerHTML = itemse.simpleName;
              if ($(".backnext1>.miannexts>span")[0].innerHTML.length > 15) {
                $(".backnext1>.miannexts>span").attr(
                  "title",
                  itemse.simpleName
                );
              }
              _this.movelists.push(itemse);
            }
            if (lefts > 1365 && tops > 155 && (lefts < 1465 && tops < 255)) {
              if ($(".backnext2")[0].childNodes.length == 1) {
                $(".backnext2").append($("#template").html());
              } else {
                _this.movelists.forEach((itemsd, i) => {
                  if (
                    itemsd.simpleName ==
                    $(".backnext2>.miannexts>span")[0].innerHTML
                  ) {
                    _this.movelists.splice(i, 1);
                  }
                });
              }
              $(".backnext2>.miannexts>span")[0].innerHTML = itemse.simpleName;
              if ($(".backnext2>.miannexts>span")[0].innerHTML.length > 15) {
                $(".backnext2>.miannexts>span").attr(
                  "title",
                  itemse.simpleName
                );
              }
              _this.movelists.push(itemse);
            }
            if (lefts > 715 && tops > 475 && (lefts < 815 && tops < 575)) {
              if ($(".backnext3")[0].childNodes.length == 1) {
                $(".backnext3").append($("#template").html());
              } else {
                _this.movelists.forEach((itemsd, i) => {
                  if (
                    itemsd.simpleName ==
                    $(".backnext3>.miannexts>span")[0].innerHTML
                  ) {
                    _this.movelists.splice(i, 1);
                  }
                });
              }
              $(".backnext3>.miannexts>span")[0].innerHTML = itemse.simpleName;
              if ($(".backnext3>.miannexts>span")[0].innerHTML.length > 15) {
                $(".backnext3>.miannexts>span").attr(
                  "title",
                  itemse.simpleName
                );
              }
              _this.movelists.push(itemse);
            }
            if (lefts > 1420 && tops > 475 && (lefts < 1520 && tops < 575)) {
              if ($(".backnext4")[0].childNodes.length == 1) {
                $(".backnext4").append($("#template").html());
              } else {
                _this.movelists.forEach((itemsd, i) => {
                  if (
                    itemsd.simpleName ==
                    $(".backnext4>.miannexts>span")[0].innerHTML
                  ) {
                    _this.movelists.splice(i, 1);
                  }
                });
              }
              $(".backnext4>.miannexts>span")[0].innerHTML = itemse.simpleName;
              if ($(".backnext4>.miannexts>span")[0].innerHTML.length > 15) {
                $(".backnext4>.miannexts>span").attr(
                  "title",
                  itemse.simpleName
                );
              }
              _this.movelists.push(itemse);
            }
            if (lefts > 1065 && tops > 655 && (lefts < 1165 && tops < 755)) {
              if ($(".backnext5")[0].childNodes.length == 1) {
                $(".backnext5").append($("#template").html());
              } else {
                _this.movelists.forEach((itemsd, i) => {
                  if (
                    itemsd.simpleName ==
                    $(".backnext5>.miannexts>span")[0].innerHTML
                  ) {
                    _this.movelists.splice(i, 1);
                  }
                });
              }
              $(".backnext5>.miannexts>span")[0].innerHTML = itemse.simpleName;
              if ($(".backnext5>.miannexts>span")[0].innerHTML.length > 15) {
                $(".backnext5>.miannexts>span").attr(
                  "title",
                  itemse.simpleName
                );
              }
              _this.movelists.push(itemse);
            }
          } else {
            _this.$message("该单位已被选择！");
          }
          items.style.left = "0px";
          items.style.top = "0px";
          setTimeout(() => {
            _this.drawers = false;
          }, 300);
          _this.drawersee = 0;
        }
        document.onmousemove = document.onmouseup = null;
      };
    },
    //市局机关以及分局的切换
    leftclick(parem) {
      this.inputsearchleft = "";
      let unitAgeCounts = document.querySelectorAll(".headernextcolor");
      if (parem == "市局机关") {
        //根据索引数设置样式
        for (let kss = 0; kss < unitAgeCounts.length; kss++) {
          if (kss == 0) {
            unitAgeCounts[kss].classList.add("nextbottom");
            this.flag = true;
          } else {
            unitAgeCounts[kss].classList.remove("nextbottom");
          }
        }
        this.nextse = "1";
        setTimeout(() => {
          this.sumerclick(0);
        }, 300);
      } else {
        //根据索引数设置样式
        for (let kss = 0; kss < unitAgeCounts.length; kss++) {
          if (this.falgbm1 == false) {
            if (kss == 0) {
              unitAgeCounts[kss].classList.add("nextbottom");
              this.flag = true;
            } else {
              unitAgeCounts[kss].classList.remove("nextbottom");
            }
          } else {
            if (kss == 1) {
              unitAgeCounts[kss].classList.add("nextbottom");
              this.flag = true;
            } else {
              unitAgeCounts[kss].classList.remove("nextbottom");
            }
          }
        }
        this.nextse = "2";
        setTimeout(() => {
          this.sumerclickes(0);
        }, 300);
      }
    },
    //输入框搜索
    slecktssearch() {
      let itrms = this.inputsearchleft;
      if (this.nextse == "2") {
        var teamdatase = this.getteamdataseold;
        this.getteamdatase = [];
        teamdatase.forEach(items => {
          if (items.simpleName.indexOf(this.inputsearchleft) > -1) {
            this.getteamdatase.push(items);
          }
        });
        if (this.getteamdatase.length == 0) {
          this.$message("没有该部门！");
          this.getteamdatase = this.getteamdataseold;
        }
        let sums = Math.ceil(this.getteamdatase.length / 15);
        this.sumers = [];
        for (let k = 0; k < sums; k++) {
          this.sumers.push(k);
        }
      } else if (this.nextse == "1") {
        var teamdlistse = this.getteamlistseold;
        this.getteamlistse = [];
        teamdlistse.forEach(items => {
          if (items.simpleName.indexOf(this.inputsearchleft) > -1) {
            this.getteamlistse.push(items);
          }
        });
        if (this.getteamlistse.length == 0) {
          this.$message("没有该部门！");
          this.getteamlistse = this.getteamlistseold;
        }
        let sum = Math.ceil(this.getteamlistse.length / 15);
        this.sumer = [];
        for (let i = 0; i < sum; i++) {
          this.sumer.push(i);
        }
      } else if (this.nextse == "3") {
        var teamdatase = this.getteamdataselold;
        this.getteamdatasel = [];
        teamdatase.forEach(items => {
          if (items.simpleName.indexOf(this.inputsearchleft) > -1) {
            this.getteamdatasel.push(items);
          }
        });
        if (this.getteamdatasel.length == 0) {
          this.$message("没有该部门！");
          this.getteamdatasel = this.getteamdataselold;
        }
        let sumss = Math.ceil(this.getteamdatasel.length / 15);
        this.sumerse = [];
        for (let i = 0; i < sumss; i++) {
          this.sumerse.push(i);
        }
      }
      let _this = this;
      setTimeout(() => {
        if (this.nextse == "1") {
          _this.sumerclickes(0);
          this.leftclick("市局机关");
        } else if (this.nextse == "2") {
          _this.sumerclick(0);
          this.leftclick("分局");
        } else if (this.nextse == "3") {
          _this.sumerseclickes(0);
        }
        this.inputsearchleft = itrms;
      }, 300);
    },
    //开始年度
    contrastclick() {
      this.valueyears = "";
    },
    //结束年度
    contrastdata() {
      if (this.valueyears < this.valueyear) {
        this.valueyears = "";
        this.$message("结束年度不可小于开始年度！");
      } else {
        this.getecharts();
        this.contonttclick();
        this.contonttclickde();
        this.getfagesclick();
      }
    },
    //判断展示的区域
    clickconttest() {
      this.flagse1 = false;
      this.flagse2 = false;
      this.flagse3 = false;
      this.flagse4 = false;
      this.flagse5 = false;
      this.flagse6 = false;
      this.flagse7 = false;
      this.valuecontest.forEach(items => {
        if (items == "年龄结构") {
          this.flagse1 = true;
        } else if (items == "集体荣誉") {
          this.flagse2 = true;
        } else if (items == "个人荣誉") {
          this.flagse3 = true;
        } else if (items == "政工绩效") {
          this.flagse4 = true;
        } else if (items == "教育培训") {
          this.flagse5 = true;
        } else if (items == "违纪案件") {
          this.flagse6 = true;
        } else if (items == "民意满意度") {
          this.flagse7 = true;
        }
      });
      setTimeout(() => {
        let biaomian = document.querySelectorAll(".biaomian");
        //根据索引数设置样式
        for (let kss = 0; kss < biaomian.length; kss++) {
          if (this.movelists.length == 2) {
            biaomian[kss].classList.add("widths");
            biaomian[kss].classList.remove("widthse");
            biaomian[kss].classList.remove("widthses");
          }
          if (this.movelists.length == 3) {
            biaomian[kss].classList.add("widthse");
            biaomian[kss].classList.remove("widths");
            biaomian[kss].classList.remove("widthses");
          }
          if (this.movelists.length == 4) {
            biaomian[kss].classList.add("widthses");
            biaomian[kss].classList.remove("widths");
            biaomian[kss].classList.remove("widthse");
          }
          if (this.movelists.length == 5) {
            biaomian[kss].classList.remove("widthses");
            biaomian[kss].classList.remove("widths");
            biaomian[kss].classList.remove("widthse");
          }
        }
        let biaomianss = document.querySelectorAll(".biaomian1");
        //根据索引数设置样式
        for (let ke = 0; ke < biaomianss.length; ke++) {
          if (this.movelists.length == 2) {
            biaomianss[ke].classList.add("widths");
            biaomianss[ke].classList.remove("widthse");
            biaomianss[ke].classList.remove("widthses");
          }
          if (this.movelists.length == 3) {
            biaomianss[ke].classList.add("widthse");
            biaomianss[ke].classList.remove("widths");
            biaomianss[ke].classList.remove("widthses");
          }
          if (this.movelists.length == 4) {
            biaomianss[ke].classList.add("widthses");
            biaomianss[ke].classList.remove("widths");
            biaomianss[ke].classList.remove("widthse");
          }
          if (this.movelists.length == 5) {
            biaomianss[ke].classList.remove("widthses");
            biaomianss[ke].classList.remove("widths");
            biaomianss[ke].classList.remove("widthse");
          }
        }
        this.getecharts();
        this.contonttclick();
        this.contonttclickde();
        this.getfagesclick();
      }, 300);
    },
    //单位数据查询
    init() {
      this.$axios
        .get("/teamComparison/getTeamInfo?idNumber=" + this.idNumberse)
        .then(res => {
          if (res.code == 0) {
            res.data.role.forEach(items => {
              if (items == "fj") {
                this.falgfj = true;
              }
              if (items == "bm1") {
                this.falgbm1 = true;
              }
              if (items == "bm2") {
                this.nextse = "3";
              }
            });
            if (res.data.fj) {
              this.getteamdata = res.data.fj; //分局数据
              this.getteamdatase = res.data.fj; //分局数据1
              this.getteamdataseold = res.data.fj;
              let sums = Math.ceil(this.getteamdatase.length / 15);
              this.sumers = [];
              for (let k = 0; k < sums; k++) {
                this.sumers.push(k);
              }
            }
            if (res.data.bm1) {
              this.getteamlist = res.data.bm1; //市局机关数据
              this.getteamlistse = res.data.bm1; //市局机关数据1
              this.getteamlistseold = res.data.bm1;
              let sum = Math.ceil(this.getteamlistse.length / 15);
              this.sumer = [];
              for (let i = 0; i < sum; i++) {
                this.sumer.push(i);
              }
            }
            if (res.data.bm2) {
              this.getteamdatasel = res.data.bm2; //市局机关数据
              this.getteamdataselold = res.data.bm2;
              let sumss = Math.ceil(this.getteamdatasel.length / 15);
              this.sumerse = [];
              for (let i = 0; i < sumss; i++) {
                this.sumerse.push(i);
              }
            }
            this.getteamjb = res.data.role;
            this.getteamjb1 = res.data.role;
            this.getteamjbse = res.data.role;
            this.getteamjbse1 = res.data.role;
            let _this = this;
            setTimeout(() => {
              if (this.nextse == "3") {
                _this.sumerseclickes(0);
              } else {
                _this.sumerclick(0);
                _this.sumerclickes(0);
                setTimeout(() => {
                  if (this.falgbm1 == false) {
                    _this.leftclick("分局");
                  } else {
                    _this.leftclick("市局机关");
                  }
                }, 300);
              }
            }, 300);
          } else {
            this.$message(res.message);
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    //第二级单位数据
    getChildUnit(parems, timer) {
      console.log(this.drawers);
      if (this.drawers == false && parems.isZoom == true) {
        let unitIds = parems.id;
        this.$axios
          .get("/teamComparison/getChildUnit?unitId=" + unitIds)
          .then(res => {
            if (res.code == 0) {
              if (timer == "分局") {
                this.getteamdatashow = [];
                this.getteamdatase = res.data.bm2; //派出所数据1
                this.getteamdataseold = res.data.bm2;
                this.getteamjbse1 = res.data.role;
                let sums = Math.ceil(this.getteamdatase.length / 15);
                this.sumers = [];
                for (let k = 0; k < sums; k++) {
                  this.sumers.push(k);
                }
              } else if (timer == "市局机关") {
                this.getteamlistshow = [];
                this.getteamlistse = res.data.bm2; //市局机关下级数据1
                this.getteamlistseold = res.data.bm2;
                this.getteamjbse = res.data.role;
                let sum = Math.ceil(this.getteamlistse.length / 15);
                this.sumer = [];
                for (let i = 0; i < sum; i++) {
                  this.sumer.push(i);
                }
              }
              let _this = this;
              setTimeout(() => {
                if (timer == "分局") {
                  _this.sumerclickes(0);
                } else {
                  _this.sumerclick(0);
                }
              }, 300);
            } else {
              this.$message(res.message);
            }
          })
          .catch(function(error) {
            console.log(error);
          });
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
    //echarts接口
    getecharts() {
      if (this.valuecontest.length > 0) {
        let unitIdse = this.unitIdses;
        if (this.flagse1 == true) {
          this.agecomparisonse = false;
          this.agecomparisonse1 = false;
          this.agecomparisonse2 = false;
          this.agecomparisonse3 = false;
          this.agecomparisonse4 = false;
          this.$axios
            .post("/teamComparison/getAgeComparison", {
              endYear: this.valueyears,
              startYear: this.valueyear,
              type: "",
              unitId: unitIdse,
              year: ""
            })
            .then(res => {
              if (res.code == 0) {
                for (let k = 0; k < res.data.length; k++) {
                  if (
                    this.moveuplists[0] &&
                    this.moveuplists[0].teamName == res.data[k].name
                  ) {
                    this.agecomparisonse = true;
                  }
                  if (
                    this.moveuplists[1] &&
                    this.moveuplists[1].teamName == res.data[k].name
                  ) {
                    this.agecomparisonse1 = true;
                  }
                  if (
                    this.moveuplists[2] &&
                    this.moveuplists[2].teamName == res.data[k].name
                  ) {
                    this.agecomparisonse2 = true;
                  }
                  if (
                    this.moveuplists[3] &&
                    this.moveuplists[3].teamName == res.data[k].name
                  ) {
                    this.agecomparisonse3 = true;
                  }
                  if (
                    this.moveuplists[4] &&
                    this.moveuplists[4].teamName == res.data[k].name
                  ) {
                    this.agecomparisonse4 = true;
                  }
                }
                setTimeout(() => {
                  if (this.agecomparisonse == true) {
                    var myChartAgeComparison1 = this.$echarts.init(
                      document.getElementById("agecomparison1")
                    );
                  }
                  if (this.agecomparisonse1 == true) {
                    var myChartAgeComparison2 = this.$echarts.init(
                      document.getElementById("agecomparison2")
                    );
                  }
                  if (
                    this.unitIdses.length > 2 &&
                    this.agecomparisonse2 == true
                  ) {
                    var myChartAgeComparison3 = this.$echarts.init(
                      document.getElementById("agecomparison3")
                    );
                  }
                  if (
                    this.unitIdses.length > 3 &&
                    this.agecomparisonse3 == true
                  ) {
                    var myChartAgeComparison4 = this.$echarts.init(
                      document.getElementById("agecomparison4")
                    );
                  }
                  if (
                    this.unitIdses.length > 4 &&
                    this.agecomparisonse4 == true
                  ) {
                    var myChartAgeComparison5 = this.$echarts.init(
                      document.getElementById("agecomparison5")
                    );
                  }
                  let colorlists = ["#2ec6c8", "#b7a3de", "#5ab1ef"];
                  for (let k = 0; k < res.data.length; k++) {
                    let seriesData = [];
                    res.data[k].chart.forEach((items, i) => {
                      seriesData.push({
                        value: items.value,
                        name: items.name,
                        itemStyle: {
                          color: colorlists[i]
                        }
                      });
                    });
                    let optionagecomparison = {
                      tooltip: {
                        trigger: "item",
                        formatter: "{b} : {c} ({d}%)"
                      },
                      series: [
                        {
                          name: "",
                          type: "pie",
                          radius: "80%",
                          center: ["50%", "50%"],
                          selectedMode: "single",
                          minAngle: 10,
                          label: {
                            position: "inner",
                            formatter: "{b}：{c}人 \n {d}% "
                            // rotate:45
                          },
                          labelLine: {
                            show: false
                          },
                          data: seriesData,
                          emphasis: {
                            itemStyle: {
                              shadowBlur: 10,
                              shadowOffsetX: 0,
                              shadowColor: "rgba(0, 0, 0, 0.5)"
                            }
                          }
                        }
                      ]
                    };
                    if (
                      this.moveuplists[0] &&
                      this.moveuplists[0].teamName == res.data[k].name
                    ) {
                      myChartAgeComparison1.setOption(
                        optionagecomparison,
                        true
                      );
                      let _this = this;
                      myChartAgeComparison1.on("click", function(params11) {
                        _this.dolaefunction(
                          "年龄结构",
                          _this.moveuplists[0].id,
                          params11
                        );
                      });
                    }
                    if (
                      this.moveuplists[1] &&
                      this.moveuplists[1].teamName == res.data[k].name
                    ) {
                      myChartAgeComparison2.setOption(
                        optionagecomparison,
                        true
                      );
                      let _this = this;
                      myChartAgeComparison2.on("click", function(params11) {
                        _this.dolaefunction(
                          "年龄结构",
                          _this.moveuplists[1].id,
                          params11
                        );
                      });
                    }
                    if (
                      this.moveuplists[2] &&
                      this.moveuplists[2].teamName == res.data[k].name
                    ) {
                      myChartAgeComparison3.setOption(
                        optionagecomparison,
                        true
                      );
                      let _this = this;
                      myChartAgeComparison3.on("click", function(params11) {
                        _this.dolaefunction(
                          "年龄结构",
                          _this.moveuplists[2].id,
                          params11
                        );
                      });
                    }
                    if (
                      this.moveuplists[3] &&
                      this.moveuplists[3].teamName == res.data[k].name
                    ) {
                      myChartAgeComparison4.setOption(
                        optionagecomparison,
                        true
                      );
                      let _this = this;
                      myChartAgeComparison4.on("click", function(params11) {
                        _this.dolaefunction(
                          "年龄结构",
                          _this.moveuplists[3].id,
                          params11
                        );
                      });
                    }
                    if (
                      this.moveuplists[4] &&
                      this.moveuplists[4].teamName == res.data[k].name
                    ) {
                      myChartAgeComparison5.setOption(
                        optionagecomparison,
                        true
                      );
                      let _this = this;
                      myChartAgeComparison5.on("click", function(params11) {
                        _this.dolaefunction(
                          "年龄结构",
                          _this.moveuplists[4].id,
                          params11
                        );
                      });
                    }
                  }
                }, 300);
              } else {
                this.$message(res.message);
              }
            })
            .catch(function(error) {
              console.log(error);
            });
        }
        if (this.flagse5 == true) {
          this.Trainse = false;
          this.Trainse1 = false;
          this.Trainse2 = false;
          this.Trainse3 = false;
          this.Trainse4 = false;
          this.$axios
            .post("/teamComparison/getTrainComparison", {
              endYear: this.valueyears,
              startYear: this.valueyear,
              type: "",
              unitId: unitIdse,
              year: ""
            })
            .then(res => {
              if (res.code == 0) {
                for (let k = 0; k < res.data.length; k++) {
                  if (
                    this.moveuplists[0] &&
                    this.moveuplists[0].teamName == res.data[k].name
                  ) {
                    this.Trainse = true;
                  }
                  if (
                    this.moveuplists[1] &&
                    this.moveuplists[1].teamName == res.data[k].name
                  ) {
                    this.Trainse1 = true;
                  }
                  if (
                    this.moveuplists[2] &&
                    this.moveuplists[2].teamName == res.data[k].name
                  ) {
                    this.Trainse2 = true;
                  }
                  if (
                    this.moveuplists[3] &&
                    this.moveuplists[3].teamName == res.data[k].name
                  ) {
                    this.Trainse3 = true;
                  }
                  if (
                    this.moveuplists[4] &&
                    this.moveuplists[4].teamName == res.data[k].name
                  ) {
                    this.Trainse4 = true;
                  }
                }
                setTimeout(() => {
                  if (this.Trainse == true) {
                    var myChartgetTrainComparison1 = this.$echarts.init(
                      document.getElementById("getTrainComparisonse1")
                    );
                  }
                  if (this.Trainse1 == true) {
                    var myChartgetTrainComparison2 = this.$echarts.init(
                      document.getElementById("getTrainComparisonse2")
                    );
                  }
                  if (this.unitIdses.length > 2 && this.Trainse2 == true) {
                    var myChartgetTrainComparison3 = this.$echarts.init(
                      document.getElementById("getTrainComparisonse3")
                    );
                  }
                  if (this.unitIdses.length > 3 && this.Trainse3 == true) {
                    var myChartgetTrainComparison4 = this.$echarts.init(
                      document.getElementById("getTrainComparisonse4")
                    );
                  }
                  if (this.unitIdses.length > 4 && this.Trainse4 == true) {
                    var myChartgetTrainComparison5 = this.$echarts.init(
                      document.getElementById("getTrainComparisonse5")
                    );
                  }
                  for (let k = 0; k < res.data.length; k++) {
                    let seriesData = [];
                    let xAxisdata = [];
                    res.data[k].chart.forEach(items => {
                      xAxisdata.push(items.name);
                      seriesData.push(items.value);
                    });
                    let optionTrainComparison = {
                      tooltip: {
                        trigger: "axis"
                      },
                      xAxis: {
                        type: "category",
                        boundaryGap: false,
                        data: xAxisdata
                      },
                      yAxis: {
                        type: "value",
                        axisLabel: {
                          formatter: "{value}"
                        }
                      },
                      series: [
                        {
                          name: "培训次数",
                          type: "line",
                          data: seriesData,
                          color: "#2ec7c9",
                          markPoint: {
                            data: [
                              { type: "max", name: "最大值" },
                              { type: "min", name: "最小值" }
                            ]
                          },
                          markLine: {
                            data: [{ type: "average", name: "平均值" }]
                          }
                        }
                      ]
                    };
                    if (
                      this.moveuplists[0] &&
                      this.moveuplists[0].teamName == res.data[k].name
                    ) {
                      myChartgetTrainComparison1.setOption(
                        optionTrainComparison,
                        true
                      );
                      let _this = this;
                      myChartgetTrainComparison1.on("click", function(
                        params11
                      ) {
                        _this.dolaefunction(
                          "教育培训",
                          _this.moveuplists[0].id,
                          params11
                        );
                      });
                    }
                    if (
                      this.moveuplists[1] &&
                      this.moveuplists[1].teamName == res.data[k].name
                    ) {
                      myChartgetTrainComparison2.setOption(
                        optionTrainComparison,
                        true
                      );
                      let _this = this;
                      myChartgetTrainComparison2.on("click", function(
                        params11
                      ) {
                        _this.dolaefunction(
                          "教育培训",
                          _this.moveuplists[1].id,
                          params11
                        );
                      });
                    }
                    if (
                      this.moveuplists[2] &&
                      this.moveuplists[2].teamName == res.data[k].name
                    ) {
                      myChartgetTrainComparison3.setOption(
                        optionTrainComparison,
                        true
                      );
                      let _this = this;
                      myChartgetTrainComparison3.on("click", function(
                        params11
                      ) {
                        _this.dolaefunction(
                          "教育培训",
                          _this.moveuplists[2].id,
                          params11
                        );
                      });
                    }
                    if (
                      this.moveuplists[3] &&
                      this.moveuplists[3].teamName == res.data[k].name
                    ) {
                      myChartgetTrainComparison4.setOption(
                        optionTrainComparison,
                        true
                      );
                      let _this = this;
                      myChartgetTrainComparison4.on("click", function(
                        params11
                      ) {
                        _this.dolaefunction(
                          "教育培训",
                          _this.moveuplists[3].id,
                          params11
                        );
                      });
                    }
                    if (
                      this.moveuplists[4] &&
                      this.moveuplists[4].teamName == res.data[k].name
                    ) {
                      myChartgetTrainComparison5.setOption(
                        optionTrainComparison,
                        true
                      );
                      let _this = this;
                      myChartgetTrainComparison5.on("click", function(
                        params11
                      ) {
                        _this.dolaefunction(
                          "教育培训",
                          _this.moveuplists[4].id,
                          params11
                        );
                      });
                    }
                  }
                }, 300);
              } else {
                this.$message(res.message);
              }
            })
            .catch(function(error) {
              console.log(error);
            });
        }
        if (this.flagse2 == true) {
          this.orghonor = false;
          this.orghonor1 = false;
          this.orghonor2 = false;
          this.orghonor3 = false;
          this.orghonor4 = false;
          this.$axios
            .post("/teamComparison/getOrgHonorComparison", {
              endYear: this.valueyears,
              startYear: this.valueyear,
              type: "",
              unitId: unitIdse,
              year: ""
            })
            .then(res => {
              if (res.code == 0) {
                for (let k = 0; k < res.data.length; k++) {
                  let namese = "";
                  for (let items in res.data[k]) {
                    namese = items;
                  }
                  if (
                    this.moveuplists[0] &&
                    this.moveuplists[0].teamName == namese
                  ) {
                    this.orghonor = true;
                  }
                  if (
                    this.moveuplists[1] &&
                    this.moveuplists[1].teamName == namese
                  ) {
                    this.orghonor1 = true;
                  }
                  if (
                    this.moveuplists[2] &&
                    this.moveuplists[2].teamName == namese
                  ) {
                    this.orghonor2 = true;
                  }
                  if (
                    this.moveuplists[3] &&
                    this.moveuplists[3].teamName == namese
                  ) {
                    this.orghonor3 = true;
                  }
                  if (
                    this.moveuplists[4] &&
                    this.moveuplists[4].teamName == namese
                  ) {
                    this.orghonor4 = true;
                  }
                }
                setTimeout(() => {
                  if (this.orghonor == true) {
                    var myChartgetOrgHonorComparison1 = this.$echarts.init(
                      document.getElementById("getOrgHonorComparison1")
                    );
                  }
                  if (this.orghonor1 == true) {
                    var myChartgetOrgHonorComparison2 = this.$echarts.init(
                      document.getElementById("getOrgHonorComparison2")
                    );
                  }
                  if (this.unitIdses.length > 2 && this.orghonor2 == true) {
                    var myChartgetOrgHonorComparison3 = this.$echarts.init(
                      document.getElementById("getOrgHonorComparison3")
                    );
                  }
                  if (this.unitIdses.length > 3 && this.orghonor3 == true) {
                    var myChartgetOrgHonorComparison4 = this.$echarts.init(
                      document.getElementById("getOrgHonorComparison4")
                    );
                  }
                  if (this.unitIdses.length > 4 && this.orghonor4 == true) {
                    var myChartgetOrgHonorComparison5 = this.$echarts.init(
                      document.getElementById("getOrgHonorComparison5")
                    );
                  }
                  for (let k = 0; k < res.data.length; k++) {
                    let seriesData = [];
                    let xAxisdata = [];
                    let namese = "";
                    for (let items in res.data[k]) {
                      namese = items;
                      res.data[k][items].trainCountVoList.forEach(itemse => {
                        xAxisdata.push(itemse.year);
                        seriesData.push(itemse.count);
                      });
                    }
                    let optionTrainComparison = {
                      tooltip: {
                        trigger: "axis"
                      },
                      calculable: true,
                      xAxis: [
                        {
                          type: "category",
                          data: xAxisdata
                        }
                      ],
                      yAxis: [
                        {
                          type: "value"
                        }
                      ],
                      series: [
                        {
                          name: "集体荣誉",
                          type: "bar",
                          color: "#b7a3de",
                          data: seriesData,
                          markPoint: {
                            data: [
                              { type: "max", name: "最大值" },
                              { type: "min", name: "最小值" }
                            ]
                          },
                          markLine: {
                            data: [{ type: "average", name: "平均值" }]
                          }
                        }
                      ]
                    };
                    if (
                      this.moveuplists[0] &&
                      this.moveuplists[0].teamName == namese
                    ) {
                      myChartgetOrgHonorComparison1.setOption(
                        optionTrainComparison,
                        true
                      );
                      let _this = this;
                      myChartgetOrgHonorComparison1.on("click", function(
                        params11
                      ) {
                        _this.dolaefunction(
                          "集体荣誉",
                          _this.moveuplists[0].id,
                          params11
                        );
                      });
                    }
                    if (
                      this.moveuplists[1] &&
                      this.moveuplists[1].teamName == namese
                    ) {
                      myChartgetOrgHonorComparison2.setOption(
                        optionTrainComparison,
                        true
                      );
                      let _this = this;
                      myChartgetOrgHonorComparison2.on("click", function(
                        params11
                      ) {
                        _this.dolaefunction(
                          "集体荣誉",
                          _this.moveuplists[1].id,
                          params11
                        );
                      });
                    }
                    if (
                      this.moveuplists[2] &&
                      this.moveuplists[2].teamName == namese
                    ) {
                      myChartgetOrgHonorComparison3.setOption(
                        optionTrainComparison,
                        true
                      );
                      let _this = this;
                      myChartgetOrgHonorComparison3.on("click", function(
                        params11
                      ) {
                        _this.dolaefunction(
                          "集体荣誉",
                          _this.moveuplists[2].id,
                          params11
                        );
                      });
                    }
                    if (
                      this.moveuplists[3] &&
                      this.moveuplists[3].teamName == namese
                    ) {
                      myChartgetOrgHonorComparison4.setOption(
                        optionTrainComparison,
                        true
                      );
                      let _this = this;
                      myChartgetOrgHonorComparison4.on("click", function(
                        params11
                      ) {
                        _this.dolaefunction(
                          "集体荣誉",
                          _this.moveuplists[3].id,
                          params11
                        );
                      });
                    }
                    if (
                      this.moveuplists[4] &&
                      this.moveuplists[4].teamName == namese
                    ) {
                      myChartgetOrgHonorComparison5.setOption(
                        optionTrainComparison,
                        true
                      );
                      let _this = this;
                      myChartgetOrgHonorComparison5.on("click", function(
                        params11
                      ) {
                        _this.dolaefunction(
                          "集体荣誉",
                          _this.moveuplists[4].id,
                          params11
                        );
                      });
                    }
                  }
                }, 300);
              } else {
                this.$message(res.message);
              }
            })
            .catch(function(error) {
              console.log(error);
            });
        }
        if (this.flagse3 == true) {
          this.PersonHonor = false;
          this.PersonHonor1 = false;
          this.PersonHonor2 = false;
          this.PersonHonor3 = false;
          this.PersonHonor4 = false;
          this.$axios
            .post("/teamComparison/getPersonHonorComparison", {
              endYear: this.valueyears,
              startYear: this.valueyear,
              type: "",
              unitId: unitIdse,
              year: ""
            })
            .then(res => {
              if (res.code == 0) {
                for (let k = 0; k < res.data.length; k++) {
                  let namese = "";
                  for (let items in res.data[k]) {
                    namese = items;
                  }
                  if (
                    this.moveuplists[0] &&
                    this.moveuplists[0].teamName == namese
                  ) {
                    this.PersonHonor = true;
                  }
                  if (
                    this.moveuplists[1] &&
                    this.moveuplists[1].teamName == namese
                  ) {
                    this.PersonHonor1 = true;
                  }
                  if (
                    this.moveuplists[2] &&
                    this.moveuplists[2].teamName == namese
                  ) {
                    this.PersonHonor2 = true;
                  }
                  if (
                    this.moveuplists[3] &&
                    this.moveuplists[3].teamName == namese
                  ) {
                    this.PersonHonor3 = true;
                  }
                  if (
                    this.moveuplists[4] &&
                    this.moveuplists[4].teamName == namese
                  ) {
                    this.PersonHonor4 = true;
                  }
                }
                setTimeout(() => {
                  if (this.PersonHonor == true) {
                    var myChartgetPersonHonorComparison1 = this.$echarts.init(
                      document.getElementById("getPersonHonorComparison1")
                    );
                  }
                  if (this.PersonHonor1 == true) {
                    var myChartgetPersonHonorComparison2 = this.$echarts.init(
                      document.getElementById("getPersonHonorComparison2")
                    );
                  }
                  if (this.unitIdses.length > 2 && this.PersonHonor2 == true) {
                    var myChartgetPersonHonorComparison3 = this.$echarts.init(
                      document.getElementById("getPersonHonorComparison3")
                    );
                  }
                  if (this.unitIdses.length > 3 && this.PersonHonor3 == true) {
                    var myChartgetPersonHonorComparison4 = this.$echarts.init(
                      document.getElementById("getPersonHonorComparison4")
                    );
                  }
                  if (this.unitIdses.length > 4 && this.PersonHonor4 == true) {
                    var myChartgetPersonHonorComparison5 = this.$echarts.init(
                      document.getElementById("getPersonHonorComparison5")
                    );
                  }
                  for (let k = 0; k < res.data.length; k++) {
                    let seriesData = [];
                    let xAxisdata = [];
                    let namese = "";
                    for (let items in res.data[k]) {
                      namese = items;
                      res.data[k][items].trainCountVoList.forEach(itemse => {
                        xAxisdata.push(itemse.year);
                        seriesData.push(itemse.count);
                      });
                    }
                    let optionTrainComparison = {
                      tooltip: {
                        trigger: "axis"
                      },
                      calculable: true,
                      xAxis: [
                        {
                          type: "category",
                          data: xAxisdata
                        }
                      ],
                      yAxis: [
                        {
                          type: "value"
                        }
                      ],
                      series: [
                        {
                          name: "个人荣誉",
                          type: "bar",
                          color: "#2ec7c9",
                          data: seriesData,
                          markPoint: {
                            data: [
                              { type: "max", name: "最大值" },
                              { type: "min", name: "最小值" }
                            ]
                          },
                          markLine: {
                            data: [{ type: "average", name: "平均值" }]
                          }
                        }
                      ]
                    };
                    if (
                      this.moveuplists[0] &&
                      this.moveuplists[0].teamName == namese
                    ) {
                      myChartgetPersonHonorComparison1.setOption(
                        optionTrainComparison,
                        true
                      );
                      let _this = this;
                      myChartgetPersonHonorComparison1.on("click", function(
                        params11
                      ) {
                        _this.dolaefunction(
                          "个人荣誉",
                          _this.moveuplists[0].id,
                          params11
                        );
                      });
                    }
                    if (
                      this.moveuplists[1] &&
                      this.moveuplists[1].teamName == namese
                    ) {
                      myChartgetPersonHonorComparison2.setOption(
                        optionTrainComparison,
                        true
                      );
                      let _this = this;
                      myChartgetPersonHonorComparison2.on("click", function(
                        params11
                      ) {
                        _this.dolaefunction(
                          "个人荣誉",
                          _this.moveuplists[1].id,
                          params11
                        );
                      });
                    }
                    if (
                      this.moveuplists[2] &&
                      this.moveuplists[2].teamName == namese
                    ) {
                      myChartgetPersonHonorComparison3.setOption(
                        optionTrainComparison,
                        true
                      );
                      let _this = this;
                      myChartgetPersonHonorComparison3.on("click", function(
                        params11
                      ) {
                        _this.dolaefunction(
                          "个人荣誉",
                          _this.moveuplists[2].id,
                          params11
                        );
                      });
                    }
                    if (
                      this.moveuplists[3] &&
                      this.moveuplists[3].teamName == namese
                    ) {
                      myChartgetPersonHonorComparison4.setOption(
                        optionTrainComparison,
                        true
                      );
                      let _this = this;
                      myChartgetPersonHonorComparison4.on("click", function(
                        params11
                      ) {
                        _this.dolaefunction(
                          "个人荣誉",
                          _this.moveuplists[3].id,
                          params11
                        );
                      });
                    }
                    if (
                      this.moveuplists[4] &&
                      this.moveuplists[4].teamName == namese
                    ) {
                      myChartgetPersonHonorComparison5.setOption(
                        optionTrainComparison,
                        true
                      );
                      let _this = this;
                      myChartgetPersonHonorComparison5.on("click", function(
                        params11
                      ) {
                        _this.dolaefunction(
                          "个人荣誉",
                          _this.moveuplists[4].id,
                          params11
                        );
                      });
                    }
                  }
                }, 300);
              } else {
                this.$message(res.message);
              }
            })
            .catch(function(error) {
              console.log(error);
            });
        }
        // if (this.flagse4 == true) {
        // }
      }
    },
    //选择违纪案件查询时间
    contonttclick() {
      if (this.flagse6 == true) {
        this.BreakRuleCase = false;
        this.BreakRuleCase1 = false;
        this.BreakRuleCase2 = false;
        this.BreakRuleCase3 = false;
        this.BreakRuleCase4 = false;
        let unitIdse = this.unitIdses;
        this.$axios
          .post("/teamComparison/getBreakRuleCaseComparison", {
            endYear: this.valueyears,
            startYear: this.valueyear,
            type: "",
            unitId: unitIdse,
            year: ""
          })
          .then(res => {
            if (res.code == 0) {
              for (let k = 0; k < res.data.length; k++) {
                let namese = "";
                for (let items in res.data[k]) {
                  namese = items;
                }
                if (
                  this.moveuplists[0] &&
                  this.moveuplists[0].teamName == namese
                ) {
                  this.BreakRuleCase = true;
                }
                if (
                  this.moveuplists[1] &&
                  this.moveuplists[1].teamName == namese
                ) {
                  this.BreakRuleCase1 = true;
                }
                if (
                  this.moveuplists[2] &&
                  this.moveuplists[2].teamName == namese
                ) {
                  this.BreakRuleCase2 = true;
                }
                if (
                  this.moveuplists[3] &&
                  this.moveuplists[3].teamName == namese
                ) {
                  this.BreakRuleCase3 = true;
                }
                if (
                  this.moveuplists[4] &&
                  this.moveuplists[4].teamName == namese
                ) {
                  this.BreakRuleCase4 = true;
                }
              }
              setTimeout(() => {
                if (this.BreakRuleCase == true) {
                  var myChartgetBreakRuleCaseComparison1 = this.$echarts.init(
                    document.getElementById("getBreakRuleCaseComparison1")
                  );
                }
                if (this.BreakRuleCase1 == true) {
                  var myChartgetBreakRuleCaseComparison2 = this.$echarts.init(
                    document.getElementById("getBreakRuleCaseComparison2")
                  );
                }
                if (this.unitIdses.length > 2 && this.BreakRuleCase2 == true) {
                  var myChartgetBreakRuleCaseComparison3 = this.$echarts.init(
                    document.getElementById("getBreakRuleCaseComparison3")
                  );
                }
                if (this.unitIdses.length > 3 && this.BreakRuleCase3 == true) {
                  var myChartgetBreakRuleCaseComparison4 = this.$echarts.init(
                    document.getElementById("getBreakRuleCaseComparison4")
                  );
                }
                if (this.unitIdses.length > 4 && this.BreakRuleCase4 == true) {
                  var myChartgetBreakRuleCaseComparison5 = this.$echarts.init(
                    document.getElementById("getBreakRuleCaseComparison5")
                  );
                }
                for (let k = 0; k < res.data.length; k++) {
                  let seriesData = [];
                  let xAxisdata = [];
                  let namese = "";
                  for (let items in res.data[k]) {
                    namese = items;
                    res.data[k][items].trainCountVoList.forEach(itemse => {
                      xAxisdata.push(itemse.year);
                      seriesData.push(itemse.count);
                    });
                  }
                  let optionTrainComparison = {
                    tooltip: {
                      trigger: "axis"
                    },
                    calculable: true,
                    xAxis: [
                      {
                        type: "category",
                        data: xAxisdata
                      }
                    ],
                    yAxis: [
                      {
                        type: "value"
                      }
                    ],
                    series: [
                      {
                        name: "违纪案件",
                        type: "bar",
                        color: "#5ab1ef",
                        data: seriesData,
                        markPoint: {
                          data: [
                            { type: "max", name: "最大值" },
                            { type: "min", name: "最小值" }
                          ]
                        },
                        markLine: {
                          data: [{ type: "average", name: "平均值" }]
                        }
                      }
                    ]
                  };
                  if (
                    this.moveuplists[0] &&
                    this.moveuplists[0].teamName == namese
                  ) {
                    myChartgetBreakRuleCaseComparison1.setOption(
                      optionTrainComparison,
                      true
                    );
                    let _this = this;
                    myChartgetBreakRuleCaseComparison1.on("click", function(
                      params11
                    ) {
                      _this.dolaefunction(
                        "违纪案件",
                        _this.moveuplists[0].id,
                        params11
                      );
                    });
                  }
                  if (
                    this.moveuplists[1] &&
                    this.moveuplists[1].teamName == namese
                  ) {
                    myChartgetBreakRuleCaseComparison2.setOption(
                      optionTrainComparison,
                      true
                    );
                    let _this = this;
                    myChartgetBreakRuleCaseComparison2.on("click", function(
                      params11
                    ) {
                      _this.dolaefunction(
                        "违纪案件",
                        _this.moveuplists[1].id,
                        params11
                      );
                    });
                  }
                  if (
                    this.moveuplists[2] &&
                    this.moveuplists[2].teamName == namese
                  ) {
                    myChartgetBreakRuleCaseComparison3.setOption(
                      optionTrainComparison,
                      true
                    );
                    let _this = this;
                    myChartgetBreakRuleCaseComparison3.on("click", function(
                      params11
                    ) {
                      _this.dolaefunction(
                        "违纪案件",
                        _this.moveuplists[2].id,
                        params11
                      );
                    });
                  }
                  if (
                    this.moveuplists[3] &&
                    this.moveuplists[3].teamName == namese
                  ) {
                    myChartgetBreakRuleCaseComparison4.setOption(
                      optionTrainComparison,
                      true
                    );
                    let _this = this;
                    myChartgetBreakRuleCaseComparison4.on("click", function(
                      params11
                    ) {
                      _this.dolaefunction(
                        "违纪案件",
                        _this.moveuplists[3].id,
                        params11
                      );
                    });
                  }
                  if (
                    this.moveuplists[4] &&
                    this.moveuplists[4].teamName == namese
                  ) {
                    myChartgetBreakRuleCaseComparison5.setOption(
                      optionTrainComparison,
                      true
                    );
                    let _this = this;
                    myChartgetBreakRuleCaseComparison5.on("click", function(
                      params11
                    ) {
                      _this.dolaefunction(
                        "违纪案件",
                        _this.moveuplists[4].id,
                        params11
                      );
                    });
                  }
                }
              }, 300);
            } else {
              this.$message(res.message);
            }
          })
          .catch(function(error) {
            console.log(error);
          });
      }
    },
    //民意满意度
    contonttclickde() {
      if (this.flagse7 == true) {
        this.Satisfied = false;
        this.Satisfied1 = false;
        this.Satisfied2 = false;
        this.Satisfied3 = false;
        this.Satisfied4 = false;
        let unitIdse = this.unitIdses;
        this.$axios
          .post("/teamComparison/getSatisfiedComparison", {
            endYear: this.valueyears,
            startYear: this.valueyear,
            type: "",
            unitId: unitIdse,
            year: ""
          })
          .then(res => {
            if (res.code == 0) {
              for (let k = 0; k < res.data.length; k++) {
                let namese = res.data[k].name;
                if (
                  this.moveuplists[0] &&
                  this.moveuplists[0].teamName == namese
                ) {
                  this.Satisfied = true;
                }
                if (
                  this.moveuplists[1] &&
                  this.moveuplists[1].teamName == namese
                ) {
                  this.Satisfied1 = true;
                }
                if (
                  this.moveuplists[2] &&
                  this.moveuplists[2].teamName == namese
                ) {
                  this.Satisfied2 = true;
                }
                if (
                  this.moveuplists[3] &&
                  this.moveuplists[3].teamName == namese
                ) {
                  this.Satisfied3 = true;
                }
                if (
                  this.moveuplists[4] &&
                  this.moveuplists[4].teamName == namese
                ) {
                  this.Satisfied4 = true;
                }
              }
              setTimeout(() => {
                if (this.Satisfied == true) {
                  var myChartgetSatisfiedComparison1 = this.$echarts.init(
                    document.getElementById("getSatisfiedComparison1")
                  );
                }
                if (this.Satisfied1 == true) {
                  var myChartgetSatisfiedComparison2 = this.$echarts.init(
                    document.getElementById("getSatisfiedComparison2")
                  );
                }
                if (this.unitIdses.length > 2 && this.Satisfied2 == true) {
                  var myChartgetSatisfiedComparison3 = this.$echarts.init(
                    document.getElementById("getSatisfiedComparison3")
                  );
                }
                if (this.unitIdses.length > 3 && this.Satisfied3 == true) {
                  var myChartgetSatisfiedComparison4 = this.$echarts.init(
                    document.getElementById("getSatisfiedComparison4")
                  );
                }
                if (this.unitIdses.length > 4 && this.Satisfied4 == true) {
                  var myChartgetSatisfiedComparison5 = this.$echarts.init(
                    document.getElementById("getSatisfiedComparison5")
                  );
                }
                for (let k = 0; k < res.data.length; k++) {
                  let seriesData = [];
                  let xAxisdata = [];
                  let minydtat = 0;
                  if (res.data[k].chart) {
                    res.data[k].chart.forEach(items => {
                      xAxisdata.push(items.name);
                      seriesData.push(items.value);
                      if (Number(minydtat) > Number(items.value)) {
                        minydtat = items.value;
                      }
                    });
                  }
                  let optionTrainComparison = {
                    tooltip: {
                      trigger: "axis"
                    },
                    xAxis: {
                      type: "category",
                      boundaryGap: false,
                      data: xAxisdata
                    },
                    yAxis: {
                      type: "value",
                      min: minydtat,
                      axisLabel: {
                        formatter: "{value}%"
                      }
                    },
                    series: [
                      {
                        name: "民意满意度",
                        type: "line",
                        data: seriesData,
                        color: "#b6a2de",
                        markPoint: {
                          data: [
                            { type: "max", name: "最大值" },
                            { type: "min", name: "最小值" }
                          ]
                        },
                        markLine: {
                          data: [{ type: "average", name: "平均值" }]
                        }
                      }
                    ]
                  };
                  if (this.moveuplists[0].teamName == res.data[k].name) {
                    myChartgetSatisfiedComparison1.setOption(
                      optionTrainComparison,
                      true
                    );
                    // let _this = this;
                    // myChartgetSatisfiedComparison1.on("click", function(
                    //   params11
                    // ) {
                    //   _this.dolaefunction(
                    //     "民意满意度",
                    //     _this.moveuplists[0].id,
                    //     params11
                    //   );
                    // });
                  }
                  if (this.moveuplists[1].teamName == res.data[k].name) {
                    myChartgetSatisfiedComparison2.setOption(
                      optionTrainComparison,
                      true
                    );
                    // let _this = this;
                    // myChartgetSatisfiedComparison2.on("click", function(
                    //   params11
                    // ) {
                    //   _this.dolaefunction(
                    //     "民意满意度",
                    //     _this.moveuplists[1].id,
                    //     params11
                    //   );
                    // });
                  }
                  if (
                    this.moveuplists[2] &&
                    this.moveuplists[2].teamName == res.data[k].name
                  ) {
                    myChartgetSatisfiedComparison3.setOption(
                      optionTrainComparison,
                      true
                    );
                    // let _this = this;
                    // myChartgetSatisfiedComparison3.on("click", function(
                    //   params11
                    // ) {
                    //   _this.dolaefunction(
                    //     "民意满意度",
                    //     _this.moveuplists[2].id,
                    //     params11
                    //   );
                    // });
                  }
                  if (
                    this.moveuplists[3] &&
                    this.moveuplists[3].teamName == res.data[k].name
                  ) {
                    myChartgetSatisfiedComparison4.setOption(
                      optionTrainComparison,
                      true
                    );
                    // let _this = this;
                    // myChartgetSatisfiedComparison4.on("click", function(
                    //   params11
                    // ) {
                    //   _this.dolaefunction(
                    //     "民意满意度",
                    //     _this.moveuplists[3].id,
                    //     params11
                    //   );
                    // });
                  }
                  if (
                    this.moveuplists[4] &&
                    this.moveuplists[4].teamName == res.data[k].name
                  ) {
                    myChartgetSatisfiedComparison5.setOption(
                      optionTrainComparison,
                      true
                    );
                    // let _this = this;
                    // myChartgetSatisfiedComparison5.on("click", function(
                    //   params11
                    // ) {
                    //   _this.dolaefunction(
                    //     "民意满意度",
                    //     _this.moveuplists[4].id,
                    //     params11
                    //   );
                    // });
                  }
                }
              }, 300);
            } else {
              this.$message(res.message);
            }
          })
          .catch(function(error) {
            console.log(error);
          });
      }
    },
    //开始对比
    contestclicks() {
      this.moveuplists = [];
      this.unitIdses = [];
      this.movelistshows = [];
      for (let m = 0; m < this.movelists.length; m++) {
        this.unitIdses.push(this.movelists[m].id);
        this.movelistshows.unshift(this.movelists[m]);
        this.moveuplists.push(this.movelists[m]);
      }
      if (this.unitIdses.length > 1 && this.unitIdses.length < 6) {
        this.flags = "1";
        let newdate = new Date();
        this.valueyears = newdate.getFullYear().toString();
        this.clickconttest();
      } else {
        this.$message("对比的单位至少为两个，至多为五个!");
      }
    },
    //左侧数据反页
    sumerclick(parems) {
      let unitAgeCounts = document.querySelectorAll(".bottombutton");
      //根据索引数设置样式
      for (let kss = 0; kss < unitAgeCounts.length; kss++) {
        if (kss == parems) {
          unitAgeCounts[kss].classList.add("activest");
        } else {
          unitAgeCounts[kss].classList.remove("activest");
        }
      }
      this.getteamlistshow = this.getteamlistse.slice(
        15 * parems,
        15 * (parems + 1)
      );
    },
    sumerclickes(paremse) {
      let unitAgeCountse = document.querySelectorAll(".bottombuttons");
      //根据索引数设置样式
      for (let ksse = 0; ksse < unitAgeCountse.length; ksse++) {
        if (ksse == paremse) {
          unitAgeCountse[ksse].classList.add("activest");
        } else {
          unitAgeCountse[ksse].classList.remove("activest");
        }
      }
      this.getteamdatashow = this.getteamdatase.slice(
        15 * paremse,
        15 * (paremse + 1)
      );
    },
    sumerseclickes(paremse) {
      let unitAgeCountse = document.querySelectorAll(".bottombuttons");
      //根据索引数设置样式
      for (let ksses = 0; ksses < unitAgeCountse.length; ksses++) {
        if (ksses == paremse) {
          unitAgeCountse[ksses].classList.add("activest");
        } else {
          unitAgeCountse[ksses].classList.remove("activest");
        }
      }
      this.getteamdatashowse = this.getteamdatasel.slice(
        15 * paremse,
        15 * (paremse + 1)
      );
    },
    //返回上级
    canelse(pemse) {
      if (pemse == "分局") {
        this.getteamdatashow = [];
        this.getteamdatase = this.getteamdata; //派出所数据1
        this.getteamdataseold = this.getteamdata;
        this.getteamjbse1 = this.getteamjb1;
        let sums = Math.ceil(this.getteamdatase.length / 15);
        this.sumers = [];
        for (let k = 0; k < sums; k++) {
          this.sumers.push(k);
        }
      } else {
        this.getteamlistshow = [];
        this.getteamlistse = this.getteamlist; //市局机关下级数据1
        this.getteamlistseold = this.getteamlist;
        this.getteamjbse = this.getteamjb;
        let sum = Math.ceil(this.getteamlistse.length / 15);
        this.sumer = [];
        for (let i = 0; i < sum; i++) {
          this.sumer.push(i);
        }
      }
      let _this = this;
      setTimeout(() => {
        if (pemse == "分局") {
          _this.sumerclickes(0);
        } else {
          _this.sumerclick(0);
        }
      }, 300);
    },
    //弹框方法
    dolaefunction(agetitles, unitId, params11) {
        if (agetitles == "违纪案件"){
            return;
        }
        if (
        params11.name !== "最小值" &&
        params11.name !== "最大值" &&
        params11.name !== "平均值"
      ) {
        this.loadingse = true;
        this.ageVisible = true;
        this.agetitle = agetitles;
        this.pageno = 1;
        this.dataIndex = params11.name;
        if (agetitles == "年龄结构") {
          this.$axios
            .post("/teamComparison/getAgeComparisonDetail", {
              unitId: unitId,
              endYear: this.valueyears,
              startYear: this.valueyear,
              type: params11.name
            })
            .then(res => {
              if (res.code == 0) {
                this.loadingse = false;
                this.agedatase = res.data.slice(0, 10);
                this.agedatasese = res.data;
                this.totalRecord = res.data.length;
              } else {
                this.$message(res.message);
              }
            })
            .catch(function(error) {
              console.log(error);
            });
        } else if (agetitles == "教育培训") {
          this.$axios
            .post("/teamComparison/getTrainComparisonDetail", {
              unitId: unitId,
              endYear: this.valueyears,
              startYear: this.valueyear,
              type: params11.name
            })
            .then(res => {
              if (res.code == 0) {
                this.loadingse = false;
                this.agedatase = res.data.slice(0, 10);
                this.agedatasese = res.data;
                this.totalRecord = res.data.length;
              } else {
                this.$message(res.message);
              }
            })
            .catch(function(error) {
              console.log(error);
            });
        } else if (agetitles == "集体荣誉") {
          this.$axios
            .post("/teamComparison/getOrgHonorComparisonDetail", {
              unitId: unitId,
              endYear: this.valueyears,
              startYear: this.valueyear,
              type: params11.name
            })
            .then(res => {
              if (res.code == 0) {
                this.loadingse = false;
                this.agedatase = res.data.slice(0, 10);
                this.agedatasese = res.data;
                this.totalRecord = res.data.length;
              } else {
                this.$message(res.message);
              }
            })
            .catch(function(error) {
              console.log(error);
            });
        } else if (agetitles == "个人荣誉") {
          this.$axios
            .post("/teamComparison/getPersonHonorComparisonDetail", {
              unitId: unitId,
              endYear: this.valueyears,
              startYear: this.valueyear,
              type: params11.name
            })
            .then(res => {
              if (res.code == 0) {
                this.loadingse = false;
                this.agedatase = res.data.slice(0, 10);
                this.agedatasese = res.data;
                this.totalRecord = res.data.length;
              } else {
                this.$message(res.message);
              }
            })
            .catch(function(error) {
              console.log(error);
            });
        } else if (agetitles == "违纪案件") {
          this.$axios
            .post("/teamComparison/getBreakRuleCaseComparisonDetail", {
              unitId: unitId,
              endYear: this.valueyears,
              startYear: this.valueyear,
              type: params11.name
            })
            .then(res => {
              if (res.code == 0) {
                this.loadingse = false;
                this.agedatase = res.data.slice(0, 10);
                this.agedatasese = res.data;
                this.totalRecord = res.data.length;
              } else {
                this.$message(res.message);
              }
            })
            .catch(function(error) {
              console.log(error);
            });
        }
        // else if (agetitles == "民意满意度") {
        //   this.$axios
        //     .post("/teamComparison/getSatisfiedComparisonDetail", {
        //       unitId: unitId,
        //       endYear: this.valueyears,
        //       startYear: this.valueyear,
        //       type: params11.name
        //     })
        //     .then(res => {
        //       if (res.code == 0) {
        //         this.loadingse = false;
        //         this.agedatase = res.data.slice(0, 10);
        //         this.agedatasese = res.data;
        //         this.totalRecord = res.data.length;
        //       } else {
        //         this.$message(res.message);
        //       }
        //     })
        //     .catch(function(error) {
        //       console.log(error);
        //     });
        // }
      }
    },
    //表格翻页
    handleCurrentChange(parmes) {
      this.loadingse = true;
      let pageNumse = parmes - 1;
      this.agedatase = this.agedatasese.slice(pageNumse * 10, parmes * 10);
      this.loadingse = false;
    },
    //下载表格
    downloadse() {
      this.multipleSelection = this.agedatasese;
      if (this.agetitle == "年龄结构") {
        setTimeout(() => {
          $("#tipMessageLabel").table2excel({
            // 不被导出的表格行的CSS class类
            exclude: ".noExl",
            name: "Excel Document Name",
            filename: "年龄结构数据.xls"
          });
        }, 300);
      } else if (this.agetitle == "教育培训") {
        setTimeout(() => {
          $("#tipMessageLabel").table2excel({
            // 不被导出的表格行的CSS class类
            exclude: ".noExl",
            name: "Excel Document Name",
            filename: "教育培训数据.xls"
          });
        }, 300);
      } else if (this.agetitle == "集体荣誉") {
        setTimeout(() => {
          $("#tipMessageLabel").table2excel({
            // 不被导出的表格行的CSS class类
            exclude: ".noExl",
            name: "Excel Document Name",
            filename: "集体荣誉数据.xls"
          });
        }, 300);
      } else if (this.agetitle == "个人荣誉") {
        setTimeout(() => {
          $("#tipMessageLabel").table2excel({
            // 不被导出的表格行的CSS class类
            exclude: ".noExl",
            name: "Excel Document Name",
            filename: "个人荣誉数据.xls"
          });
        }, 300);
      } else if (this.agetitle == "违纪案件") {
        setTimeout(() => {
          $("#tipMessageLabel").table2excel({
            // 不被导出的表格行的CSS class类
            exclude: ".noExl",
            name: "Excel Document Name",
            filename: "违纪案件数据.xls"
          });
        }, 300);
      }
      // else if (this.agetitle == "民意满意度") {
      //   setTimeout(() => {
      //     $("#tipMessageLabel").table2excel({
      //       // 不被导出的表格行的CSS class类
      //       exclude: ".noExl",
      //       name: "Excel Document Name",
      //       filename: "民意满意度数据.xls"
      //     });
      //   }, 300);
      // }
    },
    //政工绩效
    getfagesclick() {
      this.getfagest1 = false;
      this.getfagest2 = false;
      this.getfagest3 = false;
      this.getfagest4 = false;
      this.getfagest5 = false;
      let unitIdse = this.unitIdses;
      this.$axios
        .post("/teamComparison/getKPIComparison", {
          endYear: this.valueyears,
          startYear: this.valueyear,
          type: "",
          unitId: unitIdse,
          year: ""
        })
        .then(res => {
          if (res.code == 0) {
            for (let k = 0; k < res.data.length; k++) {
              if (
                this.moveuplists[0] &&
                this.moveuplists[0].teamName == res.data[k].name
              ) {
                this.getfagest1 = true;
              }
              if (
                this.moveuplists[1] &&
                this.moveuplists[1].teamName == res.data[k].name
              ) {
                this.getfagest2 = true;
              }
              if (
                this.moveuplists[2] &&
                this.moveuplists[2].teamName == res.data[k].name
              ) {
                this.getfagest3 = true;
              }
              if (
                this.moveuplists[3] &&
                this.moveuplists[3].teamName == res.data[k].name
              ) {
                this.getfagest4 = true;
              }
              if (
                this.moveuplists[4] &&
                this.moveuplists[4].teamName == res.data[k].name
              ) {
                this.getfagest5 = true;
              }
            }
            setTimeout(() => {
              if (this.getfagest1 == true) {
                var myChartgetfagesComparison1 = this.$echarts.init(
                  document.getElementById("getfages1")
                );
              }
              if (this.getfagest2 == true) {
                var myChartgetfagesComparison2 = this.$echarts.init(
                  document.getElementById("getfages2")
                );
              }
              if (this.unitIdses.length > 2 && this.getfagest3 == true) {
                var myChartgetfagesComparison3 = this.$echarts.init(
                  document.getElementById("getfages3")
                );
              }
              if (this.unitIdses.length > 3 && this.getfagest4 == true) {
                var myChartgetfagesComparison4 = this.$echarts.init(
                  document.getElementById("getfages4")
                );
              }
              if (this.unitIdses.length > 4 && this.getfagest5 == true) {
                var myChartgetfagesComparison5 = this.$echarts.init(
                  document.getElementById("getfages5")
                );
              }
              for (let k = 0; k < res.data.length; k++) {
                let seriesData = [];
                let xAxisdata = [];
                let minydtat = 0;
                res.data[k].chart.forEach(items => {
                  xAxisdata.push(items.name);
                  seriesData.push(items.value);
                  if (Number(minydtat) > Number(items.value)) {
                    minydtat = items.value;
                  }
                });
                let optiongetfagesComparison = {
                  tooltip: {
                    trigger: "axis"
                  },
                  xAxis: {
                    type: "category",
                    boundaryGap: false,
                    data: xAxisdata
                  },
                  yAxis: {
                    type: "value",
                    min: minydtat,
                    axisLabel: {
                      formatter: "{value}"
                    }
                  },
                  series: [
                    {
                      name: "政工绩效",
                      type: "line",
                      data: seriesData,
                      color: "#b6a2de",
                      markPoint: {
                        data: [
                          { type: "max", name: "最大值" },
                          { type: "min", name: "最小值" }
                        ]
                      },
                      markLine: {
                        data: [{ type: "average", name: "平均值" }]
                      }
                    }
                  ]
                };
                if (
                  this.moveuplists[0] &&
                  this.moveuplists[0].teamName == res.data[k].name
                ) {
                  myChartgetfagesComparison1.setOption(
                    optiongetfagesComparison,
                    true
                  );
                }
                if (
                  this.moveuplists[1] &&
                  this.moveuplists[1].teamName == res.data[k].name
                ) {
                  myChartgetfagesComparison2.setOption(
                    optiongetfagesComparison,
                    true
                  );
                }
                if (
                  this.moveuplists[2] &&
                  this.moveuplists[2].teamName == res.data[k].name
                ) {
                  myChartgetfagesComparison3.setOption(
                    optiongetfagesComparison,
                    true
                  );
                }
                if (
                  this.moveuplists[3] &&
                  this.moveuplists[3].teamName == res.data[k].name
                ) {
                  myChartgetfagesComparison4.setOption(
                    optiongetfagesComparison,
                    true
                  );
                }
                if (
                  this.moveuplists[4] &&
                  this.moveuplists[4].teamName == res.data[k].name
                ) {
                  myChartgetfagesComparison5.setOption(
                    optiongetfagesComparison,
                    true
                  );
                }
              }
            }, 300);
          } else {
            this.$message(res.message);
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    }
  }
};
</script>

<style scoped>
/deep/ .el-dialog__body {
  padding: 0px 0px 40px 0;
  color: #606266;
  font-size: 14px;
  word-break: break-all;
  border-radius: 0px 0px 20px 20px;
  background: #5ab1ef;
}
/deep/ .el-dialog__header {
  padding: 20px 20px 10px;
  background: #5ab1ef;
  border-radius: 20px 20px 0 0;
}
/deep/ .el-dialog__title {
  line-height: 24px;
  font-size: 18px;
  color: #fff;
}
/deep/ .el-dialog__headerbtn .el-dialog__close {
  color: #fff;
}
/deep/ .el-dialog {
  position: relative;
  margin: 0 auto 50px;
  -webkit-box-shadow: 0 1px 3px rgba(0, 0, 0, 0.3);
  box-shadow: 0 1px 3px rgba(0, 0, 0, 0.3);
  box-sizing: border-box;
  border-radius: 20px;
}
/deep/ .el-pagination.is-background .el-pager li:not(.disabled).active {
  background-color: #5ab1ef;
  color: #fff;
}
/deep/ .el-table th {
  overflow: hidden;
  -ms-user-select: none;
  user-select: none;
  background: #5ab1ef3d;
  color: #000;
}
</style>

<style>
.mianportrait {
  width: 100%;
  clear: both;
  font-size: 14px;
}
.leftbox {
  background: #6b80b6;
  height: 800px;
  border-radius: 10px;
}
.leftheader {
  background: #708cd5;
  line-height: 50px;
  border-radius: 10px;
  color: #fff;
  font-weight: 700;
  font-size: 16px;
}
.leftheadernext {
  font-size: 16px;
  text-align: center;
  width: 100%;
  border-bottom: 1px solid #cccccc73;
}
.headernextcolor {
  color: #d5dbe7;
  display: inline-block;
  width: 130px;
  height: 60px;
  line-height: 80px;
  cursor: pointer;
}
.nextbottom {
  border-bottom: 4px solid #53a0ef;
  color: #91c1f1;
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
  background: #dcdfe669;
  opacity: 1;
  color: #fff;
}
.contrastbox /deep/.el-input-group__append,
.el-input-group__prepend {
  vertical-align: middle;
  display: table-cell;
  position: relative;
  border: 1px solid #dcdfe669;
  border-radius: 4px;
  padding: 0 20px;
  width: 1px;
  white-space: nowrap;
  background: #dcdfe669;
  opacity: 1;
  color: #fff;
}
.contrastbox /deep/.el-input__inner {
  -webkit-appearance: none;
  background: #586ea8;
  background-image: none;
  border-radius: 4px;
  border: 1px solid #dcdfe6;
  -webkit-box-sizing: border-box;
  box-sizing: border-box;
  color: #fff;
  display: inline-block;
  font-size: inherit;
  height: 40px;
  line-height: 40px;
  outline: 0;
  padding: 0 15px 0 30px;
  -webkit-transition: border-color 0.2s cubic-bezier(0.645, 0.045, 0.355, 1);
  transition: border-color 0.2s cubic-bezier(0.645, 0.045, 0.355, 1);
  width: 100%;
}
.linestyle {
  color: #585ea8;
  font-size: 24px;
  font-weight: 700;
  margin: 0 10px;
}
.contstants {
  width: 32%;
  margin-right: 1%;
  background: rgb(255, 255, 255);
  height: 400px;
  float: left;
  margin-top: 30px;
  opacity: 0.8;
}
.buttonses {
  position: fixed;
  left: 0;
  top: 50%;
}
.buttonsyles {
  background: #586ea8;
  width: 24px;
  line-height: 100px;
  color: #fff;
  font-weight: 700;
  font-size: 24px;
  text-align: -webkit-center;
  border-radius: 0 10px 10px 0;
  cursor: pointer;
  float: left;
}
.fontse {
  float: left;
  width: 30px;
  height: 100px;
  padding-left: 10px;
}
._WHITE .buttonses {
  position: fixed;
  left: 30%;
  top: 50%;
}
.backcontest {
  height: 750px;
  background: url("./../../assets/image/policeporlit/组 23.png") no-repeat left;
  background-size: 75%;
}
.rightbuttons {
  width: 60%;
  height: 75px;
  background: url("./../../assets/image/policeporlit/矢量智能对象.png")
    no-repeat center;
  background-size: cover;
  position: absolute;
  bottom: 0;
  left: 20%;
  text-align: center;
  color: #fff;
  line-height: 50px;
  font-size: 18px;
  font-weight: 700;
  cursor: pointer;
}
.backcontestnext {
  width: 100px;
  height: 100px;
  background: #586fa6;
  opacity: 0.6;
  border: 1px dashed #000;
  position: absolute;
  top: 3%;
  left: 11%;
  border-radius: 10px;
}
.miannext {
  width: 80px;
  height: 80px;
  text-align: center;
  display: table-cell;
  vertical-align: middle;
  margin: 10px 23px 10px 23px;
  float: left;
  background: #66b2fd;
  color: #fff;
  border-radius: 10px;
  padding-top: 5px;
  cursor: pointer;
  position: relative;
  z-index: 999;
  -webkit-box-shadow: 5px 5px 0px 0px #6d6969;
  -moz-box-shadow: 5px 5px 0px 0px #6d6969;
  box-shadow: 5px 5px 0px 0px #6d6969;
}
.miannextse {
  width: 60px;
  height: 60px;
  text-align: center;
  margin: 0 10px;
  float: right;
  background: #66b2fd;
  color: #fff;
  border-radius: 10px;
  padding-top: 5px;
}
.bottombutton,
.bottombuttons {
  width: 16px;
  height: 16px;
  background: #d5cdcdd1;
  border-radius: 8px;
  cursor: pointer;
  float: left;
  margin: 10px;
}
.activest {
  background: #fff;
}
.mianportraitboxx {
  width: 98%;
  margin: 120px auto;
  margin-bottom: 0;
  height: 680px;
  background: #fff;
  /* overflow-x: auto; */
  padding-right: 5px;
  padding-bottom: 5px;
}
.biaomianleft {
  width: 10%;
  float: left;
  height: 50px;
  text-align: -webkit-center;
}
.biaomian {
  width: 18%;
  float: left;
  height: 50px;
  text-align: -webkit-center;
}
.biaomiannext {
  width: 100%;
  height: 50px;
  display: table-cell;
  vertical-align: middle;
  font-size: 20px;
  color: #0099cc;
}
.colorbiao {
  border-bottom: 1px solid #acd6ff;
}
.biaomian1left {
  width: 10%;
  float: left;
  height: 300px;
  text-align: -webkit-center;
  border-right: 1px solid #acd6ff;
  border-bottom: 1px dashed #000;
}
.biaomian1 {
  width: 18%;
  float: left;
  height: 300px;
  text-align: -webkit-center;
  border-right: 1px solid #acd6ff;
  border-bottom: 1px dashed #000;
}
.biaomiannext1 {
  width: 100%;
  height: 300px;
  display: table-cell;
  vertical-align: middle;
  font-size: 20px;
}
.widths {
  width: 45%;
}
.widthse {
  width: 30%;
}
.widthses {
  width: 22.5%;
}
.fontstyles {
  height: 70px;
  display: table-cell;
  vertical-align: middle;
  text-align: center;
  width: 80px;
  word-wrap: break-word;
  word-break: break-all;
  -moz-user-select: none; /* Firefox私有属性 */
  -webkit-user-select: none; /* WebKit内核私有属性 */
  -ms-user-select: none; /* IE私有属性(IE10及以后) */
  -khtml-user-select: none; /* KHTML内核私有属性 */
  -o-user-select: none; /* Opera私有属性 */
  user-select: none; /* CSS3属性 */
}
</style>

