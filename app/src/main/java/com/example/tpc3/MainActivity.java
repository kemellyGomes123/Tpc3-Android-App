package com.example.tpc3;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private ArrayList<String> records;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = findViewById(R.id.listView);
        records = new ArrayList<>();

        // Adapter para exibir os registros na ListView
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, records);
        listView.setAdapter(adapter);

        // Botão para adicionar novos registros
        findViewById(R.id.btnAdd).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, FormActivity2.class);
            startActivityForResult(intent, 1);
        });

        // Quando um item da lista for clicado
        listView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedRecord = records.get(position);
            String email = extractEmail(selectedRecord); // Extrai o e-mail do registro clicado

            // Verificar se o e-mail não está vazio e abra o aplicativo de e-mail
            if (email != null && !email.isEmpty()) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                emailIntent.setData(Uri.parse("mailto:" + email)); // Define o endereço de e-mail
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Assunto do E-mail"); // Opcional: Adiciona um assunto ao e-mail

                // Verificar se existe um aplicativo de e-mail para abrir
                if (emailIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(emailIntent);
                } else {
                    Toast.makeText(MainActivity.this, "Não foi possível abrir o aplicativo de e-mail", Toast.LENGTH_SHORT).show();
                }
            } else {
                // Se o e-mail não for encontrado ou estiver vazio, exibe uma mensagem
                Toast.makeText(MainActivity.this, "E-mail não encontrado", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Método para extrair o e-mail do registro
    private String extractEmail(String record) {
        String email = null;
        String[] parts = record.split("\n");

        for (String part : parts) {
            if (part.contains("Email:")) {
                email = part.split(":")[1].trim(); // Extrai o e-mail
                break;
            }
        }

        return email;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
            String newRecord = data.getStringExtra("record");
            records.add(newRecord);
            adapter.notifyDataSetChanged();
        }
    }
}
