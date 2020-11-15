<template>
  <div class="color-divider">
    <header>
      <slot></slot>
    </header>
    <div class="divider-up">
      <span>低</span>
      <span>高</span>
    </div>
    <div :class="`divider-mid${type}`" v-if="type == 0">
      <div></div>
      <div></div>
      <div></div>
      <div></div>
      <div></div>
      <div></div>
      <div></div>
      <div></div>
      <div></div>
    </div>
    <div :class="`divider-mid${type}`" v-else-if="type == 1">
      <div></div>
      <div></div>
      <div></div>
      <div></div>
      <div></div>
    </div>
    <div class="divider-down">
      <span :key="item.id" v-for="item in bottomNameArr">
        {{ item.value }}
      </span>
    </div>
  </div>
</template>

<script>
export default {
  props: ['type', 'unit', 'data'],
  computed: {
    bottomNameArr() {
      const _this = this;
      let unit = _this.unit;
      let left = Number(_this.data.left);
      let right = Number(_this.data.right);
      let precise;
      if (String(left).indexOf('.') > -1 || String(right).indexOf('.') > -1) {
        precise = 2;
      } else {
        precise = 0;
      }
      let diff = Number((right - left) / 4);
      let mid1 = Number(left + diff).toFixed(precise);
      let mid2 = Number(right - diff).toFixed(precise);
      return [
        { id: 1, value: left + unit },
        { id: 2, value: mid1 + unit },
        { id: 3, value: mid2 + unit },
        { id: 4, value: right + unit }
      ];
    }
  }
};
</script>

<style lang="scss" scoped>
@font-face {
  font-family: 'HYk2gj';
  src: url(../assets/fonts/HYk2gj.ttf);
}

@mixin divider-mid($width) {
  width: $width;
  height: 20px;
  margin: 0 auto;
  display: flex;
}
.color-divider {
  width: 354px;
  height: 109px;

  & > header {
    height: 19px;
    line-height: 19px;
    text-align: center;
    font-family: 'HYk2gj';
    font-size: 16px;
    letter-spacing: 1px;
    color: #333;
  }

  & > div {
    margin-top: 5px;
  }
}

.divider-up {
  display: inline-flex;
  color: #576ea9;
  font-weight: bold;
  letter-spacing: 1px;
  justify-content: space-between;
  width: 100%;
  height: 20px;
}

.divider-mid0 {
  @include divider-mid(324px);
  & > div {
    width: 36px;
    height: 100%;
    &:nth-child(1) {
      background-color: #efd8e5;
    }
    &:nth-child(2) {
      background-color: #e0c2d2;
    }
    &:nth-child(3) {
      background-color: #e6a8cc;
    }
    &:nth-child(4) {
      background-color: #d477b4;
    }
    &:nth-child(5) {
      background-color: #c04992;
    }
    &:nth-child(6) {
      background-color: #a92a6a;
    }
    &:nth-child(7) {
      background-color: #8a1745;
    }
    &:nth-child(8) {
      background-color: #531227;
    }
    &:nth-child(9) {
      background-color: #2e101b;
    }
  }
}

.divider-mid1 {
  @include divider-mid(320px);
  & > div {
    height: 100%;
    width: 64px;

    &:nth-child(1) {
      background-color: #ef9b8e;
    }
    &:nth-child(2) {
      background-color: #fe7c43;
    }
    &:nth-child(3) {
      background-color: #fcb754;
    }
    &:nth-child(4) {
      background-color: #39b77f;
    }
    &:nth-child(5) {
      background-color: #60a5ff;
    }
  }
}

.divider-down {
  display: inline-flex;
  justify-content: space-between;
  width: 100%;
  margin: 0 auto;
  height: 50px;
  line-height: 50px;
  color: #8593b6;

  & > span {
    position: relative;

    &::before {
      content: '';
      position: absolute;
      top: -10px;
      border-width: 10px;
      border-style: solid;
      border-color: transparent transparent #2171b5 transparent;
    }

    &:first-child::before {
      left: 7px;
    }

    &:nth-child(n + 2)::before {
      right: 7px;
    }
  }
}
</style>