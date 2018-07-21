package com.laika.miaudota.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.JsonArrayRequest;
import com.laika.miaudota.adapters.RecyclerViewAdapter;
import com.laika.miaudota.R;
import com.laika.miaudota.models.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    private final String JSON_URL = "https://projeto-laika2.herokuapp.com/animais.json";
    private JsonArrayRequest request;
    private RequestQueue requestQueue;
    //private ArrayList<Animal> listaAnimal;
    private List<Animal> listaAnimal;
    private RecyclerView recyclerView;

    private RecyclerViewAdapter adapter;
    private List<Animal> mData = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaAnimal = new ArrayList<Animal>();
        recyclerView = findViewById(R.id.recyclerviewid);
        jsonrequest();

        //mData = Arrays.asList(getResources().getStringArray(R.array.listaAnimal));
        //mData = Arrays.asList();
        //adapter = new RecyclerViewAdapter(this, (ArrayList<Animal>) mData);
        adapter = new RecyclerViewAdapter(this, (ArrayList<Animal>) listaAnimal);
        recyclerView.setAdapter(adapter);
        Toast.makeText(this, "onCreate - Fim", Toast.LENGTH_SHORT);
    }

    private void jsonrequest(){

        request = new JsonArrayRequest(JSON_URL, new Response.Listener<JSONArray>(){

            @Override
            public void onResponse(JSONArray response){

                JSONObject jsonObject = null;
                System.out.println("Teste");
                for(int i=0; i<response.length(); i++){
                    try{
                        jsonObject = response.getJSONObject(i);
                        if((jsonObject.getString("porte").equalsIgnoreCase("null"))) {
                            Gato gato  = new Gato();
                            gato.setNome(jsonObject.getString("nome"));
                            gato.setSexo(jsonObject.getString("sexo"));
                            gato.setPelagem(jsonObject.getString("pelagem"));
                            gato.setDescricao(jsonObject.getString("descricao"));
                            gato.setEndereco(jsonObject.getString("endereco"));
                            gato.setFotoUrl(jsonObject.getString("foto_url"));
                            gato.setIdade(jsonObject.getInt("idade"));
                            gato.setPeso(jsonObject.getDouble("peso"));
                            gato.setVermifugado(jsonObject.getBoolean("vermifugado"));
                            gato.setVacinado(jsonObject.getBoolean("vacinado"));
                            listaAnimal.add(gato);
                            System.out.println(gato.getNome());
                        }
                        else{
                            Cao cao = new Cao();
                            cao.setNome(jsonObject.getString("nome"));
                            cao.setSexo(jsonObject.getString("sexo"));
                            cao.setPelagem(jsonObject.getString("pelagem"));
                            cao.setDescricao(jsonObject.getString("descricao"));
                            cao.setEndereco(jsonObject.getString("endereco"));
                            cao.setFotoUrl(jsonObject.getString("foto_url"));
                            cao.setIdade(jsonObject.getInt("idade"));
                            cao.setPeso(jsonObject.getDouble("peso"));
                            cao.setVermifugado(jsonObject.getBoolean("vermifugado"));
                            cao.setVacinado(jsonObject.getBoolean("vacinado"));
                            cao.setPorte(jsonObject.getString("porte"));
                            listaAnimal.add(cao);
                            System.out.println(cao.getNome());
                        }

                    } catch(JSONException e){
                        e.printStackTrace();
                    }
                }
                setuprecyclerview((ArrayList<Animal>) listaAnimal);
            }
        }, new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){

            }
        });

        requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(request);
    }

    private void setuprecyclerview(ArrayList<Animal> listaAnimal){

        RecyclerViewAdapter myadapter = new RecyclerViewAdapter(this, listaAnimal);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myadapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_activity, menu);

        MenuItem menuItem = menu.findItem(R.id.pesquisa_animaisId);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(this);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String novoTexto) {
        String pesquisa = novoTexto.toLowerCase();
        List<Animal> newMData = new ArrayList<>();

        /*for(Animal animal: mData){
            if(animal.getSexo().toLowerCase().contains(pesquisa)){
                newMData.add(animal);
            }
        }*/

        for(Animal animal: listaAnimal){
            if(animal.getSexo().toLowerCase().contains(pesquisa)){
                newMData.add(animal);
            }
        }

        adapter.updateList(newMData);

        return true;
}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch(id){
            case R.id.lista_animaisId:
                Toast.makeText(this, "Você já está visualizando o item selecionado.", Toast.LENGTH_SHORT);
                break;
            case R.id.cadastrar_animalId:
                Toast.makeText(this, "Página de cadastro em desenvolvimento.", Toast.LENGTH_SHORT);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
