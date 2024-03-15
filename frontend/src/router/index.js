import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/common/HomeView.vue'
import LevelView from '@/views/level/LevelView.vue'
import HistoryView from '@/views/history/HistoryView.vue'
import StudyView from '@/views/study/StudyView.vue'
import MyPageView from '@/views/user/MyPageView.vue'
import RegisterView from '@/views/user/RegisterView.vue'


const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView
    },
    {
      path : '/history',
      name : 'history',
      component : HistoryView
    },
    {
      path : '/level',
      name : 'level',
      component : LevelView
    },
    {
      path : '/study',
      name : 'study',
      component : StudyView,
      meta: { hideHeader: true }
    },
    {
      path : '/mypage',
      name : 'mypage',
      component : MyPageView
    },
    {
      path : '/register',
      name : 'register',
      component : RegisterView
    }
  ]
})

export default router
