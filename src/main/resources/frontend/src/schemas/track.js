export default {
  props: {
    trackCatalogNum: 'int',
    trackName: 'string',
    duration: 'int',
    composerIds: {
      type: 'composer',
      isSchema: true,
      isArray: true
    },
    /*"albumIds: [
      "101"
    ],*/
    artistIds: {
      type: 'artist',
      isSchema: true,
      isArray: true,
      readonly: true
    },
    bandIds: {
      type: 'band',
      isSchema: true,
      isArray: true,
      readonly: true
    },
    featuringArtistIds: {
      type: 'artist',
      isSchema: true,
      isArray: true,
      readonly: true
    },
    featuringBandIds: {
      type: 'band',
      isSchema: true,
      isArray: true,
      readonly: true
    },
    languages: {
      type: 'country',
      isArray: true,
      joins: ['track_language']
    },
    artistBindings: {
      type: 'artist2track',
      isSchema: true,
      isArray: true,
      filterOnly: true
    }
  },
  key: 'trackCatalogNum',
  display: (item) => item.trackName
}