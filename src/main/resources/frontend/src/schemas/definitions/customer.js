export default {
  props: {
    customerNum: {
      type: 'int',
      readonly: true
    },
    customerName: 'string',
    customerEmail: {
      type: 'string',
      typeConstraint: /^[\w._\-\d]+@([\w\d-]+\.)+[\w]{2,7}$/
    },
    customerDiscount: {
      type: 'int',
      readonly: true
    },
    checksNum: {
      type: 'check',
      isSchema: true,
      filterOnly: true,
      isArray: true,
      joins: ['cheq']
    },
    customerPhoneNumbers: {
      type: 'string',
      isArray: 'true',
      joins: ['customer_phone_number'],
      isNullable: true,
      typeConstraint: /^\+?\d+$/
    }
  },
  key: 'customerNum',
  display: (item) => `${item.customerNum} - ${item.customerName}`
}