import { Card, CardBody, CardFooter, CardTitle, Container } from "reactstrap";

const Order = (props) => {
  const {
    deliveryAddress,
    orderDate,
    orderItems,
    orderStatus,
    orderGate,
    orderTotalPrice,
    orderID,
  } = props;
  return (
    <Card>
      <CardTitle>{orderID ? <h2>{orderID}</h2> : <h2>Order</h2>}</CardTitle>
      <CardBody>
        <div>
          <span>Delivery Address: </span>
          <span>{deliveryAddress}</span>
          <br></br>
          <span>Order Date: </span>
          <span>{orderDate}</span>
          <br></br>
          <span>Order Gate:</span>
          <span>{orderGate}</span>
          <br></br>
          <span>Order Total Price:</span>
          <span>{orderTotalPrice} EGP</span>
          <br></br>
          <br></br>
        </div>
      </CardBody>
      <CardFooter>
        <h5>{orderStatus}</h5>
      </CardFooter>
    </Card>
  );
};
export default Order;
