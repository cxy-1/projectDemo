<template>
  <aside>
    <header>
      <div class="img-panel">
        <div :class="`img-${type}`"></div>
        <div>{{ title }}</div>
      </div>
      <div class="right-btn-panel">
        <i @click="download" class="download iconfont iconai231"></i>
        <div class="switch-panel">
          <div
            :class="selectedIndex == 0 && 'item-selected'"
            @click="switchPanel(0)"
          >
            <i class="el-icon-pie-chart"></i>
          </div>
          <div
            :class="selectedIndex == 1 && 'item-selected'"
            @click="switchPanel(1)"
          >
            <i class="el-icon-date"></i>
          </div>
        </div>
      </div>
    </header>
    <main :class="`main-${type}`">
      <slot name="chart" v-if="selectedIndex === 0"></slot>
      <div class="tab" v-else-if="selectedIndex === 1">
        <slot name="tab"></slot>
        <footer>
          <el-pagination
            background
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page="tabData.pageNum"
            :page-sizes="pageSizes"
            :pager-count="5"
            :page-size="tabData.pageSize"
            layout="total, sizes, pager, jumper"
            :total="tabData.total"
          >
          </el-pagination>
        </footer>
      </div>
    </main>
  </aside>
</template>

<script>
export default {
  props: ['type', 'title', 'tabData'],
  data() {
    return {
      selectedIndex: 0,
      pageSizes: [10, 20, 30, 50]
    };
  },
  methods: {
    switchPanel(index) {
      const _this = this;
      if (_this.selectedIndex !== index) {
        _this.selectedIndex = index;
        _this.$emit('init-chart-and-tab', index, _this.type);
      }
    },
    handleSizeChange(val) {
      this.$emit('size-change', val, this.tabData.pageNum, this.type);
    },
    handleCurrentChange(val) {
      this.$emit('current-page-change', val, this.tabData.pageSize, this.type);
    },
    download() {
      this.$emit('download', this.type, this.selectedIndex);
    }
  }
};
</script>

<style lang="scss" scoped>
aside {
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

  & > main {
    height: calc(100% - 50px);
  }
}

.img-panel {
  display: flex;
  align-items: center;

  & > div:first-child {
    margin-right: 7px;
    height: 30px;
    width: 35px;
  }

  @for $i from 1 to 7 {
    .img-#{$i} {
      background: url(../../assets/image/teamAnalysis/developEffect/icon#{$i}.png)
        no-repeat
        center
        center;
    }
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

.tab > footer {
  height: 40px;
  display: flex;
  flex-direction: row-reverse;
  padding-top: 5px;
  overflow-x: auto;
  overflow-y: hidden;
}

.right-btn-panel {
  width: 100px;
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
</style>