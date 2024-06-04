package com.example.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppDatabase db;
    private TextView personsListTextView;
    private Button button;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText phoneNumberEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = AppActivity.getDatabase();
        personsListTextView = findViewById(R.id.txt_list);
        firstNameEditText = findViewById(R.id.edittext_name);
        lastNameEditText = findViewById(R.id.edittext_surname);
        phoneNumberEditText = findViewById(R.id.edittext_phone);
        button = findViewById(R.id.button);

        button.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_save, 0, 0, 0);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = firstNameEditText.getText().toString().trim();
                String lastName = lastNameEditText.getText().toString().trim();
                String phoneNumber = phoneNumberEditText.getText().toString().trim();

                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(lastName) || TextUtils.isEmpty(phoneNumber)){
                    Toast.makeText(getApplicationContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
                }
                else {
                    Person person = new Person();
                    person.setName(name);
                    person.setLastName(lastName);
                    person.setPhoneNumber(phoneNumber);
                    db.personDAO().insert(person);
                    Toast.makeText(
                            getApplicationContext(),
                            "Person added",
                            Toast.LENGTH_SHORT
                    ).show();
                    firstNameEditText.setText("");
                    lastNameEditText.setText("");
                    phoneNumberEditText.setText("");
                    firstNameEditText.requestFocus();
                    getPersonList();
                }
            }
        });
    }

    private void getPersonList() {
        personsListTextView.setText("");
        List<Person> personList = db.personDAO().getAllPersons();
        for(Person person : personList) {
            personsListTextView.append(person.getName() + " "
                    + person.getLastName() + " "
                    + person.getPhoneNumber()
                    + "\n");
            personsListTextView.append("\n");
        }
    }
}