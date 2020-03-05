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
      if (this.isObject) {
        if (this.type.typeConstraint) {
          if (this.type.typeConstraint.test(newValue)) {
            this.$emit('input', newValue)
            this.isWrong = false
          } else {
            this.isWrong = true
          }
        } else {
          this.$emit('input', newValue)
        }
      } else {
        this.$emit('input', newValue)
      }
    }
  }
}
</script>

<style>

</style>