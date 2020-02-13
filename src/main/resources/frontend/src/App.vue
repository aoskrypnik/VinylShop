<template>
  <div id="app" class="container">
    <div class="row">
      <span class="logo col-md-12">MyVinyl</span>
    </div>
    <router-view/>
    <div class="popups">
      <PopupView v-for="(content, i) in popups" :key="i" :type="content.type" :properties="content.properties" @close="closePopup"></PopupView>
    </div>
  </div>
</template>

<script>
import PopupView from "./components/views/PopupView";
import {mapState} from "vuex";

export default {
  name: 'app',
  components: {
    PopupView
  },
  computed: {
    ...mapState(['popups'])
  },
  methods: {
    closePopup() {
      this.$store.commit('closePopup')
    }
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
