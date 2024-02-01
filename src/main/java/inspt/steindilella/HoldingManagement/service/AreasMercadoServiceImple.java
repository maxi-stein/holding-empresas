package inspt.steindilella.HoldingManagement.service;

import inspt.steindilella.HoldingManagement.dao.AreasMercadoDAO;
import inspt.steindilella.HoldingManagement.entity.AreasMercado;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AreasMercadoServiceImple implements AreasMercadoService {

    private AreasMercadoDAO areasDAO;

    @Override
    public AreasMercado getById() {
        return null;
    }

    @Override
    public List<AreasMercado> getAll() {
        return null;
    }

    @Override
    @Transactional
    public void save(AreasMercado area) {
        areasDAO.save(area);
    }

    @Override
    public void update(AreasMercado area) {

    }

    @Override
    public void delete(Integer id) {

    }
}
