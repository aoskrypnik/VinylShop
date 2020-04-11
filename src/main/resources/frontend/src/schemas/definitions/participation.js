export default {
  props: {
    participationArtistAlias: {
      type: 'artist',
      isSchema: true,
      joins: [],
      noedit: true
    },
    participationBandAlias: {
      type: 'band',
      isSchema: true,
      joins: [],
      noedit: true
    },
    joinDate: {
      type: 'date',
      typeConstraint: {
        to: []
      },
      noedit: true
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