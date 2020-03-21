export default {
  props: {
    releaseBarcode: 'string',
    albumCatalogNum: {
      type: 'album',
      isSchema: true
    },
    releaseCountryCode: 'country',
    releaseDate: 'date',
    recordSize: '7|10|12',
    recordSpeed: '33|45|78',
    copiesCount: 'int',
    isRepress: 'boolean',
    label: {
      type: 'string',
      isNullable: true
    }
  },
  key: 'releaseBarcode',
  display: (item) => `${item.albumCatalogNum} ${item.releaseDate} ${item.label}`
}