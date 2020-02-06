<template>
  <div>
    <string-edit v-if="isString && !isArray" :value="value" :type="type" @input="onInput"></string-edit>
    <enum-edit v-else-if="isEnum" :value="value" :type="type" :schema="schema" @input="onInput"></enum-edit>
    <array-edit v-else-if="isArray" :value="value" :type="type" :schema="schema" @input="onInput"></array-edit>
  </div>
</template>

<script>
import StringEdit from './edits/StringEdit'
import EnumEdit from './edits/EnumEdit'

import * as SchemaUtils from '@/schemas/utils'
import ArrayEdit from "./edits/ArrayEdit";

export default {
  name: 'ItemEdit',
  components: {
    ArrayEdit,
    StringEdit,
    EnumEdit
  },
  props: ['type', 'value', 'schema'],
  computed: {
    isString: function() {
      return SchemaUtils.isString(this.type)
    },
    isEnum: function() {
      return SchemaUtils.isEnum(this.type)
    },
    isArray: function() {
      return SchemaUtils.isArray(this.type)
    }
  },
  methods: {
    onInput(newValue) {
      this.$emit('input', newValue)
    }
  }
}
</script>

<style>

</style>