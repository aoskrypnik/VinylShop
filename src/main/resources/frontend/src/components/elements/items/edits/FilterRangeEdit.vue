<template>
  <div>
    <div class="rangeContainer">
      <div class="rangeItem">
        <p>{{$store.getters.getAppLocale('filtersFrom')}}:</p>
        <item-edit :value="value.from" :type="type" :schema="schema" @input="onFromInput"></item-edit>
      </div>
      <div class="rangeItem">
        <p>{{$store.getters.getAppLocale('filtersTo')}}:</p>
        <item-edit :value="value.to" :type="type" :schema="schema" @input="onToInput"></item-edit>
      </div>
    </div>
    <p class="error" v-if="isWrong">{{$store.getters.getAppLocale('wrongOrder')}}</p>
  </div>
</template>

<script>
  import ItemEdit from "@/components/elements/items/ItemEdit";

  import * as Moment from 'moment'
  import * as SchemaUtils from '@/schemas/utils'

  export default {
    name: "FilterRangeEdit",
    components: {ItemEdit},
    props: ['value', 'type', 'schema'],
    data: () => ({
      isWrong: false
    }),
    methods: {
      onFromInput(val) {
        this.isWrong = false;

        if (this.verifyOrder(val, this.value.to)) {
          this.$emit('input', {
            from: val,
            to: this.value.to
          })
        } else {
          this.isWrong = true
        }
      },
      onToInput(val) {
        this.isWrong = false;

        if (this.verifyOrder(this.value.from, val)) {
          this.$emit('input', {
            from: this.value.from,
            to: val
          })
        } else {
          this.isWrong = true
        }
      },
      verifyOrder(f, t) {
        if (SchemaUtils.getTypeString(this.type) === 'date') {
          return !Moment(f).isAfter(t)
        }

        return f === null || t === null || Number(f) <= Number(t)
      }
    }
  }
</script>

<style scoped>

  .rangeContainer {
    display: flex;
    flex-wrap: wrap;
    width: 100%;
  }

  p {
    margin: 0;
  }

  .rangeItem {
    margin-right: 20px;
  }

  .error {

  }

</style>