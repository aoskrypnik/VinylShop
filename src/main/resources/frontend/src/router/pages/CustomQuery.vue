<template>
  <div>
    <router-link :to="{ name: 'home' }" class="grayLink">{{$store.getters.getAppLocale('backToHome')}}</router-link>
    <page-header>{{$store.getters.getAppLocale('customQuery')}}</page-header>
    <three-dots-spinner v-if="loading"></three-dots-spinner>
    <div class="tableContainer">
      <items-list
              class="mb-3"
              schema="artist"
              :items="items"
      ></items-list>
    </div>
  </div>
</template>

<script>
  import PageHeader from "@/components/elements/typography/PageHeader";

  import * as Api from '@/api'
  import ItemsList from "@/components/elements/lists/ItemsList";

  export default {
    name: "CustomQuery",
    components: {ItemsList, PageHeader},
    data: () => ({
      selectedArtist: null,
      items: [],
      loading: false
    }),
    mounted() {
      Api.customQuery().then(items => {
        this.items = items
      })
    }
  }
</script>

<style scoped>
  .tableContainer {
    overflow-x: scroll;
  }
</style>