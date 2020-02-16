export default {
  props: {
    trackCatalogNum: 'int',
    trackName: 'string',
    duration: 'int',
    composerIds: {
      type: 'composer',
      isSchema: true,
      isArray: true
    },
    /*"albumIds: [
      "101"
    ],*/
    //"artistIds": [],
    /*"bandIds": [
      "vremya and steklo"
    ],*/
    //"featuringArtistIds": [],
    //"featuringBandIds": [],
    languages: {
      type: 'string',
      isArray: true
    }
  },
  key: 'trackCatalogNum',
  display: (item) => item.trackName
}