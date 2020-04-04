export default {
  props: {
    tabNum: {
      type: 'int',
      readonly: true
    },
    salesmanName: 'string',
    passportNum: 'string',
    addressCity: 'string',
    addressStr: 'string',
    addressHome: 'string',
    addressApt: {
      type: 'int',
      isNullable: true,
      typeConstraint: {
        from: 1
      }
    },
    salesmanPhoneNum: {
      type: 'string',
      typeConstraint: /^\+?\d+$/
    },
    worksFrom: {
      type: 'date',
      typeConstraint: {
        to: []
      }
    },
    worksTo: {
      type: 'date',
      isNullable: true,
      typeConstraint: {
        to: []
      }
    },
    salary: {
      type: 'int',
      typeConstraint: {
        from: 0
      }
    }
  },
  key: 'tabNum',
  display: (item) => item.salesmanName
}