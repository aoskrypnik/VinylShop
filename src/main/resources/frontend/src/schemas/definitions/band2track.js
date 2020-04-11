export default {
  props: {
    trackCatalogNum: {
      type: 'track',
      isSchema: true,
      noedit: true
    },
    bandAlias: {
      type: 'band',
      isSchema: true,
      noedit: true
    },
    isFeaturing: 'boolean'
  },
  key: ['trackCatalogNum', 'bandAlias'],
  display: (item) => `${item.bandAlias} - ${item.trackCatalogNum}`
}