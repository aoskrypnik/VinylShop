<template>
  <div>
    <router-link :to="{ name: 'home' }" class="grayLink">{{$store.getters.getAppLocale('backToHome')}}</router-link>
    <page-header>{{$store.getters.getAppLocale('changePassword')}}</page-header>
    <black-input :placeholder="$store.getters.getAppLocale('oldPassword')" password="true" v-model="oldPassword"></black-input>
    <black-input :placeholder="$store.getters.getAppLocale('newPassword')" password="true" v-model="password"></black-input>
    <black-input :placeholder="$store.getters.getAppLocale('newPassword')" password="true" :wrong="passMismatch" v-model="password2"></black-input>
    <black-button :disabled="!formValid" @click="change">{{$store.getters.getAppLocale('change')}}</black-button>
    <three-dots-spinner v-if="loading"></three-dots-spinner>
    <div v-if="error">
      <error-view type="generic" operation="password" @retry="change"></error-view>
    </div>
  </div>
</template>

<script>
  import {mapGetters} from "vuex";
  import PageHeader from "@/components/elements/typography/PageHeader";
  import BlackInput from "@/components/elements/inputs/BlackInput";
  import BlackButton from "@/components/elements/buttons/BlackButton";

  import * as Api from '@/api'
  import ErrorView from "@/components/views/ErrorView";

  export default {
    name: "ChangePassword",
    components: {ErrorView, BlackButton, BlackInput, PageHeader},
    data: () => ({
      password: '',
      password2: '',
      oldPassword: '',
      error: false
    }),
    computed: {
      ...mapGetters(['username']),
      passMismatch() {
        return this.password !== this.password2
      },
      formValid() {
        return !this.passMismatch && this.password && this.password.length >= 6
      }
    },
    methods: {
      change() {
        this.loading = true
        this.error = false
        Api.changePassword(this.username, this.oldPassword, this.password).then(() => {
          this.loading = false
          this.$router.push({name: 'home'})
          this.$store.dispatch(
              'addPopup',
              {
                type: 'dialog',
                properties: {
                  title: this.$store.getters.getAppLocale('passwordSuccessful'),
                  buttons: [
                    {
                      label: 'OK',
                      onClick: () => {
                      }
                    }
                  ]
                }
              })
        }).catch(() => {
          this.loading = false
          this.error = true
        })
      }
    }
  }
</script>

<style scoped>

</style>