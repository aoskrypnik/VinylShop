<template>
  <div class="viewRoot">
    <router-link :to="{ name: 'home' }" class="grayLink">{{$store.getters.getAppLocale('backToHome')}}</router-link>
    <page-header>{{schemaName}}</page-header>
    <div :class="{ filtersContainer: true, closed: !filtersOpen }" v-if="filtersOpen">
      <div class="scroller">
        <filter-form :schema="schema" v-model="filters"></filter-form>
        <div class="filtersButtonContainer">
          <black-button @click="resetItems">{{$store.getters.getAppLocale('applyFilters')}}</black-button>
        </div>
      </div>
    </div>
    <black-button class="mb-3" @click="toggleFilters">{{ $store.getters.getAppLocale('showFilters') }}</black-button>
    <black-button v-if="multiple" @click="selectAll">{{ $store.getters.getAppLocale('selectAll') }}</black-button>
    <div class="tableContainer">
      <items-list
              class="mb-3"
              :schema="schema"
              :items="items"
              :sort-attribute="sortAttribute"
              :sort-direction="sortDirection"

              @itemSelection="itemSelection"
              @sortChange="sortChange"
      ></items-list>
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
import * as SchemaUtils from '@/schemas/utils'
import FilterForm from "@/components/elements/forms/FilterForm";

export default {
  components: {
    FilterForm,
    PageHeader,
    ItemsList,
    BlackButton,
    ThreeDotsSpinner
  },
  props: ['schema', 'multiple'],
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
      sortAttribute: undefined,
      sortDirection: 0,
      filters: {},
      filtersOpen: false
    }
  },
  mounted: function() {
    this.loadMore()
    this.resetFilters()
  },
  methods: {
    loadMore() {
      this.error = false
      this.loading = true

      Api.getItems(this.schema, Config.itemsPerPage, this.items.length, this.sortAttribute, this.sortDirection, this.filters).then((newItems) => {
        this.loading = false
        this.items = this.items.concat(newItems)

        if (newItems.length < Config.itemsPerPage) {
          //this.availableMore = false
        }
      }).catch((e) => {
        if (!e.response || e.response.status !== 404) {
          this.error = true
        }
      })
    },
    resetItems() {
      this.items = []
      this.loadMore()
    },
    resetFilters() {
      this.filters = SchemaUtils.generateFilterObject(this.schema)
    },
    itemSelection(key) {
      this.$emit('itemSelection', key)
    },
    selectAll() {
      this.$emit('itemSelection', this.items.map(SchemaUtils.getKey(this.schema)))
    },
    sortChange(attribute) {
      if (attribute === this.sortAttribute) {
        if (this.sortDirection === 0) {
          this.sortDirection = 1
        } else {
          this.sortAttribute = undefined
        }
      } else {
        this.sortAttribute = attribute
        this.sortDirection = 0
      }
      this.resetItems()
    },
    toggleFilters() {
      this.filtersOpen = !this.filtersOpen
    }
  },
  watch: {
    schema() {
      this.resetItems()
      this.resetFilters()
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

  .filtersContainer {
    margin-bottom: 15px;
    overflow: hidden;
    transform-style: flat;
  }

  .filtersContainer > .scroller {
    transform: translateY(0);
    transition: transform 1s ease-in-out;
  }

  .filtersContainer.closed > .scroller {
    transform: translateY(-100%);
  }

  .filtersButtonContainer {
    margin-top: 30px;
    display: flex;
  }

</style>