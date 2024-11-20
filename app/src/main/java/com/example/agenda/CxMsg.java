package com.example.agenda;  // Pacote da aplicação

// Importação das classes necessárias para criar uma caixa de mensagem (AlertDialog)
import android.app.Activity;  // Necessário para acessar o contexto da Activity
import androidx.appcompat.app.AlertDialog;  // Necessário para criar a caixa de diálogo (AlertDialog)

public class CxMsg {  // Classe CxMsg que contém um método estático para exibir mensagens

        // Método estático para exibir uma mensagem em uma caixa de diálogo
        public static void mostrar(String txt, Activity act) {
                // Criação de um AlertDialog.Builder que serve para construir um AlertDialog
                AlertDialog.Builder adb = new AlertDialog.Builder(act);

                // Define o texto da mensagem que será exibido no AlertDialog
                adb.setMessage(txt);

                // Adiciona um botão "Ok"
                adb.setNeutralButton("Ok", null);

                // Exibe o AlertDialog na tela
                adb.show();
        }
}
