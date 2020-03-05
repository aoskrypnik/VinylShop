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
  }
]

const router = new VueRouter({
  routes
})

export default router
