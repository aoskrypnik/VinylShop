export default {
  props: {
    albumCatalogNum: {
      type: 'int',
      noedit: true
    },
    albumName: 'string',
    releaseYear: {
      type: 'int',
      typeConstraint: {
        from: 0,
        to: new Date().getFullYear()
      }
    },
    albumGenres: {
      type: 'rock|pop|classic|indie|electronic|hip hop|reggae|funk|soul',
      isArray: true,
      joins: ['albumgenre']
    },
    variousArtists: {
      type: 'boolean'
    },
    trackCatalogNums: {
      readonly: true,
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