package com.dung.phan.product;

import static org.mockito.ArgumentMatchers.anyObject;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.dung.phan.order.message.AuditProduceMessage;
import com.dung.phan.order.message.Producer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AuditProduceMessageTest {

    private AuditProduceMessage auditProduceMessage;

    @Mock
    private Producer producer;

    @Before
    public void setup() {
        auditProduceMessage = new AuditProduceMessage();
        auditProduceMessage.setProducer(producer);
    }

    @Test
    public void send_message_Ok() {
        String message = "{\"url\":\"/api/v1/orderdetail/create\",\"method\":\"POST\",\"dateTimeStamp\":1592233298671,\"body\":\" \\\"amount\\\": \\\"5000\\\", \\\"price\\\": \\\"6000\\\", \\\"quantity\\\": \\\"2\\\", \\\"order_id\\\": \\\"123\\\", \\\"productId\\\": \\\"16\\\", \\\"customerAddress\\\": \\\"viet nam\\\", \\\"customerEmail\\\": \\\"custemorEmail@mail.com\\\", \\\"customerName\\\": \\\"my name\\\", \\\"customerPhone\\\": \\\"hahah4444a\\\" }\"}";
        auditProduceMessage.sendKafkaMessage(message);
        verify(producer, times(1)).sendMessage(anyObject(),anyString());
    }
}
