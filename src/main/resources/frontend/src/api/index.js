export async function getItems(/*schema, count, offset, sortField, sortDirection*/) {
  return [
    {
      phone: ['+123456', '+654321'],
      name: 'Lolkek Cheburek',
      email: 'drakosvlad@gmail.com',
      type: '1'
    },
    {
      phone: ['+123456', '+654321'],
      name: 'Lolkek Cheburek',
      email: 'drakosvla@gmail.com',
      type: '1'
    },
    {
      phone: ['+123456', '+654321'],
      name: 'Lolkek Cheburek',
      email: 'drakosvl@gmail.com',
      type: '1'
    },
    {
      phone: ['+123456', '+654321'],
      name: 'Lolkek Cheburek',
      email: 'drakosv@gmail.com',
      type: '1'
    }
  ]
}

export async function getItem() {
  return {
    phone: ['+123456', '+654321'],
    name: 'Lolkek Cheburek',
    email: 'drakosvlad@gmail.com',
    type: '1'
  }
}