package com.example.phonelist;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class FilterByDateActivity extends AppCompatActivity {

    private RecyclerView recyclerViewContacts;
    private ContactAdapter contactAdapter;
    private DBHelper dbHelper;
    private List<Contact> contactList;
    private RadioGroup radioGroupFilterOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_by_date);

        dbHelper = new DBHelper(this);
        recyclerViewContacts = findViewById(R.id.recyclerViewContacts);
        radioGroupFilterOptions = findViewById(R.id.radioGroupFilterOptions);
        Button btnApplyFilter = findViewById(R.id.btnApplyFilter);

        recyclerViewContacts.setLayoutManager(new LinearLayoutManager(this));
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerViewContacts.addItemDecoration(divider);

        contactList = dbHelper.getAllContacts();
        contactAdapter = new ContactAdapter(this, contactList);

        recyclerViewContacts.post(() -> {
            for (int i = 0; i < recyclerViewContacts.getChildCount(); i++) {
                View view = recyclerViewContacts.getChildAt(i);
                ImageView deleteIcon = view.findViewById(R.id.imgDelete);
                if (deleteIcon != null) {
                    deleteIcon.setVisibility(View.GONE);
                }
            }
        });

        recyclerViewContacts.setAdapter(contactAdapter);
        btnApplyFilter.setOnClickListener(v -> applyFilter());
    }

    private void applyFilter() {
        int selectedId = radioGroupFilterOptions.getCheckedRadioButtonId();
        List<Contact> filteredList;

        if (selectedId == R.id.radioCadastro) {
            filteredList = dbHelper.getContactsOrderedByRegistrationDate();
            Toast.makeText(this, "Ordenado por Data de Cadastro", Toast.LENGTH_SHORT).show();
        } else if (selectedId == R.id.radioAlteracao) {
            filteredList = dbHelper.getContactsOrderedByUpdateDate();
            Toast.makeText(this, "Ordenado por Data de Alteração", Toast.LENGTH_SHORT).show();
        } else {
            filteredList = dbHelper.getAllContacts();
        }

        contactAdapter.updateContacts(filteredList);
    }
}