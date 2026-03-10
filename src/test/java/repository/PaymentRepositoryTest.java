package repository;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class PaymentRepositoryTest {

    private PaymentRepository paymentRepository;
    private Payment payment1;
    private Payment payment2;

    @BeforeEach
    void setUp() {
        paymentRepository = new PaymentRepository();

        List<Product> products = new ArrayList<>();
        Product product = new Product();
        product.setProductId("ebb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(2);
        products.add(product);

        Order order1 = new Order(
                "13652556-012a-4c07-b546-54eb1396d79b",
                products,
                1708560000L,
                "Safira Sudrajat"
        );

        Order order2 = new Order(
                "7f9e15bb-4b15-42f4-aebc-c3af385fb078",
                products,
                1708570000L,
                "Bambang Sudrajat"
        );

        Map<String, String> voucherData = new HashMap<>();
        voucherData.put("voucherCode", "ESHOP1234ABC5678");

        Map<String, String> bankData = new HashMap<>();
        bankData.put("bankName", "BCA");
        bankData.put("referenceCode", "TRF-123456");

        payment1 = new Payment(order1, "Voucher Code", voucherData);
        payment2 = new Payment(order2, "Bank Transfer", bankData);
    }

    @Test
    void testSaveAndGetPayment() {
        paymentRepository.save(payment1);

        Payment result = paymentRepository.getPayment(payment1.getId());

        assertNotNull(result);
        assertEquals(payment1.getId(), result.getId());
        assertEquals(payment1.getMethod(), result.getMethod());
    }

    @Test
    void testGetPaymentNotFound() {
        Payment result = paymentRepository.getPayment("not-found");
        assertNull(result);
    }

    @Test
    void testGetAllPayments() {
        paymentRepository.save(payment1);
        paymentRepository.save(payment2);

        List<Payment> results = paymentRepository.getAllPayments();

        assertEquals(2, results.size());
    }
}
