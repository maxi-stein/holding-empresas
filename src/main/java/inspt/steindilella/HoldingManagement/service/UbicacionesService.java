package inspt.steindilella.HoldingManagement.service;

import inspt.steindilella.HoldingManagement.dao.UbicacionesDAOInterface;
import inspt.steindilella.HoldingManagement.entity.Ciudad;
import inspt.steindilella.HoldingManagement.entity.Pais;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class UbicacionesService implements UbicacionesServiceInterface {

    private UbicacionesDAOInterface ubicacionesDAO;

    @Autowired
    public UbicacionesService(UbicacionesDAOInterface ubicacionesDAO) {
        this.ubicacionesDAO = ubicacionesDAO;
    }

    @Override
    public Pais getPaisById(Integer id) {
        return ubicacionesDAO.getPaisById(id);
    }

    @Override
    public Ciudad getCiudadById(Integer id) {
        return ubicacionesDAO.getCiudadById(id);
    }

    @Override
    public Set<Pais> getAllPaises() {
        return ubicacionesDAO.getAllPaises();
    }

    @Override
    @Transactional
    public void savePais(Pais pais) {
        ubicacionesDAO.save(pais);
    }

    @Override
    public void updatePais(Pais pais) {
        ubicacionesDAO.updatePais(pais);
    }

    @Override
    public void deletePais(Integer id) {
        ubicacionesDAO.deletePais(id);
    }

    @Override
    @Transactional
    public void cambiarCapital(Pais pais, Ciudad ciudad) {
        Set<Ciudad> ciudades = pais.getCiudades();

        if(ciudades.contains(ciudad)){
            pais.setCapital(ciudad);
            ciudad.setPais_ciudad(pais);
            ubicacionesDAO.updatePais(pais);
        }
        else{
            //todo: manejar error
        }
    }

    @Override
    public Set<Ciudad> getAllCiudades() {
        return ubicacionesDAO.getAllCiudades();
    }

    @Override
    public void saveCiudad(Ciudad ciudad) {
        ubicacionesDAO.save(ciudad);
    }

    @Override
    public void updateCiudad(Ciudad ciudad) {
        ubicacionesDAO.updateCiudad(ciudad);
    }

    @Override
    public void deleteCiudad(Integer id) {
        ubicacionesDAO.deleteCiudad(id);
    }
}
