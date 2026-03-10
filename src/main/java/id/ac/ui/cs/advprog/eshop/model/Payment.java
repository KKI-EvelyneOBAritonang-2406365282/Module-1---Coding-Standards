package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class Payment {
    private final String id;
    private final String method;
    private String status;
    private final Map<String, String> paymentData;
    private final Order order;

    public Payment(Order order, String method, Map<String, String> paymentData) {
        if (order == null) {
            throw new IllegalArgumentException();
        }
        if (method == null || method.isBlank()) {
            throw new IllegalArgumentException();
        }

        this.id = UUID.randomUUID().toString();
        this.order = order;
        this.method = method;
        this.paymentData = paymentData == null ? new HashMap<>() : new HashMap<>(paymentData);
        this.status = determineStatus();
    }

    public void setStatus(String status) {
        if (!"SUCCESS".equals(status) && !"REJECTED".equals(status)) {
            throw new IllegalArgumentException();
        }
        this.status = status; // this is after fixing, but my files was recently re commited due to accident
    }

    private String determineStatus() {
        if ("Voucher Code".equals(method)) {
            return isVoucherValid() ? "SUCCESS" : "REJECTED";
        }

        if ("Bank Transfer".equals(method)) {
            return isFilled("bankName") && isFilled("referenceCode") ? "SUCCESS" : "REJECTED";
        }

        if ("Cash on Delivery".equals(method)) {
            return isFilled("address") && isFilled("deliveryFee") ? "SUCCESS" : "REJECTED";
        }

        throw new IllegalArgumentException();
    }

    private boolean isVoucherValid() {
        String voucherCode = paymentData.get("voucherCode");

        if (voucherCode == null) {
            return false;
        }

        if (voucherCode.length() != 16) {
            return false;
        }

        if (!voucherCode.startsWith("ESHOP")) {
            return false;
        }

        int digitCount = 0;
        for (char c : voucherCode.toCharArray()) {
            if (Character.isDigit(c)) {
                digitCount++;
            }
        }

        return digitCount == 8;
    }

    private boolean isFilled(String key) {
        String value = paymentData.get(key);
        return value != null && !value.isBlank();
    }
}