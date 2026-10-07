package com.example.kalkulatorpodrozymobilna;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText etDystans, etSpalanie, etPasazerowie;
    private RadioButton rbDiesel, rbLPG;
    private Button btnOblicz;
    private TextView tvWynik;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etDystans = findViewById(R.id.etDystans);
        etSpalanie = findViewById(R.id.etSpalanie);
        etPasazerowie = findViewById(R.id.etPasazerowie);
        rbDiesel = findViewById(R.id.rbDiesel);
        rbLPG = findViewById(R.id.rbLPG);
        btnOblicz = findViewById(R.id.btnOblicz);
        tvWynik = findViewById(R.id.tvWynik);

        btnOblicz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obliczKoszt();
            }
        });
    }

    private void obliczKoszt() {
        String strDystans = etDystans.getText().toString().trim();
        String strSpalanie = etSpalanie.getText().toString().trim();
        String strPasazerowie = etPasazerowie.getText().toString().trim();

        if (strDystans.isEmpty() || strSpalanie.isEmpty() || strPasazerowie.isEmpty()) {
            pokazBlad();
            return;
        }

        try {
            double dystans = Double.parseDouble(strDystans);
            double spalanie = Double.parseDouble(strSpalanie);
            int pasazerowie = Integer.parseInt(strPasazerowie);

            double cenaPaliwa = 6.50;
            if (rbDiesel.isChecked()) {
                cenaPaliwa = 6.70;
            } else if (rbLPG.isChecked()) {
                cenaPaliwa = 3.10;
            }

            if (dystans <= 0 || spalanie <= 0) {
                pokazBlad();
                return;
            }

            double iloscPaliwa = (dystans * spalanie) / 100.0;
            double calkowityKoszt = iloscPaliwa * cenaPaliwa;

            double kosztNaOsobe;
            if (pasazerowie <= 0) {
                kosztNaOsobe = calkowityKoszt;
            } else {
                kosztNaOsobe = calkowityKoszt / pasazerowie;
            }

            tvWynik.setTextColor(Color.parseColor("#008000"));
            tvWynik.setText(String.format("Całkowity koszt: %.2f zł\nNa osobę: %.2f zł", calkowityKoszt, kosztNaOsobe));

        } catch (NumberFormatException e) {
            pokazBlad();
        }
    }

    private void pokazBlad() {
        tvWynik.setTextColor(Color.RED);
        tvWynik.setText("Niepoprawne dane wejściowe!");
    }
}