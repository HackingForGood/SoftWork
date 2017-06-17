package com.softwork.rasp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.softwork.rasp.model.Driver;
import com.softwork.rasp.model.User;

public class RegistrationActivity extends AppCompatActivity {
    private String email;
    private User.Role role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        final Switch roleSwitch = (Switch) findViewById(R.id.roleSwitch);
        roleSwitch.setTextOn(getResources().getString(R.string.owner_registration));
        roleSwitch.setTextOff(getResources().getString(R.string.driver_registration));

        roleSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //owner reg
                    role = User.Role.Owner;
                    openPage();
                } else {
                    //driver reg
                    role = User.Role.Driver;
                    openPage();
                }
            }
        });

        Bundle extras = getIntent().getExtras();
        if (extras == null && savedInstanceState == null)
            return;
        role = (User.Role) extras.get(Constants.ROLE);

        email = extras.getString(Constants.EMAIL);
        openPage();

        //done button onclick
        Button doneButton = (Button) findViewById(R.id.doneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                done();
            }
        });
    }

    private void openPage() {
        if (role == null || role == User.Role.Driver) {
            //open driver page
            openFragment(new DriverRegistrationFragment());
        } else {
            //open owner page
            openFragment(new OwnerRegistrationFragment());
        }
    }

    private void openFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }

    private void done() {
        final String name = ((EditText) findViewById(R.id.nameEditText)).getText().toString();
        String password = ((EditText) findViewById(R.id.password)).getText().toString();
        String confirmPassword = ((EditText) findViewById(R.id.confirmPassword)).getText().toString();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        if (!password.equals(confirmPassword)) {
            //display error message
            return;
        }

        if (role == null || role == User.Role.Driver) {
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                FirebaseAuthException e = (FirebaseAuthException) task.getException();
                                Toast.makeText(RegistrationActivity.this, "Failed Registration: " +
                                        e.getMessage(), Toast.LENGTH_SHORT).show();
                                return;
                            }
                            //else it's successful
                            Toast.makeText(RegistrationActivity.this, "User added", Toast.LENGTH_SHORT);
                            Intent intent = new Intent(RegistrationActivity.this, MapsActivity.class);
                            startActivity(intent);
                            Database.writeUser(new Driver(name, email));
                        }
                    });
        } else {

        }

    }
}
