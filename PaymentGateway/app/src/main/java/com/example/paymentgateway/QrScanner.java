package com.example.paymentgateway;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class QrScanner extends AppCompatActivity {

    Button scanBtn;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_scanner);

        scanBtn = findViewById(R.id.button2);
        tv = findViewById(R.id.textView2);

        scanBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        IntentIntegrator intentIntegrator = new IntentIntegrator(QrScanner.this);
                        intentIntegrator.setOrientationLocked(true);
                        intentIntegrator.setPrompt("Scan a Ticket");
                        intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                        intentIntegrator.initiateScan();
                    }
                }
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(intentResult != null) {
            String contents = intentResult.getContents();
            if(contents != null) {
                tv.setText(intentResult.getContents());
            }else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }

    }
}