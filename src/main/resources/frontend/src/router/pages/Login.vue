<template>
  <div>
    <background-video></background-video>
    <login-view />
  </div>
</template>

<script>

import LoginView from '@/components/views/LoginView.vue'
import BackgroundVideo from "@/components/elements/misc/BackgroundVideo";

export default {
  name: 'Login',
  components: {
    BackgroundVideo,
    LoginView
  }
}
</script>

<style scoped>

</style>