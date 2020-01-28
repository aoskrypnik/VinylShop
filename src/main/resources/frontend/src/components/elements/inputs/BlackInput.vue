<template>
  <input :placeholder="placeholder" ref="input" :type="password ? 'password': 'text'" :value="value" @input="updateModel()" :class="{ wrong, large }" />
</template>

<script>
export default {
  name: 'BlackInput',
  props: ['placeholder', 'password', 'value', 'wrong', 'large'],
  methods: {
    updateModel() {
      this.$emit('input', this.$refs.input.value)
    }
  }
}
</script>

<style>
input {
  background: transparent;
  border: 2px white solid;
  color: white;
  padding: 5px 10px;
  font-size: 18px !important;
  display: block;
  margin-bottom: 2px !important;
  border-radius: 0px;
}

input.large {
  padding: 6px 14px;
  font-size: 24px !important;
}

input.wrong {
  border-color: #d87878;
}
</style>