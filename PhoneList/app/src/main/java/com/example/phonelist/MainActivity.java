package com.example.phonelist;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.phonelist.model.ContactApiModel;
import com.example.phonelist.model.LoginRequest;
import com.example.phonelist.model.LoginResponse;
import com.example.phonelist.network.ApiService;
import com.example.phonelist.network.ContactApi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fabAddContact;
    private RecyclerView recyclerViewContacts;
    private ContactAdapter contactAdapter;
    private DBHelper dbHelper;
    private List<Contact> contactList;
    private Button btnFilterByDate;
    private Button btnSync;
    private SharedPreferences sharedPreferences;
    private String authToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DBHelper(this);

        recyclerViewContacts = findViewById(R.id.recyclerViewContacts);
        recyclerViewContacts.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewContacts.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        contactList = dbHelper.getAllContacts();
        contactAdapter = new ContactAdapter(this, contactList);
        recyclerViewContacts.setAdapter(contactAdapter);

        btnFilterByDate = findViewById(R.id.btnFilterByDate);
        btnFilterByDate.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, FilterByDateActivity.class)));

        fabAddContact = findViewById(R.id.fabAddContact);
        fabAddContact.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AddOrEditContact.class)));

        setupAdapterListeners();

        sharedPreferences = getSharedPreferences("PhoneListPrefs", MODE_PRIVATE);
        authToken = sharedPreferences.getString("authToken", null);

        btnSync = findViewById(R.id.btnSync);
        btnSync.setOnClickListener(v -> {
            if (isNetworkAvailable()) {
                syncContacts();
            } else {
                Toast.makeText(this, "Sem conexão com a internet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupAdapterListeners() {
        contactAdapter.setOnItemClickListener(position -> openEditContactActivity(contactList.get(position)));
        contactAdapter.setOnDeleteClickListener(this::showDeleteConfirmationDialog);
    }

    private void openEditContactActivity(Contact contact) {
        Intent intent = new Intent(this, AddOrEditContact.class);
        intent.putExtra("CONTACT_ID", (long) contact.getId());
        intent.putExtra("CONTACT_NAME", contact.getName());
        intent.putExtra("CONTACT_APELIDO", contact.getApelido());
        intent.putExtra("CONTACT_NUMBER", contact.getNumber());
        intent.putExtra("CONTACT_CPF", contact.getCpf());
        intent.putExtra("CONTACT_EMAIL", contact.getEmail());
        startActivity(intent);
    }

    private void showDeleteConfirmationDialog(int position) {
        Contact contact = contactList.get(position);
        new AlertDialog.Builder(this)
                .setTitle("Excluir Contato")
                .setMessage("Tem certeza que deseja excluir " + contact.getName() + "?")
                .setPositiveButton("Excluir", (dialog, which) -> deleteContact(position))
                .setNegativeButton("Cancelar", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void deleteContact(int position) {
        Contact contact = contactList.get(position);
        dbHelper.deleteContact(contact.getId());
        contactList.remove(position);
        contactAdapter.notifyItemRemoved(position);
        Toast.makeText(this, "Contato excluído", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshContactList();
    }

    private void refreshContactList() {
        contactList.clear();
        contactList.addAll(dbHelper.getAllContacts());
        contactAdapter.notifyDataSetChanged();
    }

    private void showLoginDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Login para Sincronizar");
        View view = getLayoutInflater().inflate(R.layout.dialog_login, null);
        EditText etEmail = view.findViewById(R.id.etEmail);
        EditText etPassword = view.findViewById(R.id.etPassword);
        builder.setView(view);
        builder.setPositiveButton("Login", (dialog, which) -> authenticateUser(
                etEmail.getText().toString(),
                etPassword.getText().toString()
        ));
        builder.setNegativeButton("Cancelar", null);
        builder.show();
    }

    private void authenticateUser(String email, String password) {
        ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Autenticando...");
        progress.setCancelable(false);
        progress.show();

        ApiService.getAuthApi().login(new LoginRequest(email, password)).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                progress.dismiss();
                if (response.isSuccessful() && response.body() != null) {
                    sharedPreferences.edit().putString("authToken", response.body().getToken()).apply();
                    Toast.makeText(MainActivity.this, "Login realizado!", Toast.LENGTH_SHORT).show();
                    syncContacts();
                } else {
                    Toast.makeText(MainActivity.this, "Login falhou: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progress.dismiss();
                Toast.makeText(MainActivity.this, "Erro: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void syncContacts() {
        if (!isLoggedIn()) {
            showLoginDialog();
            return;
        }

        if (!isNetworkAvailable()) {
            Toast.makeText(this, "Sem conexão com a internet", Toast.LENGTH_SHORT).show();
            return;
        }

        ProgressDialog progress = new ProgressDialog(this);
        progress.setMessage("Sincronizando contatos...");
        progress.setCancelable(false);
        progress.show();

        List<Contact> contacts = dbHelper.getAllContacts();
        if (contacts.isEmpty()) {
            progress.dismiss();
            Toast.makeText(this, "Nenhum contato para sincronizar", Toast.LENGTH_SHORT).show();
            return;
        }

        ContactApi contactApi = ApiService.getContactApi();
        for (Contact contact : contacts) {
            ContactApiModel apiContact = new ContactApiModel(
                    contact.getName(),
                    contact.getNumber(),
                    contact.getApelido(),
                    contact.getCpf(),
                    contact.getEmail()
            );

            contactApi.createContact(apiContact).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {}

                @Override
                public void onFailure(Call<Void> call, Throwable t) {}
            });
        }

        progress.dismiss();
        Toast.makeText(this, "Sincronização concluída", Toast.LENGTH_SHORT).show();
    }

    private boolean isLoggedIn() {
        return sharedPreferences.getString("authToken", null) != null;
    }

    private boolean isNetworkAvailable() {
        NetworkInfo activeNetwork = ((ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }
}