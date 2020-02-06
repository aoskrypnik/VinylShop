<template>
  <button :class="{large, inline}" @click="$emit('click')"><slot></slot></button>
</template>

<script>
export default {
  props: ['large', 'inline']
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

button.large {
  padding: 6px 14px;
  font-size: 24px;
}

button.inline {
  display: inline-block;
  margin-right: 5px;
}

button:hover {
  background: white;
  color: black;
}

button:focus {
  border-color: aliceblue;
}

</style>