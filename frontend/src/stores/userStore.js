import { defineStore } from 'pinia';
import { ref } from 'vue';

export const userStore = defineStore(
    'userStore',
    () => {
        const userId = ref(0); // 회원가입 과정에서 사용하는 용도
        const nickName = ref("");
        const level = ref(-1);
        const isLogin = ref(false);

        function setUserId(id) {
			userId.value = id;
		}

        function setLogin(){
            isLogin.value = true;
        }

        function setLogout(){
            isLogin.value = false;
            nickName.value = "";
            level.value = -1;
        }

        function setNickname(input){
            nickName.value = input;
        }

        function setLevel(userlevel){
            level.value = userlevel;
        }

        return {
            userId,
            isLogin,
            nickName,
            level,
            setUserId,
            setLogin,
            setLogout,
            setNickname,
            setLevel
        }
    },
    { persist: true, strategies: [{ storage: localStorage }] },
);