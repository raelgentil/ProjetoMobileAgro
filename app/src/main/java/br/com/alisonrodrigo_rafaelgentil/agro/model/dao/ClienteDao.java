package br.com.alisonrodrigo_rafaelgentil.agro.model.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Usuario;


public class ClienteDao {

//    private SQLiteDatabase db;
//    private CriarBanco criarBanco;
//
//    public ClienteDao() {}
//
//    public ClienteDao(Context context) {
//        criarBanco = new CriarBanco(context);
//    }
//
//    public String insertUsuario(Usuario usuario) {
//        ContentValues valores;
//        long resultado;
//
//        db = criarBanco.getWritableDatabase();
//        valores = new ContentValues();
//        valores.put(criarBanco.Nome_usuario, usuario.getNome());
//        valores.put(criarBanco.Cpf, usuario.getCpf());
//        valores.put(criarBanco.Email, usuario.getuId());
//        valores.put(criarBanco.Telefone_Usuario, usuario.getTelefone());
//        valores.put(criarBanco.Data_Nascimento_Usuario, usuario.getDataNascimento());
//        valores.put(criarBanco.Nome_de_usuario, usuario.getLogin());
//        valores.put(criarBanco.Senha_usuario, usuario.getSenha());
//
//        resultado = db.insert(criarBanco.TableUsuario, null, valores);
//        db.close();
//
//        if (resultado == -1) {
//            return "Erro ao inserir usuario";
//        } else
//            return "Usuario inserido com sucesso";
//
//    }
//
//    public boolean select_verificarLogin(String login, String senha) {
//        Usuario usuario = null;
//        db = criarBanco.getWritableDatabase();
//
//        String[] colunas = new String[]{"_id", "nome", "cpf", "email", "telefone", "data_Nascimento", "usuario", "senha"};
//
//        Cursor cursor = db.query("usuario", colunas, "login = " + "'" + login + "'" + " and senha = " + "'" + senha + "'", null, null, null, "login ASC");
//
//        if (cursor.getCount() > 0) {
//            cursor.moveToFirst();
//
//            do {
//                usuario = new Usuario(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7));
//                return true;
//            } while (cursor.moveToNext());
//
//        } else
//            return false;
//    }
//
//    public Usuario verificarLogin(String login, String senha) {
//        Usuario user = new Usuario();
//        db = criarBanco.getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM usuario WHERE usuario=? AND senha=?", new String[]{login, senha});
//
//        if (cursor != null) {
//            cursor.moveToFirst();
//            if (cursor.getCount() > 0) {
//                user.setId(cursor.getInt(0));            // definição do ID retornado do cursor
//                user.setNome(cursor.getString(1));       // definição do NOME retornado do cursor
//                user.setCpf(cursor.getString(2));
//                user.setuId(cursor.getString(3));      // definição do EMAIL retornado do cursor
//                user.setTelefone(cursor.getString(4));
//                user.setDataNascimento(cursor.getString(5));
//                user.setLogin(cursor.getString(6));
//                user.setSenha(cursor.getString(7));      // definição da SENHA retornado do cursor
//            } else {
//                // caso não retornar nenhum usuario do cursor, o retorno da função será nula
//                return null;
//            }
//        }
//
//        db.close();
//        return user;
//    }
//
//
//    public Boolean updateUsuario(Usuario usuario){
//
//
//        ContentValues valores = toValues(usuario);
//        criarBanco.getWritableDatabase().update(criarBanco.TableUsuario,valores,"_id=?",new String[] {String.valueOf(usuario.getId())});
//
//        return true;
//    }
//
//
//    private ContentValues toValues(Usuario usuario) {
//        ContentValues values = new ContentValues();
//        values.put(criarBanco.Nome_usuario, usuario.getNome());
//        values.put(criarBanco.Cpf,usuario.getCpf());
//        values.put(criarBanco.Email, usuario.getuId());
//        values.put(criarBanco.Telefone_Usuario, usuario.getTelefone());
//        values.put(criarBanco.Data_Nascimento_Usuario, usuario.getDataNascimento());
//        values.put(criarBanco.Nome_de_usuario, usuario.getLogin());
//        values.put(criarBanco.Senha_usuario, usuario.getSenha());
//        return values;
//    }
//
//
//    public Usuario buscarUsuarioPorID(int usuarioID){
//
//        Usuario usuario = null;
//
//        db = criarBanco.getReadableDatabase();
//
//        String sql = "SELECT * FROM usuario WHERE id ="+usuarioID;
//
//        Cursor cursor = db.rawQuery(sql,null);
//
//        if(cursor.moveToFirst()){
//
//            String nome = cursor.getString(cursor.getColumnIndex("nome"));
//            String email = cursor.getString(cursor.getColumnIndex("email"));
//
//            usuario = new Usuario();
//            usuario.setId(usuarioID);
//            usuario.setNome(nome);
//            usuario.setuId(email);
//        }
//
//        return usuario;
//    }
//




}
