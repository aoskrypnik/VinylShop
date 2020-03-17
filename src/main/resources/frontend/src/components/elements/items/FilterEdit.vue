<template>
  <div>
    <filter-range-edit v-if="isRangeType" :type="editType" :value="value" @input="onInput"></filter-range-edit>
    <item-edit v-else :type="editType" :value="value" @input="onInput"></item-edit>
  </div>
</template>

<script>
  import * as SchemaUtils from '@/schemas/utils'

  import FilterRangeEdit from "@/components/elements/items/edits/FilterRangeEdit";
  import ItemEdit from "@/components/elements/items/ItemEdit";

  export default {
    name: 'FilterEdit',
    components: {ItemEdit, FilterRangeEdit},
    props: ['value', 'type'],
    computed: {
      isRangeType() {
        return SchemaUtils.isRangeType(this.type)
      },
      editType() {
        if (SchemaUtils.isSchemaType(this.type)) {
          return this.type
        }

        return {
          type: SchemaUtils.getTypeString(this.type),
          isNullable: true
        }
      }
    },
    methods: {
      onInput(val) {
        this.$emit('input', val)
      }
    }
  }
</script>

<style scoped>

</style>