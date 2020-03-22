<template>
  <black-input :value="value" :wrong="isWrong" :disabled="disabled" @input="onEdit"></black-input>
</template>

<script>
import BlackInput from '../../inputs/BlackInput'

export default {
  name: 'StringEdit',
  components: {
    BlackInput
  },
  props: ['value', 'type', 'disabled'],
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
          if (!this.type.typeConstraint.test(newValue)) {
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