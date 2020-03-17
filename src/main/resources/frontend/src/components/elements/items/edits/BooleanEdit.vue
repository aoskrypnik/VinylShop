<template>
  <black-checkbox :value="value" @input="onInput">{{label}}</black-checkbox>
</template>

<script>
  import BlackCheckbox from "@/components/elements/checkboxes/BlackCheckbox";
  export default {
    name: "BooleanEdit",
    components: {BlackCheckbox},
    props: ['value', 'label'],
    methods: {
      onInput(val) {
        this.$emit('input', val)
      }
    }
  }
</script>