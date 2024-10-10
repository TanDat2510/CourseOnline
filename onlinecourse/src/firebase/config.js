import { initializeApp } from "firebase/app";
import { getFirestore } from "firebase/firestore";
import { getAuth, FacebookAuthProvider } from 'firebase/auth';
import firebase from "firebase/compat/app";


// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebaseConfig = {
  apiKey: "AIzaSyDx9jU8QYtYLaM0LXaiSXaJMuED02pP-V4",
  authDomain: "chat-app-e52a0.firebaseapp.com",
  projectId: "chat-app-e52a0",
  storageBucket: "chat-app-e52a0.appspot.com",
  messagingSenderId: "66925389689",
  appId: "1:66925389689:web:c28d262ff15ea1a15a31a8",
  measurementId: "G-95VPNHJZ85"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);

const db = getFirestore(app);
const auth = getAuth(app);
export { db, auth, app }
export const fbProvider = new FacebookAuthProvider();
fbProvider.addScope('public_profile');
fbProvider.addScope('email');
export default firebase;