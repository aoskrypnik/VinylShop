<template>
  <div>
    <page-header>{{schemaName + ' ' + itemString}}</page-header>
    <div
            v-for="prop in propsNames"
            :key="prop"

            class="mb-3"
    >
      <p class="mb-0">{{dictionary[prop]}}</p>
      <item
              :value="values[prop]"
              :type="schemaProps[prop]"
              :schema="schema"
              :large="true"
      ></item>
    </div>
    <three-dots-spinner v-if="loading"></three-dots-spinner>
  </div>
</template>

<script>
  import PageHeader from '../elements/typography/PageHeader'
  import Item from '../elements/items/Item'
  import ThreeDotsSpinner from '../elements/spinners/ThreeDotsSpinner'

  import * as Api from '@/api'
  import * as SchemaUtils from '@/schemas/utils'

  export default {
    components: {
      PageHeader,
      ThreeDotsSpinner,
      Item
    },
    props: ['schema', 'item'],
    data: function() {
      return {
        loading: true,
        values: {},
        wrongNullFields: new Set()
      }
    },
    computed: {
      schemaName() {
        return this.$store.getters.getSchemaLocale(this.schema)
      },
      itemString() {
        return SchemaUtils.valueString(this.schema, this.values)
      },
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
      updateItem() {
        Api.getItem(this.schema, this.item).then((values) => {
          this.loading = false;
          this.values = values
        })
      },
      visible(prop) {
        return SchemaUtils.isListVisible(this.schemaProps[prop])
      }
    },
    mounted: function() {
      this.updateItem()
    },
    watch: {
      item() {
        this.updateItem()
      }
    }
  }
</script>

<style>

</style>