<template>
  <div>
    <div v-if="isLogin == true">
      <DefaultHome/>
    </div>
    <div v-else>
      <NonloginHome/>
    </div>
  </div>

  
  
</template>

<script setup>
import { ref, onMounted } from "vue";
import { initFlowbite, Modal } from "flowbite";
import { useRoute, useRouter } from "vue-router";
import { loginUser } from "@/api/user";
import { userStore } from "@/stores/userStore";
import { storeToRefs } from "pinia";

import NonloginHome from '@/components/common/NonLoginHome.vue'
import DefaultHome from '@/components/common/DefaultHome.vue'

const route = useRoute();
const router = useRouter();
const uStore = userStore();

const loginData = ref({
  userEmail: "",
  userPassword: "",
});

const { setLogin, setLogout, setNickname, setLevel } = uStore;
const { isLogin } = storeToRefs(uStore);

onMounted(() => {
  initFlowbite();
  console.log(modal.isVisible());
  
});


// set the modal menu element
const $targetEl = document.getElementById("authentication-modal");

// options with default values
const options = {
  placement: "bottom-right",
  backdrop: "dynamic",
  backdropClasses: "bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40",
  closable: true,
  onHide: () => {
    console.log("modal is hidden");
  },
  onShow: () => {
    console.log("modal is shown");
  },
  onToggle: () => {
    console.log("modal has been toggled");
  },
};

// instance options object
const instanceOptions = {
  id: "authentication-modal",
  override: false,
};

const modal = new Modal($targetEl, options, instanceOptions);

const login = () => {
  loginUser(
    loginData.value,
    ({ data }) => {
      console.log(data);
      console.log(data.data.level);
      localStorage.setItem("refreshToken", data.data.jwtToken.refreshToken);
      localStorage.setItem("accessToken", data.data.jwtToken.accessToken);
      setLogin();
      setLevel(data.data.level);
      setNickname(data.data.nickName);

      location.href = "/";
    },
    (error) => {
      alert("아이디 및 비밀번호를 확인해주세요!")
      loginData.value.userPassword = "";
      console.log(error);
    }
  );
};


const goToStudy = () => {
  router.push("/study");
};

</script>

<style scoped>
#now-video, #recomm-video {
  width: 80vw;
}

#now-video-img{
  width:70rem;
  border-radius: 10px;
  object-fit: contain;
}

#container-layer {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-image: linear-gradient(90deg, #00000037 10%, #000000);
    pointer-events: none; /* 이벤트를 통과시킴 */
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.5); /* 그림자 추가 */
    
}

#video-name, #sentence-count, #last-sentence {
  color: white;
  z-index: 2;
}

</style>
