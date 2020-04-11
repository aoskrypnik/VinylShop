import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from './pages/Login.vue'
import Register from './pages/Register'
import ChangePassword from './pages/ChangePassword'
import Store from '@/store'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'login',
    component: Login
  },
  {
    path: '/register',
    name: 'register',
    component: Register
  },
  {
    path: '/list/:schema',
    name: 'list',
    component: () => import(/* webpackChunkName: "list" */ './pages/List.vue')
  },
  {
    path: '/form/:schema/:item',
    name: 'form',
    component: () => import(/* webpackChunkName: "form" */ './pages/Form.vue')
  },
  {
    path: '/form/:schema',
    name: 'form-new',
    component: () => import(/* webpackChunkName: "form" */ './pages/Form.vue')
  },
  {
    path: '/entry/:schema/:item',
    name: 'entry',
    component: () => import(/* webpackChunkName: "entry" */ './pages/Entry.vue')
  },
  {
    path: '/home',
    name: 'home',
    component: () => import(/* webpackChunkName: "home" */ './pages/Home.vue')
  },
  {
    path: '/help/salesmanCreation',
    name: 'salesman-creation',
    component: () => import(/* webpackChunkName: "help" */ './pages/SalesmanCreation.vue')
  },
  {
    path: '/stats/dates',
    name: 'specific-dates-stats',
    component: () => import(/* webpackChunkName: "stats" */ './pages/SpecificDatesStats.vue')
  },
  {
    path: '/stats/month',
    name: 'month-stats',
    component: () => import(/* webpackChunkName: "stats" */ './pages/MonthStats.vue')
  },
  {
    path: '/stats',
    name: 'stats',
    component: () => import(/* webpackChunkName: "stats" */ './pages/StatsHome.vue')
  },
  {
    path: '/stats/salesmen',
    name: 'salesmen-stats',
    component: () => import(/* webpackChunkName: "stats" */ './pages/SalesmenStats.vue')
  },
  {
    path: '/custom',
    name: 'custom',
    component: () => import(/* webpackChunkName: "custom" */ './pages/CustomQuery.vue')
  },
  {
    path: '/password',
    name: 'password',
    component: ChangePassword
  }
];

const router = new VueRouter({
  routes
});

router.beforeEach((to, from, next) => {
  if (to.name !== 'login' && to.name !== 'register') {
    if (Store.getters.isAuthenticated()) {
      next()
    } else {
      next({ name: 'login' })
    }
  } else {
    if (Store.getters.isAuthenticated()) {
      next({ name: 'home' })
    } else {
      next()
    }
  }
});

export default router
