<template>
  <black-button-view :large="large" :inline="inline" :class="{ enabled: value }" @click="$emit('input', !value)"><slot></slot></black-button-view>
</template>

<script>
import BlackButtonView from "./BlackButtonView";
export default {
  components: {BlackButtonView},
  props: ['large', 'value', 'inline']
}
</script>

<style scoped>

button {
  background: transparent;
  border: 2px white solid;
  color: white;
  padding: 5px 10px;
  font-size: 18px;
  transition: 200ms;
  display: block;
  height: 100%;
}

button.inline {
  display: inline-block;
  margin-right: 5px;
}

button.large {
  padding: 6px 14px;
  font-size: 24px;
}

button.enabled {
  background: white;
  color: black;
}

button:hover {
  background: rgb(200, 200, 200);
  color: black;
}

button.enabled:hover {
  background: rgb(230, 230, 230);
  color: black;
}

button:focus {
  border-color: aliceblue;
}

</style>