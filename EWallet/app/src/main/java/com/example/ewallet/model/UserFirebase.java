package com.example.ewallet.model;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserFirebase {
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    public String getUid() {
        return user.getUid();
    }
}
