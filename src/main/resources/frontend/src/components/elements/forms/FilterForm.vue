<template>
  <div>

    <black-button v-if="!dynamic">{{$store.getters.getAppLocale('applyFilters')}}</black-button>
  </div>
</template>

<script>
  import BlackButton from "../buttons/BlackButton";
  export default {
    name: "FilterForm",
    components: {BlackButton},
    props: ['schema', 'dynamic']
  }
</script>

<style scoped>

</style>