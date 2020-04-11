export default {
  props: {
    checkNum: {
      type: 'int',
      readonly: true
    },
    dateTime: {
      type: 'datetime',
      readonly: true,
      nofilter: true
    },
    customerNum: {
      type: 'customer',
      isSchema: true,
      isNullable: true
    },
    salesmanTabNum: {
      type: 'salesman',
      isSchema: true,
      readonly: true
    },
    overallSum: {
      type: 'money',
      readonly: true
    },
    checkDiscount: {
      type: 'int',
      readonly: true
    },
    sumWithDiscount: {
      type: 'money',
      readonly: true
    },
    productBarcodes: {
      type: 'record',
      isSchema: true,
      isArray: true
    }
  },
  key: 'checkNum',
  noedit: true,
  nodelete: true,
  display: (item) => item.checkNum
}