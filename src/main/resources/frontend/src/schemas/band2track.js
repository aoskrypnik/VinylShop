export default {
  props: {
    trackCatalogNum: {
      type: 'track',
      isSchema: true
    },
    bandAlias: {
      type: 'band',
      isSchema: true
    },
    isFeaturing: 'boolean'
  },
  key: ['trackCatalogNum', 'bandAlias'],
  display: (item) => `${item.bandAlias} - ${item.trackCatalogNum}`
}