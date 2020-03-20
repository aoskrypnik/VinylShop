export default {
  props: {
    artistAlias: 'string',
    isArtistActive: 'boolean',
    countryCode: 'country',
    artistName: 'string',
    artistBirthDate: 'date',
    artistDeathDate: {
      type: 'date',
      isNullable: true
    },
    currentBandAliases: {
      type: 'band',
      isSchema: true,
      isArray: true,
      readonly: true
    },
    previousBandAliases: {
      type: 'band',
      isSchema: true,
      isArray: true,
      readonly: true
    },
    trackCatalogNums: {
      type: 'track',
      isSchema: true,
      isArray: true,
      readonly: true
    },
    featuringTrackCatalogNums: {
      type: 'track',
      isSchema: true,
      isArray: true,
      readonly: true
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