<template>
  <div class="formContainer d-flex flex-row flex-wrap">
    <div class="item">
      <p>{{$store.getters.getAppLocale('filtersFrom')}}:</p>
      <date-edit type="date" :value="value.from" @input="$emit('input', { from: $event, to: value.to })"></date-edit>
    </div>
    <div class="item">
      <p>{{$store.getters.getAppLocale('filtersTo')}}:</p>
      <date-edit type="date" :value="value.to" @input="$emit('input', { from: value.from, to: $event })"></date-edit>
    </div>
  </div>
</template>

<script>
  import DateEdit from "@/components/elements/items/edits/DateEdit";
  export default {
    name: "StatsDateForm",
    components: {DateEdit},
    props: ['value']
  }
</script>

<style scoped>

  .item {
    margin-right: 10px;
  }

  p {
    margin: 0;
  }

  .formContainer {
    margin-bottom: 20px;
  }

</style>