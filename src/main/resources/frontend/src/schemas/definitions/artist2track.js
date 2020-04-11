export default {
  props: {
    trackCatalogNum: {
      type: 'track',
      isSchema: true,
      noedit: true
    },
    artistAlias: {
      type: 'artist',
      isSchema: true,
      noedit: true
    },
    isFeaturing: 'boolean'
  },
  key: ['trackCatalogNum', 'artistAlias'],
  display: (item) => `${item.artistAlias} - ${item.trackCatalogNum}`
}