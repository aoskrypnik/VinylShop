<template>
  <black-input :value="value" @input="onEdit" :wrong="isWrong"></black-input>
</template>

<script>
import BlackInput from '../../inputs/BlackInput'

export default {
  components: {
    BlackInput
  },
  props: ['value', 'type'],
  data: function() {
    return {
      isWrong: false
    }
  },
  computed: {
    isObject() {
      return typeof type === 'object'
    }
  },
  methods: {
    onEdit(newValue) {
      if (this.isObject) {
        if (this.typeConstraint) {
          if (this.typeConstraint.test(newValue)) {
            this.$emit('input', newValue)
            this.isWrong = false
          } else {
            this.isWrong = true
          }
        }
      }
    }
  }
}
</script>

<style>

</style>