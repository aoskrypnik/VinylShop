<template>
  <div>
    <PageHeader v-if="title">{{title}}</PageHeader>
    <p v-if="text">{{text}}</p>
    <div class="buttonGroup">
      <BlackButton v-for="button in buttons" :key="button.label" @click="click(button.onClick)">{{button.label}}</BlackButton>
    </div>
  </div>
</template>

<script>
  import PageHeader from "../elements/typography/PageHeader";
  import BlackButton from "../elements/buttons/BlackButton";
  export default {
    name: "DialogView",
    components: {BlackButton, PageHeader},
    props: ['title', 'text', 'buttons'],
    methods: {
      close() {
        this.$emit('close')
      },
      click(f) {
        try {
          f();
        } finally {
          this.close()
        }
      }
    }
  }
</script>

<style scoped>



</style>