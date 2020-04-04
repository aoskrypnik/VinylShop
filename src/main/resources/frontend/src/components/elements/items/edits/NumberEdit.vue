<template>
  <black-input
          :value="actualValue"
          :wrong="isWrong"
          :number="true"
          :disabled="disabled"
          :narrow="narrow"

          :step="typeString === 'money' ? 0.01 : 1"

          @input="onEdit"
  ></black-input>
</template>

<script>
  import BlackInput from '../../inputs/BlackInput'

  import * as SchemaUtils from '@/schemas/utils'

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
      },
      typeString() {
        return SchemaUtils.getTypeString(this.type)
      },
      actualValue() {
        return this.typeString === 'money' ? this.value / 100 : this.value;
      }
    },
    methods: {
      onEdit(newValue) {
        const valid = this.validate(newValue)

        this.isWrong = !valid;
        if (valid) {
          this.$emit('input', this.typeString === 'money' ? newValue * 100 : newValue)
        }
      },
      validate(newValue) {
        if (this.isObject) {
          if (this.type.typeConstraint) {
            if (this.type.typeConstraint.from !== undefined && Number(newValue) < this.type.typeConstraint.from) {
              return false
            }

            if (this.type.typeConstraint.to !== undefined && Number(newValue) > this.type.typeConstraint.to) {
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