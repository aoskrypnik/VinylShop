<template>
  <div>
    <video autoplay muted id="bgVideo" v-on:ended="loopVideo">
      <source src="@/assets/loop_c.mp4" type="video/mp4">
    </video>
    <Login />
  </div>
</template>

<script>
// @ is an alias to /src
import Login from '@/components/views/Login.vue'

export default {
  name: 'home',
  components: {
    Login
  },
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