export default {
  props: {
    name: 'string',
    phone: {
      type: 'string',
      isArray: true
    },
    email: {
      type: 'string',
      typeConstraint: /[\w]+@[\w]+\.[\w]+/
    },
    type: {
      type: '1|2',
      isArray: true
    }
  },
  key: 'email'
}