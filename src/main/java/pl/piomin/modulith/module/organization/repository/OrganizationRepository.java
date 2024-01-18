package pl.piomin.modulith.module.organization.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.piomin.modulith.module.organization.model.Organization;

@Repository
public interface OrganizationRepository extends CrudRepository<Organization, Long> {
}
