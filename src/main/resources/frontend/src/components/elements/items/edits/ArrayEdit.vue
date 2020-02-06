<template>
    <div>
      <div v-for="(item, key) in value" :key="key" class="itemContainer">
        <item-edit :value="item" :type="{...type, isArray: false}" @input="onInput($event, key)" :schema="schema" class="itemInput"></item-edit>
        <black-button :inline="true" @click="removeItem(key)">x</black-button>
      </div>
      <BlackButton @click="addItem">{{$store.getters.getAppLocale('addArrayItem')}}</BlackButton>
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

      }
    },
    methods: {
      onInput(newValue, index) {
        const copy = [...(this.value)];
        copy[index] = newValue;
        this.$emit('input', copy)
      },
      removeItem(index) {
        const copy = [...(this.value)];
        copy.splice(index, 1);
        this.$emit('input', copy)
      },
      addItem() {
        const copy = [...(this.value)];
        copy.push(null)
        this.$emit('input', copy)
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