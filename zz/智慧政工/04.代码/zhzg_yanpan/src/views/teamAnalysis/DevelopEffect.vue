<template>
  <div class="main-container">
    <div class="banner">
      <button class="return-btn" @click="return2Sj" v-if="showReturn">
        <img
          src="../../assets/image/teamAnalysis/developEffect/return-btn.png"
        />
        返回市局
      </button>
      <button
        class="sj-analysis"
        v-if="showSjAnalysis"
        @click="openAnalysis('2014110416460086100000002942', '南京市公安局',true)"
      >
        <i class="el-icon-document"></i>
        市局建设成效
      </button>
      <aside @click="openRank">
        <img src="../../assets/image/teamAnalysis/developEffect/phb.png" />
        <span>排行榜</span>
        <img src="../../assets/image/teamAnalysis/developEffect/arrow.png" />
      </aside>
      <div>
        <button
          :class="item.key === selectedIndex && 'btn-selected'"
          v-for="item in switchData"
          :key="item.key"
          @click="switchChange(item)"
        >
          {{ item.name }}
        </button>
      </div>
    </div>
    <main>
      <aside>
        <div class="left-aside aside">
          <div>
            <img src="../../assets/image/teamAnalysis/developEffect/1.png" />
          </div>
          <div>
            <div>体量最大的队伍</div>
            <div>
              <span class="color-span-1" :title="sideTopData[0].name">
                {{ sideTopData[0].name }}
              </span>
              <span>
                <span>总计民警 </span>
                <strong class="color-strong-1">
                  {{ sideTopData[0].value }}
                </strong>
                <span>人</span>
              </span>
            </div>
          </div>
        </div>
        <div class="left-aside aside">
          <div>
            <img src="../../assets/image/teamAnalysis/developEffect/2.png" />
          </div>
          <div>
            <div>最年轻的队伍</div>
            <div>
              <span class="color-span-2" :title="sideTopData[1].name">
                {{ sideTopData[1].name }}
              </span>
              <span>
                <span>平均年龄 </span>
                <strong class="color-strong-2">
                  {{ sideTopData[1].value }}
                </strong>
                <span>岁</span>
              </span>
            </div>
          </div>
        </div>
        <div class="left-aside aside">
          <div>
            <img src="../../assets/image/teamAnalysis/developEffect/3.png" />
          </div>
          <div>
            <div>女警占比最大的队伍</div>
            <div>
              <span class="color-span-3" :title="sideTopData[2].name">
                {{ sideTopData[2].name }}
              </span>
              <span>
                <span>女警占比 </span>
                <strong class="color-strong-3">
                  {{ sideTopData[2].value }}
                </strong>
              </span>
            </div>
          </div>
        </div>
        <section>
          <ColorDivider
            :unit="''"
            :data="sideBottomData.jxkh"
            :type="selectedIndex"
          >
            政工绩效
          </ColorDivider>
        </section>
        <section>
          <ColorDivider
            :unit="'%'"
            :data="sideBottomData.zhmyd"
            :type="selectedIndex"
          >
            综合满意度
          </ColorDivider>
        </section>
      </aside>
      <section class="center-panel">
        <a
          @click.prevent="openAnalysis(downMap.id, downMap.title,true)"
          :key="downMap.id"
          v-if="selectedType === 'pcs'"
          class="down-map-title"
        >
          {{ downMap.title }}
        </a>
        <div id="map" v-loading="loading"  element-loading-background="rgba(255, 255, 255, 0.3)" v-if="selectedType === 'pcs' || selectedType === 'fj'" ></div>
        <section id="tree"  v-loading="loadings"  element-loading-background="rgba(255, 255, 255, 0.3)" v-if="selectedType === 'bm1'">
          <div
            v-for="item in treeData"
            :key="item.deptId"
            @click="deptDrillDown(item)"
            @mouseenter="getDeptDetail(item.deptId)"
            @mouseout="upTreeDetail.showDetail = false"
            :class="`item-${item.index} item-color-${item.colorLevel}`"
          >
            {{ item.simpleName }}
          </div>
          <div class="tree-detail" v-if="upTreeDetail.showDetail">
            <header>{{ upTreeDetail.data.name }}</header>
            <main>
              <div style="width: 100px;">
                政工绩效
              </div>
               <div style="width: 42px;">
               {{ upTreeDetail.data.jxkh.value }}
              </div>
              <div style="width: 80px;"  :class="`detail-color-${upTreeDetail.data.jxkh.colorLevel}`">
                &nbsp;
              </div>
              <div style="width: 100px;">
                综合满意度
              </div>
              <div style="width: 42px;">{{ upTreeDetail.data.zhmyd.value }}%</div>
               <div style="width: 80px;"
                :class="`detail-color-${upTreeDetail.data.zhmyd.colorLevel}`"
              >
                &nbsp;
              </div>
              <div style="width: 100px;">
                纪检案件
              </div>
              <div style="width: 42px;">{{ upTreeDetail.data.jjaj.value }}件</div>
              <div style="width: 80px;" :class="`detail-color-${upTreeDetail.data.jjaj.colorLevel}`">
                &nbsp;
              </div>
              <div style="width: 100px;">
                集体荣誉
              </div>
              <div style="width: 42px;">{{ upTreeDetail.data.jtry.value }}项</div>
              <div style="width: 80px;" :class="`detail-color-${upTreeDetail.data.jtry.colorLevel}`">
                &nbsp;
              </div>
            </main>
          </div>
        </section>
        <main id="down-tree" v-if="selectedType === 'bm2'">
          <div class="down-panel">
            <header>
              <span
                @click="
                  openAnalysis(
                    downTree.selectedItem.deptId,
                    downTree.selectedItem.deptName,
                    downTree.selectedItem.isZoom
                  )
                "
              >
                {{ downTree.selectedItem.deptName }}
              </span>
            </header>
            <section>
              <input v-model="downTree.keyword" @keyup.enter="btnSearch" />
              <span>
                <button @click="downTree.keyword = ''">
                  <i class="el-icon-delete"></i>
                </button>
                <button @click="btnSearch">
                  <i class="el-icon-search"></i>
                </button>
              </span>
            </section>
            <main>
              <div
                @click="openAnalysis(item.deptId, item.deptName,item.isZoom)"
                v-for="item in downTree.selectedData"
                :key="item.deptId"
              >
                {{ item.deptName }}
              </div>
            </main>
          </div>
        </main>
        <MapPopup
          v-for="item in popup.list"
          :key="item.deptName"
          :top="item.top"
          :left="item.left"
          :deptName="item.deptName"
          :popupParams="popupParams"
          @close-popup="closePopup"
        />
      </section>
      <aside>
        <div class="right-aside aside">
          <div>
            <img src="../../assets/image/teamAnalysis/developEffect/4.png" />
          </div>
          <div>
            <div>荣誉人均占比最大的队伍</div>
            <div>
              <span class="color-span-4" :title="sideTopData[3].name">
                {{ sideTopData[3].name }}
              </span>
              <span>
                <span>荣誉人均占比 </span>
                <strong class="color-strong-4">
                  {{ sideTopData[3].value }}
                </strong>
              </span>
            </div>
          </div>
        </div>
        <div class="right-aside aside">
          <div>
            <img src="../../assets/image/teamAnalysis/developEffect/5.png" />
          </div>
          <div>
            <div>群众满意度最高的队伍</div>
            <div>
              <span class="color-span-5" :title="sideTopData[4].name">
                {{ sideTopData[4].name }}
              </span>
              <span>
                <span>综合满意度 </span>
                <strong class="color-strong-5">
                  {{ sideTopData[4].value }}
                </strong>
              </span>
            </div>
          </div>
        </div>
        <div class="right-aside aside">
          <div>
            <img src="../../assets/image/teamAnalysis/developEffect/6.png" />
          </div>
          <div>
            <div>违纪人均占比最小的队伍</div>
            <div>
              <span class="color-span-6" :title="sideTopData[5].name">
                {{ sideTopData[5].name }}
              </span>
              <span>
                <span>案件人员占比 </span>
                <strong class="color-strong-6">
                  {{ sideTopData[5].value }}
                </strong>
              </span>
            </div>
          </div>
        </div>
        <section>
          <ColorDivider
            :unit="'件'"
            :data="sideBottomData.jjaj"
            :type="selectedIndex"
          >
            纪检案件
          </ColorDivider>
        </section>
        <section>
          <ColorDivider
            :unit="'项'"
            :data="sideBottomData.jtry"
            :type="selectedIndex"
          >
            集体荣誉
          </ColorDivider>
        </section>
      </aside>
    </main>
    <transition name="leftIn">
      <Rank @close-rank="closeRank" v-if="showRank" :rankParams="rankParams" />
    </transition>
    <transition name="bottomIn">
      <Analysis
        v-if="showAnalysis"
        :AnalysisTitle="AnalysisTitle"
        @close-analysis="closeAnalysis"
      />
    </transition>
  </div>
</template>

<script>
import ColorDivider from "@/components/ColorDivider";
import { map3D } from "@/chart/echarts";
import axios from "axios";
import {
  getWindowsInfo,
  getSideData,
  getDeptColor
} from "@/api/developEffectApi";
import { Base64 } from "js-base64";
import Rank from "./Rank";
import Analysis from "./Analysis";
import { getUrlParams } from "@/utils";
import MapPopup from "./MapPopup";
// 引入命名空间帮助函数
import { createNamespacedHelpers } from "vuex";
// 加载固定模块的store
const { mapState, mapActions, mapMutations } = createNamespacedHelpers(
  "teamAnalysis"
);
// 加载地图的api
const api = axios.create({
  baseURL: process.env.NODE_ENV === "production" ? "/znl/" : "/"
});

export default {
  components: { ColorDivider, Rank, Analysis, MapPopup },
  data() {
    return {
      switchData: [],
      selectedIndex: 0,
      selectedType: "",
      sideTopData: [
        {
          name: "",
          value: ""
        },
        {
          name: "",
          value: ""
        },
        {
          name: "",
          value: ""
        },
        {
          name: "",
          value: ""
        },
        {
          name: "",
          value: ""
        },
        {
          name: "",
          value: ""
        }
      ],
      sideBottomData: {
        jxkh: {
          left: 0,
          right: 0
        },
        zhmyd: {
          left: 0,
          right: 0
        },
        jjaj: {
          left: 0,
          right: 0
        },
        jtry: {
          left: 0,
          right: 0
        }
      },
      treeData: [],
      showRank: false,
      showAnalysis: false,
      rankParams: "",
      showReturn: false,
      showSjAnalysis: false,
      downTree: {
        keyword: "",
        data: [],
        selectedData: [],
        selectedItem: {
          deptName: "",
          unitId: ""
        }
      },
      popup: {
        list: [],
        params: {
          unitId: "",
          type: ""
        }
      },
      downMap: {
        title: "",
        downMapId: ""
      },
      upTreeDetail: {
        data: {
          name: "",
          jxkh: {
            value: 0,
            colorLevel: 1
          },
          zhmyd: {
            value: 0,
            colorLevel: 1
          },
          jjaj: {
            value: 0,
            colorLevel: 1
          },
          jtry: {
            value: 0,
            colorLevel: 1
          }
        },
        showDetail: false
      },
      AnalysisTitle: "",
      loading: true,
      loadings: true
    };
  },
  methods: {
    // 选择框改变事件
    switchChange(item) {
      let _this = this;
      _this.loading = true;
      _this.loadings = true;
      // 清空弹出页
      _this.popup.list = [];
      _this.selectedIndex = item.key;
      _this.selectedType = item.type;
      if (item.key != 0) {
        let chart = _this.$echarts.getInstanceByDom(
          document.getElementById("map")
        );
        chart && chart.dispose();
      }
      _this.getMainData(item.key);
    },
    // 渲染3D地图
    async renderMap(data) {
      const _this = this;
      const map = this.searchRuler.map;
      if (map) {
        const result = await api.get(`json/${map}.json`);
        const params = { id: "map" };
        params.mapData = result.data;
        params.data = data;
        params.isSj = map === "南京市公安局";
        // 点击回调函数
        params.cb = async param => {
          const deptName = param.data.name;
          const deptId = param.data.id;
          if (param.seriesType === "scatter3D") {
            const { offsetX, offsetY } = param.event;
            const filterData = this.popup.list.filter(
              val => val.deptName === deptName
            );
            // 未找到
            if (filterData.length === 0) {
              this.popup.list.push({
                deptName: deptName,
                left: offsetX,
                top: offsetY
              });
              this.popup.params.unitId = deptId;
              this.popup.params.type = 0;
            }
          } else {
            if (params.isSj) {
              // 下钻
              if (param.data.isZoom) {
                _this.showReturn = true;
                _this.popup.list = [];
                // 获取查询参数
                await _this.setSearchRuler({
                  isZoom: true,
                  unitId: param.data.id
                });
                // 处理切换的显示隐藏
                await _this.handleSwitch();
                // 加载主要数据
                await _this.getMainData();
                setTimeout(() => {
                  _this.downMap.title = param.data.name;
                  _this.downMap.id = param.data.id;
                }, 500);
              }
            } else {
                _this.openAnalysis(deptId, deptName,param.data.isZoom);
            }
          }
        };
        map3D(params);
        this.loading = false;
      }
    },
    // 从url中获取身份信息并存储到仓库
    saveIdNumber() {
      const params = getUrlParams(location.hash);
      const idNumber = Base64.decode(params.no || '');
      // const idNumber = "320114196610232446";
      this.setIdNumber(idNumber);
    },
    // 处理切换的显示隐藏
    handleSwitch() {
      let _this = this;
      const strategy = {
        fj: { name: "分局", key: 0, type: "fj" },
        pcs: { name: "派出所", key: 0, type: "pcs" },
        bm1: { name: "部门", key: 1, type: "bm1" },
        bm2: { name: "部门", key: 1, type: "bm2" },
        default: { name: null, key: null }
      };
      let role = _this.searchRuler.role;
      const resultData = [];
      if (role.length === 1) {
        let obj = strategy[role[0]] || strategy["default"];
        resultData.push(obj);
      } else if (role.length === 2) {
        role.map(val => {
          let obj = strategy[val] || strategy["default"];
          resultData.push(obj);
        });
      }
      _this.showSjAnalysis =
        role.length === 2 &&
        role.indexOf("fj") > -1 &&
        role.indexOf("bm1") > -1;

      _this.selectedIndex = resultData[0].key;
      _this.selectedType = resultData[0].type;
      _this.switchData = resultData;
    },
    // 获取部门颜色
    async getDeptColor(params, role) {
      const _this = this;
      const searchRuler = _this.searchRuler;
      params.type = 1;
      if (role === "bm1") {
        const result = await getDeptColor(params);
        if (result.code == 0) {
          this.loadings = false;
        }
        const length = result.data.length;
        let space = parseInt(40 / length);

        var isZoomMap={};
          searchRuler[role].map(val => {
              isZoomMap[val.id] = val.isZoom;
          });

        let treeData = result.data.map((val, index) => {
          val.index = index * space + 1;
          val.isZoom= isZoomMap[val.deptId];
          return val;
        });
        _this.treeData = treeData;
      } else if (role === "bm2") {
        _this.downTree.data = _this.downTree.selectedData = searchRuler[
          role
        ].map(val => {
          const { id, teamName,isZoom } = val;
          return { deptId: id, deptName: teamName ,isZoom: isZoom };
        });
      } else {
        params.type = 0;
        // 地图数据
        const result = await getDeptColor(params);
        const mapData = result.data.map(v1 => {
          searchRuler[role].map(v2 => {
            if (v1.deptId === v2.id) {
              v1.isZoom = v2.isZoom;
              v1.name = v2.teamName;
            }
          });
          return v1;
        });
        this.renderMap(mapData);
      }
    },
    // 获取两边的数据
    async getSideData(params) {
      // 两边数据
      const result = await getSideData(params);
      this.sideTopData = result.data.data1;
      this.sideBottomData = result.data.data2;
    },
    // 获得主要的数据
    async getMainData(key = 0) {
      const searchRuler = this.searchRuler;
      const role = searchRuler.role[key];
      const data = searchRuler[role].map(val => val.id);
      const params = { unitId: data };
      await this.getDeptColor(params, role);
      this.getSideData(params);
    },
    // 关闭排行榜
    closeRank(flag) {
      this.showRank = flag;
    },
    // 打开排行榜
    openRank() {
      const _this = this;
      let unitId = _this.searchRuler[_this.selectedType];
      this.rankParams = unitId.map(val => val.id);
      this.showRank = true;
    },
    // 关闭分析页面
    closeAnalysis() {
      this.showAnalysis = false;
    },
    // 打开分析页面
    openAnalysis(unitId, deptName,isZoom) {
      if(!isZoom){
          return;
      }
      this.showAnalysis = true;
      this.AnalysisTitle = deptName;
      this.setUnitId(unitId);
    },
    // 回到市局
    return2Sj() {
      this.showReturn = false;
      this.downMap.title = "";
      this.downTree.selectedItem = {};
      this.popup.list = [];
      this.initPage();
    },
    // 部门下钻
    async deptDrillDown(item) {
        if(!item.isZoom){
            return;
        }
      const _this = this;
      _this.showReturn = true;
      const { deptName, deptId ,isZoom} = item;
      _this.downTree.selectedItem = { deptName, deptId ,isZoom};
      // 获取查询参数
      await _this.setSearchRuler({ isZoom: true, unitId: deptId });
      // 处理切换的显示隐藏
      await _this.handleSwitch();
      // 加载主要数据
      await _this.getMainData();
    },
    // 下级部门查询按钮
    btnSearch() {
      const keyword = this.downTree.keyword;
      this.downTree.selectedData = this.downTree.data.filter(
        val => val.deptName.indexOf(keyword) > -1
      );
    },
    // 关闭弹出页
    closePopup(deptName) {
      let selectedIndex;
      this.popup.list.map((val, index) => {
        if (val.deptName === deptName) {
          selectedIndex = index;
        }
      });
      this.popup.list.splice(selectedIndex, 1);
    },
    // 判断哪个页面
    getPersonLog(params,idNumber) {
      this.$axios
        .post("/log/getPersonLog", {
          flagName: "队伍综合评估",
          idNumber: idNumber,
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
    // 获取部门详情
    async getDeptDetail(unitId) {
      this.upTreeDetail.showDetail = true;
      this.popup.params.type = 1;
      this.popup.params.unitId = unitId;
      const result = await getWindowsInfo(this.popupParams);
      Object.keys(result.data).forEach(key => {
        if (key !== "name" && result.data[key].value === null) {
          result.data[key].value = "-";
        }
      });
      this.upTreeDetail.data = result.data;
    },
    // 页面入口
    async initPage() {
      // 从url中获取身份信息并存储到仓库
      this.saveIdNumber();
      const idNumber = this.idNumber;
      // 获取查询参数
      await this.setSearchRuler({ idNumber });
      // 处理切换的显示隐藏
      await this.handleSwitch();
      // 加载主要数据
      this.getMainData();
      //判断那个页面
      this.getPersonLog('队伍综合建设成效',idNumber);
    },
    // 引入设置参数的action
    ...mapActions(["setSearchRuler"]),
    // 引入设置身份证mutation
    ...mapMutations(["setIdNumber", "setUnitId"])
  },
  created() {
    this.initPage();
  },
  computed: {
    // 1.全局查询条件 2.身份证信息
    ...mapState(["searchRuler", "idNumber"]),
    // 浮框参数
    popupParams() {
      return {
        ...this.sideBottomData,
        ...this.popup.params
      };
    }
  }
};
</script>

<style lang="scss" scoped>
@font-face {
  font-family: "HYk2gj";
  src: url(../../assets/fonts/HYk2gj.ttf);
}

// 单行省略
@mixin single-text-ellipsis {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}

.main-container {
  & > main {
    height: calc(100% - 90px);
    display: flex;
    padding-top: 40px;

    & > aside {
      width: 485px;
      height: 100%;

      & > section {
        height: calc(50% - 240px);
        display: flex;
        align-items: center;
        justify-content: center;
      }
    }

    & > section {
      width: calc(100% - 970px);
      height: 100%;
    }
  }
}

.banner {
  position: relative;
  height: 91px;

  & > aside {
    position: absolute;
    width: 220px;
    height: 100%;
    background-color: #f07e39;
    border-radius: 0px 46px 46px 0px;
    left: 0;
    top: 0;
    cursor: pointer;
    line-height: 91px;

    & > span {
      padding: 0 10px;
      font-size: 24px;
      letter-spacing: 1px;
      font-weight: bold;
      color: #fff;
    }
  }

  .return-btn {
    position: absolute;
    height: 30px;
    line-height: 30px;
    top: -15px;
    left: 50%;
    transform: translateX(-50%);
    background-color: #f07e39;
    border-radius: 8px;
    outline: none;
    color: #fff;
    padding: 0 10px;
    cursor: pointer;

    img {
      margin-right: 5px;
    }
  }

  .sj-analysis {
    position: absolute;
    height: 30px;
    line-height: 30px;
    top: 10px;
    right: 80px;
    background-color: #f07e39;
    border-radius: 8px;
    outline: none;
    color: #fff;
    padding: 0 10px;
    cursor: pointer;
  }

  & > div {
    position: absolute;
    top: 50%;
    left: 50%;
    display: flex;
    transform: translate(-50%, -50%);
    height: 40px;
    line-height: 40px;
    background-color: #fff;
    border-radius: 4px;

    & > button {
      background-color: #ffffff;
      opacity: 0.66;
      padding: 0 18px;
      outline: none;
      font-size: 20px;
      letter-spacing: 1px;
      color: #bdbdbd;
      border: 0;
      border-radius: 4px;
      cursor: pointer;

      &:hover {
        background-color: #576ea9;
        opacity: 1;
        color: #ecf0ff;
      }
    }

    .btn-selected {
      background-color: #576ea9;
      opacity: 1;
      color: #ecf0ff;
    }
  }
}

.aside {
  width: 100%;
  height: 100px;
  box-shadow: 2px 3px 5px 0px rgba(154, 154, 154, 0.08);
  background-color: rgba(255, 255, 255, 0.53);
  margin-bottom: 60px;
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  padding: 0 30px;

  & > div {
    height: calc(100% - 20px);
    &:first-child {
      width: 80px;
      line-height: 100px;
    }
    &:last-child {
      width: calc(100% - 100px);
      padding: 10px 0;

      & > div {
        width: 100%;
        height: 40px;
        line-height: 40px;
        text-align: left;

        &:first-child {
          font-size: 18px;
          color: #666;
          // font-family: "HYk2gj";
          font-family: "微软雅黑";
          letter-spacing: 1px;
        }

        &:last-child {
          display: flex;
          align-items: center;

          & > span {
            margin-left: 20px;
            display: inline-block;
            &:first-child {
              margin-left: 0;
              @include single-text-ellipsis;
            }
          }
        }
      }
    }
  }
}

.left-aside {
  width: 100%;
  border-radius: 0px 50px 50px 0px;
}

.right-aside {
  border-radius: 50px 0px 0px 50px;
}

.color-span,
.color-strong {
  font-weight: bold;
  letter-spacing: 1px;

  &-1 {
    color: #60a5ff;
    max-width: 165px;
  }
  &-2 {
    color: #39b983;
    max-width: 146px;
  }
  &-3 {
    color: #ef9b8e;
    max-width: 150px;
  }
  &-4 {
    color: #d9aa1d;
    max-width: 118px;
  }
  &-5 {
    color: #e76a33;
    max-width: 150px;
  }
  &-6 {
    color: #5d77b8;
    max-width: 130px;
  }
}

.color-strong {
  &-1,
  &-2,
  &-3,
  &-4,
  &-5,
  &-6 {
    font-size: 20px;
  }
}

#map,
#tree,
#down-tree {
  height: 100%;
}

#map,
#down-tree {
  // background: url(../../assets/image/teamAnalysis/developEffect/mapbg.png)
  //   no-repeat center center;
}

#tree {
  background: url(../../assets/image/teamAnalysis/developEffect/tree_bg.png)
    no-repeat center center;
}

#tree,
#down-tree {
  padding: 0 20px;
}

.circle-selected {
  border: 2px dashed #fff;
  cursor: pointer;
}

// 圆生成
@mixin circle($size) {
  width: $size;
  height: $size;
  border-radius: $size / 2;
  position: absolute;
  line-height: $size;
  color: #fff;
  font-size: $size / 4;
  @include single-text-ellipsis;
  text-align: center;

  &:hover {
    border: 2px dashed #fff;
    cursor: pointer;
  }
}

.item-1 {
  @include circle(81px);
  top: 35.8%;
  left: 3%;
}

.item-2 {
  @include circle(94px);
  top: 20.8%;
  left: 5%;
}
.item-3 {
  @include circle(78px);
  top: 45.2%;
  left: 14%;
}
.item-4 {
  @include circle(62px);
  top: 27.5%;
  left: 21.1%;
}
.item-5 {
  @include circle(52px);
  top: 23%;
  left: 27.5%;
}
.item-6 {
  @include circle(64px);
  top: 27.5%;
  left: 31.6%;
}
.item-7 {
  @include circle(68px);
  top: 44.2%;
  left: 28%;
}
.item-8 {
  @include circle(64px);
  top: 9%;
  left: 36%;
}
.item-9 {
  @include circle(100px);
  top: 15.6%;
  left: 36%;
}
.item-10 {
  @include circle(55px);
  top: 27.2%;
  left: 39.3%;
}
.item-11 {
  @include circle(61px);
  top: 37.2%;
  left: 38%;
}
.item-12 {
  @include circle(65px);
  top: 18.2%;
  left: 47%;
}
.item-13 {
  @include circle(68px);
  top: 30.4%;
  left: 48%;
}
.item-14 {
  @include circle(69px);
  top: 50%;
  left: 48%;
}
.item-15 {
  @include circle(66px);
  top: 37.6%;
  left: 53%;
}
.item-16 {
  @include circle(55px);
  top: 23.6%;
  left: 53%;
}
.item-17 {
  @include circle(94px);
  top: 3.6%;
  left: 52%;
}
.item-18 {
  @include circle(62px);
  top: 18.6%;
  left: 60%;
}
.item-19 {
  @include circle(58px);
  top: 28.6%;
  left: 60.5%;
}
.item-20 {
  @include circle(65px);
  top: 46.6%;
  left: 59.1%;
}
.item-21 {
  @include circle(62px);
  top: 36.6%;
  left: 65.7%;
}
.item-22 {
  @include circle(71px);
  top: 16.6%;
  left: 69.7%;
}
.item-23 {
  @include circle(82px);
  top: 25.6%;
  left: 71.9%;
}
.item-24 {
  @include circle(76px);
  top: 46.6%;
  left: 74.7%;
}
.item-25 {
  @include circle(96px);
  top: 37.6%;
  left: 87.7%;
}
.item-26 {
  @include circle(58px);
  top: 37.6%;
  left: 12.7%;
}
.item-27 {
  @include circle(58px);
  top: 29.6%;
  left: 13.7%;
}
.item-28 {
  @include circle(58px);
  top: 37.6%;
  left: 21.7%;
}
.item-29 {
  @include circle(58px);
  top: 51.6%;
  left: 22.7%;
}
.item-30 {
  @include circle(58px);
  top: 17.6%;
  left: 20.7%;
}
.item-31 {
  @include circle(58px);
  top: 9.6%;
  left: 44.7%;
}
.item-32 {
  @include circle(58px);
  top: 14.6%;
  left: 27.7%;
}
.item-33 {
  @include circle(58px);
  top: 15.6%;
  left: 53.7%;
}
.item-34 {
  @include circle(58px);
  top: 39.6%;
  left: 80.7%;
}
.item-35 {
  @include circle(58px);
  top: 52.6%;
  left: 81.7%;
}
.item-36 {
  @include circle(58px);
  top: 11.6%;
  left: 59.7%;
}
.item-37 {
  @include circle(58px);
  top: 45.6%;
  left: 39.7%;
}
.item-38 {
  @include circle(58px);
  top: 28.6%;
  left: 80.7%;
}
.item-39 {
  @include circle(58px);
  top: 35.6%;
  left: 31.7%;
}
.item-40 {
  @include circle(58px);
  top: 37.6%;
  left: 72.7%;
}

.item-color-1 {
  background-color: #ef9b8e;
}

.item-color-2 {
  background-color: #fe7c43;
}

.item-color-3 {
  background-color: #fcb754;
}

.item-color-4 {
  background-color: #39b77f;
}

.item-color-5 {
  background-color: #60a5ff;
}

@keyframes show {
  0% {
    opacity: 0;
    transform: translateX(-100%);
  }
  100% {
    opacity: 1;
    left: translateX(0);
  }
}
@keyframes hide {
  0% {
    opacity: 1;
    left: translateX(0);
  }
  100% {
    opacity: 0;
    transform: translateX(-100%);
  }
}

.leftIn-enter-active {
  animation: show 0.5s;
}
.leftIn-leave-active {
  animation: hide 0.5s;
}
.leftIn-enter,
.leftIn-leave-to {
  opacity: 0;
}

@keyframes detailShow {
  0% {
    opacity: 0;
    transform: translate(-50%, -100%);
  }
  100% {
    opacity: 1;
    transform: translate(-50%, 0);
  }
}
@keyframes detailHide {
  0% {
    opacity: 1;
    transform: translate(-50%, 0);
  }
  100% {
    opacity: 0;
    transform: translate(-50%, -100%);
  }
}

.bottomIn-enter-active {
  animation: detailShow 0.5s;
}
.bottomIn-leave-active {
  animation: detailHide 0.5s;
}
.bottomIn-enter,
.bottomIn-leave-to {
  opacity: 0;
}

#down-tree {
  display: flex;
  justify-content: center;
  align-items: center;
}

.down-panel {
  width: 492px;
  height: 649px;
  background-color: #576ea9;
  box-shadow: 0px 0px 13px 0px rgba(0, 0, 0, 0.35);
  border-radius: 13px;
  padding: 24px 21px 17px 24px;

  & > header {
    height: 24px;
    font-size: 24px;
    line-height: 24px;
    letter-spacing: 1px;
    color: #fff;

    & > span {
      text-decoration: underline;
      cursor: pointer;

      &:hover {
        color: #c9e2be;
      }
    }
  }

  & > section {
    height: 30px;
    line-height: 30px;
    margin-top: 21px;
    display: flex;
    justify-content: center;

    & > input {
      border-radius: 4px;
      background-color: rgba(255, 255, 255, 0.54);
      outline: none;
      border: 0;
      color: #fff;
      padding: 0 10px;
    }

    & > span {
      margin-left: 15px;
    }

    button {
      height: 30px;
      cursor: pointer;
      border-radius: 4px;
      background-color: rgba(255, 255, 255, 0.54);
      outline: none;
      border: 0;
      color: #fff;
      padding: 0 10px;
      margin-left: 5px;
    }
  }

  & > main {
    height: calc(100% - 90px);
    margin-top: 15px;
    background-color: #ffffff;
    border-radius: 10px;
    overflow-y: auto;

    & > div {
      height: 50px;
      line-height: 50px;
      cursor: pointer;

      &:hover {
        color: #fff;
        background-color: #87bbff;
      }
    }
  }
}

.center-panel {
  position: relative;
}

.down-map-title {
  position: absolute;
  top: 10px;
  left: 50%;
  transform: rotateX(-50%);
  height: 30px;
  font-size: 30px;
  line-height: 24px;
  letter-spacing: 2px;
  color: #576ea9;
  font-weight: bold;
  z-index: 100;
  cursor: pointer;
  text-decoration: underline;

  &:hover {
    color: #000;
  }
}

.tree-detail {
  position: absolute;
  right: 0;
  bottom: 50px;
  width: 262px;
  height: 156px;
  color: #576ea9;
  padding: 10px;

  & > header {
    height: 20px;
    line-height: 20px;
    font-size: 20px;
    font-weight: bold;
    letter-spacing: 1px;
    text-align: left;
    @include single-text-ellipsis;
  }

  & > main {
    margin-top: 10px;
    height: calc(100% - 30px);
    display: grid;
    grid-template-columns: 100px  42px  80px;
    grid-template-rows: repeat(4, 1fr);
    align-items: center;
    grid-column-gap: 10px;
    text-align: left;
    clear: both;

    > div {
      float: left;
      margin-top: 4px;
    }
    .detail-color-1 {
      background-color: #ef9b8e;
    }
    .detail-color-2 {
      background-color: #fe7c43;
    }
    .detail-color-3 {
      background-color: #fcb754;
    }
    .detail-color-4 {
      background-color: #39b77f;
    }
    .detail-color-5 {
      background-color: #60a5ff;
    }
  }
}
</style>