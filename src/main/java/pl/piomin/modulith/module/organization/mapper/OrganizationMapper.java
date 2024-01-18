package pl.piomin.modulith.module.organization.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import pl.piomin.modulith.module.organization.OrganizationDTO;
import pl.piomin.modulith.module.organization.model.Organization;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OrganizationMapper {
    OrganizationDTO organizationToOrganizationDTO(Organization organization);
    Organization organizationDTOToOrganization(OrganizationDTO organizationDTO);
}
