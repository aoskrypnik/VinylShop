export default {
  props: {
    composerName: {
      type: 'string',
      noedit: true
    },
    composerCountryCode: 'country',
    activityStart: {
      type: 'date',
      typeConstraint: {
        to: []
      }
    },
    activityEnd: {
      type: 'date',
      isNullable: true,
      typeConstraint: {
        to: []
      }
    },
    trackIds: {
      type: 'track',
      isSchema: true,
      isArray: true,
      joins: ['track2composer'],
      readonly: true
    }
  },
  key: 'composerName',
  nodelete: true,
  display: (item) => item.composerName
}