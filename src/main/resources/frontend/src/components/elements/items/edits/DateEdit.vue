<template>
  <black-input :wrong="isWrong" :value="value" :date="true" :disabled="disabled" @input="onEdit"></black-input>
</template>

<script>
  import BlackInput from "../../inputs/BlackInput";

  import * as Moment from 'moment'

  export default {
    name: "DateEdit",
    components: {
      BlackInput
    },
    props: ['value', 'type', 'disabled'],
    data: () => ({
      isWrong: false
    }),
    computed: {
      isObject() {
        return typeof this.type === 'object'
      }
    },
    methods: {
      onEdit(newValue) {
        const valid = this.validate(newValue)

        this.isWrong = !valid;
        if (valid) {
          this.$emit('input', newValue)
        }
      },
      validate(newValue) {
        if (this.isObject) {
          if (this.type.typeConstraint) {
            if (this.type.typeConstraint.from && Moment(newValue).isBefore(Moment(this.type.typeConstraint.from))) {
              return false
            }

            if (this.type.typeConstraint.to && Moment(newValue).isAfter(Moment(this.type.typeConstraint.from))) {
              return false
            }
          }
        }

        return true
      }
    }
  }
</script>

<style scoped>

</style>