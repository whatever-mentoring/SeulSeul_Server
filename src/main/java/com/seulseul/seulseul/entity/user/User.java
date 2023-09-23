package com.seulseul.seulseul.entity.user;

import com.seulseul.seulseul.entity.endPos.EndPos;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    private UUID uuid;

    @OneToMany(mappedBy = "user", orphanRemoval = true, fetch = FetchType.LAZY) // user가 삭제되면 endPos도 삭제됨
    private List<EndPos> endPosList = new ArrayList<>();

    public User(UUID uuid) {
        this.uuid = uuid;
    }
}
