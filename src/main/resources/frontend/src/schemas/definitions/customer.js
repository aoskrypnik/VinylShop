export default {
  props: {
    customerNum: 'int',
    customerName: 'string',
    customerEmail: 'string',
    customerDiscount: 'int'
  },
  key: 'customerNum',
  display: (item) => `${item.customerNum} - ${item.customerName}`
}