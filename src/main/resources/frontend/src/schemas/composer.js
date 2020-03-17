export default {
  props: {
    composerName: 'string',
    composerCountryCode: 'country',
    activityStart: 'date',
    activityEnd: {
      type: 'date',
      isNullable: true
    },
    trackIds: {
      type: 'track',
      isSchema: true,
      isArray: true,
      joins: ['track2composer']
    }
  },
  key: 'composerName',
  display: (item) => item.composerName
}