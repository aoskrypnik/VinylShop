<template>
  <div>
    <!--String types-->
    <span v-if="isStringType && !type.isSchema">{{value}}</span>
    <span v-else-if="type.isSchema">{{ fkActualValue }}</span>
    <span v-else-if="isEnum">{{Array.isArray(value) ? value.map(v => labels[v]).join(',') : labels[value]}}</span>
    <span v-else-if="typeString === 'country'">{{ $store.state.countries.get(value) }}</span>
    <span v-else>{{value}}</span>
  </div>
</template>

<script>

import * as SchemaUtils from '@/schemas/utils'

export default {
  props: ['value', 'type', 'schema'],
  data: function() {
    return {
      fkActualValue: ''
    }
  },
  computed: {
    isEnum() {
      return SchemaUtils.isEnum(this.type)
    },
    isStringType() {
      return SchemaUtils.isStringType(this.type, this.$store.getters.getSchema)
    },
    typeString() {
      return SchemaUtils.getTypeString(this.type)
    },
    keys() {
      return this.typeString.split('|')
    },
    labels() {
      return this.keys.reduce((acc, k) =>
              ({ ...acc, [k]: this.label(k) }),
          {}
      )
    }
  },
  methods: {
    label(key) {
      return this.$store.getters.getPropertyLocale(this.schema, key)
    },
    updateFkValue() {
      if (this.type.isSchema) {
        SchemaUtils.fkValue(this.type.type, this.value).then((value) => this.fkActualValue = value)
      } else {
        this.fkActualValue = ''
      }
    }
  },
  mounted() {
    this.updateFkValue()
  },
  watch: {
    value() {
      this.updateFkValue()
    },
    type() {
      this.updateFkValue()
    }
  }
}
</script>

<style>

</style>