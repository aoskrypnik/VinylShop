<template>
  <div class="wrapperContainer">
    <div class="editContainer">
      <component :is="inputType" @input="onInnerInput" :value="innerValue" :type="type" :disabled="isNull"></component>
    </div>
    <div class="checkboxContainer" v-if="!type.isArray && type.isNullable && !type.isActuallyArray">
      <black-checkbox :value="isNull" v-model="isNull" @input="onInput">NULL</black-checkbox>
    </div>
  </div>
</template>

<script>
  import * as SchemaUtils from '@/schemas/utils'

  import StringEdit from './StringEdit'
  import FkEdit from "./FkEdit";
  import DateEdit from "./DateEdit";
  import CountryEdit from "./CountryEdit";
  import BlackCheckbox from "../../checkboxes/BlackCheckbox";
  import BooleanEdit from "@/components/elements/items/edits/BooleanEdit";
  import DateTimeEdit from "@/components/elements/items/edits/DateTimeEdit";
  import NumberEdit from "@/components/elements/items/edits/NumberEdit";

  import {getTypeString, isArray} from "@/schemas/utils";

  export default {
    name: "NullableWrapper",
    components: {
      BlackCheckbox,
      CountryEdit,
      DateEdit,
      FkEdit,
      StringEdit,
      BooleanEdit,
      DateTimeEdit,
      NumberEdit
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
        // Custom logic for default values
        if (getTypeString(this.type) === 'boolean' && !isArray(this.type)) {
          if (!val && this.innerValue === null) {
            this.innerValue = false
          }
        }

        this.$emit('input', val ? null : this.innerValue)
      }
    },
    mounted() {
      this.innerValue = this.value
      if (this.innerValue === null && !SchemaUtils.isSchemaType(this.type)) {
        this.isNull = true
      }
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