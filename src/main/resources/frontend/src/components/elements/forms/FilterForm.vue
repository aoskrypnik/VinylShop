<template>
  <div>
    <div v-for="key in keys" :key="key" :class="['item', {printInvisible: filterText(value[key]).length === 0}]">
      <p class="label printInvisible">{{schemaDictionary[key]}}</p>
      <filter-edit class="printInvisible" :type="schemaProps[key]" :value="value[key]" :schema="schema" @input="onInput($event, key)" ></filter-edit>
      <p class="printOnly filterPrint">{{schemaDictionary[key]}}: {{filterText(value[key])}}</p>
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
      },
      filterText(value) {
        if (value === null) {
           return ''
        }

        if (value.from === null && value.to === null) {
          return ''
        }

        if (Array.isArray(value)) {
          return value.join(', ')
        }

        if (value.from || value.to) {
          return this.$store.getters.getAppLocale('filtersFrom') + ' ' + (value.from || 'N/A')+ ', ' + this.$store.getters.getAppLocale('filtersTo') + ' ' + (value.to || 'N/A')
        }

        return value
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

  .printOnly {
    display: none;
  }

  @media print {
    .printInvisible {
      display: none;
    }

    .printOnly {
      display: block;
    }

    .filterPrint {
      font-size: 14pt;
    }
  }

</style>