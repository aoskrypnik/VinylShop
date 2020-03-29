<template>
  <div>
    <router-link :to="{ name: 'stats' }" class="grayLink">{{$store.getters.getAppLocale('backToStats')}}</router-link>
    <page-header>{{$store.getters.getAppLocale('statsBySalesmen')}}</page-header>
    <stats-date-form v-model="dates"></stats-date-form>
    <black-button @click="applyDates">{{$store.getters.getAppLocale('applyFilters')}}</black-button>
    <three-dots-spinner v-if="loading"></three-dots-spinner>

    <div v-if="results && !loading" class="invert mt-5">
      <h4>{{$store.getters.getAppLocale('checksNum')}}</h4>
      <GChart
              type="PieChart"
              :data="chartDataCheckNums"
              :options="options"
      >
      </GChart>
      <h4>{{$store.getters.getAppLocale('income')}}</h4>
      <GChart
              type="PieChart"
              :data="chartDataIncome"
              :options="options"
      >
      </GChart>
      <h4>{{$store.getters.getAppLocale('avgIncome')}}</h4>
      <GChart
              type="PieChart"
              :data="chartDataAvgIncome"
              :options="options"
      >
      </GChart>
      <h4>{{$store.getters.getAppLocale('proceeds')}}</h4>
      <GChart
              type="PieChart"
              :data="chartDataProceeds"
              :options="options"
      >
      </GChart>
    </div>
    <div v-if="error">
      <error-view :type="errorType" operation="stats" @retry="applyDates"></error-view>
    </div>
  </div>
</template>

<script>
  import PageHeader from "@/components/elements/typography/PageHeader";
  import { GChart } from 'vue-google-charts'
  import BlackButton from "@/components/elements/buttons/BlackButton";
  import ThreeDotsSpinner from '@/components/elements/spinners/ThreeDotsSpinner';
  import ErrorView from "@/components/views/ErrorView";
  import StatsDateForm from "@/components/elements/forms/StatsDateForm";

  import * as Api from '@/api'
  import * as SchemaUtils from '@/schemas/utils'

  export default {
    name: "SalesmenStats",
    components: {
      StatsDateForm,
      ErrorView,
      PageHeader,
      BlackButton,
      ThreeDotsSpinner,
      GChart
    },
    data: () => ({
      dates: {
        from: null,
        to: null
      },
      results: null,
      loading: false,
      options: {
      },
      error: false,
      errorType: null,
      chartDataCheckNums: [],
      chartDataIncome: [],
      chartDataAvgIncome: [],
      chartDataProceeds: []
    }),
    methods: {
      applyDates() {
        this.loading = true
        this.error = false

        this.chartDataCheckNums = []
        this.chartDataIncome = []
        this.chartDataAvgIncome = []
        this.chartDataProceeds = []

        Api.statsBySalesmen(this.dates.from, this.dates.to).then((results) => {
          this.results = results;
          this.calculateCharts()
        }).catch(e => {
          this.error = true
          this.errorType = this.$store.getters.getErrorType(e)
          this.loading = false
          this.results = null
        })
      },
      calculateCharts() {
        const salesmenNumbers = Object.keys(this.results)
        const salesmenDisplaysP = salesmenNumbers.map(n => SchemaUtils.fkValue('salesman', n).then(d => [n, d]))

        Promise.all(salesmenDisplaysP).then(salesmenDisplays => {
          this.chartDataCheckNums = [["Продавець", this.$store.getters.getAppLocale('checksNum')]].concat(salesmenDisplays.map(([n, d]) => [d, this.results[n].salesmanChecksNum]));
          this.chartDataIncome = [["Продавець", this.$store.getters.getAppLocale('income')]].concat(salesmenDisplays.map(([n, d]) => [d, this.results[n].salesmanIncome]));
          this.chartDataAvgIncome = [["Продавець", this.$store.getters.getAppLocale('avgIncome')]].concat(salesmenDisplays.map(([n, d]) => [d, this.results[n].salesmanAvgIncome]));
          this.chartDataProceeds = [["Продавець", this.$store.getters.getAppLocale('proceeds')]].concat(salesmenDisplays.map(([n, d]) => [d, this.results[n].salesmanProceeds]));

          this.loading = false
        })
      }
    },
    computed: {
      salesmenNumbers() {
        return Object.keys(this.results)
      }
    }
  }
</script>

<style scoped>

  .invert {
    filter: invert(100%);
  }

  p {
    margin: 0;
  }

  h4 {
    color: black;
  }

</style>