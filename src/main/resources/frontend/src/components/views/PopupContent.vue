<template>
  <div>
    <list-view v-if="type === 'list'" :schema="properties.schema" @itemSelection="itemSelection"></list-view>
    <dialog v-if="type === 'dialog'"></dialog>
  </div>
</template>

<script>
  import ListView from "./ListView";
  export default {
    name: 'PopupContent',
    components: {ListView},
    props: ['type', 'properties'],
    methods: {
      itemSelection(key) {
        this.properties.itemSelection(key)
        this.$emit('close')
      }
    }
  }
</script>

<style scoped>

</style>