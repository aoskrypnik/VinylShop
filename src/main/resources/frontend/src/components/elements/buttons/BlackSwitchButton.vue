<template>
  <button :class="{large, enabled: value}" @click="$emit('input', !value)"><slot></slot></button>
</template>

<script>
export default {
  props: ['large', 'value']
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