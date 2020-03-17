import Axios from 'axios'
import store from '@/store'
import * as SchemaUtils from '@/schemas/utils'

const endpoint = 'http://localhost:8087';

function generateAuthHeader() {
  return {
    'Authorization': `Bearer ${store.state.token}`
  }
}

function generateQueryString(params) {
  // eslint-disable-next-line
  console.log(params)

  const entries = Object.entries(params).filter(([, v]) => v !== undefined && v !== null && v !== []);

  return entries.map(
      (([k, v]) => {
        if (Array.isArray(v)) {
          return v.map(s => `${k}=${s}`).join('&')
        }
        return `${k}=${v}`
      })).join('&').replace('&&', '&').replace('&&', '&')
}

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
];

export async function auth(login, password) {
  const authData = await Axios.post(
      `${endpoint}/auth/sign-in`,
      {
        login,
        password
      },
      {
        headers: {
          'Content-Type': 'application/json',
          ...generateAuthHeader()
        }
      },

  );

  store.commit('authenticate', authData.data.accessToken);
  return true
}

// eslint-disable-next-line no-unused-vars
export async function getItems(schema, count, offset, sortField, sortDirection, filters) {
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

  let query = {
    count,
    offset,
    sort: sortField,
    order: sortField ? (sortDirection === 0 ? 'ASC' : 'DESC') : undefined,
  };

  if (filters) {
    query.likes = []
    query.betweens = []
    query.wheres = []
    query.joins = []

    const schemaObject = store.getters.getSchemaProps(schema)

    Object.entries(filters).forEach(([prop, value]) => {
      if (value === null) {
        return
      }

      const type = schemaObject[prop]

      if (SchemaUtils.isRangeType(type)) {
        if (value.from === null && value.to === null) {
          return
        }

        query.betweens.push(`${prop}:${value.from || ''}:${value.to || ''}`)
      } else if (SchemaUtils.isEnum(type) || SchemaUtils.isSchemaType(type)) {
        if (SchemaUtils.isSchemaType(type)) {
          if (Array.isArray(value) && value.length === 0) {
            return
          }

          if (type.joins) {
            query.joins.push(...type.joins)
          } else {
            query.joins.push(SchemaUtils.getTypeString(type))
          }

          if (SchemaUtils.isArray(type)) {
            value.forEach(v => query.wheres.push(`${prop}:${v}`))
            return
          }
        }

        query.wheres.push(`${prop}:${value}`)
      } else {
        // Likes
        query.likes.push(`${prop}:${value}`)
      }
    })
  }

  // eslint-disable-next-line no-console
  const params = generateQueryString(query);

  const response = await Axios.get(
      `${endpoint}/${schema}/search?${params}`,
      {
        headers: {
          ...generateAuthHeader()
        }
    });

  return response.data
}

export async function getItem(schema, key) {
  if (key === null) {
    return null
  }

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
            ...generateAuthHeader()
          }
        });

    return response.data
  } catch(e) {
    // eslint-disable-next-line no-console
    console.log(e)
  }
}