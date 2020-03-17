export default {
  props: {
    trackCatalogNum: {
      type: 'track',
      isSchema: true
    },
    performerAlias: {
      type: 'artist',
      isSchema: true
    },
    isFeaturing: 'boolean'
  },
  key: ['track_catalog_num', 'artist_alias'],
  display: (item) => `${item.artist_alias} - ${item.track_catalog_num}`
}