<template>
  <div class="home">
    <router-view> </router-view>
  </div>
</template>

<script>
export default {
  components: {},
  data() {
    return {
      title: "民警画像"
    };
  }
};
</script>
<style lang="scss">
.home {
  height: 100%;
  overflow-y: visible;
  background: #f5f5f5;
}
</style>