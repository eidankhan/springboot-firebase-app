package com.firebase.app;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.firebase.app.util.AppHelper;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;

@Service
public class EmployeeService {

	public List<Employee> getAll() {
		System.out.println("UserService.getAll() --> is called");
		Firestore db = FirestoreInstance.getFirestoreInstance();
		ApiFuture<QuerySnapshot> apiFuture = db.collection("employees").get();
		List<Employee> employees = new ArrayList<Employee>();
		try {
			List<QueryDocumentSnapshot> documents = apiFuture.get().getDocuments();
			System.out.println("Iterating documents list -->>");
			for (QueryDocumentSnapshot document : documents) {
				Employee employee = document.toObject(Employee.class);
				System.out.println(employee);
				employees.add(employee);
			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return employees;
	}

	public String save(Employee employee) {
		System.out.println("UserService.save(employee) --> is called");
		// TO CHECK WHETHER DOCUMENT EXISTS OR NOT WITH GIVEN ID
		if(!exists(employee.getId())) {
			Firestore db = FirestoreInstance.getFirestoreInstance();
			ApiFuture<WriteResult> collectionsApiFuture = db.collection("employees").document(employee.getId().toString())
					.set(employee);
			try {
				String updateTime = collectionsApiFuture.get().getUpdateTime().toString();
				System.out.println("UserService.save(employee) --> Update Time:" + updateTime);
				return updateTime;
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		System.out.println("Document with Id "+employee.getId()+" already exists");
		return "Document with Id "+employee.getId()+" already exists";
	}

	public Employee getById(Integer id) {
		System.out.println("UserService.getById(id) --> is called");
		Employee employee = null;
		// TO CHECK WHETHER DOCUMENT EXISTS OR NOT
		if (exists(id)) {
			DocumentSnapshot document = null;
			Firestore db = FirestoreInstance.getFirestoreInstance();
			DocumentReference documentReference = db.collection("employees").document(id.toString());
			ApiFuture<DocumentSnapshot> future = documentReference.get();
			try {
				document = future.get();
				employee = document.toObject(Employee.class);
				return employee;
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		return employee;
	}

	public String deleteById(Integer id) {
		System.out.println("UserService.deleteById(id) --> is called");

		// CHECKER FOR THE EXISTING DOCUMENT
		if (exists(id)) {
			Firestore db = FirestoreInstance.getFirestoreInstance();
			ApiFuture<WriteResult> writeResult = db.collection("employees").document(id.toString()).delete();
			try {
				String updateTime = writeResult.get().getUpdateTime().toString();
				System.out.println("UserService.deleteById(id) --> Update Time:" + updateTime);
				return updateTime;
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
			}
		}
		return "Document with Id " + id + " does not exist";
	}

	public String update(Integer id, Employee employee) {
		System.out.println("UserService.update(id,employee) --> is called");

		// CHECKER FOR THE EXISTING DOCUMENT
		if (exists(id)) {
			Firestore db = FirestoreInstance.getFirestoreInstance();
			ApiFuture<WriteResult> apiFuture = db.collection("employees").document(id.toString())
					.update(AppHelper.convertObjectToMap(employee));
			try {
				String updateTime = apiFuture.get().getUpdateTime().toString();
				System.out.println("UserServiceupdate(id,employee) --> Update Time:" + updateTime);
				return updateTime;
			} catch (InterruptedException | ExecutionException e) {
				System.out.println("Exception Message:" + e.getMessage());
			}
		}
		return "Document with Id " + id + " does not exist";
	}

	private Boolean exists(Integer id) {
		System.out.println("EmployeeService.exists(id) --> is called");
		Firestore db = FirestoreInstance.getFirestoreInstance();
		DocumentReference documentReference = db.collection("employees").document(id.toString());
		DocumentSnapshot document = null;
		try {
			document = documentReference.get().get();
			if (document.exists()) {
				System.out.println("Document with id " + id + " exists");
				return true;
			}
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}

		System.out.println("Document with id " + id + " does not exist");
		return false;
	}

}
