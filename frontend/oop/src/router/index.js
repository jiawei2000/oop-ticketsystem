/**
 * router/index.ts
 *
 * Automatic routes for `./src/pages/*.vue`
 */

// Composables
import { createRouter, createWebHistory } from 'vue-router'
// import { setupLayouts } from 'virtual:generated-layouts'

const routes = [
  {
    path: '/',
    component: () => import('@/layouts/LoginDefault.vue'),
    children: [
      {
        path: 'login',
        name: 'Login',
        component: () => import('@/pages/Login.vue')
      },
      {
        path: 'home',
        name: 'Home',
        component: () => import('@/pages/Home.vue')
      },
      {
        path: 'createEvent',
        name: 'createEvent',
        component: () => import('@/pages/CreateEvent.vue')
      },
      {
        path: 'updateEvent',
        name: 'updateEvent',
        component: () => import('@/pages/UpdateEvent.vue')
      },

    ]
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
})

export default router
