package com.si2001.webapp.mapper;


import com.si2001.webapp.entities.User;
import com.si2001.webapp.entities.Veicolo;
import org.mapstruct.Mapper;
import org.openapitools.model.UserDTO;
import org.openapitools.model.VeicoloDTO;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface VeicoloMapper {


    VeicoloDTO toDTO(Veicolo veicolo);

    Veicolo toEntity(VeicoloDTO veicoloDTO);
}
