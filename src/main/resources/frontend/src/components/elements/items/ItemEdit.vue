<template>
  <div>
    <enum-edit v-if="isEnum" :value="value" :type="type" :schema="schema" @input="onInput"></enum-edit>
    <array-edit v-else-if="isArray" :value="value" :type="type" :schema="schema" @input="onInput"></array-edit>
    <string-edit v-else-if="isString" :value="value" :type="type" @input="onInput"></string-edit>
    <date-edit v-else-if="isDate" :value="value" :type="type" @input="onInput"></date-edit>
    <fk-edit v-else-if="type.isSchema" :value="value" :type="type" @input="onInput"></fk-edit>
  </div>
</template>

<script>
import StringEdit from './edits/StringEdit'
import EnumEdit from './edits/EnumEdit'

import * as SchemaUtils from '@/schemas/utils'
import ArrayEdit from "./edits/ArrayEdit";
import FkEdit from "./edits/FkEdit";
import DateEdit from "./edits/DateEdit";

export default {
  name: 'ItemEdit',
  components: {
    DateEdit,
    FkEdit,
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
    },
    isDate: function() {
      return SchemaUtils.isDate(this.type)
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