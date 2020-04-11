<template>
  <div id="app" class="container">
    <div class="row header">
      <span class="logo col-md-6" @click="$router.push({name: 'home'})">MyVinyl</span>
      <span>
        <span class="headerLink" @click="$store.commit('toggleLanguage')">{{language === 'en' ? 'UA' : 'EN'}}</span>
        <router-link v-if="isAuthenticated()" class="headerLink" to="/password" tag="span">{{$store.getters.getAppLocale('changePassword')}}</router-link>
        <span v-if="isAuthenticated()" class="headerLink" @click="signOut">{{$store.getters.getAppLocale('signOut')}}</span>
      </span>
    </div>
    <router-view/>
    <div class="popups">
      <transition-group name="fade">
        <PopupView v-for="content in popups" :key="content.key" :type="content.type" :properties="content.properties" @close="closePopup" transition="fade"></PopupView>
      </transition-group>
    </div>
    <p class="bottomPrint">{{$store.getters.getAppLocale('generated') + ' ' + new Date().toUTCString()}}</p>
  </div>
</template>

<script>
import PopupView from "./components/views/PopupView";
import {mapState, mapGetters} from "vuex";

export default {
  name: 'app',
  components: {
    PopupView
  },
  computed: {
    ...mapState(['popups', 'language']),
    ...mapGetters(['isAuthenticated'])
  },
  methods: {
    closePopup() {
      this.$store.commit('closePopup')
    },
    signOut() {
      this.$store.dispatch('signOut').then(() => this.$router.push({ name: 'login' }))
    }
  }
}
</script>

<style>

  body {
    background: black !important;
    color: white !important;

    scrollbar-color: #595959 black;
  }

  #app {
    font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, Oxygen, Ubuntu, Cantarell, 'Open Sans', 'Helvetica Neue', sans-serif;
  }

  .header {
    justify-content: space-between;
  }

  .logo {
    line-height: 70px;
    font-size: 18pt;
    font-weight: 400;
    color: white;
    user-select: none;
    cursor: pointer;
  }

  .headerLink {
    line-height: 70px;
    cursor: pointer;
    user-select: none;
    margin-left: 20px;
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

  .grayLink {
    color: lightgrey;
  }

  .grayLink::before {
    content: '< ';
  }

  .grayLink:hover {
    color: white;
  }

  .buttonGroup {
    display: flex;
  }

  .buttonGroup > * {
    margin-right: 5px;
  }

  h2 {
    font-weight: bold !important;
  }

  .error {
    color: #d87878;
  }

  @page {
    size: auto;
    margin: 0;
  }

  .bottomPrint {
    display: none;
  }

  @media print {
    body {

    }

    table td,th {
      border: 1px black solid;
    }

    .headerLink, .grayLink, button {
      display: none !important;
    }

    .bottomPrint {
      display: block;
      position: absolute;
      bottom: 10px;
    }
  }

</style>
