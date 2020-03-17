<template>
  <table>
    <tr>
      <th
              v-for="prop in listProps"
              :key="prop"
              :title="$store.getters.getAppLocale('sortTooltip')"
              :class="{ desc: sortAttribute === prop && sortDirection === 1, asc: sortAttribute === prop && sortDirection === 0 }"
              @click="sortChange(prop)"
      >{{schemaDictionary[prop]}}</th>
    </tr>
    <tr v-for="(item, index) in items" :key="itemsKeys[index]" class="tableRow" @click="itemClick(index)">
      <td v-for="prop in listProps" :key="prop">
        <item :value="item[prop]" :type="getProps[prop]" :schema="schema"></item>
      </td>
    </tr>
  </table>
</template>

<script>

import Item from '../items/Item'

export default {
  props: ['schema', 'items', 'sortAttribute', 'sortDirection'],
  components: {
    Item
  },
  computed: {
    getProps() {
      return this.$store.getters.getSchemaProps(this.schema)
    },
    getKey() {
      return this.$store.getters.getSchemaKey(this.schema)
    },
    itemsKeys() {
      if (typeof this.getKey === 'object') {
        return this.items.map(item => this.getKey.map(k => item[k]).join(','))
      }
      return this.items.map(item => item[this.getKey])
    },
    schemaDictionary() {
      return this.$store.getters.getSchemaDictionary(this.schema)
    },
    listProps() {
      return this.$store.getters.getSchemaListProps(this.schema)
    }
  },
  methods: {
    itemClick(index) {
      this.$emit('itemSelection', this.itemsKeys[index])
    },
    sortChange(attribute) {
      this.$emit('sortChange', attribute)
    }
  }
}
</script>

<style scoped>

  table {
    min-width: 100%;
  }

  th {
    user-select: none;
  }

  th::after {
    display: inline-block;
    width: 20px;
    content: " ";
    margin-left: 10px;
  }

  td, th {
    padding: 5px 10px;
    cursor: pointer;
  }

  th:hover {
    background: #595959;
  }

  th {
    background: #484848;
    font-weight: bold;
  }

  td {
    vertical-align: top;
  }

  .tableRow:nth-child(2n+1) {
    background: #212121;
  }

  .tableRow:hover {
    background: #333333;
  }

  .desc::after {
    content: "▼";
  }

  .asc::after {
    content: "▲";
  }

</style>