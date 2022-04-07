package com.cristianomoraes.libri_retorfit.remote;

import com.cristianomoraes.libri_retorfit.model.Livro;
import com.cristianomoraes.libri_retorfit.model.Usuario;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Body;

public interface RouterInterface {

    /** MAPEAMENTO DE TODAS AS ROTAS **/

    // POST (Rota de envio)
    @POST("/usuario/cadastrarUsuario")
    Call<Usuario> addUser(@Body Usuario usuario);

    // POST (Livro)
    @POST("/livro/cadastrarLivro")
    Call<Livro> addBook(@Body Livro livro);

}