<template>
  <div class="loginContainer">
    <router-link :to="{ name: 'register' }" class="grayLink">{{$store.getters.getAppLocale('registration')}}</router-link>
    <div class="vertMidCont">
      <div class="row vertMidEl">
          <x-header class="col-md-6">Автоматизована інформаційна система</x-header>
          <div class="col-md-6 loginArea">
            <black-input placeholder="Логін" :large="true" v-model="login"></black-input>
            <black-input placeholder="Пароль" password="true" :large="true" v-model="password"></black-input>
            <black-button large="true" @click="auth">Вхід</black-button>
          </div>
      </div>
    </div>
  </div>  
</template>

<script>
import XHeader from '../elements/typography/XHeader'
import BlackButton from '../elements/buttons/BlackButton'
import BlackInput from '../elements/inputs/BlackInput'

export default {
  components: {
    XHeader,
    BlackButton,
    BlackInput
  },
  data: function () {
    return {
      login: '',
      password: ''
    }
  },
  methods: {
    auth() {
      this.$store.dispatch('authenticate', { login: this.login, password: this.password })
          .then(() => {
            this.login = ''
            this.password = ''
            this.$router.push({
              name: 'home'
            })
          })
          .catch(() => {})
    }
  }
}
</script>

<style>
.loginContainer {
  color: white;
}

.vertMidCont {
  position: relative;
  height: calc(100vh - 70px);
}

.vertMidEl {
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
}

.loginArea {
  margin-top: 20px;
}
</style>