<template>
  <button :class="{large, inline}" :disabled="disabled" @click="$emit('click')"><slot></slot></button>
</template>

<script>
  export default {
    name: 'BlackButtonView',
    props: ['large', 'inline', 'disabled']
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
    min-height: 41px;
    border-radius: 5px;
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

  button[disabled] {
    border-color: lightgrey;
    color: lightgrey;
  }

  button[disabled]:hover {
    background: black;
    color: lightgrey;
  }

</style>