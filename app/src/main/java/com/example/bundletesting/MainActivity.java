package com.example.bundletesting;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.play.core.splitinstall.SplitInstallManager;
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory;
import com.google.android.play.core.splitinstall.SplitInstallRequest;

public class MainActivity extends AppCompatActivity {

    SplitInstallManager splitInstallManager;
    Button downloadAdminButton;
    Button accessAdminButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        splitInstallManager= SplitInstallManagerFactory.create(this);
        downloadAdminButton=findViewById(R.id.download_admin_button);
        accessAdminButton=findViewById(R.id.access_admin_button);

        //listener for buttons
        downloadAdminButton.setOnClickListener(v -> {
            getAdminFeature();
        });
        accessAdminButton.setOnClickListener(v -> {
            if(splitInstallManager.getInstalledModules().contains("adminfeature")) {
                Intent intent = new Intent(Intent.ACTION_VIEW)
                        .setClassName(BuildConfig.APPLICATION_ID, "com.example.adminfeature.AdminActivity");
                startActivity(intent);
            }else{
                Toast.makeText(this, "module not exist", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getAdminFeature(){
        // Creates a request to install a module.
        SplitInstallRequest request =
                SplitInstallRequest
                        .newBuilder()
                        .addModule("adminfeature")
                        .build();

        splitInstallManager
                .startInstall(request)
                .addOnSuccessListener(sessionId -> {
                    Toast.makeText(this, "successfully installed.", Toast.LENGTH_SHORT).show();

                })
                .addOnFailureListener(exception -> {
                    Toast.makeText(this, "failure: "+exception.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}