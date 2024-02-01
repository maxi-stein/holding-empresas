package inspt.steindilella.HoldingManagement.service;

import inspt.steindilella.HoldingManagement.entity.Ciudad;
import inspt.steindilella.HoldingManagement.entity.Pais;

import java.util.List;

public interface UbicacionesServiceInterface {
    Pais getPaisById(Integer id);
    Ciudad getCiudadById(Integer id);

    List<Pais> getAllPaises();
    void save(Pais pais, Ciudad ciudad);
    void updatePais(Pais pais);
    void deletePais(Integer id);

    List<Ciudad> getAllCiudades();
    void saveCiudad(Ciudad ciudad);
    void updateCiudad(Ciudad ciudad);
    void deleteCiudad(Integer id);

}
