export default {
  props: {
    name: 'string',
    phone: {
      type: 'string',
      isArray: true
    },
    email: 'string',
    type: '1|2'
  },
  key: 'email'
}