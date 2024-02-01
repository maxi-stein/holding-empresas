package inspt.steindilella.HoldingManagement.service;

import inspt.steindilella.HoldingManagement.dao.UbicacionesDAO;
import inspt.steindilella.HoldingManagement.entity.Ciudad;
import inspt.steindilella.HoldingManagement.entity.Pais;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UbicacionesService implements UbicacionesServiceInterface {

    @Autowired
    private UbicacionesDAO ubicacionesDAO;


    @Override
    public Pais getPaisById(Integer id) {
        return null;
    }

    @Override
    public Ciudad getCiudadById(Integer id) {
        return null;
    }

    @Override
    public List<Pais> getAllPaises() {
        return null;
    }

    @Override
    @Transactional
    public void save(Pais pais, Ciudad ciudad) {
        ubicacionesDAO.save(pais,ciudad);
    }

    @Override
    public void updatePais(Pais pais) {

    }

    @Override
    public void deletePais(Integer id) {

    }

    @Override
    public List<Ciudad> getAllCiudades() {
        return null;
    }

    @Override
    public void saveCiudad(Ciudad ciudad) {
    }

    @Override
    public void updateCiudad(Ciudad ciudad) {

    }

    @Override
    public void deleteCiudad(Integer id) {

    }
}
