export default {
  props: {
    recordBarcode: 'string',
    releaseBarcodeFk: {
      type: 'release',
      isSchema: true
    },
    purchaseDate: {
      type: 'date'
    },
    purchasePrice: 'int',
    sellPrice: 'int',
    available: true,
    recordState: 'M|NM|VG+|VG|G|F',
    stateCheckDate: 'date'
  },
  key: 'recordBarcode',
  display: (item) => `${item.releaseBarcodeFk} - ${item.recordState}`
}