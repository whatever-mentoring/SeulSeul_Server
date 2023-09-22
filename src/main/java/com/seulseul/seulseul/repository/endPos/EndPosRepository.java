package com.seulseul.seulseul.repository.endPos;

import com.seulseul.seulseul.entity.endPos.EndPos;
import com.seulseul.seulseul.entity.user.User;
import jdk.dynalink.linker.LinkerServices;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface EndPosRepository extends CrudRepository<EndPos, Long> {
    List<EndPos> findAllByUser(User user);

    Optional<EndPos> findByIdAndUser(Long id, User user);
}
