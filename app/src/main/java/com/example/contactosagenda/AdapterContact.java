package com.example.contactosagenda;

import  android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class AdapterContact extends RecyclerView.Adapter<AdapterContact.ContactViewHolder> {

    private Context context;
    private ArrayList<ModelContact> contactList;
    private Observer observer;

    public AdapterContact(Context context, ArrayList<ModelContact> contactList, Observer observer) {
        this.context = context;
        this.contactList = contactList;
        this.observer = observer;
    }

    @NonNull
    @Override
    public ContactViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_contacto, parent, false);
        ContactViewHolder viewHolder= new ContactViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContactViewHolder holder, int position) {

        ModelContact modelContact= contactList.get(position);
        String id = modelContact.getId();
        String image = modelContact.getImage();
        String name = modelContact.getName();
        String phone = modelContact.getPhone();

        holder.nombre_lista.setText(name);
        if(image.isEmpty()){
            holder.foto_contacto_lista.setImageResource(R.drawable.ic_person);
        }
        else{
            holder.foto_contacto_lista.setImageURI(Uri.parse(image));
        }
        holder.telefono_lista.setText(phone);

        holder.icono_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                observer.llamar(modelContact);
            }
        });

        holder.icono_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                observer.ver_imagen(modelContact);
            }
        });

        holder.nombre_lista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                observer.ver_imagen(modelContact);
            }
        });
        holder.foto_contacto_lista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                observer.ver_imagen(modelContact);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    static class ContactViewHolder extends RecyclerView.ViewHolder{

        ImageView foto_contacto_lista, icono_phone, icono_more;
        TextView nombre_lista, telefono_lista;

        public ContactViewHolder(@NonNull View itemView) {
            super(itemView);
            foto_contacto_lista = itemView.findViewById(R.id.foto_contacto_lista);
            icono_phone = itemView.findViewById(R.id.phone_list);
            icono_more = itemView.findViewById(R.id.more_list);
            nombre_lista = itemView.findViewById(R.id.nombre_lista);
            telefono_lista = itemView.findViewById(R.id.telefono_lista);
        }
    }
}
