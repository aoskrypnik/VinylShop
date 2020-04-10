<template>
  <div>
    <router-link :to="{ name: 'entry', params: { schema, item } }" class="grayLink">{{$store.getters.getAppLocale('backToEntry')}}</router-link>
    <page-header  v-if="!error || errorOperation !== 'load'">{{schemaName + ' ' + (item ? itemString : $store.getters.getAppLocale('newEntry'))}}</page-header>
    <item-form v-if="!error || errorOperation !== 'load'" :schema="schema" :wrong-null-fields="wrongNullFields" :newitem="!item" v-model="values"></item-form>
    <black-button v-if="!error || errorOperation !== 'load'" @click="validate">{{$store.getters.getAppLocale('saveEntry')}}</black-button>
    <div v-if="error">
      <error-view :type="errorType" :operation="errorOperation" @retry="updateItem" @return="returnToList"></error-view>
    </div>
    <three-dots-spinner v-if="loading"></three-dots-spinner>
  </div>
</template>

<script>
import PageHeader from '../elements/typography/PageHeader'
import ItemForm from '../elements/forms/ItemForm'
import ThreeDotsSpinner from '../elements/spinners/ThreeDotsSpinner'
import BlackButton from "@/components/elements/buttons/BlackButton";
import ErrorView from "@/components/views/ErrorView";

import * as Api from '@/api'
import * as SchemaUtils from '@/schemas/utils'

export default {
  components: {
    BlackButton,
    PageHeader,
    ItemForm,
    ThreeDotsSpinner,
    ErrorView
  },
  props: ['schema', 'item'],
  data: function() {
    return {
      loading: true,
      values: {},
      wrongNullFields: new Set(),
      error: false,
      errorOperation: 'load',
      errorType: ''
    }
  },
  computed: {
    schemaName() {
      return this.$store.getters.getSchemaLocale(this.schema)
    },
    itemString() {
      return SchemaUtils.valueString(this.schema, this.values)
    },
    valid() {
      return SchemaUtils.getKey(this.schema)(this.values) !== undefined
    }
  },
  methods: {
    updateItem() {
      if (this.item) {
        Api.getItem(this.schema, this.item).then((values) => {
          this.loading = false
          this.values = values
        }).catch(e => {
          this.error = true
          this.errorOperation = 'load'
          this.errorType = this.$store.getters.getErrorType(e)
          this.loading = false
          this.values = null
        })
      } else {
        this.loading = false
        this.values = {}
      }
    },
    new() {
      // TODO success popup
    },
    validate() {
      this.wrongNullFields = new Set(SchemaUtils.nullCheck(this.schema, this.values))

      if (this.wrongNullFields.size === 0) {
        this.loading = true
        this.error = false

        if (this.item) {
          Api.updateItem(this.schema, this.values, this.item).then(() => {
            this.loading = false
            this.$router.push({
              name: 'form',
              params: {
                schema: this.schema,
                item: SchemaUtils.getKey(this.schema)(this.values)
              }
            })
          }).catch(e => {
            this.error = true
            this.errorOperation = 'save'
            this.errorType = this.$store.getters.getErrorType(e)
            this.loading = false
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
          }).catch(e => {
            // eslint-disable-next-line
            console.log(e)
            this.error = true
            this.errorOperation = 'save'
            this.errorType = this.$store.getters.getErrorType(e)
            this.loading = false
          })
        }
      }
    },
    returnToList() {
      this.$router.push({
        name: 'list',
        params: {
          schema: this.schema
        }
      })
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