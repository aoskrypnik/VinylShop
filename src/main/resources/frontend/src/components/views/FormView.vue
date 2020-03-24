<template>
  <div>
    <router-link :to="{ name: 'entry', params: { schema, item } }" class="grayLink">{{$store.getters.getAppLocale('backToEntry')}}</router-link>
    <page-header>{{schemaName + ' ' + (item ? itemString : $store.getters.getAppLocale('newEntry'))}}</page-header>
    <item-form :schema="schema" :wrong-null-fields="wrongNullFields" v-model="values"></item-form>
    <black-button @click="validate">{{$store.getters.getAppLocale('saveEntry')}}</black-button>
    <three-dots-spinner v-if="loading"></three-dots-spinner>
  </div>
</template>

<script>
import PageHeader from '../elements/typography/PageHeader'
import ItemForm from '../elements/forms/ItemForm'
import ThreeDotsSpinner from '../elements/spinners/ThreeDotsSpinner'

import * as Api from '@/api'
import * as SchemaUtils from '@/schemas/utils'
import BlackButton from "@/components/elements/buttons/BlackButton";

export default {
  components: {
    BlackButton,
    PageHeader,
    ItemForm,
    ThreeDotsSpinner
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
    }
  },
  methods: {
    updateItem() {
      if (this.item) {
        Api.getItem(this.schema, this.item).then((values) => {
          this.loading = false
          this.values = values
        })
      } else {
        this.loading = false
        this.values = {}
      }
    },
    new() {

    },
    validate() {
      this.wrongNullFields = new Set(SchemaUtils.nullCheck(this.schema, this.values))

      if (this.wrongNullFields.size === 0) {
        this.loading = true

        if (this.item) {
          Api.updateItem(this.schema, this.values, this.item).then(() => {
            this.updateItem()
          })
        } else {
          Api.newItem(this.schema, this.values).then((item) => {
            this.loading = false
            this.$router.push(
                {
                  name: 'entry',
                  params: {
                    schema: this.schema,
                    item
                  }
                }
            )
          })
        }
      }
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