package inspt.steindilella.HoldingManagement.service;

import inspt.steindilella.HoldingManagement.dao.AreasMercadoDAOInterface;
import inspt.steindilella.HoldingManagement.dao.EmpleadosDAOInterface;
import inspt.steindilella.HoldingManagement.dao.EmpresaDAOInterface;
import inspt.steindilella.HoldingManagement.entity.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

@Service
public class EmpleadoService implements EmpleadoServiceInterface{

    private EmpleadosDAOInterface empleadoDao;
    private EmpresaDAOInterface empresaDao;

    private AreasMercadoDAOInterface areaDAO;

    @Autowired
    public EmpleadoService(EmpleadosDAOInterface empDao, EmpresaDAOInterface empresaDao, AreasMercadoDAOInterface area) {
        this.empleadoDao = empDao;
        this.empresaDao = empresaDao;
        this.areaDAO = area;
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
    public Set<Empleado> getUsuariosgetCredencialesAll() {
        return empleadoDao.getUsuariosgetCredencialesAll();
    }
    @Override
    public Set<Empleado> getEmpleadosPorEmpresa(Integer id) {
        return empleadoDao.getEmpleadosPorEmpresa(id);
    }

    @Override
    public Set<Administrador> getAdministradores() {
        return empleadoDao.getAdministradores();
    }

    @Override
    public Set<Asesor> getAsesores() {
        return empleadoDao.getAsesores();
    }

    @Override
    public Set<Vendedor> getVendedores() {
        return empleadoDao.getVendedores();
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
    public Set<AsesorEmpresa> getEmpresasAsesoradas(Integer idAsesor) {
        return empleadoDao.getEmpresasAsesoradas(idAsesor);
    }

    @Override
    public Set<AreasMercado> getAreasAsesoradasPorAsesor(Integer idAsesor) {
        return empleadoDao.getAreasAsesoradasPorAsesor(idAsesor);
    }

    @Override
    public Set<VendedorCaptado> getVendedoresCaptados(Integer idPadre) {
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
            if(!vendedorEsCaptable(idVendedor,idVendedorCaptado)){

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

    @Override
    public void eliminarVendedorCaptado(Integer idVendedor, Integer idVendedorCaptado) {

        if(idVendedor != null && idVendedorCaptado != null){

            Set<VendedorCaptado> vcapt =  empleadoDao.getVendedoresCaptados(idVendedor);

            if(vcapt.contains(empleadoDao.getVendedorCaptado(idVendedor,idVendedorCaptado))){
                empleadoDao.eliminarVendedorCaptado(idVendedor,idVendedorCaptado);
            }
            else{
                //todo: manejar exception
                System.out.println("El vendedor captado no se encuentra en el listado de vendedores captados por el vendedor padre.");
            }
        }
        else{
            //todo: manejar exception
            System.out.println("Error, alguno de los id son nulos.");
        }
    }

    @Override
    public void eliminarTodosLosVendedoresCaptados(Integer idVendedor) {
        if(idVendedor != null){
            empleadoDao.eliminarTodosLosVendedoresCaptados(idVendedor);
        }
    }

    //Si existe un vendedor A que capta a un vendedor B, este metodo verifica que vendedor B no haya captado previamente a vendedor A o alguno de sus captadores
    public boolean vendedorEsCaptable(Integer idVendedor, Integer idVendedorCaptado) {

        //verifico que el vendedor B no fue captado por nadie
        if(empleadoDao.getCaptadorDelVendedor(idVendedorCaptado) == null){

            //si no fue captado por nadie, verifico que no haya captado al vendedor A o alguno de sus captadores
            Set<VendedorCaptado> v = empleadoDao.getVendedoresCaptados(idVendedorCaptado);

            for(VendedorCaptado vc : v){
                //"¿captó al vendedor A?"
                if(vc.getVendedorPadre().getId().equals(idVendedor)){
                    return false;
                }
                //si no es asi, itero sobre sus vendedores captados para ver si alguno de ellos ya captaron al vendedor A
                if(vc.getVendedorCaptado() != null){
                    if(vendedorCaptableIteracion(idVendedor,vc.getVendedorCaptado().getId())){
                        return true;
                    }
                }
            }

            //si no entra al for porque no tiene captados, es captable
            return true;
        }

        //vendedor B ya fue captado por alguien más
        return false;
    }

    @Override
    public Vendedor getCaptador(Integer idVendedor) {
        return empleadoDao.getCaptadorDelVendedor(idVendedor);
    }

    //hace lo mismo que vendedorEsCaptable solo que no verifica que el vendedor no fue captado por nadie
    private boolean vendedorCaptableIteracion(Integer idVendedor, Integer idVendedorCaptado){
        //si no fue captado por nadie, verifico que no haya captado al vendedor A o alguno de sus captadores
        Set<VendedorCaptado> v = empleadoDao.getVendedoresCaptados(idVendedorCaptado);

        if(v != null){
            for(VendedorCaptado vc : v){
                //"¿captó al vendedor A?"
                if(vc.getVendedorPadre().getId().equals(idVendedor)){
                    return false;
                }
                //si no es asi, itero sobre sus vendedores captados para ver si alguno de ellos ya captaron al vendedor A
                if(vc.getVendedorCaptado() != null){
                    if(vendedorEsCaptable(idVendedor,vc.getVendedorCaptado().getId())){
                        return true;
                    }
                }
                return false;
            }
        }

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
    public void delete(Integer id) {
        empleadoDao.delete(id);
    }

    @Override
    public void savePass(Credencial password) {
        empleadoDao.savePass(password);
    }

    @Override
    public void updatePass(Credencial password) {
        empleadoDao.updatePass(password);
    }

    @Override
    @Transactional
    public void deletePass(Credencial password) {
        empleadoDao.deletePass(password);
    }

    @Override
    public String getPass(Empleado usuario) {
        return empleadoDao.getPass(usuario);
    }
    @Override
    public Credencial getCredencial(Empleado usuario){
        return empleadoDao.getCredencial(usuario);
    }


    @Override
    public String getRol(Empleado user) {
        return empleadoDao.getRol(user);
    }

    @Override
    @Transactional
    public void cubrirAreaMercado(AreasMercado area, Integer idAsesor){
        //Busco el asesor
        Asesor asesor = (Asesor) empleadoDao.getById(idAsesor);
        //Busco el area
        AreasMercado areaACubrir = areaDAO.getById(area.getId());

        //aseguro que el asesor exista
        if(asesor !=null){
            Set<Empleado> asesores = areaACubrir.getAsesores();
            Boolean asesora = true;
            //reviso que el asesor no este relacionado con el area
            for(Empleado as : asesores){
                if(as.getId().equals(asesor.getId())){
                    asesora= false;
                }
            }
            //El area no esta relacionada y existe, entonces avanzo
            if(asesora && areaACubrir != null){
                asesor.cubrirArea(areaACubrir);
                empleadoDao.update(asesor);
            }else{
                System.out.println("EL AREA NO EXISTE O YA ESTA CUBIERTA POR EL ASESOR");
            }
            //TODO: manejar exception
        }else{
            System.out.println("ASESOR NO ENCONTRADO");
        }

    }

    @Override
    @Transactional
    public void desvincularAreaMercado(AreasMercado areasMercado, Integer idAsesor){
        //Busco el asesor
        Asesor asesor = null;
        //Busco el area
        AreasMercado area = areaDAO.getById(areasMercado.getId());
        //Verifico que exista el area
        if(area !=null){
            Set<Empleado> asesores = area.getAsesores();
            //Verifico que el empleado este relacionado al area
            for(Empleado as : asesores){
                if(as.getId().equals(idAsesor)){
                    asesor = (Asesor) as;
                }
            }
            if(asesor != null){
                asesor.quitarAreaAsesor(area);
                empleadoDao.update(asesor);
            }else{
                System.out.println("EL ASESOR NO ESTA RELACIONADO AL AREA");
                //TODO: manejar exception
            }
        }else{
            System.out.println("AREA NO ENCONTRADA");
        }

    }
}
