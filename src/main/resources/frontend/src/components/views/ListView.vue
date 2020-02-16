<template>
  <div class="viewRoot">
    <page-header>{{schemaName}}</page-header>
    <black-button class="mb-3">{{ $store.getters.getAppLocale('showFilters') }}</black-button>
    <div class="tableContainer">
      <items-list class="mb-3" :schema="schema" :items="items" @itemSelection="itemSelection"></items-list>
    </div>
    <three-dots-spinner v-if="loading"></three-dots-spinner>
    <p v-if="error" class="error mx-auto">{{ $store.getters.getAppLocale( serverError ? 'serverListError' : 'otherListError') }}</p>
    <black-button v-if="!loading && availableMore" class="mx-auto" @click="loadMore">{{$store.getters.getAppLocale('loadMore')}}</black-button>
  </div>
</template>

<script>
import PageHeader from '../elements/typography/PageHeader'
import ItemsList from '../elements/lists/ItemsList'
import BlackButton from '../elements/buttons/BlackButton'
import ThreeDotsSpinner from '../elements/spinners/ThreeDotsSpinner'

import * as Config from '@/config'
import * as Api from '@/api'

export default {
  components: {
    PageHeader,
    ItemsList,
    BlackButton,
    ThreeDotsSpinner
  },
  props: ['schema'],
  computed: {
    schemaName() {
      return this.$store.getters.getSchemaLocale(this.schema)
    }
  },
  data: function() {
    return {
      items: [],
      availableMore: true,
      loading: true,
      error: false,
      serverError: false,
      sortField: undefined,
      sortDirection: 'desc'
    }
  },
  mounted: function() {
    this.loadMore()
  },
  methods: {
    loadMore() {
      this.error = false
      this.loading = true

      Api.getItems(this.schema).then((newItems) => {
        this.loading = false
        this.items = this.items.concat(newItems)

        if (newItems.length < Config.itemsPerPage) {
          //this.availableMore = false
        }
      }).catch(() => {
        this.error = true
      })
    },
    itemSelection(key) {
      this.$emit('itemSelection', key)
    }
  },
  watch: {
    schema() {
      this.items = []
      this.loadMore()
    }
  }
}
</script>

<style scoped>
  .error {
    max-width: 500px;
    text-align: center;
    font-size: 20px;
    color: #d87878;
    margin-top: 20px;
  }

  .tableContainer {
    overflow-x: scroll;
  }

  .listHeading {
    font-size: 32px;
    font-weight: bold;
  }

  .viewRoot {
    padding-bottom: 40px;
  }
</style>