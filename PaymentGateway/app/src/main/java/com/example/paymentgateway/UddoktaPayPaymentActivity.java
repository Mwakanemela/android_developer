package com.example.paymentgateway;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.paymentgateway.databinding.ActivityUddoktaPayPaymentBinding;
import com.help5g.uddoktapaysdk.UddoktaPay;

import java.util.HashMap;
import java.util.Map;

public class UddoktaPayPaymentActivity extends AppCompatActivity {

    private ActivityUddoktaPayPaymentBinding binding;
    // Constants for payment
    // Constants for payment
    private static final String API_KEY = "982d381360a69d419689740d9f2e26ce36fb7a50";
    private static final String CHECKOUT_URL = "https://sandbox.uddoktapay.com/api/checkout-v2";
    private static final String VERIFY_PAYMENT_URL = "https://sandbox.uddoktapay.com/api/verify-payment";
    private static final String REDIRECT_URL = "https://uddoktapay.com";
    private static final String CANCEL_URL = "https://uddoktapay.com";

    // Instance variables to store payment information
    private String storedFullName;
    private String storedEmail;
    private String storedAmount;
    private String storedInvoiceId;
    private String storedPaymentMethod;
    private String storedSenderNumber;
    private String storedTransactionId;
    private String storedDate;
    private String storedFee;
    private String storedChargedAmount;

    private String storedMetaKey1;
    private String storedMetaValue1;

    private String storedMetaKey2;
    private String storedMetaValue2;

    private String storedMetaKey3;
    private String storedMetaValue3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityUddoktaPayPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.payBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String fn = "Mwaka";
                String em = "email@gmail.com";
                String am = "200";

                // Set your metadata values in the map
                Map<String, String> metadataMap = new HashMap<>();
                metadataMap.put("CustomMetaData1", "Meta Value 1");
                metadataMap.put("CustomMetaData2", "Meta Value 2");
                metadataMap.put("CustomMetaData3", "Meta Value 3");

                UddoktaPay.PaymentCallback paymentCallback = new UddoktaPay.PaymentCallback() {
                    @Override
                    public void onPaymentStatus(String status, String fullName, String email, String amount, String invoiceId,
                                                String paymentMethod, String senderNumber, String transactionId,
                                                String date, Map<String, String> metadataValues, String fee,String chargeAmount) {
                        Log.d("Pay", "payment call back");
                        // Callback method triggered when the payment status is received from the payment gateway.
                        // It provides information about the payment transaction.
                        storedFullName = "Mwaka";
                        storedEmail = "email@gmail.com";
                        storedAmount = "200";
                        storedInvoiceId = invoiceId;
                        storedPaymentMethod = paymentMethod;
                        storedSenderNumber = senderNumber;
                        storedTransactionId = transactionId;
                        storedDate = date;
                        storedFee = fee;
                        storedChargedAmount = chargeAmount;

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // Clear previous metadata values to avoid duplication
                                storedMetaKey1 = null;
                                storedMetaValue1 = null;
                                storedMetaKey2 = null;
                                storedMetaValue2 = null;
                                storedMetaKey3 = null;
                                storedMetaValue3 = null;

                                // Iterate through the metadata map and store the key-value pairs
                                for (Map.Entry<String, String> entry : metadataValues.entrySet()) {
                                    String metadataKey = entry.getKey();
                                    String metadataValue = entry.getValue();

                                    if ("CustomMetaData1".equals(metadataKey)) {
                                        storedMetaKey1 = metadataKey;
                                        storedMetaValue1 = metadataValue;
                                    } else if ("CustomMetaData2".equals(metadataKey)) {
                                        storedMetaKey2 = metadataKey;
                                        storedMetaValue2 = metadataValue;
                                    } else if ("CustomMetaData3".equals(metadataKey)) {
                                        storedMetaKey3 = metadataKey;
                                        storedMetaValue3 = metadataValue;
                                    }
                                }

                                Log.d("Pay", "payyy");
                                // Update UI based on payment status
                                if ("COMPLETED".equals(status)) {
                                    // Handle payment completed case

                                    binding.payBtn.setVisibility(View.GONE);
                                    binding.webView.setVisibility(View.VISIBLE);
                                    Toast.makeText(UddoktaPayPaymentActivity.this, "Pay successful", Toast.LENGTH_LONG).show();
                                } else if ("PENDING".equals(status)) {
                                    // Handle payment pending case
//                                    binding.payBtn.setVisibility(View.GONE);
//                                    binding.webView.setVisibility(View.VISIBLE);
                                    binding.payBtn.setVisibility(View.VISIBLE);
                                    binding.webView.setVisibility(View.GONE);
                                    Toast.makeText(UddoktaPayPaymentActivity.this, "Pay pending", Toast.LENGTH_LONG).show();
                                } else if ("ERROR".equals(status)) {
                                    // Handle payment error case
                                    Toast.makeText(UddoktaPayPaymentActivity.this, "Error", Toast.LENGTH_LONG).show();

                                }
                            }
                        });
                    }
                };

                UddoktaPay uddoktapay = new UddoktaPay(binding.webView, paymentCallback);
                uddoktapay.loadPaymentForm(API_KEY, fn, em, am, CHECKOUT_URL, VERIFY_PAYMENT_URL, REDIRECT_URL, CANCEL_URL, metadataMap);
            }
        });

    }
}