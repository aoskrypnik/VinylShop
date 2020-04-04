<template>
  <div :class="{ large }">
    <!--String types-->
    <span v-if="isStringType && !type.isSchema">{{value}}</span>
    <router-link v-else-if="type.isSchema" :to="{ name: 'entry', params: { item: value, schema: type.type }}" class="text-white">{{ fkActualValue }}</router-link>
    <span v-else-if="isEnum">{{Array.isArray(value) ? value.map(v => labels[v]).join(', ') : labels[value]}}</span>
    <span v-else-if="typeString === 'country'">{{ $store.state.countries.get(value) }}</span>
    <span v-else-if="typeString === 'datetime'">{{ dateTime }}</span>
    <span v-else-if="typeString === 'money'">{{ money }}</span>
    <span v-else>{{value}}</span>
  </div>
</template>

<script>

  import * as Moment from 'moment'

import * as SchemaUtils from '@/schemas/utils'

export default {
  props: ['value', 'type', 'schema', 'large'],
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
    },
    dateTime() {
      try {
        return Moment(this.value).format("dddd, MMMM Do YYYY, h:mm:ss a")
      } catch (_) {
        return ''
      }
    },
    money() {
      try {
        return (Number(this.value) / 100).toFixed(2)
      } catch (_) {
        return ''
      }
    }
  },
  methods: {
    label(key) {
      try {
        return this.$store.getters.getPropertyLocale(this.schema, key)
      } catch (e) {
        return key
      }
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

  .large {
    font-size: 24px;
  }

  a.text-white {
    text-decoration: underline;
  }

</style>