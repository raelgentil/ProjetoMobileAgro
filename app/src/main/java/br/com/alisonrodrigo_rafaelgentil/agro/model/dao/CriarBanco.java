package br.com.alisonrodrigo_rafaelgentil.agro.model.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CriarBanco  extends SQLiteOpenHelper
{

    public static  final String Nome_Banco = "banco_AgroService";
    public static  final int Versao_Banco = 1;

    public static  final String TableUsuario = "usuario";
    public static  final String ID ="_id";
    public static  final String Nome_usuario ="nome";
    public static  final String Cpf ="cpf";
    public static  final String Email ="email";
    public static  final String Telefone_Usuario ="telefone";
    public static  final String Data_Nascimento_Usuario ="data_Nascimento";
    public static  final String Nome_de_usuario ="usuario";
    public static  final String Senha_usuario ="senha";

    public CriarBanco(Context context){
        super(context, Nome_Banco, null, Versao_Banco);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("sql", "Criando as Tabelas rubrica,entradas e saidas...");
        db.execSQL("create table if not exists " +TableUsuario +"("
                + ID +" integer primary Key  autoincrement,"+
                Nome_usuario + " text,"+
                Cpf + " text,"+
                Email + " text,"+
                Telefone_Usuario+ " text,"+
                Data_Nascimento_Usuario + " text,"+
                Nome_de_usuario + " text,"+
                Senha_usuario + " text); ");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Nome_Banco + ";");
        onCreate(db);
    }
}
