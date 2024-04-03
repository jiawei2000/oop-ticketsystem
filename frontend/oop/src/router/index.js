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
      // {
      //   path: 'dashboard',
      //   name: 'Dashboard',
      //   component: () => import('@/pages/Dashboard.vue')
      // },
      {
        path: 'login',
        name: 'Login',
        component: () => import('@/pages/Login.vue')}

    ]
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
})

export default router
