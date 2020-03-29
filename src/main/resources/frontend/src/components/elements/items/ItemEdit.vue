<template>
  <div>
    <enum-edit v-if="isEnum" :value="value" :type="type" :schema="schema" @input="onInput"></enum-edit>
    <array-edit v-else-if="isArray" :value="value" :type="type" :schema="schema" @input="onInput"></array-edit>
    <nullable-wrapper v-else :input-type="inputType" :value="value" :type="type" @input="onInput"></nullable-wrapper>
  </div>
</template>

<script>
  import EnumEdit from './edits/EnumEdit'

  import * as SchemaUtils from '@/schemas/utils'
  import ArrayEdit from "./edits/ArrayEdit";
  import NullableWrapper from "./edits/NullableWrapper";

  export default {
    name: 'ItemEdit',
    components: {
      NullableWrapper,
      ArrayEdit,
      EnumEdit
    },
    props: ['type', 'value', 'schema'],
    computed: {
      typeString() {
        if (!SchemaUtils.getTypeString(this.type)) {
          // eslint-disable-next-line
          console.log(this.type)
        }
        return SchemaUtils.getTypeString(this.type)
      },
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
      },
      isSchemaType: function() {
        return SchemaUtils.isSchemaType(this.type)
      },
      inputType: function() {
        if (this.isSchemaType) {
          return 'fk-edit'
        }
        if (this.isDate) {
          return 'date-edit'
        }
        if (this.typeString === 'country') {
          return 'country-edit'
        }

        if (this.typeString === 'int') {
          return 'number-edit'
        }

        if (this.typeString === 'boolean') {
          return 'boolean-edit'
        }

        if (this.typeString === 'datetime') {
          return 'date-time-edit'
        }

        return 'string-edit'
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