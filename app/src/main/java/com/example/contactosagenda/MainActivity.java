package com.example.contactosagenda;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity implements Observer {

    private FloatingActionButton add_contact;
    private RecyclerView recyclerView;
    private DataBaseHelper dataBaseHelper;
    private AdapterContact adapterContact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);

        dataBaseHelper = new DataBaseHelper(this);

        add_contact = findViewById(R.id.add_contact);
        add_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AddContact.class);
                startActivity(intent);
            }
        });
        loadData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu, menu);
        MenuItem item = menu.findItem(R.id.buscar);
        SearchView searchView =(SearchView) item.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                searchContact(s);
                return true;
            }
            @Override
            public boolean onQueryTextChange(String s) {
                searchContact(s);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.importar_exportar:
                imp_exp();
                break;
        }
        return true;
    }

    private void imp_exp() {
        String[] options= {"Importar contactos", "Exportar contactos"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Importar/Exportar contactos");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if(i == 0){
                    importar();
                }else if(i == 1){
                    exportar();
                }
            }
        });
    }

    private void exportar() {
    }

    private void importar() {
    }

    private void searchContact(String s) {
        adapterContact= new AdapterContact(this, dataBaseHelper.buscarContacto(s), this);
        recyclerView.setAdapter(adapterContact);
    }

    private void loadData() {
        adapterContact = new AdapterContact(this, dataBaseHelper.getData(), this);
        recyclerView.setAdapter(adapterContact);

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData(); //refresh
    }

    @Override
    public void llamar(ModelContact modelContact) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel: " + Uri.encode(modelContact.getPhone())));
        startActivity(intent);
    }

    @Override
    public void ver_imagen(ModelContact modelContact) {
        Intent intent = new Intent(this, ContactDetail.class);
        String id = modelContact.getId();
        intent.putExtra("contactId", id);
        this.startActivity(intent);
    }

    @Override
    public void editar_contacto(String id, String nombre, String telefono, String email, String dir, String notas, String foto) {
        Intent intent = new Intent(this, AddContact.class);
        intent.putExtra("id", id);
        intent.putExtra("nombre", nombre);
        intent.putExtra("telefono", telefono);
        intent.putExtra("email", email);
        intent.putExtra("dir", dir);
        intent.putExtra("notas", notas);
        intent.putExtra("foto", foto);

        intent.putExtra("isEdit", true);

        this.startActivity(intent);
    }
}