<template>
  <black-input :value="value" :date="true" @input="onEdit" :wrong="isWrong"></black-input>
</template>

<script>
  import BlackInput from "../../inputs/BlackInput";

  export default {
    name: "DateEdit",
    components: {
      BlackInput
    },
    props: ['value', 'type'],
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