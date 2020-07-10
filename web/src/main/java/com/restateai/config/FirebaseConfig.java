package com.restateai.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class FirebaseConfig {

    @Value("${firebase.database-url}")
    private String firebaseUrl;

    @Primary
    @Bean
    public void firebaseInit() throws IOException {
        InputStream serviceAccount = new ClassPathResource("firebase-key.json").getInputStream();
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl(firebaseUrl)
                .build();
        FirebaseApp.initializeApp(options);
    }

}
