package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserService {
    private final Map<String, String> users = new HashMap<>(); // Agora a chave é o CPF
    private final Map<String, User> userDetails = new HashMap<>(); // A chave continua sendo o CPF

    public boolean register(String cpf, String password, String nome, String sobrenome, String cpfInformado, String gmail) {
        if (users.containsKey(cpf)) return false;
        users.put(cpf, password);
        userDetails.put(cpf, new User(cpf, password, cpfInformado, nome, sobrenome, gmail)); // Usando CPF como username no User
        return true;
    }

    public boolean authenticate(String cpf, String password) {
        return users.containsKey(cpf) && users.get(cpf).equals(password);
    }

    public List<String> getAllUsers() {
        return new ArrayList<>(users.keySet());
    }

    public User getUserDetails(String cpf) {
        return userDetails.get(cpf);
    }
}