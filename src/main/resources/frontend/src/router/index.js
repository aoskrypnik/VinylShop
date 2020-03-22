import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from './pages/Home.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    component: Home
  },
  {
    path: '/list/:schema',
    name: 'list',
    component: () => import(/* webpackChunkName: "about" */ './pages/List.vue')
  },
  {
    path: '/form/:schema/:item',
    name: 'form',
    component: () => import(/* webpackChunkName: "about" */ './pages/Form.vue')
  },
  {
    path: '/form/:schema',
    name: 'form-new',
    component: () => import(/* webpackChunkName: "about" */ './pages/Form.vue')
  },
  {
    path: '/entry/:schema/:item',
    name: 'entry',
    component: () => import(/* webpackChunkName: "about" */ './pages/Entry.vue')
  }
]

const router = new VueRouter({
  routes
})

export default router
