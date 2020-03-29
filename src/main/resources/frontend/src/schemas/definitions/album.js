export default {
  props: {
    albumCatalogNum: 'int',
    albumName: 'string',
    releaseYear: 'int',
    albumGenres: {
      type: 'rock|pop|classic|indie|electronic|hip hop|reggae|funk|soul',
      isArray: true,
      joins: ['albumGenre']
    },
    variousArtists: {
      type: 'boolean',
      readonly: true
    },
    trackCatalogNums: {
      type: 'track',
      isSchema: 'true',
      isArray: true,
      joins: ['track2album']
    }
    // TODO release
  },
  key: 'albumCatalogNum',
  display: (item) => `${item.albumName} #${item.albumCatalogNum}`
}