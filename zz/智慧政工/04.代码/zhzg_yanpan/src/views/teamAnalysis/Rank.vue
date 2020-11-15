<template>
  <div class="pop-container">
    <header>
      <div></div>
      <div>
        <img src="../../assets/image/teamAnalysis/developEffect/phb.png" />
        <span>排行榜</span>
      </div>
      <div>
        <img
          src="../../assets/image/teamAnalysis/developEffect/arrow.png"
          @click="togglePanel"
        />
      </div>
    </header>
    <main>
      <el-table
        height="100%"
        v-loading="loading"
        :data="tableData"
        stripe
        :header-row-style="{ backgroundColor: 'red', color: '#000' }"
        style="width: 100%"
        :default-sort="{ prop: 'count', order: 'descending' }"
      >
        <el-table-column align="center" prop="rank" label="排行">
          <template slot-scope="scope">
            {{ scope.$index + 1 }}
          </template>
        </el-table-column>
        <el-table-column align="center" prop="name" label="单位" sortable />
        <el-table-column
          align="center"
          :formatter="row => row.count + '人'"
          prop="count"
          label="总人数"
          sortable
        />
        <el-table-column
          align="center"
          :formatter="ageFormatter"
          prop="age"
          label="平均年龄"
          sortable
        />
        <el-table-column
          align="center"
          :formatter="percentFormatter"
          prop="female"
          label="女警比例"
          sortable
        />
        <el-table-column
          align="center"
          :formatter="percentFormatter"
          prop="orgHonor"
          label="集体荣誉人均占比"
          sortable
        />
        <el-table-column
          align="center"
          :formatter="percentFormatter"
          prop="personHonor"
          label="个人荣誉人均占比"
          sortable
        />
        <el-table-column
          align="center"
          :formatter="percentFormatter"
          prop="satisfaction"
          label="综合满意度"
          sortable
        />
        <el-table-column
          align="center"
          :formatter="percentFormatter"
          prop="illegal"
          label="违纪案件人均占比"
          sortable
        />
      </el-table>
    </main>
  </div>
</template>

<script>
import { getRankList } from "@/api/developEffectApi";

export default {
  props: ["rankParams"],
  data() {
    return {
      tableData: [],
      loading: true
    };
  },
  methods: {
    // 收起展示面板
    togglePanel() {
      const popMask = document.querySelector(".pop-mask");
      popMask && popMask.remove();
      this.$emit("close-rank", false);
    },
    // 处理百分比显示
    percentFormatter(row, column, cellValue) {
      let result = "";
      if (cellValue !== -Infinity) {
        result = cellValue + "%";
      }
      return result;
    },
    ageFormatter(row, column, cellValue) {
      let result = "";
      if (cellValue) {
        result = cellValue + "岁";
      }
      return result;
    },
    // 初始化页面
    async initPage() {
      const _this = this;
      const params = { unitId: _this.rankParams };
      const result = await getRankList(params);
      this.tableData = result.data.map(val => {
        Object.keys(val).forEach(key => {
          const filterArr = [
            "female",
            "orgHonor",
            "personHonor",
            "satisfaction",
            "illegal"
          ];
          if (filterArr.indexOf(key) > -1 && val[key] === null) {
            val[key] = -Infinity;
          }
        });
        return val;
      });
      this.loading = false;
    }
  },
  mounted() {
    const div = document.createElement("div");
    div.className = "pop-mask";
    document.querySelector("#app").appendChild(div);
    this.initPage();
  },
  destroyed() {
    const mask = document.querySelector(".pop-mask");
    mask && mask.remove();
  }
};
</script>

<style lang="scss" scoped>
.pop-container {
  position: absolute;
  top: 120px;
  height: 100%;
  left: 0;
  width: 100%;
  z-index: 1000;

  & > header {
    width: calc(100% - 20px);
    height: 91px;
    line-height: 91px;
    background-color: #f07e39;
    border-radius: 0px 46px 46px 0px;
    display: flex;
    padding: 0 30px;
    color: #fff;
    justify-content: space-between;

    & > div {
      &:first-child {
        span {
          font-size: 16px;
        }
      }
      &:nth-child(2) {
        position: relative;
        left: -50px;
        img {
          position: relative;
          top: -8px;
        }

        span {
          font-size: 34px;
          margin-left: 5px;
        }
      }

      &:last-child {
        img {
          cursor: pointer;
          transform: rotate(180deg);
        }
      }
    }
  }

  & > main {
    width: calc(100% - 20px);
    height: calc(100% - 91px);
    background-color: #ffffff;
    border-radius: 0 24px 24px 0;

    & > div {
      border-radius: 24px;
    }
  }
}
</style>