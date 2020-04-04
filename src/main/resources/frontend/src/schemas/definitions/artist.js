export default {
  props: {
    artistAlias: 'string',
    isArtistActive: 'boolean',
    artistCountryCode: 'country',
    artistName: 'string',
    artistBirthDate: {
      type: 'date',
      typeConstraint: {
        to: []
      }
    },
    artistDeathDate: {
      type: 'date',
      typeConstraint: {
        to: []
      },
      isNullable: true
    },
    currentBandAliases: {
      type: 'band',
      isSchema: true,
      isArray: true,
      readonly: true,
      nofilter: true
    },
    previousBandAliases: {
      type: 'band',
      isSchema: true,
      isArray: true,
      readonly: true,
      nofilter: true
    },
    trackCatalogNums: {
      type: 'track',
      isSchema: true,
      isArray: true,
      readonly: true,
      nofilter: true
    },
    featuringTrackCatalogNums: {
      type: 'track',
      isSchema: true,
      isArray: true,
      readonly: true,
      nofilter: true
    },
    artistBindings: {
      type: 'artist2track',
      isSchema: true,
      isArray: true,
      filterOnly: true
    },
    participations: {
      type: 'participation',
      isSchema: true,
      isArray: true,
      filterOnly: true,
      joins: ['artist2band']
    }
  },
  key: 'artistAlias',
  display: (item) => item.artistAlias
}