package com.seuapp.bancodados;

import android.app.Activity;
import android.os.Bundle;
import android.text.InputType;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends Activity {

    private EditText editNome;
    private EditText editPreco;
    private ListView listaProdutos;
    private ProdutoDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbHelper = new ProdutoDbHelper(this);

        LinearLayout raiz = new LinearLayout(this);
        raiz.setOrientation(LinearLayout.VERTICAL);

        int margem = dp(16);
        raiz.setPadding(margem, margem, margem, margem);

        TextView titulo = new TextView(this);
        titulo.setText("Cadastro de Produtos");
        titulo.setTextSize(24);

        editNome = new EditText(this);
        editNome.setHint("Nome do produto");

        editPreco = new EditText(this);
        editPreco.setHint("Preço");
        editPreco.setInputType(
                InputType.TYPE_CLASS_NUMBER |
                InputType.TYPE_NUMBER_FLAG_DECIMAL |
                InputType.TYPE_NUMBER_FLAG_SIGNED
        );

        Button botaoSalvar = new Button(this);
        botaoSalvar.setText("SALVAR");

        TextView subtitulo = new TextView(this);
        subtitulo.setText("Produtos cadastrados");
        subtitulo.setTextSize(18);

        listaProdutos = new ListView(this);

        raiz.addView(titulo);
        raiz.addView(editNome);
        raiz.addView(editPreco);
        raiz.addView(botaoSalvar);
        raiz.addView(subtitulo);

        raiz.addView(
                listaProdutos,
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        0,
                        1
                )
        );

        setContentView(raiz);

        botaoSalvar.setOnClickListener(v -> salvarProduto());

        atualizarLista();
    }

    private void salvarProduto() {

        String nome = editNome.getText().toString().trim();
        String precoTexto = editPreco.getText().toString().trim();

        if (nome.length() < 3) {
            Toast.makeText(
                    this,
                    "Nome inválido. Mínimo de 3 caracteres.",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        if (precoTexto.isEmpty()) {
            Toast.makeText(
                    this,
                    "Informe o preço.",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        double preco;

        try {
            preco = Double.parseDouble(precoTexto.replace(",", "."));
        } catch (NumberFormatException e) {
            Toast.makeText(
                    this,
                    "Preço inválido.",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        if (preco <= 0) {
            Toast.makeText(
                    this,
                    "Preço inválido. Informe um valor maior que zero.",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        boolean sucesso = dbHelper.inserirProduto(nome, preco);

        if (sucesso) {

            Toast.makeText(
                    this,
                    "Produto salvo com sucesso!",
                    Toast.LENGTH_SHORT
            ).show();

            editNome.setText("");
            editPreco.setText("");

            atualizarLista();

        } else {

            Toast.makeText(
                    this,
                    "Erro ao salvar produto.",
                    Toast.LENGTH_SHORT
            ).show();
        }
    }

    private void atualizarLista() {

        List<String> produtos = dbHelper.listarProdutos();

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_list_item_1,
                        produtos
                );

        listaProdutos.setAdapter(adapter);
    }

    private int dp(int valor) {
        float densidade =
                getResources().getDisplayMetrics().density;

        return (int) (valor * densidade);
    }
}
