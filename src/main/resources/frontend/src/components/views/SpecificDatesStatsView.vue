<template>
  <div>
    <router-link :to="{ name: 'stats' }" class="grayLink">{{$store.getters.getAppLocale('backToStats')}}</router-link>
    <page-header>{{$store.getters.getAppLocale('statsByDate')}}</page-header>
    <stats-date-form v-model="dates"></stats-date-form>
    <black-button @click="applyDates">{{$store.getters.getAppLocale('applyFilters')}}</black-button>
    <three-dots-spinner v-if="loading"></three-dots-spinner>

    <div v-if="!loading && results"  class="statsResults">
      <h4>{{$store.getters.getAppLocale('statsResult')}}</h4>
      <div class="resultItem">
        <p class="mb-0">{{$store.getters.getAppLocale('income')}}</p>
        <item
                type="money"
                :value="results.income"
                :large="true"
        ></item>
      </div>
      <div class="resultItem">
        <p class="mb-0">{{$store.getters.getAppLocale('avgIncome')}}</p>
        <item
                type="money"
                :value="results.avgIncome"
                :large="true"
        ></item>
      </div>
      <div class="resultItem">
        <p class="mb-0">{{$store.getters.getAppLocale('proceeds')}}</p>
        <item
                type="money"
                :value="results.proceeds"
                :large="true"
        ></item>
      </div>
      <div class="resultItem">
        <p class="mb-0">{{$store.getters.getAppLocale('checksNum')}}</p>
        <item
                type="int"
                :value="results.checksNum"
                :large="true"
        ></item>
      </div>
    </div>
    <div v-if="error">
      <error-view :type="errorType" operation="stats" @retry="applyDates"></error-view>
    </div>
  </div>
</template>

<script>
  import PageHeader from "@/components/elements/typography/PageHeader";
  import StatsDateForm from "@/components/elements/forms/StatsDateForm";
  import BlackButton from "@/components/elements/buttons/BlackButton";
  import ThreeDotsSpinner from '@/components/elements/spinners/ThreeDotsSpinner';
  import Item from "@/components/elements/items/Item";

  import * as Api from '@/api'
  import ErrorView from "@/components/views/ErrorView";

  export default {
    name: "SpecificDatesStatsView",
    components: {ErrorView, Item, BlackButton, StatsDateForm, PageHeader, ThreeDotsSpinner},
    data: () => ({
      dates: {
        from: null,
        to: null
      },
      loading: false,
      results: null,
      error: false,
      errorType: null
    }),
    methods: {
      applyDates() {
        this.loading = true
        this.error = false
        Api.statsByDate(this.dates.from, this.dates.to).then((results) => {
          this.results = results;
          this.loading = false
        }).catch(e => {
          this.error = true
          this.errorType = this.$store.getters.getErrorType(e)
          this.loading = false
          this.results = null
        })
      }
    }
  }
</script>

<style scoped>

  .statsResults {
    margin-top: 30px;
  }

  .resultItem {
    margin-bottom: 10px;
  }

</style>