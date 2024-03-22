import { createRouter, createWebHistory } from "vue-router";
import HomeView from "../views/common/HomeView.vue";
import LevelView from "@/views/level/LevelView.vue";
import HistoryView from "@/views/history/HistoryView.vue";
import StudyView from "@/views/study/StudyView.vue";
import MyPageView from "@/views/user/MyPageView.vue";
import RegisterView from "@/views/user/RegisterView.vue";
import SearchView from "@/views/common/SearchView.vue";
import VideoView from "@/views/common/VideoView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: HomeView,
    },
    {
      path: "/video",
      name: "video",
      component: VideoView,

    },
    {
      path: "/search",
      name: "search",
      component: SearchView,
    },
    {
      path: "/history",
      name: "history",
      component: HistoryView,
    },
    {
      path: "/level",
      name: "level",
      component: LevelView,
    },
    {
      path: "/study",
      name: "study",
      component: StudyView,
      meta: { hideHeader: true },
    },
    {
      path: "/mypage",
      name: "mypage",
      component: MyPageView,
    },
    {
      path: "/register",
      name: "register",
      component: RegisterView,
      meta: { isRegister: true },

    },
  ],
});

export default router;
