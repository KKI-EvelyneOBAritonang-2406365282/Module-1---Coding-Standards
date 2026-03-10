package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import id.ac.ui.cs.advprog.eshop.service.OrderService;
import id.ac.ui.cs.advprog.eshop.service.PaymentService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/create")
    public String createOrderPage() {
        return "order-create";
    }

    @PostMapping("/create")
    public String createOrder(
            @RequestParam("author") String author,
            @RequestParam("productName") String productName,
            @RequestParam("productQuantity") Integer productQuantity
    ) {
        Product product = new Product();
        product.setProductId(UUID.randomUUID().toString());
        product.setProductName(productName);
        product.setProductQuantity(productQuantity);

        List<Product> products = new ArrayList<>();
        products.add(product);

        Order order = new Order(
                UUID.randomUUID().toString(),
                products,
                System.currentTimeMillis(),
                author
        );

        orderService.createOrder(order);
        return "redirect:/order/history";
    }

    @GetMapping("/history")
    public String orderHistoryFormPage() {
        return "order-history-form";
    }

    @PostMapping("/history")
    public String orderHistoryListPage(
            @RequestParam("author") String author,
            Model model
    ) {
        List<Order> orders = orderService.findAllByAuthor(author);
        model.addAttribute("orders", orders);
        return "order-history-list";
    }

    @GetMapping("/pay/{orderId}")
    public String payOrderPage(@PathVariable("orderId") String orderId, Model model) {
        Order order = orderService.findById(orderId);
        model.addAttribute("order", order);
        return "order-pay";
    }

    @PostMapping("/pay/{orderId}")
    public String payOrder(
            @PathVariable("orderId") String orderId,
            @RequestParam("method") String method,
            @RequestParam Map<String, String> requestParams,
            Model model
    ) {
        Order order = orderService.findById(orderId);

        Map<String, String> paymentData = new HashMap<>();

        if ("Voucher Code".equals(method)) {
            paymentData.put("voucherCode", requestParams.get("voucherCode"));
        } else if ("Bank Transfer".equals(method)) {
            paymentData.put("bankName", requestParams.get("bankName"));
            paymentData.put("referenceCode", requestParams.get("referenceCode"));
        }

        var payment = paymentService.addPayment(order, method, paymentData);
        model.addAttribute("payment", payment);
        return "payment-result";
    }
}