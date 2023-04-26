package classes;

import sql.BD;
import sql.MeuResultSet;

import java.sql.SQLException;

public class Devedores {
    // Pesquisa:
    public static boolean cadastrado(int idDevedor) throws Exception {
        boolean retorno = false;
        try {
            String sql;

            sql = "SELECT * " +
                    "FROM AGIOTA.DEVEDORES " +
                    "WHERE IDDEVEDORES = ?";

            BD.COMANDO.prepareStatement(sql);

            BD.COMANDO.setInt(1, idDevedor);

            MeuResultSet resultado = (MeuResultSet) BD.COMANDO.executeQuery();

            retorno = resultado.first();
        } catch (SQLException erro) {
            throw new Exception("Erro ao procurar devedor");
        }

        return retorno;
    }

    // Insert:
    public static void incluir(Devedor devedor) throws Exception {
        if (devedor == null)
            throw new Exception("Devedor nao fornecido");

        try {
            String sql;
            sql = "INSERT INTO AGIOTA.DEVEDORES " +
                    "(IDDEVEDORES,IDADE,NOME,SEXO,CEP,DIVIDA) " +
                    "VALUES " +
                    "(?,?,?,?,?,?)";

            BD.COMANDO.prepareStatement(sql);

            BD.COMANDO.setInt(1, devedor.getIdDevedor());
            BD.COMANDO.setInt(2, devedor.getIdade());
            BD.COMANDO.setString(3, devedor.getNome());
            BD.COMANDO.setString(4, devedor.getNome());
            BD.COMANDO.setString(5, devedor.getSexo());
            BD.COMANDO.setFloat(6, devedor.getDivida());

            BD.COMANDO.executeUpdate();
            BD.COMANDO.commit();
        } catch (SQLException erro) {
            BD.COMANDO.rollback();
            throw new Exception("Erro ao inserir devedor.");
        }
    }

    // Delete:
    public static void excluir(int idDevedor) throws Exception {
        if (!cadastrado(idDevedor))
            throw new Exception("Nao cadastrado");

        try {
            String sql;

            sql = "DELETE FROM AGIOTA.DEVEDORES " +
                    "WHERE IDDEVEDORES=?";

            BD.COMANDO.prepareStatement(sql);

            BD.COMANDO.setInt(1, idDevedor);

            BD.COMANDO.executeUpdate();
            BD.COMANDO.commit();
        } catch (SQLException erro) {
            BD.COMANDO.rollback();
            throw new Exception("Erro ao excluir devedor");
        }
    }

    // Update:
    public static void alterar(Devedor devedor) throws Exception {
        if (devedor == null)
            throw new Exception("Desenvolvedor nao fornecido");

        if (!cadastrado(devedor.getIdDevedor()))
            throw new Exception("Nao cadastrado");

        try {
            String sql;

            sql = "UPDATE AGIOTA.DEVEDORES " +
                    "idade=?," +
                    "nome=?," +
                    "sexo=?," +
                    "cep=?," +
                    "divida=?" +
                    " WHERE idDevedores=?";
            // (IDDEVEDOR,IDADE,NOME,SEXO,CEP,DIVIDA)

            BD.COMANDO.prepareStatement(sql);

            BD.COMANDO.setInt(1, devedor.getIdade());
            BD.COMANDO.setString(2, devedor.getNome());
            BD.COMANDO.setString(3, devedor.getSexo());
            BD.COMANDO.setString(4, devedor.getCep());
            BD.COMANDO.setFloat(5, devedor.getDivida());
            BD.COMANDO.setInt(6, devedor.getIdDevedor());


            BD.COMANDO.executeUpdate();
            BD.COMANDO.commit();
        } catch (SQLException erro) {
            BD.COMANDO.rollback();
            throw new Exception("Erro ao atualizar dados do devedor");
        }
    }

    public static Devedor getDesenvolvedor(int idDevedor) throws Exception {
        Devedor devedor = null;

        try {
            String sql;

            sql = "SELECT * " +
                    "FROM AGIOTA.DEVEDOR " +
                    "WHERE IDDEVEDOR = ?";

            BD.COMANDO.prepareStatement(sql);

            BD.COMANDO.setInt(1, idDevedor);

            MeuResultSet resultado = (MeuResultSet) BD.COMANDO.executeQuery();

            if (!resultado.first())
                throw new Exception("Nao cadastrado");

            devedor = new Devedor(
                    resultado.getInt("idDevedores"),
                    resultado.getInt("idade"),
                    resultado.getString("nome"),
                    resultado.getString("sexo"),
                    resultado.getString("cep"),
                    resultado.getFloat("divida")
            );
        } catch (SQLException erro) {
            throw new Exception("Erro ao procurar devedor");
        }

        return devedor;
    }

    public static MeuResultSet getDesenvolvedores() throws Exception {
        MeuResultSet resultado = null;

        try {
            String sql;

            sql = "SELECT * " +
                    "FROM AGIOTA.IDDEVEDOR";

            BD.COMANDO.prepareStatement(sql);

            resultado = (MeuResultSet) BD.COMANDO.executeQuery();
        } catch (SQLException erro) {
            erro.printStackTrace();
            System.out.println(erro.getMessage());
        }

        return resultado;
    }
}
