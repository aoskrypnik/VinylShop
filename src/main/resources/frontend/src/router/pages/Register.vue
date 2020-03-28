<template>
  <div>
    <background-video></background-video>
    <register-view></register-view>
  </div>
</template>

<script>
  import RegisterView from "@/components/views/RegisterView";
  import BackgroundVideo from "@/components/elements/misc/BackgroundVideo";
  export default {
    name: "Register",
    components: {BackgroundVideo, RegisterView}
  }
</script>

<style scoped>

</style>