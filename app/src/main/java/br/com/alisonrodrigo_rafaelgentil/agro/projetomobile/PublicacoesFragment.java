package br.com.alisonrodrigo_rafaelgentil.agro.projetomobile;


import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import br.com.alisonrodrigo_rafaelgentil.agro.model.entidades.classes.Publicacao;


/**
 * A simple {@link Fragment} subclass.
 */
public class PublicacoesFragment extends Fragment {


    private Button buttonPublicar;
    private PublicacoesUser publicacoesUser;
    private ListView list_publicacoes;
    private ArrayList<Publicacao> publicacoes;
    private ArrayAdapter<Publicacao> adapterPublicacoes;
    private ValueEventListener valueEventListenerPublicacoes;
    private FirebaseFirestore firebase;
    private DocumentReference documentReference;
    private  final String TAG=  "Firelog";

    public PublicacoesFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_publicacoes, container, false);


        buttonPublicar = (Button) view.findViewById(R.id.button_publicar);
        list_publicacoes = (ListView) view.findViewById(R.id.listview_Publicacoes);

        publicacoes = new ArrayList<>();

        adapterPublicacoes = new PublicacoesAdapter(getContext(),publicacoes);
        list_publicacoes.setAdapter(adapterPublicacoes);

        firebase = FirebaseFirestore.getInstance();
        documentReference = firebase.collection("publicacao").document();

        buttonPublicar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                publicacoesUser = new PublicacoesUser();
                fragmentManager.beginTransaction().replace(R.id.layout_principal, publicacoesUser).commit();
            }

        });

        receberDados();

        return  view;
    }

    private void receberDados(){
        firebase.collection("publicacao").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(QuerySnapshot DocumentSnapshots, FirebaseFirestoreException e) {
                for(DocumentChange dc: DocumentSnapshots.getDocumentChanges()){
                    if(dc.getType() == DocumentChange.Type.ADDED){
                        Publicacao publicacoesNovas = dc.getDocument().toObject(Publicacao.class);
                        publicacoes.add(publicacoesNovas);

                    }
                    adapterPublicacoes.notifyDataSetChanged();
                }
            }
        });

    }

    private void outraManeira(){
        valueEventListenerPublicacoes = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                publicacoes.clear();

                for(DataSnapshot dados: dataSnapshot.getChildren()){

                    Publicacao publicacoesNovas = dados.getValue(Publicacao.class);

                    publicacoes.add(publicacoesNovas);

                }
                adapterPublicacoes.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

    }
}
