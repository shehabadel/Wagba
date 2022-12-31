import {
  Button,
  Card,
  CardBody,
  CardFooter,
  CardTitle,
  Container,
} from "reactstrap";
import { db } from "../database/database";
import { update, ref, set, push } from "firebase/database";
const Order = (props) => {
  const {
    user,
    deliveryAddress,
    orderDate,
    orderItems,
    orderStatus,
    orderGate,
    orderTotalPrice,
    orderID,
  } = props;
  /**
   * Function to update the state of the Order
   *
   */
  const updateState = (state) => {
    const updateRef = ref(db, `users/${user}/order`);
    const prevOrderRef = ref(db, `users/${user}/previousOrders`);
    if (state === "COMPLETED") {
      push(prevOrderRef, {
        deliveryAddress: deliveryAddress,
        orderDate: orderDate,
        orderItems: orderItems,
        orderStatus: "COMPLETED",
        orderGate: orderGate,
        orderTotalPrice: orderTotalPrice,
      });
    } else {
      update(updateRef, {
        orderStatus: state,
      });
    }
  };
  return (
    <Card>
      <CardTitle>
        {orderID ? <h2>{orderID}</h2> : <h2>Order</h2>}
        <p className="text-center">
          <b>UserID: {user}</b>
        </p>
      </CardTitle>
      <hr></hr>
      <CardBody>
        <div>
          <span>
            <b>Delivery Address: </b>
          </span>
          <span>{deliveryAddress}</span>
          <br></br>
          <span>
            <b>Order Date: </b>
          </span>
          <span>{orderDate}</span>
          <br></br>
          <span>
            <b>Order Gate: </b>
          </span>
          <span>{orderGate}</span>
          <br></br>
          <span>
            <b>Order Total Price: </b>
          </span>
          <span>{orderTotalPrice} EGP</span>
          <br></br>
        </div>
        <br></br>
      </CardBody>
      <CardFooter>
        <h5>{orderStatus}</h5>
        <div className="d-flex flex-row justify-content-center">
          <Button className="mx-2" onClick={() => updateState("CONFIRMED")}>
            Confirmed
          </Button>
          <Button className="mx-2" onClick={() => updateState("PROCESSING")}>
            Processing
          </Button>
          <Button className="mx-2" onClick={() => updateState("DELIVERY")}>
            Delivery
          </Button>
          <Button className="mx-2" onClick={() => updateState("COMPLETED")}>
            Completed
          </Button>
        </div>
      </CardFooter>
    </Card>
  );
};
export default Order;
