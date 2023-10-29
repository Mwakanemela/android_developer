package com.example.paymentgateway;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QrCode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_code);

        EditText et = findViewById(R.id.editTextText);
        Button btn = findViewById(R.id.button);
        ImageView iv = findViewById(R.id.imageView);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

                try {

                    BitMatrix bitMatrix = multiFormatWriter.encode(et.getText().toString(), BarcodeFormat.QR_CODE, 300, 300);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);

                    iv.setImageBitmap(bitmap);
                }catch (WriterException e) {
                    throw new RuntimeException(e);
                }

            }
        });
    }
}