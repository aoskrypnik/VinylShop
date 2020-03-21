export default {
  props: {
    checkNum: 'int',
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
    overallSum: 'int',
    checkDiscount: 'int',
    sumWithDiscount: 'int',
    productBarcodes: {
      type: 'record',
      isSchema: true,
      isArray: true
    }
  },
  key: 'checkNum',
  display: (item) => item.checkNum
}