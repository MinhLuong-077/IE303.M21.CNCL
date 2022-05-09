package com.example.ewallet.utils;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DAOUsers {
    private DatabaseReference databaseReference;

    public DAOUsers() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference("Users");
    }

    public Task<Void> add(Users users){
        return databaseReference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(users);
    }

//    public Task<Void> delete(Users users){
//        return 0;
//    }

}
