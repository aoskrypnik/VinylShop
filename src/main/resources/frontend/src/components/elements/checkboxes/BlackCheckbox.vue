<template>
  <label :class="['boxContainer', { ghost }]">
    <slot></slot>
    <input type="checkbox" :checked="value" :disabled="disabled" @change="onInput" id="blc">
    <span class="checkmark"></span>
  </label>
</template>

<script>
  export default {
    name: "BlackCheckbox",
    props: ['value', 'disabled', 'ghost'],
    methods: {
      onInput(e) {
        this.$emit('input', e.target.checked)
      }
    }
  }
</script>

<style scoped>
  label {
    margin-bottom: 0;
    white-space: nowrap;
    text-overflow: ellipsis;
    overflow: hidden;
  }

  .boxContainer {
    display: block;
    position: relative;
    padding-left: 35px;
    cursor: pointer;
    font-size: 20px;
    -webkit-user-select: none;
    -moz-user-select: none;
    -ms-user-select: none;
    user-select: none;
  }

  .boxContainer.ghost {
    color: black;
  }

  /* Hide the browser's default checkbox */
  .boxContainer input {
    position: absolute;
    opacity: 0;
    cursor: pointer;
    height: 0;
    width: 0;
  }

  /* Create a custom checkbox */
  .checkmark {
    position: absolute;
    top: 3px;
    left: 0;
    height: 25px;
    width: 25px;
    background: black;
    border: solid 2px white;
    border-radius: 3px;
  }

  /* Create the checkmark/indicator (hidden when not checked) */
  .checkmark:after {
    content: "";
    position: absolute;
    display: none;
  }

  /* Show the checkmark when checked */
  .boxContainer input:checked ~ .checkmark:after {
    display: block;
  }

  /* Style the checkmark/indicator */
  .boxContainer .checkmark:after {
    left: 9px;
    top: 5px;
    width: 5px;
    height: 10px;
    border: solid white;
    border-width: 0 3px 3px 0;
    -webkit-transform: rotate(45deg);
    -ms-transform: rotate(45deg);
    transform: rotate(45deg);
  }
</style>