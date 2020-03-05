<template>
  <black-input :value="value" :date="true" :disabled="disabled" @input="onEdit"></black-input>
</template>

<script>
  import BlackInput from "../../inputs/BlackInput";

  export default {
    name: "DateEdit",
    components: {
      BlackInput
    },
    props: ['value', 'type', 'disabled'],
    methods: {
      onEdit(newValue) {
        // TODO constraints
        this.$emit('input', newValue)
      }
    }
  }
</script>

<style scoped>

</style>