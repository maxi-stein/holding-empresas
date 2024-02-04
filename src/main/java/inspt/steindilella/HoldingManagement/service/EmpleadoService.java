package inspt.steindilella.HoldingManagement.service;

import inspt.steindilella.HoldingManagement.dao.EmpleadosDAOInterface;
import inspt.steindilella.HoldingManagement.dao.EmpresaDAOInterface;
import inspt.steindilella.HoldingManagement.entity.Empleado;
import inspt.steindilella.HoldingManagement.entity.Empresa;
import inspt.steindilella.HoldingManagement.entity.Vendedor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Service
public class EmpleadoService implements EmpleadoServiceInterface{

    private EmpleadosDAOInterface empleadoDao;
    private EmpresaDAOInterface empresaDao;

    @Autowired
    public EmpleadoService(EmpleadosDAOInterface empDao, EmpresaDAOInterface empresaDao) {
        this.empleadoDao = empDao;
        this.empresaDao = empresaDao;
    }

    @Override
    public Empleado getById(Integer id) {
        return empleadoDao.getById(id);
    }

    @Override
    public Set<Empleado> getAll() {
        return empleadoDao.getAll();
    }

    @Override
    public Set<Empleado> getEmpleadosPorEmpresa(Integer id) {
        return empleadoDao.getEmpleadosPorEmpresa(id);
    }

    @Override
    public LocalDate getFechaAsesorEmpresa(Integer idAsesor, Integer idEmpresa) {
        return empleadoDao.getFechaAsesorEmpresa(idAsesor, idEmpresa);
    }

    @Override
    public LocalDate getFechaCaptado(Integer idPadre, Integer idCaptado) {
        return empleadoDao.getFechaCaptado(idPadre,idCaptado);
    }
    
    @Override
    public Set<Empresa> getEmpresasAsesoradas(Integer id) {
        return empleadoDao.getEmpresasAsesoradas(id);
    }
    @Override
    public Set<Vendedor> getVendedoresCaptados(Integer idPadre) {
        return empleadoDao.getVendedoresCaptados(idPadre);
    }

    @Override
    public void agregarVendedorCaptado(Integer idVendedor, Integer idVendedorCaptado, LocalDate fechaCaptado) {
        //obtengo las empresas a las cuales trabaja cada vendedor
        Integer idEmpresaVendedor = empresaDao.getEmpresaByVendedorId(idVendedor).getId();
        Integer idEmpresaVendedorCaptado = empresaDao.getEmpresaByVendedorId(idVendedorCaptado).getId();

        //verifico que sean las mismas
        if(Objects.equals(idEmpresaVendedor, idEmpresaVendedorCaptado)){

            //verifico que el vendedorCaptado no haya previamente captado al vendedor padre o alguno de sus captadores
            if(vendedorYaFueCaptado(idVendedor,idVendedorCaptado)){

                //todo: manejar exception de vendedor captado en realidad captó al vendedor padre
                System.out.println("Error. El vendedor id "+ idVendedor + " fue captado por el vendedor id " +  idVendedorCaptado + " o por alguno de sus captadores");
            }
            else{
                empleadoDao.agregarVendedorCaptado(idVendedor,idVendedorCaptado,fechaCaptado);
            }
        }
        else{
            //todo: manejar exception vendedores distintas empresas
            System.out.println("Los vendedores no pertenecen a la misma empresa");
        }
    }

    //Si existe un vendedor A que capta a un vendedor B, este metodo verifica que vendedor B no haya captado previamente a vendedor A o alguno de sus captadores
    private boolean vendedorYaFueCaptado(Integer idVendedor, Integer idVendedorCaptado) {

        //verifico que el vendedor B no fue captado por nadie
        if(empleadoDao.getCaptadorDelVendedor(idVendedorCaptado) == null){

            //si no fue captado por nadie, verifico que no haya captado al vendedor A o alguno de sus captadores
            Set<Vendedor> v = empleadoDao.getVendedoresCaptados(idVendedorCaptado);

            for(Vendedor element : v){
                //"¿captó al vendedor A?"
                if(element.getId().equals(idVendedor)){
                    return true;
                }
                //si no es asi, itero sobre sus vendedores captados para ver si alguno de ellos ya captaron al vendedor A
                if(vendedorYaFueCaptado(idVendedor,element.getId())){
                    return true;
                }
            }
        }

        //vendedor B ya fue captado por alguien más
        return true;
    }

    @Override
    public void save(Empleado emp) {
        empleadoDao.save(emp);
    }

    @Override
    public void update(Empleado emp) {
        empleadoDao.update(emp);
    }

    @Override
    public void delete(Empleado emp) {
        empleadoDao.delete(emp);
    }
}
