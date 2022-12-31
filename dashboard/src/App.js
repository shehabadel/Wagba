import logo from "./logo.svg";
import "./App.css";
import { Container, Row, Col } from "reactstrap";
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
import Order from "./components/Order";
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
  }, [usersOrders]);
  return (
    <div className="App">
      <h1>Wagba</h1>
      <Container>
        <Row>
          {usersOrders.map((order) => (
            <Col sm={4}>
              <Order
                deliveryAddress={order.deliveryAddress}
                orderDate={order.orderDate}
                orderItems={order.orderItems}
                orderStatus={order.orderStatus}
                orderGate={order.orderGate}
                orderTotalPrice={order.orderTotalPrice}
                orderID={order.orderID}
              ></Order>
            </Col>
          ))}
        </Row>
      </Container>
    </div>
  );
}

export default App;

/**
 *       <FirestoreProvider sdk={firestoreInstance}>
    </FirestoreProvider>
 */
