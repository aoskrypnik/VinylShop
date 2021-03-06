import Vue from 'vue'
import Vuex from 'vuex'

import lookup from 'country-code-lookup'
import * as uuid from 'uuid'

import Schemas from '../schemas'
import * as SchemaUtils from '../schemas/utils'
import SchemaDictionary from '../schemas/dictionary'
import AppDictionary from './appDictionary'
import ErrorMessages from './errorMessages'
import RolePermissions from './rolePermissions'

import * as Api from '@/api'

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    language: window.localStorage.getItem('language') || 'ua',
    schemas: Schemas,
    schemaDictionary: SchemaDictionary,
    appDictionary: AppDictionary,
    popups: [/*{ type: 'list', properties: { schema: 'client', itemSelection: () => {} } }*/],
    token: window.localStorage.getItem('authToken'),
    role: window.localStorage.getItem('authRole'),
    username: window.localStorage.getItem('authUsername'),
    countries: new Map(lookup.countries.map(({ iso2, country }) => [iso2, country]))
  },
  mutations: {
    popup (state, content) {
      state.popups.push(content)
    },
    closePopup (state) {
      state.popups.pop()
    },
    authenticate (state, { token, role, username }) {
      state.token = token;
      state.role = role;
      state.username = username;
      window.localStorage.setItem('authToken', token);
      window.localStorage.setItem('authRole', role);
      window.localStorage.setItem('authUsername', username)
    },
    signOut (state) {
      state.token = undefined;
      state.role = undefined;
      state.username = undefined;
      window.localStorage.removeItem('authToken');
      window.localStorage.removeItem('authRole');
      window.localStorage.removeItem('authUsername')
    },
    toggleLanguage (state) {
      state.language = state.language === 'ua' ? 'en' : 'ua'
      window.localStorage.setItem('language', state.language);
    }
  },
  getters: {
    getSchemaLocale: (state) => (schema) => {
      return state.schemaDictionary.schemas[schema][state.language] || schema
    },
    getPropertyLocale: (state) => (schema, property) => {
      try {
        if (state.schemaDictionary.schemas[schema].properties[property]) {
          return state.schemaDictionary.schemas[schema].properties[property][state.language] || property
        } else {
          return property
        }
      } catch (_) {
        // eslint-disable-next-line
        console.log(schema + property)
        return ''
      }
    },
    getSchemaHeaders: (state) => (schema) => {
      return Object.keys(state.schemas[schema].props.filter())
      .map(prop => state.schemaDictionary.schemas[schema].properties[prop][state.language])
    },
    getSchemaPropsNames: (state) => (schema) => {
      return Object.keys(state.schemas[schema].props)
    },
    getSchemaDictionary: (state) => (schema) => {
      // eslint-disable-next-line
      console.log(schema)
      // eslint-disable-next-line
      console.log(state.schemaDictionary.schemas[schema])

      return Object.keys(state.schemas[schema].props).reduce((acc, prop) => ({
        ...acc,
        [prop]: state.schemaDictionary.schemas[schema].properties[prop][state.language]
      }), {})
    },
    getSchemaProps: (state) => (schema) => {
      return state.schemas[schema].props
    },
    getSchemaListProps: (state) => (schema) => {
      return Object.entries(state.schemas[schema].props)
          .filter(([, t]) => SchemaUtils.isListVisible(t))
          .map(([p,]) => p)
    },
    getSchemaKey: (state) => (schema) => {
      return state.schemas[schema].key
    },
    getSchema: (state) => (schema) => {
      return state.schemas[schema]
    },
    getAppLocale: (state) => (key) => {
      return state.appDictionary[key] ? state.appDictionary[key][state.language] : key
    },
    getErrorMessage: (_, getters) => (operation, type) => {
      const keys = ErrorMessages[operation][type]
      if (!keys) {
        return {
          title: '',
          message: ''
        }
      }
      return {
        title: getters.getAppLocale(keys.title),
        message: getters.getAppLocale(keys.message)
      }
    },
    getErrorType: () => (error) => {
      if (error.response) {
        switch(error.response.status) {
          case 404:
            return 'notfound'
          case 405:
          case 403:
          case 401:
          case 409:
            return 'forbidden'
          case 500:
            return 'internal'
        }
      }
      return 'generic'
    },
    isAuthenticated: (state) => () => {
      return Boolean(state.token)
    },
    getQuickActions: (state) => {
      return RolePermissions.quickActions[state.role]
    },
    getAvailableSchemas: (state) => {
      return RolePermissions.schemas[state.role]
    },
    getSchemasLocales: (state) => {
      return Object.keys(state.schemas).reduce((dict, schema) => ({
        ...dict,
        [schema]: state.schemaDictionary.schemas[schema][state.language]
      }), {})
    },
    isDirector: (state) => {
      return state.role === 'ROLE_DIRECTOR';
    },
    username: (state) => {
      return state.username
    }
  },
  actions: {
    addPopup ({ commit }, popup) {
      popup.key = uuid.v4()
      commit('popup', popup)
    },
    async authenticate ({ commit }, { login, password }) {
      const credentials = await Api.auth(login, password)
      commit('authenticate', { ...credentials, username: login })
    },
    async signOut ({ commit }) {
      commit('signOut')
    }
  },
  modules: {
  }
})
