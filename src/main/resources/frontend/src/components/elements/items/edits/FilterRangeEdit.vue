<template>
  <div class="rangeContainer">
    <div class="rangeItem">
      <p>{{$store.getters.getAppLocale('filtersFrom')}}:</p>
      <item-edit :value="value.from" :type="type" :schema="schema" @input="onFromInput"></item-edit>
    </div>
    <div class="rangeItem">
      <p>{{$store.getters.getAppLocale('filtersTo')}}:</p>
      <item-edit :value="value.to" :type="type" :schema="schema" @input="onToInput"></item-edit>
    </div>
  </div>
</template>

<script>
  import ItemEdit from "@/components/elements/items/ItemEdit";
  export default {
    name: "FilterRangeEdit",
    components: {ItemEdit},
    props: ['value', 'type', 'schema'],
    methods: {
      onFromInput(val) {
        this.$emit('input', {
          from: val,
          to: this.value.to
        })
      },
      onToInput(val) {
        this.$emit('input', {
          from: this.value.from,
          to: val
        })
      }
    }
  }
</script>

<style scoped>

  .rangeContainer {
    display: flex;
  }

  p {
    margin: 0;
  }

  .rangeItem {
    margin-right: 20px;
  }

</style>