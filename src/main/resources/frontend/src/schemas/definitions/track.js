export default {
  props: {
    trackCatalogNum: {
      type: 'int',
      noedit: true
    },
    trackName: 'string',
    duration: {
      type: 'int',
      typeConstraint: {
        from: 0
      }
    },
    composerIds: {
      type: 'composer',
      isSchema: true,
      isArray: true,
      joins: ['track2composer']
    },
    albumIds: {
      type: 'album',
      isSchema: true,
      isArray: true,
      joins: ['track2album']
    },
    artistIds: {
      type: 'artist',
      isSchema: true,
      isArray: true,
      readonly: true,
      nofilter: true
    },
    bandIds: {
      type: 'band',
      isSchema: true,
      isArray: true,
      readonly: true,
      nofilter: true
    },
    featuringArtistIds: {
      type: 'artist',
      isSchema: true,
      isArray: true,
      readonly: true,
      nofilter: true
    },
    featuringBandIds: {
      type: 'band',
      isSchema: true,
      isArray: true,
      readonly: true,
      nofilter: true
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
    },
    bandBindings: {
      type: 'band2track',
      isSchema: true,
      isArray: true,
      filterOnly: true
    }
  },
  key: 'trackCatalogNum',
  display: (item) => item.trackName
}