<template>
  <div class="flex flex-col items-center p-10">
    <div id="now-video" class="">
      <h1 class="text-4xl font-bold p-5">학습 진행중인 뉴스</h1>
      <div id="now-video-container" class="relative flex border border-gray-300 rounded-xl justify-between">
        <div id="container-layer" class="rounded-xl"></div>
        <div>
          <img id="now-video-img" src="" alt="Now Video Image">
        </div>
        <div class="p-10 flex flex-col justify-around">
          <div class="text-4xl font-bold" id="video-name"></div>
          <div>
            <div class="flex">
              <div class="text-2xl font-bold text-white z-10">문장 수</div>
              <div class="relative text-2xl left-16" id="sentence-count"></div>
            </div>
            <div class="flex">
              <div class="text-2xl whitespace-nowrap font-bold text-white z-10">마지막 문장</div>
              <div id="last-sentence" class="relative text-2xl left-5"></div>
            </div>

          </div>
        </div>
      </div>
    </div>
    <div id="recomm-video">
      <h1 class="text-4xl font-bold p-5">추천 뉴스</h1>
      <div id="recomm-video-container">
        <RecommVideoList/>
      </div>
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

import RecommVideoList from '@/components/common/RecommVideoList.vue'

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

const now_video = {
  sentenceList: [
    {
      "start": 0.033,
      "text": "Elon musk tells don lemon, quote, contract is canceled, end quote."
    },
    {
      "start": 4.904,
      "text": "Now, in a moment, i'm going to speak exclusively with don lemon and he has clips of the nearly hour and a half interview with musk, clips like this one that we received from don's production team."
    },
    {
      "start": 16.616,
      "text": "Don, the only reason i'm doing this interview is because you're on the platform and you asked for it."
    },
    {
      "start": 17.45,
      "text": "Otherwise i would not do this interview."
    },
    {
      "start": 24.09,
      "text": "So you don't think do you think that you wouldn't get in trouble or you wouldn't be criticized for these things?"
    },
    {
      "start": 28.561,
      "text": "Besides, possibly i could care less."
    },
    {
      "start": 32.432,
      "text": "We're going have more clips from lemon's interview with musk in a moment."
    },
    {
      "start": 35.769,
      "text": "In a statement earlier today, lemon writes in part, quote, there were no restrictions on the interview that he willingly agreed to."
    },
    {
      "start": 42.275,
      "text": "We had a good conversation."
    },
    {
      "start": 44.01,
      "text": "Clearly, he felt differently."
    },
    {
      "start": 45.478,
      "text": "His commitment to a global town square where all questions can be asked and all ideas can be shared seems not to include questions of him from people like me."
    },
    {
      "start": 54.02,
      "text": "Now, cnn reached out to x for their comment, and their response is, quote, we reserve the right to make decisions about our business partnerships."
    },
    {
      "start": 61.861,
      "text": "And after careful consideration, x decided not to enter into a commercial partnership with the show."
    },
    {
      "start": 68.635,
      "text": "Now, this decision coming as musk had, of course, publicly courted lemon and has repeatedly made a commitment to free speech when he bought twitter again and again and again."
    },
    {
      "start": 80.647,
      "text": "Well, i think it's very important for there to be an inclusive arena for free speech where all."
    },
    {
      "start": 90.924,
      "text": "So, yeah, twitter has become kind of the de facto town square."
    },
    {
      "start": 98.164,
      "text": "Free speech is meaningless unless you're allow people you don't like to say things you don't like."
    },
    {
      "start": 105.038,
      "text": "Otherwise, it's irrelevant."
    },
    {
      "start": 106.239,
      "text": "I really can't emphasize this enough."
    },
    {
      "start": 107.707,
      "text": "We must we must protect free speech."
    },
    {
      "start": 111.845,
      "text": "And free speech only matters."
    },
    {
      "start": 104.07,
      "text": "It's only relevant when it's someone you don't like saying something like it's damn annoying when someone you don't like says something you don't like."
    },
    {
      "start": 77.043,
      "text": "That is a sign of a healthy, functioning free speech situation again and again and again."
    },
    {
      "start": 129.362,
      "text": "And remember, musk's ex has over a quarter billion daily active users."
    },
    {
      "start": 137.103,
      "text": "A quarter billion people on this planet."
    },
    {
      "start": 140.64,
      "text": "It is the town square."
    },
    {
      "start": 142.075,
      "text": "Joining me exclusively outfront is don lemon."
    },
    {
      "start": 145.912,
      "text": "Sadan, you do this 90 minute long interview with musk."
    },
    {
      "start": 149.449,
      "text": "You sit down with him."
    },
    {
      "start": 150.617,
      "text": "This is the culmination he had actively courted you to come on one of his tweets."
    },
    {
      "start": 154.12,
      "text": "Have you considered doing your show on this platform?"
    },
    {
      "start": 156.189,
      "text": "Maybe worth a try."
    },
    {
      "start": 157.023,
      "text": "Audience is much bigger."
    },
    {
      "start": 158.291,
      "text": "It had been public then."
    },
    {
      "start": 159.726,
      "text": "You had reached a deal."
    },
    {
      "start": 161.895,
      "text": "I'll talk about that later."
    },
    {
      "start": 163.563,
      "text": "Then he texts 24 hours after this 90 minute long interview, which is the first of your show launching."
    },
    {
      "start": 189.455,
      "text": "Yeah."
    },
    {
      "start": 172.939,
      "text": "First of all, it's good to see you, aaron."
    },
    {
      "start": 174.874,
      "text": "Thank you for having me on."
    },
    {
      "start": 176.676,
      "text": "That's a good question for elon musk."
    },
    {
      "start": 178.878,
      "text": "Quite frankly, what happened?"
    },
    {
      "start": 180.046,
      "text": "I don't know."
    },
    {
      "start": 180.813,
      "text": "As i said in my statement, i felt really good about the interview."
    },
    {
      "start": 184.684,
      "text": "I said to him as we were doing the interview and it was tense at moments."
    },
    {
      "start": 187.554,
      "text": "But you've been involved in tense interviews."
    },
    {
      "start": 189.822,
      "text": "I said to him, i think it's good that people see folks like you and i who have different world views come together and talk."
    },
    {
      "start": 197.564,
      "text": "As he says, i have free speech."
    },
    {
      "start": 118.351,
      "text": "Free speech is only important when someone you don't like or i would say someone who doesn't have your same point of view are someone is if they're allowed to speak freely and to say their point of view."
    },
    {
      "start": 211.911,
      "text": "Apparently that doesn't matter to elon musk."
    },
    {
      "start": 214.113,
      "text": "It's just for maybe talking points for him or rhetoric, because it doesn't seem to matter when it's about him."
    },
    {
      "start": 52.385,
      "text": "Questions about him from people like me."
    },
    {
      "start": 223.122,
      "text": "All right."
    },
    {
      "start": 223.423,
      "text": "So when you said that i wanted to play some of the clips because some of them are illuminating, these are clips that you shared with us."
    },
    {
      "start": 229.862,
      "text": "This is a part of the conversation with musk where you ask him about hate speech."
    },
    {
      "start": 235.201,
      "text": "Here it is."
    },
    {
      "start": 237.87,
      "text": "Hate speech on the platform is up."
    },
    {
      "start": 242.141,
      "text": "Do you believe that x and you have some responsibility to moderate hate speech on the platform that you wouldn't have to answer these questions from reporters about the great replacement theory?"
    },
    {
      "start": 251.384,
      "text": "Is it really?"
    },
    {
      "start": 251.851,
      "text": "I don't have to answer this great replacement theory as it relates to jewish people."
    },
    {
      "start": 18.018,
      "text": "Do you think that i don't have to answer questions from reporters on the only reason i'm doing this interview is because you're on the platform and you asked for it."
    },
    {
      "start": 259.492,
      "text": "Otherwise, i would not do this interview."
    },
    {
      "start": 24.924,
      "text": "So you don't think do you think that you wouldn't get in trouble or you wouldn't be criticized for these things?"
    },
    {
      "start": 270.603,
      "text": "Besides, possibly i could care less illuminating in so many ways."
    },
    {
      "start": 275.541,
      "text": "I have to."
    },
    {
      "start": 276.042,
      "text": "I have two things i want to ask you about that, don."
    },
    {
      "start": 252.885,
      "text": "First, the great replacement theory as you bring it up, you know, he has tweeted a tweet."
    },
    {
      "start": 285.118,
      "text": "He shared, increasing illegals boost dem voting power, causing them to recruit more."
    },
    {
      "start": 289.989,
      "text": "If dems win president, house and senate, they'll grant citizenship to all legals and america will become a permanent one party deep socialist state."
    },
    {
      "start": 408.408,
      "text": "Right."
    },
    {
      "start": 296.763,
      "text": "He has gone there directly."
    },
    {
      "start": 299.832,
      "text": "How much does he stand by these ideas?"
    },
    {
      "start": 302.502,
      "text": "Well, he didn't quite seem to understand that he did originally do that with jewish people as sort of a great replacement for everything that he did with jewish people."
    },
    {
      "start": 308.975,
      "text": "And he got in trouble when he had to go to auschwitz and answer questions and apologize and go with ben shapiro."
    },
    {
      "start": 316.082,
      "text": "But he doesn't understand that that sort of rhetoric that he talks about the great replacement theory and and migrant invasion, that's what radicalized shooters use in their manifestos, those exact words, the people who go and shoot up people, whether they be latino, people who live in texas or black people who are in a supermarket in buffalo or jewish people who are who are worshiping."
    },
    {
      "start": 343.976,
      "text": "Those people use the same rhetoric that they are, tropes that they're either racist for latinos or black people or for jewish people."
    },
    {
      "start": 352.585,
      "text": "And i wanted to know if he if he felt any responsibility as someone who has one of the largest social media and information platforms in the world, a quarter billion people."
    },
    {
      "start": 362.028,
      "text": "A quarter million people, i think it's 455 or 500 million users a week."
    },
    {
      "start": 368.267,
      "text": "And it doesn't seem that he feels that he has any responsibility with that because he seemed really averse to facts."
    },
    {
      "start": 376.075,
      "text": "Here's another clip."
    },
    {
      "start": 376.809,
      "text": "You talked about ketamine in this particular instance, something that he has discussed before."
    },
    {
      "start": 382.148,
      "text": "Here is you talk about your ketamine use in depression."
    },
    {
      "start": 387.487,
      "text": "You also have said and the reason i should say like like the reason i mentioned that the academy had a prescription on the platform was because i thought maybe this is something that can help other people."
    },
    {
      "start": 399.465,
      "text": "And that's why i mentioned it."
    },
    {
      "start": 401.1,
      "text": "I'm not a doctor, but i would say if someone has depression issues, they should consider talking to their doctor about ketamine instead of ssri."
    },
    {
      "start": 410.443,
      "text": "Very civil conversation."
    },
    {
      "start": 411.644,
      "text": "But that is where it got very personal."
    },
    {
      "start": 413.513,
      "text": "Well, i got personal, but i didn't put that out there."
    },
    {
      "start": 415.581,
      "text": "He has spoken very freely about his use of those prescription drugs, but about his use of ketamine."
    },
    {
      "start": 422.889,
      "text": "I would not have brought it up if someone actually posted it."
    },
    {
      "start": 424.957,
      "text": "He posted it."
    },
    {
      "start": 425.825,
      "text": "So i would not have brought it up because that's someone's personal information."
    },
    {
      "start": 428.261,
      "text": "So i asked him about it."
    },
    {
      "start": 429.629,
      "text": "Also."
    },
    {
      "start": 430.463,
      "text": "It's no secret his drug use or alleged drug use, i should say, has been extensively written about it by very credible news organizations like the wall street journal."
    },
    {
      "start": 440.273,
      "text": "And i asked him that and he said, listen, i have not read the wall street journal."
    },
    {
      "start": 443.409,
      "text": "I don't read the wall street journal."
    },
    {
      "start": 444.811,
      "text": "If i don't have time to read about everything that people write about me, i would never get anything done."
    },
    {
      "start": 450.416,
      "text": "But also remember, on joe rogan, he took a puff of marijuana, right?"
    },
    {
      "start": 454.62,
      "text": "And he said it was just a joke and i only took one puff."
    },
    {
      "start": 457.023,
      "text": "Elon musk is responsible for satellite."
    },
    {
      "start": 459.859,
      "text": "It's for starlink."
    },
    {
      "start": 460.76,
      "text": "He's responsible for tesla."
    },
    {
      "start": 463.262,
      "text": "He's responsible for a number of different companies on the in the stock market."
    },
    {
      "start": 469.101,
      "text": "And i think that it is important for people to understand his mindset, whether he's using drugs legally or not."
    },
    {
      "start": 479.011,
      "text": "And even even if the ones he has that he's using, that's the most powerful people."
    },
    {
      "start": 483.783,
      "text": "But that's what he's wanted."
    },
    {
      "start": 484.584,
      "text": "But the ones that he's supposed to be, that he is using prescription."
    },
    {
      "start": 488.154,
      "text": "Why is it that he's following the doctors orders under that?"
    },
    {
      "start": 492.558,
      "text": "But yes, he's one of the most consequential people to the planet."
    },
    {
      "start": 495.261,
      "text": "And that was one reason why i was attracted to do that in this conversation, you did talk about trump, and again, given his role as one of the most powerful people in the world running acts, who he is going to support, whether he donates the meeting that he took at mar a lago is a huge consequence in the context of everything that he tweets."
    },
    {
      "start": 512.378,
      "text": "And here's part of the conversation you had with him about trump."
    },
    {
      "start": 517.049,
      "text": "You recently met with donald trump in florida."
    },
    {
      "start": 519.952,
      "text": "What did you guys talk about?"
    },
    {
      "start": 521.254,
      "text": "I was at a breakfast at a friend's place and donald trump came by."
    },
    {
      "start": 525.491,
      "text": "Would you discuss i've i don't of let's just say he did most of the talking."
    },
    {
      "start": 533.733,
      "text": "Did he ask you for money?"
    },
    {
      "start": 535.234,
      "text": "You didn't."
    },
    {
      "start": 535.935,
      "text": "Are you going to loan him money to help pay his legal bills?"
    },
    {
      "start": 539.572,
      "text": "I'm not i'm not paying in exchange legal bills in any way, shape or form."
    },
    {
      "start": 543.242,
      "text": "Do they ask you for a donation?"
    },
    {
      "start": 548.714,
      "text": "You've been."
    },
    {
      "start": 549.448,
      "text": "I'm leaning leading away from biden."
    },
    {
      "start": 510.61,
      "text": "You're leaning the trademark laugh there, which, by the way, in the context of the contract is canceled 24 hours after that interview."
    },
    {
      "start": 560.293,
      "text": "It just shows there was a lot of back and forth and give and take."
    },
    {
      "start": 566.465,
      "text": "What did you take away from his comments on politics?"
    },
    {
      "start": 570.002,
      "text": "Well, he says he's not going to endorse anyone now, but he may do it later."
    },
    {
      "start": 570.503,
      "text": "He says he's not going to give money to anyone now, but he may do it later."
    },
    {
      "start": 579.278,
      "text": "You never know with him."
    },
    {
      "start": 579.946,
      "text": "He may be endorsing someone now on the platform."
    },
    {
      "start": 583.849,
      "text": "But you know what's interesting to me in all of that and all these questions that you know about what people are asking me, what i ask you on what happened, what did i do during the course of that interview?"
    },
    {
      "start": 596.262,
      "text": "I never i never raised my voice."
    },
    {
      "start": 585.518,
      "text": "I told him, you know, i think this is important for people to hear, especially considering how the type of discourse that we're having in the country right now."
    },
    {
      "start": 608.908,
      "text": "And he supposedly says this is a public square for all."
    },
    {
      "start": 612.612,
      "text": "Maybe we're learning that the public square should not be privately owned by someone who doesn't think that there should be any moderation on that platform."
    },
    {
      "start": 620.82,
      "text": "So it was interesting because at the end, i could tell that he was upset and he was uncomfortable."
    },
    {
      "start": 627.393,
      "text": "It was tense."
    },
    {
      "start": 628.027,
      "text": "And i said, listen, i'm not trying to make you uncomfortable."
    },
    {
      "start": 630.563,
      "text": "As a matter of fact, i would go and have a beer and hash these things out."
    },
    {
      "start": 634.1,
      "text": "You and i do not disagree."
    },
    {
      "start": 635.701,
      "text": "You and i do not agree on much, but i will fight for your right to be able to say what you want to say."
    },
    {
      "start": 643.809,
      "text": "That's what freedom of speech means to me."
    },
    {
      "start": 646.178,
      "text": "And 24 hours later than 24 hours later, he's freedom of speech does not mean, i guess, the same thing to him as it does to me."
    }
  ],
  category_name: "Politics",
  video_date: "2024-03-13",
  video_name: "Don Lemon speaks out after Elon Musk cancelled his show on X",
  video_thumbnail : "https://i.ytimg.com/vi/pgVZnVTKqMw/hqdefault.jpg?sqp=-oaymwEbCKgBEF5IVfKriqkDDggBFQAAiEIYAXABwAEG\\u0026rs=AOn4CLCt-mOsWO1EQ4iRQiBnoCrAg1G3Ww"
}

// 페이지가 로드된 후에 이미지 태그의 src 속성에 변수 값을 할당
window.onload = function() {
  document.getElementById("now-video-img").src = now_video.video_thumbnail;
  document.getElementById("video-name").innerText = now_video.video_name;
  document.getElementById("sentence-count").innerText = `1 / ${now_video.sentenceList.length}`;
  document.getElementById("last-sentence").innerText = now_video.sentenceList[1].text;
};


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

const register = () => {
  router.push("/register");
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
