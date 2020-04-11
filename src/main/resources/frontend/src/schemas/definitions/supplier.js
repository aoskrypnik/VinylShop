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
  nodelete: true,
  display: item => item.edrpou
}