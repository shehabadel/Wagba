import logo from "./logo.svg";
import "./App.css";
import { doc, getFirestore } from "firebase/firestore";
import { useEffect, useState } from "react";
import {
  FirebaseAppProvider,
  FirestoreProvider,
  useFirestoreDocData,
  useFirestore,
  useFirebaseApp,
} from "reactfire";
import { db } from "./database/database";
import { onValue, ref } from "firebase/database";
function App() {
  //const firestoreInstance = getFirestore(useFirebaseApp());
  const [usersOrders, setUsersOrders] = useState([]);
  useEffect(() => {
    const query = ref(db, "users");
    var currentOrders = [];
    return onValue(query, (snapshot) => {
      snapshot.forEach((user) => {
        currentOrders.push(user.child("order").toJSON());
      });
      setUsersOrders(currentOrders);
    });
  }, []);
  return (
    <div className="App">
      <h1>Wagba</h1>
      {usersOrders.map((order) => (
        <p>{JSON.stringify(order)}</p>
      ))}
    </div>
  );
}

export default App;

/**
 *       <FirestoreProvider sdk={firestoreInstance}>
    </FirestoreProvider>
 */
