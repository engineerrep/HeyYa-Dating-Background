package com.heyya.model.converter;

import com.heyya.model.dto.AccountPersistDto;
import com.heyya.model.dto.AccountUpdateDto;
import com.heyya.model.dto.UserPersistDto;
import com.heyya.model.entity.Account;
import com.heyya.model.vo.AccountVo;
import com.heyya.model.vo.SingInVo;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface AccountConverter {

    Account dto2Entity(AccountPersistDto persistDTO);

    AccountVo entity2Vo(Account user);

    Account dto2Entity(AccountUpdateDto updateDto);

    @Mappings({@Mapping(source = "userId", target = "user.id"),
    })
    SingInVo entity2SingInVo(Account user);

    @Mappings({@Mapping(source = "account", target = "nickname"),
    })
    UserPersistDto dto2dto(AccountPersistDto persistDTO);

}
