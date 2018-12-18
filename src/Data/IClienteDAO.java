package Data;

import Business.Utilizador.Cliente;

public interface IClienteDAO extends DAOFacede {
    public Cliente getCliente(int id);
}
