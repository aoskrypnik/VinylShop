<template>
  <div>
    <div v-for="prop in propsNames" :key="prop" class="mb-3">
      <p class="mb-0">{{dictionary[prop]}}</p>
      <item-edit :value="value[prop]" :type="schemaProps[prop]" :schema="schema" @input="onInput($event, prop)"></item-edit>
      <p class="error" v-if="wrongNullFields.has(prop)">{{$store.getters.getAppLocale('cantBeNull')}}</p>
    </div>
  </div>
</template>

<script>
  import ItemEdit from '../items/ItemEdit.vue'

  import * as SchemaUtils from '@/schemas/utils'

  export default {
    components: {
      ItemEdit
    },
    props: ['schema', 'value', 'wrongNullFields', 'newitem'],
    data: function() {
      return {

      }
    },
    computed: {
      dictionary() {
        return this.$store.getters.getSchemaDictionary(this.schema)
      },
      propsNames() {
        return this.$store.getters.getSchemaPropsNames(this.schema).filter(this.visible)
      },
      schemaProps() {
        return this.$store.getters.getSchemaProps(this.schema)
      }
    },
    methods: {
      onInput(newValue, propName) {
        const copy = { ...this.value }
        copy[propName] = newValue
        this.$emit('input', copy)
      },
      visible(prop) {
        return (this.newitem ? SchemaUtils.isNewFormVisible : SchemaUtils.isEditFormVisible)(this.schemaProps[prop])
      }
    }
  }
</script>

<style scoped>

  .error {
    color: #d87878;
    margin: 0;
  }

</style>