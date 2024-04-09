import { setupLayouts } from 'virtual:generated-layouts'
import { createRouter, createWebHistory } from 'vue-router'
import routes from '~pages'

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            redirect: to => {
                // const userData = JSON.parse(localStorage.getItem('userData') || '{}')
                // const userRole = (userData && userData.role) ? userData.role : null
                // if (userRole === 'admin')
                //     return { name: 'dashboards-crm' }
                // if (userRole === 'client')
                //     return { name: 'access-control' }

                return { name: 'login', query: to.query }
            },
        },
        //Routes are auto created based on Pages folder structure
        ...setupLayouts(routes),
    ],
})


// Docs: https://router.vuejs.org/guide/advanced/navigation-guards.html#global-before-guards
export default router
