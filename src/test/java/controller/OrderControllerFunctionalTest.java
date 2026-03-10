package controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class OrderControllerFunctionalTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @MockBean
    private PaymentService paymentService;

    private List<Order> orders;
    private Payment payment;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();

        Product product = new Product();
        product.setProductId("ebb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(2);
        products.add(product);

        orders = new ArrayList<>();

        Order order1 = new Order(
                "13652556-012a-4c07-b546-54eb1396d79b",
                products,
                1708560000L,
                "Safira Sudrajat"
        );
        orders.add(order1);

        Order order2 = new Order(
                "7f9e15bb-4b15-42f4-aebc-c3af385fb078",
                products,
                1708570000L,
                "Safira Sudrajat"
        );
        orders.add(order2);

        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        payment = new Payment(order1, "Voucher Code", paymentData);
    }

    @Test
    void testCreateOrderPage() throws Exception {
        mockMvc.perform(get("/order/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("order-create"));
    }

    @Test
    void testPostCreateOrder() throws Exception {
        mockMvc.perform(post("/order/create")
                        .param("author", "Safira Sudrajat")
                        .param("productName", "Sampo Cap Bambang")
                        .param("productQuantity", "2"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/order/history"));
    }

    @Test
    void testHistoryFormPage() throws Exception {
        mockMvc.perform(get("/order/history"))
                .andExpect(status().isOk())
                .andExpect(view().name("order-history-form"));
    }

    @Test
    void testPostOrderHistory() throws Exception {
        doReturn(orders).when(orderService).findAllByAuthor("Safira Sudrajat");

        mockMvc.perform(post("/order/history")
                        .param("author", "Safira Sudrajat"))
                .andExpect(status().isOk())
                .andExpect(view().name("order-history-list"))
                .andExpect(model().attributeExists("orders"));
    }

    @Test
    void testPayOrderPage() throws Exception {
        doReturn(orders.get(0)).when(orderService).findById(orders.get(0).getId());

        mockMvc.perform(get("/order/pay/" + orders.get(0).getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("order-pay"))
                .andExpect(model().attributeExists("order"));
    }

    @Test
    void testPostPayOrderVoucher() throws Exception {
        doReturn(orders.get(0)).when(orderService).findById(orders.get(0).getId());
        doReturn(payment).when(paymentService).addPayment(eq(orders.get(0)), eq("Voucher Code"), any(Map.class));

        mockMvc.perform(post("/order/pay/" + orders.get(0).getId())
                        .param("method", "Voucher Code")
                        .param("voucherCode", "ESHOP1234ABC5678"))
                .andExpect(status().isOk())
                .andExpect(view().name("payment-result"))
                .andExpect(model().attributeExists("payment"));
    }

    @Test
    void testPostPayOrderBankTransfer() throws Exception {
        doReturn(orders.get(0)).when(orderService).findById(orders.get(0).getId());
        doReturn(payment).when(paymentService).addPayment(eq(orders.get(0)), eq("Bank Transfer"), any(Map.class));

        mockMvc.perform(post("/order/pay/" + orders.get(0).getId())
                        .param("method", "Bank Transfer")
                        .param("bankName", "BCA")
                        .param("referenceCode", "TRF-123456"))
                .andExpect(status().isOk())
                .andExpect(view().name("payment-result"))
                .andExpect(model().attributeExists("payment"));
    }
}