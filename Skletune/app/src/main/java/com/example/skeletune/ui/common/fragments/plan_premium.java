package com.example.skeletune.ui.common.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.skletune.R;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;
import com.google.android.material.card.MaterialCardView;

import org.json.JSONObject;

public class plan_premium extends Fragment {

    private PaymentsClient paymentsClient;
    private MaterialCardView btnPay;

    // SOLUCIÓN AL ROJO: Launcher moderno para recibir el resultado del pago
    private final ActivityResultLauncher<Intent> paymentLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result -> {
                        if (result.getResultCode() == Activity.RESULT_OK) {
                            PaymentData paymentData = PaymentData.getFromIntent(result.getData());
                            if (paymentData != null) {
                                Toast.makeText(getContext(), "¡Pago Exitoso! Bienvenido a Premium", Toast.LENGTH_LONG).show();
                            }
                        } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                            Toast.makeText(getContext(), "Pago cancelado", Toast.LENGTH_SHORT).show();
                        } else if (result.getResultCode() == AutoResolveHelper.RESULT_ERROR) {
                            Toast.makeText(getContext(), "Error en el servidor de pagos", Toast.LENGTH_SHORT).show();
                        }
                    });

    public plan_premium() { /* Constructor vacío requerido */ }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_plan_premium, container, false);

        // Configuración del cliente (Ambiente de TEST)
        paymentsClient = Wallet.getPaymentsClient(
                requireActivity(),
                new Wallet.WalletOptions.Builder()
                        .setEnvironment(WalletConstants.ENVIRONMENT_TEST)
                        .build());

        btnPay = view.findViewById(R.id.btnGooglePay);
        btnPay.setOnClickListener(v -> iniciarPagoGooglePay());

        return view;
    }

    private void iniciarPagoGooglePay() {
        try {
            JSONObject paymentDataRequestJson = new JSONObject(getJsonConfig());
            PaymentDataRequest request = PaymentDataRequest.fromJson(paymentDataRequestJson.toString());

            if (request != null) {
                // Preparamos el Intent de Google Pay
                paymentsClient.loadPaymentData(request).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        PaymentData paymentData = task.getResult();
                        // Esta es la forma oficial de lanzar el selector de Google Pay
                        // Asegúrate de que paymentLauncher sea un 'ActivityResultLauncher<PaymentDataRequest>'
                        // Sustituye la línea 81 por esta:
                        AutoResolveHelper.resolveTask(task, getActivity(), 9001);
// Nota: 9001 es un número cualquiera (requestCode).
                    }
                     else {
                        AutoResolveHelper.resolveTask(task, requireActivity(), 991);
                        // Nota: Si el task falla aquí, algunos SDKs aún requieren resolveTask
                    }
                });
            }
        } catch (Exception e) {
            Log.e("GooglePay", "Error al iniciar: " + e.getMessage());
        }
    }

    private String getJsonConfig() {
        return "{"
                + "\"apiVersion\": 2,"
                + "\"apiVersionMinor\": 0,"
                + "\"allowedPaymentMethods\": [{"
                + "\"type\": \"CARD\","
                + "\"parameters\": {"
                + "\"allowedAuthMethods\": [\"PAN_ONLY\", \"CRYPTOGRAM_3DS\"],"
                + "\"allowedCardNetworks\": [\"VISA\", \"MASTERCARD\"]"
                + "},"
                + "\"tokenizationSpecification\": {"
                + "\"type\": \"PAYMENT_GATEWAY\","
                + "\"parameters\": {"
                + "\"gateway\": \"example\","
                + "\"gatewayMerchantId\": \"exampleGatewayMerchantId\""
                + "}"
                + "}"
                + "}],"
                + "\"transactionInfo\": {"
                + "\"totalPriceStatus\": \"FINAL\","
                + "\"totalPrice\": \"149.99\","
                + "\"currencyCode\": \"MXN\""
                + "},"
                + "\"merchantInfo\": {"
                + "\"merchantName\": \"Skeletune Premium\""
                + "}"
                + "}";
    }
}