export default {
  props: {
    recordBarcode: {
      type: 'string',
      readonly: true
    },
    releaseBarcodeFk: {
      type: 'release',
      isSchema: true
    },
    purchaseDate: {
      type: 'date',
      typeConstraint: {
        to: []
      }
    },
    purchasePrice: {
      type: 'money',
      typeConstraint: {
        from: 0
      }
    },
    sellPrice: {
      type: 'money',
      typeConstraint: {
        from: 0
      }
    },
    available: {
      type: 'boolean',
      readonly: true
    },
    recordState: 'M|NM|VG+|VG|G|F',
    stateCheckDate: 'date',
    supplierEdrpou: {
      type: 'supplier',
      isSchema: true
    }
  },
  key: 'recordBarcode',
  nodelete: true,
  display: (item) => `${item.recordBarcode}: ${item.releaseBarcodeFk} - ${item.recordState}`
}