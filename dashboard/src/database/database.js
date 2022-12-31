//require("dotenv").config();
import { initializeApp } from "firebase/app";
import { getDatabase } from "firebase/database";

const firebaseConfig = {
  apiKey: "AIzaSyCiESwLvPu73T3mCrLTLaD7YjCjFDtdUhE",
  authDomain: "wagba-359ed.firebaseapp.com",
  databaseURL: "https://wagba-359ed-default-rtdb.firebaseio.com",
  projectId: "wagba-359ed",
  storageBucket: "wagba-359ed.appspot.com",
  messagingSenderId: "45672822071",
  appId: "1:45672822071:web:e9576affd6a47de1cd16b2",
};

const app = initializeApp(firebaseConfig);
export const db = getDatabase(app);
