package com.example.agenda;  // Pacote da aplicação

// Importações das classes necessárias para manipular componentes da interface e diálogos
import androidx.appcompat.app.AlertDialog;  // Usado para exibir caixas de diálogo (não está sendo utilizado neste código)
import androidx.appcompat.app.AppCompatActivity;  // Classe base para as Activities que utilizam AppCompat
import android.content.Intent;  // Usado para iniciar novas Activities
import android.os.Bundle;  // Usado para salvar e restaurar o estado da Activity
import android.widget.Button;  // Classe para representar botões na interface
import android.widget.EditText;  // Classe para representar campos de texto
import android.view.View;  // Classe base para todos os componentes de interface do usuário
import java.sql.Date;  // Usado para representar datas (não está sendo utilizado diretamente)
import java.time.Instant;  // Usado para obter o timestamp atual (não está sendo utilizado diretamente)
import java.time.LocalDate;  // Usado para manipulação de datas (não está sendo utilizado diretamente)
import java.util.Calendar;  // Usado para manipular data e hora do sistema
import android.app.DatePickerDialog;  // Classe usada para exibir um seletor de data
import android.widget.DatePicker;  // Classe que representa o seletor de data
import android.app.TimePickerDialog;  // Classe usada para exibir um seletor de hora
import android.widget.TimePicker;  // Classe que representa o seletor de hora
import android.widget.*;  // Importa todos os widgets (não utilizado diretamente)

public class MainActivity extends AppCompatActivity {
    // Declaração dos componentes de interface
    EditText et_nome;  // Campo de texto para o nome
    Button bt_gravar, bt_consultar, bt_fechar, bt_data, bt_horas;  // Botões para gravar, consultar, fechar, selecionar data e hora

    // Variáveis para armazenar a data e a hora selecionadas
    String data, horas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Define o layout da Activity
        setContentView(R.layout.activity_main);

        // Inicializa os componentes da interface (EditText e Buttons)
        et_nome = (EditText) findViewById(R.id.et_nome);
        bt_consultar = (Button) findViewById(R.id.bt_consultar);
        bt_data = (Button) findViewById(R.id.bt_data);
        bt_horas = (Button) findViewById(R.id.bt_horas);
        bt_gravar = (Button) findViewById(R.id.bt_Gravar);
        bt_fechar = (Button) findViewById(R.id.bt_fechar);

        // Chama os métodos de banco de dados para abrir o banco e criar a tabela, caso necessário
        BDs.abrirBanco(this);
        BDs.abrirOuCriarTabela(this);
        BDs.fechar_db();

        // Configura os ouvintes de clique para os botões de data e hora
        bt_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abre o seletor de data quando o botão de data é clicado
                selectDate();
            }
        });

        bt_horas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abre o seletor de hora quando o botão de hora é clicado
                selectTime();
            }
        });
    }

    // Método para inserir um registro no banco de dados
    public void inserirRegistro(View v) {
        // Recupera os valores inseridos pelo usuário
        String st_nome, st_data, st_horas;
        st_nome = et_nome.getText().toString();  // Obtém o nome inserido
        st_data = data;  // Obtém a data selecionada
        st_horas = horas;  // Obtém a hora selecionada

        // Insere o registro no banco de dados
        BDs.inserirRegistro(st_nome, st_data, st_horas, this);

        // Limpa os campos da interface
        et_nome.setText(null);
        bt_data.setText("Data");
        bt_horas.setText("Hora");
    }

    // Método para abrir a tela de consulta
    public void abrir_tela_consulta(View v) {
        // Cria uma nova Intent para abrir a TelaConsulta
        Intent it_tela_consulta = new Intent(this, TelaConsulta.class);
        // Inicia a nova Activity
        startActivity(it_tela_consulta);
    }

    // Método para exibir o seletor de hora
    public void selectTime() {
        // Obtém a hora e o minuto atuais
        final Calendar calendar = Calendar.getInstance();
        int hora = calendar.get(Calendar.HOUR_OF_DAY);
        int minuto = calendar.get(Calendar.MINUTE);

        // Cria o TimePickerDialog para selecionar a hora
        TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                        // Formata a hora selecionada e exibe no botão
                        String horaSelecionada = String.format("%02d:%02d", hourOfDay, minute);
                        bt_horas.setText(horaSelecionada);
                        // Armazena a hora selecionada na variável
                        horas = horaSelecionada;
                    }
                }, hora, minuto, true);
        // Exibe o seletor de hora
        timePickerDialog.show();
    }

    // Método para exibir o seletor de data
    public void selectDate() {
        // Obtém a data atual
        final Calendar calendar = Calendar.getInstance();
        int ano = calendar.get(Calendar.YEAR);
        int mes = calendar.get(Calendar.MONTH);
        int dia = calendar.get(Calendar.DAY_OF_MONTH);

        // Cria o DatePickerDialog para selecionar a data
        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        // Formata a data selecionada e exibe no botão
                        String dataSelecionada = day + "/" + (month + 1) + "/" + year;
                        bt_data.setText(dataSelecionada);
                        // Armazena a data selecionada na variável
                        data = dataSelecionada;
                    }
                }, ano, mes, dia);
        // Exibe o seletor de data
        datePickerDialog.show();
    }
}
