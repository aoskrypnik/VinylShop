<template>
  <black-input
          :value="value"
          :wrong="isWrong"
          :number="true"
          :disabled="disabled"
          :narrow="narrow"

          @input="onEdit"
  ></black-input>
</template>

<script>
  import BlackInput from '../../inputs/BlackInput'

  export default {
    name: 'NumberEdit',
    components: {
      BlackInput
    },
    props: ['value', 'type', 'disabled', 'narrow'],
    data: function() {
      return {
        isWrong: false
      }
    },
    computed: {
      isObject() {
        return typeof this.type === 'object'
      }
    },
    methods: {
      onEdit(newValue) {
        const valid = this.validate(newValue)
        this.isWrong = !valid
        if (valid) {
          this.$emit('input', newValue)
        }
      },
      validate(newValue) {
        if (this.isObject) {
          if (this.type.typeConstraint) {
            if (this.type.typeConstraint.from && newValue < this.type.typeConstraint.from) {
              return false
            }

            if (this.type.typeConstraint.to && newValue > this.type.typeConstraint.to) {
              return false
            }
          }
        }

        return true
      }
    }
  }
</script>

<style>

</style>