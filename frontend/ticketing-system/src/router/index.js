import { setupLayouts } from 'virtual:generated-layouts'
import { createRouter, createWebHistory } from 'vue-router'
import routes from '~pages'

console.log('Routes: ', routes);

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes: [
        {
            path: '/',
            redirect: to => {
                return { name: 'login', query: to.query }
            },
        },
        //Routes are auto created based on Pages folder structure
        ...setupLayouts(routes),
    ],
})

router.beforeEach(to => {
    // If not logged in redirect to login page
    // if (localStorage.getItem('UserId') === null && (to.name != 'login' && to.name != 'register')) {
    //     console.log("Not logged in");
    //     return { name: 'login', query: to.query }
    // }
    
})


// Docs: https://router.vuejs.org/guide/advanced/navigation-guards.html#global-before-guards
export default router
