export default {
  props: {
    tabNum: 'int',
    salesmanName: 'string',
    passportNum: 'string',
    addressCity: 'string',
    addressStr: 'string',
    addressHome: 'string',
    addressApt: {
      type: 'int',
      isNullable: true
    },
    salesmanPhoneNum: 'string',
    worksFrom: 'date',
    worksTo: {
      type: 'date',
      isNullable: true
    },
    salary: 'int'
  },
  key: 'tabNum',
  display: (item) => item.salesmanName
}