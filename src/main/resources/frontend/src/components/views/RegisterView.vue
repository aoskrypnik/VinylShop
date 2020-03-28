<template>
  <div class="loginContainer">
    <router-link :to="{ name: 'login' }" class="grayLink">{{$store.getters.getAppLocale('login')}}</router-link>
    <div class="vertMidCont">
      <div class="row vertMidEl">
        <x-header class="col-md-6">Реєстрація продавця</x-header>
        <div class="col-md-6 loginArea">
          <black-input placeholder="Табельний номер" :large="true" v-model="tabNum"></black-input>
          <black-input placeholder="Логін" :large="true" v-model="login"></black-input>
          <black-input placeholder="Пароль" :large="true" password="true" v-model="password"></black-input>
          <black-input placeholder="Пароль (повторити)" password="true" :large="true" :wrong="passMismatch" v-model="password2"></black-input>
          <black-button large="true" :disabled="!formValid" @click="register">Реєстрація</black-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  import XHeader from '../elements/typography/XHeader'
  import BlackButton from '../elements/buttons/BlackButton'
  import BlackInput from '../elements/inputs/BlackInput'

  import * as Api from '@/api'

  export default {
    components: {
      XHeader,
      BlackButton,
      BlackInput
    },
    data: function () {
      return {
        tabNum: '',
        login: '',
        password: '',
        password2: ''
      }
    },
    computed: {
      passMismatch() {
        return this.password !== this.password2
      },
      formValid() {
        return !this.passMismatch && this.password && this.tabNum && this.login
      }
    },
    methods: {
      register() {
        Api.registerSalesman(this.tabNum, this.login, this.password)
            .then(() => {
              this.login = ''
              this.password = ''
              this.$router.push({
                name: 'login'
              })
            })
      }
    }
  }
</script>

<style scoped>

</style>