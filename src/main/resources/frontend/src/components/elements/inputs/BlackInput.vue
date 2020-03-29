<template>
  <input :placeholder="placeholder" ref="input" :type="inputType" :value="value" @input="updateModel" :class="{ wrong, large, inline, narrow }" :disabled="disabled" />
</template>

<script>
export default {
  name: 'BlackInput',
  props: ['placeholder', 'password', 'value', 'wrong', 'large', 'inline', 'number', 'date', 'disabled', 'narrow'],
  computed: {
    inputType() {
      if (this.password) {
        return 'password'
      }
      if (this.number) {
        return 'number'
      }
      if (this.date) {
        return 'date'
      }
      return 'text'
    }
  },
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
    border-radius: 5px;
  }

  input.inline {
    display: inline-block;
  }

  input.large {
    padding: 6px 14px;
    font-size: 24px !important;
  }

  input.wrong {
    border-color: #d87878;
  }

  input[disabled] {
    border-color: lightgrey;
    color: lightgrey;
  }

  input.narrow {
    width: 70px;
  }


</style>