package com.laika.miaudota.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.Intent;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.laika.miaudota.R;
import com.laika.miaudota.models.*;
import com.laika.miaudota.activities.PerfilActivity;

import java.util.ArrayList;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>{

    RequestOptions option;
    private Context mContext;
    private ArrayList<Animal> mData;

    public RecyclerViewAdapter(Context mContext, ArrayList<Animal> mData) {

        this.mContext = mContext;
        this.mData = mData;
        option = new RequestOptions().centerCrop().placeholder(R.drawable.loading_shape).error(R.drawable.loading_shape);

    }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.activity_animal_linha,parent,false);
        final MyViewHolder viewHolder = new MyViewHolder(view);
        viewHolder.view_container.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                Intent i = new Intent(mContext, PerfilActivity.class);
                i.putExtra("animal_nome", mData.get(viewHolder.getAdapterPosition()).getNome());
                i.putExtra("animal_idade", String.valueOf(mData.get(viewHolder.getAdapterPosition()).getIdade()));
                i.putExtra("animal_sexo", mData.get(viewHolder.getAdapterPosition()).getSexo());
                i.putExtra("animal_vermifugado", String.valueOf(mData.get(viewHolder.getAdapterPosition()).isVermifugado()));
                i.putExtra("animal_vacinado", String.valueOf(mData.get(viewHolder.getAdapterPosition()).isVacinado()));
                i.putExtra("animal_peso", String.valueOf(mData.get(viewHolder.getAdapterPosition()).getPeso()));
                i.putExtra("animal_pelagem", mData.get(viewHolder.getAdapterPosition()).getPelagem());
                i.putExtra("animal_descricao", mData.get(viewHolder.getAdapterPosition()).getDescricao());
                i.putExtra("animal_endereco", mData.get(viewHolder.getAdapterPosition()).getEndereco());
                i.putExtra("animal_foto", mData.get(viewHolder.getAdapterPosition()).getFoto_url());
                if(mData.get(viewHolder.getAdapterPosition()) instanceof Cao)
                    i.putExtra("animal_porte", (((Cao)mData.get(viewHolder.getAdapterPosition())).getPorte()));

                mContext.startActivity(i);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        if(mData.get(position).isVermifugado())
            holder.tv_vermifugado.setText("Vermifugado(a): Sim");
        else
            holder.tv_vermifugado.setText("Vermifugado(a): Não");

        if(mData.get(position).isVacinado())
            holder.tv_vacinado.setText("Vacinado(a): Sim");
        else
            holder.tv_vacinado.setText("Vacinado(a): Não");

        if(mData.get(position) instanceof Cao)
            holder.tv_porte.setText("Porte: " + String.valueOf((((Cao)mData.get(position)).getPorte())));
        else
            holder.tv_porte.setText("");

        holder.tv_nome.setText(String.valueOf(mData.get(position).getNome()));
        holder.tv_idade.setText("Idade: " + String.valueOf(mData.get(position).getIdade()) + " ano(s)");
        holder.tv_sexo.setText("Sexo: " + String.valueOf(mData.get(position).getSexo()));

        Glide.with(mContext).load(mData.get(position).getFoto_url()).apply(option).into(holder.iv_foto);

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_nome;
        TextView tv_idade;
        TextView tv_sexo;
        TextView tv_vermifugado;
        TextView tv_vacinado;
        TextView tv_porte;
        ImageView iv_foto;
        LinearLayout view_container;


        public MyViewHolder(View itemView){

            super(itemView);
            view_container = itemView.findViewById(R.id.container);
            tv_nome = itemView.findViewById(R.id.animal_nome);
            tv_idade = itemView.findViewById(R.id.animal_idade);
            tv_sexo = itemView.findViewById(R.id.animal_sexo);
            tv_vermifugado = itemView.findViewById(R.id.animal_vermifugado);
            tv_vacinado = itemView.findViewById(R.id.animal_vacinado);
            tv_porte = itemView.findViewById(R.id.animal_porte);
            iv_foto = itemView.findViewById(R.id.animal_foto);

        }

    }

}
