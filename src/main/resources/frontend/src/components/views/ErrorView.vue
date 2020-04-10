<template>
  <div class="row">
    <div class="col-md-4 col-6 img"><img src="@/assets/sad_record.png"></div>
    <div class="col-md-8 col-12">
      <p class="errorTitle error">{{errorMessage.title}}</p>
      <p>{{errorMessage.message}}</p>
      <black-button v-if="buttonType === 'retry'" @click="retry">{{$store.getters.getAppLocale('retryLoad')}}</black-button>
      <black-button v-if="buttonType === 'return'" @click="returnToList">{{$store.getters.getAppLocale('returnToList')}}</black-button>
      <black-button v-if="buttonType === 'home'" @click="returnToHome">{{$store.getters.getAppLocale('returnToHome')}}</black-button>
    </div>
  </div>
</template>

<script>
  import BlackButton from "@/components/elements/buttons/BlackButton";
  export default {
    name: "ErrorView",
    components: {BlackButton},
    props: ['type', 'operation'],
    computed: {
      errorMessage() {
        return this.$store.getters.getErrorMessage(this.operation, this.type)
      },
      buttonType() {
        // TODO implement home button (and home itself)
        switch(this.type) {
          case 'notfound':
            return 'return'
          case 'forbidden':
            return 'home'
          default:
            return 'retry'
        }
      }
    },
    methods: {
      retry() {
        this.$emit('retry')
      },
      returnToList() {
        this.$emit('return')
      },
      returnToHome() {
        this.$router.push({name: 'home'})
      }
    }
  }
</script>

<style scoped>

  .img img {
    width: 100%;
  }

  .errorTitle {
    font-weight: bold;
    font-size: 36px;
  }

</style>