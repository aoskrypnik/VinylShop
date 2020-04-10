<template>
  <div>
    <div v-if="!error && !loading">
      <router-link :to="{ name: 'list', params: { schema } }" class="grayLink">{{$store.getters.getAppLocale('backToList')}}</router-link>
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
      <div class="buttonGroup">
        <black-button v-if="isEditable" @click="editEntry">{{$store.getters.getAppLocale('editEntry')}}</black-button>
        <black-button @click="deleteEntry">{{$store.getters.getAppLocale('removeEntry')}}</black-button>
      </div>
    </div>
    <div v-if="error">
      <error-view :type="errorType" operation="load" @retry="retry" @return="returnToList"></error-view>
    </div>
    <three-dots-spinner v-if="loading"></three-dots-spinner>
  </div>
</template>

<script>
  import PageHeader from '../elements/typography/PageHeader'
  import Item from '../elements/items/Item'
  import ThreeDotsSpinner from '../elements/spinners/ThreeDotsSpinner'
  import BlackButton from "@/components/elements/buttons/BlackButton";
  import ErrorView from "@/components/views/ErrorView";

  import * as Api from '@/api'
  import * as SchemaUtils from '@/schemas/utils'

  export default {
    components: {
      ErrorView,
      BlackButton,
      PageHeader,
      ThreeDotsSpinner,
      Item
    },
    props: ['schema', 'item'],
    data: function() {
      return {
        loading: true,
        values: null,
        wrongNullFields: new Set(),
        errorType: null,
        error: false
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
      },
      isEditable() {
        return SchemaUtils.isEditable(this.schema, this.values)
      }
    },
    methods: {
      updateItem() {
        this.error = false
        Api.getItem(this.schema, this.item).then(values => {
          this.loading = false;
          this.values = values
        }).catch(e => {
          this.error = true
          this.errorType = this.$store.getters.getErrorType(e)
          this.loading = false
          this.values = null
        })
      },
      visible(prop) {
        return SchemaUtils.isListVisible(this.schemaProps[prop])
      },
      retry() {
        this.updateItem()
      },
      returnToList() {
        this.$router.push({
          name: 'list',
          params: { schema: this.schema }
        })
      },
      deleteEntry() {
        this.$store.dispatch(
            'addPopup',
            {
              type: 'dialog',
              properties: {
                title: this.$store.getters.getAppLocale('deleteConfirmationTitle'),
                text: this.$store.getters.getAppLocale('deleteConfirmationMessage'),
                buttons: [
                  {
                    label: this.$store.getters.getAppLocale('deleteCancel'),
                    onClick: () => {
                    }
                  },
                  {
                    label: this.$store.getters.getAppLocale('removeEntry'),
                    onClick: () => {
                      this.loading = true
                      Api.deleteItem(this.schema, this.item).then(() => {
                        this.$router.push({
                          name: 'list',
                          params: {
                            schema: this.schema
                          }
                        })
                      }).catch(() => {
                        // TODO Handle deletion error
                      })
                    }
                  }
                ]
              }
            })
      },
      editEntry() {
        this.$router.push({
          name: 'form',
          params: {
            schema: this.schema,
            item: this.item
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