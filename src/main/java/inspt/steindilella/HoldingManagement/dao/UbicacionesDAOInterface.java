package inspt.steindilella.HoldingManagement.dao;

import inspt.steindilella.HoldingManagement.entity.Ciudad;
import inspt.steindilella.HoldingManagement.entity.Pais;

import java.util.Set;

public interface UbicacionesDAOInterface {
    void save(Pais pais);
    void save(Ciudad ciudad);

    Pais getPaisById(Integer id);
    Ciudad getCiudadById(Integer id);

    Set<Pais> getAllPaises();
    Set<Ciudad> getAllCiudades();

    void updatePais(Pais pais);
    void updateCiudad(Ciudad ciudad);

    void deleteCiudad(Integer id);
    void deletePais(Integer id);


}
