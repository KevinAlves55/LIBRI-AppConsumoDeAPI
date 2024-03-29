package com.cristianomoraes.libri_retorfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cristianomoraes.libri_retorfit.model.Item;
import com.cristianomoraes.libri_retorfit.model.Livro;
import com.cristianomoraes.libri_retorfit.remote.APIUtil;
import com.cristianomoraes.libri_retorfit.remote.RouterInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedLivro extends AppCompatActivity {

    RouterInterface routerInterface;
    List<Livro> list = new ArrayList<Livro>();
    RecyclerView recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_livro);

        routerInterface = APIUtil.getUsuarioInterface();
        recyclerview = findViewById(R.id.recyclerview);

        /** RECEBE OS DADOS DE LIVROS VINDOS DA API REST **/
        Call<List<Livro>> call = routerInterface.getBook();

        call.enqueue(new Callback<List<Livro>>() {
            @Override
            public void onResponse(Call<List<Livro>> call, Response<List<Livro>> response) {

                List<Item> itens = new ArrayList<>();
                list = response.body();

                for (int i = 0; i < list.size(); i++) {

                    itens.add(new Item(0, list.get(i)));

                }

                recyclerview.setAdapter(new LivroAdapter(itens));

            }

            @Override
            public void onFailure(Call<List<Livro>> call, Throwable t) {

                Log.d("API-ERRO", t.getMessage());

            }
        });

    }

    /** CLASSE DE ADAPTER DO RECYCLERVIEW **/
    private class LivroAdapter extends RecyclerView.Adapter{

        List<Item> itens;

        public LivroAdapter(List<Item> itens) {
            this.itens = itens;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            if (viewType == 0) {

                return new LivroAdapter.LivroViewHolder(
                        LayoutInflater.from(parent.getContext())

                                .inflate(
                                        R.layout.item_container_livro,
                                        parent,
                                        false
                                )
                );
            }

            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

            /** RECUPERA OS DADOS DE LIVRO **/
            if (getItemViewCount(position) == 0 ) {

                Livro livro = (Livro) itens.get(position).getObject();
                ((LivroAdapter.LivroViewHolder) holder).setLivroData(livro);

            }

        }

        @Override
        public int getItemCount() {
            return itens.size();
        }

        /** RECUPERA O TIPO DO OBJETO DE ITEM **/
        public int getItemViewCount(int position) {
            return itens.get(position).getType();
        }

        /** CLASSE DE VIEWHOLDER DA RECYCLERVIEW **/
        class LivroViewHolder extends RecyclerView.ViewHolder {

            private TextView txtTitulo, txtDescricao;
            private int cod_livro;

            public LivroViewHolder(@NonNull View itemView) {
                super(itemView);

                txtTitulo = itemView.findViewById(R.id.txtCardTituloLivro);
                txtDescricao = itemView.findViewById(R.id.txtCardDescricaoLivro);

                /** TRATAMENTO DE CLIQUE PARA ALTERAÇÃO E EXCLUSÃO DE LIVRO **/

            }

            public void setLivroData(Livro livro) {

                txtTitulo.setText(livro.getTitulo());
                txtDescricao.setText(livro.getDescricao());
                cod_livro = livro.getCod_livro();

            }

        }

    }

}