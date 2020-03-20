<template>
  <black-button :disabled="disabled" @click="selectPopup">{{ value === null ? $store.getters.getAppLocale('fkSelect') : fkActualValue }}</black-button>
</template>

<script>

  import * as SchemaUtils from '@/schemas/utils'

  import BlackButton from "../../buttons/BlackButton";

  export default {
    name: "FkEdit",
    components: {BlackButton},
    props: ['type', 'value', 'disabled'],
    data: function () {
      return {
        fkActualValue: ''
      }
    },
    mounted() {
      this.updateFkValue()
    },
    methods: {
      updateFkValue() {
        if (this.type.isSchema) {
          SchemaUtils.fkValue(this.type.type, this.value).then((value) => this.fkActualValue = value)
        } else {
          this.fkActualValue = ''
        }
      },
      selectPopup() {
        this.$store.commit(
            'popup',
            {
              type: 'list',
              properties: {
                schema: this.type.type,
                itemSelection: (item) => { this.$emit('input', item) },
                multiple: this.type.isActuallyArray
              }
            })
      }
    },
    watch: {
      type() {
        this.updateFkValue()
      },
      value() {
        this.updateFkValue()
      }
    }
  }
</script>

<style scoped>

</style>