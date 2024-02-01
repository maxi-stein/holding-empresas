package inspt.steindilella.HoldingManagement.dao;

import inspt.steindilella.HoldingManagement.entity.Ciudad;
import inspt.steindilella.HoldingManagement.entity.Pais;

import java.util.List;

public interface UbicacionesDAOInterface {
    void save(Pais pais,Ciudad ciudad);

    Pais getPaisById(Integer id);
    Ciudad getCiudadById(Integer id);

    List<Pais> getAllPaises();
    List<Ciudad> getAllCiudades();

    void updatePais(Pais pais);
    void updateCiudad(Ciudad ciudad);

    void deleteCiudad(Ciudad ciudad);


}
