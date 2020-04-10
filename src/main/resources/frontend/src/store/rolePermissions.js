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
      },
      {
        icon: 'compact-disc',
        label: 'soldOut',
        route: {
          name: 'custom'
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
      },
      {
        icon: 'compact-disc',
        label: 'soldOut',
        route: {
          name: 'custom'
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
      'track',
      'supplier'
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
      'track',
      'supplier'
    ]
  }
}