<template>
  <div>
    <black-switch-button v-for="(text, index) in buttons" :key="index" v-model="values[index]" @input="onInput($event, index)">{{ text }}</black-switch-button>
  </div>
</template>

<script>
import BlackSwitchButton from '../../buttons/BlackSwitchButton'

export default {
  components: {
    BlackSwitchButton
  },
  props: ['type', 'value', 'schema'],
  data: function() {
    return {
      values: {}
    }
  },
  computed: {
    keys() {
      return this.type.split('|')
    },
    buttons() {
      return this.keys.reduce((acc, k) => 
        ({ ...acc, [k]: this.$store.getters.getPropertyLocale(this.schema, k) }),
        {}
      )
    }
  },
  created() {
    if (this.type.isArray) {
      this.values = this.value.reduce((acc, _, key) => ({ ...acc, [key]: true }), {})
    }
  },
  methods: {
    onInput(_, e1) {
      if (this.type.isArray) {
        //
      } else {
        this.values = { [e1]: true }
      }
    }
  }
}
</script>

<style>

</style>