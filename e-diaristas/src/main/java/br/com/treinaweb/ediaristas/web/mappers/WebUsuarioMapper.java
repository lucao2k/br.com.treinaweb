package br.com.treinaweb.ediaristas.web.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import br.com.treinaweb.ediaristas.core.models.Usuario;
import br.com.treinaweb.ediaristas.web.dtos.UsuarioCadastroForm;
import br.com.treinaweb.ediaristas.web.dtos.UsuarioEdicaoForm;

@Mapper(componentModel = "spring")
public interface WebUsuarioMapper {
    
    WebUsuarioMapper INSTANCE = Mappers.getMapper(WebUsuarioMapper.class);

    Usuario toModel(UsuarioCadastroForm form);

    Usuario toModel(UsuarioEdicaoForm form);

    UsuarioEdicaoForm toForm(Usuario model);

    //Com esta implementacao do MapStruct que foi configurado no pom.xml
    //Esta classe é gerada automaticamente no caminho
    // Target/generated-sources/.../web/mappers
    //Deixarei o WebServicoMapper.java como exemplo do método antigo.

}
