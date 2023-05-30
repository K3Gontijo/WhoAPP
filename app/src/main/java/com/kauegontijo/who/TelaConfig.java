package com.kauegontijo.who;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class TelaConfig extends AppCompatActivity {

    private ListView listView;

    //aqui criamos vetores que recebem os atributos de cada item das configurações
    private String mTitulos[] = {"Sair", "Editar Perfil"};
    private String mDescricoes[] = {"Clique aqui para deslogar", "Edite seu perfil aqui"};
    private int imagens[] = {R.drawable.ic_logout, R.drawable.baseline_edit_config};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tela_config);
        getSupportActionBar().hide(); //PARA ESCONDER A BARRA DO TÍTULO

        listView = findViewById(R.id.lista);

        //criamos um adapter para guardar os valor na lista
        MyAdapter adapter = new MyAdapter(this, mTitulos, mDescricoes, imagens);
        listView.setAdapter(adapter);


        //ação quando clicamos em algum item da lista
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position ==  0) {
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(TelaConfig.this);
                    alertDialog.setTitle("Sair");
                    alertDialog.setMessage("Quer mesmo sair?");
                    alertDialog.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            FirebaseAuth.getInstance().signOut();
                            Intent irLogin = new Intent(TelaConfig.this, TelaLogin.class);
                            startActivity(irLogin);
                        }
                    });
                    alertDialog.setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int which) {
                            dialogInterface.dismiss();
                        }
                    });
                    alertDialog.show();
                }
                if(position == 1) {
                    IrAlteraRegistro();
                }
                //aqui voce pode adicionar mais opções, basta mudar o valor do position
            }
        });
    }

    class MyAdapter extends ArrayAdapter<String> {

        Context context;
        String rTitulo[];
        String rDescricao[];
        int rImgs[];

        MyAdapter (Context c, String title[], String description[], int imgs[]) {
            super(c, R.layout.item_config_list, R.id.textView1, title);
            this.context = c;
            this.rTitulo = title;
            this.rDescricao = description;
            this.rImgs = imgs;

        }
        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View item = layoutInflater.inflate(R.layout.item_config_list, parent, false);
            ImageView images = item.findViewById(R.id.image);
            TextView myTitle = item.findViewById(R.id.textView1);
            TextView myDescription = item.findViewById(R.id.textView2);

            images.setImageResource(rImgs[position]);
            myTitle.setText(rTitulo[position]);
            myDescription.setText(rDescricao[position]);

            return item;
        }
    }

    //METODO PARA MANDAR DE VOLTA PARA O INICIO
    public void IrInicio (View v) {
        Intent irInicio = new Intent(this, TelaInicial.class);
        startActivity(irInicio);
    }

    public void IrAlteraRegistro(){
        Intent irAltera = new Intent(this, TelaAlteraRegistro.class);
        startActivity(irAltera);
    }
}