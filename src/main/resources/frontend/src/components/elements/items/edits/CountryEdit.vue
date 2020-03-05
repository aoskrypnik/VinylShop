<template>
  <select @input="onInput" :value="value">
    <option v-for="[code, name] in $store.state.countries.entries()" :key="code" :value="code">{{name}}</option>
  </select>
</template>

<script>
  export default {
    name: "CountryEdit.vue",
    props: ['value'],
    methods: {
      onInput(e) {
        // eslint-disable-next-line
        console.log(e)
        this.$emit('input', e.target.value)
      }
    }
  }
</script>

<style scoped>

  select {
    background: black;
    color: white;
    border: 2px solid white;
    padding: 5px 10px;
    font-size: 18px !important;
    display: block;
    margin-bottom: 2px !important;
    border-radius: 0px;
    width: 250px;
  }

  .select-selected:after {
    background: black;
    color: white;
  }

</style>