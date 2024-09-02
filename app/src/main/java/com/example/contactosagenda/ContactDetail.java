package com.example.contactosagenda;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ContactDetail extends AppCompatActivity {

    private TextView nombreD, emailD, telefonoD, dirD, notasD;
    private ImageView foto_detalle;
    private String id;
    private DataBaseHelper dataBaseHelper;
    private FloatingActionButton edit_contact, delete_contact;
    private String name, image, phone, email, dir, note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        //get data from intent
        Intent intent = getIntent();
        id= intent.getStringExtra("contactId");

        dataBaseHelper = new DataBaseHelper(ContactDetail.this);

        nombreD = findViewById(R.id.nombre_detail);
        emailD = findViewById(R.id.email_detail);
        telefonoD = findViewById(R.id.telefono_detail);
        dirD = findViewById(R.id.direccion_detail);
        notasD = findViewById(R.id.nota_detail);
        foto_detalle = findViewById(R.id.foto_contacto_detail);

        loadDataById();

        edit_contact = findViewById(R.id.edit_contact_detail);
        edit_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactDetail.this,AddContact.class);
                intent.putExtra("id", id);
                intent.putExtra("nombre", name);
                intent.putExtra("telefono", phone);
                intent.putExtra("email", email);
                intent.putExtra("dir", dir);
                intent.putExtra("notas", note);
                intent.putExtra("foto", image);
                intent.putExtra("isEdit", true);
                ContactDetail.this.startActivity(intent);
            }
        });

        delete_contact = findViewById(R.id.delete_contact_detail);
        delete_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ContactDetail.this,MainActivity.class);
                dataBaseHelper.eliminarContacto(id);
                Toast.makeText(getApplicationContext(), "Contacto borrado", Toast.LENGTH_LONG).show();
                ContactDetail.this.startActivity(intent);
            }
        });
    }

    private void loadDataById() {

        String query = "SELECT * FROM "+ Database.TABLE_NAME + " WHERE " + Database.ID + " =\"" +
                id + "\"";

        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToNext()){
            do{
                this.name = ""+ cursor.getString(cursor.getColumnIndexOrThrow(Database.NAME));
                this.image = ""+ cursor.getString(cursor.getColumnIndexOrThrow(Database.IMAGE));
                this.phone = ""+ cursor.getString(cursor.getColumnIndexOrThrow(Database.PHONE));
                this.email = ""+ cursor.getString(cursor.getColumnIndexOrThrow(Database.EMAIL));
                this.dir =   "" + cursor.getString(cursor.getColumnIndexOrThrow(Database.DIR));
                this.note =  "" + cursor.getString(cursor.getColumnIndexOrThrow(Database.NOTE));

                nombreD.setText(name);
                telefonoD.setText(phone);
                emailD.setText(email);
                dirD.setText(dir);
                notasD.setText(note);

                if(image.isEmpty()){
                    foto_detalle.setImageResource(R.drawable.ic_person);
                }
                else{
                    foto_detalle.setImageURI(Uri.parse(image));
                }
            }while(cursor.moveToNext());
        }
        db.close();
    }
}