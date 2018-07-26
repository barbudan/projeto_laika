package com.laika.miaudota.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.laika.miaudota.R;
import com.laika.miaudota.comunicacao.CachorroComunicacao;
import com.laika.miaudota.comunicacao.ICallback;
import com.laika.miaudota.models.Animal;
import com.laika.miaudota.models.Cao;

public class CadastroCachorroActivity extends Fragment{

    private RequestQueue queue;
    private EditText nomeCachorro;
    private EditText idadeCachorro;
    private EditText pesoCachorro;
    private RadioGroup sexoCachorro;
    private RadioButton sexoMachoCachorro;
    private RadioButton sexoFemeaCachorro;
    private Spinner pelagemCachorro;
    private Spinner porteCachorro;
    private CheckBox vermifugadoCachorro;
    private CheckBox vacinadoCachorro;
    private EditText fotoCachorro;
    private EditText aboutCachorro;
    private EditText enderecoCachorro;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        super.onCreateView(inflater, container,savedInstanceState);
        View v = inflater.inflate(R.layout.cadastro_cachorro, container, false);

        Button btnCadastrar;

        queue = Volley.newRequestQueue(v.getContext());
        nomeCachorro = (EditText) v.findViewById(R.id.nome_cachorro);
        idadeCachorro = (EditText) v.findViewById(R.id.idade_cachorro);
        pesoCachorro = (EditText) v.findViewById(R.id.peso_cachorro);
        sexoCachorro = (RadioGroup) v.findViewById(R.id.sexo_cachorro);
        sexoMachoCachorro = (RadioButton) v.findViewById(R.id.sexo_macho_cachorro);
        sexoFemeaCachorro = (RadioButton) v.findViewById(R.id.sexo_femea_cachorro);
        pelagemCachorro = (Spinner) v.findViewById(R.id.pelagem_cachorro);
        porteCachorro = (Spinner) v.findViewById(R.id.porte_cachorro);
        vermifugadoCachorro = (CheckBox) v.findViewById(R.id.vermifugado_cachorro);
        vacinadoCachorro = (CheckBox) v.findViewById(R.id.vacinado_cachoro);
        enderecoCachorro = (EditText) v.findViewById(R.id.endereco_cachorro);
        fotoCachorro = (EditText) v.findViewById(R.id.foto_cachorro);
        aboutCachorro = (EditText) v.findViewById(R.id.about_cachorro);

        btnCadastrar = (Button) v.findViewById(R.id.btn_cadastrar_cachorro);
        btnCadastrar.setOnClickListener(cliqueBotao);

        return v;
    }

    private View.OnClickListener cliqueBotao = new View.OnClickListener() {

        @Override
        public void onClick(View view) {

            try{
                enviaRequest();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    };

    private void enviaRequest(){
        Animal cachorro = new Cao(
                this.nomeCachorro.getText().toString(),
                (this.sexoCachorro.getCheckedRadioButtonId()== this.sexoMachoCachorro.getId()?this.sexoMachoCachorro.getText().toString():this.sexoFemeaCachorro.getText().toString()),
                String.valueOf(this.pelagemCachorro.getSelectedItem()),
                this.aboutCachorro.getText().toString(),
                Integer.parseInt(this.idadeCachorro.getText().toString()),
                Double.parseDouble(this.pesoCachorro.getText().toString()),
                this.vermifugadoCachorro.isChecked(),
                this.vacinadoCachorro.isChecked(),
                this.enderecoCachorro.getText().toString(),
                this.fotoCachorro.getText().toString(),
                String.valueOf(this.porteCachorro.getSelectedItem())
        );


        CachorroComunicacao comm = new CachorroComunicacao(this.queue);
        comm.cadastrar(cachorro, new ICallback() {
            @Override
            public void onSucess(Object object) {
                Toast.makeText(getActivity(), "Cão Cadastrado!", Toast.LENGTH_LONG).show();
                getActivity().finish();
            }

            @Override
            public void onFail(Object object) {

            }
        });
    }
}
