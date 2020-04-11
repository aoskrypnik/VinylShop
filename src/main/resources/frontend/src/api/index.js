import Axios from 'axios'
import store from '@/store'
import * as SchemaUtils from '@/schemas/utils'

const endpoint = 'http://192.168.88.71:8087';

function generateAuthHeader() {
  return {
    'Authorization': `Bearer ${store.state.token}`
  }
}

function generateQueryString(params) {
  const entries = Object.entries(params).filter(([, v]) => v !== undefined && v !== null && v !== []);

  return entries.map(
      (([k, v]) => {
        if (Array.isArray(v)) {
          return v.map(s => `${k}=${encodeURIComponent(s)}`).join('&')
        }
        return `${k}=${encodeURIComponent(v)}`
      })).join('&').replace(/&+/g, '&')
}

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
      });

  return {
    token: authData.data.accessToken,
    role: authData.data.authority
  }
}

export async function changePassword(login, oldPassword, password) {
  return Axios.put(`${endpoint}/credentials/sign-in`,
      {
        login,
        oldPassword,
        password,
        password2: password
      },
      {
        headers: {
          'Content-Type': 'application/json',
          ...generateAuthHeader()
        }
      });
}

// eslint-disable-next-line no-unused-vars
export async function getItems(schema, limit, offset, sortField, sortDirection, filters) {
  let query = {
    limit,
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
      }

      if (Array.isArray(value) && value.length === 0) {
        return
      }

      if (type.joins) {
        query.joins.push(...type.joins)
      }

      if (SchemaUtils.isRangeType(type)) {
        if (value.from === null && value.to === null) {
          return
        }

        query.betweens.push(`${prop}:${value.from || ''}:${value.to || ''}`)
      } else if (SchemaUtils.isString(type) && !SchemaUtils.isSchemaType(type)) {
        // Likes
        query.likes.push(`${prop}:${value}`)

      } else {
        if (SchemaUtils.isSchemaType(type)) {
          if (Array.isArray(value) && value.length === 0) {
            return
          }

          if (!type.joins) {
            query.joins.push(SchemaUtils.getTypeString(type))
          }

          if (Array.isArray(value)) {
            value.forEach(v => query.wheres.push(`${prop}:${v}`))
            return
          }
        }
        query.wheres.push(`${prop}:${value}`)
      }
    })
  }

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
  if (key === null || key === undefined) {
    return null
  }

  const response = await Axios.get(
      `${endpoint}/${schema}/${key}`,
      {
        headers: {
          ...generateAuthHeader()
        }
      });

  return response.data
}

export async function newItem(schema, item) {
  const response = await Axios.post(
      `${endpoint}/${schema}`,
      item,
      {
        headers: {
          ...generateAuthHeader()
        }
      });
  const locationParts = response.headers.location.split('/')
  return locationParts[locationParts.length - 1]
}

export async function updateItem(schema, item, key) {
  return Axios.put(
      `${endpoint}/${schema}/${key}`,
      item,
      {
        headers: {
          ...generateAuthHeader()
        }
      });
}

export async function deleteItem(schema, key) {
  return Axios.delete(
      `${endpoint}/${schema}/${key}`,
      {
        headers: {
          ...generateAuthHeader()
        }
      }
  )
}

export async function registerSalesman(tabNum, login, password) {
  return Axios.post(
      `${endpoint}/auth/sign-up`,
      {
        tabNum,
        login,
        password,
        password2: password
      },
      {
        headers: {
          'Content-Type': 'application/json'
        }
      },
  )
}

export async function statsByDate(from, to) {
  const params = generateQueryString({from, to})
  return (await Axios.get(
      `${endpoint}/statistics?${params}`,
      {
        headers: {
          ...generateAuthHeader()
        }
      }
  )).data
}

export async function statsBySalesmen(from, to) {
  const params = generateQueryString({from, to})
  return (await Axios.get(
      `${endpoint}/statistics/salesmen?${params}`,
      {
        headers: {
          ...generateAuthHeader()
        }
      }
  )).data
}

export async function statsByMonths(year) {
  const params = generateQueryString({year})
  return (await Axios.get(
      `${endpoint}/statistics/year?${params}`,
      {
        headers: {
          ...generateAuthHeader()
        }
      }
  )).data
}

export async function customQuery() {
  return (await Axios.get(
      `${endpoint}/artist/sold-out`,
      {
        headers: {
          ...generateAuthHeader()
        }
      }
  )).data
}