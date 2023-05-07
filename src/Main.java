import classes.Logradouro;
import classes.ClienteWS;
import classes.Tela;

public class Main {
    public static void main(String[] args) {
        /*Logradouro logradouro = (Logradouro)ClienteWS.getObjeto(Logradouro.class, "https://api.postmon.com.br/v1/cep", "13033205");
        System.out.println (logradouro);*/
        new Tela();
    }
}