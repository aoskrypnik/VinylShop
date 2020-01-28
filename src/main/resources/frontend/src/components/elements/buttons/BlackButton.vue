<template>
  <button :class="{large}" @click="$emit('click')"><slot></slot></button>
</template>

<script>
export default {
  props: ['large']
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
}

button.large {
  padding: 6px 14px;
  font-size: 24px;
}

button:hover {
  background: white;
  color: black;
}

button:focus {
  border-color: aliceblue;
}

</style>