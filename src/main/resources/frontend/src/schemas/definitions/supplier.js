export default {
  props: {
    edrpou: {
      type: 'string',
      noedit: true
    },
    supplierAddress: {
      type: 'string'
    },
    phoneNumber: {
      type: 'string',
      typeConstraint: /^\+?\d+$/
    }
  },
  key: 'edrpou',
  display: item => item.edrpou
}