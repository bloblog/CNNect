import { defineStore } from 'pinia';

export const userStore = defineStore(
    'userStore',
    () => {

    },
    { persist: true, strategies: [{ storage: localStorage }] },
);