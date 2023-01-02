import "./App.css";
import { Container, Row, Col } from "reactstrap";
import { useEffect, useState } from "react";
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
        if (user.hasChild("order")) {
          currentOrders.push({
            user: user.key,
            orderData: user.child("order").toJSON(),
          });
        }
      });
      setUsersOrders(currentOrders);
    });
  }, [usersOrders]);
  return (
    <div className="App">
      <h1>Wagba Admin Dashboard</h1>
      <Container className="mt-4">
        <Row>
          {usersOrders.length !== 0 ? (
            usersOrders?.map((order) => {
              return (
                <Col sm={6}>
                  <Order
                    user={order?.user}
                    deliveryAddress={order?.orderData?.deliveryAddress}
                    orderDate={order?.orderData?.orderDate}
                    orderItems={order?.orderData?.orderItems}
                    orderStatus={order?.orderData?.orderStatus}
                    orderGate={order?.orderData?.orderGate}
                    orderTotalPrice={order?.orderData?.orderTotalPrice}
                    orderID={order?.orderData?.orderID}
                  ></Order>
                </Col>
              );
            })
          ) : (
            <h2>No orders at the moment!</h2>
          )}
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
