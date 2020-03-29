export default {
  props: {
    trackCatalogNum: 'int',
    trackName: 'string',
    duration: 'int',
    composerIds: {
      type: 'composer',
      isSchema: true,
      isArray: true,
      joins: ['track2composer']
    },
    albumIds: {
      type: 'album',
      isSchema: true,
      isArray: true
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
    }
  },
  key: 'trackCatalogNum',
  display: (item) => item.trackName
}