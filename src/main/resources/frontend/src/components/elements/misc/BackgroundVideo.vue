<template>
  <video autoplay muted id="bgVideo" v-on:ended="loopVideo">
    <source src="@/assets/loop_c.mp4" type="video/mp4">
  </video>
</template>

<script>
  export default {
    name: "BackgroundVideo",
    methods: {
      loopVideo(e) {
        e.target.currentTime = 0;
        e.target.play();
      }
    }
  }
</script>

<style scoped>

  #bgVideo {
    position: fixed;
    right: 0;
    bottom: 0;
    min-width: 100%;
    min-height: 100%;
    z-index: -1;
  }

</style>