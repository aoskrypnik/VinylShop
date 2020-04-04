import store from '@/store'
import * as Api from '@/api'

const enumRegex = /([\d\w]+\|)+[\d\w]+/

export function isStringType(type) {
  if (type.isSchema) {
    const schema = store.getters.getSchema(type.type)
    return isStringType(schema.props[schema.key])
  }

  const typeString = getTypeString(type)
  return typeString === 'string' || typeString === 'int' || typeString === 'date'
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

export const isDate = (type) => isType(type, 'date')

export const isEnum = (type) => {
  if (type.isSchema) {
    const schema = store.getters.getSchema(type.type)
    return enumRegex.test(schema.props[schema.key])
  }

  return enumRegex.test(getTypeString(type))
};

export const isSchemaType = (type) => {
  return Boolean(type.isSchema)
};

export const isArray = (type) => {
  if (typeof type === 'string') {
    return false
  }

  return type.isArray
};

export const fkValue = async (schemaName, key) => {
  const item = await Api.getItem(schemaName, key);
  const schema = store.getters.getSchema(schemaName);

  if (item === null) {
    return null
  }

  return schema.display(item)
};

export const valueString = (schemaName, item) => {
  if (item === null) {
    return null
  }

  const schema = store.getters.getSchema(schemaName);
  return schema.display(item)
};

export const isRangeType = (type) => {
  const typeString = getTypeString(type);

  return typeString === 'date' || typeString === 'int' || typeString === 'money'
};

export const generateFilterObject = (schemaName) => {
  const schema = store.getters.getSchema(schemaName);

  return Object.entries(schema.props).reduce((acc, [prop, type]) => {
    if (!isFilterVisible(type)) {
      return acc
    }
    return {
      ...acc,
      [prop]: isRangeType(type) ? {from: null, to: null} : ((isArray(type) && isSchemaType(type)) ? [] : null)
    }
  }, {})
};

export const isListVisible = (type) => {
  return !(type.filterOnly)
};

export const isFilterVisible = (type) => {
  return !type.nofilter
};

export const isEditFormVisible = (type) => {
  return !type.filterOnly && !type.readonly && !type.noedit
};

export const isNewFormVisible = (type) => {
  return !type.filterOnly && !type.readonly
};

export const getKey = (schemaName) => (item) => {
  const schema = store.getters.getSchema(schemaName);

  if (Array.isArray(schema.key)) {
    return schema.key.map(k => item[k]).join(',')
  } else {
    return item[schema.key]
  }
};

export const nullCheck = (schemaName, item, newItem) => {
  const schema = store.getters.getSchema(schemaName);


  return Object.entries(schema.props).filter(([prop, type]) => {
    return (newItem ? isNewFormVisible(type) : isEditFormVisible(type)) && !type.isNullable && (item[prop] === null || item[prop] === undefined)
  }).map(([prop]) => prop)
};