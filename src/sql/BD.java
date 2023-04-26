package sql;

import java.io.File;

public class BD {
    public static final MeuPreparedStatement COMANDO;
    static final String user = "ᖁቺཟౖौل̼";
    static final String password = "ᣀᕱኜས౺ॏٓ̾";
    static
    {
        MeuPreparedStatement comando = null;

        try
        {
            comando =
                    new MeuPreparedStatement (
                            "com.microsoft.sqlserver.jdbc.SQLServerDriver",
                            "jdbc:sqlserver://regulus.cotuca.unicamp.br;databasename=" + Compressor.Uncompress(user),
                            Compressor.Uncompress(user), Compressor.Uncompress(password));
        }
        catch (Exception erro)
        {
            System.out.println(erro);
            System.err.println (erro.getMessage());
            System.exit(0); // aborta o programa
        }

        COMANDO = comando;
    }
}
