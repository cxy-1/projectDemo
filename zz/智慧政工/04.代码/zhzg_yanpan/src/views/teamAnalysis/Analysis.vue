<template>
  <div class="pop-container">
    <header>
      <div>{{ AnalysisTitle }}</div>
      <div><span class="jianclass" @click="titlechange(0)">建设成效评估</span><span style="margin-right:10px;">|</span><span class="linclass" @click="titlechange(1)">领导班子成员分析</span></div>
      <button @click="exportReport"  v-loading="loadingsede == true" :disabled="this.loadingsede" v-if="this.downseloads == this.targetNum || this.downseloads > (this.targetNum-1)">
        <i class="el-icon-document"></i> 报告输出
      </button>
      <i class="el-icon-close close-btn" @click="closePanel"></i>
    </header>
    <div style="width:100%;position: relative;">
    <section class="up-section"  v-if="bigtitlechange == true">
      <div>
        <div></div>
        <div>总人数</div>
        <div class="color1">{{ row1.count }}</div>
      </div>
      <div>
        <div></div>
        <div>领导班子人数</div>
        <div class="color2">{{ row1.leaderCount }}</div>
      </div>
      <div>
        <div></div>
        <div>民警人数</div>
        <div class="color3">{{ row1.policeCount }}</div>
      </div>
      <div>
        <div></div>
        <div>党员人数</div>
        <div class="color4">{{ row1.partyCount }}</div>
      </div>
    </section>
    <section class="down-section" v-if="bigtitlechange == true">
      <ChartAndTab
              type="1"
              title="年龄结构"
              @download="downloadReport"
              @init-chart-and-tab="initChartAndTab"
              @size-change="handleSizeChange"
              :tabData="row2.left.tab"
              @current-page-change="handleCurrentChange"
      >
        <template #chart>
          <div class="chart">
            <div id="chart1"></div>
            <div>
              <div>经统计，{{ AnalysisTitle }}</div>
              <div>
                35岁以下民警
                <strong>{{ row2.left.chart.young }}</strong>
                人，占总人数的
                <strong>{{ row2.left.chart.youngPercent }}</strong>
              </div>
              <div>
                35岁到50岁民警
                <strong>{{ row2.left.chart.middle }}</strong> 人，占总人数的
                <strong>{{ row2.left.chart.middlePercent }}</strong>
              </div>
              <div>
                50岁以上民警
                <strong>{{ row2.left.chart.old }}</strong> 人，占总人数的
                <strong>{{ row2.left.chart.oldPercent }}</strong>
              </div>
            </div>
          </div>
        </template>
        <template #tab>
          <header>
            <div class="row2-switch">
              <div
                      :class="row2.left.tab.selectedIndex === i && 'item-selected'"
                      :key="i"
                      @click="row2Switch(i)"
                      v-for="(item, i) in row2.left.tab.typeArr"
              >
                {{ item }}
              </div>
            </div>
            <div>{{ content }}</div>
          </header>
          <main>
            <el-table
                    height="100%"
                    :data="row2.left.tab.data"
                    stripe
                    style="width: 100%"
            >
              <el-table-column align="center" prop="personName" label="姓名" />
              <el-table-column align="center" prop="policeNo" label="警号" />
              <el-table-column align="center" prop="age" label="年龄" />
            </el-table>
          </main>
        </template>
      </ChartAndTab>
      <ChartAndTab
              type="2"
              title="教育培训"
              @download="downloadReport"
              @init-chart-and-tab="initChartAndTab"
              @size-change="handleSizeChange"
              :tabData="row2.right.tab"
              @current-page-change="handleCurrentChange"
      >
        <template #chart>
          <div class="chart">
            <div id="chart2"></div>
            <div>
              <div>{{ row2RightTitle }}</div>
              <div v-html="row2.right.chart.content1"></div>
              <div v-html="row2.right.chart.content2"></div>
            </div>
          </div>
        </template>
        <template #tab>
          <main>
            <el-table
                    height="100%"
                    :data="row2.right.tab.data"
                    stripe
                    style="width: 100%"
            >
              <el-table-column align="center" prop="personName" label="姓名" width="80px"/>
              <el-table-column align="center" prop="policeNo" label="警号"  width="80px"/>
              <el-table-column align="center" prop="unitName" label="部门"  width="113px"/>
              <el-table-column
                      align="center"
                      prop="startTime"
                      label="培训时间"
                      width="113px"
              />
              <el-table-column
                      align="center"
                      prop="trainingCourse"
                      label="培训班名称"
                      width="180px"
              />
            </el-table>
          </main>
        </template>
      </ChartAndTab>
    </section>
    <section class="down-section" v-if="bigtitlechange == true">
      <ChartAndTab
              type="3"
              title="集体荣誉"
              @download="downloadReport"
              @init-chart-and-tab="initChartAndTab"
              :tabData="row3.left.tab"
              @size-change="handleSizeChange"
              @current-page-change="handleCurrentChange"
      >
        <template #chart>
          <div class="chart">
            <div id="chart3"></div>
            <div>
              <div>{{ row3LeftTitle }}</div>
              <div v-html="row3.left.chart.content1"></div>
              <div v-html="row3.left.chart.content2"></div>
              <div v-html="row3.left.chart.content3"></div>
            </div>
          </div>
        </template>
        <template #tab>
          <main>
            <el-table
                    height="100%"
                    :data="row3.left.tab.data"
                    stripe
                    style="width: 100%"
            >
              <el-table-column
                      align="center"
                      prop="honorName"
                      label="表彰称号"
              />
              <el-table-column align="center" prop="deptName" label="部门" />
              <el-table-column align="center" prop="date" label="授奖时间" />
              <el-table-column
                      align="center"
                      prop="awardDept"
                      label="表彰部门"
              />
            </el-table>
          </main>
        </template>
      </ChartAndTab>
      <ChartAndTab
              type="4"
              title="个人荣誉"
              @download="downloadReport"
              @init-chart-and-tab="initChartAndTab"
              :tabData="row3.right.tab"
              @size-change="handleSizeChange"
              @current-page-change="handleCurrentChange"
      >
        <template #chart>
          <div class="chart">
            <div id="chart4"></div>
            <div>
              <div>{{ row3RightTitle }}</div>
              <div v-html="row3.right.chart.content1"></div>
              <div v-html="row3.right.chart.content2"></div>
              <div v-html="row3.right.chart.content3"></div>
            </div>
          </div>
        </template>
        <template #tab>
          <main>
            <el-table
                    height="100%"
                    :data="row3.right.tab.data"
                    stripe
                    style="width: 100%"
            >
              <el-table-column align="center" prop="personName" label="姓名" />
              <el-table-column
                      align="center"
                      prop="awardName"
                      label="表彰称号"
              />
              <el-table-column align="center" prop="policeNo" label="警号" />
              <el-table-column align="center" prop="date" label="授奖时间" />
              <el-table-column
                      align="center"
                      prop="awardDept"
                      label="表彰部门"
              />
            </el-table>
          </main>
        </template>
      </ChartAndTab>
    </section>
    <section class="down-section" v-if="bigtitlechange == true">
      <div class="showtable">
        <header>
          <div class="img-panel">
            <div class="img-5"></div>
            <div>政工绩效</div>
          </div>
          <div class="partSelect" style="width: 70%;  z-index: 200; position: relative;">
            <span>请选择：</span>
            <el-cascader v-model="KPIMenu"  :options="KPIMenuOptions" :props="KPIMenuProps" @change="handleKPIMenuChange" collapse-tags change-on-select clearable>
            </el-cascader>
          </div>
          <div class="right-btn-panel">
            <i @click="downloadKPI" class="download iconfont iconai231"></i>
          </div>
        </header>
        <div class="shownexttable">
          <el-table :data="datatablese" stripe style="width: 100%;height: 100%">
            <el-table-column align="center" width="80" prop="YEAR" label="年份" />
            <el-table-column align="center" prop="QUARTER" label="季度" />
            <el-table-column align="center"  :show-overflow-tooltip='true' prop="GROUPNAME" label="考核单元" />
            <el-table-column align="center" width="120"  :show-overflow-tooltip='true' prop="SIMPLECHINANAME" label="考核单位" />
            <el-table-column align="center" width="80" :show-overflow-tooltip='true' prop="SCORE" label="考核成绩"/>
            <el-table-column align="center" width="80"  :show-overflow-tooltip='true' prop="RN" label="单元排名"/>
          </el-table>
        </div>
        <footer>
          <el-pagination
                  background
                  @size-change="handleSizeChangezgjx"
                  @current-change="handleCurrentChangezgjx"
                  :current-page="pageNumse"
                  :page-sizes="pageSizes"
                  :pager-count="5"
                  :page-size="pageSizese"
                  layout="total, sizes, pager, jumper"
                  :total="row4.left.tab.total"
          >
          </el-pagination>
        </footer>
      </div>
      <ChartAndTab
              type="6"
              title="违纪案件"
              @download="downloadReport"
              @init-chart-and-tab="initChartAndTab"
              :tabData="row4.right.tab"
              @size-change="handleSizeChange"
              @current-page-change="handleCurrentChange"
      >
        <template #chart>
          <div class="chart">
            <div id="chart6">
              <!-- <section class="left-sec"></section>
              <section class="right-sec"></section>
              <div id="chart6"></div>
              <div id="chart7"></div> -->
            </div>
            <div>
              <div>{{ row4RightTitle }}</div>
              <div v-html="row4.right.chart.content1"></div>
              <div v-html="row4.right.chart.content2"></div>
              <div v-html="row4.right.chart.content3"></div>
            </div>
          </div>
        </template>
        <template #tab>
          <main>
            <el-table
                    height="100%"
                    :data="row4.right.tab.data"
                    stripe
                    style="width: 100%"
            >
              <el-table-column align="center" prop="deptName" label="单位" />>
              <el-table-column
                      align="center"
                      prop="personName"
                      label="姓名"
              />
              <el-table-column align="center" prop="policeNo" label="警号" />
              <el-table-column align="center" prop="date" label="处分决定时间" />
              <el-table-column align="center" prop="detail" label="问题性质" />
            </el-table>
          </main>
        </template>
      </ChartAndTab>
    </section>
    <section class="down-section" style="height: 340px;" v-if="bigtitlechange == false">
      <div class="showtable">
        <header>
          <div class="img-panel">
            <div class="img-7"></div>
            <div>人员分析</div>
          </div>
          <div class="partSelect" style="width: 45%;z-index: 200; position: relative;text-align: -webkit-center;">
            <span @click="mintitlechange(0,'人员分析')" class="mintitlese1">{{this.titlemins}}</span><span @click="mintitlechange(1,'人员分析')" class="mintitlese2">{{this.titleminse}}</span>
          </div>
          <div class="right-btn-panelse">
            <i @click="downloadKPIse('人员分析')" class="download iconfont iconai231"></i>
            <div class="switch-panel">
              <div :class="selectedIndex == 0 && 'item-selected'" @click="switchPanel(0,'人员分析')" >
                <i class="el-icon-pie-chart"></i>
              </div>
              <div :class="selectedIndex == 1 && 'item-selected'" @click="switchPanel(1,'人员分析')" >
                <i class="el-icon-date"></i>
              </div>
            </div>
          </div>
        </header>
        <div class="heightline loadecharts-1" v-if="selectedIndex == 0">
          <div class="hiddenchartse" v-if="titlechangese == 0">
            <div id="linechartes11"></div>
          </div>
          <div class="hiddenchartse" v-if="titlechangese == 1">
            <div id="linechartes12"></div>
          </div>
          <div class="hiddenchartmian">{{this.row5.left.chart.content1}}</div>
        </div>
        <div class="heightline" v-if="selectedIndex == 1">
          <div class="shownexttablese">
            <el-table :data="row5.left.tab.data" stripe style="width: 100%;height: 100%">
              <el-table-column  align="center" :min-width="row5.left.tab.width?row5.left.tab.width[i]:null"  v-for="(col,i) in row5.left.tab.heads" :show-overflow-tooltip='true' :prop="col" :label="col"></el-table-column>
            </el-table>
          </div>
          <footer>
            <el-pagination
                  background
                  style="margin-top: 5px;"
                  @size-change="handleSizeChangeldbz1"
                  @current-change="handleCurrentChangeldbz1"
                  :current-page="row5.left.tab.pageNum"
                  :page-sizes="row5.left.tab.pageSizese"
                  :pager-count="5"
                  :page-size="row5.left.tab.pageSize"
                  layout="total, sizes, pager, jumper"
                  :total="row5.left.tab.total"
            >
            </el-pagination>
          </footer>
        </div>
      </div>
      <div class="showtable">
        <header>
          <div class="img-panel">
            <div class="img-8"></div>
            <div>年龄结构</div>
          </div>
          <div class="partSelect" style="width: 45%;z-index: 200; position: relative;text-align: -webkit-center;">
            <span @click="mintitlechange(0,'年龄结构')" class="mintitlese3">{{this.titlemins}}</span><span @click="mintitlechange(1,'年龄结构')" class="mintitlese4">{{this.titleminse}}</span>
          </div>
          <div class="right-btn-panelse">
            <i @click="downloadKPIse('年龄结构')" class="download iconfont iconai231"></i>
            <div class="switch-panel">
              <div :class="selectedIndex1 == 0 && 'item-selected'" @click="switchPanel(0,'年龄结构')" >
                <i class="el-icon-pie-chart"></i>
              </div>
              <div :class="selectedIndex1 == 1 && 'item-selected'" @click="switchPanel(1,'年龄结构')" >
                <i class="el-icon-date"></i>
              </div>
            </div>
          </div>
        </header>
        <div class="heightline loadecharts-2" v-if="selectedIndex1 == 0">
          <div class="hiddenchartse" v-if="titlechangese1 == 0">
            <div id="linechartes13"></div>
          </div>
          <div class="hiddenchartse" v-if="titlechangese1 == 1">
            <div id="linechartes14"></div>
          </div>
          <div class="hiddenchartmian">{{this.row5.right.chart.content1}}</div>
        </div>
        <div class="heightline" v-if="selectedIndex1 == 1">
          <div class="shownexttablese">
            <el-table :data="row5.right.tab.data" stripe style="width: 100%;height: 100%">
              <el-table-column  align="center" :min-width="row5.right.tab.width?row5.right.tab.width[i]:null"  v-for="(col,i) in row5.right.tab.heads" :show-overflow-tooltip='true' :prop="col" :label="col"></el-table-column>
            </el-table>
          </div>
          <footer>
            <el-pagination
                  background
                  style="margin-top: 5px;"
                  @size-change="handleSizeChangeldbz2"
                  @current-change="handleCurrentChangeldbz2"
                  :current-page="row5.right.tab.pageNum"
                  :page-sizes="row5.right.tab.pageSizese"
                  :pager-count="5"
                  :page-size="row5.right.tab.pageSize"
                  layout="total, sizes, pager, jumper"
                  :total="row5.right.tab.total"
            >
            </el-pagination>
          </footer>
        </div>
      </div>
    </section>
    <section class="down-section" style="height: 340px;" v-if="bigtitlechange == false">
      <div class="showtable">
        <header>
          <div class="img-panel">
            <div class="img-9" style="height: 35px;"></div>
            <div>男女比例</div>
          </div>
          <div class="partSelect" style="width: 45%;z-index: 200; position: relative;text-align: -webkit-center;">
            <span @click="mintitlechange(0,'男女比例')" class="mintitlese5">{{this.titlemins}}</span><span @click="mintitlechange(1,'男女比例')" class="mintitlese6">{{this.titleminse}}</span>
          </div>
          <div class="right-btn-panelse">
            <i @click="downloadKPIse('男女比例')" class="download iconfont iconai231"></i>
            <div class="switch-panel">
              <div :class="selectedIndex2 == 0 && 'item-selected'" @click="switchPanel(0,'男女比例')" >
                <i class="el-icon-pie-chart"></i>
              </div>
              <div :class="selectedIndex2 == 1 && 'item-selected'" @click="switchPanel(1,'男女比例')" >
                <i class="el-icon-date"></i>
              </div>
            </div>
          </div>
        </header>
        <div class="heightline loadecharts-3" v-if="selectedIndex2 == 0">
          <div class="hiddenchartse" v-if="titlechangese2 == 0">
            <div id="linechartes15"></div>
          </div>
          <div class="hiddenchartse" v-if="titlechangese2 == 1">
            <div id="linechartes16"></div>
          </div>
          <div class="hiddenchartmian">{{this.row6.left.chart.content1}}</div>
        </div>
        <div class="heightline" v-if="selectedIndex2 == 1">
          <div class="shownexttablese">
            <el-table :data="row6.left.tab.data" stripe style="width: 100%;height: 100%">
              <el-table-column  align="center" :min-width="row6.left.tab.width?row6.left.tab.width[i]:null"  v-for="(col,i) in row6.left.tab.heads" :show-overflow-tooltip='true' :prop="col" :label="col"></el-table-column>
            </el-table>
          </div>
          <footer>
            <el-pagination
                  background
                  style="margin-top: 5px;"
                  @size-change="handleSizeChangeldbz3"
                  @current-change="handleCurrentChangeldbz3"
                  :current-page="row6.left.tab.pageNum"
                  :page-sizes="row6.left.tab.pageSizese"
                  :pager-count="5"
                  :page-size="row6.left.tab.pageSize"
                  layout="total, sizes, pager, jumper"
                  :total="row6.left.tab.total"
            >
            </el-pagination>
          </footer>
        </div>
      </div>
      <div class="showtable">
        <header>
          <div class="img-panel">
            <div class="img-10"></div>
            <div>文化程度</div>
          </div>
          <div class="partSelect" style="width: 45%;z-index: 200; position: relative;text-align: -webkit-center;">
            <span @click="mintitlechange(0,'文化程度')" class="mintitlese7">{{this.titlemins}}</span><span @click="mintitlechange(1,'文化程度')" class="mintitlese8">{{this.titleminse}}</span>
          </div>
          <div class="right-btn-panelse">
            <i @click="downloadKPIse('文化程度')" class="download iconfont iconai231"></i>
            <div class="switch-panel">
              <div :class="selectedIndex3 == 0 && 'item-selected'" @click="switchPanel(0,'文化程度')" >
                <i class="el-icon-pie-chart"></i>
              </div>
              <div :class="selectedIndex3 == 1 && 'item-selected'" @click="switchPanel(1,'文化程度')" >
                <i class="el-icon-date"></i>
              </div>
            </div>
          </div>
        </header>
        <div class="heightline loadecharts-4" v-if="selectedIndex3 == 0">
          <div class="hiddenchartse" v-if="titlechangese3 == 0">
            <div id="linechartes17"></div>
          </div>
          <div class="hiddenchartse" v-if="titlechangese3 == 1">
            <div id="linechartes18"></div>
          </div>
          <div class="hiddenchartmian">{{this.row6.right.chart.content1}}</div>
        </div>
        <div class="heightline" v-if="selectedIndex3 == 1">
          <div class="shownexttablese">
            <el-table :data="row6.right.tab.data" stripe style="width: 100%;height: 100%">
              <el-table-column  align="center" :min-width="row6.right.tab.width?row6.right.tab.width[i]:null"  v-for="(col,i) in row6.right.tab.heads" :show-overflow-tooltip='true' :prop="col" :label="col"></el-table-column>
            </el-table>
          </div>
          <footer>
            <el-pagination
                  background
                  style="margin-top: 5px;"
                  @size-change="handleSizeChangeldbz4"
                  @current-change="handleCurrentChangeldbz4"
                  :current-page="row6.right.tab.pageNum"
                  :page-sizes="row6.right.tab.pageSizese"
                  :pager-count="5"
                  :page-size="row6.right.tab.pageSize"
                  layout="total, sizes, pager, jumper"
                  :total="row6.right.tab.total"
            >
            </el-pagination>
          </footer>
        </div>
      </div>
    </section>
    <section class="down-section" style="height: 340px;" v-if="bigtitlechange == false">
      <div class="showtable">
        <header>
          <div class="img-panel">
            <div class="img-11"></div>
            <div>民族分布</div>
          </div>
          <div class="partSelect" style="width: 45%;z-index: 200; position: relative;text-align: -webkit-center;">
            <span @click="mintitlechange(0,'民族分布')" class="mintitlese9">{{this.titlemins}}</span><span @click="mintitlechange(1,'民族分布')" class="mintitlese10">{{this.titleminse}}</span>
          </div>
          <div class="right-btn-panelse">
            <i @click="downloadKPIse('民族分布')" class="download iconfont iconai231"></i>
            <div class="switch-panel">
              <div :class="selectedIndex4 == 0 && 'item-selected'" @click="switchPanel(0,'民族分布')" >
                <i class="el-icon-pie-chart"></i>
              </div>
              <div :class="selectedIndex4 == 1 && 'item-selected'" @click="switchPanel(1,'民族分布')" >
                <i class="el-icon-date"></i>
              </div>
            </div>
          </div>
        </header>
        <div class="heightline loadecharts-5" v-if="selectedIndex4 == 0">
          <div class="hiddenchartse" v-if="titlechangese4 == 0">
            <div id="linechartes19"></div>
          </div>
          <div class="hiddenchartse" v-if="titlechangese4 == 1">
            <div id="linechartes110"></div>
          </div>
          <div class="hiddenchartmian">{{this.row7.left.chart.content1}}</div>
        </div>
        <div class="heightline" v-if="selectedIndex4 == 1">
          <div class="shownexttablese">
            <el-table :data="row7.left.tab.data" stripe style="width: 100%;height: 100%">
              <el-table-column  align="center" :min-width="row7.left.tab.width?row7.left.tab.width[i]:null"  v-for="(col,i) in row7.left.tab.heads" :show-overflow-tooltip='true' :prop="col" :label="col"></el-table-column>
            </el-table>
          </div>
          <footer>
            <el-pagination
                  background
                  style="margin-top: 5px;"
                  @size-change="handleSizeChangeldbz5"
                  @current-change="handleCurrentChangeldbz5"
                  :current-page="row7.left.tab.pageNum"
                  :page-sizes="row7.left.tab.pageSizese"
                  :pager-count="5"
                  :page-size="row7.left.tab.pageSize"
                  layout="total, sizes, pager, jumper"
                  :total="row7.left.tab.total"
            >
            </el-pagination>
          </footer>
        </div>
      </div>
      <div class="showtable">
        <header>
          <div class="img-panel">
            <div class="img-12"></div>
            <div>任职时间</div>
          </div>
          <div class="partSelect" style="width: 45%;z-index: 200; position: relative;text-align: -webkit-center;">
            <span @click="mintitlechange(0,'任职时间')" class="mintitlese11">正职</span><span @click="mintitlechange(1,'任职时间')" class="mintitlese12">副职</span>
          </div>
          <div class="right-btn-panelse">
            <i @click="downloadKPIse('任职时间')" class="download iconfont iconai231"></i>
            <div class="switch-panel">
              <div :class="selectedIndex5 == 0 && 'item-selected'" @click="switchPanel(0,'任职时间')" >
                <i class="el-icon-pie-chart"></i>
              </div>
              <div :class="selectedIndex5 == 1 && 'item-selected'" @click="switchPanel(1,'任职时间')" >
                <i class="el-icon-date"></i>
              </div>
            </div>
          </div>
        </header>
        <div class="heightline loadecharts-6" v-if="selectedIndex5 == 0">
          <div class="hiddenchartse" v-if="titlechangese5 == 0">
            <div id="linechartes111"></div>
          </div>
          <div class="hiddenchartse" v-if="titlechangese5 == 1">
            <div id="linechartes112"></div>
          </div>
          <div class="hiddenchartmian">{{this.row7.right.chart.content1}}</div>
        </div>
        <div class="heightline" v-if="selectedIndex5 == 1">
          <div class="shownexttablese">
            <el-table :data="row7.right.tab.data" stripe style="width: 100%;height: 100%">
              <el-table-column  align="center" :min-width="row7.right.tab.width?row7.right.tab.width[i]:null"  v-for="(col,i) in row7.right.tab.heads" :show-overflow-tooltip='true' :prop="col" :label="col"></el-table-column>
            </el-table>
          </div>
          <footer>
            <el-pagination
                  background
                  style="margin-top: 5px;"
                  @size-change="handleSizeChangeldbz6"
                  @current-change="handleCurrentChangeldbz6"
                  :current-page="row7.right.tab.pageNum"
                  :page-sizes="row7.right.tab.pageSizese"
                  :pager-count="5"
                  :page-size="row7.right.tab.pageSize"
                  layout="total, sizes, pager, jumper"
                  :total="row7.right.tab.total"
            >
            </el-pagination>
          </footer>
        </div>
      </div>
    </section>
    <section class="down-section" style="height: 340px;" v-if="bigtitlechange == false">
      <div class="showtable">
        <header>
          <div class="img-panel">
            <div class="img-13"></div>
            <div>入警年限</div>
          </div>
          <div class="partSelect" style="width: 45%;z-index: 200; position: relative;text-align: -webkit-center;">
            <span @click="mintitlechange(0,'入警年限')" class="mintitlese13">{{this.titlemins}}</span><span @click="mintitlechange(1,'入警年限')" class="mintitlese14">{{this.titleminse}}</span>
          </div>
          <div class="right-btn-panelse">
            <i @click="downloadKPIse('入警年限')" class="download iconfont iconai231"></i>
            <div class="switch-panel">
              <div :class="selectedIndex6 == 0 && 'item-selected'" @click="switchPanel(0,'入警年限')" >
                <i class="el-icon-pie-chart"></i>
              </div>
              <div :class="selectedIndex6 == 1 && 'item-selected'" @click="switchPanel(1,'入警年限')" >
                <i class="el-icon-date"></i>
              </div>
            </div>
          </div>
        </header>
        <div class="heightline loadecharts-7" v-if="selectedIndex6 == 0">
          <div class="hiddenchartse" v-if="titlechangese6 == 0">
            <div id="linechartes113"></div>
          </div>
          <div class="hiddenchartse" v-if="titlechangese6 == 1">
            <div id="linechartes114"></div>
          </div>
          <div class="hiddenchartmian">{{this.row8.left.chart.content1}}</div>
        </div>
        <div class="heightline" v-if="selectedIndex6 == 1">
          <div class="shownexttablese">
            <el-table :data="row8.left.tab.data" stripe style="width: 100%;height: 100%">
              <el-table-column  align="center" :min-width="row8.left.tab.width?row8.left.tab.width[i]:null"  v-for="(col,i) in row8.left.tab.heads" :show-overflow-tooltip='true' :prop="col" :label="col"></el-table-column>
            </el-table>
          </div>
          <footer>
            <el-pagination
                  background
                  style="margin-top: 5px;"
                  @size-change="handleSizeChangeldbz7"
                  @current-change="handleCurrentChangeldbz7"
                  :current-page="row8.left.tab.pageNum"
                  :page-sizes="row8.left.tab.pageSizese"
                  :pager-count="5"
                  :page-size="row8.left.tab.pageSize"
                  layout="total, sizes, pager, jumper"
                  :total="row8.left.tab.total"
            >
            </el-pagination>
          </footer>
        </div>
      </div>
      <div class="showtable">
        <header>
          <div class="img-panel">
            <div class="img-14"></div>
            <div>违纪案件</div>
          </div>
          <div class="partSelect" style="width: 45%;z-index: 200; position: relative;text-align: -webkit-center;">
            <span @click="mintitlechange(0,'违纪案件')" class="mintitlese15">{{this.titlemins}}</span><span @click="mintitlechange(1,'违纪案件')" class="mintitlese16">{{this.titleminse}}</span>
          </div>
          <div class="right-btn-panelse">
            <i @click="downloadKPIse('违纪案件')" class="download iconfont iconai231"></i>
            <div class="switch-panel">
              <div :class="selectedIndex7 == 0 && 'item-selected'" @click="switchPanel(0,'违纪案件')" >
                <i class="el-icon-pie-chart"></i>
              </div>
              <div :class="selectedIndex7 == 1 && 'item-selected'" @click="switchPanel(1,'违纪案件')" >
                <i class="el-icon-date"></i>
              </div>
            </div>
          </div>
        </header>
        <div class="heightline loadecharts-8" v-if="selectedIndex7 == 0">
          <div class="hiddenchartse" v-if="titlechangese7 == 0">
            <div id="linechartes115"></div>
          </div>
          <div class="hiddenchartse" v-if="titlechangese7 == 1">
            <div id="linechartes116"></div>
          </div>
          <div class="hiddenchartmian">{{this.row8.right.chart.content1}}</div>
        </div>
        <div class="heightline" v-if="selectedIndex7 == 1">
          <div class="shownexttablese">
            <el-table :data="row8.right.tab.data" stripe style="width: 100%;height: 100%">
              <el-table-column  align="center" :min-width="row8.right.tab.width?row8.right.tab.width[i]:null"  v-for="(col,i) in row8.right.tab.heads" :show-overflow-tooltip='true' :prop="col" :label="col"></el-table-column>
            </el-table>
          </div>
          <footer>
            <el-pagination
                  background
                  style="margin-top: 5px;"
                  @size-change="handleSizeChangeldbz8"
                  @current-change="handleCurrentChangeldbz8"
                  :current-page="row8.right.tab.pageNum"
                  :page-sizes="row8.right.tab.pageSizese"
                  :pager-count="5"
                  :page-size="row8.right.tab.pageSize"
                  layout="total, sizes, pager, jumper"
                  :total="row8.right.tab.total"
            >
            </el-pagination>
          </footer>
        </div>
      </div>
    </section>
    <section class="down-sectionse">
      <div class="hiddenchart">
        <div id="chartes11"></div>
      </div>
      <div class="hiddenchart">
        <div id="chartes12"></div>
      </div>
    </section>
    <section class="down-sectionse">
      <div class="hiddenchart">
        <div id="chartes2"></div>
      </div>
      <div class="hiddenchart">
        <div id="chartes3"></div>
      </div>
    </section>
    <section class="down-sectionse">
      <div class="hiddenchart">
        <div id="chartes4"></div>
      </div>
      <div class="hiddenchart">
        <div id="chartes41"></div>
      </div>
    </section>
    <section class="down-sectionse">
      <div class="hiddenchart">
        <div id="chartes51"></div>
      </div>
      <div class="hiddenchart">
        <div id="chartes52"></div>
      </div>
    </section>
    <section class="down-sectionse">
      <div class="hiddenchart">
        <div id="chartes61"></div>
      </div>
      <div class="hiddenchart">
        <div id="chartes62"></div>
      </div>
    </section>
    <section class="down-sectionse ">
      <div class="hiddenchart">
        <div id="chartes7"></div>
      </div>
      <div class="hiddenchart">
        <div id="chartes81"></div>
      </div>
    </section>
    <section class="down-sectionse ">
      <div class="hiddenchart">
        <div id="chartes82"></div>
      </div>
      <div class="hiddenchart">
        <div id="chartes9"></div>
      </div>
    </section>
    <section class="down-sectionse ">
      <div class="hiddenchart">
        <div id="chartes10"></div>
      </div>
      <div class="hiddenchart">
        <div id="chartes101"></div>
      </div>
    </section>
    <section class="down-sectionse ">
      <div class="hiddenchart">
        <div id="chartes102"></div>
      </div>
      <div class="hiddenchart">
        <div id="chartes103"></div>
      </div>
    </section>
    <section class="down-sectionse ">
      <div class="hiddenchart">
        <div id="chartes104"></div>
      </div>
      <div class="hiddenchart">
        <div id="hiddenchart1"></div>
      </div>
    </section>
    <section class="down-sectionse ">
      <div class="hiddenchart">
        <div id="hiddenchart2"></div>
      </div>
      <div class="hiddenchart">
        <div id="hiddenchart3"></div>
      </div>
    </section>
    </div>
    <el-dialog width="40%" :title="agetitle" :visible.sync="ageVisible" append-to-body>
      <div class="mianportrait" style="background: #fff;margin-top:20px;">
        <div style="width:100%;font-size: 16px;height: 600px;" v-if="this.agetitle == '年龄结构'&&this.bigtitlechange == true">
          <el-table :data="agedatase" v-loading="loadingse" stripe style="width: 100%">
            <el-table-column align="center" prop="personName" label="姓名" />
            <el-table-column align="center" prop="policeNo" label="警号" />
            <el-table-column align="center" prop="age" label="年龄" />
          </el-table>
          <el-button round @click="downloadReport(1,1,true)" style="margin: 15px;">下载表格</el-button>
          <el-pagination style="float: right;margin: 20px 40px 0 0;" background :total="totalRecord"
                         :page-size="10" @current-change="handleCurrentChangese" :current-page.sync="pageno" layout="total, prev, pager, next">
          </el-pagination>
        </div>
        <div style="width:100%;font-size: 16px;height: 600px;" v-if="this.agetitle == '教育培训'&&this.bigtitlechange == true">
          <el-table :data="agedatase" v-loading="loadingse" stripe style="width: 100%">
            <el-table-column align="center" prop="personName" label="姓名" />
            <el-table-column align="center" prop="policeNo" label="警号" />
            <el-table-column align="center" :show-overflow-tooltip='true' prop="unitName" label="部门" />
            <el-table-column align="center" prop="startTime" label="培训时间" />
            <el-table-column  align="center" :show-overflow-tooltip='true' prop="trainingCourse" label="培训班名称" />
          </el-table>
          <el-button round @click="downloadReport(2,1,true)" style="margin: 15px;">下载表格</el-button>
          <el-pagination style="float: right;margin: 20px 40px 0 0;" background :total="totalRecord"
                         :page-size="10" @current-change="handleCurrentChangese" :current-page.sync="pageno" layout="total, prev, pager, next">
          </el-pagination>
        </div>
        <div style="width:100%;font-size: 16px;height: 600px;" v-if="this.agetitle == '集体荣誉'&&this.bigtitlechange == true">
          <el-table :data="agedatase" v-loading="loadingse" stripe style="width: 100%">
            <el-table-column align="center"  :show-overflow-tooltip='true' prop="honorName" label="表彰称号" />
            <el-table-column align="center"  :show-overflow-tooltip='true' prop="deptName" label="部门" />
            <el-table-column align="center" prop="date" label="授奖时间" />
            <el-table-column align="center"  :show-overflow-tooltip='true' prop="awardDept" label="表彰部门" />
          </el-table>
          <el-button round @click="downloadReport(3,1,true)" style="margin: 15px;">下载表格</el-button>
          <el-pagination style="float: right;margin: 20px 40px 0 0;" background :total="totalRecord"
                         :page-size="10" @current-change="handleCurrentChangese" :current-page.sync="pageno" layout="total, prev, pager, next">
          </el-pagination>
        </div>
        <div style="width:100%;font-size: 16px;height: 600px;" v-if="this.agetitle == '个人荣誉'&&this.bigtitlechange == true">
          <el-table :data="agedatase" v-loading="loadingse" stripe style="width: 100%">
            <el-table-column align="center" prop="personName" label="姓名" />
            <el-table-column align="center" prop="policeNo" label="警号" />
            <el-table-column align="center"  :show-overflow-tooltip='true' prop="awardName" label="表彰称号" />
            <el-table-column align="center" prop="date" label="授奖时间" />
            <el-table-column align="center"  :show-overflow-tooltip='true' prop="awardDept" label="表彰部门"/>
          </el-table>
          <el-button round @click="downloadReport(4,1,true)" style="margin: 15px;">下载表格</el-button>
          <el-pagination style="float: right;margin: 20px 40px 0 0;" background :total="totalRecord"
                         :page-size="10" @current-change="handleCurrentChangese" :current-page.sync="pageno" layout="total, prev, pager, next">
          </el-pagination>
        </div>
        <div style="width:100%;font-size: 16px;height: 600px;" v-if="this.agetitle == '违纪案件'&&this.bigtitlechange == true">
          <el-table :data="agedatase" v-loading="loadingse" stripe style="width: 100%">
            <el-table-column align="center" prop="personName" label="姓名" />
            <el-table-column align="center" prop="policeNo" label="警号" />
            <el-table-column align="center"  :show-overflow-tooltip='true' prop="deptName" label="单位" />>
            <el-table-column align="center" prop="date" label="处分决定时间" />
            <el-table-column align="center"  :show-overflow-tooltip='true' prop="detail" label="问题性质" />
          </el-table>
          <el-button round @click="downloadReport(6,1,true)" style="margin: 15px;">下载表格</el-button>
          <el-pagination style="float: right;margin: 20px 40px 0 0;" background :total="totalRecord"
                         :page-size="10" @current-change="handleCurrentChangese" :current-page.sync="pageno" layout="total, prev, pager, next">
          </el-pagination>
        </div>
        <div style="width:100%;font-size: 16px;height: 600px;" v-if="this.bigtitlechange == false">
          <el-table :data="lindatalist.tab.data"  v-loading="loadingse" stripe style="width: 100%;">
            <el-table-column  align="center" :min-width="lindatalist.tab.width?lindatalist.tab.width[i]:null"  v-for="(col,i) in lindatalist.tab.heads" :show-overflow-tooltip='true' :prop="col" :label="col"></el-table-column>
          </el-table>
          <el-button round @click="downloadKPIse(agetitle,true)" style="margin: 15px;">下载表格</el-button>
          <el-pagination style="float: right;margin: 20px 40px 0 0;" background :total="lindatalist.tab.total"
                         :page-size="10" @current-change="handleCurrentChangeline" :current-page.sync="lindatalist.tab.pageNum" layout="total, prev, pager, next">
          </el-pagination>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import ChartAndTab from "./ChartAndTab";
// 引入命名空间帮助函数
import { createNamespacedHelpers } from "vuex";
import { downloadFile, downloadFileByUrl } from "@/utils";
// 引入dom转图片的库
import dom2Image from "dom-to-image";
// 加载固定模块的store
const { mapState } = createNamespacedHelpers("teamAnalysis");

import {
  getPersonDetail,
  getAgeDetail,
  getTrainDetail,
  getOrgHonorDetail,
  getPersonHonorDetail,
  getKPI,
  getBreakRuleCase,
  getTeamReport,
  getLeaderData,
  getHiddenData,
  getLeaderDetail
} from "@/api/developEffectApi";
import {
  agePie,
  agePiese,
  agerrujjn,
  lineWithYRight,
  colorfulBar,
  colorfulBares,
  lineround,
  barleft
} from "@/chart/echarts";
import { setTimeout } from "timers";

export default {
  components: { ChartAndTab },
  props: ["AnalysisTitle"],
  data() {
    let that = this;
    return {
      KPIMenuOptions: [],
      KPIMenu: "",
      KPIMenuProps: {
        //                    multiple: true
        checkStrictly: true
      },
      row1: {
        count: 0,
        leaderCount: 0,
        policeCount: 0,
        partyCount: 0
      },
      row2: {
        left: {
          chart: {
            young: 0,
            middle: 0,
            old: 0,
            youngPercent: "",
            middlePercent: "",
            oldPercent: ""
          },
          tab: {
            typeArr: ["35岁以下", "35-50岁", "50岁以上"],
            selectedIndex: 0,
            percent: "",
            data: [],
            pageNum: 1,
            pageSize: 10,
            total: 50
          }
        },
        right: {
          chart: {
            showContent: false,
            content1: "",
            content2: ""
          },
          tab: {
            data: [],
            pageNum: 1,
            pageSize: 10,
            total: 50
          }
        }
      },
      row3: {
        left: {
          chart: {
            showContent: false,
            content1: "",
            content2: "",
            content3: ""
          },
          tab: {
            data: [],
            pageNum: 1,
            pageSize: 10,
            total: 50
          }
        },
        right: {
          chart: {
            showContent: false,
            content1: "",
            content2: "",
            content3: ""
          },
          tab: {
            data: [],
            pageNum: 1,
            pageSize: 10,
            total: 50
          }
        }
      },
      row4: {
        left: {
          chart: {
            showContent: false,
            content1: "",
            content2: "",
            content3: ""
          },
          tab: {
            data: [],
            pageNum: 1,
            pageSize: 10,
            total: 50
          }
        },
        right: {
          chart: {
            showContent: false,
            content1: "",
            content2: "",
            content3: "",
            leftSec: "",
            rightSec: ""
          },
          tab: {
            data: [],
            pageNum: 1,
            pageSize: 10,
            total: 50
          }
        }
      },
      row5: {
        left: {
          chart: {
            showContent: false,
            content1: "",
            content2: "",
            content3: ""
          },
          tab: {
            data: [],
            dataz: [],
            pageNum: 1,
            pageSize: 10,
            pageSizese: [10, 20, 30, 50],
            total: 50,
            heads: [],
            width: []
          }
        },
        right: {
          chart: {
            showContent: false,
            content1: "",
            content2: "",
            content3: ""
          },
          tab: {
            data: [],
            dataz: [],
            pageNum: 1,
            pageSize: 10,
            pageSizese: [10, 20, 30, 50],
            total: 50,
            heads: [],
            width: []
          }
        }
      },
      row6: {
        left: {
          chart: {
            showContent: false,
            content1: "",
            content2: "",
            content3: ""
          },
          tab: {
            data: [],
            dataz: [],
            pageNum: 1,
            pageSize: 10,
            pageSizese: [10, 20, 30, 50],
            total: 50,
            heads: [],
            width: []
          }
        },
        right: {
          chart: {
            showContent: false,
            content1: "",
            content2: "",
            content3: ""
          },
          tab: {
            data: [],
            dataz: [],
            pageNum: 1,
            pageSize: 10,
            pageSizese: [10, 20, 30, 50],
            total: 50,
            heads: [],
            width: []
          }
        }
      },
      row7: {
        left: {
          chart: {
            showContent: false,
            content1: "",
            content2: "",
            content3: ""
          },
          tab: {
            data: [],
            dataz: [],
            pageNum: 1,
            pageSize: 10,
            pageSizese: [10, 20, 30, 50],
            total: 50,
            heads: [],
            width: []
          }
        },
        right: {
          chart: {
            showContent: false,
            content1: "",
            content2: "",
            content3: ""
          },
          tab: {
            data: [],
            dataz: [],
            pageNum: 1,
            pageSize: 10,
            pageSizese: [10, 20, 30, 50],
            total: 50,
            heads: [],
            width: []
          }
        }
      },
      row8: {
        left: {
          chart: {
            showContent: false,
            content1: "",
            content2: "",
            content3: ""
          },
          tab: {
            data: [],
            dataz: [],
            pageNum: 1,
            pageSize: 10,
            pageSizese: [10, 20, 30, 50],
            total: 50,
            heads: [],
            width: []
          }
        },
        right: {
          chart: {
            showContent: false,
            content1: "",
            content2: "",
            content3: ""
          },
          tab: {
            data: [],
            dataz: [],
            pageNum: 1,
            pageSize: 10,
            pageSizese: [10, 20, 30, 50],
            total: 50,
            heads: [],
            width: []
          }
        }
      },
      lindatalist: {
        tab: {
          data: [],
          dataz: [],
          pageNum: 1,
          pageSize: 10,
          pageSizese: [10, 20, 30, 50],
          total: 50,
          heads: [],
          width: []
        }
      },
      agedatase: [],
      agetitle: "年龄结构",
      ageVisible: false,
      loadingse: true,
      totalRecord: 0,
      pageno: 1,
      dataIndex: 0,
      multipleSelection: "",
      agees: "",
      traines: "",
      personHonores: "",
      downseloads: 0,
      targetNum: 20,
      loadingsede: false,
      datatablese: [],
      pageSizes: [10, 20, 30, 50],
      pageSizese: 10,
      pageNumse: 1,
      bigtitlechange: true,
      titlemins: "",
      titleminse: "",
      selectedIndex: 0,
      selectedIndex1: 0,
      selectedIndex2: 0,
      selectedIndex3: 0,
      selectedIndex4: 0,
      selectedIndex5: 0,
      selectedIndex6: 0,
      selectedIndex7: 0,
      titlechangese: 0,
      titlechangese1: 0,
      titlechangese2: 0,
      titlechangese3: 0,
      titlechangese4: 0,
      titlechangese5: 0,
      titlechangese6: 0,
      titlechangese7: 0,
      extraTypese: ""
    };
  },
  methods: {
    async handleKPIMenuChange(value, isDetail = 1, pageNum = 1, pageSize = 10) {
      const _this = this;
      const params = {
        unitId: _this.unitId,
        isDownload: 0,
        isDetail,
        pageNum,
        pageSize,
        menu: value
      };
      let result;
      result = await getKPI(params);
      if (result.code == 0) {
        _this.row4.left.tab.data = result.data;
        _this.row4.left.tab.total = result.data.length;
        _this.datatablese = result.data.slice(0, 10);
      } else {
        _this.row4.left.tab.data = [];
        _this.row4.left.tab.total = 0;
        _this.datatablese = [];
      }
    },
    loadKPIMenu() {
      let that = this;
      this.$axios
        .post("/teamBuild/getKPIMenu")
        .then(res => {
          that.KPIMenuOptions = JSON.parse(res);
          that.KPIMenu = that.KPIMenuOptions[0].value;
          that.handleKPIMenuChange([that.KPIMenu]);
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    async initRow1() {
      const params = { unitId: this.unitId };
      const result = await getPersonDetail(params);
      this.row1 = result.data;
    },
    // 子组件的切换事件
    initChartAndTab(isDetail, type) {
      const _this = this;
      const reload = _this.strategy[type] || _this.strategy["default"];
      reload(isDetail);
    },
    async initRow2Left(isDetail = 0, pageNum = 1, pageSize = 10) {
      const _this = this;
      const params = {
        unitId: _this.unitId,
        isDetail,
        pageNum,
        pageSize,
        isDownload: 0,
        type: _this.row2.left.tab.selectedIndex
      };
      const result = await getAgeDetail(params);
      this.row2.left.chart = result.data;
      if (isDetail === 0) {
        const data = [];
        data.push({
          name: "35岁以下",
          value: result.data.young,
          percent: result.data.youngPercent
        });
        data.push({
          name: "35-50岁",
          value: result.data.middle,
          percent: result.data.middlePercent
        });
        data.push({
          name: "50岁以上",
          value: result.data.old,
          percent: result.data.oldPercent
        });
        const idse = "hiddenchart1";
        const paramses = { data, id: idse };
        agePie(paramses);
        const chartes = _this.$echarts.init(document.getElementById(idse));
        chartes.on("finished", function() {
          _this.agees = _this.$echarts
            .getInstanceByDom(document.getElementById("hiddenchart1"))
            .getDataURL();
          _this.downseloads++;
        });
        if (this.bigtitlechange == true) {
          const id = "chart1";
          const params = { data, id };
          agePie(params);
          const chart = _this.$echarts.init(document.getElementById(id));
          chart.on("finished", function() {
            _this.agees = _this.$echarts
              .getInstanceByDom(document.getElementById("chart1"))
              .getDataURL();
            _this.downseloads++;
          });
          chart.on("click", function(params11) {
            _this.loadingse = true;
            _this.ageVisible = true;
            _this.agetitle = "年龄结构";
            _this.pageno = 1;
            _this.dataIndex = params11.dataIndex;
            _this.$axios
              .post("/teamBuild/getAgeDetail", {
                unitId: _this.unitId,
                isDetail: 1,
                pageNum,
                pageSize,
                isDownload: 0,
                type: params11.dataIndex
              })
              .then(res => {
                if (res.code == 0) {
                  _this.loadingse = false;
                  _this.agedatase = res.data.detail;
                  _this.totalRecord = res.data.total;
                } else {
                  this.$message(res.message);
                }
              })
              .catch(function(error) {
                console.log(error);
              });
          });
        }
      } else {
        const { total, detail, percent } = result.data;
        _this.row2.left.tab.data = detail;
        _this.row2.left.tab.percent = percent;
        _this.row2.left.tab.total = total;
      }
    },
    async initRow2Right(isDetail = 0, pageNum = 1, pageSize = 10) {
      const _this = this;
      const params = {
        unitId: _this.unitId,
        isDetail,
        pageNum,
        pageSize,
        isDownload: 0
      };
      const result = await getTrainDetail(params);
      if (isDetail === 0) {
        const { chart, content1, content2 } = result.data;
        const showContent = !(content1 || content2);
        _this.row2.right.chart = { content1, content2, showContent };
        //影藏的chart3
        const idse = "hiddenchart2";
        const paramses = { data: chart, id: idse };
        lineWithYRight(paramses);
        const chartes3 = _this.$echarts.init(document.getElementById(idse));
        chartes3.on("finished", function() {
          _this.traines = _this.$echarts
            .getInstanceByDom(document.getElementById("hiddenchart2"))
            .getDataURL();
          _this.downseloads++;
        });
        if (this.bigtitlechange == true) {
          const id = "chart2";
          const params = { data: chart, id };
          lineWithYRight(params);
          const chart2 = _this.$echarts.init(document.getElementById(id));
          chart2.on("finished", function() {
            _this.traines = _this.$echarts
              .getInstanceByDom(document.getElementById("chart2"))
              .getDataURL();
            _this.downseloads++;
          });
          chart2.on("click", function(params11) {
            _this.loadingse = true;
            _this.ageVisible = true;
            _this.agetitle = "教育培训";
            _this.pageno = 1;
            _this.dataIndex = params11.name;
            _this.$axios
              .post("/teamBuild/getTrainDetail", {
                unitId: _this.unitId,
                isDetail: 1,
                pageNum,
                pageSize,
                isDownload: 0,
                type: params11.name
              })
              .then(res => {
                if (res.code == 0) {
                  _this.loadingse = false;
                  _this.agedatase = res.data.detail;
                  _this.totalRecord = res.data.total;
                } else {
                  this.$message(res.message);
                }
              })
              .catch(function(error) {
                console.log(error);
              });
          });
        }
      } else {
        const { total, detail } = result.data;
        _this.row2.right.tab.data = detail;
        _this.row2.right.tab.total = total;
      }
    },
    async initRow3Left(isDetail = 0, pageNum = 1, pageSize = 10) {
      const _this = this;
      const params = {
        unitId: _this.unitId,
        isDetail,
        isDownload: 0,
        pageNum,
        pageSize
      };
      const result = await getOrgHonorDetail(params);
      if (isDetail === 0) {
        const { chart, content1, content2, content3 } = result.data;
        const showContent = !(content1 || content2 || content3);
        _this.row3.left.chart = { content1, content2, content3, showContent };
        const id = "chart3";
        const params = { data: chart, id, newLine: true };
        colorfulBar(params);
        const chart2 = this.$echarts.init(document.getElementById(id));
        chart2.on("click", function(params11) {
          _this.loadingse = true;
          _this.ageVisible = true;
          _this.agetitle = "集体荣誉";
          _this.pageno = 1;
          _this.dataIndex = params11.name;
          _this.$axios
            .post("/teamBuild/getOrgHonorDetail", {
              unitId: _this.unitId,
              isDetail: 1,
              pageNum,
              pageSize,
              isDownload: 0,
              type: params11.name
            })
            .then(res => {
              if (res.code == 0) {
                _this.loadingse = false;
                _this.agedatase = res.data.detail;
                _this.totalRecord = res.data.total;
              } else {
                this.$message(res.message);
              }
            })
            .catch(function(error) {
              console.log(error);
            });
        });
      } else {
        const { total, detail } = result.data;
        _this.row3.left.tab.data = detail;
        _this.row3.left.tab.total = total;
      }
    },
    async initRow3Right(isDetail = 0, pageNum = 1, pageSize = 10) {
      const _this = this;
      const params = {
        unitId: _this.unitId,
        isDownload: 0,
        isDetail,
        pageNum,
        pageSize
      };
      const result = await getPersonHonorDetail(params);
      if (isDetail === 0) {
        const { chart, content1, content2, content3 } = result.data;
        const showContent = !(content1 || content2 || content3);
        _this.row3.right.chart = { content1, content2, content3, showContent };
        const idse = "hiddenchart3";
        const paramses = { data: chart, id: idse, newLine: true };
        colorfulBar(paramses);
        const chartes2 = _this.$echarts.init(document.getElementById(idse));
        chartes2.on("finished", function() {
          _this.personHonores = _this.$echarts
            .getInstanceByDom(document.getElementById("hiddenchart3"))
            .getDataURL();
          _this.downseloads++;
        });
        if (this.bigtitlechange == true) {
          const id = "chart4";
          const params = { data: chart, id, newLine: true };
          colorfulBar(params);
          const chart2 = _this.$echarts.init(document.getElementById(id));
          chart2.on("finished", function() {
            _this.personHonores = _this.$echarts
              .getInstanceByDom(document.getElementById("chart4"))
              .getDataURL();
            _this.downseloads++;
          });
          chart2.on("click", function(params11) {
            _this.loadingse = true;
            _this.ageVisible = true;
            _this.agetitle = "个人荣誉";
            _this.pageno = 1;
            _this.dataIndex = params11.name;
            _this.$axios
              .post("/teamBuild/getPersonHonorDetail", {
                unitId: _this.unitId,
                isDetail: 1,
                pageNum,
                pageSize,
                isDownload: 0,
                type: params11.name
              })
              .then(res => {
                if (res.code == 0) {
                  _this.loadingse = false;
                  _this.agedatase = res.data.detail;
                  _this.totalRecord = res.data.total;
                } else {
                  this.$message(res.message);
                }
              })
              .catch(function(error) {
                console.log(error);
              });
          });
        }
      } else {
        const { total, detail } = result.data;
        _this.row3.right.tab.data = detail;
        _this.row3.right.tab.total = total;
      }
    },
    async initRow4Left(isDetail = 1, pageNum = 1, pageSize = 10) {
      //                const _this = this;
      //                const params = {
      //                    unitId: _this.unitId,
      //                    isDownload: 0,
      //                    isDetail,
      //                    pageNum,
      //                    pageSize
      //                };
      //                let result;
      //                result = await getKPI(params);
      //                if (result.code == 0) {
      //                    _this.row4.left.tab.data = result.data;
      //                    _this.row4.left.tab.total = result.data.length;
      //                    _this.datatablese = result.data.slice(0, 10);
      //                } else {
      //                    _this.row4.left.tab.data = [];
      //                    _this.row4.left.tab.total = 0;
      //                    _this.datatablese = [];
      //                }
      // try {
      //   result = await Promise.all(getKPI(params));
      //   console.log(result);
      // } catch (error) {
      //   console.error("接口请求失败，进测试数据！");
      //   result = { data: { chart: [] } };
      // }
      // if (isDetail === 0) {
      //   const { chart, content1, content2, content3 } = result.data;
      //   const showContent = !(content1 || content2 || content3);
      //   _this.row4.left.chart = { content1, content2, content3, showContent };
      //   const id = "chart5";
      //   const params = { data: chart, id };
      //   colorfulBar(params);
      // } else {
      // _this.row4.left.tab.data = result.data;
      // _this.row4.left.tab.total = result.data.length;
      // _this.datatablese = result.data.slice(0,10);
      // }
    },
    async initRow4Right(isDetail = 0, pageNum = 1, pageSize = 10) {
      const _this = this;
      const params = {
        unitId: _this.unitId,
        isDownload: 0,
        isDetail,
        pageNum,
        pageSize
      };
      const result = await getBreakRuleCase(params);
      if (isDetail === 0) {
        const { chart, content1, content2, content3 } = result.data;
        const showContent = !(content1 || content2 || content3);
        _this.row4.right.chart = { content1, content2, content3, showContent };
        const id = "chart6";
        const params = { data: chart.personal, id };
        colorfulBar(params);
        const chart2 = this.$echarts.init(document.getElementById(id));
        chart2.on("click", function(params11) {
          _this.loadingse = true;
          _this.ageVisible = true;
          _this.agetitle = "违纪案件";
          _this.pageno = 1;
          _this.dataIndex = params11.name;
          _this.$axios
            .post("/teamBuild/getBreakRuleCase", {
              unitId: _this.unitId,
              isDetail: 1,
              pageNum,
              pageSize,
              isDownload: 0,
              type: params11.name
            })
            .then(res => {
              if (res.code == 0) {
                _this.loadingse = false;
                _this.agedatase = res.data.detail;
                _this.totalRecord = res.data.total;
              } else {
                this.$message(res.message);
              }
            })
            .catch(function(error) {
              console.log(error);
            });
        });
      } else {
        const { total, detail } = result.data;
        _this.row4.right.tab.data = detail;
        _this.row4.right.tab.total = total;
      }
    },
    // 第二行左侧switch事件
    row2Switch(index) {
      if (this.row2.left.tab.selectedIndex !== index) {
        this.row2.left.tab.selectedIndex = index;
        // this.dataIndex = index;
        this.initRow2Left(1);
      }
    },
    // pageSize变化
    handleSizeChange(pageSize, pageNum, type) {
      // todo
      const _this = this;
      const reload = _this.strategy[type] || _this.strategy["default"];
      reload(1, pageNum, pageSize);
    },
    handleCurrentChange(pageNum, pageSize, type) {
      const _this = this;
      const reload = _this.strategy[type] || _this.strategy["default"];
      reload(1, pageNum, pageSize);
    },
    // 关闭面板按钮
    closePanel() {
      this.$emit("close-analysis");
    },
    // 下载报告
    async downloadReport(type, isDetail, filter, titles) {
      const strategy = {
        1: {
          cb: getAgeDetail,
          title: "年龄结构"
        },
        2: {
          cb: getTrainDetail,
          title: "教育培训"
        },
        3: {
          cb: getOrgHonorDetail,
          title: "集体荣誉"
        },
        4: {
          cb: getPersonHonorDetail,
          title: "个人荣誉"
        },
        5: {
          cb: getKPI,
          title: "政工绩效"
        },
        6: {
          cb: getBreakRuleCase,
          title: "违纪案件"
        },
        7: {
          cb: getLeaderDetail,
          title: "领导班子"
        },
        defalut: {
          cb: () => {},
          title: null
        }
      };
      let downloadObj = strategy[type] || strategy["defalut"];
      let title = downloadObj.title;
      let _this = this;
      if (isDetail === 0) {
        let dom = document.querySelector(`.main-${type} .chart`);
        if (type === 7) {
          strategy[7].title = titles.title;
          title = titles.title;
          let typeses = "";
          if (titles.category == "duty") {
            typeses = "1";
          } else if (titles.category == "age") {
            typeses = "2";
          } else if (titles.category == "sex") {
            typeses = "3";
          } else if (titles.category == "education") {
            typeses = "4";
          } else if (titles.category == "nation") {
            typeses = "5";
          } else if (titles.category == "appointmentTime") {
            typeses = "6";
          } else if (titles.category == "entryPeriod") {
            typeses = "7";
          } else if (titles.category == "rule") {
            typeses = "8";
          }
          dom = document.querySelector(`.loadecharts-${typeses}`);
        }
        let img = await dom2Image.toPng(dom);
        downloadFileByUrl(img, `${title}-图表`, ".png");
      } else {
        let params = {
          unitId: _this.unitId,
          pageNum: 1,
          pageSize: 10,
          isDownload: 1,
          type: _this.dataIndex
        };
        if (!filter) {
          params.type = undefined;
        }
        if (type === 7) {
          params = {
            unitId: titles.unitId,
            type: titles.type,
            isDownload: titles.isDownload,
            category: titles.category,
            sub: titles.sub,
            extraType: titles.extraTypese
          };
          title = titles.title;
        }
        if (type === 5) {
          if (typeof _this.KPIMenu == "string") {
            params.menu = [_this.KPIMenu];
          } else {
            params.menu = _this.KPIMenu;
          }
        }

        try {
          const file = await downloadObj.cb(params, {
            responseType: "arraybuffer"
          });
          downloadFile(file, "application/vnd.ms-excel", `${title}-列表.xls`);
        } catch (error) {
          throw error;
        }
      }
    },
    //政工绩效翻页
    handleSizeChangezgjx(val) {
      this.pageSizese = val;
      let eNums1 = (this.pageNumse - 1) * this.pageSizese;
      let eNums2 = this.pageNumse * this.pageSizese;
      this.datatablese = this.row4.left.tab.data.slice(eNums1, eNums2);
    },
    handleCurrentChangezgjx(val) {
      this.pageNumse = val;
      let eNums1 = (this.pageNumse - 1) * this.pageSizese;
      let eNums2 = this.pageNumse * this.pageSizese;
      this.datatablese = this.row4.left.tab.data.slice(eNums1, eNums2);
    },
    // 导出报告
    async exportReport() {
      this.loadingsede = true;
      let deptLevel = localStorage.getItem("deptLevel");
      const _this = this;
      const age = _this.agees;
      const train = _this.traines;
      const personHonor = _this.personHonores;
      const superLeaderDuty = _this.$echarts
        .getInstanceByDom(document.getElementById("chartes11"))
        .getDataURL();
      var subLeaderDuty = "";
      if (
        _this.$echarts.getInstanceByDom(document.getElementById("chartes12")) &&
        deptLevel != "3"
      ) {
        subLeaderDuty = _this.$echarts
          .getInstanceByDom(document.getElementById("chartes12"))
          .getDataURL();
      }
      const leaderPieAge = _this.$echarts
        .getInstanceByDom(document.getElementById("chartes2"))
        .getDataURL();
      const leaderColumnAge = _this.$echarts
        .getInstanceByDom(document.getElementById("chartes3"))
        .getDataURL();
      const leaderSex = _this.$echarts
        .getInstanceByDom(document.getElementById("chartes4"))
        .getDataURL();
      const leaderColumnSex = _this.$echarts
        .getInstanceByDom(document.getElementById("chartes41"))
        .getDataURL();
      const superLeaderEdution = _this.$echarts
        .getInstanceByDom(document.getElementById("chartes51"))
        .getDataURL();
      var subLeaderEdution;
      if (
        _this.$echarts.getInstanceByDom(document.getElementById("chartes52")) &&
        deptLevel != "3"
      ) {
        subLeaderEdution = _this.$echarts
          .getInstanceByDom(document.getElementById("chartes52"))
          .getDataURL();
      }

      const superLeaderNation = _this.$echarts
        .getInstanceByDom(document.getElementById("chartes61"))
        .getDataURL();
      var subLeaderNation;
      if (
        _this.$echarts.getInstanceByDom(document.getElementById("chartes62")) &&
        deptLevel != "3"
      ) {
        subLeaderNation = _this.$echarts
          .getInstanceByDom(document.getElementById("chartes62"))
          .getDataURL();
      }
      const leaderMainPost = _this.$echarts
        .getInstanceByDom(document.getElementById("chartes7"))
        .getDataURL();
      const leaderAssistantPost = _this.$echarts
        .getInstanceByDom(document.getElementById("chartes9"))
        .getDataURL();

      const superLeaderEntryPeriod = _this.$echarts
        .getInstanceByDom(document.getElementById("chartes81"))
        .getDataURL();

      var subLeaderEntryPeriod;
      if (
        _this.$echarts.getInstanceByDom(document.getElementById("chartes82")) &&
        deptLevel != "3"
      ) {
        subLeaderEntryPeriod = _this.$echarts
          .getInstanceByDom(document.getElementById("chartes82"))
          .getDataURL();
      }
      var columnSex;
      if (
        _this.$echarts.getInstanceByDom(document.getElementById("chartes10"))
      ) {
        columnSex = _this.$echarts
          .getInstanceByDom(document.getElementById("chartes10"))
          .getDataURL();
      }
      var workTime;
      if (
        _this.$echarts.getInstanceByDom(document.getElementById("chartes101"))
      ) {
        workTime = _this.$echarts
          .getInstanceByDom(document.getElementById("chartes101"))
          .getDataURL();
      }
      var educationImg;
      if (
        _this.$echarts.getInstanceByDom(document.getElementById("chartes102"))
      ) {
        educationImg = _this.$echarts
          .getInstanceByDom(document.getElementById("chartes102"))
          .getDataURL();
      }
      var leaderRuleTrend;
      if (
        _this.$echarts.getInstanceByDom(document.getElementById("chartes103"))
      ) {
        leaderRuleTrend = _this.$echarts
          .getInstanceByDom(document.getElementById("chartes103"))
          .getDataURL();
      }
      var ruleTrend;
      if (
        _this.$echarts.getInstanceByDom(document.getElementById("chartes104"))
      ) {
        ruleTrend = _this.$echarts
          .getInstanceByDom(document.getElementById("chartes104"))
          .getDataURL();
      }
      const unitId = this.unitId;
      const params = {
        unitId,
        age,
        train,
        personHonor,
        superLeaderDuty,
        subLeaderDuty,
        leaderPieAge,
        leaderColumnAge,
        leaderSex,
        leaderColumnSex,
        superLeaderEdution,
        subLeaderEdution,
        superLeaderNation,
        subLeaderNation,
        leaderMainPost,
        leaderAssistantPost,
        superLeaderEntryPeriod,
        subLeaderEntryPeriod,
        columnSex,
        workTime,
        educationImg,
        leaderRuleTrend,
        ruleTrend
      };
      const result = await getTeamReport(params,{
          responseType: "arraybuffer"
      });
      this.loadingsede = false;
      downloadFile(result, "application/vnd.ms-word", `${this.AnalysisTitle}-报告.docx`);

      this.getPersonLog(params, this.idNumber);
    },
    async initPage() {
      this.getLeaderData();
      this.getHiddenData();
      this.initRow1();
      this.initRow2Left();
      this.initRow2Right();
      this.initRow3Left();
      this.initRow3Right();
      this.initRow4Left();
      this.initRow4Right();
    },
    //表格翻页
    handleCurrentChangese(parmes) {
      if (this.agetitle == "年龄结构") {
        this.loadingse = true;
        this.$axios
          .post("/teamBuild/getAgeDetail", {
            unitId: this.unitId,
            isDetail: 1,
            pageNum: parmes,
            pageSize: 10,
            isDownload: 0,
            type: this.dataIndex
          })
          .then(res => {
            if (res.code == 0) {
              this.loadingse = false;
              this.agedatase = res.data.detail;
              this.totalRecord = res.data.total;
            } else {
              this.$message(res.message);
            }
          })
          .catch(function(error) {
            console.log(error);
          });
      } else if (this.agetitle == "教育培训") {
        this.loadingse = true;
        this.$axios
          .post("/teamBuild/getTrainDetail", {
            unitId: this.unitId,
            isDetail: 1,
            pageNum: parmes,
            pageSize: 10,
            isDownload: 0,
            type: this.dataIndex
          })
          .then(res => {
            if (res.code == 0) {
              this.loadingse = false;
              this.agedatase = res.data.detail;
              this.totalRecord = res.data.total;
            } else {
              this.$message(res.message);
            }
          })
          .catch(function(error) {
            console.log(error);
          });
      } else if (this.agetitle == "集体荣誉") {
        this.loadingse = true;
        this.$axios
          .post("/teamBuild/getOrgHonorDetail", {
            unitId: this.unitId,
            isDetail: 1,
            pageNum: parmes,
            pageSize: 10,
            isDownload: 0,
            type: this.dataIndex
          })
          .then(res => {
            if (res.code == 0) {
              this.loadingse = false;
              this.agedatase = res.data.detail;
              this.totalRecord = res.data.total;
            } else {
              this.$message(res.message);
            }
          })
          .catch(function(error) {
            console.log(error);
          });
      } else if (this.agetitle == "个人荣誉") {
        this.loadingse = true;
        this.$axios
          .post("/teamBuild/getPersonHonorDetail", {
            unitId: this.unitId,
            isDetail: 1,
            pageNum: parmes,
            pageSize: 10,
            isDownload: 0,
            type: this.dataIndex
          })
          .then(res => {
            if (res.code == 0) {
              this.loadingse = false;
              this.agedatase = res.data.detail;
              this.totalRecord = res.data.total;
            } else {
              this.$message(res.message);
            }
          })
          .catch(function(error) {
            console.log(error);
          });
      } else if (this.agetitle == "违纪案件") {
        this.loadingse = true;
        this.$axios
          .post("/teamBuild/getBreakRuleCase", {
            unitId: this.unitId,
            isDetail: 1,
            pageNum: parmes,
            pageSize: 10,
            isDownload: 0,
            type: this.dataIndex
          })
          .then(res => {
            if (res.code == 0) {
              this.loadingse = false;
              this.agedatase = res.data.detail;
              this.totalRecord = res.data.total;
            } else {
              this.$message(res.message);
            }
          })
          .catch(function(error) {
            console.log(error);
          });
      }
    },
    //下载表格
    downloadKPI() {
      this.downloadReport(5, 1);
    },
    //隐藏eacharts图
    initChartes1(resultse) {
      let _this = this;
      colorfulBar({
        data: resultse.super,
        id: "chartes11",
        newLine: true,
        transform: true,
        percent: true
      }).on("finished", function() {
        _this.downseloads++;
      });
      if (resultse.sub) {
        colorfulBar({
          data: resultse.sub,
          id: "chartes12",
          newLine: true,
          transform: true,
          percent: true
        }).on("finished", function() {
          _this.downseloads++;
        });
      }
    },
    initChartes2(resultse) {
      const id = "chartes2";
      let optionse = [];
      optionse = [];
      for (let key in resultse) {
        optionse.push({ value: resultse[key], name: key });
      }
      const params = { data: optionse, id };
      agePiese(params);
      let _this = this;
      const chartse = this.$echarts.init(document.getElementById(id));
      chartse.on("finished", function() {
        _this.downseloads++;
      });
    },
    initChartes3(resultse) {
      const id = "chartes3";
      let optionse = {
        data: [],
        xAxisdata: [],
        seriesdata: []
      };
      optionse.data = [];
      optionse.xAxisdata = [];
      optionse.seriesdata = [];
      let colordatase = ["#2ec6c8", "#b7a3de"];
      let max = 0;
      for (let key in resultse) {
        optionse.data.push(key);
        optionse.seriesdata.push({
          name: key,
          type: "bar",
          barMinHeight: 5,
          data: [],
          color: colordatase[max]
        });
        optionse.xAxisdata = [];
        let sum = 0;
        for (let kese in resultse[key]) {
          sum += Number(resultse[key][kese]);
        }
        for (let kese in resultse[key]) {
          optionse.xAxisdata.push(kese);
          optionse.seriesdata.push();
          for (let kes = 0; kes < optionse.seriesdata.length; kes++) {
            if (optionse.seriesdata[kes].name == key) {
              optionse.seriesdata[kes].data.push(resultse[key][kese]);
              optionse.seriesdata[kes].label = {
                show: true,
                position: "top",
                formatter(param) {
                  if (sum > 0) {
                    return (
                      param.data +
                      "人" +
                      "\n" +
                      (Number(param.data) / sum * 100).toFixed(2) +
                      "%"
                    );
                  } else {
                    return name + "\n" + param.data;
                  }
                }
              };
            }
          }
        }
        max++;
      }
      const params = { data: optionse, id };
      colorfulBares(params);
      let _this = this;
      const chartse = this.$echarts.init(document.getElementById(id));
      chartse.on("finished", function() {
        _this.downseloads++;
      });
    },
    initChartes41(resultse) {
      const id = "chartes41";
      let optionse = {
        data: [],
        xAxisdata: [],
        seriesdata: []
      };
      optionse.data = [];
      optionse.xAxisdata = [];
      optionse.seriesdata = [];
      let colordatase = ["#2ec6c8", "#b7a3de"];
      let max = 0;
      for (let key in resultse) {
        optionse.data.push(key);
        optionse.seriesdata.push({
          name: key,
          type: "bar",
          barMinHeight: 5,
          data: [],
          color: colordatase[max]
        });
        optionse.xAxisdata = [];
        for (let kese in resultse[key]) {
          optionse.xAxisdata.push(kese);
          optionse.seriesdata.push();
          for (let kes = 0; kes < optionse.seriesdata.length; kes++) {
            if (optionse.seriesdata[kes].name == key) {
              optionse.seriesdata[kes].data.push(resultse[key][kese]);
              optionse.seriesdata[kes].label = {
                show: true,
                position: "top",
                formatter: "{c}人"
              };
            }
          }
        }
        max++;
      }
      const params = { data: optionse, id };
      colorfulBares(params);
      let _this = this;
      const chartse = this.$echarts.init(document.getElementById(id));
      chartse.on("finished", function() {
        _this.downseloads++;
      });
    },
    initChartes4(resultse) {
      const id = "chartes4";
      let optionse = [];
      optionse = [];
      for (let key in resultse) {
        optionse.push({ value: resultse[key], name: key });
      }
      const params = { data: optionse, id };
      agePiese(params);
      let _this = this;
      const chartse = this.$echarts.init(document.getElementById(id));
      chartse.on("finished", function() {
        _this.downseloads++;
      });
    },
    initChartes5(resultse) {
      let _this = this;
      colorfulBar({
        data: resultse.super,
        id: "chartes51",
        newLine: true,
        transform: true,
        percent: true
      }).on("finished", function() {
        _this.downseloads++;
      });
      if (resultse.sub) {
        colorfulBar({
          data: resultse.sub,
          id: "chartes52",
          newLine: true,
          transform: true,
          percent: true
        }).on("finished", function() {
          _this.downseloads++;
        });
      }
    },
    initChartes6(resultse) {
      let _this = this;
      colorfulBar({
        data: resultse.super,
        id: "chartes61",
        newLine: true,
        transform: true,
        percent: true
      }).on("finished", function() {
        _this.downseloads++;
      });
      if (resultse.sub) {
        colorfulBar({
          data: resultse.sub,
          id: "chartes62",
          newLine: true,
          transform: true,
          percent: true
        }).on("finished", function() {
          _this.downseloads++;
        });
      }
    },
    initChartes7(resultse) {
      const id = "chartes7";
      let optionse = {
        data: [],
        xAxisdata: [],
        seriesdata: []
      };
      let minbar = 0;
      let colorses = ["#2ec6c8", "#b7a3de"];
      for (let key in resultse["正职"]) {
        optionse.xAxisdata.push(key);
        if (minbar == 0) {
          let mincol = 0;
          for (let kese in resultse["正职"][key]) {
            optionse.data.push(kese);
            optionse.seriesdata.push({
              name: kese,
              type: "bar",
              data: [resultse["正职"][key][kese]],
              color: colorses[mincol]
            });
            mincol++;
          }
        } else {
          for (let keses in resultse["正职"][key]) {
            for (let k = 0; k < optionse.seriesdata.length; k++) {
              if (keses == optionse.seriesdata[k].name) {
                optionse.seriesdata[k].data.push(resultse["正职"][key][keses]);
                optionse.seriesdata[k].label = {
                  show: true,
                  position: "top",
                  formatter: "{c}人"
                };
              }
            }
          }
        }
        minbar++;
      }
      const params = { data: optionse, id };
      colorfulBares(params);
      let _this = this;
      const chartse = this.$echarts.init(document.getElementById(id));
      chartse.on("finished", function() {
        _this.downseloads++;
      });

      const ids = "chartes9";
      let option = {
        data: [],
        xAxisdata: [],
        seriesdata: []
      };
      let minbars = 0;
      let colorsess = ["#2ec6c8", "#b7a3de"];
      for (let mkey in resultse["副职"]) {
        option.xAxisdata.push(mkey);
        if (minbars == 0) {
          let mincols = 0;
          for (let mkese in resultse["副职"][mkey]) {
            option.data.push(mkese);
            option.seriesdata.push({
              name: mkese,
              type: "bar",
              data: [resultse["副职"][mkey][mkese]],
              color: colorsess[mincols]
            });
            option.seriesdata.label = {
              show: true,
              position: "top",
              formatter: "{c}人"
            };
            mincols++;
          }
        } else {
          for (let mkeses in resultse["副职"][mkey]) {
            for (let mk = 0; mk < option.seriesdata.length; mk++) {
              if (mkeses == option.seriesdata[mk].name) {
                option.seriesdata[mk].data.push(resultse["副职"][mkey][mkeses]);
                option.seriesdata[mk].label = {
                  show: true,
                  position: "top",
                  formatter: "{c}人"
                };
              }
            }
          }
        }
        minbars++;
      }
      const paramse = { data: option, id: ids };
      colorfulBares(paramse);
      let _thiss = this;
      const chartses = this.$echarts.init(document.getElementById(ids));
      chartses.on("finished", function() {
        _thiss.downseloads++;
      });
    },
    initEntryPeroid(resultse, id) {
      let optionse = {
        data: [],
        seriesdata: []
      };
      optionse.seriesdata = [];
      optionse.data = [];
      for (let key in resultse) {
        optionse.data.push(key);
        optionse.seriesdata.push({ value: resultse[key], name: key });
      }
      const params = { data: optionse, id };
      agerrujjn(params);
      let _this = this;
      const chartse = this.$echarts.init(document.getElementById(id));
      chartse.on("finished", function() {
        _this.downseloads++;
      });
    },
    initChartes8(resultse) {
      this.initEntryPeroid(resultse.super, "chartes81");
      this.initEntryPeroid(resultse.sub, "chartes82");
    },
    columnSexse(resultse) {
      const id = "chartes10";
      let optionse = {
        data: [],
        xAxisdata: [],
        seriesdata: []
      };
      let minbar = 0;
      var sum = 0;
      for (let key in resultse) {
        sum += Number(resultse[key]);
      }
      let colorses = ["#2ec6c8", "#b7a3de"];
      for (let key in resultse) {
        // optionse.xAxisdata.push(key);
        optionse.data.push(key);
        if (minbar == 0) {
          optionse.seriesdata.push({
            name: key,
            type: "bar",
            data: [resultse[key]],
            color: colorses[minbar],
            label: {
              show: true,
              position: "top",
              formatter(param) {
                let name = param.seriesName;
                if (sum > 0) {
                  return (
                    name +
                    ":" +
                    param.data +
                    "人" +
                    "\n" +
                    (Number(param.data) / sum * 100).toFixed(2) +
                    "%"
                  );
                } else {
                  return name + "\n" + param.data;
                }
              }
            }
          });
        } else {
          optionse.seriesdata.push({
            name: key,
            type: "bar",
            data: [resultse[key]],
            color: colorses[minbar],
            label: {
              show: true,
              position: "top",
              formatter(param) {
                let name = param.seriesName;
                if (sum > 0) {
                  return (
                    name +
                    ":" +
                    param.data +
                    "人" +
                    "\n" +
                    (Number(param.data) / sum * 100).toFixed(2) +
                    "%"
                  );
                } else {
                  return name + "\n" + param.data;
                }
              }
            }
          });
        }
        minbar++;
      }
      const params = { data: optionse, id };
      colorfulBares(params);
      let _this = this;
      const chartse = this.$echarts.init(document.getElementById(id));
      chartse.on("finished", function() {
        _this.downseloads++;
      });
    },
    workTimeses(resultse) {
      this.initEntryPeroid(resultse, "chartes101");
    },
    educationse(resultse) {
      let _this = this;
      colorfulBar({
        data: resultse,
        id: "chartes102",
        newLine: true,
        transform: true,
        percent: true
      }).on("finished", function() {
        _this.downseloads++;
      });
    },
    initChartes103(resultse) {
      const id = "chartes103";
      let optionse = {
        data: [],
        xAxisdata: [],
        seriesdata: [],
        color: "#becea0"
      };
      optionse.data = ["违纪人次"];
      if (resultse.length > 0) {
        resultse.forEach(items => {
          optionse.xAxisdata.push(items.name);
          optionse.seriesdata.push(items.value);
        });
      }
      const params = { data: optionse, id };
      lineround(params);
      let _this = this;
      const chartse = this.$echarts.init(document.getElementById(id));
      chartse.on("finished", function() {
        _this.downseloads++;
      });
    },
    initChartes104(resultse) {
      const id = "chartes104";
      let optionse = {
        data: [],
        xAxisdata: [],
        seriesdata: [],
        color: "#b6a2de"
      };
      optionse.data = ["违纪人次"];
      if (resultse.length > 0) {
        resultse.forEach(items => {
          optionse.xAxisdata.push(items.name);
          optionse.seriesdata.push(items.value);
        });
      }
      const params = { data: optionse, id };
      lineround(params);
      let _this = this;
      const chartse = this.$echarts.init(document.getElementById(id));
      chartse.on("finished", function() {
        _this.downseloads++;
      });
    },
    //领导班子eacharts图接口
    async getLeaderData() {
      const unitId = this.unitId;
      const params = { unitId };
      const result = await getLeaderData(params);
      //      setTimeout(() => {
      if (result.code == 0) {
        this.multipleSelection = result;
        this.titlemins = result.data.superName;
        this.titleminse = result.data.subName;
        this.initChartes1(result.data.duty);
        this.initChartes2(result.data.pieAge);
        this.initChartes3(result.data.columnAge);
        this.initChartes4(result.data.pieSex);
        this.initChartes41(result.data.columnSex);
        this.initChartes5(result.data.education);
        this.initChartes6(result.data.nation);
        this.initChartes7(result.data.post);
        this.initChartes8(result.data.entryPeriod);
        this.initChartes103(result.data.leaderRuleTrend);
        localStorage.setItem("deptLevel", result.data.deptLevel);
        if (result.data.deptLevel == 3) {
          this.targetNum = 18;
        } else {
          this.targetNum = 22;
        }
        if (this.bigtitlechange == false) {
          this.selectedIndex = 0;
          this.selectedIndex1 = 0;
          this.selectedIndex2 = 0;
          this.selectedIndex3 = 0;
          this.selectedIndex4 = 0;
          this.selectedIndex5 = 0;
          this.selectedIndex6 = 0;
          this.selectedIndex7 = 0;
          this.mintitlechange(0, "人员分析");
          this.mintitlechange(0, "年龄结构");
          this.mintitlechange(0, "男女比例");
          this.mintitlechange(0, "文化程度");
          this.mintitlechange(0, "民族分布");
          this.mintitlechange(0, "任职时间");
          this.mintitlechange(0, "入警年限");
          this.mintitlechange(0, "违纪案件");
        }
      }
      //      }, 300);
    },
    //领导班子eacharts图接口
    async getHiddenData() {
      const unitId = this.unitId;
      const params = { unitId };
      const result = await getHiddenData(params);
      //      setTimeout(() => {
      if (result.code == 0) {
        this.columnSexse(result.data.columnSex);
        this.workTimeses(result.data.workTime);
        this.educationse(result.data.edution);
        this.initChartes104(result.data.ruleTrend);
      }
      //      }, 300);
    },
    //标题切换
    titlechange(parems) {
      if (parems == 0) {
        this.bigtitlechange = true;
        this.initRow2Left();
        this.initRow2Right();
        this.initRow3Left();
        this.initRow3Right();
        this.initRow4Left();
        this.initRow4Right();
        $(".jianclass").css("color", "#60a5ff");
        $(".linclass").css("color", "#f2f42f");
      } else {
        this.bigtitlechange = false;
        if (this.multipleSelection) {
          this.selectedIndex = 0;
          this.selectedIndex1 = 0;
          this.selectedIndex2 = 0;
          this.selectedIndex3 = 0;
          this.selectedIndex4 = 0;
          this.selectedIndex5 = 0;
          this.selectedIndex6 = 0;
          this.selectedIndex7 = 0;
          this.mintitlechange(0, "人员分析");
          this.mintitlechange(0, "年龄结构");
          this.mintitlechange(0, "男女比例");
          this.mintitlechange(0, "文化程度");
          this.mintitlechange(0, "民族分布");
          this.mintitlechange(0, "任职时间");
          this.mintitlechange(0, "入警年限");
          this.mintitlechange(0, "违纪案件");
        }
        $(".linclass").css("color", "#60a5ff");
        $(".jianclass").css("color", "#f2f42f");
      }
    },
    switchPanel(index, itense) {
      const _this = this;
      if (itense == "人员分析") {
        if (_this.selectedIndex !== index) {
          _this.selectedIndex = index;
          this.mintitlechange(this.titlechangese, "人员分析");
        }
      } else if (itense == "年龄结构") {
        if (_this.selectedIndex1 !== index) {
          _this.selectedIndex1 = index;
          this.mintitlechange(this.titlechangese1, "年龄结构");
        }
      } else if (itense == "男女比例") {
        if (_this.selectedIndex2 !== index) {
          _this.selectedIndex2 = index;
          this.mintitlechange(this.titlechangese2, "男女比例");
        }
      } else if (itense == "文化程度") {
        if (_this.selectedIndex3 !== index) {
          _this.selectedIndex3 = index;
          this.mintitlechange(this.titlechangese3, "文化程度");
        }
      } else if (itense == "民族分布") {
        if (_this.selectedIndex4 !== index) {
          _this.selectedIndex4 = index;
          this.mintitlechange(this.titlechangese4, "民族分布");
        }
      } else if (itense == "任职时间") {
        if (_this.selectedIndex5 !== index) {
          _this.selectedIndex5 = index;
          this.mintitlechange(this.titlechangese5, "任职时间");
        }
      } else if (itense == "入警年限") {
        if (_this.selectedIndex6 !== index) {
          _this.selectedIndex6 = index;
          this.mintitlechange(this.titlechangese6, "入警年限");
        }
      } else if (itense == "违纪案件") {
        if (_this.selectedIndex7 !== index) {
          _this.selectedIndex7 = index;
          this.mintitlechange(this.titlechangese7, "违纪案件");
        }
      }
    },
    //领导班子表格或者图标下载
    downloadKPIse(parems, dexes) {
      let unitId = this.unitId;
      let type = undefined;
      let isDownload = 0;
      let categoryse = "";
      let sub = 1;
      let title = parems;
      let extraTypese = undefined;
      if (parems == "人员分析") {
        categoryse = "duty";
        if (this.titlechangese == 0) {
          sub = 0;
        } else {
          sub = 1;
        }
        if (this.selectedIndex == 0) {
          isDownload = 0;
        } else {
          isDownload = 1;
        }
      } else if (parems == "年龄结构") {
        categoryse = "age";
        if (this.titlechangese1 == 0) {
          sub = 0;
        } else {
          sub = 1;
        }
        if (this.selectedIndex1 == 0) {
          isDownload = 0;
        } else {
          isDownload = 1;
        }
      } else if (parems == "男女比例") {
        categoryse = "sex";
        if (this.titlechangese2 == 0) {
          sub = 0;
        } else {
          sub = 1;
        }
        if (this.selectedIndex2 == 0) {
          isDownload = 0;
        } else {
          isDownload = 1;
        }
      } else if (parems == "文化程度") {
        categoryse = "education";
        if (this.titlechangese3 == 0) {
          sub = 0;
        } else {
          sub = 1;
        }
        if (this.selectedIndex3 == 0) {
          isDownload = 0;
        } else {
          isDownload = 1;
        }
      } else if (parems == "民族分布") {
        categoryse = "nation";
        if (this.titlechangese4 == 0) {
          sub = 0;
        } else {
          sub = 1;
        }
        if (this.selectedIndex4 == 0) {
          isDownload = 0;
        } else {
          isDownload = 1;
        }
      } else if (parems == "任职时间") {
        categoryse = "appointmentTime";
        if (this.titlechangese5 == 0) {
          sub = 0;
        } else {
          sub = 1;
        }
        if (this.selectedIndex5 == 0) {
          isDownload = 0;
        } else {
          isDownload = 1;
        }
        if (dexes) {
          extraTypese = this.extraTypese;
        }
      } else if (parems == "入警年限") {
        categoryse = "entryPeriod";
        if (this.titlechangese6 == 0) {
          sub = 0;
        } else {
          sub = 1;
        }
        if (this.selectedIndex6 == 0) {
          isDownload = 0;
        } else {
          isDownload = 1;
        }
      } else if (parems == "违纪案件") {
        categoryse = "rule";
        if (this.titlechangese7 == 0) {
          sub = 0;
        } else {
          sub = 1;
        }
        if (this.selectedIndex7 == 0) {
          isDownload = 0;
        } else {
          isDownload = 1;
        }
      }
      if (dexes) {
        type = this.dataIndex;
        isDownload = 1;
      }
      let paramsse = {
        unitId,
        type,
        isDownload,
        category: categoryse,
        sub,
        extraTypese,
        title
      };
      this.downloadReport(7, isDownload, true, paramsse);
    },
    //领导班子上下级部门切换
    mintitlechange(index, itemse) {
      if (itemse == "人员分析") {
        if (index == 0) {
          this.titlechangese = 0;
          $(".mintitlese1").css("color", "#1b2af1");
          $(".mintitlese2").css("color", "#666");
          setTimeout(() => {
            if (this.selectedIndex == 0) {
              //eacharts渲染
              let _this = this;
              _this.row5.left.chart.content1 =
                _this.multipleSelection.data.content.superDutyContent;
              colorfulBar({
                data: _this.multipleSelection.data.duty.super,
                id: "linechartes11",
                newLine: true,
                transform: true,
                percent: true
              }).on("finished", function() {});
              let chartse = this.$echarts.init(
                document.getElementById("linechartes11")
              );
              chartse.off("click");
              chartse.on("click", function(params11) {
                _this.loadingse = true;
                _this.ageVisible = true;
                _this.agetitle = "人员分析";
                _this.dataIndex = params11.name;
                const unitId = _this.unitId;
                const type = params11.name;
                const isDownload = 0;
                const category = "duty";
                const sub = 0;
                const params = { unitId, type, isDownload, category, sub };
                _this.getLeaderDetail(params);
              });
            } else {
              //表格
              const unitId = this.unitId;
              const type = undefined;
              const isDownload = 0;
              const category = "duty";
              const sub = 0;
              const params = { unitId, type, isDownload, category, sub };
              this.getLeaderDetail(params);
            }
          }, 300);
        } else {
          this.titlechangese = 1;
          $(".mintitlese2").css("color", "#1b2af1");
          $(".mintitlese1").css("color", "#666");
          setTimeout(() => {
            if (this.selectedIndex == 0) {
              //eacharts渲染
              let _this = this;
              _this.row5.left.chart.content1 =
                _this.multipleSelection.data.content.subDutyContent;
              colorfulBar({
                data: _this.multipleSelection.data.duty.sub,
                id: "linechartes12",
                newLine: true,
                transform: true,
                percent: true
              }).on("finished", function() {});
              let chartse = this.$echarts.init(
                document.getElementById("linechartes12")
              );
              chartse.off("click");
              chartse.on("click", function(params11) {
                _this.loadingse = true;
                _this.ageVisible = true;
                _this.agetitle = "人员分析";
                _this.dataIndex = params11.name;
                const unitId = _this.unitId;
                const type = params11.name;
                const isDownload = 0;
                const category = "duty";
                const sub = 1;
                const params = { unitId, type, isDownload, category, sub };
                _this.getLeaderDetail(params);
              });
            } else {
              //表格
              const unitId = this.unitId;
              const type = undefined;
              const isDownload = 0;
              const category = "duty";
              const sub = 1;
              const params = { unitId, type, isDownload, category, sub };
              this.getLeaderDetail(params);
            }
          }, 300);
        }
      } else if (itemse == "年龄结构") {
        if (index == 0) {
          this.titlechangese1 = 0;
          $(".mintitlese3").css("color", "#1b2af1");
          $(".mintitlese4").css("color", "#666");
          setTimeout(() => {
            if (this.selectedIndex1 == 0) {
              //eacharts渲染
              const id = "linechartes13";
              let optionse = [],
                resultse = this.multipleSelection.data.columnAge[
                  this.multipleSelection.data.superName
                ];
              optionse = [];
              for (let key in resultse) {
                optionse.push({ value: resultse[key], name: key });
              }
              const params = { data: optionse, id };
              this.row5.right.chart.content1 = this.multipleSelection.data.content.superAgeContent;
              agePiese(params);
              let _this = this;
              const chartse = this.$echarts.init(document.getElementById(id));
              // chartse.on("finished", function() { });
              chartse.off("click");
              chartse.on("click", function(params11) {
                _this.loadingse = true;
                _this.ageVisible = true;
                _this.agetitle = "年龄结构";
                _this.dataIndex = params11.name;
                const unitId = _this.unitId;
                const type = params11.name;
                const isDownload = 0;
                const category = "age";
                const sub = 0;
                const params = { unitId, type, isDownload, category, sub };
                _this.getLeaderDetail(params);
              });
            } else {
              //表格
              const unitId = this.unitId;
              const type = undefined;
              const isDownload = 0;
              const category = "age";
              const sub = 0;
              const params = { unitId, type, isDownload, category, sub };
              this.getLeaderDetail(params);
            }
          }, 300);
        } else {
          this.titlechangese1 = 1;
          $(".mintitlese4").css("color", "#1b2af1");
          $(".mintitlese3").css("color", "#666");
          setTimeout(() => {
            if (this.selectedIndex1 == 0) {
              const id = "linechartes14";
              let optionse = [],
                resultse = this.multipleSelection.data.columnAge[
                  this.multipleSelection.data.subName
                ];
              optionse = [];
              for (let key in resultse) {
                optionse.push({ value: resultse[key], name: key });
              }
              const params = { data: optionse, id };
              this.row5.right.chart.content1 = this.multipleSelection.data.content.subAgeContent;
              agePiese(params);
              let _this = this;
              const chartse = this.$echarts.init(document.getElementById(id));
              // chartse.on("finished", function() {});
              chartse.off("click");
              chartse.on("click", function(params11) {
                _this.loadingse = true;
                _this.ageVisible = true;
                _this.agetitle = "年龄结构";
                _this.dataIndex = params11.name;
                const unitId = _this.unitId;
                const type = params11.name;
                const isDownload = 0;
                const category = "age";
                const sub = 1;
                const params = { unitId, type, isDownload, category, sub };
                _this.getLeaderDetail(params);
              });
            } else {
              //表格
              const unitId = this.unitId;
              const type = undefined;
              const isDownload = 0;
              const category = "age";
              const sub = 1;
              const params = { unitId, type, isDownload, category, sub };
              this.getLeaderDetail(params);
            }
          }, 300);
        }
      } else if (itemse == "男女比例") {
        if (index == 0) {
          this.titlechangese2 = 0;
          $(".mintitlese5").css("color", "#1b2af1");
          $(".mintitlese6").css("color", "#666");
          setTimeout(() => {
            if (this.selectedIndex2 == 0) {
              //eacharts渲染
              const id = "linechartes15";
              let optionse = [],
                resultse = this.multipleSelection.data.columnSex[
                  this.multipleSelection.data.superName
                ];
              optionse = [];
              for (let key in resultse) {
                optionse.push({ value: resultse[key], name: key });
              }
              const params = { data: optionse, id };
              this.row6.left.chart.content1 = this.multipleSelection.data.content.superSexContent;
              agePiese(params);
              let _this = this;
              const chartse = this.$echarts.init(document.getElementById(id));
              // chartse.on("finished", function() { });
              chartse.off("click");
              chartse.on("click", function(params11) {
                _this.loadingse = true;
                _this.ageVisible = true;
                _this.agetitle = "男女比例";
                _this.dataIndex = params11.name;
                const unitId = _this.unitId;
                const type = params11.name;
                const isDownload = 0;
                const category = "sex";
                const sub = 0;
                const params = { unitId, type, isDownload, category, sub };
                _this.getLeaderDetail(params);
              });
            } else {
              //表格
              const unitId = this.unitId;
              const type = undefined;
              const isDownload = 0;
              const category = "sex";
              const sub = 0;
              const params = { unitId, type, isDownload, category, sub };
              this.getLeaderDetail(params);
            }
          }, 300);
        } else {
          this.titlechangese2 = 1;
          $(".mintitlese6").css("color", "#1b2af1");
          $(".mintitlese5").css("color", "#666");
          setTimeout(() => {
            if (this.selectedIndex2 == 0) {
              const id = "linechartes16";
              let optionse = [],
                resultse = this.multipleSelection.data.columnSex[
                  this.multipleSelection.data.subName
                ];
              optionse = [];
              for (let key in resultse) {
                optionse.push({ value: resultse[key], name: key });
              }
              const params = { data: optionse, id };
              this.row6.left.chart.content1 = this.multipleSelection.data.content.subSexContent;
              agePiese(params);
              let _this = this;
              const chartse = this.$echarts.init(document.getElementById(id));
              // chartse.on("finished", function() { });
              chartse.off("click");
              chartse.on("click", function(params11) {
                _this.loadingse = true;
                _this.ageVisible = true;
                _this.agetitle = "男女比例";
                _this.dataIndex = params11.name;
                const unitId = _this.unitId;
                const type = params11.name;
                const isDownload = 0;
                const category = "sex";
                const sub = 1;
                const params = { unitId, type, isDownload, category, sub };
                _this.getLeaderDetail(params);
              });
            } else {
              //表格
              const unitId = this.unitId;
              const type = undefined;
              const isDownload = 0;
              const category = "sex";
              const sub = 1;
              const params = { unitId, type, isDownload, category, sub };
              this.getLeaderDetail(params);
            }
          }, 300);
        }
      } else if (itemse == "文化程度") {
        if (index == 0) {
          this.titlechangese3 = 0;
          $(".mintitlese7").css("color", "#1b2af1");
          $(".mintitlese8").css("color", "#666");
          setTimeout(() => {
            if (this.selectedIndex3 == 0) {
              //eacharts渲染
              let _this = this;
              _this.row6.right.chart.content1 =
                _this.multipleSelection.data.content.superEducationContent;
              colorfulBar({
                data: _this.multipleSelection.data.education.super,
                id: "linechartes17",
                newLine: true,
                transform: true,
                percent: true
              }).on("finished", function() {});
              const chartse = this.$echarts.init(
                document.getElementById("linechartes17")
              );
              chartse.off("click");
              chartse.on("click", function(params11) {
                _this.loadingse = true;
                _this.ageVisible = true;
                _this.agetitle = "文化程度";
                _this.dataIndex = params11.name;
                const unitId = _this.unitId;
                const type = params11.name;
                const isDownload = 0;
                const category = "education";
                const sub = 0;
                const params = { unitId, type, isDownload, category, sub };
                _this.getLeaderDetail(params);
              });
            } else {
              //表格
              const unitId = this.unitId;
              const type = undefined;
              const isDownload = 0;
              const category = "education";
              const sub = 0;
              const params = { unitId, type, isDownload, category, sub };
              this.getLeaderDetail(params);
            }
          }, 300);
        } else {
          this.titlechangese3 = 1;
          $(".mintitlese8").css("color", "#1b2af1");
          $(".mintitlese7").css("color", "#666");
          setTimeout(() => {
            if (this.selectedIndex3 == 0) {
              let _this = this;
              _this.row6.right.chart.content1 =
                _this.multipleSelection.data.content.subEducationContent;
              colorfulBar({
                data: _this.multipleSelection.data.education.sub,
                id: "linechartes18",
                newLine: true,
                transform: true,
                percent: true
              }).on("finished", function() {});
              const chartse = this.$echarts.init(
                document.getElementById("linechartes18")
              );
              chartse.off("click");
              chartse.on("click", function(params11) {
                _this.loadingse = true;
                _this.ageVisible = true;
                _this.agetitle = "文化程度";
                _this.dataIndex = params11.name;
                const unitId = _this.unitId;
                const type = params11.name;
                const isDownload = 0;
                const category = "education";
                const sub = 1;
                const params = { unitId, type, isDownload, category, sub };
                _this.getLeaderDetail(params);
              });
            } else {
              //表格
              const unitId = this.unitId;
              const type = undefined;
              const isDownload = 0;
              const category = "education";
              const sub = 1;
              const params = { unitId, type, isDownload, category, sub };
              this.getLeaderDetail(params);
            }
          }, 300);
        }
      } else if (itemse == "民族分布") {
        if (index == 0) {
          this.titlechangese4 = 0;
          $(".mintitlese9").css("color", "#1b2af1");
          $(".mintitlese10").css("color", "#666");
          setTimeout(() => {
            if (this.selectedIndex4 == 0) {
              //eacharts渲染
              let _this = this;
              _this.row7.left.chart.content1 =
                _this.multipleSelection.data.content.superNationContent;
              colorfulBar({
                data: _this.multipleSelection.data.nation.super,
                id: "linechartes19",
                newLine: true,
                transform: true,
                percent: true
              }).on("finished", function() {});
              const chartse = this.$echarts.init(
                document.getElementById("linechartes19")
              );
              chartse.off("click");
              chartse.on("click", function(params11) {
                _this.loadingse = true;
                _this.ageVisible = true;
                _this.agetitle = "民族分布";
                _this.dataIndex = params11.name;
                const unitId = _this.unitId;
                const type = params11.name;
                const isDownload = 0;
                const category = "nation";
                const sub = 0;
                const params = { unitId, type, isDownload, category, sub };
                _this.getLeaderDetail(params);
              });
            } else {
              //表格
              const unitId = this.unitId;
              const type = undefined;
              const isDownload = 0;
              const category = "nation";
              const sub = 0;
              const params = { unitId, type, isDownload, category, sub };
              this.getLeaderDetail(params);
            }
          }, 300);
        } else {
          this.titlechangese4 = 1;
          $(".mintitlese10").css("color", "#1b2af1");
          $(".mintitlese9").css("color", "#666");
          setTimeout(() => {
            if (this.selectedIndex4 == 0) {
              let _this = this;
              _this.row7.left.chart.content1 =
                _this.multipleSelection.data.content.subNationContent;
              colorfulBar({
                data: _this.multipleSelection.data.nation.sub,
                id: "linechartes110",
                newLine: true,
                transform: true,
                percent: true
              }).on("finished", function() {});
              const chartse = this.$echarts.init(
                document.getElementById("linechartes110")
              );
              chartse.off("click");
              chartse.on("click", function(params11) {
                _this.loadingse = true;
                _this.ageVisible = true;
                _this.agetitle = "民族分布";
                _this.dataIndex = params11.name;
                const unitId = _this.unitId;
                const type = params11.name;
                const isDownload = 0;
                const category = "nation";
                const sub = 1;
                const params = { unitId, type, isDownload, category, sub };
                _this.getLeaderDetail(params);
              });
            } else {
              //表格
              const unitId = this.unitId;
              const type = undefined;
              const isDownload = 0;
              const category = "nation";
              const sub = 1;
              const params = { unitId, type, isDownload, category, sub };
              this.getLeaderDetail(params);
            }
          }, 300);
        }
      } else if (itemse == "任职时间") {
        if (index == 0) {
          this.titlechangese5 = 0;
          $(".mintitlese11").css("color", "#1b2af1");
          $(".mintitlese12").css("color", "#666");
          setTimeout(() => {
            if (this.selectedIndex5 == 0) {
              //eacharts渲染
              const id = "linechartes111";
              let optionse = {
                data: [],
                xAxisdata: [],
                seriesdata: []
              };
              let minbar = 0;
              let colorses = ["#2ec6c8", "#b7a3de"];
              let resultse = this.multipleSelection.data.post;
              for (let key in resultse["正职"]) {
                optionse.xAxisdata.push(key);
                if (minbar == 0) {
                  let mincol = 0;
                  for (let kese in resultse["正职"][key]) {
                    optionse.data.push(kese);
                    optionse.seriesdata.push({
                      name: kese,
                      type: "bar",
                      data: [resultse["正职"][key][kese]],
                      color: colorses[mincol],
                      barMinHeight: 5
                    });
                    mincol++;
                  }
                } else {
                  for (let keses in resultse["正职"][key]) {
                    for (let k = 0; k < optionse.seriesdata.length; k++) {
                      if (keses == optionse.seriesdata[k].name) {
                        optionse.seriesdata[k].data.push(
                          resultse["正职"][key][keses]
                        );
                        optionse.seriesdata[k].label = {
                          show: true,
                          position: "top",
                          formatter: "{c}人"
                        };
                      }
                    }
                  }
                }
                minbar++;
              }
              const params = { data: optionse, id };
              this.row7.right.chart.content1 = this.multipleSelection.data.content.mainPostNum;
              colorfulBares(params);
              let _this = this;
              const chartse = this.$echarts.init(document.getElementById(id));
              // chartse.on("finished", function() {});
              chartse.off("click");
              chartse.on("click", function(params11) {
                _this.loadingse = true;
                _this.ageVisible = true;
                _this.agetitle = "任职时间";
                _this.dataIndex = params11.name;
                const unitId = _this.unitId;
                const type = params11.name;
                const isDownload = 0;
                const category = "appointmentTime";
                const sub = 0;
                _this.extraTypese = params11.seriesName;
                const extraType = params11.seriesName;
                const params = {
                  unitId,
                  type,
                  isDownload,
                  category,
                  sub,
                  extraType
                };
                _this.getLeaderDetail(params);
              });
            } else {
              //表格
              const unitId = this.unitId;
              const type = undefined;
              const isDownload = 0;
              const category = "appointmentTime";
              const sub = 0;
              const params = { unitId, type, isDownload, category, sub };
              this.getLeaderDetail(params);
            }
          }, 300);
        } else {
          this.titlechangese5 = 1;
          $(".mintitlese12").css("color", "#1b2af1");
          $(".mintitlese11").css("color", "#666");
          setTimeout(() => {
            if (this.selectedIndex5 == 0) {
              const ids = "linechartes112";
              let option = {
                data: [],
                xAxisdata: [],
                seriesdata: []
              };
              let minbars = 0;
              let colorsess = ["#2ec6c8", "#b7a3de"];
              let resultse = this.multipleSelection.data.post;
              for (let mkey in resultse["副职"]) {
                option.xAxisdata.push(mkey);
                if (minbars == 0) {
                  let mincols = 0;
                  for (let mkese in resultse["副职"][mkey]) {
                    option.data.push(mkese);
                    option.seriesdata.push({
                      name: mkese,
                      type: "bar",
                      data: [resultse["副职"][mkey][mkese]],
                      color: colorsess[mincols],
                      barMinHeight: 5
                    });
                    option.seriesdata.label = {
                      show: true,
                      position: "top",
                      formatter: "{c}人"
                    };
                    mincols++;
                  }
                } else {
                  for (let mkeses in resultse["副职"][mkey]) {
                    for (let mk = 0; mk < option.seriesdata.length; mk++) {
                      if (mkeses == option.seriesdata[mk].name) {
                        option.seriesdata[mk].data.push(
                          resultse["副职"][mkey][mkeses]
                        );
                        option.seriesdata[mk].label = {
                          show: true,
                          position: "top",
                          formatter: "{c}人"
                        };
                      }
                    }
                  }
                }
                minbars++;
              }
              const paramse = { data: option, id: ids };
              this.row7.right.chart.content1 = this.multipleSelection.data.content.assistantPostNum;
              colorfulBares(paramse);
              let _thiss = this;
              const chartses = this.$echarts.init(document.getElementById(ids));
              // chartses.on("finished", function() { });
              chartses.off("click");
              chartses.on("click", function(params11) {
                _thiss.loadingse = true;
                _thiss.ageVisible = true;
                _thiss.agetitle = "任职时间";
                _thiss.dataIndex = params11.name;
                const unitId = _thiss.unitId;
                const type = params11.name;
                const isDownload = 0;
                const category = "appointmentTime";
                const sub = 1;
                _thiss.extraTypese = params11.seriesName;
                const extraType = params11.seriesName;
                const params = {
                  unitId,
                  type,
                  isDownload,
                  category,
                  sub,
                  extraType
                };
                _thiss.getLeaderDetail(params);
              });
            } else {
              //表格
              const unitId = this.unitId;
              const type = undefined;
              const isDownload = 0;
              const category = "appointmentTime";
              const sub = 1;
              const params = { unitId, type, isDownload, category, sub };
              this.getLeaderDetail(params);
            }
          }, 300);
        }
      } else if (itemse == "入警年限") {
        if (index == 0) {
          this.titlechangese6 = 0;
          $(".mintitlese13").css("color", "#1b2af1");
          $(".mintitlese14").css("color", "#666");
          setTimeout(() => {
            if (this.selectedIndex6 == 0) {
              //eacharts渲染
              let id = "linechartes113";
              let optionse = {
                data: [],
                seriesdata: []
              };
              optionse.seriesdata = [];
              optionse.data = [];
              let resultse = this.multipleSelection.data.entryPeriod.super;
              for (let key in resultse) {
                optionse.data.push(key);
                optionse.seriesdata.push({ value: resultse[key], name: key });
              }
              let params = { data: optionse, id };
              this.row8.left.chart.content1 = this.multipleSelection.data.content.superEntryPeriodContent;
              agerrujjn(params);
              let _this = this;
              let chartse = this.$echarts.init(document.getElementById(id));
              // chartse.on("finished", function() { });
              chartse.off("click");
              chartse.on("click", function(params11) {
                _this.loadingse = true;
                _this.ageVisible = true;
                _this.agetitle = "入警年限";
                _this.dataIndex = params11.name;
                let unitId = _this.unitId;
                let type = params11.name;
                let isDownload = 0;
                let category = "entryPeriod";
                let sub = 0;
                let params = { unitId, type, isDownload, category, sub };
                _this.getLeaderDetail(params);
              });
            } else {
              //表格
              let unitId = this.unitId;
              let type = undefined;
              let isDownload = 0;
              let category = "entryPeriod";
              let sub = 0;
              let params = { unitId, type, isDownload, category, sub };
              this.getLeaderDetail(params);
            }
          }, 300);
        } else {
          this.titlechangese6 = 1;
          $(".mintitlese14").css("color", "#1b2af1");
          $(".mintitlese13").css("color", "#666");
          setTimeout(() => {
            if (this.selectedIndex6 == 0) {
              let idse = "linechartes114";
              let optionse = {
                data: [],
                seriesdata: []
              };
              optionse.seriesdata = [];
              optionse.data = [];
              let resultse = this.multipleSelection.data.entryPeriod.sub;
              for (let key in resultse) {
                optionse.data.push(key);
                optionse.seriesdata.push({ value: resultse[key], name: key });
              }
              let params12 = { data: optionse, id: idse };
              this.row8.left.chart.content1 = this.multipleSelection.data.content.subEntryPeriodContent;
              agerrujjn(params12);
              let _thise = this;
              let chartsees = this.$echarts.init(document.getElementById(idse));
              // chartsees.on("finished", function() { });
              chartsees.off("click");
              chartsees.on("click", function(params11) {
                _thise.loadingse = true;
                _thise.ageVisible = true;
                _thise.agetitle = "入警年限";
                _thise.dataIndex = params11.name;
                let unitId = _thise.unitId;
                let type = params11.name;
                let isDownload = 0;
                let category = "entryPeriod";
                let sub = 1;
                let paramss2 = { unitId, type, isDownload, category, sub };
                _thise.getLeaderDetail(paramss2);
              });
            } else {
              //表格
              let unitId = this.unitId;
              let type = undefined;
              let isDownload = 0;
              let category = "entryPeriod";
              let sub = 1;
              let params = { unitId, type, isDownload, category, sub };
              this.getLeaderDetail(params);
            }
          }, 300);
        }
      } else if (itemse == "违纪案件") {
        if (index == 0) {
          this.titlechangese7 = 0;
          $(".mintitlese15").css("color", "#1b2af1");
          $(".mintitlese16").css("color", "#666");
          setTimeout(() => {
            if (this.selectedIndex7 == 0) {
              //eacharts渲染
              const id = "linechartes115";
              let optionse = {
                data: [],
                xAxisdata: [],
                seriesdata: [],
                color: "#becea0"
              };
              optionse.data = ["违纪人次"];
              let resultse = this.multipleSelection.data.leaderRule.super;
              if (resultse.length > 0) {
                resultse.forEach(items => {
                  optionse.xAxisdata.push(items.name);
                  optionse.seriesdata.push(items.value);
                });
              }
              const params = { data: optionse, id };
              this.row8.right.chart.content1 = this.multipleSelection.data.content.superLeaderRuleContent;
              lineround(params);
              let _this = this;
              const chartse = this.$echarts.init(document.getElementById(id));
              // chartse.on("finished", function() { });
              chartse.off("click");
              chartse.on("click", function(params11) {
                if (
                  params11.name !== "最大值" &&
                  params11.name !== "最小值" &&
                  params11.name !== "平均值"
                ) {
                  _this.loadingse = true;
                  _this.ageVisible = true;
                  _this.agetitle = "违纪案件";
                  _this.dataIndex = params11.name;
                  const unitId = _this.unitId;
                  const type = params11.name;
                  const isDownload = 0;
                  const category = "rule";
                  const sub = 0;
                  const params = { unitId, type, isDownload, category, sub };
                  _this.getLeaderDetail(params);
                }
              });
            } else {
              //表格
              const unitId = this.unitId;
              const type = undefined;
              const isDownload = 0;
              const category = "rule";
              const sub = 0;
              const params = { unitId, type, isDownload, category, sub };
              this.getLeaderDetail(params);
            }
          }, 300);
        } else {
          this.titlechangese7 = 1;
          $(".mintitlese16").css("color", "#1b2af1");
          $(".mintitlese15").css("color", "#666");
          setTimeout(() => {
            if (this.selectedIndex7 == 0) {
              const id = "linechartes116";
              let optionse = {
                data: [],
                xAxisdata: [],
                seriesdata: [],
                color: "#becea0"
              };
              optionse.data = ["违纪人次"];
              let resultse = this.multipleSelection.data.leaderRule.sub;
              if (resultse.length > 0) {
                resultse.forEach(items => {
                  optionse.xAxisdata.push(items.name);
                  optionse.seriesdata.push(items.value);
                });
              }
              const params = { data: optionse, id };
              this.row8.right.chart.content1 = this.multipleSelection.data.content.subLeaderRuleContent;
              lineround(params);
              let _this = this;
              const chartse = this.$echarts.init(document.getElementById(id));
              // chartse.on("finished", function() { });
              chartse.off("click");
              chartse.on("click", function(params11) {
                if (
                  params11.name !== "最大值" &&
                  params11.name !== "最小值" &&
                  params11.name !== "平均值"
                ) {
                  _this.loadingse = true;
                  _this.ageVisible = true;
                  _this.agetitle = "违纪案件";
                  _this.dataIndex = params11.name;
                  const unitId = _this.unitId;
                  const type = params11.name;
                  const isDownload = 0;
                  const category = "rule";
                  const sub = 1;
                  const params = { unitId, type, isDownload, category, sub };
                  _this.getLeaderDetail(params);
                }
              });
            } else {
              //表格
              const unitId = this.unitId;
              const type = undefined;
              const isDownload = 0;
              const category = "rule";
              const sub = 1;
              const params = { unitId, type, isDownload, category, sub };
              this.getLeaderDetail(params);
            }
          }, 300);
        }
      }
    },
    //领导班子翻页1
    handleSizeChangeldbz1(val) {
      this.row5.left.tab.pageSize = val;
      let eNums1 =
        (this.row5.left.tab.pageNum - 1) * this.row5.left.tab.pageSize;
      let eNums2 = this.row5.left.tab.pageNum * this.row5.left.tab.pageSize;
      this.row5.left.tab.data = this.row5.left.tab.dataz.slice(eNums1, eNums2);
    },
    handleCurrentChangeldbz1(val) {
      this.row5.left.tab.pageNum = val;
      let eNums1 =
        (this.row5.left.tab.pageNum - 1) * this.row5.left.tab.pageSize;
      let eNums2 = this.row5.left.tab.pageNum * this.row5.left.tab.pageSize;
      this.row5.left.tab.data = this.row5.left.tab.dataz.slice(eNums1, eNums2);
    },
    //领导班子翻页2
    handleSizeChangeldbz2(val) {
      this.row5.right.tab.pageSize = val;
      let eNums1 =
        (this.row5.right.tab.pageNum - 1) * this.row5.right.tab.pageSize;
      let eNums2 = this.row5.right.tab.pageNum * this.row5.right.tab.pageSize;
      this.row5.right.tab.data = this.row5.right.tab.dataz.slice(
        eNums1,
        eNums2
      );
    },
    handleCurrentChangeldbz2(val) {
      this.row5.right.tab.pageNum = val;
      let eNums1 =
        (this.row5.right.tab.pageNum - 1) * this.row5.right.tab.pageSize;
      let eNums2 = this.row5.right.tab.pageNum * this.row5.right.tab.pageSize;
      this.row5.right.tab.data = this.row5.right.tab.dataz.slice(
        eNums1,
        eNums2
      );
    },
    //领导班子翻页3
    handleSizeChangeldbz3(val) {
      this.row6.left.tab.pageSize = val;
      let eNums1 =
        (this.row6.left.tab.pageNum - 1) * this.row6.left.tab.pageSize;
      let eNums2 = this.row6.left.tab.pageNum * this.row6.left.tab.pageSize;
      this.row6.left.tab.data = this.row6.left.tab.dataz.slice(eNums1, eNums2);
    },
    handleCurrentChangeldbz3(val) {
      this.row6.left.tab.pageNum = val;
      let eNums1 =
        (this.row6.left.tab.pageNum - 1) * this.row6.left.tab.pageSize;
      let eNums2 = this.row6.left.tab.pageNum * this.row6.left.tab.pageSize;
      this.row6.left.tab.data = this.row6.left.tab.dataz.slice(eNums1, eNums2);
    },
    //领导班子翻页4
    handleSizeChangeldbz4(val) {
      this.row6.right.tab.pageSize = val;
      let eNums1 =
        (this.row6.right.tab.pageNum - 1) * this.row6.right.tab.pageSize;
      let eNums2 = this.row6.right.tab.pageNum * this.row6.right.tab.pageSize;
      this.row6.right.tab.data = this.row6.right.tab.dataz.slice(
        eNums1,
        eNums2
      );
    },
    handleCurrentChangeldbz4(val) {
      this.row6.right.tab.pageNum = val;
      let eNums1 =
        (this.row6.right.tab.pageNum - 1) * this.row6.right.tab.pageSize;
      let eNums2 = this.row6.right.tab.pageNum * this.row6.right.tab.pageSize;
      this.row6.right.tab.data = this.row6.right.tab.dataz.slice(
        eNums1,
        eNums2
      );
    },
    //领导班子翻页5
    handleSizeChangeldbz5(val) {
      this.row7.left.tab.pageSize = val;
      let eNums1 =
        (this.row7.left.tab.pageNum - 1) * this.row7.left.tab.pageSize;
      let eNums2 = this.row7.left.tab.pageNum * this.row7.left.tab.pageSize;
      this.row7.left.tab.data = this.row7.left.tab.dataz.slice(eNums1, eNums2);
    },
    handleCurrentChangeldbz5(val) {
      this.row7.left.tab.pageNum = val;
      let eNums1 =
        (this.row7.left.tab.pageNum - 1) * this.row7.left.tab.pageSize;
      let eNums2 = this.row7.left.tab.pageNum * this.row7.left.tab.pageSize;
      this.row7.left.tab.data = this.row7.left.tab.dataz.slice(eNums1, eNums2);
    },
    //领导班子翻页6
    handleSizeChangeldbz6(val) {
      this.row7.right.tab.pageSize = val;
      let eNums1 =
        (this.row7.right.tab.pageNum - 1) * this.row7.right.tab.pageSize;
      let eNums2 = this.row7.right.tab.pageNum * this.row7.right.tab.pageSize;
      this.row7.right.tab.data = this.row7.right.tab.dataz.slice(
        eNums1,
        eNums2
      );
    },
    handleCurrentChangeldbz6(val) {
      this.row7.right.tab.pageNum = val;
      let eNums1 =
        (this.row7.right.tab.pageNum - 1) * this.row7.right.tab.pageSize;
      let eNums2 = this.row7.right.tab.pageNum * this.row7.right.tab.pageSize;
      this.row7.right.tab.data = this.row7.right.tab.dataz.slice(
        eNums1,
        eNums2
      );
    },
    //领导班子翻页7
    handleSizeChangeldbz7(val) {
      this.row8.left.tab.pageSize = val;
      let eNums1 =
        (this.row8.left.tab.pageNum - 1) * this.row8.left.tab.pageSize;
      let eNums2 = this.row5.left.tab.pageNum * this.row8.left.tab.pageSize;
      this.row8.left.tab.data = this.row8.left.tab.dataz.slice(eNums1, eNums2);
    },
    handleCurrentChangeldbz7(val) {
      this.row8.left.tab.pageNum = val;
      let eNums1 =
        (this.row8.left.tab.pageNum - 1) * this.row8.left.tab.pageSize;
      let eNums2 = this.row8.left.tab.pageNum * this.row8.left.tab.pageSize;
      this.row8.left.tab.data = this.row8.left.tab.dataz.slice(eNums1, eNums2);
    },
    //领导班子翻页8
    handleSizeChangeldbz8(val) {
      this.row8.right.tab.pageSize = val;
      let eNums1 =
        (this.row8.right.tab.pageNum - 1) * this.row8.right.tab.pageSize;
      let eNums2 = this.row5.right.tab.pageNum * this.row8.right.tab.pageSize;
      this.row8.right.tab.data = this.row8.right.tab.dataz.slice(
        eNums1,
        eNums2
      );
    },
    handleCurrentChangeldbz8(val) {
      this.row8.right.tab.pageNum = val;
      let eNums1 =
        (this.row8.right.tab.pageNum - 1) * this.row8.right.tab.pageSize;
      let eNums2 = this.row8.right.tab.pageNum * this.row8.right.tab.pageSize;
      this.row8.right.tab.data = this.row8.right.tab.dataz.slice(
        eNums1,
        eNums2
      );
    },
    //领导班子弹框详情分页
    handleCurrentChangeline(val) {
      this.lindatalist.tab.pageNum = val;
      let eNums1 =
        (this.lindatalist.tab.pageNum - 1) * this.lindatalist.tab.pageSize;
      let eNums2 = this.lindatalist.tab.pageNum * this.lindatalist.tab.pageSize;
      this.lindatalist.tab.data = this.lindatalist.tab.dataz.slice(
        eNums1,
        eNums2
      );
    },
    //表格详情查询方法
    getLeaderDetail(params) {
      this.$axios
        .post("/teamLeaderBuild/getLeaderDetail", params)
        .then(res => {
          if (res.code == 0) {
            if (params.type) {
              //调用弹框接口
              this.loadingse = false;
              this.lindatalist.tab.pageNum = 1;
              this.lindatalist.tab.dataz = res.data.rows;
              this.lindatalist.tab.data = this.lindatalist.tab.dataz.slice(
                0,
                10
              );
              this.lindatalist.tab.total = res.data.rows.length;
              this.lindatalist.tab.heads = res.data.heads;
              this.lindatalist.tab.width = res.data.width;
            } else {
              if (params.category == "duty") {
                this.row5.left.tab.dataz = res.data.rows;
                this.row5.left.tab.data = res.data.rows.slice(0, 10);
                this.row5.left.tab.total = res.data.rows.length;
                this.row5.left.tab.heads = res.data.heads;
                this.row5.left.tab.width = res.data.width;
                this.row5.left.tab.pageNum = 1;
                this.row5.left.tab.pageSize = 10;
              } else if (params.category == "age") {
                this.row5.right.tab.dataz = res.data.rows;
                this.row5.right.tab.data = res.data.rows.slice(0, 10);
                this.row5.right.tab.total = res.data.rows.length;
                this.row5.right.tab.heads = res.data.heads;
                this.row5.right.tab.width = res.data.width;
                this.row5.right.tab.pageNum = 1;
                this.row5.right.tab.pageSize = 10;
              } else if (params.category == "sex") {
                this.row6.left.tab.dataz = res.data.rows;
                this.row6.left.tab.data = res.data.rows.slice(0, 10);
                this.row6.left.tab.total = res.data.rows.length;
                this.row6.left.tab.heads = res.data.heads;
                this.row6.left.tab.width = res.data.width;
                this.row6.left.tab.pageNum = 1;
                this.row6.left.tab.pageSize = 10;
              } else if (params.category == "education") {
                this.row6.right.tab.dataz = res.data.rows;
                this.row6.right.tab.data = res.data.rows.slice(0, 10);
                this.row6.right.tab.total = res.data.rows.length;
                this.row6.right.tab.heads = res.data.heads;
                this.row6.right.tab.width = res.data.width;
                this.row6.right.tab.pageNum = 1;
                this.row6.right.tab.pageSize = 10;
              } else if (params.category == "nation") {
                this.row7.left.tab.dataz = res.data.rows;
                this.row7.left.tab.data = res.data.rows.slice(0, 10);
                this.row7.left.tab.total = res.data.rows.length;
                this.row7.left.tab.heads = res.data.heads;
                this.row7.left.tab.width = res.data.width;
                this.row7.left.tab.pageNum = 1;
                this.row7.left.tab.pageSize = 10;
              } else if (params.category == "appointmentTime") {
                this.row7.right.tab.dataz = res.data.rows;
                this.row7.right.tab.data = res.data.rows.slice(0, 10);
                this.row7.right.tab.total = res.data.rows.length;
                this.row7.right.tab.heads = res.data.heads;
                this.row7.right.tab.width = res.data.width;
                this.row7.right.tab.pageNum = 1;
                this.row7.right.tab.pageSize = 10;
              } else if (params.category == "entryPeriod") {
                this.row8.left.tab.dataz = res.data.rows;
                this.row8.left.tab.data = res.data.rows.slice(0, 10);
                this.row8.left.tab.total = res.data.rows.length;
                this.row8.left.tab.heads = res.data.heads;
                this.row8.left.tab.width = res.data.width;
                this.row8.left.tab.pageNum = 1;
                this.row8.left.tab.pageSize = 10;
              } else if (params.category == "rule") {
                this.row8.right.tab.dataz = res.data.rows;
                this.row8.right.tab.data = res.data.rows.slice(0, 10);
                this.row8.right.tab.total = res.data.rows.length;
                this.row8.right.tab.heads = res.data.heads;
                this.row8.right.tab.width = res.data.width;
                this.row8.right.tab.pageNum = 1;
                this.row8.right.tab.pageSize = 10;
              }
            }
          } else {
            this.$message(res.message);
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    // 判断哪个页面
    getPersonLog(params, idNumber) {
      this.$axios
        .post("/log/getPersonLog", {
          flagName: "队伍综合评估",
          idNumber: idNumber,
          pageName: "队伍综合建设成效",
          action: "报告下载"
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
    }
  },
  computed: {
    ...mapState(["unitId", "idNumber"]),
    content() {
      const { total, percent } = this.row2.left.tab;
      return `总人数： ${total}；占比：${percent}`;
    },
    strategy() {
      const _this = this;
      return {
        "1": _this.initRow2Left,
        "2": _this.initRow2Right,
        "3": _this.initRow3Left,
        "4": _this.initRow3Right,
        "5": _this.initRow4Left,
        "6": _this.initRow4Right,
        default: () => {}
      };
    },
    row2RightTitle() {
      return this.row2.right.chart.showContent
        ? ""
        : `经统计，${this.AnalysisTitle}`;
    },
    row3LeftTitle() {
      return this.row3.left.chart.showContent
        ? ""
        : `经统计，${this.AnalysisTitle}`;
    },
    row3RightTitle() {
      return this.row3.right.chart.showContent
        ? ""
        : `经统计，${this.AnalysisTitle}`;
    },
    row4LeftTitle() {
      return this.row4.left.chart.showContent
        ? ""
        : `经统计，${this.AnalysisTitle}`;
    },
    row4RightTitle() {
      return this.row4.right.chart.showContent
        ? ""
        : `经统计，${this.AnalysisTitle}`;
    }
  },
  created() {
    document.querySelector("body").style.height = "auto";
    document.querySelector("html").style.height = "auto";
    const div = document.createElement("div");
    div.className = "pop-mask";
    div.style.height = "1784px";
    document.querySelector("body").appendChild(div);
    this.initPage();
    this.loadKPIMenu();
  },
  destroyed() {
    document.querySelector("body").style.height = "100%";
    document.querySelector("html").style.height = "100%";
    document.querySelector(".pop-mask").remove();
  }
};
</script>

<style lang="scss" scoped>
/deep/ .el-dialog__body {
  padding: 0px 0px 40px 0;
  color: #606266;
  font-size: 14px;
  word-break: break-all;
  border-radius: 0px 0px 20px 20px;
  background: #d66c5c;
}
/deep/ .el-dialog__header {
  padding: 20px 20px 10px;
  background: #d66c5c;
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
  background-color: #d66c5c;
  color: #fff;
}
/deep/ .el-table th {
  overflow: hidden;
  -ms-user-select: none;
  user-select: none;
  background: #d66c5c2e;
  color: #000;
}
/deep/ .el-loading-spinner .circular {
  height: 42px;
  width: 20px;
  -webkit-animation: loading-rotate 2s linear infinite;
  animation: loading-rotate 2s linear infinite;
}
/deep/ .el-loading-mask {
  position: absolute;
  z-index: 2000;
  background-color: rgba(255, 255, 255, 0.8);
  margin: 0;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  -webkit-transition: opacity 0.3s;
  transition: opacity 0.3s;
  border-radius: 10px;
}
@font-face {
  font-family: "HYk2gj";
  src: url(../../assets/fonts/HYk2gj.ttf);
}
.pop-container {
  position: absolute;
  top: 94px;
  left: 50%;
  transform: translateX(-50%);
  width: 1356px;
  height: 1624px;
  margin-bottom: 65px;
  background: url(../../assets/image/teamAnalysis/developEffect/pop_bg.png)
    no-repeat top center / 100% 100%;
  z-index: 1000;
  padding: 34px 98px 68px 86px;
  overflow: hidden;
  & > header {
    height: 89px;
    font-family: "HYk2gj";
    position: relative;

    & > div {
      height: 35px;
      line-height: 30px;
      font-size: 30px;
      text-align: center;
      &:first-child {
        color: #fff;
      }

      &:nth-child(2) {
        color: #f2f42f;
      }
    }

    & > button {
      position: absolute;
      right: 0;
      bottom: 18px;
      width: 146px;
      height: 40px;
      background-color: #ffffff;
      box-shadow: 0px 0px 3px 0px rgba(33, 56, 78, 0.23);
      border-radius: 10px;
      outline: none;
      letter-spacing: 1px;
      color: #f08579;
      cursor: pointer;
    }

    & > i {
      position: absolute;
      right: -80px;
      top: -20px;
      color: #fff;
      font-size: 26px;
      cursor: pointer;
    }
  }
}
.jianclass {
  margin-right: 10px;
  cursor: pointer;
  color: #60a5ff;
}
.linclass {
  margin-right: 10px;
  cursor: pointer;
}
.img-panel {
  display: flex;
  align-items: center;

  & > div:first-child {
    margin-right: 7px;
    height: 30px;
    width: 35px;
  }
  .img-5 {
    background: url(../../assets/image/teamAnalysis/developEffect/icon5.png)
      no-repeat center center;
  }
  .img-7 {
    background: url(../../assets/image/portasts/policerankman.png) no-repeat
      center center;
  }
  .img-8 {
    background: url(../../assets/image/teamAnalysis/developEffect/icon1.png)
      no-repeat center center;
  }
  .img-9 {
    background: url(../../assets/image/policeporlit/人-2.png) no-repeat center
      center;
  }
  .img-11 {
    background: url(../../assets/image/portasts/opinionsatisfactionman.png)
      no-repeat center center;
  }
  .img-12 {
    background: url(../../assets/image/portasts/zidb.png) no-repeat center
      center;
  }
  .img-13 {
    background: url(../../assets/image/policeporlit/工作年限.png) no-repeat
      center center;
  }
  .img-14 {
    background: url(../../assets/image/teamAnalysis/developEffect/icon6.png)
      no-repeat center center;
  }
  .img-10 {
    background: url(../../assets/image/teamAnalysis/developEffect/icon2.png)
      no-repeat center center;
  }
}
.right-btn-panel {
  width: 50px;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;

  .download {
    font-size: 30px;
    cursor: pointer;

    &:hover {
      color: #00a0e9;
    }
  }
}
.right-btn-panelse {
  width: 110px;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: space-between;

  .download {
    font-size: 30px;
    cursor: pointer;

    &:hover {
      color: #00a0e9;
    }
  }
}
.down-section {
  margin-top: 30px;
  height: 381px;
  display: flex;
  justify-content: space-between;
}
.down-sectionse {
  margin-top: 45px;
  height: 220px;
  display: flex;
  justify-content: space-between;
}
.up-section {
  height: 223px;
  background-color: #ffffff;
  box-shadow: 0px 0px 3px 0px rgba(33, 56, 78, 0.23);
  border-radius: 10px;
  display: flex;
  justify-content: space-around;
  align-items: center;

  & > div {
    width: 150px;
    height: 120px;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
    color: #666;
    font-size: 16px;

    & > div:last-child {
      font-size: 24px;
    }

    @for $i from 1 to 5 {
      &:nth-child(#{$i}) {
        & > div:first-child {
          height: 60px;
          background: url(../../assets/image/teamAnalysis/developEffect/head#{$i}.png)
            no-repeat
            bottom
            center;
        }
      }
    }
  }
}
/deep/ .shownexttable .el-table__body-wrapper {
  height: 83%;
  overflow-x: hidden;
  position: relative;
  overflow-y: auto;
}
/deep/ .shownexttablese .el-table__body-wrapper {
  height: 83%;
  overflow-x: hidden;
  position: relative;
  overflow-y: auto;
}
.color1 {
  color: #619cff;
}
.color2 {
  color: #3bc587;
}
.color3 {
  color: #eec446;
}
.color4 {
  color: #f67136;
}

@mixin tab {
  height: 100%;
  & > main {
    height: calc(100% - 40px);
  }
  & > footer {
    height: 40px;
    display: flex;
    flex-direction: row-reverse;
    padding: 5px 10px 0 0;
  }
}

.main-1 {
  .chart {
    height: 100%;
    & > div {
      &:first-child {
        height: calc(100% - 120px);
      }
      &:last-child {
        height: 120px;
      }
    }
  }

  .tab {
    height: 100%;
    & > header {
      height: 30px;
      display: flex;
      align-items: center;
      justify-content: space-between;
      padding: 0 5px;
      font-size: 14px;
      color: #000;
    }
    & > main {
      height: calc(100% - 70px);
    }
    & > footer {
      height: 40px;
      display: flex;
      flex-direction: row-reverse;
      padding: 5px 10px 0 0;
    }
  }
}

.main-2 {
  .chart {
    height: 100%;
    & > div {
      &:first-child {
        height: calc(100% - 80px);
      }
      &:last-child {
        height: 80px;
      }
    }
  }

  .tab {
    @include tab;
  }
}

.main-3,
.main-4,
.main-5 {
  .chart {
    height: 100%;
    & > div {
      &:first-child {
        height: calc(100% - 120px);
      }
      &:last-child {
        height: 120px;
      }
    }
  }

  .tab {
    @include tab;
  }
}

.main-6 {
  .chart {
    height: 100%;
    & > div {
      &:first-child {
        height: calc(100% - 120px);
        display: flex;
        justify-content: space-between;
        padding: 0 20px;
        position: relative;

        & > div {
          width: calc(50% - 10px);
          height: 100%;
        }

        & > section {
          position: absolute;
          width: 50%;
          bottom: -16px;
          height: 24px;
          line-height: 24px;
          color: #3899eb;
          font-weight: bold;
          z-index: 1;

          &.left-sec {
            left: 0;
          }

          &.right-sec {
            right: 0;
          }
        }
      }
      &:last-child {
        height: 120px;
      }
    }
  }

  .tab {
    @include tab;
  }
}

.row2-switch {
  width: 200px;
  height: 24px;
  line-height: 24px;
  text-align: center;
  display: flex;
  align-items: center;
  border-radius: 4px;
  font-size: 14px;

  & > div {
    width: calc(100% / 3);
    height: 100%;
    box-shadow: 0px 0px 1px 0px rgba(33, 56, 78, 0.23);
    background-color: #eee;
    cursor: pointer;

    &:first-child {
      border-radius: 4px 0 0 4px;
    }
    &:last-child {
      border-radius: 0 4px 4px 0;
    }

    @mixin selected {
      background-color: #00a0e9;
      color: #fff;
    }

    &:hover,
    &.item-selected {
      @include selected;
    }
  }
}
.hiddenchart {
  width: calc(50% - 10px);
  height: 211px;
  background-color: #ffffff;
  box-shadow: 0px 0px 3px 0px rgba(33, 56, 78, 0.23);
  border-radius: 10px;
  color: #999;
}
.hiddenchartse {
  width: 100%;
  height: 211px;
  background-color: #ffffff;
  box-shadow: 0px 0px 3px 0px rgba(33, 56, 78, 0.23);
  border-radius: 10px;
  color: #999;
}
.hiddenchartmian {
  width: 100%;
  height: 80px;
  background-color: #ffffff;
  box-shadow: 0px 0px 3px 0px rgba(33, 56, 78, 0.23);
  border-radius: 10px;
  color: #999;
  padding-top: 10px;
  overflow-y: auto;
}
.showtable {
  width: calc(50% - 10px);
  height: 100%;
  background-color: #ffffff;
  box-shadow: 0px 0px 3px 0px rgba(33, 56, 78, 0.23);
  border-radius: 10px;
  color: #999;

  & > header {
    height: 50px;
    line-height: 50px;
    display: flex;
    padding: 0 15px;
    justify-content: space-between;
    color: #666;
  }
  & > .shownexttable {
    width: 100%;
    height: calc(100% - 90px);
  }
  & .shownexttablese {
    width: 100%;
    height: calc(100% - 40px);
  }
  & > footer {
    height: 40px;
    display: flex;
    flex-direction: row-reverse;
    padding-top: 5px;
    overflow-x: auto;
    overflow-y: hidden;
  }
}
.switch-panel {
  width: 60px;
  height: 100%;
  text-align: center;
  display: flex;
  align-items: center;
  border-radius: 4px;

  & > div {
    width: 50%;
    height: 30px;
    display: flex;
    justify-content: center;
    align-items: center;
    box-shadow: 0px 0px 3px 0px rgba(33, 56, 78, 0.23);
    cursor: pointer;

    &:first-child {
      border-radius: 4px 0 0 4px;
    }
    &:last-child {
      border-radius: 0 4px 4px 0;
    }

    @mixin item-selected {
      background-color: #00a0e9;
      color: #fff;
    }

    &.item-selected,
    &:hover {
      @include item-selected;
    }
  }
}
.mintitlese1,
.mintitlese3,
.mintitlese5,
.mintitlese7,
.mintitlese9,
.mintitlese11,
.mintitlese13,
.mintitlese15 {
  color: #1b2af1;
  font-weight: 700;
  margin-right: 10px;
  cursor: pointer;
}
.mintitlese2,
.mintitlese4,
.mintitlese6,
.mintitlese8,
.mintitlese10,
.mintitlese12,
.mintitlese14,
.mintitlese16 {
  font-weight: 700;
  cursor: pointer;
}
.heightline {
  width: 100%;
  height: calc(100% - 50px);
}
#chartes11,
#chartes12,
#chartes1,
#chartes2,
#chartes3,
#chartes4,
#chartes41,
#chartes5,
#chartes51,
#chartes52,
#chartes6,
#chartes61,
#chartes62,
#chartes7,
#chartes8,
#chartes81,
#chartes82,
#chartes9,
#chartes10,
#chartes101,
#chartes102,
#chartes103,
#chartes104,
#linechartes11,
#linechartes12,
#linechartes13,
#linechartes14,
#linechartes15,
#linechartes16,
#linechartes17,
#linechartes18,
#linechartes19,
#linechartes110,
#linechartes111,
#linechartes112,
#linechartes113,
#linechartes114,
#linechartes115,
#linechartes116,
#hiddenchart1,
#hiddenchart2,
#hiddenchart3 {
  width: 100%;
  height: 100%;
}
</style>

<style lang="scss">
.chart strong {
  font-size: 16px;
  color: #60a5ff;
}
</style>