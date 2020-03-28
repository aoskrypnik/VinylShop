<template>
  <button class="square" @click="$emit('click')">
    <div class="icon">
      <img :src="require(`@/assets/icons/${icon}.png`)">
      <div v-if="modifier" class="modifier">{{modifier}}</div>
    </div>
    <p class="label"><slot></slot></p>
  </button>
</template>

<script>
  export default {
    name: "SquareButton",
    props: ['icon', 'modifier']
  }
</script>

<style scoped>

  button {
    display: block;
    background: transparent;
    border: 2px white solid;
    color: white;
    width: 150px;
    height: 150px;
    font-size: 18px;
    transition: background-color 200ms;
    border-radius: 10px;
  }

  button:hover {
    background: white;
    color: black;
  }

  .label {
    font-size: 24px;
    margin-top: 10px;
    max-width: 130px;
    overflow: hidden;
    text-overflow: ellipsis;
  }

  button:hover img {
    filter: invert(100%);
  }

  .icon {
    position: relative;
    width: 70px;
    height: 70px;
    margin: 20px auto 0 auto;
  }

  .icon img {
    display: block;
    width: 100%;
    height: 100%;
  }

  .modifier {
    position: absolute;
    right: -5px;
    bottom: -5px;
    font-size: 24px;
    line-height: 24px;
  }

</style>