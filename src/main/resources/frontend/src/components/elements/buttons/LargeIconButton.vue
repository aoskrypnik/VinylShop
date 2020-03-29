<template>
  <button class="square" @click="$emit('click')">
    <div class="icon">
      <img :src="require(`@/assets/icons/${icon}.png`)">
    </div>
    <p class="label"><slot></slot></p>
  </button>
</template>

<script>
  export default {
    name: "LargeIconButton",
    props: ['icon']
  }
</script>

<style scoped>

  button {
    display: flex;
    background: transparent;
    border: 2px white solid;
    color: white;
    font-size: 18px;
    transition: background-color 200ms;
    border-radius: 10px;
    align-items: center;
    padding: 0;
  }

  button:hover {
    background: white;
    color: black;
  }

  .label {
    font-size: 24px;
    margin: 0 15px 0 10px;
  }

  button:hover img {
    filter: invert(100%);
  }

  .icon {
    width: 50px;
    height: 50px;
    margin: 10px;
  }

  .icon img {
    display: block;
    width: 100%;
    height: 100%;
  }

</style>