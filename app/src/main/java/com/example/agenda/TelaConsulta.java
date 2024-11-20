package com.example.agenda;  // Pacote da aplicação

// Importação de bibliotecas necessárias para manipulação de banco de dados e UI
import androidx.appcompat.app.AlertDialog;  // Não é utilizado diretamente neste código, mas pode ser útil para alertas
import androidx.appcompat.app.AppCompatActivity;  // Classe base para Activities com suporte a ActionBar
import android.content.Intent;  // Usado para abrir novas telas/Activities
import android.database.Cursor;  // Usado para navegar nos registros do banco de dados
import android.database.sqlite.SQLiteDatabase;  // Usado para manipular o banco de dados SQLite
import android.os.Bundle;  // Usado para salvar e restaurar o estado da Activity
import android.view.View;  // Usado para capturar cliques e outros eventos de interface
import android.widget.Button;  // Usado para criar botões na interface
import android.widget.EditText;  // Usado para criar campos de texto na interface

import java.text.ParseException;  // Para capturar exceções de parsing de data
import java.text.SimpleDateFormat;  // Para formatar e analisar strings de data
import java.util.Date;  // Representa a data

// A classe TelaConsulta exibe os registros do banco de dados
public class TelaConsulta extends AppCompatActivity {

    // Declaração dos componentes da interface
    EditText et_nome;  // Campo de texto para mostrar o nome
    Button bt_anterior, bt_proximo, bt_voltar, bt_data, bt_horas;  // Botões de navegação e exibição de data/hora
    SQLiteDatabase db = null;  // Banco de dados SQLite
    Cursor cursor;  // Cursor usado para navegar pelos registros do banco

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Define o layout da Activity
        setContentView(R.layout.activity_tela_consulta);

        // Inicializa os componentes da interface
        et_nome = (EditText) findViewById(R.id.et_nome_consulta);
        bt_data = (Button) findViewById(R.id.btn_data_consulta);
        bt_horas = (Button) findViewById(R.id.btn_horas_consulta);
        bt_anterior = (Button) findViewById(R.id.bt_anterior);
        bt_proximo = (Button) findViewById(R.id.bt_proximo);
        bt_voltar = (Button) findViewById(R.id.bt_voltar_consulta);

        // Chama o método que busca todos os dados do banco
        cursor = BDs.buscarTdDados(this);

        // Se o cursor tiver dados, exibe-os
        if(cursor.getCount() != 0) {
            mostrarDados();
        } else {
            // Se não houver dados, exibe uma mensagem
            CxMsg.mostrar("Nada encontrado", this);
        }
    }

    // Método para fechar a tela atual e retornar à tela anterior
    public void fechar_tela(View v) {
        this.finish();  // Finaliza a Activity
    }

    // Método para navegar para o próximo registro no cursor
    public void proxRegistro(View v) {
        try {
            cursor.moveToNext();  // Move para o próximo registro
            mostrarDados();  // Exibe os dados do novo registro
        } catch (Exception ex) {
            // Se não houver mais registros, exibe uma mensagem
            if (cursor.isAfterLast()) {
                CxMsg.mostrar("Não possui mais registros", this);
            } else {
                CxMsg.mostrar("Erro ao navegar pelos registros", this);
            }
        }
    }

    // Método para navegar para o registro anterior no cursor
    public void antRegistro(View v) {
        try {
            cursor.moveToPrevious();  // Move para o registro anterior
            mostrarDados();  // Exibe os dados do novo registro
        } catch (Exception ex) {
            // Se não houver mais registros, exibe uma mensagem
            if (cursor.isBeforeFirst()) {
                CxMsg.mostrar("Não possui mais registros", this);
            } else {
                CxMsg.mostrar("Erro ao navegar pelos registros", this);
            }
        }
    }

    // Método para formatar a data para o formato "dd/MM/yy"
    private String formatarData(String data) {
        try {
            // Formato de entrada: "yy/MM/dd"
            SimpleDateFormat sdfInput = new SimpleDateFormat("yy/MM/dd");
            // Formato de saída: "dd/MM/yy"
            SimpleDateFormat sdfOutput = new SimpleDateFormat("dd/MM/yy");
            Date date = sdfInput.parse(data);  // Converte a string para o tipo Date
            return sdfOutput.format(date);  // Formata e retorna a data como string
        } catch (ParseException e) {
            e.printStackTrace();  // Em caso de erro no parsing da data
        }
        return "";  // Retorna uma string vazia caso ocorra erro
    }

    // Método para exibir os dados do registro atual
    public void mostrarDados() {
        // Exibe o nome no EditText
        et_nome.setText(cursor.getString(cursor.getColumnIndex("nome")));

        // Recupera a data e a formata para o formato "dd/MM/yy"
        String data = cursor.getString(cursor.getColumnIndex("datas"));
        String dataFormatada = formatarData(data); // Chama o método de formatação de data
        bt_data.setText(dataFormatada);  // Exibe a data no botão

        // Exibe a hora no botão
        bt_horas.setText(cursor.getString(cursor.getColumnIndex("horas")));
    }
}
