export default {
  props: {
    participationArtistAlias: {
      type: 'artist',
      isSchema: true,
      joins: []
    },
    participationBandAlias: {
      type: 'band',
      isSchema: true,
      joins: []
    },
    joinDate: {
      type: 'date',
      typeConstraint: {
        to: []
      }
    },
    exitDate: {
      type: 'date',
      isNullable: true,
      typeConstraint: {
        to: []
      }
    }
  },
  key: ['participationArtistAlias', 'participationBandAlias'],
  display: (item) => `${item.participationArtistAlias} - ${item.participationBandAlias }`
}