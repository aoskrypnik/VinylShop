export default {
  props: {
    trackCatalogNum: {
      type: 'track',
      isSchema: true
    },
    artistAlias: {
      type: 'artist',
      isSchema: true
    },
    isFeaturing: 'boolean'
  },
  key: ['trackCatalogNum', 'artistAlias'],
  display: (item) => `${item.artistAlias} - ${item.trackCatalogNum}`
}