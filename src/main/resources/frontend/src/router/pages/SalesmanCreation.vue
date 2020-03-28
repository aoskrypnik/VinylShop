<template>
  <div>
    <router-link :to="{ name: 'home' }" class="grayLink">{{$store.getters.getAppLocale('backToHome')}}</router-link>
    <h2 class="mb-4">{{$store.getters.getAppLocale('salesmanCreationTitle')}}</h2>
    <p class="step">{{$store.getters.getAppLocale('step')}} 1 – <router-link :to="{name: 'form', params: {schema: 'salesman'}}">{{$store.getters.getAppLocale('salesmanCreationStep1')}}</router-link></p>
    <p class="step">{{$store.getters.getAppLocale('step')}} 2 – {{$store.getters.getAppLocale('salesmanCreationStep2')}} <router-link :to="{name: 'register' }">{{$store.getters.getAppLocale('salesmanCreationStep22')}}</router-link></p>
  </div>
</template>

<script>
  export default {
    name: "SalesmanCreation"
  }
</script>

<style scoped>

  .step {
    font-size: 24px;
  }

  .step a {
    color: white;
    text-decoration: underline;
  }

</style>