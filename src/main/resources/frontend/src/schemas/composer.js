export default {
  props: {
    composerName: 'string',
    countryCode: 'string',
    activityStart: 'date',
    activityEnd: 'date',
    trackIds: {
      type: 'track',
      isSchema: true,
      isArray: true
    }
  },
  key: 'composerName',
  display: (item) => item.composerName
}