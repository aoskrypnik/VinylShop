<template>
    <div>
      <div v-for="(item, key) in items" :key="key" class="itemContainer">
        <item-edit :value="item" :type="{...type, isArray: false, isActuallyArray: true }" @input="onInput($event, key)" :schema="schema" class="itemInput"></item-edit>
        <black-button :inline="true" :disabled="items.length === 1 && !type.isNullable" @click="removeItem(key)">x</black-button>
      </div>
      <BlackButton  @click="addItem">{{$store.getters.getAppLocale('addArrayItem')}}</BlackButton>
    </div>
</template>

<script>

  import BlackButton from '../../buttons/BlackButton'

  export default {
    name: 'ArrayEdit',
    components: {
      BlackButton,
      ItemEdit: () => import('../ItemEdit.vue')
    },
    props: ['type', 'value', 'schema'],
    data: function() {
      return {
        items: []
      }
    },
    watch: {
      value(newValue) {
        this.items = [...newValue]
      }
    },
    mounted() {
      this.items = this.value === undefined ? [] : [...this.value]
    },
    methods: {
      onInput(newValue, index) {
        if (!Array.isArray(newValue)) {
          this.items[index] = newValue;
          this.items = Array.from(new Set(this.items))
          this.$emit('input', this.items.filter(i => i !== null))
        } else {
          this.items.splice(index, 1, ...newValue);
          this.items = Array.from(new Set(this.items))
          this.$emit('input', this.items.filter(i => i !== null))
        }
      },
      removeItem(index) {
        this.items.splice(index, 1);
        this.$emit('input', this.items.filter(i => i !== null))
      },
      addItem() {
        if (this.items === undefined) {
          this.items = [null]
        } else {
          this.items.push(null)
        }
      }
    }
  }

</script>

<style scoped>

  .itemContainer {
    display: flex;
    margin-bottom: 5px;
  }

  .itemInput {
    margin-right: 5px;
  }

</style>