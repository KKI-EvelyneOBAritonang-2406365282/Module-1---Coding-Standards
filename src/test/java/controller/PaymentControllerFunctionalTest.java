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
import id.ac.ui.cs.advprog.eshop.service.PaymentService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class PaymentControllerFunctionalTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentService paymentService;

    private Payment payment;
    private Payment updatedPayment;
    private List<Payment> payments;

    @BeforeEach
    void setUp() {
        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("ebb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(2);
        products.add(product);

        Order order = new Order(
                "13652556-012a-4c07-b546-54eb1396d79b",
                products,
                1708560000L,
                "Safira Sudrajat"
        );

        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        payment = new Payment(order, "Voucher Code", paymentData);

        updatedPayment = new Payment(order, "Voucher Code", paymentData);
        updatedPayment.setStatus("REJECTED");

        payments = new ArrayList<>();
        payments.add(payment);
        payments.add(updatedPayment);
    }

    @Test
    void testPaymentDetailFormPage() throws Exception {
        mockMvc.perform(get("/payment/detail"))
                .andExpect(status().isOk())
                .andExpect(view().name("payment-detail-form"));
    }

    @Test
    void testPaymentDetailPage() throws Exception {
        doReturn(payment).when(paymentService).getPayment(payment.getId());

        mockMvc.perform(get("/payment/detail/" + payment.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("payment-detail"))
                .andExpect(model().attributeExists("payment"));
    }

    @Test
    void testAdminListPage() throws Exception {
        doReturn(payments).when(paymentService).getAllPayments();

        mockMvc.perform(get("/payment/admin/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("payment-admin-list"))
                .andExpect(model().attributeExists("payments"));
    }

    @Test
    void testAdminDetailPage() throws Exception {
        doReturn(payment).when(paymentService).getPayment(payment.getId());

        mockMvc.perform(get("/payment/admin/detail/" + payment.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("payment-admin-detail"))
                .andExpect(model().attributeExists("payment"));
    }

    @Test
    void testAdminSetStatus() throws Exception {
        doReturn(payment).when(paymentService).getPayment(payment.getId());
        doReturn(updatedPayment).when(paymentService).setStatus(payment, "REJECTED");

        mockMvc.perform(post("/payment/admin/set-status/" + payment.getId())
                        .param("status", "REJECTED"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/payment/admin/detail/" + payment.getId()));
    }
}