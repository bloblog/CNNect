/** @type {import('tailwindcss').Config} */
export default {
  purge: ["./index.html", "./src/**/*.{vue,js,ts,jsx,tsx}"],
  // darkMode: false, // or 'media' or 'class'
  content: [
    "./node_modules/flowbite/**/*.js",

    "node_modules/flowbite-vue/**/*.{js,jsx,ts,tsx,vue}",
    "node_modules/flowbite/**/*.{js,jsx,ts,tsx}",
  ],
  theme: {
    extend: {
      grayscale: {
        50: "50%",
        30: "30%",
        10: "10%",
      },
      colors: {
        "theme-red": "#CC0000",
        "theme-blue": "#006FE8",
        "theme-navy": "#0F1B4F",
        "theme-redbrown": "#80112F",
        "badge-gold" : "#f9d953",
        "badge-silver" : "#c6c3b9",
        "badge-bronze" : "#e58d35",
        "badge-gold-line" : "#f9bf08",
        "badge-silver-line" : "#7f7b70",
        "badge-bronze-line" : "#d86609",
      },
    },
  },
  plugins: [
    require("tailwind-accent-color", "tailwind-scrollbar-hide", "flowbite/plugin"),
  ],
};
