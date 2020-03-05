<template>
  <black-button-view :large="large" :inline="inline" :disabled="disabled" @click="$emit('click')"><slot></slot></black-button-view>
</template>

<script>
import BlackButtonView from "./BlackButtonView";
export default {
  components: {BlackButtonView},
  props: ['large', 'inline', 'disabled']
}
</script>