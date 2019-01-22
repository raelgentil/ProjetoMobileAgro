package br.com.alisonrodrigo_rafaelgentil.agro.model.util;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import br.com.alisonrodrigo_rafaelgentil.agro.model.business.classes.ContatoBusiness;
import br.com.alisonrodrigo_rafaelgentil.agro.model.business.classes.PessoaBusiness;

public class Conexao {
    private static FirebaseAuth auth;
    private static FirebaseFirestore firestore;

    private Conexao() {}

    public static FirebaseAuth getFirebaseAuthInstance() {
        if (auth == null) {
            auth = FirebaseAuth.getInstance();
        }
        return auth;
    }
    public static FirebaseFirestore getFirebaseFirestoreInstance() {
        if (firestore == null) {
            firestore = FirebaseFirestore.getInstance();
        }
        return firestore;
    }
}
