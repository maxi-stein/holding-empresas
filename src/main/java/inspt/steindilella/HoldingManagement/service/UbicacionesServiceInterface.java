package inspt.steindilella.HoldingManagement.service;

import inspt.steindilella.HoldingManagement.entity.Ciudad;
import inspt.steindilella.HoldingManagement.entity.Pais;

import java.util.Set;

public interface UbicacionesServiceInterface {
    Pais getPaisById(Integer id);
    Ciudad getCiudadById(Integer id);

    Set<Pais> getAllPaises();
    void savePais(Pais pais);
    void updatePais(Pais pais);
    void deletePais(Integer id);
    void cambiarCapital(Pais pais, Ciudad ciudad);

    Set<Ciudad> getAllCiudades();
    void saveCiudad(Ciudad ciudad);
    void updateCiudad(Ciudad ciudad);
    void deleteCiudad(Integer id);

}
