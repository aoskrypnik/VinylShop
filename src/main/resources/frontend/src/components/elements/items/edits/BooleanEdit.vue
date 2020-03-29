<template>
  <black-checkbox :value="value" :disabled="disabled" @input="onInput">_</black-checkbox>
</template>

<script>
  import BlackCheckbox from "@/components/elements/checkboxes/BlackCheckbox";
  export default {
    name: "BooleanEdit",
    components: {BlackCheckbox},
    props: ['value', 'label', 'disabled'],
    methods: {
      onInput(val) {
        this.$emit('input', val)
      }
    }
  }
</script>