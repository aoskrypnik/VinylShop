<template>
  <div>
    <page-header>{{schemaName}}</page-header>
    <item-form :schema="schema" :values="values"></item-form>
    <three-dots-spinner v-if="loading"></three-dots-spinner>
  </div>
</template>

<script>
import PageHeader from '../elements/typography/PageHeader'
import ItemForm from '../elements/forms/ItemForm'
import ThreeDotsSpinner from '../elements/spinners/ThreeDotsSpinner'

import * as Api from '@/api'

export default {
  components: {
    PageHeader,
    ItemForm,
    ThreeDotsSpinner
  },
  props: ['schema', 'item'],
  data: function() {
    return {
      loading: true,
      values: {}
    }
  },
  computed: {
    schemaName() {
      return this.$store.getters.getSchemaLocale(this.schema)
    }
  },
  mounted: function() {
    Api.getItem(this.schema, this.item).then((values) => {
      this.loading = false
      this.values = values
    })
  }
}
</script>

<style>

</style>