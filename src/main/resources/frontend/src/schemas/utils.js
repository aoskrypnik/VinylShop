import store from '@/store'

const enumRegex = /([\d\w]+\|)+[\d\w]+/

export function isStringType(type) {
  if (type.isSchema) {
    const schema = store.getters.getSchema(type.type)
    return isStringType(schema.props[schema.key])
  }

  const typeString = getTypeString(type)
  return typeString === 'string' || typeString === 'int' || typeString === 'date' || enumRegex.test(typeString)
}

export function getTypeString(type) {
  if (typeof type === 'string') {
    return type
  }

  return type.type
}

function isType(type, desired) {
  if (type.isSchema) {
    const schema = store.getters.getSchema(type.type)
    return schema.props[schema.key] === desired
  }

  return getTypeString(type) === desired
}

export const isString = (type) => isType(type, 'string')

export const isEnum = (type) => {
  if (type.isSchema) {
    const schema = store.getters.getSchema(type.type)
    return enumRegex.test(schema.props[schema.key])
  }

  return enumRegex.test(getTypeString(type))
}

export const isArray = (type) => {
  if (typeof type === 'string') {
    return false
  }

  return type.isArray
}