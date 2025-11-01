/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.imss.dpes.personaback.repository;

import mx.gob.imss.dpes.personaback.entity.McltUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 * @author edgar.arenas
 */
public interface UsuarioRepository extends JpaRepository<McltUsuario, Long>, JpaSpecificationExecutor<McltUsuario> {

    @Transactional
    @Modifying
    @Query(value = "update MCLT_USUARIO "
            + "set ind_activo=:ind_activo "
            + "where nom_usuario=:nom_usuario",
            nativeQuery = true)
    void updateIndActivoUsuario(
            @Param("ind_activo") Integer ind_activo,
            @Param("nom_usuario") String nom_usuario
    );

    @Transactional
    @Modifying
    @Query(value = "update MCLT_USUARIO "
            + "set nom_usuario=:nom_usuario_new "
            + "where nom_usuario=:nom_usuario_old",
            nativeQuery = true)
    void updateNomUsuario(
            @Param("nom_usuario_new") String nom_usuario_new,
            @Param("nom_usuario_old") String nom_usuario_old
    );

    @Query(name = "SELECT * FROM MGPMCLPE04.MCLT_USUARIO mu WHERE mu.CVE_USUARIO =: id", nativeQuery = true)
    McltUsuario findById(@Param("id") Long id);

    List<McltUsuario> findByNomUsuario(@Param("nomUsuario") String nomUsuario);
}
