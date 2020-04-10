<template>
  <div>
    <router-link :to="{ name: 'stats' }" class="grayLink">{{$store.getters.getAppLocale('backToStats')}}</router-link>
    <page-header>{{$store.getters.getAppLocale('statsByMonth')}}</page-header>
    <p>{{$store.getters.getAppLocale('year')}}:</p>
    <number-edit :type="{ type: 'number', typeConstraint: {from: 2000, to: currentYear} }" v-model="selectedYear"></number-edit>
    <black-button @click="applyYear">{{$store.getters.getAppLocale('applyFilters')}}</black-button>
    <three-dots-spinner v-if="loading"></three-dots-spinner>

    <div v-if="results" class="invert">
      <GChart
        type="LineChart"
        :data="chartData"
        :options="options"
      >
      </GChart>
      <GChart
              type="LineChart"
              :data="chartDataChecks"
              :options="options"
      >
      </GChart>
    </div>
    <div v-if="error">
      <error-view :type="errorType" operation="stats" @retry="applyYear"></error-view>
    </div>
  </div>
</template>

<script>
  import PageHeader from "@/components/elements/typography/PageHeader";
  import { GChart } from 'vue-google-charts'
  import NumberEdit from "@/components/elements/items/edits/NumberEdit";
  import BlackButton from "@/components/elements/buttons/BlackButton";
  import ThreeDotsSpinner from '@/components/elements/spinners/ThreeDotsSpinner';

  import * as Moment from 'moment'
  import * as Api from '@/api'
  import ErrorView from "@/components/views/ErrorView";

  export default {
    name: "MonthStatsView",
    components: {
      ErrorView,
      NumberEdit,
      PageHeader,
      BlackButton,
      ThreeDotsSpinner,
      GChart
    },
    data: () => ({
      currentYear: Moment().year(),
      selectedYear: Moment().year(),
      results: null,
      loading: false,
      options: {
        chartArea: {width: '70%'}
      },
      error: false,
      errorType: null
    }),
    methods: {
      applyYear() {
        this.loading = true
        Api.statsByMonths(`${this.selectedYear}-01-01`).then(values => {
          this.results = values;
          this.loading = false
        }).catch(e => {
          this.error = true
          this.errorType = this.$store.getters.getErrorType(e)
          this.loading = false
          this.results = null
        })
      }
    },
    computed: {
      chartData() {
        const months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
        if (!this.results) {
          return []
        }
        return [
          [
            this.$store.getters.getAppLocale('month'),
            this.$store.getters.getAppLocale('income'),
            this.$store.getters.getAppLocale('avgIncome'),
            this.$store.getters.getAppLocale('proceeds')
          ],
          ...months
              .map((month, i) => [
                month,
                this.results[i+1].income/100 || 0,
                this.results[i+1].avgCheck/100 || 0,
                this.results[i+1].proceeds/100 || 0
              ])
        ]
      },
      chartDataChecks() {
        const months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
        if (!this.results) {
          return []
        }
        return [
          [
            this.$store.getters.getAppLocale('month'),
            this.$store.getters.getAppLocale('checksNum')
          ],
          ...months
              .map((month, i) => [
                month,
                this.results[i+1].checksNum,
              ])
        ]
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

</style>