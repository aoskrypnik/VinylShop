<template>
  <table>
    <tr>
      <th v-for="header in getHeaders" :key="header" :title="$store.getters.getAppLocale('sortTooltip')">{{header}}</th>
    </tr>
    <tr v-for="item in items" :key="item[getKey]" class="tableRow">
      <td v-for="prop in getPropsNames" :key="prop">
        <item :value="item[prop]" :type="getProps[prop]"></item>
      </td>
    </tr>
  </table>
</template>

<script>

import Item from '../items/Item'

export default {
  props: ['schema', 'items'],
  components: {
    Item
  },
  computed: {
    getHeaders() {
      return this.$store.getters.getSchemaHeaders(this.schema)
    },
    getPropsNames() {
      return this.$store.getters.getSchemaPropsNames(this.schema)
    },
    getProps() {
      return this.$store.getters.getSchemaProps(this.schema)
    },
    getKey() {
      return this.$store.getters.getSchemaKey(this.schema)
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
</style>