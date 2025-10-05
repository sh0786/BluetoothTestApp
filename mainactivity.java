package com.example.bluetoothtest;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    BluetoothAdapter bluetoothAdapter;
    TextView statusText, pairedDevicesText;
    Button onBtn, offBtn, showBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusText = findViewById(R.id.statusText);
        pairedDevicesText = findViewById(R.id.pairedDevicesText);
        onBtn = findViewById(R.id.onBtn);
        offBtn = findViewById(R.id.offBtn);
        showBtn = findViewById(R.id.showBtn);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null) {
            statusText.setText("Bluetooth not supported");
            onBtn.setEnabled(false);
            offBtn.setEnabled(false);
            showBtn.setEnabled(false);
        } else {
            updateStatus();
        }

        onBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bluetoothAdapter.enable();
                updateStatus();
            }
        });

        offBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bluetoothAdapter.disable();
                updateStatus();
            }
        });

        showBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
                StringBuilder sb = new StringBuilder();
                for (BluetoothDevice device : pairedDevices) {
                    sb.append(device.getName()).append(" - ").append(device.getAddress()).append("\n");
                }
                pairedDevicesText.setText(sb.length() > 0 ? sb.toString() : "No paired devices");
            }
        });
    }

    private void updateStatus() {
        if (bluetoothAdapter.isEnabled()) {
            statusText.setText("Bluetooth is ON");
        } else {
            statusText.setText("Bluetooth is OFF");
        }
    }
}
