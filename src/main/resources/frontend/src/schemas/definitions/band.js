export default {
  props: {
    bandAlias: 'string',
    isBandActive: 'boolean',
    bandCountryCode: 'country',
    startYear: {
      type: 'date',
      typeConstraint: {
        to: []
      }
    },
    endYear: {
      type: 'date',
      isNullable: true,
      typeConstraint: {
        to: []
      }
    },
    currentArtistAliases: {
      type: 'artist',
      isSchema: true,
      isArray: true,
      readonly: true,
      nofilter: true
    },
    previousArtistAliases: {
      type: 'artist',
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
    featuringTracksCatalogNums: {
      type: 'track',
      isSchema: true,
      isArray: true,
      readonly: true,
      nofilter: true
    },
    participations: {
      type: 'participation',
      isSchema: true,
      isArray: true,
      filterOnly: true,
      joins: ['artist2band']
    }
  },
  key: 'bandAlias',
  display: (item) => item.bandAlias
}