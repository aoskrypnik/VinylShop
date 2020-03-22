export default {
  props: {
    checkNum: {
      type: 'int',
      readonly: true
    },
    dateTime: 'datetime',
    customerNum: {
      type: 'customer',
      isSchema: true,
      isNullable: true
    },
    salesmanTabNum: {
      type: 'salesman',
      isSchema: true
    },
    overallSum: {
      type: 'int',
      readonly: true
    },
    checkDiscount: {
      type: 'int',
      readonly: true
    },
    sumWithDiscount: {
      type: 'int',
      readonly: true
    },
    productBarcodes: {
      type: 'record',
      isSchema: true,
      isArray: true
    }
  },
  key: 'checkNum',
  display: (item) => item.checkNum
}