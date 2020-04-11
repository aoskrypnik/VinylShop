<template>
  <div>
    <black-switch-button v-for="(text, index) in buttons" :key="index" :value="values[index]" :inline="true" @input="onInput($event, index)">{{ text }}</black-switch-button>
  </div>
</template>

<script>
import BlackSwitchButton from '../../buttons/BlackSwitchButton'

import * as SchemaUtils from '@/schemas/utils'

export default {
  name: 'EnumEdit',
  components: {
    BlackSwitchButton
  },
  props: ['type', 'value', 'schema'],
  data: function() {
    return {
    }
  },
  computed: {
    keys() {
      return this.typeString.split('|')
    },
    buttons() {
      return this.keys.reduce((acc, k) => 
        ({ ...acc, [k]: this.$store.getters.getPropertyLocale(this.schema, k) }),
        {}
      )
    },
    typeString() {
      return SchemaUtils.getTypeString(this.type)
    },
    values() {
      if (!this.value) {
        return {}
      }
      if (this.type.isArray) {
        return this.value.reduce((acc, val) => ({...acc, [val]: true}), {})
      } else {
        return { [this.value]: true }
      }
    }
  },
  methods: {
    onInput(value, e1) {
      const copy = { ...this.values, [e1]: value };
      if (this.type.isArray) {
        this.$emit('input', Object.keys(copy).filter(e => copy[e]))
      } else {
        if (!value && this.type.isNullable) {
          this.$emit('input', null)
        } else {
          this.$emit('input', e1)
        }
      }
    }
  }
}
</script>

<style>

</style>