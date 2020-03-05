<template>
  <div class="wrapperContainer">
    <div class="editContainer">
      <component :is="inputType" @input="onInnerInput" :value="innerValue" :type="type" :disabled="isNull"></component>
    </div>
    <div class="checkboxContainer" v-if="!type.isArray && type.isNullable">
      <black-checkbox :value="isNull" v-model="isNull" @input="onInput">NULL</black-checkbox>
    </div>
  </div>
</template>

<script>
  import StringEdit from './StringEdit'
  import FkEdit from "./FkEdit";
  import DateEdit from "./DateEdit";
  import CountryEdit from "./CountryEdit";
  import BlackCheckbox from "../../checkboxes/BlackCheckbox";

  export default {
    name: "NullableWrapper",
    components: {
      BlackCheckbox,
      CountryEdit,
      DateEdit,
      FkEdit,
      StringEdit
    },
    props: ['value', 'inputType', 'type'],
    data: function() {
      return {
        innerValue: null,
        isNull: false
      }
    },
    methods: {
      onInnerInput(val) {
        this.innerValue = val
        if (!this.isNull) {
          this.$emit('input', val)
        }
      },
      onInput(val) {
        this.$emit('input', val ? null : this.innerValue)
      }
    },
    mounted() {
      this.innerValue = this.value
    },
    watch: {
      value(newValue) {
        if (newValue !== null) {
          this.innerValue = newValue
        }
      }
    },

  }
</script>

<style scoped>

  .wrapperContainer {
    display: flex;
  }

  .editContainer {
  }

  .checkboxContainer {

    margin-left: 15px;
    display: flex;
    flex-direction: column;
    justify-content: center;
  }

</style>