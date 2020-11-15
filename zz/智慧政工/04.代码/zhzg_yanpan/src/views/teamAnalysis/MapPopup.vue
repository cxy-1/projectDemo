<template>
  <div class="map-popup" :style="`left: ${left - 134}px; top: ${top - 178}px;`">
    <header>
      {{ deptName }}<i @click="closePopup" class="el-icon-close"></i>
    </header>
    <main>
      <div>
        政工绩效
      </div>
      <div>
        {{ data.jxkh.value }}
      </div>
      <div :class="`popup-color-${data.jxkh.colorLevel}`">&nbsp;</div>
      <div>
        综合满意度
      </div>
      <div>{{ data.zhmyd.value }}%</div>
      <div :class="`popup-color-${data.zhmyd.colorLevel}`">&nbsp;</div>
      <div>
        纪检案件
      </div>
      <div>{{ data.jjaj.value }}件</div>
      <div :class="`popup-color-${data.jjaj.colorLevel}`">&nbsp;</div>
      <div>
        集体荣誉
      </div>
      <div>{{ data.jtry.value }}项</div>
      <div :class="`popup-color-${data.jtry.colorLevel}`">&nbsp;</div>
    </main>
  </div>
</template>

<script>
import { getWindowsInfo } from '@/api/developEffectApi';

export default {
  props: ['left', 'top', 'deptName', 'popupParams'],
  data() {
    return {
      data: {
        jxkh: {
          value: '',
          colorLevel: 1
        },
        zhmyd: {
          value: '',
          colorLevel: 1
        },
        jjaj: {
          value: '',
          colorLevel: 1
        },
        jtry: {
          value: '',
          colorLevel: 1
        }
      }
    };
  },
  methods: {
    // 关闭案件
    closePopup() {
      this.$emit('close-popup', this.deptName);
    }
  },
  async created() {
    if (this.popupParams.unitId) {
      const result = await getWindowsInfo(this.popupParams);
      this.data = result.data;
    }
  }
};
</script>

<style lang="scss" scoped>
.map-popup {
  width: 262px;
  height: 156px;
  background-color: rgba(255, 255, 255, 0.9);
  border-radius: 4px;
  position: absolute;
  color: #576ea9;
  padding: 10px;
  z-index: 101;

  &::after {
    content: '';
    position: absolute;
    bottom: -20px;
    left: 50%;
    transform: translateX(-50%);
    border-width: 10px;
    border-style: solid;
    border-color: rgba(255, 255, 255, 0.9) transparent transparent transparent;
  }

  & > header {
    height: 20px;
    line-height: 20px;
    font-size: 20px;
    font-weight: bold;
    letter-spacing: 1px;
    text-align: left;

    & > i {
      position: absolute;
      right: 10px;
      top: 5px;
      cursor: pointer;
    }
  }

  & > main {
    margin-top: 10px;
    height: calc(100% - 30px);
    display: grid;
    grid-template-columns: 80px auto minmax(70px, 80px);
    grid-template-rows: repeat(4, 1fr);
    align-items: center;
    grid-column-gap: 10px;
    text-align: left;
  }
}

.popup-color-1 {
  background-color: #efd8e5;
}
.popup-color-2 {
  background-color: #e0c2d2;
}
.popup-color-3 {
  background-color: #e6a8cc;
}
.popup-color-4 {
  background-color: #d477b4;
}
.popup-color-5 {
  background-color: #c04992;
}
.popup-color-6 {
  background-color: #a92a6a;
}
.popup-color-7 {
  background-color: #8a1745;
}
.popup-color-8 {
  background-color: #531227;
}
.popup-color-9 {
  background-color: #2e101b;
}
</style>