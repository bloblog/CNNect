<template>
  <div class="flex items-center justify-center min-h-[90vh]">
    <div class="w-2/4 mx-4">
      <div class="flex items-center mb-5">
        <div class="w-1/4">
          <label
            for="email"
            class="mb-2 mr-3 mt-2 text-sm font-medium whitespace-nowrap text-gray-900"
            >이메일</label
          >
        </div>
        <div class="w-3/4 gird grid-cols-2 gap-6 flex h-10">
          <input
            type="email"
            id="email"
            v-model="formData.userEmail"
            class="bg-gray-50 w-2/3 mr-3 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-2 focus:outline-none focus:ring-red-300 p-2.5"
            placeholder="example@google.com"
            required
          />
          <button
            type="button"
            @click="emailDuplCheck()"
            class="min-w-fit text-theme-red hover:text-white border border-theme-red hover:bg-theme-red focus:ring-2 focus:outline-none focus:ring-red-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center"
          >
            중복확인
          </button>
        </div>
      </div>
      <div class="flex items-center mb-5">
        <div class="w-1/4">
          <label
            for="email"
            class="mb-2 mr-3 mt-2 text-sm font-medium whitespace-nowrap text-gray-900 dark:text-white"
            >인증코드</label
          >
        </div>
        <div class="w-3/4 gird grid-cols-2 gap-6 flex h-10">
          <input
            type="text"
            id="authCode"
            v-model="authCode"
            class="bg-gray-50 w-2/3 mr-3 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-2 focus:outline-none focus:ring-red-300 p-2.5"
            required
          />

          <button
            v-if="!authCheck"
            type="button"
            @click="codeSend()"
            class="min-w-fit text-theme-red hover:text-white border border-theme-red hover:bg-theme-red focus:ring-2 focus:outline-none focus:ring-red-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center"
          >
            인증전송
          </button>
          <button
            v-else
            type="button"
            @click="codeCheck()"
            class="min-w-fit text-theme-red hover:text-white border border-theme-red hover:bg-theme-red focus:ring-2 focus:outline-none focus:ring-red-300 font-medium rounded-lg text-sm px-5 py-2.5 text-center"
          >
            인증확인
          </button>
        </div>
      </div>
      <div class="flex items-center mb-5">
        <div class="w-1/4">
          <label
            for="password"
            class="mb-2 mr-3 mt-2 text-sm font-medium whitespace-nowrap text-gray-900"
            >비밀번호</label
          >
        </div>
        <div class="w-3/4">
          <input
            type="password"
            id="password"
            v-model="formData.userPassword"
            class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-2 focus:outline-none focus:ring-red-300 w-full p-2.5"
            required
          />
        </div>
      </div>
      <div class="flex items-center mb-5">
        <div class="w-1/4">
          <label
            for="password"
            class="mb-2 mr-3 mt-2 text-sm font-medium whitespace-nowrap text-gray-900"
            >비밀번호 확인</label
          >
        </div>
        <div class="w-3/4">
          <input
            type="password"
            id="passworConfirm"
            v-model="passworConfirm"
            class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-2 focus:outline-none focus:ring-red-300 w-full p-2.5"
            required
          />
        </div>
      </div>
      <div class="flex items-center mb-5">
        <div class="w-1/4">
          <label
            for="nickname"
            class="mb-2 mr-3 mt-2 text-sm font-medium whitespace-nowrap text-gray-900"
            >닉네임</label
          >
        </div>
        <div class="w-3/4">
          <input
            type="text"
            id="nickname"
            v-model="formData.userNickname"
            class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-2 focus:outline-none focus:ring-red-300 w-full p-2.5"
            required
          />
        </div>
      </div>
      <div class="flex items-center w-full justify-center">
        <button
          type="button"
          @click="nextStep(1)"
          class="min-w-fit items-center text-white bg-theme-red hover:bg-theme-redbrown focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center"
        >
          다음
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from "vue";
import { registUser, emailCheck, emailSend, loginUser, codeValidate } from "@/api/user";
import { userStore } from "@/stores/userStore";
import Swal from "sweetalert2";

const uStore = userStore();
const { setUserId, setNickname, setLevel } = uStore;

const dupliCheck = ref(false); // 이메일 중복 확인 여부
const authCheck = ref(false); // 이메일 인증 여부

const authCode = ref("");

const formData = ref({
  userEmail: "",
  userPassword: "",
  userNickname: "",
});

const checkValue = ref({
  email : "",
  authCode : ""
})
const passworConfirm = ref("");

const msg = Swal.mixin({
  position: "center",
  showConfirmButton: true,
  confirmButtonText: "확인",
  backdrop: true,
}); // alert창 기본틀

const emit = defineEmits(["nextStep"]);
const nextStep = (input) => {
  if (!dupliCheck.value) {
    Swal.fire({
      icon: "warning",
      title: "이메일 중복 확인을 해주세요!",
    });
    // alert("이메일 중복 확인을 해주세요!");
  } else if (!authCheck.value) {
    Swal.fire({
      icon: "warning",
      title: "이메일 인증을 진행해주세요!",
    });
  } else if (
    formData.value.userNickname.length > 10 ||
    formData.value.userNickname.length < 2
  ) {
    Swal.fire({
      icon: "warning",
      title: "닉네임을 확인해주세요!",
      text: "닉네임은 2자 이상 10자 이하로 작성해주세요.",
    });
    // alert("닉네임을 2자 이상 10자 이하로 작성해주세요!")
  } else if (passworConfirm.value != formData.value.userPassword) {
    Swal.fire({
      icon: "warning",
      title: "비밀번호가 일치하지 않습니다!",
    });
    // alert("비밀번호가 일치하지 않습니다!");
  } else if (!authCheck.value) {
    Swal.fire({
      icon: "warning",
      title: "이메일 인증을 진행해주세요!",
    });
  } else {
    // 1. 다음 단계 이동
    emit("nextStep", input);

    // 2. 기본 user 정보 insert api 호출
    registUser(
      formData.value,
      ({ data }) => {
        setUserId(data.data);
        loginUser(
          {
            userEmail: formData.value.userEmail,
            userPassword: formData.value.userPassword,
          },
          ({ data }) => {
            localStorage.setItem(
              "refreshToken",
              data.data.jwtToken.refreshToken
            );
            localStorage.setItem("accessToken", data.data.jwtToken.accessToken);
            setLevel(data.data.level);
            setNickname(data.data.nickName);
          },
          (error) => {
            console.log(error);
          }
        );
      },
      (error) => {
        console.log(error);
      }
    );
  }
};

const emailDuplCheck = () => {
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;

  if (formData.value.userEmail.length == 0) {
    Swal.fire({
      icon: "warning",
      title: "이메일을 입력해주세요!",
    });
    // alert("이메일을 입력해주세요!");
  } else if (!emailRegex.test(formData.value.userEmail)) {
    Swal.fire({
      icon: "warning",
      title: "이메일 형식을 확인해주세요!",
    });
    // alert("이메일 형식을 확인해주세요!");
  } else {
    emailCheck(formData.value.userEmail, ({ data }) => {
      if (data.data) {
        Swal.fire({
          icon: "warning",
          title: "중복된 이메일입니다!",
        });
        // alert("중복된 이메일입니다!");
        formData.value.userEmail = "";
      } else {
        Swal.fire({
          icon: "success",
          title: "사용 가능한 이메일입니다!",
        });
        // alert("가능한 이메일입니다!");
        dupliCheck.value = true;
      }
    });
  }
};

const codeSend = () => {
  if (!dupliCheck.value) {
    Swal.fire({
      icon: "warning",
      title: "이메일 중복 확인을 <br>먼저 진행해주세요!",
    });
  }else{
    emailSend(
    formData.value.userEmail,
    ({ data }) => {
      Swal.fire({
        icon: "info",
        title: "인증 코드가 전송되었습니다!",
      });
      authCheck.value = true;
    },
    (error) => {
      console.log(error);
    }
  );
  }
};

const codeCheck = () => {
  checkValue.value.email = formData.value.userEmail;
  checkValue.value.authCode = authCode.value;
  // console.log(checkValue.value);
  codeValidate(
    checkValue.value,
    ({ data }) => {
      if (data.data) {
        Swal.fire({
          icon: "success",
          title: "인증이 성공되었습니다!",
        });
      } else {
        Swal.fire({
          icon: "warning",
          title: "인증번호를 확인해주세요!",
        });
      }
    },
    (error) => {}
  );
};
</script>

<style></style>
