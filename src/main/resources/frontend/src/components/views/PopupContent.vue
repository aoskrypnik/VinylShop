<template>
  <div>
    <list-view v-if="type === 'list'" :schema="properties.schema" :multiple="properties.multiple" @itemSelection="itemSelection"></list-view>
    <dialog-view v-if="type === 'dialog'" :title="properties.title" :text="properties.text" :buttons="properties.buttons" @close="$emit('close')"></dialog-view>
  </div>
</template>

<script>
  import ListView from "./ListView";
  import DialogView from "./DialogView";
  export default {
    name: 'PopupContent',
    components: {ListView, DialogView},
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