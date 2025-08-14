package com.example.phonelist;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class AddOrEditContact extends AppCompatActivity {
    private EditText editName, editApelido, editNumber, editCpf, editEmail;
    private DBHelper dbHelper;
    private long contactId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_edit_contact);

        initializeViews();
        setupContactData();
        setupSaveButton();
    }

    private void initializeViews() {
        dbHelper = new DBHelper(this);
        editName = findViewById(R.id.editName);
        editApelido = findViewById(R.id.editApelido);
        editNumber = findViewById(R.id.editNumber);
        editCpf = findViewById(R.id.editCpf);
        editEmail = findViewById(R.id.editEmail);
    }

    private void setupContactData() {
        if (getIntent().hasExtra("CONTACT_ID")) {
            contactId = getIntent().getLongExtra("CONTACT_ID", -1);
            fillFormWithContactData();
            setTitle("Editar Contato");
        } else {
            setTitle("Novo Contato");
        }
    }

    private void setupSaveButton() {
        Button btnSave = findViewById(R.id.btnSave);
        btnSave.setOnClickListener(v -> saveContact());
    }

    private void fillFormWithContactData() {
        editName.setText(getIntent().getStringExtra("CONTACT_NAME"));
        editApelido.setText(getIntent().getStringExtra("CONTACT_APELIDO"));
        editNumber.setText(getIntent().getStringExtra("CONTACT_NUMBER"));
        editCpf.setText(getIntent().getStringExtra("CONTACT_CPF"));
        editEmail.setText(getIntent().getStringExtra("CONTACT_EMAIL"));
    }

    private void saveContact() {
        String name = editName.getText().toString().trim();
        String number = editNumber.getText().toString().trim();

        if (name.isEmpty() || number.isEmpty()) {
            Toast.makeText(this, "Nome e número são obrigatórios", Toast.LENGTH_SHORT).show();
            return;
        }

        String time = String.valueOf(System.currentTimeMillis());

        if (contactId == -1) {
            createNewContact(name, time);
        } else {
            updateExistingContact(name, time);
        }

        finish();
    }

    private void createNewContact(String name, String time) {
        String apelido = editApelido.getText().toString().trim();
        String number = editNumber.getText().toString().trim();
        String cpf = editCpf.getText().toString().trim();
        String email = editEmail.getText().toString().trim();

        long id = dbHelper.insertContact(name, apelido, number, cpf, email, time, time);
        if (id != -1) {
            Toast.makeText(this, "Contato salvo", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
        }
    }

    private void updateExistingContact(String name, String time) {
        Contact contact = new Contact();
        contact.setId((int)contactId);
        contact.setName(name);
        contact.setApelido(editApelido.getText().toString().trim());
        contact.setNumber(editNumber.getText().toString().trim());
        contact.setCpf(editCpf.getText().toString().trim());
        contact.setEmail(editEmail.getText().toString().trim());
        contact.setTimeUpdate(time);

        int rowsAffected = dbHelper.updateContact(contact);
        if (rowsAffected > 0) {
            Toast.makeText(this, "Contato atualizado", Toast.LENGTH_SHORT).show();
            setResult(RESULT_OK);
        } else {
            Toast.makeText(this, "Falha ao atualizar contato", Toast.LENGTH_SHORT).show();
        }
    }
}