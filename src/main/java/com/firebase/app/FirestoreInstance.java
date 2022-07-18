package com.firebase.app;

import java.io.FileInputStream;
import java.io.InputStream;

import org.springframework.context.annotation.Configuration;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;

public class FirestoreInstance {

	private static Firestore firestoreDB;

	public static Firestore getFirestoreInstance() {
		System.out.println("FirestoreInstance.getFirestoreInstance() --> is called");
		if (firestoreDB == null) {
			System.out.println("FirestoreInstance.getFirestoreInstance() --> Firestore instance is null");
			try {
				InputStream serviceAccount = new FileInputStream(
						"src/main/resources/static/spring-firebase-app-file.json");
				GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
				FirebaseOptions options = new FirebaseOptions.Builder().setCredentials(credentials).build();
				FirebaseApp.initializeApp(options);
				firestoreDB = FirestoreClient.getFirestore();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return firestoreDB;
	}

}
