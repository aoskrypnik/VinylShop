import Axios from 'axios'

const endpoint = 'http://localhost:8087'

const clients = [
  {
    phone: ['+123456', '+654321'],
    name: 'Lolkek Cheburek1',
    email: 'drakosvlad@gmail.com',
    type: '1'
  },
  {
    phone: ['+123456', '+654321'],
    name: 'Lolkek Cheburek2',
    email: 'drakosvla@gmail.com',
    type: '1'
  },
  {
    phone: ['+123456', '+654321'],
    name: 'Lolkek Cheburek3',
    email: 'drakosvl@gmail.com',
    type: '1'
  },
  {
    phone: ['+123456', '+654321'],
    name: 'Lolkek Cheburek4',
    email: 'drakosv@gmail.com',
    type: '1'
  }
]

export async function getItems(schema/*, count, offset, sortField, sortDirection*/) {
  if (schema === 'client') {
    return clients
  }

  if (schema === 'check') {
    return [
      {
        date: '2018-12-03',
        client: 'drakosvlad@gmail.com'
      },
      {
        date: '2018-12-04',
        client: 'drakosvl@gmail.com'
      }
    ]
  }


  const response = await Axios.get(
      `${endpoint}/${schema}/search`,
      {
        headers: {
          'Authorization': 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkaXJlY3RvciIsImlhdCI6MTU4MTg3MjQzNCwiZXhwIjoxNTgyNDc3MjM0fQ.4ivEPBa-OdwvN6bKdf7pDBwX-gt6jcBbjCLrHqrdVFHDgSnVj87CcdXWwT5vIFPcJeZU9F-3gYWJc7BugPjtYg'
        }
      })

  return response.data
}

export async function getItem(schema, key) {
  if (schema === 'client') {
    return clients.filter(c => c.email === key)[0]
  }

  if (schema === 'check') {
    return {
      date: '2018-12-03',
      client: 'drakosvl@gmail.com'
    }
  }

  try {
    const response = await Axios.get(
        `${endpoint}/${schema}/${key}`,
        {
          headers: {
            'Authorization': 'Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkaXJlY3RvciIsImlhdCI6MTU4MTg3MjQzNCwiZXhwIjoxNTgyNDc3MjM0fQ.4ivEPBa-OdwvN6bKdf7pDBwX-gt6jcBbjCLrHqrdVFHDgSnVj87CcdXWwT5vIFPcJeZU9F-3gYWJc7BugPjtYg'
          }
        })

    return response.data
  } catch(e) {
    // eslint-disable-next-line no-console
    console.log(e)
  }
}