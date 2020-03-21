export default {
  props: {
    bandAlias: 'string',
    isBandActive: 'boolean',
    countryCode: 'country',
    startYear: 'date',
    endYear: 'date',
    currentArtistAliases: {
      type: 'artist',
      isSchema: true,
      isArray: true,
      readonly: true
    },
    previousArtistAliases: {
      type: 'artist',
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
    featuringTracksCatalogNums: {
      type: 'track',
      isSchema: true,
      isArray: true,
      readonly: true
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