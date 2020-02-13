<template>
  <div>
    <PageHeader v-if="title">{{title}}}</PageHeader>
    <p v-if="text">{{text}}</p>
    <BlackButton v-for="button in buttons" :key="button.label" @click="button.onClick(close)">{{button.label}}</BlackButton>
  </div>
</template>

<script>
  import PageHeader from "../elements/typography/PageHeader";
  import BlackButton from "../elements/buttons/BlackButton";
  export default {
    name: "Dialog",
    components: {BlackButton, PageHeader},
    props: ['title', 'text', 'buttons'],
    methods: {
      close() {
        this.$emit('close')
      }
    }
  }
</script>

<style scoped>

</style>