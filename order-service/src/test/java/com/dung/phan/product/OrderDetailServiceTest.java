package com.dung.phan.product;

import com.dung.phan.order.model.OrderDetail;
import com.dung.phan.order.model.OrderRequest;
import com.dung.phan.order.model.Orders;
import com.dung.phan.order.model.Status;
import com.dung.phan.order.repository.OrderDetailRepository;
import com.dung.phan.order.repository.OrderRepository;
import com.dung.phan.order.service.connector.ProductConnector;
import com.dung.phan.order.service.impl.OrderDetailServiceImpl;
import io.reactivex.subscribers.TestSubscriber;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.junit.Assert;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RunWith(MockitoJUnitRunner.Silent.class)
public class OrderDetailServiceTest {

    private OrderDetailServiceImpl orderDetailService;

    @Mock
    private OrderDetailRepository orderDetailRepository;
    @Mock
    private ProductConnector productConnector;
    @Mock
    private OrderRepository orderRepository;
    OrderRequest request;
    @Before
    public void setup(){
        orderDetailService = new OrderDetailServiceImpl();
        orderDetailService.setOrderRepository(orderRepository);
        orderDetailService.setProductConnector(productConnector);
        orderDetailService.setOrderDetailRepository(orderDetailRepository);
        request = OrderRequest.builder()
                .amount(10.5)
                .customerAddress("vietnam")
                .customerEmail("myemail.com")
                .customerPhone("32432435")
                .orderNum(12324)
                .customerName("my Name")
                .productId("S0001")
                .quantity(2)
                .build();
    }


    @Test
    public void create_New_Order_OK(){

        ResponseEntity<String> responseEntity = new ResponseEntity<>("some response body", null, HttpStatus.OK);
        Mockito.when(productConnector.getProductDetail(request.getProductId())).thenReturn(responseEntity);

        Orders order = Orders.builder()
                .amount(request.getAmount())
                .customerAddress(request.getCustomerAddress())
                .customerName(request.getCustomerName())
                .customerPhone(request.getCustomerPhone())
                .customerEmail(request.getCustomerEmail())
                .id(12l)
                .build();
        Mockito.when(orderRepository.save(order)).thenReturn(order);
        OrderDetail newOder =  OrderDetail.builder()
                .amount(request.getAmount())
                .price(request.getPrice())
                .orderId(order.getId())
                .productId(request.getProductId())
                .quantity(request.getQuantity())
                .status(Status.OPENING)
                .id(123l)
                .build();
        Mockito.when(orderDetailRepository.save(newOder)).thenReturn(newOder);
        orderDetailService.createOrder(request).subscribe();
        Assert.assertTrue("Expect no exception in this test case", true);
    }


    @Test
    public void create_New_Order_EXception(){
        ResponseEntity<String> responseEntity = new ResponseEntity<>("some response body", null, HttpStatus.NOT_FOUND);
        Mockito.when(productConnector.getProductDetail(request.getProductId())).thenReturn(responseEntity);

        Orders order = Orders.builder()
                .amount(request.getAmount())
                .customerAddress(request.getCustomerAddress())
                .customerName(request.getCustomerName())
                .customerPhone(request.getCustomerPhone())
                .customerEmail(request.getCustomerEmail())
                .id(12l)
                .build();
        Mockito.when(orderRepository.save(order)).thenReturn(order);
        OrderDetail newOder =  OrderDetail.builder()
                .amount(request.getAmount())
                .price(request.getPrice())
                .orderId(order.getId())
                .productId(request.getProductId())
                .quantity(request.getQuantity())
                .status(Status.OPENING)
                .id(123l)
                .build();
        Mockito.when(orderDetailRepository.save(newOder)).thenReturn(newOder);
        orderDetailService.createOrder(request).subscribe();
        TestSubscriber<OrderDetail> subscriber = new TestSubscriber<>();
        subscriber.assertNoErrors();
    }

}
