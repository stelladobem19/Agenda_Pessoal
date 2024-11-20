package com.example.agenda;

// Importações necessárias para o funcionamento do código
import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;  // Banco de dados SQLite
import android.database.Cursor;  // Para navegar entre os registros do banco
import android.content.ContextWrapper;  // Para acessar o contexto da aplicação
import java.text.SimpleDateFormat;  // Para manipulação e formatação de datas
import java.util.Date;  // Para manipulação de datas
import java.text.ParseException;  // Para tratar exceções durante o parsing de datas
import static android.content.Context.MODE_PRIVATE;  // Permissão para abrir banco de dados em modo privado

// Classe principal que gerencia operações de banco de dados
public class BDs {
    // Variáveis estáticas para armazenar a conexão com o banco de dados e o cursor
    static SQLiteDatabase db = null;
    static Cursor cursor;

    // Método para abrir o banco de dados, se não existir, cria o banco "bancoAgenda"
    public static void abrirBanco(Activity act) {
        try {
            // Criação de um ContextWrapper para acessar o contexto da aplicação
            ContextWrapper cw = new ContextWrapper(act);
            // Abre ou cria o banco de dados "bancoAgenda" no modo privado
            db = cw.openOrCreateDatabase("bancoAgenda", MODE_PRIVATE, null);
        } catch (Exception ex) {
            // Caso ocorra algum erro ao abrir o banco de dados, mostra uma mensagem de erro
            CxMsg.mostrar("Erro ao abrir ou criar banco de dados", act);
        }
    }

    // Método para criar a tabela 'agenda' caso ela ainda não exista
    public static void abrirOuCriarTabela(Activity act) {
        try {
            // Criação da tabela 'agenda' com os campos id, nome, datas e horas
            db.execSQL("CREATE TABLE IF NOT EXISTS agenda(id INTEGER PRIMARY KEY, nome TEXT, datas DATE, horas TIME);");
        } catch (Exception ex) {
            // Caso ocorra um erro na criação da tabela, mostra uma mensagem de erro
            CxMsg.mostrar("Erro ao criar tabela", act);
        }
    }

    // Método para fechar a conexão com o banco de dados
    public static void fechar_db() {
        db.close();
    }

    // Método para inserir um novo registro na tabela 'agenda'
    public static void inserirRegistro(String nome, String data, String horas, Activity act) {
        abrirBanco(act);  // Abre o banco de dados antes de realizar qualquer operação
        try {
            // Formatação da data para o formato compatível com o banco de dados
            SimpleDateFormat sdfInput = new SimpleDateFormat("dd/MM/yy");  // Formato de entrada (dia/mês/ano)
            SimpleDateFormat sdfOutput = new SimpleDateFormat("yy/MM/dd");  // Formato de saída (ano/mês/dia)
            String dataF = "";
            try {
                // Tenta fazer o parsing da data recebida
                Date date = sdfInput.parse(data);
                // Formata a data para o formato compatível com o banco de dados
                dataF = sdfOutput.format(date);
            } catch (ParseException e) {
                e.printStackTrace();  // Caso ocorra um erro no parsing da data, imprime a pilha de erros
            }

            // Comando SQL para inserir um novo registro na tabela 'agenda'
            db.execSQL("INSERT INTO agenda(nome, datas, horas) VALUES('" + nome + "','" + dataF + " ',' " + horas + "')");
        } catch (Exception exp) {
            // Caso ocorra algum erro ao inserir o registro, mostra uma mensagem de erro
            CxMsg.mostrar("Erro ao inserir registro", act);
        } finally {
            // Exibe uma mensagem de sucesso ao salvar o registro
            CxMsg.mostrar("Salvo com sucesso", act);
        }
        fechar_db();  // Fecha o banco de dados após a operação
    }

    // Método para buscar todos os registros da tabela 'agenda' ordenados pela data
    public static Cursor buscarTdDados(Activity act) {
        fechar_db();  // Fecha o banco de dados se já estiver aberto
        abrirBanco(act);  // Abre o banco de dados novamente

        // Realiza uma consulta SQL para buscar todos os registros da tabela 'agenda'
        // O cursor irá armazenar os resultados da consulta
        cursor = db.query(
                "agenda",  // Nome da tabela
                new String[]{"nome", "datas", "horas"},  // Colunas a serem retornadas
                null,  // Cláusula WHERE (não utilizada, retorna todos os registros)
                null,  // Parâmetros para a cláusula WHERE (não utilizada)
                null,  // Cláusula GROUP BY (não utilizada)
                null,  // Cláusula HAVING (não utilizada)
                "datas ASC",  // Ordenação dos resultados pela data em ordem crescente
                null   // Limite de registros a serem retornados (não utilizado)
        );

        // Move o cursor para o primeiro registro
        cursor.moveToFirst();
        return cursor;  // Retorna o cursor com os dados obtidos
    }
}
