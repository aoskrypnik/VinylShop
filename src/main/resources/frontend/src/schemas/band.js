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
    }
  },
  key: 'bandAlias',
  display: (item) => item.bandAlias
}