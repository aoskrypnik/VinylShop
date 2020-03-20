export default {
  props: {
    artistAlias: {
      type: 'artist',
      isSchema: true,
      joins: []
    },
    bandAlias: {
      type: 'band',
      isSchema: true,
      joins: []
    },
    joinDate: 'date',
    exitDate: {
      type: 'date',
      isNullable: true
    }
  },
  key: ['artistAlias', 'bandAlias'],
  display: (item) => `${item.artistAlias} - ${item.bandAlias}`
}