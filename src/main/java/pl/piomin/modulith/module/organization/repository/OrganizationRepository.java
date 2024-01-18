package pl.piomin.modulith.module.organization.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.piomin.modulith.module.organization.OrganizationDTO;
import pl.piomin.modulith.module.organization.model.Organization;

@Repository
public interface OrganizationRepository extends CrudRepository<Organization, Long> {

    @Query("""
           SELECT new pl.piomin.modulith.module.organization.OrganizationDTO(o.id, o.name, o.address)
           FROM Organization o
           WHERE o.id = :id
           """)
    OrganizationDTO findDTOById(Long id);
}
