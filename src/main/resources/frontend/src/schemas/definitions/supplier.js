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
    },
    records: {
      type: 'record',
      isSchema: true,
      filterOnly: true
    }
  },
  key: 'edrpou',
  nodelete: true,
  display: item => item.edrpou
}