package com.group8.config;

import com.group8.pojo.Invoice;
import com.group8.service.InvoiceService;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.web.socket.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyWebSocketHandler extends TextWebSocketHandler {

    private final InvoiceService invoiceService;

    @Autowired
    public MyWebSocketHandler(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        System.out.println("Received message: " + payload);

        // Giả sử payload là requestId
        String requestId = payload;

        // Kiểm tra requestId qua InvoiceService
        Invoice invoice = invoiceService.checkrequestId(requestId);
        // - Đạt được: Đã có thể handle được transaction thành công và timeout
        // - Hạn chế: Chưa giải quyết được việc hủy transaction khi chưa hết time out
        // - Hướng giải quyết: thay đổi status (chuyển sang dạng ENUM thay vì là bit'1'/bit'0') để bắt được trạng thái Cancle, Pending, Completed
        // - Tình trạng: Chưa khắc phục.
        if (invoice.getTransactionId() != null) {
            session.sendMessage(new TextMessage("Transaction Sucess"));
        } else {
            session.sendMessage(new TextMessage("Transaction Fail"));
        }
    }
}
