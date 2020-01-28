<template>
  <div id="app" class="container">
    <div class="row">
      <span class="logo col-md-12">MyVinyl</span>
    </div>
    <router-view/>
  </div>
</template>

<script>
export default {
  name: 'app',
  components: {
  }
}
</script>

<style>
body {
  background: black !important;
  color: white !important;
}

#app {
  font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;
}

.logo {
  line-height: 70px;
  font-size: 18pt;
  font-weight: 400;
  color: white;
}

</style>
