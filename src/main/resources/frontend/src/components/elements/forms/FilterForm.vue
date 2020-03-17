<template>
  <div>
    <div v-for="key in keys" :key="key" class="item">
      <p class="label">{{schemaDictionary[key]}}</p>
      <filter-edit :type="schemaProps[key]" :value="value[key]" @input="onInput($event, key)"></filter-edit>
    </div>
  </div>
</template>

<script>
  import FilterEdit from "@/components/elements/items/FilterEdit";

  export default {
    name: "FilterForm",
    components: {FilterEdit},
    props: ['schema', 'dynamic', 'value'],
    computed: {
      keys() {
        return Object.keys(this.value)
      },
      schemaProps() {
        return this.$store.getters.getSchemaProps(this.schema)
      },
      schemaDictionary() {
        return this.$store.getters.getSchemaDictionary(this.schema)
      }
    },
    methods: {
      onInput(val, key) {
        this.$emit('input', {
          ...this.value,
          [key]: val
        })
      }
    }
  }
</script>

<style scoped>

  p.label {
    margin: 0;
  }

  .item {
    margin-bottom: 10px;
  }

</style>