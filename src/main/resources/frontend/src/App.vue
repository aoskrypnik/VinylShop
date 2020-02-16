<template>
  <div id="app" class="container">
    <div class="row">
      <span class="logo col-md-12">MyVinyl</span>
    </div>
    <router-view/>
    <div class="popups">
      <transition-group name="fade" tag="span">
        <PopupView v-for="(content, i) in popups" :key="i" :type="content.type" :properties="content.properties" @close="closePopup" transition="fade"></PopupView>
      </transition-group>
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

  scrollbar-color: white black;
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

.fade-enter-active {
  animation: fade-in .5s;
}

.fade-leave-active {
  animation: fade-out .5s;
}

@keyframes fade-in {
  0% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }
}

@keyframes fade-out {
  0% {
    opacity: 1;
  }
  100% {
    opacity: 0;
  }
}

</style>
