package com.example.my1stapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
 import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


//import static com.example.my1stapplication.MainActivity.userID;

public class ConfrimAddMaterial extends AppCompatActivity {
    DatabaseReference postsref, usersref;
    EditText username,bankname, IBAN,address, phone;
    DatabaseReference db;
    Button confirm, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confrim_add_material);

usersref=FirebaseDatabase.getInstance().getReference("Users");

        postsref = FirebaseDatabase.getInstance().getReference("posts");



        final String materialname= getIntent().getStringExtra("materialname");
        final String coursename= getIntent().getStringExtra("coursename");
        final String uniname= getIntent().getStringExtra("uniname");
        final String materialtype= getIntent().getStringExtra("materialtype");
        final String description= getIntent().getStringExtra("description");
        final String price= getIntent().getStringExtra("price");
        final String id = getIntent().getStringExtra("userID");
        final String URL = getIntent().getStringExtra("URL");

        username = (EditText) findViewById(R.id.editText);
        bankname = (EditText) findViewById(R.id.editText2);
        IBAN = (EditText) findViewById(R.id.editText3);
        address = (EditText) findViewById(R.id.editText4);
        phone = (EditText) findViewById(R.id.editText5);
        confirm=(Button)findViewById(R.id.confirm);
        cancel=(Button)findViewById(R.id.button2);
        final String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        db = FirebaseDatabase.getInstance().getReference("Users").child(userID);
//String userID= FirebaseAuth.getInstance().getCurrentUser().getUid();

//if(usersref.child(userID).child("name")!=null)
  //  String name = usersref.child(userID).child("name").;
   // username.setText();

        db.addListenerForSingleValueEvent(new ValueEventListener() {
            // @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        Log.e("ID3", (dataSnapshot.getKey()));
                        Log.e("ID03", userID);
                        if (dataSnapshot.getKey().equalsIgnoreCase(userID)) {
                            User one = dataSnapshot.getValue(User.class);
                            username.setText(one.getName());
                            bankname.setText(one.getBank());
                            //Welcome.setText(one.getName());
                           // emailID.setText(one.getEmail());
                            IBAN.setText(one.getIban());
                            phone.setText(one.getPhone());
                            //owner.setText(one.getOwner());
                            address.setText(one.getAddr());

                            break;
                        }
                        Log.e("ID5", dataSnapshot.getKey());
                    }
                }catch (Exception E){   E.printStackTrace();}
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




cancel.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        moveToHome();
    }
});

confirm.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        if (phone.getText().toString().isEmpty()) {
            if (IBAN.getText().toString().isEmpty()) {
                Toast.makeText(ConfrimAddMaterial.this, "Check the IBAN", Toast.LENGTH_SHORT).show();
                IBAN.setError("IBAN field should not be empty");
                Toast.makeText(ConfrimAddMaterial.this, "Check the phone", Toast.LENGTH_SHORT).show();
                phone.setError("Phone field should not be empty");
            }else{
                Toast.makeText(ConfrimAddMaterial.this, "Check the phone", Toast.LENGTH_SHORT).show();
                phone.setError("Phone field should not be empty");
            }
        }
        else if (IBAN.getText().toString().isEmpty()) {
            if (phone.getText().toString().isEmpty()) {
                Toast.makeText(ConfrimAddMaterial.this, "Check the IBAN", Toast.LENGTH_SHORT).show();
                IBAN.setError("IBAN field should not be empty");
                Toast.makeText(ConfrimAddMaterial.this, "Check the phone", Toast.LENGTH_SHORT).show();
                phone.setError("Phone field should not be empty");
            } else {
                Toast.makeText(ConfrimAddMaterial.this, "Check the IBAN", Toast.LENGTH_SHORT).show();
                IBAN.setError("IBAN field should not be empty");
            }
        }
        else if(!IBAN.getText().toString().isEmpty() && (IBAN.getText().toString().charAt(0)!='S'||IBAN.getText().toString().charAt(1)!='A')){
            Toast.makeText(ConfrimAddMaterial.this,"Check the IBAN", Toast.LENGTH_SHORT).show();
            IBAN.setError("IBAN should start with SA");
        } else
        if(!phone.getText().toString().isEmpty() && (phone.getText().toString().charAt(0)!='0'||phone.getText().toString().charAt(1)!='5')) {
            Toast.makeText(ConfrimAddMaterial.this, "Check the phone", Toast.LENGTH_SHORT).show();
            phone.setError("Phone number format is wrong");
        }  else {
            if (    !IBAN.getText().toString().isEmpty() &&    (IBAN.getText().toString().length() != 25)) {
                if (!phone.getText().toString().isEmpty() && phone.length() != 10) {
                    Toast.makeText(ConfrimAddMaterial.this, "Check the phone", Toast.LENGTH_SHORT).show();
                    phone.setError("Phone should be 10 characters");
                    Toast.makeText(ConfrimAddMaterial.this, "Check the IBAN", Toast.LENGTH_SHORT).show();
                    IBAN.setError("IBAN should be 25 characters and starting with SA");
                } else{
                    Toast.makeText(ConfrimAddMaterial.this, "Check the IBAN", Toast.LENGTH_SHORT).show();
                    IBAN.setError("IBAN should be 25 characters and starting with SA");
                }
            } else if (!phone.getText().toString().isEmpty() && phone.length() != 10) {
                Toast.makeText(ConfrimAddMaterial.this, "Check the phone", Toast.LENGTH_SHORT).show();
                phone.setError("Phone should be 10 characters");
            } else {
                if(!phone.getText().toString().isEmpty() && !address.getText().toString().isEmpty() && !IBAN.getText().toString().isEmpty() && !username.getText().toString().isEmpty() && !bankname.getText().toString().isEmpty()){
                    String id1 = db.push().getKey();
                    String s1=phone.getText().toString().trim();
                    String s2= address.getText().toString().trim();
                    String s3=IBAN.getText().toString().trim();
                    String s4=username.getText().toString().trim();
                    String s5=bankname.getText().toString().trim();
                    Post p = new Post(id1, materialname, coursename, uniname, materialtype, price, description, s1, s2, s3, s4, s5,MainActivity.userID,URL);
                    Log.e("user id post",p.getUserID());
                    postsref.child(id1).setValue(p);
                    Toast.makeText(ConfrimAddMaterial.this,"Material added successfully", Toast.LENGTH_LONG).show();
                    moveToHome();
                }
        else
            Toast.makeText(ConfrimAddMaterial.this,"please ensure all fields are filled", Toast.LENGTH_LONG).show();

            }
        }
    }
});




    }



    public void moveToHome(){

        Intent intent = new Intent(ConfrimAddMaterial.this, BooksList.class);
        startActivity(intent);
    }






}
