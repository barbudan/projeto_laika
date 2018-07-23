package com.laika.miaudota.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.JsonArrayRequest;
import com.laika.miaudota.adapters.RecyclerViewAdapter;
import com.laika.miaudota.R;
import com.laika.miaudota.comunicacao.CachorroComunicacao;
import com.laika.miaudota.comunicacao.ICallback;
import com.laika.miaudota.models.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    // Constante que define a URL onde se encontra o banco de dados
    // Lista de Animais definida para ser exclusivamente um ArrayList
    //private ArrayList<Animal> listaAnimal;


    private RecyclerView recyclerView;
    private Button btn_criar;
    private ArrayList<Animal> listaAnimal;

    // Adaptador para o filtro da busca
    private RecyclerViewAdapter adapter;

    // Responsável por criar Activity atual
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_criar = (Button) findViewById(R.id.btn_cadastrar);
        btn_criar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CadastroActivity.class);
                startActivity(intent);

            }
        });
        listaAnimal = new ArrayList<Animal>();
        recyclerView = findViewById(R.id.recyclerviewid);
        jsonrequest();

        //Atribuição do adaptador
        //adapter = new RecyclerViewAdapter(this, listaAnimal);
        recyclerView.setAdapter(adapter);
    }


    // Função que realiza o Request no Banco de dados e adiciona cada Animal na lista
    private void jsonrequest(){
        CachorroComunicacao comm = new CachorroComunicacao(MainActivity.this);
        comm.listar(new ICallback() {
            @Override
            public void onSucess(Object object) {
                setuprecyclerview((ArrayList<Animal>) object);
                listaAnimal = (ArrayList<Animal>) object;
                adapter = new RecyclerViewAdapter(MainActivity.this, (ArrayList<Animal>) object);
            }

            @Override
            public void onFail(Object object) {

            }
        });

    }

    private void setuprecyclerview(ArrayList<Animal> listaAnimal){

        RecyclerViewAdapter myadapter = new RecyclerViewAdapter(this, listaAnimal);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myadapter);

    }

    // Cria o Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity, menu);

        //Mapeamento e declaração do SearchView
        MenuItem menuItem = menu.findItem(R.id.pesquisa_animaisId);
        SearchView searchView = ( SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }

    // Realiza uma ação a depender da opção escolhida no Menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id){
            case R.id.lista_animaisId:
                Toast.makeText(this, "Você já está visualizando o item selecionado.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cadastrar_animalId:
                Intent intent = new Intent(MainActivity.this, CadastroActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    //Implementação do método concernente ao evento onQueryTextChange do SearchView



    @Override
    public boolean onQueryTextChange(String newText) {
        String pesquisa = newText.toLowerCase();
        List<Animal> newMData = new ArrayList<>(); //Nova lista onde serão adicionados somente os objetos que correspondem à pesquisa

        //Percorre o ArrayList adicionando os animais que se enquadram na pesquisa
        for(Animal animal: listaAnimal){
            String idade = String.valueOf(animal.getIdade()+" anos");            String vermifugado = "Não";
            String vacinado = "Não";

            //Tratamento dos atributos booleanos (vermifugado e vacinado)
            if(animal.isVermifugado())
                vermifugado = "Vermifugado";

            if(animal.isVacinado())
                vacinado = "Vacinado";

            pesquisa = tiraAcento(pesquisa); // Retira acentos do que foi digitado na pesquisa

            if(     tiraAcento(animal.getSexo().toLowerCase()).contains(pesquisa) ||
                    tiraAcento(animal.getDescricao().toLowerCase()).contains(pesquisa) ||
                    tiraAcento(animal.getEndereco().toLowerCase()).contains(pesquisa) ||
                    tiraAcento(animal.getNome().toLowerCase()).contains(pesquisa) ||
                    tiraAcento(animal.getPelagem().toLowerCase()).contains(pesquisa) ||
                    tiraAcento(idade.toLowerCase()).contains(pesquisa) ||
                    tiraAcento(vermifugado.toLowerCase()).contains(pesquisa) ||
                    tiraAcento(vacinado.toLowerCase()).contains(pesquisa) ||
                    ((animal instanceof Cao) && tiraAcento(((Cao) animal).getPorte().toLowerCase()).contains(pesquisa))
                    ){
                newMData.add(animal);
            }
        }

        //Atualiza o adapter e seta o recyclerView com o resultado da pesquisa
        adapter.updateList(newMData);
        recyclerView.setAdapter(adapter);

        return true;
    }

    // Retira Acentos de uma String
    public String tiraAcento(String str){
        String nfdNormalizedString = Normalizer.normalize(str, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(nfdNormalizedString).replaceAll("");
    }
}
