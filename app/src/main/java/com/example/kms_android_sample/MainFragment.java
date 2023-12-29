package com.example.kms_android_sample;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kms.AWSKMSClient;
import com.amazonaws.services.kms.model.DecryptRequest;
import com.amazonaws.services.kms.model.DecryptResult;
import com.amazonaws.services.kms.model.EncryptRequest;
import com.amazonaws.services.kms.model.EncryptResult;
import com.example.kms_android_sample.databinding.FragmentMainBinding;

import java.nio.ByteBuffer;

public class MainFragment extends Fragment {

    private FragmentMainBinding binding;
    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        this.binding = FragmentMainBinding.inflate(inflater, container, false);

        AWSKMSClient client = new AWSKMSClient(new AWSCredentials() {
            @Override
            public String getAWSAccessKeyId() {
                return "please set accessKey";
            }

            @Override
            public String getAWSSecretKey() {
                return "please set secretKey";
            }
        });
        client.setRegion(Region.getRegion(Regions.AP_NORTHEAST_1.getName()));
        this.binding.btEncrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("encrypt", "start");
                EncryptResult result = client.encrypt(new EncryptRequest()
                                .withKeyId("please set keyId")
                                .withPlaintext(ByteBuffer.wrap(binding.etEncrypt.getText().toString().getBytes())));
                binding.etEncryptResult.setText(new String(result.getCiphertextBlob().array()));
            }
        });

        this.binding.btDecrypt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DecryptResult result = client.decrypt(new DecryptRequest()
                                .withKeyId("please set keyId")
                                .withCiphertextBlob(ByteBuffer.wrap(binding.etDecrypt.getText().toString().getBytes())));
                binding.etDecryptResult.setText(new String(result.getPlaintext().array()));
            }
        });

        return inflater.inflate(R.layout.fragment_main, container, false);
    }

}