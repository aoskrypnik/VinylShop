export default {
  quickActions: {
    ROLE_DIRECTOR: [
      {
        icon: 'user',
        modifier: '+',
        label: 'createSalesmanAction',
        route: {
          name: 'salesman-creation'
        }
      },
      {
        icon: 'pie-chart',
        label: 'statsAction',
        route: {
          name: 'stats'
        }
      }
    ],
    ROLE_SALESMAN: [
      {
        icon: 'layers',
        modifier: '+',
        label: 'createCheckAction',
        route: {
          name: 'form',
          params: {
            schema: 'check'
          }
        }
      }
    ]
  },
  schemas: {
    ROLE_DIRECTOR: [
      'album',
      'artist',
      'artist2track',
      'band',
      'band2track',
      'check',
      'composer',
      'customer',
      'participation',
      'record',
      'release',
      'salesman',
      'track'
    ],
    ROLE_SALESMAN: [
      'album',
      'artist',
      'artist2track',
      'band',
      'band2track',
      'check',
      'composer',
      'customer',
      'participation',
      'record',
      'release',
      'track'
    ]
  }
}