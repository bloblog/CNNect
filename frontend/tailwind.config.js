/** @type {import('tailwindcss').Config} */
export default {
  purge: ["./index.html", "./src/**/*.{vue,js,ts,jsx,tsx}"],
  darkMode: false, // or 'media' or 'class'
  content: [
    "./node_modules/flowbite/**/*.js",

    "node_modules/flowbite-vue/**/*.{js,jsx,ts,tsx,vue}",
    "node_modules/flowbite/**/*.{js,jsx,ts,tsx}",
  ],
  theme: {
    extend: {
      grayscale : {
        50 : '50%',
        30 : '30%',
        10 : '10%'
      },
      colors: {
        "theme-red": "#CC0000",
        "theme-blue": "#006FE8",
        "theme-navy": "#0F1B4F",
        "theme-redbrown": "#80112F",
      },
    },
  },
  plugins: [require("flowbite/plugin")],
};
