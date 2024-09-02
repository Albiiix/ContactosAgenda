package com.example.contactosagenda;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class AddContact extends AppCompatActivity {

    private ImageView foto_contacto;
    private EditText nombre, telefono, email, direccion, nota;
    private FloatingActionButton add_contact2;
    private String id, name, phone, email2, dir, note;
    private ActionBar actionBar;
    private String[] storagePermission;
    private Uri imageUri;
    private DataBaseHelper dbHelper;
    private Boolean isEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);

        dbHelper = new DataBaseHelper(this);
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        foto_contacto = findViewById(R.id.foto_contacto);
        nombre = findViewById(R.id.nombre);
        telefono = findViewById(R.id.telefono);
        email = findViewById(R.id.email);
        direccion = findViewById(R.id.direccion);
        nota = findViewById(R.id.nota);

        add_contact2 = findViewById(R.id.add_contact2);

        Intent intent = getIntent();
        isEdit = intent.getBooleanExtra("isEdit", false);
        if(isEdit){

            actionBar.setTitle("Editar contacto");
            add_contact2.setImageResource(R.drawable.ic_edit);

            id = intent.getStringExtra("id");
            String name = intent.getStringExtra("nombre");
            String phone = intent.getStringExtra("telefono");
            String email2 = intent.getStringExtra("email");
            String dir2 = intent.getStringExtra("dir");
            String notes = intent.getStringExtra("notas");
            String photo = intent.getStringExtra("foto");

            nombre.setText(name);
            telefono.setText(phone);
            email.setText(email2);
            direccion.setText(dir2);
            nota.setText(notes);
            imageUri= Uri.parse(photo);
            if(photo.equals("null")){
                foto_contacto.setImageResource(R.drawable.ic_person);
            }else{
                foto_contacto.setImageURI(Uri.parse(photo));
            }
        } else{
            actionBar.setTitle("Añadir contacto");
        }

        add_contact2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });

        foto_contacto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
    }

    private void saveData(){

        name = nombre.getText().toString().trim();
        phone = telefono.getText().toString().trim();
        email2 = email.getText().toString().trim();
        dir = direccion.getText().toString().trim();
        note = nota.getText().toString().trim();

        if(!name.isEmpty() || !phone.isEmpty() || !email2.isEmpty() || !dir.isEmpty() || !note.isEmpty()){

            if(isEdit){
                dbHelper.actualizarContacto("" + id, "" + name, "" + imageUri, "" + phone, "" + email2, "" + dir, "" + note );
                Toast.makeText(getApplicationContext(), "Contacto editado", Toast.LENGTH_LONG).show();
            }else{
                dbHelper.insertarContacto("" + name, "" + imageUri, "" + phone, "" + email2, "" + dir, "" + note );
                Toast.makeText(getApplicationContext(), "Contacto añadido", Toast.LENGTH_LONG).show();
            }
            Intent intent = new Intent(AddContact.this, MainActivity.class);
            startActivity(intent);
        } else{
            Toast.makeText(getApplicationContext(), "No hay datos que guardar", Toast.LENGTH_SHORT).show();
        }
    }

    private void openGallery(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        }
       Intent imagenPick = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
       startActivityForResult(imagenPick, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK){
            imageUri = data.getData();
            foto_contacto.setImageURI(imageUri);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}