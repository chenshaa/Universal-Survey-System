import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/view/Home.vue'
import Survey from '@/view/questionnaire'
import Login from '@/view/login'
import Admin from '@/view/admin'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home
    },
    {
      path: '/survey/:id',
      name: 'Survey',
      component: Survey
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/admin',
      name: 'Admin',
      redirect: '/admin/home',
      component: Admin,
      children: [
        {
          path: 'home',
          name: 'home',
          components: {
            adminView: () => import('@/view/admin/home')
          }
        },
        {
          path: 'designQuest',
          name: 'designQuest',
          components: {
            adminView: () => import('@/view/admin/designQuest')
          },
          children: [
            {
              path: '',
              name: 'home',
              components: {
                designView: () => import('@/view/admin/design')
              }
            },
            {
              path: 'design/:id',
              name: 'design',
              components: {
                designView: () => import('@/view/admin/design/design.vue')
              }
            },
          ]
        },
        {
          path: 'sentQuest',
          name: 'sentQuest',
          components: {
            adminView: () => import('@/view/admin/sentQuest.vue')
          }
        },
        {
          path: 'manageUser',
          name: 'manageUser',
          components: {
            adminView: () => import('@/view/admin/manageUser.vue')
          }
        },
        {
          path: 'manageSystem',
          name: 'manageSystem',
          components: {
            adminView: () => import('@/view/admin/manageSystem.vue')
          }
        },
        {
          path: 'downloadQuest',
          name: 'downloadQuest',
          components: {
            adminView: () => import('@/view/admin/downloadQuest.vue')
          }
        },

      ]
    }
  ]
})
