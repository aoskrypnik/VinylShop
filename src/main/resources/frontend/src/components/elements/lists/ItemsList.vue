<template>
  <div>
    <table>
      <tr>
        <th
                v-for="prop in listProps"
                :key="prop"
                :title="$store.getters.getAppLocale('sortTooltip')"
                :class="{ desc: sortAttribute === prop && sortDirection === 1, asc: sortAttribute === prop && sortDirection === 0, sortable: isSortable(prop) }"
                @click="isSortable(prop) ? sortChange(prop): ''"
        >{{schemaDictionary[prop]}}</th>
      </tr>
      <tr v-for="(item, index) in items" :key="itemsKeys[index]" class="tableRow" @click="itemClick(index)">
        <td v-for="prop in listProps" :key="prop">
          <item :value="item[prop]" :type="getProps[prop]" :schema="schema"></item>
        </td>
      </tr>
    </table>
    <div v-if="items.length === 0 && !loading" class="row">
      <div class="col-md-4 col-6 img"><img src="@/assets/record.png"></div>
      <div class="col-md-8 col-12 mt-5">
        <p class="errorTitle">{{$store.getters.getAppLocale('listNoItems')}}</p>
        <p>{{$store.getters.getAppLocale('listNoItemsMessage')}}</p>
      </div>
    </div>
  </div>
</template>

<script>

import Item from '../items/Item'

import * as SchemaUtils from '@/schemas/utils'

export default {
  props: ['schema', 'items', 'sortAttribute', 'sortDirection', 'loading'],
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
        return this.items.map(item => this.getKey.map(k => item[k]).join('@'))
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
    },
    isSortable(prop) {
      return SchemaUtils.isSortable(this.getProps[prop])
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
  }

  td, .sortable {
    cursor: pointer;
  }

  .sortable:hover {
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


  .img img {
    width: 100%;
  }

  .errorTitle {
    font-weight: bold;
    font-size: 36px;
  }

</style>