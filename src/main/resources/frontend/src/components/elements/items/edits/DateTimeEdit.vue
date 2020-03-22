<template>
  <div class="editContainer">
    <date-edit :value="date" @input="dateInput"></date-edit>
    <div class="time">
      <number-edit :type="hourType" :value="hour" :narrow="true"></number-edit>
      <number-edit :type="minuteType" :value="minute" :narrow="true"></number-edit>
    </div>
    <black-button @click="now">{{ $store.getters.getAppLocale('dateTimeNow') }}</black-button>
  </div>
</template>

<script>
  import DateEdit from "@/components/elements/items/edits/DateEdit";
  import NumberEdit from "@/components/elements/items/edits/NumberEdit";
  import BlackButton from "@/components/elements/buttons/BlackButton";

  import * as Moment from 'moment'

  export default {
    name: "DateTimeEdit",
    components: {BlackButton, DateEdit, NumberEdit},
    props: ['value'],
    computed: {
      date() {
        return Moment(this.value).format('YYYY-MM-DD')
      },
      hour() {
        return Moment(this.value).hours()
      },
      minute() {
        return Moment(this.value).minute()
      }
    },
    data: () => ({
      minuteType: {
        type: 'int',
        typeConstraint: {
          from: 0,
          to: 60
        }
      },
      hourType: {
        type: 'int',
        typeConstraint: {
          from: 0,
          to: 24
        }
      }
    }),
    methods: {
      fireMoment(m) {
        this.$emit('input', m.utc().format('YYYY-MM-DDTHH:mm:ss.SSSZZ'))
      },
      dateInput(date) {
        const newDate = Moment(date)
        this.fireMoment(Moment(this.value).date(newDate.date()).month(newDate.month()).year(newDate.year()))
      },
      hourInput(h) {
        this.fireMoment(Moment(this.value).hours(h))
      },
      minuteInput(m) {
        this.fireMoment(Moment(this.value).minutes(m))
      },
      now() {
        this.fireMoment(Moment())
      }
    },
    mounted() {
      if (!this.value) {
        this.fireMoment(Moment())
      }
    },
    watch: {
      value(newValue) {
        if (!newValue) {
          this.fireMoment(Moment())
        }
      }
    }
  }
</script>

<style scoped>

  .editContainer {
    display: flex;
  }

  .time {
    display: flex;
    margin: 0 10px;
  }

</style>