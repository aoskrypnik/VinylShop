<template>
  <div>
    <div v-for="(prop, index) in getPropsNames" :key="prop">
      <p class="mb-0">{{getHeaders[index]}}</p>
      <item-edit class="mb-3" v-model="values[prop]" :type="getProps[prop]" :schema="schema"></item-edit>
    </div>
  </div>
</template>

<script>
import ItemEdit from '../items/ItemEdit.vue'

export default {
  components: {
    ItemEdit
  },
  props: ['schema', 'values'],
  data: function() {
    return {
    }
  },
  computed: {
    getHeaders() {
      return this.$store.getters.getSchemaHeaders(this.schema)
    },
    getPropsNames() {
      return this.$store.getters.getSchemaPropsNames(this.schema)
    },
    getProps() {
      return this.$store.getters.getSchemaProps(this.schema)
    }
  }
}
</script>

<style scoped>
</style>